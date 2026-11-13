/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.astemir.astemirlib.re.client.model.AstemirModel
 *  com.astemir.astemirlib.re.client.model.CommonRootOperations
 *  com.astemir.astemirlib.re.client.model.CommonRootOperations$SmoothBodyRotation
 *  com.astemir.astemirlib.re.client.model.core.RootOperation
 *  com.astemir.astemirlib.re.client.model.core.animation.node.AbstractNode$Definition
 *  com.astemir.astemirlib.re.client.model.core.animation.node.BlendNode
 *  com.astemir.astemirlib.re.client.model.core.animation.node.BlendNode$Definition
 *  com.astemir.astemirlib.re.client.model.core.animation.node.ClipNode
 *  com.astemir.astemirlib.re.client.model.core.animation.node.EmptyNode
 *  com.astemir.astemirlib.re.client.model.core.animation.node.LayerNode
 *  com.astemir.astemirlib.re.client.model.core.animation.node.LayerNode$Definition
 *  com.astemir.astemirlib.re.client.model.core.animation.node.Weight
 *  com.astemir.astemirlib.re.client.model.core.animation.node.Weight$Definition
 *  com.astemir.astemirlib.re.client.model.core.animation.node.modifier.Modifier
 *  com.astemir.astemirlib.re.client.model.core.animation.node.modifier.Modifier$Procedural
 *  com.astemir.astemirlib.re.client.model.core.animation.node.modifier.Modifier$Rotate
 *  com.astemir.astemirlib.re.client.model.core.animation.node.modifier.Modifier$Translate
 *  com.astemir.astemirlib.re.client.model.core.animation.node.modifier.ModifierNode
 *  com.astemir.astemirlib.re.client.model.core.animation.node.state.StateMachineNode
 *  com.astemir.astemirlib.re.client.model.core.animation.node.state.StateMachineNode$Definition
 *  com.astemir.astemirlib.re.client.model.core.system.ModelSystem
 *  com.astemir.astemirlib.re.client.model.core.system.module.AnimatorModule
 *  com.astemir.astemirlib.re.client.model.core.texture.ModelTexture
 *  com.astemir.astemirlib.re.client.model.render.component.OverlayComponent
 *  com.astemir.astemirlib.re.client.model.render.component.ShadowComponent
 *  com.astemir.astemirlib.re.client.model.render.renderer.AstemirEntityRenderer
 *  com.astemir.astemirlib.re.client.model.render.renderer.AstemirEntityRenderer$Provider
 *  com.astemir.astemirlib.re.client.model.render.renderer.layer.BaseRenderLayer
 *  com.astemir.astemirlib.re.client.model.render.renderer.layer.LayerContainer
 *  com.astemir.astemirlib.re.common.action.ActionState
 *  com.astemir.astemirlib.re.common.world.entity.EntityLocomotion
 *  com.astemir.astemirlib.re.core.function.DeltaFloat
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
import com.astemir.astemirlib.re.client.model.core.animation.node.AbstractNode;
import com.astemir.astemirlib.re.client.model.core.animation.node.BlendNode;
import com.astemir.astemirlib.re.client.model.core.animation.node.ClipNode;
import com.astemir.astemirlib.re.client.model.core.animation.node.EmptyNode;
import com.astemir.astemirlib.re.client.model.core.animation.node.LayerNode;
import com.astemir.astemirlib.re.client.model.core.animation.node.Weight;
import com.astemir.astemirlib.re.client.model.core.animation.node.modifier.Modifier;
import com.astemir.astemirlib.re.client.model.core.animation.node.modifier.ModifierNode;
import com.astemir.astemirlib.re.client.model.core.animation.node.state.StateMachineNode;
import com.astemir.astemirlib.re.client.model.core.system.ModelSystem;
import com.astemir.astemirlib.re.client.model.core.system.module.AnimatorModule;
import com.astemir.astemirlib.re.client.model.core.texture.ModelTexture;
import com.astemir.astemirlib.re.client.model.render.component.OverlayComponent;
import com.astemir.astemirlib.re.client.model.render.component.ShadowComponent;
import com.astemir.astemirlib.re.client.model.render.renderer.AstemirEntityRenderer;
import com.astemir.astemirlib.re.client.model.render.renderer.layer.BaseRenderLayer;
import com.astemir.astemirlib.re.client.model.render.renderer.layer.LayerContainer;
import com.astemir.astemirlib.re.common.action.ActionState;
import com.astemir.astemirlib.re.common.world.entity.EntityLocomotion;
import com.astemir.astemirlib.re.core.function.DeltaFloat;
import com.astemir.astemirlib.re.core.math.Easing;
import com.eternal_creations.archies_dino_mod.client.model.registry.ADEntityModels;
import com.eternal_creations.archies_dino_mod.client.model.render.entity.MountedListener;
import com.eternal_creations.archies_dino_mod.client.model.render.entity.layer.PassengerLayer;
import com.eternal_creations.archies_dino_mod.client.particle.effect.ChorusoatlEffects;
import com.eternal_creations.archies_dino_mod.common.entity.base.Mount;
import com.eternal_creations.archies_dino_mod.common.entity.mob.chorusoatl.ChorusoatlEntity;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ChorusoatlClient
implements MountedListener<ChorusoatlEntity> {
    private static final String ROOT_BONE = "chorusoatl";
    private static final String HEAD_BONE = "neck1";
    private static final String[] SADDLE_BONES = new String[]{"saddle", "bandage_neck", "bandage", "snaffle"};
    public static final String LOCATOR_PASSENGER = "passenger";
    public static final String BELLY_BONE = "belly";
    public static final Mount.MountPoint<ChorusoatlEntity> SADDLED_MOUNT_POINT = new Mount.MountPoint(true, Mount.PointType.LOCATOR, "passenger", new Vec3(0.0, (double)-0.8f, 0.0));
    public static final Mount.MountPoint<ChorusoatlEntity> UNSADDLED_MOUNT_POINT = new Mount.MountPoint(false, Mount.PointType.BONE, "belly", new Vec3(0.0, -0.75, -0.75));
    private static final String ANIMATION_IDLE = "idle";
    private static final String ANIMATION_FLY_IDLE = "fly_idle";
    private static final String ANIMATION_WALK = "walk";
    private static final String ANIMATION_FLY = "fly1";
    private static final String ANIMATION_GLIDE = "fly2";
    private static final String ANIMATION_BLINK = "blink";
    private static final String ANIMATION_SHAKE = "shake";
    private static final String ANIMATION_UP = "up";
    private static final String ANIMATION_LAND = "land";
    private static final String ANIMATION_DEATH_GROUND = "death_ground";
    private static final String ANIMATION_DEATH_AIR_FALL_IDLE = "death_air_fall_idle";
    private static final String ANIMATION_DEATH_AIR_GROUND_IMPACT = "death_air_ground_impact";
    private static final String ANIMATION_FLY_ATTACK = "fly_attack";
    private static final String ANIMATION_BITE_ATTACK = "attack1";
    private static final String ANIMATION_HIT_ATTACK = "attack2";
    private static final String ANIMATION_SHOOT_ATTACK = "attack3";
    private static final String ANIMATION_SCREAM = "scream";
    private static final String TEXTURE = "main";

    public void onInitialize(ModelSystem<ChorusoatlEntity> modelSystem, ChorusoatlEntity entity) {
        modelSystem.addRootOperation(new RootOperation[]{CommonRootOperations.SHAKING, CommonRootOperations.SmoothBodyRotation.of((float)0.3f), CommonRootOperations.GRUMM_ROTATION});
        this.setupAnimations(modelSystem, entity);
    }

    private void setupAnimations(ModelSystem<ChorusoatlEntity> modelSystem, ChorusoatlEntity entity) {
        LayerNode.Definition rootState = this.buildRootState(entity);
        ((AnimatorModule)modelSystem.addModule(AnimatorModule::new)).setRoot((AbstractNode.Definition)ModifierNode.of((AbstractNode.Definition)rootState).add((boneId, state, alpha, partialTick, out) -> out.setRotation(-entity.flyingPitch.get(partialTick), 0.0f, entity.flyingRoll.get(partialTick)), new String[]{ROOT_BONE}).add(Modifier.Procedural.lookAtOrganic((LivingEntity)entity, (float)10.0f, (float)0.1f, (float)45.0f, (float)65.0f), new String[]{HEAD_BONE}).add(Modifier.visibility(entity::m_6254_), SADDLE_BONES).add(Modifier.Rotate.x((DeltaFloat)entity.sittingWeight.mul(-22.0f)).andThen(Modifier.Translate.y((DeltaFloat)entity.sittingWeight.mul(-0.2f))), new String[]{"body"})).setDefaultEasing((Easing)Easing.Simple.SINE_IN_OUT, (Easing)Easing.Simple.SINE_IN_OUT);
    }

    private LayerNode.Definition buildRootState(ChorusoatlEntity entity) {
        StateMachineNode.Definition deathStateMachine = StateMachineNode.of().addState((AbstractNode.Definition)ClipNode.of((String)ANIMATION_DEATH_AIR_FALL_IDLE), 0.1f, entity::isFallingDead).addState((AbstractNode.Definition)ClipNode.of((String)ANIMATION_DEATH_AIR_GROUND_IMPACT, (ActionState)entity.actionDeathAirLand), 0.1f, () -> ((ActionState)entity.actionDeathAirLand).isActive()).addState((AbstractNode.Definition)ClipNode.of((String)ANIMATION_DEATH_GROUND, (ActionState)entity.actionDeathGround), 0.1f, () -> ((ActionState)entity.actionDeathGround).isActive()).addState(EmptyNode.of());
        Weight.Definition[] definitionArray = new Weight.Definition[3];
        definitionArray[0] = Weight.of(entity::isFallingDead, (float)0.1f);
        definitionArray[1] = entity.actionDeathAirLand.weight();
        definitionArray[2] = entity.actionDeathGround.weight();
        return LayerNode.of((AbstractNode.Definition)ClipNode.of((String)ANIMATION_BLINK), (AbstractNode.Definition)LayerNode.of((AbstractNode.Definition)this.buildActionState(entity), (AbstractNode.Definition)deathStateMachine, (Weight.Definition)Weight.combine((Weight.Definition[])definitionArray)), (Weight.Definition)Weight.of((float)1.0f));
    }

    private LayerNode.Definition buildActionState(ChorusoatlEntity entity) {
        StateMachineNode.Definition actionStateMachine = StateMachineNode.of().addState((AbstractNode.Definition)ClipNode.of((String)ANIMATION_SCREAM, (ActionState)entity.actionScream).events(ChorusoatlEffects.SCREAM_EFFECTS), () -> ((ActionState)entity.actionScream).isActive()).addState((AbstractNode.Definition)ClipNode.of((String)ANIMATION_SHAKE, (ActionState)entity.actionShake), () -> ((ActionState)entity.actionShake).isActive()).addState((AbstractNode.Definition)ClipNode.of((String)ANIMATION_BITE_ATTACK, (ActionState)entity.actionAttackBite), () -> ((ActionState)entity.actionAttackBite).isActive()).addState((AbstractNode.Definition)ClipNode.of((String)ANIMATION_HIT_ATTACK, (ActionState)entity.actionAttackHit).events(ChorusoatlEffects.BITE_EFFECTS), () -> ((ActionState)entity.actionAttackHit).isActive()).addState((AbstractNode.Definition)ClipNode.of((String)ANIMATION_SHOOT_ATTACK, (ActionState)entity.actionAttackShoot), () -> ((ActionState)entity.actionAttackShoot).isActive()).addState((AbstractNode.Definition)ClipNode.of((String)ANIMATION_FLY_ATTACK, (ActionState)entity.actionAttackFly), () -> ((ActionState)entity.actionAttackFly).isActive()).addState(EmptyNode.of());
        return LayerNode.of(this.buildLocomotionState(entity), (AbstractNode.Definition)actionStateMachine, (Weight.Definition)Weight.combine((Weight.Definition[])new Weight.Definition[]{entity.actionScream.weight(), entity.actionShake.weight(), entity.actionAttackBite.weight(), entity.actionAttackHit.weight(), entity.actionAttackShoot.weight(), entity.actionAttackFly.weight()}));
    }

    private AbstractNode.Definition<?> buildLocomotionState(ChorusoatlEntity entity) {
        EntityLocomotion locomotion = entity.getLocomotion();
        LayerNode.Definition groundLocomotion = LayerNode.of((AbstractNode.Definition)ClipNode.of((String)ANIMATION_IDLE), (AbstractNode.Definition)ClipNode.of((String)ANIMATION_WALK, p -> !entity.isFlying() ? locomotion.getWalkLocomotion(p, 3.5f) : 0.0f).events(ChorusoatlEffects.WALK_EFFECTS), (Weight.Definition)locomotion.walkWeight());
        StateMachineNode.Definition flyMovingState = StateMachineNode.of().addState((AbstractNode.Definition)ClipNode.of((String)ANIMATION_GLIDE).events(ChorusoatlEffects.GLIDE_EFFECTS), 0.2f, entity::isGliding).addState((AbstractNode.Definition)ClipNode.of((String)ANIMATION_FLY).events(ChorusoatlEffects.FLY_EFFECTS), 0.2f);
        BlendNode.Definition airLocomotion = BlendNode.of((AbstractNode.Definition)ClipNode.of((String)ANIMATION_FLY_IDLE).events(ChorusoatlEffects.FLY_IDLE_EFFECTS), (AbstractNode.Definition)flyMovingState, (Weight.Definition)Weight.of((DeltaFloat)entity.flyingMotion));
        return StateMachineNode.of().addState((AbstractNode.Definition)ClipNode.of((String)ANIMATION_UP, (ActionState)entity.actionUp).events(ChorusoatlEffects.TAKEOFF_EFFECTS), 0.15f, () -> ((ActionState)entity.actionUp).isActiveWithoutFadeOut()).addState((AbstractNode.Definition)ClipNode.of((String)ANIMATION_LAND, (ActionState)entity.actionDown).events(ChorusoatlEffects.LAND_EFFECTS), 0.15f, () -> ((ActionState)entity.actionDown).isActiveWithoutFadeOut()).addState((AbstractNode.Definition)airLocomotion, 0.25f, entity::isFlying).addState((AbstractNode.Definition)groundLocomotion, 0.25f);
    }

    @Override
    public Mount.MountPoint<ChorusoatlEntity> getSaddledPoint() {
        return SADDLED_MOUNT_POINT;
    }

    @Override
    public Mount.MountPoint<ChorusoatlEntity> getUnsaddledPoint() {
        return UNSADDLED_MOUNT_POINT;
    }

    public static void setupModel(AstemirModel<ChorusoatlEntity> model, LayerContainer<ChorusoatlEntity> layers) {
        ModelTexture texture = ModelTexture.of(model, (String)TEXTURE, RenderType::m_110458_);
        layers.add(BaseRenderLayer.of((ModelTexture)texture).light((instance, blockLight, skyLight) -> LightTexture.m_109885_((int)10, (int)skyLight)));
        layers.add(new PassengerLayer<ChorusoatlEntity>(model, SADDLED_MOUNT_POINT, UNSADDLED_MOUNT_POINT));
    }

    public static AstemirEntityRenderer.Provider<ChorusoatlEntity> createRenderProvider() {
        return AstemirEntityRenderer.of(e -> e.m_6162_() ? ADEntityModels.CHORUSOATL_BABY : ADEntityModels.CHORUSOATL, ChorusoatlClient::new).overlay(OverlayComponent.livingEntityNoDeathOverlay()).shadow((ShadowComponent)ShadowComponent.of((float)2.0f));
    }
}

