/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.astemir.astemirlib.re.common.action.ActionState
 *  net.minecraft.core.BlockPos
 *  net.minecraft.tags.FluidTags
 *  net.minecraft.world.entity.ai.goal.Goal
 *  net.minecraft.world.entity.ai.goal.Goal$Flag
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.pathfinder.Path
 *  net.minecraft.world.phys.Vec3
 */
package com.eternal_creations.archies_dino_mod.common.entity.mob.obsidiceratops.ai;

import com.astemir.astemirlib.re.common.action.ActionState;
import com.eternal_creations.archies_dino_mod.common.entity.mob.obsidiceratops.ObsidiceratopsEntity;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumSet;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

public class ObsidiceratopsGoalDrinkLava
extends Goal {
    private final ObsidiceratopsEntity mob;
    private final Level level;
    private final double speedModifier;
    private BlockPos targetLavaPos;
    private Phase phase;
    private int timer;
    private int checkTime;

    public ObsidiceratopsGoalDrinkLava(ObsidiceratopsEntity mob, double speedModifier) {
        this.mob = mob;
        this.level = mob.m_9236_();
        this.speedModifier = speedModifier;
        this.phase = Phase.FINISHED;
        this.m_7021_(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    public boolean m_8036_() {
        if (this.mob.m_6688_() != null || this.mob.m_5448_() != null || !this.mob.isThirstyForLava() || !this.mob.isNoActionActive()) {
            return false;
        }
        if (this.mob.m_20077_() || this.level.m_6425_(this.mob.m_20183_().m_7495_()).m_205070_(FluidTags.f_13132_)) {
            Vec3 lookVec = this.mob.m_20154_();
            this.targetLavaPos = BlockPos.m_274561_((double)(this.mob.m_20185_() + lookVec.f_82479_ * 1.5), (double)this.mob.m_20186_(), (double)(this.mob.m_20189_() + lookVec.f_82481_ * 1.5));
            return true;
        }
        if (++this.checkTime >= 60) {
            this.checkTime = 0;
            return this.findTargetLava();
        }
        return false;
    }

    public boolean m_8045_() {
        return this.phase != Phase.FINISHED && this.targetLavaPos != null && this.mob.isThirstyForLava() && this.mob.m_5448_() == null;
    }

    public void m_8056_() {
        this.timer = 0;
        if (this.mob.m_20077_() || this.mob.m_20275_((double)this.targetLavaPos.m_123341_() + 0.5, this.mob.m_20186_(), (double)this.targetLavaPos.m_123343_() + 0.5) <= 4.0) {
            this.mob.m_21573_().m_26573_();
            this.phase = Phase.TURNING;
        } else {
            this.phase = Phase.APPROACHING;
            this.mob.m_21573_().m_26519_((double)this.targetLavaPos.m_123341_() + 0.5, (double)this.targetLavaPos.m_123342_(), (double)this.targetLavaPos.m_123343_() + 0.5, this.speedModifier);
        }
    }

    public void m_8037_() {
        if (this.targetLavaPos == null) {
            this.phase = Phase.FINISHED;
            return;
        }
        double lavaX = (double)this.targetLavaPos.m_123341_() + 0.5;
        double lavaY = (double)this.targetLavaPos.m_123342_() + 0.5;
        double lavaZ = (double)this.targetLavaPos.m_123343_() + 0.5;
        switch (this.phase) {
            case APPROACHING: {
                double distSq = this.mob.m_20275_(lavaX, this.mob.m_20186_(), lavaZ);
                boolean inLava = this.mob.m_20077_();
                if (distSq <= 4.0 || inLava) {
                    this.mob.m_21573_().m_26573_();
                    this.phase = Phase.TURNING;
                    this.timer = 0;
                    break;
                }
                if (this.mob.m_21573_().m_26571_()) {
                    ++this.timer;
                    if (this.timer > 20) {
                        this.phase = Phase.FINISHED;
                        break;
                    }
                    this.mob.m_21573_().m_26519_(lavaX, lavaY, lavaZ, this.speedModifier);
                    break;
                }
                this.timer = 0;
                break;
            }
            case TURNING: {
                this.mob.m_21563_().m_24950_(lavaX, lavaY, lavaZ, 30.0f, 30.0f);
                ++this.timer;
                if (this.timer < 15) break;
                ActionState drinkAction = this.mob.m_217043_().m_188499_() ? this.mob.actionDrink1 : this.mob.actionDrink2;
                this.mob.playActionSynced(drinkAction);
                this.phase = Phase.DRINKING;
                this.timer = 0;
                break;
            }
            case DRINKING: {
                this.mob.m_21563_().m_24950_(lavaX, lavaY, lavaZ, 30.0f, 30.0f);
                ++this.timer;
                if (this.timer < 70) break;
                this.mob.drinkLava();
                this.phase = Phase.FINISHED;
                break;
            }
        }
    }

    public void m_8041_() {
        this.targetLavaPos = null;
        this.phase = Phase.FINISHED;
        this.timer = 0;
    }

    private boolean findTargetLava() {
        BlockPos mobPos = this.mob.m_20183_();
        int radiusXZ = 16;
        int radiusY = 4;
        ArrayList<BlockPos> candidateLava = new ArrayList<BlockPos>();
        for (BlockPos pos : BlockPos.m_121940_((BlockPos)mobPos.m_7918_(-radiusXZ, -radiusY, -radiusXZ), (BlockPos)mobPos.m_7918_(radiusXZ, radiusY, radiusXZ))) {
            if (!this.level.m_6425_(pos).m_205070_(FluidTags.f_13132_) || this.level.m_8055_(pos.m_7494_()).m_280555_()) continue;
            candidateLava.add(pos.m_7949_());
        }
        if (candidateLava.isEmpty()) {
            return false;
        }
        candidateLava.sort(Comparator.comparingDouble(arg_0 -> ((BlockPos)mobPos).m_123331_(arg_0)));
        for (BlockPos lava : candidateLava) {
            Path path = this.mob.m_21573_().m_7864_(lava, 0);
            if (path == null || !path.m_77403_()) continue;
            this.targetLavaPos = lava;
            return true;
        }
        return false;
    }

    private static enum Phase {
        APPROACHING,
        TURNING,
        DRINKING,
        FINISHED;

    }
}

