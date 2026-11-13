/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 */
package com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.widget;

import com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.UIContext;
import com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.misc.Sprite;
import com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.render.RenderState;
import com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.widget.AbstractWidget;
import net.minecraft.resources.ResourceLocation;

public class ImageView
extends AbstractWidget {
    private final Sprite sprite;

    protected ImageView(Builder builder) {
        super(builder);
        this.sprite = builder.buildSprite();
    }

    @Override
    protected void renderContent(UIContext ctx, RenderState state) {
        ctx.drawSprite(this.sprite, 0.0f, 0.0f, this.bounds.width(), this.bounds.height());
    }

    public Sprite sprite() {
        return this.sprite;
    }

    public static Builder builder(float x, float y, float width, float height) {
        return new Builder(x, y, width, height);
    }

    public static Builder builder(ResourceLocation texture, float x, float y, float width, float height) {
        return new Builder(x, y, width, height).texture(texture);
    }

    public static class Builder
    extends AbstractWidget.Builder<Builder, ImageView> {
        private Sprite customSprite;
        private ResourceLocation texture;
        private float u = 0.0f;
        private float v = 0.0f;
        private Float spriteWidth = null;
        private Float spriteHeight = null;
        private float textureWidth = 0.0f;
        private float textureHeight = 0.0f;

        public Builder(float x, float y, float width, float height) {
            super(x, y, width, height);
        }

        public Builder sprite(Sprite sprite) {
            this.customSprite = sprite;
            return this;
        }

        public Builder texture(ResourceLocation texture) {
            this.texture = texture;
            return this;
        }

        public Builder uv(float u, float v) {
            this.u = u;
            this.v = v;
            return this;
        }

        public Builder spriteSize(float spriteWidth, float spriteHeight) {
            this.spriteWidth = Float.valueOf(spriteWidth);
            this.spriteHeight = Float.valueOf(spriteHeight);
            return this;
        }

        public Builder textureSize(float texWidth, float texHeight) {
            this.textureWidth = texWidth;
            this.textureHeight = texHeight;
            return this;
        }

        public Builder atlas(ResourceLocation texture, float u, float v, float spriteW, float spriteH, float texW, float texH) {
            this.texture = texture;
            this.u = u;
            this.v = v;
            this.spriteWidth = Float.valueOf(spriteW);
            this.spriteHeight = Float.valueOf(spriteH);
            this.textureWidth = texW;
            this.textureHeight = texH;
            return this;
        }

        protected Sprite buildSprite() {
            if (this.customSprite != null) {
                return this.customSprite;
            }
            if (this.texture == null) {
                return Sprite.EMPTY;
            }
            if (this.textureWidth > 0.0f && this.textureHeight > 0.0f) {
                float sw = this.spriteWidth != null ? this.spriteWidth.floatValue() : this.width;
                float sh = this.spriteHeight != null ? this.spriteHeight.floatValue() : this.height;
                return Sprite.of(this.texture, this.u, this.v, sw, sh, this.textureWidth, this.textureHeight);
            }
            return Sprite.of(this.texture);
        }

        @Override
        public ImageView build() {
            return new ImageView(this);
        }
    }
}

