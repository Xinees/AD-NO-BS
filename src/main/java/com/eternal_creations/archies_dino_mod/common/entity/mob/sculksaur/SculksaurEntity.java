/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.astemir.astemirlib.re.common.action.ActionSequence
 *  com.astemir.astemirlib.re.common.action.ActionState
 *  com.astemir.astemirlib.re.common.action.ActionType
 *  com.astemir.astemirlib.re.common.action.SyncedActionHolder
 *  com.astemir.astemirlib.re.common.world.entity.EntityLocomotion
 *  com.astemir.astemirlib.re.core.math.AMath
 *  com.astemir.astemirlib.re.core.math.Easing
 *  com.astemir.astemirlib.re.core.math.Easing$Simple
 *  com.astemir.astemirlib.re.core.math.tween.TweenFloat
 *  com.google.common.annotations.VisibleForTesting
 *  com.mojang.serialization.Dynamic
 *  com.mojang.serialization.DynamicOps
 *  javax.annotation.Nullable
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.advancements.CriteriaTriggers
 *  net.minecraft.commands.arguments.EntityAnchorArgument$Anchor
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.NbtOps
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.syncher.EntityDataAccessor
 *  net.minecraft.network.syncher.EntityDataSerializer
 *  net.minecraft.network.syncher.EntityDataSerializers
 *  net.minecraft.network.syncher.SynchedEntityData
 *  net.minecraft.server.level.ServerBossEvent
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.tags.BlockTags
 *  net.minecraft.tags.GameEventTags
 *  net.minecraft.tags.TagKey
 *  net.minecraft.util.Mth
 *  net.minecraft.util.Unit
 *  net.minecraft.world.BossEvent$BossBarColor
 *  net.minecraft.world.BossEvent$BossBarOverlay
 *  net.minecraft.world.DifficultyInstance
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.damagesource.DamageTypes
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.Entity$RemovalReason
 *  net.minecraft.world.entity.EntityDimensions
 *  net.minecraft.world.entity.EntitySelector
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.MobSpawnType
 *  net.minecraft.world.entity.Pose
 *  net.minecraft.world.entity.SpawnGroupData
 *  net.minecraft.world.entity.ai.Brain
 *  net.minecraft.world.entity.ai.behavior.EntityTracker
 *  net.minecraft.world.entity.ai.behavior.PositionTracker
 *  net.minecraft.world.entity.ai.control.BodyRotationControl
 *  net.minecraft.world.entity.ai.memory.MemoryModuleType
 *  net.minecraft.world.entity.ai.memory.MemoryStatus
 *  net.minecraft.world.entity.ai.navigation.PathNavigation
 *  net.minecraft.world.entity.item.ItemEntity
 *  net.minecraft.world.entity.monster.Monster
 *  net.minecraft.world.entity.monster.warden.AngerLevel
 *  net.minecraft.world.entity.monster.warden.AngerManagement
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.GameRules
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.ServerLevelAccessor
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.gameevent.DynamicGameEventListener
 *  net.minecraft.world.level.gameevent.EntityPositionSource
 *  net.minecraft.world.level.gameevent.GameEvent
 *  net.minecraft.world.level.gameevent.GameEvent$Context
 *  net.minecraft.world.level.gameevent.GameEventListener
 *  net.minecraft.world.level.gameevent.PositionSource
 *  net.minecraft.world.level.gameevent.vibrations.VibrationSystem
 *  net.minecraft.world.level.gameevent.vibrations.VibrationSystem$Data
 *  net.minecraft.world.level.gameevent.vibrations.VibrationSystem$Listener
 *  net.minecraft.world.level.gameevent.vibrations.VibrationSystem$Ticker
 *  net.minecraft.world.level.gameevent.vibrations.VibrationSystem$User
 *  net.minecraft.world.level.pathfinder.BlockPathTypes
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 *  org.slf4j.Logger
 */
package com.eternal_creations.archies_dino_mod.common.entity.mob.sculksaur;

