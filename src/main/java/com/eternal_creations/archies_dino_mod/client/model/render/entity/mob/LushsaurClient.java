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
 *  com.astemir.astemirlib.re.client.model.core.animation.node.modifier.Modifier
 *  com.astemir.astemirlib.re.client.model.core.animation.node.modifier.Modifier$Procedural
 *  com.astemir.astemirlib.re.client.model.core.animation.node.modifier.ModifierNode
 *  com.astemir.astemirlib.re.client.model.core.animation.node.state.State
 *  com.astemir.astemirlib.re.client.model.core.animation.node.state.StateMachineNode
 *  com.astemir.astemirlib.re.client.model.core.animation.node.state.StateMachineNode$Definition
 *  com.astemir.astemirlib.re.client.model.core.system.ModelSystem
 *  com.astemir.astemirlib.re.client.model.core.system.module.AnimatorModule
 *  com.astemir.astemirlib.re.client.model.core.system.module.PhysicsModule
 *  com.astemir.astemirlib.re.client.model.core.system.module.PhysicsModule$PhysicsProperties
 *  com.astemir.astemirlib.re.client.model.core.system.module.PhysicsModule$PhysicsProperties$RotationAxes
 *  com.astemir.astemirlib.re.client.model.core.texture.ModelTexture
 *  com.astemir.astemirlib.re.client.model.render.component.OverlayComponent
 *  com.astemir.astemirlib.re.client.model.render.component.ShadowComponent
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
 *  net.minecraft.world.phys.Vec3
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
import com.astemir.astemirlib.re.client.model.core.system.module.PhysicsModule;
import com.astemir.astemirlib.re.client.model.core.texture.ModelTexture;
import com.astemir.astemirlib.re.client.model.render.component.OverlayComponent;
import com.astemir.astemirlib.re.client.model.render.component.ShadowComponent;
import com.astemir.astemirlib.re.client.model.render.renderer.AstemirEntityRenderer;
import com.astemir.astemirlib.re.client.model.render.renderer.layer.BaseRenderLayer;
import com.astemir.astemirlib.re.client.model.render.renderer.layer.LayerContainer;
import com.astemir.astemirlib.re.common.action.ActionState;
import com.astemir.astemirlib.re.common.world.entity.EntityLocomotion;
import com.astemir.astemirlib.re.core.function.Condition;
import com.astemir.astemirlib.re.core.math.Easing;
import com.eternal_creations.archies_dino_mod.client.model.registry.ADEntityModels;
import com.eternal_creations.archies_dino_mod.client.model.render.entity.MountedListener;
import com.eternal_creations.archies_dino_mod.client.model.render.entity.layer.PassengerLayer;
import com.eternal_creations.archies_dino_mod.client.particle.effect.LushsaurEffects;
import com.eternal_creations.archies_dino_mod.common.entity.base.Mount;
import com.eternal_creations.archies_dino_mod.common.entity.mob.lushsaur.LushsaurEntity;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class LushsaurClient
implements MountedListener<LushsaurEntity> {
    public static final String BONE_HEAD = "head";
    public static final String BONE_SADDLE = "saddle";
    private static final String BONE_TAIL1 = "tail1";
    private static final String BONE_TAIL2 = "tail2";
    private static final String BONE_MASE = "mase";
    private static final String BONE_STRING = "string";
    private static final String BONE_BEETROOT = "beetroot";
    private static final String BONE_BABY_TAIL = "tail";
    public static final String LOCATOR_PASSENGER = "passenger";
    public static final String BONE_BODY = "body";
    public static final String[] BONE_MOSS = new String[]{"moss1", "moss2"};
    public static final Mount.MountPoint<LushsaurEntity> SADDLED_MOUNT_POINT = new Mount.MountPoint(true, Mount.PointType.LOCATOR, "passenger", new Vec3(0.0, (double)-0.7f, 0.0));
    public static final Mount.MountPoint<LushsaurEntity> UNSADDLED_MOUNT_POINT = new Mount.MountPoint(false, Mount.PointType.BONE, "body", new Vec3(0.0, (double)-0.7f, (double)-0.6f));
    private static final String ANIMATION_IDLE = "idle";
    private static final String ANIMATION_WALK = "walk";
    private static final String ANIMATION_RUN = "run";
    private static final String ANIMATION_RUN_ATTACK = "run_attack";
    private static final String ANIMATION_REST = "rest";
    private static final String ANIMATION_UP = "up";
    private static final String ANIMATION_DOWN = "down";
    private static final String ANIMATION_DEATH = "death";
    private static final String ANIMATION_HIDE = "hide";
    private static final String ANIMATION_ROAR = "roar";
    private static final String ANIMATION_EAT = "eat";
    private static final String ANIMATION_EMERGE = "emerge";
    private static final String ANIMATION_BURY = "bury";
    private static final String ANIMATION_ATTACK_TAIL_STOMP = "attack3";
    private static final String ANIMATION_ATTACK_TAIL = "attack4";
    private static final String ANIMATION_ATTACK_HEADBUTT = "attack1";
    private static final String ANIMATION_ATTACK_BITE = "attack2";
    private static final String TEXTURE = "main";

    public void onInitialize(ModelSystem<LushsaurEntity> modelSystem, LushsaurEntity entity) {
        modelSystem.addRootOperation(new RootOperation[]{CommonRootOperations.SHAKING, CommonRootOperations.SmoothBodyRotation.of((float)0.3f), CommonRootOperations.RollOnRotation.of((float)15.0f, (float)0.8f, (float)0.2f), CommonRootOperations.GRUMM_ROTATION});
        this.setupAnimations(modelSystem, entity);
        this.setupPhysics(modelSystem, entity);
    }

    @Override
    public Mount.MountPoint<LushsaurEntity> getSaddledPoint() {
        return SADDLED_MOUNT_POINT;
    }

    @Override
    public Mount.MountPoint<LushsaurEntity> getUnsaddledPoint() {
        return UNSADDLED_MOUNT_POINT;
    }

    private void setupAnimations(ModelSystem<LushsaurEntity> modelSystem, LushsaurEntity entity) {
        LayerNode.Definition rootState = this.buildRootState(entity);
        ((AnimatorModule)modelSystem.addModule(AnimatorModule::new)).setRoot((AbstractNode.Definition)ModifierNode.of((AbstractNode.Definition)rootState).add(Modifier.Procedural.lookAtOrganic((LivingEntity)entity, (float)10.0f, (float)0.1f, (float)45.0f, (float)65.0f), new String[]{BONE_HEAD}).add(Modifier.visibility((Condition)Condition.of(entity::isSheared).not()), BONE_MOSS).add(Modifier.visibility(entity::m_6254_), new String[]{BONE_SADDLE}).add(Modifier.visibility((Condition)Condition.of(entity::hasNoMace).not()), new String[]{BONE_MASE})).setDefaultEasing((Easing)Easing.Simple.SINE_IN, (Easing)Easing.Simple.SINE_OUT);
    }

    private LayerNode.Definition buildRootState(LushsaurEntity entity) {
        StateMachineNode.Definition rootStateMachine = StateMachineNode.of().addState((AbstractNode.Definition)ClipNode.of((String)ANIMATION_DEATH, (ActionState)entity.actionDeath).events(LushsaurEffects.DEATH_EFFECTS), () -> ((ActionState)entity.actionDeath).isActive()).addState((AbstractNode.Definition)ClipNode.of((String)ANIMATION_UP, (ActionState)entity.actionUp), 0.3f, () -> ((ActionState)entity.actionUp).isActive()).addState((AbstractNode.Definition)ClipNode.of((String)ANIMATION_REST).events(LushsaurEffects.REST_EFFECTS), 0.3f, entity::isSitting).addState((AbstractNode.Definition)ClipNode.of((String)ANIMATION_DOWN, (ActionState)entity.actionDown), 0.2f, () -> ((ActionState)entity.actionDown).isActive()).addState((AbstractNode.Definition)ClipNode.of((String)ANIMATION_EMERGE).events(LushsaurEffects.UP_EFFECTS), 0.2f, () -> ((ActionState)entity.actionEmerge).isActive()).addState((AbstractNode.Definition)ClipNode.of((String)ANIMATION_BURY).events(LushsaurEffects.DOWN_EFFECTS), 0.2f, () -> ((ActionState)entity.actionBury).isActive()).addState((AbstractNode.Definition)ClipNode.of((String)ANIMATION_HIDE), 0.3f, entity::isBuried).addState((AbstractNode.Definition)ClipNode.of((String)ANIMATION_ATTACK_TAIL_STOMP, (ActionState)entity.actionAttackTailStomp).events(LushsaurEffects.TAIL_STOMP_EFFECTS), () -> ((ActionState)entity.actionAttackTailStomp).isActive()).addState((AbstractNode.Definition)ClipNode.of((String)ANIMATION_ATTACK_TAIL, (ActionState)entity.actionAttackTail).events(LushsaurEffects.TAIL_SWING_EFFECTS), 0.1f, () -> ((ActionState)entity.actionAttackTail).isActive()).addState(EmptyNode.of(), 0.5f);
        Weight.Definition[] definitionArray = new Weight.Definition[9];
        definitionArray[0] = Weight.of(entity::isSitting);
        definitionArray[1] = Weight.of(entity::isBuried);
        definitionArray[2] = entity.actionUp.weight();
        definitionArray[3] = entity.actionDown.weight();
        definitionArray[4] = entity.actionEmerge.weight();
        definitionArray[5] = entity.actionBury.weight();
        definitionArray[6] = entity.actionDeath.weight();
        definitionArray[7] = entity.actionAttackTailStomp.weight();
        definitionArray[8] = entity.actionAttackTail.weight();
        return LayerNode.of((AbstractNode.Definition)this.buildActionState(entity), (AbstractNode.Definition)rootStateMachine, (Weight.Definition)Weight.combine((Weight.Definition[])definitionArray));
    }

    private LayerNode.Definition buildActionState(LushsaurEntity entity) {
        StateMachineNode.Definition actionStateMachine = StateMachineNode.of().addState((AbstractNode.Definition)ClipNode.of((String)ANIMATION_ATTACK_HEADBUTT, (ActionState)entity.actionAttackHeadbutt), () -> ((ActionState)entity.actionAttackHeadbutt).isActive()).addState((AbstractNode.Definition)ClipNode.of((String)ANIMATION_ATTACK_BITE, (ActionState)entity.actionAttackBite), () -> ((ActionState)entity.actionAttackBite).isActive()).addState((AbstractNode.Definition)ClipNode.of((String)ANIMATION_ROAR, (ActionState)entity.actionRoar).events(LushsaurEffects.ROAR_EFFECTS), 0.1f, () -> ((ActionState)entity.actionRoar).isActive()).addState((AbstractNode.Definition)ClipNode.of((String)ANIMATION_EAT, (ActionState)entity.actionEat).events(LushsaurEffects.EAT_EFFECTS), () -> ((ActionState)entity.actionEat).isActive()).addState(EmptyNode.of(), 0.5f);
        return LayerNode.of((AbstractNode.Definition)this.buildLocomotionState(entity), (AbstractNode.Definition)actionStateMachine, (Weight.Definition)Weight.combine((Weight.Definition[])new Weight.Definition[]{entity.actionAttackBite.weight(), entity.actionAttackHeadbutt.weight(), entity.actionRoar.weight(), entity.actionEat.weight()}));
    }

    private LayerNode.Definition buildLocomotionState(LushsaurEntity entity) {
        EntityLocomotion locomotion = entity.getLocomotion();
        State[] stateArray = new State[3];
        stateArray[0] = State.of((AbstractNode.Definition)ClipNode.of((String)ANIMATION_RUN_ATTACK, (ProgressEvaluator)locomotion.runEvaluator(3.5f)).events(LushsaurEffects.RUN_EFFECTS), (float)0.2f, (Condition)locomotion.whenRunning().and(entity::isCharging));
        stateArray[1] = State.of((AbstractNode.Definition)ClipNode.of((String)ANIMATION_RUN, (ProgressEvaluator)locomotion.runEvaluator(3.5f)).events(LushsaurEffects.RUN_EFFECTS), (float)0.2f, (Condition)locomotion.whenRunning());
        stateArray[2] = State.of((AbstractNode.Definition)ClipNode.of((String)ANIMATION_WALK, (ProgressEvaluator)locomotion.walkEvaluator(3.5f)).events(LushsaurEffects.WALK_EFFECTS), (float)0.2f);
        StateMachineNode.Definition walkState = StateMachineNode.of((State[])stateArray);
        return LayerNode.of((AbstractNode.Definition)ClipNode.of((String)ANIMATION_IDLE), (AbstractNode.Definition)walkState, (Weight.Definition)locomotion.walkWeight());
    }

    private void setupPhysics(ModelSystem<LushsaurEntity> modelSystem, LushsaurEntity entity) {
        EntityLocomotion locomotion = entity.getLocomotion();
        PhysicsModule physics = (PhysicsModule)modelSystem.addModule(PhysicsModule::new);
        PhysicsModule.PhysicsProperties tailProperties = physics.createProperties();
        if (!entity.m_6162_()) {
            PhysicsModule.PhysicsProperties ropeProperties = physics.createProperties().rotationAxes(PhysicsModule.PhysicsProperties.RotationAxes.XZ);
            physics.createChain(() -> ((EntityLocomotion)locomotion).getLocalMovement(), () -> ((EntityLocomotion)locomotion).getTurnSpeed()).addBone(BONE_TAIL1, tailProperties).addBone(BONE_TAIL2, tailProperties).addBone(BONE_MASE, tailProperties).restOffset(0.0f, 0.15f, 1.0f).gravity(-0.03f).stiffness(0.15f).inertia(0.0f, 0.4f);
            physics.createChain(() -> ((EntityLocomotion)locomotion).getLocalMovement(), () -> ((EntityLocomotion)locomotion).getTurnSpeed()).addBone(BONE_STRING, ropeProperties).addBone(BONE_BEETROOT, ropeProperties).restOffset(0.0f, -2.071f, 0.0f).gravity(-0.08f).stiffness(0.03f).damping(0.97f).inertia(0.0f, 1.0f);
        } else {
            physics.createChain(() -> ((EntityLocomotion)locomotion).getLocalMovement(), () -> ((EntityLocomotion)locomotion).getTurnSpeed()).addBone(BONE_BABY_TAIL, tailProperties).addBone(BONE_TAIL2, tailProperties).restOffset(0.0f, 0.15f, 1.0f).inertia(0.0f, 0.4f).gravity(-0.03f).stiffness(0.15f);
        }
    }

    public static void setupModel(AstemirModel<LushsaurEntity> model, LayerContainer<LushsaurEntity> layers) {
        ModelTexture texture = ModelTexture.of(model, (String)TEXTURE, RenderType::m_110458_);
        layers.add(BaseRenderLayer.of((ModelTexture)texture));
        layers.add(new PassengerLayer<LushsaurEntity>(model, SADDLED_MOUNT_POINT, UNSADDLED_MOUNT_POINT));
    }

    public static AstemirEntityRenderer.Provider<LushsaurEntity> createRenderProvider() {
        return AstemirEntityRenderer.of(entity -> entity.m_6162_() ? ADEntityModels.LUSHSAUR_BABY : ADEntityModels.LUSHSAUR, LushsaurClient::new).overlay(OverlayComponent.livingEntityNoDeathOverlay()).shadow((ShadowComponent)ShadowComponent.of((instance, partialTick) -> instance.shadowRadius.get(partialTick)));
    }
}

