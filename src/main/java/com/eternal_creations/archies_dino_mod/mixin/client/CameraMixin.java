/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Camera
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.level.BlockGetter
 *  org.joml.Vector3d
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package com.eternal_creations.archies_dino_mod.mixin.client;

import com.eternal_creations.archies_dino_mod.common.entity.base.Mount;
import net.minecraft.client.Camera;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import org.joml.Vector3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={Camera.class})
public abstract class CameraMixin {
    @Shadow
    protected abstract void m_90568_(double var1, double var3, double var5);

    @Shadow
    protected abstract double m_90566_(double var1);

    @Shadow
    protected abstract void m_90584_(double var1, double var3, double var5);

    @Inject(method={"setup"}, at={@At(value="TAIL")})
    public void archiesDinoMod$Setup(BlockGetter level, Entity entity, boolean detached, boolean thirdPersonReverse, float partialTick, CallbackInfo ci) {
        Entity vehicle = entity.m_20202_();
        if (vehicle instanceof Mount) {
            Mount mount = (Mount)vehicle;
            Vector3d position = mount.getCameraPosition();
            float cameraDistance = mount.getCameraDistance(partialTick);
            this.m_90584_(Mth.m_14139_((double)partialTick, (double)vehicle.f_19854_, (double)vehicle.m_20185_()) + position.x(), Mth.m_14139_((double)partialTick, (double)vehicle.f_19855_, (double)vehicle.m_20186_()) + position.y(), Mth.m_14139_((double)partialTick, (double)vehicle.f_19856_, (double)vehicle.m_20189_()) + position.z());
            if (detached) {
                this.m_90568_(-this.m_90566_(cameraDistance), 0.0, 0.0);
            }
        }
    }
}

