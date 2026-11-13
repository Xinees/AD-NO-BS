/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.astemir.astemirlib.re.common.function.PositionSupplier
 *  com.astemir.astemirlib.re.common.world.particle.ParticleEmitter
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.network.protocol.game.ClientboundAddEntityPacket
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.Entity$RemovalReason
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.projectile.LlamaSpit
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.EntityHitResult
 *  net.minecraft.world.phys.Vec3
 */
package com.eternal_creations.archies_dino_mod.common.entity.projectile;

import com.astemir.astemirlib.re.common.function.PositionSupplier;
import com.astemir.astemirlib.re.common.world.particle.ParticleEmitter;
import com.eternal_creations.archies_dino_mod.common.effect.ADMobEffects;
import com.eternal_creations.archies_dino_mod.common.entity.ADEntityTypes;
import com.eternal_creations.archies_dino_mod.common.entity.mob.sculkraptor.SculkraptorEntity;
import com.eternal_creations.archies_dino_mod.common.entity.utils.EntityUtils;
import com.eternal_creations.archies_dino_mod.common.particle.ADParticles;
import java.util.function.Predicate;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.projectile.LlamaSpit;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class SculkraptorSpitEntity
extends LlamaSpit {
    private static final Predicate<RandomSource> POISON_CHANCE = randomSource -> randomSource.m_188503_(100) < 15;
    private static final Predicate<RandomSource> TRACK_CHANCE = randomSource -> randomSource.m_188503_(100) < 7;
    private static final int POISON_DURATION = 3;
    private static final int TRACK_DURATION = 20;

    public SculkraptorSpitEntity(EntityType<? extends LlamaSpit> entityType, Level level) {
        super(entityType, level);
    }

    public SculkraptorSpitEntity(Level level, LivingEntity shooter) {
        super(ADEntityTypes.SCULKRAPTOR_SPIT.get(), level);
        this.m_5602_((Entity)shooter);
        this.m_6034_(shooter.m_20185_(), shooter.m_20188_() - 0.1, shooter.m_20189_());
    }

    /*
     * Unable to fully structure code
     */
    public void m_5790_(EntityHitResult result) {
        victim = result.m_82443_();
        if (victim instanceof SculkraptorEntity) {
            return;
        }
        var4_3 = this.m_19749_();
        if (!(var4_3 instanceof LivingEntity)) {
            return;
        }
        owner = (LivingEntity)var4_3;
        if (!(owner instanceof SculkraptorEntity)) ** GOTO lbl-1000
        raptor = (SculkraptorEntity)owner;
        if (victim instanceof LivingEntity) {
            target = (LivingEntity)victim;
            v0 = EntityUtils.multiplyDamage((Mob)raptor, target, 0.6666667f);
        } else lbl-1000:
        // 2 sources

        {
            v0 = damage = 1.0f;
        }
        if (victim.m_6469_(this.m_269291_().m_269299_((Entity)this, owner), damage) && victim instanceof LivingEntity) {
            target = (LivingEntity)victim;
            this.doPostHurtEffects(target);
        }
        if (!this.m_9236_().f_46443_) {
            this.m_146870_();
        }
    }

    protected boolean m_5603_(Entity entity) {
        return !(entity instanceof SculkraptorEntity) && super.m_5603_(entity);
    }

    protected void doPostHurtEffects(LivingEntity living) {
        if (TRACK_CHANCE.test(living.m_217043_())) {
            living.m_7292_(new MobEffectInstance((MobEffect)ADMobEffects.TRACKED.get(), 20, 0));
        } else if (POISON_CHANCE.test(living.m_217043_())) {
            living.m_7292_(new MobEffectInstance(MobEffects.f_19614_, 3, 0));
        }
    }

    public void m_8119_() {
        super.m_8119_();
    }

    public void m_142687_(Entity.RemovalReason reason) {
        if (this.m_9236_().f_46443_) {
            Vec3 pos = this.m_20182_();
            ADParticles.SCULKRAPTOR_PROJECTILE_IMPACT_0.get().spawnClient(this.m_9236_(), PositionSupplier.of((double)pos.f_82479_, (double)pos.f_82480_, (double)pos.f_82481_), ParticleEmitter.DEFAULT_ROTATION, 1.0f);
            ADParticles.SCULKRAPTOR_PROJECTILE_IMPACT_1.get().spawnClient(this.m_9236_(), PositionSupplier.of((double)pos.f_82479_, (double)pos.f_82480_, (double)pos.f_82481_), ParticleEmitter.DEFAULT_ROTATION, 1.0f);
        }
        super.m_142687_(reason);
    }

    public void m_141965_(ClientboundAddEntityPacket packet) {
        int i = packet.m_131496_();
        double d0 = packet.m_131500_();
        double d1 = packet.m_131501_();
        double d2 = packet.m_131502_();
        this.m_217006_(d0, d1, d2);
        this.m_6027_(d0, d1, d2);
        this.m_146926_(packet.m_237566_());
        this.m_146922_(packet.m_237567_());
        this.m_20234_(i);
        this.m_20084_(packet.m_131499_());
        Entity entity = this.m_9236_().m_6815_(packet.m_131509_());
        if (entity != null) {
            this.m_5602_(entity);
        }
        this.m_20334_(d0, d1, d2);
    }
}

