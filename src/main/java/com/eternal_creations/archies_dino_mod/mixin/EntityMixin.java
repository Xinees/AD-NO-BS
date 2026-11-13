/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package com.eternal_creations.archies_dino_mod.mixin;

import com.eternal_creations.archies_dino_mod.common.items.armor.ObsidiceratopsArmorItem;
import com.eternal_creations.archies_dino_mod.common.items.util.ADArmorItem;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={Entity.class})
public abstract class EntityMixin {
    @Inject(method={"fireImmune"}, at={@At(value="HEAD")}, cancellable=true)
    public void archiesDinoDisableFire(CallbackInfoReturnable<Boolean> cir) {
        LivingEntity living;
        Entity entity = (Entity)this;
        if (entity instanceof LivingEntity && ADArmorItem.checkFullSet(living = (LivingEntity)entity, ObsidiceratopsArmorItem.class)) {
            cir.setReturnValue((Object)true);
        }
    }
}

