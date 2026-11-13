package com.eternal_creations.archies_dino_mod.client;

import com.astemir.astemirlib.re.client.model.core.attachable.Attachable;
import com.eternal_creations.archies_dino_mod.client.animation.player.ADPlayerAnimations;
import com.eternal_creations.archies_dino_mod.client.model.attachable.LushsaurMaceAttachable;
import com.eternal_creations.archies_dino_mod.client.model.registry.ADAttachableModels;
import com.eternal_creations.archies_dino_mod.client.model.registry.ADBlockEntityModels;
import com.eternal_creations.archies_dino_mod.client.model.registry.ADEntityModels;
import com.eternal_creations.archies_dino_mod.client.model.render.block.DinoEggClient;
import com.eternal_creations.archies_dino_mod.client.particle.ADParticleEmitters;
import com.eternal_creations.archies_dino_mod.common.block.entity.ADBlockEntityTypes;
import com.eternal_creations.archies_dino_mod.common.entity.ADEntityTypes;
import com.eternal_creations.archies_dino_mod.platform.Services;
import com.eternal_creations.archies_dino_mod.util.ResourceSupplier;
import java.util.function.Supplier;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class CommonClient {
    public static void init() {
        ADEntityModels.init();
        ADBlockEntityModels.init();
        ADAttachableModels.init();
        ADPlayerAnimations.init();
        ADParticleEmitters.init();
        registerEntityRenderers();
        registerBlockEntityRenderers();
        Attachable.register(new Attachable.Builder[] { LushsaurMaceAttachable.create() });
    }

    private static void registerEntityRenderers() {
        registerEntityRenderer(ADEntityTypes.LUSHSAUR, com.eternal_creations.archies_dino_mod.client.model.render.entity.mob.LushsaurClient.createRenderProvider());
        registerEntityRenderer(ADEntityTypes.CHORUSOATL, com.eternal_creations.archies_dino_mod.client.model.render.entity.mob.ChorusoatlClient.createRenderProvider());
        registerEntityRenderer(ADEntityTypes.SCULKSAUR, com.eternal_creations.archies_dino_mod.client.model.render.entity.mob.SculksaurClient.createRenderProvider());
        registerEntityRenderer(ADEntityTypes.CHORUSOATL_SPIT, NoopRenderer::new);
        registerEntityRenderer(ADEntityTypes.PUS_TIPPED_ARROW, context -> new net.minecraft.client.renderer.entity.ArrowRenderer<com.eternal_creations.archies_dino_mod.common.entity.projectile.PusTippedArrowEntity>(context) {
            @Override
            public net.minecraft.resources.ResourceLocation getTextureLocation(com.eternal_creations.archies_dino_mod.common.entity.projectile.PusTippedArrowEntity entity) {
                return com.eternal_creations.archies_dino_mod.common.entity.projectile.PusTippedArrowEntity.TEXTURE;
            }
        });
    }

    private static void registerBlockEntityRenderers() {
        registerBlockEntityRenderer(ADBlockEntityTypes.LUSHSAUR_EGG, DinoEggClient.createRenderer(ADBlockEntityModels.LUSHSAUR_EGG));
        registerBlockEntityRenderer(ADBlockEntityTypes.CHORUSOATL_EGG, DinoEggClient.createRenderer(ADBlockEntityModels.CHORUSOATL_EGG));
    }

    private static <T extends BlockEntity> void registerBlockEntityRenderer(ResourceSupplier<BlockEntityType<T>> type, BlockEntityRenderer<T> renderer) {
        Services.CLIENT.registerBlockEntityRenderer(type, ctx -> renderer);
    }

    private static <T extends Entity> void registerEntityRenderer(Supplier<EntityType<T>> type, EntityRendererProvider<T> provider) {
        Services.CLIENT.registerEntityRenderer(type, provider);
    }
}
