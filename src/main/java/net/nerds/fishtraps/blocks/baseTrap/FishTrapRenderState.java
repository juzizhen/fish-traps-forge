package net.nerds.fishtraps.blocks.baseTrap;

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;

public class FishTrapRenderState extends BlockEntityRenderState {
    public final ItemStackRenderState baitItem = new ItemStackRenderState();
    public boolean hasBait = false;
}
