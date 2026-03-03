package net.nerds.fishtraps.util;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.ItemStackHandler;
import net.nerds.fishtraps.items.FishBait;

import javax.annotation.Nonnull;
import java.util.List;

public class FishTrapItemHandler extends ItemStackHandler {
    private BlockEntity tile;

    public FishTrapItemHandler() {
        super(46);
    }

    // 关联任意鱼笼 TileEntity
    public void setTile(BlockEntity tile) {
        this.tile = tile;
    }

    @Override
    protected void onContentsChanged(int slot) {
        if (tile != null) {
            tile.setChanged();
        }
    }

    public void addListToInventory(List<ItemStack> list) {
        for (ItemStack stack : list) {
            ItemStack remaining = stack;
            for (int i = 1; i < getSlots(); i++) { // 从索引 1 开始，跳过诱饵槽
                remaining = insertItem(i, remaining, false);
                if (remaining.isEmpty()) break;
            }
        }
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        if (slot == 0) {
            return stack.getItem() instanceof FishBait;
        }
        return true;
    }
}