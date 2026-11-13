/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.astemir.astemirlib.re.core.math.AMath
 *  com.mojang.blaze3d.platform.GlStateManager$DestFactor
 *  com.mojang.blaze3d.platform.GlStateManager$SourceFactor
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.blaze3d.vertex.BufferBuilder
 *  com.mojang.blaze3d.vertex.BufferBuilder$RenderedBuffer
 *  com.mojang.blaze3d.vertex.BufferUploader
 *  com.mojang.blaze3d.vertex.DefaultVertexFormat
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.Tesselator
 *  com.mojang.blaze3d.vertex.VertexFormat$Mode
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.renderer.GameRenderer
 *  net.minecraft.resources.ResourceLocation
 *  org.joml.Matrix4f
 *  org.joml.Vector3f
 */
package com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.render;

import com.astemir.astemirlib.re.core.math.AMath;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class PageCurlMesh {
    private static final int TOTAL_SLICES = 32;
    private final Vector3f[] tops = PageCurlMesh.createVectorPool(33);
    private final Vector3f[] bottoms = PageCurlMesh.createVectorPool(33);
    private final float[] angles = new float[33];

    public void updateGeometry(float bookWidth, float bookHeight, float progress, boolean forward, boolean fastFlip) {
        float halfWidth = bookWidth / 2.0f;
        float halfHeight = bookHeight / 2.0f;
        float ds = halfWidth / 32.0f;
        float waveCount = fastFlip ? 3.2f : 2.6f;
        float waveSpeed = fastFlip ? 7.5f : 5.0f;
        float whipAmp = fastFlip ? 0.65f : 0.48f;
        float twist3D = 0.4f;
        float maxLift = fastFlip ? 26.0f : 18.0f;
        float deep3DDepth = 12.0f;
        float envelope = AMath.sin((float)(progress * (float)Math.PI));
        float envelopePow = envelope * envelope * (3.0f - 2.0f * envelope);
        float baseBeta = forward ? progress * (float)Math.PI : (1.0f - progress) * (float)Math.PI;
        float direction = forward ? 1.0f : -1.0f;
        float topX = 0.0f;
        float topZ = 0.0f;
        float botX = 0.0f;
        float botZ = 0.0f;
        this.tops[0].set(0.0f, -halfHeight, 0.0f);
        this.bottoms[0].set(0.0f, halfHeight, 0.0f);
        this.angles[0] = baseBeta;
        for (int j = 1; j <= 32; ++j) {
            float t = (float)j / 32.0f;
            float basePhase = (t * waveCount - progress * waveSpeed) * ((float)Math.PI * 2);
            float phaseTop = basePhase + twist3D * (float)Math.PI;
            float primaryWaveBot = (float)Math.sin(basePhase);
            float harmonicWaveBot = 0.3f * (float)Math.sin((double)basePhase * 2.0);
            float primaryWaveTop = (float)Math.sin(phaseTop);
            float harmonicWaveTop = 0.3f * (float)Math.sin((double)phaseTop * 2.0);
            float whipGrowth = t * (float)Math.sqrt(t);
            float botWhip = whipAmp * envelopePow * whipGrowth * (primaryWaveBot + harmonicWaveBot);
            float topWhip = whipAmp * envelopePow * whipGrowth * (primaryWaveTop + harmonicWaveTop);
            float botAngle = baseBeta + direction * botWhip;
            float topAngle = baseBeta + direction * topWhip;
            this.angles[j] = (botAngle + topAngle) * 0.5f;
            botX += ds * AMath.cos((float)botAngle);
            botZ += ds * AMath.sin((float)botAngle);
            float lift = maxLift * envelope * (t * t);
            float zWaveBot = primaryWaveBot * deep3DDepth * envelope * whipGrowth;
            float zWaveTop = primaryWaveTop * deep3DDepth * envelope * whipGrowth;
            this.tops[j].set(topX += ds * AMath.cos((float)topAngle), -halfHeight, (topZ += ds * AMath.sin((float)topAngle)) + zWaveTop);
            this.bottoms[j].set(botX, halfHeight - lift, botZ + zWaveBot);
        }
    }

    public void renderMesh(GuiGraphics graphics, float bookWidth, float bookHeight, float progress, boolean forward, int srcTextureId, int dstTextureId) {
        float rightAlpha;
        float leftAlpha;
        int rightTexId;
        int leftTexId;
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate((GlStateManager.SourceFactor)GlStateManager.SourceFactor.ONE, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, (GlStateManager.SourceFactor)GlStateManager.SourceFactor.ONE, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        RenderSystem.disableCull();
        if (forward) {
            leftTexId = srcTextureId;
            rightTexId = dstTextureId;
            leftAlpha = progress < 0.5f ? 1.0f : 1.0f - (progress - 0.5f) / 0.5f;
            rightAlpha = progress < 0.5f ? progress / 0.5f : 1.0f;
        } else {
            leftTexId = dstTextureId;
            rightTexId = srcTextureId;
            leftAlpha = progress < 0.5f ? progress / 0.5f : 1.0f;
            rightAlpha = progress < 0.5f ? 1.0f : 1.0f - (progress - 0.5f) / 0.5f;
        }
        PageCurlMesh.drawFlatHalf(graphics, bookWidth, bookHeight, leftTexId, true, leftAlpha);
        PageCurlMesh.drawFlatHalf(graphics, bookWidth, bookHeight, rightTexId, false, rightAlpha);
        PoseStack pose = graphics.m_280168_();
        pose.m_85836_();
        pose.m_252880_(bookWidth / 2.0f, bookHeight / 2.0f, 0.1f);
        float halfWidth = bookWidth / 2.0f;
        float ds = halfWidth / 32.0f;
        for (int j = 1; j <= 32; ++j) {
            int activeTextureId;
            Vector3f t1 = this.tops[j - 1];
            Vector3f t2 = this.tops[j];
            Vector3f b1 = this.bottoms[j - 1];
            Vector3f b2 = this.bottoms[j];
            float segmentAngle = (this.angles[j - 1] + this.angles[j]) * 0.5f;
            boolean isBackFace = forward ? Math.cos(segmentAngle) < 0.0 : Math.cos(segmentAngle) >= 0.0;
            int n = activeTextureId = isBackFace ? dstTextureId : srcTextureId;
            if (activeTextureId <= 0) continue;
            boolean localIsLeftSide = forward == isBackFace;
            float u1 = localIsLeftSide ? halfWidth - (float)(j - 1) * ds : halfWidth + (float)(j - 1) * ds;
            float u2 = localIsLeftSide ? halfWidth - (float)j * ds : halfWidth + (float)j * ds;
            PageCurlMesh.draw3DSlice(graphics, activeTextureId, bookWidth, t1, b1, t2, b2, u1, u2);
        }
        pose.m_85849_();
        RenderSystem.enableCull();
        RenderSystem.defaultBlendFunc();
    }

    private static void draw3DSlice(GuiGraphics graphics, int textureId, float bookWidth, Vector3f t1, Vector3f b1, Vector3f t2, Vector3f b2, float u1, float u2) {
        Matrix4f matrix = graphics.m_280168_().m_85850_().m_252922_();
        RenderSystem.setShader(GameRenderer::m_172817_);
        RenderSystem.setShaderTexture((int)0, (int)textureId);
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        float texU1 = u1 / bookWidth;
        float texU2 = u2 / bookWidth;
        BufferBuilder buffer = Tesselator.m_85913_().m_85915_();
        buffer.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85817_);
        buffer.m_252986_(matrix, t1.x, t1.y, t1.z).m_7421_(texU1, 1.0f).m_5752_();
        buffer.m_252986_(matrix, b1.x, b1.y, b1.z).m_7421_(texU1, 0.0f).m_5752_();
        buffer.m_252986_(matrix, b2.x, b2.y, b2.z).m_7421_(texU2, 0.0f).m_5752_();
        buffer.m_252986_(matrix, t2.x, t2.y, t2.z).m_7421_(texU2, 1.0f).m_5752_();
        BufferUploader.m_231202_((BufferBuilder.RenderedBuffer)buffer.m_231175_());
    }

    private static void drawFlatHalf(GuiGraphics graphics, float bookWidth, float bookHeight, int textureId, boolean leftSide, float alpha) {
        if (textureId <= 0 || alpha <= 0.001f) {
            return;
        }
        float halfW = bookWidth / 2.0f;
        float x = leftSide ? 0.0f : halfW;
        float minU = leftSide ? 0.0f : 0.5f;
        float maxU = leftSide ? 0.5f : 1.0f;
        RenderSystem.setShaderTexture((int)0, (int)textureId);
        RenderSystem.setShader(GameRenderer::m_172817_);
        RenderSystem.setShaderColor((float)alpha, (float)alpha, (float)alpha, (float)alpha);
        Matrix4f matrix = graphics.m_280168_().m_85850_().m_252922_();
        BufferBuilder buffer = Tesselator.m_85913_().m_85915_();
        buffer.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85817_);
        buffer.m_252986_(matrix, x, 0.0f, 0.0f).m_7421_(minU, 1.0f).m_5752_();
        buffer.m_252986_(matrix, x, bookHeight, 0.0f).m_7421_(minU, 0.0f).m_5752_();
        buffer.m_252986_(matrix, x + halfW, bookHeight, 0.0f).m_7421_(maxU, 0.0f).m_5752_();
        buffer.m_252986_(matrix, x + halfW, 0.0f, 0.0f).m_7421_(maxU, 1.0f).m_5752_();
        BufferUploader.m_231202_((BufferBuilder.RenderedBuffer)buffer.m_231175_());
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
    }

    public static void drawFloatQuad(GuiGraphics graphics, ResourceLocation texture, float x, float y, float width, float height, float u, float v, float regionW, float regionH, float texW, float texH) {
        float minU = u / texW;
        float maxU = (u + regionW) / texW;
        float minV = v / texH;
        float maxV = (v + regionH) / texH;
        RenderSystem.setShaderTexture((int)0, (ResourceLocation)texture);
        RenderSystem.setShader(GameRenderer::m_172817_);
        Matrix4f matrix = graphics.m_280168_().m_85850_().m_252922_();
        BufferBuilder buffer = Tesselator.m_85913_().m_85915_();
        buffer.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85817_);
        buffer.m_252986_(matrix, x, y, 0.0f).m_7421_(minU, minV).m_5752_();
        buffer.m_252986_(matrix, x, y + height, 0.0f).m_7421_(minU, maxV).m_5752_();
        buffer.m_252986_(matrix, x + width, y + height, 0.0f).m_7421_(maxU, maxV).m_5752_();
        buffer.m_252986_(matrix, x + width, y, 0.0f).m_7421_(maxU, minV).m_5752_();
        BufferUploader.m_231202_((BufferBuilder.RenderedBuffer)buffer.m_231175_());
    }

    private static Vector3f[] createVectorPool(int size) {
        Vector3f[] pool = new Vector3f[size];
        for (int i = 0; i < size; ++i) {
            pool[i] = new Vector3f();
        }
        return pool;
    }
}

