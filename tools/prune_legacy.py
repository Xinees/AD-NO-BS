from pathlib import Path
import re

BASE = Path("src/main/java/com/eternal_creations/archies_dino_mod")
if not BASE.is_dir():
    raise SystemExit(f"Source root not found: {BASE}")

KEEPS = {
    "common/items/ADItems.java": {
        "LUSH_CORE", "CHORUS_FOSSIL", "SCULK_TOXIN", "OOZE_TIPPED_ARROW",
        "LUSHSAUR_MACE", "AMBER", "LUSHSAUR_EGG", "CHORUSOATL_EGG",
        "LUSHSAUR_SPAWN_EGG", "CHORUSOATL_SPAWN_EGG", "SCULKSAUR_SPAWN_EGG",
    },
    "common/block/ADBlocks.java": {"LUSHSAUR_EGG", "CHORUSOATL_EGG"},
    "common/block/entity/ADBlockEntityTypes.java": {"LUSHSAUR_EGG", "CHORUSOATL_EGG"},
    "common/entity/ADEntityTypes.java": {
        "SPAWN_INFO", "LUSHSAUR", "CHORUSOATL", "SCULKSAUR",
        "CHORUSOATL_SPIT", "PUS_TIPPED_ARROW",
    },
    "common/entity/ADSensorTypes.java": {"SCULKSAUR_SENSOR"},
    "common/entity/ADActivities.java": {"CHASE"},
    "common/entity/ADMemoryModuleTypes.java": {"IS_ENRAGED", "WALK_COMMAND"},
    "common/effect/ADMobEffects.java": {"DAZED"},
    "common/ADGameEvents.java": {"PUS_ARROW_AGGRO"},
    "common/particle/ADParticles.java": {
        "DUST_PILLAR", "CHORUSOATL_SPIT_0", "CHORUSOATL_SPIT_1",
        "CHORUSOATL_SPIT_AOE", "CHORUSOATL_SPIT_PROJECTILE",
        "SCULKSAUR_EXPLOSION_2", "SCULKSAUR_EXPLOSION_3",
        "SCULKSAUR_EXPLOSION_4", "SCULKSAUR_EXPLOSION_5",
        "SCULKSAUR_EXPLOSION_6",
    },
    "client/model/registry/ADEntityModels.java": {
        "LUSHSAUR", "LUSHSAUR_BABY", "CHORUSOATL", "CHORUSOATL_BABY", "SCULKSAUR",
    },
    "client/model/registry/ADBlockEntityModels.java": {"LUSHSAUR_EGG", "CHORUSOATL_EGG"},
    "client/model/registry/ADAttachableModels.java": {"LUSHSAUR_MACE"},
}

SOUND_KEEP = set("""
DINO_EGG_BREAK DINO_EGG_CRACK DINO_EGG_HATCH DINO_EGG_PICKUP DINO_EQUIP
CHORUSOATL_BITE CHORUSOATL_DEATH_GROUND CHORUSOATL_FLAP_DOWN CHORUSOATL_FLAP_UP
CHORUSOATL_GLIDE CHORUSOATL_HURT CHORUSOATL_IDLE CHORUSOATL_SCREAM CHORUSOATL_SPIT
CHORUSOATL_STEP_HEAVY CHORUSOATL_STEP_LIGHT
LUSHSAUR_ATTACK_BITE LUSHSAUR_ATTACK_HEADBUTT LUSHSAUR_ATTACK_TAIL_STOMP
LUSHSAUR_ATTACK_TAIL_SWING LUSHSAUR_BURY LUSHSAUR_DEATH LUSHSAUR_EAT
LUSHSAUR_EMERGE LUSHSAUR_HURT LUSHSAUR_IDLE LUSHSAUR_REST LUSHSAUR_ROAR
LUSHSAUR_RUN_ATTACK LUSHSAUR_SHEAR LUSHSAUR_STEP LUSHSAUR_STOMP_EXPLOSION
SCULKSAUR_ATTACK SCULKSAUR_ATTACK_TAIL SCULKSAUR_BITE SCULKSAUR_DEATH
SCULKSAUR_EXPLOSION SCULKSAUR_HURT SCULKSAUR_IDLE SCULKSAUR_ROAR
SCULKSAUR_SNIFF SCULKSAUR_SPAWN SCULKSAUR_STEP SCULKSAUR_TENDRIL_CLICKS
SCULKSAUR_WART_POP MACE_ATTACK MACE_SPIN
""".split())
KEEPS["common/sound/ADSoundEvents.java"] = SOUND_KEEP

