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

public abstract class AppearAnimation
implements UIAnimation {
    public static final AppearAnimation NONE = new AppearAnimation(1.0f, (Easing)Easing.Simple.LINEAR){

        @Override
        public void updateVisibilityStatus(boolean visible) {
        }

        @Override
        public void tick() {
        }

        @Override
        public void apply(RenderState renderState, Rect2f bounds, float partialTick) {
        }

        @Override
        public boolean isFinished(float partialTick) {
            return true;
        }
    };
    protected TweenFloat tween = TweenFloat.of();
    private final float speed;
    private final Easing easing;

    private AppearAnimation(float speed, Easing easing) {
        this.speed = speed;
        this.easing = easing;
    }

    public void show() {
        this.updateVisibilityStatus(true);
    }

    public void hide() {
        this.updateVisibilityStatus(false);
    }

    public void updateVisibilityStatus(boolean visible) {
        if (visible) {
            this.tween.setValue(0.0f);
            this.tween.setTarget(1.0f, this.speed, this.easing);
        } else {
            this.tween.setTarget(0.0f, this.speed, this.easing);
        }
    }

    @Override
    public void tick() {
        this.tween.tick();
    }

    @Override
    public boolean isFinished(float partialTick) {
        return this.tween.isFinished();
    }

    public static AppearAnimation verticalBottomSlide(float speed, Easing easing) {
        return new AppearAnimation(speed, easing){

            @Override
            public void apply(RenderState state, Rect2f bounds, float pt) {
                float progress = this.tween.get(pt);
                float inv = 1.0f - progress;
                state.transform().translate(0.0f, -inv * bounds.height());
                state.crop().cropTop(inv);
            }
        };
    }

    public static AppearAnimation leftShift(final float amount, float speed, Easing easing) {
        return new AppearAnimation(speed, easing){

            @Override
            public void apply(RenderState state, Rect2f bounds, float pt) {
                float progress = this.tween.get(pt);
                float inv = 1.0f - progress;
                if (inv > 0.0f) {
                    float shiftX = inv * amount;
                    state.transform().translate(shiftX, 0.0f);
                    float baseHidden = bounds.width() - amount;
                    float hiddenPixels = baseHidden + inv * amount;
                    state.crop().cropRight(hiddenPixels / bounds.width());
                }
            }
        };
    }

    public static AppearAnimation rightShift(final float amount, float speed, Easing easing) {
        return new AppearAnimation(speed, easing){

            @Override
            public void apply(RenderState state, Rect2f bounds, float pt) {
                float progress = this.tween.get(pt);
                float inv = 1.0f - progress;
                if (inv > 0.0f) {
                    float shiftX = -inv * amount;
                    state.transform().translate(shiftX, 0.0f);
                    float baseHidden = bounds.width() - amount;
                    float hiddenPixels = baseHidden + inv * amount;
                    state.crop().cropLeft(hiddenPixels / bounds.width());
                }
            }
        };
    }
}

