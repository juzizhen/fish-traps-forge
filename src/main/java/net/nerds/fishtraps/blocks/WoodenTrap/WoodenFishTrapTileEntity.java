package net.nerds.fishtraps.blocks.WoodenTrap;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
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
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
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
import net.nerds.fishtraps.util.FishTrapItemHandler;
import net.nerds.fishtraps.util.FishTrapsConfig;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WoodenFishTrapTileEntity extends BlockEntity implements MenuProvider {

    private final long tickCheck;
    private final int luckOfTheSeaLevel;
    private final int lureLevel;
    private final boolean shouldTrapHavePenalty;
    private final boolean useDefaultFishingLoottable;
    protected FishTrapItemHandler fishTrapItemHandler = new FishTrapItemHandler(this) {
        @Override
        public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
            if (slot == 0) {
                if (!getStackInSlot(0).isEmpty()) return stack;

                ItemStack toInsert = stack.copy();
                toInsert.setCount(1);

                ItemStack remainder = stack.copy();
                remainder.shrink(1);

                return super.insertItem(slot, toInsert, simulate).isEmpty() ? remainder : stack;
            }
            return super.insertItem(slot, stack, simulate);
        }
    };
    protected RangedWrapper itemHandlerBait;
    protected LazyOptional<IItemHandler> capBait;
    protected RangedWrapper itemHandlerOutput;
    protected LazyOptional<IItemHandler> capOutput;
    private long tickCounter = 0;

    public WoodenFishTrapTileEntity(BlockPos pos, BlockState state) {
        super(FishTrapInit.WOODEN_FISH_TRAP_ENTITY.get(), pos, state);
        this.luckOfTheSeaLevel = FishTrapsConfig.woodenTrapLuckLevel.get();
        this.lureLevel = FishTrapsConfig.woodenTrapLureLevel.get();
        this.tickCheck = FishTrapsConfig.woodenTrapBaseTime.get();
        this.shouldTrapHavePenalty = FishTrapsConfig.shouldTrapHavePenalty.get();
        this.useDefaultFishingLoottable = FishTrapsConfig.useDefaultFishingLoottable.get();

        this.itemHandlerBait = new RangedWrapper(fishTrapItemHandler, 0, 1);
        this.capBait = LazyOptional.of(() -> itemHandlerBait);
        this.itemHandlerOutput = new RangedWrapper(fishTrapItemHandler, 1, 46);
        this.capOutput = LazyOptional.of(() -> itemHandlerOutput);
    }

    public void tick(Level level) {
        if (level.isClientSide) return;

        long effectiveTickCheck = this.tickCheck;
        ItemStack bait = itemHandlerBait.getStackInSlot(0);

        if (bait.isEmpty() && shouldTrapHavePenalty) {
            effectiveTickCheck *= Math.max(1, FishTrapsConfig.fishBaitDurability.get());
        }

        if (tickCounter >= effectiveTickCheck) {
            tickCounter = 0;
            if (isSurroundedByLiquid()) {
                fish();
            }
        } else {
            tickCounter++;
        }
    }

    private boolean isValidLiquid(BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        boolean valid = state.is(Blocks.WATER);

        if (FishTrapsConfig.workingInLava.get()) {
            valid = valid || state.is(Blocks.LAVA);
        }

        return valid;
    }

    private boolean isSurroundedByLiquid() {
        if (level == null) return false;

        BlockPos center = this.getBlockPos();
        Iterable<BlockPos> checkArea = BlockPos.betweenClosed(
                center.offset(-1, 0, -1),
                center.offset(1, 0, 1)
        );

        for (BlockPos checkPos : checkArea) {
            BlockState state = level.getBlockState(checkPos);
            if (!(isValidLiquid(checkPos) || state.getBlock() instanceof WoodenFishTrap)) {
                return false;
            }
        }
        return true;
    }

    private void fish() {
        if (level == null || !(level instanceof ServerLevel serverLevel)) return;

        ItemStack fishingRod = new ItemStack(Items.FISHING_ROD);
        Holder<net.minecraft.world.item.enchantment.Enchantment> lureHolder = serverLevel.registryAccess()
                .lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.LURE);
        Holder<net.minecraft.world.item.enchantment.Enchantment> luckHolder = serverLevel.registryAccess()
                .lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.LUCK_OF_THE_SEA);

        ItemEnchantments.Mutable mutableEnchantments = new ItemEnchantments.Mutable(ItemEnchantments.EMPTY);
        mutableEnchantments.set(lureHolder, this.lureLevel);
        mutableEnchantments.set(luckHolder, this.luckOfTheSeaLevel);
        fishingRod.set(net.minecraft.core.component.DataComponents.ENCHANTMENTS, mutableEnchantments.toImmutable());

        LootParams params = new LootParams.Builder(serverLevel)
                .withParameter(LootContextParams.ORIGIN, this.getBlockPos().getCenter())
                .withParameter(LootContextParams.TOOL, fishingRod)
                .withLuck(this.luckOfTheSeaLevel)
                .create(LootContextParamSets.FISHING);

        LootTable lootTable;
        if (useDefaultFishingLoottable) {
            lootTable = serverLevel.getServer().reloadableRegistries().getLootTable(BuiltInLootTables.FISHING);
            if (serverLevel.random.nextDouble() < 0.04 + ((double) this.luckOfTheSeaLevel / 100)) {
                lootTable = serverLevel.getServer().reloadableRegistries().getLootTable(BuiltInLootTables.FISHING_TREASURE);
            }
        } else {
            ResourceKey<LootTable> customKey = ResourceKey.create(Registries.LOOT_TABLE,
                    ResourceLocation.fromNamespaceAndPath("fishtraps", "traps/wooden_fish_trap"));
            lootTable = serverLevel.getServer().reloadableRegistries().getLootTable(customKey);
        }

        List<ItemStack> loot = lootTable.getRandomItems(params);
        fishTrapItemHandler.addListToInventory(loot);

        ItemStack bait = itemHandlerBait.getStackInSlot(0);
        if (!bait.isEmpty()) {
            int currentDamage = bait.getOrDefault(DataComponents.DAMAGE, 0);
            int maxDamage = FishTrapsConfig.fishBaitDurability.get();

            if (currentDamage + 1 >= maxDamage) {
                itemHandlerBait.setStackInSlot(0, ItemStack.EMPTY);
            } else {
                bait.set(DataComponents.MAX_DAMAGE, maxDamage);
                bait.set(DataComponents.DAMAGE, currentDamage + 1);
            }
            setChanged();
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
        return fishTrapItemHandler;
    }

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
        return Component.translatable("block.fishtraps.wooden_fish_trap");
    }

    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new WoodenFishTrapContainer(containerId, playerInventory, getInventory(), FishTrapInit.WOODEN_FISH_TRAP_MENU.get());
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.saveAdditional(tag, provider);
        tag.put("Inventory", fishTrapItemHandler.serializeNBT(provider));
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.loadAdditional(tag, provider);
        if (tag.contains("Inventory")) {
            fishTrapItemHandler.deserializeNBT(provider, tag.getCompound("Inventory"));
        }
    }
}