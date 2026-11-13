/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 */
package com.eternal_creations.archies_dino_mod.client.screen.guidebook.book;

import com.eternal_creations.archies_dino_mod.client.screen.guidebook.data.CategoryData;
import com.eternal_creations.archies_dino_mod.client.screen.guidebook.data.PageData;
import com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.widget.AbstractWidget;
import net.minecraft.resources.ResourceLocation;

public class PageSpread
extends AbstractWidget {
    private final CategoryData category;
    private final PageData page;

    public PageSpread(CategoryData category, PageData page, float width, float height) {
        super(0.0f, 0.0f, width, height);
        this.category = category;
        this.page = page;
    }

    public CategoryData category() {
        return this.category;
    }

    public PageData page() {
        return this.page;
    }

    public ResourceLocation background() {
        return this.category.background();
    }

    public ResourceLocation info() {
        return this.page.info();
    }
}

