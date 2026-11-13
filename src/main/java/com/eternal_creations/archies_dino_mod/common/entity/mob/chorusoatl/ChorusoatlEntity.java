/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.astemir.astemirlib.re.common.action.ActionState
 *  com.astemir.astemirlib.re.common.action.ActionType
 *  com.astemir.astemirlib.re.common.world.entity.EntityLocomotion
 *  com.astemir.astemirlib.re.common.world.physics.CollisionContext
 *  com.astemir.astemirlib.re.common.world.physics.CollisionContext$Mutable
 *  com.astemir.astemirlib.re.common.world.physics.Raycast
 *  com.astemir.astemirlib.re.core.math.tween.ChasingFloat
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
 *  net.minecraft.util.Mth
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.DifficultyInstance
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.AgeableMob
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.Entity$RemovalReason
 *  net.minecraft.world.entity.EntityDimensions
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.MobSpawnType
 *  net.minecraft.world.entity.MoverType
 *  net.minecraft.world.entity.PlayerRideableJumping
 *  net.minecraft.world.entity.Pose
 *  net.minecraft.world.entity.SpawnGroupData
 *  net.minecraft.world.entity.ai.control.BodyRotationControl
 *  net.minecraft.world.entity.ai.targeting.TargetingConditions
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.ServerLevelAccessor
 *  net.minecraft.world.level.block.state.BlockBehaviour$BlockStateBase
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.Nullable
 */
package com.eternal_creations.archies_dino_mod.common.entity.mob.chorusoatl;

