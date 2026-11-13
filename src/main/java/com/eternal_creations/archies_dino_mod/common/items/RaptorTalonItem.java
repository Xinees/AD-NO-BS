/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.VibrationParticleOption
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.ai.memory.MemoryModuleType
 *  net.minecraft.world.entity.ai.memory.WalkTarget
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.context.UseOnContext
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.gameevent.vibrations.VibrationSystem$User
 *  net.minecraft.world.phys.Vec3
 */
package com.eternal_creations.archies_dino_mod.common.items;

import com.eternal_creations.archies_dino_mod.common.entity.ADMemoryModuleTypes;
import com.eternal_creations.archies_dino_mod.common.entity.mob.sculkraptor.SculkraptorEntity;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.VibrationParticleOption;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.vibrations.VibrationSystem;
import net.minecraft.world.phys.Vec3;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class RaptorTalonItem
extends Item {
    private static final int COOLDOWN_TICKS = 600;

    public RaptorTalonItem(Item.Properties properties) {
        super(properties);
    }

    public InteractionResult m_6225_(UseOnContext context) {
        Level level = context.m_43725_();
        Player player = context.m_43723_();
        if (player instanceof ServerPlayer) {
            ServerPlayer player2 = (ServerPlayer)player;
            Vec3 vec3 = context.m_43720_();
            BlockPos pos = context.m_8083_();
            player2.m_284548_().m_8583_().forEach(entity -> {
                SculkraptorEntity raptor;
                if (entity instanceof SculkraptorEntity && (raptor = (SculkraptorEntity)entity).m_21805_() == player2.m_20148_()) {
                    raptor.m_6274_().m_21879_(ADMemoryModuleTypes.WALK_COMMAND.get(), (Object)new WalkTarget(pos, 1.0f, 1));
                    raptor.m_6274_().m_21936_(MemoryModuleType.f_26370_);
                    VibrationSystem.User user = raptor.m_280445_();
                    player2.m_284548_().m_8767_((ParticleOptions)new VibrationParticleOption(user.m_280010_(), user.m_280576_((float)vec3.m_82554_(raptor.m_20182_()))), vec3.f_82479_, vec3.f_82480_, vec3.f_82481_, 1, 0.0, 0.0, 0.0, 0.0);
                }
            });
            player2.m_36335_().m_41524_(context.m_43722_().m_41720_(), 600);
        }
        return InteractionResult.m_19078_((boolean)level.f_46443_);
    }
}

