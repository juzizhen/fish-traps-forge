package net.nerds.fishtraps;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.nerds.fishtraps.util.FishTrapsConfig;

@Mod(Fishtraps.MODID)
public class Fishtraps {

    public static final String MODID = "fishtraps";

    public Fishtraps(IEventBus modBus, ModContainer container) {
        container.registerConfig(ModConfig.Type.COMMON, FishTrapsConfig.FORGE_CONFIG_SPEC);

        FishTrapInit.BLOCKS.register(modBus);
        FishTrapInit.ITEMS.register(modBus);
        FishTrapInit.BLOCK_ENTITIES.register(modBus);
        FishTrapInit.MENUS.register(modBus);
        FishTrapInit.CREATIVE_MODE_TABS.register(modBus);
    }
}