/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 */
package com.eternal_creations.archies_dino_mod.client.screen.guidebook.data;

import com.eternal_creations.archies_dino_mod.client.screen.guidebook.data.PageData;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.resources.ResourceLocation;

public class CategoryData {
    private final String id;
    private final ResourceLocation background;
    private final ResourceLocation markerOn;
    private final ResourceLocation markerOff;
    private final List<PageData> pages = new ArrayList<PageData>();
    private boolean disableMarkers;

    public CategoryData(String id, ResourceLocation background, ResourceLocation markerOn, ResourceLocation markerOff) {
        this.id = id;
        this.background = background;
        this.markerOn = markerOn;
        this.markerOff = markerOff;
    }

    public CategoryData disableMarkers() {
        this.disableMarkers = true;
        return this;
    }

    public void addPage(PageData pageData) {
        this.pages.add(pageData);
    }

    public String id() {
        return this.id;
    }

    public ResourceLocation background() {
        return this.background;
    }

    public ResourceLocation markerOn() {
        return this.markerOn;
    }

    public ResourceLocation markerOff() {
        return this.markerOff;
    }

    public boolean hasNoMarkers() {
        return this.disableMarkers;
    }

    public List<PageData> pages() {
        return this.pages;
    }

    public int getPageCount() {
        return this.pages.size();
    }
}

