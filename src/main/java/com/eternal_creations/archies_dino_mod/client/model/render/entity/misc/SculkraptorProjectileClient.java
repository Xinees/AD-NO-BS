/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.astemir.astemirlib.re.client.model.AstemirModel
 *  com.astemir.astemirlib.re.client.model.core.animation.node.AbstractNode$Definition
 *  com.astemir.astemirlib.re.client.model.core.animation.node.ClipNode
 *  com.astemir.astemirlib.re.client.model.core.system.ModelSystem
 *  com.astemir.astemirlib.re.client.model.core.system.ModelSystem$Listener
 *  com.astemir.astemirlib.re.client.model.core.system.module.AnimatorModule
 *  com.astemir.astemirlib.re.client.model.core.texture.ModelTexture
 *  com.astemir.astemirlib.re.client.model.render.renderer.AstemirEntityRenderer
 *  com.astemir.astemirlib.re.client.model.render.renderer.AstemirEntityRenderer$Provider
 *  com.astemir.astemirlib.re.client.model.render.renderer.layer.BaseRenderLayer
 *  com.astemir.astemirlib.re.client.model.render.renderer.layer.LayerContainer
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.client.renderer.RenderType
 */
package com.eternal_creations.archies_dino_mod.client.model.render.entity.misc;

import com.astemir.astemirlib.re.client.model.AstemirModel;
import com.astemir.astemirlib.re.client.model.core.animation.node.AbstractNode;
import com.astemir.astemirlib.re.client.model.core.animation.node.ClipNode;
import com.astemir.astemirlib.re.client.model.core.system.ModelSystem;
import com.astemir.astemirlib.re.client.model.core.system.module.AnimatorModule;
import com.astemir.astemirlib.re.client.model.core.texture.ModelTexture;
import com.astemir.astemirlib.re.client.model.render.renderer.AstemirEntityRenderer;
import com.astemir.astemirlib.re.client.model.render.renderer.layer.BaseRenderLayer;
import com.astemir.astemirlib.re.client.model.render.renderer.layer.LayerContainer;
import com.eternal_creations.archies_dino_mod.client.model.registry.ADEntityModels;
import com.eternal_creations.archies_dino_mod.client.particle.effect.SculkraptorEffects;
import com.eternal_creations.archies_dino_mod.common.entity.projectile.SculkraptorSpitEntity;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.renderer.RenderType;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class SculkraptorProjectileClient
implements ModelSystem.Listener<SculkraptorSpitEntity> {
    private static final String NORMAL_TEXTURE = "sculkraptor_projectile";
    private static final String ANIMATION_IDLE = "idle";

    public void onInitialize(ModelSystem<SculkraptorSpitEntity> system, SculkraptorSpitEntity spit) {
        this.setupAnimations(system, spit);
    }

    private void setupAnimations(ModelSystem<SculkraptorSpitEntity> system, SculkraptorSpitEntity spit) {
        ((AnimatorModule)system.addModule(AnimatorModule::new)).setRoot((AbstractNode.Definition)ClipNode.of((String)ANIMATION_IDLE).events(SculkraptorEffects.PROJECTILE_EFFECTS));
    }

    public static void setupModel(AstemirModel<SculkraptorSpitEntity> model, LayerContainer<SculkraptorSpitEntity> layers) {
        layers.add(BaseRenderLayer.of((ModelTexture)ModelTexture.of(model, (String)NORMAL_TEXTURE, RenderType::m_110458_)));
    }

    public static AstemirEntityRenderer.Provider<SculkraptorSpitEntity> createRenderProvider() {
        return AstemirEntityRenderer.of(entity -> ADEntityModels.SCULKRAPTOR_SPIT, SculkraptorProjectileClient::new);
    }
}

