package net.nerds.fishtraps.blocks.diamondFishTrap;

import net.minecraft.world.entity.player.Inventory;
import net.nerds.fishtraps.blocks.FishTrapEntityManager;
import net.nerds.fishtraps.blocks.baseTrap.BaseFishTrapBlockEntity;
import net.nerds.fishtraps.blocks.baseTrap.BaseFishTrapContainer;

public class DiamondFishTrapContainer extends BaseFishTrapContainer {

    public DiamondFishTrapContainer(int containerId, Inventory playerInv, BaseFishTrapBlockEntity blockEntity) {
        super(FishTrapEntityManager.DIAMOND_FISH_TRAP_MENU.get(), containerId, playerInv,
                blockEntity.getInventory(), blockEntity.getBlockPos());
    }
}
