package net.nerds.fishtraps.items;

import net.minecraft.core.component.DataComponents;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.nerds.fishtraps.config.FishTrapsConfig;

public class FishingBait extends Item {

    public FishingBait(Properties properties) {
        super(properties);
    }

    public static int getMaxBaitDamage() {
        return FishTrapsConfig.fishBaitDurability.get();
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return stack.getOrDefault(DataComponents.DAMAGE, 0) > 0;
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        int damage = stack.getOrDefault(DataComponents.DAMAGE, 0);
        int maxDamage = getMaxBaitDamage();
        return Math.round(13.0f - (float) damage * 13.0f / (float) maxDamage);
    }

    @Override
    public int getBarColor(ItemStack stack) {
        int damage = stack.getOrDefault(DataComponents.DAMAGE, 0);
        int maxDamage = getMaxBaitDamage();
        float f = Math.max(0.0f, (float) (maxDamage - damage) / (float) maxDamage);
        return Mth.hsvToRgb(f / 3.0f, 1.0f, 1.0f);
    }
}
