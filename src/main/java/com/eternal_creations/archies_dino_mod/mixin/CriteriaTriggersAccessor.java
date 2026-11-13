/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.advancements.CriteriaTriggers
 *  net.minecraft.advancements.CriterionTrigger
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.gen.Invoker
 */
package com.eternal_creations.archies_dino_mod.mixin;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.CriterionTrigger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@Mixin(value={CriteriaTriggers.class})
public interface CriteriaTriggersAccessor {
    @Invoker(value="register")
    public static <T extends CriterionTrigger<?>> T invokeRegister(T criterion) {
        throw new IllegalArgumentException("This is an accessor, it should not be executed.");
    }
}

