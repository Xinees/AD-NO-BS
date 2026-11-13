/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.astemir.astemirlib.re.common.action.ActionSequence
 *  com.astemir.astemirlib.re.common.action.ActionState
 *  com.astemir.astemirlib.re.common.action.ActionType
 *  com.astemir.astemirlib.re.common.action.SyncedActionHolder
 *  com.astemir.astemirlib.re.common.world.entity.EntityLocomotion
 *  com.astemir.astemirlib.re.core.function.FloatProperty
 *  com.astemir.astemirlib.re.core.math.AMath
 *  com.astemir.astemirlib.re.core.math.Easing
 *  com.astemir.astemirlib.re.core.math.Easing$Simple
 *  com.astemir.astemirlib.re.core.math.tween.TweenFloat
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.syncher.EntityDataAccessor
 *  net.minecraft.network.syncher.EntityDataSerializer
 *  net.minecraft.network.syncher.EntityDataSerializers
 *  net.minecraft.network.syncher.SynchedEntityData
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.util.Mth
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.entity.AgeableMob
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.Entity$RemovalReason
 *  net.minecraft.world.entity.EntityDimensions
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.PathfinderMob
 *  net.minecraft.world.entity.Pose
 *  net.minecraft.world.entity.Shearable
 *  net.minecraft.world.entity.ai.control.BodyRotationControl
 *  net.minecraft.world.entity.animal.IronGolem
 *  net.minecraft.world.entity.item.ItemEntity
 *  net.minecraft.world.entity.monster.warden.Warden
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.entity.projectile.AbstractArrow
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.GameRules
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.gameevent.GameEvent
 *  net.minecraft.world.phys.AABB
 *  org.jetbrains.annotations.Nullable
 */
package com.eternal_creations.archies_dino_mod.common.entity.mob.lushsaur;

