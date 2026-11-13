/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.crafting.RecipeSerializer
 */
package com.eternal_creations.archies_dino_mod.common.recipe;

import com.eternal_creations.archies_dino_mod.common.recipe.ArchieTrade;
import com.eternal_creations.archies_dino_mod.platform.Services;
import com.eternal_creations.archies_dino_mod.util.ResourceSupplier;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class ADRecipeSerializers {
    public static final ResourceSupplier<RecipeSerializer<ArchieTrade>> ARCHIE_TRADE = Services.COMMON.registerRecipeSerializer("archie_trade", ArchieTrade.Serializer::new);

    public static void init() {
    }
}