import com.astemir.astemirlib.re.common.action.ActionState;
import com.astemir.astemirlib.re.common.action.ActionType;
import com.astemir.astemirlib.re.common.world.entity.EntityLocomotion;
import com.astemir.astemirlib.re.common.world.physics.CollisionContext;
import com.astemir.astemirlib.re.common.world.physics.Raycast;
import com.astemir.astemirlib.re.core.math.tween.ChasingFloat;
import com.eternal_creations.archies_dino_mod.common.ADTags;
import com.eternal_creations.archies_dino_mod.common.entity.ADActionTypes;
import com.eternal_creations.archies_dino_mod.common.entity.ADEntityTypes;
import com.eternal_creations.archies_dino_mod.common.entity.ai.control.SmoothBodyControl;
import com.eternal_creations.archies_dino_mod.common.entity.ai.control.SmoothLookControl;
import com.eternal_creations.archies_dino_mod.common.entity.ai.control.SmoothMoveControl;
import com.eternal_creations.archies_dino_mod.common.entity.base.AbstractComplexMob;
import com.eternal_creations.archies_dino_mod.common.entity.base.AbstractComplexMount;
import com.eternal_creations.archies_dino_mod.common.entity.base.EggLaying;
import com.eternal_creations.archies_dino_mod.common.entity.base.Flyer;
import com.eternal_creations.archies_dino_mod.common.entity.base.Mount;
import com.eternal_creations.archies_dino_mod.common.entity.component.MountComponent;
import com.eternal_creations.archies_dino_mod.common.entity.component.PetComponent;
import com.eternal_creations.archies_dino_mod.common.entity.mob.chorusoatl.ai.ChorusoatlAI;
import com.eternal_creations.archies_dino_mod.common.entity.mob.chorusoatl.ai.ChorusoatlState;
import com.eternal_creations.archies_dino_mod.common.entity.projectile.ChorusoatlSpitEntity;
import com.eternal_creations.archies_dino_mod.common.entity.utils.EntityUtils;
import com.eternal_creations.archies_dino_mod.common.sound.ADSoundEvents;
import com.eternal_creations.archies_dino_mod.server.ServerConfig;
import java.util.List;
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
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.PlayerRideableJumping;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ChorusoatlEntity
extends AbstractComplexMount
implements Flyer,
PlayerRideableJumping,
EggLaying {
    private static final EntityDataAccessor<Byte> DATA_FLIGHT_PHASE = SynchedEntityData.m_135353_(ChorusoatlEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135027_);
    private static final EntityDataAccessor<Boolean> DATA_HAS_EGG = SynchedEntityData.m_135353_(ChorusoatlEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135035_);
    private static final EntityDataAccessor<Boolean> DATA_LAYING_EGG = SynchedEntityData.m_135353_(ChorusoatlEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135035_);
    private static final float DEFAULT_HEALTH = 140.0f;
    private static final float BABY_HEALTH_MULTIPLIER = 0.21428572f;
    private static final float DEFAULT_ATTACK_DAMAGE = 6.0f;
    private static final float BITE_MULTIPLIER = 1.6666666f;
    private static final float STOMP_MULTIPLIER = 1.3333334f;
    public static final float RANGED_MULTIPLIER = 0.16666667f;
    public static final AbstractComplexMob.TameableDinoSettings DEFAULT_STATS = new AbstractComplexMob.TameableDinoSettings(false, true, true, new ServerConfig.EntityStats(0.2, 1.0, 140.0, 0.8, 0.0, 96.0, 6.0));
    public ActionState actionShake;
    public ActionState actionScream;
    public ActionState actionAttackBite;
    public ActionState actionAttackHit;
    public ActionState actionAttackShoot;
    public ActionState actionAttackFly;
    public ActionState actionUp;
    public ActionState actionDown;
    public ActionState actionDeathGround;
    public ActionState actionDeathAirLand;
    public ChasingFloat flyingPitch = ChasingFloat.of((float)0.0f);
    public ChasingFloat flyingRoll = ChasingFloat.of((float)0.0f);
    public ChasingFloat flyingMotion = ChasingFloat.of((float)0.0f);
    public ChasingFloat sittingWeight = ChasingFloat.of((float)0.0f);
    private final CollisionContext.Mutable collisionContext = CollisionContext.create();
    private int flightDelay;
    private int glideTicks;

    public ChorusoatlEntity(EntityType<? extends AbstractComplexMount> entityType, Level level) {
        super((EntityType<? extends AbstractComplexMob>)entityType, level);
        this.f_21342_ = new SmoothMoveControl((Mob)this, 0.15f, true);
        this.f_21365_ = new SmoothLookControl((Mob)this, 15.0f, 0.3).setCanRotate(this::canRotate);
    }

    @Override
    protected Set<ActionState> makeActionSet() {
        this.actionShake = ActionState.of((ActionType)ADActionTypes.SHAKE, (float)2.8f, (float)0.2f, (float)0.6f).exitTime(2.0f);
        this.actionScream = ActionState.of((ActionType)ADActionTypes.SCREAM, (float)5.56f, (float)0.2f, (float)0.6f).exitTime(5.0f).onStart(() -> this.m_216990_(ADSoundEvents.CHORUSOATL_SCREAM.get())).onTick(tick -> {
            LivingEntity target;
            if (tick >= 23 && tick <= 90) {
                this.screamForward(this.m_6162_() ? 6.0f : 15.0f, -0.75f, this.m_6162_() ? 0.05f : 0.15f);
            }
            if (tick == 27 && (target = this.m_5448_()) != null) {
                for (ChorusoatlEntity nearbyEntity : this.m_9236_().m_45971_(ChorusoatlEntity.class, TargetingConditions.m_148353_(), (LivingEntity)this, this.m_20191_().m_82377_(32.0, 32.0, 32.0))) {
                    if (nearbyEntity.m_5448_() != null || nearbyEntity.isTamed()) continue;
                    nearbyEntity.m_6710_(target);
                }
            }
        });
        this.actionAttackBite = ActionState.of((ActionType)ADActionTypes.ATTACK_BITE, (float)1.52f, (float)0.2f, (float)0.6f).exitTime(1.1f).onTick(tick -> {
            if (tick == 5) {
                this.m_216990_(ADSoundEvents.CHORUSOATL_BITE.get());
            }
            if (tick == 14) {
                this.attackActionTargetEntity(1.6666666f, 0.2f);
            }
        });
        this.actionAttackHit = ActionState.of((ActionType)ADActionTypes.ATTACK_HIT, (float)2.8f, (float)0.2f, (float)0.6f).exitTime(2.0f).onTick(tick -> {
            if (tick == 22) {
                this.attackActionTargetEntity(1.3333334f, 0.4f);
            }
        });
        this.actionAttackShoot = ActionState.of((ActionType)ADActionTypes.ATTACK_SHOOT, (float)1.68f, (float)0.2f, (float)0.6f).exitTime(1.1f).onTick(tick -> {
            if (tick == 5) {
                this.m_216990_(ADSoundEvents.CHORUSOATL_SPIT.get());
            }
            if (tick == 16 && !this.m_9236_().m_5776_()) {
                LivingEntity target = this.m_5448_();
                LivingEntity rider = this.m_6688_();
                Vec3 look = rider != null ? rider.m_20154_() : EntityUtils.getViewVector((LivingEntity)this);
                ChorusoatlSpitEntity spit = new ChorusoatlSpitEntity(this.m_9236_(), (LivingEntity)this);
                spit.m_6034_(this.m_20185_() + look.f_82479_ * 2.0, this.m_20188_() + 0.2, this.m_20189_() + look.f_82481_ * 2.0);
                if (rider != null) {
                    spit.m_6686_(look.f_82479_, look.f_82480_, look.f_82481_, 2.5f, 0.0f);
                } else {
                    double dirZ;
                    double dirY;
                    double dirX;
                    if (target != null) {
                        dirX = target.m_20185_() - this.m_20185_();
                        dirY = target.m_20188_() - spit.m_20186_();
                        dirZ = target.m_20189_() - this.m_20189_();
                    } else {
                        dirX = look.f_82479_;
                        dirY = look.f_82480_;
                        dirZ = look.f_82481_;
                    }
                    spit.m_6686_(dirX, dirY, dirZ, 2.0f, 0.0f);
                }
                this.m_9236_().m_7967_((Entity)spit);
            }
        });
        this.actionAttackFly = ActionState.of((ActionType)ADActionTypes.ATTACK_FLY, (float)1.56f, (float)0.4f, (float)0.6f).exitTime(1.1f).onTick(tick -> {
            if (tick == 5) {
                this.m_216990_(ADSoundEvents.CHORUSOATL_BITE.get());
            }
            if (tick == 16) {
                this.attackActionTargetEntity(1.6666666f, 0.4f);
            }
        });
        this.actionUp = ActionState.of((ActionType)ADActionTypes.UP, (float)0.88f, (float)0.2f, (float)0.2f).exitTime(0.85f).onStart(() -> this.setFlightPhase(Flyer.FlightPhase.NONE)).onTick(tick -> {
            if (tick == 10) {
                this.glideTicks = 120;
                this.setFlying(true);
                if (!this.m_21525_()) {
                    this.m_20256_(this.m_20154_());
                }
            }
        });
        this.actionDown = ActionState.of((ActionType)ADActionTypes.DOWN, (float)0.68f, (float)0.4f, (float)0.5f).exitTime(0.58f).onStart(() -> {
            this.m_21573_().m_26573_();
            this.setFlightPhase(Flyer.FlightPhase.NONE);
            this.setFlying(false);
        });
        this.actionDeathGround = ActionState.of((ActionType)ADActionTypes.DEATH, (float)1.7f, (float)0.1f, (float)0.1f).holdOnLastFrame();
        this.actionDeathAirLand = ActionState.of((ActionType)ADActionTypes.DEATH_AIR, (float)1.5833f, (float)0.1f, (float)0.1f).onStart(() -> this.setFallingDead(false)).holdOnLastFrame();
        return Set.of(this.actionShake, this.actionScream, this.actionAttackBite, this.actionAttackHit, this.actionAttackShoot, this.actionAttackFly, this.actionUp, this.actionDown, this.actionDeathGround, this.actionDeathAirLand);
    }

    @Override
    protected void m_8097_() {
        super.m_8097_();
        this.f_19804_.m_135372_(DATA_FLIGHT_PHASE, (Object)0);
        this.f_19804_.m_135372_(DATA_LAYING_EGG, (Object)false);
        this.f_19804_.m_135372_(DATA_HAS_EGG, (Object)false);
    }

    public void m_7350_(EntityDataAccessor<?> key) {
        super.m_7350_(key);
        if (key == DATA_MOB_STATE) {
            this.sittingWeight.setTarget(this.isSitting() ? 1.0f : 0.0f, 0.2f);
        }
    }

    @Override
    public void m_7380_(CompoundTag compound) {
        super.m_7380_(compound);
        compound.m_128379_("HasEgg", ((Boolean)this.f_19804_.m_135370_(DATA_HAS_EGG)).booleanValue());
    }

    @Override
    public void m_7378_(CompoundTag compound) {
        super.m_7378_(compound);
        if (compound.m_128441_("HasEgg")) {
            this.f_19804_.m_135381_(DATA_HAS_EGG, (Object)compound.m_128471_("HasEgg"));
        }
        this.m_6210_();
    }

    protected void m_8099_() {
        ChorusoatlAI.setupAI(this, this.f_21345_, this.f_21346_);
    }

    public float m_5610_(BlockPos pos, LevelReader level) {
        return level.m_8055_(pos.m_7495_()).m_204336_(ADTags.Blocks.CHORUSOATL_VALID_SPAWN) ? 10.0f : level.m_220419_(pos);
    }

    public SpawnGroupData m_6518_(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag dataTag) {
        if (level.m_213780_().m_188501_() < 0.35f) {
            this.m_6863_(true);
        }
        if (level.m_213780_().m_188501_() < 0.75f) {
            this.playActionSynced(this.actionUp);
        }
        return super.m_6518_(level, difficulty, reason, spawnData, dataTag);
    }

    @Override
    public void m_8119_() {
        super.m_8119_();
        this.flyingPitch.tick();
        this.flyingRoll.tick();
        this.flyingMotion.tick();
        this.sittingWeight.tick();
        boolean flying = this.isFlying();
        Vec3 deltaMovement = this.m_20184_();
        double horizontalDistance = deltaMovement.m_165925_();
        if (this.m_20068_() != flying) {
            this.m_20242_(flying);
        }
        if (flying && (horizontalDistance > (double)0.1f || this.glideTicks > 0)) {
            this.flyingMotion.setTarget(1.0f, 0.1f);
            float yawDelta = Mth.m_14177_((float)(this.m_146908_() - this.f_19859_));
            float targetRoll = Mth.m_14036_((float)(-yawDelta * 2.5f), (float)-60.0f, (float)60.0f);
            this.flyingRoll.setTarget(targetRoll * ((float)Math.PI / 180), 0.05f);
            float targetPitch = (float)Mth.m_14136_((double)(-deltaMovement.f_82480_), (double)horizontalDistance);
            this.flyingPitch.setTarget(targetPitch, 0.05f);
        } else {
            this.flyingMotion.setTarget(0.0f, 0.1f);
            this.flyingPitch.setTarget(0.0f, 0.1f);
            this.flyingRoll.setTarget(0.0f, 0.1f);
        }
        if (this.m_6084_() && this.isNoActionActive() && this.m_6688_() == null) {
            if (flying && this.isPreparingToLand()) {
                if (this.canLandOnGround()) {
                    this.playActionSynced(this.actionDown);
                }
            } else if (!flying && this.isPreparingToFly()) {
                this.playActionSynced(this.actionUp);
            }
        }
        if (this.isTamed() && this.isFlying() && this.m_6688_() == null) {
            this.setFlightPhase(Flyer.FlightPhase.PREPARING_TO_LAND);
        }
        if (this.flightDelay > 0) {
            --this.flightDelay;
        }
        if (this.glideTicks > 0) {
            --this.glideTicks;
        }
    }

    @Override
    public void onRiderAttack(LivingEntity rider, Mount.AttackType type) {
        if (!this.isNoActionActive()) {
            return;
        }
        switch (type) {
            case PRIMARY: {
                if (this.isFlying()) {
                    this.playActionSynced(this.actionAttackFly);
                    break;
                }
                this.playActionSynced(this.actionAttackBite);
                break;
            }
            case SPECIAL: {
                this.playActionSynced(this.actionAttackShoot);
            }
        }
    }

    @Override
    public void m_7023_(Vec3 motion) {
        if (this.isFlying()) {
            motion = this.travelComponent(motion);
            this.travelFlying(motion);
        } else {
            super.m_7023_(motion);
        }
    }

    private void travelFlying(Vec3 travelVector) {
        if (this.m_20069_()) {
            this.m_19920_(0.02f, travelVector);
            this.m_6478_(MoverType.SELF, this.m_20184_());
            this.m_20256_(this.m_20184_().m_82490_((double)0.8f));
        } else if (this.m_20077_()) {
            this.m_19920_(0.02f, travelVector);
            this.m_6478_(MoverType.SELF, this.m_20184_());
            this.m_20256_(this.m_20184_().m_82490_(0.5));
        } else {
            this.m_19920_(0.08f, travelVector);
            this.m_6478_(MoverType.SELF, this.m_20184_());
            this.m_20256_(this.m_20184_().m_82490_((double)0.95f));
        }
        this.f_19812_ = true;
        this.m_267651_(false);
    }

    protected void m_6153_() {
        if (this.f_20919_ == 0) {
            if (this.isFlying()) {
                this.setFallingDead(true);
                this.m_20242_(false);
            } else if (!this.actionDeathGround.isActive()) {
                this.playActionSynced(this.actionDeathGround);
            }
        }
        if (this.isFallingDead() && !this.m_21525_()) {
            if (this.f_20919_ >= 10 && this.m_20160_()) {
                this.m_20153_();
            }
            if (this.m_20096_()) {
                this.m_20256_(Vec3.f_82478_);
                this.m_21567_(0.0f);
                this.playActionSynced(this.actionDeathAirLand);
                this.setFallingDead(false);
            }
        } else {
            if (this.m_20096_() && this.f_20919_ < 200) {
                this.f_20919_ = 200;
            }
            if (this.f_20919_ >= 260 && !this.m_9236_().m_5776_() && !this.m_213877_()) {
                this.m_9236_().m_7605_((Entity)this, (byte)60);
                this.m_142687_(Entity.RemovalReason.KILLED);
            }
        }
        ++this.f_20919_;
    }

    protected void m_7840_(double y, boolean onGround, BlockState state, BlockPos pos) {
    }

    public boolean m_142535_(float fallDistance, float multiplier, DamageSource source) {
        return false;
    }

    @Override
    public boolean isSitting() {
        return this.getState(ChorusoatlState::byId) == ChorusoatlState.SITTING;
    }

    @Override
    public void setSitting(boolean active) {
        this.setState(active ? ChorusoatlState.SITTING : ChorusoatlState.IDLE);
    }

    @Override
    protected EntityLocomotion createLocomotion() {
        return EntityLocomotion.of().speed(0.02f, 0.24f).transitionLimit(0.2f).responsiveness(0.3f).suppressTurns(this::isFlying).build();
    }

    protected MountComponent<ChorusoatlEntity> createMount() {
        return MountComponent.of(player -> this.isAdult() && this.isOwnedBy((LivingEntity)player) && !this.isSitting()).maxSpeed(0.7f).followYawSpeed(0.1f).followHeadSpeed(0.4f).inertia(0.05f).friction(0.3f).flying(this::isFlying).passengerOffset(0.0f, -0.5f, 0.0f).build(this);
    }

    @Override
    protected PetComponent<?> createPet() {
        return PetComponent.of().setBabyHealthMultiplier(0.21428572f).build(this);
    }

    protected BodyRotationControl m_7560_() {
        return new SmoothBodyControl((Mob)this, 35.0f, 15, 10.0f, 0.07f).setCanRotate(this::canRotate);
    }

    @Override
    public float getCameraDistance(float partialTick) {
        return 12.0f;
    }

    @Override
    protected boolean canRotate() {
        return !this.actionUp.isActive() && !this.actionDown.isActive() && !this.actionDeathGround.isActive() && !this.actionDeathAirLand.isActive() && !this.isSitting();
    }

    @Override
    protected boolean isMovementBlocked() {
        return this.actionDown.isActive() || this.actionDeathGround.isActive() || this.actionDeathAirLand.isActive() || this.actionAttackFly.isActive() || this.isSitting();
    }

    @Override
    public boolean tryToTame(Player player, RandomSource random) {
        if (random.m_188503_(2) == 0) {
            return true;
        }
        this.m_6710_((LivingEntity)player);
        this.playActionSynced(this.actionScream);
        return false;
    }

    private boolean canLandOnGround() {
        return Raycast.rayCastBlocks((Level)this.m_9236_(), (double)this.m_20185_(), (double)this.m_20186_(), (double)this.m_20189_(), (double)0.0, (double)(-this.m_20206_() / 2.0f), (double)0.0, BlockBehaviour.BlockStateBase::m_280296_, (CollisionContext.Mutable)this.collisionContext).hasHit();
    }

    public boolean isGliding() {
        if (!this.isFlying()) {
            return false;
        }
        if (this.glideTicks > 0) {
            return false;
        }
        int cycle = this.f_19797_ % 240;
        return cycle > 140;
    }

    private void screamForward(float screamRadius, float screamAngleCos, float pushPower) {
        AABB searchArea = this.m_20191_().m_82400_((double)screamRadius);
        List targets = this.m_9236_().m_45976_(LivingEntity.class, searchArea);
        Vec3 lookVector = EntityUtils.getViewVector((LivingEntity)this);
        for (LivingEntity target : targets) {
            if (target == this || target.m_6095_() == this.m_6095_() || !this.m_6779_(target)) continue;
            Vec3 toTarget = new Vec3(target.m_20185_() - this.m_20185_(), 0.0, target.m_20189_() - this.m_20189_()).m_82541_();
            Vec3 flatLook = new Vec3(lookVector.f_82479_, 0.0, lookVector.f_82481_).m_82541_();
            double dotProduct = flatLook.m_82526_(toTarget);
            if (!(dotProduct >= (double)screamAngleCos) || !this.m_142582_((Entity)target)) continue;
            target.m_5997_(toTarget.f_82479_ * (double)pushPower, 0.01, toTarget.f_82481_ * (double)pushPower);
        }
    }

    @Override
    public boolean isFlying() {
        return this.getState(ChorusoatlState::byId) == ChorusoatlState.FLYING;
    }

    @Override
    public void setFlightPhase(Flyer.FlightPhase phase) {
        if (this.flightDelay > 0) {
            return;
        }
        switch (phase) {
            case NONE: {
                this.f_19804_.m_135381_(DATA_FLIGHT_PHASE, (Object)0);
                break;
            }
            case PREPARING_TO_FLY: {
                this.f_19804_.m_135381_(DATA_FLIGHT_PHASE, (Object)1);
                break;
            }
            case PREPARING_TO_LAND: {
                this.f_19804_.m_135381_(DATA_FLIGHT_PHASE, (Object)2);
            }
        }
        this.flightDelay = 80;
    }

    @Override
    public Flyer.FlightPhase getFlightPhase() {
        byte phase = (Byte)this.f_19804_.m_135370_(DATA_FLIGHT_PHASE);
        return switch (phase) {
            case 1 -> Flyer.FlightPhase.PREPARING_TO_FLY;
            case 2 -> Flyer.FlightPhase.PREPARING_TO_LAND;
            default -> Flyer.FlightPhase.NONE;
        };
    }

    @Override
    public void setFlying(boolean active) {
        this.setState(active ? ChorusoatlState.FLYING : ChorusoatlState.IDLE);
        this.m_6210_();
    }

    public boolean isFallingDead() {
        return this.getState(ChorusoatlState::byId) == ChorusoatlState.FALLING_DEAD;
    }

    public void setFallingDead(boolean active) {
        this.setState(active ? ChorusoatlState.FALLING_DEAD : ChorusoatlState.IDLE);
    }

    public int m_8085_() {
        return 35;
    }

    @Override
    @Nullable
    public AgeableMob m_142606_(ServerLevel serverLevel, AgeableMob ageableMob) {
        return (AgeableMob)ADEntityTypes.CHORUSOATL.get().m_20615_((Level)serverLevel);
    }

    public EntityDimensions m_6972_(Pose pose) {
        EntityDimensions entitydimensions = super.m_6972_(pose);
        return this.isFlying() || this.actionUp.isActive() ? EntityDimensions.m_20398_((float)entitydimensions.f_20377_, (float)(2.6f * this.m_6134_())) : entitydimensions;
    }

    public float m_6121_() {
        return 2.0f;
    }

    @Nullable
    protected SoundEvent m_7515_() {
        return ADSoundEvents.CHORUSOATL_IDLE.get();
    }

    @Nullable
    protected SoundEvent m_5592_() {
        return ADSoundEvents.CHORUSOATL_DEATH_GROUND.get();
    }

    @Nullable
    protected SoundEvent m_7975_(DamageSource damageSource) {
        return ADSoundEvents.CHORUSOATL_HURT.get();
    }

    @Override
    public boolean m_6898_(ItemStack stack) {
        return stack.m_204117_(ADTags.Items.CHORUSOATL_EDIBLE_ITEM);
    }

    @Override
    protected AbstractComplexMob.TameableDinoSettings getSettings() {
        return ServerConfig.getEntitySettings().chorusoatl();
    }

    @Override
    public float getAttackRange() {
        return 3.0f;
    }

    public void m_7888_(int i) {
    }

    public boolean m_7132_() {
        return !this.actionUp.isActive() && !this.actionDown.isActive();
    }

    public void m_7199_(int i) {
        if (this.isFlying()) {
            if (this.canLandOnGround()) {
                this.playActionSynced(this.actionDown);
            }
        } else {
            this.playActionSynced(this.actionUp);
        }
    }

    public void m_8012_() {
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
}

