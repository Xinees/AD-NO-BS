/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  javax.annotation.Nullable
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.client.renderer.LevelRenderer
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.entity.EntityRenderDispatcher
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.particles.BlockParticleOption
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.phys.Vec3
 *  org.spongepowered.asm.mixin.Final
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package com.eternal_creations.archies_dino_mod.mixin.client;

import com.eternal_creations.archies_dino_mod.common.entity.projectile.TherimossProjectileEntity;
import com.eternal_creations.archies_dino_mod.common.particle.ADParticles;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={LevelRenderer.class})
public abstract class LevelRendererMixin {
    @Shadow
    @Nullable
    private ClientLevel f_109465_;
    @Shadow
    @Final
    private EntityRenderDispatcher f_109463_;

    @Inject(method={"renderEntity"}, at={@At(value="HEAD")}, cancellable=true)
    private void archiesDinoMod$onEntityRender(Entity entity, double camX, double camY, double camZ, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, CallbackInfo ci) {
        TherimossProjectileEntity projectile;
        if (entity instanceof TherimossProjectileEntity && (projectile = (TherimossProjectileEntity)entity).isAttached()) {
            this.f_109463_.m_114384_(entity, projectile.renderPos.x - camX, projectile.renderPos.y - camY, projectile.renderPos.z - camZ, Mth.m_14179_((float)partialTick, (float)entity.f_19859_, (float)entity.m_146908_()), partialTick, poseStack, bufferSource, this.f_109463_.m_114394_(entity, partialTick));
            ci.cancel();
        }
    }

    @Inject(method={"levelEvent"}, at={@At(value="HEAD")}, cancellable=true)
    private void archiesDinoMod$onLevelEvent(int type, BlockPos pos, int data, CallbackInfo ci) {
        if (type == 2013) {
            Vec3 vec3 = pos.m_252807_().m_82520_(0.0, 0.5, 0.0);
            BlockParticleOption blockparticleoption = new BlockParticleOption(ADParticles.DUST_PILLAR.get(), Objects.requireNonNull(this.f_109465_).m_8055_(pos));
            int i = 0;
            while ((float)i < (float)data / 3.0f) {
                double d0 = vec3.f_82479_ + this.f_109465_.m_213780_().m_188583_() / 2.0;
                double d1 = vec3.f_82480_;
                double d2 = vec3.f_82481_ + this.f_109465_.m_213780_().m_188583_() / 2.0;
                double d3 = this.f_109465_.m_213780_().m_188583_() * (double)0.2f;
                double d4 = this.f_109465_.m_213780_().m_188583_() * (double)0.2f;
                double d5 = this.f_109465_.m_213780_().m_188583_() * (double)0.2f;
                this.f_109465_.m_7106_((ParticleOptions)blockparticleoption, d0, d1, d2, d3, d4, d5);
                ++i;
            }
            int j = 0;
            while ((float)j < (float)data / 1.5f) {
                double d6 = vec3.f_82479_ + 3.5 * Math.cos(j) + this.f_109465_.m_213780_().m_188583_() / 2.0;
                double d7 = vec3.f_82480_;
                double d8 = vec3.f_82481_ + 3.5 * Math.sin(j) + this.f_109465_.m_213780_().m_188583_() / 2.0;
                double d9 = this.f_109465_.m_213780_().m_188583_() * (double)0.05f;
                double d10 = this.f_109465_.m_213780_().m_188583_() * (double)0.05f;
                double d11 = this.f_109465_.m_213780_().m_188583_() * (double)0.05f;
                this.f_109465_.m_7106_((ParticleOptions)blockparticleoption, d6, d7, d8, d9, d10, d11);
                ++j;
            }
            ci.cancel();
        }
    }
}

