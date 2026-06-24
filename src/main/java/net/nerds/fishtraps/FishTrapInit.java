package net.nerds.fishtraps;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.nerds.fishtraps.blocks.DiamondTrap.DiamondFishTrap;
import net.nerds.fishtraps.blocks.DiamondTrap.DiamondFishTrapContainer;
import net.nerds.fishtraps.blocks.DiamondTrap.DiamondFishTrapTileEntity;
import net.nerds.fishtraps.blocks.IronTrap.IronFishTrap;
import net.nerds.fishtraps.blocks.IronTrap.IronFishTrapContainer;
import net.nerds.fishtraps.blocks.IronTrap.IronFishTrapTileEntity;
import net.nerds.fishtraps.blocks.WoodenTrap.WoodenFishTrap;
import net.nerds.fishtraps.blocks.WoodenTrap.WoodenFishTrapContainer;
import net.nerds.fishtraps.blocks.WoodenTrap.WoodenFishTrapTileEntity;
import net.nerds.fishtraps.items.FishBait;

@EventBusSubscriber(modid = Fishtraps.MODID)
public class FishTrapInit {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Fishtraps.MODID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Fishtraps.MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, Fishtraps.MODID);
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(Registries.MENU, Fishtraps.MODID);

    public static final DeferredBlock<WoodenFishTrap> WOODEN_FISH_TRAP = BLOCKS.register("wooden_fish_trap", WoodenFishTrap::new);
    public static final DeferredBlock<IronFishTrap> IRON_FISH_TRAP = BLOCKS.register("iron_fish_trap", IronFishTrap::new);
    public static final DeferredBlock<DiamondFishTrap> DIAMOND_FISH_TRAP = BLOCKS.register("diamond_fish_trap", DiamondFishTrap::new);

    public static final DeferredItem<BlockItem> WOODEN_FISH_TRAP_ITEM = ITEMS.registerSimpleBlockItem("wooden_fish_trap", WOODEN_FISH_TRAP);
    public static final DeferredItem<BlockItem> IRON_FISH_TRAP_ITEM = ITEMS.registerSimpleBlockItem("iron_fish_trap", IRON_FISH_TRAP);
    public static final DeferredItem<BlockItem> DIAMOND_FISH_TRAP_ITEM = ITEMS.registerSimpleBlockItem("diamond_fish_trap", DIAMOND_FISH_TRAP);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<WoodenFishTrapTileEntity>> WOODEN_FISH_TRAP_ENTITY =
            BLOCK_ENTITIES.register("wooden_fish_trap",
                    () -> new BlockEntityType<>(WoodenFishTrapTileEntity::new, WOODEN_FISH_TRAP.get()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<IronFishTrapTileEntity>> IRON_FISH_TRAP_ENTITY =
            BLOCK_ENTITIES.register("iron_fish_trap",
                    () -> new BlockEntityType<>(IronFishTrapTileEntity::new, IRON_FISH_TRAP.get()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<DiamondFishTrapTileEntity>> DIAMOND_FISH_TRAP_ENTITY =
            BLOCK_ENTITIES.register("diamond_fish_trap",
                    () -> new BlockEntityType<>(DiamondFishTrapTileEntity::new, DIAMOND_FISH_TRAP.get()));

    public static final DeferredHolder<MenuType<?>, MenuType<WoodenFishTrapContainer>> WOODEN_FISH_TRAP_MENU =
            MENUS.register("wooden_fish_trap",
                    () -> IMenuTypeExtension.create((windowId, inv, buf) -> {
                        BlockPos pos = buf.readBlockPos();
                        BlockEntity be = inv.player.level().getBlockEntity(pos);
                        if (be instanceof WoodenFishTrapTileEntity trap) {
                            return new WoodenFishTrapContainer(windowId, inv, trap.getInventory(), FishTrapInit.WOODEN_FISH_TRAP_MENU.get(), pos);
                        }
                        return null;
                    }));
    public static final DeferredHolder<MenuType<?>, MenuType<IronFishTrapContainer>> IRON_FISH_TRAP_MENU =
            MENUS.register("iron_fish_trap",
                    () -> IMenuTypeExtension.create((windowId, inv, buf) -> {
                        BlockPos pos = buf.readBlockPos();
                        BlockEntity be = inv.player.level().getBlockEntity(pos);
                        if (be instanceof IronFishTrapTileEntity trap) {
                            return new IronFishTrapContainer(windowId, inv, trap.getInventory(), FishTrapInit.IRON_FISH_TRAP_MENU.get(), pos);
                        }
                        return null;
                    }));
    public static final DeferredHolder<MenuType<?>, MenuType<DiamondFishTrapContainer>> DIAMOND_FISH_TRAP_MENU =
            MENUS.register("diamond_fish_trap",
                    () -> IMenuTypeExtension.create((windowId, inv, buf) -> {
                        BlockPos pos = buf.readBlockPos();
                        BlockEntity be = inv.player.level().getBlockEntity(pos);
                        if (be instanceof DiamondFishTrapTileEntity trap) {
                            return new DiamondFishTrapContainer(windowId, inv, trap.getInventory(), FishTrapInit.DIAMOND_FISH_TRAP_MENU.get(), pos);
                        }
                        return null;
                    }));

    public static final DeferredItem<FishBait> FISH_BAIT = ITEMS.registerItem("fish_trap_bait",
            FishBait::new, () -> new Item.Properties().stacksTo(64)
    );

    static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Fishtraps.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> FISH_TRAP_TAB = CREATIVE_MODE_TABS.register("fishtraps_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.fishtraps.fishtraps"))
                    .icon(() -> new ItemStack(WOODEN_FISH_TRAP_ITEM.get()))
                    .displayItems((_, output) -> {
                        output.accept(WOODEN_FISH_TRAP_ITEM.get());
                        output.accept(IRON_FISH_TRAP_ITEM.get());
                        output.accept(DIAMOND_FISH_TRAP_ITEM.get());
                        output.accept(FISH_BAIT.get());
                    })
                    .build());

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(Capabilities.Item.BLOCK, WOODEN_FISH_TRAP_ENTITY.get(),
                (be, side) -> {
                    if (side == Direction.UP) return be.getBaitHandler();
                    if (side == Direction.DOWN) return be.getOutputHandler();
                    return null;
                });
        event.registerBlockEntity(Capabilities.Item.BLOCK, IRON_FISH_TRAP_ENTITY.get(),
                (be, side) -> {
                    if (side == Direction.UP) return be.getBaitHandler();
                    if (side == Direction.DOWN) return be.getOutputHandler();
                    return null;
                });
        event.registerBlockEntity(Capabilities.Item.BLOCK, DIAMOND_FISH_TRAP_ENTITY.get(),
                (be, side) -> {
                    if (side == Direction.UP) return be.getBaitHandler();
                    if (side == Direction.DOWN) return be.getOutputHandler();
                    return null;
                });
    }
}