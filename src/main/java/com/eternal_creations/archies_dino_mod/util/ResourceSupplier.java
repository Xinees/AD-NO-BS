/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.resources.ResourceLocation
 */
package com.eternal_creations.archies_dino_mod.util;

import java.util.function.Supplier;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.resources.ResourceLocation;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ResourceSupplier<T>
implements Supplier<T> {
    private final Supplier<T> supplier;
    private final ResourceLocation id;

    public ResourceSupplier(Supplier<T> supplier, ResourceLocation id) {
        this.supplier = supplier;
        this.id = id;
    }

    @Override
    public T get() {
        return this.supplier.get();
    }

    public ResourceLocation id() {
        return this.id;
    }
}

