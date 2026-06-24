package net.nerds.fishtraps.blocks.IronTrap;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

public class IronFishTrapRenderer implements BlockEntityRenderer<IronFishTrapTileEntity, IronFishTrapRenderer.RenderState> {

    private final ItemModelResolver itemModelResolver;

    public IronFishTrapRenderer(BlockEntityRendererProvider.Context context) {
        this.itemModelResolver = context.itemModelResolver();
    }

    @Override
    public RenderState createRenderState() {
        return new RenderState();
    }

    @Override
    public void extractRenderState(IronFishTrapTileEntity tile, RenderState renderState, float partialTick,
                                   Vec3 cameraPos,
                                   ModelFeatureRenderer.@Nullable CrumblingOverlay crumblingOverlay) {
        BlockEntityRenderState.extractBase(tile, renderState, crumblingOverlay);
        renderState.tile = tile;
        ItemStack bait = tile.getInventory().getStackInSlot(0);
        this.itemModelResolver.updateForTopItem(renderState.baitItem, bait, ItemDisplayContext.FIXED, tile.getLevel(), null, 0);
        renderState.hasBait = !bait.isEmpty();
    }

    @Override
    public void submit(RenderState renderState, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        if (renderState.tile == null) return;
        if (!renderState.hasBait) return;

        poseStack.pushPose();
        poseStack.translate(0.5, 0.5, 0.5);
        poseStack.scale(0.6f, 0.6f, 0.6f);

        float time = ((float) System.currentTimeMillis() / 20) % 360;
        poseStack.mulPose(Axis.YP.rotationDegrees(time));

        renderState.baitItem.submit(poseStack, submitNodeCollector, renderState.lightCoords, OverlayTexture.NO_OVERLAY, 0);

        poseStack.popPose();
    }

    public static class RenderState extends BlockEntityRenderState {
        public final ItemStackRenderState baitItem = new ItemStackRenderState();
        public boolean hasBait = false;
        public IronFishTrapTileEntity tile;
    }
}