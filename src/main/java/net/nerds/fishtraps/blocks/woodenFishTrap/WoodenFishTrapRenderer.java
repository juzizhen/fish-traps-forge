package net.nerds.fishtraps.blocks.woodenFishTrap;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.nerds.fishtraps.blocks.baseTrap.BaseFishTrapRenderer;
import net.nerds.fishtraps.blocks.baseTrap.FishTrapRenderState;

public class WoodenFishTrapRenderer extends BaseFishTrapRenderer<WoodenFishTrapBlockEntity, FishTrapRenderState> {

    public WoodenFishTrapRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public FishTrapRenderState createRenderState() {
        return new FishTrapRenderState();
    }
}
