/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.Vec3
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Unique
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package com.eternal_creations.archies_dino_mod.mixin;

import com.eternal_creations.archies_dino_mod.util.iPlayerWithMace;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@Mixin(value={Player.class})
public abstract class PlayerInterfaceMixin
extends LivingEntity
implements iPlayerWithMace {
    @Unique
    @Nullable
    public Vec3 archies_Dino_Mod$currentExplosionImpactPos;
    @Unique
    @Nullable
    public Entity archies_Dino_Mod$explodedBy;
    @Unique
    private boolean archies_Dino_Mod$ignoreFallDamageFromCurrentExplosion;
    @Unique
    private int archies_Dino_Mod$currentExplosionResetGraceTime;
    @Unique
    private boolean archies_Dino_Mod$spawnExtraParticlesOnFall;

    protected PlayerInterfaceMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void archies_Dino_Mod$clearCurrentExplosion() {
        this.archies_Dino_Mod$currentExplosionResetGraceTime = 0;
        this.archies_Dino_Mod$explodedBy = null;
        this.archies_Dino_Mod$currentExplosionImpactPos = null;
        this.archies_Dino_Mod$ignoreFallDamageFromCurrentExplosion = false;
    }

    @Override
    public void archies_Dino_Mod$tryClearCurrentExplosion() {
        if (this.archies_Dino_Mod$currentExplosionResetGraceTime == 0) {
            this.archies_Dino_Mod$clearCurrentExplosion();
        }
    }

    @Override
    public void archies_Dino_Mod$setCurrentExplosionImpactPos(Vec3 newVal) {
        this.archies_Dino_Mod$currentExplosionImpactPos = newVal;
    }

    @Override
    @Nullable
    public Vec3 archies_Dino_Mod$currentExplosionImpactPos() {
        return this.archies_Dino_Mod$currentExplosionImpactPos;
    }

    @Override
    public void archies_Dino_Mod$setIgnoreFallDamageFromCurrentExplosion(boolean newVal) {
        this.archies_Dino_Mod$ignoreFallDamageFromCurrentExplosion = newVal;
        this.archies_Dino_Mod$currentExplosionResetGraceTime = newVal ? 40 : 0;
    }

    @Override
    public boolean archies_Dino_Mod$ignoreFallDamageFromCurrentExplosion() {
        return this.archies_Dino_Mod$ignoreFallDamageFromCurrentExplosion;
    }

    @Override
    public void archies_Dino_Mod$setSpawnExtraParticlesOnFall(boolean newVal) {
        this.archies_Dino_Mod$spawnExtraParticlesOnFall = newVal;
    }

    @Override
    public boolean archies_Dino_Mod$spawnExtraParticlesOnFall() {
        return this.archies_Dino_Mod$spawnExtraParticlesOnFall;
    }

    @Inject(method={"tick"}, at={@At(value="TAIL")})
    private void onTick(CallbackInfo ci) {
        if (this.archies_Dino_Mod$currentExplosionResetGraceTime > 0) {
            --this.archies_Dino_Mod$currentExplosionResetGraceTime;
        }
    }

    @Inject(method={"causeFallDamage"}, at={@At(value="RETURN", ordinal=1)}, cancellable=true)
    private void onFallDamage(float fallDistance, float multiplier, DamageSource source, CallbackInfoReturnable<Boolean> cir) {
        boolean flag = (Boolean)cir.getReturnValue();
        if (this.archies_Dino_Mod$ignoreFallDamageFromCurrentExplosion() && this.archies_Dino_Mod$currentExplosionImpactPos != null) {
            double y = this.archies_Dino_Mod$currentExplosionImpactPos.f_82480_;
            this.archies_Dino_Mod$tryClearCurrentExplosion();
            if (y < this.m_20186_()) {
                cir.setReturnValue((Object)false);
                return;
            }
            float f = Math.min(fallDistance, (float)(y - this.m_20186_()));
            flag = super.m_142535_(f, multiplier, source);
            cir.setReturnValue((Object)flag);
        }
        if (flag) {
            this.archies_Dino_Mod$clearCurrentExplosion();
        }
    }
}

