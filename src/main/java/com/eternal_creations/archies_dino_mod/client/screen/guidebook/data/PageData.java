/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 */
package com.eternal_creations.archies_dino_mod.client.screen.guidebook.data;

import com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.UIElement;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.resources.ResourceLocation;

public class PageData {
    private final ResourceLocation info;
    private final List<UIElement.Factory> factories = new ArrayList<UIElement.Factory>();

    public PageData(ResourceLocation info) {
        this.info = info;
    }

    public void addElementFactory(UIElement.Factory factory) {
        this.factories.add(factory);
    }

    public ResourceLocation info() {
        return this.info;
    }

    public List<UIElement.Factory> factories() {
        return this.factories;
    }
}

