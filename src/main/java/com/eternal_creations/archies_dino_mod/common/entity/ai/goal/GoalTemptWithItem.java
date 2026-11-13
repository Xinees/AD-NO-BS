/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.PathfinderMob
 *  net.minecraft.world.entity.ai.goal.Goal
 *  net.minecraft.world.entity.ai.goal.Goal$Flag
 *  net.minecraft.world.entity.ai.targeting.TargetingConditions
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.crafting.Ingredient
 */
package com.eternal_creations.archies_dino_mod.common.entity.ai.goal;

import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.Ingredient;

public class GoalTemptWithItem
extends Goal {
    private static final TargetingConditions TEMP_TARGETING = TargetingConditions.m_148353_().m_26883_(10.0).m_148355_();
    private final TargetingConditions targetingConditions;
    protected final PathfinderMob mob;
    private final double speedModifier;
    private double px;
    private double py;
    private double pz;
    private double pRotX;
    private double pRotY;
    @Nullable
    protected Player player;
    private int calmDown;
    private boolean isRunning;
    private final Ingredient items;
    private final boolean canScare;

    public GoalTemptWithItem(PathfinderMob mob, double speedModifier, Ingredient items, boolean canScare) {
        this.mob = mob;
        this.speedModifier = speedModifier;
        this.items = items;
        this.canScare = canScare;
        this.m_7021_(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        this.targetingConditions = TEMP_TARGETING.m_148354_().m_26888_(this::shouldFollow);
    }

    public boolean m_8036_() {
        if (this.mob.m_6688_() != null || this.mob.m_5448_() != null) {
            return false;
        }
        if (this.calmDown > 0) {
            --this.calmDown;
            return false;
        }
        this.player = this.mob.m_9236_().m_45946_(this.targetingConditions, (LivingEntity)this.mob);
        return this.player != null;
    }

    private boolean shouldFollow(LivingEntity entity) {
        return this.items.test(entity.m_21205_()) || this.items.test(entity.m_21206_());
    }

    public boolean m_8045_() {
        if (this.mob.m_6688_() != null || this.mob.m_5448_() != null) {
            return false;
        }
        if (this.canScare()) {
            if (this.mob.m_20280_((Entity)this.player) < 36.0) {
                if (this.player.m_20275_(this.px, this.py, this.pz) > 0.010000000000000002) {
                    return false;
                }
                if (Math.abs((double)this.player.m_146909_() - this.pRotX) > 5.0 || Math.abs((double)this.player.m_146908_() - this.pRotY) > 5.0) {
                    return false;
                }
            } else {
                this.px = this.player.m_20185_();
                this.py = this.player.m_20186_();
                this.pz = this.player.m_20189_();
            }
            this.pRotX = this.player.m_146909_();
            this.pRotY = this.player.m_146908_();
        }
        return this.m_8036_();
    }

    protected boolean canScare() {
        return this.canScare;
    }

    public void m_8056_() {
        this.px = this.player.m_20185_();
        this.py = this.player.m_20186_();
        this.pz = this.player.m_20189_();
        this.isRunning = true;
    }

    public void m_8041_() {
        this.player = null;
        this.mob.m_21573_().m_26573_();
        this.calmDown = GoalTemptWithItem.m_186073_((int)100);
        this.isRunning = false;
    }

    public void m_8037_() {
        this.mob.m_21563_().m_24960_((Entity)this.player, (float)(this.mob.m_8085_() + 20), (float)this.mob.m_8132_());
        if (this.mob.m_20280_((Entity)this.player) < 6.25) {
            this.mob.m_21573_().m_26573_();
        } else {
            this.mob.m_21573_().m_5624_((Entity)this.player, this.speedModifier);
        }
    }

    public boolean isRunning() {
        return this.isRunning;
    }
}

