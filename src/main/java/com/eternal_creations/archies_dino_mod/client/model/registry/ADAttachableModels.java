/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.astemir.astemirlib.re.client.model.AstemirModel
 *  com.astemir.astemirlib.re.client.model.AstemirModelManager
 *  com.astemir.astemirlib.re.client.model.core.attachable.AttachableContext
 */
package com.eternal_creations.archies_dino_mod.client.model.registry;

import com.astemir.astemirlib.re.client.model.AstemirModel;
import com.astemir.astemirlib.re.client.model.AstemirModelManager;
import com.astemir.astemirlib.re.client.model.core.attachable.AttachableContext;
import com.eternal_creations.archies_dino_mod.Constants;
import com.eternal_creations.archies_dino_mod.client.model.attachable.ChorusScytheAttachable;
import com.eternal_creations.archies_dino_mod.client.model.attachable.LushsaurMaceAttachable;
import com.eternal_creations.archies_dino_mod.client.model.attachable.RibBowAttachable;
import com.eternal_creations.archies_dino_mod.client.model.attachable.ScorchingHornAttachable;
import com.eternal_creations.archies_dino_mod.client.model.attachable.SculkBowAttachable;
import com.eternal_creations.archies_dino_mod.client.model.attachable.TheriClawsAttachable;

public class ADAttachableModels {
    public static final AstemirModel<AttachableContext> THERIMOSS_CLAWS = AstemirModelManager.INSTANCE.registerModel(Constants.prefix("models/attachable/therimoss_claws"), TheriClawsAttachable::setupModel);
    public static final AstemirModel<AttachableContext> CHORUS_SCYTHE = AstemirModelManager.INSTANCE.registerModel(Constants.prefix("models/attachable/chorus_scythe"), ChorusScytheAttachable::setupModel);
    public static final AstemirModel<AttachableContext> LUSHSAUR_MACE = AstemirModelManager.INSTANCE.registerModel(Constants.prefix("models/attachable/lushsaur_mace"), LushsaurMaceAttachable::setupModel);
    public static final AstemirModel<AttachableContext> RIB_BOW = AstemirModelManager.INSTANCE.registerModel(Constants.prefix("models/attachable/rib_bow"), RibBowAttachable::setupModel);
    public static final AstemirModel<AttachableContext> SCULK_BOW = AstemirModelManager.INSTANCE.registerModel(Constants.prefix("models/attachable/sculk_bow"), SculkBowAttachable::setupModel);
    public static final AstemirModel<AttachableContext> SCORCHING_HORN = AstemirModelManager.INSTANCE.registerModel(Constants.prefix("models/attachable/scorching_horn"), ScorchingHornAttachable::setupModel);

    public static void init() {
    }
}

