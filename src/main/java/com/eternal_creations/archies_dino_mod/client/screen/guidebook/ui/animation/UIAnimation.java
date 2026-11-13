/*
 * Decompiled with CFR 0.152.
 */
package com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.animation;

import com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.misc.Rect2f;
import com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.render.RenderState;

public interface UIAnimation {
    public void tick();

    public void apply(RenderState var1, Rect2f var2, float var3);

    public boolean isFinished(float var1);
}

