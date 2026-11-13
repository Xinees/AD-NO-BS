package com.eternal_creations.archies_dino_mod.client.particle;

import com.astemir.astemirlib.re.client.particle.BedrockParticleManager;
import com.astemir.astemirlib.re.client.particle.ParticleEmitterConfig;
import com.astemir.astemirlib.re.common.world.particle.ParticleEmitterType;
import com.eternal_creations.archies_dino_mod.Constants;
import com.eternal_creations.archies_dino_mod.client.particle.effect.ChorusoatlEffects;
import com.eternal_creations.archies_dino_mod.client.particle.effect.LushsaurEffects;
import com.eternal_creations.archies_dino_mod.client.particle.effect.SculksaurEffects;
import com.eternal_creations.archies_dino_mod.common.particle.ADParticles;
import com.eternal_creations.archies_dino_mod.util.ResourceSupplier;

public class ADParticleEmitters {
    public static void init() {
        LushsaurEffects.init();
        ChorusoatlEffects.init();
        SculksaurEffects.init();
    }

    public static ParticleEmitterConfig[] register(String path, int startIndex, int count) {
        ParticleEmitterConfig[] configs = new ParticleEmitterConfig[count];
        for (int i = 0; i < count; ++i) {
            int currentIndex = startIndex == 0 ? i + 1 : startIndex + i;
            String fullPath = startIndex == 0 && i == 0 ? path : path + "_" + currentIndex;
            configs[i] = register(fullPath);
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
        register(ADParticles.CHORUSOATL_SPIT_0, "chorusoatl/spit/chorusoatl_spit");
        register(ADParticles.CHORUSOATL_SPIT_1, "chorusoatl/spit/chorusoatl_spit_2");
        register(ADParticles.CHORUSOATL_SPIT_AOE, "chorusoatl/spit/chorusoatl_spit_aoe");
        register(ADParticles.CHORUSOATL_SPIT_PROJECTILE, "chorusoatl/spit/chorusoatl_spit_projectile");
    }
}
