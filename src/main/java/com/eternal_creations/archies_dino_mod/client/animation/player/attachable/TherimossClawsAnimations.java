/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.astemir.astemirlib.re.client.model.core.BoneMask
 *  com.astemir.astemirlib.re.client.model.core.animation.node.AbstractNode$Definition
 *  com.astemir.astemirlib.re.client.model.core.animation.node.ClipNode
 *  com.astemir.astemirlib.re.client.model.core.animation.node.Weight
 *  com.astemir.astemirlib.re.client.model.core.animation.node.modifier.Modifier
 *  com.astemir.astemirlib.re.client.model.core.animation.node.modifier.ModifierNode
 *  com.astemir.astemirlib.re.client.model.virtual.VirtualModelManager
 *  com.astemir.astemirlib.re.common.action.ActionState
 *  com.astemir.astemirlib.re.common.action.SyncedActionHolder
 *  net.minecraft.world.entity.EntityType
 */
package com.eternal_creations.archies_dino_mod.client.animation.player.attachable;

import com.astemir.astemirlib.re.client.model.core.BoneMask;
import com.astemir.astemirlib.re.client.model.core.animation.node.AbstractNode;
import com.astemir.astemirlib.re.client.model.core.animation.node.ClipNode;
import com.astemir.astemirlib.re.client.model.core.animation.node.Weight;
import com.astemir.astemirlib.re.client.model.core.animation.node.modifier.Modifier;
import com.astemir.astemirlib.re.client.model.core.animation.node.modifier.ModifierNode;
import com.astemir.astemirlib.re.client.model.virtual.VirtualModelManager;
import com.astemir.astemirlib.re.common.action.ActionState;
import com.astemir.astemirlib.re.common.action.SyncedActionHolder;
import com.eternal_creations.archies_dino_mod.Constants;
import com.eternal_creations.archies_dino_mod.common.items.weapon.TherimossClawsItem;
import net.minecraft.world.entity.EntityType;

public class TherimossClawsAnimations {
    public static void init(int priority) {
        VirtualModelManager.INSTANCE.registerVirtualAnimations(EntityType.f_20532_, Constants.prefix("animations/player/attachable/therimoss_claws"), (entity, system) -> {
            ActionState actionAttack = ((SyncedActionHolder)entity).getActionByType(TherimossClawsItem.ATTACK);
            system.addAnimationNode((AbstractNode.Definition)ModifierNode.of((AbstractNode.Definition)ClipNode.of((String)"theri_claws.attack_third_person", (ActionState)actionAttack).mirror(entity, actionAttack)).add(Modifier.lookAt(), new String[]{"head"}), actionAttack.weight(), priority, BoneMask.exclude((String[])new String[]{"leftLeg", "rightLeg"})).yieldsToVanilla().hybrid();
            system.addAnimationNode((AbstractNode.Definition)ModifierNode.of((AbstractNode.Definition)ClipNode.of((String)"theri_claws.slide_third_person").mirror(entity, actionAttack)).add(Modifier.lookAt(), new String[]{"head"}), Weight.of(() -> entity.f_19862_ && entity.m_21093_(stack -> stack.m_41720_() instanceof TherimossClawsItem), (float)0.3f), priority + 2);
        });
    }
}