# Keep only the registration fields required by the three original creatures.
field_pattern = re.compile(r"^(\s*public\s+static\s+(?:final\s+)?[^=;]+?\s+)([A-Z][A-Z0-9_]*)\s*=.*;\s*$")
for relative, keep in KEEPS.items():
    path = BASE / relative
    if not path.is_file():
        raise SystemExit(f"Expected registry source missing: {path}")
    lines = path.read_text(encoding="utf-8").splitlines()
    output = []
    for line in lines:
        match = field_pattern.match(line)
        if match and match.group(2) not in keep:
            line = match.group(1) + match.group(2) + " = null;"
        output.append(line)
    path.write_text("\n".join(output) + "\n", encoding="utf-8")

# Restore the spawn metadata collection and the Sculk Tipped Arrow entity after registry filtering.
entity_types = BASE / "common/entity/ADEntityTypes.java"
entity_text = entity_types.read_text(encoding="utf-8")
entity_text = entity_text.replace(
    "public static final List<SpawnInfo<?>> SPAWN_INFO = null;",
    "public static final List<SpawnInfo<?>> SPAWN_INFO = new ArrayList();",
)
entity_text = entity_text.replace(
    "public static final ResourceSupplier<EntityType<PusTippedArrowEntity>> PUS_TIPPED_ARROW = null;",
    'public static final ResourceSupplier<EntityType<PusTippedArrowEntity>> PUS_TIPPED_ARROW = ADEntityTypes.register("pus_tipped_arrow", PusTippedArrowEntity::new, MobCategory.MISC, builder -> builder.m_20699_(0.5f, 0.5f).m_20702_(150).m_20717_(20));',
)
entity_types.write_text(entity_text, encoding="utf-8")

def write(relative, content):
    path = BASE / relative
    path.parent.mkdir(parents=True, exist_ok=True)
    path.write_text(content, encoding="utf-8")

write("common/tab/ADCreativeTabs.java", """package com.eternal_creations.archies_dino_mod.common.tab;

import com.eternal_creations.archies_dino_mod.common.items.ADItems;
import com.eternal_creations.archies_dino_mod.platform.Services;
import com.eternal_creations.archies_dino_mod.util.ResourceSupplier;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class ADCreativeTabs {
    public static final ResourceSupplier<CreativeModeTab> MOD_TAB = Services.COMMON.registerCreativeTab(
        "items", () -> new ItemStack((ItemLike) ADItems.LUSH_CORE.get()), (parameters, output) -> {
            output.m_246326_((ItemLike) ADItems.LUSH_CORE.get());
            output.m_246326_((ItemLike) ADItems.CHORUS_FOSSIL.get());
            output.m_246326_((ItemLike) ADItems.SCULK_TOXIN.get());
            output.m_246326_((ItemLike) ADItems.LUSHSAUR_MACE.get());
            output.m_246326_((ItemLike) ADItems.OOZE_TIPPED_ARROW.get());
        });

    public static void init() { }
}
""")

