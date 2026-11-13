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
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
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
import com.eternal_creations.archies_dino_mod.common.ADTags;
import com.eternal_creations.archies_dino_mod.common.items.ADItems;
import com.eternal_creations.archies_dino_mod.common.items.weapon.TherimossClawsItem;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class TheriClawsAttachable {
    public static Attachable.Builder create() {
        return Attachable.of((AttachableSystem.Factory)TheriClawsAttachable.createFirstPersonSystem(), (AttachableSystem.Factory)TheriClawsAttachable.createThirdPersonSystem(), ADAttachableModels.THERIMOSS_CLAWS).shouldUse(ctx -> ctx.getItemStack().m_41720_() instanceof TherimossClawsItem).shouldDisplayBone((ctx, name) -> {
            if (ctx.getItemStack().m_204117_(ADTags.Items.TWO_HANDED)) {
                return true;
            }
            boolean offhandTP = !ctx.isFirstPerson() && ctx.getSlot() == EquipmentSlot.OFFHAND;
            String boneToHide = offhandTP ? "main_hand_theri_claws" : "off_hand_theri_claws";
            return !name.equals("off_hand_first_person") && !name.equals(boneToHide);
        }).disableMirroring();
    }

    private static AttachableSystem.Factory createFirstPersonSystem() {
        return AttachableSystem.Factory.createFirstPerson(() -> new AttachableSystem.Listener(){

            public void onInitialize(AttachableSystem system, LivingEntity entity) {
                if (!(entity instanceof SyncedActionHolder)) {
                    return;
                }
                SyncedActionHolder holder = (SyncedActionHolder)entity;
                ActionState actionAttack = holder.getActionByType(TherimossClawsItem.ATTACK);
                ((AnimatorModule)system.addModule(AnimatorModule::new)).setRoot((AbstractNode.Definition)StateMachineNode.of().addState((AbstractNode.Definition)ClipNode.of((String)"attack_first_person", (ActionState)actionAttack), actionAttack.whenEnding(0.2f)).addState((AbstractNode.Definition)ClipNode.of((String)"hold_first_person"), 0.5f)).setDefaultEasing((Easing)Easing.Simple.SINE_IN, (Easing)Easing.Simple.SINE_OUT);
                system.setArmHeight(defaultValue -> actionAttack.isActive() ? 0.0f : defaultValue);
                system.setAttackAnim(defaultValue -> actionAttack.isActive() ? 0.0f : defaultValue);
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
        ModelTexture copper = ModelTexture.of(model, (String)"theri_claw_copper", RenderType::m_110458_);
        ModelTexture iron = ModelTexture.of(model, (String)"theri_claw_iron", RenderType::m_110458_);
        ModelTexture gold = ModelTexture.of(model, (String)"theri_claw_gold", RenderType::m_110458_);
        ModelTexture diamond = ModelTexture.of(model, (String)"theri_claw_diamond", RenderType::m_110458_);
        ModelTexture netherite = ModelTexture.of(model, (String)"theri_claw_netherite", RenderType::m_110458_);
        layers.add(BaseRenderLayer.of(ctx -> {
            ItemStack stack = ctx.getItemStack();
            if (stack.m_150930_((Item)ADItems.THERIMOSS_CLAWS_COPPER.get())) {
                return copper;
            }
            if (stack.m_150930_((Item)ADItems.THERIMOSS_CLAWS_IRON.get())) {
                return iron;
            }
            if (stack.m_150930_((Item)ADItems.THERIMOSS_CLAWS_GOLD.get())) {
                return gold;
            }
            if (stack.m_150930_((Item)ADItems.THERIMOSS_CLAWS_DIAMOND.get())) {
                return diamond;
            }
            if (stack.m_150930_((Item)ADItems.THERIMOSS_CLAWS_NETHERITE.get())) {
                return netherite;
            }
            return copper;
        }));
    }
}

