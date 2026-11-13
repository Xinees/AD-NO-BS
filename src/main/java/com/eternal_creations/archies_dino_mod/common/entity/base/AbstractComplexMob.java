/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.astemir.astemirlib.re.common.action.ActionState
 *  com.astemir.astemirlib.re.common.action.ActionTarget
 *  com.astemir.astemirlib.re.common.action.ActionType
 *  com.astemir.astemirlib.re.common.action.SyncedActionHolder
 *  com.astemir.astemirlib.re.common.world.entity.EntityLocomotion
 *  com.astemir.astemirlib.re.core.math.AMath
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMap
 *  it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.syncher.EntityDataAccessor
 *  net.minecraft.network.syncher.EntityDataSerializer
 *  net.minecraft.network.syncher.EntityDataSerializers
 *  net.minecraft.network.syncher.SynchedEntityData
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.damagesource.DamageTypes
 *  net.minecraft.world.entity.AgeableMob
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.Entity$MoveFunction
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.OwnableEntity
 *  net.minecraft.world.entity.ai.navigation.PathNavigation
 *  net.minecraft.world.entity.animal.Animal
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.GameRules
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 *  net.minecraft.world.phys.shapes.BooleanOp
 *  net.minecraft.world.phys.shapes.Shapes
 *  net.minecraft.world.phys.shapes.VoxelShape
 *  net.minecraft.world.scores.Team
 *  org.jetbrains.annotations.Nullable
 */
package com.eternal_creations.archies_dino_mod.common.entity.base;

