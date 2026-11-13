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
package com.eternal_creations.archies_dino_mod.common.entity.mob.sculkraptor.ai;

import com.eternal_creations.archies_dino_mod.common.entity.ADActivities;
import com.eternal_creations.archies_dino_mod.common.entity.ADMemoryModuleTypes;
import com.eternal_creations.archies_dino_mod.common.entity.ADSensorTypes;
import com.eternal_creations.archies_dino_mod.common.entity.ai.behaviour.CustomMoveSink;
import com.eternal_creations.archies_dino_mod.common.entity.mob.sculkraptor.SculkraptorEntity;
import com.eternal_creations.archies_dino_mod.common.entity.mob.sculkraptor.ai.SculkraptorCombatAttack;
import com.eternal_creations.archies_dino_mod.common.entity.mob.sculkraptor.ai.SculkraptorFlee;
import com.eternal_creations.archies_dino_mod.common.entity.mob.sculkraptor.ai.SculkraptorPrepareToScream;
import com.eternal_creations.archies_dino_mod.common.entity.mob.sculkraptor.ai.SculkraptorRangedAttack;
import com.eternal_creations.archies_dino_mod.common.entity.mob.sculkraptor.ai.SculkraptorSniffing;
import com.eternal_creations.archies_dino_mod.common.entity.mob.sculkraptor.ai.SculkraptorSonicBoom;
import com.eternal_creations.archies_dino_mod.common.entity.mob.sculkraptor.ai.SetSculkraptorAttackTarget;
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
public class SculkraptorAI {
    public static final float SPEED_MULTIPLIER_WHEN_IDLING = 0.5f;
    public static final float SPEED_MULTIPLIER_WHEN_INVESTIGATING = 0.7f;
    public static final float SPEED_MULTIPLIER_WHEN_FIGHTING = 1.0f;
    private static final int DISTURBANCE_LOCATION_EXPIRY_TIME = 100;
    private static final List<SensorType<? extends Sensor<? super SculkraptorEntity>>> SENSOR_TYPES = List.of(SensorType.f_26812_, ADSensorTypes.SCULKRAPTOR_SENSOR.get());
    private static final List<MemoryModuleType<?>> MEMORY_TYPES = List.of(MemoryModuleType.f_217768_, MemoryModuleType.f_148204_, MemoryModuleType.f_148205_, MemoryModuleType.f_26368_, MemoryModuleType.f_148206_, MemoryModuleType.f_26333_, MemoryModuleType.f_26371_, ADMemoryModuleTypes.WALK_COMMAND.get(), MemoryModuleType.f_26370_, MemoryModuleType.f_26326_, MemoryModuleType.f_26377_, MemoryModuleType.f_26372_, ADMemoryModuleTypes.IS_READY_TO_SCREAM.get(), MemoryModuleType.f_26373_, MemoryModuleType.f_148194_, MemoryModuleType.f_217783_, ADMemoryModuleTypes.FLEE_PATH.get(), MemoryModuleType.f_217784_, MemoryModuleType.f_217785_, MemoryModuleType.f_217772_, MemoryModuleType.f_217773_, MemoryModuleType.f_217774_, MemoryModuleType.f_217775_, MemoryModuleType.f_217776_, MemoryModuleType.f_217777_);

    public static void updateActivity(SculkraptorEntity sculkraptor) {
        sculkraptor.m_6274_().m_21926_((List)ImmutableList.of((Object)ADActivities.FLEE.get(), (Object)ADActivities.SCREAM.get(), (Object)Activity.f_37988_, (Object)Activity.f_219850_, (Object)Activity.f_219849_, (Object)Activity.f_37979_));
    }

