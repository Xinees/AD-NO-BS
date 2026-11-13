/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.OwnableEntity
 *  net.minecraft.world.level.EntityGetter
 *  org.jetbrains.annotations.Nullable
 */
package com.eternal_creations.archies_dino_mod.util;

import java.util.UUID;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.level.EntityGetter;
import org.jetbrains.annotations.Nullable;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public interface iGuardianSummoned
extends OwnableEntity {
    public void archiesDinoMod$setOwner(@Nullable UUID var1);

    @Nullable
    public UUID archiesDinoMod$getOwner();

    @Nullable
    default public UUID m_21805_() {
        return this.archiesDinoMod$getOwner();
    }

    default public EntityGetter m_9236_() {
        iGuardianSummoned iGuardianSummoned2 = this;
        if (iGuardianSummoned2 instanceof Entity) {
            Entity entity = (Entity)iGuardianSummoned2;
            return entity.m_9236_();
        }
        throw new UnsupportedOperationException();
    }
}

