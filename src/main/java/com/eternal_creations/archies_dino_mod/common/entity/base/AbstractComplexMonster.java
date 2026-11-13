/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.astemir.astemirlib.re.common.action.ActionState
 *  com.astemir.astemirlib.re.common.action.ActionTarget
 *  com.astemir.astemirlib.re.common.action.ActionType
 *  com.astemir.astemirlib.re.common.action.SyncedActionHolder
 *  com.astemir.astemirlib.re.common.world.entity.EntityLocomotion
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMap
 *  it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.core.particles.SimpleParticleType
 *  net.minecraft.network.syncher.EntityDataAccessor
 *  net.minecraft.network.syncher.EntityDataSerializer
 *  net.minecraft.network.syncher.EntityDataSerializers
 *  net.minecraft.network.syncher.SynchedEntityData
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.ai.navigation.PathNavigation
 *  net.minecraft.world.entity.monster.Monster
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.Nullable
 */
package com.eternal_creations.archies_dino_mod.common.entity.base;

import com.astemir.astemirlib.re.common.action.ActionState;
import com.astemir.astemirlib.re.common.action.ActionTarget;
import com.astemir.astemirlib.re.common.action.ActionType;
import com.astemir.astemirlib.re.common.action.SyncedActionHolder;
import com.astemir.astemirlib.re.common.world.entity.EntityLocomotion;
import com.eternal_creations.archies_dino_mod.common.entity.ai.control.pathfinder.SmoothPathNavigation;
import com.eternal_creations.archies_dino_mod.common.entity.base.ActionTargetBlock;
import com.eternal_creations.archies_dino_mod.common.entity.base.ActionTargetEntity;
import com.eternal_creations.archies_dino_mod.common.entity.utils.EntityUtils;
import com.eternal_creations.archies_dino_mod.server.ServerConfig;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.util.Set;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public abstract class AbstractComplexMonster
extends Monster
implements ActionTargetEntity,
ActionTargetBlock,
SyncedActionHolder {
    private static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.m_135353_(AbstractComplexMonster.class, (EntityDataSerializer)EntityDataSerializers.f_135027_);
    private final Object2ObjectMap<ActionType, ActionState> actions = new Object2ObjectOpenHashMap();
    private final EntityLocomotion locomotion = this.createLocomotion();
    @Nullable
    private LivingEntity tempAttackTarget;
    @Nullable
    private BlockPos tempBlockTarget;

    protected AbstractComplexMonster(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        this.setupActionStates();
    }

    public void setupActionStates() {
        this.actions.clear();
        for (ActionState state : this.createActionSet()) {
            this.actions.put((Object)state.getActionType(), (Object)state);
        }
    }

    protected abstract Set<ActionState> createActionSet();

    protected abstract EntityLocomotion createLocomotion();

    protected void m_8097_() {
        super.m_8097_();
        this.f_19804_.m_135372_(DATA_FLAGS_ID, (Object)0);
    }

    public void m_8119_() {
        super.m_8119_();
        this.tickActionStates();
        this.locomotion.tick((LivingEntity)this);
    }

    protected boolean m_8028_() {
        return this.getSettings().despawnsOnPeaceful();
    }

    protected abstract WildDinoSettings getSettings();

    public void m_216990_(SoundEvent sound) {
        if (!this.m_20067_()) {
            this.m_5496_(sound, 1.0f, 1.0f + this.f_19796_.m_188501_() * 0.25f);
        }
    }

    protected PathNavigation m_6037_(Level level) {
        return new SmoothPathNavigation((Mob)this, level);
    }

    protected boolean canRotate() {
        return true;
    }

    protected boolean isMovementBlocked() {
        return false;
    }

    public void m_7023_(Vec3 motion) {
        if (!this.m_6084_()) {
            return;
        }
        if (this.isMovementBlocked()) {
            motion = motion.m_82542_(0.0, 1.0, 0.0);
        }
        super.m_7023_(motion);
    }

    public void m_7822_(byte id) {
        if (id == 7) {
            this.spawnTamingParticles(true);
        } else if (id == 6) {
            this.spawnTamingParticles(false);
        } else {
            super.m_7822_(id);
        }
    }

    protected void spawnTamingParticles(boolean tamed) {
        SimpleParticleType particleoptions = ParticleTypes.f_123750_;
        if (!tamed) {
            particleoptions = ParticleTypes.f_123762_;
        }
        for (int i = 0; i < 7; ++i) {
            double d0 = this.f_19796_.m_188583_() * 0.02;
            double d1 = this.f_19796_.m_188583_() * 0.02;
            double d2 = this.f_19796_.m_188583_() * 0.02;
            this.m_9236_().m_7106_((ParticleOptions)particleoptions, this.m_20208_(1.0), this.m_20187_() + 0.5, this.m_20262_(1.0), d0, d1, d2);
        }
    }

    public int m_213860_() {
        return 10 + this.m_9236_().f_46441_.m_188503_(10);
    }

    public boolean m_217066_(LivingEntity target) {
        float attackRange = this.getAttackRange();
        return EntityUtils.getDistanceSqr((Entity)this, (Entity)target) <= (double)(attackRange * attackRange);
    }

    public float getAttackRange() {
        return 2.0f;
    }

    public EntityLocomotion getLocomotion() {
        return this.locomotion;
    }

    @Override
    public void setActionTargetBlock(@Nullable BlockPos targetBlock) {
        this.tempBlockTarget = targetBlock;
    }

    @Override
    @Nullable
    public BlockPos getActionTargetBlock() {
        return this.tempBlockTarget;
    }

    @Override
    public void setActionTargetEntity(@Nullable LivingEntity target) {
        this.tempAttackTarget = target;
    }

    @Override
    @Nullable
    public LivingEntity getActionTargetEntity() {
        return this.tempAttackTarget;
    }

    public Object2ObjectMap<ActionType, ActionState> getActionStates() {
        return this.actions;
    }

    public ActionTarget getActionTarget() {
        return ActionTarget.ENTITY;
    }

    public record WildDinoSettings(boolean despawnsOnPeaceful, boolean canTargetPlayers, ServerConfig.EntityStats stats) {
        public static final Codec<WildDinoSettings> CODEC = RecordCodecBuilder.create(instance -> instance.group((App)Codec.BOOL.fieldOf("despawns_on_peaceful").forGetter(WildDinoSettings::despawnsOnPeaceful), (App)Codec.BOOL.fieldOf("can_target_players").forGetter(WildDinoSettings::canTargetPlayers), (App)ServerConfig.EntityStats.CODEC.fieldOf("settings").forGetter(WildDinoSettings::stats)).apply((Applicative)instance, WildDinoSettings::new));
    }
}