import com.astemir.astemirlib.re.common.action.ActionSequence;
import com.astemir.astemirlib.re.common.action.ActionState;
import com.astemir.astemirlib.re.common.action.ActionType;
import com.astemir.astemirlib.re.common.action.SyncedActionHolder;
import com.astemir.astemirlib.re.common.world.entity.EntityLocomotion;
import com.astemir.astemirlib.re.core.function.FloatProperty;
import com.astemir.astemirlib.re.core.math.AMath;
import com.astemir.astemirlib.re.core.math.Easing;
import com.astemir.astemirlib.re.core.math.tween.TweenFloat;
import com.eternal_creations.archies_dino_mod.common.ADTags;
import com.eternal_creations.archies_dino_mod.common.effect.ADMobEffects;
import com.eternal_creations.archies_dino_mod.common.entity.ADActionTypes;
import com.eternal_creations.archies_dino_mod.common.entity.ADEntityTypes;
import com.eternal_creations.archies_dino_mod.common.entity.ai.control.SmoothBodyControl;
import com.eternal_creations.archies_dino_mod.common.entity.ai.control.SmoothLookControl;
import com.eternal_creations.archies_dino_mod.common.entity.ai.control.SmoothMoveControl;
import com.eternal_creations.archies_dino_mod.common.entity.base.AbstractComplexMob;
import com.eternal_creations.archies_dino_mod.common.entity.base.AbstractComplexMount;
import com.eternal_creations.archies_dino_mod.common.entity.base.ChargeAttacker;
import com.eternal_creations.archies_dino_mod.common.entity.base.EggLaying;
import com.eternal_creations.archies_dino_mod.common.entity.base.IMultipartEntity;
import com.eternal_creations.archies_dino_mod.common.entity.base.IPartEntity;
import com.eternal_creations.archies_dino_mod.common.entity.base.Mount;
import com.eternal_creations.archies_dino_mod.common.entity.base.SimplePart;
import com.eternal_creations.archies_dino_mod.common.entity.component.MountComponent;
import com.eternal_creations.archies_dino_mod.common.entity.component.PetComponent;
import com.eternal_creations.archies_dino_mod.common.entity.mob.lushsaur.ai.LushsaurAI;
import com.eternal_creations.archies_dino_mod.common.entity.mob.lushsaur.ai.LushsaurState;
import com.eternal_creations.archies_dino_mod.common.entity.utils.EntityUtils;
import com.eternal_creations.archies_dino_mod.common.items.ADItems;
import com.eternal_creations.archies_dino_mod.common.sound.ADSoundEvents;
import com.eternal_creations.archies_dino_mod.server.ServerConfig;
import com.eternal_creations.archies_dino_mod.util.CommonUtil;
import java.util.Set;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.Shearable;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class LushsaurEntity
extends AbstractComplexMount
implements EggLaying,
ChargeAttacker,
IMultipartEntity,
Shearable {
    private static final String DATA_KEY_HURT_TOLERANCE = "HurtTolerance";
    private static final String DATA_KEY_SPEED_BOOST_TICKS = "SpeedBoostTicks";
    private static final String DATA_KEY_DIG_COOLDOWN_TICKS = "DigCooldownTicks";
    private static final String DATA_KEY_HAS_EGG = "HasEgg";
    private static final String DATA_KEY_FED_COUNT = "FedTameCount";
    private static final String DATA_KEY_SHEARED = "Sheared";
    private static final String DATA_KEY_NO_MACE = "NoMace";
    private static final String DATA_KEY_NO_MACE_TICKS = "NoMaceTicks";
    private static final float DEFAULT_HEALTH = 100.0f;
    private static final float BABY_HEALTH_MULTIPLIER = 0.45f;
    private static final float DEFAULT_ATTACK_DAMAGE = 6.0f;
    private static final float HEADBUTT_MULTIPLIER = 1.8333334f;
    private static final float BITE_MULTIPLIER = 1.1666666f;
    private static final float STOMP_MULTIPLIER = 0.8333333f;
    private static final float TAIL_MULTIPLIER = 1.5f;
    private static final EntityDataAccessor<Integer> DATA_HURT_TOLERANCE = SynchedEntityData.m_135353_(LushsaurEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135028_);
    private static final EntityDataAccessor<Integer> DATA_SPEED_BOOST_TICKS = SynchedEntityData.m_135353_(LushsaurEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135028_);
    private static final EntityDataAccessor<Integer> DATA_DIG_COOLDOWN_TICKS = SynchedEntityData.m_135353_(LushsaurEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135028_);
    private static final EntityDataAccessor<Boolean> DATA_HAS_EGG = SynchedEntityData.m_135353_(LushsaurEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135035_);
    private static final EntityDataAccessor<Boolean> DATA_LAYING_EGG = SynchedEntityData.m_135353_(LushsaurEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135035_);
    private static final EntityDataAccessor<Boolean> DATA_SHEARED = SynchedEntityData.m_135353_(LushsaurEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135035_);
    private static final EntityDataAccessor<Boolean> DATA_HAS_NO_MACE = SynchedEntityData.m_135353_(LushsaurEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135035_);
    private static final EntityDataAccessor<Integer> DATA_NO_MACE_TICKS = SynchedEntityData.m_135353_(LushsaurEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135028_);
    private static final float BURIED_SIZE_HEIGHT = 2.0f;
    public static final AbstractComplexMob.TameableDinoSettings DEFAULT_STATS = new AbstractComplexMob.TameableDinoSettings(false, true, true, new ServerConfig.EntityStats(0.2, 100.0, 0.9, 2.0, 48.0, 6.0));
    public ActionState actionAttackHeadbutt;
    public ActionState actionAttackBite;
    public ActionState actionAttackTail;
    public ActionState actionAttackTailStomp;
    public ActionState actionRoar;
    public ActionState actionEat;
    public ActionState actionDeath;
    public ActionState actionEmerge;
    public ActionState actionBury;
    public ActionState actionUp;
    public ActionState actionDown;
    private final ActionSequence actionSequence;
    public TweenFloat shadowRadius = TweenFloat.of((float)2.0f);
    private final SimplePart<LushsaurEntity> head;
    private final SimplePart<LushsaurEntity> tail;
    private final IPartEntity<?>[] parts;
    private int fedForTameCount = 0;

    public LushsaurEntity(EntityType<? extends AbstractComplexMount> entityType, Level level) {
        super((EntityType<? extends AbstractComplexMob>)entityType, level);
        this.actionSequence = new ActionSequence((SyncedActionHolder)this);
        this.f_21342_ = new SmoothMoveControl((Mob)this, 0.25f);
        this.f_21365_ = new SmoothLookControl((Mob)this, 15.0f, 0.3).setCanRotate(this::canRotate);
        this.head = new SimplePart<LushsaurEntity>(this, 1.5f, 1.2f, 0.0, 1.0, 2.5);
        this.tail = new SimplePart<LushsaurEntity>(this, 1.5f, 1.2f, 0.0, 1.0, -2.5);
        this.parts = SimplePart.collectEntities(this, this.head, this.tail);
        this.m_274367_(1.0f);
    }

    public void m_20234_(int id) {
        super.m_20234_(id);
        if (this.parts != null) {
            for (int i = 0; i < this.parts.length; ++i) {
                this.parts[i].setPartId(id + i + 1);
            }
        }
    }

    @Override
    protected Set<ActionState> makeActionSet() {
        this.actionAttackHeadbutt = ActionState.of((ActionType)ADActionTypes.ATTACK_HEADBUTT, (float)0.75f, (float)0.1f, (float)0.4f).exitTime(0.6f).onStart(() -> this.m_216990_(ADSoundEvents.LUSHSAUR_ATTACK_HEADBUTT.get())).onTick(tick -> {
            if (tick == 10) {
                this.attackActionTargetEntity(1.8333334f, 0.2f);
                EntityUtils.breakBlocksForward((Mob)this, 3.0, 3.0f, 1.0f, true, state -> !state.m_204336_(ADTags.Blocks.LUSHSAUR_BREAK_IMMUNE_BLOCKS));
            }
        });
        this.actionAttackBite = ActionState.of((ActionType)ADActionTypes.ATTACK_BITE, (float)0.7917f, (float)0.1f, (float)0.4f).exitTime(0.5f).onStart(() -> this.m_216990_(ADSoundEvents.LUSHSAUR_ATTACK_BITE.get())).onTick(tick -> {
            if (tick == 8) {
                this.attackActionTargetEntity(1.1666666f, 0.1f);
            }
        });
        this.actionAttackTail = ActionState.of((ActionType)ADActionTypes.ATTACK_TAIL, (float)1.9583f, (float)0.3f, (float)0.8f).exitTime(1.7f).onStart(() -> this.m_216990_(ADSoundEvents.LUSHSAUR_ATTACK_TAIL_SWING.get())).onTick(tick -> {
            if (tick == 20) {
                EntityUtils.attackForward((Mob)this, this.m_6162_() ? 2.0f : 4.0f, -0.75f, 1.0f, 1.5f, target -> {});
            }
        }).condition(() -> !this.hasNoMace());
        this.actionAttackTailStomp = ActionState.of((ActionType)ADActionTypes.ATTACK_TAIL_STOMP, (float)1.25f, (float)0.2f, (float)0.4f).exitTime(1.0f).onStart(() -> this.m_216990_(ADSoundEvents.LUSHSAUR_ATTACK_TAIL_STOMP.get())).onTick(tick -> {
            if (tick == 18) {
                this.m_9236_().m_245803_((Entity)this, this.m_20183_(), ADSoundEvents.LUSHSAUR_STOMP_EXPLOSION.get(), SoundSource.NEUTRAL, 1.25f, 0.6f + this.f_19796_.m_188501_() * 0.5f);
                EntityUtils.attackForward((Mob)this, this.m_6162_() ? 3.0f : 6.0f, -1.0f, 1.0f, 0.8333333f, target -> target.m_147207_(new MobEffectInstance((MobEffect)ADMobEffects.DAZED.get(), 60, 0, false, false, true), (Entity)this));
            }
        }).condition(() -> !this.hasNoMace());
        this.actionRoar = ActionState.of((ActionType)ADActionTypes.ROAR, (float)2.5f, (float)0.2f, (float)0.8f).exitTime(2.0f).onTick(tick -> {
            if (tick == 10) {
                this.m_216990_(ADSoundEvents.LUSHSAUR_ROAR.get());
            }
        });
        this.actionEat = ActionState.of((ActionType)ADActionTypes.EAT, (float)4.75f, (float)0.2f, (float)1.0f).exitTime(4.0f).onTick(tick -> {
            if (tick == 3 && this.destroyActionTargetBlock(false)) {
                this.m_216990_(ADSoundEvents.LUSHSAUR_EAT.get());
                this.setSheared(false);
            }
        });
        this.actionDeath = ActionState.of((ActionType)ADActionTypes.DEATH, (float)3.0f, (float)0.1f, (float)0.0f).holdOnLastFrame();
        this.actionEmerge = ActionState.of((ActionType)ADActionTypes.EMERGE, (float)2.4583f, (float)0.0f, (float)0.8f).exitTime(2.2f).onStart(() -> {
            this.setBuried(false);
            this.m_216990_(ADSoundEvents.LUSHSAUR_EMERGE.get());
        });
        this.actionBury = ActionState.of((ActionType)ADActionTypes.BURY, (float)2.5833f, (float)0.1f, (float)0.0f).onStart(() -> this.m_216990_(ADSoundEvents.LUSHSAUR_BURY.get())).onFinish(() -> this.setBuried(true));
        this.actionUp = ActionState.of((ActionType)ADActionTypes.UP, (float)3.5f, (float)0.2f, (float)0.8f).exitTime(3.0f).onTick(tick -> {
            if (tick == 64) {
                this.setState(LushsaurState.IDLE);
            }
        });
        this.actionDown = ActionState.of((ActionType)ADActionTypes.DOWN, (float)3.5f, (float)0.2f, (float)0.2f).onTick(tick -> {
            if (tick == 64) {
                this.setState(LushsaurState.SITTING);
            }
        });
        return Set.of(this.actionAttackHeadbutt, this.actionAttackBite, this.actionAttackTail, this.actionAttackTailStomp, this.actionRoar, this.actionDeath, this.actionEat, this.actionEmerge, this.actionBury, this.actionUp, this.actionDown);
    }

    @Override
    protected EntityLocomotion createLocomotion() {
        return EntityLocomotion.of().speed(0.02f, 0.24f).transitionLimit(0.2f).responsiveness(0.3f).build();
    }

    protected MountComponent<LushsaurEntity> createMount() {
        return MountComponent.of(player -> this.isAdult() && this.isOwnedBy((LivingEntity)player) && !this.isSitting()).maxSpeed((FloatProperty<PathfinderMob>)((FloatProperty)(instance, partialTick) -> this.getSpeedBoostTicks() > 0 ? 2.0f : 1.0f)).followYawSpeed(0.1f).followHeadSpeed(0.4f).inertia(0.05f).friction(0.3f).passengerOffset(0.0f, 0.0f, 1.0f).build(this);
    }

    @Override
    protected PetComponent<?> createPet() {
        return PetComponent.of().setBabyHealthMultiplier(0.45f).build(this);
    }

    @Override
    protected void m_8097_() {
        super.m_8097_();
        this.f_19804_.m_135372_(DATA_HURT_TOLERANCE, (Object)3);
        this.f_19804_.m_135372_(DATA_SPEED_BOOST_TICKS, (Object)0);
        this.f_19804_.m_135372_(DATA_DIG_COOLDOWN_TICKS, (Object)0);
        this.f_19804_.m_135372_(DATA_LAYING_EGG, (Object)false);
        this.f_19804_.m_135372_(DATA_HAS_EGG, (Object)false);
        this.f_19804_.m_135372_(DATA_SHEARED, (Object)false);
        this.f_19804_.m_135372_(DATA_HAS_NO_MACE, (Object)false);
        this.f_19804_.m_135372_(DATA_NO_MACE_TICKS, (Object)0);
    }

    @Override
    public void m_7380_(CompoundTag compound) {
        super.m_7380_(compound);
        compound.m_128405_(DATA_KEY_HURT_TOLERANCE, this.getHurtTolerance());
        compound.m_128405_(DATA_KEY_SPEED_BOOST_TICKS, this.getSpeedBoostTicks());
        compound.m_128405_(DATA_KEY_DIG_COOLDOWN_TICKS, this.getDigCooldownTicks());
        compound.m_128379_(DATA_KEY_HAS_EGG, ((Boolean)this.f_19804_.m_135370_(DATA_HAS_EGG)).booleanValue());
        compound.m_128405_(DATA_KEY_FED_COUNT, this.fedForTameCount);
        compound.m_128379_(DATA_KEY_SHEARED, this.isSheared());
        compound.m_128379_(DATA_KEY_NO_MACE, this.hasNoMace());
        compound.m_128405_(DATA_KEY_NO_MACE_TICKS, this.getNoMaceTicks());
    }

    @Override
    public void m_7378_(CompoundTag compound) {
        super.m_7378_(compound);
        if (compound.m_128441_(DATA_KEY_HURT_TOLERANCE)) {
            this.setHurtTolerance(compound.m_128451_(DATA_KEY_HURT_TOLERANCE));
        }
        if (compound.m_128441_(DATA_KEY_SPEED_BOOST_TICKS)) {
            this.setSpeedBoostTicks(compound.m_128451_(DATA_KEY_SPEED_BOOST_TICKS));
        }
        if (compound.m_128441_(DATA_KEY_DIG_COOLDOWN_TICKS)) {
            this.setDigCooldownTicks(compound.m_128451_(DATA_KEY_DIG_COOLDOWN_TICKS));
        }
        if (compound.m_128441_(DATA_KEY_HAS_EGG)) {
            this.f_19804_.m_135381_(DATA_HAS_EGG, (Object)compound.m_128471_(DATA_KEY_HAS_EGG));
        }
        if (compound.m_128441_(DATA_KEY_FED_COUNT)) {
            this.fedForTameCount = compound.m_128451_(DATA_KEY_FED_COUNT);
        }
        if (compound.m_128441_(DATA_KEY_SHEARED)) {
            this.setSheared(compound.m_128471_(DATA_KEY_SHEARED));
        }
        if (compound.m_128441_(DATA_KEY_NO_MACE)) {
            this.setHasNoMace(compound.m_128471_(DATA_KEY_NO_MACE));
        }
        if (compound.m_128441_(DATA_KEY_NO_MACE_TICKS)) {
            this.setNoMaceTicks(compound.m_128451_(DATA_KEY_NO_MACE_TICKS));
        }
        this.m_6210_();
    }

    protected void m_30232_() {
        super.m_30232_();
        if (!this.m_6162_()) {
            this.setHasNoMace(true);
            this.m_19998_((ItemLike)ADItems.LUSH_CORE.get());
        }
    }

    public boolean m_6785_(double distanceToClosestPlayer) {
        return false;
    }

    protected void m_8099_() {
        LushsaurAI.setupAI(this, this.f_21345_, this.f_21346_);
    }

    protected BodyRotationControl m_7560_() {
        return new SmoothBodyControl((Mob)this, 35.0f, 15, 10.0f, 0.07f).setCanRotate(this::canRotate);
    }

    public float m_5610_(BlockPos pos, LevelReader level) {
        return level.m_8055_(pos.m_7495_()).m_204336_(ADTags.Blocks.LUSHSAUR_VALID_SPAWN) ? 10.0f : level.m_220419_(pos);
    }

    public void m_6075_() {
        super.m_6075_();
        this.head.tick(0.0f, this.isBuried() ? -1.0f : 0.0f, 0.0f);
        this.tail.tick(0.0f, this.isBuried() ? -1.0f : 0.0f, 0.0f);
    }

    @Override
    public void m_8119_() {
        int noMaceTicks;
        int digCooldownTicks;
        super.m_8119_();
        this.shadowRadius.tick();
        this.actionSequence.tick();
        int speedBoostTicks = this.getSpeedBoostTicks();
        if (speedBoostTicks > 0) {
            this.setSpeedBoostTicks(speedBoostTicks - 1);
        }
        if ((digCooldownTicks = this.getDigCooldownTicks()) > 0) {
            this.setDigCooldownTicks(digCooldownTicks - 1);
        }
        if ((noMaceTicks = this.getNoMaceTicks()) > 0) {
            this.setNoMaceTicks(noMaceTicks - 1);
        }
        if (this.hasNoMace() && noMaceTicks <= 0) {
            this.setHasNoMace(false);
        }
        if (this.isBuried() || this.actionBury.isActive()) {
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
        Level normalizedRot = this.m_9236_();
        if (normalizedRot instanceof ServerLevel) {
            ServerLevel serverLevel = (ServerLevel)normalizedRot;
            if (this.m_5448_() == null && this.isCharging()) {
                this.setCharging(false);
            }
            if (!this.isTamed() && this.f_19797_ % 10 == 0) {
                CommonUtil.checkWalls(serverLevel, this.m_20191_(), this.m_20069_(), ADTags.Blocks.LUSHSAUR_BREAK_VEGETATION_BLOCKS);
            }
        }
        if (this.isCharging() && this.m_9236_().m_46469_().m_46207_(GameRules.f_46132_)) {
            boolean flag = false;
            AABB aabb = this.m_20191_().m_82400_(1.0);
            for (BlockPos blockpos : BlockPos.m_121976_((int)Mth.m_14107_((double)aabb.f_82288_), (int)Mth.m_14107_((double)aabb.f_82289_), (int)Mth.m_14107_((double)aabb.f_82290_), (int)Mth.m_14107_((double)aabb.f_82291_), (int)Mth.m_14107_((double)aabb.f_82292_), (int)Mth.m_14107_((double)aabb.f_82293_))) {
                BlockState blockState = this.m_9236_().m_8055_(blockpos);
                if (!blockState.m_204336_(ADTags.Blocks.LUSHSAUR_BREAK_VEGETATION_BLOCKS)) continue;
                flag = this.m_9236_().m_46953_(blockpos, false, (Entity)this) || flag;
            }
        }
    }

    @Override
    public void onRiderAttack(LivingEntity rider, Mount.AttackType type) {
        if (!this.isNoActionActive()) {
            return;
        }
        switch (type) {
            case PRIMARY: {
                this.playActionSynced(this.actionAttackHeadbutt);
                break;
            }
            case SPECIAL: {
                this.playActionSynced(this.actionAttackTail);
            }
        }
    }

    protected void m_6153_() {
        if (this.f_20919_ == 0) {
            this.playActionSynced(this.actionDeath);
        }
        ++this.f_20919_;
        if (this.f_20919_ >= 60 && !this.m_9236_().m_5776_() && !this.m_213877_()) {
            this.m_9236_().m_7605_((Entity)this, (byte)60);
            this.m_142687_(Entity.RemovalReason.KILLED);
        }
    }

    @Override
    protected boolean canRotate() {
        return !this.actionEmerge.isActive() && !this.actionBury.isActive() && !this.actionDeath.isActive() && !this.actionAttackTail.isActiveWithoutFadeOut() && !this.actionAttackTailStomp.isActive() && !this.isBuried() && !this.isSitting();
    }

    @Override
    protected boolean isMovementBlocked() {
        return this.actionEmerge.isActive() || this.actionBury.isActive() || this.actionDeath.isActive() || this.actionAttackTail.isActiveWithoutFadeOut() || this.actionAttackTailStomp.isActive() || this.actionRoar.isActive() || this.actionEat.isActive() || this.actionDown.isActive() || this.actionUp.isActive() || this.isBuried() || this.isSitting();
    }

    public void m_147240_(double strength, double x, double z) {
        if (this.isBuried()) {
            return;
        }
        super.m_147240_(strength, x, z);
    }

    public boolean m_6094_() {
        return !this.isBuried() && super.m_6094_();
    }

    @Override
    public boolean m_6469_(DamageSource source, float amount) {
        boolean hurt;
        Entity entitySource = source.m_7639_();
        if (entitySource instanceof AbstractArrow && this.f_19796_.m_188501_() < 0.75f) {
            return false;
        }
        if (entitySource instanceof Warden || entitySource instanceof IronGolem) {
            amount *= 0.1f;
        }
        if ((hurt = super.m_6469_(source, amount)) && this.isBuried()) {
            int hurtTolerance = this.getHurtTolerance();
            if (hurtTolerance - 1 > 0) {
                this.setHurtTolerance(hurtTolerance - 1);
            } else {
                this.actionSequence.clearQueue().addAction(this.actionEmerge).addAction(this.actionAttackTailStomp).start();
            }
            this.setDigCooldownTicks(600);
        }
        return hurt;
    }

    @Override
    public InteractionResult m_6071_(Player player, InteractionHand hand) {
        if (this.isBuried() || this.actionEmerge.isActive() || this.actionBury.isActive()) {
            return InteractionResult.PASS;
        }
        ItemStack itemstack = player.m_21120_(hand);
        if (itemstack.m_150930_(Items.f_42574_)) {
            if (!this.m_9236_().f_46443_ && this.m_6220_()) {
                this.m_5851_(SoundSource.PLAYERS);
                this.m_146852_(GameEvent.f_157781_, (Entity)player);
                itemstack.m_41622_(1, (LivingEntity)player, p -> p.m_21190_(hand));
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.CONSUME;
        }
        return super.m_6071_(player, hand);
    }

    @Override
    public InteractionResult eatFromHand(Player player, ItemStack itemStack) {
        InteractionResult result = super.eatFromHand(player, itemStack);
        if (result != InteractionResult.PASS) {
            if (this.isTamed()) {
                this.setSpeedBoostTicks(1200);
            }
            this.setDigCooldownTicks(400);
            this.playActionSynced(this.actionEat);
        }
        return result;
    }

    @Override
    public boolean tryToTame(Player player, RandomSource random) {
        boolean success;
        ++this.fedForTameCount;
        if (this.fedForTameCount < 5) {
            return false;
        }
        boolean bl = success = random.m_188503_(5) == 0;
        if (success) {
            this.fedForTameCount = 0;
        }
        return success;
    }

    public void m_5496_(SoundEvent sound, float volume, float pitch) {
        if (!this.isBuried()) {
            super.m_5496_(sound, volume, pitch);
        }
    }

    public double m_20968_(@Nullable Entity lookingEntity) {
        return this.isBuried() ? 0.0 : super.m_20968_(lookingEntity);
    }

    public void m_6710_(@Nullable LivingEntity target) {
        super.m_6710_(target);
        this.setDigCooldownTicks(400);
    }

    public int m_21529_() {
        return 2;
    }

    protected SoundEvent m_7515_() {
        return ADSoundEvents.LUSHSAUR_IDLE.get();
    }

    protected SoundEvent m_7975_(DamageSource damageSource) {
        return ADSoundEvents.LUSHSAUR_HURT.get();
    }

    protected SoundEvent m_5592_() {
        return ADSoundEvents.LUSHSAUR_DEATH.get();
    }

    protected float m_6121_() {
        return 1.5f;
    }

    @Override
    public boolean m_6898_(ItemStack stack) {
        return stack.m_150930_(Items.f_151079_);
    }

    @Override
    protected AbstractComplexMob.TameableDinoSettings getSettings() {
        return ServerConfig.getEntitySettings().lushsaur();
    }

    @Override
    @Nullable
    public AgeableMob m_142606_(ServerLevel serverLevel, AgeableMob ageableMob) {
        return (AgeableMob)ADEntityTypes.LUSHSAUR.get().m_20615_((Level)serverLevel);
    }

    public EntityDimensions m_6972_(Pose pose) {
        EntityDimensions entitydimensions = super.m_6972_(pose);
        return this.isBuried() || this.actionBury.isActive() ? EntityDimensions.m_20398_((float)entitydimensions.f_20377_, (float)(2.0f * this.m_6134_())) : entitydimensions;
    }

    @Override
    public float getCameraDistance(float partialTick) {
        return 8.0f;
    }

    @Override
    public boolean isSitting() {
        return this.getState(LushsaurState::byId) == LushsaurState.SITTING;
    }

    @Override
    public void setSitting(boolean active) {
        if (!this.isNoActionActive()) {
            return;
        }
        if (this.isSitting()) {
            this.playActionSynced(this.actionUp);
        } else {
            this.playActionSynced(this.actionDown);
        }
    }

    public boolean isBuried() {
        return this.getState(LushsaurState::byId) == LushsaurState.BURIED;
    }

    public void setBuried(boolean active) {
        if (active) {
            this.setState(LushsaurState.BURIED);
            this.setHurtTolerance(3);
        } else {
            this.setState(LushsaurState.IDLE);
        }
        this.m_6210_();
    }

    @Override
    public boolean isCharging() {
        return this.getState(LushsaurState::byId) == LushsaurState.CHARGING;
    }

    @Override
    public void setCharging(boolean active) {
        this.setState(active ? LushsaurState.CHARGING : LushsaurState.IDLE);
    }

    public int getHurtTolerance() {
        return (Integer)this.f_19804_.m_135370_(DATA_HURT_TOLERANCE);
    }

    public void setHurtTolerance(int hurtTolerance) {
        this.f_19804_.m_135381_(DATA_HURT_TOLERANCE, (Object)hurtTolerance);
    }

    public int getSpeedBoostTicks() {
        return (Integer)this.f_19804_.m_135370_(DATA_SPEED_BOOST_TICKS);
    }

    public void setSpeedBoostTicks(int speedBoostTicks) {
        this.f_19804_.m_135381_(DATA_SPEED_BOOST_TICKS, (Object)speedBoostTicks);
    }

    public void setSheared(boolean sheared) {
        this.f_19804_.m_135381_(DATA_SHEARED, (Object)sheared);
    }

    public boolean isSheared() {
        return (Boolean)this.f_19804_.m_135370_(DATA_SHEARED);
    }

    public void setHasNoMace(boolean noMace) {
        if (noMace) {
            this.setNoMaceTicks(2000);
        }
        this.f_19804_.m_135381_(DATA_HAS_NO_MACE, (Object)noMace);
    }

    public boolean hasNoMace() {
        return (Boolean)this.f_19804_.m_135370_(DATA_HAS_NO_MACE);
    }

    public void setNoMaceTicks(int ticks) {
        this.f_19804_.m_135381_(DATA_NO_MACE_TICKS, (Object)ticks);
    }

    public int getNoMaceTicks() {
        return (Integer)this.f_19804_.m_135370_(DATA_NO_MACE_TICKS);
    }

    public int getDigCooldownTicks() {
        return (Integer)this.f_19804_.m_135370_(DATA_DIG_COOLDOWN_TICKS);
    }

    public void setDigCooldownTicks(int canDigCooldown) {
        this.f_19804_.m_135381_(DATA_DIG_COOLDOWN_TICKS, (Object)canDigCooldown);
    }

    @Override
    public boolean isLayingEgg() {
        return (Boolean)this.f_19804_.m_135370_(DATA_LAYING_EGG);
    }

    @Override
    public void setLayingEgg(boolean flag) {
        this.f_19804_.m_135381_(DATA_LAYING_EGG, (Object)flag);
    }

    @Override
    public void setHasEgg(boolean flag) {
        this.f_19804_.m_135381_(DATA_HAS_EGG, (Object)flag);
    }

    @Override
    public boolean hasEgg() {
        return (Boolean)this.f_19804_.m_135370_(DATA_HAS_EGG);
    }

    @Override
    public IPartEntity<?>[] getIParts() {
        return this.parts;
    }

    @Override
    public boolean hurtFromPart(IPartEntity<?> part, DamageSource source, float amount) {
        return this.m_6469_(source, amount);
    }

    @Override
    public InteractionResult interactFromPart(IPartEntity<?> part, Player player, InteractionHand hand) {
        return this.m_6071_(player, hand);
    }

    public void m_5851_(SoundSource category) {
        this.m_9236_().m_6269_(null, (Entity)this, ADSoundEvents.LUSHSAUR_SHEAR.get(), category, 1.0f, 1.0f);
        this.setSheared(true);
        for (int j = 0; j < 1 + this.f_19796_.m_188503_(3); ++j) {
            ItemEntity itementity = this.m_20000_((ItemLike)Items.f_151016_, 1);
            if (itementity == null) continue;
            itementity.m_20256_(itementity.m_20184_().m_82520_((double)((this.f_19796_.m_188501_() - this.f_19796_.m_188501_()) * 0.1f), (double)(this.f_19796_.m_188501_() * 0.05f), (double)((this.f_19796_.m_188501_() - this.f_19796_.m_188501_()) * 0.1f)));
        }
    }

    public boolean m_6220_() {
        return this.m_6084_() && !this.isSheared() && !this.m_6162_();
    }
}

