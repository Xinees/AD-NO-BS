/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.astemir.astemirlib.re.common.action.ActionState
 *  com.astemir.astemirlib.re.common.action.SyncedActionHolder
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.ai.behavior.Behavior$Status
 *  net.minecraft.world.entity.ai.behavior.BehaviorControl
 *  net.minecraft.world.entity.ai.memory.MemoryModuleType
 *  net.minecraft.world.entity.ai.memory.MemoryStatus
 */
package com.eternal_creations.archies_dino_mod.common.entity.ai.behaviour;

import com.astemir.astemirlib.re.common.action.ActionState;
import com.astemir.astemirlib.re.common.action.SyncedActionHolder;
import java.util.Map;
import java.util.function.Function;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public abstract class ActionBehaviour<E extends LivingEntity>
implements BehaviorControl<E> {
    protected final Map<MemoryModuleType<?>, MemoryStatus> entryCondition;
    protected final Function<E, ActionState> associatedAction;
    private Behavior.Status status = Behavior.Status.STOPPED;

    public ActionBehaviour(Map<MemoryModuleType<?>, MemoryStatus> entryCondition, Function<E, ActionState> associatedAction) {
        this.entryCondition = entryCondition;
        this.associatedAction = associatedAction;
    }

    public Behavior.Status m_22536_() {
        return this.status;
    }

    public ActionState getAssociatedAction(E owner) {
        return this.associatedAction.apply(owner);
    }

    public final boolean m_22554_(ServerLevel level, E owner, long gameTime) {
        if (this.hasRequiredMemories(owner) && this.checkExtraStartConditions(level, owner)) {
            this.status = Behavior.Status.RUNNING;
            ActionState action = this.getAssociatedAction(owner);
            ((SyncedActionHolder)owner).playActionSynced(action);
            if (action.isActive()) {
                this.start(level, owner, gameTime);
                return true;
            }
        }
        return false;
    }

    protected void start(ServerLevel level, E owner, long gameTime) {
    }

    public final void m_22558_(ServerLevel level, E owner, long gameTime) {
        if (!this.timedOut(owner) && this.canStillUse(level, owner, gameTime)) {
            this.tick(level, owner, gameTime);
        } else {
            this.m_22562_(level, owner, gameTime);
        }
    }

    protected void tick(ServerLevel level, E owner, long gameTime) {
    }

    public final void m_22562_(ServerLevel level, E owner, long gameTime) {
        this.status = Behavior.Status.STOPPED;
        this.stop(level, owner, gameTime);
    }

    protected void stop(ServerLevel level, E owner, long gameTime) {
    }

    protected boolean canStillUse(ServerLevel level, E owner, long gameTime) {
        return false;
    }

    protected boolean timedOut(E owner) {
        return !this.getAssociatedAction(owner).isActive();
    }

    protected boolean checkExtraStartConditions(ServerLevel level, E owner) {
        return ((SyncedActionHolder)owner).isNoActionActive();
    }

    public String m_22566_() {
        return this.getClass().getSimpleName();
    }

    protected boolean hasRequiredMemories(E owner) {
        for (Map.Entry<MemoryModuleType<?>, MemoryStatus> entry : this.entryCondition.entrySet()) {
            MemoryModuleType<?> memorymoduletype = entry.getKey();
            MemoryStatus memorystatus = entry.getValue();
            if (owner.m_6274_().m_21876_(memorymoduletype, memorystatus)) continue;
            return false;
        }
        return true;
    }
}

