/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Sets
 *  javax.annotation.Nullable
 *  net.minecraft.core.BlockPos
 *  net.minecraft.util.profiling.ProfilerFiller
 *  net.minecraft.util.profiling.metrics.MetricCategory
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.level.PathNavigationRegion
 *  net.minecraft.world.level.pathfinder.BinaryHeap
 *  net.minecraft.world.level.pathfinder.Node
 *  net.minecraft.world.level.pathfinder.NodeEvaluator
 *  net.minecraft.world.level.pathfinder.Path
 *  net.minecraft.world.level.pathfinder.PathFinder
 *  net.minecraft.world.level.pathfinder.Target
 *  org.jetbrains.annotations.NotNull
 */
package com.eternal_creations.archies_dino_mod.common.entity.ai.control.pathfinder;

import com.eternal_creations.archies_dino_mod.common.entity.ai.control.pathfinder.SmoothPathNavigation;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.util.profiling.metrics.MetricCategory;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.PathNavigationRegion;
import net.minecraft.world.level.pathfinder.BinaryHeap;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.NodeEvaluator;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.level.pathfinder.Target;
import org.jetbrains.annotations.NotNull;

public class SmartPathFinder
extends PathFinder {
    protected final SmoothPathNavigation navigation;
    private final Node[] neighbors = new Node[32];
    private final int maxVisitedNodes;
    private final BinaryHeap openSet = new BinaryHeap();

    public SmartPathFinder(SmoothPathNavigation navigation, NodeEvaluator defaultNodeEvaluator, int maxVisitedNodes) {
        super(defaultNodeEvaluator, maxVisitedNodes);
        this.navigation = navigation;
        this.maxVisitedNodes = maxVisitedNodes;
    }

    @Nullable
    public Path m_77427_(@NotNull PathNavigationRegion region, @NotNull Mob mob, @NotNull Set<BlockPos> targetPositions, float maxRange, int accuracy, float searchDepthMultiplier) {
        this.openSet.m_77081_();
        NodeEvaluator activeEvaluator = this.navigation.getCurrentEvaluator();
        activeEvaluator.m_6028_(region, mob);
        Node node = activeEvaluator.m_7171_();
        Map<Target, BlockPos> map = targetPositions.stream().collect(Collectors.toMap(pos -> activeEvaluator.m_7568_((double)pos.m_123341_(), (double)pos.m_123342_(), (double)pos.m_123343_()), Function.identity()));
        Path path = this.findPath(region.m_151625_(), node, map, maxRange, accuracy, searchDepthMultiplier, activeEvaluator);
        activeEvaluator.m_6802_();
        return path;
    }

    @Nullable
    private Path findPath(ProfilerFiller profiler, Node p_node, Map<Target, BlockPos> targetPos, float maxRange, int accuracy, float searchDepthMultiplier, NodeEvaluator evaluator) {
        profiler.m_6180_("find_path");
        profiler.m_142259_(MetricCategory.PATH_FINDING);
        Set<Target> set = targetPos.keySet();
        p_node.f_77275_ = 0.0f;
        p_node.f_77277_ = p_node.f_77276_ = this.m_77444_(p_node, set);
        this.openSet.m_77081_();
        this.openSet.m_77084_(p_node);
        int i = 0;
        HashSet set2 = Sets.newHashSetWithExpectedSize((int)set.size());
        int j = (int)((float)this.maxVisitedNodes * searchDepthMultiplier);
        while (!this.openSet.m_77092_() && ++i < j) {
            Node node = this.openSet.m_77091_();
            node.f_77279_ = true;
            for (Target target : set) {
                if (!(node.m_77304_((Node)target) <= (float)accuracy)) continue;
                target.m_77509_();
                set2.add(target);
            }
            if (!set2.isEmpty()) break;
            if (node.m_77293_(p_node) >= maxRange) continue;
            int k = evaluator.m_6065_(this.neighbors, node);
            for (int l = 0; l < k; ++l) {
                Node node1 = this.neighbors[l];
                float f = this.m_214208_(node, node1);
                node1.f_77280_ = node.f_77280_ + f;
                float f1 = node.f_77275_ + f + node1.f_77281_;
                if (!(node1.f_77280_ < maxRange) || node1.m_77303_() && !(f1 < node1.f_77275_)) continue;
                node1.f_77278_ = node;
                node1.f_77275_ = f1;
                node1.f_77276_ = this.m_77444_(node1, set) * 1.5f;
                if (node1.m_77303_()) {
                    this.openSet.m_77086_(node1, node1.f_77275_ + node1.f_77276_);
                    continue;
                }
                node1.f_77277_ = node1.f_77275_ + node1.f_77276_;
                this.openSet.m_77084_(node1);
            }
        }
        Optional<Path> optional = !set2.isEmpty() ? set2.stream().map(p_77454_ -> this.m_77434_(p_77454_.m_77508_(), (BlockPos)targetPos.get(p_77454_), true)).min(Comparator.comparingInt(Path::m_77398_)) : set.stream().map(p_77451_ -> this.m_77434_(p_77451_.m_77508_(), (BlockPos)targetPos.get(p_77451_), false)).min(Comparator.comparingDouble(Path::m_77407_).thenComparingInt(Path::m_77398_));
        profiler.m_7238_();
        return optional.orElse(null);
    }

    protected float m_214208_(Node first, @NotNull Node second) {
        return first.m_77293_(second);
    }

    private float m_77444_(Node node, Set<Target> targets) {
        float f = Float.MAX_VALUE;
        for (Target target : targets) {
            float f1 = node.m_77293_((Node)target);
            target.m_77503_(f1, node);
            f = Math.min(f1, f);
        }
        return f;
    }

    private Path m_77434_(Node point, BlockPos targetPos, boolean reachesTarget) {
        ArrayList list = Lists.newArrayList();
        Node node = point;
        list.add(0, point);
        while (node.f_77278_ != null) {
            node = node.f_77278_;
            list.add(0, node);
        }
        return new Path((List)list, targetPos, reachesTarget);
    }
}

