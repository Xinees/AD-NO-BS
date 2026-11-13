/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 */
package com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.misc;

import com.mojang.blaze3d.vertex.PoseStack;

public class Viewport2D {
    private final float targetWidth;
    private final float targetHeight;
    private final float baseScaleFactor;
    public float originX;
    public float originY;
    public float scale;

    public Viewport2D(float targetWidth, float targetHeight, float baseScaleFactor) {
        this.targetWidth = targetWidth;
        this.targetHeight = targetHeight;
        this.baseScaleFactor = baseScaleFactor;
        this.scale = 1.0f;
    }

    public Viewport2D(float targetWidth, float targetHeight) {
        this(targetWidth, targetHeight, 1.0f);
    }

    public void update(int windowWidth, int windowHeight) {
        float maxTargetW = this.targetWidth * this.baseScaleFactor;
        float maxTargetH = this.targetHeight * this.baseScaleFactor;
        float autoScale = 1.0f;
        if ((float)windowWidth < maxTargetW || (float)windowHeight < maxTargetH) {
            autoScale = Math.min((float)windowWidth / maxTargetW, (float)windowHeight / maxTargetH);
        }
        this.scale = autoScale * this.baseScaleFactor;
        float scaledW = this.targetWidth * this.scale;
        float scaledH = this.targetHeight * this.scale;
        this.originX = ((float)windowWidth - scaledW) / 2.0f;
        this.originY = ((float)windowHeight - scaledH) / 2.0f;
    }

    public double toLocalX(double windowMouseX) {
        return (windowMouseX - (double)this.originX) / (double)this.scale;
    }

    public double toLocalY(double windowMouseY) {
        return (windowMouseY - (double)this.originY) / (double)this.scale;
    }

    public void apply(PoseStack pose) {
        pose.m_252880_(this.originX, this.originY, 0.0f);
        pose.m_85841_(this.scale, this.scale, 1.0f);
    }

    public float targetWidth() {
        return this.targetWidth;
    }

    public float targetHeight() {
        return this.targetHeight;
    }

    public float scale() {
        return this.scale;
    }

    public float originX() {
        return this.originX;
    }

    public float originY() {
        return this.originY;
    }

    public static Viewport2D of(float targetWidth, float targetHeight, float baseScaleFactor) {
        return new Viewport2D(targetWidth, targetHeight, baseScaleFactor);
    }

    public static Viewport2D of(float targetWidth, float targetHeight) {
        return new Viewport2D(targetWidth, targetHeight, 1.0f);
    }
}

