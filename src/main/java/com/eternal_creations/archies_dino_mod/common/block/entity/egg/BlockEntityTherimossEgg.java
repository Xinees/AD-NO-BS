/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.astemir.astemirlib.re.common.action.ActionState
 *  com.astemir.astemirlib.re.common.action.ActionTarget
 *  com.astemir.astemirlib.re.common.action.ActionType
 *  com.astemir.astemirlib.re.common.action.SyncedActionHolder
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMap
 *  it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockState
 */
package com.eternal_creations.archies_dino_mod.common.block.entity.egg;

import com.astemir.astemirlib.re.common.action.ActionState;
import com.astemir.astemirlib.re.common.action.ActionTarget;
import com.astemir.astemirlib.re.common.action.ActionType;
import com.astemir.astemirlib.re.common.action.SyncedActionHolder;
import com.eternal_creations.archies_dino_mod.common.block.entity.ADBlockEntityTypes;
import com.eternal_creations.archies_dino_mod.common.block.entity.TickingBlockEntity;
import com.eternal_creations.archies_dino_mod.common.entity.ADActionTypes;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class BlockEntityTherimossEgg
extends BlockEntity
implements TickingBlockEntity,
SyncedActionHolder {
    private final Object2ObjectMap<ActionType, ActionState> actions = new Object2ObjectOpenHashMap();
    public ActionState actionHatch = ActionState.of((ActionType)ADActionTypes.HATCH, (float)0.5f, (float)0.1f, (float)0.1f);

    public BlockEntityTherimossEgg(BlockPos pos, BlockState blockState) {
        super(ADBlockEntityTypes.THERIMOSS_EGG.get(), pos, blockState);
        this.actions.put((Object)ADActionTypes.HATCH, (Object)this.actionHatch);
    }

    public void setupActionStates() {
    }

    @Override
    public void tick(Level level, BlockPos blockPos, BlockState blockState) {
        this.tickActionStates();
    }

    public ActionTarget getActionTarget() {
        return ActionTarget.BLOCK_ENTITY;
    }

    public Object2ObjectMap<ActionType, ActionState> getActionStates() {
        return this.actions;
    }
}

