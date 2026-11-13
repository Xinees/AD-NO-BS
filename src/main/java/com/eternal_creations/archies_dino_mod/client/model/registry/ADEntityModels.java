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
    public static final AstemirModel<ArchieEntity> ARCHIE = AstemirModelManager.INSTANCE.registerModel(Constants.prefix("models/entity/archie.astemirmodel"), ArchieClient::setupModel);
    public static final AstemirModel<LushsaurEntity> LUSHSAUR = AstemirModelManager.INSTANCE.registerModel(Constants.prefix("models/entity/lushsaur.astemirmodel"), LushsaurClient::setupModel);
    public static final AstemirModel<LushsaurEntity> LUSHSAUR_BABY = AstemirModelManager.INSTANCE.registerModel(Constants.prefix("models/entity/lushsaur_baby.astemirmodel"), LushsaurClient::setupModel);
    public static final AstemirModel<ChorusoatlEntity> CHORUSOATL = AstemirModelManager.INSTANCE.registerModel(Constants.prefix("models/entity/chorusoatl.astemirmodel"), ChorusoatlClient::setupModel);
    public static final AstemirModel<ChorusoatlEntity> CHORUSOATL_BABY = AstemirModelManager.INSTANCE.registerModel(Constants.prefix("models/entity/chorusoatl_baby.astemirmodel"), ChorusoatlClient::setupModel);
    public static final AstemirModel<ObsidiceratopsEntity> OBSIDICERATOPS = AstemirModelManager.INSTANCE.registerModel(Constants.prefix("models/entity/obsidiceratops.astemirmodel"), (model, layers) -> ObsidiceratopsClient.setupModel((AstemirModel<ObsidiceratopsEntity>)model, (LayerContainer<ObsidiceratopsEntity>)layers, false));
    public static final AstemirModel<ObsidiceratopsEntity> OBSIDICERATOPS_BABY = AstemirModelManager.INSTANCE.registerModel(Constants.prefix("models/entity/obsidiceratops_baby.astemirmodel"), (model, layers) -> ObsidiceratopsClient.setupModel((AstemirModel<ObsidiceratopsEntity>)model, (LayerContainer<ObsidiceratopsEntity>)layers, true));
    public static final AstemirModel<SculkraptorEntity> SCULKRAPTOR = AstemirModelManager.INSTANCE.registerModel(Constants.prefix("models/entity/sculkraptor.astemirmodel"), SculkraptorClient::setupModel);
    public static final AstemirModel<SculkraptorEntity> SCULKRAPTOR_BABY = AstemirModelManager.INSTANCE.registerModel(Constants.prefix("models/entity/sculkraptor_baby.astemirmodel"), SculkraptorClient::setupModel);
    public static final AstemirModel<SculkraptorNestEntity> SCULKRAPTOR_NEST = AstemirModelManager.INSTANCE.registerModel(Constants.prefix("models/entity/sculkraptor_nest.astemirmodel"), SculkraptorNestClient::setupModel);
    public static final AstemirModel<SculkraptorSpitEntity> SCULKRAPTOR_SPIT = AstemirModelManager.INSTANCE.registerModel(Constants.prefix("models/entity/sculkraptor_projectile.astemirmodel"), SculkraptorProjectileClient::setupModel);
    public static final AstemirModel<SculksaurEntity> SCULKSAUR = AstemirModelManager.INSTANCE.registerModel(Constants.prefix("models/entity/sculksaur.astemirmodel"), SculksaurClient::setupModel);
    public static final AstemirModel<TherimossEntity> THERIMOSS = AstemirModelManager.INSTANCE.registerModel(Constants.prefix("models/entity/therimoss.astemirmodel"), TherimossClient::setupModel);
    public static final AstemirModel<TherimossEntity> THERIMOSS_BABY = AstemirModelManager.INSTANCE.registerModel(Constants.prefix("models/entity/therimoss_baby.astemirmodel"), TherimossClient::setupModelBaby);
    public static final AstemirModel<TherimossProjectileEntity> THERIMOSS_PROJECTILE = AstemirModelManager.INSTANCE.registerModel(Constants.prefix("models/entity/therimoss_projectile.astemirmodel"), TherimossProjectileClient::setupModel);
    public static final AstemirModel<CoralosaurEntity> CORALOSAUR = AstemirModelManager.INSTANCE.registerModel(Constants.prefix("models/entity/coralosaur.astemirmodel"), CoralosaurClient::setupModel);
    public static final AstemirModel<CoralosaurEntity> CORALOSAUR_BABY = AstemirModelManager.INSTANCE.registerModel(Constants.prefix("models/entity/coralosaur_baby.astemirmodel"), CoralosaurClient::setupBabyModel);

    public static void init() {
    }
}

