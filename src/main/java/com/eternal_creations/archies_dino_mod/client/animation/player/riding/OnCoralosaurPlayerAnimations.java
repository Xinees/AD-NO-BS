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
import com.eternal_creations.archies_dino_mod.common.entity.mob.coralosaur.CoralosaurEntity;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;

public class OnCoralosaurPlayerAnimations {
    private static final ResourceLocation PATH = Constants.prefix("animations/player/riding/coralosaur");
    private static final String ANIMATION_IDLE = "coralosaur.idle";
    private static final String ANIMATION_WALK = "coralosaur.swim";
    private static final String ANIMATION_BITE = "coralosaur.bite_trigger";
    private static final String ANIMATION_GRAB = "coralosaur.grab_trigger";
    private static final String ANIMATION_DRAG_DOWN = "coralosaur.drag_down";
    private static final String ANIMATION_BURY = "coralosaur.bury";
    private static final String ANIMATION_SPECIAL = "coralosaur.special";
    private static final String ANIMATION_DEATH = "coralosaur.death";
    private static final String ANIMATION_VS_GRAB = "vs.coralosaur.grab_player";
    private static final String ANIMATION_VS_DRAG_DOWN = "vs.coralosaur.drag_down_player";
    private static final String ANIMATION_VS_BURY = "vs.coralosaur.bury_player";

    public static void init(int priority) {
        VirtualModelManager.INSTANCE.registerVirtualAnimations(EntityType.f_20532_, PATH, (player, system) -> {
            CoralosaurContext ctx = new CoralosaurContext((LivingEntity)player);
            StateMachineNode.Definition stateMachine = StateMachineNode.of().addState(ctx.action(ANIMATION_DEATH, 0.2f, c -> c.actionDeath)).addState(ctx.action(ANIMATION_SPECIAL, 0.2f, c -> c.actionSpecial)).addState(ctx.victimAction(ANIMATION_VS_GRAB, 0.2f, c -> c.actionGrab)).addState(ctx.victimAction(ANIMATION_VS_DRAG_DOWN, 0.2f, c -> c.actionDragDown)).addState(ctx.victimAction(ANIMATION_VS_BURY, 0.2f, c -> c.actionBury)).addState(ctx.riderAction(ANIMATION_GRAB, 0.2f, c -> c.actionGrab)).addState(ctx.riderAction(ANIMATION_DRAG_DOWN, 0.2f, c -> c.actionDragDown)).addState(ctx.riderAction(ANIMATION_BURY, 0.2f, c -> c.actionBury)).addState(ctx.action(ANIMATION_BITE, 0.2f, c -> c.actionBite)).addState(ctx.state(ANIMATION_WALK, 0.2f, c -> c.m_20184_().m_165925_() > 1.0E-4)).addState((AbstractNode.Definition)ClipNode.of((String)ANIMATION_IDLE), 0.2f);
            system.addAnimationNode((AbstractNode.Definition)ModifierNode.of((AbstractNode.Definition)stateMachine).add(ADModifiers.mountPlayerModifier(ctx::getMount, 2.0f, 0.2f), new String[]{"root"}).add(Modifier.lookAt(), new String[]{"head"}), Weight.of(ctx::isRiding, (float)0.5f), priority).yieldsToVanilla();
        });
    }

    private record CoralosaurContext(LivingEntity player) {
        public CoralosaurEntity getMount() {
            CoralosaurEntity coralosaur;
            Entity entity = this.player.m_20202_();
            return entity instanceof CoralosaurEntity ? (coralosaur = (CoralosaurEntity)entity) : null;
        }

        public boolean isRiding() {
            return this.player.m_20202_() instanceof CoralosaurEntity;
        }

        public boolean isVictim(CoralosaurEntity mount) {
            return mount.getVictim() == this.player.m_19879_();
        }

        public State state(String anim, float transitionTime, Predicate<CoralosaurEntity> cond) {
            return State.of((AbstractNode.Definition)ClipNode.of((String)anim), (float)transitionTime, () -> {
                CoralosaurEntity mount = this.getMount();
                return mount != null && cond.test(mount);
            });
        }

        public State action(String anim, float transitionTime, Function<CoralosaurEntity, ActionState> getter) {
            return this.actionWithFilter(anim, transitionTime, getter, mount -> true);
        }

        public State riderAction(String anim, float transitionTime, Function<CoralosaurEntity, ActionState> getter) {
            return this.actionWithFilter(anim, transitionTime, getter, mount -> !this.isVictim((CoralosaurEntity)mount));
        }

        public State victimAction(String anim, float transitionTime, Function<CoralosaurEntity, ActionState> getter) {
            return this.actionWithFilter(anim, transitionTime, getter, this::isVictim);
        }

        private State actionWithFilter(String anim, float transitionTime, Function<CoralosaurEntity, ActionState> getter, Predicate<CoralosaurEntity> filter) {
            Supplier<ActionState> supplier = () -> {
                CoralosaurEntity mount = this.getMount();
                return mount != null && filter.test(mount) ? (ActionState)getter.apply(mount) : null;
            };
            return State.of((AbstractNode.Definition)ClipNode.of((String)anim, (ProgressEvaluator)ActionEvaluator.of(supplier)), (float)transitionTime, () -> {
                ActionState a = (ActionState)supplier.get();
                return a != null && a.isActive();
            });
        }
    }
}

