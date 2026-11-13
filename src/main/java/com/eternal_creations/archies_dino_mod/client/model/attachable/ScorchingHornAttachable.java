/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.astemir.astemirlib.re.client.model.AstemirModel
 *  com.astemir.astemirlib.re.client.model.core.animation.evaluator.ItemUseEvaluator
 *  com.astemir.astemirlib.re.client.model.core.animation.evaluator.ProgressEvaluator
 *  com.astemir.astemirlib.re.client.model.core.animation.node.AbstractNode$Definition
 *  com.astemir.astemirlib.re.client.model.core.animation.node.ClipNode
 *  com.astemir.astemirlib.re.client.model.core.animation.node.modifier.Modifier
 *  com.astemir.astemirlib.re.client.model.core.animation.node.modifier.Modifier$Translate
 *  com.astemir.astemirlib.re.client.model.core.animation.node.modifier.ModifierNode
 *  com.astemir.astemirlib.re.client.model.core.animation.node.state.StateMachineNode
 *  com.astemir.astemirlib.re.client.model.core.attachable.Attachable
 *  com.astemir.astemirlib.re.client.model.core.attachable.Attachable$Builder
 *  com.astemir.astemirlib.re.client.model.core.attachable.AttachableContext
 *  com.astemir.astemirlib.re.client.model.core.attachable.AttachableSystem
 *  com.astemir.astemirlib.re.client.model.core.attachable.AttachableSystem$Factory
 *  com.astemir.astemirlib.re.client.model.core.attachable.AttachableSystem$Listener
 *  com.astemir.astemirlib.re.client.model.core.system.module.AnimatorModule
 *  com.astemir.astemirlib.re.client.model.core.texture.ModelTexture
 *  com.astemir.astemirlib.re.client.model.render.component.BufferComponent
 *  com.astemir.astemirlib.re.client.model.render.renderer.layer.BaseRenderLayer
 *  com.astemir.astemirlib.re.client.model.render.renderer.layer.LayerContainer
 *  com.astemir.astemirlib.re.client.shader.post.PostRenderType
 *  com.astemir.astemirlib.re.client.shader.post.pass.BloomPass
 *  com.astemir.astemirlib.re.common.action.SyncedActionHolder
 *  com.astemir.astemirlib.re.core.function.DeltaFloat
 *  com.astemir.astemirlib.re.core.math.Easing
 *  com.astemir.astemirlib.re.core.math.Easing$Simple
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.item.Item
 */
package com.eternal_creations.archies_dino_mod.client.model.attachable;

import com.astemir.astemirlib.re.client.model.AstemirModel;
import com.astemir.astemirlib.re.client.model.core.animation.evaluator.ItemUseEvaluator;
import com.astemir.astemirlib.re.client.model.core.animation.evaluator.ProgressEvaluator;
import com.astemir.astemirlib.re.client.model.core.animation.node.AbstractNode;
import com.astemir.astemirlib.re.client.model.core.animation.node.ClipNode;
import com.astemir.astemirlib.re.client.model.core.animation.node.modifier.Modifier;
import com.astemir.astemirlib.re.client.model.core.animation.node.modifier.ModifierNode;
import com.astemir.astemirlib.re.client.model.core.animation.node.state.StateMachineNode;
import com.astemir.astemirlib.re.client.model.core.attachable.Attachable;
import com.astemir.astemirlib.re.client.model.core.attachable.AttachableContext;
import com.astemir.astemirlib.re.client.model.core.attachable.AttachableSystem;
import com.astemir.astemirlib.re.client.model.core.system.module.AnimatorModule;
import com.astemir.astemirlib.re.client.model.core.texture.ModelTexture;
import com.astemir.astemirlib.re.client.model.render.component.BufferComponent;
import com.astemir.astemirlib.re.client.model.render.renderer.layer.BaseRenderLayer;
import com.astemir.astemirlib.re.client.model.render.renderer.layer.LayerContainer;
import com.astemir.astemirlib.re.client.shader.post.PostRenderType;
import com.astemir.astemirlib.re.client.shader.post.pass.BloomPass;
import com.astemir.astemirlib.re.common.action.SyncedActionHolder;
import com.astemir.astemirlib.re.core.function.DeltaFloat;
import com.astemir.astemirlib.re.core.math.Easing;
import com.eternal_creations.archies_dino_mod.client.model.registry.ADAttachableModels;
import com.eternal_creations.archies_dino_mod.client.model.render.ADRenderTypes;
import com.eternal_creations.archies_dino_mod.common.items.ADItems;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;

