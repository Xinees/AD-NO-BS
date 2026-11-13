/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.tags.FluidTags
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
package com.eternal_creations.archies_dino_mod.common.entity.mob.obsidiceratops.ai;

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
import com.eternal_creations.archies_dino_mod.common.entity.ai.goal.action.GoalChargeMeleeAttack;
import com.eternal_creations.archies_dino_mod.common.entity.ai.goal.action.WrappedAction;
import com.eternal_creations.archies_dino_mod.common.entity.mob.obsidiceratops.ObsidiceratopsEntity;
import com.eternal_creations.archies_dino_mod.common.entity.mob.obsidiceratops.ai.ObsidiceratopsGoalDrinkLava;
import com.eternal_creations.archies_dino_mod.common.entity.mob.obsidiceratops.ai.ObsidiceratopsTargetGoal;
import com.eternal_creations.archies_dino_mod.common.items.ADItems;
import com.eternal_creations.archies_dino_mod.common.sound.ADSoundEvents;
import java.util.List;
import net.minecraft.tags.FluidTags;
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

public class ObsidiceratopsAI {
    public static void setupAI(final ObsidiceratopsEntity mob, GoalSelector goalSelector, GoalSelector targetSelector) {
        WrappedAction attackHit = WrappedAction.of(() -> mob.actionAttackHit, 0.4f).maxRange(() -> mob.m_6162_() ? 1.5 : 3.0);
        WrappedAction attackTail = WrappedAction.of(() -> mob.actionAttackTail, 0.8f).maxRange(() -> mob.m_6162_() ? 1.5 : 3.0);
        WrappedAction warn = WrappedAction.of(() -> mob.actionWarn);
        goalSelector.m_25352_(0, (Goal)new FloatGoal((Mob)mob){

            public boolean m_8036_() {
                return mob.m_20069_() && mob.m_204036_(FluidTags.f_13131_) > mob.m_20204_();
            }
        });
        goalSelector.m_25352_(2, new GoalChargeMeleeAttack<ObsidiceratopsEntity>(mob, 1.5, 6.0, 81, 180, 100, warn, List.of(attackHit), ADSoundEvents.OBSIDICERATOPS_CHARGE_AGRO, () -> ObsidiceratopsAI.isNoActiveAction(mob)));
        goalSelector.m_25352_(3, GoalActionMeleeAttack.of(attackHit, attackTail).speed(1.8).interval(60).canAttack(() -> ObsidiceratopsAI.isNoActiveAction(mob)).build(mob));
        goalSelector.m_25352_(4, (Goal)new ObsidiceratopsGoalDrinkLava(mob, 0.7f));
        goalSelector.m_25352_(5, new GoalLayEgg<ObsidiceratopsEntity>(mob, 0.7, ADBlocks.OBSIDICERATOPS_EGG, (level, pos) -> level.m_8055_(pos).m_204336_(ADTags.Blocks.OBSIDICERATOPS_EGG_HATCHABLE_BLOCKS)));
        goalSelector.m_25352_(5, new GoalBreedWithEgg<ObsidiceratopsEntity>(mob, 0.7, 12.0, 4.0));
        goalSelector.m_25352_(5, (Goal)new GoalTemptWithItem((PathfinderMob)mob, 0.7, Ingredient.m_43929_((ItemLike[])new ItemLike[]{(ItemLike)ADItems.AMBER.get()}), false));
        goalSelector.m_25352_(6, new GoalFollowParent<ObsidiceratopsEntity>(mob, 0.7, 10.0, 6.0));
        goalSelector.m_25352_(8, (Goal)new WaterAvoidingRandomStrollGoal((PathfinderMob)mob, 0.5));
        goalSelector.m_25352_(9, (Goal)new LookAtPlayerGoal((Mob)mob, Player.class, 3.5f));
        goalSelector.m_25352_(10, (Goal)new RandomLookAroundGoal((Mob)mob));
        targetSelector.m_25352_(1, new GoalOwnerHurtByTarget<ObsidiceratopsEntity>(mob));
        targetSelector.m_25352_(2, new GoalOwnerHurtTarget<ObsidiceratopsEntity>(mob));
        targetSelector.m_25352_(3, (Goal)new GoalHurtByTarget((PathfinderMob)mob, new Class[0]));
        targetSelector.m_25352_(4, (Goal)new ObsidiceratopsTargetGoal(mob));
    }

    private static boolean isNoActiveAction(ObsidiceratopsEntity mob) {
        return mob.isNoActionActive() && !mob.isSitting();
    }
}

