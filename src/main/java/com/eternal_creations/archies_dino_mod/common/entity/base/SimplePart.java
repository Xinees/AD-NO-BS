/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 */
package com.eternal_creations.archies_dino_mod.common.entity.base;

import com.eternal_creations.archies_dino_mod.common.entity.base.IPartEntity;
import com.eternal_creations.archies_dino_mod.mixin.EntityAccessor;
import com.eternal_creations.archies_dino_mod.platform.Services;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class SimplePart<T extends LivingEntity> {
    private final T parent;
    private final IPartEntity<?> entity;
    private final double offsetX;
    private final double offsetY;
    private final double offsetZ;
    private final float baseWidth;
    private final float baseHeight;
    private float width;
    private float height;

    public SimplePart(T parent, float width, float height, double offsetX, double offsetY, double offsetZ) {
        this.parent = parent;
        this.baseWidth = width;
        this.baseHeight = height;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.offsetZ = offsetZ;
        this.width = width;
        this.height = height;
        this.entity = Services.COMMON.makePartEntity(parent, width, height);
    }

    public void tick() {
        this.tick(0.0f, 0.0f, 0.0f);
    }

    public void tick(float ox, float oy, float oz) {
        float scale = this.parent.m_6134_();
        float xRot = this.parent.m_146909_();
        float yRot = ((LivingEntity)this.parent).f_20883_;
        float pitchRad = xRot * ((float)Math.PI / 180);
        float yawRad = -yRot * ((float)Math.PI / 180);
        float cosYaw = Mth.m_14089_((float)yawRad);
        float sinYaw = Mth.m_14031_((float)yawRad);
        float cosPitch = Mth.m_14089_((float)pitchRad);
        float sinPitch = Mth.m_14031_((float)pitchRad);
        double curX = (this.offsetX + (double)ox) * (double)scale;
        double curY = (this.offsetY + (double)oy) * (double)scale;
        double curZ = (this.offsetZ + (double)oz) * (double)scale;
        double y1 = curY * (double)cosPitch - curZ * (double)sinPitch;
        double z1 = curZ * (double)cosPitch + curY * (double)sinPitch;
        double x1 = curX;
        double rx = x1 * (double)cosYaw + z1 * (double)sinYaw;
        double ry = y1;
        double rz = z1 * (double)cosYaw - x1 * (double)sinYaw;
        this.width = this.baseWidth * scale;
        this.height = this.baseHeight * scale;
        this.entity.setPartPos(this.parent.m_20185_() + rx, this.parent.m_20186_() + ry, this.parent.m_20189_() + rz);
        this.entity.resize(this.width, this.height);
    }

    public IPartEntity<?> getEntity() {
        return this.entity;
    }

    public static <T extends Entity> IPartEntity<?>[] collectEntities(T parent, SimplePart<?> ... parts) {
        IPartEntity[] result = new IPartEntity[parts.length];
        int startId = EntityAccessor.getENTITY_COUNTER().getAndAdd(parts.length + 1) + 1;
        parent.m_20234_(startId);
        for (int i = 0; i < parts.length; ++i) {
            result[i] = parts[i].entity;
            parts[i].entity.setPartId(startId + i + 1);
        }
        return result;
    }
}

