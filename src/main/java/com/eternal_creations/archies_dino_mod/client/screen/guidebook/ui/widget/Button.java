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

public class Button
extends AbstractWidget {
    private final Sprite normalSprite;
    private final Sprite hoveredSprite;

    protected Button(Builder builder) {
        super(builder);
        this.normalSprite = builder.normalSprite;
        this.hoveredSprite = builder.hoveredSprite;
    }

    @Override
    protected void renderContent(UIContext ctx, RenderState state) {
        Sprite sprite = this.isHovered(ctx) && this.hoveredSprite != null ? this.hoveredSprite : this.normalSprite;
        ctx.drawSprite(sprite, 0.0f, 0.0f, this.bounds.width(), this.bounds.height());
    }

    public static Builder builder(float x, float y, float width, float height) {
        return new Builder(x, y, width, height);
    }

    public static class Builder
    extends AbstractWidget.Builder<Builder, Button> {
        private Sprite normalSprite;
        private Sprite hoveredSprite;

        public Builder(float x, float y, float width, float height) {
            super(x, y, width, height);
        }

        public Builder sprites(Sprite normal, Sprite hovered) {
            this.normalSprite = normal;
            this.hoveredSprite = hovered;
            return this;
        }

        public Builder sprites(ResourceLocation normalSheet, ResourceLocation hoveredSheet, int sheetW, int sheetH) {
            return this.sprites(normalSheet, hoveredSheet, this.x, this.y, sheetW, sheetH);
        }

        public Builder sprites(ResourceLocation normal, ResourceLocation hovered) {
            this.normalSprite = Sprite.of(normal, this.width, this.height);
            this.hoveredSprite = Sprite.of(hovered, this.width, this.height);
            return this;
        }

        public Builder sprites(ResourceLocation normalSheet, ResourceLocation hoveredSheet, float u, float v, int sheetW, int sheetH) {
            this.normalSprite = Sprite.of(normalSheet, u, v, this.width, this.height, sheetW, sheetH);
            this.hoveredSprite = Sprite.of(hoveredSheet, u, v, this.width, this.height, sheetW, sheetH);
            return this;
        }

        @Override
        public Button build() {
            return new Button(this);
        }
    }
}

