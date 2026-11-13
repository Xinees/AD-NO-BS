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
 *  com.astemir.astemirlib.re.client.model.core.animation.node.BlendNode
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
 *  com.astemir.astemirlib.re.client.model.core.animation.node.state.State
 *  com.astemir.astemirlib.re.client.model.core.animation.node.state.StateMachineNode
 *  com.astemir.astemirlib.re.client.model.core.animation.node.state.StateMachineNode$Definition
 *  com.astemir.astemirlib.re.client.model.core.system.ModelSystem
 *  com.astemir.astemirlib.re.client.model.core.system.module.AnimatorModule
 *  com.astemir.astemirlib.re.client.model.core.system.module.PhysicsModule
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
 *  com.astemir.astemirlib.re.core.function.DeltaFloat
 *  com.astemir.astemirlib.re.core.math.Easing
 *  com.astemir.astemirlib.re.core.math.Easing$Simple
 *  com.astemir.astemirlib.re.core.math.Transform
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.phys.Vec3
 */
package com.eternal_creations.archies_dino_mod.client.model.render.entity.mob;

import com.astemir.astemirlib.re.client.model.AstemirModel;
import com.astemir.astemirlib.re.client.model.CommonRootOperations;
import com.astemir.astemirlib.re.client.model.core.RootOperation;
import com.astemir.astemirlib.re.client.model.core.animation.evaluator.ProgressEvaluator;
import com.astemir.astemirlib.re.client.model.core.animation.node.AbstractNode;
import com.astemir.astemirlib.re.client.model.core.animation.node.BlendNode;
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
import com.astemir.astemirlib.re.core.function.DeltaFloat;
import com.astemir.astemirlib.re.core.math.Easing;
import com.astemir.astemirlib.re.core.math.Transform;
import com.eternal_creations.archies_dino_mod.client.model.registry.ADEntityModels;
import com.eternal_creations.archies_dino_mod.client.model.render.entity.MountedListener;
import com.eternal_creations.archies_dino_mod.client.model.render.entity.layer.PassengerLayer;
import com.eternal_creations.archies_dino_mod.client.particle.effect.TherimossEffects;
import com.eternal_creations.archies_dino_mod.common.entity.base.Mount;
import com.eternal_creations.archies_dino_mod.common.entity.mob.therimoss.TherimossEntity;
import com.eternal_creations.archies_dino_mod.common.entity.projectile.TherimossProjectileEntity;
import com.eternal_creations.archies_dino_mod.common.items.ADItems;
import com.eternal_creations.archies_dino_mod.common.items.weapon.TherimossClawsItem;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class TherimossClient
implements MountedListener<TherimossEntity> {
    private static final String[] SADDLE_BONES = new String[]{"saddle", "saddle_head", "saddle_neck1", "saddle_neck2"};
    public static final String BONE_HEAD = "head";
    public static final String BONE_NECK = "neck";
    public static final String BONE_ROOT = "root";
    public static final String BONE_TAIL = "tail1";
    public static final String BONE_TAIL2 = "tail2";
    public static final String LOCATOR_PASSENGER = "passenger";
    public static final String BONE_BODY = "body";
    public static final Mount.MountPoint<TherimossEntity> SADDLED_MOUNT_POINT = new Mount.MountPoint(true, Mount.PointType.LOCATOR, "passenger", new Vec3(0.0, (double)-0.6f, 0.0));
    public static final Mount.MountPoint<TherimossEntity> UNSADDLED_MOUNT_POINT = new Mount.MountPoint(false, Mount.PointType.BONE, "body", new Vec3(0.0, (double)1.15f, (double)-0.6f));
    private static final String ANIMATION_IDLE = "idle";
    private static final String ANIMATION_WALK = "walk";
    private static final String ANIMATION_RUN = "run";
    private static final String ANIMATION_REST = "resting";
    private static final String ANIMATION_FALL = "fall_idle";
    private static final String ANIMATION_CLIMB_IDLE = "climb_idle";
    private static final String ANIMATION_CLIMB_UP = "climb_up";
    private static final String ANIMATION_CLIMB_DOWN = "climb_down";
    private static final String ANIMATION_CLIMB_START = "climb_start";
    private static final String ANIMATION_CLIMB_STOP = "climb_stop";
    private static final String ANIMATION_CLIMB_FINISH = "climb_finish";
    private static final String ANIMATION_JUMP = "jump";
    private static final String ANIMATION_LAND = "land";
    private static final String ANIMATION_DOWN = "down";
    private static final String ANIMATION_UP = "up";
    private static final String ANIMATION_DEATH = "death";
    private static final String ANIMATION_EAT = "eat";
    private static final String ANIMATION_ATTACK_HIT = "attack1";
    private static final String ANIMATION_ATTACK_BITE = "attack2";
    private static final String ANIMATION_ATTACK_THROW = "attack3";
    private static final String TEXTURE = "main";
    private static final String TEXTURE_CLAWS_COPPER = "claw_copper";
    private static final String TEXTURE_CLAWS_IRON = "claw_iron";
    private static final String TEXTURE_CLAWS_GOLD = "claw_gold";
    private static final String TEXTURE_CLAWS_DIAMOND = "claw_diamond";
    private static final String TEXTURE_CLAWS_NETHERITE = "claw_netherite";

    public void onInitialize(ModelSystem<TherimossEntity> modelSystem, TherimossEntity entity) {
        modelSystem.addRootOperation(new RootOperation[]{CommonRootOperations.SHAKING, CommonRootOperations.SmoothBodyRotation.of((float)0.3f), CommonRootOperations.RollOnRotation.of((float)15.0f, (float)0.8f, (float)0.2f), CommonRootOperations.GRUMM_ROTATION});
        this.setupAnimations(modelSystem, entity);
        this.setupPhysics(modelSystem, entity);
    }

    @Override
    public void onUpdate(ModelSystem<TherimossEntity> modelSystem, TherimossEntity instance, float partialTick) {
        MountedListener.super.onUpdate(modelSystem, instance, partialTick);
        TherimossProjectileEntity heldProjectile = instance.getHeldProjectile();
        if (heldProjectile != null) {
            Transform transform = modelSystem.getBoneTransform((Object)instance, "rightItem");
            transform.getTranslation(0.0f, 0.0f, -0.5f, heldProjectile.renderPos);
            heldProjectile.renderPos.add(Mth.m_14139_((double)partialTick, (double)instance.f_19854_, (double)instance.m_20185_()), Mth.m_14139_((double)partialTick, (double)instance.f_19855_, (double)instance.m_20186_()), Mth.m_14139_((double)partialTick, (double)instance.f_19856_, (double)instance.m_20189_()));
        }
    }

    @Override
    public Mount.MountPoint<TherimossEntity> getSaddledPoint() {
        return SADDLED_MOUNT_POINT;
    }

    @Override
    public Mount.MountPoint<TherimossEntity> getUnsaddledPoint() {
        return UNSADDLED_MOUNT_POINT;
    }

    private void setupAnimations(ModelSystem<TherimossEntity> modelSystem, TherimossEntity entity) {
        LayerNode.Definition rootState = this.buildRootState(entity);
        ((AnimatorModule)modelSystem.addModule(AnimatorModule::new)).setRoot((AbstractNode.Definition)ModifierNode.of((AbstractNode.Definition)rootState).add(Modifier.Rotate.x((DeltaFloat)entity.wallRotation).andThen(Modifier.Translate.z((DeltaFloat)entity.wallOffset)), new String[]{BONE_ROOT}).add(Modifier.Procedural.lookAtOrganic((LivingEntity)entity, (float)10.0f, (float)0.1f, (float)45.0f, (float)65.0f), new String[]{BONE_HEAD}).add(Modifier.Procedural.lookAtOrganic((LivingEntity)entity, (float)10.0f, (float)0.1f, (float)45.0f, (float)65.0f).scaled(0.5f), new String[]{BONE_NECK}).add(Modifier.visibility(entity::m_6254_), SADDLE_BONES)).setDefaultEasing((Easing)Easing.Simple.SINE_OUT, (Easing)Easing.Simple.SINE_IN);
    }

    private LayerNode.Definition buildRootState(TherimossEntity entity) {
        StateMachineNode.Definition climbStateMachine = StateMachineNode.of().addState((AbstractNode.Definition)ClipNode.of((String)ANIMATION_CLIMB_START, (ActionState)entity.actions.climbStart), () -> ((ActionState)entity.actions.climbStart).isActive()).addState((AbstractNode.Definition)ClipNode.of((String)ANIMATION_CLIMB_STOP, (ActionState)entity.actions.climbStop), () -> ((ActionState)entity.actions.climbStop).isActive()).addState((AbstractNode.Definition)ClipNode.of((String)ANIMATION_CLIMB_UP).events(TherimossEffects.CLIMB_UP_EFFECTS), 0.2f, () -> entity.isClimbing() && entity.climbVelocity > 0.05f).addState((AbstractNode.Definition)ClipNode.of((String)ANIMATION_CLIMB_DOWN).events(TherimossEffects.CLIMB_DOWN_EFFECTS), 0.2f, () -> entity.isClimbing() && entity.climbVelocity < -0.05f).addState((AbstractNode.Definition)ClipNode.of((String)ANIMATION_CLIMB_IDLE), 0.2f, entity::isClimbing).addState(EmptyNode.of());
        Weight.Definition[] definitionArray = new Weight.Definition[3];
        definitionArray[0] = entity.actions.climbStart.weight();
        definitionArray[1] = entity.actions.climbStop.weight();
        definitionArray[2] = Weight.of(entity::isClimbing);
        LayerNode.Definition movementState = LayerNode.of((AbstractNode.Definition)this.buildLocomotionState(entity), (AbstractNode.Definition)climbStateMachine, (Weight.Definition)Weight.combine((Weight.Definition[])definitionArray));
        StateMachineNode.Definition actionStateMachine = StateMachineNode.of().addState((AbstractNode.Definition)ClipNode.of((String)ANIMATION_DEATH, (ActionState)entity.actions.death).events(TherimossEffects.DEATH_EFFECTS), () -> ((ActionState)entity.actions.death).isActive()).addState((AbstractNode.Definition)ClipNode.of((String)ANIMATION_UP, (ActionState)entity.actions.up), 0.3f, entity.actions.up.whenActiveWithoutFadeOut()).addState((AbstractNode.Definition)ClipNode.of((String)ANIMATION_DOWN, (ActionState)entity.actions.down), 0.3f, entity.actions.down.whenActiveWithoutFadeOut()).addState((AbstractNode.Definition)ClipNode.of((String)ANIMATION_REST), 0.3f, entity::isSitting).addState((AbstractNode.Definition)ClipNode.of((String)ANIMATION_EAT, (ActionState)entity.actions.eat).events(TherimossEffects.EAT_EFFECTS), () -> ((ActionState)entity.actions.eat).isActive()).addState((AbstractNode.Definition)ClipNode.of((String)ANIMATION_ATTACK_BITE, (ActionState)entity.actions.attackBite).events(TherimossEffects.ATTACK_BITE_EFFECTS), () -> ((ActionState)entity.actions.attackBite).isActive()).addState((AbstractNode.Definition)ClipNode.of((String)ANIMATION_ATTACK_HIT, (ActionState)entity.actions.attackHit).events(TherimossEffects.ATTACK_HIT_EFFECTS), () -> ((ActionState)entity.actions.attackHit).isActive()).addState((AbstractNode.Definition)ClipNode.of((String)ANIMATION_ATTACK_THROW, (ActionState)entity.actions.attackShoot), () -> ((ActionState)entity.actions.attackShoot).isActive()).addState(EmptyNode.of(), 0.5f);
        Weight.Definition mantleWeight = entity.actions.climbFinish.weight().smoothed(0.3f);
        Weight.Definition[] definitionArray2 = new Weight.Definition[9];
        definitionArray2[0] = Weight.of(entity::isSitting).smoothed(0.3f);
        definitionArray2[1] = mantleWeight;
        definitionArray2[2] = entity.actions.up.weight();
        definitionArray2[3] = entity.actions.down.weight();
        definitionArray2[4] = entity.actions.eat.weight();
        definitionArray2[5] = entity.actions.death.weight();
        definitionArray2[6] = entity.actions.attackBite.weight();
        definitionArray2[7] = entity.actions.attackHit.weight();
        definitionArray2[8] = entity.actions.attackShoot.weight();
        LayerNode.Definition bodyLayer = LayerNode.of((AbstractNode.Definition)BlendNode.of((AbstractNode.Definition)movementState, (AbstractNode.Definition)ClipNode.of((String)ANIMATION_CLIMB_FINISH, (ActionState)entity.actions.climbFinish), (Weight.Definition)mantleWeight), (AbstractNode.Definition)actionStateMachine, (Weight.Definition)Weight.combine((Weight.Definition[])definitionArray2));
        StateMachineNode.Definition jumpStateMachine = StateMachineNode.of().addState((AbstractNode.Definition)ClipNode.of((String)ANIMATION_JUMP, (ActionState)entity.actions.jump), () -> ((ActionState)entity.actions.jump).isActive()).addState((AbstractNode.Definition)ClipNode.of((String)ANIMATION_LAND, (ActionState)entity.actions.land).events(TherimossEffects.LAND_EFFECTS), () -> ((ActionState)entity.actions.land).isActive()).addState((AbstractNode.Definition)ClipNode.of((String)ANIMATION_FALL), 0.2f, entity::isFalling).addState(EmptyNode.of(), 0.3f);
        Weight.Definition[] definitionArray3 = new Weight.Definition[3];
        definitionArray3[0] = entity.actions.jump.weight();
        definitionArray3[1] = entity.actions.land.weight();
        definitionArray3[2] = Weight.of(entity::isFalling, (float)0.1f);
        return LayerNode.of((AbstractNode.Definition)bodyLayer, (AbstractNode.Definition)jumpStateMachine, (Weight.Definition)Weight.combine((Weight.Definition[])definitionArray3));
    }

    private LayerNode.Definition buildLocomotionState(TherimossEntity entity) {
        EntityLocomotion locomotion = entity.getLocomotion();
        StateMachineNode.Definition walkState = StateMachineNode.of((State[])new State[]{State.of((AbstractNode.Definition)ClipNode.of((String)ANIMATION_RUN, (ProgressEvaluator)locomotion.runEvaluator(2.5f)).events(TherimossEffects.RUN_EFFECTS), (float)0.2f, (Condition)locomotion.whenRunning()), State.of((AbstractNode.Definition)ClipNode.of((String)ANIMATION_WALK, (ProgressEvaluator)locomotion.walkEvaluator(2.0f)).events(TherimossEffects.WALK_EFFECTS), (float)0.2f)});
        return LayerNode.of((AbstractNode.Definition)ClipNode.of((String)ANIMATION_IDLE).events(TherimossEffects.IDLE_EFFECTS), (AbstractNode.Definition)walkState, (Weight.Definition)locomotion.walkWeight().mul(Weight.of(entity::isGroundedForAnimation, (float)0.2f)));
    }

    private void setupPhysics(ModelSystem<TherimossEntity> modelSystem, TherimossEntity entity) {
        EntityLocomotion locomotion = entity.getLocomotion();
        PhysicsModule physics = (PhysicsModule)modelSystem.addModule(PhysicsModule::new);
        physics.createChain(() -> ((EntityLocomotion)locomotion).getLocalMovement(), () -> ((EntityLocomotion)locomotion).getTurnSpeed()).addBone(BONE_TAIL, physics.createProperties()).addBone(BONE_TAIL2, physics.createProperties()).restOffset(0.0f, 0.15f, 1.0f).inertia(0.0f, 0.4f).gravity(-0.03f).stiffness(0.15f);
    }

    public static void setupModel(AstemirModel<TherimossEntity> model, LayerContainer<TherimossEntity> layers) {
        ModelTexture texture = ModelTexture.of(model, (String)TEXTURE, RenderType::m_110458_);
        ModelTexture copperTexture = ModelTexture.of(model, (String)TEXTURE_CLAWS_COPPER, RenderType::m_110458_);
        ModelTexture ironTexture = ModelTexture.of(model, (String)TEXTURE_CLAWS_IRON, RenderType::m_110458_);
        ModelTexture goldTexture = ModelTexture.of(model, (String)TEXTURE_CLAWS_GOLD, RenderType::m_110458_);
        ModelTexture diamondTexture = ModelTexture.of(model, (String)TEXTURE_CLAWS_DIAMOND, RenderType::m_110458_);
        ModelTexture netheriteTexture = ModelTexture.of(model, (String)TEXTURE_CLAWS_NETHERITE, RenderType::m_110458_);
        layers.add(BaseRenderLayer.of((ModelTexture)texture));
        layers.add(BaseRenderLayer.of(entity -> {
            ItemStack clawsItemStack = entity.getClawsItem();
            if (clawsItemStack.m_41720_() instanceof TherimossClawsItem) {
                if (clawsItemStack.m_150930_((Item)ADItems.THERIMOSS_CLAWS_COPPER.get())) {
                    return copperTexture;
                }
                if (clawsItemStack.m_150930_((Item)ADItems.THERIMOSS_CLAWS_IRON.get())) {
                    return ironTexture;
                }
                if (clawsItemStack.m_150930_((Item)ADItems.THERIMOSS_CLAWS_GOLD.get())) {
                    return goldTexture;
                }
                if (clawsItemStack.m_150930_((Item)ADItems.THERIMOSS_CLAWS_DIAMOND.get())) {
                    return diamondTexture;
                }
                if (clawsItemStack.m_150930_((Item)ADItems.THERIMOSS_CLAWS_NETHERITE.get())) {
                    return netheriteTexture;
                }
            }
            return copperTexture;
        }).condition(mob -> mob.isAdult() && mob.hasClaws()));
        layers.add(new PassengerLayer<TherimossEntity>(model, new Mount.MountPoint(true, Mount.PointType.LOCATOR, LOCATOR_PASSENGER, new Vec3(0.0, (double)-0.1f, (double)0.3f)), new Mount.MountPoint(true, Mount.PointType.BONE, BONE_BODY, new Vec3(0.0, (double)-0.1f, (double)0.3f))));
    }

    public static void setupModelBaby(AstemirModel<TherimossEntity> model, LayerContainer<TherimossEntity> layers) {
        ModelTexture texture = ModelTexture.of(model, (String)TEXTURE, RenderType::m_110458_);
        layers.add(BaseRenderLayer.of((ModelTexture)texture));
    }

    public static AstemirEntityRenderer.Provider<TherimossEntity> createRenderProvider() {
        return AstemirEntityRenderer.of(entity -> entity.m_6162_() ? ADEntityModels.THERIMOSS_BABY : ADEntityModels.THERIMOSS, TherimossClient::new).overlay(OverlayComponent.livingEntityNoDeathOverlay()).shadow((ShadowComponent)ShadowComponent.of((float)1.7f));
    }
}

