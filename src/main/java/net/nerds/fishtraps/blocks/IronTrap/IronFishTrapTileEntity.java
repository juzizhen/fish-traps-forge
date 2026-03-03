package net.nerds.fishtraps.blocks.IronTrap;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.RangedWrapper;
import net.nerds.fishtraps.FishTrapInit;
import net.nerds.fishtraps.Fishtraps;
import net.nerds.fishtraps.items.FishBait;
import net.nerds.fishtraps.util.FishTrapItemHandler;
import net.nerds.fishtraps.util.FishTrapsConfig;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class IronFishTrapTileEntity extends BlockEntity implements MenuProvider {

    protected FishTrapItemHandler fishTrapItemHandler = new FishTrapItemHandler();
    protected RangedWrapper itemHandlerBait = new RangedWrapper(fishTrapItemHandler, 0, 1);
    protected LazyOptional<IItemHandler> capBait = LazyOptional.of(() -> itemHandlerBait);
    protected RangedWrapper itemHandlerOutput = new RangedWrapper(fishTrapItemHandler, 1, 46);
    protected LazyOptional<IItemHandler> capOutput = LazyOptional.of(() -> itemHandlerOutput);

    private long tickCounter = 0;
    private final long tickCheck;
    private final int luckOfTheSeaLevel;
    private final int lureLevel;
    private final int fishBaitDurability;
    private final boolean shouldTrapHavePenalty;
    private final boolean useDefaultFishingLoottable;

    public IronFishTrapTileEntity(BlockPos pos, BlockState state) {
        super(FishTrapInit.IRON_FISH_TRAP_ENTITY.get(), pos, state);
        this.luckOfTheSeaLevel = FishTrapsConfig.ironTrapLuckLevel.get();
        this.lureLevel = FishTrapsConfig.ironTrapLureLevel.get();
        this.tickCheck = FishTrapsConfig.ironTrapBaseTime.get();
        this.fishBaitDurability = FishTrapsConfig.fishBaitDurability.get();
        this.shouldTrapHavePenalty = FishTrapsConfig.shouldTrapHavePenalty.get();
        this.useDefaultFishingLoottable = FishTrapsConfig.useDefaultFishingLoottable.get();
    }

    public void tick(Level level) {
        if (level.isClientSide) return;

        long effectiveTickCheck = this.tickCheck;
        ItemStack bait = itemHandlerBait.getStackInSlot(0);
        if (bait.isEmpty() && shouldTrapHavePenalty) {
            effectiveTickCheck = effectiveTickCheck * fishBaitDurability;
        }

        if (tickCounter >= effectiveTickCheck) {
            tickCounter = 0;
            fish();
        } else {
            tickCounter++;
        }
    }

    private void fish() {
        if (level == null || !(level instanceof ServerLevel serverLevel)) return;
        ItemStack fishingRod = new ItemStack(Items.FISHING_ROD);
        fishingRod.enchant(Enchantments.FISHING_SPEED, this.lureLevel);
        fishingRod.enchant(Enchantments.FISHING_LUCK, this.luckOfTheSeaLevel);

        LootParams params = new LootParams.Builder(serverLevel)
                .withParameter(LootContextParams.ORIGIN, this.getBlockPos().getCenter())
                .withParameter(LootContextParams.TOOL, fishingRod)
                .withLuck(this.luckOfTheSeaLevel)
                .create(LootContextParamSets.FISHING);

        LootTable lootTable;
        if (useDefaultFishingLoottable) {
            lootTable = serverLevel.getServer().getLootData().getLootTable(BuiltInLootTables.FISHING);
            if (serverLevel.random.nextDouble() < 0.04 + ((double) this.luckOfTheSeaLevel / 100)) {
                lootTable = serverLevel.getServer().getLootData().getLootTable(BuiltInLootTables.FISHING_TREASURE);
            }
        } else {
            lootTable = serverLevel.getServer().getLootData().getLootTable(new ResourceLocation(Fishtraps.MODID, "traps/iron_fish_trap"));
        }

        List<ItemStack> loot = lootTable.getRandomItems(params);
        fishTrapItemHandler.addListToInventory(loot);

        ItemStack bait = itemHandlerBait.getStackInSlot(0);
        if (bait.getItem() instanceof FishBait) {
            if (bait.hurt(1, serverLevel.getRandom(), null)) {
                itemHandlerBait.setStackInSlot(0, ItemStack.EMPTY);
                setChanged();
            }
        }
    }

    public void dropContents() {
        if (level == null) return;
        for (int i = 0; i < fishTrapItemHandler.getSlots(); i++) {
            ItemStack stack = fishTrapItemHandler.getStackInSlot(i);
            if (!stack.isEmpty()) {
                Containers.dropItemStack(level, worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), stack);
            }
        }
    }

    public FishTrapItemHandler getInventory() {
        return this.fishTrapItemHandler;
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable net.minecraft.core.Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            if (side == net.minecraft.core.Direction.UP) {
                return capBait.cast();
            } else if (side == net.minecraft.core.Direction.DOWN) {
                return capOutput.cast();
            }
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        capBait.invalidate();
        capOutput.invalidate();
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.fishtraps.iron_fish_trap");
    }

    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new IronFishTrapContainer(containerId, playerInventory, this.getInventory(), FishTrapInit.IRON_FISH_TRAP_MENU.get());
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains("Inventory")) {
            fishTrapItemHandler.deserializeNBT(tag.getCompound("Inventory"));
        }
    }
}