/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.Util
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.screens.ConfirmLinkScreen
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.network.chat.ClickEvent
 *  net.minecraft.network.chat.ClickEvent$Action
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.FormattedText
 *  net.minecraft.network.chat.Style
 *  net.minecraft.network.chat.TextColor
 *  net.minecraft.util.FormattedCharSequence
 */
package com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.widget;

import com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.UIContext;
import com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.render.RenderState;
import com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.widget.AbstractWidget;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.ConfirmLinkScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.util.FormattedCharSequence;

public class TextLabel
extends AbstractWidget {
    private Component text;
    private final Alignment alignment;
    private final int defaultColor;
    private final int lineSpacing;
    private final boolean shadow;
    private final boolean autoHeight;
    private final float scaleX;
    private final float scaleY;
    private final float initialWidth;
    private final List<FormattedCharSequence> cachedLines = new ArrayList<FormattedCharSequence>();
    private boolean dirty = true;

    protected TextLabel(Builder builder) {
        super(builder);
        this.text = builder.text;
        this.defaultColor = builder.defaultColor;
        this.shadow = builder.shadow;
        this.alignment = builder.alignment;
        this.lineSpacing = builder.lineSpacing;
        this.autoHeight = builder.autoHeight;
        this.scaleX = builder.scaleX;
        this.scaleY = builder.scaleY;
        this.initialWidth = builder.width;
    }

    public void setText(Component text) {
        this.text = text;
        this.dirty = true;
    }

    public void setText(String text) {
        this.setText((Component)Component.m_237113_((String)text));
    }

    public void setWidth(float width) {
        if (this.bounds.width() != width) {
            this.bounds.setSize(width, this.bounds.height());
            this.dirty = true;
        }
    }

    @Override
    protected void renderContent(UIContext ctx, RenderState state) {
        if (this.text == null) {
            return;
        }
        Font font = ctx.font();
        if (this.dirty) {
            this.rebuildLines(font);
            this.dirty = false;
        }
        if (this.cachedLines.isEmpty()) {
            return;
        }
        Style hoveredStyle = this.getStyleAt(font, ctx.mouseX(), ctx.mouseY());
        ClickEvent hoveredClick = hoveredStyle != null ? hoveredStyle.m_131182_() : null;
        PoseStack pose = ctx.pose();
        pose.m_85836_();
        if (this.scaleX != 1.0f || this.scaleY != 1.0f) {
            pose.m_85841_(this.scaleX, this.scaleY, 1.0f);
        }
        int currentY = 0;
        for (FormattedCharSequence line : this.cachedLines) {
            int lineWidth = font.m_92724_(line);
            int currentX = this.getLineStartX(lineWidth);
            FormattedCharSequence lineToRender = hoveredClick != null ? this.applyHoverColor(line, hoveredClick) : line;
            ctx.drawText(lineToRender, currentX, currentY, this.defaultColor, this.shadow);
            Objects.requireNonNull(font);
            currentY += 9 + this.lineSpacing;
        }
        pose.m_85849_();
    }

    private FormattedCharSequence applyHoverColor(FormattedCharSequence line, ClickEvent targetClick) {
        return sink -> line.m_13731_((index, style, codePoint) -> {
            ClickEvent click = style.m_131182_();
            if (click != null && click.m_130623_().equals(targetClick.m_130623_())) {
                TextColor baseColor = style.m_131135_();
                int rgb = baseColor != null ? baseColor.m_131265_() : this.defaultColor & 0xFFFFFF;
                int highlightedRgb = this.blendColors(rgb, 59391, 0.45f);
                return sink.m_6411_(index, style.m_131148_(TextColor.m_131266_((int)highlightedRgb)), codePoint);
            }
            return sink.m_6411_(index, style, codePoint);
        });
    }

    @Override
    public boolean mouseClicked(UIContext ctx, int button) {
        if (button == 0 && this.visible) {
            ClickEvent click;
            double localX = ctx.mouseX() - (double)this.bounds.x();
            double localY = ctx.mouseY() - (double)this.bounds.y();
            Style style = this.getStyleAt(ctx.font(), localX, localY);
            if (style != null && style.m_131182_() != null && (click = style.m_131182_()).m_130622_() == ClickEvent.Action.OPEN_URL) {
                String url = click.m_130623_();
                Minecraft mc = Minecraft.m_91087_();
                mc.m_91152_((Screen)new ConfirmLinkScreen(confirmed -> {
                    if (confirmed) {
                        Util.m_137581_().m_137646_(url);
                    }
                    mc.m_91152_(null);
                }, url, true));
                return true;
            }
        }
        return super.mouseClicked(ctx, button);
    }

    public Style getStyleAt(Font font, double localX, double localY) {
        int lineIndex;
        double scaledX;
        block9: {
            block8: {
                if (this.dirty) {
                    this.rebuildLines(font);
                    this.dirty = false;
                }
                if (this.cachedLines.isEmpty()) {
                    return null;
                }
                scaledX = localX / (double)this.scaleX;
                double scaledY = localY / (double)this.scaleY;
                if (scaledY < 0.0) {
                    return null;
                }
                Objects.requireNonNull(font);
                int lineStep = 9 + this.lineSpacing;
                lineIndex = (int)(scaledY / (double)lineStep);
                if (lineIndex < 0 || lineIndex >= this.cachedLines.size()) {
                    return null;
                }
                double yInLine = scaledY - (double)(lineIndex * lineStep);
                if (yInLine < 0.0) break block8;
                Objects.requireNonNull(font);
                if (!(yInLine >= 9.0)) break block9;
            }
            return null;
        }
        FormattedCharSequence line = this.cachedLines.get(lineIndex);
        int lineWidth = font.m_92724_(line);
        int startX = this.getLineStartX(lineWidth);
        int charX = (int)(scaledX - (double)startX);
        if (charX >= 0 && charX <= lineWidth) {
            return font.m_92865_().m_92338_(line, charX);
        }
        return null;
    }

    private int getLineStartX(int lineWidth) {
        if (this.initialWidth > 0.0f) {
            int maxWidth = (int)(this.initialWidth / this.scaleX);
            if (this.alignment == Alignment.CENTER) {
                return (maxWidth - lineWidth) / 2;
            }
            if (this.alignment == Alignment.RIGHT) {
                return maxWidth - lineWidth;
            }
            return 0;
        }
        if (this.alignment == Alignment.CENTER) {
            return -lineWidth / 2;
        }
        if (this.alignment == Alignment.RIGHT) {
            return -lineWidth;
        }
        return 0;
    }

    private int blendColors(int baseColor, int targetColor, float ratio) {
        int r1 = baseColor >> 16 & 0xFF;
        int g1 = baseColor >> 8 & 0xFF;
        int b1 = baseColor & 0xFF;
        int r2 = targetColor >> 16 & 0xFF;
        int g2 = targetColor >> 8 & 0xFF;
        int b2 = targetColor & 0xFF;
        int r = (int)((float)r1 + (float)(r2 - r1) * ratio);
        int g = (int)((float)g1 + (float)(g2 - g1) * ratio);
        int b = (int)((float)b1 + (float)(b2 - b1) * ratio);
        return r << 16 | g << 8 | b;
    }

    private void rebuildLines(Font font) {
        this.cachedLines.clear();
        if (this.text == null) {
            return;
        }
        int effectiveWidth = (int)(this.initialWidth / this.scaleX);
        if (effectiveWidth <= 0) {
            this.cachedLines.addAll(font.m_92923_((FormattedText)this.text, Integer.MAX_VALUE));
        } else {
            this.cachedLines.addAll(font.m_92923_((FormattedText)this.text, effectiveWidth));
        }
        int maxLineWidth = 0;
        for (FormattedCharSequence line : this.cachedLines) {
            maxLineWidth = Math.max(maxLineWidth, font.m_92724_(line));
        }
        int n = this.cachedLines.size();
        Objects.requireNonNull(font);
        float totalHeight = (float)(n * 9 + Math.max(0, this.cachedLines.size() - 1) * this.lineSpacing) * this.scaleY;
        float width = this.initialWidth > 0.0f ? this.initialWidth : (float)maxLineWidth * this.scaleX;
        this.bounds.setSize(width, this.autoHeight ? totalHeight : this.bounds.height());
        this.hitbox.setSize(width, this.bounds.height());
    }

    public static Builder builder(Component text, float x, float y, float maxWidth) {
        return new Builder(text, x, y, maxWidth);
    }

    public static Builder builder(String text, float x, float y, float maxWidth) {
        return new Builder((Component)Component.m_237113_((String)text), x, y, maxWidth);
    }

    public static Builder builder(Component text, float x, float y) {
        return new Builder(text, x, y, 0.0f).autoHeight(false);
    }

    public static Builder builder(String text, float x, float y) {
        return new Builder((Component)Component.m_237113_((String)text), x, y, 0.0f).autoHeight(false);
    }

    public static class Builder
    extends AbstractWidget.Builder<Builder, TextLabel> {
        private final Component text;
        private Alignment alignment = Alignment.LEFT;
        private int lineSpacing = 2;
        private int defaultColor = -12632257;
        private boolean shadow = false;
        private boolean autoHeight = true;
        private float scaleX = 1.0f;
        private float scaleY = 1.0f;

        public Builder(Component text, float x, float y, float width) {
            super(x, y, width, 10.0f);
            this.text = text;
        }

        public Builder color(int color) {
            this.defaultColor = color;
            return this;
        }

        public Builder scale(float scale) {
            this.scaleX = scale;
            this.scaleY = scale;
            return this;
        }

        public Builder scale(float scaleX, float scaleY) {
            this.scaleX = scaleX;
            this.scaleY = scaleY;
            return this;
        }

        public Builder shadow(boolean shadow) {
            this.shadow = shadow;
            return this;
        }

        public Builder align(Alignment alignment) {
            this.alignment = alignment;
            return this;
        }

        public Builder lineSpacing(int spacing) {
            this.lineSpacing = spacing;
            return this;
        }

        public Builder autoHeight(boolean autoHeight) {
            this.autoHeight = autoHeight;
            return this;
        }

        @Override
        public TextLabel build() {
            return new TextLabel(this);
        }
    }

    public static enum Alignment {
        LEFT,
        CENTER,
        RIGHT;

    }
}

