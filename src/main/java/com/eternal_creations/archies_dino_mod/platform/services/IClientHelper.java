/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider
 *  net.minecraft.client.renderer.entity.EntityRendererProvider
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityType
 */
package com.eternal_creations.archies_dino_mod.platform.services;

import java.util.function.Supplier;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public interface IClientHelper {
    public <T extends Entity> void registerEntityRenderer(Supplier<EntityType<T>> var1, EntityRendererProvider<T> var2);

    public <T extends BlockEntity> void registerBlockEntityRenderer(Supplier<BlockEntityType<T>> var1, BlockEntityRendererProvider<T> var2);
}

