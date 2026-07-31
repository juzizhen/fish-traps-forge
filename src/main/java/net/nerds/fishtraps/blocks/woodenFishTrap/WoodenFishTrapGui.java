package net.nerds.fishtraps.blocks.woodenFishTrap;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.nerds.fishtraps.Fishtraps;

public class WoodenFishTrapGui extends AbstractContainerScreen<WoodenFishTrapContainer> {

    private static final Identifier TRAP_TEXTURE = Identifier.fromNamespaceAndPath(Fishtraps.MODID, "textures/gui/fish_trap_gui.png");

    public WoodenFishTrapGui(WoodenFishTrapContainer container, Inventory playerInventory, Component title) {
        super(container, playerInventory, title);
        this.imageHeight = 133 + 5 * 18;
        this.imageWidth = 176;
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.drawString(this.font, Component.translatable("block.fishtraps.wooden_fish_trap"), this.titleLabelX, this.titleLabelY, 4210752, false);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, TRAP_TEXTURE, x, y, 0f, 0f, this.imageWidth, this.imageHeight, 256, 256);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }
}
