/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 */
package com.eternal_creations.archies_dino_mod.common.effect;

import com.eternal_creations.archies_dino_mod.common.effect.MobEffectDazed;
import com.eternal_creations.archies_dino_mod.common.effect.MobEffectFallNegation;
import com.eternal_creations.archies_dino_mod.common.effect.MobEffectHungerReduction;
import com.eternal_creations.archies_dino_mod.common.effect.MobEffectTracker;
import com.eternal_creations.archies_dino_mod.platform.Services;
import com.eternal_creations.archies_dino_mod.util.ResourceSupplier;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ADMobEffects {
    public static final ResourceSupplier<MobEffectFallNegation> FALL_NEGATION = Services.COMMON.registerMobEffect("fall_negation", MobEffectFallNegation::new);
    public static final ResourceSupplier<MobEffectHungerReduction> HUNGER_REDUCTION = Services.COMMON.registerMobEffect("hunger_reduction", MobEffectHungerReduction::new);
    public static final ResourceSupplier<MobEffectDazed> DAZED = Services.COMMON.registerMobEffect("dazed", MobEffectDazed::new);
    public static final ResourceSupplier<MobEffectTracker> TRACKED = Services.COMMON.registerMobEffect("tracked", MobEffectTracker::new);

    public static void init() {
    }
}

