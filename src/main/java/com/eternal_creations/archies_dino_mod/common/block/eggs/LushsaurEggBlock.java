/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.core.BlockPos
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.shapes.CollisionContext
 *  net.minecraft.world.phys.shapes.VoxelShape
 */
package com.eternal_creations.archies_dino_mod.common.block.eggs;

import com.eternal_creations.archies_dino_mod.common.ADTags;
import com.eternal_creations.archies_dino_mod.common.block.eggs.DinoEggBlock;
import com.eternal_creations.archies_dino_mod.common.block.entity.ADBlockEntityTypes;
import com.eternal_creations.archies_dino_mod.common.entity.ADEntityTypes;
import com.eternal_creations.archies_dino_mod.common.entity.mob.lushsaur.LushsaurEntity;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class LushsaurEggBlock
extends DinoEggBlock {
    private static final VoxelShape SHAPE = Block.m_49796_((double)1.0, (double)0.0, (double)1.0, (double)15.0, (double)16.2, (double)15.0);

    public LushsaurEggBlock() {
        super(() -> ADBlockEntityTypes.LUSHSAUR_EGG.get(), ADEntityTypes.LUSHSAUR);
    }

    @Override
    protected boolean shouldTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (this.isDay(level)) {
            return true;
        }
        if (level.m_6249_((Entity)null, new AABB(pos).m_82400_(16.0), entity -> entity instanceof LushsaurEntity).size() >= 2) {
            return level.f_46441_.m_188503_(11) == 0;
        }
        return level.f_46441_.m_188503_(36) == 0;
    }

    @Override
    protected boolean canHatch(BlockState state, Level level, BlockPos pos) {
        return level.m_8055_(pos.m_7495_()).m_204336_(ADTags.Blocks.LUSHSAUR_EGG_HATCHABLE_BLOCKS);
    }

    @Override
    public VoxelShape m_5940_(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
}