write("CommonClass.java", """package com.eternal_creations.archies_dino_mod;

import com.eternal_creations.archies_dino_mod.common.ADGameEvents;
import com.eternal_creations.archies_dino_mod.common.block.ADBlocks;
import com.eternal_creations.archies_dino_mod.common.block.entity.ADBlockEntityTypes;
import com.eternal_creations.archies_dino_mod.common.effect.ADMobEffects;
import com.eternal_creations.archies_dino_mod.common.entity.ADActionTypes;
import com.eternal_creations.archies_dino_mod.common.entity.ADActivities;
import com.eternal_creations.archies_dino_mod.common.entity.ADEntityTypes;
import com.eternal_creations.archies_dino_mod.common.entity.ADMemoryModuleTypes;
import com.eternal_creations.archies_dino_mod.common.entity.ADSensorTypes;
import com.eternal_creations.archies_dino_mod.common.items.ADItems;
import com.eternal_creations.archies_dino_mod.common.particle.ADParticles;
import com.eternal_creations.archies_dino_mod.common.sound.ADSoundEvents;
import com.eternal_creations.archies_dino_mod.common.tab.ADCreativeTabs;

public class CommonClass {
    public static void init() {
        ADActionTypes.init();
        ADSoundEvents.init();
        ADBlocks.init();
        ADBlockEntityTypes.init();
        ADEntityTypes.init();
        ADParticles.init();
        ADItems.init();
        ADCreativeTabs.init();
        ADMobEffects.init();
        ADGameEvents.init();
        ADSensorTypes.init();
        ADActivities.init();
        ADMemoryModuleTypes.init();
    }
}
""")

write("client/ADKeyMappings.java", """package com.eternal_creations.archies_dino_mod.client;

import com.eternal_creations.archies_dino_mod.common.entity.base.Mount;
import com.eternal_creations.archies_dino_mod.networking.serverbound.MountControlMessage;
import com.eternal_creations.archies_dino_mod.platform.Services;
import com.mojang.blaze3d.platform.InputConstants;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ADKeyMappings {
    public static final String DEFAULT_MOD_CATEGORY = "category.ec_ad.default";
    public static final Map<KeyMapping, BiConsumer<LocalPlayer, ClientLevel>> KEY_MAPPINGS = new HashMap<>();
    public static final KeyMapping MOUNT_ATTACK_PRIMARY = createConditional(
        "mount_primary_attack", InputConstants.Type.MOUSE, 0, DEFAULT_MOD_CATEGORY,
        (player, level) -> sendMountControl(player, Mount.AttackType.PRIMARY), ADKeyMappings::isOnMount);
    public static final KeyMapping MOUNT_ATTACK_SPECIAL = createConditional(
        "mount_special_attack", InputConstants.Type.MOUSE, 1, DEFAULT_MOD_CATEGORY,
        (player, level) -> sendMountControl(player, Mount.AttackType.SPECIAL), ADKeyMappings::isOnMount);

    private static void sendMountControl(LocalPlayer player, Mount.AttackType type) {
        if (player.m_20202_() instanceof Mount mount && mount.mount().isNoPassengerCooldown()) {
            Services.NETWORK.sendToServer(new MountControlMessage(player.m_20202_().m_19879_(), type));
        }
    }

    private static boolean isOnMount(LocalPlayer player) {
        return player.m_20202_() instanceof Mount;
    }

    private static KeyMapping createConditional(String name, InputConstants.Type type, int keyCode,
            String category, BiConsumer<LocalPlayer, ClientLevel> consumer, Predicate<LocalPlayer> predicate) {
        KeyMappingConditional mapping = new KeyMappingConditional("ec_ad." + name, type, keyCode, category, predicate);
        KEY_MAPPINGS.put(mapping, consumer);
        return mapping;
    }

    public static class KeyMappingConditional extends KeyMapping {
        private final Predicate<LocalPlayer> predicate;

        public KeyMappingConditional(String name, InputConstants.Type type, int keyCode, String category,
                Predicate<LocalPlayer> predicate) {
            super(name, type, keyCode, category);
            this.predicate = predicate;
        }

        public boolean isActive(LocalPlayer player) { return predicate.test(player); }
        public Predicate<LocalPlayer> getPredicate() { return predicate; }
    }

    public interface KeyMappingExtension {
        InputConstants.Key getKey();
        int getClickCount();
        void setClickCount(int count);
        void setDown(boolean down);
    }
}
""")

