package net.nerds.fishtraps;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.nerds.fishtraps.blocks.DiamondTrap.DiamondFishTrapGui;
import net.nerds.fishtraps.blocks.DiamondTrap.DiamondFishTrapRenderer;
import net.nerds.fishtraps.blocks.IronTrap.IronFishTrapGui;
import net.nerds.fishtraps.blocks.IronTrap.IronFishTrapRenderer;
import net.nerds.fishtraps.blocks.WoodenTrap.WoodenFishTrapGui;
import net.nerds.fishtraps.blocks.WoodenTrap.WoodenFishTrapRenderer;

@Mod.EventBusSubscriber(modid = Fishtraps.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class FishTrapClient {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        MenuScreens.register(FishTrapInit.WOODEN_FISH_TRAP_MENU.get(), WoodenFishTrapGui::new);
        MenuScreens.register(FishTrapInit.IRON_FISH_TRAP_MENU.get(), IronFishTrapGui::new);
        MenuScreens.register(FishTrapInit.DIAMOND_FISH_TRAP_MENU.get(), DiamondFishTrapGui::new);

        ItemBlockRenderTypes.setRenderLayer(FishTrapInit.WOODEN_FISH_TRAP.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(FishTrapInit.IRON_FISH_TRAP.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(FishTrapInit.DIAMOND_FISH_TRAP.get(), RenderType.cutout());

        BlockEntityRenderers.register(FishTrapInit.WOODEN_FISH_TRAP_ENTITY.get(), WoodenFishTrapRenderer::new);
        BlockEntityRenderers.register(FishTrapInit.IRON_FISH_TRAP_ENTITY.get(), IronFishTrapRenderer::new);
        BlockEntityRenderers.register(FishTrapInit.DIAMOND_FISH_TRAP_ENTITY.get(), DiamondFishTrapRenderer::new);
    }
}
