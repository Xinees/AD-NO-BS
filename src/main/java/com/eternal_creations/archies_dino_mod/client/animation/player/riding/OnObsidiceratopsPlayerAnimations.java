/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.astemir.astemirlib.re.client.model.core.animation.evaluator.ActionEvaluator
 *  com.astemir.astemirlib.re.client.model.core.animation.evaluator.ProgressEvaluator
 *  com.astemir.astemirlib.re.client.model.core.animation.node.AbstractNode$Definition
 *  com.astemir.astemirlib.re.client.model.core.animation.node.ClipNode
 *  com.astemir.astemirlib.re.client.model.core.animation.node.Weight
 *  com.astemir.astemirlib.re.client.model.core.animation.node.modifier.Modifier
 *  com.astemir.astemirlib.re.client.model.core.animation.node.modifier.ModifierNode
 *  com.astemir.astemirlib.re.client.model.core.animation.node.state.State
 *  com.astemir.astemirlib.re.client.model.core.animation.node.state.StateMachineNode
 *  com.astemir.astemirlib.re.client.model.core.animation.node.state.StateMachineNode$Definition
 *  com.astemir.astemirlib.re.client.model.virtual.VirtualModelManager
 *  com.astemir.astemirlib.re.common.action.ActionState
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 */
package com.eternal_creations.archies_dino_mod.client.animation.player.riding;

import com.astemir.astemirlib.re.client.model.core.animation.evaluator.ActionEvaluator;
import com.astemir.astemirlib.re.client.model.core.animation.evaluator.ProgressEvaluator;
import com.astemir.astemirlib.re.client.model.core.animation.node.AbstractNode;
import com.astemir.astemirlib.re.client.model.core.animation.node.ClipNode;
import com.astemir.astemirlib.re.client.model.core.animation.node.Weight;
import com.astemir.astemirlib.re.client.model.core.animation.node.modifier.Modifier;
import com.astemir.astemirlib.re.client.model.core.animation.node.modifier.ModifierNode;
import com.astemir.astemirlib.re.client.model.core.animation.node.state.State;
import com.astemir.astemirlib.re.client.model.core.animation.node.state.StateMachineNode;
import com.astemir.astemirlib.re.client.model.virtual.VirtualModelManager;
import com.astemir.astemirlib.re.common.action.ActionState;
import com.eternal_creations.archies_dino_mod.Constants;
import com.eternal_creations.archies_dino_mod.client.model.system.ADModifiers;
import com.eternal_creations.archies_dino_mod.common.entity.mob.obsidiceratops.ObsidiceratopsEntity;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;

public class OnObsidiceratopsPlayerAnimations {
    private static final ResourceLocation PATH = Constants.prefix("animations/player/riding/obsidiceratops");
    private static final String ANIMATION_IDLE = "obsidiceratops.idle";
    private static final String ANIMATION_WALK = "obsidiceratops.walk";
    private static final String ANIMATION_RUN = "obsidiceratops.run";
    private static final String ANIMATION_CHARGE = "obsidiceratops.charge";
    private static final String ANIMATION_DEATH = "obsidiceratops.death";
    private static final String ANIMATION_ATTACK_HIT = "obsidiceratops.attack1_trigger";
    private static final String ANIMATION_ATTACK_TAIL = "obsidiceratops.attack2_trigger";
    private static final String ANIMATION_EAT = "obsidiceratops.eat";
    private static final String ANIMATION_WARN = "obsidiceratops.agro";
    private static final String ANIMATION_DRINK1 = "obsidiceratops.drink1";
    private static final String ANIMATION_DRINK2 = "obsidiceratops.drink2";

    public static void init(int priority) {
        VirtualModelManager.INSTANCE.registerVirtualAnimations(EntityType.f_20532_, PATH, (player, system) -> {
            ObsidiceratopsContext ctx = new ObsidiceratopsContext((LivingEntity)player);
            StateMachineNode.Definition stateMachine = StateMachineNode.of().addState(ctx.action(ANIMATION_DEATH, 0.2f, o -> o.actionDeath)).addState(ctx.action(ANIMATION_ATTACK_HIT, 0.2f, o -> o.actionAttackHit)).addState(ctx.action(ANIMATION_ATTACK_TAIL, 0.2f, o -> o.actionAttackTail)).addState(ctx.action(ANIMATION_DRINK1, 0.2f, o -> o.actionDrink1)).addState(ctx.action(ANIMATION_DRINK2, 0.2f, o -> o.actionDrink2)).addState(ctx.action(ANIMATION_EAT, 0.2f, o -> o.actionEat)).addState(ctx.action(ANIMATION_WARN, 0.2f, o -> o.actionWarn)).addState(ctx.locomotionState(ANIMATION_CHARGE, 0.2f, ObsidiceratopsEntity::isCharging)).addState(ctx.locomotionState(ANIMATION_RUN, 0.2f, o -> o.getLocomotion().getRunLocomotion(0.0f, 0.3f) > 0.01f)).addState(ctx.state(ANIMATION_WALK, 0.2f, o -> o.m_20184_().m_165925_() > 1.0E-4)).addState((AbstractNode.Definition)ClipNode.of((String)ANIMATION_IDLE), 0.2f);
            system.addAnimationNode((AbstractNode.Definition)ModifierNode.of((AbstractNode.Definition)stateMachine).add(ADModifiers.mountPlayerModifier(ctx::getMount, 2.0f, 0.2f), new String[]{"root"}).add(Modifier.lookAt(), new String[]{"head"}), Weight.of(ctx::isRiding, (float)0.2f), priority).yieldsToVanilla();
        });
    }

    private record ObsidiceratopsContext(LivingEntity player) {
        public ObsidiceratopsEntity getMount() {
            ObsidiceratopsEntity obsidiceratops;
            Entity entity = this.player.m_20202_();
            return entity instanceof ObsidiceratopsEntity ? (obsidiceratops = (ObsidiceratopsEntity)entity) : null;
        }

        public boolean isRiding() {
            return this.player.m_20202_() instanceof ObsidiceratopsEntity;
        }

        public State state(String anim, float transitionTime, Predicate<ObsidiceratopsEntity> cond) {
            return State.of((AbstractNode.Definition)ClipNode.of((String)anim), (float)transitionTime, () -> {
                ObsidiceratopsEntity mount = this.getMount();
                return mount != null && cond.test(mount);
            });
        }

        public State action(String anim, float transitionTime, Function<ObsidiceratopsEntity, ActionState> getter) {
            Supplier<ActionState> supplier = () -> {
                ObsidiceratopsEntity mount = this.getMount();
                return mount != null ? (ActionState)getter.apply(mount) : null;
            };
            return State.of((AbstractNode.Definition)ClipNode.of((String)anim, (ProgressEvaluator)ActionEvaluator.of(supplier)), (float)transitionTime, () -> {
                ActionState a = (ActionState)supplier.get();
                return a != null && a.isActive();
            });
        }

        public State locomotionState(String anim, float transitionTime, Predicate<ObsidiceratopsEntity> cond) {
            return State.of((AbstractNode.Definition)ClipNode.of((String)anim, p -> {
                ObsidiceratopsEntity mount = this.getMount();
                return mount != null ? mount.getLocomotion().getRunLocomotion(p, 0.3f) : 0.0f;
            }), (float)transitionTime, () -> {
                ObsidiceratopsEntity mount = this.getMount();
                return mount != null && cond.test(mount);
            });
        }
    }
}

