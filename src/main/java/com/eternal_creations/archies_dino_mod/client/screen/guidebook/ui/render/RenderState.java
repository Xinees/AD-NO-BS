/*
 * Decompiled with CFR 0.152.
 */
package com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.render;

import com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.misc.Color4f;
import com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.misc.Crop;
import com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.misc.TransformStack;

public class RenderState {
    public static final RenderState EMPTY = new RenderState();
    private final TransformStack transform = new TransformStack();
    private final Color4f.Mutable color = Color4f.of();
    private final Crop.Mutable crop = Crop.of();

    public void reset() {
        this.transform.reset();
        this.color.reset();
        this.crop.reset();
    }

    public TransformStack transform() {
        return this.transform;
    }

    public Color4f.Mutable color() {
        return this.color;
    }

    public Crop.Mutable crop() {
        return this.crop;
    }

    public static RenderState of() {
        return new RenderState();
    }
}

