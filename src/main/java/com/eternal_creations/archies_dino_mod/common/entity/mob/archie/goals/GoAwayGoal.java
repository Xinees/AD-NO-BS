/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.PathfinderMob
 *  net.minecraft.world.entity.ai.goal.Goal
 *  net.minecraft.world.entity.ai.goal.Goal$Flag
 *  net.minecraft.world.entity.ai.navigation.PathNavigation
 *  net.minecraft.world.entity.ai.util.DefaultRandomPos
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.pathfinder.Path
 *  net.minecraft.world.phys.Vec3
 */
package com.eternal_creations.archies_dino_mod.common.entity.mob.archie.goals;

import com.eternal_creations.archies_dino_mod.common.entity.mob.archie.ArchieEntity;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class GoAwayGoal
extends Goal {
    protected final ArchieEntity archie;
    private final double speedModifier;
    @Nullable
    protected Path path;
    protected final PathNavigation pathNav;

    public GoAwayGoal(ArchieEntity archie, double speedModifier) {
        this.archie = archie;
        this.speedModifier = speedModifier;
        this.pathNav = archie.m_21573_();
        this.m_7021_(EnumSet.of(Goal.Flag.MOVE));
    }

    public boolean m_8036_() {
        if (this.archie.denyTick <= 0) {
            return false;
        }
        Player nearest = this.archie.m_9236_().m_45924_(this.archie.m_20185_() + 0.5, this.archie.m_20186_() + 0.5, this.archie.m_20189_() + 0.5, 64.0, true);
        if (nearest == null) {
            List entities = this.archie.m_9236_().m_6249_((Entity)this.archie, this.archie.m_20191_().m_82400_(32.0), entity -> !entity.m_5833_());
            if (entities.isEmpty()) {
                return false;
            }
            entities.sort(Comparator.comparingDouble(entity -> entity.m_20270_((Entity)this.archie)));
            nearest = (Entity)entities.get(0);
        }
        if (nearest == null) {
            return false;
        }
        Vec3 vec3 = DefaultRandomPos.m_148407_((PathfinderMob)this.archie, (int)16, (int)7, (Vec3)nearest.m_20182_());
        if (vec3 == null) {
            return false;
        }
        if (nearest.m_20275_(vec3.f_82479_, vec3.f_82480_, vec3.f_82481_) < nearest.m_20280_((Entity)this.archie)) {
            return false;
        }
        this.path = this.pathNav.m_26524_(vec3.f_82479_, vec3.f_82480_, vec3.f_82481_, 0);
        return this.path != null;
    }

    public boolean m_8045_() {
        return !this.pathNav.m_26571_();
    }

    public void m_8056_() {
        this.pathNav.m_26536_(this.path, this.speedModifier);
    }
}

