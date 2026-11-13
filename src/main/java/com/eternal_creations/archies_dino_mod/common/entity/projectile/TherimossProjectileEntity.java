/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.particles.ItemParticleOption
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.network.syncher.EntityDataAccessor
 *  net.minecraft.network.syncher.EntityDataSerializer
 *  net.minecraft.network.syncher.EntityDataSerializers
 *  net.minecraft.network.syncher.SynchedEntityData
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.projectile.ThrowableProjectile
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.phys.EntityHitResult
 *  net.minecraft.world.phys.HitResult
 *  org.jetbrains.annotations.NotNull
 *  org.joml.Vector3d
 */
package com.eternal_creations.archies_dino_mod.common.entity.projectile;

import com.eternal_creations.archies_dino_mod.common.entity.ADEntityTypes;
import com.eternal_creations.archies_dino_mod.common.entity.base.IMultipartEntity;
import com.eternal_creations.archies_dino_mod.common.entity.mob.therimoss.TherimossEntity;
import com.eternal_creations.archies_dino_mod.common.entity.utils.EntityUtils;
import com.eternal_creations.archies_dino_mod.common.particle.ADParticles;
import com.eternal_creations.archies_dino_mod.common.sound.ADSoundEvents;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3d;

public class TherimossProjectileEntity
extends ThrowableProjectile {
    private static final EntityDataAccessor<Boolean> IS_ATTACHED = SynchedEntityData.m_135353_(TherimossProjectileEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135035_);
    private static final ItemParticleOption DIRT_PARTICLE = new ItemParticleOption(ParticleTypes.f_123752_, Blocks.f_50493_.m_5456_().m_7968_());
    public final Vector3d renderPos = new Vector3d();

    public TherimossProjectileEntity(EntityType<? extends ThrowableProjectile> entityType, Level level) {
        super(entityType, level);
        this.f_19794_ = true;
    }

    public TherimossProjectileEntity(Level level) {
        this(ADEntityTypes.THERIMOSS_PROJECTILE.get(), level);
    }

    public TherimossProjectileEntity(Level level, LivingEntity owner) {
        this(ADEntityTypes.THERIMOSS_PROJECTILE.get(), level);
        this.m_5602_((Entity)owner);
    }

    private void triggerShoot() {
        this.f_19804_.m_135381_(IS_ATTACHED, (Object)false);
        this.f_19794_ = false;
        this.m_5496_(ADSoundEvents.THERIMOSS_BLOCK_THROW.get(), 1.0f, 1.0f);
    }

    protected void m_8097_() {
        this.f_19804_.m_135372_(IS_ATTACHED, (Object)true);
    }

    public void m_8119_() {
        super.m_8119_();
        if (this.f_19797_ > 200) {
            this.m_146870_();
            return;
        }
        if (this.m_9236_().f_46443_ && !this.isAttached()) {
            for (int i = 0; i < 3; ++i) {
                this.m_9236_().m_7106_((ParticleOptions)DIRT_PARTICLE, this.m_20208_(0.5), this.m_20187_(), this.m_20262_(0.5), 0.0, 0.0, 0.0);
            }
        }
    }

    /*
     * Unable to fully structure code
     */
    protected void m_5790_(@NotNull EntityHitResult result) {
        super.m_5790_(result);
        target = result.m_82443_();
        owner = this.m_19749_();
        if (target == owner) {
            return;
        }
        if (owner instanceof IMultipartEntity && (multipartEntity = (IMultipartEntity)owner).isPartOfMe(target)) {
            return;
        }
        if (owner != null && (owner.m_20363_(target) || target.m_20363_(owner))) {
            return;
        }
        var damageSource = this.m_269291_().m_269390_((Entity)this, owner);
        float damage = 5.0f;
        if (owner instanceof TherimossEntity therimoss && target instanceof LivingEntity living) {
            damage = EntityUtils.multiplyDamage((Mob)therimoss, living, 0.625f);
        }
        target.m_6469_(damageSource, damage);
    }

    protected void m_6532_(@NotNull HitResult result) {
        super.m_6532_(result);
        if (!this.m_9236_().f_46443_) {
            ADParticles.THERIMOSS_PROJECTILE_IMPACT.get().spawn(this.m_9236_(), result.m_82450_().m_82520_(0.0, 0.25, 0.0));
            this.m_9236_().m_6263_(null, this.m_20185_(), this.m_20186_(), this.m_20189_(), ADSoundEvents.THERIMOSS_BLOCK_LAND.get(), SoundSource.NEUTRAL, 1.0f, 1.0f);
            this.m_146870_();
        }
    }

    protected boolean m_5603_(@NotNull Entity target) {
        IMultipartEntity multipartEntity;
        Entity entity = this.m_19749_();
        if (entity instanceof IMultipartEntity && (multipartEntity = (IMultipartEntity)entity).isPartOfMe(target)) {
            return false;
        }
        return super.m_5603_(target);
    }

    public void m_6686_(double x, double y, double z, float velocity, float inaccuracy) {
        super.m_6686_(x, y, z, velocity, inaccuracy);
        this.triggerShoot();
    }

    public void m_37251_(@NotNull Entity shooter, float x, float y, float z, float velocity, float inaccuracy) {
        super.m_37251_(shooter, x, y, z, velocity, inaccuracy);
        this.triggerShoot();
    }

    public boolean isAttached() {
        return (Boolean)this.f_19804_.m_135370_(IS_ATTACHED);
    }

    protected float m_7139_() {
        return this.isAttached() ? 0.0f : 0.02f;
    }
}

