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
import com.eternal_creations.archies_dino_mod.common.entity.mob.chorusoatl.ChorusoatlEntity;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;

public class OnChorusoatlPlayerAnimations {
    private static final ResourceLocation PATH = Constants.prefix("animations/player/riding/chorusoatl");
    private static final String ANIMATION_IDLE = "chorusoatl.idle";
    private static final String ANIMATION_WALK = "chorusoatl.walk";
    private static final String ANIMATION_FLY = "chorusoatl.fly1";
    private static final String ANIMATION_GLIDE = "chorusoatl.fly2";
    private static final String ANIMATION_UP = "chorusoatl.up";
    private static final String ANIMATION_LAND = "chorusoatl.land";
    private static final String ANIMATION_DEATH_GROUND = "chorusoatl.death_ground";
    private static final String ANIMATION_DEATH_AIR_INITIAL = "chorusoatl.death_air_initial";
    private static final String ANIMATION_BITE_ATTACK = "chorusoatl.attack1_trigger";
    private static final String ANIMATION_HIT_ATTACK = "chorusoatl.attack2_trigger";
    private static final String ANIMATION_SHOOT_ATTACK = "chorusoatl.attack3_trigger";
    private static final String ANIMATION_FLY_ATTACK = "chorusoatl.fly_attack_trigger";
    private static final String ANIMATION_SCREAM = "chorusoatl.scream";

    public static void init(int priority) {
        VirtualModelManager.INSTANCE.registerVirtualAnimations(EntityType.f_20532_, PATH, (player, system) -> {
            ChorusoatlContext ctx = new ChorusoatlContext((LivingEntity)player);
            StateMachineNode.Definition stateMachine = StateMachineNode.of().addState(ctx.action(ANIMATION_DEATH_GROUND, 0.2f, c -> c.actionDeathGround)).addState(ctx.state(ANIMATION_DEATH_AIR_INITIAL, 0.2f, ChorusoatlEntity::isFallingDead)).addState(ctx.action(ANIMATION_FLY_ATTACK, 0.2f, c -> c.actionAttackFly)).addState(ctx.action(ANIMATION_BITE_ATTACK, 0.2f, c -> c.actionAttackBite)).addState(ctx.action(ANIMATION_HIT_ATTACK, 0.2f, c -> c.actionAttackHit)).addState(ctx.action(ANIMATION_SHOOT_ATTACK, 0.2f, c -> c.actionAttackShoot)).addState(ctx.action(ANIMATION_SCREAM, 0.2f, c -> c.actionScream)).addState(ctx.action(ANIMATION_UP, 0.2f, c -> c.actionUp)).addState(ctx.action(ANIMATION_LAND, 0.2f, c -> c.actionDown)).addState(ctx.state(ANIMATION_GLIDE, 0.2f, ChorusoatlEntity::isGliding)).addState(ctx.state(ANIMATION_FLY, 0.2f, ChorusoatlEntity::isFlying)).addState(ctx.state(ANIMATION_WALK, 0.2f, c -> !c.isFlying() && c.m_20184_().m_165925_() > 1.0E-4)).addState((AbstractNode.Definition)ClipNode.of((String)ANIMATION_IDLE), 0.2f);
            system.addAnimationNode((AbstractNode.Definition)ModifierNode.of((AbstractNode.Definition)stateMachine).add(ADModifiers.mountPlayerModifier(ctx::getMount, 2.0f, 0.2f), new String[]{"root"}).add(Modifier.lookAt(), new String[]{"head"}), Weight.of(ctx::isRiding, (float)0.5f), priority).yieldsToVanilla();
        });
    }

    private record ChorusoatlContext(LivingEntity player) {
        public ChorusoatlEntity getMount() {
            ChorusoatlEntity chorusoatl;
            Entity entity = this.player.m_20202_();
            return entity instanceof ChorusoatlEntity ? (chorusoatl = (ChorusoatlEntity)entity) : null;
        }

        public boolean isRiding() {
            return this.player.m_20202_() instanceof ChorusoatlEntity;
        }

        public State state(String anim, float transitionTime, Predicate<ChorusoatlEntity> cond) {
            return State.of((AbstractNode.Definition)ClipNode.of((String)anim), (float)transitionTime, () -> {
                ChorusoatlEntity mount = this.getMount();
                return mount != null && cond.test(mount);
            });
        }

        public State action(String anim, float transitionTime, Function<ChorusoatlEntity, ActionState> getter) {
            Supplier<ActionState> supplier = () -> {
                ChorusoatlEntity mount = this.getMount();
                return mount != null ? (ActionState)getter.apply(mount) : null;
            };
            return State.of((AbstractNode.Definition)ClipNode.of((String)anim, (ProgressEvaluator)ActionEvaluator.of(supplier)), (float)transitionTime, () -> {
                ActionState a = (ActionState)supplier.get();
                return a != null && a.isActive();
            });
        }
    }
}

