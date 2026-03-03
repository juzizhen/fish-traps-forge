package net.nerds.fishtraps.blocks.DiamondTrap;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.nerds.fishtraps.Fishtraps;

@OnlyIn(Dist.CLIENT)
public class DiamondFishTrapGui extends AbstractContainerScreen<DiamondFishTrapContainer> {

    private static final ResourceLocation TRAP_TEXTURE = ResourceLocation.fromNamespaceAndPath(Fishtraps.MODID, "textures/gui/fish_trap_gui.png");

    public DiamondFishTrapGui(DiamondFishTrapContainer container, Inventory playerInventory, Component title) {
        super(container, playerInventory, title);
        int rows = 5;
        this.imageHeight = 133 + rows * 18;
        this.imageWidth = 176;
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, 4210752, false);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(TRAP_TEXTURE, x, y, 0, 0, this.imageWidth, this.imageHeight);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }
}