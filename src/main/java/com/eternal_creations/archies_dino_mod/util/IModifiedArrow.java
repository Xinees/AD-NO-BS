/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.astemir.astemirlib.re.common.function.PositionSupplier
 *  com.astemir.astemirlib.re.common.world.particle.ParticleEmitter
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.Util
 *  net.minecraft.core.Position
 *  net.minecraft.core.particles.BlockParticleOption
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.entity.projectile.AbstractArrow
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.phys.Vec3
 */
package com.eternal_creations.archies_dino_mod.util;

import com.astemir.astemirlib.re.common.function.PositionSupplier;
import com.astemir.astemirlib.re.common.world.particle.ParticleEmitter;
import com.eternal_creations.archies_dino_mod.common.particle.ADParticles;
import java.util.List;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.Util;
import net.minecraft.core.Position;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public interface IModifiedArrow {
    public static void archiesDinoMod$tick(AbstractArrow arrow) {
        Level level = arrow.m_9236_();
        if (arrow.f_19797_ >= 1 && arrow.f_19797_ <= 2) {
            Block coral = (Block)Util.m_214621_(List.of(Blocks.f_50595_, Blocks.f_50594_, Blocks.f_50597_, Blocks.f_50598_, Blocks.f_50596_), (RandomSource)level.m_213780_());
            Level level2 = arrow.m_9236_();
            if (level2 instanceof ServerLevel) {
                ServerLevel server = (ServerLevel)level2;
                server.m_8767_((ParticleOptions)new BlockParticleOption(ParticleTypes.f_123794_, coral.m_49966_()), arrow.m_20185_(), arrow.m_20186_(), arrow.m_20189_(), 5, 0.2, 0.2, 0.2, 0.3);
            }
        }
        if (!level.f_46443_) {
            return;
        }
        Vec3 dm = arrow.m_20184_();
        if (dm.m_82556_() < 1.0E-6) {
            return;
        }
        Vec3 look = dm.m_82541_();
        Vec3 tail = arrow.m_20182_().m_82549_(look.m_82490_(-0.5));
        ADParticles.RIB_BOW.get().spawnClient(level, PositionSupplier.of((Position)tail), ParticleEmitter.DEFAULT_ROTATION, 2.0f);
    }

    public void archiesDinoMod$setShotFromRibBow(boolean var1);
}

