/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.astemir.astemirlib.re.common.action.ActionSequence
 *  com.astemir.astemirlib.re.common.action.ActionState
 *  com.astemir.astemirlib.re.common.action.ActionType
 *  com.astemir.astemirlib.re.common.action.SyncedActionHolder
 *  com.astemir.astemirlib.re.common.world.entity.EntityLocomotion
 *  com.google.common.annotations.VisibleForTesting
 *  com.mojang.serialization.Dynamic
 *  com.mojang.serialization.DynamicOps
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.commands.arguments.EntityAnchorArgument$Anchor
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.VibrationParticleOption
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.NbtOps
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.tags.TagKey
 *  net.minecraft.util.Mth
 *  net.minecraft.util.SpawnUtil$Strategy
 *  net.minecraft.util.Unit
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.AgeableMob
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.Entity$RemovalReason
 *  net.minecraft.world.entity.EntitySelector
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.ai.Brain
 *  net.minecraft.world.entity.ai.control.BodyRotationControl
 *  net.minecraft.world.entity.ai.memory.MemoryModuleType
 *  net.minecraft.world.entity.ai.memory.MemoryStatus
 *  net.minecraft.world.entity.monster.RangedAttackMob
 *  net.minecraft.world.entity.monster.warden.AngerLevel
 *  net.minecraft.world.entity.monster.warden.AngerManagement
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.gameevent.DynamicGameEventListener
 *  net.minecraft.world.level.gameevent.EntityPositionSource
 *  net.minecraft.world.level.gameevent.GameEvent
 *  net.minecraft.world.level.gameevent.GameEvent$Context
 *  net.minecraft.world.level.gameevent.GameEventListener
 *  net.minecraft.world.level.gameevent.PositionSource
 *  net.minecraft.world.level.gameevent.vibrations.VibrationInfo
 *  net.minecraft.world.level.gameevent.vibrations.VibrationSystem
 *  net.minecraft.world.level.gameevent.vibrations.VibrationSystem$Data
 *  net.minecraft.world.level.gameevent.vibrations.VibrationSystem$Listener
 *  net.minecraft.world.level.gameevent.vibrations.VibrationSystem$Ticker
 *  net.minecraft.world.level.gameevent.vibrations.VibrationSystem$User
 *  net.minecraft.world.level.pathfinder.BlockPathTypes
 *  net.minecraft.world.level.pathfinder.Path
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.EntityHitResult
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.Nullable
 *  org.slf4j.Logger
 */
package com.eternal_creations.archies_dino_mod.common.entity.mob.sculkraptor;

