/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.astemir.astemirlib.re.client.model.AstemirModel
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
 */
package com.eternal_creations.archies_dino_mod.client.model.attachable;

import com.astemir.astemirlib.re.client.model.AstemirModel;
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
import com.eternal_creations.archies_dino_mod.common.items.weapon.LushsaurMaceItem;
import java.util.function.Supplier;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.LivingEntity;

public class LushsaurMaceAttachable {
    public static Attachable.Builder create() {
        Supplier[] supplierArray = new Supplier[1];
        supplierArray[0] = ADItems.LUSHSAUR_MACE::get;
        return Attachable.of((AttachableSystem.Factory)LushsaurMaceAttachable.createFirstPersonSystem(), (AttachableSystem.Factory)LushsaurMaceAttachable.createThirdPersonSystem(), ADAttachableModels.LUSHSAUR_MACE).shouldUse(supplierArray);
    }

    private static AttachableSystem.Factory createFirstPersonSystem() {
        return AttachableSystem.Factory.createFirstPerson(() -> new AttachableSystem.Listener(){

            public void onInitialize(AttachableSystem system, LivingEntity entity) {
                if (!(entity instanceof SyncedActionHolder)) {
                    return;
                }
                SyncedActionHolder holder = (SyncedActionHolder)entity;
                ActionState actionAttack = holder.getActionByType(LushsaurMaceItem.ATTACK);
                ActionState actionAttackSpin = holder.getActionByType(LushsaurMaceItem.ATTACK_SPIN);
                ((AnimatorModule)system.addModule(AnimatorModule::new)).setRoot((AbstractNode.Definition)StateMachineNode.of().addState((AbstractNode.Definition)ClipNode.of((String)"spin_first_person", (ActionState)actionAttackSpin), actionAttackSpin.whenEndingWithoutFadeOut(0.5f)).addState((AbstractNode.Definition)ClipNode.of((String)"attack_first_person", (ActionState)actionAttack), () -> ((ActionState)actionAttack).isActive()).addState((AbstractNode.Definition)ClipNode.of((String)"hold_first_person"), 0.5f));
                system.setArmHeight(defaultValue -> actionAttackSpin.isActive() || actionAttack.isActive() ? 0.0f : defaultValue);
                system.setAttackAnim(defaultValue -> actionAttackSpin.isActive() || actionAttack.isActive() ? 0.0f : defaultValue);
            }
        });
    }

    private static AttachableSystem.Factory createThirdPersonSystem() {
        return AttachableSystem.Factory.createThirdPerson(() -> new AttachableSystem.Listener(){

            public void onInitialize(AttachableSystem system, LivingEntity entity) {
                ((AnimatorModule)system.addModule(AnimatorModule::new)).setRoot((AbstractNode.Definition)ClipNode.of((String)"hold_third_person")).setDefaultEasing((Easing)Easing.Simple.SINE_IN, (Easing)Easing.Simple.SINE_OUT);
            }
        });
    }

    public static void setupModel(AstemirModel<AttachableContext> model, LayerContainer<AttachableContext> layers) {
        layers.add(BaseRenderLayer.of((ModelTexture)ModelTexture.of(model, (String)"main", RenderType::m_110458_)));
    }
}

