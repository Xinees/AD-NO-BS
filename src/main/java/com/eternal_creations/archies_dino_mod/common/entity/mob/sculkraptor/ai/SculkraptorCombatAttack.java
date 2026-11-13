/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.ai.behavior.EntityTracker
 *  net.minecraft.world.entity.ai.behavior.OneShot
 *  net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder
 *  net.minecraft.world.entity.ai.memory.MemoryModuleType
 *  net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities
 */
package com.eternal_creations.archies_dino_mod.common.entity.mob.sculkraptor.ai;

import com.eternal_creations.archies_dino_mod.common.entity.mob.sculkraptor.SculkraptorEntity;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.behavior.OneShot;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class SculkraptorCombatAttack {
    private static final int COOLDOWN = 36;

    public static OneShot<SculkraptorEntity> create() {
        return BehaviorBuilder.m_258034_(instance -> instance.group((App)instance.m_257492_(MemoryModuleType.f_26371_), (App)instance.m_257495_(MemoryModuleType.f_26372_), (App)instance.m_258080_(MemoryModuleType.f_26373_), (App)instance.m_257495_(MemoryModuleType.f_148205_)).apply((Applicative)instance, (lookTarget, attackTarget, coolDown, nearestVisible) -> (level, sculkraptor, l) -> {
            LivingEntity target = (LivingEntity)instance.m_258051_(attackTarget);
            if (sculkraptor.m_217066_(target) && ((NearestVisibleLivingEntities)instance.m_258051_(nearestVisible)).m_186107_(target) && sculkraptor.isNoActionActive()) {
                lookTarget.m_257512_((Object)new EntityTracker((Entity)target, true));
                sculkraptor.setActionTargetEntity(target);
                sculkraptor.playActionSynced(sculkraptor.actionAttackBiteStand);
                coolDown.m_257465_((Object)true, 36L);
                return true;
            }
            return false;
        }));
    }
}

