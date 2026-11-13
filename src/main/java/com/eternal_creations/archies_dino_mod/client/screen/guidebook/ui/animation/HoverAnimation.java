/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.astemir.astemirlib.re.core.math.Easing
 *  com.astemir.astemirlib.re.core.math.Easing$Simple
 *  com.astemir.astemirlib.re.core.math.tween.TweenFloat
 */
package com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.animation;

import com.astemir.astemirlib.re.core.math.Easing;
import com.astemir.astemirlib.re.core.math.tween.TweenFloat;
import com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.animation.UIAnimation;
import com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.misc.Rect2f;
import com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.render.RenderState;

public abstract class HoverAnimation
implements UIAnimation {
    public static final HoverAnimation NONE = new HoverAnimation(1.0f, (Easing)Easing.Simple.LINEAR){

        @Override
        public void apply(RenderState renderState, Rect2f bounds, float partialTick) {
        }

        @Override
        public void updateHoverStatus(boolean hovered) {
        }

        @Override
        public void tick() {
        }

        @Override
        public boolean isFinished(float partialTick) {
            return true;
        }
    };
    protected TweenFloat tween = TweenFloat.of();
    private final float speed;
    private final Easing easing;

    private HoverAnimation(float speed, Easing easing) {
        this.speed = speed;
        this.easing = easing;
    }

    public void updateHoverStatus(boolean hovered) {
        this.tween.setTarget(hovered ? 1.0f : 0.0f, this.speed, this.easing);
    }

    @Override
    public void tick() {
        this.tween.tick();
    }

    @Override
    public boolean isFinished(float partialTick) {
        return this.tween.isFinished();
    }

    public static HoverAnimation leftSlide(final float amount, final float maxShift, float speed, Easing easing) {
        return new HoverAnimation(speed, easing){

            @Override
            public void apply(RenderState renderState, Rect2f bounds, float pt) {
                float progress = this.tween.get(pt);
                float shift = progress * maxShift;
                renderState.transform().translate(-shift, 0.0f);
                float hiddenTail = bounds.width() - amount - shift;
                renderState.crop().cropRight(Math.max(0.0f, hiddenTail) / bounds.width());
            }
        };
    }

    public static HoverAnimation rightSlide(final float amount, final float maxShift, float speed, Easing easing) {
        return new HoverAnimation(speed, easing){

            @Override
            public void apply(RenderState renderState, Rect2f bounds, float pt) {
                float progress = this.tween.get(pt);
                float shift = progress * maxShift;
                renderState.transform().translate(shift, 0.0f);
                float hiddenTail = bounds.width() - amount - shift;
                renderState.crop().cropLeft(Math.max(0.0f, hiddenTail) / bounds.width());
            }
        };
    }

    public static HoverAnimation scale(final float targetScale, float speed, Easing easing) {
        return new HoverAnimation(speed, easing){

            @Override
            public void apply(RenderState renderState, Rect2f bounds, float pt) {
                float scale = 1.0f + this.tween.get(pt) * (targetScale - 1.0f);
                renderState.transform().scale(scale, scale, bounds.width() * 0.5f, bounds.height() * 0.5f);
            }
        };
    }

    public static HoverAnimation parse(String animName) {
        if (animName == null || animName.isBlank()) {
            return NONE;
        }
        return switch (animName.toLowerCase().trim()) {
            case "scale" -> HoverAnimation.scale(1.05f, 0.1f, (Easing)Easing.Simple.SMOOTH_STEP);
            default -> NONE;
        };
    }
}

