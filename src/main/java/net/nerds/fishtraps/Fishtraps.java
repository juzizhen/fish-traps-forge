package net.nerds.fishtraps;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.nerds.fishtraps.blocks.FishTrapEntityManager;
import net.nerds.fishtraps.blocks.FishTrapsManager;
import net.nerds.fishtraps.config.FishTrapsConfig;

@Mod(Fishtraps.MODID)
public class Fishtraps {
    public static final String MODID = "fishtraps";

    public Fishtraps(IEventBus modBus, ModContainer container) {
        container.registerConfig(ModConfig.Type.COMMON, FishTrapsConfig.FORGE_CONFIG_SPEC);

        FishTrapsManager.BLOCKS.register(modBus);
        FishTrapsManager.ITEMS.register(modBus);
        FishTrapsManager.CREATIVE_MODE_TABS.register(modBus);
        FishTrapEntityManager.BLOCK_ENTITIES.register(modBus);
        FishTrapEntityManager.MENUS.register(modBus);
    }
}
