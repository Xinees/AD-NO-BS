/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.pipeline.RenderTarget
 *  com.mojang.blaze3d.pipeline.TextureTarget
 *  com.mojang.blaze3d.platform.GlStateManager$DestFactor
 *  com.mojang.blaze3d.platform.GlStateManager$SourceFactor
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.VertexSorting
 *  net.minecraft.client.Minecraft
 *  org.joml.Matrix4f
 *  org.joml.Matrix4fc
 */
package com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.render;

import com.eternal_creations.archies_dino_mod.client.screen.guidebook.book.PageSpread;
import com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.UIContext;
import com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.render.PageCurlMesh;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexSorting;
import net.minecraft.client.Minecraft;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;

public class PageBuffer {
    private RenderTarget target;
    private int pixelWidth = -1;
    private int pixelHeight = -1;
    private boolean isDirty = true;
    private final Matrix4f oldProjMat = new Matrix4f();
    private final Matrix4f projMat = new Matrix4f();

    public void markDirty() {
        this.isDirty = true;
    }

    public void prepare(int pw, int ph) {
        if (this.target == null || this.pixelWidth != pw || this.pixelHeight != ph) {
            if (this.target != null) {
                this.target.m_83930_();
            }
            this.pixelWidth = Math.max(1, pw);
            this.pixelHeight = Math.max(1, ph);
            this.target = new TextureTarget(this.pixelWidth, this.pixelHeight, false, Minecraft.f_91002_);
            this.target.m_83931_(0.0f, 0.0f, 0.0f, 0.0f);
            this.isDirty = true;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void renderSpread(PageSpread spread, UIContext ctx, float w, float h) {
        if (spread == null || w <= 0.0f || h <= 0.0f) {
            return;
        }
        Minecraft mc = Minecraft.m_91087_();
        double scale = mc.m_91268_().m_85449_();
        int pw = Math.max(1, (int)Math.round((double)w * scale));
        int ph = Math.max(1, (int)Math.round((double)h * scale));
        this.prepare(pw, ph);
        float snappedW = (float)pw / (float)scale;
        float snappedH = (float)ph / (float)scale;
        if (!this.isDirty) {
            return;
        }
        this.oldProjMat.set((Matrix4fc)RenderSystem.getProjectionMatrix());
        this.projMat.setOrtho(0.0f, snappedW, snappedH, 0.0f, 1000.0f, 21000.0f);
        PoseStack pose = ctx.graphics().m_280168_();
        pose.m_85836_();
        try {
            RenderSystem.setProjectionMatrix((Matrix4f)this.projMat, (VertexSorting)VertexSorting.f_276633_);
            this.target.m_83954_(Minecraft.f_91002_);
            this.target.m_83947_(true);
            pose.m_166856_();
            RenderSystem.enableBlend();
            RenderSystem.blendFuncSeparate((GlStateManager.SourceFactor)GlStateManager.SourceFactor.SRC_ALPHA, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, (GlStateManager.SourceFactor)GlStateManager.SourceFactor.ONE, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            if (spread.background() != null) {
                PageCurlMesh.drawFloatQuad(ctx.graphics(), spread.background(), 0.0f, 0.0f, w, h, 0.0f, 0.0f, w, h, w, h);
            }
            if (spread.info() != null) {
                PageCurlMesh.drawFloatQuad(ctx.graphics(), spread.info(), 0.0f, 0.0f, w, h, 0.0f, 0.0f, w, h, w, h);
            }
            spread.render(ctx);
            this.isDirty = false;
        }
        finally {
            pose.m_85849_();
            this.target.m_83970_();
            mc.m_91385_().m_83947_(true);
            RenderSystem.setProjectionMatrix((Matrix4f)this.oldProjMat, (VertexSorting)VertexSorting.f_276633_);
            RenderSystem.viewport((int)0, (int)0, (int)mc.m_91268_().m_85441_(), (int)mc.m_91268_().m_85442_());
            RenderSystem.defaultBlendFunc();
        }
    }

    public int getTextureId() {
        return this.target != null ? this.target.m_83975_() : -1;
    }

    public void destroy() {
        if (this.target != null) {
            this.target.m_83930_();
            this.target = null;
            this.pixelWidth = -1;
            this.pixelHeight = -1;
            this.isDirty = true;
        }
    }
}

