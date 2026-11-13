/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.astemir.astemirlib.re.client.model.AstemirModel
 *  com.astemir.astemirlib.re.client.model.core.system.TransformSystem
 *  com.astemir.astemirlib.re.client.model.render.pipeline.ModelRenderContext
 *  com.astemir.astemirlib.re.client.model.render.renderer.layer.RenderLayer
 *  com.astemir.astemirlib.re.core.math.Transform
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.math.Axis
 *  javax.annotation.Nullable
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.entity.EntityRenderer
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Saddleable
 *  net.minecraft.world.phys.Vec3
 */
package com.eternal_creations.archies_dino_mod.client.model.render.entity.layer;

import com.astemir.astemirlib.re.client.model.AstemirModel;
import com.astemir.astemirlib.re.client.model.core.system.TransformSystem;
import com.astemir.astemirlib.re.client.model.render.pipeline.ModelRenderContext;
import com.astemir.astemirlib.re.client.model.render.renderer.layer.RenderLayer;
import com.astemir.astemirlib.re.core.math.Transform;
import com.eternal_creations.archies_dino_mod.client.GlobalRenderState;
import com.eternal_creations.archies_dino_mod.common.entity.base.Mount;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Saddleable;
import net.minecraft.world.phys.Vec3;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class PassengerLayer<T extends LivingEntity>
implements RenderLayer<T> {
    private final AstemirModel<T> model;
    @Nullable
    private final ResolvedMountPoint saddled;
    @Nullable
    private final ResolvedMountPoint unsaddled;

    public PassengerLayer(AstemirModel<T> model, Mount.MountPoint<T> saddled, Mount.MountPoint<T> unsaddled) {
        this.model = model;
        this.saddled = ResolvedMountPoint.resolve(model, saddled);
        this.unsaddled = ResolvedMountPoint.resolve(model, unsaddled);
    }

    public void render(T instance, TransformSystem<?> system, ModelRenderContext context) {
        ResolvedMountPoint mountPoint;
        ResolvedMountPoint resolvedMountPoint = mountPoint = ((Saddleable)instance).m_6254_() ? this.saddled : this.unsaddled;
        if (mountPoint == null) {
            return;
        }
        Entity entity = instance.m_146895_();
        if (entity instanceof LivingEntity) {
            LivingEntity passenger = (LivingEntity)entity;
            if (Minecraft.m_91087_().f_91074_ == passenger && Minecraft.m_91087_().f_91066_.m_92176_().m_90612_()) {
                return;
            }
            GlobalRenderState.IS_ON_MOUNT_RENDER.set(true);
            EntityRenderer renderer = Minecraft.m_91087_().m_91290_().m_114382_((Entity)passenger);
            Transform transform = mountPoint.type() == Mount.PointType.LOCATOR ? system.getLocatorTransform(mountPoint.bone) : system.getBoneTransform(mountPoint.bone);
            PoseStack poseStack = context.getPoseStack();
            float partialTick = context.getPartialTick();
            float headYaw = Mth.m_14189_((float)partialTick, (float)passenger.f_20886_, (float)passenger.f_20885_);
            float vehicleYaw = Mth.m_14189_((float)partialTick, (float)((LivingEntity)instance).f_20884_, (float)((LivingEntity)instance).f_20883_);
            float yawDiff = headYaw - vehicleYaw;
            float clampedYaw = Mth.m_14177_((float)yawDiff);
            if (clampedYaw < -85.0f) {
                clampedYaw = -85.0f;
            }
            if (clampedYaw >= 85.0f) {
                clampedYaw = 85.0f;
            }
            float finalVanillaYaw = headYaw - clampedYaw;
            if (clampedYaw * clampedYaw > 2500.0f) {
                finalVanillaYaw += clampedYaw * 0.2f;
            }
            poseStack.m_85836_();
            poseStack.m_85850_().m_252922_().mul(transform.pose());
            poseStack.m_85837_(mountPoint.offset.f_82479_, mountPoint.offset.f_82480_, mountPoint.offset.f_82481_);
            poseStack.m_252781_(Axis.f_252436_.m_252977_(finalVanillaYaw - 180.0f));
            renderer.m_7392_((Entity)passenger, 0.0f, partialTick, poseStack, context.getBufferSource(), context.getPackedLight());
            poseStack.m_85849_();
            GlobalRenderState.IS_ON_MOUNT_RENDER.set(false);
        }
    }

    public AstemirModel<T> getModel() {
        return this.model;
    }

    private record ResolvedMountPoint(int bone, Vec3 offset, Mount.PointType type) {
        @Nullable
        private static <T extends LivingEntity> ResolvedMountPoint resolve(AstemirModel<T> model, Mount.MountPoint<T> mountPoint) {
            if (mountPoint.type() == Mount.PointType.LOCATOR) {
                int locatorID = model.getLocatorIdByName(mountPoint.name());
                return locatorID != -1 ? new ResolvedMountPoint(locatorID, mountPoint.offset(), Mount.PointType.LOCATOR) : null;
            }
            int boneID = model.getBoneIdByName(mountPoint.name());
            return boneID != -1 ? new ResolvedMountPoint(boneID, mountPoint.offset(), Mount.PointType.BONE) : null;
        }
    }
}

