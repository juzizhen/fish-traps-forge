package net.nerds.fishtraps.blocks.IronTrap;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.nerds.fishtraps.Fishtraps;

public class IronFishTrapGui extends AbstractContainerScreen<IronFishTrapContainer> {

    private static final Identifier TRAP_TEXTURE = Identifier.fromNamespaceAndPath(Fishtraps.MODID, "textures/gui/fish_trap_gui.png");

    public IronFishTrapGui(IronFishTrapContainer container, Inventory playerInventory, Component title) {
        super(container, playerInventory, title, 176, 133 + 5 * 18);
    }

    @Override
    protected void extractLabels(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY) {
        guiGraphics.text(this.font, Component.translatable("block.fishtraps.iron_fish_trap"), this.titleLabelX, this.titleLabelY, -12566464, false);
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.extractBackground(guiGraphics, mouseX, mouseY, partialTick);
        int x = this.leftPos;
        int y = this.topPos;
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, TRAP_TEXTURE, x, y, 0f, 0f, this.imageWidth, this.imageHeight, 256, 256);
    }
}