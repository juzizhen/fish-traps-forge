package net.nerds.fishtraps;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.nerds.fishtraps.util.FishTrapsConfig;

@Mod(Fishtraps.MODID)
public class Fishtraps {

    public static final String MODID = "fishtraps";

    public Fishtraps() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, FishTrapsConfig.FORGE_CONFIG_SPEC);

        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();

        MinecraftForge.EVENT_BUS.register(this);
        FishTrapInit.BLOCKS.register(modBus);
        FishTrapInit.ITEMS.register(modBus);
        FishTrapInit.BLOCK_ENTITIES.register(modBus);
        FishTrapInit.MENUS.register(modBus);
        FishTrapInit.CREATIVE_MODE_TABS.register(modBus);
    }
}
