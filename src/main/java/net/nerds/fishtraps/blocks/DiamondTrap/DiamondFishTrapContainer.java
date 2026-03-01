package net.nerds.fishtraps.blocks.DiamondTrap;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;
import net.nerds.fishtraps.items.FishBait;
import net.nerds.fishtraps.util.FishTrapItemHandler;
import org.jetbrains.annotations.Nullable;

public class DiamondFishTrapContainer extends AbstractContainerMenu {

    public DiamondFishTrapContainer(int containerId, Inventory playerInv, FishTrapItemHandler handler,
                                    @Nullable MenuType<?> type) {
        super(type, containerId);
        int slotIndex = 0;

        this.addSlot(new SlotItemHandler(handler, slotIndex++, 8, 118) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.getItem() instanceof FishBait;
            }

            @Override
            public int getMaxStackSize() {
                return 1;
            }
        });

        for (int row = 0; row < 5; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlot(new SlotItemHandler(handler, slotIndex++, 8 + col * 18, 17 + row * 18) {
                    @Override
                    public boolean mayPlace(ItemStack stack) {
                        return false;
                    }
                });
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
        return true;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot.hasItem()) {
            ItemStack stackInSlot = slot.getItem();
            itemstack = stackInSlot.copy();

            int fishTrapSlots = 46;

            if (index < fishTrapSlots) {
                if (!this.moveItemStackTo(stackInSlot, fishTrapSlots, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if (isFishBait(stackInSlot)) {
                    if (!this.moveItemStackTo(stackInSlot, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else {
                    if (!this.moveItemStackTo(stackInSlot, 1, fishTrapSlots, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            }

            if (stackInSlot.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return itemstack;
    }

    private boolean isFishBait(ItemStack stack) {
        return stack.getItem() instanceof FishBait;
    }
}