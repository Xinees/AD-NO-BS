/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.world.entity.Entity
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.gen.Accessor
 */
package com.eternal_creations.archies_dino_mod.mixin;

import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
@Mixin(value={Entity.class})
public interface EntityAccessor {
    @Accessor(value="ENTITY_COUNTER")
    public static AtomicInteger getENTITY_COUNTER() {
        throw new RuntimeException();
    }
}

