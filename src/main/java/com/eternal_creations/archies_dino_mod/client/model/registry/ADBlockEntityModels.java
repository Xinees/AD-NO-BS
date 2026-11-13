/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.astemir.astemirlib.re.client.model.AstemirModel
 *  com.astemir.astemirlib.re.client.model.AstemirModelManager
 */
package com.eternal_creations.archies_dino_mod.client.model.registry;

import com.astemir.astemirlib.re.client.model.AstemirModel;
import com.astemir.astemirlib.re.client.model.AstemirModelManager;
import com.eternal_creations.archies_dino_mod.Constants;
import com.eternal_creations.archies_dino_mod.client.model.render.block.DinoEggClient;
import com.eternal_creations.archies_dino_mod.common.block.entity.egg.BlockEntityChorusoatlEgg;
import com.eternal_creations.archies_dino_mod.common.block.entity.egg.BlockEntityLushsaurEgg;
import com.eternal_creations.archies_dino_mod.common.block.entity.egg.BlockEntityObsidiceratopsEgg;
import com.eternal_creations.archies_dino_mod.common.block.entity.egg.BlockEntitySculkraptorEgg;
import com.eternal_creations.archies_dino_mod.common.block.entity.egg.BlockEntityTherimossEgg;

public class ADBlockEntityModels {
    public static final AstemirModel<BlockEntityLushsaurEgg> LUSHSAUR_EGG = AstemirModelManager.INSTANCE.registerModel(Constants.prefix("models/block/lushsaur_egg.astemirmodel"), DinoEggClient::setupLushsaur);
    public static final AstemirModel<BlockEntityChorusoatlEgg> CHORUSOATL_EGG = AstemirModelManager.INSTANCE.registerModel(Constants.prefix("models/block/chorusoatl_egg.astemirmodel"), DinoEggClient::setupChorusoatl);
    public static final AstemirModel<BlockEntityObsidiceratopsEgg> OBSIDICERATOPS_EGG = AstemirModelManager.INSTANCE.registerModel(Constants.prefix("models/block/obsidiceratops_egg.astemirmodel"), DinoEggClient::setupObsidiceratops);
    public static final AstemirModel<BlockEntitySculkraptorEgg> SCULKRAPTOR_EGG = AstemirModelManager.INSTANCE.registerModel(Constants.prefix("models/block/sculkraptor_egg.astemirmodel"), DinoEggClient::setupSculkraptor);
    public static final AstemirModel<BlockEntityTherimossEgg> THERIMOSS_EGG = AstemirModelManager.INSTANCE.registerModel(Constants.prefix("models/block/therimoss_egg.astemirmodel"), DinoEggClient::setupTherimoss);

    public static void init() {
    }
}

