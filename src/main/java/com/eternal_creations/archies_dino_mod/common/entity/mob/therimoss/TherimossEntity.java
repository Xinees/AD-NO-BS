/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.astemir.astemirlib.re.common.action.ActionState
 *  com.astemir.astemirlib.re.common.world.entity.EntityLocomotion
 *  com.astemir.astemirlib.re.core.math.AMath
 *  com.astemir.astemirlib.re.core.math.tween.TweenFloat
 *  com.google.common.collect.Lists
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.network.syncher.EntityDataAccessor
 *  net.minecraft.network.syncher.EntityDataSerializer
 *  net.minecraft.network.syncher.EntityDataSerializers
 *  net.minecraft.network.syncher.SynchedEntityData
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.util.Mth
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.Container
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.damagesource.DamageTypes
 *  net.minecraft.world.entity.AgeableMob
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.Entity$RemovalReason
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.PlayerRideableJumping
 *  net.minecraft.world.entity.SlotAccess
 *  net.minecraft.world.entity.ai.attributes.AttributeInstance
 *  net.minecraft.world.entity.ai.attributes.AttributeModifier
 *  net.minecraft.world.entity.ai.attributes.AttributeModifier$Operation
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.entity.ai.control.BodyRotationControl
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.MenuType
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.Nullable
 *  org.joml.Vector3d
 */
package com.eternal_creations.archies_dino_mod.common.entity.mob.therimoss;

