/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.astemir.astemirlib.re.client.model.core.BoneMask
 *  com.astemir.astemirlib.re.client.model.core.animation.node.AbstractNode$Definition
 *  com.astemir.astemirlib.re.client.model.core.animation.node.BlendNode
 *  com.astemir.astemirlib.re.client.model.core.animation.node.ClipNode
 *  com.astemir.astemirlib.re.client.model.core.animation.node.EmptyNode
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
import com.eternal_creations.archies_dino_mod.common.items.weapon.LushsaurMaceItem;
import net.minecraft.world.entity.EntityType;

public class LushsaurMaceAnimations {
    public static void init(int priority) {
        VirtualModelManager.INSTANCE.registerVirtualAnimations(EntityType.f_20532_, Constants.prefix("animations/player/attachable/lushsaur_mace"), (entity, system) -> {
            if (!(entity instanceof SyncedActionHolder)) {
                return;
            }
            SyncedActionHolder holder = (SyncedActionHolder)entity;
            ActionState actionAttack = holder.getActionByType(LushsaurMaceItem.ATTACK);
            ActionState actionSpin = holder.getActionByType(LushsaurMaceItem.ATTACK_SPIN);
            system.addAnimationNode((AbstractNode.Definition)ModifierNode.of((AbstractNode.Definition)BlendNode.of((AbstractNode.Definition)EmptyNode.of(), (AbstractNode.Definition)ClipNode.of((String)"lushsaur_mace.spin", (ActionState)actionSpin), (Weight.Definition)actionSpin.weight())).add(Modifier.lookAt(), new String[]{"head"}), actionSpin.weight(), priority + 1).yieldsToVanilla().hybrid();
            system.addAnimationNode((AbstractNode.Definition)ModifierNode.of((AbstractNode.Definition)StateMachineNode.of().addState((AbstractNode.Definition)ClipNode.of((String)"lushsaur_mace.attack", (ActionState)actionAttack).mirror(entity, actionAttack), () -> ((ActionState)actionAttack).isActive()).addState(EmptyNode.of())).add(Modifier.lookAt(), new String[]{"head"}), actionAttack.weight(), priority, BoneMask.exclude((String[])new String[]{"leftLeg", "rightLeg"})).hybrid().yieldsToVanilla();
        });
    }
}

