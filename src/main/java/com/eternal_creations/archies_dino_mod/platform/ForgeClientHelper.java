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
package com.eternal_creations.archies_dino_mod.platform;

import com.eternal_creations.archies_dino_mod.platform.services.IClientHelper;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ForgeClientHelper
implements IClientHelper {
    public static final Map<Supplier<? extends EntityType>, EntityRendererProvider> ENTITY_RENDERERS = new HashMap<Supplier<? extends EntityType>, EntityRendererProvider>();
    public static final Map<Supplier<? extends BlockEntityType>, BlockEntityRendererProvider> BLOCK_ENTITY_RENDERERS = new HashMap<Supplier<? extends BlockEntityType>, BlockEntityRendererProvider>();

    @Override
    public <T extends Entity> void registerEntityRenderer(Supplier<EntityType<T>> entityType, EntityRendererProvider<T> provider) {
        ENTITY_RENDERERS.put(entityType, provider);
    }

    @Override
    public <T extends BlockEntity> void registerBlockEntityRenderer(Supplier<BlockEntityType<T>> blockEntityType, BlockEntityRendererProvider<T> provider) {
        BLOCK_ENTITY_RENDERERS.put(blockEntityType, provider);
    }
}

