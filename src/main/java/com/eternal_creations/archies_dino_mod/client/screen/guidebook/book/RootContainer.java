/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.astemir.astemirlib.re.core.math.Easing
 *  com.astemir.astemirlib.re.core.math.Easing$Back
 *  com.astemir.astemirlib.re.core.math.Easing$Direction
 *  com.astemir.astemirlib.re.core.math.tween.TweenFloat
 *  net.minecraft.resources.ResourceLocation
 */
package com.eternal_creations.archies_dino_mod.client.screen.guidebook.book;

import com.astemir.astemirlib.re.core.math.Easing;
import com.astemir.astemirlib.re.core.math.tween.TweenFloat;
import com.eternal_creations.archies_dino_mod.Constants;
import com.eternal_creations.archies_dino_mod.client.screen.guidebook.book.AnimatedBookFlipper;
import com.eternal_creations.archies_dino_mod.client.screen.guidebook.book.CategoryTabs;
import com.eternal_creations.archies_dino_mod.client.screen.guidebook.book.NavigationArrows;
import com.eternal_creations.archies_dino_mod.client.screen.guidebook.book.PageSpread;
import com.eternal_creations.archies_dino_mod.client.screen.guidebook.data.CategoryData;
import com.eternal_creations.archies_dino_mod.client.screen.guidebook.data.GuidebookData;
import com.eternal_creations.archies_dino_mod.client.screen.guidebook.data.PageData;
import com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.UIContext;
import com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.UIElement;
import com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.render.RenderState;
import com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.widget.AbstractWidget;
import com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.widget.ImageView;
import java.util.List;
import net.minecraft.resources.ResourceLocation;

public class RootContainer
extends AbstractWidget {
    private static final ResourceLocation BACKGROUND = Constants.prefix("textures/gui/guidebook/background.png");
    private final TweenFloat animPos = TweenFloat.of();
    private final TweenFloat animScale = TweenFloat.of();
    private final AnimatedBookFlipper book;
    private final CategoryTabs tabs;

    public RootContainer(float x, float y, float width, float height) {
        super(x, y, width, height);
        this.addChild(ImageView.builder(BACKGROUND, 0.0f, 0.0f, width, height).build());
        GuidebookData guidebookData = GuidebookData.loadData();
        this.book = new AnimatedBookFlipper(width, height);
        this.tabs = new CategoryTabs(width, height, this.book, guidebookData);
        this.addChild(new NavigationArrows(this.book));
        this.addChild(this.tabs);
        this.addChild(this.book);
        this.buildSpreads(guidebookData.categories());
        this.triggerAppear();
    }

    private void buildSpreads(List<CategoryData> categories) {
        for (CategoryData category : categories) {
            for (PageData page : category.pages()) {
                PageSpread spread = this.book.createSpread(category, page);
                for (UIElement.Factory factory : page.factories()) {
                    spread.addChild(factory.create(this.bounds.width, this.bounds.height));
                }
            }
        }
    }

    public void triggerAppear() {
        this.animPos.setValue(1.0f);
        this.animPos.setTarget(0.0f, 0.15f, (Easing)Easing.Back.of((Easing.Direction)Easing.Direction.OUT, (float)1.2f));
        this.animScale.setValue(0.0f);
        this.animScale.setTarget(1.0f, 0.12f, (Easing)Easing.Back.of((Easing.Direction)Easing.Direction.OUT, (float)1.2f));
    }

    @Override
    public void tick() {
        this.animPos.tick();
        this.animScale.tick();
        super.tick();
    }

    @Override
    public void applyStateModification(UIContext ctx, RenderState renderState) {
        float pt = ctx.partialTick();
        float scale = this.animScale.get(pt);
        float posProgress = this.animPos.get(pt);
        float yOffset = posProgress * ((float)ctx.bookHeight() * 1.5f);
        renderState.transform().translate(0.0f, yOffset).scale(scale, scale, this.bounds.width * 0.5f, this.bounds.height * 0.5f);
    }

    public static RootContainer of(float width, float height) {
        return new RootContainer(0.0f, 0.0f, width, height);
    }
}

