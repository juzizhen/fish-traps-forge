package net.nerds.fishtraps.blocks.ironFishTrap;

import net.minecraft.world.entity.player.Inventory;
import net.nerds.fishtraps.blocks.FishTrapEntityManager;
import net.nerds.fishtraps.blocks.baseTrap.BaseFishTrapBlockEntity;
import net.nerds.fishtraps.blocks.baseTrap.BaseFishTrapContainer;

public class IronFishTrapContainer extends BaseFishTrapContainer {

    public IronFishTrapContainer(int containerId, Inventory playerInv, BaseFishTrapBlockEntity blockEntity) {
        super(FishTrapEntityManager.IRON_FISH_TRAP_MENU.get(), containerId, playerInv,
                blockEntity.getInventory(), blockEntity.getBlockPos());
    }
}
