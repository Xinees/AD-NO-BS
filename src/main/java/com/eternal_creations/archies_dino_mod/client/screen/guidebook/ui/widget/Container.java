/*
 * Decompiled with CFR 0.152.
 */
package com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.widget;

import com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.widget.AbstractWidget;

public class Container
extends AbstractWidget {
    protected Container(Builder builder) {
        super(builder);
    }

    public Container(float x, float y, float width, float height) {
        super(x, y, width, height);
    }

    public static Container of(float x, float y, float width, float height) {
        return new Container(x, y, width, height);
    }

    public static Builder builder(float x, float y, float width, float height) {
        return new Builder(x, y, width, height);
    }

    public static class Builder
    extends AbstractWidget.Builder<Builder, Container> {
        public Builder(float x, float y, float width, float height) {
            super(x, y, width, height);
        }

        @Override
        public Container build() {
            return new Container(this);
        }
    }
}

