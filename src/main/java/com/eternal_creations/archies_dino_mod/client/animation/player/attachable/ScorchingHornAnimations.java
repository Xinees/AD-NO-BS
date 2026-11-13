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
 *  com.astemir.astemirlib.re.client.model.core.animation.node.state.StateMachineNode
 *  com.astemir.astemirlib.re.client.model.virtual.VirtualModelManager
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
import com.astemir.astemirlib.re.client.model.core.animation.node.state.StateMachineNode;
import com.astemir.astemirlib.re.client.model.virtual.VirtualModelManager;
import com.astemir.astemirlib.re.core.function.Condition;
import com.eternal_creations.archies_dino_mod.Constants;
import com.eternal_creations.archies_dino_mod.common.items.ADItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;

public class ScorchingHornAnimations {
    public static void init(int priority) {
        VirtualModelManager.INSTANCE.registerVirtualAnimations(EntityType.f_20532_, Constants.prefix("animations/player/attachable/scorching_horn"), (entity, system) -> {
            Condition useItem = Condition.of(() -> entity.m_21211_().m_150930_((Item)ADItems.SCORCHING_HORN.get()));
            system.addAnimationNode((AbstractNode.Definition)StateMachineNode.of().addState((AbstractNode.Definition)ClipNode.of((String)"scorching_horn.blow_third_person", (ProgressEvaluator)ItemUseEvaluator.of((LivingEntity)entity)).mirrorUse(entity), useItem).addState(EmptyNode.of(), 0.3f), Weight.of((Condition)useItem, (float)0.3f), priority, BoneMask.exclude((String[])new String[]{"leftLeg", "rightLeg"})).yieldsToVanilla().hybrid();
        });
    }
}

