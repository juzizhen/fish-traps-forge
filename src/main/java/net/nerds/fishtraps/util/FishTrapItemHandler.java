package net.nerds.fishtraps.util;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemUtil;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import net.neoforged.neoforge.transfer.transaction.TransactionContext;

import java.util.List;

public class FishTrapItemHandler extends ItemStacksResourceHandler {

    private final net.minecraft.world.level.block.entity.BlockEntity tileEntity;

    public FishTrapItemHandler(net.minecraft.world.level.block.entity.BlockEntity tileEntity) {
        super(46);
        this.tileEntity = tileEntity;
    }

    @Override
    protected void onContentsChanged(int index, ItemStack previousContents) {
        super.onContentsChanged(index, previousContents);
        if (tileEntity != null) {
            tileEntity.setChanged();
        }
    }

    /**
     * Overrides insert to enforce bait slot logic:
     * Slot 0 only accepts FishBait items, max 1 at a time.
     */
    @Override
    public int insert(int index, ItemResource resource, int amount, TransactionContext transaction) {
        if (index == 0) {
            // Bait slot: only accept bait items, max 1
            if (!resource.isEmpty() && !(resource.value() instanceof net.nerds.fishtraps.items.FishBait)) {
                return 0;
            }
            int currentAmount = getAmountAsInt(0);
            if (currentAmount > 0) return 0; // slot occupied
            int toInsert = Math.min(amount, 1);
            return super.insert(0, resource, toInsert, transaction);
        }
        return super.insert(index, resource, amount, transaction);
    }

    public void addListToInventory(List<ItemStack> loot) {
        try (var tx = Transaction.openRoot()) {
            for (ItemStack stackToAdd : loot) {
                if (stackToAdd.isEmpty()) continue;

                ItemResource resource = ItemResource.of(stackToAdd);
                int remaining = stackToAdd.getCount();

                // Try to insert into existing stacks in output slots (1 to size-1)
                for (int i = 1; i < size() && remaining > 0; i++) {
                    ItemStack existing = ItemUtil.getStack(this, i);
                    if (!existing.isEmpty()
                            && ItemStack.isSameItemSameComponents(existing, stackToAdd)
                            && existing.getCount() < existing.getMaxStackSize()) {
                        int inserted = insert(i, resource, remaining, tx);
                        remaining -= inserted;
                    }
                }

                // Try to insert into empty slots
                if (remaining > 0) {
                    for (int i = 1; i < size() && remaining > 0; i++) {
                        if (getAmountAsInt(i) == 0) {
                            int inserted = insert(i, resource, remaining, tx);
                            remaining -= inserted;
                        }
                    }
                }
            }
            tx.commit();
        }
    }

    @Override
    protected int getCapacity(int index, ItemResource resource) {
        if (index == 0) return 1;
        if (resource.isEmpty()) return 64;
        return Math.min(resource.getMaxStackSize(), Item.ABSOLUTE_MAX_STACK_SIZE);
    }

    @Override
    public boolean isValid(int index, ItemResource resource) {
        if (index == 0) {
            return !resource.isEmpty() && resource.value() instanceof net.nerds.fishtraps.items.FishBait;
        }
        return true;
    }

    /**
     * Helper method for backward compatibility - gets the ItemStack at a given slot.
     */
    public ItemStack getStackInSlot(int slot) {
        return ItemUtil.getStack(this, slot);
    }

    /**
     * Helper to set a stack directly in a slot.
     */
    public void setStackInSlot(int slot, ItemStack stack) {
        if (stack.isEmpty()) {
            set(slot, ItemResource.EMPTY, 0);
        } else {
            set(slot, ItemResource.of(stack), stack.getCount());
        }
    }
}