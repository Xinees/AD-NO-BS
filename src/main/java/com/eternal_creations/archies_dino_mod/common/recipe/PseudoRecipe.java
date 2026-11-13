/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.world.Container
 *  net.minecraft.world.item.crafting.Recipe
 *  net.minecraft.world.level.Level
 */
package com.eternal_creations.archies_dino_mod.common.recipe;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public interface PseudoRecipe
extends Recipe<Container> {
    default public boolean m_5818_(Container container, Level level) {
        return true;
    }

    default public boolean m_8004_(int i, int i1) {
        return true;
    }
}

