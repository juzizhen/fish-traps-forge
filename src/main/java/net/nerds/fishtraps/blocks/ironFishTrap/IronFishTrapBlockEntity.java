package net.nerds.fishtraps.blocks.ironFishTrap;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;
import net.nerds.fishtraps.blocks.FishTrapEntityManager;
import net.nerds.fishtraps.blocks.baseTrap.BaseFishTrapBlockEntity;
import net.nerds.fishtraps.config.FishTrapsConfig;

public class IronFishTrapBlockEntity extends BaseFishTrapBlockEntity {

    public IronFishTrapBlockEntity(BlockPos pos, BlockState state) {
        super(FishTrapEntityManager.IRON_FISH_TRAP_ENTITY.get(), pos, state,
                FishTrapsConfig.ironTrapBaseTime.get(),
                FishTrapsConfig.ironTrapLureLevel.get(),
                FishTrapsConfig.ironTrapLuckLevel.get());
    }

    @Override
    protected String getTrapName() {
        return "iron_fish_trap";
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.fishtraps.iron_fish_trap");
    }

    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new IronFishTrapContainer(containerId, playerInventory, this);
    }
}
