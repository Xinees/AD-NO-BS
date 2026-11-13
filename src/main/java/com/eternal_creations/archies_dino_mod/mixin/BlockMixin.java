/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.ai.targeting.TargetingConditions
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.AABB
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package com.eternal_creations.archies_dino_mod.mixin;

import com.eternal_creations.archies_dino_mod.common.ADTags;
import com.eternal_creations.archies_dino_mod.common.entity.mob.therimoss.TherimossEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={Block.class})
public abstract class BlockMixin {
    @Inject(method={"playerWillDestroy"}, at={@At(value="HEAD")})
    private void archiesDino$onPlayerDestroyWood(Level level, BlockPos pos, BlockState state, Player player, CallbackInfo ci) {
        if (level.f_46443_) {
            return;
        }
        if (state.m_204336_(ADTags.Blocks.THERIMOSS_PROTECTED_BLOCKS)) {
            double radius = 16.0;
            AABB box = new AABB(pos).m_82400_(radius);
            TherimossEntity therimoss = (TherimossEntity)level.m_45963_(TherimossEntity.class, TargetingConditions.m_148352_().m_26883_(radius), (LivingEntity)player, (double)pos.m_123341_() + 0.5, (double)pos.m_123342_() + 0.5, (double)pos.m_123343_() + 0.5, box);
            if (therimoss != null) {
                therimoss.handleBlockBreak(player, state);
            }
        }
    }
}