write("client/particle/ADParticleEmitters.java", """package com.eternal_creations.archies_dino_mod.client.particle;

import com.astemir.astemirlib.re.client.particle.BedrockParticleManager;
import com.astemir.astemirlib.re.client.particle.ParticleEmitterConfig;
import com.astemir.astemirlib.re.common.world.particle.ParticleEmitterType;
import com.eternal_creations.archies_dino_mod.Constants;
import com.eternal_creations.archies_dino_mod.client.particle.effect.ChorusoatlEffects;
import com.eternal_creations.archies_dino_mod.client.particle.effect.LushsaurEffects;
import com.eternal_creations.archies_dino_mod.client.particle.effect.SculksaurEffects;
import com.eternal_creations.archies_dino_mod.common.particle.ADParticles;
import com.eternal_creations.archies_dino_mod.util.ResourceSupplier;

public class ADParticleEmitters {
    public static void init() {
        LushsaurEffects.init();
        ChorusoatlEffects.init();
        SculksaurEffects.init();
    }

    public static ParticleEmitterConfig[] register(String path, int startIndex, int count) {
        ParticleEmitterConfig[] configs = new ParticleEmitterConfig[count];
        for (int i = 0; i < count; ++i) {
            int currentIndex = startIndex == 0 ? i + 1 : startIndex + i;
            String fullPath = startIndex == 0 && i == 0 ? path : path + "_" + currentIndex;
            configs[i] = register(fullPath);
        }
        return configs;
    }

    public static ParticleEmitterConfig register(String path) {
        return BedrockParticleManager.INSTANCE.registerParticle(Constants.prefix("particles/" + path + ".particle.json"));
    }

    public static ParticleEmitterConfig register(ResourceSupplier<ParticleEmitterType> type, String path) {
        return BedrockParticleManager.INSTANCE.registerParticle(type.get(), Constants.prefix("particles/" + path + ".particle.json"));
    }

    static {
        register(ADParticles.CHORUSOATL_SPIT_0, "chorusoatl/spit/chorusoatl_spit");
        register(ADParticles.CHORUSOATL_SPIT_1, "chorusoatl/spit/chorusoatl_spit_2");
        register(ADParticles.CHORUSOATL_SPIT_AOE, "chorusoatl/spit/chorusoatl_spit_aoe");
        register(ADParticles.CHORUSOATL_SPIT_PROJECTILE, "chorusoatl/spit/chorusoatl_spit_projectile");
    }
}
""")

write("client/CommonClient.java", """package com.eternal_creations.archies_dino_mod.client;

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
""")

write("client/ADClientModBus.java", """package com.eternal_creations.archies_dino_mod.client;

import com.eternal_creations.archies_dino_mod.client.particle.DustPillarParticle;
import com.eternal_creations.archies_dino_mod.common.particle.ADParticles;
import com.eternal_creations.archies_dino_mod.platform.ForgeClientHelper;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = {Dist.CLIENT}, modid = "ec_ad")
public class ADClientModBus {
    public static void init() { CommonClient.init(); }

    @SubscribeEvent
    public static void onLoadRenderers(EntityRenderersEvent.RegisterRenderers event) {
        ForgeClientHelper.ENTITY_RENDERERS.forEach((type, renderer) -> event.registerEntityRenderer((EntityType) type.get(), renderer));
        ForgeClientHelper.BLOCK_ENTITY_RENDERERS.forEach((type, renderer) -> event.registerBlockEntityRenderer((BlockEntityType) type.get(), renderer));
    }

    @SubscribeEvent
    public static void registerFactories(RegisterParticleProvidersEvent event) {
        event.registerSpecial(ADParticles.DUST_PILLAR.get(), (ParticleProvider) new DustPillarParticle.DustPillarProvider());
    }

    @SubscribeEvent
    public static void registerKeyMappingsEvent(RegisterKeyMappingsEvent event) {
        ADKeyMappings.KEY_MAPPINGS.keySet().forEach(event::register);
    }
}
""")

