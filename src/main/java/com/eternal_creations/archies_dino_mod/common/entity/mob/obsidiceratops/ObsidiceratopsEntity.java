/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.astemir.astemirlib.re.common.action.ActionState
 *  com.astemir.astemirlib.re.common.action.ActionType
 *  com.astemir.astemirlib.re.common.world.entity.EntityLocomotion
 *  com.astemir.astemirlib.re.core.math.tween.TweenFloat
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.syncher.EntityDataAccessor
 *  net.minecraft.network.syncher.EntityDataSerializer
 *  net.minecraft.network.syncher.EntityDataSerializers
 *  net.minecraft.network.syncher.SynchedEntityData
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.tags.FluidTags
 *  net.minecraft.util.Mth
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.DifficultyInstance
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.AgeableMob
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.Entity$RemovalReason
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.MobSpawnType
 *  net.minecraft.world.entity.SpawnGroupData
 *  net.minecraft.world.entity.ai.control.BodyRotationControl
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.ItemUtils
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.GameRules
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.ServerLevelAccessor
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.LiquidBlock
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.material.FluidState
 *  net.minecraft.world.level.pathfinder.BlockPathTypes
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 *  net.minecraft.world.phys.shapes.CollisionContext
 *  org.jetbrains.annotations.Nullable
 */
package com.eternal_creations.archies_dino_mod.common.entity.mob.obsidiceratops;

