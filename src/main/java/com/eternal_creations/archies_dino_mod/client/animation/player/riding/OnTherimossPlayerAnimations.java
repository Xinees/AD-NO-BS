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
import com.eternal_creations.archies_dino_mod.common.entity.mob.therimoss.TherimossEntity;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;

public class OnTherimossPlayerAnimations {
    private static final ResourceLocation PATH = Constants.prefix("animations/player/riding/therimoss");
    private static final String ANIMATION_IDLE = "therimoss.idle";
    private static final String ANIMATION_WALK = "therimoss.walk";
    private static final String ANIMATION_RUN = "therimoss.run";
    private static final String ANIMATION_CLIMB_IDLE = "therimoss.climb_idle";
    private static final String ANIMATION_CLIMB_UP = "therimoss.climb_up";
    private static final String ANIMATION_CLIMB_DOWN = "therimoss.climb_down";
    private static final String ANIMATION_CLIMB_START = "therimoss.climb_start";
    private static final String ANIMATION_CLIMB_STOP = "therimoss.climb_stop";
    private static final String ANIMATION_CLIMB_FINISH = "therimoss.climb_finish";
    private static final String ANIMATION_EAT = "therimoss_player.eat";
    private static final String ANIMATION_JUMP = "therimoss.jump";
    private static final String ANIMATION_FALL_IDLE = "therimoss.fall_idle";
    private static final String ANIMATION_LAND = "therimoss.land";
    private static final String ANIMATION_MELEE_TRIGGER = "therimoss.melee_trigger";
    private static final String ANIMATION_PLAYER_MELEE_AUTO = "therimoss.player_melee_auto";
    private static final String ANIMATION_BEAK = "therimoss.beak";
    private static final String ANIMATION_THROW_TRIGGER = "therimoss.throw_trigger";
    private static final String ANIMATION_THROW_AUTO = "therimoss.throw_auto";
    private static final String ANIMATION_DEATH = "therimoss.death";

    public static void init(int priority) {
        VirtualModelManager.INSTANCE.registerVirtualAnimations(EntityType.f_20532_, PATH, (player, system) -> {
            TherimossContext ctx = new TherimossContext((LivingEntity)player);
            StateMachineNode.Definition stateMachine = StateMachineNode.of().addState(ctx.action(ANIMATION_DEATH, 0.2f, t -> t.actions.death)).addState(ctx.action(ANIMATION_THROW_TRIGGER, 0.2f, t -> t.actions.attackShoot)).addState(ctx.action(ANIMATION_MELEE_TRIGGER, 0.2f, t -> t.actions.attackHit)).addState(ctx.action(ANIMATION_BEAK, 0.2f, t -> t.actions.attackBite)).addState(ctx.action(ANIMATION_EAT, 0.2f, t -> t.actions.eat)).addState(ctx.action(ANIMATION_JUMP, 0.2f, t -> t.actions.jump)).addState(ctx.action(ANIMATION_LAND, 0.2f, t -> t.actions.land)).addState(ctx.state(ANIMATION_FALL_IDLE, 0.2f, TherimossEntity::isFalling)).addState(ctx.action(ANIMATION_CLIMB_START, 0.2f, t -> t.actions.climbStart)).addState(ctx.action(ANIMATION_CLIMB_STOP, 0.2f, t -> t.actions.climbStop)).addState(ctx.action(ANIMATION_CLIMB_FINISH, 0.2f, t -> t.actions.climbFinish)).addState(ctx.state(ANIMATION_CLIMB_UP, 0.2f, t -> t.isClimbing() && t.climbVelocity > 0.05f)).addState(ctx.state(ANIMATION_CLIMB_DOWN, 0.2f, t -> t.isClimbing() && t.climbVelocity < -0.05f)).addState(ctx.state(ANIMATION_CLIMB_IDLE, 0.2f, TherimossEntity::isClimbing)).addState(ctx.locomotionState(ANIMATION_RUN, 0.2f, t -> t.getLocomotion().getRunLocomotion(0.0f, 0.3f) > 0.01f)).addState(ctx.state(ANIMATION_WALK, 0.2f, t -> t.m_20184_().m_165925_() > 1.0E-4)).addState((AbstractNode.Definition)ClipNode.of((String)ANIMATION_IDLE), 0.2f);
            system.addAnimationNode((AbstractNode.Definition)ModifierNode.of((AbstractNode.Definition)stateMachine).add(ADModifiers.mountPlayerModifier(ctx::getMount, 2.0f, 0.2f), new String[]{"root"}).add(Modifier.lookAt(), new String[]{"head"}), Weight.of(ctx::isRiding, (float)0.5f), priority).yieldsToVanilla();
        });
    }

    private record TherimossContext(LivingEntity player) {
        public TherimossEntity getMount() {
            TherimossEntity therimoss;
            Entity entity = this.player.m_20202_();
            return entity instanceof TherimossEntity ? (therimoss = (TherimossEntity)entity) : null;
        }

        public boolean isRiding() {
            return this.player.m_20202_() instanceof TherimossEntity;
        }

        public State state(String anim, float transitionTime, Predicate<TherimossEntity> cond) {
            return State.of((AbstractNode.Definition)ClipNode.of((String)anim), (float)transitionTime, () -> {
                TherimossEntity mount = this.getMount();
                return mount != null && cond.test(mount);
            });
        }

        public State action(String anim, float transitionTime, Function<TherimossEntity, ActionState> getter) {
            Supplier<ActionState> supplier = () -> {
                TherimossEntity mount = this.getMount();
                return mount != null ? (ActionState)getter.apply(mount) : null;
            };
            return State.of((AbstractNode.Definition)ClipNode.of((String)anim, (ProgressEvaluator)ActionEvaluator.of(supplier)), (float)transitionTime, () -> {
                ActionState a = (ActionState)supplier.get();
                return a != null && a.isActive();
            });
        }

        public State locomotionState(String anim, float transitionTime, Predicate<TherimossEntity> cond) {
            return State.of((AbstractNode.Definition)ClipNode.of((String)anim, p -> {
                TherimossEntity mount = this.getMount();
                return mount != null ? mount.getLocomotion().getRunLocomotion(p, 0.3f) : 0.0f;
            }), (float)transitionTime, () -> {
                TherimossEntity mount = this.getMount();
                return mount != null && cond.test(mount);
            });
        }
    }
}

