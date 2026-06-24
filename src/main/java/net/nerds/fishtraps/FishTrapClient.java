package net.nerds.fishtraps;

import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.nerds.fishtraps.blocks.DiamondTrap.DiamondFishTrapGui;
import net.nerds.fishtraps.blocks.DiamondTrap.DiamondFishTrapRenderer;
import net.nerds.fishtraps.blocks.IronTrap.IronFishTrapGui;
import net.nerds.fishtraps.blocks.IronTrap.IronFishTrapRenderer;
import net.nerds.fishtraps.blocks.WoodenTrap.WoodenFishTrapGui;
import net.nerds.fishtraps.blocks.WoodenTrap.WoodenFishTrapRenderer;

@EventBusSubscriber(modid = Fishtraps.MODID, value = Dist.CLIENT)
public class FishTrapClient {
    @SubscribeEvent
    public static void onRegisterMenuScreens(RegisterMenuScreensEvent event) {
        event.register(FishTrapInit.WOODEN_FISH_TRAP_MENU.get(), WoodenFishTrapGui::new);
        event.register(FishTrapInit.IRON_FISH_TRAP_MENU.get(), IronFishTrapGui::new);
        event.register(FishTrapInit.DIAMOND_FISH_TRAP_MENU.get(), DiamondFishTrapGui::new);
    }

    @SubscribeEvent
    public static void onClientSetup(net.neoforged.fml.event.lifecycle.FMLClientSetupEvent event) {
        BlockEntityRenderers.register(FishTrapInit.WOODEN_FISH_TRAP_ENTITY.get(), WoodenFishTrapRenderer::new);
        BlockEntityRenderers.register(FishTrapInit.IRON_FISH_TRAP_ENTITY.get(), IronFishTrapRenderer::new);
        BlockEntityRenderers.register(FishTrapInit.DIAMOND_FISH_TRAP_ENTITY.get(), DiamondFishTrapRenderer::new);
    }
}