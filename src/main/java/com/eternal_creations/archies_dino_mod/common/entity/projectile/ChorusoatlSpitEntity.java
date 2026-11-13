/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.astemir.astemirlib.re.common.function.PositionSupplier
 *  com.astemir.astemirlib.re.common.world.particle.ParticleEmitter
 *  net.minecraft.core.Position
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.projectile.ThrowableProjectile
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.EntityHitResult
 *  net.minecraft.world.phys.HitResult
 *  org.jetbrains.annotations.NotNull
 */
package com.eternal_creations.archies_dino_mod.common.entity.projectile;

import com.astemir.astemirlib.re.common.function.PositionSupplier;
import com.astemir.astemirlib.re.common.world.particle.ParticleEmitter;
import com.eternal_creations.archies_dino_mod.common.entity.ADEntityTypes;
import com.eternal_creations.archies_dino_mod.common.entity.mob.chorusoatl.ChorusoatlEntity;
import com.eternal_creations.archies_dino_mod.common.entity.utils.EntityUtils;
import com.eternal_creations.archies_dino_mod.common.particle.ADParticles;
import net.minecraft.core.Position;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;

public class ChorusoatlSpitEntity
extends ThrowableProjectile {
    public ChorusoatlSpitEntity(EntityType<? extends ThrowableProjectile> entityType, Level level) {
        super(entityType, level);
    }

    public ChorusoatlSpitEntity(Level level) {
        this(ADEntityTypes.CHORUSOATL_SPIT.get(), level);
    }

    public ChorusoatlSpitEntity(Level level, LivingEntity shooter) {
        this(ADEntityTypes.CHORUSOATL_SPIT.get(), level);
        this.m_5602_((Entity)shooter);
    }

    protected void m_8097_() {
    }

    public void m_8119_() {
        super.m_8119_();
        if (this.m_9236_().f_46443_) {
            if (this.f_19797_ == 1) {
                ADParticles.CHORUSOATL_SPIT_0.get().spawnClient(this.m_9236_(), PositionSupplier.of((Position)this.m_20182_()), ParticleEmitter.DEFAULT_ROTATION, 1.0f);
                ADParticles.CHORUSOATL_SPIT_1.get().spawnClient(this.m_9236_(), PositionSupplier.of((Position)this.m_20182_()), ParticleEmitter.DEFAULT_ROTATION, 1.0f);
            }
            if (this.f_19797_ % 2 == 0) {
                ADParticles.CHORUSOATL_SPIT_PROJECTILE.get().spawnClient(this.m_9236_(), PositionSupplier.of(arg_0 -> ((ChorusoatlSpitEntity)this).m_20318_(arg_0)), ParticleEmitter.DEFAULT_ROTATION, 1.0f).setAliveCondition(() -> ((ChorusoatlSpitEntity)this).m_6084_());
            }
        }
        if (this.f_19797_ > 60) {
            this.m_146870_();
        }
    }

    protected void m_5790_(@NotNull EntityHitResult result) {
        super.m_5790_(result);
        Entity target = result.m_82443_();
        Entity owner = this.m_19749_();
        if (target == owner || owner != null && owner.m_20363_(target)) {
            return;
        }
        if (target instanceof ChorusoatlEntity) {
            return;
        }
        if (target instanceof LivingEntity) {
            float f;
            LivingEntity livingEntity = (LivingEntity)target;
            DamageSource damageSource = this.m_269291_().m_269390_((Entity)this, owner);
            if (owner instanceof ChorusoatlEntity) {
                ChorusoatlEntity chorusoatl = (ChorusoatlEntity)owner;
                f = EntityUtils.multiplyDamage((Mob)chorusoatl, livingEntity, 0.16666667f);
            } else {
                f = 3.0f;
            }
            if (livingEntity.m_6469_(damageSource, f)) {
                livingEntity.m_147207_(new MobEffectInstance(MobEffects.f_19620_, 40, 4), (Entity)this);
            }
        }
    }

    protected void m_6532_(@NotNull HitResult result) {
        super.m_6532_(result);
        if (!this.m_9236_().f_46443_) {
            this.m_9236_().m_7605_((Entity)this, (byte)3);
            this.m_146870_();
        }
    }

    protected float m_7139_() {
        return 0.0f;
    }
}

