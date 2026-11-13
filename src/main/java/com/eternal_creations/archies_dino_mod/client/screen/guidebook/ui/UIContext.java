/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.blaze3d.vertex.BufferBuilder
 *  com.mojang.blaze3d.vertex.BufferBuilder$RenderedBuffer
 *  com.mojang.blaze3d.vertex.BufferUploader
 *  com.mojang.blaze3d.vertex.DefaultVertexFormat
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.Tesselator
 *  com.mojang.blaze3d.vertex.VertexFormat$Mode
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.Font$DisplayMode
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.renderer.GameRenderer
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.MultiBufferSource$BufferSource
 *  net.minecraft.client.resources.sounds.SimpleSoundInstance
 *  net.minecraft.client.resources.sounds.SoundInstance
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.util.FormattedCharSequence
 *  net.minecraft.world.entity.player.Player
 *  org.joml.Matrix4f
 *  org.joml.Vector4f
 */
package com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui;

import com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.PopupManager;
import com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.misc.Color4f;
import com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.misc.Crop;
import com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.misc.Rect2f;
import com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.misc.Sprite;
import com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.render.RenderState;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Player;
import org.joml.Matrix4f;
import org.joml.Vector4f;

public class UIContext {
    private GuiGraphics graphics;
    private Player player;
    private double baseMouseX;
    private double baseMouseY;
    private float partialTick;
    private int windowWidth;
    private int windowHeight;
    private int bookWidth;
    private int bookHeight;
    private PopupManager popupManager;
    private double[] offsetXStack = new double[16];
    private double[] offsetYStack = new double[16];
    private int stackPointer = 0;
    private double currentOffsetX = 0.0;
    private double currentOffsetY = 0.0;
    private final Deque<RenderState> stateStack = new ArrayDeque<RenderState>();
    private final Vector4f scratch = new Vector4f();
    private Rect2f.Mutable[] clipRects = UIContext.createRectPool(8);
    private int clipDepth = 0;

    private UIContext() {
    }

    public static UIContext of() {
        return new UIContext();
    }

    public void prepare(GuiGraphics graphics, Player player, double rootMouseX, double rootMouseY, float partialTick, int bookWidth, int bookHeight, int windowWidth, int windowHeight, PopupManager popupManager) {
        this.graphics = graphics;
        this.player = player;
        this.baseMouseX = rootMouseX;
        this.baseMouseY = rootMouseY;
        this.partialTick = partialTick;
        this.bookWidth = bookWidth;
        this.bookHeight = bookHeight;
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.popupManager = popupManager;
        this.stackPointer = 0;
        this.currentOffsetX = 0.0;
        this.currentOffsetY = 0.0;
        this.stateStack.clear();
        this.clipDepth = 0;
        RenderSystem.disableScissor();
    }

    public void pushOffset(double x, double y) {
        if (this.stackPointer >= this.offsetXStack.length) {
            this.offsetXStack = Arrays.copyOf(this.offsetXStack, this.offsetXStack.length * 2);
            this.offsetYStack = Arrays.copyOf(this.offsetYStack, this.offsetYStack.length * 2);
        }
        this.offsetXStack[this.stackPointer] = x;
        this.offsetYStack[this.stackPointer] = y;
        ++this.stackPointer;
        this.currentOffsetX += x;
        this.currentOffsetY += y;
        this.graphics.m_280168_().m_85836_();
        this.graphics.m_280168_().m_85837_(x, y, 0.0);
    }

    public void popOffset() {
        if (this.stackPointer > 0) {
            --this.stackPointer;
            this.currentOffsetX -= this.offsetXStack[this.stackPointer];
            this.currentOffsetY -= this.offsetYStack[this.stackPointer];
            this.graphics.m_280168_().m_85849_();
        }
    }

    public void pushState(RenderState state, Rect2f bounds) {
        Crop.Mutable crop;
        this.stateStack.push(state);
        PoseStack pose = this.graphics.m_280168_();
        pose.m_85836_();
        state.transform().apply(pose, bounds);
        if (!state.color().isIdentity()) {
            Color4f.Mutable color = state.color();
            RenderSystem.setShaderColor((float)color.r(), (float)color.g(), (float)color.b(), (float)color.a());
        }
        if (!(crop = state.crop()).isEmpty()) {
            this.ensureClipCapacity();
            Rect2f.Mutable target = this.clipRects[this.clipDepth];
            this.computeCropScreenRect(bounds, crop, pose.m_85850_().m_252922_(), target);
            if (this.clipDepth > 0) {
                target.intersect(this.clipRects[this.clipDepth - 1]);
            }
            ++this.clipDepth;
            this.applyScissor(target);
        }
    }

