/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.astemir.astemirlib.re.client.particle.BedrockParticleManager
 *  com.astemir.astemirlib.re.client.particle.ParticleEmitterConfig
 *  com.astemir.astemirlib.re.common.world.particle.ParticleEmitterType
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 */
package com.eternal_creations.archies_dino_mod.client.particle;

import com.astemir.astemirlib.re.client.particle.BedrockParticleManager;
import com.astemir.astemirlib.re.client.particle.ParticleEmitterConfig;
import com.astemir.astemirlib.re.common.world.particle.ParticleEmitterType;
import com.eternal_creations.archies_dino_mod.Constants;
import com.eternal_creations.archies_dino_mod.client.particle.effect.ChorusoatlEffects;
import com.eternal_creations.archies_dino_mod.client.particle.effect.CoralosaurEffects;
import com.eternal_creations.archies_dino_mod.client.particle.effect.LushsaurEffects;
import com.eternal_creations.archies_dino_mod.client.particle.effect.ObsidiceratopsEffects;
import com.eternal_creations.archies_dino_mod.client.particle.effect.SculkraptorEffects;
import com.eternal_creations.archies_dino_mod.client.particle.effect.SculksaurEffects;
import com.eternal_creations.archies_dino_mod.client.particle.effect.TherimossEffects;
import com.eternal_creations.archies_dino_mod.common.particle.ADParticles;
import com.eternal_creations.archies_dino_mod.util.ResourceSupplier;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ADParticleEmitters {
    public static void init() {
        LushsaurEffects.init();
        ChorusoatlEffects.init();
        ObsidiceratopsEffects.init();
        SculksaurEffects.init();
        SculkraptorEffects.init();
        TherimossEffects.init();
        CoralosaurEffects.init();
    }

    public static ParticleEmitterConfig[] register(String path, int startIndex, int count) {
        ParticleEmitterConfig[] configs = new ParticleEmitterConfig[count];
        for (int i = 0; i < count; ++i) {
            int currentIndex = startIndex + i;
            Object fullPath = startIndex == 0 ? (i == 0 ? path : path + "_" + (i + 1)) : path + "_" + currentIndex;
            configs[i] = ADParticleEmitters.register((String)fullPath);
        }
        return configs;
    }

    public static ParticleEmitterConfig register(String path) {
        return BedrockParticleManager.INSTANCE.registerParticle(Constants.prefix("particles/" + path + ".particle.json"));
    }

    public static ParticleEmitterConfig register(ResourceSupplier<ParticleEmitterType> type, String path) {
        return BedrockParticleManager.INSTANCE.registerParticle(type.get(), Constants.prefix("particles/" + path + ".particle.json"));
    }

    static {
        ADParticleEmitters.register("sculkraptor/projectile/sculkraptor_projectile", 1, 2);
        ADParticleEmitters.register(ADParticles.SCULKRAPTOR_PROJECTILE_IMPACT_0, "sculkraptor/projectile/sculkraptor_projectile_impact");
        ADParticleEmitters.register(ADParticles.SCULKRAPTOR_PROJECTILE_IMPACT_1, "sculkraptor/projectile/sculkraptor_projectile_impact_2");
        ADParticleEmitters.register(ADParticles.CHORUSOATL_SPIT_0, "chorusoatl/spit/chorusoatl_spit");
        ADParticleEmitters.register(ADParticles.CHORUSOATL_SPIT_1, "chorusoatl/spit/chorusoatl_spit_2");
        ADParticleEmitters.register(ADParticles.CHORUSOATL_SPIT_AOE, "chorusoatl/spit/chorusoatl_spit_aoe");
        ADParticleEmitters.register(ADParticles.CHORUSOATL_SPIT_PROJECTILE, "chorusoatl/spit/chorusoatl_spit_projectile");
        ADParticleEmitters.register(ADParticles.THERIMOSS_PROJECTILE_IMPACT, "therimoss/projectile/therimoss_ranged_impact_1");
        ADParticleEmitters.register(ADParticles.THERIMOSS_PROJECTILE, "therimoss/projectile/therimoss_ranged_attack");
        ADParticleEmitters.register(ADParticles.RIB_BOW, "rib_bow");
        ADParticleEmitters.register(ADParticles.AMBER, "amber");
        ADParticleEmitters.register(ADParticles.TOTEM_SHINE, "totem/totem_shine");
        ADParticleEmitters.register(ADParticles.TOTEM_PLACE, "totem/trophy_place");
        ADParticleEmitters.register(ADParticles.TOTEM_PLACE_PARTICLE_UNDER, "totem/trophy_place_particle_under");
        ADParticleEmitters.register(ADParticles.SCULKSAUR_EXPLOSION_2, "sculksaur/attacks/explosion/sculksaur_explosion_2");
        ADParticleEmitters.register(ADParticles.SCULKSAUR_EXPLOSION_3, "sculksaur/attacks/explosion/sculksaur_explosion_3");
        ADParticleEmitters.register(ADParticles.SCULKSAUR_EXPLOSION_4, "sculksaur/attacks/explosion/sculksaur_explosion_4");
        ADParticleEmitters.register(ADParticles.SCULKSAUR_EXPLOSION_5, "sculksaur/attacks/explosion/sculksaur_explosion_5");
        ADParticleEmitters.register(ADParticles.SCULKSAUR_EXPLOSION_6, "sculksaur/attacks/explosion/sculksaur_explosion_6");
    }
}

