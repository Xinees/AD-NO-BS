/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.monster.Enemy
 *  net.minecraft.world.entity.monster.Guardian
 *  org.spongepowered.asm.mixin.Final
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package com.eternal_creations.archies_dino_mod.mixin.coralosaur;

import com.eternal_creations.archies_dino_mod.util.iGuardianSummoned;
import java.util.UUID;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Guardian;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets={"net.minecraft.world.entity.monster.Guardian$GuardianAttackSelector"})
public class GuardianSelectorMixin {
    @Shadow
    @Final
    private Guardian f_32877_;

    @Inject(method={"test(Ljava/lang/Object;)Z"}, at={@At(value="HEAD")}, cancellable=true)
    private void onTest(Object target, CallbackInfoReturnable<Boolean> cir) {
        iGuardianSummoned summoned;
        UUID owner;
        Guardian guardian = this.f_32877_;
        if (guardian instanceof iGuardianSummoned && (owner = (summoned = (iGuardianSummoned)guardian).m_21805_()) != null) {
            Mob mob;
            iGuardianSummoned summon;
            cir.setReturnValue((Object)(target instanceof Enemy && (!(target instanceof iGuardianSummoned) || owner.equals((summon = (iGuardianSummoned)target).archiesDinoMod$getOwner())) || target instanceof Mob && (mob = (Mob)target).m_5448_() != null && mob.m_5448_().m_20148_().equals(owner) ? 1 : 0));
        }
    }
}

