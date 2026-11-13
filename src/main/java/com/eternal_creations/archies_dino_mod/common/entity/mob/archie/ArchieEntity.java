/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.astemir.astemirlib.re.common.action.ActionState
 *  com.astemir.astemirlib.re.common.action.ActionTarget
 *  com.astemir.astemirlib.re.common.action.ActionType
 *  com.astemir.astemirlib.re.common.action.SyncedActionHolder
 *  com.astemir.astemirlib.re.common.world.entity.EntityLocomotion
 *  com.google.common.collect.Lists
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMap
 *  it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.core.Holder
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.syncher.EntityDataAccessor
 *  net.minecraft.network.syncher.EntityDataSerializer
 *  net.minecraft.network.syncher.EntityDataSerializers
 *  net.minecraft.network.syncher.SynchedEntityData
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.tags.DamageTypeTags
 *  net.minecraft.world.DifficultyInstance
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.MenuProvider
 *  net.minecraft.world.SimpleMenuProvider
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.AgeableMob
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.Entity$RemovalReason
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.ExperienceOrb
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.MobSpawnType
 *  net.minecraft.world.entity.OwnableEntity
 *  net.minecraft.world.entity.PathfinderMob
 *  net.minecraft.world.entity.Pose
 *  net.minecraft.world.entity.SpawnGroupData
 *  net.minecraft.world.entity.ai.goal.FloatGoal
 *  net.minecraft.world.entity.ai.goal.Goal
 *  net.minecraft.world.entity.ai.goal.LookAtPlayerGoal
 *  net.minecraft.world.entity.ai.goal.RandomLookAroundGoal
 *  net.minecraft.world.entity.ai.goal.TradeWithPlayerGoal
 *  net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal
 *  net.minecraft.world.entity.ai.navigation.PathNavigation
 *  net.minecraft.world.entity.npc.AbstractVillager
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.AbstractContainerMenu
 *  net.minecraft.world.inventory.MerchantMenu
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.item.trading.Merchant
 *  net.minecraft.world.item.trading.MerchantOffer
 *  net.minecraft.world.item.trading.MerchantOffers
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.level.ServerLevelAccessor
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.Nullable
 */
package com.eternal_creations.archies_dino_mod.common.entity.mob.archie;

