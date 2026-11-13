/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.crafting.Recipe
 *  net.minecraft.world.item.crafting.RecipeType
 */
package com.eternal_creations.archies_dino_mod.common.recipe;

import com.eternal_creations.archies_dino_mod.common.recipe.ArchieTrade;
import com.eternal_creations.archies_dino_mod.platform.Services;
import com.eternal_creations.archies_dino_mod.util.ResourceSupplier;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ADRecipeTypes {
    public static final ResourceSupplier<RecipeType<ArchieTrade>> ARCHIE_TRADE = Services.COMMON.registerRecipeType("archie_trade");

    public static void init() {
    }

    public static <T extends Recipe<?>> RecipeType<T> simple(ResourceLocation name) {
        final String toString = name.toString();
        return new RecipeType<T>(){

            public String toString() {
                return toString;
            }
        };
    }
}

