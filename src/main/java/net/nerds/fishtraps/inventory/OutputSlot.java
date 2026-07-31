package net.nerds.fishtraps.inventory;

import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.transfer.IndexModifier;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ResourceHandlerSlot;

/**
 * Output-only inventory slot — prevents item insertion.
 */
public class OutputSlot extends ResourceHandlerSlot {

    public OutputSlot(ResourceHandler<ItemResource> handler, IndexModifier<ItemResource> setter, int index, int x, int y) {
        super(handler, setter, index, x, y);
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return false;
    }
}
