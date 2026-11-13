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
 *  com.astemir.astemirlib.re.client.model.core.texture.ModelTexture
 *  com.astemir.astemirlib.re.client.model.render.component.BufferComponent
 *  com.astemir.astemirlib.re.client.model.render.component.ColorComponent
 *  com.astemir.astemirlib.re.client.model.render.component.LightComponent
 *  com.astemir.astemirlib.re.client.model.render.component.LightComponent$Dynamic
 *  com.astemir.astemirlib.re.client.model.render.component.OverlayComponent
 *  com.astemir.astemirlib.re.client.model.render.renderer.AstemirEntityRenderer
 *  com.astemir.astemirlib.re.client.model.render.renderer.AstemirEntityRenderer$Provider
 *  com.astemir.astemirlib.re.client.model.render.renderer.layer.BaseRenderLayer
 *  com.astemir.astemirlib.re.client.model.render.renderer.layer.LayerContainer
 *  com.astemir.astemirlib.re.client.shader.post.PostRenderType
 *  com.astemir.astemirlib.re.client.shader.post.pass.BloomPass
 *  com.astemir.astemirlib.re.common.action.ActionState
 *  com.astemir.astemirlib.re.common.world.entity.EntityLocomotion
 *  com.astemir.astemirlib.re.core.function.Condition
 *  com.astemir.astemirlib.re.core.math.Easing
 *  com.astemir.astemirlib.re.core.math.Easing$Simple
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.client.renderer.LightTexture
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
import com.astemir.astemirlib.re.client.model.render.component.BufferComponent;
import com.astemir.astemirlib.re.client.model.render.component.ColorComponent;
import com.astemir.astemirlib.re.client.model.render.component.LightComponent;
import com.astemir.astemirlib.re.client.model.render.component.OverlayComponent;
import com.astemir.astemirlib.re.client.model.render.renderer.AstemirEntityRenderer;
import com.astemir.astemirlib.re.client.model.render.renderer.layer.BaseRenderLayer;
import com.astemir.astemirlib.re.client.model.render.renderer.layer.LayerContainer;
import com.astemir.astemirlib.re.client.shader.post.PostRenderType;
import com.astemir.astemirlib.re.client.shader.post.pass.BloomPass;
import com.astemir.astemirlib.re.common.action.ActionState;
import com.astemir.astemirlib.re.common.world.entity.EntityLocomotion;
import com.astemir.astemirlib.re.core.function.Condition;
import com.astemir.astemirlib.re.core.math.Easing;
import com.eternal_creations.archies_dino_mod.client.model.registry.ADEntityModels;
import com.eternal_creations.archies_dino_mod.client.model.render.ADRenderTypes;
import com.eternal_creations.archies_dino_mod.client.model.render.entity.MountedListener;
import com.eternal_creations.archies_dino_mod.client.model.render.entity.layer.PassengerLayer;
import com.eternal_creations.archies_dino_mod.client.particle.effect.ObsidiceratopsEffects;
import com.eternal_creations.archies_dino_mod.common.entity.base.Mount;
import com.eternal_creations.archies_dino_mod.common.entity.mob.obsidiceratops.ObsidiceratopsEntity;
import java.util.EnumMap;
import java.util.function.Function;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ObsidiceratopsClient
implements MountedListener<ObsidiceratopsEntity> {
    public static final String BONE_HEAD = "head";
    public static final String BONE_SADDLE = "saddle_body";
    public static final String BONE_HARNESS = "saddle_head";
    public static final String BONE_STEERING_ROPE = "bone3";
    private static final String BONE_TAIL1 = "tail";
    private static final String BONE_TAIL2 = "tail2";
    public static final String LOCATOR_PASSENGER = "passenger";
    public static final String BONE_BODY = "body";
    public static final String[] BONE_SPEARS = new String[]{"gold_spear1", "gold_spear2", "gold_spear3", "gold_spear4"};
    public static final Mount.MountPoint<ObsidiceratopsEntity> SADDLED_MOUNT_POINT = new Mount.MountPoint(true, Mount.PointType.LOCATOR, "passenger", new Vec3(0.0, (double)-0.6f, 0.0));
    public static final Mount.MountPoint<ObsidiceratopsEntity> UNSADDLED_MOUNT_POINT = new Mount.MountPoint(false, Mount.PointType.BONE, "body", new Vec3(0.0, (double)1.15f, (double)-0.6f));
    private static final String ANIMATION_IDLE = "idle";
    private static final String ANIMATION_WALK = "walk";
    private static final String ANIMATION_RUN = "run";
    private static final String ANIMATION_CHARGE = "charge";
    private static final String ANIMATION_DEATH = "death";
    private static final String ANIMATION_ATTACK_HIT = "attack1";
    private static final String ANIMATION_ATTACK_TAIL = "attack2";
    private static final String ANIMATION_ATTACK_HIT_MOVE = "attack1_move";
    private static final String ANIMATION_EAT = "eat";
    private static final String ANIMATION_WARN = "agro";
    private static final String ANIMATION_DRINK1 = "drink1";
    private static final String ANIMATION_DRINK2 = "drink2";
    private static final String TEXTURE = "main";
    private static final String TEXTURE_LAVA = "lava";
    private static final String TEXTURE_GLOW = "glow";

    public void onInitialize(ModelSystem<ObsidiceratopsEntity> modelSystem, ObsidiceratopsEntity entity) {
        modelSystem.addRootOperation(new RootOperation[]{CommonRootOperations.SHAKING, CommonRootOperations.SmoothBodyRotation.of((float)0.3f), CommonRootOperations.RollOnRotation.of((float)20.0f, (float)1.3f, (float)0.3f), CommonRootOperations.GRUMM_ROTATION});
        this.setupAnimations(modelSystem, entity);
        this.setupPhysics(modelSystem, entity);
    }

    @Override
    public Mount.MountPoint<ObsidiceratopsEntity> getSaddledPoint() {
        return SADDLED_MOUNT_POINT;
    }

    @Override
    public Mount.MountPoint<ObsidiceratopsEntity> getUnsaddledPoint() {
        return UNSADDLED_MOUNT_POINT;
    }

    private void setupAnimations(ModelSystem<ObsidiceratopsEntity> modelSystem, ObsidiceratopsEntity entity) {
        LayerNode.Definition rootState = this.buildRootState(entity);
        ((AnimatorModule)modelSystem.addModule(AnimatorModule::new)).setRoot((AbstractNode.Definition)ModifierNode.of((AbstractNode.Definition)rootState).add(Modifier.Procedural.lookAtOrganic((LivingEntity)entity, (float)10.0f, (float)0.1f, (float)45.0f, (float)65.0f), new String[]{BONE_HEAD}).add(Modifier.visibility(entity::m_6254_), new String[]{BONE_SADDLE}).add(Modifier.visibility(entity::m_6254_), new String[]{BONE_HARNESS}).add(Modifier.visibility(entity::m_6254_), new String[]{BONE_STEERING_ROPE}).add(Modifier.visibility((Condition)Condition.FALSE), BONE_SPEARS)).setDefaultEasing((Easing)Easing.Simple.SINE_IN, (Easing)Easing.Simple.SINE_OUT);
    }

    private LayerNode.Definition buildRootState(ObsidiceratopsEntity entity) {
        StateMachineNode.Definition rootStateMachine = StateMachineNode.of().addState((AbstractNode.Definition)ClipNode.of((String)ANIMATION_DEATH, (ActionState)entity.actionDeath).events(ObsidiceratopsEffects.DEATH_EFFECTS), () -> ((ActionState)entity.actionDeath).isActive()).addState((AbstractNode.Definition)ClipNode.of((String)ANIMATION_ATTACK_TAIL, (ActionState)entity.actionAttackTail).events(ObsidiceratopsEffects.ATTACK_TAIL_EFFECTS), () -> ((ActionState)entity.actionAttackTail).isActive()).addState((AbstractNode.Definition)ClipNode.of((String)ANIMATION_EAT, (ActionState)entity.actionEat).events(ObsidiceratopsEffects.EAT_EFFECTS), () -> ((ActionState)entity.actionEat).isActive()).addState((AbstractNode.Definition)ClipNode.of((String)ANIMATION_WARN, (ActionState)entity.actionWarn).events(ObsidiceratopsEffects.WARN_EFFECTS), () -> ((ActionState)entity.actionWarn).isActive()).addState((AbstractNode.Definition)ClipNode.of((String)ANIMATION_DRINK1, (ActionState)entity.actionDrink1).events(ObsidiceratopsEffects.DRINK1_EFFECTS), () -> ((ActionState)entity.actionDrink1).isActive()).addState((AbstractNode.Definition)ClipNode.of((String)ANIMATION_DRINK2, (ActionState)entity.actionDrink2).events(ObsidiceratopsEffects.DRINK2_EFFECTS), () -> ((ActionState)entity.actionDrink2).isActive()).addState(EmptyNode.of());
        return LayerNode.of(this.buildLocomotionState(entity), (AbstractNode.Definition)rootStateMachine, (Weight.Definition)Weight.combine((Weight.Definition[])new Weight.Definition[]{entity.actionDeath.weight(), entity.actionAttackTail.weight(), entity.actionEat.weight(), entity.actionWarn.weight(), entity.actionDrink1.weight(), entity.actionDrink2.weight()}));
    }

    private AbstractNode.Definition<?> buildLocomotionState(ObsidiceratopsEntity entity) {
        EntityLocomotion locomotion = entity.getLocomotion();
        State[] stateArray = new State[3];
        stateArray[0] = State.of((AbstractNode.Definition)ClipNode.of((String)ANIMATION_CHARGE, (ProgressEvaluator)locomotion.runEvaluator(4.0f)).events(ObsidiceratopsEffects.CHARGE_EFFECTS), (float)0.2f, (Condition)locomotion.whenRunning().and(entity::isCharging));
        stateArray[1] = State.of((AbstractNode.Definition)ClipNode.of((String)ANIMATION_RUN, (ProgressEvaluator)locomotion.runEvaluator(4.0f)).events(ObsidiceratopsEffects.RUN_EFFECTS), (float)0.2f, (Condition)locomotion.whenRunning());
        stateArray[2] = State.of((AbstractNode.Definition)ClipNode.of((String)ANIMATION_WALK, (ProgressEvaluator)locomotion.walkEvaluator(3.5f)).events(ObsidiceratopsEffects.WALK_EFFECTS), (float)0.2f);
        StateMachineNode.Definition walkState = StateMachineNode.of((State[])stateArray);
        LayerNode.Definition locomotionLayer = LayerNode.of((AbstractNode.Definition)ClipNode.of((String)ANIMATION_IDLE), (AbstractNode.Definition)walkState, (Weight.Definition)locomotion.walkWeight());
        Weight.Definition walk = locomotion.walkWeight();
        Weight.Definition attack = entity.actionAttackHit.weight();
        LayerNode.Definition standingAttack = LayerNode.of((AbstractNode.Definition)locomotionLayer, (AbstractNode.Definition)ClipNode.of((String)ANIMATION_ATTACK_HIT, (ActionState)entity.actionAttackHit).events(ObsidiceratopsEffects.ATTACK_HIT_EFFECTS), (Weight.Definition)attack.mul(walk.inverted()));
        return LayerNode.of((AbstractNode.Definition)standingAttack, (AbstractNode.Definition)ClipNode.of((String)ANIMATION_ATTACK_HIT_MOVE, (ActionState)entity.actionAttackHit).events(ObsidiceratopsEffects.ATTACK_HIT_EFFECTS), (Weight.Definition)attack.mul(walk));
    }

    private void setupPhysics(ModelSystem<ObsidiceratopsEntity> modelSystem, ObsidiceratopsEntity entity) {
        if (entity.m_6162_()) {
            return;
        }
        EntityLocomotion locomotion = entity.getLocomotion();
        PhysicsModule physics = (PhysicsModule)modelSystem.addModule(PhysicsModule::new);
        PhysicsModule.PhysicsProperties tailProperties = physics.createProperties();
        physics.createChain(() -> ((EntityLocomotion)locomotion).getLocalMovement(), () -> ((EntityLocomotion)locomotion).getTurnSpeed()).addBone(BONE_TAIL1, tailProperties).addBone(BONE_TAIL2, tailProperties).restOffset(0.0f, 0.15f, 1.0f).inertia(0.0f, 0.4f).gravity(-0.03f).stiffness(0.15f);
    }

    public static void setupModel(AstemirModel<ObsidiceratopsEntity> model, LayerContainer<ObsidiceratopsEntity> layers, boolean isBaby) {
        if (isBaby) {
            ModelTexture texture = ModelTexture.of(model, (String)TEXTURE, RenderType::m_110458_);
            layers.add(BaseRenderLayer.of((ModelTexture)texture).light((inst, blockLight, skyLight) -> LightTexture.m_109885_((int)12, (int)skyLight)));
            layers.add(BaseRenderLayer.of((ModelTexture)ModelTexture.of(model, (String)TEXTURE_GLOW, ADRenderTypes::eyesNoCull)).buffer(BufferComponent.postEffect((Function)PostRenderType.MODEL_UNLIT, (boolean)false, (Class[])new Class[]{BloomPass.class})));
            return;
        }
        EnumMap<ObsidiceratopsEntity.LavaState, ModelTexture> textures = new EnumMap<ObsidiceratopsEntity.LavaState, ModelTexture>(ObsidiceratopsEntity.LavaState.class);
        EnumMap<ObsidiceratopsEntity.LavaState, ModelTexture> fadeTextures = new EnumMap<ObsidiceratopsEntity.LavaState, ModelTexture>(ObsidiceratopsEntity.LavaState.class);
        for (ObsidiceratopsEntity.LavaState state : ObsidiceratopsEntity.LavaState.values()) {
            String name = state == ObsidiceratopsEntity.LavaState.NORMAL ? TEXTURE : "lava_" + state.name().toLowerCase();
            textures.put(state, ModelTexture.of(model, (String)name, RenderType::m_110458_));
            fadeTextures.put(state, ModelTexture.of(model, (String)name, RenderType::m_110473_));
        }
        LightComponent.Dynamic fullLight = LightComponent.of((int)12);
        layers.add(BaseRenderLayer.of(mob -> (ModelTexture)textures.get((Object)mob.getLavaState())).light((LightComponent)fullLight));
        layers.add(BaseRenderLayer.of(mob -> (ModelTexture)fadeTextures.get((Object)mob.getPreviousLavaState())).light((LightComponent)fullLight).color((ColorComponent)ColorComponent.of().alpha((instance, partialTick) -> 1.0f - instance.lavaStateProgress.get(partialTick))).condition(mob -> mob.lavaStateProgress.isActive()));
        layers.add(BaseRenderLayer.of((ModelTexture)ModelTexture.of(model, (String)TEXTURE_GLOW, ADRenderTypes::eyesNoCull)).buffer(BufferComponent.postEffect((Function)PostRenderType.MODEL_UNLIT, (boolean)false, (Class[])new Class[]{BloomPass.class})));
        layers.add(new PassengerLayer<ObsidiceratopsEntity>(model, SADDLED_MOUNT_POINT, UNSADDLED_MOUNT_POINT));
    }

    public static AstemirEntityRenderer.Provider<ObsidiceratopsEntity> createRenderProvider() {
        return AstemirEntityRenderer.of(e -> e.m_6162_() ? ADEntityModels.OBSIDICERATOPS_BABY : ADEntityModels.OBSIDICERATOPS, ObsidiceratopsClient::new).overlay(OverlayComponent.livingEntityNoDeathOverlay()).shadow(2.0f);
    }
}

