/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.util.Mth
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockState
 */
package com.eternal_creations.archies_dino_mod.common.block.entity.egg;

import com.eternal_creations.archies_dino_mod.common.block.entity.ADBlockEntityTypes;
import com.eternal_creations.archies_dino_mod.common.block.entity.TickingBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class BlockEntityObsidiceratopsEgg
extends BlockEntity
implements TickingBlockEntity {
    private int tickCount;

    public BlockEntityObsidiceratopsEgg(BlockPos pos, BlockState blockState) {
        super(ADBlockEntityTypes.OBSIDICERATOPS_EGG.get(), pos, blockState);
    }

    @Override
    public void tick(Level level, BlockPos blockPos, BlockState blockState) {
        ++this.tickCount;
        if (this.tickCount % 20 == 0 && level.f_46441_.m_188503_(4) == 0) {
            RandomSource random = level.f_46441_;
            level.m_7106_((ParticleOptions)ParticleTypes.f_123756_, (double)blockPos.m_123341_() + 0.5 + (double)Mth.m_216283_((RandomSource)random, (float)-0.25f, (float)0.25f), (double)blockPos.m_123342_(), (double)blockPos.m_123343_() + 0.5 + (double)Mth.m_216283_((RandomSource)random, (float)-0.25f, (float)0.25f), 0.0, 0.0, 0.0);
        }
    }
}

