/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.astemir.astemirlib.re.core.math.Easing
 *  com.astemir.astemirlib.re.core.math.Easing$Back
 *  com.astemir.astemirlib.re.core.math.Easing$Direction
 *  com.astemir.astemirlib.re.core.math.tween.TweenFloat
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.resources.sounds.SimpleSoundInstance
 *  net.minecraft.client.resources.sounds.SoundInstance
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundEvents
 */
package com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui;

import com.astemir.astemirlib.re.core.math.Easing;
import com.astemir.astemirlib.re.core.math.tween.TweenFloat;
import com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.UIContext;
import com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.UIElement;
import com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.widget.Container;
import com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.widget.ImageView;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;

public class PopupManager {
    private UIElement activePopup;
    private final TweenFloat scaleAnim = TweenFloat.of((float)0.0f);
    private final TweenFloat posAnim = TweenFloat.of((float)1.0f);

    public void open(UIElement popup) {
        this.activePopup = popup;
        this.scaleAnim.setValue(0.0f);
        this.posAnim.setValue(1.0f);
        this.scaleAnim.setTarget(1.0f, 0.12f, (Easing)Easing.Back.of((Easing.Direction)Easing.Direction.OUT, (float)1.2f));
        this.posAnim.setTarget(0.0f, 0.15f, (Easing)Easing.Back.of((Easing.Direction)Easing.Direction.OUT, (float)1.2f));
    }

    public void openImage(ResourceLocation texture, int bookWidth, int bookHeight, List<UIElement.Factory> popupChildren) {
        Container popupContainer = Container.of(0.0f, 0.0f, bookWidth, bookHeight);
        popupContainer.addChild(ImageView.builder(0.0f, 0.0f, bookWidth, bookHeight).texture(texture).build());
        for (UIElement.Factory factory : popupChildren) {
            popupContainer.addChild(factory.create(bookWidth, bookHeight));
        }
        this.open(popupContainer);
    }

    public void close() {
        this.scaleAnim.setTarget(0.0f, 0.12f, (Easing)Easing.Back.of((Easing.Direction)Easing.Direction.IN, (float)1.2f));
        this.posAnim.setTarget(1.0f, 0.15f, (Easing)Easing.Back.of((Easing.Direction)Easing.Direction.IN, (float)1.2f));
        Minecraft.m_91087_().m_91106_().m_120367_((SoundInstance)SimpleSoundInstance.m_119752_((SoundEvent)SoundEvents.f_11713_, (float)1.0f));
    }

    public boolean isOpen() {
        return this.activePopup != null && (this.scaleAnim.getValue() > 0.001f || this.scaleAnim.getTargetValue() > 0.0f);
    }

    public void tick() {
        this.scaleAnim.tick();
        this.posAnim.tick();
        if (this.activePopup != null) {
            this.activePopup.tick();
            if (this.scaleAnim.getValue() <= 0.001f && this.scaleAnim.getTargetValue() == 0.0f) {
                this.activePopup = null;
            }
        }
    }

    public void render(UIContext ctx) {
        if (!this.isOpen() || this.activePopup == null) {
            return;
        }
        float pt = ctx.partialTick();
        float scale = this.scaleAnim.get(pt);
        float posProgress = this.posAnim.get(pt);
        if (scale <= 0.001f) {
            return;
        }
        int w = ctx.bookWidth();
        int h = ctx.bookHeight();
        float yOffset = posProgress * ((float)h * 0.8f);
        float centerX = (float)w / 2.0f;
        float centerY = (float)h / 2.0f;
        PoseStack pose = ctx.pose();
        pose.m_85836_();
        pose.m_252880_(centerX, centerY + yOffset, 1050.0f);
        pose.m_85841_(scale, scale, 1.0f);
        pose.m_252880_(-centerX, -centerY, 0.0f);
        float alpha = Math.min(1.0f, Math.max(0.0f, scale));
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)alpha);
        this.activePopup.render(ctx);
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        pose.m_85849_();
    }

    public void renderBackground(UIContext ctx) {
        if (!this.isOpen() || this.activePopup == null) {
            return;
        }
        float pt = ctx.partialTick();
        float scale = this.scaleAnim.get(pt);
        if (scale <= 0.001f) {
            return;
        }
        float alpha = Math.min(1.0f, Math.max(0.0f, scale));
        int alphaInt = (int)(alpha * 117.0f);
        int dimColor = alphaInt << 24;
        RenderSystem.enableBlend();
        PoseStack poseStack = ctx.pose();
        poseStack.m_85836_();
        poseStack.m_252880_(0.0f, 0.0f, 1000.0f);
        ctx.graphics().m_280509_(0, 0, ctx.windowWidth(), ctx.windowHeight(), dimColor);
        poseStack.m_85849_();
    }

    public boolean mouseClicked(UIContext ctx, int button) {
        if (this.posAnim.isFinished() && this.scaleAnim.isFinished()) {
            this.close();
        }
        return false;
    }
}

