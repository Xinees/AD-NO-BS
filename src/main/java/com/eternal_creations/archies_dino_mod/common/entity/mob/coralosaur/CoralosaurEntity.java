/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.astemir.astemirlib.re.client.model.core.system.TransformSystem
 *  com.astemir.astemirlib.re.common.action.ActionSequence
 *  com.astemir.astemirlib.re.common.action.ActionState
 *  com.astemir.astemirlib.re.common.action.ActionType
 *  com.astemir.astemirlib.re.common.action.SyncedActionHolder
 *  com.astemir.astemirlib.re.common.extension.EntityExtension
 *  com.astemir.astemirlib.re.common.world.entity.EntityLocomotion
 *  com.astemir.astemirlib.re.core.math.AMath
 *  com.astemir.astemirlib.re.core.math.Easing
 *  com.astemir.astemirlib.re.core.math.Easing$Simple
 *  com.astemir.astemirlib.re.core.math.Transform
 *  com.astemir.astemirlib.re.core.math.tween.TweenFloat
 *  com.google.common.collect.Lists
 *  com.mojang.datafixers.util.Pair
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Direction$Axis
 *  net.minecraft.core.Vec3i
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.syncher.EntityDataAccessor
 *  net.minecraft.network.syncher.EntityDataSerializer
 *  net.minecraft.network.syncher.EntityDataSerializers
 *  net.minecraft.network.syncher.SynchedEntityData
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.util.Mth
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.Clearable
 *  net.minecraft.world.Container
 *  net.minecraft.world.ContainerListener
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.SimpleContainer
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.damagesource.DamageTypes
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.entity.AgeableMob
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.Entity$RemovalReason
 *  net.minecraft.world.entity.EntityDimensions
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.MobType
 *  net.minecraft.world.entity.MoverType
 *  net.minecraft.world.entity.OwnableEntity
 *  net.minecraft.world.entity.PathfinderMob
 *  net.minecraft.world.entity.Pose
 *  net.minecraft.world.entity.SlotAccess
 *  net.minecraft.world.entity.ai.attributes.AttributeInstance
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.entity.ai.control.BodyRotationControl
 *  net.minecraft.world.entity.ai.goal.Goal
 *  net.minecraft.world.entity.ai.goal.Goal$Flag
 *  net.minecraft.world.entity.ai.goal.MeleeAttackGoal
 *  net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal
 *  net.minecraft.world.entity.ai.targeting.TargetingConditions
 *  net.minecraft.world.entity.monster.Guardian
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.entity.projectile.AbstractArrow
 *  net.minecraft.world.inventory.MenuType
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.item.enchantment.EnchantmentHelper
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.ServerLevelAccessor
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.Mirror
 *  net.minecraft.world.level.block.Rotation
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.levelgen.structure.BoundingBox
 *  net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings
 *  net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate
 *  net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager
 *  net.minecraft.world.level.pathfinder.BlockPathTypes
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 *  net.minecraft.world.phys.shapes.BitSetDiscreteVoxelShape
 *  net.minecraft.world.phys.shapes.DiscreteVoxelShape
 *  org.jetbrains.annotations.Nullable
 */
package com.eternal_creations.archies_dino_mod.common.entity.mob.coralosaur;

