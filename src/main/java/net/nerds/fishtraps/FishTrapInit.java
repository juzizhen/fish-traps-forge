package net.nerds.fishtraps;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
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


@Mod.EventBusSubscriber(modid = Fishtraps.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FishTrapInit {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Fishtraps.MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Fishtraps.MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Fishtraps.MODID);
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, Fishtraps.MODID);

    public static final RegistryObject<WoodenFishTrap> WOODEN_FISH_TRAP = BLOCKS.register("wooden_fish_trap", WoodenFishTrap::new);
    public static final RegistryObject<IronFishTrap> IRON_FISH_TRAP = BLOCKS.register("iron_fish_trap", IronFishTrap::new);
    public static final RegistryObject<DiamondFishTrap> DIAMOND_FISH_TRAP = BLOCKS.register("diamond_fish_trap", DiamondFishTrap::new);

    public static final RegistryObject<Item> WOODEN_FISH_TRAP_ITEM = ITEMS.register("wooden_fish_trap",
            () -> new BlockItem(WOODEN_FISH_TRAP.get(), new Item.Properties()));
    public static final RegistryObject<Item> IRON_FISH_TRAP_ITEM = ITEMS.register("iron_fish_trap",
            () -> new BlockItem(IRON_FISH_TRAP.get(), new Item.Properties()));
    public static final RegistryObject<Item> DIAMOND_FISH_TRAP_ITEM = ITEMS.register("diamond_fish_trap",
            () -> new BlockItem(DIAMOND_FISH_TRAP.get(), new Item.Properties()));

    public static final RegistryObject<BlockEntityType<WoodenFishTrapTileEntity>> WOODEN_FISH_TRAP_ENTITY =
            BLOCK_ENTITIES.register("wooden_fish_trap",
                    () -> BlockEntityType.Builder.of(WoodenFishTrapTileEntity::new, WOODEN_FISH_TRAP.get()).build(null));
    public static final RegistryObject<BlockEntityType<IronFishTrapTileEntity>> IRON_FISH_TRAP_ENTITY =
            BLOCK_ENTITIES.register("iron_fish_trap",
                    () -> BlockEntityType.Builder.of(IronFishTrapTileEntity::new, IRON_FISH_TRAP.get()).build(null));
    public static final RegistryObject<BlockEntityType<DiamondFishTrapTileEntity>> DIAMOND_FISH_TRAP_ENTITY =
            BLOCK_ENTITIES.register("diamond_fish_trap",
                    () -> BlockEntityType.Builder.of(DiamondFishTrapTileEntity::new, DIAMOND_FISH_TRAP.get()).build(null));

    public static final RegistryObject<MenuType<WoodenFishTrapContainer>> WOODEN_FISH_TRAP_MENU =
            MENUS.register("wooden_fish_trap",
                    () -> IForgeMenuType.create((windowId, inv, buf) -> {
                        BlockPos pos = buf.readBlockPos();
                        BlockEntity be = inv.player.level().getBlockEntity(pos);
                        if (be instanceof WoodenFishTrapTileEntity trap) {
                            return new WoodenFishTrapContainer(windowId, inv, trap.getInventory(), FishTrapInit.WOODEN_FISH_TRAP_MENU.get());
                        }
                        return null;
                    }));
    public static final RegistryObject<MenuType<IronFishTrapContainer>> IRON_FISH_TRAP_MENU =
            MENUS.register("iron_fish_trap",
                    () -> IForgeMenuType.create((windowId, inv, buf) -> {
                        BlockPos pos = buf.readBlockPos();
                        BlockEntity be = inv.player.level().getBlockEntity(pos);
                        if (be instanceof IronFishTrapTileEntity trap) {
                            return new IronFishTrapContainer(windowId, inv, trap.getInventory(), FishTrapInit.IRON_FISH_TRAP_MENU.get());
                        }
                        return null;
                    }));
    public static final RegistryObject<MenuType<DiamondFishTrapContainer>> DIAMOND_FISH_TRAP_MENU =
            MENUS.register("diamond_fish_trap",
                    () -> IForgeMenuType.create((windowId, inv, buf) -> {
                        BlockPos pos = buf.readBlockPos();
                        BlockEntity be = inv.player.level().getBlockEntity(pos);
                        if (be instanceof DiamondFishTrapTileEntity trap) {
                            return new DiamondFishTrapContainer(windowId, inv, trap.getInventory(), FishTrapInit.DIAMOND_FISH_TRAP_MENU.get());
                        }
                        return null;
                    }));


    public static final RegistryObject<Item> FISH_BAIT = ITEMS.register("fish_trap_bait", FishBait::new);

    static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Fishtraps.MODID);

    public static final RegistryObject<CreativeModeTab> FISH_TRAP_TAB = CREATIVE_MODE_TABS.register("fishtraps_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.fishtraps.fishtraps"))
                    .icon(() -> new ItemStack(WOODEN_FISH_TRAP_ITEM.get()))
                    .displayItems((parameters, output) -> {
                        output.accept(WOODEN_FISH_TRAP_ITEM.get());
                        output.accept(IRON_FISH_TRAP_ITEM.get());
                        output.accept(DIAMOND_FISH_TRAP_ITEM.get());
                        output.accept(FISH_BAIT.get());
                    })
                    .build());
}