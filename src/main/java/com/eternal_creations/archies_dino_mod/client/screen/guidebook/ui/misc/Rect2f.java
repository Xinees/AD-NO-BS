/*
 * Decompiled with CFR 0.152.
 */
package com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.misc;

public interface Rect2f {
    public static final Rect2f EMPTY = Rect2f.ofImmutable(0.0f, 0.0f, 0.0f, 0.0f);

    default public Rect2f intersect(Rect2f other) {
        float x1 = Math.max(this.minX(), other.minX());
        float y1 = Math.max(this.minY(), other.minY());
        float x2 = Math.min(this.maxX(), other.maxX());
        float y2 = Math.min(this.maxY(), other.maxY());
        return Rect2f.ofImmutable(x1, y1, Math.max(0.0f, x2 - x1), Math.max(0.0f, y2 - y1));
    }

    public float x();

    public float y();

    public float width();

    public float height();

    default public float minX() {
        return this.x();
    }

    default public float minY() {
        return this.y();
    }

    default public float maxX() {
        return this.x() + this.width();
    }

    default public float maxY() {
        return this.y() + this.height();
    }

    default public float centerX() {
        return this.x() + this.width() / 2.0f;
    }

    default public float centerY() {
        return this.y() + this.height() / 2.0f;
    }

    default public boolean contains(double px, double py) {
        return px >= (double)this.minX() && px < (double)this.maxX() && py >= (double)this.minY() && py < (double)this.maxY();
    }

    default public boolean intersects(Rect2f other) {
        return this.minX() < other.maxX() && this.maxX() > other.minX() && this.minY() < other.maxY() && this.maxY() > other.minY();
    }

    default public boolean isIdentity() {
        return this.x() == 0.0f && this.y() == 0.0f && this.width() == 0.0f && this.height() == 0.0f;
    }

    public static Mutable of() {
        return new Mutable();
    }

    public static Mutable of(float x, float y, float width, float height) {
        return new Mutable(x, y, width, height);
    }

    public static Rect2f ofImmutable(float x, float y, float width, float height) {
        return new Immutable(x, y, width, height);
    }

    public static class Mutable
    implements Rect2f {
        public float x;
        public float y;
        public float width;
        public float height;

        public Mutable() {
            this(0.0f, 0.0f, 0.0f, 0.0f);
        }

        public Mutable(float x, float y, float width, float height) {
            this.set(x, y, width, height);
        }

        public Mutable set(float x, float y, float width, float height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            return this;
        }

        @Override
        public Mutable intersect(Rect2f other) {
            float x1 = Math.max(this.x, other.minX());
            float y1 = Math.max(this.y, other.minY());
            float x2 = Math.min(this.x + this.width, other.maxX());
            float y2 = Math.min(this.y + this.height, other.maxY());
            this.x = x1;
            this.y = y1;
            this.width = Math.max(0.0f, x2 - x1);
            this.height = Math.max(0.0f, y2 - y1);
            return this;
        }

        public Mutable set(Rect2f other) {
            return this.set(other.x(), other.y(), other.width(), other.height());
        }

        public Mutable setPosition(float x, float y) {
            this.x = x;
            this.y = y;
            return this;
        }

        public Mutable setSize(float width, float height) {
            this.width = width;
            this.height = height;
            return this;
        }

        public Mutable translate(float dx, float dy) {
            this.x += dx;
            this.y += dy;
            return this;
        }

        public Mutable inflate(float dx, float dy) {
            this.x -= dx;
            this.y -= dy;
            this.width += dx * 2.0f;
            this.height += dy * 2.0f;
            return this;
        }

        public Mutable inflate(float amount) {
            return this.inflate(amount, amount);
        }

        @Override
        public float x() {
            return this.x;
        }

        @Override
        public float y() {
            return this.y;
        }

        @Override
        public float width() {
            return this.width;
        }

        @Override
        public float height() {
            return this.height;
        }
    }

    public record Immutable(float x, float y, float width, float height) implements Rect2f
    {
    }
}

