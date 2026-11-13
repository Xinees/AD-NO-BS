/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.data.DataProvider
 *  net.minecraft.data.PackOutput
 */
package com.eternal_creations.archies_dino_mod.data.util;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public interface DataProviderFactory<T extends DataProvider> {
    public T create(PackOutput var1);
}

