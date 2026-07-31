package net.nerds.fishtraps.blocks;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.nerds.fishtraps.Fishtraps;
import net.nerds.fishtraps.blocks.diamondFishTrap.DiamondFishTrapBlock;
import net.nerds.fishtraps.blocks.ironFishTrap.IronFishTrapBlock;
import net.nerds.fishtraps.blocks.woodenFishTrap.WoodenFishTrapBlock;
import net.nerds.fishtraps.items.FishingBait;

public class FishTrapsManager {

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Fishtraps.MODID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Fishtraps.MODID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Fishtraps.MODID);

    public static final DeferredBlock<WoodenFishTrapBlock> WOODEN_FISH_TRAP = BLOCKS.register("wooden_fish_trap", WoodenFishTrapBlock::new);
    public static final DeferredBlock<IronFishTrapBlock> IRON_FISH_TRAP = BLOCKS.register("iron_fish_trap", IronFishTrapBlock::new);
    public static final DeferredBlock<DiamondFishTrapBlock> DIAMOND_FISH_TRAP = BLOCKS.register("diamond_fish_trap", DiamondFishTrapBlock::new);

    public static final DeferredItem<BlockItem> WOODEN_FISH_TRAP_ITEM = ITEMS.registerSimpleBlockItem("wooden_fish_trap", WOODEN_FISH_TRAP);
    public static final DeferredItem<BlockItem> IRON_FISH_TRAP_ITEM = ITEMS.registerSimpleBlockItem("iron_fish_trap", IRON_FISH_TRAP);
    public static final DeferredItem<BlockItem> DIAMOND_FISH_TRAP_ITEM = ITEMS.registerSimpleBlockItem("diamond_fish_trap", DIAMOND_FISH_TRAP);

    public static final DeferredItem<FishingBait> FISH_BAIT = ITEMS.registerItem("fish_trap_bait",
            FishingBait::new, () -> new Item.Properties().stacksTo(64));

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> FISH_TRAP_TAB = CREATIVE_MODE_TABS.register("fishtraps_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.fishtraps.fishtraps"))
                    .icon(() -> new ItemStack(WOODEN_FISH_TRAP_ITEM.get()))
                    .displayItems((parameters, output) -> {
                        output.accept(WOODEN_FISH_TRAP_ITEM.get());
                        output.accept(IRON_FISH_TRAP_ITEM.get());
                        output.accept(DIAMOND_FISH_TRAP_ITEM.get());
                        output.accept(FISH_BAIT.get());
                    })
                    .build());
}
