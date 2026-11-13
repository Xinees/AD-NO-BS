/*
 * Decompiled with CFR 0.152.
 */
package com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui;

import com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.UIContext;
import com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.misc.Rect2f;
import com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.render.RenderState;

public interface UIElement {
    public void render(UIContext var1);

    default public void tick() {
    }

    default public void close() {
    }

    default public boolean mouseClicked(UIContext ctx, int button) {
        return false;
    }

    default public boolean mouseReleased(UIContext ctx, int button) {
        return false;
    }

    default public boolean isHovered(UIContext ctx) {
        return this.bounds().contains(ctx.mouseX(), ctx.mouseY());
    }

    default public RenderState state() {
        return RenderState.EMPTY;
    }

    default public Rect2f bounds() {
        return Rect2f.EMPTY;
    }

    default public int zIndex() {
        return 0;
    }

    @FunctionalInterface
    public static interface Factory {
        public UIElement create(float var1, float var2);
    }
}

