/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.astemir.astemirlib.re.client.model.core.animation.AnimationState
 *  com.astemir.astemirlib.re.client.model.core.animation.BoneState
 *  com.astemir.astemirlib.re.client.model.core.animation.node.modifier.Modifier
 *  com.astemir.astemirlib.re.core.math.AMath
 *  net.minecraft.world.entity.LivingEntity
 */
package com.eternal_creations.archies_dino_mod.client.model.system;

import com.astemir.astemirlib.re.client.model.core.animation.AnimationState;
import com.astemir.astemirlib.re.client.model.core.animation.BoneState;
import com.astemir.astemirlib.re.client.model.core.animation.node.modifier.Modifier;
import com.astemir.astemirlib.re.core.math.AMath;
import java.util.function.Supplier;
import net.minecraft.world.entity.LivingEntity;

public class ADModifiers {
    public static Modifier mountPlayerModifier(final Supplier<LivingEntity> mountSupplier, final float intensity, final float speed) {
        return new Modifier(){
            private float currentRoll = 0.0f;
            private float prevRoll = 0.0f;
            private double dx;
            private double dz;
            private float yBodyRot;
            private float yBodyRotO;

            public void modify(int boneId, AnimationState state, float alpha, float partialTick, BoneState out) {
                float renderRoll = AMath.lerp((float)partialTick, (float)this.prevRoll, (float)this.currentRoll);
                if (Math.abs(renderRoll) > 1.0E-5f) {
                    out.rotateZ(AMath.toRadians((float)renderRoll));
                }
            }

            public void tick() {
                LivingEntity mount = (LivingEntity)mountSupplier.get();
                if (mount != null) {
                    this.dx = mount.m_20185_() - mount.f_19854_;
                    this.dz = mount.m_20189_() - mount.f_19856_;
                    this.yBodyRot = mount.f_20883_;
                    this.yBodyRotO = mount.f_20884_;
                    this.prevRoll = this.currentRoll;
                    float targetRoll = 0.0f;
                    if (this.dx * this.dx + this.dz * this.dz > (double)1.0E-5f) {
                        float turnSpeed = AMath.wrapDegrees((float)(this.yBodyRot - this.yBodyRotO));
                        targetRoll = AMath.clamp((float)(-turnSpeed * intensity), (float)-45.0f, (float)45.0f);
                    }
                    this.currentRoll = AMath.lerp((float)speed, (float)this.currentRoll, (float)targetRoll);
                }
            }
        };
    }
}

