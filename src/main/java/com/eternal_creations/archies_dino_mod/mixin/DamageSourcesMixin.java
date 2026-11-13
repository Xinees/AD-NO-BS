/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.damagesource.DamageSources
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.projectile.AbstractArrow
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package com.eternal_creations.archies_dino_mod.mixin;

import com.eternal_creations.archies_dino_mod.common.ADDamageTypes;
import com.eternal_creations.archies_dino_mod.common.entity.projectile.PusTippedArrowEntity;
import javax.annotation.Nullable;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={DamageSources.class})
public class DamageSourcesMixin {
    @Inject(method={"arrow"}, at={@At(value="HEAD")}, cancellable=true)
    private void onArrow(AbstractArrow arrow, @Nullable Entity shooter, CallbackInfoReturnable<DamageSource> cir) {
        if (arrow instanceof PusTippedArrowEntity) {
            PusTippedArrowEntity pusTippedArrow = (PusTippedArrowEntity)arrow;
            if (shooter == null) {
                cir.setReturnValue((Object)ADDamageTypes.getEntityDamageSource(pusTippedArrow.m_9236_(), ADDamageTypes.PUS_ARROW, (Entity)pusTippedArrow));
            } else {
                cir.setReturnValue((Object)ADDamageTypes.getIndirectEntityDamageSource(pusTippedArrow.m_9236_(), ADDamageTypes.PUS_ARROW, (Entity)pusTippedArrow, shooter));
            }
        }
    }
}

