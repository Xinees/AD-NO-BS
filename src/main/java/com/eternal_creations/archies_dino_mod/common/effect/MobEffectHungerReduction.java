/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.effect.MobEffectCategory
 *  net.minecraft.world.effect.MobEffectInstance
 */
package com.eternal_creations.archies_dino_mod.common.effect;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class MobEffectHungerReduction
extends MobEffect {
    private static final double REDUCTION_SCALAR = 4.0;

    protected MobEffectHungerReduction() {
        super(MobEffectCategory.BENEFICIAL, 0xFFF6A6);
    }

    public static double calculateExhaustion(MobEffectInstance effect) {
        return Math.pow(effect.m_19564_() + 1, 0.25) / 4.0;
    }
}