    public void popState() {
        if (this.stateStack.isEmpty()) {
            return;
        }
        RenderState state = this.stateStack.pop();
        if (!state.crop().isEmpty()) {
            --this.clipDepth;
            if (this.clipDepth <= 0) {
                this.clipDepth = 0;
                RenderSystem.disableScissor();
            } else {
                this.applyScissor(this.clipRects[this.clipDepth - 1]);
            }
        }
        if (!state.color().isIdentity()) {
            RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        }
        this.graphics.m_280168_().m_85849_();
    }

    private static Rect2f.Mutable[] createRectPool(int size) {
        Rect2f.Mutable[] arr = new Rect2f.Mutable[size];
        for (int i = 0; i < size; ++i) {
            arr[i] = Rect2f.of();
        }
        return arr;
    }

    private void ensureClipCapacity() {
        if (this.clipDepth >= this.clipRects.length) {
            Rect2f.Mutable[] grown = new Rect2f.Mutable[this.clipRects.length * 2];
            System.arraycopy(this.clipRects, 0, grown, 0, this.clipRects.length);
            for (int i = this.clipRects.length; i < grown.length; ++i) {
                grown[i] = Rect2f.of();
            }
            this.clipRects = grown;
        }
    }

    private void computeCropScreenRect(Rect2f bounds, Crop crop, Matrix4f matrix, Rect2f.Mutable out) {
        float left = bounds.width() * crop.left();
        float top = bounds.height() * crop.top();
        float right = bounds.width() * (1.0f - crop.right());
        float bottom = bounds.height() * (1.0f - crop.bottom());
        float minX = Float.MAX_VALUE;
        float minY = Float.MAX_VALUE;
        float maxX = -3.4028235E38f;
        float maxY = -3.4028235E38f;
        for (int i = 0; i < 4; ++i) {
            float px = (i & 1) == 0 ? left : right;
            float py = (i & 2) == 0 ? top : bottom;
            this.scratch.set(px, py, 0.0f, 1.0f);
            matrix.transform(this.scratch);
            minX = Math.min(minX, this.scratch.x());
            maxX = Math.max(maxX, this.scratch.x());
            minY = Math.min(minY, this.scratch.y());
            maxY = Math.max(maxY, this.scratch.y());
        }
        out.set(minX, minY, maxX - minX, maxY - minY);
    }

    private void applyScissor(Rect2f rect) {
        double scale = Minecraft.m_91087_().m_91268_().m_85449_();
        int windowH = Minecraft.m_91087_().m_91268_().m_85442_();
        int sx = Math.round((float)((double)rect.x() * scale));
        int sw = Math.max(0, Math.round((float)((double)rect.width() * scale)));
        int sy = windowH - Math.round((float)((double)rect.maxY() * scale));
        int sh = Math.max(0, Math.round((float)((double)rect.height() * scale)));
        RenderSystem.enableScissor((int)sx, (int)sy, (int)sw, (int)sh);
    }

