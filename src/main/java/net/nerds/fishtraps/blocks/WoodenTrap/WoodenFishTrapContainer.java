package net.nerds.fishtraps.blocks.WoodenTrap;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.transfer.item.ResourceHandlerSlot;
import net.nerds.fishtraps.items.FishBait;
import net.nerds.fishtraps.util.FishTrapItemHandler;
import org.jetbrains.annotations.Nullable;

public class WoodenFishTrapContainer extends AbstractContainerMenu {

    private final BlockPos trapPos;

    public WoodenFishTrapContainer(int containerId, Inventory playerInv, FishTrapItemHandler handler,
                                    @Nullable MenuType<?> type, BlockPos trapPos) {
        super(type, containerId);
        this.trapPos = trapPos;
        int slotIndex = 0;

        this.addSlot(new ResourceHandlerSlot(handler, handler::set, slotIndex++, 8, 118) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.getItem() instanceof FishBait;
            }

            @Override
            public int getMaxStackSize() {
                return 1;
            }

            @Override
            public int getMaxStackSize(ItemStack stack) {
                return 1;
            }
        });

        for (int row = 0; row < 5; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlot(new ResourceHandlerSlot(handler, handler::set, slotIndex++, 8 + col * 18, 17 + row * 18) {
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
        return trapPos == null || player.distanceToSqr(trapPos.getX() + 0.5, trapPos.getY() + 0.5, trapPos.getZ() + 0.5) <= 64.0;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot.hasItem()) {
            ItemStack stackInSlot = slot.getItem();
            itemstack = stackInSlot.copy();
            // Pass a copy to moveItemStackTo to avoid corrupting the handler's cached stack reference
            ItemStack toMove = stackInSlot.copy();

            int fishTrapSlots = 46;
            boolean moved = false;

            if (index < fishTrapSlots) {
                moved = this.moveItemStackTo(toMove, fishTrapSlots, this.slots.size(), true);
            } else {
                if (isFishBait(stackInSlot)) {
                    moved = this.moveItemStackTo(toMove, 0, 1, false);
                } else {
                    moved = this.moveItemStackTo(toMove, 1, fishTrapSlots, false);
                }
            }

            if (!moved) {
                return ItemStack.EMPTY;
            }

            // Sync source slot: write back remaining count to handler
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

    private boolean isFishBait(ItemStack stack) {
        return stack.getItem() instanceof FishBait;
    }
}