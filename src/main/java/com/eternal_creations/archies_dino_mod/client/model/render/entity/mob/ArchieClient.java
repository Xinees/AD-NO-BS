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
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.item.ItemDisplayContext
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
import com.eternal_creations.archies_dino_mod.client.model.registry.ADEntityModels;
import com.eternal_creations.archies_dino_mod.client.particle.ItemLayer;
import com.eternal_creations.archies_dino_mod.common.entity.mob.archie.ArchieEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;

public class ArchieClient
implements ModelSystem.Listener<ArchieEntity> {
    public static final String BONE_HEAD = "head";
    public static final String RIGHT_HAND_BONE = "RightArm";
    public static final String LEFT_HAND_BONE = "LeftArm";
    private static final String ANIMATION_IDLE = "idle";
    private static final String ANIMATION_WALK = "walk";
    private static final String ANIMATION_RUN = "run";
    private static final String ANIMATION_SWIM = "swim";
    private static final String ANIMATION_SWIM_IDLE = "swim_idle";
    private static final String ANIMATION_HURT = "hurt";
    private static final String ANIMATION_ATTACK = "attack";
    private static final String ANIMATION_SNEAK_IDLE = "sneak_idle";
    private static final String ANIMATION_SNEAK_WALK = "sneak_walk";
    private static final String TEXTURE = "archie";

    public void onInitialize(ModelSystem<ArchieEntity> modelSystem, ArchieEntity entity) {
        modelSystem.addRootOperation(new RootOperation[]{CommonRootOperations.SHAKING, CommonRootOperations.Y_BODY_ROTATION, CommonRootOperations.GRUMM_ROTATION});
        this.setupAnimations(modelSystem, entity);
    }

    private void setupAnimations(ModelSystem<ArchieEntity> modelSystem, ArchieEntity entity) {
        LayerNode.Definition rootState = this.buildActionState(entity);
        ((AnimatorModule)modelSystem.addModule(AnimatorModule::new)).setRoot((AbstractNode.Definition)ModifierNode.of((AbstractNode.Definition)rootState).add(Modifier.lookAt(), new String[]{BONE_HEAD}));
    }

    private LayerNode.Definition buildActionState(ArchieEntity entity) {
        StateMachineNode.Definition actionStateMachine = StateMachineNode.of().addState((AbstractNode.Definition)ClipNode.of((String)ANIMATION_ATTACK, (ActionState)entity.actionAttack), () -> ((ActionState)entity.actionAttack).isActive()).addState((AbstractNode.Definition)ClipNode.of((String)ANIMATION_HURT, (ActionState)entity.actionHurt), () -> ((ActionState)entity.actionHurt).isActive()).addState(EmptyNode.of(), 0.5f);
        return LayerNode.of((AbstractNode.Definition)this.buildLocomotionState(entity), (AbstractNode.Definition)actionStateMachine, (Weight.Definition)Weight.combine((Weight.Definition[])new Weight.Definition[]{entity.actionAttack.weight(), entity.actionHurt.weight()}));
    }

    private LayerNode.Definition buildLocomotionState(ArchieEntity entity) {
        EntityLocomotion locomotion = entity.getLocomotion();
        StateMachineNode.Definition idleStateMachine = StateMachineNode.of().addState((AbstractNode.Definition)ClipNode.of((String)ANIMATION_SWIM_IDLE), 0.3f, () -> ((ArchieEntity)entity).m_5842_()).addState((AbstractNode.Definition)ClipNode.of((String)ANIMATION_SNEAK_IDLE), 0.3f, () -> ((ArchieEntity)entity).m_6047_()).addState((AbstractNode.Definition)ClipNode.of((String)ANIMATION_IDLE), 0.3f);
        StateMachineNode.Definition walkStateMachine = StateMachineNode.of().addState((AbstractNode.Definition)ClipNode.of((String)ANIMATION_SWIM, (ProgressEvaluator)locomotion.walkEvaluator(3.5f)), 0.3f, () -> ((ArchieEntity)entity).m_5842_()).addState((AbstractNode.Definition)ClipNode.of((String)ANIMATION_SNEAK_WALK, (ProgressEvaluator)locomotion.walkEvaluator(3.5f)), 0.3f, () -> ((ArchieEntity)entity).m_6047_()).addState((AbstractNode.Definition)ClipNode.of((String)ANIMATION_RUN, (ProgressEvaluator)locomotion.runEvaluator(3.5f)), 0.3f, entity.getLocomotion().whenRunning()).addState((AbstractNode.Definition)ClipNode.of((String)ANIMATION_WALK, (ProgressEvaluator)locomotion.walkEvaluator(3.5f)), 0.3f);
        return LayerNode.of((AbstractNode.Definition)idleStateMachine, (AbstractNode.Definition)walkStateMachine, (Weight.Definition)locomotion.walkWeight());
    }

    public static void setupModel(AstemirModel<ArchieEntity> model, LayerContainer<ArchieEntity> layers) {
        ModelTexture texture = ModelTexture.of(model, (String)TEXTURE, RenderType::m_110458_);
        layers.add(BaseRenderLayer.of((ModelTexture)texture));
        layers.add(new ItemLayer<ArchieEntity>(model, LivingEntity::m_21205_, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, RIGHT_HAND_BONE, -0.0625f, 0.125f, -0.4375f, 180.0f, 180.0f, true));
        layers.add(new ItemLayer<ArchieEntity>(model, LivingEntity::m_21206_, ItemDisplayContext.THIRD_PERSON_LEFT_HAND, LEFT_HAND_BONE, 0.0625f, 0.125f, -0.4375f, 180.0f, 180.0f, false));
    }

    public static AstemirEntityRenderer.Provider<ArchieEntity> createRenderProvider() {
        return AstemirEntityRenderer.of(entity -> ADEntityModels.ARCHIE, ArchieClient::new).overlay(OverlayComponent.livingEntityNoDeathOverlay()).shadow((ShadowComponent)new ShadowComponent<ArchieEntity>(){

            public float getShadowRadius(ArchieEntity entity, float partialTick) {
                return 0.5f;
            }

            public float getShadowStrength(ArchieEntity entity, float partialTick) {
                return 1.0f;
            }
        });
    }
}

