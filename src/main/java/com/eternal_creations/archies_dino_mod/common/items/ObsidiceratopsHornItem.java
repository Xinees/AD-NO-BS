/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.tags.InstrumentTags
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.ai.targeting.TargetingConditions
 *  net.minecraft.world.item.InstrumentItem
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  org.jetbrains.annotations.NotNull
 */
package com.eternal_creations.archies_dino_mod.common.items;

import com.eternal_creations.archies_dino_mod.common.entity.mob.obsidiceratops.ObsidiceratopsEntity;
import net.minecraft.tags.InstrumentTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.item.InstrumentItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class ObsidiceratopsHornItem
extends InstrumentItem {
    public ObsidiceratopsHornItem(Item.Properties properties) {
        super(properties, InstrumentTags.f_215858_);
    }

    public void m_5929_(@NotNull Level level, @NotNull LivingEntity livingEntity, @NotNull ItemStack stack, int remainingUseDuration) {
        super.m_5929_(level, livingEntity, stack, remainingUseDuration);
        for (ObsidiceratopsEntity entity : level.m_45971_(ObsidiceratopsEntity.class, TargetingConditions.m_148352_(), livingEntity, livingEntity.m_20191_().m_82400_(10.0))) {
            entity.m_7292_(new MobEffectInstance(MobEffects.f_19597_, 40, 2, false, false));
            entity.m_7292_(new MobEffectInstance(MobEffects.f_19613_, 40, 2, false, false));
        }
    }
}

