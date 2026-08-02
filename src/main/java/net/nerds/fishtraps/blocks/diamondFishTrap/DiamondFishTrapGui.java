package net.nerds.fishtraps.blocks.diamondFishTrap;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.nerds.fishtraps.blocks.baseTrap.BaseFishTrapGui;

public class DiamondFishTrapGui extends BaseFishTrapGui<DiamondFishTrapContainer> {

    public DiamondFishTrapGui(DiamondFishTrapContainer container, Inventory playerInventory, Component title) {
        super(container, playerInventory, title);
    }

}
