/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.astemir.astemirlib.re.common.world.particle.ParticleEmitterType
 *  com.mojang.serialization.Codec
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.core.particles.BlockParticleOption
 *  net.minecraft.core.particles.ParticleType
 *  net.minecraft.resources.ResourceLocation
 */
package com.eternal_creations.archies_dino_mod.common.particle;

import com.astemir.astemirlib.re.common.world.particle.ParticleEmitterType;
import com.eternal_creations.archies_dino_mod.Constants;
import com.eternal_creations.archies_dino_mod.platform.Services;
import com.eternal_creations.archies_dino_mod.util.ResourceSupplier;
import com.mojang.serialization.Codec;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.resources.ResourceLocation;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ADParticles {
    public static final ResourceSupplier<ParticleType<BlockParticleOption>> DUST_PILLAR = Services.COMMON.registerParticleType("dust_pillar", () -> new ParticleType<BlockParticleOption>(false, BlockParticleOption.f_123624_){

        public Codec<BlockParticleOption> m_7652_() {
            return BlockParticleOption.m_123634_((ParticleType)this);
        }
    });
    public static final ResourceSupplier<ParticleEmitterType> SCULKRAPTOR_PROJECTILE_IMPACT_0 = null;
    public static final ResourceSupplier<ParticleEmitterType> SCULKRAPTOR_PROJECTILE_IMPACT_1 = null;
    public static final ResourceSupplier<ParticleEmitterType> CHORUSOATL_SPIT_0 = ADParticles.registerParticleEmitterType("chorusoatl_spit_0");
    public static final ResourceSupplier<ParticleEmitterType> CHORUSOATL_SPIT_1 = ADParticles.registerParticleEmitterType("chorusoatl_spit_1");
    public static final ResourceSupplier<ParticleEmitterType> CHORUSOATL_SPIT_AOE = ADParticles.registerParticleEmitterType("chorusoatl_spit_aoe");
    public static final ResourceSupplier<ParticleEmitterType> CHORUSOATL_SPIT_PROJECTILE = ADParticles.registerParticleEmitterType("chorusoatl_spit_projectile");
    public static final ResourceSupplier<ParticleEmitterType> THERIMOSS_PROJECTILE_IMPACT = null;
    public static final ResourceSupplier<ParticleEmitterType> THERIMOSS_PROJECTILE = null;
    public static final ResourceSupplier<ParticleEmitterType> RIB_BOW = null;
    public static final ResourceSupplier<ParticleEmitterType> AMBER = null;
    public static final ResourceSupplier<ParticleEmitterType> TOTEM_SHINE = null;
    public static final ResourceSupplier<ParticleEmitterType> TOTEM_PLACE = null;
    public static final ResourceSupplier<ParticleEmitterType> TOTEM_PLACE_PARTICLE_UNDER = null;
    public static final ResourceSupplier<ParticleEmitterType> SCULKSAUR_EXPLOSION_2 = ADParticles.registerParticleEmitterType("sculksaur_explosion_2");
    public static final ResourceSupplier<ParticleEmitterType> SCULKSAUR_EXPLOSION_3 = ADParticles.registerParticleEmitterType("sculksaur_explosion_3");
    public static final ResourceSupplier<ParticleEmitterType> SCULKSAUR_EXPLOSION_4 = ADParticles.registerParticleEmitterType("sculksaur_explosion_4");
    public static final ResourceSupplier<ParticleEmitterType> SCULKSAUR_EXPLOSION_5 = ADParticles.registerParticleEmitterType("sculksaur_explosion_5");
    public static final ResourceSupplier<ParticleEmitterType> SCULKSAUR_EXPLOSION_6 = ADParticles.registerParticleEmitterType("sculksaur_explosion_6");

    public static void init() {
    }

    private static ResourceSupplier<ParticleEmitterType> registerParticleEmitterType(String id) {
        ParticleEmitterType type = ParticleEmitterType.register((ResourceLocation)Constants.prefix(id));
        return new ResourceSupplier<ParticleEmitterType>(() -> type, type.id());
    }
}

