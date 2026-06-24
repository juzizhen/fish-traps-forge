package net.nerds.fishtraps.blocks.IronTrap;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
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
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.transfer.RangedResourceHandler;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.nerds.fishtraps.FishTrapInit;
import net.nerds.fishtraps.util.FishTrapItemHandler;
import net.nerds.fishtraps.util.FishTrapsConfig;

import net.minecraft.core.NonNullList;

import java.util.List;

public class IronFishTrapTileEntity extends BlockEntity implements MenuProvider {

    private final long tickCheck;
    private final int luckOfTheSeaLevel;
    private final int lureLevel;
    private final boolean shouldTrapHavePenalty;
    private final boolean useDefaultFishingLoottable;
    protected FishTrapItemHandler fishTrapItemHandler = new FishTrapItemHandler(this);
    protected RangedResourceHandler<ItemResource> itemHandlerBait;
    protected RangedResourceHandler<ItemResource> itemHandlerOutput;
    private long tickCounter = 0;

    public IronFishTrapTileEntity(BlockPos pos, BlockState state) {
        super(FishTrapInit.IRON_FISH_TRAP_ENTITY.get(), pos, state);
        this.luckOfTheSeaLevel = FishTrapsConfig.ironTrapLuckLevel.get();
        this.lureLevel = FishTrapsConfig.ironTrapLureLevel.get();
        this.tickCheck = FishTrapsConfig.ironTrapBaseTime.get();
        this.shouldTrapHavePenalty = FishTrapsConfig.shouldTrapHavePenalty.get();
        this.useDefaultFishingLoottable = FishTrapsConfig.useDefaultFishingLoottable.get();

        this.itemHandlerBait = RangedResourceHandler.of(fishTrapItemHandler, 0, 1);
        this.itemHandlerOutput = RangedResourceHandler.of(fishTrapItemHandler, 1, 46);
    }

    public void tick(Level level) {
        if (level.isClientSide()) return;

        long effectiveTickCheck = this.tickCheck;
        ItemStack bait = fishTrapItemHandler.getStackInSlot(0);

        if (bait.isEmpty() && shouldTrapHavePenalty) {
            effectiveTickCheck *= Math.max(1, FishTrapsConfig.trapPenaltyMultiplier.get());
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
        if (level == null) return false;
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
            if (!(isValidLiquid(checkPos) || state.getBlock() instanceof IronFishTrap)) {
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
                .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(this.getBlockPos()))
                .withParameter(LootContextParams.TOOL, fishingRod)
                .withLuck(this.luckOfTheSeaLevel)
                .create(LootContextParamSets.FISHING);

        LootTable lootTable;
        if (useDefaultFishingLoottable) {
            lootTable = serverLevel.getServer().reloadableRegistries().getLootTable(BuiltInLootTables.FISHING);
            if (serverLevel.getRandom().nextDouble() < 0.04 + ((double) this.luckOfTheSeaLevel / 100)) {
                lootTable = serverLevel.getServer().reloadableRegistries().getLootTable(BuiltInLootTables.FISHING_TREASURE);
            }
        } else {
            ResourceKey<LootTable> customKey = ResourceKey.create(Registries.LOOT_TABLE,
                    Identifier.fromNamespaceAndPath("fishtraps", "traps/iron_fish_trap"));
            lootTable = serverLevel.getServer().reloadableRegistries().getLootTable(customKey);
        }

        List<ItemStack> loot = lootTable.getRandomItems(params);
        fishTrapItemHandler.addListToInventory(loot);

        ItemStack bait = fishTrapItemHandler.getStackInSlot(0);
        if (!bait.isEmpty()) {
            int currentDamage = bait.getOrDefault(DataComponents.DAMAGE, 0);
            int maxDamage = FishTrapsConfig.fishBaitDurability.get();

            if (currentDamage + 1 >= maxDamage) {
                fishTrapItemHandler.setStackInSlot(0, ItemStack.EMPTY);
            } else {
                bait.set(DataComponents.MAX_DAMAGE, maxDamage);
                bait.set(DataComponents.DAMAGE, currentDamage + 1);
                fishTrapItemHandler.setStackInSlot(0, bait);
            }
            setChanged();
        }
    }

    private boolean dropped = false;

    public void dropContents() {
        if (level == null || dropped) return;
        dropped = true;
        NonNullList<ItemStack> contents = fishTrapItemHandler.copyToList();
        for (ItemStack stack : contents) {
            if (!stack.isEmpty()) {
                Block.popResource(level, worldPosition, stack);
            }
        }
        // Clear the handler after dropping
        for (int i = 0; i < fishTrapItemHandler.size(); i++) {
            fishTrapItemHandler.setStackInSlot(i, ItemStack.EMPTY);
        }
    }

    public FishTrapItemHandler getInventory() {
        return fishTrapItemHandler;
    }

    public ResourceHandler<ItemResource> getBaitHandler() {
        return itemHandlerBait;
    }

    public ResourceHandler<ItemResource> getOutputHandler() {
        return itemHandlerOutput;
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.fishtraps.iron_fish_trap");
    }

    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new IronFishTrapContainer(containerId, playerInventory, getInventory(), FishTrapInit.IRON_FISH_TRAP_MENU.get(), getBlockPos());
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        ValueOutput inventoryOutput = output.child("Inventory");
        fishTrapItemHandler.serialize(inventoryOutput);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        input.child("Inventory").ifPresent(inventoryInput -> {
            fishTrapItemHandler.deserialize(inventoryInput);
            this.setChanged();
        });
    }

    @Override
    public void onLoad() {
        super.onLoad();
        if (level != null && level.isClientSide()) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
        }
    }

    @Override
    public CompoundTag getUpdateTag(net.minecraft.core.HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}