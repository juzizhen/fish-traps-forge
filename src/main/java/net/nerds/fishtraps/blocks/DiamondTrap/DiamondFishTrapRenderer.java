package net.nerds.fishtraps.blocks.DiamondTrap;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DiamondFishTrapRenderer implements BlockEntityRenderer<DiamondFishTrapTileEntity> {

    private final ItemRenderer itemRenderer;

    public DiamondFishTrapRenderer(BlockEntityRendererProvider.Context context) {
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(DiamondFishTrapTileEntity tile, float partialTicks, PoseStack poseStack,
                       MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
        ItemStack bait = tile.getInventory().getStackInSlot(0);
        if (!bait.isEmpty()) {
            poseStack.pushPose();

            poseStack.translate(0.5, 0.5, 0.5);

            poseStack.scale(0.6f, 0.6f, 0.6f);

            float time = ((float) System.currentTimeMillis() / 20) % 360;
            poseStack.mulPose(Axis.YP.rotationDegrees(time));

            itemRenderer.renderStatic(bait, ItemDisplayContext.FIXED,
                    combinedLight, combinedOverlay, poseStack, buffer, tile.getLevel(), 0);

            poseStack.popPose();
        }
    }
}