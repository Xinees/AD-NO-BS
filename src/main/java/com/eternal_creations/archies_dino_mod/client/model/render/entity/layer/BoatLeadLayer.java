/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.astemir.astemirlib.re.client.model.AstemirModel
 *  com.astemir.astemirlib.re.client.model.core.system.TransformSystem
 *  com.astemir.astemirlib.re.client.model.render.pipeline.ModelRenderContext
 *  com.astemir.astemirlib.re.client.model.render.renderer.layer.RenderLayer
 *  com.astemir.astemirlib.re.core.math.AMath
 *  com.astemir.astemirlib.re.core.math.Transform
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.VertexConsumer
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.LightTexture
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Position
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.vehicle.Boat
 *  net.minecraft.world.level.LightLayer
 *  net.minecraft.world.phys.Vec3
 *  org.joml.Matrix4f
 */
package com.eternal_creations.archies_dino_mod.client.model.render.entity.layer;

import com.astemir.astemirlib.re.client.model.AstemirModel;
import com.astemir.astemirlib.re.client.model.core.system.TransformSystem;
import com.astemir.astemirlib.re.client.model.render.pipeline.ModelRenderContext;
import com.astemir.astemirlib.re.client.model.render.renderer.layer.RenderLayer;
import com.astemir.astemirlib.re.core.math.AMath;
import com.astemir.astemirlib.re.core.math.Transform;
import com.eternal_creations.archies_dino_mod.util.IBoatWithLead;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Position;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

public class BoatLeadLayer<T extends LivingEntity>
implements RenderLayer<T> {
    private static final List<Vec3> OFFSETS = List.of(new Vec3(0.5, 0.45, 0.85), new Vec3(-0.5, 0.45, 0.85));
    private final AstemirModel<T> model;
    private final int saddleBoneId;

    public BoatLeadLayer(AstemirModel<T> model, String saddleBoneName) {
        this.model = model;
        this.saddleBoneId = model.getLocatorIdByName(saddleBoneName);
    }

    public void render(T instance, TransformSystem<?> system, ModelRenderContext context) {
        if (this.saddleBoneId == -1 || Minecraft.m_91087_().f_91073_ == null) {
            return;
        }
        Minecraft.m_91087_().f_91073_.m_104735_().forEach(entity -> {
            IBoatWithLead lead;
            Boat boat;
            if (entity instanceof Boat && (boat = (Boat)entity) instanceof IBoatWithLead && (lead = (IBoatWithLead)boat).archies_Dino_Mod$getLeashHolder() == instance) {
                this.renderLeash(instance, system, boat, context.getPartialTick(), context.getPoseStack(), context.getBufferSource());
            }
        });
    }

    public void renderLeash(T living, TransformSystem<?> system, Boat boat, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource) {
        Transform transform = system.getLocatorTransform(this.saddleBoneId);
        Vec3 leashOffset = new Vec3((double)transform.getX(), (double)transform.getY(), (double)transform.getZ());
        Vec3 pos = living.m_20318_(partialTicks);
        for (Vec3 offset : OFFSETS) {
            int segment;
            poseStack.m_85836_();
            float rot = Mth.m_14179_((float)partialTicks, (float)boat.f_19859_, (float)boat.m_146908_()) * ((float)Math.PI / 180);
            Vec3 holderPos = boat.m_20318_(partialTicks).m_82549_(offset.m_82524_(-rot));
            poseStack.m_85837_(leashOffset.f_82479_, leashOffset.f_82480_, leashOffset.f_82481_);
            float dx = (float)(holderPos.f_82479_ - leashOffset.f_82479_ - pos.f_82479_);
            float dy = (float)(holderPos.f_82480_ - leashOffset.f_82480_ - pos.f_82480_);
            float dz = (float)(holderPos.f_82481_ - leashOffset.f_82481_ - pos.f_82481_);
            VertexConsumer vertexConsumer = bufferSource.m_6299_(RenderType.m_110475_());
            Matrix4f matrix = poseStack.m_85850_().m_252922_();
            float horizontalDistSqr = dx * dx + dz * dz;
            float invLengthScale = AMath.invSqrt((float)horizontalDistSqr) * 0.025f;
            float offsetZ = dz * invLengthScale;
            float offsetX = dx * invLengthScale;
            BlockPos mobPos = BlockPos.m_274446_((Position)living.m_20299_(partialTicks));
            BlockPos holderPosBlock = BlockPos.m_274446_((Position)boat.m_20299_(partialTicks));
            int mobBlockLight = living.m_9236_().m_45517_(LightLayer.BLOCK, mobPos);
            int holderBlockLight = living.m_9236_().m_45517_(LightLayer.BLOCK, holderPosBlock);
            int mobSkyLight = living.m_9236_().m_45517_(LightLayer.SKY, mobPos);
            int holderSkyLight = living.m_9236_().m_45517_(LightLayer.SKY, holderPosBlock);
            for (segment = 0; segment <= 24; ++segment) {
                this.renderLeashSide(vertexConsumer, matrix, dx, dy, dz, mobBlockLight, holderBlockLight, mobSkyLight, holderSkyLight, 0.025f, 0.025f, offsetZ, offsetX, segment, false);
            }
            for (segment = 24; segment >= 0; --segment) {
                this.renderLeashSide(vertexConsumer, matrix, dx, dy, dz, mobBlockLight, holderBlockLight, mobSkyLight, holderSkyLight, 0.025f, 0.0f, offsetZ, offsetX, segment, true);
            }
            poseStack.m_85849_();
        }
    }

    private void renderLeashSide(VertexConsumer buffer, Matrix4f matrix, float dx, float dy, float dz, int bL1, int bL2, int sL1, int sL2, float width, float yOffset, float offsetZ, float offsetX, int segment, boolean flipColor) {
        float y;
        float progress = (float)segment * 0.041666668f;
        int light = LightTexture.m_109885_((int)((int)AMath.lerp((float)progress, (float)bL1, (float)bL2)), (int)((int)AMath.lerp((float)progress, (float)sL1, (float)sL2)));
        float colorMod = segment % 2 == (flipColor ? 1 : 0) ? 0.7f : 1.0f;
        float r = 0.5f * colorMod;
        float g = 0.4f * colorMod;
        float b = 0.3f * colorMod;
        float x = dx * progress;
        if (dy > 0.0f) {
            y = dy * (progress * progress);
        } else {
            float invProgress = 1.0f - progress;
            y = dy - dy * (invProgress * invProgress);
        }
        float z = dz * progress;
        buffer.m_252986_(matrix, x - offsetZ, y + yOffset, z + offsetX).m_85950_(r, g, b, 1.0f).m_85969_(light).m_5752_();
        buffer.m_252986_(matrix, x + offsetZ, y + width - yOffset, z - offsetX).m_85950_(r, g, b, 1.0f).m_85969_(light).m_5752_();
    }

    public AstemirModel<T> getModel() {
        return this.model;
    }
}