    public static Brain<?> makeBrain(SculkraptorEntity sculkraptor, Dynamic<?> ops) {
        Brain.Provider provider = Brain.m_21923_(MEMORY_TYPES, SENSOR_TYPES);
        Brain brain = provider.m_22073_(ops);
        SculkraptorAI.initCoreActivity((Brain<SculkraptorEntity>)brain);
        SculkraptorAI.initFleeActivity((Brain<SculkraptorEntity>)brain);
        SculkraptorAI.initScreamActivity((Brain<SculkraptorEntity>)brain);
        SculkraptorAI.initIdleActivity((Brain<SculkraptorEntity>)brain);
        SculkraptorAI.initCombatActivity(sculkraptor, (Brain<SculkraptorEntity>)brain);
        SculkraptorAI.initInvestigateActivity((Brain<SculkraptorEntity>)brain);
        SculkraptorAI.initSniffingActivity((Brain<SculkraptorEntity>)brain);
        brain.m_21930_((Set)ImmutableSet.of((Object)Activity.f_37978_));
        brain.m_21944_(Activity.f_37979_);
        brain.m_21962_();
        return brain;
    }

    private static void initCoreActivity(Brain<SculkraptorEntity> brain) {
        brain.m_21891_(Activity.f_37978_, 0, ImmutableList.of((Object)new Swim(0.8f), (Object)SetWardenLookTarget.m_257897_(), (Object)new LookAtTargetSink(45, 90), (Object)((Object)new CustomMoveSink(ADMemoryModuleTypes.WALK_COMMAND.get())), (Object)((Object)new CustomMoveSink((MemoryModuleType<WalkTarget>)MemoryModuleType.f_26370_, ADMemoryModuleTypes.WALK_COMMAND.get()))));
    }

    private static void initFleeActivity(Brain<SculkraptorEntity> brain) {
        brain.m_21907_(ADActivities.FLEE.get(), ImmutableList.of((Object)Pair.of((Object)1, (Object)((Object)new SculkraptorFlee(1.0f)))), (Set)ImmutableSet.of((Object)Pair.of(ADMemoryModuleTypes.FLEE_PATH.get(), (Object)MemoryStatus.VALUE_PRESENT)), (Set)ImmutableSet.of(ADMemoryModuleTypes.FLEE_PATH.get()));
    }

    private static void initScreamActivity(Brain<SculkraptorEntity> brain) {
        int priority = 1;
        brain.m_21907_(ADActivities.SCREAM.get(), ImmutableList.of((Object)Pair.of((Object)priority++, (Object)new SculkraptorSonicBoom()), (Object)Pair.of((Object)priority, (Object)new SculkraptorPrepareToScream())), (Set)ImmutableSet.of((Object)Pair.of((Object)MemoryModuleType.f_26372_, (Object)MemoryStatus.VALUE_PRESENT), (Object)Pair.of((Object)MemoryModuleType.f_217775_, (Object)MemoryStatus.VALUE_ABSENT)), (Set)ImmutableSet.of(ADMemoryModuleTypes.IS_READY_TO_SCREAM.get()));
    }

    private static void initIdleActivity(Brain<SculkraptorEntity> brain) {
        brain.m_21891_(Activity.f_37979_, 10, ImmutableList.of(SetSculkraptorAttackTarget.create(SculkraptorEntity::getEntityAngryAt), (Object)TryToSniff.m_257812_(), (Object)new RunOne((Map)ImmutableMap.of((Object)MemoryModuleType.f_217785_, (Object)MemoryStatus.VALUE_ABSENT), (List)ImmutableList.of((Object)Pair.of((Object)RandomStroll.m_257965_((float)0.5f), (Object)2), (Object)Pair.of((Object)new DoNothing(30, 60), (Object)1)))));
    }

