/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.PathfinderMob
 *  net.minecraft.world.entity.ai.goal.FloatGoal
 *  net.minecraft.world.entity.ai.goal.Goal
 *  net.minecraft.world.entity.ai.goal.GoalSelector
 *  net.minecraft.world.entity.ai.goal.LookAtPlayerGoal
 *  net.minecraft.world.entity.ai.goal.RandomLookAroundGoal
 *  net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.crafting.Ingredient
 *  net.minecraft.world.level.ItemLike
 */
package com.eternal_creations.archies_dino_mod.common.entity.mob.therimoss.ai;

import com.eternal_creations.archies_dino_mod.common.ADTags;
import com.eternal_creations.archies_dino_mod.common.block.ADBlocks;
import com.eternal_creations.archies_dino_mod.common.entity.ai.goal.GoalBreedWithEgg;
import com.eternal_creations.archies_dino_mod.common.entity.ai.goal.GoalFollowParent;
import com.eternal_creations.archies_dino_mod.common.entity.ai.goal.GoalHurtByTarget;
import com.eternal_creations.archies_dino_mod.common.entity.ai.goal.GoalLayEgg;
import com.eternal_creations.archies_dino_mod.common.entity.ai.goal.GoalOwnerHurtByTarget;
import com.eternal_creations.archies_dino_mod.common.entity.ai.goal.GoalOwnerHurtTarget;
import com.eternal_creations.archies_dino_mod.common.entity.ai.goal.GoalTemptWithItem;
import com.eternal_creations.archies_dino_mod.common.entity.ai.goal.action.GoalActionMeleeAttack;
import com.eternal_creations.archies_dino_mod.common.entity.ai.goal.action.WrappedAction;
import com.eternal_creations.archies_dino_mod.common.entity.mob.therimoss.TherimossEntity;
import com.eternal_creations.archies_dino_mod.common.entity.mob.therimoss.TherimossState;
import com.eternal_creations.archies_dino_mod.common.entity.mob.therimoss.ai.GoalTherimossEatPile;
import com.eternal_creations.archies_dino_mod.common.items.ADItems;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class TherimossAI {
    public static void setupAI(TherimossEntity mob, GoalSelector goalSelector, GoalSelector targetSelector) {
        WrappedAction attackBite = WrappedAction.of(() -> mob.actions.attackBite, 0.4f).maxRange(() -> mob.m_6162_() ? 1.0 : 2.0);
        WrappedAction attackHit = WrappedAction.of(() -> mob.actions.attackHit, 0.4f).maxRange(() -> mob.m_6162_() ? 1.0 : 2.0);
        WrappedAction attackShoot = WrappedAction.of(() -> mob.actions.attackShoot, 0.8f).minRange(() -> mob.m_6162_() ? 2.0 : 4.0).maxRange(() -> mob.m_6162_() ? 18.0 : 36.0).condition(mob::isAdult).cooldown(160);
        WrappedAction eat = WrappedAction.of(() -> mob.actions.eat).maxRange(() -> mob.m_6162_() ? 2.0 : 4.0);
        goalSelector.m_25352_(0, (Goal)new FloatGoal((Mob)mob));
        goalSelector.m_25352_(2, GoalActionMeleeAttack.of(attackBite, attackHit, attackShoot).speed(1.4).interval(80).canAttack(() -> TherimossAI.isNoActiveAction(mob)).build(mob));
        goalSelector.m_25352_(3, (Goal)new GoalTherimossEatPile(mob, eat, 0.7, 20, 8));
        goalSelector.m_25352_(4, new GoalLayEgg<TherimossEntity>(mob, 0.7, ADBlocks.THERIMOSS_EGG, (level, pos) -> level.m_8055_(pos).m_204336_(ADTags.Blocks.THERIMOSS_EGG_HATCHABLE_BLOCKS)));
        goalSelector.m_25352_(4, new GoalBreedWithEgg<TherimossEntity>(mob, 0.7, 12.0, 4.0));
        goalSelector.m_25352_(4, (Goal)new GoalTemptWithItem((PathfinderMob)mob, 0.7, Ingredient.m_43929_((ItemLike[])new ItemLike[]{(ItemLike)ADItems.AMBER.get()}), false));
        goalSelector.m_25352_(5, new GoalFollowParent<TherimossEntity>(mob, 1.6, 10.0, 6.0));
        goalSelector.m_25352_(8, (Goal)new WaterAvoidingRandomStrollGoal((PathfinderMob)mob, (double)0.7f));
        goalSelector.m_25352_(9, (Goal)new LookAtPlayerGoal((Mob)mob, Player.class, 3.0f));
        goalSelector.m_25352_(10, (Goal)new RandomLookAroundGoal((Mob)mob));
        targetSelector.m_25352_(1, new GoalOwnerHurtByTarget<TherimossEntity>(mob));
        targetSelector.m_25352_(2, new GoalOwnerHurtTarget<TherimossEntity>(mob));
        targetSelector.m_25352_(3, (Goal)new GoalHurtByTarget((PathfinderMob)mob, new Class[0]));
    }

    public static boolean isNoActiveAction(TherimossEntity mob) {
        return mob.isNoActionActive() && mob.getState(TherimossState::byId) == TherimossState.IDLE;
    }
}

