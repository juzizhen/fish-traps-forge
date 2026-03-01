package net.nerds.fishtraps.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.nerds.fishtraps.util.FishTrapsConfig;

public class FishBait extends Item {

    public FishBait() {
        super(new Item.Properties());
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return FishTrapsConfig.fishBaitDurability.get();
    }

    @Override
    public boolean isDamageable(ItemStack stack) {
        return true;
    }
}