import com.astemir.astemirlib.re.common.action.ActionSequence;
import com.astemir.astemirlib.re.common.action.ActionState;
import com.astemir.astemirlib.re.common.action.ActionType;
import com.astemir.astemirlib.re.common.action.SyncedActionHolder;
import com.astemir.astemirlib.re.common.world.entity.EntityLocomotion;
import com.eternal_creations.archies_dino_mod.Constants;
import com.eternal_creations.archies_dino_mod.common.ADGameEvents;
import com.eternal_creations.archies_dino_mod.common.ADTags;
import com.eternal_creations.archies_dino_mod.common.entity.ADActionTypes;
import com.eternal_creations.archies_dino_mod.common.entity.ADEntityTypes;
import com.eternal_creations.archies_dino_mod.common.entity.ADMemoryModuleTypes;
import com.eternal_creations.archies_dino_mod.common.entity.ai.control.SmoothBodyControl;
import com.eternal_creations.archies_dino_mod.common.entity.ai.control.SmoothLookControl;
import com.eternal_creations.archies_dino_mod.common.entity.ai.control.SmoothMoveControl;
import com.eternal_creations.archies_dino_mod.common.entity.base.AbstractComplexMob;
import com.eternal_creations.archies_dino_mod.common.entity.component.PetComponent;
import com.eternal_creations.archies_dino_mod.common.entity.mob.sculkraptor.SculkraptorNestEntity;
import com.eternal_creations.archies_dino_mod.common.entity.mob.sculkraptor.ai.SculkraptorAI;
import com.eternal_creations.archies_dino_mod.common.entity.mob.sculkraptor.ai.SculkraptorFlee;
import com.eternal_creations.archies_dino_mod.common.entity.mob.sculksaur.ai.SculksaurSonicBoom;
import com.eternal_creations.archies_dino_mod.common.entity.projectile.SculkraptorSpitEntity;
import com.eternal_creations.archies_dino_mod.common.entity.utils.EntityUtils;
import com.eternal_creations.archies_dino_mod.common.sound.ADSoundEvents;
import com.eternal_creations.archies_dino_mod.server.ServerConfig;
import com.google.common.annotations.VisibleForTesting;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.VibrationParticleOption;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.SpawnUtil;
import net.minecraft.util.Unit;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.monster.warden.AngerLevel;
import net.minecraft.world.entity.monster.warden.AngerManagement;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.gameevent.DynamicGameEventListener;
import net.minecraft.world.level.gameevent.EntityPositionSource;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.gameevent.PositionSource;
import net.minecraft.world.level.gameevent.vibrations.VibrationInfo;
import net.minecraft.world.level.gameevent.vibrations.VibrationSystem;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class SculkraptorEntity
extends AbstractComplexMob
implements VibrationSystem,
RangedAttackMob {
    private static final String DATA_KEY_ANGER = "anger";
    private static final String DATA_KEY_LISTENER = "listener";
    private static final double FRIEND_CHECK_RANGE = 32.0;
    private static final int NEST_SPAWN_INTERVAL = 80;
    private static final float DEFAULT_HEALTH = 34.0f;
    private static final float BABY_HEALTH_MULTIPLIER = 0.5294118f;
    private static final float DEFAULT_ATTACK_DAMAGE = 3.0f;
    private static final float BITE_MULTIPLIER = 1.0f;
    public static final float RANGED_MULTIPLIER = 0.6666667f;
    public static final float TRACKER_MULTIPLIER = 0.33333334f;
    public static final AbstractComplexMob.TameableDinoSettings DEFAULT_STATS = new AbstractComplexMob.TameableDinoSettings(false, true, true, new ServerConfig.EntityStats(0.4, 34.0, 0.0, 0.0, 48.0, 3.0));
    public ActionState actionDeath;
    public ActionState actionSniff;
    public ActionState actionAttackSonicBoom;
    public ActionState actionAttackBiteStand;
    public ActionState actionAttackRanged;
    private final ActionSequence actionSequence;
    private final DynamicGameEventListener<VibrationSystem.Listener> dynamicGameEventListener;
    private final VibrationSystem.User vibrationUser;
    private VibrationSystem.Data vibrationData;
    AngerManagement angerManagement = new AngerManagement(this::canTargetEntity, Collections.emptyList());
    @Nullable
    private GameEvent lastGameEvent = null;

    public SculkraptorEntity(EntityType<? extends AbstractComplexMob> entityType, Level level) {
        super(entityType, level);
        this.actionSequence = new ActionSequence((SyncedActionHolder)this);
        this.f_21342_ = new SmoothMoveControl((Mob)this, 0.45f);
        this.f_21365_ = new SmoothLookControl((Mob)this, 45.0f, 0.6).setCanRotate(this::canRotate);
        this.vibrationUser = new VibrationUser();
        this.vibrationData = new VibrationSystem.Data();
        this.dynamicGameEventListener = new DynamicGameEventListener((GameEventListener)new VibrationSystem.Listener((VibrationSystem)this));
        this.f_21364_ = 5;
        this.m_21573_().m_7008_(true);
        this.m_21441_(BlockPathTypes.UNPASSABLE_RAIL, 0.0f);
        this.m_21441_(BlockPathTypes.DAMAGE_OTHER, 8.0f);
        this.m_21441_(BlockPathTypes.POWDER_SNOW, 8.0f);
        this.m_21441_(BlockPathTypes.LAVA, 8.0f);
        this.m_21441_(BlockPathTypes.DAMAGE_FIRE, 0.0f);
        this.m_21441_(BlockPathTypes.DANGER_FIRE, 0.0f);
    }

    protected Brain<?> m_8075_(Dynamic<?> dynamic) {
        return SculkraptorAI.makeBrain(this, dynamic);
    }

    public Brain<SculkraptorEntity> m_6274_() {
        return super.m_6274_();
    }

    public VibrationSystem.Data m_280002_() {
        return this.vibrationData;
    }

    public VibrationSystem.User m_280445_() {
        return this.vibrationUser;
    }

    @Override
    protected Set<ActionState> makeActionSet() {
        this.actionAttackSonicBoom = ActionState.of((ActionType)ADActionTypes.ATTACK_SONIC_BOOM, (float)2.25f, (float)0.1f, (float)0.4f).exitTime(2.1f).onStart(() -> this.m_216990_(ADSoundEvents.SCULKRAPTOR_EXPLOSION.get()));
        this.actionAttackRanged = ActionState.of((ActionType)ADActionTypes.ATTACK_SHOOT, (float)2.17f, (float)0.1f, (float)0.4f).exitTime(2.0f).onStart(() -> this.m_216990_(ADSoundEvents.SCULKRAPTOR_RANGED.get())).onTick(tick -> {
            this.m_6274_().m_21952_(MemoryModuleType.f_26372_).ifPresent(this::stareAtTarget);
            if (tick == 30) {
                this.m_6504_(this.m_5448_(), 1.5f);
            }
        });
        this.actionAttackBiteStand = ActionState.of((ActionType)ADActionTypes.ATTACK_BITE, (float)1.25f, (float)0.1f, (float)0.4f).exitTime(1.1f).onStart(() -> this.m_216990_(ADSoundEvents.SCULKRAPTOR_ATTACK.get())).onTick(tick -> {
            this.m_6274_().m_21952_(MemoryModuleType.f_26372_).ifPresent(this::stareAtTarget);
            if (tick == 15) {
                this.attackActionTargetEntity(1.0f, 0.1f);
            }
        });
        this.actionDeath = ActionState.of((ActionType)ADActionTypes.DEATH, (float)1.88f, (float)0.1f, (float)0.0f).holdOnLastFrame();
        this.actionSniff = ActionState.of((ActionType)ADActionTypes.SNIFF, (float)4.75f, (float)0.2f, (float)0.6f).exitTime(4.6f);
        return Set.of(this.actionAttackSonicBoom, this.actionAttackRanged, this.actionAttackBiteStand, this.actionDeath, this.actionSniff);
    }

    @Override
    public void attackActionTargetEntity(float damage, float knockback) {
        LivingEntity target = this.getActionTargetEntity();
        if (EntityUtils.attackTarget((Mob)this, target, damage, knockback)) {
            this.setActionTargetEntity(null);
        }
    }

    public void stareAtTarget(LivingEntity target) {
        this.m_7618_(EntityAnchorArgument.Anchor.EYES, target.m_20182_());
        this.f_19812_ = true;
    }

    @Override
    public void m_7380_(CompoundTag compound) {
        super.m_7380_(compound);
        AngerManagement.m_219277_(this::canTargetEntity).encodeStart((DynamicOps)NbtOps.f_128958_, (Object)this.angerManagement).resultOrPartial(arg_0 -> ((Logger)Constants.LOG).error(arg_0)).ifPresent(tag -> compound.m_128365_(DATA_KEY_ANGER, tag));
        VibrationSystem.Data.f_279637_.encodeStart((DynamicOps)NbtOps.f_128958_, (Object)this.vibrationData).resultOrPartial(arg_0 -> ((Logger)Constants.LOG).error(arg_0)).ifPresent(tag -> compound.m_128365_(DATA_KEY_LISTENER, tag));
        if (this.m_21536_()) {
            compound.m_128405_("restriction_rad", (int)this.m_21535_());
            compound.m_128356_("restriction_center", this.m_21534_().m_121878_());
        }
    }

    @Override
    public void m_7378_(CompoundTag compound) {
        super.m_7378_(compound);
        this.m_6210_();
        if (compound.m_128441_(DATA_KEY_ANGER)) {
            AngerManagement.m_219277_(this::canTargetEntity).parse(new Dynamic((DynamicOps)NbtOps.f_128958_, (Object)compound.m_128423_(DATA_KEY_ANGER))).resultOrPartial(arg_0 -> ((Logger)Constants.LOG).error(arg_0)).ifPresent(management -> {
                this.angerManagement = management;
            });
        }
        if (compound.m_128425_(DATA_KEY_LISTENER, 10)) {
            VibrationSystem.Data.f_279637_.parse(new Dynamic((DynamicOps)NbtOps.f_128958_, (Object)compound.m_128469_(DATA_KEY_LISTENER))).resultOrPartial(arg_0 -> ((Logger)Constants.LOG).error(arg_0)).ifPresent(data -> {
                this.vibrationData = data;
            });
        }
        if (compound.m_128441_("restriction_rad")) {
            this.m_21446_(BlockPos.m_122022_((long)compound.m_128454_("restriction_center")), compound.m_128451_("restriction_rad"));
        }
    }

    @Nullable
    public LivingEntity m_5448_() {
        return this.m_6274_().m_21952_(MemoryModuleType.f_26372_).orElseGet(() -> {
            LivingEntity target = super.m_5448_();
            if (target != null) {
                this.m_6274_().m_21879_(MemoryModuleType.f_26372_, (Object)target);
            }
            return target;
        });
    }

    public void m_6710_(@Nullable LivingEntity target) {
        this.m_6274_().m_21879_(MemoryModuleType.f_26372_, (Object)target);
        this.m_6274_().m_21936_(MemoryModuleType.f_26326_);
        SculksaurSonicBoom.setCooldown((LivingEntity)this, 200);
        super.m_6710_(target);
    }

    public boolean m_7327_(Entity entity) {
        this.m_9236_().m_7605_((Entity)this, (byte)4);
        SculksaurSonicBoom.setCooldown((LivingEntity)this, 40);
        return super.m_7327_(entity);
    }

    protected BodyRotationControl m_7560_() {
        return new SmoothBodyControl((Mob)this, this.m_8085_(), 15, 20.0f, 0.13f).setCanRotate(this::canRotate);
    }

    @Override
    protected EntityLocomotion createLocomotion() {
        return EntityLocomotion.of().speed(0.02f, 0.24f).transitionLimit(0.2f).responsiveness(0.3f).build();
    }

    @Override
    protected PetComponent<?> createPet() {
        return PetComponent.of().setBabyHealthMultiplier(0.5294118f).build(this);
    }

    public AngerLevel getAngerLevel() {
        return AngerLevel.m_219227_((int)this.getActiveAnger());
    }

    private int getActiveAnger() {
        return this.angerManagement.m_219286_((Entity)this.m_5448_());
    }

    public void clearAnger(Entity entity) {
        this.angerManagement.m_219266_(entity);
    }

    public void increaseAngerAt(@Nullable Entity entity) {
        this.increaseAngerAt(entity, 35, true);
    }

    @VisibleForTesting
    public void increaseAngerAt(@Nullable Entity entity, int offset, boolean playListeningSound) {
        if (!this.m_21525_() && this.canTargetEntity(entity)) {
            boolean flag;
            if (this.isTamed()) {
                offset = 200;
            }
            boolean bl = flag = !(this.m_6274_().m_21952_(MemoryModuleType.f_26372_).orElse(null) instanceof Player);
            if (entity == null) {
                return;
            }
            int anger = this.angerManagement.m_219268_(entity, offset);
            if (entity instanceof Player && flag && AngerLevel.m_219227_((int)anger).m_219236_()) {
                this.m_6274_().m_21936_(MemoryModuleType.f_26372_);
            }
            if (playListeningSound) {
                this.playListeningSound();
            }
        }
    }

    public Optional<LivingEntity> getEntityAngryAt() {
        return this.getAngerLevel().m_219236_() ? this.angerManagement.m_219256_() : Optional.empty();
    }

    private void playListeningSound() {
    }

    @Override
    public void m_8119_() {
        Level level = this.m_9236_();
        if (level instanceof ServerLevel) {
            List<SculkraptorEntity> otherRaptors;
            ServerLevel serverlevel = (ServerLevel)level;
            VibrationSystem.Ticker.m_280259_((Level)serverlevel, (VibrationSystem.Data)this.vibrationData, (VibrationSystem.User)this.vibrationUser);
            if (!this.isTamed() && this.f_19797_ % 80 == 0 && (otherRaptors = this.getNearbyOtherRaptors((Level)serverlevel, 32.0)).size() >= 3 && serverlevel.m_45976_(SculkraptorNestEntity.class, new AABB(this.m_20182_().m_82492_(32.0, 32.0, 32.0), this.m_20182_().m_82520_(32.0, 32.0, 32.0))).isEmpty()) {
                SculkraptorNestEntity.tryPlace(serverlevel, this.m_20183_(), 20, 5, 6, SpawnUtil.Strategy.f_216413_).ifPresent(nest -> otherRaptors.forEach(sculkraptorEntity -> sculkraptorEntity.m_21446_(nest.m_20183_(), 32)));
            }
        }
        super.m_8119_();
        this.actionSequence.tick();
    }

    @Override
    protected void m_8024_() {
        ServerLevel serverlevel = (ServerLevel)this.m_9236_();
        serverlevel.m_46473_().m_6180_("sculkraptorBrain");
        this.m_6274_().m_21865_(serverlevel, (LivingEntity)this);
        serverlevel.m_46473_().m_7238_();
        super.m_8024_();
        if (this.f_19797_ % 20 == 0) {
            this.angerManagement.m_219263_(serverlevel, this::canTargetEntity);
        }
        SculkraptorAI.updateActivity(this);
    }

    protected void m_6153_() {
        if (this.f_20919_++ == 0) {
            this.playActionSynced(this.actionDeath);
        } else if (this.f_20919_ >= 38 && !this.m_9236_().m_5776_() && !this.m_213877_()) {
            this.m_142687_(Entity.RemovalReason.KILLED);
        }
    }

    @Override
    protected boolean canRotate() {
        return !this.actionDeath.isActive();
    }

    @Override
    protected AbstractComplexMob.TameableDinoSettings getSettings() {
        return ServerConfig.getEntitySettings().sculkraptor();
    }

    public int m_8085_() {
        return 45;
    }

    @Override
    public void m_7023_(Vec3 motion) {
        if (this.actionDeath.isActive() || this.actionAttackRanged.isActive() || this.actionAttackBiteStand.isActive() || this.actionSniff.isActive() || this.actionAttackSonicBoom.isActive()) {
            motion = motion.m_82542_(0.0, 1.0, 0.0);
        }
        super.m_7023_(motion);
    }

    protected void m_7324_(Entity entity) {
        if (!this.m_21525_() && !this.m_6274_().m_21874_(MemoryModuleType.f_217773_)) {
            this.m_6274_().m_21882_(MemoryModuleType.f_217773_, (Object)Unit.INSTANCE, 20L);
            this.increaseAngerAt(entity);
            SculkraptorAI.setDisturbanceLocation(this, entity.m_20183_());
        }
        super.m_7324_(entity);
    }

    @Override
    @Nullable
    public AgeableMob m_142606_(ServerLevel level, AgeableMob otherParent) {
        return null;
    }

    public double m_142593_(LivingEntity target) {
        return this.m_20205_() * 1.5f * this.m_20205_() * 1.5f + target.m_20205_();
    }

    public int m_21529_() {
        return 3;
    }

    protected SoundEvent m_7515_() {
        return ADSoundEvents.SCULKRAPTOR_IDLE.get();
    }

    protected SoundEvent m_7975_(DamageSource damageSource) {
        return ADSoundEvents.SCULKRAPTOR_HURT.get();
    }

    protected SoundEvent m_5592_() {
        return ADSoundEvents.SCULKRAPTOR_DEATH.get();
    }

    public boolean m_213854_() {
        return true;
    }

    public boolean isReadyToScream() {
        return this.m_6274_().m_21876_(ADMemoryModuleTypes.IS_READY_TO_SCREAM.get(), MemoryStatus.VALUE_PRESENT);
    }

    public void setReadyToScream(boolean readyToScream) {
        if (readyToScream) {
            this.m_6274_().m_21879_(ADMemoryModuleTypes.IS_READY_TO_SCREAM.get(), (Object)Unit.INSTANCE);
        } else {
            this.m_6274_().m_21936_(ADMemoryModuleTypes.IS_READY_TO_SCREAM.get());
        }
    }

    public float m_5610_(BlockPos pos, LevelReader level) {
        return level.m_8055_(pos.m_7495_()).m_204336_(ADTags.Blocks.SCULKRAPTOR_VALID_SPAWN) ? 10.0f : level.m_220419_(pos);
    }

    public void m_213651_(BiConsumer<DynamicGameEventListener<?>, ServerLevel> listenerConsumer) {
        Level level = this.m_9236_();
        if (level instanceof ServerLevel) {
            ServerLevel serverlevel = (ServerLevel)level;
            listenerConsumer.accept(this.dynamicGameEventListener, serverlevel);
        }
    }

    public boolean canTargetEntity(@Nullable Entity entity) {
        if (entity instanceof LivingEntity) {
            LivingEntity livingentity = (LivingEntity)entity;
            return this.m_9236_() == entity.m_9236_() && EntitySelector.f_20406_.test(entity) && !this.m_7307_(entity) && livingentity.m_6095_() != EntityType.f_20529_ && livingentity.m_6095_() != ADEntityTypes.SCULKRAPTOR.get() && !livingentity.m_20147_() && !livingentity.m_21224_() && this.m_9236_().m_6857_().m_61935_(livingentity.m_20191_());
        }
        return false;
    }

    @Override
    public boolean isSitting() {
        return false;
    }

    @Override
    public void setSitting(boolean sitting) {
    }

    @Override
    public boolean m_6469_(DamageSource source, float amount) {
        boolean supra = super.m_6469_(source, amount);
        if (supra) {
            if (this.isTamed()) {
                if (source.m_7640_() == null || source.m_7640_().m_20148_() != this.m_21805_()) {
                    SculkraptorEntity sculkraptorEntity;
                    Entity entity = source.m_7640_();
                    if (entity instanceof LivingEntity) {
                        LivingEntity living = (LivingEntity)entity;
                        sculkraptorEntity = living;
                    } else {
                        sculkraptorEntity = this;
                    }
                    Path path = SculkraptorFlee.createPath(this, (LivingEntity)sculkraptorEntity);
                    if (path != null) {
                        this.f_20939_.m_21882_(ADMemoryModuleTypes.FLEE_PATH.get(), (Object)path, 80L);
                    }
                }
            } else {
                this.increaseAngerAt(source.m_7640_(), 200, true);
            }
        }
        return supra;
    }

    public void m_6504_(@Nullable LivingEntity target, float velocity) {
        double dirZ;
        double dirY;
        double dirX;
        SculkraptorSpitEntity spitEntity = new SculkraptorSpitEntity(this.m_9236_(), (LivingEntity)this);
        spitEntity.m_6034_(this.m_20185_() - (double)(this.m_20205_() + 1.0f) * 0.5 * (double)Mth.m_14031_((float)(this.f_20883_ * ((float)Math.PI / 180))), this.m_20188_() - (double)0.1f, this.m_20189_() + (double)(this.m_20205_() + 1.0f) * 0.5 * (double)Mth.m_14089_((float)(this.f_20883_ * ((float)Math.PI / 180))));
        if (target != null) {
            dirX = target.m_20185_() - this.m_20185_();
            dirY = target.m_20227_(0.3333333333333333) - spitEntity.m_20186_();
            dirZ = target.m_20189_() - this.m_20189_();
        } else {
            Vec3 view = EntityUtils.getViewVector((LivingEntity)this);
            dirX = view.f_82479_;
            dirY = view.f_82480_;
            dirZ = view.f_82481_;
        }
        double scaled = Math.sqrt(dirX * dirX + dirZ * dirZ) * (double)0.2f;
        spitEntity.m_6686_(dirX, dirY + scaled, dirZ, velocity, 10.0f);
        this.m_9236_().m_7967_((Entity)spitEntity);
        if (target != null && (double)target.m_20270_((Entity)this) <= 1.0) {
            spitEntity.m_5790_(new EntityHitResult((Entity)target, spitEntity.m_20182_()));
            this.m_9236_().m_214171_(GameEvent.f_157777_, spitEntity.m_20182_(), GameEvent.Context.m_223719_((Entity)this, null));
        }
    }

    public List<SculkraptorEntity> getNearbyOtherRaptors(Level level, double range) {
        Vec3 center = this.m_20182_();
        return level.m_6443_(SculkraptorEntity.class, new AABB(center.m_82492_(range, range, range), center.m_82520_(range, range, range)), raptor -> raptor != this && !raptor.m_21224_() && raptor.m_20280_((Entity)this) < range * range && raptor.m_21805_() == this.m_21805_());
    }

    class VibrationUser
    implements VibrationSystem.User {
        private static final int GAME_EVENT_LISTENER_RANGE = 16;
        private final PositionSource positionSource;

        VibrationUser() {
            this.positionSource = new EntityPositionSource((Entity)SculkraptorEntity.this, SculkraptorEntity.this.m_20192_());
        }

        public int m_280351_() {
            return 16;
        }

        public PositionSource m_280010_() {
            return this.positionSource;
        }

        public TagKey<GameEvent> m_280028_() {
            return SculkraptorEntity.this.isTamed() ? ADTags.GameEvents.SCULKRAPTOR_TAMED_CAN_LISTEN : ADTags.GameEvents.SCULKRAPTOR_WILD_CAN_LISTEN;
        }

        public boolean m_280076_() {
            return true;
        }

        public boolean m_280080_(ServerLevel level, BlockPos pos, GameEvent event, GameEvent.Context context) {
            if (!SculkraptorEntity.this.m_21525_() && !SculkraptorEntity.this.m_21224_() && level.m_6857_().m_61937_(pos)) {
                if (SculkraptorEntity.this.m_6274_().m_21874_(MemoryModuleType.f_217774_) && !event.m_204528_(ADTags.GameEvents.SCULKRAPTOR_IGNORE_COOLDOWN)) {
                    return false;
                }
                Entity entity = context.f_223711_();
                if (entity instanceof LivingEntity) {
                    LivingEntity living = (LivingEntity)entity;
                    return SculkraptorEntity.this.canTargetEntity((Entity)living) && SculkraptorEntity.this.m_5448_() != living;
                }
                return true;
            }
            return false;
        }

        public void m_280271_(ServerLevel level, BlockPos pos, GameEvent gameEvent, @Nullable Entity entity, @Nullable Entity playerEntity, float distance) {
            if (!SculkraptorEntity.this.m_21224_()) {
                if (gameEvent == ADGameEvents.SCULKRAPTOR_NEST_AGGRO.get()) {
                    SculkraptorEntity.this.increaseAngerAt(playerEntity, 200, true);
                    SculkraptorEntity.this.m_5496_(ADSoundEvents.SCULKRAPTOR_TENDRIL_CLICKS.get(), 1.0f, SculkraptorEntity.this.m_6100_());
                    SculkraptorEntity.this.f_20939_.m_21882_(MemoryModuleType.f_217774_, (Object)Unit.INSTANCE, 40L);
                    return;
                }
                SculkraptorEntity.this.lastGameEvent = gameEvent;
                Vec3 loc = SculkraptorEntity.this.m_20182_();
                Entity target = playerEntity == null ? entity : playerEntity;
                int i = 0;
                for (SculkraptorEntity other : SculkraptorEntity.this.getNearbyOtherRaptors((Level)level, 32.0)) {
                    VibrationSystem.Data data;
                    ++i;
                    if (target != null) {
                        int that;
                        int max;
                        int dis = SculkraptorEntity.this.angerManagement.m_219286_(target);
                        if (dis < (max = Math.max(dis, that = other.angerManagement.m_219286_(target)))) {
                            SculkraptorEntity.this.angerManagement.m_219268_(target, max - dis);
                        }
                        if (that < max) {
                            other.angerManagement.m_219268_(target, max - that);
                        }
                    }
                    if (other.lastGameEvent == gameEvent || (data = other.m_280002_()).m_280602_() != null && data.m_280602_().f_243709_() == gameEvent) continue;
                    VibrationSystem.User user = other.m_280445_();
                    VibrationInfo info = new VibrationInfo(gameEvent, (float)loc.m_82554_(other.m_20182_()), loc, entity);
                    data.m_280036_(info);
                    Vec3 vec3 = info.f_243906_();
                    data.m_280178_(user.m_280576_(info.f_243776_()));
                    level.m_8767_((ParticleOptions)new VibrationParticleOption(user.m_280010_(), data.m_280274_()), vec3.f_82479_, vec3.f_82480_, vec3.f_82481_, 1, 0.0, 0.0, 0.0, 0.0);
                    user.m_280022_();
                    data.m_280457_().m_246080_();
                }
                SculkraptorEntity.this.m_5496_(ADSoundEvents.SCULKRAPTOR_TENDRIL_CLICKS.get(), 1.0f, SculkraptorEntity.this.m_6100_());
                if (i < 3 && target instanceof Player) {
                    Player player = (Player)target;
                    Path path = SculkraptorFlee.createPath(SculkraptorEntity.this, (LivingEntity)player);
                    if (path != null) {
                        SculkraptorEntity.this.f_20939_.m_21882_(ADMemoryModuleTypes.FLEE_PATH.get(), (Object)path, 80L - (long)i * 20L);
                    }
                    SculkraptorEntity.this.f_20939_.m_21882_(MemoryModuleType.f_217774_, (Object)Unit.INSTANCE, 10L);
                    return;
                }
                SculkraptorEntity.this.f_20939_.m_21882_(MemoryModuleType.f_217774_, (Object)Unit.INSTANCE, 40L);
                if (playerEntity != null) {
                    if (SculkraptorEntity.this.m_19950_(playerEntity, 30.0)) {
                        if (SculkraptorEntity.this.m_6274_().m_21874_(MemoryModuleType.f_217784_)) {
                            if (SculkraptorEntity.this.canTargetEntity(playerEntity)) {
                                pos = playerEntity.m_20183_();
                            }
                            SculkraptorEntity.this.increaseAngerAt(playerEntity);
                        } else {
                            SculkraptorEntity.this.increaseAngerAt(playerEntity, 10, true);
                        }
                    }
                    SculkraptorEntity.this.m_6274_().m_21882_(MemoryModuleType.f_217784_, (Object)Unit.INSTANCE, 100L);
                } else {
                    SculkraptorEntity.this.increaseAngerAt(entity);
                }
                if (!SculkraptorEntity.this.getAngerLevel().m_219236_()) {
                    Optional optional = SculkraptorEntity.this.angerManagement.m_219256_();
                    if (playerEntity != null || optional.isEmpty() || optional.get() == entity) {
                        SculkraptorAI.setDisturbanceLocation(SculkraptorEntity.this, pos);
                    }
                }
            }
        }
    }
}

