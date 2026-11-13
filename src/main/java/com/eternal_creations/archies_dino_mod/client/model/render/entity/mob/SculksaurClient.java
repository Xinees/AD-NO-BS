/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.astemir.astemirlib.re.client.model.AstemirModel
 *  com.astemir.astemirlib.re.client.model.CommonRootOperations
 *  com.astemir.astemirlib.re.client.model.CommonRootOperations$RollOnRotation
 *  com.astemir.astemirlib.re.client.model.CommonRootOperations$SmoothBodyRotation
 *  com.astemir.astemirlib.re.client.model.core.BoneMask
 *  com.astemir.astemirlib.re.client.model.core.RootOperation
 *  com.astemir.astemirlib.re.client.model.core.animation.evaluator.ProgressEvaluator
 *  com.astemir.astemirlib.re.client.model.core.animation.node.AbstractNode$Definition
 *  com.astemir.astemirlib.re.client.model.core.animation.node.ClipNode
 *  com.astemir.astemirlib.re.client.model.core.animation.node.ClipNode$Definition
 *  com.astemir.astemirlib.re.client.model.core.animation.node.EmptyNode
 *  com.astemir.astemirlib.re.client.model.core.animation.node.LayerNode
 *  com.astemir.astemirlib.re.client.model.core.animation.node.LayerNode$Definition
 *  com.astemir.astemirlib.re.client.model.core.animation.node.Weight
 *  com.astemir.astemirlib.re.client.model.core.animation.node.Weight$Definition
 *  com.astemir.astemirlib.re.client.model.core.animation.node.modifier.Modifier
 *  com.astemir.astemirlib.re.client.model.core.animation.node.modifier.Modifier$Procedural
 *  com.astemir.astemirlib.re.client.model.core.animation.node.modifier.ModifierNode
 *  com.astemir.astemirlib.re.client.model.core.animation.node.modifier.ModifierNode$Definition
 *  com.astemir.astemirlib.re.client.model.core.animation.node.state.State
 *  com.astemir.astemirlib.re.client.model.core.animation.node.state.StateMachineNode
 *  com.astemir.astemirlib.re.client.model.core.animation.node.state.StateMachineNode$Definition
 *  com.astemir.astemirlib.re.client.model.core.system.ModelSystem
 *  com.astemir.astemirlib.re.client.model.core.system.ModelSystem$Listener
 *  com.astemir.astemirlib.re.client.model.core.system.module.AnimatorModule
 *  com.astemir.astemirlib.re.client.model.core.system.module.LegSolverModule
 *  com.astemir.astemirlib.re.client.model.core.system.module.PhysicsModule
 *  com.astemir.astemirlib.re.client.model.core.texture.ModelTexture
 *  com.astemir.astemirlib.re.client.model.render.component.BufferComponent
 *  com.astemir.astemirlib.re.client.model.render.component.ColorComponent
 *  com.astemir.astemirlib.re.client.model.render.component.OverlayComponent
 *  com.astemir.astemirlib.re.client.model.render.component.ShadowComponent
 *  com.astemir.astemirlib.re.client.model.render.renderer.AstemirEntityRenderer
 *  com.astemir.astemirlib.re.client.model.render.renderer.AstemirEntityRenderer$Provider
 *  com.astemir.astemirlib.re.client.model.render.renderer.layer.BaseRenderLayer
 *  com.astemir.astemirlib.re.client.model.render.renderer.layer.LayerContainer
 *  com.astemir.astemirlib.re.client.shader.post.PostRenderType
 *  com.astemir.astemirlib.re.client.shader.post.pass.BloomPass
 *  com.astemir.astemirlib.re.common.action.ActionState
 *  com.astemir.astemirlib.re.common.world.entity.EntityLocomotion
 *  com.astemir.astemirlib.re.core.function.Condition
 *  com.astemir.astemirlib.re.core.math.AMath
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
import com.astemir.astemirlib.re.client.model.core.BoneMask;
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
import com.astemir.astemirlib.re.client.model.core.system.module.LegSolverModule;
import com.astemir.astemirlib.re.client.model.core.system.module.PhysicsModule;
import com.astemir.astemirlib.re.client.model.core.texture.ModelTexture;
import com.astemir.astemirlib.re.client.model.render.component.BufferComponent;
import com.astemir.astemirlib.re.client.model.render.component.ColorComponent;
import com.astemir.astemirlib.re.client.model.render.component.OverlayComponent;
import com.astemir.astemirlib.re.client.model.render.component.ShadowComponent;
import com.astemir.astemirlib.re.client.model.render.renderer.AstemirEntityRenderer;
import com.astemir.astemirlib.re.client.model.render.renderer.layer.BaseRenderLayer;
import com.astemir.astemirlib.re.client.model.render.renderer.layer.LayerContainer;
import com.astemir.astemirlib.re.client.shader.post.PostRenderType;
import com.astemir.astemirlib.re.client.shader.post.pass.BloomPass;
import com.astemir.astemirlib.re.common.action.ActionState;
import com.astemir.astemirlib.re.common.world.entity.EntityLocomotion;
import com.astemir.astemirlib.re.core.function.Condition;
import com.astemir.astemirlib.re.core.math.AMath;
import com.astemir.astemirlib.re.core.math.Easing;
import com.eternal_creations.archies_dino_mod.client.model.registry.ADEntityModels;
import com.eternal_creations.archies_dino_mod.client.model.render.ADRenderTypes;
import com.eternal_creations.archies_dino_mod.client.particle.effect.SculksaurEffects;
import com.eternal_creations.archies_dino_mod.common.entity.mob.sculksaur.SculksaurEntity;
import java.util.function.Function;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.LivingEntity;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class SculksaurClient
implements ModelSystem.Listener<SculksaurEntity> {
    private static final String NORMAL_TEXTURE = "main";
    private static final String OVERLAY_TEXTURE = "glow";
    private static final String SPAWN_ANIMATION = "animation.ec_ad.sculksaur.spawn";
    private static final String IDLE_ANIMATION = "animation.ec_ad.sculksaur.idle";
    private static final String WALK_ANIMATION = "animation.ec_ad.sculksaur.walk";
    private static final String RUN_ANIMATION = "animation.ec_ad.sculksaur.run3";
    private static final String DEATH_ANIMATION = "animation.ec_ad.sculksaur.death";
    private static final String TENDRILS_ANIMATION = "animation.ec_ad.sculksaur.tendrils";
    private static final String LISTEN_ANIMATION = "animation.ec_ad.sculksaur.listen";
    private static final String ROAR_ANIMATION = "animation.ec_ad.sculksaur.roar";
    private static final String EXPLOSION_ANIMATION = "animation.ec_ad.sculksaur.explosion";
    private static final String RUN_BITE_ANIMATION = "animation.ec_ad.sculksaur.run2";
    private static final String BITE_ANIMATION = "animation.ec_ad.sculksaur.attack";
    private static final String TAIL_ANIMATION = "animation.ec_ad.sculksaur.tail_attack";

    public void onInitialize(ModelSystem<SculksaurEntity> modelSystem, SculksaurEntity entity) {
        modelSystem.addRootOperation(new RootOperation[]{CommonRootOperations.SHAKING, CommonRootOperations.SmoothBodyRotation.of((float)0.3f), CommonRootOperations.RollOnRotation.of((float)15.0f, (float)0.8f, (float)0.2f), CommonRootOperations.GRUMM_ROTATION});
        this.setupAnimations(modelSystem, entity);
        this.setupPhysics(modelSystem, entity);
    }

    private void setupAnimations(ModelSystem<SculksaurEntity> modelSystem, SculksaurEntity entity) {
        ModifierNode.Definition root = ModifierNode.of((AbstractNode.Definition)this.buildTendrilsLayer(entity)).add(Modifier.Procedural.lookAtOrganic((LivingEntity)entity, (float)10.0f, (float)0.1f, (float)45.0f, (float)65.0f), new String[]{"head"});
        int i = 0;
        while (i < 6) {
            int group = i++;
            root.add(Modifier.visibility(() -> entity.hasWartGroup(group)), BoneMask.exact((String[])((String[])SculksaurEntity.getWartGroup(group).names().toArray(String[]::new))));
        }
        ((AnimatorModule)modelSystem.addModule(AnimatorModule::new)).setRoot((AbstractNode.Definition)root).setDefaultEasing((Easing)Easing.Simple.SINE_IN, (Easing)Easing.Simple.SINE_OUT);
    }

    private LayerNode.Definition buildTendrilsLayer(SculksaurEntity entity) {
        ClipNode.Definition tendrilsClip = ClipNode.of((String)TENDRILS_ANIMATION, (ActionState)entity.actionSense).events(SculksaurEffects.TENDRILS_EFFECTS);
        return LayerNode.of((AbstractNode.Definition)this.buildRootState(entity), (AbstractNode.Definition)tendrilsClip, (Weight.Definition)entity.actionSense.weight());
    }

    private LayerNode.Definition buildRootState(SculksaurEntity entity) {
        StateMachineNode.Definition rootStateMachine = StateMachineNode.of().addState((AbstractNode.Definition)ClipNode.of((String)SPAWN_ANIMATION, (ActionState)entity.actionSpawn).events(SculksaurEffects.SPAWN_EFFECTS), () -> ((ActionState)entity.actionSpawn).isActive()).addState((AbstractNode.Definition)ClipNode.of((String)DEATH_ANIMATION, (ActionState)entity.actionDeath).events(SculksaurEffects.DEATH_EFFECTS), () -> ((ActionState)entity.actionDeath).isActive()).addState((AbstractNode.Definition)ClipNode.of((String)BITE_ANIMATION, (ActionState)entity.actionAttackBiteStand), () -> ((ActionState)entity.actionAttackBiteStand).isActive()).addState((AbstractNode.Definition)ClipNode.of((String)TAIL_ANIMATION, (ActionState)entity.actionAttackTail).events(SculksaurEffects.TAIL_EFFECTS), () -> ((ActionState)entity.actionAttackTail).isActive()).addState((AbstractNode.Definition)ClipNode.of((String)EXPLOSION_ANIMATION, (ActionState)entity.actionAttackSonicBoom).events(SculksaurEffects.EXPLOSION_EFFECTS), () -> ((ActionState)entity.actionAttackSonicBoom).isActive()).addState((AbstractNode.Definition)ClipNode.of((String)ROAR_ANIMATION, (ActionState)entity.actionRoar).events(SculksaurEffects.ROAR_EFFECTS), () -> ((ActionState)entity.actionRoar).isActive()).addState(EmptyNode.of(), 0.5f);
        return LayerNode.of((AbstractNode.Definition)this.buildActionState(entity), (AbstractNode.Definition)rootStateMachine, (Weight.Definition)Weight.combine((Weight.Definition[])new Weight.Definition[]{entity.actionSpawn.weight(), entity.actionDeath.weight(), entity.actionAttackBiteStand.weight(), entity.actionAttackTail.weight(), entity.actionAttackSonicBoom.weight(), entity.actionRoar.weight()}));
    }

    private LayerNode.Definition buildActionState(SculksaurEntity entity) {
        StateMachineNode.Definition actionStateMachine = StateMachineNode.of().addState((AbstractNode.Definition)ClipNode.of((String)RUN_BITE_ANIMATION, (ActionState)entity.actionAttackBiteRun), () -> ((ActionState)entity.actionAttackBiteRun).isActive()).addState((AbstractNode.Definition)ClipNode.of((String)LISTEN_ANIMATION, (ActionState)entity.actionListen).events(SculksaurEffects.LISTEN_EFFECTS), () -> ((ActionState)entity.actionListen).isActive()).addState(EmptyNode.of(), 0.5f);
        return LayerNode.of((AbstractNode.Definition)this.buildLocomotionState(entity), (AbstractNode.Definition)actionStateMachine, (Weight.Definition)Weight.combine((Weight.Definition[])new Weight.Definition[]{entity.actionAttackBiteRun.weight(), entity.actionListen.weight(), entity.actionSense.weight()}));
    }

    private LayerNode.Definition buildLocomotionState(SculksaurEntity entity) {
        EntityLocomotion locomotion = entity.getLocomotion();
        StateMachineNode.Definition walkState = StateMachineNode.of((State[])new State[]{State.of((AbstractNode.Definition)ClipNode.of((String)RUN_ANIMATION, (ProgressEvaluator)locomotion.runEvaluator(3.0f)).events(SculksaurEffects.RUN_EFFECTS), (float)0.2f, (Condition)locomotion.whenRunning()), State.of((AbstractNode.Definition)ClipNode.of((String)WALK_ANIMATION, (ProgressEvaluator)locomotion.walkEvaluator(3.5f)).events(SculksaurEffects.WALK_EFFECTS), (float)0.2f)});
        return LayerNode.of((AbstractNode.Definition)ClipNode.of((String)IDLE_ANIMATION), (AbstractNode.Definition)walkState, (Weight.Definition)locomotion.walkWeight());
    }

    private void setupPhysics(ModelSystem<SculksaurEntity> modelSystem, SculksaurEntity entity) {
        PhysicsModule physics = (PhysicsModule)modelSystem.addModule(PhysicsModule::new);
        LegSolverModule legSolverModule = (LegSolverModule)modelSystem.addModule(LegSolverModule::new);
        legSolverModule.addLeg("leg_left", "foot_ground_left", 1.5f);
        legSolverModule.addLeg("leg_right", "foot_ground_right", 1.5f);
        EntityLocomotion locomotion = entity.getLocomotion();
        physics.createChain(() -> ((EntityLocomotion)locomotion).getLocalMovement(), () -> ((EntityLocomotion)locomotion).getTurnSpeed()).addBone("tail1", physics.createProperties()).addBone("tail2", physics.createProperties()).restOffset(0.0f, 0.15f, 1.0f).inertia(0.0f, 0.4f).gravity(-0.03f).stiffness(0.15f);
    }

    public static void setupModel(AstemirModel<SculksaurEntity> model, LayerContainer<SculksaurEntity> layers) {
        layers.add(BaseRenderLayer.of((ModelTexture)ModelTexture.of(model, (String)NORMAL_TEXTURE, RenderType::m_110458_)));
        layers.add(BaseRenderLayer.of((ModelTexture)ModelTexture.of(model, (String)OVERLAY_TEXTURE, ADRenderTypes::eyesTranslucent)).color((ColorComponent)ColorComponent.of().alpha((entity, partialTick) -> {
            float animTick = (float)entity.f_19797_ + partialTick;
            if (entity.actionRoar.isActive()) {
                float time = entity.actionRoar.getTime(partialTick);
                float startTime = 0.7f;
                float endTime = 4.7f;
                if (time >= 0.7f && time <= 4.7f) {
                    float envelope;
                    float progress = (time - 0.7f) / 3.9999998f;
                    if (progress < 0.15f) {
                        float inT = progress / 0.15f;
                        envelope = Easing.Simple.CUBIC_OUT.ease(inT);
                    } else if (progress > 0.8f) {
                        float outT = (1.0f - progress) / 0.2f;
                        envelope = Easing.Simple.SINE_IN.ease(outT);
                    } else {
                        envelope = 1.0f;
                    }
                    float ripple = 0.85f + 0.15f * AMath.sin((float)(animTick * 1.2f));
                    return AMath.clamp((float)(envelope * ripple), (float)0.0f, (float)1.0f);
                }
            }
            float idlePulse = AMath.cos((float)(animTick * 0.06f)) * 0.35f + 0.45f;
            return AMath.clamp((float)idlePulse, (float)0.0f, (float)1.0f);
        })).buffer(BufferComponent.postEffect((Function)PostRenderType.MODEL_UNLIT, (boolean)true, (Class[])new Class[]{BloomPass.class})));
    }

    public static AstemirEntityRenderer.Provider<SculksaurEntity> createRenderProvider() {
        return AstemirEntityRenderer.of(entity -> ADEntityModels.SCULKSAUR, SculksaurClient::new).overlay(OverlayComponent.livingEntityNoDeathOverlay()).shadow((ShadowComponent)ShadowComponent.of((entity, partialTick) -> entity.shadowRadius.get(partialTick), (entity, partialTick) -> entity.isEmerging() || entity.m_21224_() ? 0.0f : 1.0f));
    }
}

