package net.nerds.fishtraps.inventory;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemUtil;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import net.neoforged.neoforge.transfer.transaction.TransactionContext;
import net.nerds.fishtraps.items.FishingBait;

import java.util.List;

public class FishTrapItemHandler extends ItemStacksResourceHandler {

    private final BlockEntity blockEntity;

    public FishTrapItemHandler(BlockEntity blockEntity) {
        super(46);
        this.blockEntity = blockEntity;
    }

    @Override
    protected void onContentsChanged(int index, ItemStack previousContents) {
        super.onContentsChanged(index, previousContents);
        if (blockEntity != null) {
            blockEntity.setChanged();
        }
    }

    @Override
    public int insert(int index, ItemResource resource, int amount, TransactionContext transaction) {
        if (index == 0) {
            if (!resource.isEmpty() && !(resource.value() instanceof FishingBait)) {
                return 0;
            }
            long currentAmount = getAmountAsLong(0);
            if (currentAmount > 0) return 0;
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

                for (int i = 1; i < size() && remaining > 0; i++) {
                    ItemStack existing = ItemUtil.getStack(this, i);
                    if (!existing.isEmpty()
                            && ItemStack.isSameItemSameComponents(existing, stackToAdd)
                            && existing.getCount() < existing.getMaxStackSize()) {
                        int inserted = insert(i, resource, remaining, tx);
                        remaining -= inserted;
                    }
                }

                if (remaining > 0) {
                    for (int i = 1; i < size() && remaining > 0; i++) {
                        if (getAmountAsLong(i) == 0) {
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
            return !resource.isEmpty() && resource.value() instanceof FishingBait;
        }
        return true;
    }

    public ItemStack getStackInSlot(int slot) {
        return ItemUtil.getStack(this, slot);
    }

    public void setStackInSlot(int slot, ItemStack stack) {
        if (stack.isEmpty()) {
            set(slot, ItemResource.EMPTY, 0);
        } else {
            set(slot, ItemResource.of(stack), stack.getCount());
        }
    }
}
