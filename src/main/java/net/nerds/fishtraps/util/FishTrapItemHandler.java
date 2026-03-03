package net.nerds.fishtraps.util;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FishTrapItemHandler extends ItemStackHandler {

    private final net.minecraft.world.level.block.entity.BlockEntity tileEntity;

    public FishTrapItemHandler(net.minecraft.world.level.block.entity.BlockEntity tileEntity) {
        super(46);
        this.tileEntity = tileEntity;
    }

    @Override
    protected void onContentsChanged(int slot) {
        super.onContentsChanged(slot);
        if (tileEntity != null) {
            tileEntity.setChanged();
        }
    }

    public void addListToInventory(List<ItemStack> loot) {
        for (ItemStack stackToAdd : loot) {
            if (stackToAdd.isEmpty()) continue;

            ItemStack remaining = stackToAdd.copy();

            for (int i = 1; i < getSlots(); i++) {
                ItemStack existing = getStackInSlot(i);
                if (!existing.isEmpty() &&
                        ItemStack.isSameItemSameComponents(existing, remaining) &&
                existing.getCount() < existing.getMaxStackSize()) {
                    remaining = insertItem(i, remaining, false);
                    if (remaining.isEmpty()) break;
                }
            }

            if (!remaining.isEmpty()) {
                for (int i = 1; i < getSlots(); i++) {
                    if (getStackInSlot(i).isEmpty()) {
                        setStackInSlot(i, remaining);
                        break;
                    }
                }
            }
        }
    }

    @Override
    public int getSlotLimit(int slot) {
        if (slot == 0) return 64;
        ItemStack stack = getStackInSlot(slot);
        return stack.isEmpty() ? 64 : stack.getMaxStackSize();
    }

    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        if (slot == 0) {
            return stack.getItem() instanceof net.nerds.fishtraps.items.FishBait;
        }
        return true;
    }
}