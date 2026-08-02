package net.nerds.fishtraps.blocks.woodenFishTrap;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.nerds.fishtraps.blocks.baseTrap.BaseFishTrapGui;

public class WoodenFishTrapGui extends BaseFishTrapGui<WoodenFishTrapContainer> {

    public WoodenFishTrapGui(WoodenFishTrapContainer container, Inventory playerInventory, Component title) {
        super(container, playerInventory, title);
    }

}
