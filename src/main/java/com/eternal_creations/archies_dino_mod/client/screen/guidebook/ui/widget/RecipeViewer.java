/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.core.NonNullList
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.crafting.CraftingRecipe
 *  net.minecraft.world.item.crafting.Ingredient
 *  net.minecraft.world.item.crafting.RecipeManager
 *  net.minecraft.world.item.crafting.RecipeType
 *  net.minecraft.world.item.crafting.ShapedRecipe
 */
package com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.widget;

import com.eternal_creations.archies_dino_mod.Constants;
import com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.UIContext;
import com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.misc.Sprite;
import com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.render.RenderState;
import com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.widget.AbstractWidget;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;

public class RecipeViewer
extends AbstractWidget {
    private static final ResourceLocation RECIPE_VIEWER_LOCATION = Constants.prefix("textures/gui/guidebook/recipe_viewer.png");
    private final ItemStack resultStack;
    private final Sprite gridSprite;
    private final List<Ingredient> ingredients = new ArrayList<Ingredient>();

    protected RecipeViewer(Builder builder) {
        super(builder);
        this.resultStack = builder.result;
        this.gridSprite = Sprite.of(RECIPE_VIEWER_LOCATION, 13.0f, 7.0f, 101.0f, 51.0f, 128.0f, 64.0f);
        this.loadRecipe();
    }

    private void loadRecipe() {
        if (this.resultStack == null || this.resultStack.m_41619_()) {
            return;
        }
        Minecraft mc = Minecraft.m_91087_();
        if (mc.f_91073_ == null) {
            return;
        }
        RecipeManager recipeManager = mc.f_91073_.m_7465_();
        for (CraftingRecipe recipe : recipeManager.m_44013_(RecipeType.f_44107_)) {
            ItemStack result = recipe.m_8043_(mc.f_91073_.m_9598_());
            if (!ItemStack.m_150942_((ItemStack)result, (ItemStack)this.resultStack)) continue;
            this.setupCraftingGrid(recipe);
            break;
        }
    }

    private void setupCraftingGrid(CraftingRecipe recipe) {
        this.ingredients.clear();
        NonNullList recipeIngredients = recipe.m_7527_();
        if (recipe instanceof ShapedRecipe) {
            ShapedRecipe shaped = (ShapedRecipe)recipe;
            int width = shaped.m_44220_();
            int height = shaped.m_44221_();
            for (int row = 0; row < 3; ++row) {
                for (int col = 0; col < 3; ++col) {
                    int index;
                    if (col < width && row < height && (index = row * width + col) < recipeIngredients.size()) {
                        this.ingredients.add((Ingredient)recipeIngredients.get(index));
                        continue;
                    }
                    this.ingredients.add(Ingredient.f_43901_);
                }
            }
        } else {
            for (int i = 0; i < 9; ++i) {
                if (i < recipeIngredients.size()) {
                    this.ingredients.add((Ingredient)recipeIngredients.get(i));
                    continue;
                }
                this.ingredients.add(Ingredient.f_43901_);
            }
        }
    }

    @Override
    protected void renderContent(UIContext ctx, RenderState state) {
        ctx.drawSprite(this.gridSprite, 0.0f, 0.0f, this.bounds.width(), this.bounds.height());
        long gameTime = ctx.player() != null ? ctx.player().m_9236_().m_46467_() : System.currentTimeMillis() / 50L;
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 3; ++col) {
                ItemStack[] matchingStacks;
                Ingredient ingredient;
                int index = row * 3 + col;
                if (index >= this.ingredients.size() || (ingredient = this.ingredients.get(index)).m_43947_() || (matchingStacks = ingredient.m_43908_()).length <= 0) continue;
                int itemIndex = (int)(gameTime / 20L % (long)matchingStacks.length);
                ItemStack currentStack = matchingStacks[itemIndex];
                int slotX = col * 17;
                int slotY = row * 17;
                this.renderSlotItem(ctx, currentStack, slotX, slotY);
            }
        }
        if (this.resultStack != null && !this.resultStack.m_41619_()) {
            this.renderSlotItem(ctx, this.resultStack, 84, 17);
        }
        super.renderContent(ctx, state);
    }

    private void renderSlotItem(UIContext ctx, ItemStack stack, int slotX, int slotY) {
        GuiGraphics graphics = ctx.graphics();
        graphics.m_280480_(stack, slotX, slotY);
        graphics.m_280370_(ctx.font(), stack, slotX, slotY);
        double mouseX = ctx.mouseX();
        double mouseY = ctx.mouseY();
        if (mouseX >= (double)slotX && mouseY >= (double)slotY && mouseX < (double)(slotX + 16) && mouseY < (double)(slotY + 16)) {
            graphics.m_280153_(ctx.font(), stack, (int)mouseX, (int)mouseY);
        }
    }

    public static Builder builder(ItemStack result, float x, float y) {
        return new Builder(result, x, y, 101.0f, 51.0f);
    }

    public static class Builder
    extends AbstractWidget.Builder<Builder, RecipeViewer> {
        private final ItemStack result;

        public Builder(ItemStack result, float x, float y, float width, float height) {
            super(x, y, width, height);
            this.result = result;
        }

        @Override
        public RecipeViewer build() {
            return new RecipeViewer(this);
        }
    }
}

