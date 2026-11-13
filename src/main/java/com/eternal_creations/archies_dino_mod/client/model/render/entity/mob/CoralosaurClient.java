/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.astemir.astemirlib.re.client.model.AstemirModel
 *  com.astemir.astemirlib.re.client.model.CommonRootOperations
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
 *  com.astemir.astemirlib.re.client.model.core.animation.node.modifier.ModifierNode
 *  com.astemir.astemirlib.re.client.model.core.animation.node.state.StateMachineNode
 *  com.astemir.astemirlib.re.client.model.core.animation.node.state.StateMachineNode$Definition
 *  com.astemir.astemirlib.re.client.model.core.system.ModelSystem
 *  com.astemir.astemirlib.re.client.model.core.system.ModelSystem$Listener
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
 *  com.astemir.astemirlib.re.core.math.Transform
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.Entity
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
import com.astemir.astemirlib.re.core.math.Transform;
import com.eternal_creations.archies_dino_mod.client.model.registry.ADEntityModels;
import com.eternal_creations.archies_dino_mod.client.model.render.entity.layer.BoatLeadLayer;
import com.eternal_creations.archies_dino_mod.client.model.render.entity.layer.MultiPassengerLayer;
import com.eternal_creations.archies_dino_mod.client.particle.effect.CoralosaurEffects;
import com.eternal_creations.archies_dino_mod.common.entity.base.Mount;
import com.eternal_creations.archies_dino_mod.common.entity.mob.coralosaur.CoralosaurEntity;
import java.util.List;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CoralosaurClient
implements ModelSystem.Listener<CoralosaurEntity> {
    public static final String BONE_HEAD = "head";
    public static final String BONE_HARNESS = "harness";
    public static final String BONE_BODY = "body";
    public static final String BONE_HARNESS_LEASH = "harness_leash";
    private static final String BONE_BACK_CORAL_1 = "b_coral1";
    private static final String BONE_BACK_CORAL_2 = "b_coral2";
    private static final String BONE_BACK_CORAL_3 = "b_coral3";
    private static final String BONE_EYEBROW = "eyebrow";
    private static final String[] STORAGE = new String[]{"storage1", "storage2", "storage3", "storage4", "storage5", "storage6", "storage7", "storage8"};
    public static final String[] CONTAINERS = new String[]{"storage_1_loc", "storage_2_loc", "storage_3_loc", "storage_4_loc", "storage_5_loc", "storage_6_loc", "storage_7_loc", "storage_8_loc"};
    private static final String ANIMATION_IDLE = "idle";
    private static final String ANIMATION_SLEEP = "sleep";
    private static final String ANIMATION_WAKE = "wake";
    private static final String ANIMATION_SWIM = "swim";
    private static final String ANIMATION_BITE = "bite";
    private static final String ANIMATION_GRAB = "grab";
    private static final String ANIMATION_DRAG_DOWN = "drag_down";
    private static final String ANIMATION_BURY = "bury";
    private static final String ANIMATION_SPECIAL = "special";
    private static final String ANIMATION_CAMO = "down";
    private static final String ANIMATION_DEATH = "death";
    private static final String TEXTURE = "coralsauruss";
    private static final String BABY_TEXTURE = "baby_coralsaurus";
    private static final Mount.MountPoint<CoralosaurEntity> SADDLED_MOUNT_POINT = new Mount.MountPoint<CoralosaurEntity>((coralosaur, integer) -> coralosaur.m_6254_() && ((Entity)coralosaur.m_20197_().get((int)integer)).m_19879_() != coralosaur.getVictim(), Mount.PointType.BONE, "harness", new Vec3(0.0, 0.5, -1.0));
    private static final Mount.MountPoint<CoralosaurEntity> UNSADDLED_MOUNT_POINT = new Mount.MountPoint<CoralosaurEntity>((coralosaur, integer) -> !coralosaur.m_6254_() && ((Entity)coralosaur.m_20197_().get((int)integer)).m_19879_() != coralosaur.getVictim(), Mount.PointType.BONE, "body", new Vec3(0.0, 2.35, -1.0));
    private static final Mount.MountPoint<CoralosaurEntity> VICTIM_MOUNT_POINT = new Mount.MountPoint<CoralosaurEntity>((coralosaur, integer) -> ((Entity)coralosaur.m_20197_().get((int)integer)).m_19879_() == coralosaur.getVictim(), Mount.PointType.BONE, "head", new Vec3(0.0, -2.5, -3.0));

    public void onInitialize(ModelSystem<CoralosaurEntity> modelSystem, CoralosaurEntity entity) {
        modelSystem.addRootOperation(new RootOperation[]{CommonRootOperations.SHAKING, CommonRootOperations.Y_BODY_ROTATION, CommonRootOperations.GRUMM_ROTATION});
        this.setupAnimations(modelSystem, entity);
    }

    public void onUpdate(ModelSystem<CoralosaurEntity> modelSystem, CoralosaurEntity instance, float partialTick) {
        List passengers;
        LocalPlayer player = Minecraft.m_91087_().f_91074_;
        if (player != null && (passengers = instance.m_20197_()).contains(player)) {
            MultiPassengerLayer.getPoint(instance, passengers.indexOf(player), SADDLED_MOUNT_POINT, UNSADDLED_MOUNT_POINT, VICTIM_MOUNT_POINT).ifPresent(point -> {
                Transform transform;
                Transform transform2 = transform = point.type() == Mount.PointType.BONE ? modelSystem.getBoneTransform((Object)instance, point.name()) : modelSystem.getLocatorTransform((Object)instance, point.name());
                if (transform == null) {
                    return;
                }
                float yRot = (Mth.m_14179_((float)partialTick, (float)instance.f_19859_, (float)instance.m_146908_()) - 180.0f) * ((float)Math.PI / 180);
                Vec3 vec3 = point.offset().m_82524_(-yRot);
                instance.setCameraPosition(transform.getX() + (float)vec3.f_82479_, transform.getY() + player.m_20206_() + (float)vec3.f_82480_, transform.getZ() + (float)vec3.f_82481_);
            });
        }
    }

    private void setupAnimations(ModelSystem<CoralosaurEntity> modelSystem, CoralosaurEntity entity) {
        LayerNode.Definition rootState = this.buildRootState(entity);
        ((AnimatorModule)modelSystem.addModule(AnimatorModule::new)).setRoot((AbstractNode.Definition)ModifierNode.of((AbstractNode.Definition)rootState).add(Modifier.lookAt(), new String[]{BONE_HEAD}).add(Modifier.visibility(entity::m_6254_), new String[]{BONE_HARNESS}).add(Modifier.visibility(() -> !entity.m_6254_()), new String[]{BONE_BACK_CORAL_1, BONE_BACK_CORAL_2, BONE_BACK_CORAL_3}).add(Modifier.visibility(entity::isBuried), new String[]{BONE_EYEBROW}).add(i -> Modifier.visibility(() -> entity.hasChest(i)), STORAGE));
    }

    private LayerNode.Definition buildRootState(CoralosaurEntity entity) {
        StateMachineNode.Definition rootStateMachine = StateMachineNode.of().addState((AbstractNode.Definition)ClipNode.of((String)ANIMATION_DEATH, (ActionState)entity.actionDeath).events(CoralosaurEffects.DEATH_EFFECTS), () -> ((ActionState)entity.actionDeath).isActive()).addState((AbstractNode.Definition)ClipNode.of((String)ANIMATION_WAKE, (ActionState)entity.actionWake).events(CoralosaurEffects.WAKE_EFFECTS), () -> ((ActionState)entity.actionWake).isActive()).addState((AbstractNode.Definition)ClipNode.of((String)ANIMATION_CAMO, (ActionState)entity.actionCamo).events(CoralosaurEffects.CAMO_EFFECTS), () -> ((ActionState)entity.actionCamo).isActive()).addState((AbstractNode.Definition)ClipNode.of((String)ANIMATION_SPECIAL, (ActionState)entity.actionSpecial).events(CoralosaurEffects.SPECIAL_EFFECTS), () -> ((ActionState)entity.actionSpecial).isActive()).addState((AbstractNode.Definition)ClipNode.of((String)ANIMATION_BITE, (ActionState)entity.actionBite), () -> ((ActionState)entity.actionBite).isActive()).addState(EmptyNode.of(), 0.5f);
        return LayerNode.of((AbstractNode.Definition)this.buildActionState(entity), (AbstractNode.Definition)rootStateMachine, (Weight.Definition)Weight.combine((Weight.Definition[])new Weight.Definition[]{entity.actionDeath.weight(), entity.actionWake.weight(), entity.actionCamo.weight(), entity.actionSpecial.weight(), entity.actionBite.weight(), Weight.of((DeltaFloat)entity.sittingWeight)}));
    }

    private LayerNode.Definition buildActionState(CoralosaurEntity entity) {
        StateMachineNode.Definition actionStateMachine = StateMachineNode.of().addState((AbstractNode.Definition)ClipNode.of((String)ANIMATION_GRAB, (ActionState)entity.actionGrab), () -> ((ActionState)entity.actionGrab).isActive()).addState((AbstractNode.Definition)ClipNode.of((String)ANIMATION_DRAG_DOWN, (ActionState)entity.actionDragDown), () -> ((ActionState)entity.actionDragDown).isActive()).addState((AbstractNode.Definition)ClipNode.of((String)ANIMATION_BURY, (ActionState)entity.actionBury), () -> ((ActionState)entity.actionBury).isActive()).addState(EmptyNode.of(), 0.5f);
        return LayerNode.of((AbstractNode.Definition)this.buildLocomotionState(entity), (AbstractNode.Definition)actionStateMachine, (Weight.Definition)Weight.combine((Weight.Definition[])new Weight.Definition[]{entity.actionBury.weight(), entity.actionGrab.weight(), entity.actionDragDown.weight()}));
    }

    private LayerNode.Definition buildLocomotionState(CoralosaurEntity entity) {
        EntityLocomotion locomotion = entity.getLocomotion();
        StateMachineNode.Definition idle = StateMachineNode.of().addState((AbstractNode.Definition)ClipNode.of((String)ANIMATION_SLEEP).events(CoralosaurEffects.SLEEP_EFFECTS), entity::isBuried).addState((AbstractNode.Definition)ClipNode.of((String)ANIMATION_IDLE), () -> !entity.isBuried()).addState(EmptyNode.of(), 0.5f);
        return LayerNode.of((AbstractNode.Definition)idle, (AbstractNode.Definition)ClipNode.of((String)ANIMATION_SWIM, (ProgressEvaluator)locomotion.walkEvaluator(3.5f)).events(CoralosaurEffects.SWIM_EFFECTS), (Weight.Definition)locomotion.walkWeight());
    }

    public static void setupModel(AstemirModel<CoralosaurEntity> model, LayerContainer<CoralosaurEntity> layers) {
        ModelTexture texture = ModelTexture.of(model, (String)TEXTURE, RenderType::m_110458_);
        layers.add(BaseRenderLayer.of((ModelTexture)texture));
        layers.add(new MultiPassengerLayer<CoralosaurEntity>(model, SADDLED_MOUNT_POINT, UNSADDLED_MOUNT_POINT, VICTIM_MOUNT_POINT));
        layers.add(new BoatLeadLayer<CoralosaurEntity>(model, BONE_HARNESS_LEASH));
    }

    public static void setupBabyModel(AstemirModel<CoralosaurEntity> model, LayerContainer<CoralosaurEntity> layers) {
        ModelTexture texture = ModelTexture.of(model, (String)BABY_TEXTURE, RenderType::m_110458_);
        layers.add(BaseRenderLayer.of((ModelTexture)texture));
    }

    public static AstemirEntityRenderer.Provider<CoralosaurEntity> createRenderProvider() {
        return AstemirEntityRenderer.of(entity -> entity.m_6162_() ? ADEntityModels.CORALOSAUR_BABY : ADEntityModels.CORALOSAUR, CoralosaurClient::new).overlay(OverlayComponent.livingEntityNoDeathOverlay()).shadow((ShadowComponent)ShadowComponent.of((instance, partialTick) -> instance.shadowRadius.get(partialTick)));
    }
}

