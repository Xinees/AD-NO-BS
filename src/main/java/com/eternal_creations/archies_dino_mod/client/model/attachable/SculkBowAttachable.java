/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.astemir.astemirlib.re.client.model.AstemirModel
 *  com.astemir.astemirlib.re.client.model.core.animation.evaluator.ItemUseEvaluator
 *  com.astemir.astemirlib.re.client.model.core.animation.evaluator.ProgressEvaluator
 *  com.astemir.astemirlib.re.client.model.core.animation.node.AbstractNode$Definition
 *  com.astemir.astemirlib.re.client.model.core.animation.node.ClipNode
 *  com.astemir.astemirlib.re.client.model.core.animation.node.state.StateMachineNode
 *  com.astemir.astemirlib.re.client.model.core.attachable.Attachable
 *  com.astemir.astemirlib.re.client.model.core.attachable.Attachable$Builder
 *  com.astemir.astemirlib.re.client.model.core.attachable.AttachableContext
 *  com.astemir.astemirlib.re.client.model.core.attachable.AttachableSystem
 *  com.astemir.astemirlib.re.client.model.core.attachable.AttachableSystem$Factory
 *  com.astemir.astemirlib.re.client.model.core.attachable.AttachableSystem$Listener
 *  com.astemir.astemirlib.re.client.model.core.system.module.AnimatorModule
 *  com.astemir.astemirlib.re.client.model.core.texture.ModelTexture
 *  com.astemir.astemirlib.re.client.model.render.renderer.layer.BaseRenderLayer
 *  com.astemir.astemirlib.re.client.model.render.renderer.layer.LayerContainer
 *  com.astemir.astemirlib.re.common.action.ActionState
 *  com.astemir.astemirlib.re.common.action.SyncedActionHolder
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
import com.astemir.astemirlib.re.client.model.core.animation.node.state.StateMachineNode;
import com.astemir.astemirlib.re.client.model.core.attachable.Attachable;
import com.astemir.astemirlib.re.client.model.core.attachable.AttachableContext;
import com.astemir.astemirlib.re.client.model.core.attachable.AttachableSystem;
import com.astemir.astemirlib.re.client.model.core.system.module.AnimatorModule;
import com.astemir.astemirlib.re.client.model.core.texture.ModelTexture;
import com.astemir.astemirlib.re.client.model.render.renderer.layer.BaseRenderLayer;
import com.astemir.astemirlib.re.client.model.render.renderer.layer.LayerContainer;
import com.astemir.astemirlib.re.common.action.ActionState;
import com.astemir.astemirlib.re.common.action.SyncedActionHolder;
import com.astemir.astemirlib.re.core.math.Easing;
import com.eternal_creations.archies_dino_mod.client.model.registry.ADAttachableModels;
import com.eternal_creations.archies_dino_mod.common.items.ADItems;
import com.eternal_creations.archies_dino_mod.common.items.weapon.SculkBowItem;
import java.util.function.Supplier;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;

public class SculkBowAttachable {
    public static Attachable.Builder create() {
        Supplier[] supplierArray = new Supplier[1];
        supplierArray[0] = ADItems.SCULK_BOW::get;
        return Attachable.of((AttachableSystem.Factory)SculkBowAttachable.createFirstPersonSystem(), (AttachableSystem.Factory)SculkBowAttachable.createThirdPersonSystem(), ADAttachableModels.SCULK_BOW).shouldUse(supplierArray);
    }

    private static AttachableSystem.Factory createFirstPersonSystem() {
        return AttachableSystem.Factory.createFirstPerson(() -> new AttachableSystem.Listener(){

            public void onInitialize(AttachableSystem system, LivingEntity entity) {
                if (!(entity instanceof SyncedActionHolder)) {
                    return;
                }
                SyncedActionHolder holder = (SyncedActionHolder)entity;
                ActionState actionRelease = holder.getActionByType(SculkBowItem.RELEASE);
                ((AnimatorModule)system.addModule(AnimatorModule::new)).setRoot((AbstractNode.Definition)StateMachineNode.of().addState((AbstractNode.Definition)ClipNode.of((String)"first_person_charge", (ProgressEvaluator)ItemUseEvaluator.of((LivingEntity)entity)), 0.2f, () -> entity.m_21211_().m_150930_((Item)ADItems.SCULK_BOW.get())).addState((AbstractNode.Definition)ClipNode.of((String)"first_person_release", (ActionState)actionRelease), actionRelease.whenEnding(0.1f)).addState((AbstractNode.Definition)ClipNode.of((String)"first_person_hold"), 0.2f));
                system.setArmHeight(defaultValue -> actionRelease.isActive() ? 0.0f : defaultValue);
                system.setAttackAnim(defaultValue -> actionRelease.isActive() ? 0.0f : defaultValue);
            }
        });
    }

    private static AttachableSystem.Factory createThirdPersonSystem() {
        return AttachableSystem.Factory.createThirdPerson(() -> new AttachableSystem.Listener(){

            public void onInitialize(AttachableSystem system, LivingEntity entity) {
                ((AnimatorModule)system.addModule(AnimatorModule::new)).setRoot((AbstractNode.Definition)StateMachineNode.of().addState((AbstractNode.Definition)ClipNode.of((String)"third_person_charge", (ProgressEvaluator)ItemUseEvaluator.of((LivingEntity)entity)), 0.2f, () -> entity.m_21211_().m_150930_((Item)ADItems.SCULK_BOW.get())).addState((AbstractNode.Definition)ClipNode.of((String)"third_person_hold"), 0.2f)).setDefaultEasing((Easing)Easing.Simple.SINE_IN, (Easing)Easing.Simple.SINE_OUT);
            }
        });
    }

    public static void setupModel(AstemirModel<AttachableContext> model, LayerContainer<AttachableContext> layers) {
        layers.add(BaseRenderLayer.of((ModelTexture)ModelTexture.of(model, (String)"sculk_bow", RenderType::m_110458_)));
    }
}

