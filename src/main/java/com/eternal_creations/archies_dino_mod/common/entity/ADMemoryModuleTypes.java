/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 *  net.minecraft.util.Unit
 *  net.minecraft.world.entity.ai.memory.MemoryModuleType
 *  net.minecraft.world.entity.ai.memory.WalkTarget
 *  net.minecraft.world.level.pathfinder.Path
 */
package com.eternal_creations.archies_dino_mod.common.entity;

import com.eternal_creations.archies_dino_mod.platform.Services;
import com.eternal_creations.archies_dino_mod.util.ResourceSupplier;
import com.mojang.serialization.Codec;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.level.pathfinder.Path;

public class ADMemoryModuleTypes {
    public static final ResourceSupplier<MemoryModuleType<Unit>> IS_ENRAGED = Services.COMMON.registerMemoryModuleType("is_enraged", Codec.unit((Object)Unit.INSTANCE));
    public static final ResourceSupplier<MemoryModuleType<Unit>> IS_READY_TO_SCREAM = Services.COMMON.registerMemoryModuleType("is_ready_to_scream", Codec.unit((Object)Unit.INSTANCE));
    public static final ResourceSupplier<MemoryModuleType<Path>> FLEE_PATH = Services.COMMON.registerMemoryModuleType("flee_path", null);
    public static final ResourceSupplier<MemoryModuleType<WalkTarget>> WALK_COMMAND = Services.COMMON.registerMemoryModuleType("walk_command", null);

    public static void init() {
    }
}

