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
import com.eternal_creations.archies_dino_mod.common.entity.mob.lushsaur.LushsaurEntity;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;

public class OnLushsaurPlayerAnimations {
    private static final ResourceLocation PATH = Constants.prefix("animations/player/riding/lushsaur");
    private static final String ANIMATION_IDLE = "lushsaur.idle";
    private static final String ANIMATION_WALK = "lushsaur.walk";
    private static final String ANIMATION_RUN = "lushsaur.run";
    private static final String ANIMATION_RUN_ATTACK = "lushsaur.run_attack";
    private static final String ANIMATION_REST = "lushsaur.rest";
    private static final String ANIMATION_DEATH = "lushsaur.death";
    private static final String ANIMATION_HIDE = "lushsaur.hide";
    private static final String ANIMATION_ROAR = "lushsaur.roar";
    private static final String ANIMATION_EAT = "lushsaur.eat";
    private static final String ANIMATION_UP = "lushsaur.up";
    private static final String ANIMATION_DOWN = "lushsaur.down";
    private static final String ANIMATION_ATTACK_TAIL_STOMP = "lushsaur.attack3";
    private static final String ANIMATION_ATTACK_TAIL = "lushsaur.attack4_trigger";
    private static final String ANIMATION_ATTACK_HEADBUTT = "lushsaur.attack1_trigger";
    private static final String ANIMATION_ATTACK_BITE = "lushsaur.attack2_trigger";

    public static void init(int priority) {
        VirtualModelManager.INSTANCE.registerVirtualAnimations(EntityType.f_20532_, PATH, (player, system) -> {
            LushsaurContext ctx = new LushsaurContext((LivingEntity)player);
            StateMachineNode.Definition stateMachine = StateMachineNode.of().addState(ctx.action(ANIMATION_DEATH, 0.2f, l -> l.actionDeath)).addState(ctx.action(ANIMATION_ROAR, 0.2f, l -> l.actionRoar)).addState(ctx.action(ANIMATION_EAT, 0.2f, l -> l.actionEat)).addState(ctx.state(ANIMATION_REST, 0.2f, LushsaurEntity::isSitting)).addState(ctx.state(ANIMATION_HIDE, 0.2f, LushsaurEntity::isBuried)).addState(ctx.action(ANIMATION_UP, 0.2f, l -> l.actionEmerge)).addState(ctx.action(ANIMATION_DOWN, 0.2f, l -> l.actionBury)).addState(ctx.action(ANIMATION_UP, 0.2f, l -> l.actionUp)).addState(ctx.action(ANIMATION_DOWN, 0.2f, l -> l.actionDown)).addState(ctx.action(ANIMATION_ATTACK_TAIL_STOMP, 0.2f, l -> l.actionAttackTailStomp)).addState(ctx.action(ANIMATION_ATTACK_TAIL, 0.2f, l -> l.actionAttackTail)).addState(ctx.action(ANIMATION_ATTACK_HEADBUTT, 0.2f, l -> l.actionAttackHeadbutt)).addState(ctx.action(ANIMATION_ATTACK_BITE, 0.2f, l -> l.actionAttackBite)).addState(ctx.locomotionState(ANIMATION_RUN_ATTACK, 0.2f, LushsaurEntity::isCharging)).addState(ctx.locomotionState(ANIMATION_RUN, 0.2f, l -> l.getLocomotion().getRunLocomotion(0.0f, 0.3f) > 0.01f)).addState(ctx.state(ANIMATION_WALK, 0.2f, l -> l.m_20184_().m_165925_() > 1.0E-4)).addState((AbstractNode.Definition)ClipNode.of((String)ANIMATION_IDLE), 0.2f);
            system.addAnimationNode((AbstractNode.Definition)ModifierNode.of((AbstractNode.Definition)stateMachine).add(ADModifiers.mountPlayerModifier(ctx::getMount, 2.0f, 0.2f), new String[]{"root"}).add(Modifier.lookAt(), new String[]{"head"}), Weight.of(ctx::isRiding, (float)0.2f), priority).yieldsToVanilla();
        });
    }

    private record LushsaurContext(LivingEntity player) {
        public LushsaurEntity getMount() {
            LushsaurEntity lushsaur;
            Entity entity = this.player.m_20202_();
            return entity instanceof LushsaurEntity ? (lushsaur = (LushsaurEntity)entity) : null;
        }

        public boolean isRiding() {
            return this.player.m_20202_() instanceof LushsaurEntity;
        }

        public State state(String anim, float transitionTime, Predicate<LushsaurEntity> cond) {
            return State.of((AbstractNode.Definition)ClipNode.of((String)anim), (float)transitionTime, () -> {
                LushsaurEntity mount = this.getMount();
                return mount != null && cond.test(mount);
            });
        }

        public State action(String anim, float transitionTime, Function<LushsaurEntity, ActionState> getter) {
            Supplier<ActionState> supplier = () -> {
                LushsaurEntity mount = this.getMount();
                return mount != null ? (ActionState)getter.apply(mount) : null;
            };
            return State.of((AbstractNode.Definition)ClipNode.of((String)anim, (ProgressEvaluator)ActionEvaluator.of(supplier)), (float)transitionTime, () -> {
                ActionState a = (ActionState)supplier.get();
                return a != null && a.isActive();
            });
        }

        public State locomotionState(String anim, float transitionTime, Predicate<LushsaurEntity> cond) {
            return State.of((AbstractNode.Definition)ClipNode.of((String)anim, p -> {
                LushsaurEntity mount = this.getMount();
                return mount != null ? mount.getLocomotion().getRunLocomotion(p, 0.3f) : 0.0f;
            }), (float)transitionTime, () -> {
                LushsaurEntity mount = this.getMount();
                return mount != null && cond.test(mount);
            });
        }
    }
}

