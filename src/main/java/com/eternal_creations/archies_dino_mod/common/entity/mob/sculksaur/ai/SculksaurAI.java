/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.ImmutableSet
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.serialization.Dynamic
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.core.BlockPos
 *  net.minecraft.util.Unit
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.ai.Brain
 *  net.minecraft.world.entity.ai.Brain$Provider
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.entity.ai.behavior.BlockPosTracker
 *  net.minecraft.world.entity.ai.behavior.DoNothing
 *  net.minecraft.world.entity.ai.behavior.GoToTargetLocation
 *  net.minecraft.world.entity.ai.behavior.LookAtTargetSink
 *  net.minecraft.world.entity.ai.behavior.RandomStroll
 *  net.minecraft.world.entity.ai.behavior.RunOne
 *  net.minecraft.world.entity.ai.behavior.SetEntityLookTarget
 *  net.minecraft.world.entity.ai.behavior.SetWalkTargetFromAttackTargetIfTargetOutOfReach
 *  net.minecraft.world.entity.ai.behavior.StopAttackingIfTargetInvalid
 *  net.minecraft.world.entity.ai.behavior.Swim
 *  net.minecraft.world.entity.ai.behavior.warden.SetWardenLookTarget
 *  net.minecraft.world.entity.ai.behavior.warden.TryToSniff
 *  net.minecraft.world.entity.ai.memory.MemoryModuleType
 *  net.minecraft.world.entity.ai.memory.MemoryStatus
 *  net.minecraft.world.entity.ai.memory.WalkTarget
 *  net.minecraft.world.entity.ai.sensing.Sensor
 *  net.minecraft.world.entity.ai.sensing.SensorType
 *  net.minecraft.world.entity.schedule.Activity
 */
package com.eternal_creations.archies_dino_mod.common.entity.mob.sculksaur.ai;

