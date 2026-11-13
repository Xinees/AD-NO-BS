/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.sounds.SoundEvent
 */
package com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.widget;

import com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.UIContext;
import com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.UIElement;
import com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.animation.AppearAnimation;
import com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.animation.ClickAnimation;
import com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.animation.HoverAnimation;
import com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.misc.Rect2f;
import com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.render.RenderState;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import net.minecraft.sounds.SoundEvent;

public abstract class AbstractWidget
implements UIElement {
    protected final Rect2f.Mutable bounds = Rect2f.of();
    protected final Rect2f.Mutable hitbox = Rect2f.of();
    protected final RenderState renderState;
    protected final AppearAnimation appearAnimation;
    protected final HoverAnimation hoverAnimation;
    protected final ClickAnimation clickAnimation;
    protected final ClickListener clickListener;
    protected final SoundEvent clickSound;
    protected final boolean absolute;
    protected final int zIndex;
    protected boolean visible = true;
    protected boolean hovered = false;
    protected List<UIElement> children = null;
    private boolean needsSort = false;

    protected AbstractWidget(Builder<?, ?> builder) {
        this.bounds.set(builder.x, builder.y, builder.width, builder.height);
        this.hitbox.set(builder.x + builder.hitboxOffsetX, builder.y + builder.hitboxOffsetY, builder.hitboxWidth != null ? builder.hitboxWidth.floatValue() : builder.width, builder.hitboxHeight != null ? builder.hitboxHeight.floatValue() : builder.height);
        this.zIndex = builder.zIndex;
        this.absolute = builder.absolute;
        this.appearAnimation = builder.appearAnimation;
        this.hoverAnimation = builder.hoverAnimation;
        this.clickAnimation = builder.clickAnimation;
        this.clickListener = builder.clickListener;
        this.clickSound = builder.clickSound;
        this.renderState = RenderState.of();
        this.appearAnimation.show();
    }

    protected AbstractWidget(float x, float y, float width, float height) {
        this.bounds.set(x, y, width, height);
        this.hitbox.set(x, y, width, height);
        this.zIndex = 0;
        this.absolute = false;
        this.appearAnimation = AppearAnimation.NONE;
        this.hoverAnimation = HoverAnimation.NONE;
        this.clickAnimation = ClickAnimation.NONE;
        this.clickListener = ClickListener.NONE;
        this.clickSound = null;
        this.renderState = RenderState.of();
        this.appearAnimation.show();
    }

    @Override
    public void tick() {
        this.appearAnimation.tick();
        this.hoverAnimation.tick();
        this.clickAnimation.tick();
        if (this.children != null) {
            for (UIElement child : this.children) {
                child.tick();
            }
        }
    }

    @Override
    public void render(UIContext ctx) {
        float pt = ctx.partialTick();
        if (!this.isRenderable(pt)) {
            return;
        }
        this.renderState.reset();
        this.updateHoverStatus(ctx);
        this.appearAnimation.apply(this.renderState, this.bounds, pt);
        this.hoverAnimation.apply(this.renderState, this.bounds, pt);
        this.clickAnimation.apply(this.renderState, this.bounds, pt);
        this.applyStateModification(ctx, this.renderState);
        float offsetX = this.absolute ? this.bounds.x() - (float)ctx.currentOffsetX() : this.bounds.x();
        float offsetY = this.absolute ? this.bounds.y() - (float)ctx.currentOffsetY() : this.bounds.y();
        ctx.pushOffset(offsetX, offsetY);
        ctx.pushState(this.renderState, this.bounds);
        this.renderContent(ctx, this.renderState);
        this.renderChildren(ctx);
        ctx.popState();
        ctx.popOffset();
    }

    private void updateHoverStatus(UIContext ctx) {
        boolean hovered;
        float pt = ctx.partialTick();
        float mx = (float)ctx.mouseX() - (this.absolute ? 0.0f : (float)ctx.currentOffsetX());
        float my = (float)ctx.mouseY() - (this.absolute ? 0.0f : (float)ctx.currentOffsetY());
        boolean bl = hovered = this.visible && this.appearAnimation.isFinished(pt) && this.hitbox.contains(mx, my);
        if (this.hovered != hovered) {
            this.hovered = hovered;
            this.hoverAnimation.updateHoverStatus(hovered);
        }
    }

    public void applyStateModification(UIContext ctx, RenderState renderState) {
    }

    protected void renderContent(UIContext ctx, RenderState state) {
    }

    protected void renderChildren(UIContext ctx) {
        if (this.children == null || this.children.isEmpty()) {
            return;
        }
        if (this.needsSort) {
            this.children.sort(Comparator.comparingInt(UIElement::zIndex));
            this.needsSort = false;
        }
        for (UIElement child : this.children) {
            child.render(ctx);
        }
    }

    protected boolean isRenderable(float pt) {
        if (this.visible || !this.appearAnimation.isFinished(pt)) {
            return true;
        }
        if (this.children != null) {
            for (UIElement child : this.children) {
                AbstractWidget ui;
                if (!(child instanceof AbstractWidget) || !(ui = (AbstractWidget)child).isRenderable(pt)) continue;
                return true;
            }
        }
        return false;
    }

    @Override
    public void close() {
        if (this.children != null) {
            for (UIElement child : this.children) {
                child.close();
            }
        }
    }

    @Override
    public boolean mouseClicked(UIContext ctx, int button) {
        if (!this.visible) {
            return false;
        }
        float ox = this.absolute ? this.bounds.x() - (float)ctx.currentOffsetX() : this.bounds.x();
        float oy = this.absolute ? this.bounds.y() - (float)ctx.currentOffsetY() : this.bounds.y();
        ctx.pushOffset(ox, oy);
        if (this.children != null) {
            for (int i = this.children.size() - 1; i >= 0; --i) {
                if (!this.children.get(i).mouseClicked(ctx, button)) continue;
                ctx.popOffset();
                return true;
            }
        }
        ctx.popOffset();
        if (button == 0 && this.isHovered(ctx) && this.clickListener != null && this.clickListener.onClick(ctx, this)) {
            if (this.clickSound != null) {
                ctx.playSound(this.clickSound);
            }
            this.clickAnimation.trigger();
            return true;
        }
        return false;
    }

    @Override
    public boolean mouseReleased(UIContext ctx, int button) {
        if (!this.visible) {
            return false;
        }
        float ox = this.absolute ? this.bounds.x() - (float)ctx.currentOffsetX() : this.bounds.x();
        float oy = this.absolute ? this.bounds.y() - (float)ctx.currentOffsetY() : this.bounds.y();
        ctx.pushOffset(ox, oy);
        if (this.children != null) {
            for (int i = this.children.size() - 1; i >= 0; --i) {
                if (!this.children.get(i).mouseReleased(ctx, button)) continue;
                ctx.popOffset();
                return true;
            }
        }
        ctx.popOffset();
        return false;
    }

    public <T extends UIElement> T addChild(T child) {
        if (this.children == null) {
            this.children = new ArrayList<UIElement>();
        }
        this.children.add(child);
        this.needsSort = true;
        if (!this.visible && child instanceof AbstractWidget) {
            AbstractWidget ui = (AbstractWidget)child;
            ui.setVisible(false);
        }
        return child;
    }

    public void removeChild(UIElement child) {
        if (this.children != null) {
            this.children.remove(child);
        }
    }

    public void clear() {
        if (this.children != null) {
            this.children.clear();
        }
    }

    public List<UIElement> getChildren() {
        return this.children != null ? this.children : Collections.emptyList();
    }

    @Override
    public int zIndex() {
        return this.zIndex;
    }

    public void setVisible(boolean visible) {
        if (this.visible != visible) {
            this.visible = visible;
            this.appearAnimation.updateVisibilityStatus(visible);
        }
        if (this.children != null) {
            for (UIElement child : this.children) {
                if (!(child instanceof AbstractWidget)) continue;
                AbstractWidget ui = (AbstractWidget)child;
                ui.setVisible(visible);
            }
        }
    }

    public boolean isVisible() {
        return this.visible;
    }

    public RenderState renderState() {
        return this.renderState;
    }

    @Override
    public Rect2f bounds() {
        return this.bounds;
    }

    @Override
    public boolean isHovered(UIContext ctx) {
        return this.hovered;
    }

    public static abstract class Builder<B extends Builder<B, T>, T extends AbstractWidget> {
        protected final float x;
        protected final float y;
        protected final float width;
        protected final float height;
        protected float hitboxOffsetX = 0.0f;
        protected float hitboxOffsetY = 0.0f;
        protected Float hitboxWidth = null;
        protected Float hitboxHeight = null;
        protected int zIndex = 0;
        protected AppearAnimation appearAnimation = AppearAnimation.NONE;
        protected HoverAnimation hoverAnimation = HoverAnimation.NONE;
        protected ClickAnimation clickAnimation = ClickAnimation.NONE;
        protected SoundEvent clickSound;
        protected ClickListener clickListener = ClickListener.NONE;
        protected boolean absolute = false;

        public Builder(float x, float y, float width, float height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }

        protected B self() {
            return (B)this;
        }

        public B hitbox(Rect2f hitbox) {
            this.hitboxOffsetX = hitbox.x();
            this.hitboxOffsetY = hitbox.y();
            this.hitboxWidth = Float.valueOf(hitbox.width());
            this.hitboxHeight = Float.valueOf(hitbox.height());
            return this.self();
        }

        public B hitbox(float offX, float offY, float w, float h) {
            this.hitboxOffsetX = offX;
            this.hitboxOffsetY = offY;
            this.hitboxWidth = Float.valueOf(w);
            this.hitboxHeight = Float.valueOf(h);
            return this.self();
        }

        public B zIndex(int zIndex) {
            this.zIndex = zIndex;
            return this.self();
        }

        public B sound(SoundEvent sound) {
            this.clickSound = sound;
            return this.self();
        }

        public B absolute(boolean absolute) {
            this.absolute = absolute;
            return this.self();
        }

        public B appearAnimation(AppearAnimation animation) {
            this.appearAnimation = animation;
            return this.self();
        }

        public B hoverAnimation(HoverAnimation animation) {
            this.hoverAnimation = animation;
            return this.self();
        }

        public B clickAnimation(ClickAnimation animation) {
            this.clickAnimation = animation;
            return this.self();
        }

        public B clicked(ClickListener onClick) {
            this.clickListener = onClick != null ? onClick : ClickListener.NONE;
            return this.self();
        }

        public abstract T build();
    }

    @FunctionalInterface
    public static interface ClickListener {
        public static final ClickListener NONE = (ctx, clicked) -> false;

        public boolean onClick(UIContext var1, UIElement var2);
    }
}

