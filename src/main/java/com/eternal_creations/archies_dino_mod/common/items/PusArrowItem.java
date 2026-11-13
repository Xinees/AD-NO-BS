/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.projectile.AbstractArrow
 *  net.minecraft.world.item.ArrowItem
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 */
package com.eternal_creations.archies_dino_mod.common.items;

import com.eternal_creations.archies_dino_mod.common.entity.projectile.PusTippedArrowEntity;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class PusArrowItem
extends ArrowItem {
    public PusArrowItem(Item.Properties properties) {
        super(properties);
    }

    public AbstractArrow m_6394_(Level level, ItemStack stack, LivingEntity shooter) {
        return new PusTippedArrowEntity(level, shooter);
    }
}

