/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.astemir.astemirlib.re.common.action.SyncedActionHolder
 *  com.astemir.astemirlib.re.core.function.Condition
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.ai.goal.Goal$Flag
 */
package com.eternal_creations.archies_dino_mod.common.entity.ai.goal.action;

import com.astemir.astemirlib.re.common.action.SyncedActionHolder;
import com.astemir.astemirlib.re.core.function.Condition;
import com.eternal_creations.archies_dino_mod.common.entity.ai.goal.action.GoalActionSelector;
import com.eternal_creations.archies_dino_mod.common.entity.ai.goal.action.WrappedAction;
import java.util.EnumSet;
import java.util.List;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class GoalRandomAction<T extends Mob>
extends GoalActionSelector {
    private final T mob;
    private final int cooldownInterval;
    private final Condition condition;
    private final float chance;
    private long lastActionTime;

    private GoalRandomAction(T mob, Builder builder) {
        super(builder.actions);
        this.mob = mob;
        this.chance = builder.chance;
        this.cooldownInterval = builder.cooldownInterval;
        this.condition = builder.condition;
        this.lastActionTime = mob.m_9236_().m_46467_();
        if (builder.stopMovement) {
            this.m_7021_(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }
    }

    public boolean m_8036_() {
        boolean success;
        long time = this.mob.m_9236_().m_46467_();
        if (this.lastActionTime >= 0L && time - this.lastActionTime < (long)this.cooldownInterval) {
            return false;
        }
        if (!this.condition.check()) {
            return false;
        }
        if (!this.isAnyActionPossible(0.0)) {
            return false;
        }
        boolean bl = success = this.mob.m_217043_().m_188501_() <= this.chance;
        if (!success) {
            this.lastActionTime = time;
        }
        return success;
    }

    public void m_8056_() {
        this.lastActionTime = this.mob.m_9236_().m_46467_();
        WrappedAction selectedAction = this.getWeightedAction((Mob)this.mob, 0.0);
        if (selectedAction != null) {
            selectedAction.run((SyncedActionHolder)this.mob);
        }
    }

    public boolean m_8045_() {
        return false;
    }

    public static Builder of(WrappedAction ... actions) {
        return new Builder(actions);
    }

    public static class Builder {
        private final List<WrappedAction> actions;
        private int cooldownInterval = 100;
        private Condition condition = Condition.TRUE;
        private boolean stopMovement = false;
        private float chance = 1.0f;

        public Builder(WrappedAction ... actions) {
            this.actions = List.of(actions);
        }

        public Builder cooldown(int ticks) {
            this.cooldownInterval = ticks;
            return this;
        }

        public Builder condition(Condition condition) {
            this.condition = condition;
            return this;
        }

        public Builder chance(float chance) {
            this.chance = chance;
            return this;
        }

        public Builder stopMovement(boolean stopMovement) {
            this.stopMovement = stopMovement;
            return this;
        }

        public <T extends Mob> GoalRandomAction<T> build(T mob) {
            return new GoalRandomAction<T>(mob, this);
        }
    }
}

