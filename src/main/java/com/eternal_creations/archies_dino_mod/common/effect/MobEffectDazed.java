/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.effect.MobEffectCategory
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.ai.attributes.AttributeMap
 *  org.lwjgl.system.NonnullDefault
 */
package com.eternal_creations.archies_dino_mod.common.effect;

import com.eternal_creations.archies_dino_mod.common.sound.ADSoundEvents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import org.lwjgl.system.NonnullDefault;

@NonnullDefault
public class MobEffectDazed
extends MobEffect {
    protected MobEffectDazed() {
        super(MobEffectCategory.HARMFUL, 1);
    }

    public void m_6385_(LivingEntity living, AttributeMap attributeMap, int amplifier) {
        super.m_6385_(living, attributeMap, amplifier);
        if (!living.m_9236_().f_46443_) {
            int duration = (amplifier + 1) * 100;
            living.m_7292_(new MobEffectInstance(MobEffects.f_19604_, duration, amplifier, false, false, false));
            living.m_7292_(new MobEffectInstance(MobEffects.f_19597_, duration, amplifier, false, false, false));
            living.m_7292_(new MobEffectInstance(MobEffects.f_19610_, duration, 0, false, false, false));
            if (living instanceof ServerPlayer) {
                ServerPlayer player = (ServerPlayer)living;
                player.m_6330_(ADSoundEvents.LUSHSAUR_EAR_RINGING.get(), SoundSource.PLAYERS, 0.5f, 1.0f);
            }
        }
    }
}

