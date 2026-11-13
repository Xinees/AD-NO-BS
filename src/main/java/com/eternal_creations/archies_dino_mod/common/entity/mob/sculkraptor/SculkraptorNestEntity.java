/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.BlockPos$MutableBlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Vec3i
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.syncher.EntityDataAccessor
 *  net.minecraft.network.syncher.EntityDataSerializer
 *  net.minecraft.network.syncher.EntityDataSerializers
 *  net.minecraft.network.syncher.SynchedEntityData
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.tags.DamageTypeTags
 *  net.minecraft.util.Mth
 *  net.minecraft.util.RandomSource
 *  net.minecraft.util.SpawnUtil$Strategy
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityDimensions
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.MobSpawnType
 *  net.minecraft.world.entity.Pose
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.entity.projectile.AbstractArrow
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.gameevent.GameEvent
 *  net.minecraft.world.level.gameevent.GameEvent$Context
 */
package com.eternal_creations.archies_dino_mod.common.entity.mob.sculkraptor;

import com.eternal_creations.archies_dino_mod.common.ADGameEvents;
import com.eternal_creations.archies_dino_mod.common.entity.ADEntityTypes;
import com.eternal_creations.archies_dino_mod.common.entity.mob.sculkraptor.SculkraptorEntity;
import com.eternal_creations.archies_dino_mod.common.items.ADItems;
import com.eternal_creations.archies_dino_mod.common.sound.ADSoundEvents;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.SpawnUtil;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class SculkraptorNestEntity
extends Entity {
    private static final double DETECT_RANGE = 16.0;
    private static final long TRIP_COOLDOWN = 12000L;
    private static final long DESPAWN_TIME = 6000L;
    private static final EntityDataAccessor<Boolean> HAS_EGG = SynchedEntityData.m_135353_(SculkraptorNestEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135035_);
    private final Map<UUID, Long> tripmap = new HashMap<UUID, Long>();
    protected int noActionTime;

    public SculkraptorNestEntity(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    public SculkraptorNestEntity(Level level) {
        super(ADEntityTypes.SCULKRAPTOR_NEST.get(), level);
    }

    public static Optional<SculkraptorNestEntity> tryPlace(ServerLevel level, BlockPos pos, int attempts, int range, int yOffset, SpawnUtil.Strategy strategy) {
        BlockPos.MutableBlockPos blockpos$mutableblockpos = pos.m_122032_();
        for (int i = 0; i < attempts; ++i) {
            SculkraptorNestEntity nest;
            int j = Mth.m_216287_((RandomSource)level.f_46441_, (int)(-range), (int)range);
            int k = Mth.m_216287_((RandomSource)level.f_46441_, (int)(-range), (int)range);
            blockpos$mutableblockpos.m_122154_((Vec3i)pos, j, yOffset, k);
            if (!level.m_6857_().m_61937_((BlockPos)blockpos$mutableblockpos) || !SculkraptorNestEntity.moveToPossibleSpawnPosition(level, yOffset, blockpos$mutableblockpos, strategy) || (nest = (SculkraptorNestEntity)ADEntityTypes.SCULKRAPTOR_NEST.get().m_262451_(level, null, null, (BlockPos)blockpos$mutableblockpos, MobSpawnType.EVENT, false, false)) == null) continue;
            level.m_47205_((Entity)nest);
            return Optional.of(nest);
        }
        return Optional.empty();
    }

    private static boolean moveToPossibleSpawnPosition(ServerLevel level, int yOffset, BlockPos.MutableBlockPos pos, SpawnUtil.Strategy strategy) {
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos().m_122190_((Vec3i)pos);
        BlockState blockstate = level.m_8055_((BlockPos)blockpos$mutableblockpos);
        for (int i = yOffset; i >= -yOffset; --i) {
            pos.m_122173_(Direction.DOWN);
            blockpos$mutableblockpos.m_122159_((Vec3i)pos, Direction.UP);
            BlockState blockstate1 = level.m_8055_((BlockPos)pos);
            if (strategy.m_216427_(level, (BlockPos)pos, blockstate1, (BlockPos)blockpos$mutableblockpos, blockstate)) {
                pos.m_122173_(Direction.UP);
                return true;
            }
            blockstate = blockstate1;
        }
        return false;
    }

    protected void m_8097_() {
        this.f_19804_.m_135372_(HAS_EGG, (Object)true);
    }

    public void m_7350_(EntityDataAccessor<?> key) {
        if (key == HAS_EGG) {
            this.m_6210_();
        }
    }

    public EntityDimensions m_6972_(Pose pose) {
        EntityDimensions entitydimensions = super.m_6972_(pose);
        return !this.hasEgg() ? EntityDimensions.m_20398_((float)entitydimensions.f_20377_, (float)0.5f) : entitydimensions;
    }

    public boolean hasEgg() {
        return (Boolean)this.f_19804_.m_135370_(HAS_EGG);
    }

    public void setHasEgg(boolean hasEgg) {
        this.f_19804_.m_135381_(HAS_EGG, (Object)hasEgg);
    }

    public boolean m_6469_(DamageSource source, float amount) {
        if (!this.m_9236_().f_46443_ && !this.m_213877_()) {
            if (source.m_269533_(DamageTypeTags.f_268738_)) {
                this.m_6074_();
                return false;
            }
            if (!this.m_6673_(source)) {
                if (source.m_269533_(DamageTypeTags.f_268415_)) {
                    this.m_6074_();
                    return false;
                }
                Entity entity = source.m_7639_();
                if (entity instanceof Player) {
                    Player player = (Player)entity;
                    if (!player.m_150110_().f_35938_) {
                        if ((long)this.noActionTime < 6000L) {
                            return false;
                        }
                        this.m_6074_();
                        return true;
                    }
                }
                if (source.m_19390_()) {
                    AbstractArrow arrow;
                    this.m_6074_();
                    entity = source.m_7640_();
                    return entity instanceof AbstractArrow && (arrow = (AbstractArrow)entity).m_36796_() > 0;
                }
            } else {
                return false;
            }
        }
        return false;
    }

    public void m_8119_() {
        super.m_8119_();
        Level level = this.m_9236_();
        if (level instanceof ServerLevel) {
            ServerLevel level2 = (ServerLevel)level;
            ++this.noActionTime;
            for (ServerPlayer player : level2.m_6907_()) {
                UUID id;
                if (!(player.m_20280_((Entity)this) <= 16.0) || player.m_21224_() || player.m_7500_() || !this.canTrip(id = player.m_20148_(), level2.m_46467_())) continue;
                this.noActionTime = 0;
                this.tripmap.put(id, level2.m_46467_());
                level2.m_220407_(ADGameEvents.SCULKRAPTOR_NEST_AGGRO.get(), this.m_20183_(), GameEvent.Context.m_223717_((Entity)player));
                break;
            }
        }
    }

    public void m_6043_() {
        Player nearestPlayer = this.m_9236_().m_45930_((Entity)this, -1.0);
        if (nearestPlayer != null) {
            double distance = nearestPlayer.m_20280_((Entity)this);
            int despawnDistance = this.m_6095_().m_20674_().m_21611_();
            if (distance > (double)(despawnDistance *= despawnDistance)) {
                this.m_146870_();
            }
            int noDespawnDistance = this.m_6095_().m_20674_().m_21612_();
            noDespawnDistance *= noDespawnDistance;
            if ((long)this.noActionTime > 6000L && this.f_19796_.m_188503_(800) == 0 && distance > (double)noDespawnDistance) {
                this.m_146870_();
            } else if (distance < (double)noDespawnDistance) {
                this.noActionTime = 0;
            }
        }
    }

    private boolean canTrip(UUID uuid, long gameTime) {
        if (!this.tripmap.containsKey(uuid)) {
            return true;
        }
        return this.tripmap.get(uuid) + 12000L < gameTime;
    }

    public boolean m_6087_() {
        return !this.m_213877_();
    }

    public boolean m_5829_() {
        return !this.m_213877_();
    }

    public InteractionResult m_6096_(Player player, InteractionHand hand) {
        if (player.m_9236_() instanceof ServerLevel && this.hasEgg() && this.getNearbyRaptors().isEmpty()) {
            this.m_5552_(ADItems.SCULKRAPTOR_EGG.get().m_7968_(), 0.5f);
            this.m_5496_(ADSoundEvents.SCULKRAPTOR_NEST_EGG.get(), 1.0f, (this.f_19796_.m_188501_() - this.f_19796_.m_188501_()) * 0.2f + 1.0f);
            this.m_146850_(GameEvent.f_157810_);
            this.setHasEgg(false);
            this.noActionTime = 0;
        }
        return InteractionResult.SUCCESS;
    }

    private List<SculkraptorEntity> getNearbyRaptors() {
        return this.m_9236_().m_6443_(SculkraptorEntity.class, this.m_20191_().m_82400_(16.0), sculkraptor -> !sculkraptor.m_21224_() && sculkraptor.m_21534_().equals((Object)this.m_20183_()));
    }

    public void m_7380_(CompoundTag tag) {
        tag.m_128379_("has_egg", ((Boolean)this.f_19804_.m_135370_(HAS_EGG)).booleanValue());
        tag.m_128405_("no_action_time", this.noActionTime);
    }

    public void m_7378_(CompoundTag tag) {
        if (tag.m_128441_("has_egg")) {
            this.f_19804_.m_135381_(HAS_EGG, (Object)tag.m_128471_("has_egg"));
        }
        if (tag.m_128441_("no_action_time")) {
            this.noActionTime = tag.m_128451_("no_action_time");
        }
    }
}