    public void drawSprite(Sprite sprite, Rect2f bounds) {
        float maxV;
        float minV;
        float maxU;
        float minU;
        if (sprite == null || sprite.texture() == null || bounds.width() <= 0.0f || bounds.height() <= 0.0f) {
            return;
        }
        float x1 = bounds.x();
        float y1 = bounds.y();
        float x2 = x1 + bounds.width();
        float y2 = y1 + bounds.height();
        if (sprite.textureWidth() <= 0.0f || sprite.textureHeight() <= 0.0f || sprite.width() <= 0.0f || sprite.height() <= 0.0f) {
            minU = 0.0f;
            maxU = 1.0f;
            minV = 0.0f;
            maxV = 1.0f;
        } else {
            minU = sprite.u() / sprite.textureWidth();
            maxU = (sprite.u() + sprite.width()) / sprite.textureWidth();
            minV = sprite.v() / sprite.textureHeight();
            maxV = (sprite.v() + sprite.height()) / sprite.textureHeight();
        }
        RenderSystem.setShaderTexture((int)0, (ResourceLocation)sprite.texture());
        RenderSystem.setShader(GameRenderer::m_172817_);
        Matrix4f matrix = this.graphics.m_280168_().m_85850_().m_252922_();
        BufferBuilder buffer = Tesselator.m_85913_().m_85915_();
        buffer.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85817_);
        buffer.m_252986_(matrix, x1, y1, 0.0f).m_7421_(minU, minV).m_5752_();
        buffer.m_252986_(matrix, x1, y2, 0.0f).m_7421_(minU, maxV).m_5752_();
        buffer.m_252986_(matrix, x2, y2, 0.0f).m_7421_(maxU, maxV).m_5752_();
        buffer.m_252986_(matrix, x2, y1, 0.0f).m_7421_(maxU, minV).m_5752_();
        BufferUploader.m_231202_((BufferBuilder.RenderedBuffer)buffer.m_231175_());
    }

    public void drawSprite(Sprite sprite, float x, float y, float width, float height) {
        this.drawSprite(sprite, Rect2f.of(x, y, width, height));
    }

    public void drawText(FormattedCharSequence text, float x, float y, int color, boolean shadow) {
        Font font = this.font();
        MultiBufferSource.BufferSource bufferSource = this.graphics.m_280091_();
        Matrix4f matrix = this.graphics.m_280168_().m_85850_().m_252922_();
        int finalColor = this.applyStateAlpha(color);
        float scale = (float)Math.sqrt(matrix.m00() * matrix.m00() + matrix.m01() * matrix.m01());
        if (scale < 0.95f && scale > 0.05f) {
            float d = (1.0f - scale) * 0.35f;
            int alpha = finalColor >> 24 & 0xFF;
            int subColor = (int)((float)alpha * 0.5f) << 24 | finalColor & 0xFFFFFF;
            font.m_272191_(text, x + d, y, subColor, false, matrix, (MultiBufferSource)bufferSource, Font.DisplayMode.NORMAL, 0, 0xF000F0);
            font.m_272191_(text, x - d, y, subColor, false, matrix, (MultiBufferSource)bufferSource, Font.DisplayMode.NORMAL, 0, 0xF000F0);
            font.m_272191_(text, x, y + d, subColor, false, matrix, (MultiBufferSource)bufferSource, Font.DisplayMode.NORMAL, 0, 0xF000F0);
            font.m_272191_(text, x, y - d, subColor, false, matrix, (MultiBufferSource)bufferSource, Font.DisplayMode.NORMAL, 0, 0xF000F0);
        }
        font.m_272191_(text, x, y, finalColor, shadow, matrix, (MultiBufferSource)bufferSource, Font.DisplayMode.NORMAL, 0, 0xF000F0);
        bufferSource.m_109911_();
    }

    public int applyStateAlpha(int color) {
        if (this.stateStack.isEmpty()) {
            return color;
        }
        RenderState state = this.stateStack.peek();
        float alphaFactor = state.color().a();
        if (alphaFactor >= 1.0f) {
            return color;
        }
        int baseAlpha = color >>> 24;
        if (baseAlpha == 0) {
            baseAlpha = 255;
        }
        int finalAlpha = Math.min(255, Math.max(0, (int)((float)baseAlpha * alphaFactor)));
        return color & 0xFFFFFF | finalAlpha << 24;
    }

    public double currentOffsetX() {
        return this.currentOffsetX;
    }

    public double currentOffsetY() {
        return this.currentOffsetY;
    }

    public GuiGraphics graphics() {
        return this.graphics;
    }

    public PoseStack pose() {
        return this.graphics.m_280168_();
    }

    public Player player() {
        return this.player;
    }

    public double mouseX() {
        return this.baseMouseX - this.currentOffsetX;
    }

    public double mouseY() {
        return this.baseMouseY - this.currentOffsetY;
    }

    public float partialTick() {
        return this.partialTick;
    }

    public int bookWidth() {
        return this.bookWidth;
    }

    public int bookHeight() {
        return this.bookHeight;
    }

    public PopupManager popupManager() {
        return this.popupManager;
    }

    public Font font() {
        return Minecraft.m_91087_().f_91062_;
    }

    public int windowWidth() {
        return this.windowWidth;
    }

    public int windowHeight() {
        return this.windowHeight;
    }

    public void playSound(SoundEvent sound, float volume, float pitch) {
        Minecraft.m_91087_().m_91106_().m_120367_((SoundInstance)SimpleSoundInstance.m_119755_((SoundEvent)sound, (float)pitch, (float)volume));
    }

    public void playSound(SoundEvent sound) {
        this.playSound(sound, 1.0f, 1.0f);
    }
}

