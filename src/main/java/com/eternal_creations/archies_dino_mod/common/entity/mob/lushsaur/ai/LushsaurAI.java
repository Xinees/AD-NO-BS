/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.PathfinderMob
 *  net.minecraft.world.entity.ai.goal.FloatGoal
 *  net.minecraft.world.entity.ai.goal.Goal
 *  net.minecraft.world.entity.ai.goal.GoalSelector
 *  net.minecraft.world.entity.ai.goal.LookAtPlayerGoal
 *  net.minecraft.world.entity.ai.goal.RandomLookAroundGoal
 *  net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.entity.raid.Raider
 *  net.minecraft.world.item.crafting.Ingredient
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.state.BlockState
 */
package com.eternal_creations.archies_dino_mod.common.entity.mob.lushsaur.ai;

import com.eternal_creations.archies_dino_mod.common.ADTags;
import com.eternal_creations.archies_dino_mod.common.block.ADBlocks;
import com.eternal_creations.archies_dino_mod.common.entity.ai.goal.GoalAvoidEntity;
import com.eternal_creations.archies_dino_mod.common.entity.ai.goal.GoalBreedWithEgg;
import com.eternal_creations.archies_dino_mod.common.entity.ai.goal.GoalFollowParent;
import com.eternal_creations.archies_dino_mod.common.entity.ai.goal.GoalHurtByTarget;
import com.eternal_creations.archies_dino_mod.common.entity.ai.goal.GoalLayEgg;
import com.eternal_creations.archies_dino_mod.common.entity.ai.goal.GoalOwnerHurtByTarget;
import com.eternal_creations.archies_dino_mod.common.entity.ai.goal.GoalOwnerHurtTarget;
import com.eternal_creations.archies_dino_mod.common.entity.ai.goal.GoalTemptWithItem;
import com.eternal_creations.archies_dino_mod.common.entity.ai.goal.action.GoalActionBlockInteract;
import com.eternal_creations.archies_dino_mod.common.entity.ai.goal.action.GoalActionMeleeAttack;
import com.eternal_creations.archies_dino_mod.common.entity.ai.goal.action.GoalChargeMeleeAttack;
import com.eternal_creations.archies_dino_mod.common.entity.ai.goal.action.GoalRandomAction;
import com.eternal_creations.archies_dino_mod.common.entity.ai.goal.action.WrappedAction;
import com.eternal_creations.archies_dino_mod.common.entity.mob.lushsaur.LushsaurEntity;
import com.eternal_creations.archies_dino_mod.common.entity.mob.lushsaur.ai.LushsaurGoalHideFromEntities;
import com.eternal_creations.archies_dino_mod.common.items.ADItems;
import com.eternal_creations.archies_dino_mod.common.sound.ADSoundEvents;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.state.BlockState;

