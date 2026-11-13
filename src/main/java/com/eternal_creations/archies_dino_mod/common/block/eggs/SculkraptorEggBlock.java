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
public class SculkraptorEggBlock
extends DinoEggBlock {
    private static final VoxelShape SHAPE = Block.m_49796_((double)4.5, (double)0.0, (double)4.5, (double)11.5, (double)11.2, (double)11.5);

    public SculkraptorEggBlock() {
        super(() -> ADBlockEntityTypes.SCULKRAPTOR_EGG.get(), ADEntityTypes.SCULKRAPTOR);
    }

    @Override
    protected boolean shouldTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        return random.m_188503_(2) == 0;
    }

    @Override
    protected boolean canHatch(BlockState state, Level level, BlockPos pos) {
        return level.m_45517_(LightLayer.BLOCK, pos) <= 5 && level.m_45517_(LightLayer.SKY, pos) <= 5;
    }

    @Override
    public VoxelShape m_5940_(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
}

