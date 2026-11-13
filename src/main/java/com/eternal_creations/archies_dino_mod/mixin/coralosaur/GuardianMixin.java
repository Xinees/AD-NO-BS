/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.network.syncher.EntityDataAccessor
 *  net.minecraft.network.syncher.EntityDataSerializer
 *  net.minecraft.network.syncher.EntityDataSerializers
 *  net.minecraft.network.syncher.SynchedEntityData
 *  net.minecraft.world.entity.monster.Guardian
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Unique
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package com.eternal_creations.archies_dino_mod.mixin.coralosaur;

import com.eternal_creations.archies_dino_mod.util.iGuardianSummoned;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.monster.Guardian;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={Guardian.class})
public class GuardianMixin
implements iGuardianSummoned {
    @Unique
    private static final EntityDataAccessor<Optional<UUID>> archies_Dino_Mod$SUMMONED = SynchedEntityData.m_135353_(Guardian.class, (EntityDataSerializer)EntityDataSerializers.f_135041_);

    @Inject(method={"defineSynchedData"}, at={@At(value="TAIL")})
    private void onDefineSynchedData(CallbackInfo ci) {
        ((Guardian)this).m_20088_().m_135372_(archies_Dino_Mod$SUMMONED, Optional.empty());
    }

    @Override
    public void archiesDinoMod$setOwner(@Nullable UUID owner) {
        ((Guardian)this).m_20088_().m_135381_(archies_Dino_Mod$SUMMONED, Optional.ofNullable(owner));
    }

    @Override
    @Nullable
    public UUID archiesDinoMod$getOwner() {
        return ((Optional)((Guardian)this).m_20088_().m_135370_(archies_Dino_Mod$SUMMONED)).orElse(null);
    }
}

