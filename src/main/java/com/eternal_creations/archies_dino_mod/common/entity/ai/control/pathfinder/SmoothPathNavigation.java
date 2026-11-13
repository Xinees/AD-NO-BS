/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMap
 *  it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Position
 *  net.minecraft.network.protocol.game.DebugPackets
 *  net.minecraft.tags.FluidTags
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.entity.ai.navigation.PathNavigation
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.PathNavigationRegion
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.material.Fluids
 *  net.minecraft.world.level.pathfinder.NodeEvaluator
 *  net.minecraft.world.level.pathfinder.Path
 *  net.minecraft.world.level.pathfinder.PathFinder
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package com.eternal_creations.archies_dino_mod.common.entity.ai.control.pathfinder;

import com.eternal_creations.archies_dino_mod.common.entity.ai.control.pathfinder.NavigationType;
import com.eternal_creations.archies_dino_mod.common.entity.ai.control.pathfinder.SmartPathFinder;
import com.eternal_creations.archies_dino_mod.common.entity.base.Flyer;
import com.eternal_creations.archies_dino_mod.common.entity.base.WallClimber;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.util.Set;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Position;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.PathNavigationRegion;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.NodeEvaluator;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SmoothPathNavigation
extends PathNavigation {
    public final Supplier<NavigationType> navigationTypeSelector;
    public final Object2ObjectMap<NavigationType, NodeEvaluator> otherNodeEvaluators;
    private final NavigationType defaultNavigationType;
    private NavigationType currentNavigationType;
    @Nullable
    private BlockPos pathToPosition;

    public SmoothPathNavigation(Mob mob, Level level, NavigationType defaultNavigationType, Set<NavigationType> otherNavigationTypes, Supplier<NavigationType> navigationTypeSelector) {
        super(mob, level);
        this.defaultNavigationType = defaultNavigationType;
        this.currentNavigationType = defaultNavigationType;
        this.navigationTypeSelector = navigationTypeSelector;
        this.otherNodeEvaluators = new Object2ObjectOpenHashMap();
        this.f_26508_ = this.defaultNavigationType.createEvaluator();
        this.f_26508_.m_77351_(true);
        for (NavigationType otherNavigationType : otherNavigationTypes) {
            this.addOtherEvaluator(otherNavigationType);
        }
        int maxVisitedNodes = Math.min(2048, Mth.m_14107_((double)(mob.m_21133_(Attributes.f_22277_) * 16.0)));
        this.f_26512_ = new SmartPathFinder(this, this.f_26508_, maxVisitedNodes);
    }

    public SmoothPathNavigation(Mob mob, Level level) {
        this(mob, level, NavigationType.GROUND, NavigationType.ALL, () -> {
            WallClimber climber;
            Flyer flyer;
            if (mob instanceof Flyer && (flyer = (Flyer)mob).isFlying()) {
                return NavigationType.FLIGHT;
            }
            if (mob instanceof WallClimber && (climber = (WallClimber)mob).isClimbing()) {
                return NavigationType.CLIMB;
            }
            return NavigationType.GROUND;
        });
    }

    public void addOtherEvaluator(NavigationType type) {
        NodeEvaluator evaluator = type.createEvaluator();
        evaluator.m_77351_(true);
        this.otherNodeEvaluators.put((Object)type, (Object)evaluator);
    }

    public void m_7638_() {
        this.currentNavigationType = this.navigationTypeSelector.get();
        if (this.currentNavigationType == NavigationType.CLIMB && this.m_26571_() && this.pathToPosition != null) {
            if (this.pathToPosition.m_203195_((Position)this.f_26494_.m_20182_(), (double)this.f_26494_.m_20205_()) || this.f_26494_.m_20186_() > (double)this.pathToPosition.m_123342_() && BlockPos.m_274561_((double)this.pathToPosition.m_123341_(), (double)this.f_26494_.m_20186_(), (double)this.pathToPosition.m_123343_()).m_203195_((Position)this.f_26494_.m_20182_(), (double)this.f_26494_.m_20205_())) {
                this.pathToPosition = null;
            } else {
                this.f_26494_.m_21566_().m_6849_((double)this.pathToPosition.m_123341_(), (double)this.pathToPosition.m_123342_(), (double)this.pathToPosition.m_123343_(), this.f_26497_);
            }
        } else {
            this.tick(this.currentNavigationType.checkForGround());
        }
    }

    public void tick(boolean checkForGround) {
        ++this.f_26498_;
        if (this.f_26506_) {
            this.m_26569_();
        }
        if (!this.m_26571_()) {
            Vec3 currentEntityPos;
            if (this.m_7632_()) {
                this.m_7636_();
            } else if (this.f_26496_ != null && !this.f_26496_.m_77392_()) {
                currentEntityPos = this.m_7475_();
                Vec3 nextEntityPos = this.f_26496_.m_77380_((Entity)this.f_26494_);
                if (Mth.m_14107_((double)currentEntityPos.f_82479_) == Mth.m_14107_((double)nextEntityPos.f_82479_) && Mth.m_14107_((double)currentEntityPos.f_82481_) == Mth.m_14107_((double)nextEntityPos.f_82481_)) {
                    if (checkForGround) {
                        if (currentEntityPos.f_82480_ > nextEntityPos.f_82480_ && !this.f_26494_.m_20096_()) {
                            this.f_26496_.m_77374_();
                        }
                    } else if (Mth.m_14107_((double)currentEntityPos.f_82480_) == Mth.m_14107_((double)nextEntityPos.f_82480_)) {
                        this.f_26496_.m_77374_();
                    }
                }
            }
            DebugPackets.m_133703_((Level)this.f_26495_, (Mob)this.f_26494_, (Path)this.f_26496_, (float)this.f_26505_);
            if (!this.m_26571_() && this.f_26496_ != null) {
                currentEntityPos = this.f_26496_.m_77380_((Entity)this.f_26494_);
                this.f_26494_.m_21566_().m_6849_(currentEntityPos.f_82479_, checkForGround ? this.m_183345_(currentEntityPos) : currentEntityPos.f_82480_, currentEntityPos.f_82481_, this.f_26497_);
            }
        }
    }

    public boolean m_5624_(@NotNull Entity entity, double speed) {
        if (this.currentNavigationType == NavigationType.CLIMB) {
            Path path = this.m_6570_(entity, 0);
            if (path != null) {
                return this.m_26536_(path, speed);
            }
            this.pathToPosition = entity.m_20183_();
            this.f_26497_ = speed;
            return true;
        }
        return super.m_5624_(entity, speed);
    }

    public boolean m_26519_(double x, double y, double z, double speed) {
        if (this.currentNavigationType == NavigationType.CLIMB) {
            BlockPos target = BlockPos.m_274561_((double)x, (double)y, (double)z);
            Path path = this.m_7864_(target, 0);
            if (path != null) {
                return this.m_26536_(path, speed);
            }
            this.pathToPosition = target;
            this.f_26497_ = speed;
            return true;
        }
        return super.m_26519_(x, y, z, speed);
    }

    public void m_26573_() {
        super.m_26573_();
        this.pathToPosition = null;
    }

    protected boolean m_7632_() {
        return this.currentNavigationType.canUpdatePath(this.f_26494_, this);
    }

    @NotNull
    protected Vec3 m_7475_() {
        if (!this.currentNavigationType.checkForGround()) {
            return this.f_26494_.m_20182_();
        }
        return new Vec3(this.f_26494_.m_20185_(), (double)this.getSurfaceY(), this.f_26494_.m_20189_());
    }

    private int getSurfaceY() {
        if (this.f_26494_.m_20069_() && this.m_26576_() || this.f_26494_.m_20077_() && this.f_26494_.m_203441_(Fluids.f_76195_.m_76145_())) {
            int i = this.f_26494_.m_146904_();
            BlockState blockstate = this.f_26495_.m_8055_(BlockPos.m_274561_((double)this.f_26494_.m_20185_(), (double)i, (double)this.f_26494_.m_20189_()));
            int j = 0;
            while (blockstate.m_60713_(Blocks.f_49990_) || blockstate.m_60713_(Blocks.f_49991_)) {
                blockstate = this.f_26495_.m_8055_(BlockPos.m_274561_((double)this.f_26494_.m_20185_(), (double)(++i), (double)this.f_26494_.m_20189_()));
                if (++j <= 16) continue;
                return this.f_26494_.m_146904_();
            }
            return i;
        }
        return Mth.m_14107_((double)(this.f_26494_.m_20186_() + 0.5));
    }

    protected void m_7636_() {
        double verticalTolerance;
        Vec3 nodePos;
        int nextNodeIndex;
        if (this.f_26496_ == null) {
            return;
        }
        Vec3 mobPos = this.m_7475_();
        double width = this.f_26494_.m_20205_();
        this.f_26505_ = width > 0.75 ? (float)(width * 0.5) : 0.5f;
        int furthestVisibleNode = nextNodeIndex = this.f_26496_.m_77399_();
        int lookAheadLimit = Math.min(nextNodeIndex + 4, this.f_26496_.m_77398_());
        int i = nextNodeIndex;
        while (i < lookAheadLimit && this.m_183431_(mobPos, nodePos = this.f_26496_.m_77382_((Entity)this.f_26494_, i))) {
            furthestVisibleNode = i++;
        }
        if (furthestVisibleNode > nextNodeIndex) {
            while (this.f_26496_.m_77399_() < furthestVisibleNode) {
                this.f_26496_.m_77374_();
            }
        }
        BlockPos targetNode = this.f_26496_.m_77400_();
        double dx = Math.abs(this.f_26494_.m_20185_() - ((double)targetNode.m_123341_() + 0.5));
        double dy = Math.abs(this.f_26494_.m_20186_() - (double)targetNode.m_123342_());
        double dz = Math.abs(this.f_26494_.m_20189_() - ((double)targetNode.m_123343_() + 0.5));
        double d = verticalTolerance = this.f_26494_.m_20206_() > 2.0f ? (double)this.f_26494_.m_20206_() * 0.5 : 1.0;
        if (dx < (double)this.f_26505_ && dz < (double)this.f_26505_ && dy < verticalTolerance) {
            this.f_26496_.m_77374_();
        }
        this.m_6481_(mobPos);
    }

    protected boolean m_183431_(@NotNull Vec3 fromPos, @NotNull Vec3 toPos) {
        return this.currentNavigationType.canMoveDirectly(this.f_26494_, this, fromPos, toPos);
    }

    @Nullable
    public Path m_7864_(BlockPos pos, int accuracy) {
        if (this.currentNavigationType == NavigationType.CLIMB) {
            this.pathToPosition = pos;
        }
        return super.m_7864_(pos, accuracy);
    }

    @Nullable
    public Path m_148222_(@NotNull Set<BlockPos> targets, int regionOffset, boolean offsetUpward, int accuracy, float followRange) {
        return this.currentNavigationType.createPath(this.f_26494_, this, targets, regionOffset, offsetUpward, accuracy, followRange);
    }

    public Path createPathDefault(@NotNull Set<BlockPos> targets, int regionOffset, boolean offsetUpward, int accuracy, float followRange) {
        Level level = this.f_26494_.m_9236_();
        Path path = this.m_26570_();
        BlockPos targetPos = this.f_26509_;
        if (targets.isEmpty()) {
            return null;
        }
        if (this.f_26494_.m_20186_() < (double)level.m_141937_()) {
            return null;
        }
        if (!this.m_7632_()) {
            return null;
        }
        if (path != null && !path.m_77392_() && targets.contains(targetPos)) {
            return path;
        }
        level.m_46473_().m_6180_("pathfind");
        BlockPos blockPos = offsetUpward ? this.f_26494_.m_20183_().m_7494_() : this.f_26494_.m_20183_();
        int i = (int)(followRange + (float)regionOffset);
        PathNavigationRegion pathnavigationregion = new PathNavigationRegion(level, blockPos.m_7918_(-i, -i, -i), blockPos.m_7918_(i, i, i));
        path = this.f_26512_.m_77427_(pathnavigationregion, this.f_26494_, targets, followRange, accuracy, this.f_26511_);
        level.m_46473_().m_7238_();
        if (path != null) {
            this.f_26509_ = path.m_77406_();
            this.f_26510_ = accuracy;
            this.m_26565_();
        }
        return path;
    }

    public boolean m_6342_(@NotNull BlockPos pos) {
        if (!this.currentNavigationType.checkForGround()) {
            return this.f_26495_.m_8055_(pos).m_60795_() || super.m_6342_(pos);
        }
        BlockPos blockpos = pos.m_7495_();
        BlockState state = this.f_26495_.m_8055_(blockpos);
        if (state.m_60819_().m_205070_(FluidTags.f_13132_) && this.f_26494_.m_203441_(state.m_60819_())) {
            return true;
        }
        return state.m_60804_((BlockGetter)this.f_26495_, blockpos);
    }

    public void m_7008_(boolean canSwim) {
        this.f_26508_.m_77358_(canSwim);
        this.otherNodeEvaluators.values().forEach(evaluator -> evaluator.m_77358_(canSwim));
    }

    protected PathFinder m_5532_(int maxVisitedNodes) {
        return null;
    }

    public boolean m_26574_() {
        return super.m_26574_();
    }

    public NavigationType getDefaultNavigationType() {
        return this.defaultNavigationType;
    }

    public NodeEvaluator getCurrentEvaluator() {
        return this.currentNavigationType == this.defaultNavigationType ? this.f_26508_ : (NodeEvaluator)this.otherNodeEvaluators.get((Object)this.currentNavigationType);
    }
}

