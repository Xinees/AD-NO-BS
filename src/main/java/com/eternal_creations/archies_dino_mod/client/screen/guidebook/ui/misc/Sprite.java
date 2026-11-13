/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 */
package com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.misc;

import com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.misc.Rect2f;
import net.minecraft.resources.ResourceLocation;

public interface Sprite {
    public static final Sprite EMPTY = new Sprite(){

        @Override
        public ResourceLocation texture() {
            return null;
        }

        @Override
        public float u() {
            return 0.0f;
        }

        @Override
        public float v() {
            return 0.0f;
        }

        @Override
        public float width() {
            return 0.0f;
        }

        @Override
        public float height() {
            return 0.0f;
        }

        @Override
        public float textureWidth() {
            return 0.0f;
        }

        @Override
        public float textureHeight() {
            return 0.0f;
        }
    };

    public ResourceLocation texture();

    public float u();

    public float v();

    public float width();

    public float height();

    public float textureWidth();

    public float textureHeight();

    public static Mutable of(ResourceLocation texture) {
        return new Mutable(texture, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);
    }

    public static Mutable of(ResourceLocation texture, Rect2f rect, float texWidth, float texHeight) {
        return new Mutable(texture, rect.x(), rect.y(), rect.width(), rect.height(), texWidth, texHeight);
    }

    public static Mutable of(ResourceLocation texture, float u, float v, float width, float height, float texWidth, float texHeight) {
        return new Mutable(texture, u, v, width, height, texWidth, texHeight);
    }

    public static Mutable of(ResourceLocation texture, float width, float height) {
        return new Mutable(texture, 0.0f, 0.0f, width, height, width, height);
    }

    public static class Mutable
    implements Sprite {
        public ResourceLocation texture;
        public float u;
        public float v;
        public float width;
        public float height;
        public float textureWidth;
        public float textureHeight;

        public Mutable(ResourceLocation texture, float u, float v, float width, float height, float texWidth, float texHeight) {
            this.set(texture, u, v, width, height, texWidth, texHeight);
        }

        public Mutable set(ResourceLocation texture, float u, float v, float width, float height, float texWidth, float texHeight) {
            this.texture = texture;
            this.u = u;
            this.v = v;
            this.width = width;
            this.height = height;
            this.textureWidth = texWidth;
            this.textureHeight = texHeight;
            return this;
        }

        public Mutable set(Sprite other) {
            return this.set(other.texture(), other.u(), other.v(), other.width(), other.height(), other.textureWidth(), other.textureHeight());
        }

        @Override
        public ResourceLocation texture() {
            return this.texture;
        }

        @Override
        public float u() {
            return this.u;
        }

        @Override
        public float v() {
            return this.v;
        }

        @Override
        public float width() {
            return this.width;
        }

        @Override
        public float height() {
            return this.height;
        }

        @Override
        public float textureWidth() {
            return this.textureWidth;
        }

        @Override
        public float textureHeight() {
            return this.textureHeight;
        }
    }
}

