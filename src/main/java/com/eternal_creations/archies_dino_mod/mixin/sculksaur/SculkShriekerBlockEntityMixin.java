/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.core.BlockPos
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.level.block.entity.SculkShriekerBlockEntity
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Unique
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package com.eternal_creations.archies_dino_mod.mixin.sculksaur;

import com.eternal_creations.archies_dino_mod.common.entity.mob.sculksaur.SculksaurEntity;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.SculkShriekerBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@Mixin(value={SculkShriekerBlockEntity.class})
public class SculkShriekerBlockEntityMixin {
    @Unique
    private static final double archiesDinoMod$BAD_DISTANCE = 128.0;

    @Inject(method={"trySummonWarden"}, at={@At(value="HEAD")}, cancellable=true)
    private void onTrySummonWarden(ServerLevel level, CallbackInfoReturnable<Boolean> cir) {
        for (Entity entity : level.m_8583_()) {
            BlockPos pos;
            SculksaurEntity sculksaur;
            if (!(entity instanceof SculksaurEntity) || (sculksaur = (SculksaurEntity)entity).m_21224_() || !(sculksaur.m_20275_((double)(pos = ((SculkShriekerBlockEntity)this).m_58899_()).m_123341_() + 0.5, pos.m_123342_(), (double)pos.m_123343_() + 0.5) < 16384.0)) continue;
            cir.setReturnValue((Object)false);
            return;
        }
    }
}

