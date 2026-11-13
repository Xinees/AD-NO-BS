/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.astemir.astemirlib.re.core.math.Easing
 *  com.astemir.astemirlib.re.core.math.Easing$Simple
 *  com.astemir.astemirlib.re.core.math.tween.TweenFloat
 *  com.mojang.blaze3d.systems.RenderSystem
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.resources.sounds.SimpleSoundInstance
 *  net.minecraft.client.resources.sounds.SoundInstance
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundEvents
 */
package com.eternal_creations.archies_dino_mod.client.screen.guidebook.book;

import com.astemir.astemirlib.re.core.math.Easing;
import com.astemir.astemirlib.re.core.math.tween.TweenFloat;
import com.eternal_creations.archies_dino_mod.client.screen.guidebook.book.PageSpread;
import com.eternal_creations.archies_dino_mod.client.screen.guidebook.data.CategoryData;
import com.eternal_creations.archies_dino_mod.client.screen.guidebook.data.PageData;
import com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.UIContext;
import com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.render.PageBuffer;
import com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.render.PageCurlMesh;
import com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.widget.AbstractWidget;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;

public class AnimatedBookFlipper
extends AbstractWidget {
    private final List<PageSpread> spreads = new ArrayList<PageSpread>();
    private FlipTarget current = new FlipTarget.Real(0);
    private FlipTarget step = new FlipTarget.Real(0);
    private FlipTarget target = new FlipTarget.Real(0);
    private int lastRealIndex = 0;
    private final TweenFloat flipAnimation = TweenFloat.of();
    private boolean isFlipping = false;
    private boolean flipForward = true;
    private BiConsumer<CategoryData, PageData> spreadChanged;
    private int soundCooldown = 0;
    private final PageCurlMesh curlMesh = new PageCurlMesh();
    private final PageBuffer srcBuffer = new PageBuffer();
    private final PageBuffer dstBuffer = new PageBuffer();

    protected AnimatedBookFlipper(float width, float height) {
        super(0.0f, 0.0f, width, height);
    }

    /*
     * Unable to fully structure code
     */
    @Override
    public void tick() {
        this.flipAnimation.tick();
        if (this.soundCooldown > 0) {
            --this.soundCooldown;
        }

        if (this.isFlipping && this.flipAnimation.isFinished()) {
            this.current = this.step;
            if (this.current instanceof FlipTarget.Real currentReal) {
                this.lastRealIndex = currentReal.index();
            }

            if (this.current instanceof FlipTarget.Real currentReal
                    && this.target instanceof FlipTarget.Real targetReal) {
                if (currentReal.index() != targetReal.index()) {
                    int remaining = Math.abs(targetReal.index() - currentReal.index());
                    boolean forward = targetReal.index() > currentReal.index();
                    int stepAmount = remaining > 3 ? 2 : 1;
                    int next = currentReal.index() + (forward ? stepAmount : -stepAmount);
                    next = forward ? Math.min(next, targetReal.index()) : Math.max(next, targetReal.index());
                    this.startStepFlip(new FlipTarget.Real(next));
                } else {
                    this.isFlipping = false;
                }
            } else {
                this.isFlipping = false;
            }
        }

        if (!this.isFlipping) {
            PageSpread spread = this.getCurrentSpread();
            if (spread != null) {
                spread.tick();
            }
        }
    }

    private void startStepFlip(FlipTarget.Real nextStep) {
        PageSpread target;
        float speed;
        Easing.Simple easing;
        boolean isStart;
        int n;
        int n2;
        FlipTarget flipTarget = this.current;
        if (flipTarget instanceof FlipTarget.Real) {
            FlipTarget.Real r = (FlipTarget.Real)flipTarget;
            n2 = r.index();
        } else {
            n2 = this.lastRealIndex;
        }
        int curIndex = n2;
        FlipTarget flipTarget2 = this.target;
        if (flipTarget2 instanceof FlipTarget.Real) {
            FlipTarget.Real r = (FlipTarget.Real)flipTarget2;
            n = r.index();
        } else {
            n = curIndex;
        }
        int tgtIndex = n;
        boolean forward = tgtIndex > curIndex;
        boolean isSingle = curIndex == tgtIndex || Math.abs(tgtIndex - curIndex) == 1 && nextStep.index() == tgtIndex;
        boolean isFinal = nextStep.index() == tgtIndex;
        boolean bl = isStart = this.flipAnimation.isFinished() && !this.isFlipping;
        if (isSingle) {
            easing = Easing.Simple.QUAD_IN_OUT;
            speed = 0.09f;
        } else if (isFinal) {
            easing = Easing.Simple.QUAD_OUT;
            speed = 0.16f;
        } else if (isStart) {
            easing = Easing.Simple.QUAD_IN;
            speed = 0.22f;
        } else {
            easing = Easing.Simple.LINEAR;
            speed = 0.62f;
        }
        if (isFinal && (target = this.getSpread(tgtIndex)) != null && this.spreadChanged != null) {
            this.spreadChanged.accept(target.category(), target.page());
        }
        this.beginFlip(nextStep, forward, speed, (Easing)easing);
    }

    private void beginFlip(FlipTarget nextStep, boolean forward, float speed, Easing easing) {
        this.step = nextStep;
        this.flipForward = forward;
        this.isFlipping = true;
        this.srcBuffer.markDirty();
        this.dstBuffer.markDirty();
        this.flipAnimation.setValue(0.0f);
        this.flipAnimation.setTarget(1.0f, speed, easing);
        this.playContinuousSound(speed);
    }

    private void playContinuousSound(float speed) {
        if (this.soundCooldown <= 0) {
            float pitch = 0.95f + speed * 1.2f + (float)(Math.random() * (double)0.15f);
            Minecraft.m_91087_().m_91106_().m_120367_((SoundInstance)SimpleSoundInstance.m_119755_((SoundEvent)SoundEvents.f_11713_, (float)pitch, (float)0.85f));
            this.soundCooldown = 2;
        }
    }

    public boolean flipToFake(PageSpread custom, boolean forward) {
        FlipTarget flipTarget;
        if (custom == null || this.isOnFakePage() || this.isEnteringFakePage()) {
            return false;
        }
        if (this.isFlipping && (flipTarget = (this.current = this.step)) instanceof FlipTarget.Real) {
            FlipTarget.Real r = (FlipTarget.Real)flipTarget;
            this.lastRealIndex = r.index();
        }
        if (this.spreadChanged != null) {
            this.spreadChanged.accept(null, null);
        }
        FlipTarget.Overlay overlay = new FlipTarget.Overlay(custom);
        this.target = overlay;
        this.beginFlip(overlay, forward, 0.16f, (Easing)Easing.Simple.QUAD_OUT);
        return true;
    }

    public boolean flipBackFromFake(boolean forward) {
        if (!this.isOnFakePage()) {
            return false;
        }
        PageSpread target = this.getSpread(this.lastRealIndex);
        if (target != null && this.spreadChanged != null) {
            this.spreadChanged.accept(target.category(), target.page());
        }
        FlipTarget.Real back = new FlipTarget.Real(this.lastRealIndex);
        this.target = back;
        this.beginFlip(back, forward, 0.16f, (Easing)Easing.Simple.QUAD_OUT);
        return true;
    }

    public boolean isOnFakePage() {
        return !this.isFlipping && this.current instanceof FlipTarget.Overlay;
    }

    public boolean isEnteringFakePage() {
        return this.isFlipping && this.step instanceof FlipTarget.Overlay;
    }

    public boolean flipToCategory(CategoryData category, int pageIndex) {
        int target = this.findSpreadIndex(category, pageIndex);
        return target != -1 && this.flipTo(target);
    }

    public boolean flipTo(int targetIndex) {
        FlipTarget.Real r;
        if (targetIndex < 0 || targetIndex >= this.spreads.size()) {
            return false;
        }
        FlipTarget flipTarget = this.target;
        if (flipTarget instanceof FlipTarget.Real && (r = (FlipTarget.Real)flipTarget).index() == targetIndex) {
            return false;
        }
        PageSpread curSpread = this.getCurrentSpread();
        PageSpread tgtSpread = this.getSpread(targetIndex);
        if (curSpread != null && tgtSpread != null) {
            boolean isDifferentCategory;
            boolean bl = isDifferentCategory = curSpread.category() != tgtSpread.category() && (curSpread.category() == null || !curSpread.category().equals(tgtSpread.category()));
            if (isDifferentCategory && this.spreadChanged != null) {
                this.spreadChanged.accept(null, null);
            }
        }
        this.target = new FlipTarget.Real(targetIndex);
        if (!this.isFlipping) {
            int n;
            FlipTarget flipTarget2 = this.current;
            if (flipTarget2 instanceof FlipTarget.Real) {
                FlipTarget.Real r2 = (FlipTarget.Real)flipTarget2;
                n = r2.index();
            } else {
                n = this.lastRealIndex;
            }
            int curIndex = n;
            int remaining = Math.abs(targetIndex - curIndex);
            int stepAmount = remaining > 3 ? 2 : 1;
            int next = curIndex + (targetIndex > curIndex ? stepAmount : -stepAmount);
            this.startStepFlip(new FlipTarget.Real(next));
        }
        return true;
    }

    public boolean nextPage() {
        if (this.hasNextPage()) {
            int n;
            FlipTarget flipTarget = this.target;
            if (flipTarget instanceof FlipTarget.Real) {
                FlipTarget.Real r = (FlipTarget.Real)flipTarget;
                n = r.index();
            } else {
                n = this.lastRealIndex;
            }
            int tgtIndex = n;
            return this.flipTo(tgtIndex + 1);
        }
        return false;
    }

    public boolean previousPage() {
        if (this.hasPreviousPage()) {
            int n;
            FlipTarget flipTarget = this.target;
            if (flipTarget instanceof FlipTarget.Real) {
                FlipTarget.Real r = (FlipTarget.Real)flipTarget;
                n = r.index();
            } else {
                n = this.lastRealIndex;
            }
            int tgtIndex = n;
            return this.flipTo(tgtIndex - 1);
        }
        return false;
    }

    public void spreadChanged(BiConsumer<CategoryData, PageData> spreadChanged) {
        this.spreadChanged = spreadChanged;
    }

    /*
     * Unable to fully structure code
     */
    @Override
    public void render(UIContext ctx) {
        float partialTick = ctx.partialTick();
        float width = this.bounds.width();
        float height = this.bounds.height();

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        if (this.isFlipping) {
            float progress = this.flipAnimation.get(partialTick);
            PageSpread source = this.resolve(this.current);
            PageSpread destination = this.resolve(this.step);
            this.srcBuffer.renderSpread(source, ctx, width, height);
            this.dstBuffer.renderSpread(destination, ctx, width, height);

            boolean isFast = false;
            if (this.current instanceof FlipTarget.Real currentReal
                    && this.target instanceof FlipTarget.Real targetReal) {
                isFast = Math.abs(targetReal.index() - currentReal.index()) > 1;
            }

            this.curlMesh.updateGeometry(width, height, progress, this.flipForward, isFast);
            this.curlMesh.renderMesh(
                    ctx.graphics(),
                    width,
                    height,
                    progress,
                    this.flipForward,
                    this.srcBuffer.getTextureId(),
                    this.dstBuffer.getTextureId()
            );
        } else {
            PageSpread spread = this.getCurrentSpread();
            if (spread != null) {
                this.renderStaticSpread(ctx.graphics(), width, height, spread);
                spread.render(ctx);
            }
        }

        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
    }

    private void renderStaticSpread(GuiGraphics graphics, float w, float h, PageSpread spread) {
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        if (spread.background() != null) {
            PageCurlMesh.drawFloatQuad(graphics, spread.background(), 0.0f, 0.0f, w, h, 0.0f, 0.0f, w, h, w, h);
        }
        if (spread.info() != null) {
            PageCurlMesh.drawFloatQuad(graphics, spread.info(), 0.0f, 0.0f, w, h, 0.0f, 0.0f, w, h, w, h);
        }
    }

    @Override
    public boolean mouseClicked(UIContext ctx, int button) {
        if (this.isFlipping) {
            return true;
        }
        PageSpread cur = this.getCurrentSpread();
        return cur != null && cur.mouseClicked(ctx, button);
    }

    public PageSpread createSpread(CategoryData category, PageData page) {
        PageSpread spread = new PageSpread(category, page, this.bounds.width(), this.bounds.height());
        this.spreads.add(spread);
        return spread;
    }

    public int findSpreadIndex(CategoryData category, int pageIndex) {
        int categoryPageIndex = 0;
        for (int i = 0; i < this.spreads.size(); ++i) {
            PageSpread spread = this.spreads.get(i);
            if (spread.category() != category && (spread.category() == null || !spread.category().equals(category))) continue;
            if (categoryPageIndex == pageIndex) {
                return i;
            }
            ++categoryPageIndex;
        }
        return -1;
    }

    public int getSpreadCount() {
        return this.spreads.size();
    }

    public PageSpread getSpread(int index) {
        return index >= 0 && index < this.spreads.size() ? this.spreads.get(index) : null;
    }

    public PageSpread getCurrentSpread() {
        return this.resolve(this.current);
    }

    private PageSpread resolve(FlipTarget t) {
        if (t instanceof FlipTarget.Real) {
            FlipTarget.Real r = (FlipTarget.Real)t;
            return this.getSpread(r.index());
        }
        if (t instanceof FlipTarget.Overlay) {
            FlipTarget.Overlay o = (FlipTarget.Overlay)t;
            return o.spread();
        }
        return null;
    }

    @Override
    public void close() {
        super.close();
        this.srcBuffer.destroy();
        this.dstBuffer.destroy();
    }

    public boolean hasNextPage() {
        FlipTarget.Real r;
        FlipTarget flipTarget = this.target;
        return flipTarget instanceof FlipTarget.Real && (r = (FlipTarget.Real)flipTarget).index() < this.spreads.size() - 1;
    }

    public boolean hasPreviousPage() {
        FlipTarget.Real r;
        FlipTarget flipTarget = this.target;
        return flipTarget instanceof FlipTarget.Real && (r = (FlipTarget.Real)flipTarget).index() > 0;
    }

    public int currentSpreadIndex() {
        int n;
        FlipTarget flipTarget = this.current;
        if (flipTarget instanceof FlipTarget.Real) {
            FlipTarget.Real r = (FlipTarget.Real)flipTarget;
            n = r.index();
        } else {
            n = this.lastRealIndex;
        }
        return n;
    }

    public boolean isFlipping() {
        return this.isFlipping;
    }

    /*
     * Uses 'sealed' constructs - enablewith --sealed true
     */
    private static interface FlipTarget {

        public record Overlay(PageSpread spread) implements FlipTarget
        {
        }

        public record Real(int index) implements FlipTarget
        {
        }
    }
}

