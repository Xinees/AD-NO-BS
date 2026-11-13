/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.astemir.astemirlib.re.common.function.PositionSupplier
 *  com.astemir.astemirlib.re.common.world.particle.ParticleEmitter
 *  net.minecraft.core.BlockPos
 *  net.minecraft.util.RandomSource
 *  net.minecraft.util.valueproviders.IntProvider
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.DropExperienceBlock
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  org.jetbrains.annotations.NotNull
 */
package com.eternal_creations.archies_dino_mod.common.block;

import com.astemir.astemirlib.re.common.function.PositionSupplier;
import com.astemir.astemirlib.re.common.world.particle.ParticleEmitter;
import com.eternal_creations.archies_dino_mod.common.particle.ADParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class AmberOre
extends DropExperienceBlock {
    public AmberOre(BlockBehaviour.Properties properties, IntProvider xpRange) {
        super(properties, xpRange);
    }

    public void m_214162_(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        ADParticles.AMBER.get().spawnClient(level, PositionSupplier.of((double)((double)pos.m_123341_() + 0.5), (double)((double)pos.m_123342_() + 1.0), (double)((double)pos.m_123343_() + 0.5)), ParticleEmitter.DEFAULT_ROTATION, 1.0f);
    }
}