import com.astemir.astemirlib.re.common.action.ActionState;
import com.astemir.astemirlib.re.common.action.ActionTarget;
import com.astemir.astemirlib.re.common.action.ActionType;
import com.astemir.astemirlib.re.common.action.SyncedActionHolder;
import com.astemir.astemirlib.re.common.world.entity.EntityLocomotion;
import com.astemir.astemirlib.re.core.math.AMath;
import com.eternal_creations.archies_dino_mod.common.entity.ai.control.pathfinder.SmoothPathNavigation;
import com.eternal_creations.archies_dino_mod.common.entity.base.ActionTargetBlock;
import com.eternal_creations.archies_dino_mod.common.entity.base.ActionTargetEntity;
import com.eternal_creations.archies_dino_mod.common.entity.base.Pet;
import com.eternal_creations.archies_dino_mod.common.entity.base.StateDriven;
import com.eternal_creations.archies_dino_mod.common.entity.component.EntityComponentHolder;
import com.eternal_creations.archies_dino_mod.common.entity.component.PetComponent;
import com.eternal_creations.archies_dino_mod.common.entity.utils.EntityUtils;
import com.eternal_creations.archies_dino_mod.server.ServerConfig;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Supplier;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.scores.Team;
import org.jetbrains.annotations.Nullable;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public abstract class AbstractComplexMob
extends Animal
implements ActionTargetEntity,
ActionTargetBlock,
StateDriven,
SyncedActionHolder,
Pet {
    public static final byte FLAG_TAMED = 1;
    protected static final EntityDataAccessor<Byte> DATA_MOB_STATE = SynchedEntityData.m_135353_(AbstractComplexMob.class, (EntityDataSerializer)EntityDataSerializers.f_135027_);
    private static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.m_135353_(AbstractComplexMob.class, (EntityDataSerializer)EntityDataSerializers.f_135027_);
    private static final EntityDataAccessor<Optional<UUID>> DATA_OWNERUUID_ID = SynchedEntityData.m_135353_(AbstractComplexMob.class, (EntityDataSerializer)EntityDataSerializers.f_135041_);
    private final EntityLocomotion locomotion;
    protected final EntityComponentHolder componentHolder;
    private final PetComponent<?> petComponent;
    private final Object2ObjectMap<ActionType, ActionState> actions = new Object2ObjectOpenHashMap();
    @Nullable
    private LivingEntity tempAttackTarget;
    @Nullable
    private BlockPos tempBlockTarget;

    protected AbstractComplexMob(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
        this.componentHolder = new EntityComponentHolder();
        this.petComponent = this.componentHolder.addComponent(this.createPet());
        this.locomotion = this.createLocomotion();
        this.setupActionStates();
    }

    public void setupActionStates() {
        this.actions.clear();
        for (ActionState state : this.makeActionSet()) {
            this.actions.put((Object)state.getActionType(), (Object)state);
        }
    }

    protected abstract Set<ActionState> makeActionSet();

    protected abstract EntityLocomotion createLocomotion();

    protected abstract PetComponent<?> createPet();

    protected void m_8097_() {
        super.m_8097_();
        this.f_19804_.m_135372_(DATA_MOB_STATE, (Object)0);
        this.f_19804_.m_135372_(DATA_FLAGS_ID, (Object)0);
        this.f_19804_.m_135372_(DATA_OWNERUUID_ID, Optional.empty());
    }

    public void m_8119_() {
        super.m_8119_();
        this.locomotion.tick((LivingEntity)this);
        this.componentHolder.tick();
        this.tickActionStates();
    }

    public void m_8107_() {
        super.m_8107_();
        this.componentHolder.aiStep();
    }

    protected void m_8024_() {
        super.m_8024_();
        this.componentHolder.tick();
    }

    protected PathNavigation m_6037_(Level level) {
        return new SmoothPathNavigation((Mob)this, level);
    }

    public void m_7023_(Vec3 motion) {
        motion = this.travelComponent(motion);
        if (this.isMovementBlocked()) {
            motion = motion.m_82542_(0.0, 1.0, 0.0);
        }
        super.m_7023_(motion);
    }

    protected Vec3 travelComponent(Vec3 motion) {
        return this.componentHolder.travel(motion);
    }

    protected void m_274498_(Player player, Vec3 motion) {
        this.componentHolder.tickRidden(player, motion);
    }

    protected void m_20348_(Entity passenger) {
        super.m_20348_(passenger);
        this.componentHolder.addPassenger(passenger);
    }

    protected void m_19956_(Entity passenger, Entity.MoveFunction callback) {
        super.m_19956_(passenger, callback);
        this.componentHolder.positionRider(passenger, callback);
    }

    public Vec3 m_7688_(LivingEntity passenger) {
        return this.m_20182_();
    }

    protected boolean isMovementBlocked() {
        return false;
    }

    protected boolean canRotate() {
        return true;
    }

    public InteractionResult m_6071_(Player player, InteractionHand hand) {
        ItemStack itemStack = player.m_21120_(hand);
        if (itemStack.m_150930_(Items.f_42751_)) {
            if (!this.m_9236_().f_46443_) {
                this.pet().tame(player);
                this.f_21344_.m_26573_();
                this.m_6710_(null);
                this.m_9236_().m_7605_((Entity)this, (byte)7);
            }
            return InteractionResult.m_19078_((boolean)this.m_9236_().f_46443_);
        }
        return this.componentHolder.mobInteract(player, hand);
    }

    public void alignBodyRotationToGrid(float rotationSpeed) {
        float yBodyRot = this.f_20883_;
        float normalizedRot = (yBodyRot % 360.0f + 360.0f) % 360.0f;
        float targetRot = (float)Math.round(normalizedRot / 90.0f) * 90.0f;
        if (targetRot >= 360.0f) {
            targetRot -= 360.0f;
        }
        this.f_20883_ = AMath.lerpAngle((float)rotationSpeed, (float)yBodyRot, (float)targetRot);
    }

    @Override
    public boolean m_6898_(ItemStack stack) {
        return false;
    }

    @Override
    public boolean isTamed() {
        return this.hasFlag((byte)1);
    }

    @Override
    public void setTamed(boolean tamed) {
        this.setFlag((byte)1, tamed);
    }

    public void setFlag(byte mask, boolean value) {
        byte flags = (Byte)this.f_19804_.m_135370_(DATA_FLAGS_ID);
        if (value) {
            this.f_19804_.m_135381_(DATA_FLAGS_ID, (Object)((byte)(flags | mask)));
        } else {
            this.f_19804_.m_135381_(DATA_FLAGS_ID, (Object)((byte)(flags & ~mask)));
        }
    }

    public boolean hasFlag(byte mask) {
        return ((Byte)this.f_19804_.m_135370_(DATA_FLAGS_ID) & mask) != 0;
    }

    @Override
    public void setStateId(byte stateId) {
        this.f_19804_.m_135381_(DATA_MOB_STATE, (Object)stateId);
    }

    @Override
    public byte getStateId() {
        return (Byte)this.f_19804_.m_135370_(DATA_MOB_STATE);
    }

    @Nullable
    public UUID m_21805_() {
        return ((Optional)this.f_19804_.m_135370_(DATA_OWNERUUID_ID)).orElse(null);
    }

    @Override
    public void setOwnerUUID(@Nullable UUID uuid) {
        this.f_19804_.m_135381_(DATA_OWNERUUID_ID, Optional.ofNullable(uuid));
    }

    public boolean m_7307_(Entity entity) {
        if (this.isTamed()) {
            LivingEntity owner = this.m_269323_();
            if (entity == owner) {
                return true;
            }
            if (owner != null) {
                return owner.m_7307_(entity);
            }
        }
        return super.m_7307_(entity);
    }

    protected boolean m_8028_() {
        return this.getSettings().despawnsOnPeaceful();
    }

    protected abstract TameableDinoSettings getSettings();

    @Nullable
    public Team m_5647_() {
        LivingEntity owner;
        if (this.isTamed() && (owner = this.m_269323_()) != null) {
            return owner.m_5647_();
        }
        return super.m_5647_();
    }

    public boolean m_217066_(LivingEntity target) {
        float attackRange = this.getAttackRange();
        return EntityUtils.getDistanceSqr((Entity)this, (Entity)target) <= (double)(attackRange * attackRange);
    }

    public float getAttackRange() {
        return 2.0f;
    }

    public boolean m_6779_(LivingEntity target) {
        Pet pet;
        if (target.m_6095_() == this.m_6095_() && !this.isTamed() && target instanceof Pet && !(pet = (Pet)target).isTamed()) {
            return false;
        }
        if (this.isTamed() && target instanceof OwnableEntity && (pet = (OwnableEntity)target).m_21805_() == this.m_21805_()) {
            return false;
        }
        return !this.isOwnedBy(target) && super.m_6779_(target);
    }

    public boolean m_6549_(EntityType<?> type) {
        if (type == EntityType.f_20532_) {
            if (!this.isTamed() && !this.getSettings().canTargetPlayersUntamed()) {
                return false;
            }
            if (this.isTamed() && !this.getSettings().canFightPlayersTamed()) {
                return false;
            }
        }
        return super.m_6549_(type);
    }

    public boolean m_6469_(DamageSource source, float amount) {
        if (source.m_7639_() != null && this.m_20363_(source.m_7639_())) {
            return false;
        }
        boolean hurt = super.m_6469_(source, amount);
        if (hurt && source.m_276093_(DamageTypes.f_268612_)) {
            Level level = this.m_9236_();
            if (level instanceof ServerLevel) {
                ServerLevel serverLevel = (ServerLevel)level;
                float width = this.m_20205_() * 0.8f;
                AABB aabb = AABB.m_165882_((Vec3)this.m_146892_(), (double)width, (double)1.0E-6, (double)width);
                Vec3 walls = Vec3.f_82478_;
                for (BlockPos pos : BlockPos.m_121921_((AABB)aabb).toList()) {
                    BlockState state = serverLevel.m_8055_(pos);
                    if (state.m_60795_() || !state.m_60828_((BlockGetter)serverLevel, pos) || !Shapes.m_83157_((VoxelShape)state.m_60812_((BlockGetter)serverLevel, pos).m_83216_((double)pos.m_123341_(), (double)pos.m_123342_(), (double)pos.m_123343_()), (VoxelShape)Shapes.m_83064_((AABB)aabb), (BooleanOp)BooleanOp.f_82689_)) continue;
                    walls = walls.m_82549_(pos.m_252807_());
                }
                if (walls != Vec3.f_82478_) {
                    walls = walls.m_82541_().m_82490_(-0.05);
                    this.m_5997_(walls.f_82479_, walls.f_82480_, walls.f_82481_);
                }
            }
            return false;
        }
        return hurt;
    }

    public void m_7380_(CompoundTag compound) {
        super.m_7380_(compound);
        this.componentHolder.addAdditionalSaveData(compound);
        compound.m_128344_("State", ((Byte)this.f_19804_.m_135370_(DATA_MOB_STATE)).byteValue());
    }

    public void m_7378_(CompoundTag compound) {
        super.m_7378_(compound);
        this.componentHolder.readAdditionalSaveData(compound);
        if (compound.m_128441_("State")) {
            this.f_19804_.m_135381_(DATA_MOB_STATE, (Object)compound.m_128445_("State"));
        }
    }

    public void m_6667_(DamageSource cause) {
        Component deathMessage = this.m_21231_().m_19293_();
        super.m_6667_(cause);
        if (this.f_20890_ && !this.m_9236_().f_46443_ && this.m_9236_().m_46469_().m_46207_(GameRules.f_46142_) && this.m_269323_() instanceof ServerPlayer) {
            this.m_269323_().m_213846_(deathMessage);
        }
    }

    public void playSound(Supplier<SoundEvent> sound) {
        this.m_216990_(sound.get());
    }

    public void m_216990_(SoundEvent sound) {
        if (!this.m_20067_()) {
            this.m_5496_(sound, this.m_6121_(), this.m_6100_());
        }
    }

    public void m_7822_(byte id) {
        this.componentHolder.handleEntityEvent(id);
        super.m_7822_(id);
    }

    protected void m_5907_() {
        super.m_5907_();
        this.componentHolder.dropEquipment();
    }

    public boolean m_6914_(LevelReader level) {
        return super.m_6914_(level) && level.m_45756_((Entity)this, this.m_6095_().m_20680_().m_20393_(this.m_20182_()));
    }

    public AABB m_6921_() {
        return super.m_6921_().m_82400_(2.0);
    }

    public EntityLocomotion getLocomotion() {
        return this.locomotion;
    }

    public boolean isAdult() {
        return !this.m_6162_();
    }

    public Object2ObjectMap<ActionType, ActionState> getActionStates() {
        return this.actions;
    }

    public ActionTarget getActionTarget() {
        return ActionTarget.ENTITY;
    }

    @Override
    public boolean isSitting() {
        return false;
    }

    @Override
    public void setSitting(boolean sitting) {
    }

    @Nullable
    public AgeableMob m_142606_(ServerLevel serverLevel, AgeableMob ageableMob) {
        return null;
    }

    @Override
    public void setActionTargetBlock(BlockPos targetBlock) {
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

    @Override
    public PetComponent<?> pet() {
        return this.petComponent;
    }

    public record TameableDinoSettings(boolean despawnsOnPeaceful, boolean canTargetPlayersUntamed, boolean canFightPlayersTamed, ServerConfig.EntityStats stats) {
        public static final Codec<TameableDinoSettings> CODEC = RecordCodecBuilder.create(instance -> instance.group((App)Codec.BOOL.fieldOf("despawns_on_peaceful").forGetter(TameableDinoSettings::despawnsOnPeaceful), (App)Codec.BOOL.fieldOf("untamed_can_target_players").forGetter(TameableDinoSettings::canTargetPlayersUntamed), (App)Codec.BOOL.fieldOf("tamed_can_target_other_players").forGetter(TameableDinoSettings::canFightPlayersTamed), (App)ServerConfig.EntityStats.CODEC.fieldOf("settings").forGetter(TameableDinoSettings::stats)).apply((Applicative)instance, TameableDinoSettings::new));
    }
}

