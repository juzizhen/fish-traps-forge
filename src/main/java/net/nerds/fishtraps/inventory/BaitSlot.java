package net.nerds.fishtraps.inventory;

import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.transfer.IndexModifier;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ResourceHandlerSlot;
import net.nerds.fishtraps.items.FishingBait;

public class BaitSlot extends ResourceHandlerSlot {

    public BaitSlot(ResourceHandler<ItemResource> handler, IndexModifier<ItemResource> setter, int index, int x, int y) {
        super(handler, setter, index, x, y);
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return stack.getItem() instanceof FishingBait;
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        return 1;
    }
}
