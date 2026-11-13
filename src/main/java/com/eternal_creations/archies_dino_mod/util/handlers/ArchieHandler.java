/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.Decoder
 *  com.mojang.serialization.Dynamic
 *  com.mojang.serialization.DynamicLike
 *  com.mojang.serialization.DynamicOps
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  javax.annotation.Nullable
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.UUIDUtil
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.NbtOps
 *  net.minecraft.network.chat.Component
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.util.SpawnUtil
 *  net.minecraft.util.SpawnUtil$Strategy
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.MobSpawnType
 *  net.minecraft.world.entity.ai.targeting.TargetingConditions
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.level.storage.LevelData
 *  org.slf4j.Logger
 */
package com.eternal_creations.archies_dino_mod.util.handlers;

import com.eternal_creations.archies_dino_mod.Constants;
import com.eternal_creations.archies_dino_mod.common.ADStrings;
import com.eternal_creations.archies_dino_mod.common.ADTags;
import com.eternal_creations.archies_dino_mod.common.entity.ADEntityTypes;
import com.eternal_creations.archies_dino_mod.common.entity.mob.archie.ArchieEntity;
import com.eternal_creations.archies_dino_mod.common.trigger.ADCriteriaTriggers;
import com.eternal_creations.archies_dino_mod.server.ServerConfig;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Decoder;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicLike;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.UUIDUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.SpawnUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.storage.LevelData;
import org.slf4j.Logger;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ArchieHandler {
    public static final Codec<ArchieHandler> CODEC = RecordCodecBuilder.create(recordCodecBuilder -> recordCodecBuilder.group((App)UUIDUtil.f_235867_.optionalFieldOf("archie_id").forGetter(handler -> Optional.ofNullable(handler.archieID)), (App)Codec.LONG.fieldOf("idle_start").forGetter(handler -> handler.idleStart), (App)Codec.LONG.fieldOf("no_archie").forGetter(handler -> handler.noArchies)).apply((Applicative)recordCodecBuilder, ArchieHandler::new));
    private static final String SAVE_ID = "ArchieHandler";
    private static final int IDLE_BEFORE_DESPAWN = 3000;
    private static final int NO_ARCHIE_BEFORE_SPAWN = 1200;
    @Nullable
    private UUID archieID = null;
    private long idleStart = -1L;
    private long noArchies = -1L;

    public ArchieHandler() {
    }

    public ArchieHandler(@Nullable UUID archieID, long idleStart, long noArchies) {
        this.archieID = archieID;
        this.idleStart = idleStart;
        this.noArchies = noArchies;
    }

    public ArchieHandler(Optional<UUID> archieID, long idleStart, long noArchies) {
        this((UUID)archieID.orElse(null), idleStart, noArchies);
    }

    @Nullable
    public UUID getArchieID() {
        return this.archieID;
    }

    public void setArchieID(@Nullable UUID archieID) {
        this.archieID = archieID;
    }

    public static ArchieHandler read(DynamicLike<?> dynamic) {
        CompoundTag tag = dynamic.get(SAVE_ID).read((Decoder)CompoundTag.f_128325_).result().orElse(null);
        if (tag == null) {
            return new ArchieHandler();
        }
        return CODEC.parse(new Dynamic((DynamicOps)NbtOps.f_128958_, (Object)tag)).resultOrPartial(arg_0 -> ((Logger)Constants.LOG).error(arg_0)).orElse(new ArchieHandler());
    }

    public void write(CompoundTag nbt) {
        CODEC.encodeStart((DynamicOps)NbtOps.f_128958_, (Object)this).resultOrPartial(arg_0 -> ((Logger)Constants.LOG).error(arg_0)).ifPresent(tag -> nbt.m_128365_(SAVE_ID, tag));
    }

    public void tick(MinecraftServer server) {
        if (!ServerConfig.getEntitySettings().archie().shouldSpawnNearPlayersInDinoTerritory()) {
            return;
        }
        if (this.archieID == null) {
            if (this.noArchies < 0L) {
                this.noArchies = server.m_129783_().m_46467_();
            } else if (server.m_129783_().m_46467_() - this.noArchies > 1200L) {
                for (ServerLevel serverlevel : server.m_129785_()) {
                    for (ServerPlayer player : serverlevel.m_6907_()) {
                        ArchieEntity archie;
                        if (!serverlevel.m_204166_(player.m_20183_()).m_203656_(ADTags.Biomes.SPAWNS_ARCHIE) || (archie = (ArchieEntity)SpawnUtil.m_216403_(ADEntityTypes.ARCHIE.get(), (MobSpawnType)MobSpawnType.TRIGGERED, (ServerLevel)serverlevel, (BlockPos)player.m_20183_(), (int)20, (int)30, (int)15, (SpawnUtil.Strategy)SpawnUtil.Strategy.f_216413_).orElse(null)) == null) continue;
                        ADCriteriaTriggers.SPOTTED_ENTITY.trigger(player, (Entity)archie);
                        player.m_5661_((Component)Component.m_237115_((String)ADStrings.Messages.ARCHIE_SPAWN), false);
                        for (Player nearby : serverlevel.m_45955_(TargetingConditions.f_26872_, (LivingEntity)player, archie.m_20191_().m_82400_(32.0))) {
                            nearby.m_5661_((Component)Component.m_237115_((String)ADStrings.Messages.ARCHIE_SPAWN), false);
                            if (!(nearby instanceof ServerPlayer)) continue;
                            ServerPlayer serverPlayer = (ServerPlayer)nearby;
                            ADCriteriaTriggers.SPOTTED_ENTITY.trigger(serverPlayer, (Entity)archie);
                        }
                        this.archieID = archie.m_20148_();
                        return;
                    }
                }
                this.noArchies = -1L;
            }
        } else {
            this.noArchies = -1L;
            for (ServerLevel serverlevel : server.m_129785_()) {
                if (serverlevel.m_8791_(this.archieID) != null) {
                    this.idleStart = -1L;
                    return;
                }
                serverlevel.m_8791_(this.archieID);
            }
            if (this.idleStart < 0L) {
                this.idleStart = server.m_129783_().m_46467_();
            } else if (server.m_129783_().m_46467_() - this.idleStart > 3000L) {
                this.archieID = null;
                this.idleStart = -1L;
            }
        }
    }

    public static void getArchieHandler(LevelAccessor level, Consumer<ArchieHandler> consumer) {
        MinecraftServer server = level.m_7654_();
        if (server != null) {
            ArchieHandler.getArchieHandler(server, consumer);
        }
    }

    public static void getArchieHandler(MinecraftServer server, Consumer<ArchieHandler> consumer) {
        LevelData levelData = server.m_129783_().m_6106_();
        if (levelData instanceof iLevelDataWithArchie) {
            iLevelDataWithArchie levelData2 = (iLevelDataWithArchie)levelData;
            consumer.accept(levelData2.archies_Dino_Mod$getArchieHandler());
        }
    }

    public static interface iLevelDataWithArchie {
        public ArchieHandler archies_Dino_Mod$getArchieHandler();

        public void archies_Dino_Mod$setArchieHandler(ArchieHandler var1);
    }
}

