package net.nerds.fishtraps;

import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.nerds.fishtraps.blocks.FishTrapEntityManager;
import net.nerds.fishtraps.blocks.diamondFishTrap.DiamondFishTrapGui;
import net.nerds.fishtraps.blocks.diamondFishTrap.DiamondFishTrapRenderer;
import net.nerds.fishtraps.blocks.ironFishTrap.IronFishTrapGui;
import net.nerds.fishtraps.blocks.ironFishTrap.IronFishTrapRenderer;
import net.nerds.fishtraps.blocks.woodenFishTrap.WoodenFishTrapGui;
import net.nerds.fishtraps.blocks.woodenFishTrap.WoodenFishTrapRenderer;

@EventBusSubscriber(modid = Fishtraps.MODID, value = Dist.CLIENT)
public class FishtrapsClient {

    @SubscribeEvent
    public static void onRegisterMenuScreens(RegisterMenuScreensEvent event) {
        event.register(FishTrapEntityManager.WOODEN_FISH_TRAP_MENU.get(), WoodenFishTrapGui::new);
        event.register(FishTrapEntityManager.IRON_FISH_TRAP_MENU.get(), IronFishTrapGui::new);
        event.register(FishTrapEntityManager.DIAMOND_FISH_TRAP_MENU.get(), DiamondFishTrapGui::new);
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        BlockEntityRenderers.register(FishTrapEntityManager.WOODEN_FISH_TRAP_ENTITY.get(), WoodenFishTrapRenderer::new);
        BlockEntityRenderers.register(FishTrapEntityManager.IRON_FISH_TRAP_ENTITY.get(), IronFishTrapRenderer::new);
        BlockEntityRenderers.register(FishTrapEntityManager.DIAMOND_FISH_TRAP_ENTITY.get(), DiamondFishTrapRenderer::new);
    }
}
