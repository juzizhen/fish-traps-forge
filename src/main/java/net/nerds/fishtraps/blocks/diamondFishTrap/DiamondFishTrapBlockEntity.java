package net.nerds.fishtraps.blocks.diamondFishTrap;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;
import net.nerds.fishtraps.blocks.FishTrapEntityManager;
import net.nerds.fishtraps.blocks.baseTrap.BaseFishTrapBlockEntity;
import net.nerds.fishtraps.config.FishTrapsConfig;

public class DiamondFishTrapBlockEntity extends BaseFishTrapBlockEntity {

    public DiamondFishTrapBlockEntity(BlockPos pos, BlockState state) {
        super(FishTrapEntityManager.DIAMOND_FISH_TRAP_ENTITY.get(), pos, state,
                FishTrapsConfig.diamondTrapBaseTime.get(),
                FishTrapsConfig.diamondTrapLureLevel.get(),
                FishTrapsConfig.diamondTrapLuckLevel.get());
    }

    @Override
    protected String getTrapName() {
        return "diamond_fish_trap";
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.fishtraps.diamond_fish_trap");
    }

    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new DiamondFishTrapContainer(containerId, playerInventory, this);
    }
}