import com.astemir.astemirlib.re.common.action.ActionSequence;
import com.astemir.astemirlib.re.common.action.ActionState;
import com.astemir.astemirlib.re.common.action.ActionType;
import com.astemir.astemirlib.re.common.action.SyncedActionHolder;
import com.astemir.astemirlib.re.common.world.entity.EntityLocomotion;
import com.astemir.astemirlib.re.core.math.AMath;
import com.astemir.astemirlib.re.core.math.Easing;
import com.astemir.astemirlib.re.core.math.tween.TweenFloat;
import com.eternal_creations.archies_dino_mod.Constants;
import com.eternal_creations.archies_dino_mod.common.ADTags;
import com.eternal_creations.archies_dino_mod.common.entity.ADActionTypes;
import com.eternal_creations.archies_dino_mod.common.entity.ADEntityTypes;
import com.eternal_creations.archies_dino_mod.common.entity.ADMemoryModuleTypes;
import com.eternal_creations.archies_dino_mod.common.entity.ai.control.SmoothBodyControl;
import com.eternal_creations.archies_dino_mod.common.entity.ai.control.SmoothLookControl;
import com.eternal_creations.archies_dino_mod.common.entity.ai.control.SmoothMoveControl;
import com.eternal_creations.archies_dino_mod.common.entity.base.AbstractComplexMonster;
import com.eternal_creations.archies_dino_mod.common.entity.mob.sculksaur.ai.SculksaurAI;
import com.eternal_creations.archies_dino_mod.common.entity.mob.sculksaur.ai.SculksaurPathNavigation;
import com.eternal_creations.archies_dino_mod.common.entity.mob.sculksaur.ai.SculksaurSonicBoom;
import com.eternal_creations.archies_dino_mod.common.entity.utils.EntityUtils;
import com.eternal_creations.archies_dino_mod.common.sound.ADSoundEvents;
import com.eternal_creations.archies_dino_mod.server.ServerConfig;
import com.eternal_creations.archies_dino_mod.util.CommonUtil;
import com.eternal_creations.archies_dino_mod.util.handlers.SculksaurHandler;
import com.google.common.annotations.VisibleForTesting;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.GameEventTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.Unit;
import net.minecraft.world.BossEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.behavior.PositionTracker;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.warden.AngerLevel;
import net.minecraft.world.entity.monster.warden.AngerManagement;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.DynamicGameEventListener;
import net.minecraft.world.level.gameevent.EntityPositionSource;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.gameevent.PositionSource;
import net.minecraft.world.level.gameevent.vibrations.VibrationSystem;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.slf4j.Logger;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class SculksaurEntity
extends AbstractComplexMonster
implements VibrationSystem {
    private static final String DATA_KEY_ANGER = "anger";
    private static final String DATA_KEY_LISTENER = "listener";
    public static final int MAX_ANGER = 150;
    private static final int DEFAULT_ANGER_INCREASE = 80;
    private static final int SHOT_ANGER_INCREASE = 15;
    private static final float DEFAULT_ATTACK_DAMAGE = 8.0f;
    private static final float BITE_MULTIPLIER = 1.75f;
    private static final float TAIL_MULTIPLIER = 1.5f;
    public static final float EXPLOSION_MULTIPLIER = 2.5f;
    public static final EntityDataAccessor<Byte> DATA_WARTS = SynchedEntityData.m_135353_(SculksaurEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135027_);
    public static final SculksaurHandler.SculksaurSettings DEFAULT_STATS = new SculksaurHandler.SculksaurSettings(true, true, 18000, 600, new AbstractComplexMonster.WildDinoSettings(true, true, new ServerConfig.EntityStats(0.4, 500.0, 1.0, 2.0, 48.0, 8.0)));
    public static final WartGroup WARTS_1 = new WartGroup(1, 2, 3);
    public static final WartGroup WARTS_2 = new WartGroup(4, 5);
    public static final WartGroup WARTS_3 = new WartGroup(6, 7);
    public static final WartGroup WARTS_4 = new WartGroup(8, 9, 10);
    public static final WartGroup WARTS_5 = new WartGroup(11, 12);
    public static final WartGroup WARTS_6 = new WartGroup(13, 14);
    public ActionState actionSpawn;
    public ActionState actionDeath;
    public ActionState actionSense;
    public ActionState actionListen;
    public ActionState actionRoar;
    public ActionState actionAttackSonicBoom;
    public ActionState actionAttackBiteRun;
    public ActionState actionAttackBiteStand;
    public ActionState actionAttackTail;
    private final ActionSequence actionSequence;
    public TweenFloat shadowRadius = TweenFloat.of((float)2.0f);
    private final DynamicGameEventListener<VibrationSystem.Listener> dynamicGameEventListener;
    private final VibrationSystem.User vibrationUser;
    private VibrationSystem.Data vibrationData;
    private AngerManagement angerManagement = new AngerManagement(this::canTargetEntity, Collections.emptyList());
    private final ServerBossEvent bossEvent = (ServerBossEvent)new ServerBossEvent(this.m_5446_(), BossEvent.BossBarColor.PURPLE, BossEvent.BossBarOverlay.PROGRESS).m_7003_(true);

    public SculksaurEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        this.actionSequence = new ActionSequence((SyncedActionHolder)this);
        this.f_21342_ = new SmoothMoveControl((Mob)this, 0.15f);
        this.f_21365_ = new SmoothLookControl((Mob)this, 15.0f, 0.3).setCanRotate(this::canRotate);
        this.vibrationUser = new VibrationUser();
        this.vibrationData = new VibrationSystem.Data();
        this.dynamicGameEventListener = new DynamicGameEventListener((GameEventListener)new VibrationSystem.Listener((VibrationSystem)this));
        this.m_274367_(1.5f);
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
        return SculksaurAI.makeBrain(this, dynamic);
    }

    public Brain<SculksaurEntity> m_6274_() {
        return super.m_6274_();
    }

    @Override
    protected PathNavigation m_6037_(Level level) {
        return new SculksaurPathNavigation((Mob)this, level, this::isEnraged);
    }

    public VibrationSystem.Data m_280002_() {
        return this.vibrationData;
    }

    public VibrationSystem.User m_280445_() {
        return this.vibrationUser;
    }

    protected float m_6121_() {
        return 4.0f;
    }

    public boolean m_213824_() {
        return true;
    }

    public float m_5610_(BlockPos pos, LevelReader level) {
        return 0.0f;
    }

    public AABB m_6921_() {
        return super.m_6921_().m_82400_(2.0);
    }

    @Override
    protected Set<ActionState> createActionSet() {
        this.actionAttackSonicBoom = ActionState.of((ActionType)ADActionTypes.ATTACK_SONIC_BOOM, (float)7.76f, (float)0.4f, (float)0.8f).exitTime(7.0f).onStart(() -> this.m_5496_(ADSoundEvents.SCULKSAUR_EXPLOSION.get(), 5.0f, 1.0f));
        this.actionAttackBiteRun = ActionState.of((ActionType)ADActionTypes.ATTACK_BITE_RUN, (float)0.5f, (float)0.2f, (float)0.6f).onStart(() -> this.m_216990_(ADSoundEvents.SCULKSAUR_BITE.get())).onTick(tick -> {
            this.m_6274_().m_21952_(MemoryModuleType.f_26372_).ifPresent(this::stareAtTarget);
            if (tick == 8) {
                this.attackActionTargetEntity(1.75f, 0.1f);
            }
        });
        this.actionAttackTail = ActionState.of((ActionType)ADActionTypes.ATTACK_TAIL, (float)2.0f, (float)0.4f, (float)0.4f).exitTime(1.9f).onStart(() -> this.m_216990_(ADSoundEvents.SCULKSAUR_ATTACK_TAIL.get())).onTick(tick -> {
            this.m_6274_().m_21952_(MemoryModuleType.f_26372_).ifPresent(this::stareAtTarget);
            if (tick == 18) {
                EntityUtils.attackForward((Mob)this, 4.0f, 0.5f, 1.0f, 1.5f, target -> {});
            }
        });
        this.actionAttackBiteStand = ActionState.of((ActionType)ADActionTypes.ATTACK_BITE, (float)1.375f, (float)0.4f, (float)0.4f).exitTime(1.2f).onStart(() -> this.m_216990_(ADSoundEvents.SCULKSAUR_ATTACK.get())).onTick(tick -> {
            this.m_6274_().m_21952_(MemoryModuleType.f_26372_).ifPresent(this::stareAtTarget);
            if (tick == 8) {
                this.attackActionTargetEntity(1.75f, 0.1f);
            }
        });
        this.actionRoar = ActionState.of((ActionType)ADActionTypes.ROAR, (float)5.72f, (float)0.2f, (float)0.8f).onTick(tick -> this.m_6274_().m_21952_(MemoryModuleType.f_217782_).ifPresent(this::stareAtTarget)).exitTime(5.0f).onStart(() -> this.m_5496_(ADSoundEvents.SCULKSAUR_ROAR.get(), 5.0f, this.m_6100_()));
        this.actionDeath = ActionState.of((ActionType)ADActionTypes.DEATH, (float)6.6f, (float)0.4f, (float)0.0f).holdOnLastFrame();
        this.actionSpawn = ActionState.of((ActionType)ADActionTypes.SPAWN, (float)11.04f, (float)0.0f, (float)0.8f).exitTime(10.5f).onStart(() -> this.m_5496_(ADSoundEvents.SCULKSAUR_SPAWN.get(), 15.0f, 1.0f)).onTick(tick -> {
            if (tick == 1) {
                this.m_6842_(false);
            }
        });
        this.actionSense = ActionState.of((ActionType)ADActionTypes.SENSE, (float)0.64f, (float)0.2f, (float)0.4f).forcePlay().onStart(() -> this.m_5496_(ADSoundEvents.SCULKSAUR_TENDRIL_CLICKS.get(), 5.0f, this.m_6100_()));
        this.actionListen = ActionState.of((ActionType)ADActionTypes.LISTEN, (float)6.72f, (float)0.4f, (float)0.8f).exitTime(6.2f).onStart(() -> this.m_5496_(ADSoundEvents.SCULKSAUR_SNIFF.get(), 5.0f, 1.0f)).onTick(i -> {
            if (i >= 30 && i <= 100 && i % 10 == 0) {
                this.m_5496_(ADSoundEvents.SCULKSAUR_TENDRIL_CLICKS.get(), 5.0f, 1.0f);
            }
        });
        return Set.of(this.actionAttackSonicBoom, this.actionAttackBiteRun, this.actionAttackTail, this.actionAttackBiteStand, this.actionRoar, this.actionDeath, this.actionListen, this.actionSpawn, this.actionSense);
    }

    @Override
    public void attackActionTargetEntity(float damage, float knockback) {
        LivingEntity target = this.getActionTargetEntity();
        if (target != null && EntityUtils.attackTarget((Mob)this, target, target.m_6095_().m_204039_(ADTags.EntityTypes.SCULK) ? damage * 5.0f : damage, knockback)) {
            this.setActionTargetEntity(null);
        }
    }

    public void stareAtTarget(LivingEntity target) {
        this.m_7618_(EntityAnchorArgument.Anchor.EYES, target.m_20182_());
        this.f_19812_ = true;
    }

    @Override
    protected void m_8097_() {
        super.m_8097_();
        this.f_19804_.m_135372_(DATA_WARTS, (Object)-1);
    }

    public static WartGroup getWartGroup(int index) {
        return switch (index) {
            case 0 -> WARTS_1;
            case 1 -> WARTS_2;
            case 2 -> WARTS_3;
            case 3 -> WARTS_4;
            case 4 -> WARTS_5;
            default -> WARTS_6;
        };
    }

    public boolean hasWartGroup(int index) {
        if (index < 0 || index > 5) {
            return false;
        }
        return ((Byte)this.f_19804_.m_135370_(DATA_WARTS) & 1 << index) != 0;
    }

    public void setWartGroup(int index, boolean value) {
        if (index < 0 || index > 5) {
            return;
        }
        if (value) {
            this.f_19804_.m_135381_(DATA_WARTS, (Object)((byte)((Byte)this.f_19804_.m_135370_(DATA_WARTS) | 1 << index)));
        } else {
            this.f_19804_.m_135381_(DATA_WARTS, (Object)((byte)((Byte)this.f_19804_.m_135370_(DATA_WARTS) & ~(1 << index))));
        }
    }

    public void m_7380_(CompoundTag compound) {
        super.m_7380_(compound);
        compound.m_128344_("warts", ((Byte)this.f_19804_.m_135370_(DATA_WARTS)).byteValue());
        AngerManagement.m_219277_(this::canTargetEntity).encodeStart((DynamicOps)NbtOps.f_128958_, (Object)this.angerManagement).resultOrPartial(arg_0 -> ((Logger)Constants.LOG).error(arg_0)).ifPresent(tag -> compound.m_128365_(DATA_KEY_ANGER, tag));
        VibrationSystem.Data.f_279637_.encodeStart((DynamicOps)NbtOps.f_128958_, (Object)this.vibrationData).resultOrPartial(arg_0 -> ((Logger)Constants.LOG).error(arg_0)).ifPresent(tag -> compound.m_128365_(DATA_KEY_LISTENER, tag));
    }

    public void m_7378_(CompoundTag compound) {
        super.m_7378_(compound);
        this.m_6210_();
        if (compound.m_128441_("warts")) {
            this.f_19804_.m_135381_(DATA_WARTS, (Object)compound.m_128445_("warts"));
        }
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
        if (this.m_8077_()) {
            this.bossEvent.m_6456_(this.m_5446_());
        }
    }

    public void m_6593_(@Nullable Component name) {
        super.m_6593_(name);
        this.bossEvent.m_6456_(this.m_5446_());
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
        Brain<SculksaurEntity> brain = this.m_6274_();
        brain.m_21936_(MemoryModuleType.f_217782_);
        brain.m_21936_(MemoryModuleType.f_26326_);
        if (target != null) {
            brain.m_21879_(MemoryModuleType.f_26372_, (Object)target);
        } else {
            brain.m_21936_(MemoryModuleType.f_26372_);
        }
        SculksaurSonicBoom.setCooldown((LivingEntity)this, 200);
        super.m_6710_(target);
    }

    public boolean m_7327_(Entity entity) {
        this.m_9236_().m_7605_((Entity)this, (byte)4);
        SculksaurSonicBoom.setCooldown((LivingEntity)this, 120);
        return super.m_7327_(entity);
    }

    public boolean m_6469_(DamageSource source, float amount) {
        boolean supra;
        if (source.m_276093_(DamageTypes.f_268612_)) {
            Level level = this.m_9236_();
            if (level instanceof ServerLevel) {
                ServerLevel serverLevel = (ServerLevel)level;
                CommonUtil.checkWalls(serverLevel, this.m_20191_(), this.m_20069_());
            }
            return false;
        }
        int scalar = 20;
        for (int i = 0; i < 6; ++i) {
            if (!this.hasWartGroup(i)) continue;
            scalar -= 3;
        }
        amount *= (float)scalar / 20.0f;
        if (!this.isEnraged() && this.m_5448_() != null && source.m_269533_(ADTags.DamageTypes.HURTS_COMBAT_SCULKSAUR_EXTRA)) {
            amount *= 5.0f;
        }
        if (supra = super.m_6469_(source, amount)) {
            if (this.isEnraged() && amount > 2.0f) {
                this.setEnraged(false);
            }
            this.increaseAngerAt(source.m_7640_(), 150, true);
        }
        return supra;
    }

    public boolean m_6785_(double distanceToClosestPlayer) {
        return false;
    }

    protected BodyRotationControl m_7560_() {
        return new SmoothBodyControl((Mob)this, 35.0f, 15, 10.0f, 0.07f).setCanRotate(this::canRotate);
    }

    @Override
    protected EntityLocomotion createLocomotion() {
        return EntityLocomotion.of().speed(0.02f, 0.4f).turnFactor(0.003f).transitionLimit(0.3f).responsiveness(0.4f).build();
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
        this.increaseAngerAt(entity, 80, true);
    }

    @VisibleForTesting
    public void increaseAngerAt(@Nullable Entity entity, int offset, boolean playListeningSound) {
        if (!this.m_21525_() && this.canTargetEntity(entity)) {
            boolean currentTargetNotPlayer;
            boolean bl = currentTargetNotPlayer = !(this.m_6274_().m_21952_(MemoryModuleType.f_26372_).orElse(null) instanceof Player);
            if (entity == null) {
                return;
            }
            int anger = this.angerManagement.m_219268_(entity, offset);
            if (entity instanceof Player && currentTargetNotPlayer && AngerLevel.m_219227_((int)anger).m_219236_()) {
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
        this.m_5496_(this.getAngerLevel().m_219235_(), 10.0f, this.m_6100_());
    }

    @Override
    public void m_8119_() {
        Level level = this.m_9236_();
        if (level instanceof ServerLevel) {
            ServerLevel serverlevel = (ServerLevel)level;
            VibrationSystem.Ticker.m_280259_((Level)serverlevel, (VibrationSystem.Data)this.vibrationData, (VibrationSystem.User)this.vibrationUser);
        }
        super.m_8119_();
        this.shadowRadius.tick();
        this.actionSequence.tick();
        if (this.isEmerging()) {
            float yBodyRot = this.f_20883_;
            float normalizedRot = (yBodyRot % 360.0f + 360.0f) % 360.0f;
            float targetRot = (float)Math.round(normalizedRot / 90.0f) * 90.0f;
            if (targetRot >= 360.0f) {
                targetRot -= 360.0f;
            }
            this.f_20883_ = AMath.lerpAngle((float)0.1f, (float)yBodyRot, (float)targetRot);
            this.shadowRadius.setTarget(0.0f, 0.1f, (Easing)Easing.Simple.SINE_IN);
        } else {
            this.shadowRadius.setTarget(2.0f, 0.1f, (Easing)Easing.Simple.SINE_OUT);
        }
        if (!level.f_46443_) {
            this.bossEvent.m_142711_(this.m_21223_() / this.m_21233_());
        }
    }

    @Override
    protected AbstractComplexMonster.WildDinoSettings getSettings() {
        return ServerConfig.getEntitySettings().sculksaur().settings();
    }

    public boolean m_6549_(EntityType<?> type) {
        if (type == EntityType.f_20532_ && !this.getSettings().canTargetPlayers()) {
            return false;
        }
        return super.m_6549_(type);
    }

    protected void m_8024_() {
        this.bossEvent.m_8321_(ServerConfig.getEntitySettings().sculksaur().isBoss());
        ServerLevel serverlevel = (ServerLevel)this.m_9236_();
        serverlevel.m_46473_().m_6180_("sculksaurBrain");
        this.m_6274_().m_21865_(serverlevel, (LivingEntity)this);
        serverlevel.m_46473_().m_7238_();
        super.m_8024_();
        if (this.f_19797_ % 20 == 0) {
            LivingEntity target = this.m_5448_();
            if (target != null && !this.m_6779_(target)) {
                target = null;
                this.m_6710_(null);
            }
            if (target != null) {
                this.angerManagement.m_219268_((Entity)target, 5);
            }
            this.angerManagement.m_219263_(serverlevel, this::canTargetEntity);
        }
        SculksaurAI.updateActivity(this);
    }

    public boolean m_6779_(LivingEntity target) {
        if (target.m_213877_() || target.m_21224_()) {
            return false;
        }
        return super.m_6779_(target);
    }

    public void m_6457_(ServerPlayer serverPlayer) {
        super.m_6457_(serverPlayer);
        this.bossEvent.m_6543_(serverPlayer);
    }

    public void m_6452_(ServerPlayer serverPlayer) {
        super.m_6452_(serverPlayer);
        this.bossEvent.m_6539_(serverPlayer);
    }

    protected void m_6153_() {
        if (this.f_20919_++ == 0) {
            this.playActionSynced(this.actionDeath);
        } else if (this.f_20919_ >= 132 && !this.m_9236_().m_5776_() && !this.m_213877_()) {
            this.m_142687_(Entity.RemovalReason.KILLED);
        }
    }

    @Override
    public float getAttackRange() {
        return 3.0f;
    }

    @Override
    protected boolean canRotate() {
        return !this.actionSpawn.isActive() && !this.actionDeath.isActive() && !this.actionAttackTail.isActive() && !this.actionAttackBiteStand.isActive() && !this.isEmerging();
    }

    @Override
    protected boolean isMovementBlocked() {
        return this.actionSpawn.isActive() || this.actionDeath.isActive() || this.actionAttackTail.isActive() || this.actionAttackBiteStand.isActive() || this.actionRoar.isActive() || this.actionListen.isActive() || this.actionAttackSonicBoom.isActive() || this.isEmerging();
    }

    @Override
    public void m_7023_(Vec3 motion) {
        if (this.isEnraged()) {
            this.m_6274_().m_21952_(MemoryModuleType.f_26370_).ifPresent(target -> {
                EntityTracker tracker;
                PositionTracker patt22255$temp = target.m_26420_();
                if (patt22255$temp instanceof EntityTracker && (tracker = (EntityTracker)patt22255$temp).m_147481_() == this.m_5448_()) {
                    this.checkWalls();
                }
            });
        }
        super.m_7023_(motion);
    }

    public void m_147240_(double strength, double x, double z) {
        if (this.isEmerging()) {
            return;
        }
        super.m_147240_(strength, x, z);
    }

    public boolean m_6094_() {
        return !this.isEmerging() && super.m_6094_();
    }

    public boolean m_6128_() {
        return this.isEmerging();
    }

    protected void m_7324_(Entity entity) {
        if (!this.m_21525_() && !this.m_6274_().m_21874_(MemoryModuleType.f_217773_)) {
            this.m_6274_().m_21882_(MemoryModuleType.f_217773_, (Object)Unit.INSTANCE, 20L);
            this.increaseAngerAt(entity, 150, true);
            SculksaurAI.setDisturbanceLocation(this, entity.m_20183_());
        }
        super.m_7324_(entity);
    }

    public void m_7334_(Entity entity) {
        if (!this.m_21525_() && !this.m_6274_().m_21874_(MemoryModuleType.f_217773_)) {
            this.m_6274_().m_21882_(MemoryModuleType.f_217773_, (Object)Unit.INSTANCE, 20L);
            this.increaseAngerAt(entity, 150, true);
            SculksaurAI.setDisturbanceLocation(this, entity.m_20183_());
        }
        super.m_7334_(entity);
    }

    @Nullable
    public SpawnGroupData m_6518_(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag dataTag) {
        if (reason == MobSpawnType.TRIGGERED) {
            this.m_6842_(true);
            this.m_6274_().m_21879_(MemoryModuleType.f_217786_, (Object)Unit.INSTANCE);
        }
        return super.m_6518_(level, difficulty, reason, spawnData, dataTag);
    }

    public double m_142593_(LivingEntity target) {
        return this.m_20205_() * 1.5f * this.m_20205_() * 1.5f + target.m_20205_();
    }

    public int m_21529_() {
        return 2;
    }

    public int m_8100_() {
        return 300;
    }

    protected SoundEvent m_7515_() {
        return ADSoundEvents.SCULKSAUR_IDLE.get();
    }

    protected SoundEvent m_7975_(DamageSource damageSource) {
        return ADSoundEvents.SCULKSAUR_HURT.get();
    }

    protected SoundEvent m_5592_() {
        return ADSoundEvents.SCULKSAUR_DEATH.get();
    }

    public EntityDimensions m_6972_(Pose pose) {
        EntityDimensions entitydimensions = super.m_6972_(pose);
        return this.isEmerging() ? EntityDimensions.m_20398_((float)entitydimensions.f_20377_, (float)1.0f) : entitydimensions;
    }

    public boolean m_213854_() {
        return true;
    }

    public boolean isEmerging() {
        return this.actionSpawn.isActive();
    }

    public boolean isEnraged() {
        return this.m_6274_().m_21876_(ADMemoryModuleTypes.IS_ENRAGED.get(), MemoryStatus.VALUE_PRESENT);
    }

    public void setEnraged(boolean enraged) {
        if (enraged) {
            this.m_6274_().m_21879_(ADMemoryModuleTypes.IS_ENRAGED.get(), (Object)Unit.INSTANCE);
        } else {
            this.m_6274_().m_21936_(ADMemoryModuleTypes.IS_ENRAGED.get());
        }
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
            LivingEntity living = (LivingEntity)entity;
            if (!this.m_6779_(living)) {
                return false;
            }
            return this.m_9236_() == entity.m_9236_() && EntitySelector.f_20406_.test(entity) && !this.m_7307_(entity) && living.m_6095_() != EntityType.f_20529_ && living.m_6095_() != ADEntityTypes.SCULKSAUR.get() && !living.m_20147_() && !living.m_21224_() && this.m_9236_().m_6857_().m_61935_(living.m_20191_());
        }
        return false;
    }

    public void checkWalls() {
        AABB area = this.m_20191_().m_82377_(3.0, 0.0, 3.0);
        int minX = Mth.m_14107_((double)area.f_82288_);
        int minY = Mth.m_14107_((double)area.f_82289_);
        int minZ = Mth.m_14107_((double)area.f_82290_);
        int maxX = Mth.m_14107_((double)area.f_82291_);
        int maxY = Mth.m_14107_((double)area.f_82292_) + 5;
        int manZ = Mth.m_14107_((double)area.f_82293_);
        boolean worked = false;
        for (int x = minX; x <= maxX; ++x) {
            for (int y = minY; y <= maxY; ++y) {
                for (int z = minZ; z <= manZ; ++z) {
                    BlockPos blockpos = new BlockPos(x, y, z);
                    BlockState blockstate = this.m_9236_().m_8055_(blockpos);
                    if (blockstate.m_60795_() || blockstate.m_204336_(BlockTags.f_215822_) || !this.m_9236_().m_46469_().m_46207_(GameRules.f_46132_) || blockstate.m_204336_(BlockTags.f_13069_)) continue;
                    worked = this.m_9236_().m_7471_(blockpos, false) || worked;
                }
            }
        }
        if (worked) {
            BlockPos pos = new BlockPos(minX + this.f_19796_.m_188503_(maxX - minX + 1), minY + this.f_19796_.m_188503_(maxY - minY + 1), minZ + this.f_19796_.m_188503_(manZ - minZ + 1));
            this.m_9236_().m_46796_(2001, pos, 0);
        }
    }

    class VibrationUser
    implements VibrationSystem.User {
        private static final int GAME_EVENT_LISTENER_RANGE = 16;
        private final PositionSource positionSource;

        VibrationUser() {
            this.positionSource = new EntityPositionSource((Entity)SculksaurEntity.this, SculksaurEntity.this.m_20192_());
        }

        public int m_280351_() {
            return 16;
        }

        public PositionSource m_280010_() {
            return this.positionSource;
        }

        public TagKey<GameEvent> m_280028_() {
            return ADTags.GameEvents.SCULKSAUR_CAN_LISTEN;
        }

        public boolean m_280076_() {
            return true;
        }

        public boolean m_280612_(GameEvent gameEvent, GameEvent.Context context) {
            if (!gameEvent.m_204528_(this.m_280028_())) {
                return false;
            }
            Entity entity = context.f_223711_();
            if (entity != null) {
                ItemEntity item;
                if (entity.m_5833_() || entity instanceof SculksaurEntity) {
                    return false;
                }
                if (entity.m_20161_() && gameEvent.m_204528_(GameEventTags.f_144303_)) {
                    if (this.m_280076_() && entity instanceof ServerPlayer) {
                        ServerPlayer serverplayer = (ServerPlayer)entity;
                        CriteriaTriggers.f_215658_.m_222618_(serverplayer);
                    }
                    return false;
                }
                if (entity instanceof ItemEntity && (item = (ItemEntity)entity).m_213854_()) {
                    return false;
                }
            }
            if (context.f_223712_() == null) {
                return true;
            }
            return !context.f_223712_().m_204336_(BlockTags.f_215836_);
        }

        public boolean m_280080_(ServerLevel level, BlockPos pos, GameEvent event, GameEvent.Context context) {
            if (!(SculksaurEntity.this.m_21525_() || SculksaurEntity.this.m_21224_() || SculksaurEntity.this.m_6274_().m_21874_(MemoryModuleType.f_217774_) || SculksaurEntity.this.isEmerging() || !level.m_6857_().m_61937_(pos))) {
                Entity entity = context.f_223711_();
                if (entity instanceof LivingEntity) {
                    LivingEntity living = (LivingEntity)entity;
                    return SculksaurEntity.this.canTargetEntity((Entity)living) && SculksaurEntity.this.m_5448_() != living;
                }
                return true;
            }
            return false;
        }

        public void m_280271_(ServerLevel level, BlockPos pos, GameEvent gameEvent, @Nullable Entity entity, @Nullable Entity playerEntity, float distance) {
            if (!SculksaurEntity.this.m_21224_()) {
                SculksaurEntity.this.f_20939_.m_21882_(MemoryModuleType.f_217774_, (Object)Unit.INSTANCE, 40L);
                SculksaurEntity.this.playActionSynced(SculksaurEntity.this.actionSense);
                if (playerEntity != null) {
                    if (SculksaurEntity.this.m_19950_(playerEntity, 30.0)) {
                        if (SculksaurEntity.this.m_6274_().m_21874_(MemoryModuleType.f_217784_)) {
                            if (SculksaurEntity.this.canTargetEntity(playerEntity)) {
                                pos = playerEntity.m_20183_();
                            }
                            SculksaurEntity.this.increaseAngerAt(playerEntity);
                        } else {
                            SculksaurEntity.this.increaseAngerAt(playerEntity, 15, true);
                        }
                    }
                    SculksaurEntity.this.m_6274_().m_21882_(MemoryModuleType.f_217784_, (Object)Unit.INSTANCE, 100L);
                } else {
                    SculksaurEntity.this.increaseAngerAt(entity);
                }
                if (!SculksaurEntity.this.getAngerLevel().m_219236_()) {
                    Optional optional = SculksaurEntity.this.angerManagement.m_219256_();
                    if (playerEntity != null || optional.isEmpty() || optional.get() == entity) {
                        SculksaurAI.setDisturbanceLocation(SculksaurEntity.this, pos);
                    }
                }
            }
        }
    }

    public record WartGroup(List<String> names) {
        public WartGroup(Integer ... ids) {
            this(Stream.of(ids).map(integer -> "wart" + integer).toList());
        }
    }
}

