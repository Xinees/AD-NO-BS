/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.ai.behavior.BehaviorControl
 *  net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder
 *  net.minecraft.world.entity.ai.memory.MemoryModuleType
 */
package com.eternal_creations.archies_dino_mod.common.entity.mob.sculksaur.ai;

import com.eternal_creations.archies_dino_mod.common.entity.mob.sculksaur.SculksaurEntity;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import java.util.Optional;
import java.util.function.Function;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class SetSculksaurRoarTarget {
    public static <E extends SculksaurEntity> BehaviorControl<E> create(Function<E, Optional<? extends LivingEntity>> targetFinder) {
        return BehaviorBuilder.m_258034_(instance -> instance.group((App)instance.m_258080_(MemoryModuleType.f_217782_), (App)instance.m_258080_(MemoryModuleType.f_26372_), (App)instance.m_257492_(MemoryModuleType.f_26326_)).apply((Applicative)instance, (roarTarget, attackTarget, cantReachSince) -> (level, sculksaur, l) -> {
            Optional optional = (Optional)targetFinder.apply(sculksaur);
            if (optional.filter(sculksaur::canTargetEntity).isEmpty()) {
                return false;
            }
            roarTarget.m_257512_((Object)((LivingEntity)optional.get()));
            cantReachSince.m_257971_();
            return true;
        }));
    }
}

