/*
 * Decompiled with CFR 0.152.
 */
package com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.misc;

public interface Color4f {
    public static final Color4f WHITE = Color4f.ofImmutable(1.0f, 1.0f, 1.0f, 1.0f);
    public static final Color4f BLACK = Color4f.ofImmutable(0.0f, 0.0f, 0.0f, 1.0f);
    public static final Color4f TRANSPARENT = Color4f.ofImmutable(0.0f, 0.0f, 0.0f, 0.0f);

    public static Mutable of() {
        return new Mutable();
    }

    public static Mutable of(float r, float g, float b, float a) {
        return new Mutable(r, g, b, a);
    }

    public static Mutable fromARGB(int argb) {
        return new Mutable().setFromARGB(argb);
    }

    public static Color4f ofImmutable(float r, float g, float b, float a) {
        return new Immutable(r, g, b, a);
    }

    public float r();

    public float g();

    public float b();

    public float a();

    default public int toARGB() {
        int ia = (int)(this.a() * 255.0f) & 0xFF;
        int ir = (int)(this.r() * 255.0f) & 0xFF;
        int ig = (int)(this.g() * 255.0f) & 0xFF;
        int ib = (int)(this.b() * 255.0f) & 0xFF;
        return ia << 24 | ir << 16 | ig << 8 | ib;
    }

    default public boolean isIdentity() {
        return this.a() == 1.0f && this.r() == 1.0f && this.g() == 1.0f && this.b() == 1.0f;
    }

    public static class Mutable
    implements Color4f {
        public float r = 1.0f;
        public float g = 1.0f;
        public float b = 1.0f;
        public float a = 1.0f;

        public Mutable() {
            this(1.0f, 1.0f, 1.0f, 1.0f);
        }

        public Mutable(float r, float g, float b, float a) {
            this.set(r, g, b, a);
        }

        public Mutable set(float r, float g, float b, float a) {
            this.r = r;
            this.g = g;
            this.b = b;
            this.a = a;
            return this;
        }

        public Mutable set(Color4f other) {
            return this.set(other.r(), other.g(), other.b(), other.a());
        }

        public Mutable setRGB(float r, float g, float b) {
            this.r = r;
            this.g = g;
            this.b = b;
            return this;
        }

        public Mutable setAlpha(float a) {
            this.a = a;
            return this;
        }

        public Mutable setFromARGB(int argb) {
            this.a = (float)(argb >> 24 & 0xFF) / 255.0f;
            this.r = (float)(argb >> 16 & 0xFF) / 255.0f;
            this.g = (float)(argb >> 8 & 0xFF) / 255.0f;
            this.b = (float)(argb & 0xFF) / 255.0f;
            return this;
        }

        public Mutable lerp(Color4f target, float delta) {
            this.r += (target.r() - this.r) * delta;
            this.g += (target.g() - this.g) * delta;
            this.b += (target.b() - this.b) * delta;
            this.a += (target.a() - this.a) * delta;
            return this;
        }

        public Mutable multiplyAlpha(float a) {
            this.a *= a;
            return this;
        }

        public Mutable multiplyRGB(float r, float g, float b) {
            this.r *= r;
            this.g *= g;
            this.b *= b;
            return this;
        }

        public Mutable multiply(Color4f other) {
            this.r *= other.r();
            this.g *= other.g();
            this.b *= other.b();
            this.a *= other.a();
            return this;
        }

        public Mutable reset() {
            this.r = 1.0f;
            this.g = 1.0f;
            this.b = 1.0f;
            this.a = 1.0f;
            return this;
        }

        @Override
        public float r() {
            return this.r;
        }

        @Override
        public float g() {
            return this.g;
        }

        @Override
        public float b() {
            return this.b;
        }

        @Override
        public float a() {
            return this.a;
        }
    }

    public record Immutable(float r, float g, float b, float a) implements Color4f
    {
    }
}

