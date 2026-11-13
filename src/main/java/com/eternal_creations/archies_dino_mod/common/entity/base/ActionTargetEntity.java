/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 */
package com.eternal_creations.archies_dino_mod.common.entity.base;

import com.eternal_creations.archies_dino_mod.common.entity.utils.EntityUtils;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public interface ActionTargetEntity {
    default public void attackActionTargetEntity(float damageMul, float knockbackMul) {
        Mob mob = (Mob)this;
        LivingEntity tempTarget = this.getActionTargetEntity();
        if (tempTarget != null) {
            if (EntityUtils.attackTarget(mob, tempTarget, damageMul, knockbackMul)) {
                this.setActionTargetEntity(null);
            }
        } else {
            EntityUtils.attackForward(mob, mob.m_20205_() * 2.0f, -1.0f, knockbackMul, damageMul, t -> {});
        }
    }

    public void setActionTargetEntity(@Nullable LivingEntity var1);

    @Nullable
    public LivingEntity getActionTargetEntity();
}

