/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.entity.ai.behavior.Behavior$Status
 *  net.minecraft.world.entity.ai.behavior.BehaviorControl
 *  net.minecraft.world.entity.ai.memory.MemoryModuleType
 */
package com.eternal_creations.archies_dino_mod.common.entity.mob.sculkraptor.ai;

import com.eternal_creations.archies_dino_mod.common.entity.mob.sculkraptor.SculkraptorEntity;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class SculkraptorPrepareToScream
implements BehaviorControl<SculkraptorEntity> {
    private Behavior.Status status = Behavior.Status.STOPPED;
    private long endTimestamp;

    public Behavior.Status m_22536_() {
        return this.status;
    }

    public final boolean tryStart(ServerLevel level, SculkraptorEntity sculkraptor, long gameTime) {
        this.status = Behavior.Status.RUNNING;
        this.endTimestamp = gameTime + 2L;
        if (!sculkraptor.isReadyToScream()) {
            sculkraptor.setReadyToScream(true);
            sculkraptor.m_6274_().m_21936_(MemoryModuleType.f_217776_);
        }
        return true;
    }

    public final void tickOrStop(ServerLevel level, SculkraptorEntity sculkraptor, long gameTime) {
        if (gameTime > this.endTimestamp) {
            this.doStop(level, sculkraptor, gameTime);
        }
    }

    public final void doStop(ServerLevel level, SculkraptorEntity sculkraptor, long gameTime) {
        this.status = Behavior.Status.STOPPED;
    }

    public String m_22566_() {
        return this.getClass().getSimpleName();
    }
}

