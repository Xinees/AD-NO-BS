/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.enchantment.EnchantmentHelper
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package com.eternal_creations.archies_dino_mod.mixin.coralosaur;

import com.eternal_creations.archies_dino_mod.common.items.armor.CoralosaurArmorItem;
import com.eternal_creations.archies_dino_mod.common.items.util.ADArmorItem;
import java.util.Map;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={EnchantmentHelper.class})
public class EnchantmentHelperMixin {
    @Inject(method={"hasAquaAffinity"}, at={@At(value="HEAD")}, cancellable=true)
    private static void onHasAquaAffinity(LivingEntity living, CallbackInfoReturnable<Boolean> cir) {
        Map<EquipmentSlot, ItemStack> armorSet = ADArmorItem.getArmorSet(living);
        if (armorSet.size() == 4 && armorSet.get(EquipmentSlot.HEAD).m_41720_() instanceof CoralosaurArmorItem) {
            cir.setReturnValue((Object)true);
        }
    }
}