import com.astemir.astemirlib.re.common.action.ActionState;
import com.astemir.astemirlib.re.common.action.ActionType;
import com.astemir.astemirlib.re.common.world.entity.EntityLocomotion;
import com.astemir.astemirlib.re.core.math.tween.TweenFloat;
import com.eternal_creations.archies_dino_mod.common.ADTags;
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
import com.eternal_creations.archies_dino_mod.common.entity.mob.obsidiceratops.ai.ObsidiceratopsAI;
import com.eternal_creations.archies_dino_mod.common.entity.mob.obsidiceratops.ai.ObsidiceratopsState;
import com.eternal_creations.archies_dino_mod.common.sound.ADSoundEvents;
import com.eternal_creations.archies_dino_mod.server.ServerConfig;
import java.util.Set;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import org.jetbrains.annotations.Nullable;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ObsidiceratopsEntity
extends AbstractComplexMount
implements ChargeAttacker,
EggLaying,
IMultipartEntity {
    private static final String DATA_KEY_LAVA_STATE = "LavaState";
    private static final String DATA_KEY_LAVA_COUNT = "LavaCount";
    private static final String DATA_KEY_FED_COUNT = "FedTameCount";
    public static final EntityDataAccessor<Byte> DATA_LAVA_STATE = SynchedEntityData.m_135353_(ObsidiceratopsEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135027_);
    private static final EntityDataAccessor<Boolean> DATA_HAS_EGG = SynchedEntityData.m_135353_(ObsidiceratopsEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135035_);
    private static final EntityDataAccessor<Boolean> DATA_LAYING_EGG = SynchedEntityData.m_135353_(ObsidiceratopsEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135035_);
    private static final EntityDataAccessor<Integer> DATA_LAVA_COUNT = SynchedEntityData.m_135353_(ObsidiceratopsEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135028_);
    private static final float DEFAULT_HEALTH = 70.0f;
    private static final float BABY_HEALTH_MULTIPLIER = 0.64285713f;
    private static final float TAMED_HEALTH_MULTIPLIER = 1.5714285f;
    private static final float DEFAULT_ATTACK_DAMAGE = 7.5f;
    private static final float HEADBUTT_MULTIPLIER = 1.0666667f;
    private static final float TAIL_MULTIPLIER = 0.93333334f;
    public static final AbstractComplexMob.TameableDinoSettings DEFAULT_STATS = new AbstractComplexMob.TameableDinoSettings(false, true, true, new ServerConfig.EntityStats(0.3, 70.0, 0.9, 0.0, 48.0, 7.5));
    public ActionState actionDeath;
    public ActionState actionEat;
    public ActionState actionAttackHit;
    public ActionState actionAttackTail;
    public ActionState actionDrink1;
    public ActionState actionDrink2;
    public ActionState actionWarn;
    public TweenFloat lavaStateProgress = TweenFloat.of();
    private LavaState currentLavaState = LavaState.NORMAL;
    private LavaState previousLavaState = LavaState.NORMAL;
    private long lavaStarveTicks;
    private int fedForTameCount = 0;
    private final SimplePart<ObsidiceratopsEntity> head;
    private final SimplePart<ObsidiceratopsEntity> tail;
    private final IPartEntity<?>[] parts;

    public ObsidiceratopsEntity(EntityType<? extends AbstractComplexMount> entityType, Level level) {
        super((EntityType<? extends AbstractComplexMob>)entityType, level);
        this.f_21342_ = new SmoothMoveControl((Mob)this, 0.25f);
        this.f_21365_ = new SmoothLookControl((Mob)this, 15.0f, 0.3).setCanRotate(this::canRotate);
        this.m_21441_(BlockPathTypes.WATER, -1.0f);
        this.m_21441_(BlockPathTypes.LAVA, 0.0f);
        this.m_21441_(BlockPathTypes.DANGER_FIRE, 0.0f);
        this.m_21441_(BlockPathTypes.DAMAGE_FIRE, 0.0f);
        this.head = new SimplePart<ObsidiceratopsEntity>(this, 1.5f, 1.2f, 0.0, 1.5, 2.5);
        this.tail = new SimplePart<ObsidiceratopsEntity>(this, 1.5f, 1.2f, 0.0, 1.0, -2.5);
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

    private void floatInLava() {
        if (this.m_20077_()) {
            CollisionContext collisioncontext = CollisionContext.m_82750_((Entity)this);
            if (collisioncontext.m_6513_(LiquidBlock.f_54690_, this.m_20183_(), true) && !this.m_9236_().m_6425_(this.m_20183_().m_7494_()).m_205070_(FluidTags.f_13132_)) {
                this.m_6853_(true);
            } else {
                this.m_20256_(this.m_20184_().m_82490_(0.5).m_82520_(0.0, 0.1, 0.0));
            }
        }
    }

    public SpawnGroupData m_6518_(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag dataTag) {
        this.setLavaState(LavaState.VALUES[level.m_213780_().m_188503_(LavaState.VALUES.length)]);
        return super.m_6518_(level, difficulty, reason, spawnData, dataTag);
    }

    public boolean m_6785_(double distanceToClosestPlayer) {
        return false;
    }

    protected void m_8099_() {
        ObsidiceratopsAI.setupAI(this, this.f_21345_, this.f_21346_);
    }

    @Override
    protected void m_8097_() {
        super.m_8097_();
        this.f_19804_.m_135372_(DATA_LAVA_STATE, (Object)0);
        this.f_19804_.m_135372_(DATA_LAYING_EGG, (Object)false);
        this.f_19804_.m_135372_(DATA_HAS_EGG, (Object)false);
        this.f_19804_.m_135372_(DATA_LAVA_COUNT, (Object)0);
    }

    @Override
    protected EntityLocomotion createLocomotion() {
        return EntityLocomotion.of().speed(0.02f, 0.24f).transitionLimit(0.2f).responsiveness(0.3f).build();
    }

    protected MountComponent<ObsidiceratopsEntity> createMount() {
        return MountComponent.of(player -> this.isAdult() && this.isOwnedBy((LivingEntity)player) && !this.isSitting()).maxSpeed(0.7f).followYawSpeed(0.1f).followHeadSpeed(0.4f).inertia(0.05f).friction(0.3f).passengerOffset(0.0f, 0.5f, 0.5f).build(this);
    }

    protected PetComponent<ObsidiceratopsEntity> createPet() {
        return PetComponent.of().setBabyHealthMultiplier(0.64285713f).setTamedHealthMultiplier(1.5714285f).build(this);
    }

    protected BodyRotationControl m_7560_() {
        return new SmoothBodyControl((Mob)this, 35.0f, 15, 10.0f, 0.07f).setCanRotate(this::canRotate);
    }

    public void m_7350_(EntityDataAccessor<?> key) {
        LavaState newState;
        if (DATA_LAVA_STATE.equals(key) && (newState = this.getLavaStateFromData()) != this.currentLavaState) {
            this.previousLavaState = this.currentLavaState;
            this.currentLavaState = newState;
            this.lavaStateProgress.setValue(0.0f);
            this.lavaStateProgress.setTarget(1.0f, 0.05f);
        }
        super.m_7350_(key);
    }

    @Override
    public void m_7378_(CompoundTag compound) {
        super.m_7378_(compound);
        if (compound.m_128441_(DATA_KEY_LAVA_STATE)) {
            this.setLavaState(LavaState.getByValue(compound.m_128445_(DATA_KEY_LAVA_STATE)));
        }
        if (compound.m_128441_("HasEgg")) {
            this.f_19804_.m_135381_(DATA_HAS_EGG, (Object)compound.m_128471_("HasEgg"));
        }
        if (compound.m_128441_(DATA_KEY_LAVA_COUNT)) {
            this.f_19804_.m_135381_(DATA_LAVA_COUNT, (Object)compound.m_128451_(DATA_KEY_LAVA_COUNT));
        }
        if (compound.m_128441_(DATA_KEY_FED_COUNT)) {
            this.fedForTameCount = compound.m_128451_(DATA_KEY_FED_COUNT);
        }
    }

    @Override
    public void m_7380_(CompoundTag compound) {
        super.m_7380_(compound);
        compound.m_128405_(DATA_KEY_LAVA_COUNT, ((Integer)this.f_19804_.m_135370_(DATA_LAVA_COUNT)).intValue());
        compound.m_128344_(DATA_KEY_LAVA_STATE, ((Byte)this.f_19804_.m_135370_(DATA_LAVA_STATE)).byteValue());
        compound.m_128379_("HasEgg", ((Boolean)this.f_19804_.m_135370_(DATA_HAS_EGG)).booleanValue());
        compound.m_128405_(DATA_KEY_FED_COUNT, this.fedForTameCount);
    }

    @Override
    protected Set<ActionState> makeActionSet() {
        this.actionDeath = ActionState.of((ActionType)ADActionTypes.DEATH, (float)1.5f, (float)0.1f, (float)0.1f).holdOnLastFrame();
        this.actionEat = ActionState.of((ActionType)ADActionTypes.EAT, (float)8.0f, (float)0.1f, (float)0.5f);
        this.actionAttackHit = ActionState.of((ActionType)ADActionTypes.ATTACK_HIT, (float)2.625f, (float)0.1f, (float)0.4f).onStart(() -> this.m_216990_(ADSoundEvents.OBSIDICERATOPS_ATTACK.get())).onTick(tick -> {
            if (tick == 10) {
                this.attackActionTargetEntity(1.0666667f, 0.2f);
            }
        }).exitTime(2.3f);
        this.actionAttackTail = ActionState.of((ActionType)ADActionTypes.ATTACK_TAIL, (float)3.2917f, (float)0.2f, (float)0.5f).onStart(() -> this.m_216990_(ADSoundEvents.OBSIDICERATOPS_ATTACK.get())).onTick(tick -> {
            if (tick == 20) {
                this.attackActionTargetEntity(0.93333334f, 0.8f);
            }
        }).exitTime(3.1f);
        this.actionDrink1 = ActionState.of((ActionType)ADActionTypes.DRINK1, (float)4.0f, (float)0.2f, (float)0.6f).onStart(() -> this.m_216990_(ADSoundEvents.OBSIDICERATOPS_DRINK.get())).exitTime(3.5f);
        this.actionDrink2 = ActionState.of((ActionType)ADActionTypes.DRINK2, (float)8.0f, (float)0.2f, (float)1.5f).exitTime(7.5f).onStart(() -> this.m_216990_(ADSoundEvents.OBSIDICERATOPS_DRINK.get()));
        this.actionWarn = ActionState.of((ActionType)ADActionTypes.WARN, (float)4.0f, (float)0.2f, (float)0.6f).onStart(() -> this.m_216990_(ADSoundEvents.OBSIDICERATOPS_AGRO.get())).exitTime(3.6f);
        return Set.of(this.actionDeath, this.actionEat, this.actionAttackHit, this.actionAttackTail, this.actionDrink1, this.actionDrink2, this.actionWarn);
    }

    public float m_5610_(BlockPos pos, LevelReader level) {
        return level.m_8055_(pos.m_7495_()).m_204336_(ADTags.Blocks.OBSIDICERATOPS_VALID_SPAWN) ? 10.0f : level.m_220419_(pos);
    }

    @Override
    public void m_8119_() {
        super.m_8119_();
        this.lavaStateProgress.tick();
        this.floatInLava();
        this.m_20101_();
        if (this.m_9236_().f_46443_) {
            Vec3 delta = this.m_20184_();
            if (this.m_20077_() && delta.m_165924_() > (double)0.01f) {
                for (int i = 0; i < 2; ++i) {
                    this.m_9236_().m_7106_((ParticleOptions)ParticleTypes.f_123756_, this.m_20208_(0.8f) - delta.f_82479_, this.m_20186_(), this.m_20262_(0.8f) - delta.f_82481_, -delta.f_82479_, (double)this.f_19796_.m_188501_(), -delta.f_82481_);
                }
            }
        }
        if (this.isCharging() && this.m_9236_().m_46469_().m_46207_(GameRules.f_46132_)) {
            boolean flag = false;
            AABB aabb = this.m_20191_().m_82400_(1.0);
            for (BlockPos blockpos : BlockPos.m_121976_((int)Mth.m_14107_((double)aabb.f_82288_), (int)Mth.m_14107_((double)aabb.f_82289_), (int)Mth.m_14107_((double)aabb.f_82290_), (int)Mth.m_14107_((double)aabb.f_82291_), (int)Mth.m_14107_((double)aabb.f_82292_), (int)Mth.m_14107_((double)aabb.f_82293_))) {
                BlockState blockState = this.m_9236_().m_8055_(blockpos);
                if (!blockState.m_204336_(ADTags.Blocks.OBSIDICERATOPS_WILD_BREAK)) continue;
                flag = this.m_9236_().m_46953_(blockpos, false, (Entity)this) || flag;
            }
        }
    }

    public void m_6075_() {
        super.m_6075_();
        this.head.tick();
        this.tail.tick();
    }

    @Override
    protected void m_8024_() {
        super.m_8024_();
        if (!this.m_6162_()) {
            LavaState state = this.getLavaState();
            if (state != LavaState.EMPTY) {
                ++this.lavaStarveTicks;
                if (this.lavaStarveTicks >= 1800L && this.f_19796_.m_188503_(10) == 0) {
                    this.starveLava();
                }
            } else if (this.isTamed()) {
                ++this.lavaStarveTicks;
                if (this.lavaStarveTicks >= 1800L) {
                    this.fedForTameCount = 0;
                }
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
                this.playActionSynced(this.actionAttackHit);
                break;
            }
            case SPECIAL: {
                this.playActionSynced(this.actionAttackTail);
            }
        }
    }

    protected void m_6153_() {
        if (this.f_20919_++ == 0) {
            this.playActionSynced(this.actionDeath);
        } else if (this.f_20919_ >= 60 && !this.m_9236_().m_5776_() && !this.m_213877_()) {
            this.m_142687_(Entity.RemovalReason.KILLED);
        }
    }

    @Override
    public InteractionResult m_6071_(Player player, InteractionHand hand) {
        ItemStack itemStack = player.m_21120_(hand);
        int lavaCount = this.getLavaCount();
        if (this.isTamed() && !this.m_6162_()) {
            if (itemStack.m_150930_(Items.f_42448_) && lavaCount < 15) {
                this.setLavaCount(lavaCount + 1);
                this.playSound(ADSoundEvents.OBSIDICERATOPS_FEED_LAVA);
                ItemStack emptyBucket = ItemUtils.m_41813_((ItemStack)itemStack, (Player)player, (ItemStack)Items.f_42446_.m_7968_());
                player.m_21008_(hand, emptyBucket);
                return InteractionResult.m_19078_((boolean)this.m_9236_().f_46443_);
            }
            if (itemStack.m_150930_(Items.f_42446_) && lavaCount > 0) {
                this.setLavaCount(lavaCount - 1);
                this.playSound(ADSoundEvents.OBSIDICERATOPS_HARVEST_LAVA);
                ItemStack filledBucket = ItemUtils.m_41813_((ItemStack)itemStack, (Player)player, (ItemStack)Items.f_42448_.m_7968_());
                player.m_21008_(hand, filledBucket);
                return InteractionResult.m_19078_((boolean)this.m_9236_().f_46443_);
            }
        }
        return super.m_6071_(player, hand);
    }

    @Override
    protected boolean canRotate() {
        return !this.actionDeath.isActive() && !this.actionAttackTail.isActive() && !this.actionEat.isActive() && !this.actionDrink1.isActive() && !this.actionDrink2.isActive() && !this.isSitting();
    }

    @Override
    protected boolean isMovementBlocked() {
        return this.actionDeath.isActive() || this.actionAttackTail.isActive() || this.actionEat.isActive() || this.actionDrink1.isActive() || this.actionDrink2.isActive() || this.actionWarn.isActive() || this.isSitting();
    }

    public boolean m_203441_(FluidState fluidState) {
        return fluidState.m_205070_(FluidTags.f_13132_);
    }

    @Override
    public boolean isSitting() {
        return this.getState(ObsidiceratopsState::byId) == ObsidiceratopsState.SITTING;
    }

    @Override
    public void setSitting(boolean active) {
        this.setState(active ? ObsidiceratopsState.SITTING : ObsidiceratopsState.IDLE);
    }

    public boolean isThirstyForLava() {
        return this.getLavaStateFromData() != LavaState.NORMAL;
    }

    public int getLavaCount() {
        return (Integer)this.f_19804_.m_135370_(DATA_LAVA_COUNT);
    }

    public void setLavaCount(int count) {
        if (count == 1) {
            this.setLavaState(LavaState.LOW);
        }
        if (count > 1) {
            this.setLavaState(LavaState.NORMAL);
        }
        this.f_19804_.m_135381_(DATA_LAVA_COUNT, (Object)count);
    }

    public void drinkLava() {
        this.setLavaState(this.getLavaState().nextState());
    }

    public void starveLava() {
        this.setLavaState(this.getLavaState().previousState());
        if (this.getLavaState() == LavaState.EMPTY) {
            this.fedForTameCount = 0;
        }
    }

    public void setLavaState(LavaState state) {
        this.lavaStarveTicks = 0L;
        this.f_19804_.m_135381_(DATA_LAVA_STATE, (Object)state.value);
    }

    private LavaState getLavaStateFromData() {
        return LavaState.getByValue((Byte)this.f_19804_.m_135370_(DATA_LAVA_STATE));
    }

    public LavaState getPreviousLavaState() {
        return this.previousLavaState;
    }

    public LavaState getLavaState() {
        return this.currentLavaState;
    }

    @Override
    public boolean m_6898_(ItemStack stack) {
        return stack.m_150930_(Blocks.f_50450_.m_5456_());
    }

    @Override
    protected AbstractComplexMob.TameableDinoSettings getSettings() {
        return ServerConfig.getEntitySettings().obsidiceratops();
    }

    @Override
    public boolean tryToTame(Player player, RandomSource random) {
        boolean success;
        ++this.fedForTameCount;
        if (this.fedForTameCount < 14) {
            return false;
        }
        boolean bl = success = random.m_188503_(10) == 0;
        if (success) {
            this.fedForTameCount = 0;
        }
        return success;
    }

    @Override
    public boolean isCharging() {
        return this.getState(ObsidiceratopsState::byId) == ObsidiceratopsState.CHARGING;
    }

    @Override
    public void setCharging(boolean active) {
        this.setState(active ? ObsidiceratopsState.CHARGING : ObsidiceratopsState.IDLE);
    }

    public boolean m_6060_() {
        return false;
    }

    @Override
    @Nullable
    public AgeableMob m_142606_(ServerLevel serverLevel, AgeableMob ageableMob) {
        return (AgeableMob)ADEntityTypes.OBSIDICERATOPS.get().m_20615_((Level)serverLevel);
    }

    @Override
    public float getCameraDistance(float partialTick) {
        return 8.0f;
    }

    @Override
    public float getAttackRange() {
        return 3.0f;
    }

    @Nullable
    protected SoundEvent m_7515_() {
        return ADSoundEvents.OBSIDICERATOPS_IDLE.get();
    }

    @Nullable
    protected SoundEvent m_5592_() {
        return ADSoundEvents.OBSIDICERATOPS_DEATH.get();
    }

    @Nullable
    protected SoundEvent m_7975_(DamageSource damageSource) {
        return ADSoundEvents.OBSIDICERATOPS_HURT.get();
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

    public static enum LavaState {
        EMPTY(2),
        LOW(1),
        NORMAL(0);

        public static final LavaState[] VALUES;
        private final byte value;

        private LavaState(byte value) {
            this.value = value;
        }

        public LavaState nextState() {
            int nextOrdinal = this.ordinal() + 1;
            if (nextOrdinal >= VALUES.length) {
                return NORMAL;
            }
            return VALUES[nextOrdinal];
        }

        public LavaState previousState() {
            int prevOrdinal = this.ordinal() - 1;
            if (prevOrdinal < 0) {
                return EMPTY;
            }
            return VALUES[prevOrdinal];
        }

        public static LavaState getByValue(byte value) {
            for (LavaState lavaState : VALUES) {
                if (lavaState.value != value) continue;
                return lavaState;
            }
            return NORMAL;
        }

        static {
            VALUES = LavaState.values();
        }
    }
}

