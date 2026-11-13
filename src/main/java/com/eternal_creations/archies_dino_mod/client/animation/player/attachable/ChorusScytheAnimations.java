/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.astemir.astemirlib.re.client.model.core.BoneMask
 *  com.astemir.astemirlib.re.client.model.core.animation.node.AbstractNode$Definition
 *  com.astemir.astemirlib.re.client.model.core.animation.node.BlendNode
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
 *  net.minecraft.world.entity.EntityType
 */
package com.eternal_creations.archies_dino_mod.client.animation.player.attachable;

import com.astemir.astemirlib.re.client.model.core.BoneMask;
import com.astemir.astemirlib.re.client.model.core.animation.node.AbstractNode;
import com.astemir.astemirlib.re.client.model.core.animation.node.BlendNode;
import com.astemir.astemirlib.re.client.model.core.animation.node.ClipNode;
import com.astemir.astemirlib.re.client.model.core.animation.node.EmptyNode;
import com.astemir.astemirlib.re.client.model.core.animation.node.Weight;
import com.astemir.astemirlib.re.client.model.core.animation.node.modifier.Modifier;
import com.astemir.astemirlib.re.client.model.core.animation.node.modifier.ModifierNode;
import com.astemir.astemirlib.re.client.model.core.animation.node.state.StateMachineNode;
import com.astemir.astemirlib.re.client.model.virtual.VirtualModelManager;
import com.astemir.astemirlib.re.common.action.ActionState;
import com.astemir.astemirlib.re.common.action.SyncedActionHolder;
import com.eternal_creations.archies_dino_mod.Constants;
import com.eternal_creations.archies_dino_mod.common.items.weapon.ChorusScytheItem;
import net.minecraft.world.entity.EntityType;

public class ChorusScytheAnimations {
    public static void init(int priority) {
        VirtualModelManager.INSTANCE.registerVirtualAnimations(EntityType.f_20532_, Constants.prefix("animations/player/attachable/chorus_scythe"), (entity, system) -> {
            if (!(entity instanceof SyncedActionHolder)) {
                return;
            }
            SyncedActionHolder holder = (SyncedActionHolder)entity;
            ActionState actionAttack1 = holder.getActionByType(ChorusScytheItem.ATTACK1);
            ActionState actionAttack2 = holder.getActionByType(ChorusScytheItem.ATTACK2);
            ActionState actionSpin = holder.getActionByType(ChorusScytheItem.SPIN);
            system.addAnimationNode((AbstractNode.Definition)ModifierNode.of((AbstractNode.Definition)BlendNode.of((AbstractNode.Definition)EmptyNode.of(), (AbstractNode.Definition)ClipNode.of((String)"chorus_scythe.spin", (ActionState)actionSpin), (Weight.Definition)actionSpin.weight())).add(Modifier.lookAt(), new String[]{"head"}), actionSpin.weight(), priority + 1).yieldsToVanilla().hybrid();
            system.addAnimationNode((AbstractNode.Definition)ModifierNode.of((AbstractNode.Definition)StateMachineNode.of().addState((AbstractNode.Definition)ClipNode.of((String)"chorus_scythe.attack_1", (ActionState)actionAttack1).mirror(entity, actionAttack1), () -> ((ActionState)actionAttack1).isActive()).addState((AbstractNode.Definition)ClipNode.of((String)"chorus_scythe.attack_2", (ActionState)actionAttack2).mirror(entity, actionAttack2), () -> ((ActionState)actionAttack2).isActive()).addState(EmptyNode.of())).add(Modifier.lookAt(), new String[]{"head"}), Weight.combine((Weight.Definition[])new Weight.Definition[]{actionAttack1.weight(), actionAttack2.weight()}), priority, BoneMask.exclude((String[])new String[]{"leftLeg", "rightLeg"})).yieldsToVanilla().hybrid();
        });
    }
}

