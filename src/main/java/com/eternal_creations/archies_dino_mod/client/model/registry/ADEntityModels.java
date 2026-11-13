/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.astemir.astemirlib.re.client.model.AstemirModel
 *  com.astemir.astemirlib.re.client.model.AstemirModelManager
 *  com.astemir.astemirlib.re.client.model.render.renderer.layer.LayerContainer
 */
package com.eternal_creations.archies_dino_mod.client.model.registry;

import com.astemir.astemirlib.re.client.model.AstemirModel;
import com.astemir.astemirlib.re.client.model.AstemirModelManager;
import com.astemir.astemirlib.re.client.model.render.renderer.layer.LayerContainer;
import com.eternal_creations.archies_dino_mod.Constants;
import com.eternal_creations.archies_dino_mod.client.model.render.entity.misc.SculkraptorNestClient;
import com.eternal_creations.archies_dino_mod.client.model.render.entity.misc.SculkraptorProjectileClient;
import com.eternal_creations.archies_dino_mod.client.model.render.entity.mob.ArchieClient;
import com.eternal_creations.archies_dino_mod.client.model.render.entity.mob.ChorusoatlClient;
import com.eternal_creations.archies_dino_mod.client.model.render.entity.mob.CoralosaurClient;
import com.eternal_creations.archies_dino_mod.client.model.render.entity.mob.LushsaurClient;
import com.eternal_creations.archies_dino_mod.client.model.render.entity.mob.ObsidiceratopsClient;
import com.eternal_creations.archies_dino_mod.client.model.render.entity.mob.SculkraptorClient;
import com.eternal_creations.archies_dino_mod.client.model.render.entity.mob.SculksaurClient;
import com.eternal_creations.archies_dino_mod.client.model.render.entity.mob.TherimossClient;
import com.eternal_creations.archies_dino_mod.client.model.render.entity.projectile.TherimossProjectileClient;
import com.eternal_creations.archies_dino_mod.common.entity.mob.archie.ArchieEntity;
import com.eternal_creations.archies_dino_mod.common.entity.mob.chorusoatl.ChorusoatlEntity;
import com.eternal_creations.archies_dino_mod.common.entity.mob.coralosaur.CoralosaurEntity;
import com.eternal_creations.archies_dino_mod.common.entity.mob.lushsaur.LushsaurEntity;
import com.eternal_creations.archies_dino_mod.common.entity.mob.obsidiceratops.ObsidiceratopsEntity;
import com.eternal_creations.archies_dino_mod.common.entity.mob.sculkraptor.SculkraptorEntity;
import com.eternal_creations.archies_dino_mod.common.entity.mob.sculkraptor.SculkraptorNestEntity;
import com.eternal_creations.archies_dino_mod.common.entity.mob.sculksaur.SculksaurEntity;
import com.eternal_creations.archies_dino_mod.common.entity.mob.therimoss.TherimossEntity;
import com.eternal_creations.archies_dino_mod.common.entity.projectile.SculkraptorSpitEntity;
import com.eternal_creations.archies_dino_mod.common.entity.projectile.TherimossProjectileEntity;

public class ADEntityModels {
    public static final AstemirModel<ArchieEntity> ARCHIE = null;
    public static final AstemirModel<LushsaurEntity> LUSHSAUR = AstemirModelManager.INSTANCE.registerModel(Constants.prefix("models/entity/lushsaur.astemirmodel"), LushsaurClient::setupModel);
    public static final AstemirModel<LushsaurEntity> LUSHSAUR_BABY = AstemirModelManager.INSTANCE.registerModel(Constants.prefix("models/entity/lushsaur_baby.astemirmodel"), LushsaurClient::setupModel);
    public static final AstemirModel<ChorusoatlEntity> CHORUSOATL = AstemirModelManager.INSTANCE.registerModel(Constants.prefix("models/entity/chorusoatl.astemirmodel"), ChorusoatlClient::setupModel);
    public static final AstemirModel<ChorusoatlEntity> CHORUSOATL_BABY = AstemirModelManager.INSTANCE.registerModel(Constants.prefix("models/entity/chorusoatl_baby.astemirmodel"), ChorusoatlClient::setupModel);
    public static final AstemirModel<ObsidiceratopsEntity> OBSIDICERATOPS = null;
    public static final AstemirModel<ObsidiceratopsEntity> OBSIDICERATOPS_BABY = null;
    public static final AstemirModel<SculkraptorEntity> SCULKRAPTOR = null;
    public static final AstemirModel<SculkraptorEntity> SCULKRAPTOR_BABY = null;
    public static final AstemirModel<SculkraptorNestEntity> SCULKRAPTOR_NEST = null;
    public static final AstemirModel<SculkraptorSpitEntity> SCULKRAPTOR_SPIT = null;
    public static final AstemirModel<SculksaurEntity> SCULKSAUR = AstemirModelManager.INSTANCE.registerModel(Constants.prefix("models/entity/sculksaur.astemirmodel"), SculksaurClient::setupModel);
    public static final AstemirModel<TherimossEntity> THERIMOSS = null;
    public static final AstemirModel<TherimossEntity> THERIMOSS_BABY = null;
    public static final AstemirModel<TherimossProjectileEntity> THERIMOSS_PROJECTILE = null;
    public static final AstemirModel<CoralosaurEntity> CORALOSAUR = null;
    public static final AstemirModel<CoralosaurEntity> CORALOSAUR_BABY = null;

    public static void init() {
    }
}

