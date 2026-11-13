/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.particles.ItemParticleOption
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.util.Mth
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.ai.behavior.BlockPosTracker
 *  net.minecraft.world.entity.ai.behavior.PositionTracker
 *  net.minecraft.world.entity.ai.memory.MemoryModuleType
 *  net.minecraft.world.entity.ai.memory.WalkTarget
 *  net.minecraft.world.entity.item.ItemEntity
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  org.joml.Vector3d
 */
package com.eternal_creations.archies_dino_mod.networking.serverbound;

import com.eternal_creations.archies_dino_mod.Constants;
import com.eternal_creations.archies_dino_mod.common.entity.ADMemoryModuleTypes;
import com.eternal_creations.archies_dino_mod.common.entity.mob.sculksaur.SculksaurEntity;
import com.eternal_creations.archies_dino_mod.common.entity.mob.sculksaur.ai.SculksaurAI;
import com.eternal_creations.archies_dino_mod.common.items.ADItems;
import com.eternal_creations.archies_dino_mod.common.sound.ADSoundEvents;
import com.eternal_creations.archies_dino_mod.networking.serverbound.AbstractServerboundMessage;
import java.util.UUID;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.BlockPosTracker;
import net.minecraft.world.entity.ai.behavior.PositionTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import org.joml.Vector3d;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public final class PopZitMessage
extends AbstractServerboundMessage {
    public static final ResourceLocation ID = Constants.prefix("pop_zit");
    private final UUID petUuid;
    private final UUID shooter;
    private final Vector3d wartPos;
    private final String wart;

    public PopZitMessage(UUID petUuid, UUID shooter, Vector3d wartPos, String wart) {
        this.petUuid = petUuid;
        this.shooter = shooter;
        this.wartPos = wartPos;
        this.wart = wart;
    }

    public static PopZitMessage decode(FriendlyByteBuf buf) {
        return new PopZitMessage(buf.m_130259_(), buf.m_130259_(), new Vector3d(buf.readDouble(), buf.readDouble(), buf.readDouble()), buf.m_130277_());
    }

    @Override
    public ResourceLocation getID() {
        return ID;
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.m_130077_(this.petUuid);
        buf.m_130077_(this.shooter);
        buf.writeDouble(this.wartPos.x());
        buf.writeDouble(this.wartPos.y());
        buf.writeDouble(this.wartPos.z());
        buf.m_130070_(this.wart);
    }

    @Override
    public void execute(ServerPlayer player) {
        ServerLevel level = player.m_284548_();
        Entity entity = level.m_8791_(this.petUuid);
        if (entity instanceof SculksaurEntity) {
            LivingEntity living;
            Entity entity2;
            SculksaurEntity sculksaur = (SculksaurEntity)entity;
            boolean flag = false;
            for (int i = 0; i < 6; ++i) {
                if (!SculksaurEntity.getWartGroup(i).names().contains(this.wart)) continue;
                if (sculksaur.hasWartGroup(i)) {
                    sculksaur.setWartGroup(i, false);
                    flag = true;
                    break;
                }
                return;
            }
            if (!flag) {
                return;
            }
            ItemStack pus = new ItemStack((ItemLike)ADItems.SCULK_TOXIN.get(), Mth.m_216287_((RandomSource)sculksaur.m_217043_(), (int)1, (int)2));
            ItemEntity itementity = new ItemEntity((Level)level, this.wartPos.x(), this.wartPos.y(), this.wartPos.z(), pus);
            itementity.m_32060_();
            sculksaur.m_5496_(ADSoundEvents.SCULKSAUR_WART_POP.get(), 1.0f, sculksaur.m_6100_());
            level.m_8767_((ParticleOptions)new ItemParticleOption(ParticleTypes.f_123752_, pus), this.wartPos.x(), this.wartPos.y(), this.wartPos.z(), 6, (double)((level.f_46441_.m_188501_() - 0.5f) * 0.08f), (double)((level.f_46441_.m_188501_() - 0.5f) * 0.08f), (double)((level.f_46441_.m_188501_() - 0.5f) * 0.08f), (double)0.15f);
            level.m_7967_((Entity)itementity);
            int count = 0;
            for (int i = 0; i < 6; ++i) {
                if (!sculksaur.hasWartGroup(i)) continue;
                ++count;
            }
            if (count > 0 || !((entity2 = level.m_8791_(this.shooter)) instanceof LivingEntity) || !sculksaur.m_6779_(living = (LivingEntity)entity2)) {
                sculksaur.playActionSynced(sculksaur.actionRoar);
            } else {
                sculksaur.increaseAngerAt((Entity)living, 150, true);
                sculksaur.m_6274_().m_21936_(MemoryModuleType.f_217782_);
                sculksaur.setEnraged(true);
                sculksaur.m_6274_().m_21879_(MemoryModuleType.f_26372_, (Object)living);
                sculksaur.m_6274_().m_21936_(MemoryModuleType.f_217775_);
            }
            entity2 = level.m_8791_(this.shooter);
            if (entity2 instanceof LivingEntity) {
                LivingEntity living2 = (LivingEntity)entity2;
                SculksaurAI.setDisturbanceLocation(sculksaur, living2.m_20183_());
                PopZitMessage.setWalkAndLookTargetMemories((LivingEntity)sculksaur, living2.m_20183_(), 0.95f, 2);
            }
        }
    }

    public static void setWalkAndLookTargetMemories(LivingEntity livingEntity, BlockPos pos, float speed, int distance) {
        PopZitMessage.setWalkAndLookTargetMemories(livingEntity, (PositionTracker)new BlockPosTracker(pos), speed, distance);
    }

    public static void setWalkAndLookTargetMemories(LivingEntity entity, PositionTracker positionTracker, float speedModifier, int closeEnoughDist) {
        WalkTarget walktarget = new WalkTarget(positionTracker, speedModifier, closeEnoughDist);
        entity.m_6274_().m_21879_(MemoryModuleType.f_26371_, (Object)positionTracker);
        entity.m_6274_().m_21879_(MemoryModuleType.f_26370_, (Object)walktarget);
        entity.m_6274_().m_21879_(ADMemoryModuleTypes.WALK_COMMAND.get(), (Object)walktarget);
    }
}

