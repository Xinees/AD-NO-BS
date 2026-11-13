/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.resources.ResourceLocation
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package com.eternal_creations.archies_dino_mod;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class Constants {
    public static final String MOD_ID = "ec_ad";
    public static final String MOD_NAME = "Archie\u2019s Dinosaurs";
    public static final Logger LOG = LoggerFactory.getLogger((String)"Archie\u2019s Dinosaurs");

    public static ResourceLocation prefix(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}

