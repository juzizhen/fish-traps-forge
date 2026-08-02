package net.nerds.fishtraps.blocks.ironFishTrap;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.nerds.fishtraps.blocks.baseTrap.BaseFishTrapRenderer;
import net.nerds.fishtraps.blocks.baseTrap.FishTrapRenderState;

public class IronFishTrapRenderer extends BaseFishTrapRenderer<IronFishTrapBlockEntity, FishTrapRenderState> {

    public IronFishTrapRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public FishTrapRenderState createRenderState() {
        return new FishTrapRenderState();
    }
}
