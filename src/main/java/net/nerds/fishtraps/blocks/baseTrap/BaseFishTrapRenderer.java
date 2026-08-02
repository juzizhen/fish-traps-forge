package net.nerds.fishtraps.blocks.baseTrap;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

public abstract class BaseFishTrapRenderer<T extends BaseFishTrapBlockEntity, S extends FishTrapRenderState>
        implements BlockEntityRenderer<T, S> {

    private final ItemModelResolver itemModelResolver;

    protected BaseFishTrapRenderer(BlockEntityRendererProvider.Context context) {
        this.itemModelResolver = context.itemModelResolver();
    }

    @Override
    public void extractRenderState(T blockEntity, S renderState, float partialTick,
                                   Vec3 cameraPos,
                                   ModelFeatureRenderer.@Nullable CrumblingOverlay crumblingOverlay) {
        BlockEntityRenderState.extractBase(blockEntity, renderState, crumblingOverlay);
        ItemStack bait = blockEntity.getInventory().getStackInSlot(0);
        this.itemModelResolver.updateForTopItem(renderState.baitItem, bait, ItemDisplayContext.FIXED, blockEntity.getLevel(), null, 0);
        renderState.hasBait = !bait.isEmpty();
    }

    @Override
    public void submit(S renderState, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        if (!renderState.hasBait) return;

        poseStack.pushPose();
        poseStack.translate(0.5, 0.5, 0.5);
        poseStack.scale(0.6f, 0.6f, 0.6f);

        float time = ((float) System.currentTimeMillis() / 20) % 360;
        poseStack.mulPose(Axis.YP.rotationDegrees(time));

        renderState.baitItem.submit(poseStack, submitNodeCollector, renderState.lightCoords, OverlayTexture.NO_OVERLAY, 0);

        poseStack.popPose();
    }
}
