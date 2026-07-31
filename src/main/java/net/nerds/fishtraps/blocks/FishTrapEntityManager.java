package net.nerds.fishtraps.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.nerds.fishtraps.Fishtraps;
import net.nerds.fishtraps.blocks.diamondFishTrap.DiamondFishTrapBlockEntity;
import net.nerds.fishtraps.blocks.diamondFishTrap.DiamondFishTrapContainer;
import net.nerds.fishtraps.blocks.ironFishTrap.IronFishTrapBlockEntity;
import net.nerds.fishtraps.blocks.ironFishTrap.IronFishTrapContainer;
import net.nerds.fishtraps.blocks.woodenFishTrap.WoodenFishTrapBlockEntity;
import net.nerds.fishtraps.blocks.woodenFishTrap.WoodenFishTrapContainer;

@EventBusSubscriber(modid = Fishtraps.MODID)
public class FishTrapEntityManager {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, Fishtraps.MODID);
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(Registries.MENU, Fishtraps.MODID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<WoodenFishTrapBlockEntity>> WOODEN_FISH_TRAP_ENTITY =
            BLOCK_ENTITIES.register("wooden_fish_trap_entity",
                    () -> new BlockEntityType<>(WoodenFishTrapBlockEntity::new, FishTrapsManager.WOODEN_FISH_TRAP.get()));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<IronFishTrapBlockEntity>> IRON_FISH_TRAP_ENTITY =
            BLOCK_ENTITIES.register("iron_fish_trap_entity",
                    () -> new BlockEntityType<>(IronFishTrapBlockEntity::new, FishTrapsManager.IRON_FISH_TRAP.get()));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<DiamondFishTrapBlockEntity>> DIAMOND_FISH_TRAP_ENTITY =
            BLOCK_ENTITIES.register("diamond_fish_trap_entity",
                    () -> new BlockEntityType<>(DiamondFishTrapBlockEntity::new, FishTrapsManager.DIAMOND_FISH_TRAP.get()));

    public static final DeferredHolder<MenuType<?>, MenuType<WoodenFishTrapContainer>> WOODEN_FISH_TRAP_MENU =
            MENUS.register("wooden_fish_trap_container",
                    () -> IMenuTypeExtension.create((windowId, inv, buf) -> {
                        BlockPos pos = buf.readBlockPos();
                        BlockEntity be = inv.player.level().getBlockEntity(pos);
                        if (be instanceof WoodenFishTrapBlockEntity trap) {
                            return new WoodenFishTrapContainer(windowId, inv, trap);
                        }
                        throw new IllegalStateException("Unable to open Wooden Fish Trap menu at " + pos);
                    }));

    public static final DeferredHolder<MenuType<?>, MenuType<IronFishTrapContainer>> IRON_FISH_TRAP_MENU =
            MENUS.register("iron_fish_trap_container",
                    () -> IMenuTypeExtension.create((windowId, inv, buf) -> {
                        BlockPos pos = buf.readBlockPos();
                        BlockEntity be = inv.player.level().getBlockEntity(pos);
                        if (be instanceof IronFishTrapBlockEntity trap) {
                            return new IronFishTrapContainer(windowId, inv, trap);
                        }
                        throw new IllegalStateException("Unable to open Iron Fish Trap menu at " + pos);
                    }));

    public static final DeferredHolder<MenuType<?>, MenuType<DiamondFishTrapContainer>> DIAMOND_FISH_TRAP_MENU =
            MENUS.register("diamond_fish_trap_container",
                    () -> IMenuTypeExtension.create((windowId, inv, buf) -> {
                        BlockPos pos = buf.readBlockPos();
                        BlockEntity be = inv.player.level().getBlockEntity(pos);
                        if (be instanceof DiamondFishTrapBlockEntity trap) {
                            return new DiamondFishTrapContainer(windowId, inv, trap);
                        }
                        throw new IllegalStateException("Unable to open Diamond Fish Trap menu at " + pos);
                    }));

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
