package com.eternal_creations.archies_dino_mod.mixin;

import net.minecraft.core.HolderOwner;
import net.minecraft.core.Holder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Holder.Reference.class)
public interface EndPeaksHolderReferenceAccessor {
    @Accessor("owner")
    HolderOwner<?> archie$getOwner();
}