    private static void initInvestigateActivity(Brain<SculkraptorEntity> brain) {
        int priority = 5;
        brain.m_21907_(Activity.f_219850_, ImmutableList.of((Object)Pair.of((Object)priority++, SetSculkraptorAttackTarget.create(SculkraptorEntity::getEntityAngryAt)), (Object)Pair.of((Object)priority, (Object)GoToTargetLocation.m_257680_((MemoryModuleType)MemoryModuleType.f_217783_, (int)2, (float)0.7f))), (Set)ImmutableSet.of((Object)Pair.of((Object)MemoryModuleType.f_217783_, (Object)MemoryStatus.VALUE_PRESENT), (Object)Pair.of(ADMemoryModuleTypes.WALK_COMMAND.get(), (Object)MemoryStatus.VALUE_ABSENT)), (Set)ImmutableSet.of((Object)MemoryModuleType.f_217783_));
    }

    private static void initSniffingActivity(Brain<SculkraptorEntity> brain) {
        int priority = 5;
        brain.m_21907_(Activity.f_219849_, ImmutableList.of((Object)Pair.of((Object)priority++, SetSculkraptorAttackTarget.create(SculkraptorEntity::getEntityAngryAt)), (Object)Pair.of((Object)priority, new SculkraptorSniffing())), (Set)ImmutableSet.of((Object)Pair.of((Object)MemoryModuleType.f_217785_, (Object)MemoryStatus.VALUE_PRESENT), (Object)Pair.of(ADMemoryModuleTypes.WALK_COMMAND.get(), (Object)MemoryStatus.VALUE_ABSENT)), (Set)ImmutableSet.of((Object)MemoryModuleType.f_217785_));
    }

    private static void initCombatActivity(SculkraptorEntity sculksaur, Brain<SculkraptorEntity> brain) {
        int priority = 1;
        brain.m_21907_(Activity.f_37988_, ImmutableList.of((Object)Pair.of((Object)priority++, (Object)StopAttackingIfTargetInvalid.m_257811_(living -> !sculksaur.getAngerLevel().m_219236_() || !sculksaur.canTargetEntity((Entity)living), SculkraptorAI::onTargetInvalid, (boolean)false)), (Object)Pair.of((Object)priority++, (Object)SetEntityLookTarget.m_257836_(living -> SculkraptorAI.isTarget(sculksaur, living), (float)((float)sculksaur.m_21133_(Attributes.f_22277_)))), (Object)Pair.of((Object)priority++, (Object)SetWalkTargetFromAttackTargetIfTargetOutOfReach.m_257469_((float)1.0f)), (Object)Pair.of((Object)priority++, (Object)new SculkraptorRangedAttack()), (Object)Pair.of((Object)priority, SculkraptorCombatAttack.create())), (Set)ImmutableSet.of((Object)Pair.of((Object)MemoryModuleType.f_26372_, (Object)MemoryStatus.VALUE_PRESENT)), (Set)ImmutableSet.of((Object)MemoryModuleType.f_26372_));
    }

    private static boolean isTarget(SculkraptorEntity warden, LivingEntity entity) {
        return warden.m_6274_().m_21952_(MemoryModuleType.f_26372_).filter(living -> living == entity).isPresent();
    }

    private static void onTargetInvalid(SculkraptorEntity warden, LivingEntity target) {
        if (!warden.canTargetEntity((Entity)target)) {
            warden.clearAnger((Entity)target);
        }
    }

    public static void setDisturbanceLocation(SculkraptorEntity warden, BlockPos disturbanceLocation) {
        if (warden.m_9236_().m_6857_().m_61937_(disturbanceLocation) && warden.getEntityAngryAt().isEmpty() && warden.m_6274_().m_21952_(MemoryModuleType.f_26372_).isEmpty()) {
            warden.m_6274_().m_21882_(MemoryModuleType.f_217772_, (Object)Unit.INSTANCE, 100L);
            warden.m_6274_().m_21882_(MemoryModuleType.f_26371_, (Object)new BlockPosTracker(disturbanceLocation), 100L);
            warden.m_6274_().m_21882_(MemoryModuleType.f_217783_, (Object)disturbanceLocation, 100L);
            warden.m_6274_().m_21936_(MemoryModuleType.f_26370_);
        }
    }
}

