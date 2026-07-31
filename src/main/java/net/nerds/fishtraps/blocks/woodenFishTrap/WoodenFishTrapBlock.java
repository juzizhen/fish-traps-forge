package net.nerds.fishtraps.blocks.woodenFishTrap;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.nerds.fishtraps.blocks.FishTrapEntityManager;
import net.nerds.fishtraps.blocks.baseTrap.BaseFishTrapBlock;

public class WoodenFishTrapBlock extends BaseFishTrapBlock {

    public WoodenFishTrapBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    public WoodenFishTrapBlock(Identifier id) {
        this(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)
                .noOcclusion()
                .setId(ResourceKey.create(Registries.BLOCK, id)));
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new WoodenFishTrapBlockEntity(pos, state);
    }

    @Override
    protected BlockEntityType<?> getBlockEntityType() {
        return FishTrapEntityManager.WOODEN_FISH_TRAP_ENTITY.get();
    }

    @Override
    protected MapCodec<? extends BaseFishTrapBlock> codec() {
        return simpleCodec(WoodenFishTrapBlock::new);
    }
}
