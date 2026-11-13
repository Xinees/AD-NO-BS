/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.client.particle.Particle
 *  net.minecraft.client.particle.ParticleProvider
 *  net.minecraft.client.particle.TerrainParticle
 *  net.minecraft.core.particles.BlockParticleOption
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.state.BlockState
 */
package com.eternal_creations.archies_dino_mod.client.particle;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.TerrainParticle;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class DustPillarParticle
extends TerrainParticle {
    public DustPillarParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, BlockState state) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed, state);
    }

    @Nullable
    static DustPillarParticle createTerrainParticle(BlockParticleOption type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        BlockState blockstate = type.m_123642_();
        return !blockstate.m_60795_() && !blockstate.m_60713_(Blocks.f_50110_) ? new DustPillarParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, blockstate) : null;
    }

    public static class DustPillarProvider
    implements ParticleProvider<BlockParticleOption> {
        @Nullable
        public Particle createParticle(BlockParticleOption type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            DustPillarParticle particle = DustPillarParticle.createTerrainParticle(type, level, x, y, z, xSpeed, ySpeed, zSpeed);
            if (particle != null) {
                particle.m_172260_(level.f_46441_.m_188583_() / 30.0, ySpeed + level.f_46441_.m_188583_() / 2.0, level.f_46441_.m_188583_() / 30.0);
                particle.m_107257_(level.f_46441_.m_188503_(20) + 20);
            }
            return particle;
        }
    }
}