public class LushsaurAI {
    public static void setupAI(LushsaurEntity mob, GoalSelector goalSelector, GoalSelector targetSelector) {
        WrappedAction attackHeadbutt = WrappedAction.of(() -> mob.actionAttackHeadbutt, 0.4f).maxRange(() -> mob.m_6162_() ? 2.0 : 5.0);
        WrappedAction attackBite = WrappedAction.of(() -> mob.actionAttackBite, 0.4f).maxRange(() -> mob.m_6162_() ? 2.0 : 5.0);
        WrappedAction attackTail = WrappedAction.of(() -> mob.actionAttackTail, 0.8f).maxRange(() -> mob.m_6162_() ? 4.0 : 7.0);
        WrappedAction attackTailStomp = WrappedAction.of(() -> mob.actionAttackTailStomp, 0.6f).maxRange(() -> mob.m_6162_() ? 3.0 : 7.0);
        WrappedAction roar = WrappedAction.of(() -> mob.actionRoar);
        WrappedAction eat = WrappedAction.of(() -> mob.actionEat).maxRange(() -> mob.m_6162_() ? 2.0 : 4.0);
        WrappedAction bury = WrappedAction.of(() -> mob.actionBury).condition(() -> !mob.isBuried());
        WrappedAction emerge = WrappedAction.of(() -> mob.actionEmerge).condition(mob::isBuried);
        goalSelector.m_25352_(0, (Goal)new FloatGoal((Mob)mob));
        goalSelector.m_25352_(1, (Goal)new LushsaurGoalHideFromEntities(mob, 10.0, LushsaurAI::isThreatEntity));
        goalSelector.m_25352_(2, GoalAvoidEntity.of(Player.class).speed(2.0).awareDistance(15.0).keepFighting().avoidPredicate(LushsaurAI::shouldAvoidEntity).shouldUse(() -> !mob.isTamed()).build((PathfinderMob)mob));
        goalSelector.m_25352_(3, new GoalChargeMeleeAttack<LushsaurEntity>(mob, 2.5, 6.0, 46, 140, 100, roar, List.of(attackHeadbutt, attackBite), ADSoundEvents.LUSHSAUR_RUN_ATTACK, () -> LushsaurAI.isNoActiveAction(mob)));
        goalSelector.m_25352_(4, GoalActionMeleeAttack.of(attackHeadbutt, attackBite, attackTail, attackTailStomp).speed(1.8).interval(60).canAttack(() -> LushsaurAI.isNoActiveAction(mob)).build(mob));
        goalSelector.m_25352_(5, (Goal)new GoalTemptWithItem((PathfinderMob)mob, 0.7, Ingredient.m_43929_((ItemLike[])new ItemLike[]{(ItemLike)ADItems.AMBER.get()}), false));
        goalSelector.m_25352_(5, new GoalLayEgg<LushsaurEntity>(mob, 0.7, ADBlocks.LUSHSAUR_EGG, (level, pos) -> level.m_8055_(pos).m_204336_(ADTags.Blocks.LUSHSAUR_EGG_HATCHABLE_BLOCKS)));
        goalSelector.m_25352_(5, new GoalBreedWithEgg<LushsaurEntity>(mob, 0.7, 12.0, 4.0));
        goalSelector.m_25352_(6, new GoalFollowParent<LushsaurEntity>(mob, 2.0, 10.0, 6.0));
        goalSelector.m_25352_(7, GoalActionBlockInteract.of(eat).speed(0.7).interval(400).canInteract(() -> LushsaurAI.canStartEating(mob)).blockFilter(LushsaurAI::isEatableBlock).build(mob));
        goalSelector.m_25352_(8, GoalRandomAction.of(bury, emerge).condition(() -> LushsaurAI.canHideOrShow(mob)).chance(0.33f).cooldown(800).build(mob));
        goalSelector.m_25352_(9, (Goal)new WaterAvoidingRandomStrollGoal((PathfinderMob)mob, (double)0.7f));
        goalSelector.m_25352_(10, (Goal)new LookAtPlayerGoal((Mob)mob, Player.class, 3.0f));
        goalSelector.m_25352_(11, (Goal)new RandomLookAroundGoal((Mob)mob));
        targetSelector.m_25352_(1, new GoalOwnerHurtByTarget<LushsaurEntity>(mob));
        targetSelector.m_25352_(2, new GoalOwnerHurtTarget<LushsaurEntity>(mob));
        targetSelector.m_25352_(3, (Goal)new GoalHurtByTarget((PathfinderMob)mob, target -> !mob.isBuried() || mob.getHurtTolerance() <= 0, new Class[0]));
    }

    private static boolean canStartEating(LushsaurEntity mob) {
        return mob.isNoActionActive() && mob.m_5448_() == null && mob.m_6688_() == null;
    }

    private static boolean isThreatEntity(LivingEntity livingEntity) {
        return livingEntity instanceof Raider;
    }

    private static boolean isNoActiveAction(LushsaurEntity mob) {
        return !mob.actionAttackHeadbutt.isActive() && !mob.actionAttackBite.isActive() && !mob.actionAttackTail.isActive() && !mob.actionAttackTailStomp.isActive() && !mob.actionRoar.isActive() && !mob.actionBury.isActive() && !mob.actionEmerge.isActive() && !mob.actionEat.isActive() && !mob.actionDeath.isActive() && !mob.isBuried() && !mob.isSitting();
    }

    private static boolean canHideOrShow(LushsaurEntity mob) {
        return mob.m_5448_() == null && !mob.isTamed() && mob.getDigCooldownTicks() <= 0 && !mob.actionBury.isActive() && !mob.actionEmerge.isActive() && !mob.actionEat.isActive();
    }

    private static boolean shouldAvoidEntity(LivingEntity entity) {
        return !entity.m_6047_();
    }

    private static boolean isEatableBlock(BlockState state, BlockPos pos) {
        return state.m_204336_(ADTags.Blocks.LUSHSAUR_EDIBLE_BLOCK);
    }
}

