/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.astemir.astemirlib.re.client.model.core.system.ModelSystem
 *  com.astemir.astemirlib.re.client.model.core.system.ModelSystem$Listener
 *  com.astemir.astemirlib.re.core.math.Transform
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.Saddleable
 *  net.minecraft.world.phys.Vec3
 */
package com.eternal_creations.archies_dino_mod.client.model.render.entity;

import com.astemir.astemirlib.re.client.model.core.system.ModelSystem;
import com.astemir.astemirlib.re.core.math.Transform;
import com.eternal_creations.archies_dino_mod.client.screen.DinoScreen;
import com.eternal_creations.archies_dino_mod.common.entity.base.Mount;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Saddleable;
import net.minecraft.world.phys.Vec3;

public interface MountedListener<T extends Mob>
extends ModelSystem.Listener<T> {
    default public void onUpdate(ModelSystem<T> modelSystem, T instance, float partialTick) {
        Minecraft mc = Minecraft.m_91087_();
        LocalPlayer player = mc.f_91074_;
        if (player == null || mc.f_91073_ == null) {
            return;
        }
        if (instance.m_9236_() != mc.f_91073_) {
            return;
        }
        if (mc.f_91080_ instanceof DinoScreen) {
            return;
        }
        List passengers = instance.m_20197_();
        if (passengers.contains(player)) {
            Transform transform;
            Mount.MountPoint<T> point = ((Saddleable)instance).m_6254_() ? this.getSaddledPoint() : this.getUnsaddledPoint();
            Transform transform2 = transform = point.type() == Mount.PointType.BONE ? modelSystem.getBoneTransform(instance, point.name()) : modelSystem.getLocatorTransform(instance, point.name());
            if (transform == null) {
                return;
            }
            float yRot = (Mth.m_14179_((float)partialTick, (float)((Mob)instance).f_20884_, (float)((Mob)instance).f_20883_) - 180.0f) * ((float)Math.PI / 180);
            Vec3 vec3 = point.offset().m_82524_(-yRot);
            ((Mount)instance).setCameraPosition(transform.getX() + (float)vec3.f_82479_, transform.getY() + player.m_20206_() + (float)vec3.f_82480_, transform.getZ() + (float)vec3.f_82481_);
        }
    }

    public Mount.MountPoint<T> getSaddledPoint();

    public Mount.MountPoint<T> getUnsaddledPoint();
}

