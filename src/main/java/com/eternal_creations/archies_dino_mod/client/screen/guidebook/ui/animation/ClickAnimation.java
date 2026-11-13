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

public abstract class ClickAnimation
implements UIAnimation {
    public static final ClickAnimation NONE = new ClickAnimation(1.0f, (Easing)Easing.Simple.LINEAR){

        @Override
        public void apply(RenderState renderState, Rect2f bounds, float partialTick) {
        }

        @Override
        public void trigger() {
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

    private ClickAnimation(float speed, Easing easing) {
        this.speed = speed;
        this.easing = easing;
    }

    public void trigger() {
        this.tween.setValue(0.0f);
        this.tween.setTarget(1.0f, this.speed, this.easing);
    }

    @Override
    public void tick() {
        this.tween.tick();
    }

    @Override
    public boolean isFinished(float partialTick) {
        return this.tween.isFinished();
    }

    public static ClickAnimation ding(final float maxAngle, final float swings, float speed, Easing easing) {
        return new ClickAnimation(speed, easing){

            @Override
            public void apply(RenderState renderState, Rect2f bounds, float pt) {
                float progress = this.tween.get(pt);
                if (progress > 0.0f && progress < 1.0f) {
                    float angle = maxAngle * (float)Math.sin((double)progress * Math.PI * 2.0 * (double)swings) * (1.0f - progress);
                    renderState.transform().rotate(angle, 0.5f * bounds.width(), 0.15f * bounds.height());
                }
            }
        };
    }

    public static ClickAnimation squish(final float minScale, float speed, Easing easing) {
        return new ClickAnimation(speed, easing){

            @Override
            public void apply(RenderState renderState, Rect2f bounds, float pt) {
                float progress = this.tween.get(pt);
                if (progress > 0.0f && progress < 1.0f) {
                    float s = 1.0f - (float)Math.sin((double)progress * Math.PI) * (1.0f - minScale);
                    renderState.transform().scale(s, bounds.width() * 0.5f, bounds.height() * 0.5f);
                }
            }
        };
    }
}

