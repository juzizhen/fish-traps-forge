package net.nerds.fishtraps.blocks.diamondFishTrap;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.nerds.fishtraps.blocks.baseTrap.BaseFishTrapRenderer;
import net.nerds.fishtraps.blocks.baseTrap.FishTrapRenderState;

public class DiamondFishTrapRenderer extends BaseFishTrapRenderer<DiamondFishTrapBlockEntity, FishTrapRenderState> {

    public DiamondFishTrapRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public FishTrapRenderState createRenderState() {
        return new FishTrapRenderState();
    }
}
