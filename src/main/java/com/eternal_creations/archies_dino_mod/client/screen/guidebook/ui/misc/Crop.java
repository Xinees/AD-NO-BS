/*
 * Decompiled with CFR 0.152.
 */
package com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.misc;

public interface Crop {
    public static final Crop EMPTY = new Crop(){

        @Override
        public float top() {
            return 0.0f;
        }

        @Override
        public float bottom() {
            return 0.0f;
        }

        @Override
        public float left() {
            return 0.0f;
        }

        @Override
        public float right() {
            return 0.0f;
        }
    };

    public float top();

    public float bottom();

    public float left();

    public float right();

    default public boolean isEmpty() {
        return this.top() == 0.0f && this.bottom() == 0.0f && this.left() == 0.0f && this.right() == 0.0f;
    }

    public static Mutable of() {
        return new Mutable();
    }

    public static class Mutable
    implements Crop {
        private float top;
        private float bottom;
        private float left;
        private float right;

        private Mutable() {
        }

        public void set(float top, float bottom, float left, float right) {
            this.top = top;
            this.bottom = bottom;
            this.left = left;
            this.right = right;
        }

        public void reset() {
            this.top = 0.0f;
            this.bottom = 0.0f;
            this.left = 0.0f;
            this.right = 0.0f;
        }

        public void cropLeft(float amount) {
            this.left = Math.max(this.left, amount);
        }

        public void cropRight(float amount) {
            this.right = Math.max(this.right, amount);
        }

        public void cropTop(float amount) {
            this.top = Math.max(this.top, amount);
        }

        public void cropBottom(float amount) {
            this.bottom = Math.max(this.bottom, amount);
        }

        @Override
        public float top() {
            return this.top;
        }

        @Override
        public float bottom() {
            return this.bottom;
        }

        @Override
        public float left() {
            return this.left;
        }

        @Override
        public float right() {
            return this.right;
        }
    }
}

