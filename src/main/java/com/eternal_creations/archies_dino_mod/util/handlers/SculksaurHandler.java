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
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.NbtOps
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.util.Mth
 *  net.minecraft.util.RandomSource
 *  net.minecraft.util.SpawnUtil
 *  net.minecraft.util.SpawnUtil$Strategy
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.MobSpawnType
 *  net.minecraft.world.entity.monster.warden.Warden
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.storage.LevelData
 *  net.minecraft.world.phys.shapes.VoxelShape
 *  org.slf4j.Logger
 */
package com.eternal_creations.archies_dino_mod.util.handlers;

import com.eternal_creations.archies_dino_mod.Constants;
import com.eternal_creations.archies_dino_mod.common.entity.ADEntityTypes;
import com.eternal_creations.archies_dino_mod.common.entity.base.AbstractComplexMonster;
import com.eternal_creations.archies_dino_mod.common.entity.mob.sculksaur.SculksaurEntity;
import com.eternal_creations.archies_dino_mod.server.ServerConfig;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Decoder;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicLike;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.function.Consumer;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.SpawnUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.LevelData;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.slf4j.Logger;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class SculksaurHandler {
    public static final Codec<SculksaurHandler> CODEC = RecordCodecBuilder.create(recordCodecBuilder -> recordCodecBuilder.group((App)Codec.LONG.fieldOf("cooldown").forGetter(handler -> handler.cooldown)).apply((Applicative)recordCodecBuilder, SculksaurHandler::new));
    private static final String SAVE_ID = "SculksaurHandler";
    public static final int DEFAULT_REQUIRED_WARDEN_TIME = 600;
    public static final int DEFAULT_COOLDOWN = 18000;
    private long cooldown = -1L;
    private static final SpawnUtil.Strategy SCULKSAUR_STRAT = (serverLevel, pos, state, abovePos, aboveState) -> {
        boolean check = SculksaurHandler.posCheck(serverLevel, pos, state, abovePos, aboveState);
        if (!check) {
            return false;
        }
        for (BlockPos relativePos : BlockPos.m_121976_((int)(pos.m_123341_() - 2), (int)pos.m_123342_(), (int)(pos.m_123343_() - 2), (int)(pos.m_123341_() + 2), (int)pos.m_123342_(), (int)(pos.m_123343_() + 2))) {
            BlockState upState;
            BlockPos upPos = relativePos.m_7494_();
            BlockState relativeState = serverLevel.m_8055_(relativePos);
            boolean relativeCheck = SculksaurHandler.posCheck(serverLevel, relativePos, relativeState, upPos, upState = serverLevel.m_8055_(upPos));
            if (relativeCheck) continue;
            return false;
        }
        return true;
    };

    public SculksaurHandler() {
    }

    public SculksaurHandler(long cooldown) {
        this.cooldown = cooldown;
    }

    public boolean isOffCooldown() {
        return this.cooldown == -1L;
    }

    public static SculksaurHandler read(DynamicLike<?> dynamic) {
        CompoundTag tag = dynamic.get(SAVE_ID).read((Decoder)CompoundTag.f_128325_).result().orElse(null);
        if (tag == null) {
            return new SculksaurHandler();
        }
        return CODEC.parse(new Dynamic((DynamicOps)NbtOps.f_128958_, (Object)tag)).resultOrPartial(arg_0 -> ((Logger)Constants.LOG).error(arg_0)).orElse(new SculksaurHandler());
    }

    public void write(CompoundTag nbt) {
        CODEC.encodeStart((DynamicOps)NbtOps.f_128958_, (Object)this).resultOrPartial(arg_0 -> ((Logger)Constants.LOG).error(arg_0)).ifPresent(tag -> nbt.m_128365_(SAVE_ID, tag));
    }

    public void trySummon(Warden warden) {
        if (!ServerConfig.getEntitySettings().sculksaur().canSpawnDueToWardens()) {
            return;
        }
        Level level = warden.m_9236_();
        if (level instanceof ServerLevel) {
            SculksaurEntity sculksaur;
            int expectedTime;
            ServerLevel level2 = (ServerLevel)level;
            if (warden.f_19797_ >= ServerConfig.getEntitySettings().sculksaur().minSpawnTime() && warden.f_19797_ >= (expectedTime = Mth.m_216287_((RandomSource)RandomSource.m_216335_((long)warden.m_20148_().getMostSignificantBits()), (int)ServerConfig.getEntitySettings().sculksaur().minSpawnTime(), (int)(ServerConfig.getEntitySettings().sculksaur().minSpawnTime() * 3))) && (sculksaur = (SculksaurEntity)SpawnUtil.m_216403_(ADEntityTypes.SCULKSAUR.get(), (MobSpawnType)MobSpawnType.TRIGGERED, (ServerLevel)level2, (BlockPos)warden.m_20183_(), (int)20, (int)5, (int)6, (SpawnUtil.Strategy)SCULKSAUR_STRAT).orElse(null)) != null) {
                this.cooldown = level2.m_7654_().m_129783_().m_46467_();
                sculksaur.m_6710_((LivingEntity)warden);
            }
        }
    }

    public void tick(MinecraftServer server) {
        if (this.cooldown >= 0L && server.m_129783_().m_46467_() - this.cooldown > (long)ServerConfig.getEntitySettings().sculksaur().spawnCooldown()) {
            this.cooldown = -1L;
        }
    }

    public static void getSculksaurHandler(LevelAccessor level, Consumer<SculksaurHandler> consumer) {
        MinecraftServer server = level.m_7654_();
        if (server != null) {
            SculksaurHandler.getSculksaurHandler(server, consumer);
        }
    }

    public static void getSculksaurHandler(MinecraftServer server, Consumer<SculksaurHandler> consumer) {
        LevelData levelData = server.m_129783_().m_6106_();
        if (levelData instanceof iLevelDataWithSculksaur) {
            iLevelDataWithSculksaur levelData2 = (iLevelDataWithSculksaur)levelData;
            consumer.accept(levelData2.archies_Dino_Mod$getSculksaurHandler());
        }
    }

    private static boolean posCheck(ServerLevel serverLevel, BlockPos pos, BlockState state, BlockPos relativePos, BlockState relativeState) {
        return relativeState.m_60812_((BlockGetter)serverLevel, relativePos).m_83281_() && Block.m_49918_((VoxelShape)state.m_60812_((BlockGetter)serverLevel, pos), (Direction)Direction.UP);
    }

    public record SculksaurSettings(boolean canSpawnDueToWardens, boolean isBoss, int spawnCooldown, int minSpawnTime, AbstractComplexMonster.WildDinoSettings settings) {
        public static final Codec<SculksaurSettings> CODEC = RecordCodecBuilder.create(instance -> instance.group((App)Codec.BOOL.fieldOf("can_spawn_due_to_wardens").forGetter(SculksaurSettings::canSpawnDueToWardens), (App)Codec.BOOL.fieldOf("is_boss").forGetter(SculksaurSettings::isBoss), (App)Codec.INT.fieldOf("spawn_cooldown").forGetter(SculksaurSettings::spawnCooldown), (App)Codec.INT.fieldOf("min_spawn_time").forGetter(SculksaurSettings::minSpawnTime), (App)AbstractComplexMonster.WildDinoSettings.CODEC.fieldOf("stats").forGetter(SculksaurSettings::settings)).apply((Applicative)instance, SculksaurSettings::new));
    }

    public static interface iLevelDataWithSculksaur {
        public SculksaurHandler archies_Dino_Mod$getSculksaurHandler();

        public void archies_Dino_Mod$setSculksaurHandler(SculksaurHandler var1);
    }
}

