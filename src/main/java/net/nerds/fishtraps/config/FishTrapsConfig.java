package net.nerds.fishtraps.config;

import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.BooleanValue;
import net.neoforged.neoforge.common.ModConfigSpec.IntValue;
import org.apache.commons.lang3.tuple.Pair;

public class FishTrapsConfig {

    public static final ModConfigSpec FORGE_CONFIG_SPEC;
    public static final FishTrapsConfig FISH_TRAPS_CONFIG;

    public static IntValue woodenTrapLureLevel;
    public static IntValue woodenTrapLuckLevel;
    public static IntValue woodenTrapBaseTime;
    public static IntValue ironTrapLureLevel;
    public static IntValue ironTrapLuckLevel;
    public static IntValue ironTrapBaseTime;
    public static IntValue diamondTrapLureLevel;
    public static IntValue diamondTrapLuckLevel;
    public static IntValue diamondTrapBaseTime;
    public static IntValue trapPenaltyMultiplier;
    public static IntValue fishBaitDurability;
    public static BooleanValue shouldTrapHavePenalty;
    public static BooleanValue useDefaultFishingLoottable;
    public static BooleanValue workingInLava;

    static {
        Pair<FishTrapsConfig, ModConfigSpec> specPair = new ModConfigSpec.Builder().configure(FishTrapsConfig::new);
        FORGE_CONFIG_SPEC = specPair.getRight();
        FISH_TRAPS_CONFIG = specPair.getLeft();
    }

    FishTrapsConfig(ModConfigSpec.Builder builder) {
        woodenTrapLureLevel = builder.comment("The Lure Level of the wooden trap").defineInRange(FishTrapValues.WOODEN_LURE, 1, 0, 10);
        woodenTrapLuckLevel = builder.comment("The Luck of the Sea Level of the wooden trap").defineInRange(FishTrapValues.WOODEN_LUCK, 1, 0, 10);
        woodenTrapBaseTime = builder.comment("The base fishing time in ticks of the wooden trap").defineInRange(FishTrapValues.WOODEN_TIME, 900, 20, 2000000000);

        ironTrapLureLevel = builder.comment("The Lure level of the Iron trap").defineInRange(FishTrapValues.IRON_LURE, 2, 0, 10);
        ironTrapLuckLevel = builder.comment("The Luck of the Sea level of the Iron trap").defineInRange(FishTrapValues.IRON_LUCK, 2, 0, 10);
        ironTrapBaseTime = builder.comment("The base fishing time in ticks of the Iron trap").defineInRange(FishTrapValues.IRON_TIME, 600, 20, 2000000000);

        diamondTrapLureLevel = builder.comment("The Lure level of the diamond trap").defineInRange(FishTrapValues.DIAMOND_LURE, 3, 0, 10);
        diamondTrapLuckLevel = builder.comment("The Luck of the Sea level of the diamond trap").defineInRange(FishTrapValues.DIAMOND_LUCK, 3, 0, 10);
        diamondTrapBaseTime = builder.comment("The base fishing time in ticks of the diamond trap").defineInRange(FishTrapValues.DIAMOND_TIME, 400, 20, 2000000000);

        trapPenaltyMultiplier = builder.comment("The Multiplier penalty of traps without fishbait").defineInRange(FishTrapValues.PENALTY_MULTIPLIER_AMOUNT, 40, 1, 1000);
        fishBaitDurability = builder.comment("The durability of fish bait").defineInRange(FishTrapValues.FISH_BAIT_DURABILITY, 400, 1, 200000);
        shouldTrapHavePenalty = builder.comment("Should fish traps be penalized if they dont have bait in them").define(FishTrapValues.SHOULD_PENALTY_MULTIPLIER, true);
        useDefaultFishingLoottable = builder.comment("Use the vanilla fishing loot table. If false - add custom datapack loottable in /fishtraps/loot_table/traps/{material}_fish_trap.json").define(FishTrapValues.USE_DEFAULT_FISHING_LOOTTABLE, true);
        workingInLava = builder.comment("Allow fish traps to function even in lava.").define(FishTrapValues.WORKING_IN_LAVA, false);
    }
}
