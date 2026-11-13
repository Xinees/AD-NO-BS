/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.astemir.astemirlib.re.core.math.Easing
 *  com.astemir.astemirlib.re.core.math.Easing$Simple
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.Minecraft
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.sounds.SoundEvents
 */
package com.eternal_creations.archies_dino_mod.client.screen.guidebook.book;

import com.astemir.astemirlib.re.core.math.Easing;
import com.eternal_creations.archies_dino_mod.Constants;
import com.eternal_creations.archies_dino_mod.client.screen.guidebook.book.AnimatedBookFlipper;
import com.eternal_creations.archies_dino_mod.client.screen.guidebook.book.BadgesPage;
import com.eternal_creations.archies_dino_mod.client.screen.guidebook.data.CategoryData;
import com.eternal_creations.archies_dino_mod.client.screen.guidebook.data.GuidebookData;
import com.eternal_creations.archies_dino_mod.client.screen.guidebook.data.PageData;
import com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.animation.AppearAnimation;
import com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.animation.ClickAnimation;
import com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.animation.HoverAnimation;
import com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.widget.AbstractWidget;
import com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.widget.Button;
import com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.widget.Container;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;

public class CategoryTabs
extends AbstractWidget {
    public static final String KEY_CONFIG = "ec_ad.config.book";
    private static final ResourceLocation BOOKMARKER_OFF = Constants.prefix("textures/gui/guidebook/markers/off.png");
    private static final ResourceLocation BOOKMARKER_ON = Constants.prefix("textures/gui/guidebook/markers/on.png");
    private final AnimatedBookFlipper book;
    private final List<CategoryData> categories;
    private final Map<CategoryData, AbstractWidget> categorySubTabs = new HashMap<CategoryData, AbstractWidget>();
    private AbstractWidget activeSubTabsContainer = null;
    private final BadgesPage badgesPage;

    public CategoryTabs(float width, float height, AnimatedBookFlipper book, GuidebookData guidebookData) {
        super(0.0f, 0.0f, width, height);
        this.book = book;
        this.book.spreadChanged(this::onSpreadChanged);
        this.badgesPage = new BadgesPage(guidebookData.badgesPath(), width, height);
        this.categories = guidebookData.categories();
        this.addChild(((Button.Builder)((Button.Builder)((Button.Builder)Button.builder(224.0f, 272.0f, 32.0f, 48.0f).sprites(BOOKMARKER_OFF, BOOKMARKER_ON, 576, 320).appearAnimation(AppearAnimation.verticalBottomSlide(0.3f, (Easing)Easing.Simple.SINE_OUT))).clickAnimation(ClickAnimation.ding(10.0f, 3.0f, 0.1f, (Easing)Easing.Simple.SINE_OUT))).clicked((ctx, clicked) -> {
            this.book.flipTo(this.book.getSpreadCount() - 1);
            ctx.playSound(SoundEvents.f_12275_, 1.0f, 2.0f);
            return true;
        })).build());
        this.addChild(((Button.Builder)((Button.Builder)((Button.Builder)Button.builder(176.0f, 272.0f, 32.0f, 48.0f).sprites(BOOKMARKER_OFF, BOOKMARKER_ON, 576, 320).appearAnimation(AppearAnimation.verticalBottomSlide(0.3f, (Easing)Easing.Simple.SINE_OUT))).clickAnimation(ClickAnimation.ding(10.0f, 3.0f, 0.1f, (Easing)Easing.Simple.SINE_OUT))).clicked((ctx, clicked) -> {
            Minecraft.m_91087_().m_91152_(null);
            ctx.playSound(SoundEvents.f_12508_, 1.0f, 1.0f);
            ctx.player().m_213846_((Component)Component.m_237115_((String)KEY_CONFIG).m_130940_(ChatFormatting.BLUE));
            return true;
        })).build());
        this.addChild(((Button.Builder)((Button.Builder)((Button.Builder)Button.builder(128.0f, 272.0f, 32.0f, 48.0f).sprites(BOOKMARKER_OFF, BOOKMARKER_ON, 576, 320).appearAnimation(AppearAnimation.verticalBottomSlide(0.3f, (Easing)Easing.Simple.SINE_OUT))).clickAnimation(ClickAnimation.ding(10.0f, 3.0f, 0.1f, (Easing)Easing.Simple.SINE_OUT))).clicked((ctx, clicked) -> {
            ctx.playSound(SoundEvents.f_11699_, 1.0f, 2.0f);
            this.book.flipToFake(this.badgesPage, false);
            return true;
        })).build());
        this.createCategoryButtons();
        this.createAllSubTabs();
    }

    private void createCategoryButtons() {
        for (int i = 0; i < Math.min(this.categories.size(), 8); ++i) {
            CategoryData category = this.categories.get(i);
            float x = 520.0f;
            float y = 16.0f + (float)i * 32.0f;
            this.addChild(((Button.Builder)((Button.Builder)((Button.Builder)((Button.Builder)Button.builder(x - 16.0f, y, 64.0f, 32.0f).sprites(BOOKMARKER_OFF, BOOKMARKER_ON, x, y, 576, 320).hitbox(22.0f, 0.0f, 40.0f, 32.0f)).hoverAnimation(HoverAnimation.rightSlide(48.0f, 12.8f, 0.3f, (Easing)Easing.Simple.SINE_OUT))).appearAnimation(AppearAnimation.rightShift(48.0f, 0.1f, (Easing)Easing.Simple.SINE_OUT))).clicked((ctx, btn) -> this.book.flipToCategory(category, 0))).build());
        }
    }

    private void createAllSubTabs() {
        for (CategoryData category : this.categories) {
            if (category.hasNoMarkers()) continue;
            AbstractWidget group = this.addChild(Container.of(0.0f, 0.0f, this.bounds.width(), this.bounds.height()));
            ResourceLocation off = category.markerOff();
            ResourceLocation on = category.markerOn();
            for (int i = 0; i < category.getPageCount(); ++i) {
                int pageIndex = i;
                group.addChild(((Button.Builder)((Button.Builder)((Button.Builder)((Button.Builder)Button.builder(0.0f, 32.0f + (float)i * 48.0f, 64.0f, 32.0f).sprites(off, on, 576, 320).hitbox(0.0f, 0.0f, 48.0f, 32.0f)).hoverAnimation(HoverAnimation.leftSlide(48.0f, 8.0f, 0.3f, (Easing)Easing.Simple.SINE_OUT))).appearAnimation(AppearAnimation.leftShift(48.0f, 0.1f, (Easing)Easing.Simple.SINE_OUT))).clicked((ctx, btn) -> this.book.flipToCategory(category, pageIndex))).build());
            }
            group.setVisible(false);
            this.categorySubTabs.put(category, group);
        }
    }

    public void onSpreadChanged(CategoryData category, PageData page) {
        if (category == null) {
            if (this.activeSubTabsContainer != null) {
                this.activeSubTabsContainer.setVisible(false);
                this.activeSubTabsContainer = null;
            }
            return;
        }
        AbstractWidget nextContainer = this.categorySubTabs.get(category);
        if (this.activeSubTabsContainer == nextContainer) {
            return;
        }
        if (this.activeSubTabsContainer != null) {
            this.activeSubTabsContainer.setVisible(false);
        }
        this.activeSubTabsContainer = nextContainer;
        if (this.activeSubTabsContainer != null) {
            this.activeSubTabsContainer.setVisible(true);
        }
    }
}

