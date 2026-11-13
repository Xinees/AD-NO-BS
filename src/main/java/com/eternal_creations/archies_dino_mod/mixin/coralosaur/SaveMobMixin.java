/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.entity.Mob
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package com.eternal_creations.archies_dino_mod.mixin.coralosaur;

import com.eternal_creations.archies_dino_mod.util.iGuardianSummoned;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Mob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={Mob.class})
public class SaveMobMixin {
    @Inject(method={"addAdditionalSaveData"}, at={@At(value="TAIL")})
    private void onAdd(CompoundTag tag, CallbackInfo ci) {
        iGuardianSummoned summoned;
        UUID owner;
        SaveMobMixin saveMobMixin = this;
        if (saveMobMixin instanceof iGuardianSummoned && (owner = (summoned = (iGuardianSummoned)((Object)saveMobMixin)).m_21805_()) != null) {
            tag.m_128362_("archie_owner", owner);
        }
    }

    @Inject(method={"readAdditionalSaveData"}, at={@At(value="TAIL")})
    private void onRead(CompoundTag tag, CallbackInfo ci) {
        SaveMobMixin saveMobMixin = this;
        if (saveMobMixin instanceof iGuardianSummoned) {
            iGuardianSummoned summoned = (iGuardianSummoned)((Object)saveMobMixin);
            if (tag.m_128441_("archie_owner")) {
                summoned.archiesDinoMod$setOwner(tag.m_128342_("archie_owner"));
            }
        }
    }
}

