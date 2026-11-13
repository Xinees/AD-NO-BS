/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.monster.Monster
 *  net.minecraft.world.entity.monster.warden.Warden
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package com.eternal_creations.archies_dino_mod.mixin.sculksaur;

import com.eternal_creations.archies_dino_mod.common.entity.mob.sculksaur.SculksaurEntity;
import com.eternal_creations.archies_dino_mod.util.handlers.SculksaurHandler;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@Mixin(value={Warden.class})
public abstract class WardenMixin
extends Monster {
    protected WardenMixin(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method={"tick"}, at={@At(value="INVOKE", target="Lnet/minecraft/world/level/gameevent/vibrations/VibrationSystem$Ticker;tick(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/level/gameevent/vibrations/VibrationSystem$Data;Lnet/minecraft/world/level/gameevent/vibrations/VibrationSystem$User;)V")})
    private void archiesDino$onTick(CallbackInfo ci) {
        SculksaurHandler.getSculksaurHandler((LevelAccessor)this.m_9236_(), handler -> {
            if (handler.isOffCooldown()) {
                handler.trySummon((Warden)this);
            }
        });
    }

    @Inject(method={"hurt"}, at={@At(value="HEAD")}, cancellable=true)
    public void archiesDino$onHurt(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (source.m_7639_() instanceof SculksaurEntity) {
            this.m_21153_(0.0f);
            this.m_6667_(source);
            cir.setReturnValue((Object)true);
        }
    }
}

