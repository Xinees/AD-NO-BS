/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.client.model.geom.ModelLayerLocation
 */
package com.eternal_creations.archies_dino_mod.client.model.registry;

import com.eternal_creations.archies_dino_mod.Constants;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.model.geom.ModelLayerLocation;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ADModelLayers {
    public static final ModelLayerLocation LUSHSAUR_ARMOR_OUTER = ADModelLayers.register("lushsaur_armor", "outer");
    public static final ModelLayerLocation LUSHSAUR_ARMOR_INNER = ADModelLayers.register("lushsaur_armor", "inner");
    public static final ModelLayerLocation CHORUSOATL_ARMOR_OUTER = ADModelLayers.register("chorusoatl_armor", "outer");
    public static final ModelLayerLocation CHORUSOATL_ARMOR_INNER = ADModelLayers.register("chorusoatl_armor", "inner");
    public static final ModelLayerLocation SCULKSAUR_ARMOR_OUTER = ADModelLayers.register("sculksaur_armor", "outer");
    public static final ModelLayerLocation SCULKSAUR_ARMOR_INNER = ADModelLayers.register("sculksaur_armor", "inner");
    public static final ModelLayerLocation OBSIDICERATOPS_ARMOR_OUTER = ADModelLayers.register("obsidiceratops_armor", "outer");
    public static final ModelLayerLocation OBSIDICERATOPS_ARMOR_INNER = ADModelLayers.register("obsidiceratops_armor", "inner");
    public static final ModelLayerLocation CORALOSAUR_ARMOR_OUTER = ADModelLayers.register("coralosaur_armor", "outer");
    public static final ModelLayerLocation CORALOSAUR_ARMOR_INNER = ADModelLayers.register("coralosaur_armor", "inner");
    public static final ModelLayerLocation THERIMOSS_ARMOR_OUTER = ADModelLayers.register("therimoss_armor", "outer");
    public static final ModelLayerLocation THERIMOSS_ARMOR_INNER = ADModelLayers.register("therimoss_armor", "inner");
    public static final ModelLayerLocation SCULKRAPTOR_SPIT = ADModelLayers.register("sculkraptor_armor");

    private static ModelLayerLocation register(String id) {
        return ADModelLayers.register(id, "main");
    }

    private static ModelLayerLocation register(String id, String layer) {
        return new ModelLayerLocation(Constants.prefix(id), layer);
    }
}