import com.eternal_creations.archies_dino_mod.common.entity.ADActivities;
import com.eternal_creations.archies_dino_mod.common.entity.ADMemoryModuleTypes;
import com.eternal_creations.archies_dino_mod.common.entity.ADSensorTypes;
import com.eternal_creations.archies_dino_mod.common.entity.ai.behaviour.CustomMoveSink;
import com.eternal_creations.archies_dino_mod.common.entity.mob.sculksaur.SculksaurEntity;
import com.eternal_creations.archies_dino_mod.common.entity.mob.sculksaur.ai.SculksaurCombatAttack;
import com.eternal_creations.archies_dino_mod.common.entity.mob.sculksaur.ai.SculksaurEmerging;
import com.eternal_creations.archies_dino_mod.common.entity.mob.sculksaur.ai.SculksaurEnragedAttack;
import com.eternal_creations.archies_dino_mod.common.entity.mob.sculksaur.ai.SculksaurRoar;
import com.eternal_creations.archies_dino_mod.common.entity.mob.sculksaur.ai.SculksaurSniffing;
import com.eternal_creations.archies_dino_mod.common.entity.mob.sculksaur.ai.SculksaurSonicBoom;
import com.eternal_creations.archies_dino_mod.common.entity.mob.sculksaur.ai.SetSculksaurAttackTarget;
import com.eternal_creations.archies_dino_mod.common.entity.mob.sculksaur.ai.SetSculksaurRoarTarget;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.BlockPosTracker;
import net.minecraft.world.entity.ai.behavior.DoNothing;
import net.minecraft.world.entity.ai.behavior.GoToTargetLocation;
import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
import net.minecraft.world.entity.ai.behavior.RandomStroll;
import net.minecraft.world.entity.ai.behavior.RunOne;
import net.minecraft.world.entity.ai.behavior.SetEntityLookTarget;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromAttackTargetIfTargetOutOfReach;
import net.minecraft.world.entity.ai.behavior.StopAttackingIfTargetInvalid;
import net.minecraft.world.entity.ai.behavior.Swim;
import net.minecraft.world.entity.ai.behavior.warden.SetWardenLookTarget;
import net.minecraft.world.entity.ai.behavior.warden.TryToSniff;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.schedule.Activity;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class SculksaurAI {
    private static final float SPEED_MULTIPLIER_WHEN_IDLING = 0.5f;
    private static final float SPEED_MULTIPLIER_WHEN_INVESTIGATING = 0.5f;
    private static final float SPEED_MULTIPLIER_WHEN_CHASING = 1.3f;
    private static final float SPEED_MULTIPLIER_WHEN_FIGHTING = 1.3f;
    private static final int DISTURBANCE_LOCATION_EXPIRY_TIME = 100;
    private static final List<SensorType<? extends Sensor<? super SculksaurEntity>>> SENSOR_TYPES = List.of(SensorType.f_26812_, ADSensorTypes.SCULKSAUR_SENSOR.get());
    private static final List<MemoryModuleType<?>> MEMORY_TYPES = List.of(MemoryModuleType.f_148204_, MemoryModuleType.f_148205_, MemoryModuleType.f_26368_, MemoryModuleType.f_148206_, MemoryModuleType.f_26333_, MemoryModuleType.f_26371_, ADMemoryModuleTypes.WALK_COMMAND.get(), MemoryModuleType.f_26370_, MemoryModuleType.f_26326_, MemoryModuleType.f_26377_, MemoryModuleType.f_26372_, ADMemoryModuleTypes.IS_ENRAGED.get(), MemoryModuleType.f_26373_, MemoryModuleType.f_148194_, MemoryModuleType.f_217782_, MemoryModuleType.f_217783_, MemoryModuleType.f_217784_, MemoryModuleType.f_217785_, MemoryModuleType.f_217786_, MemoryModuleType.f_217772_, MemoryModuleType.f_217773_, MemoryModuleType.f_217774_, MemoryModuleType.f_217775_, MemoryModuleType.f_217776_, MemoryModuleType.f_217777_);

    public static void updateActivity(SculksaurEntity warden) {
        warden.m_6274_().m_21926_((List)ImmutableList.of((Object)Activity.f_219852_, (Object)ADActivities.CHASE.get(), (Object)Activity.f_37988_, (Object)Activity.f_219851_, (Object)Activity.f_219850_, (Object)Activity.f_219849_, (Object)Activity.f_37979_));
    }

    public static Brain<?> makeBrain(SculksaurEntity sculksaur, Dynamic<?> ops) {
        Brain.Provider provider = Brain.m_21923_(MEMORY_TYPES, SENSOR_TYPES);
        Brain brain = provider.m_22073_(ops);
        SculksaurAI.initCoreActivity((Brain<SculksaurEntity>)brain);
        SculksaurAI.initEmergeActivity((Brain<SculksaurEntity>)brain);
        SculksaurAI.initIdleActivity((Brain<SculksaurEntity>)brain);
        SculksaurAI.initRoarActivity((Brain<SculksaurEntity>)brain);
        SculksaurAI.initEnragedActivity(sculksaur, (Brain<SculksaurEntity>)brain);
        SculksaurAI.initCombatActivity(sculksaur, (Brain<SculksaurEntity>)brain);
        SculksaurAI.initInvestigateActivity((Brain<SculksaurEntity>)brain);
        SculksaurAI.initSniffingActivity((Brain<SculksaurEntity>)brain);
        brain.m_21930_((Set)ImmutableSet.of((Object)Activity.f_37978_));
        brain.m_21944_(Activity.f_37979_);
        brain.m_21962_();
        return brain;
    }

    private static void initCoreActivity(Brain<SculksaurEntity> brain) {
        brain.m_21891_(Activity.f_37978_, 0, ImmutableList.of((Object)new Swim(0.8f), (Object)SetWardenLookTarget.m_257897_(), (Object)new LookAtTargetSink(45, 90), (Object)((Object)new CustomMoveSink(ADMemoryModuleTypes.WALK_COMMAND.get())), (Object)((Object)new CustomMoveSink((MemoryModuleType<WalkTarget>)MemoryModuleType.f_26370_, ADMemoryModuleTypes.WALK_COMMAND.get()))));
    }

    private static void initEmergeActivity(Brain<SculksaurEntity> brain) {
        brain.m_21895_(Activity.f_219852_, 5, ImmutableList.of((Object)new SculksaurEmerging()), MemoryModuleType.f_217786_);
    }

    private static void initIdleActivity(Brain<SculksaurEntity> brain) {
        brain.m_21891_(Activity.f_37979_, 10, ImmutableList.of(SetSculksaurRoarTarget.create(SculksaurEntity::getEntityAngryAt), (Object)TryToSniff.m_257812_(), (Object)new RunOne((Map)ImmutableMap.of((Object)MemoryModuleType.f_217785_, (Object)MemoryStatus.VALUE_ABSENT), (List)ImmutableList.of((Object)Pair.of((Object)RandomStroll.m_257965_((float)0.5f), (Object)2), (Object)Pair.of((Object)new DoNothing(30, 60), (Object)1)))));
    }

    private static void initInvestigateActivity(Brain<SculksaurEntity> brain) {
        int priority = 5;
        brain.m_21907_(Activity.f_219850_, ImmutableList.of((Object)Pair.of((Object)priority++, SetSculksaurAttackTarget.create(SculksaurEntity::getEntityAngryAt)), (Object)Pair.of((Object)priority, (Object)GoToTargetLocation.m_257680_((MemoryModuleType)MemoryModuleType.f_217783_, (int)2, (float)0.5f))), (Set)ImmutableSet.of((Object)Pair.of((Object)MemoryModuleType.f_217783_, (Object)MemoryStatus.VALUE_PRESENT), (Object)Pair.of(ADMemoryModuleTypes.WALK_COMMAND.get(), (Object)MemoryStatus.VALUE_ABSENT)), (Set)ImmutableSet.of((Object)MemoryModuleType.f_217783_));
    }

    private static void initSniffingActivity(Brain<SculksaurEntity> brain) {
        int priority = 5;
        brain.m_21907_(Activity.f_219849_, ImmutableList.of((Object)Pair.of((Object)priority++, SetSculksaurAttackTarget.create(SculksaurEntity::getEntityAngryAt)), (Object)Pair.of((Object)priority, new SculksaurSniffing())), (Set)ImmutableSet.of((Object)Pair.of((Object)MemoryModuleType.f_217785_, (Object)MemoryStatus.VALUE_PRESENT), (Object)Pair.of(ADMemoryModuleTypes.WALK_COMMAND.get(), (Object)MemoryStatus.VALUE_ABSENT)), (Set)ImmutableSet.of((Object)MemoryModuleType.f_217785_));
    }

    private static void initRoarActivity(Brain<SculksaurEntity> brain) {
        brain.m_21895_(Activity.f_219851_, 10, ImmutableList.of((Object)new SculksaurRoar()), MemoryModuleType.f_217782_);
    }

    private static void initEnragedActivity(SculksaurEntity sculksaur, Brain<SculksaurEntity> brain) {
        int priority = 10;
        brain.m_21907_(ADActivities.CHASE.get(), ImmutableList.of((Object)Pair.of((Object)priority++, (Object)StopAttackingIfTargetInvalid.m_257811_(target -> {
            if (sculksaur.m_20188_() < target.m_20186_() - 5.0) {
                return true;
            }
            return !sculksaur.getAngerLevel().m_219236_() || !sculksaur.canTargetEntity((Entity)target);
        }, (sculksaurEntity, living) -> {}, (boolean)false)), (Object)Pair.of((Object)priority++, (Object)new SculksaurSonicBoom()), (Object)Pair.of((Object)priority++, (Object)SetEntityLookTarget.m_257836_(living -> SculksaurAI.isTarget(sculksaur, living), (float)((float)sculksaur.m_21133_(Attributes.f_22277_)))), (Object)Pair.of((Object)priority++, (Object)SetWalkTargetFromAttackTargetIfTargetOutOfReach.m_257469_((float)1.3f)), (Object)Pair.of((Object)priority, SculksaurEnragedAttack.create())), (Set)ImmutableSet.of((Object)Pair.of((Object)MemoryModuleType.f_26372_, (Object)MemoryStatus.VALUE_PRESENT), (Object)Pair.of(ADMemoryModuleTypes.IS_ENRAGED.get(), (Object)MemoryStatus.VALUE_PRESENT)), (Set)ImmutableSet.of(ADMemoryModuleTypes.IS_ENRAGED.get()));
    }

    private static void initCombatActivity(SculksaurEntity sculksaur, Brain<SculksaurEntity> brain) {
        int priority = 1;
        brain.m_21907_(Activity.f_37988_, ImmutableList.of((Object)Pair.of((Object)priority++, (Object)StopAttackingIfTargetInvalid.m_257811_(living -> !sculksaur.getAngerLevel().m_219236_() || !sculksaur.canTargetEntity((Entity)living), SculksaurAI::onTargetInvalid, (boolean)false)), (Object)Pair.of((Object)priority++, (Object)SetEntityLookTarget.m_257836_(living -> SculksaurAI.isTarget(sculksaur, living), (float)((float)sculksaur.m_21133_(Attributes.f_22277_)))), (Object)Pair.of((Object)priority++, (Object)SetWalkTargetFromAttackTargetIfTargetOutOfReach.m_257469_((float)1.3f)), (Object)Pair.of((Object)priority++, (Object)new SculksaurSonicBoom()), (Object)Pair.of((Object)priority, SculksaurCombatAttack.create())), (Set)ImmutableSet.of((Object)Pair.of((Object)MemoryModuleType.f_26372_, (Object)MemoryStatus.VALUE_PRESENT)), (Set)ImmutableSet.of((Object)MemoryModuleType.f_26372_));
    }

    private static boolean isTarget(SculksaurEntity warden, LivingEntity entity) {
        return warden.m_6274_().m_21952_(MemoryModuleType.f_26372_).filter(living -> living == entity).isPresent();
    }

    private static void onTargetInvalid(SculksaurEntity warden, LivingEntity target) {
        if (!warden.canTargetEntity((Entity)target)) {
            warden.clearAnger((Entity)target);
        }
    }

    public static void setDisturbanceLocation(SculksaurEntity warden, BlockPos disturbanceLocation) {
        if (warden.m_9236_().m_6857_().m_61937_(disturbanceLocation) && warden.getEntityAngryAt().isEmpty() && warden.m_6274_().m_21952_(MemoryModuleType.f_26372_).isEmpty()) {
            warden.m_6274_().m_21882_(MemoryModuleType.f_217772_, (Object)Unit.INSTANCE, 100L);
            warden.m_6274_().m_21882_(MemoryModuleType.f_26371_, (Object)new BlockPosTracker(disturbanceLocation), 100L);
            warden.m_6274_().m_21882_(MemoryModuleType.f_217783_, (Object)disturbanceLocation, 100L);
            warden.m_6274_().m_21936_(MemoryModuleType.f_26370_);
        }
    }
}

