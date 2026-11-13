/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.ai.goal.Goal
 *  net.minecraft.world.entity.ai.goal.Goal$Flag
 *  net.minecraft.world.entity.ai.targeting.TargetingConditions
 *  net.minecraft.world.entity.player.Player
 */
package com.eternal_creations.archies_dino_mod.common.entity.mob.archie.goals;

import com.eternal_creations.archies_dino_mod.common.entity.mob.archie.ArchieEntity;
import com.eternal_creations.archies_dino_mod.common.menu.ArchieMenu;
import java.util.EnumSet;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ListenToPlayerGoal
extends Goal {
    private static final TargetingConditions TEMP_TARGETING = TargetingConditions.m_148353_().m_26883_(10.0).m_148355_();
    private final TargetingConditions targetingConditions;
    protected final ArchieEntity archie;
    private final double speedModifier;
    @Nullable
    protected Player player;

    public ListenToPlayerGoal(ArchieEntity archie, double speedModifier) {
        this.archie = archie;
        this.speedModifier = speedModifier;
        this.m_7021_(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        this.targetingConditions = TEMP_TARGETING.m_148354_().m_26888_(this::shouldFollow);
    }

    public boolean m_8036_() {
        this.player = this.archie.m_9236_().m_45946_(this.targetingConditions, (LivingEntity)this.archie);
        return this.player != null;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean shouldFollow(LivingEntity entity) {
        if (!(entity instanceof Player)) return false;
        Player player1 = (Player)entity;
        if (!(player1.f_36096_ instanceof ArchieMenu)) return false;
        return true;
    }

    public boolean m_8045_() {
        if (this.archie.denyTick > 0) {
            return false;
        }
        if (this.player == null) {
            return false;
        }
        if (!this.shouldFollow((LivingEntity)this.player)) {
            return false;
        }
        return this.m_8036_();
    }

    public void m_8056_() {
    }

    public void m_8041_() {
        this.player = null;
        this.archie.m_21573_().m_26573_();
    }

    public void m_8037_() {
        if (this.player != null) {
            this.archie.m_21563_().m_24960_((Entity)this.player, (float)(this.archie.m_8085_() + 20), (float)this.archie.m_8132_());
            if (this.archie.m_20280_((Entity)this.player) < 6.25) {
                this.archie.m_21573_().m_26573_();
            } else {
                this.archie.m_21573_().m_5624_((Entity)this.player, this.speedModifier);
            }
        }
    }
}

