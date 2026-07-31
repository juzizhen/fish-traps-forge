package net.nerds.fishtraps.blocks.woodenFishTrap;

import net.minecraft.world.entity.player.Inventory;
import net.nerds.fishtraps.blocks.FishTrapEntityManager;
import net.nerds.fishtraps.blocks.baseTrap.BaseFishTrapBlockEntity;
import net.nerds.fishtraps.blocks.baseTrap.BaseFishTrapContainer;

public class WoodenFishTrapContainer extends BaseFishTrapContainer {

    public WoodenFishTrapContainer(int containerId, Inventory playerInv, BaseFishTrapBlockEntity blockEntity) {
        super(FishTrapEntityManager.WOODEN_FISH_TRAP_MENU.get(), containerId, playerInv,
                blockEntity.getInventory(), blockEntity.getBlockPos());
    }
}
