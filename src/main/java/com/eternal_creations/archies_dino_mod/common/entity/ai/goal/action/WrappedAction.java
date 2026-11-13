/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.astemir.astemirlib.re.common.action.ActionState
 *  com.astemir.astemirlib.re.common.action.SyncedActionHolder
 *  com.astemir.astemirlib.re.core.function.Condition
 *  org.jetbrains.annotations.Nullable
 */
package com.eternal_creations.archies_dino_mod.common.entity.ai.goal.action;

import com.astemir.astemirlib.re.common.action.ActionState;
import com.astemir.astemirlib.re.common.action.SyncedActionHolder;
import com.astemir.astemirlib.re.core.function.Condition;
import java.util.function.DoubleSupplier;
import java.util.function.Supplier;
import org.jetbrains.annotations.Nullable;

public class WrappedAction {
    @Nullable
    private Supplier<ActionState> action;
    @Nullable
    private Runnable runnable;
    private Condition condition = () -> true;
    private DoubleSupplier minRange = () -> 0.0;
    private DoubleSupplier maxRange = () -> Double.MAX_VALUE;
    private float weight = 1.0f;
    private int cooldown;
    private int tickCount;

    WrappedAction(@Nullable Supplier<ActionState> action) {
        this.action = action;
    }

    public WrappedAction(@Nullable Runnable runnable) {
        this.runnable = runnable;
    }

    public void tick() {
        if (this.tickCount > 0) {
            --this.tickCount;
        }
    }

    public void run(SyncedActionHolder holder) {
        this.tickCount = this.cooldown;
        if (this.runnable != null) {
            this.runnable.run();
        }
        if (this.action != null) {
            holder.playActionSynced(this.action.get());
        }
    }

    public boolean test(double distanceSqr) {
        if (this.tickCount > 0) {
            return false;
        }
        double linearDistance = Math.sqrt(distanceSqr);
        return this.condition.check() && linearDistance >= this.minRange.getAsDouble() && linearDistance <= this.maxRange.getAsDouble();
    }

    public WrappedAction cooldown(int cooldown) {
        this.cooldown = cooldown;
        return this;
    }

    public WrappedAction weight(float weight) {
        this.weight = weight;
        return this;
    }

    public WrappedAction minRange(double minRange) {
        this.minRange = () -> minRange;
        return this;
    }

    public WrappedAction minRange(DoubleSupplier minRange) {
        this.minRange = minRange;
        return this;
    }

    public WrappedAction maxRange(double maxRange) {
        this.maxRange = () -> maxRange;
        return this;
    }

    public WrappedAction maxRange(DoubleSupplier maxRange) {
        this.maxRange = maxRange;
        return this;
    }

    public WrappedAction condition(Condition predicate) {
        this.condition = predicate;
        return this;
    }

    public float weight() {
        return this.weight;
    }

    public double maxRange() {
        return this.maxRange.getAsDouble();
    }

    public double minRange() {
        return this.minRange.getAsDouble();
    }

    public static WrappedAction of(Supplier<ActionState> action) {
        return new WrappedAction(action);
    }

    public static WrappedAction of(Supplier<ActionState> action, float weight) {
        return new WrappedAction(action).weight(weight);
    }

    public static WrappedAction of(Runnable runnable) {
        return new WrappedAction(runnable);
    }

    public static WrappedAction of(Runnable runnable, float weight) {
        return new WrappedAction(runnable).weight(weight);
    }
}

