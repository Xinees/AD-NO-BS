/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.astemir.astemirlib.re.core.math.Easing
 *  com.astemir.astemirlib.re.core.math.Easing$Simple
 *  net.minecraft.resources.ResourceLocation
 */
package com.eternal_creations.archies_dino_mod.client.screen.guidebook.book;

import com.astemir.astemirlib.re.core.math.Easing;
import com.eternal_creations.archies_dino_mod.Constants;
import com.eternal_creations.archies_dino_mod.client.screen.guidebook.book.AnimatedBookFlipper;
import com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.animation.AppearAnimation;
import com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.widget.AbstractWidget;
import com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.widget.Button;
import net.minecraft.resources.ResourceLocation;

public class NavigationArrows
extends AbstractWidget {
    private static final ResourceLocation ARROW_OFF = Constants.prefix("textures/gui/guidebook/arrows/off.png");
    private static final ResourceLocation ARROWS_ON = Constants.prefix("textures/gui/guidebook/arrows/on.png");
    private final AnimatedBookFlipper book;
    private final Button prevButton;
    private final Button nextButton;
    private boolean lastHasPrev;
    private boolean lastHasNext;

    public NavigationArrows(AnimatedBookFlipper book) {
        super(0.0f, 0.0f, book.bounds().width(), book.bounds().height());
        this.book = book;
        this.prevButton = this.addChild(((Button.Builder)((Button.Builder)Button.builder(64.0f, 280.0f, 32.0f, 30.0f).sprites(ARROW_OFF, ARROWS_ON, 576, 320).appearAnimation(AppearAnimation.verticalBottomSlide(0.3f, (Easing)Easing.Simple.SINE_OUT))).clicked((ctx, clicked) -> {
            this.book.previousPage();
            this.updateVisibility();
            return true;
        })).build());
        this.nextButton = this.addChild(((Button.Builder)((Button.Builder)Button.builder(480.0f, 280.0f, 32.0f, 30.0f).sprites(ARROW_OFF, ARROWS_ON, 576, 320).appearAnimation(AppearAnimation.verticalBottomSlide(0.3f, (Easing)Easing.Simple.SINE_OUT))).clicked((ctx, clicked) -> {
            if (this.book.isOnFakePage()) {
                this.book.flipBackFromFake(true);
            } else {
                this.book.nextPage();
            }
            this.updateVisibility();
            return true;
        })).build());
        this.prevButton.setVisible(this.book.hasPreviousPage());
        this.nextButton.setVisible(this.hasNextOrFake());
        this.lastHasPrev = this.book.hasPreviousPage();
        this.lastHasNext = this.hasNextOrFake();
    }

    private boolean hasNextOrFake() {
        return this.book.isOnFakePage() || this.book.isEnteringFakePage() || this.book.hasNextPage();
    }

    public void updateVisibility() {
        boolean hasNext;
        boolean fake = this.book.isOnFakePage() || this.book.isEnteringFakePage();
        boolean hasPrev = !fake && this.book.hasPreviousPage();
        boolean bl = hasNext = fake || this.book.hasNextPage();
        if (this.lastHasPrev != hasPrev) {
            this.prevButton.setVisible(hasPrev);
            this.lastHasPrev = hasPrev;
        }
        if (this.lastHasNext != hasNext) {
            this.nextButton.setVisible(hasNext);
            this.lastHasNext = hasNext;
        }
    }

    @Override
    public void tick() {
        super.tick();
        this.updateVisibility();
    }
}