import com.astemir.astemirlib.re.client.model.core.system.TransformSystem;
import com.astemir.astemirlib.re.common.action.ActionSequence;
import com.astemir.astemirlib.re.common.action.ActionState;
import com.astemir.astemirlib.re.common.action.ActionType;
import com.astemir.astemirlib.re.common.action.SyncedActionHolder;
import com.astemir.astemirlib.re.common.extension.EntityExtension;
import com.astemir.astemirlib.re.common.world.entity.EntityLocomotion;
import com.astemir.astemirlib.re.core.math.AMath;
import com.astemir.astemirlib.re.core.math.Easing;
import com.astemir.astemirlib.re.core.math.Transform;
import com.astemir.astemirlib.re.core.math.tween.TweenFloat;
import com.eternal_creations.archies_dino_mod.Constants;
import com.eternal_creations.archies_dino_mod.common.ADStrings;
import com.eternal_creations.archies_dino_mod.common.ADTags;
import com.eternal_creations.archies_dino_mod.common.entity.ADActionTypes;
import com.eternal_creations.archies_dino_mod.common.entity.ADEntityTypes;
import com.eternal_creations.archies_dino_mod.common.entity.ai.control.SmoothBodyControl;
import com.eternal_creations.archies_dino_mod.common.entity.ai.control.SmoothLookControl;
import com.eternal_creations.archies_dino_mod.common.entity.ai.control.SmoothMoveControl;
import com.eternal_creations.archies_dino_mod.common.entity.base.AbstractComplexMob;
import com.eternal_creations.archies_dino_mod.common.entity.base.AbstractComplexMount;
import com.eternal_creations.archies_dino_mod.common.entity.base.IMultipartEntity;
import com.eternal_creations.archies_dino_mod.common.entity.base.IPartEntity;
import com.eternal_creations.archies_dino_mod.common.entity.base.Mount;
import com.eternal_creations.archies_dino_mod.common.entity.base.Swimmer;
import com.eternal_creations.archies_dino_mod.common.entity.component.MountComponent;
import com.eternal_creations.archies_dino_mod.common.entity.component.PetComponent;
import com.eternal_creations.archies_dino_mod.common.entity.mob.coralosaur.CoralosaurAI;
import com.eternal_creations.archies_dino_mod.common.entity.mob.coralosaur.CoralosaurState;
import com.eternal_creations.archies_dino_mod.common.entity.utils.EntityUtils;
import com.eternal_creations.archies_dino_mod.common.items.ADItems;
import com.eternal_creations.archies_dino_mod.common.menu.ADMenus;
import com.eternal_creations.archies_dino_mod.common.menu.CoralosaurMenu;
import com.eternal_creations.archies_dino_mod.common.sound.ADSoundEvents;
import com.eternal_creations.archies_dino_mod.mixin.EntityAccessor;
import com.eternal_creations.archies_dino_mod.mixin.coralosaur.MobAccessor;
import com.eternal_creations.archies_dino_mod.mixin.coralosaur.StructureTemplateAccessor;
import com.eternal_creations.archies_dino_mod.platform.Services;
import com.eternal_creations.archies_dino_mod.server.ServerConfig;
import com.eternal_creations.archies_dino_mod.util.CommonUtil;
import com.eternal_creations.archies_dino_mod.util.iGuardianSummoned;
import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Clearable;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerListener;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Guardian;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BitSetDiscreteVoxelShape;
import net.minecraft.world.phys.shapes.DiscreteVoxelShape;
import org.jetbrains.annotations.Nullable;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class CoralosaurEntity
extends AbstractComplexMount
implements Swimmer,
IMultipartEntity,
ContainerListener {
    public static final EntityDataAccessor<Byte> DATA_CHESTS = SynchedEntityData.m_135353_(CoralosaurEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135027_);
    public static final EntityDataAccessor<Integer> DATA_VICTIM = SynchedEntityData.m_135353_(CoralosaurEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135028_);
    private static final float BURIED_SIZE_HEIGHT = 2.0f;
    private static final int ALONE_TIME_UNTIL_DESPAWN = 1200;
    private static final float DEFAULT_HEALTH = 80.0f;
    private static final float BABY_HEALTH_MULTIPLIER = 0.625f;
    private static final float TAMED_HEALTH_MULTIPLIER = 1.3125f;
    private static final float DEFAULT_ATTACK_DAMAGE = 8.0f;
    private static final float BITE_MULTIPLIER = 1.125f;
    public static final float BURY_MULTIPLIER = 1.25f;
    private static final double GRAB_RANGE = 3.0;
    private int stewCount = 0;
    private int aloneTime = 0;
    public static final AbstractComplexMob.TameableDinoSettings DEFAULT_STATS = new AbstractComplexMob.TameableDinoSettings(false, true, true, new ServerConfig.EntityStats(0.2, 80.0, 0.9, 2.0, 48.0, 8.0));
    public ActionState actionGrab;
    public ActionState actionDragDown;
    public ActionState actionBury;
    public ActionState actionBite;
    public ActionState actionSpecial;
    public ActionState actionDeath;
    public ActionState actionWake;
    public ActionState actionCamo;
    private final ActionSequence actionSequence;
    public TweenFloat shadowRadius = TweenFloat.of((float)2.0f);
    public TweenFloat sittingWeight = TweenFloat.of((float)0.0f);
    public final List<IPartEntity<?>> head = new ArrayList();
    public final List<IPartEntity<?>> tail = new ArrayList();
    public final IPartEntity<?>[] parts = new IPartEntity[7];
    public final List<SimpleContainer> containers = new ArrayList<SimpleContainer>();

    public CoralosaurEntity(EntityType<? extends AbstractComplexMount> entityType, Level level) {
        super((EntityType<? extends AbstractComplexMob>)entityType, level);
        this.actionSequence = new ActionSequence((SyncedActionHolder)this);
        this.f_21342_ = new SmoothMoveControl((Mob)this, 0.35f);
        this.f_21365_ = new SmoothLookControl((Mob)this, 15.0f, 0.3).setCanRotate(this::canRotate);
        this.m_274367_(1.0f);
        this.m_21441_(BlockPathTypes.WATER, 0.0f);
        this.parts[0] = Services.COMMON.makePartEntity(this, 2.0f, 2.0f);
        this.head.add(this.parts[0]);
        this.parts[1] = Services.COMMON.makePartEntity(this, 2.0f, 2.0f);
        this.head.add(this.parts[1]);
        this.parts[2] = Services.COMMON.makePartEntity(this, 2.0f, 2.0f);
        this.head.add(this.parts[2]);
        this.parts[3] = Services.COMMON.makePartEntity(this, 2.0f, 2.0f);
        this.tail.add(this.parts[3]);
        this.parts[4] = Services.COMMON.makePartEntity(this, 2.0f, 2.0f);
        this.tail.add(this.parts[4]);
        this.parts[5] = Services.COMMON.makePartEntity(this, 2.0f, 2.0f);
        this.tail.add(this.parts[5]);
        this.parts[6] = Services.COMMON.makePartEntity(this, 2.0f, 2.0f);
        this.tail.add(this.parts[6]);
        for (int i = 0; i < 8; ++i) {
            this.containers.add(new SimpleContainer(54));
            this.containers.get(this.containers.size() - 1).m_19164_((ContainerListener)this);
        }
        this.m_20234_(EntityAccessor.getENTITY_COUNTER().getAndAdd(this.parts.length + 1) + 1);
    }

    public int m_8100_() {
        return 900;
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
        if (this.head.contains(part) && this.isBuried() && !this.isTamed() && this.pet().canBeTamed()) {
            ItemStack stack = player.m_21120_(hand);
            if (!stack.m_150930_(ADItems.CORAL_STEW.get())) {
                return this.m_6071_(player, hand);
            }
            if (!this.m_9236_().f_46443_) {
                this.m_142075_(player, hand, stack);
                if (this.tryToTame(player, this.f_19796_)) {
                    this.pet().tame(player);
                    this.f_21344_.m_26573_();
                    this.m_6710_(null);
                    this.m_9236_().m_7605_((Entity)this, (byte)7);
                    this.actionSequence.clearQueue().addAction(this.actionWake).start();
                } else {
                    this.m_9236_().m_7605_((Entity)this, (byte)6);
                }
            }
            return InteractionResult.m_19078_((boolean)this.m_9236_().f_46443_);
        }
        return this.m_6071_(player, hand);
    }

    @Override
    protected boolean canFloatWhileMounted() {
        return false;
    }

    @Override
    @Nullable
    public LivingEntity m_6688_() {
        if (!this.m_6254_()) {
            return null;
        }
        List passengers = this.m_20197_();
        if (passengers.isEmpty()) {
            return null;
        }
        for (Entity passenger : passengers) {
            Player player;
            if (!(passenger instanceof Player) || (player = (Player)passenger).m_19879_() == this.getVictim()) continue;
            return player;
        }
        return null;
    }

    @Override
    public boolean tryToTame(Player player, RandomSource random) {
        return ++this.stewCount >= 5;
    }

    public boolean m_5830_() {
        return super.m_5830_() && !this.isBuried();
    }

    public void m_20234_(int id) {
        super.m_20234_(id);
        for (int i = 0; i < this.parts.length; ++i) {
            this.parts[i].setPartId(id + i + 1);
        }
    }

    @Override
    public boolean isSwimmingInLiquid() {
        return this.m_20072_();
    }

    public MobType m_6336_() {
        return MobType.f_21644_;
    }

    @Override
    public boolean m_6914_(LevelReader level) {
        return level.m_45784_((Entity)this);
    }

    public int m_213860_() {
        return 1 + this.m_9236_().f_46441_.m_188503_(3);
    }

    public void m_6075_() {
        Vec3 pos;
        int i;
        super.m_6075_();
        if (this.isBuried() && !this.m_9236_().f_46443_ && this.f_19797_ % 20 == 0) {
            this.m_9236_().m_45955_(TargetingConditions.m_148353_(), (LivingEntity)this, this.m_6921_().m_82377_(2.0, 4.0, 2.0)).forEach(player -> player.m_7292_(new MobEffectInstance(MobEffects.f_19608_, 100)));
        }
        for (i = 0; i < this.head.size(); ++i) {
            pos = this.m_20182_().m_82549_(this.m_20171_(this.m_146909_(), this.f_20883_).m_82490_((double)(2.0f * (float)(i + 1)))).m_82520_(0.0, this.isBuried() ? -2.0 : 0.0, 0.0);
            this.head.get(i).setPartPos(pos.f_82479_, pos.f_82480_, pos.f_82481_);
        }
        for (i = 0; i < this.tail.size(); ++i) {
            pos = this.m_20182_().m_82549_(this.m_20171_(this.m_146909_(), this.f_20883_).m_82490_((double)(-2.0f * (float)(i + 1)))).m_82520_(0.0, this.isBuried() ? -2.0 : 0.0, 0.0);
            this.tail.get(i).setPartPos(pos.f_82479_, pos.f_82480_, pos.f_82481_);
        }
        if (!this.isBuried() && !this.isTamed() && this.m_5448_() == null) {
            ++this.aloneTime;
            if (this.aloneTime > 1200) {
                this.playActionSynced(this.actionCamo);
            }
        }
    }

    public boolean hasChest(int index) {
        if (index < 0 || index > 7) {
            return false;
        }
        return ((Byte)this.f_19804_.m_135370_(DATA_CHESTS) & 1 << index) != 0;
    }

    public void setChest(int index, boolean value) {
        if (index < 0 || index > 7) {
            return;
        }
        this.inventory.m_6836_(index + 1, value ? Items.f_42009_.m_7968_() : ItemStack.f_41583_);
    }

    public void setChestData(int index, boolean value) {
        if (index < 0 || index > 7) {
            return;
        }
        if (value) {
            this.f_19804_.m_135381_(DATA_CHESTS, (Object)((byte)((Byte)this.f_19804_.m_135370_(DATA_CHESTS) | 1 << index)));
        } else {
            this.f_19804_.m_135381_(DATA_CHESTS, (Object)((byte)((Byte)this.f_19804_.m_135370_(DATA_CHESTS) & ~(1 << index))));
        }
    }

    public int getVictim() {
        return (Integer)this.f_19804_.m_135370_(DATA_VICTIM);
    }

    public void setVictim(int victim) {
        this.f_19804_.m_135381_(DATA_VICTIM, (Object)victim);
    }

    @Override
    protected void m_5907_() {
        super.m_5907_();
        for (int chest = 0; chest < 8; ++chest) {
            SimpleContainer container = this.containers.get(chest);
            for (int i = 0; i < container.m_6643_(); ++i) {
                ItemStack itemstack = container.m_8020_(i);
                if (itemstack.m_41619_() || EnchantmentHelper.m_44924_((ItemStack)itemstack)) continue;
                this.m_19983_(itemstack);
            }
        }
    }

    protected AABB m_20217_(Pose pose) {
        if (this.isBuried()) {
            return super.m_20217_(pose).m_82386_(0.0, -2.0, 0.0);
        }
        return super.m_20217_(pose);
    }

    public boolean m_20039_(BlockPos pos, BlockState state) {
        if (this.isBuried()) {
            return false;
        }
        return super.m_20039_(pos, state);
    }

    public void m_19920_(float amount, Vec3 relative) {
        if (this.m_6688_() != null) {
            super.m_19920_(amount * 4.0f, relative);
        } else {
            super.m_19920_(amount, relative);
        }
    }

    @Override
    public void m_7023_(Vec3 travelVector) {
        if (this.isBuried()) {
            return;
        }
        if (this.m_21515_() && this.m_20069_() && !this.isMovementBlocked()) {
            this.m_19920_(this.m_6113_(), travelVector);
            this.m_6478_(MoverType.SELF, this.m_20184_());
            this.m_20256_(this.m_20184_().m_82490_(0.9));
            if (this.m_5448_() == null) {
                this.m_20256_(this.m_20184_().m_82520_(0.0, -0.005, 0.0));
            }
        } else {
            super.m_7023_(travelVector);
        }
    }

    public boolean m_35506_() {
        return this.isTamed();
    }

    public boolean m_6573_(Player player) {
        return this.m_6254_();
    }

    protected Vec3 m_7939_() {
        TransformSystem system = EntityExtension.as((Entity)this).getCurrentTransformSystem();
        if (system == null) {
            return super.m_7939_();
        }
        Transform transform = system.getLocatorTransform((Object)this, "harness_leash");
        return new Vec3((double)transform.getX(), (double)transform.getY(), (double)transform.getZ());
    }

    protected void m_6119_() {
        Entity entity;
        MobAccessor accessor = (MobAccessor)((Object)this);
        if (accessor.getLeashInfoTag() != null) {
            accessor.invokeRestoreLeashFromSave();
        }
        if (!(accessor.getFieldLeashHolder() == null || this.m_6084_() && accessor.getFieldLeashHolder().m_6084_())) {
            this.m_21455_(true, true);
        }
        if ((entity = this.m_21524_()) != null && entity.m_9236_() == this.m_9236_()) {
            this.m_21446_(entity.m_20183_(), 5);
            float distance = this.m_20270_(entity);
            this.m_7880_(distance);
            if (distance > 20.0f) {
                this.m_21455_(true, true);
                this.f_21345_.m_25355_(Goal.Flag.MOVE);
            } else if (distance > 6.0f) {
                double d0 = (entity.m_20185_() - this.m_20185_()) / (double)distance;
                double d1 = (entity.m_20186_() - this.m_20186_()) / (double)distance;
                double d2 = (entity.m_20189_() - this.m_20189_()) / (double)distance;
                this.m_20256_(this.m_20184_().m_82520_(Math.copySign(d0 * d0 * 0.1, d0), Math.copySign(d1 * d1 * 0.1, d1), Math.copySign(d2 * d2 * 0.1, d2)));
                this.m_245125_();
            } else if (this.m_213814_()) {
                this.f_21345_.m_25374_(Goal.Flag.MOVE);
                Vec3 vec3 = new Vec3(entity.m_20185_() - this.m_20185_(), entity.m_20186_() - this.m_20186_(), entity.m_20189_() - this.m_20189_()).m_82541_().m_82490_((double)Math.max(distance - 2.0f, 0.0f));
                this.m_21573_().m_26519_(this.m_20185_() + vec3.f_82479_, this.m_20186_() + vec3.f_82480_, this.m_20189_() + vec3.f_82481_, this.m_5823_());
            }
        }
    }

    protected void m_20351_(Entity passenger) {
        if (passenger.m_19879_() == this.getVictim()) {
            this.setVictim(-1);
        }
        super.m_20351_(passenger);
    }

    @Override
    protected Set<ActionState> makeActionSet() {
        this.actionGrab = ActionState.of((ActionType)ADActionTypes.ATTACK_HEADBUTT, (float)2.25f, (float)0.3f, (float)1.0f).exitTime(2.0f).onTick(tick -> {
            if (tick == 13) {
                LivingEntity target = this.getActionTargetEntity();
                IPartEntity<?> head = this.head.get(2);
                if (target != null && head != null && (double)target.m_20270_(head.asPart()) < 9.0 && !target.m_21224_() && target.m_6469_(this.m_9236_().m_269111_().m_269333_((LivingEntity)this), EntityUtils.multiplyDamage((Mob)this, target, 1.25f))) {
                    target.m_20329_((Entity)this);
                    this.setVictim(target.m_19879_());
                }
            }
        }).onFinish(() -> {
            for (Entity entity : this.m_20197_()) {
                LivingEntity living;
                if (entity.m_19879_() != this.getVictim()) continue;
                this.playActionSynced(this.actionDragDown);
                this.setActionTargetEntity(entity instanceof LivingEntity ? (living = (LivingEntity)entity) : null);
                return;
            }
            this.setVictim(-1);
        });
        this.actionDragDown = ActionState.of((ActionType)ADActionTypes.ATTACK_BITE_RUN, (float)1.5f, (float)0.2f, (float)0.6f).exitTime(1.0f).onTick(tick -> this.m_20334_(0.0, -0.5, 0.0)).onFinish(() -> {
            for (Entity entity : this.m_20197_()) {
                if (entity.m_19879_() != this.getVictim()) continue;
                this.m_20334_(0.0, -0.5, 0.0);
                if (entity.m_9236_().m_8055_(entity.m_20183_().m_7495_()).m_280296_()) {
                    LivingEntity living;
                    this.playActionSynced(this.actionBury);
                    this.setActionTargetEntity(entity instanceof LivingEntity ? (living = (LivingEntity)entity) : null);
                    return;
                }
                if (entity instanceof Player) {
                    Player player = (Player)entity;
                    player.m_5661_((Component)Component.m_237115_((String)ADStrings.Messages.CORALOSAUR_BURIED), true);
                }
                entity.m_8127_();
            }
            this.setActionTargetEntity(null);
            this.setVictim(-1);
        });
        this.actionBury = ActionState.of((ActionType)ADActionTypes.ATTACK_HIT, (float)4.79f, (float)0.2f, (float)0.6f).exitTime(4.5f).onTick(tick -> {
            if (tick == 15) {
                for (Entity entity : this.m_20197_()) {
                    if (entity.m_19879_() != this.getVictim()) continue;
                    Vec3 pos = entity.m_20182_().m_82520_(0.0, -1.0, 0.0);
                    entity.m_8127_();
                    this.setVictim(-1);
                    this.setActionTargetEntity(null);
                    entity.m_20219_(pos);
                    if (this.m_9236_().f_46443_) {
                        return;
                    }
                    BlockPos.m_121940_((BlockPos)entity.m_20183_().m_7918_(-1, -1, -1), (BlockPos)entity.m_20183_().m_7918_(1, 2, 1)).forEach(blockPos -> {
                        BlockState state = this.m_9236_().m_8055_(blockPos);
                        if (state.m_280296_() && !state.m_60713_(Blocks.f_49992_)) {
                            this.m_9236_().m_7731_(blockPos, Blocks.f_49992_.m_49966_(), 3);
                        }
                    });
                    return;
                }
            }
        });
        this.actionBite = ActionState.of((ActionType)ADActionTypes.ATTACK_BITE, (float)3.0f, (float)0.2f, (float)0.4f).exitTime(2.7f).onStart(() -> this.m_216990_(ADSoundEvents.CORALOSAUR_BITE.get())).onTick(tick -> {
            Level patt20144$temp;
            if (tick == 20 && (patt20144$temp = this.m_9236_()) instanceof ServerLevel) {
                ServerLevel serverLevel = (ServerLevel)patt20144$temp;
                CommonUtil.checkWalls(serverLevel, this.m_20191_().m_82383_(this.m_20252_(1.0f).m_82490_(4.0)), this.m_20069_());
                EntityUtils.attackForward((Mob)this, this.m_6162_() ? 2.0f : 4.0f, -0.75f, 1.0f, 1.125f, target -> {});
            }
        });
        this.actionSpecial = ActionState.of((ActionType)ADActionTypes.ROAR, (float)3.5f, (float)0.2f, (float)0.8f).exitTime(3.2f).onTick(tick -> {
            if (tick == 25) {
                Level level = this.m_9236_();
                this.makeNearbyMarineMobsAggressive(level, this.m_20183_(), 16);
                for (int i = 0; i < 3 + this.f_19796_.m_188503_(3); ++i) {
                    Guardian guardian;
                    BlockPos spawnPos = CoralosaurEntity.findValidSpawnPosition(level, this.m_20183_(), 10);
                    if (spawnPos == null || !((guardian = (Guardian)EntityType.f_20455_.m_20615_(level)) instanceof iGuardianSummoned)) continue;
                    iGuardianSummoned summoned = (iGuardianSummoned)guardian;
                    summoned.archiesDinoMod$setOwner(this.m_20148_());
                    guardian.m_7678_((double)spawnPos.m_123341_() + 0.5, (double)spawnPos.m_123342_(), (double)spawnPos.m_123343_() + 0.5, 0.0f, 0.0f);
                    level.m_7967_((Entity)guardian);
                }
            }
        });
        this.actionDeath = ActionState.of((ActionType)ADActionTypes.DEATH, (float)3.0f, (float)0.1f, (float)0.0f).holdOnLastFrame();
        this.actionWake = ActionState.of((ActionType)ADActionTypes.UP, (float)15.0f, (float)0.0f, (float)0.8f).exitTime(14.7f).onStart(() -> {
            this.setBuried(false);
            this.m_216990_(ADSoundEvents.CORALOSAUR_WAKE.get());
        }).onTick(tick -> {
            Level patt21581$temp = this.m_9236_();
            if (patt21581$temp instanceof ServerLevel) {
                ServerLevel level = (ServerLevel)patt21581$temp;
                if (tick == 30) {
                    CoralosaurEntity.placeBreakAroundEntity(level, this.m_20183_().m_6630_(3), (LivingEntity)this, 1);
                } else if (tick == 80) {
                    CoralosaurEntity.placeBreakAroundEntity(level, this.m_20183_().m_6630_(3), (LivingEntity)this, 2);
                }
            }
        });
        this.actionCamo = ActionState.of((ActionType)ADActionTypes.DOWN, (float)2.5833f, (float)0.1f, (float)0.0f).onStart(() -> this.m_216990_(ADSoundEvents.CORALOSAUR_BURY.get())).onFinish(() -> this.m_142687_(Entity.RemovalReason.DISCARDED));
        return Set.of(this.actionGrab, this.actionBury, this.actionBite, this.actionSpecial, this.actionDragDown, this.actionDeath, this.actionWake, this.actionCamo);
    }

    public static void placeBreakAroundEntity(ServerLevel serverlevel, BlockPos blockpos, LivingEntity living, int phase) {
        try {
            StructureTemplateManager structuretemplatemanager = serverlevel.m_215082_();
            Optional optional = structuretemplatemanager.m_230407_(Constants.prefix("coralosaur_break/" + phase));
            if (optional.isEmpty()) {
                return;
            }
            StructureTemplate template = (StructureTemplate)optional.get();
            Direction facing = Direction.m_122364_((double)living.m_213816_());
            Rotation rotation = switch (facing) {
                case Direction.EAST -> Rotation.NONE;
                case Direction.SOUTH -> Rotation.CLOCKWISE_90;
                case Direction.WEST -> Rotation.CLOCKWISE_180;
                default -> Rotation.COUNTERCLOCKWISE_90;
            };
            StructurePlaceSettings settings = new StructurePlaceSettings().m_74377_(Mirror.NONE).m_74379_(rotation).m_163782_(true).m_74392_(true);
            BoundingBox boundingBox = template.m_74633_(settings, blockpos);
            BlockPos offset = new BlockPos(-(facing.m_122434_() == Direction.Axis.X ? boundingBox.m_71056_() : boundingBox.m_71058_()) / 2, -boundingBox.m_71057_(), -(facing.m_122434_() == Direction.Axis.X ? boundingBox.m_71058_() : boundingBox.m_71056_()) / 2).m_7954_(rotation);
            CoralosaurEntity.placeInWorld((ServerLevelAccessor)serverlevel, template, blockpos.m_121955_((Vec3i)offset), settings, serverlevel.f_46441_, 2);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public static <T extends StructureTemplate> void placeInWorld(ServerLevelAccessor serverLevel, StructureTemplate structureTemplate, BlockPos pos, StructurePlaceSettings settings, RandomSource random, int flags) {
        CoralosaurEntity.internalPlaceInWorld(serverLevel, structureTemplate, pos, settings, random, flags);
    }

    private static <T extends StructureTemplate> void internalPlaceInWorld(ServerLevelAccessor serverLevel, T template, BlockPos pos, StructurePlaceSettings settings, RandomSource random, int flags) {
        if (((StructureTemplateAccessor)template).getPalettes().isEmpty()) {
            return;
        }
        List list = settings.m_74387_(((StructureTemplateAccessor)template).getPalettes(), pos).m_74652_();
        if (list.isEmpty() || template.m_163801_().m_123341_() < 1 || template.m_163801_().m_123342_() < 1 || template.m_163801_().m_123343_() < 1) {
            return;
        }
        BoundingBox box = settings.m_74409_();
        ArrayList tagAt = Lists.newArrayListWithCapacity((int)list.size());
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int minZ = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;
        int maxZ = Integer.MIN_VALUE;
        for (Object structureBlockInfo : StructureTemplate.m_74517_((ServerLevelAccessor)serverLevel, (BlockPos)pos, (BlockPos)pos, (StructurePlaceSettings)settings, (List)list)) {
            BlockEntity blockentity1;
            BlockState newState;
            BlockPos placePos = structureBlockInfo.f_74675_();
            if (box != null && !box.m_71051_((Vec3i)placePos)) continue;
            BlockState currentState = serverLevel.m_8055_(pos);
            if (!currentState.m_60713_((newState = Blocks.f_49990_.m_49966_()).m_60734_())) {
                serverLevel.m_46796_(2001, pos, Block.m_49956_((BlockState)currentState));
            }
            if (structureBlockInfo.f_74677_() != null) {
                BlockEntity blockentity = serverLevel.m_7702_(placePos);
                Clearable.m_18908_((Object)blockentity);
                serverLevel.m_7731_(placePos, Blocks.f_50375_.m_49966_(), 20);
            }
            if (!serverLevel.m_7731_(placePos, newState, flags)) continue;
            minX = Math.min(minX, placePos.m_123341_());
            minY = Math.min(minY, placePos.m_123342_());
            minZ = Math.min(minZ, placePos.m_123343_());
            maxX = Math.max(maxX, placePos.m_123341_());
            maxY = Math.max(maxY, placePos.m_123342_());
            maxZ = Math.max(maxZ, placePos.m_123343_());
            tagAt.add(Pair.of((Object)placePos, (Object)structureBlockInfo.f_74677_()));
            if (structureBlockInfo.f_74677_() == null || (blockentity1 = serverLevel.m_7702_(placePos)) == null) continue;
            if (blockentity1 instanceof RandomizableContainerBlockEntity) {
                structureBlockInfo.f_74677_().m_128356_("LootTableSeed", random.m_188505_());
            }
            blockentity1.m_142466_(structureBlockInfo.f_74677_());
        }
        if (minX <= maxX) {
            if (!settings.m_74410_()) {
                BitSetDiscreteVoxelShape discretevoxelshape = new BitSetDiscreteVoxelShape(maxX - minX + 1, maxY - minY + 1, maxZ - minZ + 1);
                for (Pair pair1 : tagAt) {
                    BlockPos first = (BlockPos)pair1.getFirst();
                    discretevoxelshape.m_142703_(first.m_123341_() - minX, first.m_123342_() - minY, first.m_123343_() - minZ);
                }
                StructureTemplate.m_74510_((LevelAccessor)serverLevel, (int)flags, (DiscreteVoxelShape)discretevoxelshape, (int)minX, (int)minY, (int)minZ);
            }
            for (Pair pair : tagAt) {
                BlockEntity blockEntity;
                BlockPos first = (BlockPos)pair.getFirst();
                if (!settings.m_74410_()) {
                    BlockState neighbourShapes;
                    BlockState state = serverLevel.m_8055_(first);
                    if (state != (neighbourShapes = Block.m_49931_((BlockState)state, (LevelAccessor)serverLevel, (BlockPos)first))) {
                        serverLevel.m_7731_(first, neighbourShapes, flags & 0xFFFFFFFE | 0x10);
                    }
                    serverLevel.m_6289_(first, neighbourShapes.m_60734_());
                }
                if (pair.getSecond() == null || (blockEntity = serverLevel.m_7702_(first)) == null) continue;
                blockEntity.m_6596_();
            }
        }
    }

    @Override
    public void attackActionTargetEntity(float damage, float knockback) {
        LivingEntity target = this.getActionTargetEntity();
        if (EntityUtils.attackTarget((Mob)this, target, damage, knockback)) {
            this.setActionTargetEntity(null);
        }
    }

    public boolean m_6040_() {
        return true;
    }

    public boolean m_6063_() {
        return false;
    }

    @Override
    protected EntityLocomotion createLocomotion() {
        return EntityLocomotion.of().speed(0.02f, 0.24f).transitionLimit(0.2f).responsiveness(0.3f).build();
    }

    @Override
    protected MountComponent<?> createMount() {
        return MountComponent.of(player -> this.isAdult() && this.isOwnedBy((LivingEntity)player) && !this.isSitting()).maxSpeed(1.0f).followYawSpeed(0.1f).followHeadSpeed(0.4f).inertia(0.05f).friction(0.3f).swimming(this::m_6069_).build(this);
    }

    public boolean m_6069_() {
        return this.m_20072_();
    }

    @Override
    protected PetComponent<?> createPet() {
        return PetComponent.of().setBabyHealthMultiplier(0.625f).setTamedHealthMultiplier(1.3125f).build(this);
    }

    @Override
    protected void m_8097_() {
        super.m_8097_();
        this.f_19804_.m_135372_(DATA_CHESTS, (Object)0);
        this.f_19804_.m_135372_(DATA_VICTIM, (Object)-1);
    }

    @Override
    public void m_7380_(CompoundTag compound) {
        super.m_7380_(compound);
        compound.m_128344_("chests", ((Byte)this.f_19804_.m_135370_(DATA_CHESTS)).byteValue());
        compound.m_128405_("victim", ((Integer)this.f_19804_.m_135370_(DATA_VICTIM)).intValue());
        for (int chest = 0; chest < 8; ++chest) {
            compound.m_128365_("chest_" + chest, (Tag)this.containers.get(chest).m_7927_());
        }
    }

    @Override
    public void m_7378_(CompoundTag compound) {
        super.m_7378_(compound);
        if (compound.m_128441_("chests")) {
            byte chests = compound.m_128445_("chests");
            for (int i = 0; i < 8; ++i) {
                this.setChest(i, (chests & 1 << i) != 0);
            }
        }
        if (compound.m_128441_("victim")) {
            this.f_19804_.m_135381_(DATA_VICTIM, (Object)compound.m_128451_("victim"));
        }
        this.m_6210_();
        for (int chest = 0; chest < 8; ++chest) {
            if (!compound.m_128425_("chest_" + chest, 9)) continue;
            this.containers.get(chest).m_7797_(compound.m_128437_("chest_" + chest, 10));
        }
    }

    @Override
    public AABB m_6921_() {
        return super.m_6921_().m_82400_(5.0);
    }

    public void m_7350_(EntityDataAccessor<?> key) {
        super.m_7350_(key);
        if (DATA_MOB_STATE.equals(key)) {
            this.sittingWeight.setTarget(this.isSitting() ? 1.0f : 0.0f, 0.1f);
        }
    }

    protected void m_8099_() {
        CoralosaurAI.setupAI(this, this.f_21345_, this.f_21346_);
    }

    protected BodyRotationControl m_7560_() {
        return new SmoothBodyControl((Mob)this, 35.0f, 15, 10.0f, 0.07f).setCanRotate(this::canRotate);
    }

    @Override
    public void m_8119_() {
        super.m_8119_();
        if (this.m_21525_()) {
            this.m_20301_(this.m_6062_());
        } else {
            if (!this.m_20071_() && this.m_20096_()) {
                this.m_20256_(this.m_20184_().m_82520_((double)((this.f_19796_.m_188501_() * 2.0f - 1.0f) * 0.2f), 0.5, (double)((this.f_19796_.m_188501_() * 2.0f - 1.0f) * 0.2f)));
                this.m_146922_(this.f_19796_.m_188501_() * 360.0f);
                this.m_6853_(false);
                this.f_19812_ = true;
            }
            if (this.m_9236_().f_46443_ && this.m_20069_() && this.m_20184_().m_82556_() > 0.03) {
                Vec3 vec3 = this.m_20252_(0.0f);
                float f = Mth.m_14089_((float)(this.m_146908_() * ((float)Math.PI / 180))) * 0.3f;
                float f1 = Mth.m_14031_((float)(this.m_146908_() * ((float)Math.PI / 180))) * 0.3f;
                float f2 = 1.2f - this.f_19796_.m_188501_() * 0.7f;
                for (int i = 0; i < 2; ++i) {
                    this.m_9236_().m_7106_((ParticleOptions)ParticleTypes.f_123776_, this.m_20185_() - vec3.f_82479_ * (double)f2 + (double)f, this.m_20186_() - vec3.f_82480_, this.m_20189_() - vec3.f_82481_ * (double)f2 + (double)f1, 0.0, 0.0, 0.0);
                    this.m_9236_().m_7106_((ParticleOptions)ParticleTypes.f_123776_, this.m_20185_() - vec3.f_82479_ * (double)f2 - (double)f, this.m_20186_() - vec3.f_82480_, this.m_20189_() - vec3.f_82481_ * (double)f2 - (double)f1, 0.0, 0.0, 0.0);
                }
            }
        }
        this.shadowRadius.tick();
        this.sittingWeight.tick();
        this.actionSequence.tick();
        if (this.isBuried() || this.actionCamo.isActive()) {
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
        if (!this.m_9236_().f_46443_ && this.m_5448_() == null && this.isCharging()) {
            this.setCharging(false);
        }
    }

    @Nullable
    public static BlockPos findValidSpawnPosition(Level level, BlockPos center, int maxDist) {
        int attempts = 0;
        while (attempts++ < 20) {
            int dz;
            int dy;
            int dx = level.f_46441_.m_188503_(2 * maxDist) - maxDist;
            BlockPos pos = center.m_7918_(dx, dy = level.f_46441_.m_188503_(4) - 2, dz = level.f_46441_.m_188503_(2 * maxDist) - maxDist);
            if (!level.m_46801_(pos) || level.m_8055_(pos).m_280296_()) continue;
            return pos;
        }
        return null;
    }

    private void makeNearbyMarineMobsAggressive(Level level, BlockPos pos, int radius) {
        AABB area = new AABB((double)(pos.m_123341_() - radius), (double)(pos.m_123342_() - radius), (double)(pos.m_123343_() - radius), (double)(pos.m_123341_() + radius), (double)(pos.m_123342_() + radius), (double)(pos.m_123343_() + radius));
        level.m_6443_(PathfinderMob.class, area, CoralosaurEntity::canCoralosaurTurn).forEach(mob -> {
            TargetNotOurPlayer<LivingEntity> targetNotOurPlayer;
            ((MobAccessor)mob).getGoalSelector().m_148105_().clear();
            ((MobAccessor)mob).getGoalSelector().m_25352_(1, (Goal)new FishMeleeAttackGoal((PathfinderMob)mob));
            ((MobAccessor)mob).getTargetSelector().m_148105_().clear();
            if (this.isTamed()) {
                Player player;
                LivingEntity patt33753$temp = this.m_269323_();
                Player player2 = patt33753$temp instanceof Player ? (player = (Player)patt33753$temp) : null;
                targetNotOurPlayer = new TargetNotOurPlayer<LivingEntity>((Mob)mob, LivingEntity.class, true, player2);
            } else {
                targetNotOurPlayer = new TargetNotOurPlayer<Player>((Mob)mob, Player.class, true, null);
            }
            TargetNotOurPlayer<LivingEntity> goal = targetNotOurPlayer;
            ((MobAccessor)mob).getTargetSelector().m_25352_(1, goal);
        });
    }

    private static boolean canCoralosaurTurn(PathfinderMob mob) {
        return mob.m_6095_().m_204039_(ADTags.EntityTypes.CORALOSAUR_TURN);
    }

    @Override
    public void onRiderAttack(LivingEntity rider, Mount.AttackType type) {
        if (!this.isNoActionActive()) {
            return;
        }
        switch (type) {
            case PRIMARY: {
                this.playActionSynced(this.actionBite);
                break;
            }
            case SPECIAL: {
                this.playActionSynced(this.actionSpecial);
            }
        }
    }

    protected void m_6153_() {
        if (this.f_20919_++ == 0) {
            this.playActionSynced(this.actionDeath);
        }
        if (this.f_20919_ >= 60 && !this.m_9236_().m_5776_() && !this.m_213877_()) {
            this.m_9236_().m_7605_((Entity)this, (byte)60);
            this.m_142687_(Entity.RemovalReason.KILLED);
        }
    }

    @Override
    protected boolean canRotate() {
        return !this.actionWake.isActive() && !this.actionCamo.isActive() && !this.actionDeath.isActive() && !this.actionSpecial.isActive() && !this.isBuried() && !this.isSitting();
    }

    @Override
    protected boolean isMovementBlocked() {
        return this.actionWake.isActive() || this.actionCamo.isActive() || this.actionDeath.isActive() || this.actionSpecial.isActive() || this.actionDragDown.isActive() || this.isBuried() || this.isSitting();
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
        if (source.m_276093_(DamageTypes.f_268612_)) {
            return false;
        }
        if (entitySource != null && entitySource.m_6095_().m_204039_(ADTags.EntityTypes.DINO_RESISTS_DAMAGE_FROM)) {
            amount *= 0.1f;
        }
        if ((hurt = super.m_6469_(source, amount)) && this.isBuried()) {
            this.actionSequence.clearQueue().addAction(this.actionWake).addAction(this.actionSpecial).start();
        }
        return hurt;
    }

    @Override
    public InteractionResult m_6071_(Player player, InteractionHand hand) {
        ItemStack stack;
        if (this.m_9236_().f_46443_) {
            this.m_5830_();
            return InteractionResult.SUCCESS;
        }
        if (this.isBuried() || this.actionWake.isActive() || this.actionCamo.isActive()) {
            return InteractionResult.PASS;
        }
        if (this.isOwnedBy((LivingEntity)player) && (stack = player.m_21120_(hand)).m_150930_(Items.f_42655_) && this.m_6573_(player)) {
            this.m_21463_((Entity)player, true);
            stack.m_41774_(1);
            return InteractionResult.m_19078_((boolean)this.m_9236_().f_46443_);
        }
        return super.m_6071_(player, hand);
    }

    public void m_146762_(int age) {
        AttributeInstance maxHealthAttr;
        boolean wasBaby = this.m_6162_();
        super.m_146762_(age);
        if (wasBaby != this.m_6162_() && (maxHealthAttr = this.m_21051_(Attributes.f_22276_)) != null) {
            double oldMax = maxHealthAttr.m_22115_();
            double newMax = this.m_6162_() ? 24.0 : (this.isTamed() ? 50.0 + (double)this.f_19796_.m_188503_(41) : 50.0);
            maxHealthAttr.m_22100_(newMax);
            float healthDiff = (float)(newMax - oldMax);
            if (healthDiff > 0.0f) {
                this.m_5634_(healthDiff);
            } else {
                this.m_21153_(Math.min(this.m_21223_(), (float)newMax));
            }
        }
    }

    public void m_5496_(SoundEvent sound, float volume, float pitch) {
        if (!this.isBuried() || sound == ADSoundEvents.CORALOSAUR_SLEEP.get()) {
            super.m_5496_(sound, volume, pitch);
        }
    }

    public double m_20968_(@Nullable Entity lookingEntity) {
        return this.isBuried() ? 0.0 : super.m_20968_(lookingEntity);
    }

    public double m_142593_(LivingEntity target) {
        return this.m_20205_() * 1.5f * this.m_20205_() * 1.5f + target.m_20205_();
    }

    public int m_21529_() {
        return 2;
    }

    @Override
    public float getCameraDistance(float partialTick) {
        return 8.0f;
    }

    protected SoundEvent m_7515_() {
        if (this.isBuried()) {
            return ADSoundEvents.CORALOSAUR_SLEEP.get();
        }
        return ADSoundEvents.CORALOSAUR_IDLE.get();
    }

    protected SoundEvent m_7975_(DamageSource damageSource) {
        return ADSoundEvents.CORALOSAUR_HURT.get();
    }

    protected SoundEvent m_5592_() {
        return ADSoundEvents.CORALOSAUR_DEATH.get();
    }

    protected float m_6121_() {
        return 1.5f;
    }

    @Override
    public boolean m_6898_(ItemStack stack) {
        return stack.m_204117_(ADTags.Items.CORALOSAUR_EDIBLE_ITEM);
    }

    @Override
    protected AbstractComplexMob.TameableDinoSettings getSettings() {
        return ServerConfig.getEntitySettings().coralosaur();
    }

    @Override
    @Nullable
    public AgeableMob m_142606_(ServerLevel serverLevel, AgeableMob ageableMob) {
        return (AgeableMob)ADEntityTypes.CORALOSAUR.get().m_20615_((Level)serverLevel);
    }

    public EntityDimensions m_6972_(Pose pose) {
        EntityDimensions entitydimensions = super.m_6972_(pose);
        return this.isBuried() || this.actionCamo.isActive() ? EntityDimensions.m_20398_((float)entitydimensions.f_20377_, (float)(2.0f * this.m_6134_())) : entitydimensions;
    }

    @Override
    public InteractionResult eatFromHand(Player player, ItemStack itemStack) {
        if (!this.isTamed()) {
            return InteractionResult.FAIL;
        }
        return super.eatFromHand(player, itemStack);
    }

    @Override
    public boolean isSitting() {
        return this.getState(CoralosaurState::byId) == CoralosaurState.SITTING;
    }

    @Override
    public void setSitting(boolean active) {
        this.setState(active ? CoralosaurState.SITTING : CoralosaurState.IDLE);
    }

    public boolean isBuried() {
        return this.getState(CoralosaurState::byId) == CoralosaurState.BURIED;
    }

    public void setBuried(boolean active) {
        this.setState(active ? CoralosaurState.BURIED : CoralosaurState.IDLE);
        this.m_6210_();
    }

    public boolean isCharging() {
        return this.getState(CoralosaurState::byId) == CoralosaurState.CHARGING;
    }

    public void setCharging(boolean active) {
        this.setState(active ? CoralosaurState.CHARGING : CoralosaurState.IDLE);
    }

    @Override
    public void m_5757_(Container container) {
        boolean[] chests = new boolean[8];
        for (int i = 0; i < 8; ++i) {
            chests[i] = this.hasChest(i);
        }
        boolean wasSaddled = this.m_6254_();
        this.updateContainerEquipment();
        if (this.f_19797_ > 20) {
            if (!wasSaddled && this.m_6254_()) {
                this.m_5496_(this.m_246265_(), 0.5f, 1.0f);
            }
            if (wasSaddled && !this.m_6254_()) {
                for (int i = 0; i < 8; ++i) {
                    if (!this.hasChest(i)) continue;
                    this.setChest(i, false);
                    this.m_19983_(new ItemStack((ItemLike)Items.f_42009_));
                }
            }
            boolean flag = false;
            for (int i = 0; i < 8; ++i) {
                if (chests[i] == this.hasChest(i)) continue;
                if (chests[i]) {
                    SimpleContainer simpleContainer = this.containers.get(i);
                    for (int j = 0; j < simpleContainer.m_6643_(); ++j) {
                        ItemStack itemstack = simpleContainer.m_8020_(j);
                        if (itemstack.m_41619_() || EnchantmentHelper.m_44924_((ItemStack)itemstack)) continue;
                        this.m_19983_(itemstack);
                    }
                    simpleContainer.m_6211_();
                }
                flag = true;
            }
            if (flag) {
                this.m_9236_().m_6269_(null, (Entity)this, ADSoundEvents.DINO_EQUIP.get(), SoundSource.NEUTRAL, 0.5f, 1.0f);
            }
        }
    }

    @Override
    protected void updateContainerEquipment() {
        super.updateContainerEquipment();
        if (!this.m_9236_().f_46443_) {
            for (int i = 0; i < 8; ++i) {
                this.setChestData(i, !this.inventory.m_8020_(i + 1).m_41619_());
            }
        }
    }

    @Override
    protected int getInventorySize() {
        return 9;
    }

    public MenuType<CoralosaurMenu> getMenuType() {
        return ADMenus.CORALOSAUR.get();
    }

    @Override
    public SlotAccess m_141942_(int slot) {
        int i = slot - 400;
        if (i >= 0 && i < this.getInventorySize() && i < this.inventory.m_6643_()) {
            if (i == 0) {
                return this.createEquipmentSlotAccess(i, itemStack -> itemStack.m_41619_() || itemStack.m_150930_(Items.f_42450_));
            }
            return this.createEquipmentSlotAccess(i, itemStack -> itemStack.m_41619_() || itemStack.m_150930_(Items.f_42009_));
        }
        int j = slot - 500 + this.getInventorySize();
        return j >= this.getInventorySize() && j < this.inventory.m_6643_() ? SlotAccess.m_147292_((Container)this.inventory, (int)j) : super.m_141942_(slot);
    }

    private static class FishMeleeAttackGoal
    extends MeleeAttackGoal {
        public FishMeleeAttackGoal(PathfinderMob mob) {
            super(mob, 1.5, true);
        }

        protected void m_6739_(LivingEntity enemy, double distToEnemySqr) {
            double d0 = this.m_6639_(enemy);
            if (distToEnemySqr <= d0 && this.m_25564_()) {
                this.m_25563_();
                this.f_25540_.m_6674_(InteractionHand.MAIN_HAND);
                this.doHurtTarget((Entity)enemy);
            }
        }

        public void doHurtTarget(Entity entity) {
            boolean flag;
            float damage = 2.0f;
            float knockback = 1.0f;
            int i = EnchantmentHelper.m_44914_((LivingEntity)this.f_25540_);
            if (i > 0) {
                entity.m_20254_(i * 4);
            }
            if (flag = entity.m_6469_(this.f_25540_.m_269291_().m_269333_((LivingEntity)this.f_25540_), damage)) {
                if (entity instanceof LivingEntity) {
                    ((LivingEntity)entity).m_147240_((double)(knockback * 0.5f), (double)Mth.m_14031_((float)(this.f_25540_.m_146908_() * ((float)Math.PI / 180))), (double)(-Mth.m_14089_((float)(this.f_25540_.m_146908_() * ((float)Math.PI / 180)))));
                    this.f_25540_.m_20256_(this.f_25540_.m_20184_().m_82542_(0.6, 1.0, 0.6));
                }
                this.f_25540_.m_19970_((LivingEntity)this.f_25540_, entity);
                this.f_25540_.m_21335_(entity);
            }
        }
    }

    private static class TargetNotOurPlayer<T extends LivingEntity>
    extends NearestAttackableTargetGoal<T> {
        @Nullable
        private final Player owner;

        public TargetNotOurPlayer(Mob mob, Class<T> targetType, boolean mustSee, @Nullable Player owner) {
            super(mob, targetType, mustSee);
            this.owner = owner;
        }

        protected boolean m_26150_(@Nullable LivingEntity potentialTarget, TargetingConditions targetPredicate) {
            if (this.owner != null) {
                OwnableEntity ownable;
                if (potentialTarget == this.owner) {
                    return false;
                }
                if (potentialTarget instanceof OwnableEntity && (ownable = (OwnableEntity)potentialTarget).m_269323_() == this.owner) {
                    return false;
                }
            }
            return super.m_26150_(potentialTarget, targetPredicate);
        }
    }
}

