/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.astemir.astemirlib.re.client.effect.shake.ShakeInstance
 *  com.astemir.astemirlib.re.common.function.PositionSupplier
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 */
package com.eternal_creations.archies_dino_mod.client.util;

import com.astemir.astemirlib.re.client.effect.shake.ShakeInstance;
import com.astemir.astemirlib.re.common.function.PositionSupplier;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class ShakeUtil {
    public static void shakeStep(Entity entity, float mass) {
        float m = Math.max(0.1f, mass);
        float radius = Math.min(18.0f, 5.0f + (float)Math.sqrt(m) * 2.0f);
        float intensity = Math.max(0.4f, Math.min(3.0f, 0.4f + (float)Math.log10(1.0f + m * 0.2f)));
        float duration = 0.3f;
        ShakeInstance.of((PositionSupplier)PositionSupplier.of(arg_0 -> ((Entity)entity).m_20318_(arg_0)), (float)radius, (float)intensity, (float)duration, p -> p.groundOnly().pureSpring().pitch(1.0f).noRoll().noYaw().linearFalloff().directionalImpulse(-1.0f, 0.0f).impulsePower(40.0f).spring(110.0f, 12.0f)).play();
    }

    public static void shakeTailSlam(LivingEntity entity, float intensity, float radius) {
        float i = Math.min(1.0f, intensity);
        RandomSource r = entity.m_217043_();
        float side = r.m_188499_() ? 1.0f : -1.0f;
        float front = -(0.7f + r.m_188501_() * 0.6f);
        float sideAmt = side * (0.2f + r.m_188501_() * 0.9f);
        float power = 95.0f + r.m_188501_() * 40.0f;
        float stiff = 220.0f + r.m_188501_() * 100.0f;
        float damp = 5.5f + r.m_188501_() * 3.0f;
        float freq = 10.0f + r.m_188501_() * 6.0f;
        float dur = 0.7f + r.m_188501_() * 0.5f;
        ShakeInstance.of((PositionSupplier)PositionSupplier.of(arg_0 -> ((LivingEntity)entity).m_20318_(arg_0)), (float)radius, (float)i, (float)dur, p -> p.groundOnly().weights(0.3f, 1.0f).angles(2.5f, 1.5f, 3.0f).frequency(freq).exponent(1.5f).directionalImpulse(front, sideAmt).impulsePower(power).spring(stiff, damp).linearFalloff()).play();
    }

    public static void shakeRoar(Entity entity, float duration, float intensity, float radius) {
        float i = Math.min(1.0f, intensity);
        ShakeInstance.of((PositionSupplier)PositionSupplier.of(arg_0 -> ((Entity)entity).m_20318_(arg_0)), (float)radius, (float)i, (float)Math.min(0.7f, duration), p -> p.pureSpring().spring(140.0f, 9.0f).directionalImpulse(1.0f, 0.3f).impulsePower(14.0f).falloff(0.7f)).play();
        ShakeInstance.of((PositionSupplier)PositionSupplier.of(arg_0 -> ((Entity)entity).m_20318_(arg_0)), (float)radius, (float)(i * 0.8f), (float)duration, p -> p.pureNoise().fadeIn(0.25f).frequency(68.0f).angles(0.9f, 0.7f, 1.5f).exponent(0.7f).falloff(0.7f)).play();
        ShakeInstance.of((PositionSupplier)PositionSupplier.of(arg_0 -> ((Entity)entity).m_20318_(arg_0)), (float)radius, (float)(i * 0.8f), (float)duration, p -> p.pureNoise().fadeIn(0.25f).frequency(81.0f).angles(0.7f, 0.9f, 1.2f).exponent(0.7f).falloff(0.7f)).play();
        ShakeInstance.of((PositionSupplier)PositionSupplier.of(arg_0 -> ((Entity)entity).m_20318_(arg_0)), (float)radius, (float)i, (float)duration, p -> p.pureNoise().fadeIn(0.15f).frequency(12.0f).angles(0.8f, 0.5f, 2.2f).exponent(1.0f).falloff(0.7f)).play();
    }

    public static void shakeUltrasonicScreech(Entity entity, float duration, float intensity, float radius) {
        float i = Math.min(1.0f, intensity);
        ShakeInstance.of((PositionSupplier)PositionSupplier.of(arg_0 -> ((Entity)entity).m_20318_(arg_0)), (float)radius, (float)i, (float)duration, p -> p.fadeIn(0.03f).frequency(85.0f).angles(0.8f, 1.6f, 1.8f).weights(1.0f, 1.0f).exponent(0.6f).directionalImpulse(0.8f, 0.0f).impulsePower(80.0f).spring(180.0f, 14.0f).linearFalloff()).play();
    }

    public static void shakeExplosion(Entity entity, float intensity, float radius) {
        float t = Math.min(1.0f, intensity);
        float g = Math.min(2.5f, Math.max(1.0f, intensity));
        ShakeInstance.of((PositionSupplier)PositionSupplier.of(arg_0 -> ((Entity)entity).m_20318_(arg_0)), (float)radius, (float)t, (float)1.2f, p -> p.pureSpring().spring(260.0f, 7.0f).directionalImpulse(-1.0f, 0.6f).impulsePower(130.0f * g).falloff(1.2f)).play();
        ShakeInstance.of((PositionSupplier)PositionSupplier.of(arg_0 -> ((Entity)entity).m_20318_(arg_0)), (float)radius, (float)t, (float)1.6f, p -> p.pureNoise().frequency(85.0f).angles(5.0f * g, 4.0f * g, 8.0f * g).exponent(1.8f).falloff(1.2f)).play();
        ShakeInstance.of((PositionSupplier)PositionSupplier.of(arg_0 -> ((Entity)entity).m_20318_(arg_0)), (float)radius, (float)t, (float)2.8f, p -> p.pureNoise().fadeIn(0.05f).frequency(38.0f).angles(3.0f * g, 2.0f * g, 7.0f * g).exponent(1.2f).falloff(0.8f)).play();
    }
}

