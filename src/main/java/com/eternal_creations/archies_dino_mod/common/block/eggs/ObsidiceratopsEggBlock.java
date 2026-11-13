/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.BlockPos$MutableBlockPos
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraft.world.level.material.FluidState
 *  net.minecraft.world.level.material.Fluids
 *  net.minecraft.world.phys.shapes.CollisionContext
 *  net.minecraft.world.phys.shapes.VoxelShape
 */
package com.eternal_creations.archies_dino_mod.common.block.eggs;

import com.eternal_creations.archies_dino_mod.common.ADTags;
import com.eternal_creations.archies_dino_mod.common.block.eggs.DinoEggBlock;
import com.eternal_creations.archies_dino_mod.common.block.entity.ADBlockEntityTypes;
import com.eternal_creations.archies_dino_mod.common.entity.ADEntityTypes;
import com.eternal_creations.archies_dino_mod.common.sound.ADSoundEvents;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ObsidiceratopsEggBlock
extends DinoEggBlock {
    private static final VoxelShape SHAPE = Block.m_49796_((double)2.5, (double)0.0, (double)2.5, (double)13.5, (double)14.2, (double)13.5);

    public ObsidiceratopsEggBlock() {
        super(() -> ADBlockEntityTypes.OBSIDICERATOPS_EGG.get(), ADEntityTypes.OBSIDICERATOPS);
    }

    @Override
    protected boolean canHatch(BlockState state, Level level, BlockPos pos) {
        return level.m_8055_(pos.m_7495_()).m_204336_(ADTags.Blocks.OBSIDICERATOPS_EGG_HATCHABLE_BLOCKS);
    }

    public void m_141947_(Level level, BlockPos pos, BlockState state, Entity entity) {
        if (!entity.m_5825_() && entity instanceof LivingEntity) {
            entity.m_6469_(level.m_269111_().m_269047_(), 1.0f);
        }
        super.m_141947_(level, pos, state, entity);
    }

    @Override
    public void m_213898_(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!ObsidiceratopsEggBlock.isNearLava((LevelReader)level, pos, 5)) {
            this.breakEggWithoutHatching(level, pos, state);
            return;
        }
        super.m_213898_(state, level, pos, random);
    }

    @Override
    public VoxelShape m_5940_(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    private void breakEggWithoutHatching(ServerLevel level, BlockPos pos, BlockState state) {
        level.m_5594_(null, pos, ADSoundEvents.DINO_EGG_BREAK.get(), SoundSource.BLOCKS, 0.8f, 0.9f + level.f_46441_.m_188501_() * 0.2f);
        level.m_46796_(2001, pos, Block.m_49956_((BlockState)state));
        level.m_7471_(pos, false);
    }

    public static boolean isNearLava(LevelReader level, BlockPos centerPos, int radius) {
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
        for (int x = -radius; x <= radius; ++x) {
            for (int y = -radius; y <= radius; ++y) {
                for (int z = -radius; z <= radius; ++z) {
                    mutablePos.m_122178_(centerPos.m_123341_() + x, centerPos.m_123342_() + y, centerPos.m_123343_() + z);
                    FluidState fluidState = level.m_6425_((BlockPos)mutablePos);
                    if (!fluidState.m_192917_((Fluid)Fluids.f_76195_) && !fluidState.m_192917_((Fluid)Fluids.f_76194_) && !level.m_8055_((BlockPos)mutablePos).m_60713_(Blocks.f_50450_)) continue;
                    return true;
                }
            }
        }
        return false;
    }
}

