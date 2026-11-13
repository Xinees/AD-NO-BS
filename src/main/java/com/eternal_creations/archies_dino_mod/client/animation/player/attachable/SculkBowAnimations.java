/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.astemir.astemirlib.re.client.model.core.BoneMask
 *  com.astemir.astemirlib.re.client.model.core.animation.evaluator.ItemUseEvaluator
 *  com.astemir.astemirlib.re.client.model.core.animation.evaluator.ProgressEvaluator
 *  com.astemir.astemirlib.re.client.model.core.animation.node.AbstractNode$Definition
 *  com.astemir.astemirlib.re.client.model.core.animation.node.ClipNode
 *  com.astemir.astemirlib.re.client.model.core.animation.node.EmptyNode
 *  com.astemir.astemirlib.re.client.model.core.animation.node.Weight
 *  com.astemir.astemirlib.re.client.model.core.animation.node.Weight$Definition
 *  com.astemir.astemirlib.re.client.model.core.animation.node.modifier.Modifier
 *  com.astemir.astemirlib.re.client.model.core.animation.node.modifier.ModifierNode
 *  com.astemir.astemirlib.re.client.model.core.animation.node.state.StateMachineNode
 *  com.astemir.astemirlib.re.client.model.virtual.VirtualModelManager
 *  com.astemir.astemirlib.re.common.action.ActionState
 *  com.astemir.astemirlib.re.common.action.SyncedActionHolder
 *  com.astemir.astemirlib.re.core.function.Condition
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.item.Item
 */
package com.eternal_creations.archies_dino_mod.client.animation.player.attachable;

import com.astemir.astemirlib.re.client.model.core.BoneMask;
import com.astemir.astemirlib.re.client.model.core.animation.evaluator.ItemUseEvaluator;
import com.astemir.astemirlib.re.client.model.core.animation.evaluator.ProgressEvaluator;
import com.astemir.astemirlib.re.client.model.core.animation.node.AbstractNode;
import com.astemir.astemirlib.re.client.model.core.animation.node.ClipNode;
import com.astemir.astemirlib.re.client.model.core.animation.node.EmptyNode;
import com.astemir.astemirlib.re.client.model.core.animation.node.Weight;
import com.astemir.astemirlib.re.client.model.core.animation.node.modifier.Modifier;
import com.astemir.astemirlib.re.client.model.core.animation.node.modifier.ModifierNode;
import com.astemir.astemirlib.re.client.model.core.animation.node.state.StateMachineNode;
import com.astemir.astemirlib.re.client.model.virtual.VirtualModelManager;
import com.astemir.astemirlib.re.common.action.ActionState;
import com.astemir.astemirlib.re.common.action.SyncedActionHolder;
import com.astemir.astemirlib.re.core.function.Condition;
import com.eternal_creations.archies_dino_mod.Constants;
import com.eternal_creations.archies_dino_mod.common.items.ADItems;
import com.eternal_creations.archies_dino_mod.common.items.weapon.SculkBowItem;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;

public class SculkBowAnimations {
    public static void init(int priority) {
        VirtualModelManager.INSTANCE.registerVirtualAnimations(EntityType.f_20532_, Constants.prefix("animations/player/attachable/sculk_bow"), (entity, system) -> {
            if (!(entity instanceof SyncedActionHolder)) {
                return;
            }
            SyncedActionHolder holder = (SyncedActionHolder)entity;
            ActionState actionRelease = holder.getActionByType(SculkBowItem.RELEASE);
            Condition useItem = Condition.of(() -> entity.m_21211_().m_150930_((Item)ADItems.SCULK_BOW.get()));
            system.addAnimationNode((AbstractNode.Definition)ModifierNode.of((AbstractNode.Definition)StateMachineNode.of().addState((AbstractNode.Definition)ClipNode.of((String)"sculk_bow.charge", (ProgressEvaluator)ItemUseEvaluator.of((LivingEntity)entity)).mirrorUse(entity)).addState((AbstractNode.Definition)ClipNode.of((String)"sculk_bow.release", (ActionState)actionRelease).mirror(entity, actionRelease), () -> ((ActionState)actionRelease).isActive()).addState(EmptyNode.of(), 0.3f)).add(Modifier.lookAt(), new String[]{"head"}), Weight.combine((Weight.Definition[])new Weight.Definition[]{Weight.of((Condition)useItem, (float)0.3f), actionRelease.weight()}), priority, BoneMask.exclude((String[])new String[]{"leftLeg", "rightLeg"})).yieldsToVanilla().hybrid();
        });
    }
}

