/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.tags.TagKey
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.crafting.Ingredient
 *  net.minecraft.world.level.ItemLike
 */
package com.eternal_creations.archies_dino_mod.data.util;

import java.util.function.Supplier;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public interface PlatformSpecific {
    public void setEnvironment(Environment var1);

    public Environment getEnvironment();

    default public <T> T select(T forgeVariant, T fabricVariant) {
        return this.isForge() ? forgeVariant : fabricVariant;
    }

    default public Ingredient selectIngredient(TagKey<Item> forgeVariant, Item fabricVariant) {
        return this.isForge() ? Ingredient.m_204132_(forgeVariant) : Ingredient.m_43929_((ItemLike[])new ItemLike[]{fabricVariant});
    }

    default public <T> T selectLazy(Supplier<T> forgeVariant, Supplier<T> fabricVariant) {
        return this.isForge() ? forgeVariant.get() : fabricVariant.get();
    }

    default public <T> T selectLazy(Supplier<T> forgeVariant, T fabricVariant) {
        return this.isForge() ? forgeVariant.get() : fabricVariant;
    }

    default public <T> T selectLazy(T forgeVariant, Supplier<T> fabricVariant) {
        return this.isForge() ? forgeVariant : fabricVariant.get();
    }

    default public boolean isForge() {
        return this.getEnvironment() == Environment.FORGE;
    }

    default public boolean isFabric() {
        return this.getEnvironment() == Environment.FABRIC;
    }

    public static enum Environment {
        FORGE,
        FABRIC;

    }
}

