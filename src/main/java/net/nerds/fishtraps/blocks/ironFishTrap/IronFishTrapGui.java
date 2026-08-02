package net.nerds.fishtraps.blocks.ironFishTrap;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.nerds.fishtraps.blocks.baseTrap.BaseFishTrapGui;

public class IronFishTrapGui extends BaseFishTrapGui<IronFishTrapContainer> {

    public IronFishTrapGui(IronFishTrapContainer container, Inventory playerInventory, Component title) {
        super(container, playerInventory, title);
    }

}