import com.astemir.astemirlib.re.common.action.ActionState;
import com.astemir.astemirlib.re.common.action.ActionTarget;
import com.astemir.astemirlib.re.common.action.ActionType;
import com.astemir.astemirlib.re.common.action.SyncedActionHolder;
import com.astemir.astemirlib.re.common.world.entity.EntityLocomotion;
import com.eternal_creations.archies_dino_mod.common.ADStrings;
import com.eternal_creations.archies_dino_mod.common.ADTags;
import com.eternal_creations.archies_dino_mod.common.entity.ADActionTypes;
import com.eternal_creations.archies_dino_mod.common.entity.ADEntityTypes;
import com.eternal_creations.archies_dino_mod.common.entity.ai.control.pathfinder.SmoothPathNavigation;
import com.eternal_creations.archies_dino_mod.common.entity.ai.goal.action.GoalActionMeleeAttack;
import com.eternal_creations.archies_dino_mod.common.entity.ai.goal.action.WrappedAction;
import com.eternal_creations.archies_dino_mod.common.entity.base.ActionTargetEntity;
import com.eternal_creations.archies_dino_mod.common.entity.base.Pet;
import com.eternal_creations.archies_dino_mod.common.entity.mob.archie.ArchieTrades;
import com.eternal_creations.archies_dino_mod.common.entity.mob.archie.goals.FollowFriendGoal;
import com.eternal_creations.archies_dino_mod.common.entity.mob.archie.goals.GoAwayGoal;
import com.eternal_creations.archies_dino_mod.common.entity.mob.archie.goals.ListenToPlayerGoal;
import com.eternal_creations.archies_dino_mod.common.entity.mob.archie.targeting.ArchieHurtByTargetGoal;
import com.eternal_creations.archies_dino_mod.common.entity.mob.archie.targeting.FriendHurtByTargetGoal;
import com.eternal_creations.archies_dino_mod.common.entity.mob.archie.targeting.FriendHurtTargetGoal;
import com.eternal_creations.archies_dino_mod.common.menu.ArchieMenu;
import com.eternal_creations.archies_dino_mod.common.sound.ADSoundEvents;
import com.eternal_creations.archies_dino_mod.networking.clientbound.ArchieSpeechMessage;
import com.eternal_creations.archies_dino_mod.platform.Services;
import com.eternal_creations.archies_dino_mod.server.ServerConfig;
import com.eternal_creations.archies_dino_mod.util.ResourceSupplier;
import com.eternal_creations.archies_dino_mod.util.handlers.ArchieHandler;
import com.google.common.collect.Lists;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Set;
import java.util.UUID;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TradeWithPlayerGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MerchantMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.Merchant;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ArchieEntity
extends AbstractVillager
implements ActionTargetEntity,
SyncedActionHolder,
OwnableEntity {
    private static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.m_135353_(ArchieEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135027_);
    private static final EntityDataAccessor<Optional<UUID>> DATA_OWNERUUID_ID = SynchedEntityData.m_135353_(ArchieEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135041_);
    private static final int DESPAWN_WHEN_DENIED = 400;
    private static final int DESPAWN_WHEN_ALONE = 3000;
    private static final int RECOVERY_TIME = 1200;
    private static final int BARK_COOLDOWN = 6000;
    public static final ArchieSettings DEFAULT_STATS = new ArchieSettings(true, true, new ServerConfig.EntityStats(0.2, 30.0, 0.0, 4.0, 48.0, 6.0));
    private final Object2ObjectMap<ActionType, ActionState> actions = new Object2ObjectOpenHashMap();
    private final EntityLocomotion locomotion = this.createLocomotion();
    @Nullable
    private LivingEntity tempAttackTarget;
    public ActionState actionAttack;
    public ActionState actionHurt;
    public int recovering = -1;
    public int denyTick = -1;
    private int aloneTick = 0;
    private int barked = -1;

    public ArchieEntity(EntityType<? extends AbstractVillager> entityType, Level level) {
        super(entityType, level);
        this.setupActionStates();
    }

    public void setupActionStates() {
        this.actions.clear();
        for (ActionState state : this.makeActionSet()) {
            this.actions.put((Object)state.getActionType(), (Object)state);
        }
    }

    public boolean m_6549_(EntityType<?> type) {
        return super.m_6549_(type) && this.recovering <= 0;
    }

    public boolean m_142066_() {
        return this.isTamed() && this.recovering <= 0 && super.m_142066_();
    }

    protected void m_8097_() {
        super.m_8097_();
        this.f_19804_.m_135372_(DATA_FLAGS_ID, (Object)0);
        this.f_19804_.m_135372_(DATA_OWNERUUID_ID, Optional.empty());
    }

    public boolean m_6785_(double distanceToClosestPlayer) {
        return false;
    }

    protected void m_8058_(MerchantOffer offer) {
        if (offer.m_45383_()) {
            int i = 3 + this.f_19796_.m_188503_(4);
            this.m_9236_().m_7967_((Entity)new ExperienceOrb(this.m_9236_(), this.m_20185_(), this.m_20186_() + 0.5, this.m_20189_(), i));
        }
    }

    public boolean m_7826_() {
        return false;
    }

    public void m_7380_(CompoundTag tag) {
        super.m_7380_(tag);
        if (this.m_21805_() != null) {
            tag.m_128362_("owner", this.m_21805_());
        }
        tag.m_128405_("recovering", this.recovering);
        tag.m_128405_("denyTick", this.denyTick);
        tag.m_128405_("aloneTick", this.aloneTick);
        tag.m_128405_("barked", this.barked);
    }

    public void m_7378_(CompoundTag tag) {
        super.m_7378_(tag);
        if (tag.m_128441_("owner")) {
            this.setOwnerUUID(tag.m_128342_("owner"));
        }
        if (tag.m_128441_("recovering")) {
            this.recovering = tag.m_128451_("recovering");
        }
        if (tag.m_128441_("denyTick")) {
            this.denyTick = tag.m_128451_("denyTick");
        }
        if (tag.m_128441_("aloneTick")) {
            this.aloneTick = tag.m_128451_("aloneTick");
        }
        if (tag.m_128441_("barked")) {
            this.barked = tag.m_128451_("barked");
        }
    }

    public void m_7189_(@Nullable Player player) {
        if (player instanceof ServerPlayer && this.m_7962_() == null) {
            this.m_6616_().clear();
            super.m_7189_(player);
            this.m_7604_();
        } else {
            super.m_7189_(player);
        }
    }

    public void m_45301_(Player player, Component displayName, int level) {
        OptionalInt optionalint = player.m_5893_((MenuProvider)new SimpleMenuProvider((id, inventory, player1) -> new MerchantMenu(id, inventory, (Merchant)this), displayName));
        if (optionalint.isPresent()) {
            this.m_7189_(player);
            MerchantOffers merchantoffers = this.m_6616_();
            if (!merchantoffers.isEmpty()) {
                player.m_7662_(optionalint.getAsInt(), merchantoffers, level, this.m_7809_(), this.m_7826_(), this.m_7862_());
            }
        }
    }

    protected void m_7604_() {
        ArchieTrades.updateTrades(this, this.m_7962_());
    }

    public double m_20204_() {
        if (this.f_185931_ || this.f_19862_) {
            return super.m_20204_();
        }
        return (double)this.m_20192_() < 0.5 ? (double)0.2f : 1.4;
    }

    public void deny() {
        this.denyTick = 1;
    }

    protected PathNavigation m_6037_(Level level) {
        return new SmoothPathNavigation((Mob)this, level);
    }

    protected void m_8099_() {
        this.f_21345_.m_25352_(0, (Goal)new FloatGoal((Mob)this));
        this.f_21345_.m_25352_(1, GoalActionMeleeAttack.of(WrappedAction.of(() -> this.actionAttack, 0.8f).maxRange(() -> 3.0)).speed(1.2).interval(40).canAttack(() -> true).targetFilter(target -> !(target instanceof Player)).build(this));
        this.f_21345_.m_25352_(2, (Goal)new GoAwayGoal(this, 1.25));
        this.f_21345_.m_25352_(3, (Goal)new TradeWithPlayerGoal((AbstractVillager)this));
        this.f_21345_.m_25352_(4, (Goal)new FollowFriendGoal(this, 1.0, 10.0f, 5.0f));
        this.f_21345_.m_25352_(5, (Goal)new ListenToPlayerGoal(this, 1.2));
        this.f_21345_.m_25352_(6, (Goal)new WaterAvoidingRandomStrollGoal((PathfinderMob)this, 1.0));
        this.f_21345_.m_25352_(7, (Goal)new LookAtPlayerGoal((Mob)this, Player.class, 6.0f));
        this.f_21345_.m_25352_(8, (Goal)new RandomLookAroundGoal((Mob)this));
        this.f_21346_.m_25352_(1, (Goal)new FriendHurtByTargetGoal(this));
        this.f_21346_.m_25352_(2, (Goal)new FriendHurtTargetGoal(this));
        this.f_21346_.m_25352_(3, (Goal)new ArchieHurtByTargetGoal((PathfinderMob)this));
    }

    protected Set<ActionState> makeActionSet() {
        this.actionAttack = ActionState.of((ActionType)ADActionTypes.ATTACK_HIT, (float)2.0f, (float)0.2f, (float)0.3f).exitTime(1.9f).onTick(tick -> {
            if (tick == 17) {
                this.attackActionTargetEntity(1.0f, 0.1f);
            }
        });
        this.actionHurt = ActionState.of((ActionType)ADActionTypes.HURT, (float)0.75f, (float)0.2f, (float)0.3f).exitTime(0.7f);
        return Set.of(this.actionAttack, this.actionHurt);
    }

    protected EntityLocomotion createLocomotion() {
        return EntityLocomotion.of().speed(0.02f, 0.24f).transitionLimit(0.2f).responsiveness(0.3f).build();
    }

    public void m_7023_(Vec3 motion) {
        if (this.actionAttack.isActive()) {
            motion = motion.m_82542_(0.0, 1.0, 0.0);
        }
        super.m_7023_(motion);
    }

    public ActionTarget getActionTarget() {
        return ActionTarget.ENTITY;
    }

    public Object2ObjectMap<ActionType, ActionState> getActionStates() {
        return this.actions;
    }

    @Override
    public void setActionTargetEntity(LivingEntity target) {
        this.tempAttackTarget = target;
    }

    @Override
    @Nullable
    public LivingEntity getActionTargetEntity() {
        return this.tempAttackTarget;
    }

    public EntityLocomotion getLocomotion() {
        return this.locomotion;
    }

    public SpawnGroupData m_6518_(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag dataTag) {
        if (reason == MobSpawnType.COMMAND) {
            ArchieHandler.getArchieHandler((LevelAccessor)level, handler -> handler.setArchieID(this.f_19820_));
        }
        this.m_8061_(EquipmentSlot.MAINHAND, new ItemStack((ItemLike)Items.f_42383_));
        return super.m_6518_(level, difficulty, reason, spawnData, dataTag);
    }

    @Nullable
    public AgeableMob m_142606_(ServerLevel serverLevel, AgeableMob ageableMob) {
        return null;
    }

    public void m_8119_() {
        Level level = this.m_9236_();
        if (level instanceof ServerLevel) {
            ServerLevel level2 = (ServerLevel)level;
            if (this.recovering > 0) {
                --this.recovering;
            }
            if (this.barked > 0) {
                --this.barked;
            } else if (this.f_19797_ % 100 == 0) {
                this.m_5634_(1.0f);
            }
            if (this.denyTick > 0 && this.denyTick++ >= 400) {
                this.m_142687_(Entity.RemovalReason.DISCARDED);
                return;
            }
            LivingEntity livingEntity = this.m_269323_();
            if (livingEntity instanceof ServerPlayer) {
                ServerPlayer owner = (ServerPlayer)livingEntity;
                if (owner.m_6047_()) {
                    this.m_20124_(Pose.CROUCHING);
                } else if (this.m_6047_()) {
                    this.m_20124_(Pose.STANDING);
                }
            }
            if (this.f_19797_ % 20 == 0) {
                if (!this.getNearbyPlayers(this.m_20191_().m_82400_(32.0)).isEmpty()) {
                    this.aloneTick = 0;
                } else if (this.aloneTick++ >= 3000) {
                    this.m_146870_();
                    return;
                }
                List<EntityType> nearbyNonTamedDinosaurs = level2.m_6249_((Entity)this, this.m_20191_().m_82400_(24.0), entity -> {
                    if (entity.m_6095_().m_204039_(ADTags.EntityTypes.DINOSAUR)) {
                        Pet pet;
                        return !(entity instanceof Pet) || !(pet = (Pet)entity).isTamed();
                    }
                    return false;
                }).stream().map(Entity::m_6095_).toList();
                if (!nearbyNonTamedDinosaurs.isEmpty()) {
                    if (this.m_269323_() instanceof ServerPlayer && this.f_19796_.m_188503_(4) == 0 && this.barked <= 0) {
                        if (nearbyNonTamedDinosaurs.contains(ADEntityTypes.SCULKSAUR.get())) {
                            this.say(this.f_19796_.m_188499_() ? VoicedChatLog.ARCHIE_SCULKSAUR_1 : VoicedChatLog.ARCHIE_SCULKSAUR_2);
                            this.barked = 6000;
                        } else if (nearbyNonTamedDinosaurs.contains(ADEntityTypes.SCULKRAPTOR.get())) {
                            this.say(this.f_19796_.m_188499_() ? VoicedChatLog.ARCHIE_SCULK_RAPTOR_1 : VoicedChatLog.ARCHIE_SCULK_RAPTOR_2);
                            this.barked = 6000;
                        } else if (nearbyNonTamedDinosaurs.contains(ADEntityTypes.THERIMOSS.get())) {
                            this.say(this.f_19796_.m_188499_() ? VoicedChatLog.ARCHIE_THERIMOSS_1 : VoicedChatLog.ARCHIE_THERIMOSS_2);
                            this.barked = 6000;
                        } else if (nearbyNonTamedDinosaurs.contains(ADEntityTypes.CORALOSAUR.get())) {
                            this.say(this.f_19796_.m_188499_() ? VoicedChatLog.ARCHIE_CORALOSAUR_1 : VoicedChatLog.ARCHIE_CORALOSAUR_2);
                            this.barked = 6000;
                        } else if (nearbyNonTamedDinosaurs.contains(ADEntityTypes.OBSIDICERATOPS.get())) {
                            this.say(this.f_19796_.m_188499_() ? VoicedChatLog.ARCHIE_OBSIDICERATOPS_1 : VoicedChatLog.ARCHIE_OBSIDICERATOPS_2);
                            this.barked = 6000;
                        } else if (nearbyNonTamedDinosaurs.contains(ADEntityTypes.LUSHSAUR.get())) {
                            this.say(this.f_19796_.m_188499_() ? VoicedChatLog.ARCHIE_LUSHSAUR_1 : VoicedChatLog.ARCHIE_LUSHSAUR_2);
                            this.barked = 6000;
                        } else if (nearbyNonTamedDinosaurs.contains(ADEntityTypes.CHORUSOATL.get())) {
                            this.say(this.f_19796_.m_188499_() ? VoicedChatLog.ARCHIE_CHORUSOATL_1 : VoicedChatLog.ARCHIE_CHORUSOATL_2);
                            this.barked = 6000;
                        }
                    }
                    this.m_20124_(Pose.CROUCHING);
                } else if (this.recovering <= 0) {
                    this.m_20124_(Pose.STANDING);
                }
                if (this.m_269323_() instanceof ServerPlayer && this.barked <= 0 && this.f_19796_.m_188503_(60) == 0) {
                    Holder biome = this.m_9236_().m_204166_(this.m_20183_());
                    if (biome.m_203656_(ADTags.Biomes.SPAWNS_SCULKRAPTORS)) {
                        this.say(this.f_19796_.m_188499_() ? VoicedChatLog.ARCHIE_UNDERGROUND_1 : VoicedChatLog.ARCHIE_UNDERGROUND_2);
                        this.barked = 6000;
                    } else if (biome.m_203656_(ADTags.Biomes.SPAWNS_THERIMOSS)) {
                        this.say(this.f_19796_.m_188499_() ? VoicedChatLog.ARCHIE_ABOVEGROUND_1 : VoicedChatLog.ARCHIE_ABOVEGROUND_2);
                        this.barked = 6000;
                    } else if (biome.m_203656_(ADTags.Biomes.SPAWNS_CORALOSAURS)) {
                        this.say(this.f_19796_.m_188499_() ? VoicedChatLog.ARCHIE_ABOVEGROUND_1 : VoicedChatLog.ARCHIE_ABOVEGROUND_2);
                        this.barked = 6000;
                    } else if (biome.m_203656_(ADTags.Biomes.SPAWNS_OBSIDICERATOPSES)) {
                        this.say(this.f_19796_.m_188499_() ? VoicedChatLog.ARCHIE_DIMENSIONS_1 : VoicedChatLog.ARCHIE_DIMENSIONS_2);
                        this.barked = 6000;
                    } else if (biome.m_203656_(ADTags.Biomes.SPAWNS_LUSHSAURS)) {
                        this.say(this.f_19796_.m_188499_() ? VoicedChatLog.ARCHIE_UNDERGROUND_1 : VoicedChatLog.ARCHIE_UNDERGROUND_2);
                        this.barked = 6000;
                    } else if (biome.m_203656_(ADTags.Biomes.SPAWNS_CHORUSOATLS)) {
                        this.say(this.f_19796_.m_188499_() ? VoicedChatLog.ARCHIE_DIMENSIONS_1 : VoicedChatLog.ARCHIE_DIMENSIONS_2);
                        this.barked = 6000;
                    }
                }
                ArchieHandler.getArchieHandler(level2.m_7654_(), handler -> {
                    if (!this.m_20148_().equals(handler.getArchieID())) {
                        this.m_142687_(Entity.RemovalReason.DISCARDED);
                    }
                });
            }
        }
        if (this.m_213877_()) {
            return;
        }
        super.m_8119_();
        this.tickActionStates();
        this.locomotion.tick((LivingEntity)this);
    }

    public boolean m_6469_(DamageSource source, float amount) {
        if (this.recovering > 0) {
            return false;
        }
        boolean supra = super.m_6469_(source, amount);
        if (this.m_21223_() <= 0.0f && source.m_269533_(DamageTypeTags.f_268738_)) {
            this.m_142687_(Entity.RemovalReason.DISCARDED);
            return true;
        }
        this.playActionSynced(this.actionHurt);
        if (this.m_21223_() <= 0.0f && ServerConfig.getEntitySettings().archie().shouldRunAwayWhenOutOfHealth()) {
            this.m_20124_(Pose.CROUCHING);
            this.m_6710_(null);
            this.m_21153_(1.0f);
            this.f_20890_ = false;
            if (this.isTamed()) {
                this.recovering = 1200;
                this.say(ADStrings.Messages.ARCHIE_LOW_HEALTH);
            } else {
                this.deny();
            }
        }
        return supra;
    }

    public boolean m_21224_() {
        return super.m_21224_() && !ServerConfig.getEntitySettings().archie().shouldRunAwayWhenOutOfHealth();
    }

    protected InteractionResult m_6071_(Player player, InteractionHand hand) {
        if (!this.m_9236_().f_46443_) {
            player.m_5893_(new MenuProvider(){

                public Component m_5446_() {
                    return Component.m_237115_((String)ADStrings.Gui.ARCHIE_TITLE);
                }

                public AbstractContainerMenu m_7208_(int i, Inventory inventory, Player player) {
                    return new ArchieMenu(i, inventory);
                }
            });
        }
        return InteractionResult.SUCCESS;
    }

    public void m_142687_(Entity.RemovalReason reason) {
        Level level = this.m_9236_();
        if (level instanceof ServerLevel) {
            ServerLevel level2 = (ServerLevel)level;
            ArchieHandler.getArchieHandler(level2.m_7654_(), handler -> {
                if (this.m_20148_() == handler.getArchieID()) {
                    handler.setArchieID(null);
                }
            });
        }
        super.m_142687_(reason);
    }

    @Nullable
    public UUID m_21805_() {
        return ((Optional)this.f_19804_.m_135370_(DATA_OWNERUUID_ID)).orElse(null);
    }

    public void setOwnerUUID(@Nullable UUID uuid) {
        this.f_19804_.m_135381_(DATA_OWNERUUID_ID, Optional.ofNullable(uuid));
    }

    public void m_7713_(ItemStack stack) {
    }

    public void m_35310_() {
    }

    public boolean m_20161_() {
        return this.m_6047_();
    }

    public void tame(Player player) {
        this.setTamed(true);
        this.setOwnerUUID(player.m_20148_());
        this.denyTick = -1;
    }

    public boolean isTamed() {
        return ((Byte)this.f_19804_.m_135370_(DATA_FLAGS_ID) & 4) != 0;
    }

    public void setTamed(boolean tamed) {
        byte flag = (Byte)this.f_19804_.m_135370_(DATA_FLAGS_ID);
        if (tamed) {
            this.f_19804_.m_135381_(DATA_FLAGS_ID, (Object)((byte)(flag | 4)));
        } else {
            this.setOwnerUUID(null);
            this.f_19804_.m_135381_(DATA_FLAGS_ID, (Object)((byte)(flag & 0xFFFFFFFB)));
        }
    }

    public void say(VoicedChatLog log) {
        this.broadcast(ArchieSpeechMessage.voiced(log.text(), log.sound().get()));
    }

    public void say(String text) {
        this.broadcast(ArchieSpeechMessage.chat(text));
    }

    private void broadcast(ArchieSpeechMessage message) {
        for (Player player : this.getNearbyPlayers(this.m_20191_().m_82400_(32.0))) {
            if (!(player instanceof ServerPlayer)) continue;
            ServerPlayer serverPlayer = (ServerPlayer)player;
            Services.NETWORK.sendToClient(serverPlayer, message);
        }
    }

    protected List<Player> getNearbyPlayers(AABB area) {
        ArrayList list = Lists.newArrayList();
        for (Player player : this.m_9236_().m_6907_()) {
            if (!area.m_82393_(player.m_20185_(), player.m_20186_(), player.m_20189_())) continue;
            list.add(player);
        }
        return list;
    }

    public record VoicedChatLog(ResourceSupplier<SoundEvent> sound, String text) {
        public static final VoicedChatLog ARCHIE_ABOVEGROUND_1 = VoicedChatLog.log(ADSoundEvents.ARCHIE_ABOVEGROUND_1, "aboveground_1");
        public static final VoicedChatLog ARCHIE_ABOVEGROUND_2 = VoicedChatLog.log(ADSoundEvents.ARCHIE_ABOVEGROUND_2, "aboveground_2");
        public static final VoicedChatLog ARCHIE_ACCEPT = VoicedChatLog.log(ADSoundEvents.ARCHIE_ACCEPT, "accept");
        public static final VoicedChatLog ARCHIE_CHORUSOATL_1 = VoicedChatLog.log(ADSoundEvents.ARCHIE_CHORUSOATL_1, "chorusoatl_1");
        public static final VoicedChatLog ARCHIE_CHORUSOATL_2 = VoicedChatLog.log(ADSoundEvents.ARCHIE_CHORUSOATL_2, "chorusoatl_2");
        public static final VoicedChatLog ARCHIE_CORALOSAUR_1 = VoicedChatLog.log(ADSoundEvents.ARCHIE_CORALOSAUR_1, "coralosaur_1");
        public static final VoicedChatLog ARCHIE_CORALOSAUR_2 = VoicedChatLog.log(ADSoundEvents.ARCHIE_CORALOSAUR_2, "coralosaur_2");
        public static final VoicedChatLog ARCHIE_DENY = VoicedChatLog.log(ADSoundEvents.ARCHIE_DENY, "deny");
        public static final VoicedChatLog ARCHIE_DIMENSIONS_1 = VoicedChatLog.log(ADSoundEvents.ARCHIE_DIMENSIONS_1, "dimensions_1");
        public static final VoicedChatLog ARCHIE_DIMENSIONS_2 = VoicedChatLog.log(ADSoundEvents.ARCHIE_DIMENSIONS_2, "dimensions_2");
        public static final VoicedChatLog ARCHIE_FRIEND = VoicedChatLog.log(ADSoundEvents.ARCHIE_FRIEND, "friend");
        public static final VoicedChatLog ARCHIE_INTRO = VoicedChatLog.log(ADSoundEvents.ARCHIE_INTRO, "intro");
        public static final VoicedChatLog ARCHIE_LUSHSAUR_1 = VoicedChatLog.log(ADSoundEvents.ARCHIE_LUSHSAUR_1, "lushsaur_1");
        public static final VoicedChatLog ARCHIE_LUSHSAUR_2 = VoicedChatLog.log(ADSoundEvents.ARCHIE_LUSHSAUR_2, "lushsaur_2");
        public static final VoicedChatLog ARCHIE_OBSIDICERATOPS_1 = VoicedChatLog.log(ADSoundEvents.ARCHIE_OBSIDICERATOPS_1, "obsidiceratops_1");
        public static final VoicedChatLog ARCHIE_OBSIDICERATOPS_2 = VoicedChatLog.log(ADSoundEvents.ARCHIE_OBSIDICERATOPS_2, "obsidiceratops_2");
        public static final VoicedChatLog ARCHIE_SCULK_RAPTOR_1 = VoicedChatLog.log(ADSoundEvents.ARCHIE_SCULK_RAPTOR_1, "sculk_raptor_1");
        public static final VoicedChatLog ARCHIE_SCULK_RAPTOR_2 = VoicedChatLog.log(ADSoundEvents.ARCHIE_SCULK_RAPTOR_2, "sculk_raptor_2");
        public static final VoicedChatLog ARCHIE_SCULKSAUR_1 = VoicedChatLog.log(ADSoundEvents.ARCHIE_SCULKSAUR_1, "sculksaur_1");
        public static final VoicedChatLog ARCHIE_SCULKSAUR_2 = VoicedChatLog.log(ADSoundEvents.ARCHIE_SCULKSAUR_2, "sculksaur_2");
        public static final VoicedChatLog ARCHIE_THERIMOSS_1 = VoicedChatLog.log(ADSoundEvents.ARCHIE_THERIMOSS_1, "therimoss_1");
        public static final VoicedChatLog ARCHIE_THERIMOSS_2 = VoicedChatLog.log(ADSoundEvents.ARCHIE_THERIMOSS_2, "therimoss_2");
        public static final VoicedChatLog ARCHIE_UNDERGROUND_1 = VoicedChatLog.log(ADSoundEvents.ARCHIE_UNDERGRONUD_1, "underground_1");
        public static final VoicedChatLog ARCHIE_UNDERGROUND_2 = VoicedChatLog.log(ADSoundEvents.ARCHIE_UNDERGRONUD_2, "underground_2");

        private static VoicedChatLog log(ResourceSupplier<SoundEvent> sound, String id) {
            return new VoicedChatLog(sound, "gui.ec_ad.archie.talk." + id);
        }
    }

    public record ArchieSettings(boolean shouldSpawnNearPlayersInDinoTerritory, boolean shouldRunAwayWhenOutOfHealth, ServerConfig.EntityStats stats) {
        public static final Codec<ArchieSettings> CODEC = RecordCodecBuilder.create(instance -> instance.group((App)Codec.BOOL.fieldOf("should_spawn_near_players_in_dino_territory").forGetter(ArchieSettings::shouldSpawnNearPlayersInDinoTerritory), (App)Codec.BOOL.fieldOf("should_run_away_when_out_of_health").forGetter(ArchieSettings::shouldRunAwayWhenOutOfHealth), (App)ServerConfig.EntityStats.CODEC.fieldOf("settings").forGetter(ArchieSettings::stats)).apply((Applicative)instance, ArchieSettings::new));
    }
}

