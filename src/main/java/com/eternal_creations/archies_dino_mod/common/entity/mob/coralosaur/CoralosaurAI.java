/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.PathfinderMob
 *  net.minecraft.world.entity.ai.goal.BreathAirGoal
 *  net.minecraft.world.entity.ai.goal.Goal
 *  net.minecraft.world.entity.ai.goal.GoalSelector
 *  net.minecraft.world.entity.ai.goal.LookAtPlayerGoal
 *  net.minecraft.world.entity.ai.goal.MeleeAttackGoal
 *  net.minecraft.world.entity.ai.goal.RandomLookAroundGoal
 *  net.minecraft.world.entity.ai.goal.RandomSwimmingGoal
 *  net.minecraft.world.entity.ai.goal.TemptGoal
 *  net.minecraft.world.entity.ai.goal.TryFindWaterGoal
 *  net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.crafting.Ingredient
 */
package com.eternal_creations.archies_dino_mod.common.entity.mob.coralosaur;

import com.eternal_creations.archies_dino_mod.common.ADTags;
import com.eternal_creations.archies_dino_mod.common.entity.ai.goal.GoalCustomBreed;
import com.eternal_creations.archies_dino_mod.common.entity.ai.goal.GoalOwnerHurtByTarget;
import com.eternal_creations.archies_dino_mod.common.entity.ai.goal.GoalOwnerHurtTarget;
import com.eternal_creations.archies_dino_mod.common.entity.ai.goal.action.GoalActionMeleeAttack;
import com.eternal_creations.archies_dino_mod.common.entity.ai.goal.action.WrappedAction;
import com.eternal_creations.archies_dino_mod.common.entity.mob.coralosaur.CoralosaurEntity;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.BreathAirGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.TryFindWaterGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.Ingredient;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CoralosaurAI {
    public static void setupAI(CoralosaurEntity mob, GoalSelector goalSelector, GoalSelector targetSelector) {
        WrappedAction attackBite = WrappedAction.of(() -> mob.actionBite, 0.8f).maxRange(() -> mob.m_6162_() ? 1.5 : 3.0);
        WrappedAction attackGrab = WrappedAction.of(() -> mob.actionGrab, 0.4f).maxRange(() -> mob.m_6162_() ? 2.5 : 5.0);
        goalSelector.m_25352_(0, (Goal)new BreathAirGoal((PathfinderMob)mob));
        goalSelector.m_25352_(0, (Goal)new TryFindWaterGoal((PathfinderMob)mob));
        goalSelector.m_25352_(1, (Goal)new GoalCustomBreed(mob, 1.0, 16.0));
        goalSelector.m_25352_(2, (Goal)new TemptGoal((PathfinderMob)mob, 1.25, Ingredient.m_204132_(ADTags.Items.CORALOSAUR_EDIBLE_ITEM), false));
        goalSelector.m_25352_(4, (Goal)new RandomSwimmingGoal((PathfinderMob)mob, 0.7, 10));
        goalSelector.m_25352_(4, (Goal)new RandomLookAroundGoal((Mob)mob));
        goalSelector.m_25352_(5, (Goal)new LookAtPlayerGoal((Mob)mob, Player.class, 6.0f));
        goalSelector.m_25352_(6, (Goal)new MeleeAttackGoal((PathfinderMob)mob, (double)1.2f, true));
        goalSelector.m_25352_(3, GoalActionMeleeAttack.of(attackBite, attackGrab).speed(1.8).interval(60).canAttack(() -> CoralosaurAI.isNoActiveAction(mob)).targetFilter(target -> !mob.isOwnedBy((LivingEntity)target)).build(mob));
        targetSelector.m_25352_(1, new GoalOwnerHurtByTarget<CoralosaurEntity>(mob));
        targetSelector.m_25352_(2, new GoalOwnerHurtTarget<CoralosaurEntity>(mob));
        targetSelector.m_25352_(3, (Goal)new NearestAttackableTargetGoal((Mob)mob, Player.class, true));
    }

    private static boolean isNoActiveAction(CoralosaurEntity mob) {
        return !mob.actionBite.isActive() && !mob.actionGrab.isActive() && !mob.actionWake.isActive() && !mob.actionSpecial.isActive() && !mob.actionCamo.isActive() && !mob.actionBury.isActive() && !mob.actionDragDown.isActive() && !mob.actionDeath.isActive() && !mob.isBuried() && !mob.isSitting();
    }
}

