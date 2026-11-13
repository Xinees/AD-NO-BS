/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.astemir.astemirlib.re.client.model.AstemirModel
 *  com.astemir.astemirlib.re.client.model.CommonRootOperations
 *  com.astemir.astemirlib.re.client.model.core.animation.node.AbstractNode$Definition
 *  com.astemir.astemirlib.re.client.model.core.animation.node.ClipNode
 *  com.astemir.astemirlib.re.client.model.core.animation.node.EmptyNode
 *  com.astemir.astemirlib.re.client.model.core.animation.node.state.StateMachineNode
 *  com.astemir.astemirlib.re.client.model.core.system.ModelSystem
 *  com.astemir.astemirlib.re.client.model.core.system.ModelSystem$Listener
 *  com.astemir.astemirlib.re.client.model.core.system.module.AnimatorModule
 *  com.astemir.astemirlib.re.client.model.core.texture.ModelTexture
 *  com.astemir.astemirlib.re.client.model.render.renderer.AstemirEntityRenderer
 *  com.astemir.astemirlib.re.client.model.render.renderer.AstemirEntityRenderer$Provider
 *  com.astemir.astemirlib.re.client.model.render.renderer.layer.BaseRenderLayer
 *  com.astemir.astemirlib.re.client.model.render.renderer.layer.LayerContainer
 *  com.astemir.astemirlib.re.core.math.Easing
 *  com.astemir.astemirlib.re.core.math.Easing$Simple
 *  net.minecraft.client.renderer.RenderType
 */
package com.eternal_creations.archies_dino_mod.client.model.render.entity.projectile;

import com.astemir.astemirlib.re.client.model.AstemirModel;
import com.astemir.astemirlib.re.client.model.CommonRootOperations;
import com.astemir.astemirlib.re.client.model.core.animation.node.AbstractNode;
import com.astemir.astemirlib.re.client.model.core.animation.node.ClipNode;
import com.astemir.astemirlib.re.client.model.core.animation.node.EmptyNode;
import com.astemir.astemirlib.re.client.model.core.animation.node.state.StateMachineNode;
import com.astemir.astemirlib.re.client.model.core.system.ModelSystem;
import com.astemir.astemirlib.re.client.model.core.system.module.AnimatorModule;
import com.astemir.astemirlib.re.client.model.core.texture.ModelTexture;
import com.astemir.astemirlib.re.client.model.render.renderer.AstemirEntityRenderer;
import com.astemir.astemirlib.re.client.model.render.renderer.layer.BaseRenderLayer;
import com.astemir.astemirlib.re.client.model.render.renderer.layer.LayerContainer;
import com.astemir.astemirlib.re.core.math.Easing;
import com.eternal_creations.archies_dino_mod.client.model.registry.ADEntityModels;
import com.eternal_creations.archies_dino_mod.common.entity.projectile.TherimossProjectileEntity;
import net.minecraft.client.renderer.RenderType;

public class TherimossProjectileClient
implements ModelSystem.Listener<TherimossProjectileEntity> {
    public void onInitialize(ModelSystem<TherimossProjectileEntity> modelSystem, TherimossProjectileEntity instance) {
        modelSystem.addRootOperation(CommonRootOperations.PROJECTILE_ROTATION);
        ((AnimatorModule)modelSystem.addModule(AnimatorModule::new)).setRoot((AbstractNode.Definition)StateMachineNode.of().addState(EmptyNode.of(), 0.2f, instance::isAttached).addState((AbstractNode.Definition)ClipNode.of((String)"thrown"), 0.2f)).setDefaultEasing((Easing)Easing.Simple.SINE_IN, (Easing)Easing.Simple.SINE_OUT);
    }

    public static void setupModel(AstemirModel<TherimossProjectileEntity> model, LayerContainer<TherimossProjectileEntity> layers) {
        layers.add(BaseRenderLayer.of((ModelTexture)ModelTexture.of(model, (String)"main", RenderType::m_110452_)));
    }

    public static AstemirEntityRenderer.Provider<TherimossProjectileEntity> createRenderProvider() {
        return AstemirEntityRenderer.of(entity -> ADEntityModels.THERIMOSS_PROJECTILE, TherimossProjectileClient::new);
    }
}

