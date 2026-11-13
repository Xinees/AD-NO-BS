/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.enchantment.EnchantmentHelper
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package com.eternal_creations.archies_dino_mod.mixin;

import com.eternal_creations.archies_dino_mod.common.items.armor.SculksaurArmorItem;
import com.eternal_creations.archies_dino_mod.common.items.util.ADArmorItem;
import java.util.Map;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={EnchantmentHelper.class})
public class EnchantmentHelperMixin {
    @Inject(method={"doPostDamageEffects"}, at={@At(value="HEAD")})
    private static void onPostDamageEffects(LivingEntity attacker, Entity target, CallbackInfo ci) {
        if (!(target instanceof LivingEntity)) {
            return;
        }
        LivingEntity living = (LivingEntity)target;
        Map<EquipmentSlot, ItemStack> armorSet = ADArmorItem.getArmorSet(attacker);
        if (armorSet.size() == 4 && armorSet.get(EquipmentSlot.HEAD).m_41720_() instanceof SculksaurArmorItem) {
            living.m_147207_(new MobEffectInstance(MobEffects.f_19614_, 100, 0), (Entity)attacker);
        }
    }
}