import com.astemir.astemirlib.re.common.action.ActionState;
import com.astemir.astemirlib.re.common.world.entity.EntityLocomotion;
import com.astemir.astemirlib.re.core.math.AMath;
import com.astemir.astemirlib.re.core.math.tween.TweenFloat;
import com.eternal_creations.archies_dino_mod.common.ADTags;
import com.eternal_creations.archies_dino_mod.common.entity.ADEntityTypes;
import com.eternal_creations.archies_dino_mod.common.entity.ai.control.SmoothBodyControl;
import com.eternal_creations.archies_dino_mod.common.entity.ai.control.SmoothLookControl;
import com.eternal_creations.archies_dino_mod.common.entity.ai.control.SmoothMoveControl;
import com.eternal_creations.archies_dino_mod.common.entity.base.AbstractComplexMob;
import com.eternal_creations.archies_dino_mod.common.entity.base.AbstractComplexMount;
import com.eternal_creations.archies_dino_mod.common.entity.base.EggLaying;
import com.eternal_creations.archies_dino_mod.common.entity.base.IMultipartEntity;
import com.eternal_creations.archies_dino_mod.common.entity.base.IPartEntity;
import com.eternal_creations.archies_dino_mod.common.entity.base.Mount;
import com.eternal_creations.archies_dino_mod.common.entity.base.SimplePart;
import com.eternal_creations.archies_dino_mod.common.entity.base.WallClimber;
import com.eternal_creations.archies_dino_mod.common.entity.component.MountComponent;
import com.eternal_creations.archies_dino_mod.common.entity.component.PetComponent;
import com.eternal_creations.archies_dino_mod.common.entity.mob.therimoss.ClimbController;
import com.eternal_creations.archies_dino_mod.common.entity.mob.therimoss.TherimossActions;
import com.eternal_creations.archies_dino_mod.common.entity.mob.therimoss.TherimossState;
import com.eternal_creations.archies_dino_mod.common.entity.mob.therimoss.ai.TherimossAI;
import com.eternal_creations.archies_dino_mod.common.entity.projectile.TherimossProjectileEntity;
import com.eternal_creations.archies_dino_mod.common.items.weapon.TherimossClawsItem;
import com.eternal_creations.archies_dino_mod.common.menu.ADMenus;
import com.eternal_creations.archies_dino_mod.common.menu.DinoMenu;
import com.eternal_creations.archies_dino_mod.common.sound.ADSoundEvents;
import com.eternal_creations.archies_dino_mod.server.ServerConfig;
import com.google.common.collect.Lists;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PlayerRideableJumping;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3d;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class TherimossEntity
extends AbstractComplexMount
implements PlayerRideableJumping,
WallClimber,
EggLaying,
IMultipartEntity {
    private static final UUID CLAWS_MODIFIER_UUID = UUID.fromString("556E1665-8B10-40C8-8F9D-CF9B1667F295");
    private static final EntityDataAccessor<Integer> DATA_HELD_PROJECTILE_ID = SynchedEntityData.m_135353_(TherimossEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135028_);
    private static final EntityDataAccessor<Optional<BlockPos>> DATA_START_MANTLE_POS = SynchedEntityData.m_135353_(TherimossEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135039_);
    private static final EntityDataAccessor<Optional<BlockPos>> DATA_MANTLE_POS = SynchedEntityData.m_135353_(TherimossEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135039_);
    private static final EntityDataAccessor<Boolean> DATA_HAS_EGG = SynchedEntityData.m_135353_(TherimossEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135035_);
    private static final EntityDataAccessor<Boolean> DATA_LAYING_EGG = SynchedEntityData.m_135353_(TherimossEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135035_);
    private static final EntityDataAccessor<ItemStack> DATA_CLAWS_ITEM = SynchedEntityData.m_135353_(TherimossEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135033_);
    private static final float DEFAULT_HEALTH = 65.0f;
    private static final float BABY_HEALTH_MULTIPLIER = 0.61538464f;
    private static final float DEFAULT_ATTACK_DAMAGE = 8.0f;
    public static final float CLAW_MULTIPLIER = 0.625f;
    public static final float BEAK_MULTIPLIER = 1.25f;
    public static final float THROW_MULTIPLIER = 0.625f;
    public static final byte FLAG_FEASTING = 4;
    private static final int AIR_TICKS_BEFORE_FALLING = 6;
    public static final AbstractComplexMob.TameableDinoSettings DEFAULT_STATS = new AbstractComplexMob.TameableDinoSettings(false, true, true, new ServerConfig.EntityStats(0.3, 65.0, 0.9, 0.0, 48.0, 8.0));
    public TherimossActions actions;
    public TweenFloat wallOffset = TweenFloat.of();
    public TweenFloat wallRotation = TweenFloat.of();
    public ClimbController climbController;
    public float climbVelocity;
    private final Vector3d jumpImpulse = new Vector3d();
    private boolean isPerformingLeap = false;
    private int airTicks = 0;
    private int climbTicks = 0;
    private int eatenFoodBlocks = 0;
    private final SimplePart<TherimossEntity> neck1;
    private final SimplePart<TherimossEntity> neck2;
    private final IPartEntity<?>[] parts;
    private int fallHoldTicks = 0;

    public TherimossEntity(EntityType<? extends AbstractComplexMount> entityType, Level level) {
        super((EntityType<? extends AbstractComplexMob>)entityType, level);
        this.f_21342_ = new SmoothMoveControl((Mob)this, 0.35f);
        this.f_21365_ = new SmoothLookControl((Mob)this, 15.0f, 0.3).setCanRotate(this::canRotate);
        this.climbController = new ClimbController(this);
        this.neck1 = new SimplePart<TherimossEntity>(this, 1.0f, 2.0f, 0.0, 2.5, 1.8f);
        this.neck2 = new SimplePart<TherimossEntity>(this, 1.0f, 1.0f, 0.0, 4.5, 2.5);
        this.parts = SimplePart.collectEntities(this, this.neck1, this.neck2);
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

    public void m_7350_(EntityDataAccessor<?> key) {
        super.m_7350_(key);
        if (key == AbstractComplexMob.DATA_MOB_STATE) {
            boolean climbing = this.isClimbing();
            this.m_20242_(climbing);
            this.wallOffset.setTarget(climbing ? 1.0f : 0.0f, 0.1f);
        }
    }

    @Override
    public void m_7380_(CompoundTag compound) {
        super.m_7380_(compound);
        compound.m_128365_("ClawsItem", (Tag)((ItemStack)this.f_19804_.m_135370_(DATA_CLAWS_ITEM)).m_41739_(new CompoundTag()));
        compound.m_128405_("airTicks", this.airTicks);
        compound.m_128405_("eatenFoodBlocks", this.eatenFoodBlocks);
        compound.m_128405_("climbTicks", this.climbTicks);
        compound.m_128379_("isPerformingLeap", this.isPerformingLeap);
    }

    @Override
    public void m_7378_(CompoundTag compound) {
        super.m_7378_(compound);
        if (compound.m_128441_("ClawsItem")) {
            this.setClawsItem(ItemStack.m_41712_((CompoundTag)compound.m_128469_("ClawsItem")));
        }
        if (compound.m_128441_("airTicks")) {
            this.airTicks = compound.m_128451_("airTicks");
        }
        if (compound.m_128441_("eatenFoodBlocks")) {
            this.eatenFoodBlocks = compound.m_128451_("eatenFoodBlocks");
        }
        if (compound.m_128441_("climbTicks")) {
            this.climbTicks = compound.m_128451_("climbTicks");
        }
        if (compound.m_128441_("isPerformingLeap")) {
            this.isPerformingLeap = compound.m_128471_("isPerformingLeap");
        }
    }

    protected void m_8099_() {
        TherimossAI.setupAI(this, this.f_21345_, this.f_21346_);
    }

    @Override
    protected void m_8097_() {
        super.m_8097_();
        this.f_19804_.m_135372_(DATA_HELD_PROJECTILE_ID, (Object)-1);
        this.f_19804_.m_135372_(DATA_START_MANTLE_POS, Optional.empty());
        this.f_19804_.m_135372_(DATA_MANTLE_POS, Optional.empty());
        this.f_19804_.m_135372_(DATA_LAYING_EGG, (Object)false);
        this.f_19804_.m_135372_(DATA_HAS_EGG, (Object)false);
        this.f_19804_.m_135372_(DATA_CLAWS_ITEM, (Object)ItemStack.f_41583_);
    }

    public boolean m_6785_(double distanceToClosestPlayer) {
        return false;
    }

    @Override
    protected Set<ActionState> makeActionSet() {
        this.actions = new TherimossActions(this);
        return this.actions.all();
    }

    @Override
    protected EntityLocomotion createLocomotion() {
        return EntityLocomotion.of().speed(0.02f, 0.24f).transitionLimit(0.2f).responsiveness(0.3f).build();
    }

    @Override
    protected MountComponent<?> createMount() {
        return MountComponent.of(player -> this.isAdult() && this.isOwnedBy((LivingEntity)player) && !this.isSitting()).followYawSpeed(0.2f).followHeadSpeed(0.4f).maxSpeed(0.8f).inertia(0.1f).friction(0.3f).passengerOffset(0.0f, 0.0f, 0.5f).build(this);
    }

    @Override
    protected PetComponent<?> createPet() {
        return PetComponent.of().canBeTamed(() -> this.hasFlag((byte)4)).setBabyHealthMultiplier(0.61538464f).build(this);
    }

    protected BodyRotationControl m_7560_() {
        return new SmoothBodyControl((Mob)this, 35.0f, 15, 10.0f, 0.07f).setCanRotate(this::canRotate);
    }

    public float m_5610_(BlockPos pos, LevelReader level) {
        return level.m_8055_(pos.m_7495_()).m_204336_(ADTags.Blocks.THERIMOSS_VALID_SPAWN) ? 10.0f : level.m_220419_(pos);
    }

    @Override
    public void m_8119_() {
        super.m_8119_();
        this.neck1.tick();
        this.neck2.tick();
        this.climbController.tick();
        this.tickFall();
        this.wallOffset.tick();
        this.wallRotation.tick();
    }

    private void tickFall() {
        boolean airborne;
        if (this.isClimbingOrTryingToClimb() || this.m_20068_() || this.m_21525_()) {
            return;
        }
        if (this.f_19797_ <= 1) {
            return;
        }
        boolean bl = airborne = !this.m_20096_() && !this.m_20069_();
        if (airborne) {
            ++this.airTicks;
            if (!this.isFalling() && this.isPerformingLeap) {
                this.setFalling(true);
                this.fallHoldTicks = 6;
            } else if (!this.isFalling() && this.airTicks >= 6) {
                this.setFalling(true);
                this.fallHoldTicks = 6;
            }
            if (this.fallHoldTicks > 0) {
                --this.fallHoldTicks;
            }
        } else {
            boolean wasFalling = this.isFalling();
            if (wasFalling && this.fallHoldTicks > 0) {
                --this.fallHoldTicks;
                return;
            }
            if (wasFalling) {
                this.setFalling(false);
                if (this.airTicks >= 8) {
                    this.m_216990_(ADSoundEvents.THERIMOSS_LAND.get());
                    this.playActionSynced(this.actions.land);
                }
            }
            this.isPerformingLeap = false;
            this.airTicks = 0;
            this.fallHoldTicks = 0;
        }
    }

    @Override
    public void m_7023_(Vec3 motion) {
        if (this.climbController.travel()) {
            return;
        }
        super.m_7023_(motion);
        float jumpX = (float)this.jumpImpulse.x;
        float jumpY = (float)this.jumpImpulse.y;
        float jumpZ = (float)this.jumpImpulse.z;
        if (!(AMath.isZero((float)jumpX) && AMath.isZero((float)jumpY) && AMath.isZero((float)jumpZ))) {
            this.m_20334_(this.jumpImpulse.x, this.jumpImpulse.y, this.jumpImpulse.z);
            this.jumpImpulse.set(0.0, 0.0, 0.0);
        }
    }

    protected void m_6153_() {
        if (this.f_20919_ == 0) {
            this.playActionSynced(this.actions.death);
        }
        ++this.f_20919_;
        if (this.f_20919_ >= 80 && !this.m_9236_().m_5776_() && !this.m_213877_()) {
            this.m_9236_().m_7605_((Entity)this, (byte)60);
            this.m_142687_(Entity.RemovalReason.KILLED);
        }
    }

    @Override
    public void onRiderAttack(LivingEntity rider, Mount.AttackType type) {
        if (!this.isNoActionActive()) {
            return;
        }
        if (this.isFalling()) {
            return;
        }
        switch (type) {
            case PRIMARY: {
                this.playActionSynced(this.actions.attackHit);
                break;
            }
            case SPECIAL: {
                this.playActionSynced(this.actions.attackShoot);
            }
        }
    }

    public void jumpFar() {
        float power;
        boolean climbing = this.isClimbing();
        float f = power = climbing ? 1.2f : 2.2f;
        if (!this.actions.land.isActive() && this.m_20096_() || climbing) {
            Vec3 deltaMovement = this.m_20184_();
            float yRot = this.m_146908_() * ((float)Math.PI / 180);
            this.jumpImpulse.set(deltaMovement.f_82479_ + (double)(climbing ? Mth.m_14031_((float)yRot) * power : -Mth.m_14031_((float)yRot) * power), (double)(0.5f * power * this.m_20098_() + this.m_285755_()), deltaMovement.f_82481_ + (double)(climbing ? -Mth.m_14089_((float)yRot) * power : Mth.m_14089_((float)yRot) * power));
            this.f_19812_ = true;
            this.isPerformingLeap = true;
            this.setClimbing(false);
        }
    }

    protected void m_20351_(Entity passenger) {
        super.m_20351_(passenger);
        this.jumpImpulse.set(0.0, 0.0, 0.0);
    }

    @Override
    protected boolean canRotate() {
        return !this.actions.up.isActive() && !this.actions.down.isActive() && !this.actions.death.isActive() && !this.isClimbing() && !this.isSitting();
    }

    @Override
    public boolean canMountRotateHead() {
        return super.canMountRotateHead() || this.isClimbing();
    }

    @Override
    protected boolean isMovementBlocked() {
        return this.actions.up.isActive() || this.actions.down.isActive() || this.actions.death.isActive() || this.actions.attackShoot.isActiveBeforeEnd(0.5f) || this.actions.attackHit.isActiveBeforeEnd(0.5f) || this.actions.attackBite.isActive() || this.actions.eat.isActive() || this.actions.jump.isActiveBeforeEnd(0.4f) || this.actions.land.isActiveBeforeEnd(0.2f) || this.actions.climbStart.isActive() || this.actions.climbFinish.isActive() || this.isSitting();
    }

    protected void m_7472_(DamageSource source, int looting, boolean recentlyHit) {
        super.m_7472_(source, looting, recentlyHit);
        ItemStack itemStack = this.getClawsItem();
        if (!itemStack.m_41619_()) {
            this.m_19983_(itemStack);
        }
    }

    public boolean m_142535_(float fallDistance, float multiplier, DamageSource source) {
        if (fallDistance > 50.0f) {
            return super.m_142535_(fallDistance, multiplier, source);
        }
        return false;
    }

    public void m_7888_(int i) {
    }

    @Override
    public boolean tryToTame(Player player, RandomSource random) {
        if (this.eatenFoodBlocks >= 2) {
            boolean success;
            boolean bl = success = random.m_188501_() < 0.14f;
            if (success) {
                this.setFlag((byte)4, false);
                this.resetEatenFoodBlocks();
            }
            return success;
        }
        return false;
    }

    public void handleBlockBreak(Player player, BlockState state) {
        if (this.isTamed()) {
            return;
        }
        if (this.f_19796_.m_188501_() < 0.25f) {
            this.m_6710_((LivingEntity)player);
        }
    }

    public void incrementEatenFoodBlocks() {
        ++this.eatenFoodBlocks;
    }

    public void resetEatenFoodBlocks() {
        this.eatenFoodBlocks = 0;
    }

    @Override
    public boolean m_6898_(ItemStack stack) {
        return stack.m_204117_(ADTags.Items.THERIMOSS_EDIBLE_ITEM);
    }

    @Override
    protected AbstractComplexMob.TameableDinoSettings getSettings() {
        return ServerConfig.getEntitySettings().therimoss();
    }

    public boolean m_7132_() {
        return !this.actions.jump.isActive();
    }

    public void m_7199_(int i) {
        if (this.m_20096_() && !this.isFalling() && this.isNoActionActive() || this.isClimbing()) {
            this.playActionSynced(this.actions.jump);
        }
    }

    @Override
    public InteractionResult m_6071_(Player player, InteractionHand hand) {
        if (this.isOwnedBy((LivingEntity)player)) {
            ItemStack itemStack = player.m_21120_(hand);
            if (itemStack.m_150930_(Items.f_42574_)) {
                ItemStack clawsItem = this.getClawsItem();
                if (!clawsItem.m_41619_()) {
                    this.m_19983_(clawsItem);
                }
                this.setClawsItem(ItemStack.f_41583_);
                return InteractionResult.m_19078_((boolean)this.m_9236_().f_46443_);
            }
            if (itemStack.m_41720_() instanceof TherimossClawsItem) {
                ItemStack clawsItem = this.getClawsItem();
                if (!clawsItem.m_41619_()) {
                    this.m_19983_(clawsItem);
                }
                this.setClawsItem(itemStack.m_41777_());
                itemStack.m_41774_(1);
                return InteractionResult.m_19078_((boolean)this.m_9236_().f_46443_);
            }
        }
        return super.m_6071_(player, hand);
    }

    public void m_8012_() {
    }

    public int m_21529_() {
        return 2;
    }

    @Override
    public boolean isSitting() {
        return this.getState(TherimossState::byId) == TherimossState.SITTING;
    }

    @Override
    public void setSitting(boolean active) {
        if (!this.isNoActionActive()) {
            return;
        }
        if (this.isSitting()) {
            this.playActionSynced(this.actions.up);
        } else {
            this.playActionSynced(this.actions.down);
        }
    }

    public boolean isFalling() {
        return this.getState(TherimossState::byId) == TherimossState.FALLING;
    }

    public void setFalling(boolean active) {
        this.setState(active ? TherimossState.FALLING : TherimossState.IDLE);
    }

    public int getAirTicks() {
        return this.airTicks;
    }

    public void setAirTicks(int airTicks) {
        this.airTicks = airTicks;
    }

    public boolean isClimbingOrTryingToClimb() {
        return this.isClimbing() || this.actions.isAscendingOrDescendingOnWall();
    }

    @Override
    public boolean isClimbing() {
        return this.getState(TherimossState::byId) == TherimossState.CLIMBING;
    }

    @Override
    public void setClimbing(boolean active) {
        this.setState(active ? TherimossState.CLIMBING : TherimossState.IDLE);
    }

    public void setHeldProjectile(@Nullable TherimossProjectileEntity projectile) {
        this.f_19804_.m_135381_(DATA_HELD_PROJECTILE_ID, (Object)(projectile != null ? projectile.m_19879_() : -1));
    }

    @Nullable
    public TherimossProjectileEntity getHeldProjectile() {
        TherimossProjectileEntity projectile;
        Entity entity = this.m_9236_().m_6815_(((Integer)this.f_19804_.m_135370_(DATA_HELD_PROJECTILE_ID)).intValue());
        return entity instanceof TherimossProjectileEntity ? (projectile = (TherimossProjectileEntity)entity) : null;
    }

    @Override
    public void m_5757_(Container container) {
        ItemStack clawsItem = this.getClawsItem();
        super.m_5757_(container);
        if (this.f_19797_ > 20 && !clawsItem.m_150930_(this.getClawsItem().m_41720_())) {
            this.m_9236_().m_6269_(null, (Entity)this, ADSoundEvents.DINO_EQUIP.get(), SoundSource.NEUTRAL, 0.5f, 1.0f);
        }
    }

    @Override
    protected void updateContainerEquipment() {
        super.updateContainerEquipment();
        if (!this.m_9236_().f_46443_) {
            ItemStack itemStack = this.inventory.m_8020_(1);
            this.f_19804_.m_135381_(DATA_CLAWS_ITEM, (Object)itemStack);
            AttributeInstance attribute = this.m_21051_(Attributes.f_22281_);
            if (attribute == null) {
                return;
            }
            attribute.m_22120_(CLAWS_MODIFIER_UUID);
            if (itemStack.m_41720_() instanceof TherimossClawsItem) {
                double damage = 0.0;
                for (AttributeModifier mod : itemStack.m_41638_(EquipmentSlot.MAINHAND).get((Object)Attributes.f_22281_)) {
                    damage += mod.m_22218_();
                }
                if ((damage *= 0.5) != 0.0) {
                    attribute.m_22118_(new AttributeModifier(CLAWS_MODIFIER_UUID, "Claws bonus", damage, AttributeModifier.Operation.ADDITION));
                }
            } else {
                attribute.m_22120_(CLAWS_MODIFIER_UUID);
            }
        }
    }

    public void setClawsItem(ItemStack stack) {
        this.inventory.m_6836_(1, stack);
    }

    public boolean hasClaws() {
        return !this.getClawsItem().m_41619_();
    }

    public ItemStack getClawsItem() {
        return (ItemStack)this.f_19804_.m_135370_(DATA_CLAWS_ITEM);
    }

    public void setMantleStartPos(BlockPos blockPos) {
        this.f_19804_.m_135381_(DATA_START_MANTLE_POS, Optional.of(blockPos));
    }

    public void setMantlePos(BlockPos blockPos) {
        this.f_19804_.m_135381_(DATA_MANTLE_POS, Optional.of(blockPos));
    }

    public Optional<BlockPos> getMantleStartPos() {
        return (Optional)this.f_19804_.m_135370_(DATA_START_MANTLE_POS);
    }

    public Optional<BlockPos> getMantlePos() {
        return (Optional)this.f_19804_.m_135370_(DATA_MANTLE_POS);
    }

    public boolean m_6673_(DamageSource source) {
        if (source.m_276093_(DamageTypes.f_268612_) && this.isClimbingOrTryingToClimb()) {
            return true;
        }
        return super.m_6673_(source);
    }

    @Override
    public float getCameraDistance(float partialTick) {
        return 8.0f;
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
    @Nullable
    public AgeableMob m_142606_(ServerLevel serverLevel, AgeableMob ageableMob) {
        return (AgeableMob)ADEntityTypes.THERIMOSS.get().m_20615_((Level)serverLevel);
    }

    @Nullable
    protected SoundEvent m_7515_() {
        return ADSoundEvents.THERIMOSS_IDLE.get();
    }

    @Nullable
    protected SoundEvent m_7975_(DamageSource damageSource) {
        return ADSoundEvents.THERIMOSS_HURT.get();
    }

    @Nullable
    protected SoundEvent m_5592_() {
        return ADSoundEvents.THERIMOSS_DEATH.get();
    }

    public ItemStack m_6844_(EquipmentSlot slot) {
        if (slot == EquipmentSlot.MAINHAND) {
            return this.getClawsItem();
        }
        return super.m_6844_(slot);
    }

    public void m_21008_(InteractionHand hand, ItemStack stack) {
        if (hand == InteractionHand.MAIN_HAND) {
            this.setClawsItem(stack);
        }
        super.m_21008_(hand, stack);
    }

    public void m_8061_(EquipmentSlot slot, ItemStack stack) {
        if (slot == EquipmentSlot.MAINHAND) {
            this.setClawsItem(stack);
        }
        super.m_8061_(slot, stack);
    }

    public Iterable<ItemStack> m_6167_() {
        return Lists.newArrayList((Object[])new ItemStack[]{this.getClawsItem(), this.m_21206_()});
    }

    @Override
    protected int getInventorySize() {
        return 2;
    }

    @Override
    public MenuType<? extends DinoMenu> getMenuType() {
        return ADMenus.THERIMOSS.get();
    }

    @Override
    public SlotAccess m_141942_(int slot) {
        int i = slot - 400;
        if (i >= 0 && i < this.getInventorySize() && i < this.inventory.m_6643_()) {
            if (i == 0) {
                return this.createEquipmentSlotAccess(i, itemStack -> itemStack.m_41619_() || itemStack.m_150930_(Items.f_42450_));
            }
            return this.createEquipmentSlotAccess(i, itemStack -> itemStack.m_41619_() || itemStack.m_41720_() instanceof TherimossClawsItem);
        }
        int j = slot - 500 + this.getInventorySize();
        return j >= this.getInventorySize() && j < this.inventory.m_6643_() ? SlotAccess.m_147292_((Container)this.inventory, (int)j) : super.m_141942_(slot);
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

    public boolean isGroundedForAnimation() {
        if (this.m_20069_()) {
            return false;
        }
        return this.m_20096_() || this.airTicks < 6 && !this.isFalling();
    }
}

