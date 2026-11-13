/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.astemir.astemirlib.re.client.model.AstemirModel
 *  com.astemir.astemirlib.re.client.model.CommonRootOperations
 *  com.astemir.astemirlib.re.client.model.CommonRootOperations$RollOnRotation
 *  com.astemir.astemirlib.re.client.model.CommonRootOperations$SmoothBodyRotation
 *  com.astemir.astemirlib.re.client.model.core.RootOperation
 *  com.astemir.astemirlib.re.client.model.core.animation.evaluator.ProgressEvaluator
 *  com.astemir.astemirlib.re.client.model.core.animation.node.AbstractNode$Definition
 *  com.astemir.astemirlib.re.client.model.core.animation.node.ClipNode
 *  com.astemir.astemirlib.re.client.model.core.animation.node.EmptyNode
 *  com.astemir.astemirlib.re.client.model.core.animation.node.LayerNode
 *  com.astemir.astemirlib.re.client.model.core.animation.node.LayerNode$Definition
 *  com.astemir.astemirlib.re.client.model.core.animation.node.Weight
 *  com.astemir.astemirlib.re.client.model.core.animation.node.Weight$Definition
 *  com.astemir.astemirlib.re.client.model.core.animation.node.modifier.Modifier$Procedural
 *  com.astemir.astemirlib.re.client.model.core.animation.node.modifier.ModifierNode
 *  com.astemir.astemirlib.re.client.model.core.animation.node.state.State
 *  com.astemir.astemirlib.re.client.model.core.animation.node.state.StateMachineNode
 *  com.astemir.astemirlib.re.client.model.core.animation.node.state.StateMachineNode$Definition
 *  com.astemir.astemirlib.re.client.model.core.system.ModelSystem
 *  com.astemir.astemirlib.re.client.model.core.system.ModelSystem$Listener
 *  com.astemir.astemirlib.re.client.model.core.system.module.AnimatorModule
 *  com.astemir.astemirlib.re.client.model.core.texture.ModelTexture
 *  com.astemir.astemirlib.re.client.model.render.component.OverlayComponent
 *  com.astemir.astemirlib.re.client.model.render.renderer.AstemirEntityRenderer
 *  com.astemir.astemirlib.re.client.model.render.renderer.AstemirEntityRenderer$Provider
 *  com.astemir.astemirlib.re.client.model.render.renderer.layer.BaseRenderLayer
 *  com.astemir.astemirlib.re.client.model.render.renderer.layer.LayerContainer
 *  com.astemir.astemirlib.re.common.action.ActionState
 *  com.astemir.astemirlib.re.common.world.entity.EntityLocomotion
 *  com.astemir.astemirlib.re.core.function.Condition
 *  com.astemir.astemirlib.re.core.math.Easing
 *  com.astemir.astemirlib.re.core.math.Easing$Simple
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.world.entity.LivingEntity
 */
package com.eternal_creations.archies_dino_mod.client.model.render.entity.mob;

