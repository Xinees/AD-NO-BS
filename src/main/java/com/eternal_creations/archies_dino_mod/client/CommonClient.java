/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.astemir.astemirlib.re.client.model.core.attachable.Attachable
 *  com.astemir.astemirlib.re.client.model.core.attachable.Attachable$Builder
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.client.renderer.blockentity.BlockEntityRenderer
 *  net.minecraft.client.renderer.entity.ArrowRenderer
 *  net.minecraft.client.renderer.entity.EntityRendererProvider
 *  net.minecraft.client.renderer.entity.NoopRenderer
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityType
 */
package com.eternal_creations.archies_dino_mod.client;

import com.astemir.astemirlib.re.client.model.core.attachable.Attachable;
import com.eternal_creations.archies_dino_mod.client.animation.player.ADPlayerAnimations;
import com.eternal_creations.archies_dino_mod.client.model.attachable.ChorusScytheAttachable;
import com.eternal_creations.archies_dino_mod.client.model.attachable.LushsaurMaceAttachable;
import com.eternal_creations.archies_dino_mod.client.model.attachable.RibBowAttachable;
import com.eternal_creations.archies_dino_mod.client.model.attachable.ScorchingHornAttachable;
import com.eternal_creations.archies_dino_mod.client.model.attachable.SculkBowAttachable;
import com.eternal_creations.archies_dino_mod.client.model.attachable.TheriClawsAttachable;
import com.eternal_creations.archies_dino_mod.client.model.registry.ADAttachableModels;
import com.eternal_creations.archies_dino_mod.client.model.registry.ADBlockEntityModels;
import com.eternal_creations.archies_dino_mod.client.model.registry.ADEntityModels;
import com.eternal_creations.archies_dino_mod.client.model.render.block.DinoEggClient;
import com.eternal_creations.archies_dino_mod.client.model.render.entity.misc.SculkraptorNestClient;
import com.eternal_creations.archies_dino_mod.client.model.render.entity.misc.SculkraptorProjectileClient;
import com.eternal_creations.archies_dino_mod.client.model.render.entity.mob.ArchieClient;
import com.eternal_creations.archies_dino_mod.client.model.render.entity.mob.ChorusoatlClient;
import com.eternal_creations.archies_dino_mod.client.model.render.entity.mob.CoralosaurClient;
import com.eternal_creations.archies_dino_mod.client.model.render.entity.mob.LushsaurClient;
import com.eternal_creations.archies_dino_mod.client.model.render.entity.mob.ObsidiceratopsClient;
import com.eternal_creations.archies_dino_mod.client.model.render.entity.mob.SculkraptorClient;
import com.eternal_creations.archies_dino_mod.client.model.render.entity.mob.SculksaurClient;
import com.eternal_creations.archies_dino_mod.client.model.render.entity.mob.TherimossClient;
import com.eternal_creations.archies_dino_mod.client.model.render.entity.projectile.TherimossProjectileClient;
import com.eternal_creations.archies_dino_mod.client.particle.ADParticleEmitters;
import com.eternal_creations.archies_dino_mod.common.block.entity.ADBlockEntityTypes;
import com.eternal_creations.archies_dino_mod.common.entity.ADEntityTypes;
import com.eternal_creations.archies_dino_mod.common.entity.projectile.PusTippedArrowEntity;
import com.eternal_creations.archies_dino_mod.platform.Services;
import com.eternal_creations.archies_dino_mod.util.ResourceSupplier;
import java.util.function.Supplier;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CommonClient {
    public static void init() {
        ADEntityModels.init();
        ADBlockEntityModels.init();
        ADAttachableModels.init();
        ADPlayerAnimations.init();
        ADBlockEntityTypes.init();
        ADParticleEmitters.init();
        CommonClient.registerEntityRenderers();
        CommonClient.registerBlockEntityRenderers();
        CommonClient.registerAttachables();
        Attachable.register((Attachable.Builder[])new Attachable.Builder[]{ChorusScytheAttachable.create(), LushsaurMaceAttachable.create(), RibBowAttachable.create(), SculkBowAttachable.create(), TheriClawsAttachable.create(), ScorchingHornAttachable.create()});
    }

    private static void registerEntityRenderers() {
        CommonClient.registerEntityRenderer(ADEntityTypes.ARCHIE, ArchieClient.createRenderProvider());
        CommonClient.registerEntityRenderer(ADEntityTypes.CHORUSOATL_SPIT, NoopRenderer::new);
        CommonClient.registerEntityRenderer(ADEntityTypes.LUSHSAUR, LushsaurClient.createRenderProvider());
        CommonClient.registerEntityRenderer(ADEntityTypes.CHORUSOATL, ChorusoatlClient.createRenderProvider());
        CommonClient.registerEntityRenderer(ADEntityTypes.OBSIDICERATOPS, ObsidiceratopsClient.createRenderProvider());
        CommonClient.registerEntityRenderer(ADEntityTypes.THERIMOSS, TherimossClient.createRenderProvider());
        CommonClient.registerEntityRenderer(ADEntityTypes.SCULKSAUR, SculksaurClient.createRenderProvider());
        CommonClient.registerEntityRenderer(ADEntityTypes.SCULKRAPTOR, SculkraptorClient.createRenderProvider());
        CommonClient.registerEntityRenderer(ADEntityTypes.PUS_TIPPED_ARROW, context -> new ArrowRenderer<PusTippedArrowEntity>(context){

            public ResourceLocation getTextureLocation(PusTippedArrowEntity entity) {
                return PusTippedArrowEntity.TEXTURE;
            }
        });
        CommonClient.registerEntityRenderer(ADEntityTypes.SCULKRAPTOR_NEST, SculkraptorNestClient.createRenderProvider());
        CommonClient.registerEntityRenderer(ADEntityTypes.THERIMOSS_PROJECTILE, TherimossProjectileClient.createRenderProvider());
        CommonClient.registerEntityRenderer(ADEntityTypes.SCULKRAPTOR_SPIT, SculkraptorProjectileClient.createRenderProvider());
        CommonClient.registerEntityRenderer(ADEntityTypes.CORALOSAUR, CoralosaurClient.createRenderProvider());
    }

    private static void registerBlockEntityRenderers() {
        CommonClient.registerBlockEntityRenderer(ADBlockEntityTypes.LUSHSAUR_EGG, DinoEggClient.createRenderer(ADBlockEntityModels.LUSHSAUR_EGG));
        CommonClient.registerBlockEntityRenderer(ADBlockEntityTypes.CHORUSOATL_EGG, DinoEggClient.createRenderer(ADBlockEntityModels.CHORUSOATL_EGG));
        CommonClient.registerBlockEntityRenderer(ADBlockEntityTypes.OBSIDICERATOPS_EGG, DinoEggClient.createRenderer(ADBlockEntityModels.OBSIDICERATOPS_EGG));
        CommonClient.registerBlockEntityRenderer(ADBlockEntityTypes.SCULKRAPTOR_EGG, DinoEggClient.createRenderer(ADBlockEntityModels.SCULKRAPTOR_EGG));
        CommonClient.registerBlockEntityRenderer(ADBlockEntityTypes.THERIMOSS_EGG, DinoEggClient.createRenderer(ADBlockEntityModels.THERIMOSS_EGG));
    }

    private static void registerAttachables() {
    }

    private static <T extends BlockEntity> void registerBlockEntityRenderer(ResourceSupplier<BlockEntityType<T>> blockEntityType, BlockEntityRenderer<T> renderer) {
        Services.CLIENT.registerBlockEntityRenderer(blockEntityType, ctx -> renderer);
    }

    private static <T extends Entity> void registerEntityRenderer(Supplier<EntityType<T>> entityType, EntityRendererProvider<T> provider) {
        Services.CLIENT.registerEntityRenderer(entityType, provider);
    }
}