write("ArchiesDinosaurs.java", """package com.eternal_creations.archies_dino_mod;

import com.eternal_creations.archies_dino_mod.client.ADClientModBus;
import com.eternal_creations.archies_dino_mod.common.ADGameEvents;
import com.eternal_creations.archies_dino_mod.common.entity.ADEntityTypes;
import com.eternal_creations.archies_dino_mod.platform.ForgeCommonHelper;
import com.eternal_creations.archies_dino_mod.platform.ForgeNetworkHelper;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.function.Supplier;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.gameevent.vibrations.VibrationSystem;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("ec_ad")
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ArchiesDinosaurs {
    public ArchiesDinosaurs(FMLJavaModLoadingContext context) {
        CommonClass.init();
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> ADClientModBus::init);
        ForgeNetworkHelper.init();
        registerCommon(context.getModEventBus());
    }

    private void registerCommon(IEventBus bus) {
        bus.addListener(this::registerAttributes);
        bus.addListener(this::init);
        ForgeCommonHelper.BLOCKS.register(bus);
        ForgeCommonHelper.BLOCK_ENTITY_TYPES.register(bus);
        ForgeCommonHelper.ENTITY_TYPES.register(bus);
        ForgeCommonHelper.ITEMS.register(bus);
        ForgeCommonHelper.SOUND_EVENTS.register(bus);
        ForgeCommonHelper.PARTICLE_TYPES.register(bus);
        ForgeCommonHelper.TABS.register(bus);
        ForgeCommonHelper.GAME_EVENTS.register(bus);
        ForgeCommonHelper.MOB_EFFECTS.register(bus);
        ForgeCommonHelper.SENSOR_TYPES.register(bus);
        ForgeCommonHelper.ACTIVITIES.register(bus);
        ForgeCommonHelper.MEMORY_MODULE_TYPES.register(bus);
    }

    private void registerAttributes(EntityAttributeCreationEvent event) {
        ForgeCommonHelper.ATTRIBUTES.forEach((entityType, attributes) ->
            event.put((EntityType) entityType.get(), (AttributeSupplier) attributes.get()));
    }

    private void init(FMLCommonSetupEvent event) {
        event.enqueueWork(() ->
            ((Object2IntOpenHashMap) VibrationSystem.f_279561_).put(ADGameEvents.PUS_ARROW_AGGRO.get(), 64));
    }

    @Mod.EventBusSubscriber(modid = "ec_ad", bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModBusEvents {
        @SubscribeEvent
        public static void registerSpawnPlacements(SpawnPlacementRegisterEvent event) {
            ground(event, ADEntityTypes.LUSHSAUR);
            ground(event, ADEntityTypes.CHORUSOATL);
            ground(event, ADEntityTypes.SCULKSAUR);
        }

        private static <T extends Mob> void ground(SpawnPlacementRegisterEvent event, Supplier<EntityType<T>> type) {
            event.register(type.get(), SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::m_217057_,
                SpawnPlacementRegisterEvent.Operation.REPLACE);
        }
    }
}
""")

write("client/animation/player/ADPlayerAnimations.java", """package com.eternal_creations.archies_dino_mod.client.animation.player;

import com.eternal_creations.archies_dino_mod.client.animation.player.attachable.LushsaurMaceAnimations;
import com.eternal_creations.archies_dino_mod.client.animation.player.riding.OnChorusoatlPlayerAnimations;
import com.eternal_creations.archies_dino_mod.client.animation.player.riding.OnLushsaurPlayerAnimations;

public class ADPlayerAnimations {
    public static void init() {
        OnLushsaurPlayerAnimations.init(1);
        OnChorusoatlPlayerAnimations.init(1);
        LushsaurMaceAnimations.init(2);
    }
}
""")

# Remove the generated decompiler summary from Java source roots.
summary = Path("src/main/java/summary.txt")
if summary.exists():
    summary.unlink()

print("Legacy registrations narrowed to Sculksaur, Chorusoatl and Lushsaur.")
