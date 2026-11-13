/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.tags.TagKey
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.EntityType$Builder
 *  net.minecraft.world.entity.EntityType$EntityFactory
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.MobCategory
 *  net.minecraft.world.entity.ai.attributes.AttributeSupplier
 *  net.minecraft.world.level.biome.Biome
 */
package com.eternal_creations.archies_dino_mod.common.entity;

import com.eternal_creations.archies_dino_mod.common.ADTags;
import com.eternal_creations.archies_dino_mod.common.entity.mob.archie.ArchieEntity;
import com.eternal_creations.archies_dino_mod.common.entity.mob.chorusoatl.ChorusoatlEntity;
import com.eternal_creations.archies_dino_mod.common.entity.mob.coralosaur.CoralosaurEntity;
import com.eternal_creations.archies_dino_mod.common.entity.mob.lushsaur.LushsaurEntity;
import com.eternal_creations.archies_dino_mod.common.entity.mob.obsidiceratops.ObsidiceratopsEntity;
import com.eternal_creations.archies_dino_mod.common.entity.mob.sculkraptor.SculkraptorEntity;
import com.eternal_creations.archies_dino_mod.common.entity.mob.sculkraptor.SculkraptorNestEntity;
import com.eternal_creations.archies_dino_mod.common.entity.mob.sculksaur.SculksaurEntity;
import com.eternal_creations.archies_dino_mod.common.entity.mob.therimoss.TherimossEntity;
import com.eternal_creations.archies_dino_mod.common.entity.projectile.ChorusoatlSpitEntity;
import com.eternal_creations.archies_dino_mod.common.entity.projectile.PusTippedArrowEntity;
import com.eternal_creations.archies_dino_mod.common.entity.projectile.SculkraptorSpitEntity;
import com.eternal_creations.archies_dino_mod.common.entity.projectile.TherimossProjectileEntity;
import com.eternal_creations.archies_dino_mod.platform.Services;
import com.eternal_creations.archies_dino_mod.server.ServerConfig;
import com.eternal_creations.archies_dino_mod.util.ResourceSupplier;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.biome.Biome;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ADEntityTypes {
    public static final List<SpawnInfo<?>> SPAWN_INFO = new ArrayList();
    public static final ResourceSupplier<EntityType<ArchieEntity>> ARCHIE = ADEntityTypes.register("archie", ArchieEntity::new, MobCategory.CREATURE, builder -> builder.m_20699_(0.6f, 1.8f), () -> ServerConfig.getEntitySettings().archie().stats().build());
    public static final ResourceSupplier<EntityType<LushsaurEntity>> LUSHSAUR = ADEntityTypes.register("lushsaur", LushsaurEntity::new, MobCategory.CREATURE, builder -> builder.m_20699_(3.2f, 3.0f), () -> ServerConfig.getEntitySettings().lushsaur().stats().build());
    public static final ResourceSupplier<EntityType<ObsidiceratopsEntity>> OBSIDICERATOPS = ADEntityTypes.register("obsidiceratops", ObsidiceratopsEntity::new, MobCategory.CREATURE, builder -> builder.m_20699_(3.2f, 3.0f).m_20719_(), () -> ServerConfig.getEntitySettings().obsidiceratops().stats().build());
    public static final ResourceSupplier<EntityType<TherimossEntity>> THERIMOSS = ADEntityTypes.register("therimoss", TherimossEntity::new, MobCategory.CREATURE, builder -> builder.m_20699_(2.4f, 3.5f), () -> ServerConfig.getEntitySettings().therimoss().stats().build());
    public static final ResourceSupplier<EntityType<ChorusoatlEntity>> CHORUSOATL = ADEntityTypes.register("chorusoatl", ChorusoatlEntity::new, MobCategory.CREATURE, builder -> builder.m_20699_(3.6f, 5.2f).m_20717_(2), () -> ServerConfig.getEntitySettings().chorusoatl().stats().build(), ADTags.Biomes.SPAWNS_CHORUSOATLS, 2, 3, 5);
    public static final ResourceSupplier<EntityType<SculkraptorEntity>> SCULKRAPTOR = ADEntityTypes.register("sculkraptor", SculkraptorEntity::new, MobCategory.MONSTER, builder -> builder.m_20699_(0.8f, 1.2f), () -> ServerConfig.getEntitySettings().sculkraptor().stats().build(), ADTags.Biomes.SPAWNS_SCULKRAPTORS, 10, 1, 1);
    public static final ResourceSupplier<EntityType<SculksaurEntity>> SCULKSAUR = ADEntityTypes.register("sculksaur", SculksaurEntity::new, MobCategory.MONSTER, builder -> builder.m_20699_(3.8f, 5.6f).m_20702_(16).m_20719_(), () -> ServerConfig.getEntitySettings().sculksaur().settings().stats().build());
    public static final ResourceSupplier<EntityType<CoralosaurEntity>> CORALOSAUR = ADEntityTypes.register("coralosaur", CoralosaurEntity::new, MobCategory.CREATURE, builder -> builder.m_20699_(3.6f, 3.6f), () -> ServerConfig.getEntitySettings().coralosaur().stats().build());
    public static final ResourceSupplier<EntityType<ChorusoatlSpitEntity>> CHORUSOATL_SPIT = ADEntityTypes.register("chorusoatl_spit", ChorusoatlSpitEntity::new, MobCategory.MISC, builder -> builder.m_20699_(0.5f, 0.5f).m_20702_(100).m_20717_(20));
    public static final ResourceSupplier<EntityType<TherimossProjectileEntity>> THERIMOSS_PROJECTILE = ADEntityTypes.register("therimoss_projectile", TherimossProjectileEntity::new, MobCategory.MISC, builder -> builder.m_20699_(0.85f, 0.85f).m_20702_(100).m_20717_(20));
    public static final ResourceSupplier<EntityType<PusTippedArrowEntity>> PUS_TIPPED_ARROW = ADEntityTypes.register("pus_tipped_arrow", PusTippedArrowEntity::new, MobCategory.MISC, builder -> builder.m_20699_(0.5f, 0.5f).m_20702_(150).m_20717_(20));
    public static final ResourceSupplier<EntityType<SculkraptorSpitEntity>> SCULKRAPTOR_SPIT = ADEntityTypes.register("sculkraptor_spit", SculkraptorSpitEntity::new, MobCategory.MISC, builder -> builder.m_20699_(0.5f, 0.5f).m_20702_(150).m_20717_(20));
    public static final ResourceSupplier<EntityType<SculkraptorNestEntity>> SCULKRAPTOR_NEST = ADEntityTypes.register("sculkraptor_nest", SculkraptorNestEntity::new, MobCategory.MISC, builder -> builder.m_20699_(1.5f, 1.0f).m_20702_(150).m_20717_(20));

    public static void init() {
    }

    private static <T extends LivingEntity> ResourceSupplier<EntityType<T>> register(String id, EntityType.EntityFactory<T> factory, MobCategory category, Function<EntityType.Builder<T>, EntityType.Builder<T>> function, Supplier<AttributeSupplier> attributes, TagKey<Biome> biomes, int weight, int minCount, int maxCount) {
        ResourceSupplier<EntityType<T>> result = Services.COMMON.registerEntityType(id, function.apply(EntityType.Builder.m_20704_(factory, (MobCategory)category)));
        Services.COMMON.registerEntityAttributes(result, attributes);
        SPAWN_INFO.add(new SpawnInfo<T>(result, biomes, weight, minCount, maxCount));
        return result;
    }

    private static <T extends LivingEntity> ResourceSupplier<EntityType<T>> register(String id, EntityType.EntityFactory<T> factory, MobCategory category, Function<EntityType.Builder<T>, EntityType.Builder<T>> function, Supplier<AttributeSupplier> attributes) {
        ResourceSupplier<EntityType<T>> result = Services.COMMON.registerEntityType(id, function.apply(EntityType.Builder.m_20704_(factory, (MobCategory)category)));
        Services.COMMON.registerEntityAttributes(result, attributes);
        return result;
    }

    private static <T extends Entity> ResourceSupplier<EntityType<T>> register(String id, EntityType.EntityFactory<T> factory, MobCategory category, Function<EntityType.Builder<T>, EntityType.Builder<T>> function) {
        return Services.COMMON.registerEntityType(id, function.apply(EntityType.Builder.m_20704_(factory, (MobCategory)category)));
    }

    public record SpawnInfo<T extends LivingEntity>(ResourceSupplier<EntityType<T>> supplier, TagKey<Biome> biomes, int weight, int minCount, int maxCount) {
    }
}

