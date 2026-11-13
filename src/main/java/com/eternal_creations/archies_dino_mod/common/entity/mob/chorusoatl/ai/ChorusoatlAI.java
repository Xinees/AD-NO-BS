/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.PathfinderMob
 *  net.minecraft.world.entity.ai.goal.FloatGoal
 *  net.minecraft.world.entity.ai.goal.Goal
 *  net.minecraft.world.entity.ai.goal.GoalSelector
 *  net.minecraft.world.entity.ai.goal.LookAtPlayerGoal
 *  net.minecraft.world.entity.ai.goal.RandomLookAroundGoal
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.crafting.Ingredient
 *  net.minecraft.world.level.ItemLike
 */
package com.eternal_creations.archies_dino_mod.common.entity.mob.chorusoatl.ai;

import com.eternal_creations.archies_dino_mod.common.ADTags;
import com.eternal_creations.archies_dino_mod.common.block.ADBlocks;
import com.eternal_creations.archies_dino_mod.common.entity.ai.goal.GoalBreedWithEgg;
import com.eternal_creations.archies_dino_mod.common.entity.ai.goal.GoalHurtByTarget;
import com.eternal_creations.archies_dino_mod.common.entity.ai.goal.GoalLayEgg;
import com.eternal_creations.archies_dino_mod.common.entity.ai.goal.GoalOwnerHurtByTarget;
import com.eternal_creations.archies_dino_mod.common.entity.ai.goal.GoalOwnerHurtTarget;
import com.eternal_creations.archies_dino_mod.common.entity.ai.goal.GoalStrollLandAndAir;
import com.eternal_creations.archies_dino_mod.common.entity.ai.goal.GoalTemptWithItem;
import com.eternal_creations.archies_dino_mod.common.entity.ai.goal.action.GoalActionMeleeAttack;
import com.eternal_creations.archies_dino_mod.common.entity.ai.goal.action.GoalFlyMeleeAttack;
import com.eternal_creations.archies_dino_mod.common.entity.ai.goal.action.GoalRandomAction;
import com.eternal_creations.archies_dino_mod.common.entity.ai.goal.action.WrappedAction;
import com.eternal_creations.archies_dino_mod.common.entity.base.Flyer;
import com.eternal_creations.archies_dino_mod.common.entity.mob.chorusoatl.ChorusoatlEntity;
import com.eternal_creations.archies_dino_mod.common.entity.mob.chorusoatl.ai.ChorusoatlGoalFollowPack;
import com.eternal_creations.archies_dino_mod.common.entity.mob.chorusoatl.ai.ChorusoatlGoalTakeOffForTarget;
import com.eternal_creations.archies_dino_mod.common.entity.mob.chorusoatl.ai.ChorusoatlTargetGoalWarn;
import com.eternal_creations.archies_dino_mod.common.items.ADItems;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ChorusoatlAI {
    private static final float ATTACK_HIT_DISTANCE = 5.0f;
    private static final float ATTACK_BITE_DISTANCE = 5.0f;
    private static final float ATTACK_FLY_DISTANCE = 8.0f;
    private static final float ATTACK_BABY_HIT_DISTANCE = 2.0f;
    private static final float ATTACK_BABY_BITE_DISTANCE = 2.0f;
    private static final float ATTACK_BABY_FLY_DISTANCE = 5.0f;
    private static final float BREED_SEE_DISTANCE = 15.0f;
    private static final float BREED_DISTANCE = 4.0f;
    private static final int FLIGHT_NEAR_BLOCKS = 30;
    private static final int FLIGHT_FAR_BLOCKS = 50;
    private static final int FLIGHT_GROUND_THRESHOLD = 20;
    private static final int FLIGHT_TARGET_HEIGHT = 25;
    private static final int FLIGHT_MIN_HEIGHT = 15;
    private static final int FLIGHT_MAX_ATTEMPTS_TO_FIND_POS = 20;
    private static final float FLIGHT_ROAM_RADIUS = 64.0f;
    private static final float TAKE_OFF_TARGET_DISTANCE = 20.0f;
    private static final float TAKE_OFF_OR_LAND_CHANCE = 0.15f;
    private static final float SHAKE_CHANCE = 0.25f;
    private static final float LAND_WALK_SPEED = 0.7f;
    private static final float AIR_FLY_SPEED = 2.0f;
    private static final float BREED_WALK_SPEED = 0.7f;
    private static final float EGG_LAY_WALK_SPEED = 0.7f;
    private static final float LAND_MELEE_ATTACK_SPEED = 0.9f;
    private static final float AIR_ATTACK_SPEED = 2.5f;
    private static final float AIR_RETREAT_SPEED = 2.0f;
    private static final int TAKE_OFF_OR_LAND_COOLDOWN = 400;
    private static final int FLY_ATTACK_COOLDOWN = 120;
    private static final int SHAKE_COOLDOWN = 300;
    private static final int RETREAT_MIN_DURATION = 40;
    private static final int RETREAT_MAX_DURATION = 80;
    private static final int LAND_WALK_INTERVAL = 100;
    private static final int AIR_FLY_INTERVAL = 20;
    private static final float PACK_MIN_DISTANCE = 8.0f;
    private static final float PACK_MAX_DISTANCE = 48.0f;
    private static final float PACK_SEARCH_RANGE = 48.0f;

    public static void setupAI(ChorusoatlEntity mob, GoalSelector goalSelector, GoalSelector targetSelector) {
        RandomSource random = RandomSource.m_216335_((long)mob.m_20148_().getMostSignificantBits());
        WrappedAction takeOff = WrappedAction.of(() -> mob.setFlightPhase(Flyer.FlightPhase.PREPARING_TO_FLY)).condition(() -> !mob.isFlying() && !mob.hasEgg() && !mob.isTamed()).weight(0.75f * random.m_188501_());
        WrappedAction land = WrappedAction.of(() -> mob.setFlightPhase(Flyer.FlightPhase.PREPARING_TO_LAND)).condition(mob::isFlying).weight(0.25f * random.m_188501_());
        WrappedAction shake = WrappedAction.of(() -> mob.actionShake).condition(() -> !mob.isFlying());
        WrappedAction attackHit = WrappedAction.of(() -> mob.actionAttackHit, 0.4f).maxRange(() -> mob.m_6162_() ? 2.0 : 5.0);
        WrappedAction attackBite = WrappedAction.of(() -> mob.actionAttackBite, 0.4f).maxRange(() -> mob.m_6162_() ? 2.0 : 5.0);
        WrappedAction attackShoot = WrappedAction.of(() -> mob.actionAttackShoot, 0.2f).minRange(() -> mob.m_6162_() ? 10.0 : 12.0).maxRange(() -> mob.m_6162_() ? 20.0 : 45.0).cooldown(120);
        WrappedAction attackFly = WrappedAction.of(() -> mob.actionAttackFly).maxRange(() -> mob.m_6162_() ? 5.0 : 8.0);
        goalSelector.m_25352_(0, (Goal)new FloatGoal((Mob)mob));
        goalSelector.m_25352_(1, (Goal)new ChorusoatlGoalTakeOffForTarget(mob, 20.0));
        goalSelector.m_25352_(1, GoalRandomAction.of(takeOff, land).condition(() -> ChorusoatlAI.isNoActionUsed(mob)).chance(0.15f).cooldown(400).build(mob));
        goalSelector.m_25352_(2, GoalActionMeleeAttack.of(attackHit, attackBite, attackShoot).speed(0.9f).condition(() -> !mob.isFlying()).canAttack(() -> ChorusoatlAI.isNoActionUsed(mob)).interval(60).build(mob));
        goalSelector.m_25352_(2, GoalFlyMeleeAttack.of(attackFly).speed(2.5, 2.0).attackCooldown(120).retreatDuration(40, 80).condition(mob::isFlying).canAttack(() -> ChorusoatlAI.isNoActionUsed(mob)).build(mob));
        goalSelector.m_25352_(4, new GoalLayEgg<ChorusoatlEntity>(mob, 0.7f, ADBlocks.CHORUSOATL_EGG, (level, pos) -> level.m_8055_(pos).m_204336_(ADTags.Blocks.CHORUSOATL_EGG_HATCHABLE_BLOCKS)));
        goalSelector.m_25352_(4, new GoalBreedWithEgg<ChorusoatlEntity>(mob, 0.7f, 15.0, 4.0));
        goalSelector.m_25352_(4, (Goal)new GoalTemptWithItem((PathfinderMob)mob, 0.7f, Ingredient.m_43929_((ItemLike[])new ItemLike[]{(ItemLike)ADItems.AMBER.get()}), false));
        goalSelector.m_25352_(7, GoalRandomAction.of(shake).condition(() -> mob.m_5448_() == null && ChorusoatlAI.isNoActionUsed(mob)).chance(0.25f).cooldown(300).build(mob));
        goalSelector.m_25352_(8, (Goal)new ChorusoatlGoalFollowPack(mob, 0.7f, 2.0, 8.0f, 48.0f, 48.0f));
        goalSelector.m_25352_(8, (Goal)GoalStrollLandAndAir.of().speed(0.7f, 2.0).landRange(64, 48).intervals(100, 20).maxDistance(30, 50).maxRoamRadius(64.0).groundThreshold(20).targetFlyingHeight(25).minTargetDistanceToGround(15).maxAttempts(20).build((PathfinderMob)mob));
        goalSelector.m_25352_(9, (Goal)new LookAtPlayerGoal((Mob)mob, Player.class, 4.0f));
        goalSelector.m_25352_(10, (Goal)new RandomLookAroundGoal((Mob)mob));
        targetSelector.m_25352_(1, new GoalOwnerHurtByTarget<ChorusoatlEntity>(mob));
        targetSelector.m_25352_(2, new GoalOwnerHurtTarget<ChorusoatlEntity>(mob));
        targetSelector.m_25352_(3, (Goal)new GoalHurtByTarget((PathfinderMob)mob, new Class[0]));
        targetSelector.m_25352_(4, (Goal)new ChorusoatlTargetGoalWarn(mob));
    }

    public static boolean isNoActionUsed(ChorusoatlEntity mob) {
        return mob.isNoActionActive();
    }
}

