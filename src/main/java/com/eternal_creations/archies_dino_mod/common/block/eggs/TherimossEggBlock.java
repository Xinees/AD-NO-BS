/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
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
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class TherimossEggBlock
extends DinoEggBlock {
    private static final VoxelShape SHAPE = Block.m_49796_((double)2.5, (double)0.0, (double)2.5, (double)13.5, (double)14.2, (double)13.5);

    public TherimossEggBlock() {
        super(() -> ADBlockEntityTypes.THERIMOSS_EGG.get(), ADEntityTypes.THERIMOSS);
    }

    @Override
    protected boolean canHatch(BlockState state, Level level, BlockPos pos) {
        return level.m_8055_(pos.m_7495_()).m_204336_(ADTags.Blocks.THERIMOSS_EGG_HATCHABLE_BLOCKS);
    }

    @Override
    public VoxelShape m_5940_(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
}

