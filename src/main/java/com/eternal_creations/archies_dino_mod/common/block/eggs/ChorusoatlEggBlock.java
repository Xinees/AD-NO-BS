/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.core.BlockPos
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LightLayer
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.shapes.CollisionContext
 *  net.minecraft.world.phys.shapes.VoxelShape
 */
package com.eternal_creations.archies_dino_mod.common.block.eggs;

import com.eternal_creations.archies_dino_mod.common.ADTags;
import com.eternal_creations.archies_dino_mod.common.block.eggs.DinoEggBlock;
import com.eternal_creations.archies_dino_mod.common.block.entity.ADBlockEntityTypes;
import com.eternal_creations.archies_dino_mod.common.entity.ADEntityTypes;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ChorusoatlEggBlock
extends DinoEggBlock {
    private static final VoxelShape SHAPE = Block.m_49796_((double)2.5, (double)0.0, (double)2.5, (double)13.5, (double)14.2, (double)13.5);

    public ChorusoatlEggBlock() {
        super(() -> ADBlockEntityTypes.CHORUSOATL_EGG.get(), ADEntityTypes.CHORUSOATL);
    }

    @Override
    protected boolean shouldTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (level.m_204156_().m_203656_(ADTags.DimensionTypes.CHORUSOATL_EGG_PREFERRED)) {
            return random.m_188503_(4) != 0;
        }
        return random.m_188499_();
    }

    @Override
    protected boolean canHatch(BlockState state, Level level, BlockPos pos) {
        if (level.m_204156_().m_203656_(ADTags.DimensionTypes.CHORUSOATL_EGG_PREFERRED)) {
            return true;
        }
        if (level.m_45517_(LightLayer.BLOCK, pos) > 0 || level.m_45517_(LightLayer.SKY, pos) > 0) {
            return false;
        }
        if (!level.m_8055_(pos.m_7495_()).m_204336_(ADTags.Blocks.CHORUSOATL_EGG_HATCHABLE_BLOCKS)) {
            return false;
        }
        int count = 0;
        for (BlockPos relative : BlockPos.m_121940_((BlockPos)pos.m_7918_(-3, -3, -3), (BlockPos)pos.m_7918_(3, 3, 3))) {
            if (!level.m_8055_(relative).m_204336_(ADTags.Blocks.CHORUSOATL_EGG_CHORUS) || ++count < 5) continue;
            return true;
        }
        return false;
    }

    @Override
    public VoxelShape m_5940_(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
}