import com.astemir.astemirlib.re.client.model.AstemirModel;
import com.astemir.astemirlib.re.client.model.CommonRootOperations;
import com.astemir.astemirlib.re.client.model.core.RootOperation;
import com.astemir.astemirlib.re.client.model.core.animation.evaluator.ProgressEvaluator;
import com.astemir.astemirlib.re.client.model.core.animation.node.AbstractNode;
import com.astemir.astemirlib.re.client.model.core.animation.node.ClipNode;
import com.astemir.astemirlib.re.client.model.core.animation.node.EmptyNode;
import com.astemir.astemirlib.re.client.model.core.animation.node.LayerNode;
import com.astemir.astemirlib.re.client.model.core.animation.node.Weight;
import com.astemir.astemirlib.re.client.model.core.animation.node.modifier.Modifier;
import com.astemir.astemirlib.re.client.model.core.animation.node.modifier.ModifierNode;
import com.astemir.astemirlib.re.client.model.core.animation.node.state.State;
import com.astemir.astemirlib.re.client.model.core.animation.node.state.StateMachineNode;
import com.astemir.astemirlib.re.client.model.core.system.ModelSystem;
import com.astemir.astemirlib.re.client.model.core.system.module.AnimatorModule;
import com.astemir.astemirlib.re.client.model.core.texture.ModelTexture;
import com.astemir.astemirlib.re.client.model.render.component.OverlayComponent;
import com.astemir.astemirlib.re.client.model.render.renderer.AstemirEntityRenderer;
import com.astemir.astemirlib.re.client.model.render.renderer.layer.BaseRenderLayer;
import com.astemir.astemirlib.re.client.model.render.renderer.layer.LayerContainer;
import com.astemir.astemirlib.re.common.action.ActionState;
import com.astemir.astemirlib.re.common.world.entity.EntityLocomotion;
import com.astemir.astemirlib.re.core.function.Condition;
import com.astemir.astemirlib.re.core.math.Easing;
import com.eternal_creations.archies_dino_mod.client.model.registry.ADEntityModels;
import com.eternal_creations.archies_dino_mod.client.particle.effect.SculkraptorEffects;
import com.eternal_creations.archies_dino_mod.common.entity.mob.sculkraptor.SculkraptorEntity;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.LivingEntity;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class SculkraptorClient
implements ModelSystem.Listener<SculkraptorEntity> {
    private static final String MOTEL_TEXTURE = "sculkraptor";
    private static final String IDLE_ANIMATION = "animation.ec_ad.sculkraptor.idle";
    private static final String WALK_ANIMATION = "animation.ec_ad.sculkraptor.walk";
    private static final String RUN_ANIMATION = "animation.ec_ad.sculkraptor.run";
    private static final String SNIFF_ANIMATION = "animation.ec_ad.sculkraptor.sniff";
    private static final String EXPLOSION_ANIMATION = "animation.ec_ad.sculkraptor.scream";
    private static final String DEATH_ANIMATION = "animation.ec_ad.sculkraptor.death";
    private static final String BITE_ANIMATION = "animation.ec_ad.sculkraptor.bite";
    private static final String RANGED_ANIMATION = "animation.ec_ad.sculkraptor.ranged";
    private static final String HEAD_BONE = "head";

    public void onInitialize(ModelSystem<SculkraptorEntity> modelSystem, SculkraptorEntity entity) {
        modelSystem.addRootOperation(new RootOperation[]{CommonRootOperations.SHAKING, CommonRootOperations.SmoothBodyRotation.of((float)0.3f), CommonRootOperations.RollOnRotation.of((float)15.0f, (float)1.2f, (float)0.3f), CommonRootOperations.GRUMM_ROTATION});
        this.setupAnimations(modelSystem, entity);
    }

    private void setupAnimations(ModelSystem<SculkraptorEntity> modelSystem, SculkraptorEntity entity) {
        LayerNode.Definition rootState = this.buildRootState(entity);
        ((AnimatorModule)modelSystem.addModule(AnimatorModule::new)).setRoot((AbstractNode.Definition)ModifierNode.of((AbstractNode.Definition)rootState).add(Modifier.Procedural.lookAtOrganic((LivingEntity)entity, (float)10.0f, (float)0.1f, (float)45.0f, (float)65.0f), new String[]{HEAD_BONE})).setDefaultEasing((Easing)Easing.Simple.SINE_IN, (Easing)Easing.Simple.SINE_OUT);
    }

    private LayerNode.Definition buildRootState(SculkraptorEntity entity) {
        StateMachineNode.Definition rootStateMachine = StateMachineNode.of().addState((AbstractNode.Definition)ClipNode.of((String)DEATH_ANIMATION, (ActionState)entity.actionDeath), () -> ((ActionState)entity.actionDeath).isActive()).addState((AbstractNode.Definition)ClipNode.of((String)BITE_ANIMATION, (ActionState)entity.actionAttackBiteStand), () -> ((ActionState)entity.actionAttackBiteStand).isActive()).addState((AbstractNode.Definition)ClipNode.of((String)RANGED_ANIMATION, (ActionState)entity.actionAttackRanged).events(SculkraptorEffects.RANGED_ATTACK_EFFECTS), () -> ((ActionState)entity.actionAttackRanged).isActive()).addState(EmptyNode.of(), 0.2f);
        return LayerNode.of((AbstractNode.Definition)this.buildActionState(entity), (AbstractNode.Definition)rootStateMachine, (Weight.Definition)Weight.combine((Weight.Definition[])new Weight.Definition[]{entity.actionDeath.weight(), entity.actionAttackBiteStand.weight(), entity.actionAttackRanged.weight()}));
    }

    private LayerNode.Definition buildActionState(SculkraptorEntity entity) {
        StateMachineNode.Definition actionStateMachine = StateMachineNode.of().addState((AbstractNode.Definition)ClipNode.of((String)EXPLOSION_ANIMATION, (ActionState)entity.actionAttackSonicBoom), () -> ((ActionState)entity.actionAttackSonicBoom).isActive()).addState((AbstractNode.Definition)ClipNode.of((String)SNIFF_ANIMATION, (ActionState)entity.actionSniff), () -> ((ActionState)entity.actionSniff).isActive()).addState(EmptyNode.of(), 0.2f);
        return LayerNode.of((AbstractNode.Definition)this.buildLocomotionState(entity), (AbstractNode.Definition)actionStateMachine, (Weight.Definition)Weight.combine((Weight.Definition[])new Weight.Definition[]{entity.actionAttackSonicBoom.weight(), entity.actionSniff.weight()}));
    }

    private LayerNode.Definition buildLocomotionState(SculkraptorEntity entity) {
        EntityLocomotion locomotion = entity.getLocomotion();
        StateMachineNode.Definition walkState = StateMachineNode.of((State[])new State[]{State.of((AbstractNode.Definition)ClipNode.of((String)RUN_ANIMATION, (ProgressEvaluator)locomotion.runEvaluator(3.5f)).events(SculkraptorEffects.RUN_EFFECTS), (float)0.2f, (Condition)locomotion.whenRunning()), State.of((AbstractNode.Definition)ClipNode.of((String)WALK_ANIMATION, (ProgressEvaluator)locomotion.walkEvaluator(3.5f)).events(SculkraptorEffects.WALK_EFFECTS), (float)0.2f)});
        return LayerNode.of((AbstractNode.Definition)ClipNode.of((String)IDLE_ANIMATION), (AbstractNode.Definition)walkState, (Weight.Definition)locomotion.walkWeight());
    }

    public static void setupModel(AstemirModel<SculkraptorEntity> model, LayerContainer<SculkraptorEntity> layers) {
        layers.add(BaseRenderLayer.of((ModelTexture)ModelTexture.of(model, (String)MOTEL_TEXTURE, RenderType::m_110458_)));
    }

    public static AstemirEntityRenderer.Provider<SculkraptorEntity> createRenderProvider() {
        return AstemirEntityRenderer.of(entity -> entity.m_6162_() ? ADEntityModels.SCULKRAPTOR_BABY : ADEntityModels.SCULKRAPTOR, SculkraptorClient::new).overlay(OverlayComponent.livingEntityNoDeathOverlay()).shadow(0.65f);
    }
}

