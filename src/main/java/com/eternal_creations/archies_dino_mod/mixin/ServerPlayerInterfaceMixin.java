/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.particles.BlockParticleOption
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.util.Mth
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.Vec3
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package com.eternal_creations.archies_dino_mod.mixin;

import com.eternal_creations.archies_dino_mod.util.iPlayerWithMace;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@Mixin(value={ServerPlayer.class})
public abstract class ServerPlayerInterfaceMixin {
    @Inject(method={"checkFallDamage"}, at={@At(value="HEAD")})
    private void onFallDamage(double y, boolean onGround, BlockState state, BlockPos pos, CallbackInfo ci) {
        ServerPlayer player = (ServerPlayer)this;
        iPlayerWithMace iPlayer = (iPlayerWithMace)player;
        if (iPlayer.archies_Dino_Mod$spawnExtraParticlesOnFall() && onGround && player.f_19789_ > 0.0f) {
            Vec3 vec3d = pos.m_252807_().m_82520_(0.0, 0.5, 0.0);
            int i = (int)Mth.m_14036_((float)(50.0f * player.f_19789_), (float)0.0f, (float)200.0f);
            player.m_284548_().m_8767_((ParticleOptions)new BlockParticleOption(ParticleTypes.f_123794_, state), vec3d.f_82479_, vec3d.f_82480_, vec3d.f_82481_, i, (double)0.3f, (double)0.3f, (double)0.3f, (double)0.15f);
            iPlayer.archies_Dino_Mod$setSpawnExtraParticlesOnFall(false);
        }
    }
}

