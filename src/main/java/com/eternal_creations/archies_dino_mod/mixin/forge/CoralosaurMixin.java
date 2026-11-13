/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.common.extensions.IForgeEntity
 *  net.minecraftforge.entity.PartEntity
 *  org.jetbrains.annotations.Nullable
 *  org.spongepowered.asm.mixin.Mixin
 */
package com.eternal_creations.archies_dino_mod.mixin.forge;

import com.eternal_creations.archies_dino_mod.common.entity.base.IPartEntity;
import com.eternal_creations.archies_dino_mod.common.entity.mob.coralosaur.CoralosaurEntity;
import com.eternal_creations.archies_dino_mod.entity.ForgeEntityPart;
import net.minecraftforge.common.extensions.IForgeEntity;
import net.minecraftforge.entity.PartEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value={CoralosaurEntity.class}, remap=false)
public abstract class CoralosaurMixin
implements IForgeEntity {
    public boolean isMultipartEntity() {
        return true;
    }

    @Nullable
    public PartEntity<?>[] getParts() {
        IPartEntity<?>[] list = ((CoralosaurEntity)((Object)this)).parts;
        ForgeEntityPart[] parts = new ForgeEntityPart[list.length];
        int i = 0;
        for (IPartEntity<?> part : list) {
            parts[i++] = (ForgeEntityPart)part;
        }
        return parts;
    }
}

