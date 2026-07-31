package net.nerds.fishtraps.blocks.woodenFishTrap;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;
import net.nerds.fishtraps.blocks.FishTrapEntityManager;
import net.nerds.fishtraps.blocks.baseTrap.BaseFishTrapBlockEntity;
import net.nerds.fishtraps.config.FishTrapsConfig;

public class WoodenFishTrapBlockEntity extends BaseFishTrapBlockEntity {

    public WoodenFishTrapBlockEntity(BlockPos pos, BlockState state) {
        super(FishTrapEntityManager.WOODEN_FISH_TRAP_ENTITY.get(), pos, state,
                FishTrapsConfig.woodenTrapBaseTime.get(),
                FishTrapsConfig.woodenTrapLureLevel.get(),
                FishTrapsConfig.woodenTrapLuckLevel.get());
    }

    @Override
    protected String getTrapName() {
        return "wooden_fish_trap";
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.fishtraps.wooden_fish_trap");
    }

    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new WoodenFishTrapContainer(containerId, playerInventory, this);
    }
}
