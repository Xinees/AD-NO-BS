/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.level.EntityGetter
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockState
 */
package com.eternal_creations.archies_dino_mod.common.block.entity.egg;

import com.eternal_creations.archies_dino_mod.common.block.entity.ADBlockEntityTypes;
import com.eternal_creations.archies_dino_mod.common.block.entity.OwnableBlockEntity;
import java.util.UUID;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.EntityGetter;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BlockEntitySculkraptorEgg
extends BlockEntity
implements OwnableBlockEntity {
    @Nullable
    protected UUID owner = null;

    public BlockEntitySculkraptorEgg(BlockPos pos, BlockState blockState) {
        super(ADBlockEntityTypes.SCULKRAPTOR_EGG.get(), pos, blockState);
    }

    @Override
    @Nullable
    public UUID getOwnerUUID() {
        return this.owner;
    }

    @Override
    public void setOwnerUUID(@Nullable UUID owner) {
        this.owner = owner;
    }

    protected void m_183515_(CompoundTag tag) {
        if (this.owner != null) {
            tag.m_128362_("owner", this.owner);
        }
    }

    public void m_142466_(CompoundTag tag) {
        if (tag.m_128441_("owner")) {
            this.owner = tag.m_128342_("owner");
        }
    }

    @Override
    @Nullable
    public EntityGetter level() {
        return this.f_58857_;
    }
}

