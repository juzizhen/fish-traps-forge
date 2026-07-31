package net.nerds.fishtraps.blocks.baseTrap;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.nerds.fishtraps.inventory.BaitSlot;
import net.nerds.fishtraps.inventory.FishTrapItemHandler;
import net.nerds.fishtraps.inventory.OutputSlot;
import net.nerds.fishtraps.items.FishingBait;
import org.jetbrains.annotations.Nullable;

public abstract class BaseFishTrapContainer extends AbstractContainerMenu {

    protected final BlockPos trapPos;

    protected BaseFishTrapContainer(@Nullable MenuType<?> type, int containerId, Inventory playerInv,
                                    FishTrapItemHandler handler, BlockPos trapPos) {
        super(type, containerId);
        this.trapPos = trapPos;

        int slotIndex = 0;
        this.addSlot(new BaitSlot(handler, handler::set, slotIndex++, 8, 118));

        for (int row = 0; row < 5; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlot(new OutputSlot(handler, handler::set, slotIndex++, 8 + col * 18, 18 + row * 18));
            }
        }

        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlot(new Slot(playerInv, col + row * 9 + 9, 8 + col * 18, 142 + row * 18));
            }
        }
        for (int col = 0; col < 9; ++col) {
            this.addSlot(new Slot(playerInv, col, 8 + col * 18, 200));
        }
    }

    @Override
    public boolean stillValid(Player player) {
        return trapPos == null || player.distanceToSqr(trapPos.getX() + 0.5, trapPos.getY() + 0.5, trapPos.getZ() + 0.5) <= 64.0;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot.hasItem()) {
            ItemStack stackInSlot = slot.getItem();
            itemstack = stackInSlot.copy();
            ItemStack toMove = stackInSlot.copy();

            int fishTrapSlots = 46;
            boolean moved;

            if (index < fishTrapSlots) {
                moved = this.moveItemStackTo(toMove, fishTrapSlots, this.slots.size(), true);
            } else {
                if (stackInSlot.getItem() instanceof FishingBait) {
                    moved = this.moveItemStackTo(toMove, 0, 1, false);
                } else {
                    moved = this.moveItemStackTo(toMove, 1, fishTrapSlots, false);
                }
            }

            if (!moved) {
                return ItemStack.EMPTY;
            }

            int remaining = toMove.getCount();
            if (remaining <= 0) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.set(stackInSlot.copyWithCount(remaining));
            }
            slot.setChanged();
        }

        return itemstack;
    }
}