public class ScorchingHornAttachable {
    public static Attachable.Builder create() {
        Supplier[] supplierArray = new Supplier[1];
        supplierArray[0] = ADItems.SCORCHING_HORN::get;
        return Attachable.of((AttachableSystem.Factory)ScorchingHornAttachable.createFirstPersonSystem(), (AttachableSystem.Factory)ScorchingHornAttachable.createThirdPersonSystem(), ADAttachableModels.SCORCHING_HORN).shouldUse(supplierArray);
    }

    private static AttachableSystem.Factory createFirstPersonSystem() {
        return AttachableSystem.Factory.createFirstPerson(() -> new AttachableSystem.Listener(){

            public void onInitialize(AttachableSystem system, LivingEntity entity) {
                if (!(entity instanceof SyncedActionHolder)) {
                    return;
                }
                SyncedActionHolder holder = (SyncedActionHolder)entity;
                ((AnimatorModule)system.addModule(AnimatorModule::new)).setRoot((AbstractNode.Definition)ModifierNode.of((AbstractNode.Definition)StateMachineNode.of().addState((AbstractNode.Definition)ClipNode.of((String)"use_first_person", (ProgressEvaluator)ItemUseEvaluator.of((LivingEntity)entity)), 0.2f, () -> entity.m_21211_().m_150930_((Item)ADItems.SCORCHING_HORN.get())).addState((AbstractNode.Definition)ClipNode.of((String)"hold_first_person"), 0.2f)).add(Modifier.visibility(() -> ScorchingHornAttachable.shouldShowRings(entity)).andThen(Modifier.Translate.y((DeltaFloat)DeltaFloat.of((float)-0.2f))), new String[]{"rings"}));
            }
        });
    }

    private static AttachableSystem.Factory createThirdPersonSystem() {
        return AttachableSystem.Factory.createThirdPerson(() -> new AttachableSystem.Listener(){

            public void onInitialize(AttachableSystem system, LivingEntity entity) {
                ((AnimatorModule)system.addModule(AnimatorModule::new)).setRoot((AbstractNode.Definition)ModifierNode.of((AbstractNode.Definition)StateMachineNode.of().addState((AbstractNode.Definition)ClipNode.of((String)"use_third_person", (ProgressEvaluator)ItemUseEvaluator.of((LivingEntity)entity)), 0.2f, () -> entity.m_21211_().m_150930_((Item)ADItems.SCORCHING_HORN.get())).addState((AbstractNode.Definition)ClipNode.of((String)"hold_third_person"), 0.5f)).add(Modifier.visibility(() -> ScorchingHornAttachable.shouldShowRings(entity)), new String[]{"rings"})).setDefaultEasing((Easing)Easing.Simple.SINE_IN, (Easing)Easing.Simple.SINE_OUT);
            }
        });
    }

    public static void setupModel(AstemirModel<AttachableContext> model, LayerContainer<AttachableContext> layers) {
        layers.add(BaseRenderLayer.of((ModelTexture)ModelTexture.of(model, (String)"main", RenderType::m_110458_)));
        layers.add(BaseRenderLayer.of((ModelTexture)ModelTexture.of(model, (String)"glow", ADRenderTypes::eyesNoCull)).buffer(BufferComponent.postEffect((Function)PostRenderType.MODEL_UNLIT, (boolean)false, (Class[])new Class[]{BloomPass.class})));
    }

    private static boolean shouldShowRings(LivingEntity livingEntity) {
        return livingEntity.m_21211_().m_150930_((Item)ADItems.SCORCHING_HORN.get()) && livingEntity.m_21252_() > 10;
    }
}

