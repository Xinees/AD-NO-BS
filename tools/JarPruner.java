import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.jar.*;
import java.util.zip.*;

public final class JarPruner {
    private static final String ROOT = "com/eternal_creations/archies_dino_mod/";
    private static final Map<String, Set<String>> PROCESSED_METHODS = new HashMap<>();

    private static final Map<String, Set<String>> REGISTRY_FIELDS = Map.ofEntries(
        Map.entry(ROOT + "common/items/ADItems", Set.of(
            "LUSH_CORE", "CHORUS_FOSSIL", "SCULK_TOXIN",
            "LUSHSAUR_EGG", "CHORUSOATL_EGG",
            "LUSHSAUR_SPAWN_EGG", "CHORUSOATL_SPAWN_EGG", "SCULKSAUR_SPAWN_EGG"
        )),
        Map.entry(ROOT + "common/block/ADBlocks", Set.of("LUSHSAUR_EGG", "CHORUSOATL_EGG")),
        Map.entry(ROOT + "common/block/entity/ADBlockEntityTypes", Set.of("LUSHSAUR_EGG", "CHORUSOATL_EGG")),
        Map.entry(ROOT + "common/menu/ADMenus", Set.of("DINO")),
        Map.entry(ROOT + "common/entity/ADEntityTypes", Set.of(
            "SPAWN_INFO", "LUSHSAUR", "CHORUSOATL", "SCULKSAUR", "CHORUSOATL_SPIT"
        )),
        Map.entry(ROOT + "common/entity/ADActionTypes", Set.of(
            "ATTACK_HEADBUTT", "ATTACK_BITE", "ATTACK_TAIL", "ATTACK_TAIL_STOMP",
            "ATTACK_HIT", "ATTACK_SHOOT", "ATTACK_FLY", "ATTACK_SONIC_BOOM",
            "ATTACK_BITE_RUN", "SCREAM", "SHAKE", "ROAR", "EAT", "DEATH",
            "DEATH_AIR", "SPAWN", "UP", "DOWN", "SENSE", "LISTEN", "HURT",
            "EMERGE", "BURY"
        )),
        Map.entry(ROOT + "common/entity/ADSensorTypes", Set.of("SCULKSAUR_SENSOR")),
        Map.entry(ROOT + "common/entity/ADActivities", Set.of("CHASE")),
        Map.entry(ROOT + "common/entity/ADMemoryModuleTypes", Set.of("IS_ENRAGED", "WALK_COMMAND")),
        Map.entry(ROOT + "common/effect/ADMobEffects", Set.of("DAZED")),
        Map.entry(ROOT + "common/ADGameEvents", Set.of()),
        Map.entry(ROOT + "common/particle/ADParticles", Set.of(
            "DUST_PILLAR",
            "CHORUSOATL_SPIT_0", "CHORUSOATL_SPIT_1", "CHORUSOATL_SPIT_AOE", "CHORUSOATL_SPIT_PROJECTILE",
            "SCULKSAUR_EXPLOSION_2", "SCULKSAUR_EXPLOSION_3", "SCULKSAUR_EXPLOSION_4",
            "SCULKSAUR_EXPLOSION_5", "SCULKSAUR_EXPLOSION_6"
        )),
        Map.entry(ROOT + "common/sound/ADSoundEvents", Set.of(
            "DINO_EQUIP", "DINO_EGG_PICKUP", "DINO_EGG_BREAK", "DINO_EGG_CRACK", "DINO_EGG_HATCH", "DINO_EGG_LAY",
            "LUSHSAUR_IDLE", "LUSHSAUR_ATTACK_HEADBUTT", "LUSHSAUR_ATTACK_BITE",
            "LUSHSAUR_ATTACK_TAIL_STOMP", "LUSHSAUR_ATTACK_TAIL_SWING", "LUSHSAUR_EMERGE",
            "LUSHSAUR_BURY", "LUSHSAUR_EAR_RINGING", "LUSHSAUR_EAT", "LUSHSAUR_REST", "LUSHSAUR_ROAR",
            "LUSHSAUR_RUN_ATTACK", "LUSHSAUR_STEP", "LUSHSAUR_HURT", "LUSHSAUR_DEATH",
            "LUSHSAUR_SHEAR", "LUSHSAUR_STOMP_EXPLOSION",
            "CHORUSOATL_BITE", "CHORUSOATL_HURT", "CHORUSOATL_DEATH_GROUND",
            "CHORUSOATL_FLAP_DOWN", "CHORUSOATL_FLAP_UP", "CHORUSOATL_GLIDE", "CHORUSOATL_IDLE",
            "CHORUSOATL_SCREAM", "CHORUSOATL_SPIT", "CHORUSOATL_STEP_HEAVY", "CHORUSOATL_STEP_LIGHT",
            "SCULKSAUR_IDLE", "SCULKSAUR_ATTACK", "SCULKSAUR_ATTACK_TAIL", "SCULKSAUR_BITE",
            "SCULKSAUR_EXPLOSION", "SCULKSAUR_ROAR", "SCULKSAUR_SNIFF", "SCULKSAUR_SPAWN",
            "SCULKSAUR_STEP", "SCULKSAUR_HURT", "SCULKSAUR_DEATH", "SCULKSAUR_WART_POP",
            "SCULKSAUR_TENDRIL_CLICKS"
        )),
        Map.entry(ROOT + "client/model/registry/ADEntityModels", Set.of(
            "LUSHSAUR", "LUSHSAUR_BABY", "CHORUSOATL", "CHORUSOATL_BABY", "SCULKSAUR"
        )),
        Map.entry(ROOT + "client/model/registry/ADBlockEntityModels", Set.of("LUSHSAUR_EGG", "CHORUSOATL_EGG")),
        Map.entry(ROOT + "client/model/registry/ADAttachableModels", Set.of())
    );

    private static final List<String> CREATIVE_ITEMS = List.of(
        "LUSHSAUR_SPAWN_EGG", "CHORUSOATL_SPAWN_EGG", "SCULKSAUR_SPAWN_EGG",
        "LUSHSAUR_EGG", "CHORUSOATL_EGG",
        "LUSH_CORE", "SCULK_TOXIN", "CHORUS_FOSSIL"
    );

    private static final Map<String, Set<String>> FRAMEWORK_CALLBACK_METHODS = Map.of(
        ROOT + "client/model/system/ADModifiers$1", Set.of(
            "modify(ILcom/astemir/astemirlib/re/client/model/core/animation/AnimationState;FFLcom/astemir/astemirlib/re/client/model/core/animation/BoneState;)V"
        ),
        ROOT + "common/menu/DinoMenu", Set.of(
            "m_6875_(Lnet/minecraft/world/entity/player/Player;)Z",
            "m_7648_(Lnet/minecraft/world/entity/player/Player;I)Lnet/minecraft/world/item/ItemStack;"
        ),
        ROOT + "client/screen/DinoScreen", Set.of(
            "m_7286_(Lnet/minecraft/client/gui/GuiGraphics;FII)V"
        ),
        ROOT + "networking/serverbound/MountControlMessage", Set.of(
            "execute(Lnet/minecraft/server/level/ServerPlayer;)V"
        ),
        ROOT + "common/entity/mob/lushsaur/LushsaurEntity", Set.of(
            "m_264410_()Z"
        )
    );

    private static final Set<String> NETWORK_MESSAGES = Set.of(
        ROOT + "networking/serverbound/MountControlMessage",
        ROOT + "networking/serverbound/PopZitMessage",
        ROOT + "networking/clientbound/SyncSettingsMessage",
        ROOT + "networking/clientbound/AskShooterIfHitBoneMessage"
    );

    private static final Set<String> ROOT_CLASSES = Set.of(
        ROOT + "ArchiesDinosaurs",
        ROOT + "ArchiesDinosaurs$ModBusEvents",
        ROOT + "CommonClass",
        ROOT + "Constants",
        ROOT + "client/ADClientModBus",
        ROOT + "client/ADKeyMappings",
        ROOT + "client/ADClientForgeBus",
        ROOT + "client/CommonClient",
        ROOT + "client/animation/player/ADPlayerAnimations",
        ROOT + "platform/Services",
        ROOT + "platform/ForgeCommonHelper",
        ROOT + "platform/ForgeClientHelper",
        ROOT + "platform/ForgeNetworkHelper",
        ROOT + "platform/ForgePlatformHelper"
    );

    private static final Set<String> MIXIN_ROOTS = Set.of(
        ROOT + "mixin/AbstractArrowMixin",
        ROOT + "mixin/BreedGoalAccessor",
        ROOT + "mixin/CriteriaTriggersAccessor",
        ROOT + "mixin/EntityAccessor",
        ROOT + "mixin/PlayerListMixin",
        ROOT + "mixin/sculksaur/PrimaryLevelDataMixin",
        ROOT + "mixin/sculksaur/SculkShriekerBlockEntityMixin",
        ROOT + "mixin/sculksaur/WalkNodeEvaluatorAccessor",
        ROOT + "mixin/sculksaur/WardenMixin",
        ROOT + "mixin/client/CameraMixin",
        ROOT + "mixin/client/KeyMappingMixin",
        ROOT + "mixin/client/LevelRendererMixin",
        ROOT + "mixin/client/LivingEntityRendererMixin",
        ROOT + "mixin/client/LocalPlayerMixin"
    );

    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            throw new IllegalArgumentException("Usage: JarPruner <input.jar> <output.jar>");
        }

        Path input = Paths.get(args[0]);
        Path output = Paths.get(args[1]);
        if (!Files.isRegularFile(input)) throw new FileNotFoundException(input.toString());

        Map<String, byte[]> allEntries = new LinkedHashMap<>();
        Map<String, byte[]> classBytes = new HashMap<>();

        try (ZipFile zip = new ZipFile(input.toFile())) {
            Enumeration<? extends ZipEntry> entries = zip.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                if (entry.isDirectory()) continue;
                try (InputStream in = zip.getInputStream(entry)) {
                    byte[] bytes = in.readAllBytes();
                    allEntries.put(entry.getName(), bytes);
                    if (entry.getName().endsWith(".class")) {
                        String internalName = entry.getName().substring(0, entry.getName().length() - 6);
                        classBytes.put(internalName, bytes);
                    }
                }
            }
        }

        Map<String, ClassNode> nodes = new HashMap<>();
        for (Map.Entry<String, byte[]> entry : classBytes.entrySet()) {
            ClassNode node = new ClassNode(Opcodes.ASM9);
            new ClassReader(entry.getValue()).accept(node, 0);
            nodes.put(entry.getKey(), node);
        }

        for (Map.Entry<String, Set<String>> registry : REGISTRY_FIELDS.entrySet()) {
            ClassNode node = nodes.get(registry.getKey());
            if (node != null) pruneRegistryInitializer(node, registry.getValue());
        }

        patchNoOpMethod(nodes.get(ROOT + "ArchiesDinosaurs"), "init",
            "(Lnet/minecraftforge/fml/event/lifecycle/FMLCommonSetupEvent;)V");
        patchCommonClass(nodes.get(ROOT + "CommonClass"));
        patchCommonClient(nodes.get(ROOT + "client/CommonClient"));
        patchClientModBus(nodes.get(ROOT + "client/ADClientModBus"));
        patchClientModBusRegistrations(nodes.get(ROOT + "client/ADClientModBus"));
        patchSpawnPlacements(nodes.get(ROOT + "ArchiesDinosaurs$ModBusEvents"));
        patchPlayerAnimations(nodes.get(ROOT + "client/animation/player/ADPlayerAnimations"));
        patchCreativeTab(nodes.get(ROOT + "common/tab/ADCreativeTabs"), nodes.get(ROOT + "common/items/ADItems"));
        patchParticleEmitters(nodes.get(ROOT + "client/particle/ADParticleEmitters"));
        patchTemptItems(nodes.get(ROOT + "common/entity/mob/lushsaur/ai/LushsaurAI"), "LUSH_CORE");
        patchTemptItems(nodes.get(ROOT + "common/entity/mob/chorusoatl/ai/ChorusoatlAI"), "CHORUS_FOSSIL");
        patchEntitySettings(nodes.get(ROOT + "server/ServerConfig$EntitySettings"));
        patchServerConfig(nodes.get(ROOT + "server/ServerConfig"));
        patchNetworkHelper(nodes.get(ROOT + "platform/ForgeNetworkHelper"));
        patchKeyMappings(nodes.get(ROOT + "client/ADKeyMappings"));
        patchAbstractArrowMixin(nodes.get(ROOT + "mixin/AbstractArrowMixin"));
        patchLushsaurControllingPassenger(nodes.get(ROOT + "common/entity/mob/lushsaur/LushsaurEntity"));
        patchLushsaurCanSprint(nodes.get(ROOT + "common/entity/mob/lushsaur/LushsaurEntity"));
        patchLushsaurMountSpeed(nodes.get(ROOT + "common/entity/mob/lushsaur/LushsaurEntity"));
        patchLushsaurRunAnimation(nodes.get(ROOT + "client/model/render/entity/mob/LushsaurClient"));
        patchLushsaurRiderRunAnimation(nodes.get(ROOT + "client/animation/player/riding/OnLushsaurPlayerAnimations"));
        patchSculksaurExplosionEffects(nodes.get(ROOT + "client/particle/effect/SculksaurEffects"));
        patchSmoothMoveControl(nodes.get(ROOT + "common/entity/ai/control/SmoothMoveControl"));
        patchSmoothBodyControl(nodes.get(ROOT + "common/entity/ai/control/SmoothBodyControl"));
        patchSmoothLookControl(nodes.get(ROOT + "common/entity/ai/control/SmoothLookControl"));
        patchLushsaurLocalPlayerSprintMixin(nodes.get(ROOT + "mixin/client/LocalPlayerMixin"));
        patchPlayerMixin(nodes.get(ROOT + "mixin/PlayerMixin"));
        removeMethods(nodes.get(ROOT + "mixin/client/LevelRendererMixin"),
            Set.of("archiesDinoMod$onEntityRender"));

        Set<String> roots = new LinkedHashSet<>(ROOT_CLASSES);
        roots.addAll(MIXIN_ROOTS);
        addServiceRoots(allEntries, roots);

        Set<String> retained = dependencyClosure(roots, nodes);
        stripUnreferencedMethods(retained, nodes);
        verifyCommonClientRendererCalls(retained, nodes);
        printPruneDiagnostics(retained, nodes);

        Map<String, byte[]> transformedClasses = new HashMap<>();
        for (String internalName : retained) {
            ClassNode node = nodes.get(internalName);
            if (node == null) continue;
            ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
            node.accept(writer);
            transformedClasses.put(internalName + ".class", writer.toByteArray());
        }

        List<String> missingRoots = new ArrayList<>();
        for (String root : roots) {
            if (!nodes.containsKey(root)) missingRoots.add(root);
        }
        if (!missingRoots.isEmpty()) {
            throw new IllegalStateException("Required class roots not found: " + missingRoots);
        }

        int keptCount = 0;
        int removedCount = 0;
        for (Map.Entry<String, byte[]> entry : allEntries.entrySet()) {
            String name = entry.getKey();
            if (name.endsWith(".class")) {
                String internal = name.substring(0, name.length() - 6);
                if (!retained.contains(internal)) {
                    removedCount++;
                    continue;
                }
                byte[] bytes = transformedClasses.getOrDefault(name, entry.getValue());
                allEntries.put(name, bytes);
                keptCount++;
            }
        }

        try (ZipOutputStream out = new ZipOutputStream(Files.newOutputStream(output))) {
            for (Map.Entry<String, byte[]> entry : allEntries.entrySet()) {
                String name = entry.getKey();
                if (!name.endsWith(".class")) continue;
                if (!retained.contains(name.substring(0, name.length() - 6))) continue;
                ZipEntry ze = new ZipEntry(name);
                ze.setTime(0L);
                out.putNextEntry(ze);
                out.write(entry.getValue());
                out.closeEntry();
            }
            for (Map.Entry<String, byte[]> entry : allEntries.entrySet()) {
                String name = entry.getKey();
                if (name.endsWith(".class")) continue;
                ZipEntry ze = new ZipEntry(name);
                ze.setTime(0L);
                out.putNextEntry(ze);
                out.write(entry.getValue());
                out.closeEntry();
            }
        }

        System.out.println("Class roots: " + roots.size());
        System.out.println("Retained classes: " + keptCount);
        System.out.println("Removed classes: " + removedCount);
        System.out.println("Output: " + output);
    }

    private static void pruneRegistryInitializer(ClassNode owner, Set<String> keepFields) {
        if (owner == null) return;

        Set<String> removedFields = new HashSet<>();
        for (FieldNode field : owner.fields) {
            if (!keepFields.contains(field.name)) removedFields.add(field.name);
        }

        for (MethodNode method : owner.methods) {
            if (!method.name.equals("<clinit>")) continue;

            List<AbstractInsnNode> segment = new ArrayList<>();
            for (AbstractInsnNode insn : method.instructions.toArray()) {
                if (insn instanceof FieldInsnNode field &&
                    field.getOpcode() == Opcodes.PUTSTATIC &&
                    field.owner.equals(owner.name)) {

                    if (removedFields.contains(field.name)) {
                        for (AbstractInsnNode remove : segment) {
                            method.instructions.remove(remove);
                        }
                        method.instructions.remove(field);
                    }
                    segment.clear();
                } else {
                    segment.add(insn);
                }
            }
        }

        owner.fields.removeIf(field -> removedFields.contains(field.name));
    }

    private static void patchNoOpMethod(ClassNode owner, String name, String descriptor) {
        if (owner == null) return;
        for (MethodNode method : owner.methods) {
            if (!method.name.equals(name) || !method.desc.equals(descriptor)) continue;
            clearMethod(method);
            method.instructions.add(new InsnNode(Opcodes.RETURN));
            return;
        }
    }

    private static void patchCommonClient(ClassNode owner) {
        if (owner == null) return;

        for (MethodNode method : owner.methods) {
            if (method.name.equals("init") && method.desc.equals("()V")) {
                clearMethod(method);
                InsnList code = method.instructions;
                code.add(new MethodInsnNode(Opcodes.INVOKESTATIC, ROOT + "client/model/registry/ADEntityModels", "init", "()V", false));
                code.add(new MethodInsnNode(Opcodes.INVOKESTATIC, ROOT + "client/model/registry/ADBlockEntityModels", "init", "()V", false));
                code.add(new MethodInsnNode(Opcodes.INVOKESTATIC, ROOT + "client/animation/player/ADPlayerAnimations", "init", "()V", false));
                code.add(new MethodInsnNode(Opcodes.INVOKESTATIC, ROOT + "client/particle/ADParticleEmitters", "init", "()V", false));
                code.add(new MethodInsnNode(Opcodes.INVOKESTATIC, owner.name, "registerEntityRenderers", "()V", false));
                code.add(new MethodInsnNode(Opcodes.INVOKESTATIC, owner.name, "registerBlockEntityRenderers", "()V", false));
                code.add(new InsnNode(Opcodes.RETURN));
                method.maxLocals = 0;
            }

            if (method.name.equals("registerEntityRenderers") && method.desc.equals("()V")) {
                clearMethod(method);
                addEntityRenderer(method, "LUSHSAUR",
                    ROOT + "client/model/render/entity/mob/LushsaurClient", "createRenderProvider");
                addEntityRenderer(method, "CHORUSOATL",
                    ROOT + "client/model/render/entity/mob/ChorusoatlClient", "createRenderProvider");
                addEntityRenderer(method, "SCULKSAUR",
                    ROOT + "client/model/render/entity/mob/SculksaurClient", "createRenderProvider");

                InsnList code = method.instructions;
                code.add(new FieldInsnNode(Opcodes.GETSTATIC, ROOT + "common/entity/ADEntityTypes",
                    "CHORUSOATL_SPIT", "L" + ROOT + "util/ResourceSupplier;"));
                code.add(new InvokeDynamicInsnNode("m_174009_", "()Lnet/minecraft/client/renderer/entity/EntityRendererProvider;",
                    new Handle(Opcodes.H_INVOKESTATIC, "java/lang/invoke/LambdaMetafactory", "metafactory",
                        "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodHandle;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;", false),
                    Type.getMethodType("(Lnet/minecraft/client/renderer/entity/EntityRendererProvider$Context;)Lnet/minecraft/client/renderer/entity/EntityRenderer;"),
                    new Handle(Opcodes.H_NEWINVOKESPECIAL, "net/minecraft/client/renderer/entity/NoopRenderer", "<init>",
                        "(Lnet/minecraft/client/renderer/entity/EntityRendererProvider$Context;)V", false),
                    Type.getMethodType("(Lnet/minecraft/client/renderer/entity/EntityRendererProvider$Context;)Lnet/minecraft/client/renderer/entity/NoopRenderer;")));
                code.add(new MethodInsnNode(Opcodes.INVOKESTATIC, owner.name, "registerEntityRenderer",
                    "(Ljava/util/function/Supplier;Lnet/minecraft/client/renderer/entity/EntityRendererProvider;)V", false));
                code.add(new InsnNode(Opcodes.RETURN));
                method.maxLocals = 0;
            }

            if (method.name.equals("registerBlockEntityRenderers") && method.desc.equals("()V")) {
                clearMethod(method);
                addBlockEntityRenderer(method, "LUSHSAUR_EGG", "LUSHSAUR_EGG");
                addBlockEntityRenderer(method, "CHORUSOATL_EGG", "CHORUSOATL_EGG");
                method.instructions.add(new InsnNode(Opcodes.RETURN));
                method.maxLocals = 0;
            }
        }
    }

    private static void addEntityRenderer(MethodNode method, String entityField, String rendererOwner, String rendererMethod) {
        InsnList code = method.instructions;
        code.add(new FieldInsnNode(Opcodes.GETSTATIC, ROOT + "common/entity/ADEntityTypes",
            entityField, "L" + ROOT + "util/ResourceSupplier;"));
        code.add(new MethodInsnNode(Opcodes.INVOKESTATIC, rendererOwner, rendererMethod,
            "()Lcom/astemir/astemirlib/re/client/model/render/renderer/AstemirEntityRenderer$Provider;", false));
        code.add(new MethodInsnNode(Opcodes.INVOKESTATIC, ROOT + "client/CommonClient", "registerEntityRenderer",
            "(Ljava/util/function/Supplier;Lnet/minecraft/client/renderer/entity/EntityRendererProvider;)V", false));
    }

    private static void addBlockEntityRenderer(MethodNode method, String blockEntityField, String modelField) {
        InsnList code = method.instructions;
        code.add(new FieldInsnNode(Opcodes.GETSTATIC, ROOT + "common/block/entity/ADBlockEntityTypes",
            blockEntityField, "L" + ROOT + "util/ResourceSupplier;"));
        code.add(new FieldInsnNode(Opcodes.GETSTATIC, ROOT + "client/model/registry/ADBlockEntityModels",
            modelField, "Lcom/astemir/astemirlib/re/client/model/AstemirModel;"));
        code.add(new MethodInsnNode(Opcodes.INVOKESTATIC, ROOT + "client/model/render/block/DinoEggClient",
            "createRenderer", "(Lcom/astemir/astemirlib/re/client/model/AstemirModel;)Lcom/astemir/astemirlib/re/client/model/render/renderer/AstemirBlockEntityRenderer;", false));
        code.add(new MethodInsnNode(Opcodes.INVOKESTATIC, ROOT + "client/CommonClient", "registerBlockEntityRenderer",
            "(L" + ROOT + "util/ResourceSupplier;Lnet/minecraft/client/renderer/blockentity/BlockEntityRenderer;)V", false));
    }

    private static void patchClientModBusRegistrations(ClassNode owner) {
        if (owner == null) return;
        for (MethodNode method : owner.methods) {
            if (method.name.equals("onClientSetup") &&
                method.desc.equals("(Lnet/minecraftforge/fml/event/lifecycle/FMLClientSetupEvent;)V")) {
                clearMethod(method);
                InsnList code = method.instructions;
                code.add(new FieldInsnNode(Opcodes.GETSTATIC, ROOT + "common/menu/ADMenus",
                    "DINO", "L" + ROOT + "util/ResourceSupplier;"));
                code.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, ROOT + "util/ResourceSupplier",
                    "get", "()Ljava/lang/Object;", false));
                code.add(new TypeInsnNode(Opcodes.CHECKCAST, "net/minecraft/world/inventory/MenuType"));
                code.add(new InvokeDynamicInsnNode("m_96214_",
                    "()Lnet/minecraft/client/gui/screens/MenuScreens$ScreenConstructor;",
                    new Handle(Opcodes.H_INVOKESTATIC, "java/lang/invoke/LambdaMetafactory", "metafactory",
                        "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodHandle;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;", false),
                    Type.getMethodType("(Lnet/minecraft/world/inventory/AbstractContainerMenu;Lnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/network/chat/Component;)Lnet/minecraft/client/gui/screens/Screen;"),
                    new Handle(Opcodes.H_NEWINVOKESPECIAL,
                        ROOT + "client/screen/DinoScreen", "<init>",
                        "(L" + ROOT + "common/menu/DinoMenu;Lnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/network/chat/Component;)V",
                        false),
                    Type.getMethodType("(L" + ROOT + "common/menu/DinoMenu;Lnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/network/chat/Component;)L" + ROOT + "client/screen/DinoScreen;")));
                code.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "net/minecraft/client/gui/screens/MenuScreens",
                    "m_96206_", "(Lnet/minecraft/world/inventory/MenuType;Lnet/minecraft/client/gui/screens/MenuScreens$ScreenConstructor;)V", false));
                code.add(new InsnNode(Opcodes.RETURN));
                method.maxLocals = 1;
            } else if (method.name.equals("registerLayers") &&
                method.desc.equals("(Lnet/minecraftforge/client/event/EntityRenderersEvent$RegisterLayerDefinitions;)V")) {
                clearMethod(method);
                method.instructions.add(new InsnNode(Opcodes.RETURN));
                method.maxLocals = 1;
            }
        }
    }

    private static void patchClientModBus(ClassNode owner) {
        if (owner == null) return;
        for (MethodNode method : owner.methods) {
            if (method.name.equals("modifyBakingResult") &&
                method.desc.equals("(Lnet/minecraftforge/client/event/ModelEvent$ModifyBakingResult;)V")) {
                clearMethod(method);
                method.instructions.add(new InsnNode(Opcodes.RETURN));
                method.maxLocals = 2;
            }
        }
    }

    private static void patchSpawnPlacements(ClassNode owner) {
        if (owner == null) return;
        for (MethodNode method : owner.methods) {
            if (!method.name.equals("registerSpawnPlacements") ||
                !method.desc.equals("(Lnet/minecraftforge/event/entity/SpawnPlacementRegisterEvent;)V")) continue;
            clearMethod(method);
            InsnList code = method.instructions;
            for (String entity : List.of("LUSHSAUR", "CHORUSOATL", "SCULKSAUR")) {
                code.add(new VarInsnNode(Opcodes.ALOAD, 0));
                code.add(new FieldInsnNode(Opcodes.GETSTATIC, ROOT + "common/entity/ADEntityTypes",
                    entity, "L" + ROOT + "util/ResourceSupplier;"));
                code.add(new MethodInsnNode(Opcodes.INVOKESTATIC, owner.name, "ground",
                    "(Lnet/minecraftforge/event/entity/SpawnPlacementRegisterEvent;Ljava/util/function/Supplier;)V", false));
            }
            code.add(new InsnNode(Opcodes.RETURN));
            method.maxLocals = 1;
        }
    }

    private static void patchParticleEmitters(ClassNode owner) {
        if (owner == null) return;
        for (MethodNode method : owner.methods) {
            if (method.name.equals("<clinit>")) {
                clearMethod(method);
                addParticleEmitter(method, "CHORUSOATL_SPIT_0", "chorusoatl/spit/chorusoatl_spit");
                addParticleEmitter(method, "CHORUSOATL_SPIT_1", "chorusoatl/spit/chorusoatl_spit_2");
                addParticleEmitter(method, "CHORUSOATL_SPIT_AOE", "chorusoatl/spit/chorusoatl_spit_aoe");
                addParticleEmitter(method, "CHORUSOATL_SPIT_PROJECTILE", "chorusoatl/spit/chorusoatl_spit_projectile");
                method.instructions.add(new InsnNode(Opcodes.RETURN));
                method.maxLocals = 0;
            } else if (method.name.equals("init") && method.desc.equals("()V")) {
                clearMethod(method);
                InsnList code = method.instructions;
                for (String effect : List.of("LushsaurEffects", "ChorusoatlEffects", "SculksaurEffects")) {
                    code.add(new MethodInsnNode(Opcodes.INVOKESTATIC, ROOT + "client/particle/effect/" + effect,
                        "init", "()V", false));
                }
                code.add(new InsnNode(Opcodes.RETURN));
                method.maxLocals = 0;
            }
        }
    }

    private static void addParticleEmitter(MethodNode method, String fieldName, String path) {
        InsnList code = method.instructions;
        code.add(new FieldInsnNode(Opcodes.GETSTATIC, ROOT + "common/particle/ADParticles",
            fieldName, "L" + ROOT + "util/ResourceSupplier;"));
        code.add(new LdcInsnNode(path));
        code.add(new MethodInsnNode(Opcodes.INVOKESTATIC, ROOT + "client/particle/ADParticleEmitters",
            "register", "(L" + ROOT + "util/ResourceSupplier;Ljava/lang/String;)Lcom/astemir/astemirlib/re/client/particle/ParticleEmitterConfig;", false));
        code.add(new InsnNode(Opcodes.POP));
    }

    private static void patchTemptItems(ClassNode owner, String retainedItemField) {
        if (owner == null) return;
        int replaced = 0;
        for (MethodNode method : owner.methods) {
            for (AbstractInsnNode insn : method.instructions.toArray()) {
                if (insn instanceof FieldInsnNode field &&
                    field.getOpcode() == Opcodes.GETSTATIC &&
                    field.owner.equals(ROOT + "common/items/ADItems") &&
                    field.name.equals("AMBER")) {
                    method.instructions.set(field, new FieldInsnNode(Opcodes.GETSTATIC,
                        ROOT + "common/items/ADItems", retainedItemField, "L" + ROOT + "util/ResourceSupplier;"));
                    replaced++;
                }
            }
        }
        if (replaced == 0) {
            throw new IllegalStateException("No Amber temptation reference found in " + owner.name);
        }
    }

    private static final String ENTITY_SETTINGS =
        ROOT + "server/ServerConfig$EntitySettings";
    private static final String TAMEABLE_SETTINGS =
        ROOT + "common/entity/base/AbstractComplexMob$TameableDinoSettings";
    private static final String SCULK_SETTINGS =
        ROOT + "util/handlers/SculksaurHandler$SculksaurSettings";
    private static final String ENTITY_SETTINGS_CTOR =
        "(L" + TAMEABLE_SETTINGS + ";L" + TAMEABLE_SETTINGS + ";L" + TAMEABLE_SETTINGS +
        ";L" + TAMEABLE_SETTINGS + ";L" + TAMEABLE_SETTINGS + ";L" + TAMEABLE_SETTINGS +
        ";L" + SCULK_SETTINGS + ";L" + TAMEABLE_SETTINGS + ";)V";

    private static void patchEntitySettings(ClassNode owner) {
        if (owner == null) throw new IllegalStateException("ServerConfig EntitySettings class is missing");
        String oldArchieType = "L" + ROOT + "common/entity/mob/archie/ArchieEntity$ArchieSettings;";
        String newGenericType = "L" + TAMEABLE_SETTINGS + ";";

        for (FieldNode field : owner.fields) {
            if (field.name.equals("archie")) {
                field.desc = newGenericType;
                field.signature = null;
            }
        }
        if (owner.recordComponents != null) {
            for (RecordComponentNode component : owner.recordComponents) {
                if (component.name.equals("archie")) {
                    component.descriptor = newGenericType;
                    component.signature = null;
                }
            }
        }

        for (MethodNode method : owner.methods) {
            if (method.name.equals("<init>")) {
                method.desc = method.desc.replace(oldArchieType, newGenericType);
                method.signature = null;
            } else if (method.name.equals("archie")) {
                method.desc = "()" + newGenericType;
                method.signature = null;
            } else if (method.name.equals("toTag")) {
                clearMethod(method);
                InsnList code = method.instructions;
                code.add(new TypeInsnNode(Opcodes.NEW, "net/minecraft/nbt/CompoundTag"));
                code.add(new InsnNode(Opcodes.DUP));
                code.add(new MethodInsnNode(Opcodes.INVOKESPECIAL, "net/minecraft/nbt/CompoundTag",
                    "<init>", "()V", false));
                code.add(new VarInsnNode(Opcodes.ASTORE, 1));
                addEntitySettingEncode(code, "lushsaur", "lushsaur", TAMEABLE_SETTINGS);
                addEntitySettingEncode(code, "chorusoatl", "chorusoatl", TAMEABLE_SETTINGS);
                addEntitySettingEncode(code, "sculksaur", "sculksaur", SCULK_SETTINGS);
                code.add(new VarInsnNode(Opcodes.ALOAD, 1));
                code.add(new InsnNode(Opcodes.ARETURN));
                method.maxLocals = 2;
            } else if (method.name.equals("fromTag") &&
                method.desc.equals("(Lnet/minecraft/nbt/CompoundTag;L" + ENTITY_SETTINGS + ";)L" + ENTITY_SETTINGS + ";")) {
                clearMethod(method);
                InsnList code = method.instructions;
                code.add(new TypeInsnNode(Opcodes.NEW, ENTITY_SETTINGS));
                code.add(new InsnNode(Opcodes.DUP));
                code.add(new InsnNode(Opcodes.ACONST_NULL));
                addEntitySettingDecode(code, "lushsaur", "lushsaur", TAMEABLE_SETTINGS);
                code.add(new InsnNode(Opcodes.ACONST_NULL));
                code.add(new InsnNode(Opcodes.ACONST_NULL));
                addEntitySettingDecode(code, "chorusoatl", "chorusoatl", TAMEABLE_SETTINGS);
                code.add(new InsnNode(Opcodes.ACONST_NULL));
                addEntitySettingDecode(code, "sculksaur", "sculksaur", SCULK_SETTINGS);
                code.add(new InsnNode(Opcodes.ACONST_NULL));
                code.add(new MethodInsnNode(Opcodes.INVOKESPECIAL, ENTITY_SETTINGS,
                    "<init>", ENTITY_SETTINGS_CTOR, false));
                code.add(new InsnNode(Opcodes.ARETURN));
                method.maxLocals = 2;
            } else if (method.name.equals("equals") || method.name.equals("hashCode") ||
                method.name.equals("toString")) {
                clearMethod(method);
                owner.methods.remove(method);
                break;
            }
        }

        for (MethodNode method : owner.methods) {
            for (AbstractInsnNode insn : method.instructions.toArray()) {
                if (insn instanceof FieldInsnNode field &&
                    field.owner.equals(owner.name) && field.name.equals("archie")) {
                    field.desc = newGenericType;
                }
            }
        }
    }

    private static void addEntitySettingEncode(
        InsnList code, String tagKey, String fieldName, String valueType
    ) {
        code.add(new VarInsnNode(Opcodes.ALOAD, 1));
        code.add(new LdcInsnNode(tagKey));
        String codecOwner = valueType.equals(SCULK_SETTINGS) ? SCULK_SETTINGS : TAMEABLE_SETTINGS;
        code.add(new FieldInsnNode(Opcodes.GETSTATIC, codecOwner, "CODEC", "Lcom/mojang/serialization/Codec;"));
        code.add(new VarInsnNode(Opcodes.ALOAD, 0));
        code.add(new FieldInsnNode(Opcodes.GETFIELD, ENTITY_SETTINGS, fieldName, "L" + valueType + ";"));
        code.add(new MethodInsnNode(Opcodes.INVOKESTATIC, ROOT + "util/ADConfig", "encode",
            "(Lcom/mojang/serialization/Codec;Ljava/lang/Object;)Lnet/minecraft/nbt/Tag;", false));
        code.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "net/minecraft/nbt/CompoundTag", "m_128365_",
            "(Ljava/lang/String;Lnet/minecraft/nbt/Tag;)Lnet/minecraft/nbt/Tag;", false));
        code.add(new InsnNode(Opcodes.POP));
    }

    private static void addEntitySettingDecode(
        InsnList code, String tagKey, String fieldName, String valueType
    ) {
        String codecOwner = valueType.equals(SCULK_SETTINGS) ? SCULK_SETTINGS : TAMEABLE_SETTINGS;
        code.add(new FieldInsnNode(Opcodes.GETSTATIC, codecOwner, "CODEC", "Lcom/mojang/serialization/Codec;"));
        code.add(new VarInsnNode(Opcodes.ALOAD, 0));
        code.add(new LdcInsnNode(tagKey));
        code.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "net/minecraft/nbt/CompoundTag", "m_128423_",
            "(Ljava/lang/String;)Lnet/minecraft/nbt/Tag;", false));
        code.add(new VarInsnNode(Opcodes.ALOAD, 1));
        code.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, ENTITY_SETTINGS, fieldName,
            "()L" + valueType + ";", false));
        code.add(new MethodInsnNode(Opcodes.INVOKESTATIC, ROOT + "util/ADConfig", "decode",
            "(Lcom/mojang/serialization/Codec;Lnet/minecraft/nbt/Tag;Ljava/lang/Object;)Ljava/lang/Object;", false));
        code.add(new TypeInsnNode(Opcodes.CHECKCAST, valueType));
    }

    private static void patchServerConfig(ClassNode owner) {
        if (owner == null) throw new IllegalStateException("ServerConfig class is missing");
        owner.fields.removeIf(field -> field.name.equals("weaponSettings") || field.name.equals("armorSettings"));
        owner.methods.removeIf(method -> method.name.equals("getWeaponSettings") || method.name.equals("getArmorSettings"));

        for (MethodNode method : owner.methods) {
            if (method.name.equals("<init>")) {
                replaceExcludedEntityDefaults(method);
                for (AbstractInsnNode insn : method.instructions.toArray()) {
                    if (insn instanceof MethodInsnNode call && call.owner.equals(ENTITY_SETTINGS) &&
                        call.name.equals("<init>")) {
                        call.desc = ENTITY_SETTINGS_CTOR;
                    }
                }
                removeConstructorFieldInit(method, owner.name, "weaponSettings");
                removeConstructorFieldInit(method, owner.name, "armorSettings");
            } else if (method.name.equals("toTag") && method.desc.equals("()Lnet/minecraft/nbt/CompoundTag;")) {
                clearMethod(method);
                InsnList code = method.instructions;
                code.add(new TypeInsnNode(Opcodes.NEW, "net/minecraft/nbt/CompoundTag"));
                code.add(new InsnNode(Opcodes.DUP));
                code.add(new MethodInsnNode(Opcodes.INVOKESPECIAL, "net/minecraft/nbt/CompoundTag", "<init>", "()V", false));
                code.add(new VarInsnNode(Opcodes.ASTORE, 1));
                code.add(new VarInsnNode(Opcodes.ALOAD, 0));
                code.add(new FieldInsnNode(Opcodes.GETFIELD, owner.name, "entitySettings",
                    "L" + ROOT + "util/ADConfig$Setting;"));
                code.add(new VarInsnNode(Opcodes.ALOAD, 1));
                code.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, ROOT + "util/ADConfig$Setting", "write",
                    "(Lnet/minecraft/nbt/CompoundTag;)V", false));
                code.add(new VarInsnNode(Opcodes.ALOAD, 1));
                code.add(new InsnNode(Opcodes.ARETURN));
                method.maxLocals = 2;
            } else if (method.name.equals("fromTag") &&
                method.desc.equals("(Lnet/minecraft/nbt/CompoundTag;)V")) {
                clearMethod(method);
                InsnList code = method.instructions;
                code.add(new VarInsnNode(Opcodes.ALOAD, 0));
                code.add(new FieldInsnNode(Opcodes.GETFIELD, owner.name, "entitySettings",
                    "L" + ROOT + "util/ADConfig$Setting;"));
                code.add(new VarInsnNode(Opcodes.ALOAD, 1));
                code.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, ROOT + "util/ADConfig$Setting", "read",
                    "(Lnet/minecraft/nbt/CompoundTag;)V", false));
                code.add(new InsnNode(Opcodes.RETURN));
                method.maxLocals = 2;
            }
        }
    }

    private static void replaceExcludedEntityDefaults(MethodNode method) {
        Set<String> excludedOwners = Set.of(
            ROOT + "common/entity/mob/archie/ArchieEntity",
            ROOT + "common/entity/mob/obsidiceratops/ObsidiceratopsEntity",
            ROOT + "common/entity/mob/therimoss/TherimossEntity",
            ROOT + "common/entity/mob/sculkraptor/SculkraptorEntity",
            ROOT + "common/entity/mob/coralosaur/CoralosaurEntity"
        );
        for (AbstractInsnNode insn : method.instructions.toArray()) {
            if (insn instanceof FieldInsnNode field &&
                field.getOpcode() == Opcodes.GETSTATIC &&
                field.name.equals("DEFAULT_STATS") &&
                excludedOwners.contains(field.owner)) {
                method.instructions.set(field, new InsnNode(Opcodes.ACONST_NULL));
            }
        }
    }

    private static void removeConstructorFieldInit(MethodNode method, String owner, String fieldName) {
        FieldInsnNode put = null;
        for (AbstractInsnNode insn : method.instructions.toArray()) {
            if (insn instanceof FieldInsnNode field &&
                field.getOpcode() == Opcodes.PUTFIELD &&
                field.owner.equals(owner) && field.name.equals(fieldName)) {
                put = field;
                break;
            }
        }
        if (put == null) return;

        AbstractInsnNode start = put;
        for (AbstractInsnNode cursor = put.getPrevious(); cursor != null; cursor = cursor.getPrevious()) {
            if (cursor instanceof VarInsnNode var && var.getOpcode() == Opcodes.ALOAD && var.var == 0) {
                start = cursor;
                break;
            }
        }
        removeInclusive(method.instructions, start, put);
    }

    private static void patchDazedEffect(ClassNode owner) {
        if (owner == null) return;
        for (MethodNode method : owner.methods) {
            if (!method.name.equals("m_6385_")) continue;

            TypeInsnNode check = null;
            JumpInsnNode skip = null;
            MethodInsnNode soundCall = null;
            for (AbstractInsnNode insn : method.instructions.toArray()) {
                if (insn instanceof TypeInsnNode type &&
                    type.getOpcode() == Opcodes.INSTANCEOF &&
                    type.desc.equals("net/minecraft/server/level/ServerPlayer")) {
                    check = type;
                }
                if (check != null && skip == null && insn instanceof JumpInsnNode jump &&
                    jump.getOpcode() == Opcodes.IFEQ) {
                    skip = jump;
                }
                if (skip != null && insn instanceof MethodInsnNode call &&
                    call.owner.equals("net/minecraft/server/level/ServerPlayer") &&
                    call.name.equals("m_6330_")) {
                    soundCall = call;
                    break;
                }
            }
            if (check != null && skip != null && soundCall != null) {
                removeInclusive(method.instructions, check, soundCall);
            }
        }
    }

    private static void patchCommonClass(ClassNode owner) {
        if (owner == null) return;
        for (MethodNode method : owner.methods) {
            if (!method.name.equals("init") || !method.desc.equals("()V")) continue;
            for (AbstractInsnNode insn : method.instructions.toArray()) {
                if (insn instanceof MethodInsnNode call &&
                    call.getOpcode() == Opcodes.INVOKESTATIC &&
                    call.owner.equals(ROOT + "common/recipe/ADRecipeTypes") &&
                    call.name.equals("init")) {
                    method.instructions.remove(call);
                } else if (insn instanceof MethodInsnNode call &&
                    call.getOpcode() == Opcodes.INVOKESTATIC &&
                    call.owner.equals(ROOT + "common/recipe/ADRecipeSerializers") &&
                    call.name.equals("init")) {
                    method.instructions.remove(call);
                }
            }
        }
    }

    private static void patchPlayerAnimations(ClassNode owner) {
        if (owner == null) return;
        for (MethodNode method : owner.methods) {
            if (!method.name.equals("init") || !method.desc.equals("()V")) continue;
            clearMethod(method);
            InsnList code = method.instructions;
            code.add(new InsnNode(Opcodes.ICONST_1));
            code.add(new MethodInsnNode(Opcodes.INVOKESTATIC,
                ROOT + "client/animation/player/riding/OnLushsaurPlayerAnimations",
                "init", "(I)V", false));
            code.add(new InsnNode(Opcodes.ICONST_1));
            code.add(new MethodInsnNode(Opcodes.INVOKESTATIC,
                ROOT + "client/animation/player/riding/OnChorusoatlPlayerAnimations",
                "init", "(I)V", false));
            code.add(new InsnNode(Opcodes.RETURN));
            method.maxLocals = 0;
        }
    }

    private static void patchCreativeTab(ClassNode owner, ClassNode items) {
        if (owner == null || items == null) return;

        Map<String, String> itemDescriptors = new HashMap<>();
        for (FieldNode field : items.fields) {
            itemDescriptors.put(field.name, field.desc);
        }

        String outputOwner = "net/minecraft/world/item/CreativeModeTab$Output";
        String acceptName = "m_246326_";
        String acceptDesc = "(Lnet/minecraft/world/level/ItemLike;)V";

        for (MethodNode method : owner.methods) {
            if (!method.desc.contains("CreativeModeTab$Output")) continue;

            for (AbstractInsnNode insn : method.instructions.toArray()) {
                if (insn instanceof MethodInsnNode call &&
                    call.owner.equals(outputOwner) &&
                    (call.name.equals("m_246326_") || call.name.equals("accept"))) {
                    acceptName = call.name;
                    acceptDesc = call.desc;
                    break;
                }
            }

            clearMethod(method);
            for (String itemName : CREATIVE_ITEMS) {
                String descriptor = itemDescriptors.get(itemName);
                if (descriptor == null) {
                    throw new IllegalStateException("Creative tab item field missing: " + itemName);
                }
                InsnList code = method.instructions;
                code.add(new VarInsnNode(Opcodes.ALOAD, 1));
                code.add(new FieldInsnNode(Opcodes.GETSTATIC, items.name, itemName, descriptor));
                code.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, ROOT + "util/ResourceSupplier",
                    "get", "()Ljava/lang/Object;", false));
                code.add(new TypeInsnNode(Opcodes.CHECKCAST, "net/minecraft/world/level/ItemLike"));
                code.add(new MethodInsnNode(Opcodes.INVOKEINTERFACE, outputOwner, acceptName, acceptDesc, true));
            }
            method.instructions.add(new InsnNode(Opcodes.RETURN));
            method.maxLocals = 2;
        }

        for (MethodNode method : owner.methods) {
            if (method.desc.equals("()Lnet/minecraft/world/item/ItemStack;")) {
                clearMethod(method);
                InsnList code = method.instructions;
                code.add(new TypeInsnNode(Opcodes.NEW, "net/minecraft/world/item/ItemStack"));
                code.add(new InsnNode(Opcodes.DUP));
                code.add(new FieldInsnNode(Opcodes.GETSTATIC, items.name, "LUSH_CORE",
                    itemDescriptors.get("LUSH_CORE")));
                code.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, ROOT + "util/ResourceSupplier",
                    "get", "()Ljava/lang/Object;", false));
                code.add(new TypeInsnNode(Opcodes.CHECKCAST, "net/minecraft/world/level/ItemLike"));
                code.add(new MethodInsnNode(Opcodes.INVOKESPECIAL, "net/minecraft/world/item/ItemStack",
                    "<init>", "(Lnet/minecraft/world/level/ItemLike;)V", false));
                code.add(new InsnNode(Opcodes.ARETURN));
                method.maxLocals = 0;
            }
        }
    }

    private static void patchAbstractArrowMixin(ClassNode owner) {
        if (owner == null) return;

        owner.interfaces.removeIf(name -> name.equals(ROOT + "util/IModifiedArrow"));
        owner.fields.removeIf(field -> field.name.equals("archies_Dino_Mod$RIB_BOW_ARROW"));

        owner.methods.removeIf(method ->
            method.name.equals("<clinit>") ||
            method.name.equals("onDefineSynchedData") ||
            method.name.equals("onGetWaterInertia") ||
            method.name.equals("archiesDinoMod$setShotFromRibBow") ||
            method.name.equals("onAddAdditionalSaveData") ||
            method.name.equals("onReadAdditionalSaveData")
        );

        for (MethodNode method : owner.methods) {
            if (!method.name.equals("onTick")) continue;
            clearMethod(method);
            LabelNode done = new LabelNode();
            InsnList code = method.instructions;
            code.add(new VarInsnNode(Opcodes.ALOAD, 0));
            code.add(new TypeInsnNode(Opcodes.CHECKCAST, "net/minecraft/world/entity/projectile/AbstractArrow"));
            code.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL,
                "net/minecraft/world/entity/projectile/AbstractArrow", "m_9236_",
                "()Lnet/minecraft/world/level/Level;", false));
            code.add(new FieldInsnNode(Opcodes.GETFIELD, "net/minecraft/world/level/Level", "f_46443_", "Z"));
            code.add(new JumpInsnNode(Opcodes.IFEQ, done));
            code.add(new VarInsnNode(Opcodes.ALOAD, 0));
            code.add(new TypeInsnNode(Opcodes.CHECKCAST, "net/minecraft/world/entity/projectile/AbstractArrow"));
            code.add(new MethodInsnNode(Opcodes.INVOKESTATIC,
                ROOT + "util/ClientUtil", "handleArrowTick",
                "(Lnet/minecraft/world/entity/projectile/AbstractArrow;)V", false));
            code.add(done);
            code.add(new InsnNode(Opcodes.RETURN));
            method.maxLocals = 2;
        }
    }

    private static void patchLushsaurControllingPassenger(ClassNode owner) {
        if (owner == null) throw new IllegalStateException("LushsaurEntity class is missing");

        String descriptor = "()Lnet/minecraft/world/entity/LivingEntity;";
        owner.methods.removeIf(method -> method.name.equals("m_6688_") && method.desc.equals(descriptor));

        MethodNode method = new MethodNode(
            Opcodes.ACC_PUBLIC,
            "m_6688_",
            descriptor,
            null,
            null
        );
        InsnList code = method.instructions;
        LabelNode unsaddled = new LabelNode();
        LabelNode noController = new LabelNode();

        code.add(new VarInsnNode(Opcodes.ALOAD, 0));
        code.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL,
            ROOT + "common/entity/base/AbstractComplexMount", "m_6254_", "()Z", false));
        code.add(new JumpInsnNode(Opcodes.IFEQ, unsaddled));
        code.add(new VarInsnNode(Opcodes.ALOAD, 0));
        code.add(new MethodInsnNode(Opcodes.INVOKESPECIAL,
            ROOT + "common/entity/base/AbstractComplexMount", "m_6688_", descriptor, false));
        code.add(new InsnNode(Opcodes.ARETURN));

        code.add(unsaddled);
        code.add(new FrameNode(Opcodes.F_FULL, 1,
            new Object[] { owner.name }, 0, new Object[0]));
        code.add(new VarInsnNode(Opcodes.ALOAD, 0));
        code.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, owner.name, "isAdult", "()Z", false));
        code.add(new JumpInsnNode(Opcodes.IFEQ, noController));
        code.add(new VarInsnNode(Opcodes.ALOAD, 0));
        code.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, owner.name, "isSitting", "()Z", false));
        code.add(new JumpInsnNode(Opcodes.IFNE, noController));
        code.add(new VarInsnNode(Opcodes.ALOAD, 0));
        code.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL,
            ROOT + "common/entity/base/AbstractComplexMount", "m_146895_",
            "()Lnet/minecraft/world/entity/Entity;", false));
        code.add(new VarInsnNode(Opcodes.ASTORE, 1));
        code.add(new VarInsnNode(Opcodes.ALOAD, 1));
        code.add(new TypeInsnNode(Opcodes.INSTANCEOF, "net/minecraft/world/entity/player/Player"));
        code.add(new JumpInsnNode(Opcodes.IFEQ, noController));
        code.add(new VarInsnNode(Opcodes.ALOAD, 0));
        code.add(new VarInsnNode(Opcodes.ALOAD, 1));
        code.add(new TypeInsnNode(Opcodes.CHECKCAST, "net/minecraft/world/entity/player/Player"));
        code.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, owner.name, "isOwnedBy",
            "(Lnet/minecraft/world/entity/LivingEntity;)Z", false));
        code.add(new JumpInsnNode(Opcodes.IFEQ, noController));
        code.add(new VarInsnNode(Opcodes.ALOAD, 1));
        code.add(new TypeInsnNode(Opcodes.CHECKCAST, "net/minecraft/world/entity/LivingEntity"));
        code.add(new InsnNode(Opcodes.ARETURN));

        code.add(noController);
        code.add(new FrameNode(Opcodes.F_FULL, 1,
            new Object[] { owner.name }, 0, new Object[0]));
        code.add(new InsnNode(Opcodes.ACONST_NULL));
        code.add(new InsnNode(Opcodes.ARETURN));

        method.maxLocals = 2;
        method.maxStack = 2;
        owner.methods.add(method);
    }

    private static void patchLushsaurCanSprint(ClassNode owner) {
        if (owner == null) throw new IllegalStateException("LushsaurEntity class is missing");

        String descriptor = "()Z";
        owner.methods.removeIf(method -> method.name.equals("m_264410_") && method.desc.equals(descriptor));

        MethodNode method = new MethodNode(
            Opcodes.ACC_PUBLIC,
            "m_264410_",
            descriptor,
            null,
            null
        );
        method.instructions.add(new InsnNode(Opcodes.ICONST_1));
        method.instructions.add(new InsnNode(Opcodes.IRETURN));
        method.maxStack = 1;
        method.maxLocals = 1;
        owner.methods.add(method);
    }

    private static void patchLushsaurMountSpeed(ClassNode owner) {
        if (owner == null) throw new IllegalStateException("LushsaurEntity class is missing");

        String descriptor = "(Lnet/minecraft/world/entity/PathfinderMob;F)F";
        MethodNode method = owner.methods.stream()
            .filter(candidate -> candidate.name.equals("lambda$createMount$21") &&
                candidate.desc.equals(descriptor))
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("Lushsaur mount speed property is missing"));

        clearMethod(method);
        InsnList code = method.instructions;

        code.add(new VarInsnNode(Opcodes.ALOAD, 1));
        code.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL,
            "net/minecraft/world/entity/PathfinderMob", "m_6688_",
            "()Lnet/minecraft/world/entity/LivingEntity;", false));
        code.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL,
            "net/minecraft/world/entity/LivingEntity", "m_20142_", "()Z", false));
        code.add(new InsnNode(Opcodes.I2F));
        code.add(new LdcInsnNode(0.5f));
        code.add(new InsnNode(Opcodes.FMUL));
        code.add(new LdcInsnNode(0.5f));
        code.add(new InsnNode(Opcodes.FADD));

        code.add(new VarInsnNode(Opcodes.ALOAD, 0));
        code.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, owner.name,
            "getSpeedBoostTicks", "()I", false));
        code.add(new MethodInsnNode(Opcodes.INVOKESTATIC,
            "java/lang/Integer", "signum", "(I)I", false));
        code.add(new InsnNode(Opcodes.ICONST_0));
        code.add(new MethodInsnNode(Opcodes.INVOKESTATIC,
            "java/lang/Math", "max", "(II)I", false));
        code.add(new InsnNode(Opcodes.I2F));
        code.add(new InsnNode(Opcodes.FCONST_1));
        code.add(new InsnNode(Opcodes.FADD));
        code.add(new InsnNode(Opcodes.FMUL));
        code.add(new InsnNode(Opcodes.FRETURN));

        method.maxStack = 3;
        method.maxLocals = 3;
    }

    private static void patchLushsaurRunAnimation(ClassNode owner) {
        if (owner == null) throw new IllegalStateException("LushsaurClient class is missing");

        String entityInternal = ROOT + "common/entity/mob/lushsaur/LushsaurEntity";
        String entityDescriptor = "L" + entityInternal + ";";
        String locomotionInternal = "com/astemir/astemirlib/re/common/world/entity/EntityLocomotion";
        String conditionInternal = "com/astemir/astemirlib/re/core/function/Condition";
        String conditionDescriptor = "()L" + conditionInternal + ";";
        String helperName = "archiesDinoMod$shouldUseRunAnimation";
        String helperDescriptor = "(" + entityDescriptor + ")Z";

        owner.methods.removeIf(method ->
            method.name.equals(helperName) && method.desc.equals(helperDescriptor));

        MethodNode helper = new MethodNode(
            Opcodes.ACC_PRIVATE | Opcodes.ACC_STATIC,
            helperName,
            helperDescriptor,
            null,
            null
        );
        InsnList helperCode = helper.instructions;
        LabelNode useLocomotion = new LabelNode();

        helperCode.add(new VarInsnNode(Opcodes.ALOAD, 0));
        helperCode.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL,
            entityInternal, "m_6688_", "()Lnet/minecraft/world/entity/LivingEntity;", false));
        helperCode.add(new VarInsnNode(Opcodes.ASTORE, 1));
        helperCode.add(new VarInsnNode(Opcodes.ALOAD, 1));
        helperCode.add(new JumpInsnNode(Opcodes.IFNULL, useLocomotion));
        helperCode.add(new VarInsnNode(Opcodes.ALOAD, 1));
        helperCode.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL,
            "net/minecraft/world/entity/LivingEntity", "m_20142_", "()Z", false));
        helperCode.add(new InsnNode(Opcodes.IRETURN));
        helperCode.add(useLocomotion);
        helperCode.add(new FrameNode(Opcodes.F_FULL, 2,
            new Object[] { entityInternal, "net/minecraft/world/entity/LivingEntity" },
            0, new Object[0]));
        helperCode.add(new VarInsnNode(Opcodes.ALOAD, 0));
        helperCode.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL,
            entityInternal, "getLocomotion",
            "()L" + locomotionInternal + ";", false));
        helperCode.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL,
            locomotionInternal, "whenRunning", conditionDescriptor, false));
        helperCode.add(new MethodInsnNode(Opcodes.INVOKEINTERFACE,
            conditionInternal, "check", "()Z", true));
        helperCode.add(new InsnNode(Opcodes.IRETURN));
        helper.maxStack = 1;
        helper.maxLocals = 2;
        owner.methods.add(helper);

        String buildDescriptor = "(" + entityDescriptor +
            ")Lcom/astemir/astemirlib/re/client/model/core/animation/node/LayerNode$Definition;";
        MethodNode build = owner.methods.stream()
            .filter(method -> method.name.equals("buildLocomotionState") &&
                method.desc.equals(buildDescriptor))
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("Lushsaur locomotion animation method is missing"));

        Handle lambdaMetafactory = new Handle(
            Opcodes.H_INVOKESTATIC,
            "java/lang/invoke/LambdaMetafactory",
            "metafactory",
            "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;" +
                "Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodHandle;" +
                "Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;",
            false
        );
        int patchedConditions = 0;
        for (AbstractInsnNode insn : build.instructions.toArray()) {
            if (!(insn instanceof MethodInsnNode call) ||
                call.getOpcode() != Opcodes.INVOKEVIRTUAL ||
                !call.owner.equals(locomotionInternal) ||
                !call.name.equals("whenRunning") ||
                !call.desc.equals(conditionDescriptor)) continue;

            AbstractInsnNode receiver = call.getPrevious();
            if (!(receiver instanceof VarInsnNode load) ||
                load.getOpcode() != Opcodes.ALOAD || load.var != 2) {
                throw new IllegalStateException("Unexpected receiver before Lushsaur whenRunning condition");
            }

            build.instructions.set(receiver, new VarInsnNode(Opcodes.ALOAD, 1));
            build.instructions.set(call, new InvokeDynamicInsnNode(
                "check",
                "(" + entityDescriptor + ")L" + conditionInternal + ";",
                lambdaMetafactory,
                Type.getMethodType("()Z"),
                new Handle(Opcodes.H_INVOKESTATIC, owner.name, helperName, helperDescriptor, false),
                Type.getMethodType("()Z")
            ));
            patchedConditions++;
        }

        if (patchedConditions != 2) {
            throw new IllegalStateException("Expected to replace 2 Lushsaur run conditions, replaced " +
                patchedConditions);
        }
    }

    private static void patchLushsaurRiderRunAnimation(ClassNode owner) {
        if (owner == null) throw new IllegalStateException("OnLushsaurPlayerAnimations class is missing");

        String entityInternal = ROOT + "common/entity/mob/lushsaur/LushsaurEntity";
        String descriptor = "(L" + entityInternal + ";)Z";
        MethodNode method = owner.methods.stream()
            .filter(candidate -> candidate.name.equals("lambda$init$11") &&
                candidate.desc.equals(descriptor))
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("Lushsaur rider run-animation predicate is missing"));

        clearMethod(method);
        InsnList code = method.instructions;
        LabelNode noController = new LabelNode();

        code.add(new VarInsnNode(Opcodes.ALOAD, 0));
        code.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL,
            entityInternal, "m_6688_", "()Lnet/minecraft/world/entity/LivingEntity;", false));
        code.add(new VarInsnNode(Opcodes.ASTORE, 1));
        code.add(new VarInsnNode(Opcodes.ALOAD, 1));
        code.add(new JumpInsnNode(Opcodes.IFNULL, noController));
        code.add(new VarInsnNode(Opcodes.ALOAD, 1));
        code.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL,
            "net/minecraft/world/entity/LivingEntity", "m_20142_", "()Z", false));
        code.add(new InsnNode(Opcodes.IRETURN));
        code.add(noController);
        code.add(new FrameNode(Opcodes.F_FULL, 2,
            new Object[] { entityInternal, "net/minecraft/world/entity/LivingEntity" },
            0, new Object[0]));
        code.add(new InsnNode(Opcodes.ICONST_0));
        code.add(new InsnNode(Opcodes.IRETURN));

        method.maxStack = 1;
        method.maxLocals = 2;
    }

    private static void patchSculksaurExplosionEffects(ClassNode owner) {
        if (owner == null) throw new IllegalStateException("SculksaurEffects class is missing");

        String contextInternal =
            "com/astemir/astemirlib/re/client/model/core/animation/event/AnimationEvent$Context";
        String entityInternal = "net/minecraft/world/entity/Entity";
        String levelInternal = "net/minecraft/world/level/Level";

        String finalDescriptor = "(L" + contextInternal + ";)V";
        MethodNode finalEvent = owner.methods.stream()
            .filter(method -> method.name.equals("lambda$explosionFinalEvent$12") &&
                method.desc.equals(finalDescriptor))
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("Sculksaur final Sonic Boom event is missing"));

        clearMethod(finalEvent);
        InsnList finalCode = finalEvent.instructions;
        finalCode.add(new VarInsnNode(Opcodes.ALOAD, 0));
        finalCode.add(new MethodInsnNode(Opcodes.INVOKESTATIC,
            owner.name, "archiesDinoMod$spawnSculksaurSonicBoomSphere",
            finalDescriptor, false));
        finalCode.add(new VarInsnNode(Opcodes.ALOAD, 0));
        finalCode.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL,
            contextInternal, "getInstance", "()Ljava/lang/Object;", false));
        finalCode.add(new TypeInsnNode(Opcodes.CHECKCAST, entityInternal));
        finalCode.add(new InsnNode(Opcodes.FCONST_1));
        finalCode.add(new LdcInsnNode(20.0f));
        finalCode.add(new MethodInsnNode(Opcodes.INVOKESTATIC,
            "com/eternal_creations/archies_dino_mod/client/util/ShakeUtil",
            "shakeExplosion", "(L" + entityInternal + ";FF)V", false));
        finalCode.add(new InsnNode(Opcodes.RETURN));
        finalEvent.maxStack = 4;
        finalEvent.maxLocals = 1;

        MethodNode groundEvent = owner.methods.stream()
            .filter(method -> method.name.equals("lambda$explosionGroundEvent$11") &&
                method.desc.equals(finalDescriptor))
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("Sculksaur ground Sonic Boom event is missing"));
        clearMethod(groundEvent);
        groundEvent.instructions.add(new InsnNode(Opcodes.RETURN));
        groundEvent.maxStack = 0;
        groundEvent.maxLocals = 1;

        addSculksaurSonicBoomSphereHelpers(owner, contextInternal, entityInternal, levelInternal);
    }

    private static void addSculksaurSonicBoomSphereHelpers(
        ClassNode owner, String contextInternal, String entityInternal, String levelInternal
    ) {
        String sphereName = "archiesDinoMod$spawnSculksaurSonicBoomSphere";
        String sphereDescriptor = "(L" + contextInternal + ";)V";
        String particleName = "archiesDinoMod$spawnSculksaurSonicBoomParticle";
        String particleDescriptor = "(L" + levelInternal + ";DDDI)V";
        String consumerInternal = "java/util/function/IntConsumer";

        owner.methods.removeIf(method ->
            (method.name.equals(sphereName) && method.desc.equals(sphereDescriptor)) ||
            (method.name.equals(particleName) && method.desc.equals(particleDescriptor)));

        MethodNode particle = new MethodNode(
            Opcodes.ACC_PRIVATE | Opcodes.ACC_STATIC,
            particleName,
            particleDescriptor,
            null,
            null
        );
        InsnList p = particle.instructions;

        p.add(new VarInsnNode(Opcodes.ILOAD, 7));
        p.add(new IntInsnNode(Opcodes.SIPUSH, 768));
        p.add(new InsnNode(Opcodes.IREM));
        p.add(new VarInsnNode(Opcodes.ISTORE, 18));

        p.add(new VarInsnNode(Opcodes.ILOAD, 7));
        p.add(new IntInsnNode(Opcodes.SIPUSH, 768));
        p.add(new InsnNode(Opcodes.IDIV));
        p.add(new VarInsnNode(Opcodes.ISTORE, 19));

        p.add(new VarInsnNode(Opcodes.ILOAD, 18));
        p.add(new InsnNode(Opcodes.I2D));
        p.add(new LdcInsnNode(0.5d));
        p.add(new InsnNode(Opcodes.DADD));
        p.add(new LdcInsnNode(2.0d / 768.0d));
        p.add(new InsnNode(Opcodes.DMUL));
        p.add(new InsnNode(Opcodes.DCONST_1));
        p.add(new InsnNode(Opcodes.DSUB));
        p.add(new VarInsnNode(Opcodes.DSTORE, 8));

        p.add(new InsnNode(Opcodes.DCONST_1));
        p.add(new VarInsnNode(Opcodes.DLOAD, 8));
        p.add(new VarInsnNode(Opcodes.DLOAD, 8));
        p.add(new InsnNode(Opcodes.DMUL));
        p.add(new InsnNode(Opcodes.DSUB));
        p.add(new MethodInsnNode(Opcodes.INVOKESTATIC,
            "java/lang/Math", "sqrt", "(D)D", false));
        p.add(new VarInsnNode(Opcodes.DSTORE, 10));

        p.add(new VarInsnNode(Opcodes.ILOAD, 18));
        p.add(new InsnNode(Opcodes.I2D));
        p.add(new LdcInsnNode(2.399963229728653d));
        p.add(new InsnNode(Opcodes.DMUL));
        p.add(new VarInsnNode(Opcodes.DSTORE, 12));

        p.add(new VarInsnNode(Opcodes.DLOAD, 12));
        p.add(new MethodInsnNode(Opcodes.INVOKESTATIC,
            "java/lang/Math", "cos", "(D)D", false));
        p.add(new VarInsnNode(Opcodes.DLOAD, 10));
        p.add(new InsnNode(Opcodes.DMUL));
        p.add(new VarInsnNode(Opcodes.DSTORE, 14));

        p.add(new VarInsnNode(Opcodes.DLOAD, 12));
        p.add(new MethodInsnNode(Opcodes.INVOKESTATIC,
            "java/lang/Math", "sin", "(D)D", false));
        p.add(new VarInsnNode(Opcodes.DLOAD, 10));
        p.add(new InsnNode(Opcodes.DMUL));
        p.add(new VarInsnNode(Opcodes.DSTORE, 16));

        p.add(new LdcInsnNode(0.55d));
        p.add(new VarInsnNode(Opcodes.ILOAD, 19));
        p.add(new InsnNode(Opcodes.I2D));
        p.add(new LdcInsnNode(0.50d));
        p.add(new InsnNode(Opcodes.DMUL));
        p.add(new InsnNode(Opcodes.DSUB));
        p.add(new VarInsnNode(Opcodes.DSTORE, 20));

        p.add(new VarInsnNode(Opcodes.ALOAD, 0));
        p.add(new FieldInsnNode(Opcodes.GETSTATIC,
            "net/minecraft/core/particles/ParticleTypes", "f_235898_",
            "Lnet/minecraft/core/particles/SimpleParticleType;"));

        p.add(new VarInsnNode(Opcodes.DLOAD, 1));
        p.add(new VarInsnNode(Opcodes.DLOAD, 14));
        p.add(new VarInsnNode(Opcodes.DLOAD, 20));
        p.add(new InsnNode(Opcodes.DMUL));
        p.add(new InsnNode(Opcodes.DADD));

        p.add(new VarInsnNode(Opcodes.DLOAD, 3));
        p.add(new VarInsnNode(Opcodes.DLOAD, 8));
        p.add(new VarInsnNode(Opcodes.DLOAD, 20));
        p.add(new InsnNode(Opcodes.DMUL));
        p.add(new InsnNode(Opcodes.DADD));

        p.add(new VarInsnNode(Opcodes.DLOAD, 5));
        p.add(new VarInsnNode(Opcodes.DLOAD, 16));
        p.add(new VarInsnNode(Opcodes.DLOAD, 20));
        p.add(new InsnNode(Opcodes.DMUL));
        p.add(new InsnNode(Opcodes.DADD));

        p.add(new VarInsnNode(Opcodes.DLOAD, 14));
        p.add(new LdcInsnNode(0.32d));
        p.add(new InsnNode(Opcodes.DMUL));
        p.add(new VarInsnNode(Opcodes.DLOAD, 8));
        p.add(new LdcInsnNode(0.32d));
        p.add(new InsnNode(Opcodes.DMUL));
        p.add(new VarInsnNode(Opcodes.DLOAD, 16));
        p.add(new LdcInsnNode(0.32d));
        p.add(new InsnNode(Opcodes.DMUL));

        p.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL,
            levelInternal, "m_7106_",
            "(Lnet/minecraft/core/particles/ParticleOptions;DDDDDD)V", false));
        p.add(new InsnNode(Opcodes.RETURN));
        particle.maxStack = 14;
        particle.maxLocals = 22;
        owner.methods.add(particle);

        MethodNode sphere = new MethodNode(
            Opcodes.ACC_PRIVATE | Opcodes.ACC_STATIC,
            sphereName,
            sphereDescriptor,
            null,
            null
        );
        InsnList s = sphere.instructions;
        s.add(new VarInsnNode(Opcodes.ALOAD, 0));
        s.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL,
            contextInternal, "getInstance", "()Ljava/lang/Object;", false));
        s.add(new TypeInsnNode(Opcodes.CHECKCAST, entityInternal));
        s.add(new VarInsnNode(Opcodes.ASTORE, 1));

        s.add(new VarInsnNode(Opcodes.ALOAD, 1));
        s.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL,
            entityInternal, "m_9236_", "()L" + levelInternal + ";", false));

        s.add(new VarInsnNode(Opcodes.ALOAD, 1));
        s.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL,
            entityInternal, "m_20185_", "()D", false));

        s.add(new VarInsnNode(Opcodes.ALOAD, 1));
        s.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL,
            entityInternal, "m_20186_", "()D", false));

        s.add(new VarInsnNode(Opcodes.ALOAD, 1));
        s.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL,
            entityInternal, "m_20206_", "()F", false));
        s.add(new InsnNode(Opcodes.F2D));
        s.add(new LdcInsnNode(0.5d));
        s.add(new InsnNode(Opcodes.DMUL));
        s.add(new InsnNode(Opcodes.DADD));

        s.add(new VarInsnNode(Opcodes.ALOAD, 1));
        s.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL,
            entityInternal, "m_20189_", "()D", false));

        Handle metafactory = new Handle(
            Opcodes.H_INVOKESTATIC,
            "java/lang/invoke/LambdaMetafactory",
            "metafactory",
            "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;" +
                "Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodHandle;" +
                "Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;",
            false
        );
        s.add(new InvokeDynamicInsnNode(
            "accept",
            "(L" + levelInternal + ";DDD)L" + consumerInternal + ";",
            metafactory,
            Type.getMethodType("(I)V"),
            new Handle(Opcodes.H_INVOKESTATIC,
                owner.name, particleName, particleDescriptor, false),
            Type.getMethodType("(I)V")
        ));
        s.add(new VarInsnNode(Opcodes.ASTORE, 7));
        s.add(new InsnNode(Opcodes.ICONST_0));
        s.add(new IntInsnNode(Opcodes.SIPUSH, 1536));
        s.add(new MethodInsnNode(Opcodes.INVOKESTATIC,
            "java/util/stream/IntStream", "range",
            "(II)Ljava/util/stream/IntStream;", true));
        s.add(new VarInsnNode(Opcodes.ALOAD, 7));
        s.add(new MethodInsnNode(Opcodes.INVOKEINTERFACE,
            "java/util/stream/IntStream", "forEach",
            "(Ljava/util/function/IntConsumer;)V", true));
        s.add(new InsnNode(Opcodes.RETURN));
        sphere.maxStack = 9;
        sphere.maxLocals = 2;
        owner.methods.add(sphere);
    }

    private static void patchSmoothMoveControl(ClassNode owner) {
        if (owner == null) throw new IllegalStateException("SmoothMoveControl class is missing");

        String helperName = "archiesDinoMod$instantYaw";
        String helperDescriptor = "(FFF)F";
        owner.methods.removeIf(method ->
            method.name.equals(helperName) && method.desc.equals(helperDescriptor));

        MethodNode helper = new MethodNode(
            Opcodes.ACC_PRIVATE,
            helperName,
            helperDescriptor,
            null,
            null
        );
        helper.instructions.add(new VarInsnNode(Opcodes.FLOAD, 2));
        helper.instructions.add(new InsnNode(Opcodes.FRETURN));
        helper.maxStack = 1;
        helper.maxLocals = 4;
        owner.methods.add(helper);

        patchYawCalls(owner, "tick3DMovement", "(DDDDZ)V", 2, 1);
        patchYawCalls(owner, "tickClimber", "(DDD)V", 1, 1);
        patchYawCalls(owner, "tickWalker", "(DDDD)V", 1, 1);
    }

    private static void patchYawCalls(
        ClassNode owner, String methodName, String methodDescriptor, int expectedCalls, int replaceCalls
    ) {
        MethodNode method = owner.methods.stream()
            .filter(candidate -> candidate.name.equals(methodName) &&
                candidate.desc.equals(methodDescriptor))
            .findFirst()
            .orElseThrow(() -> new IllegalStateException(
                "SmoothMoveControl method is missing: " + methodName + methodDescriptor));

        int seen = 0;
        int replaced = 0;
        for (AbstractInsnNode insn : method.instructions.toArray()) {
            if (!(insn instanceof MethodInsnNode call) ||
                !call.owner.equals(owner.name) ||
                !call.name.equals("smoothRotlerp") ||
                !call.desc.equals("(FFF)F")) continue;

            if (seen < replaceCalls) {
                method.instructions.set(insn, new MethodInsnNode(
                    Opcodes.INVOKESPECIAL,
                    owner.name,
                    "archiesDinoMod$instantYaw",
                    "(FFF)F",
                    false
                ));
                replaced++;
            }
            seen++;
        }

        if (seen != expectedCalls || replaced != replaceCalls) {
            throw new IllegalStateException(
                "Unexpected yaw interpolation calls in " + methodName +
                ": found " + seen + ", replaced " + replaced);
        }
    }

    private static void patchSmoothBodyControl(ClassNode owner) {
        if (owner == null) throw new IllegalStateException("SmoothBodyControl class is missing");

        MethodNode tick = owner.methods.stream()
            .filter(method -> method.name.equals("m_8121_") && method.desc.equals("()V"))
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("SmoothBodyControl tick method is missing"));

        clearMethod(tick);
        InsnList code = tick.instructions;
        LabelNode snapBody = new LabelNode();
        LabelNode done = new LabelNode();
        String conditionInternal = "com/astemir/astemirlib/re/core/function/Condition";
        String mobInternal = "net/minecraft/world/entity/Mob";

        code.add(new VarInsnNode(Opcodes.ALOAD, 0));
        code.add(new FieldInsnNode(Opcodes.GETFIELD, owner.name, "canRotate",
            "L" + conditionInternal + ";"));
        code.add(new MethodInsnNode(Opcodes.INVOKEINTERFACE,
            conditionInternal, "check", "()Z", true));
        code.add(new JumpInsnNode(Opcodes.IFEQ, done));

        code.add(new VarInsnNode(Opcodes.ALOAD, 0));
        code.add(new FieldInsnNode(Opcodes.GETFIELD, owner.name, "mob", "L" + mobInternal + ";"));
        code.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL,
            mobInternal, "m_20160_", "()Z", false));
        code.add(new JumpInsnNode(Opcodes.IFEQ, snapBody));

        code.add(new VarInsnNode(Opcodes.ALOAD, 0));
        code.add(new FieldInsnNode(Opcodes.GETFIELD, owner.name, "mob", "L" + mobInternal + ";"));
        code.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL,
            mobInternal, "m_6688_", "()Lnet/minecraft/world/entity/LivingEntity;", false));
        code.add(new TypeInsnNode(Opcodes.INSTANCEOF, "net/minecraft/world/entity/player/Player"));
        code.add(new JumpInsnNode(Opcodes.IFNE, done));

        code.add(snapBody);
        code.add(new FrameNode(Opcodes.F_FULL, 1, new Object[] { owner.name }, 0, new Object[0]));
        code.add(new VarInsnNode(Opcodes.ALOAD, 0));
        code.add(new FieldInsnNode(Opcodes.GETFIELD, owner.name, "mob", "L" + mobInternal + ";"));
        code.add(new VarInsnNode(Opcodes.ALOAD, 0));
        code.add(new FieldInsnNode(Opcodes.GETFIELD, owner.name, "mob", "L" + mobInternal + ";"));
        code.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL,
            mobInternal, "m_146908_", "()F", false));
        code.add(new FieldInsnNode(Opcodes.PUTFIELD, mobInternal, "f_20883_", "F"));

        code.add(done);
        code.add(new FrameNode(Opcodes.F_FULL, 1, new Object[] { owner.name }, 0, new Object[0]));
        code.add(new InsnNode(Opcodes.RETURN));
        tick.maxStack = 2;
        tick.maxLocals = 1;
    }

    private static void patchSmoothLookControl(ClassNode owner) {
        if (owner == null) throw new IllegalStateException("SmoothLookControl class is missing");

        MethodNode tick = owner.methods.stream()
            .filter(method -> method.name.equals("m_8128_") && method.desc.equals("()V"))
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("SmoothLookControl tick method is missing"));

        clearMethod(tick);
        InsnList code = tick.instructions;
        LabelNode noRotation = new LabelNode();
        LabelNode invokeVanilla = new LabelNode();
        String conditionInternal = "com/astemir/astemirlib/re/core/function/Condition";
        String lookControlInternal = "net/minecraft/world/entity/ai/control/LookControl";

        code.add(new VarInsnNode(Opcodes.ALOAD, 0));
        code.add(new FieldInsnNode(Opcodes.GETFIELD, owner.name, "canRotate",
            "L" + conditionInternal + ";"));
        code.add(new MethodInsnNode(Opcodes.INVOKEINTERFACE,
            conditionInternal, "check", "()Z", true));
        code.add(new JumpInsnNode(Opcodes.IFEQ, noRotation));

        code.add(new VarInsnNode(Opcodes.ALOAD, 0));
        code.add(new FieldInsnNode(Opcodes.GETFIELD, owner.name, "f_186068_", "I"));
        code.add(new JumpInsnNode(Opcodes.IFLE, noRotation));

        code.add(new VarInsnNode(Opcodes.ALOAD, 0));
        code.add(new VarInsnNode(Opcodes.ALOAD, 0));
        code.add(new FieldInsnNode(Opcodes.GETFIELD, owner.name, "f_24941_", "D"));
        code.add(new FieldInsnNode(Opcodes.PUTFIELD, owner.name, "smoothX", "D"));

        code.add(new VarInsnNode(Opcodes.ALOAD, 0));
        code.add(new VarInsnNode(Opcodes.ALOAD, 0));
        code.add(new FieldInsnNode(Opcodes.GETFIELD, owner.name, "smoothY", "D"));
        code.add(new VarInsnNode(Opcodes.ALOAD, 0));
        code.add(new FieldInsnNode(Opcodes.GETFIELD, owner.name, "f_24942_", "D"));
        code.add(new VarInsnNode(Opcodes.ALOAD, 0));
        code.add(new FieldInsnNode(Opcodes.GETFIELD, owner.name, "smoothY", "D"));
        code.add(new InsnNode(Opcodes.DSUB));
        code.add(new VarInsnNode(Opcodes.ALOAD, 0));
        code.add(new FieldInsnNode(Opcodes.GETFIELD, owner.name, "interpolationSpeed", "D"));
        code.add(new InsnNode(Opcodes.DMUL));
        code.add(new InsnNode(Opcodes.DADD));
        code.add(new FieldInsnNode(Opcodes.PUTFIELD, owner.name, "smoothY", "D"));

        code.add(new VarInsnNode(Opcodes.ALOAD, 0));
        code.add(new VarInsnNode(Opcodes.ALOAD, 0));
        code.add(new FieldInsnNode(Opcodes.GETFIELD, owner.name, "f_24943_", "D"));
        code.add(new FieldInsnNode(Opcodes.PUTFIELD, owner.name, "smoothZ", "D"));

        code.add(new VarInsnNode(Opcodes.ALOAD, 0));
        code.add(new LdcInsnNode(360.0f));
        code.add(new FieldInsnNode(Opcodes.PUTFIELD, lookControlInternal, "f_24938_", "F"));

        code.add(new VarInsnNode(Opcodes.ALOAD, 0));
        code.add(new VarInsnNode(Opcodes.ALOAD, 0));
        code.add(new FieldInsnNode(Opcodes.GETFIELD, owner.name, "maxSpeedLimit", "F"));
        code.add(new FieldInsnNode(Opcodes.PUTFIELD, lookControlInternal, "f_24939_", "F"));
        code.add(new JumpInsnNode(Opcodes.GOTO, invokeVanilla));

        code.add(noRotation);
        code.add(new FrameNode(Opcodes.F_FULL, 1, new Object[] { owner.name }, 0, new Object[0]));
        code.add(new VarInsnNode(Opcodes.ALOAD, 0));
        code.add(new InsnNode(Opcodes.ICONST_0));
        code.add(new FieldInsnNode(Opcodes.PUTFIELD, owner.name, "hasTarget", "Z"));

        code.add(invokeVanilla);
        code.add(new FrameNode(Opcodes.F_FULL, 1, new Object[] { owner.name }, 0, new Object[0]));
        code.add(new VarInsnNode(Opcodes.ALOAD, 0));
        code.add(new MethodInsnNode(Opcodes.INVOKESPECIAL,
            lookControlInternal, "m_8128_", "()V", false));
        code.add(new InsnNode(Opcodes.RETURN));
        tick.maxStack = 4;
        tick.maxLocals = 1;
    }

    private static void patchLushsaurLocalPlayerSprintMixin(ClassNode owner) {
        if (owner == null) throw new IllegalStateException("LocalPlayerMixin class is missing");

        owner.fields.removeIf(field -> field.name.equals("f_108619_"));
        owner.methods.removeIf(method -> method.name.equals("archiesDinoMod$onOpenBook"));

        String descriptor =
            "(Lnet/minecraft/world/entity/Entity;" +
            "Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfoReturnable;)V";
        owner.methods.removeIf(method ->
            method.name.equals("archiesDinoMod$allowLushsaurSprint") &&
                method.desc.equals(descriptor));

        MethodNode method = new MethodNode(
            Opcodes.ACC_PUBLIC,
            "archiesDinoMod$allowLushsaurSprint",
            descriptor,
            null,
            null
        );
        InsnList code = method.instructions;
        LabelNode done = new LabelNode();

        code.add(new VarInsnNode(Opcodes.ALOAD, 1));
        code.add(new TypeInsnNode(Opcodes.INSTANCEOF,
            ROOT + "common/entity/mob/lushsaur/LushsaurEntity"));
        code.add(new JumpInsnNode(Opcodes.IFEQ, done));

        code.add(new VarInsnNode(Opcodes.ALOAD, 1));
        code.add(new TypeInsnNode(Opcodes.CHECKCAST,
            ROOT + "common/entity/mob/lushsaur/LushsaurEntity"));
        code.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL,
            ROOT + "common/entity/mob/lushsaur/LushsaurEntity",
            "m_6688_", "()Lnet/minecraft/world/entity/LivingEntity;", false));
        code.add(new VarInsnNode(Opcodes.ALOAD, 0));
        code.add(new JumpInsnNode(Opcodes.IF_ACMPNE, done));

        code.add(new VarInsnNode(Opcodes.ALOAD, 2));
        code.add(new FieldInsnNode(Opcodes.GETSTATIC,
            "java/lang/Boolean", "TRUE", "Ljava/lang/Boolean;"));
        code.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL,
            "org/spongepowered/asm/mixin/injection/callback/CallbackInfoReturnable",
            "setReturnValue", "(Ljava/lang/Object;)V", false));
        code.add(done);
        code.add(new InsnNode(Opcodes.RETURN));

        AnnotationNode at = new AnnotationNode(
            "Lorg/spongepowered/asm/mixin/injection/At;");
        at.values = new ArrayList<>();
        at.values.add("value");
        at.values.add("HEAD");

        AnnotationNode inject = new AnnotationNode(
            "Lorg/spongepowered/asm/mixin/injection/Inject;");
        inject.values = new ArrayList<>();
        inject.values.add("method");
        inject.values.add(List.of("m_264231_"));
        inject.values.add("at");
        inject.values.add(List.of(at));
        inject.values.add("cancellable");
        inject.values.add(Boolean.TRUE);
        inject.values.add("remap");
        inject.values.add(Boolean.FALSE);
        method.visibleAnnotations = new ArrayList<>();
        method.visibleAnnotations.add(inject);

        method.maxStack = 2;
        method.maxLocals = 3;
        owner.methods.add(method);
    }

    private static void patchPlayerMixin(ClassNode owner) {
        if (owner == null) return;

        owner.methods.removeIf(method -> method.name.equals("onCauseFoodExhaustion") ||
            method.name.equals("archiesDinoMod$isTwoHanded"));

        for (MethodNode method : owner.methods) {
            if (!method.name.equals("archiesDinoMod$enforceTwoHanded")) continue;
            clearMethod(method);
            method.instructions.add(new InsnNode(Opcodes.RETURN));
            method.maxLocals = 2;
        }
    }

    private static void removeMethods(ClassNode owner, Set<String> methodNames) {
        if (owner == null) return;
        owner.methods.removeIf(method -> methodNames.contains(method.name));
    }

    private static void patchKeyMappings(ClassNode owner) {
        if (owner == null) return;

        boolean removedField = owner.fields.removeIf(field -> field.name.equals("ARMOR_ABILITY"));
        if (!removedField) {
            throw new IllegalStateException("Expected ARMOR_ABILITY key mapping field was not found");
        }

        boolean removedHandler = owner.methods.removeIf(method ->
            method.name.equals("lambda$static$0") &&
            method.desc.equals("(Lnet/minecraft/client/player/LocalPlayer;Lnet/minecraft/client/multiplayer/ClientLevel;)V")
        );
        if (!removedHandler) {
            throw new IllegalStateException("Expected armor-ability key handler was not found");
        }

        boolean removedRegistration = false;
        for (MethodNode method : owner.methods) {
            if (!method.name.equals("<clinit>")) continue;
            AbstractInsnNode start = null;
            AbstractInsnNode end = null;
            for (AbstractInsnNode insn : method.instructions.toArray()) {
                if (start == null && insn instanceof LdcInsnNode ldc &&
                    "armor".equals(ldc.cst)) {
                    start = insn;
                }
                if (start != null && insn instanceof FieldInsnNode field &&
                    field.getOpcode() == Opcodes.PUTSTATIC &&
                    field.owner.equals(owner.name) && field.name.equals("ARMOR_ABILITY")) {
                    end = field;
                    break;
                }
            }
            if (start != null && end != null) {
                removeInclusive(method.instructions, start, end);
                removedRegistration = true;
            }
        }
        if (!removedRegistration) {
            throw new IllegalStateException("Could not isolate the armor-ability key registration");
        }
    }

    private static void patchNetworkHelper(ClassNode owner) {
        if (owner == null) return;
        for (MethodNode method : owner.methods) {
            if (!method.name.equals("init") || !method.desc.equals("()V")) continue;

            List<MethodInsnNode> registrations = new ArrayList<>();
            for (AbstractInsnNode insn : method.instructions.toArray()) {
                if (insn instanceof MethodInsnNode call &&
                    call.name.equals("registerMessage") &&
                    call.owner.endsWith("/SimpleChannel")) {
                    registrations.add(call);
                }
            }

            for (int i = registrations.size() - 1; i >= 0; i--) {
                MethodInsnNode registration = registrations.get(i);
                AbstractInsnNode start = null;
                for (AbstractInsnNode cursor = registration.getPrevious(); cursor != null; cursor = cursor.getPrevious()) {
                    if (cursor instanceof FieldInsnNode field &&
                        field.getOpcode() == Opcodes.GETSTATIC &&
                        field.owner.equals(owner.name) &&
                        field.name.equals("CHANNEL")) {
                        start = field;
                        break;
                    }
                }
                if (start == null) continue;

                String messageClass = null;
                for (AbstractInsnNode cursor = start; cursor != null; cursor = cursor.getNext()) {
                    if (cursor instanceof LdcInsnNode ldc && ldc.cst instanceof Type type &&
                        type.getSort() == Type.OBJECT) {
                        String candidate = type.getInternalName();
                        if (candidate.endsWith("Message")) {
                            messageClass = candidate;
                            break;
                        }
                    }
                    if (cursor == registration) break;
                }

                if (messageClass != null && !NETWORK_MESSAGES.contains(messageClass)) {
                    AbstractInsnNode trailing = registration.getNext();
                    while (trailing != null &&
                        (trailing.getType() == AbstractInsnNode.LABEL ||
                         trailing.getType() == AbstractInsnNode.FRAME ||
                         trailing.getType() == AbstractInsnNode.LINE)) {
                        trailing = trailing.getNext();
                    }
                    removeInclusive(method.instructions, start, registration);
                    if (trailing != null && trailing.getOpcode() == Opcodes.POP) {
                        method.instructions.remove(trailing);
                    }
                }
            }
        }
    }

    private static void removeFieldRegistrationBlock(
        MethodNode method, String fieldOwner, String fieldName, String callOwner, String callName
    ) {
        AbstractInsnNode target = null;
        for (AbstractInsnNode insn : method.instructions.toArray()) {
            if (insn instanceof FieldInsnNode field &&
                field.getOpcode() == Opcodes.GETSTATIC &&
                field.owner.equals(fieldOwner) &&
                field.name.equals(fieldName)) {
                target = field;
                break;
            }
        }
        if (target == null) return;

        MethodInsnNode previousCall = null;
        MethodInsnNode endCall = null;
        for (AbstractInsnNode insn : method.instructions.toArray()) {
            if (insn instanceof MethodInsnNode call &&
                call.owner.equals(callOwner) &&
                call.name.equals(callName)) {
                if (insn.getPrevious() == null) continue;
                if (insn.equals(target)) break;
                if (insn.getNext() != null && isBefore(insn, target)) previousCall = call;
                if (isAfter(insn, target)) {
                    endCall = call;
                    break;
                }
            }
        }

        if (endCall == null) return;
        AbstractInsnNode start = previousCall == null ? method.instructions.getFirst() : previousCall.getNext();
        removeInclusive(method.instructions, start, endCall);
    }

    private static boolean isBefore(AbstractInsnNode a, AbstractInsnNode b) {
        for (AbstractInsnNode n = a; n != null; n = n.getNext()) if (n == b) return false;
        return true;
    }

    private static boolean isAfter(AbstractInsnNode a, AbstractInsnNode b) {
        for (AbstractInsnNode n = b; n != null; n = n.getNext()) if (n == a) return true;
        return false;
    }

    private static void clearMethod(MethodNode method) {
        for (AbstractInsnNode insn : method.instructions.toArray()) {
            method.instructions.remove(insn);
        }
        method.tryCatchBlocks.clear();
        if (method.localVariables != null) method.localVariables.clear();
        method.maxStack = 0;
        method.maxLocals = (method.access & Opcodes.ACC_STATIC) == 0 ? 1 : 0;
    }

    private static void removeInclusive(InsnList list, AbstractInsnNode first, AbstractInsnNode last) {
        if (first == null || last == null) return;
        AbstractInsnNode cursor = first;
        while (cursor != null) {
            AbstractInsnNode next = cursor.getNext();
            list.remove(cursor);
            if (cursor == last) break;
            cursor = next;
        }
    }

    private static void stripUnreferencedSyntheticMethods(Set<String> retained, Map<String, ClassNode> nodes) {
        for (String name : retained) {
            ClassNode owner = nodes.get(name);
            if (owner == null) continue;
            Set<String> processed = PROCESSED_METHODS.getOrDefault(name, Set.of());
            owner.methods.removeIf(method ->
                (method.access & Opcodes.ACC_SYNTHETIC) != 0 &&
                !processed.contains(method.name + method.desc));
        }
    }

    private static void printPruneDiagnostics(Set<String> retained, Map<String, ClassNode> nodes) {
        System.out.println("Retained classes: " + retained.size());
        for (String name : retained) {
            if (name.contains("ADItemProperties") ||
                name.contains("ADAttachableModels") ||
                name.contains("Attachable") ||
                name.contains("RibBow") ||
                name.contains("SculkBow") ||
                name.contains("ScorchingHorn") ||
                name.contains("Archie") ||
                name.contains("Therimoss") ||
                name.contains("Coralosaur") ||
                name.contains("Obsidiceratops") ||
                name.contains("Sculkraptor")) {
                System.out.println("KEEP-OUTSIDE-SCOPE " + name);
            }
        }

        for (String target : List.of(
            ROOT + "common/items/ADItemProperties",
            ROOT + "client/model/registry/ADAttachableModels",
            ROOT + "client/model/attachable/LushsaurMaceAttachable",
            ROOT + "client/animation/player/attachable/RibBowAnimations",
            ROOT + "client/particle/effect/TherimossEffects",
            ROOT + "common/entity/mob/archie/ArchieEntity"
        )) {
            System.out.println("REFERENCERS " + target);
            for (ClassNode owner : nodes.values()) {
                for (MethodNode method : owner.methods) {
                    for (AbstractInsnNode insn : method.instructions.toArray()) {
                        String referenced = null;
                        if (insn instanceof TypeInsnNode type) referenced = type.desc;
                        else if (insn instanceof FieldInsnNode field) referenced = field.owner;
                        else if (insn instanceof MethodInsnNode call) referenced = call.owner;
                        if (target.equals(referenced)) {
                            System.out.println("  " + owner.name + "." + method.name + method.desc);
                            break;
                        }
                    }
                }
            }
        }
    }

    private static void verifyNoRemovedRegistryReferences(Set<String> retained, Map<String, ClassNode> nodes) {
        List<String> invalid = new ArrayList<>();
        for (String name : retained) {
            ClassNode owner = nodes.get(name);
            if (owner == null) continue;
            for (MethodNode method : owner.methods) {
                for (AbstractInsnNode insn : method.instructions.toArray()) {
                    if (!(insn instanceof FieldInsnNode field)) continue;
                    Set<String> allowed = REGISTRY_FIELDS.get(field.owner);
                    if (allowed != null && !allowed.contains(field.name)) {
                        invalid.add(name + "." + method.name + " -> " + field.owner + "." + field.name);
                    }
                }
            }
        }
        if (!invalid.isEmpty()) {
            throw new IllegalStateException("Retained classes still reference pruned registry fields:\n" +
                String.join("\n", invalid));
        }
    }

    private static Set<String> dependencyClosure(Set<String> roots, Map<String, ClassNode> nodes) {
        PROCESSED_METHODS.clear();
        Set<String> retained = new LinkedHashSet<>();
        Deque<String> classQueue = new ArrayDeque<>();
        Map<String, Set<String>> requestedMethods = new HashMap<>();

        for (String root : roots) {
            if (nodes.containsKey(root) && retained.add(root)) classQueue.addLast(root);
        }

        while (!classQueue.isEmpty()) {
            String name = classQueue.removeFirst();
            ClassNode node = nodes.get(name);
            if (node == null) continue;

            Set<String> refs = new LinkedHashSet<>();
            addType(refs, node.superName);
            if (node.interfaces != null) refs.addAll(node.interfaces);
            if (node.fields != null) {
                for (FieldNode field : node.fields) addDescriptor(refs, field.desc);
            }

            boolean keepAll = shouldKeepAllMethods(name, roots);
            Set<String> selected = requestedMethods.computeIfAbsent(name, k -> new HashSet<>());
            Set<String> processed = PROCESSED_METHODS.computeIfAbsent(name, k -> new HashSet<>());

            boolean progressed;
            do {
                progressed = false;
                for (MethodNode method : node.methods) {
                    String key = method.name + method.desc;
                    boolean synthetic = (method.access & Opcodes.ACC_SYNTHETIC) != 0;
                    boolean bridge = (method.access & Opcodes.ACC_BRIDGE) != 0;
                    boolean requiredCallback = FRAMEWORK_CALLBACK_METHODS
                        .getOrDefault(name, Set.of()).contains(key);
                    boolean shouldScan = method.name.equals("<clinit>") ||
                        bridge || (keepAll && !synthetic) || selected.contains(key) || requiredCallback;
                    if (!shouldScan || !processed.add(key)) continue;
                    progressed = true;

                    addDescriptor(refs, method.desc);
                    if (method.exceptions != null) refs.addAll(method.exceptions);

                    for (AbstractInsnNode insn : method.instructions.toArray()) {
                        if (insn instanceof TypeInsnNode type) {
                            addType(refs, type.desc);
                        } else if (insn instanceof FieldInsnNode field) {
                            addType(refs, field.owner);
                            addDescriptor(refs, field.desc);
                        } else if (insn instanceof MethodInsnNode call) {
                            addType(refs, call.owner);
                            addDescriptor(refs, call.desc);
                            requestMethod(call.owner, call.name, call.desc,
                                nodes, requestedMethods, retained, classQueue);
                        } else if (insn instanceof MultiANewArrayInsnNode multi) {
                            addDescriptor(refs, multi.desc);
                        } else if (insn instanceof LdcInsnNode ldc) {
                            addConstantWithMethodRefs(refs, ldc.cst, nodes,
                                requestedMethods, retained, classQueue);
                        } else if (insn instanceof InvokeDynamicInsnNode indy) {
                            addDescriptor(refs, indy.desc);
                            addConstantWithMethodRefs(refs, indy.bsm, nodes,
                                requestedMethods, retained, classQueue);
                            for (Object arg : indy.bsmArgs) {
                                addConstantWithMethodRefs(refs, arg, nodes,
                                    requestedMethods, retained, classQueue);
                            }
                        }
                    }
                }
            } while (progressed);

            for (String ref : refs) {
                if (ref.startsWith(ROOT) && nodes.containsKey(ref) && retained.add(ref)) {
                    classQueue.addLast(ref);
                }
            }
        }
        return retained;
    }

    private static boolean shouldKeepAllMethods(String name, Set<String> roots) {
        if (roots.contains(name)) return true;

        return name.startsWith(ROOT + "common/entity/base/") ||
            name.startsWith(ROOT + "common/entity/component/") ||
            name.startsWith(ROOT + "common/entity/ai/") ||
            name.startsWith(ROOT + "common/entity/utils/") ||
            name.startsWith(ROOT + "common/entity/mob/lushsaur/") ||
            name.startsWith(ROOT + "common/entity/mob/chorusoatl/") ||
            name.startsWith(ROOT + "common/entity/mob/sculksaur/") ||
            name.startsWith(ROOT + "common/block/eggs/") ||
            name.equals(ROOT + "common/block/entity/egg/BlockEntityLushsaurEgg") ||
            name.equals(ROOT + "common/block/entity/egg/BlockEntityChorusoatlEgg") ||
            name.equals(ROOT + "common/entity/projectile/ChorusoatlSpitEntity") ||
            name.equals(ROOT + "common/effect/MobEffectDazed") ||
            name.equals(ROOT + "common/particle/ADParticles") ||
            name.startsWith(ROOT + "client/model/render/entity/mob/LushsaurClient") ||
            name.startsWith(ROOT + "client/model/render/entity/mob/ChorusoatlClient") ||
            name.startsWith(ROOT + "client/model/render/entity/mob/SculksaurClient") ||
            name.startsWith(ROOT + "client/animation/player/riding/OnLushsaurPlayerAnimations") ||
            name.startsWith(ROOT + "client/animation/player/riding/OnChorusoatlPlayerAnimations") ||
            name.equals(ROOT + "util/ADConfig") ||
            name.equals(ROOT + "util/ResourceSupplier") ||
            name.equals(ROOT + "util/ClientUtil") ||
            name.equals(ROOT + "util/IModifiedArrow") ||
            name.equals(ROOT + "common/trigger/SpottedEntityTrigger") ||
            name.equals(ROOT + "common/trigger/ADCriteriaTriggers") ||
            name.equals(ROOT + "common/structure/GenericTemplateStructure") ||
            name.equals(ROOT + "common/structure/CustomTemplateStructure") ||
            name.startsWith(ROOT + "common/structure/GenericTemplateStructure$") ||
            name.equals(ROOT + "common/structure/piece/CustomStructurePiece") ||
            name.startsWith(ROOT + "common/structure/piece/CustomStructurePiece$") ||
            name.equals(ROOT + "server/ServerConfig") ||
            name.equals(ROOT + "entity/ForgeEntityPart");
    }

    private static void requestMethod(
        String owner, String methodName, String descriptor,
        Map<String, ClassNode> nodes, Map<String, Set<String>> requestedMethods,
        Set<String> retained, Deque<String> classQueue
    ) {
        if (owner == null || !owner.startsWith(ROOT) || !nodes.containsKey(owner)) return;

        if (retained.add(owner)) classQueue.addLast(owner);

        ClassNode node = nodes.get(owner);
        boolean found = false;
        for (MethodNode method : node.methods) {
            if (method.name.equals(methodName) && method.desc.equals(descriptor)) {
                found = true;
                break;
            }
        }
        if (found) {
            String key = methodName + descriptor;
            if (requestedMethods.computeIfAbsent(owner, k -> new HashSet<>()).add(key) &&
                PROCESSED_METHODS.getOrDefault(owner, Set.of()).contains(key)) {
                classQueue.addLast(owner);
            } else if (!PROCESSED_METHODS.getOrDefault(owner, Set.of()).contains(key) &&
                       !classQueue.contains(owner)) {
                classQueue.addLast(owner);
            }
        }
    }

    private static void addConstantWithMethodRefs(
        Set<String> refs, Object value, Map<String, ClassNode> nodes,
        Map<String, Set<String>> requestedMethods, Set<String> retained,
        Deque<String> classQueue
    ) {
        if (value instanceof Handle handle) {
            addType(refs, handle.getOwner());
            addDescriptor(refs, handle.getDesc());
            requestMethod(handle.getOwner(), handle.getName(), handle.getDesc(),
                nodes, requestedMethods, retained, classQueue);
        } else if (value instanceof Type type) {
            if (type.getSort() == Type.OBJECT) addType(refs, type.getInternalName());
            else if (type.getSort() == Type.ARRAY) addDescriptor(refs, type.getDescriptor());
        } else if (value instanceof ConstantDynamic dynamic) {
            addDescriptor(refs, dynamic.getDescriptor());
            addConstantWithMethodRefs(refs, dynamic.getBootstrapMethod(), nodes,
                requestedMethods, retained, classQueue);
            for (int i = 0; i < dynamic.getBootstrapMethodArgumentCount(); i++) {
                addConstantWithMethodRefs(refs, dynamic.getBootstrapMethodArgument(i), nodes,
                    requestedMethods, retained, classQueue);
            }
        }
    }

    private static void stripUnreferencedMethods(Set<String> retained, Map<String, ClassNode> nodes) {
        for (String name : retained) {
            ClassNode owner = nodes.get(name);
            if (owner == null) continue;
            Set<String> processed = PROCESSED_METHODS.getOrDefault(name, Set.of());
            owner.methods.removeIf(method -> !processed.contains(method.name + method.desc));
        }
    }

    private static void verifyCommonClientRendererCalls(Set<String> retained, Map<String, ClassNode> nodes) {
        String commonClientName = ROOT + "client/CommonClient";
        ClassNode commonClient = nodes.get(commonClientName);
        if (commonClient == null) {
            throw new IllegalStateException("Missing CommonClient class during renderer verification");
        }

        String helperDescriptor = "(L" + ROOT +
            "util/ResourceSupplier;Lnet/minecraft/client/renderer/blockentity/BlockEntityRenderer;)V";
        boolean helperDeclared = commonClient.methods.stream().anyMatch(candidate ->
            candidate.name.equals("registerBlockEntityRenderer") &&
                candidate.desc.equals(helperDescriptor));
        if (!helperDeclared) {
            throw new IllegalStateException("CommonClient is missing registerBlockEntityRenderer" +
                helperDescriptor);
        }

        String resourceSupplierName = ROOT + "util/ResourceSupplier";
        List<String> invalidSupplierCalls = new ArrayList<>();
        for (String retainedName : retained) {
            ClassNode retainedClass = nodes.get(retainedName);
            if (retainedClass == null) continue;
            for (MethodNode retainedMethod : retainedClass.methods) {
                for (AbstractInsnNode insn : retainedMethod.instructions) {
                    if (!(insn instanceof MethodInsnNode call) ||
                        !call.owner.equals(resourceSupplierName) ||
                        !call.name.equals("get")) continue;
                    if (call.getOpcode() != Opcodes.INVOKEVIRTUAL ||
                        call.itf ||
                        !call.desc.equals("()Ljava/lang/Object;")) {
                        invalidSupplierCalls.add(retainedClass.name + "." +
                            retainedMethod.name + retainedMethod.desc + " -> opcode=" +
                            call.getOpcode() + ", itf=" + call.itf + ", descriptor=" + call.desc);
                    }
                }
            }
        }
        if (!invalidSupplierCalls.isEmpty()) {
            throw new IllegalStateException("ResourceSupplier.get must be invokevirtual " +
                "on the ResourceSupplier class:\\n" + String.join("\\n", invalidSupplierCalls));
        }

        MethodNode entityRendererRegistration = commonClient.methods.stream()
            .filter(candidate -> candidate.name.equals("registerEntityRenderers") &&
                candidate.desc.equals("()V"))
            .findFirst().orElseThrow(() ->
                new IllegalStateException("Missing CommonClient.registerEntityRenderers()"));
        int providerLambdas = 0;
        for (AbstractInsnNode insn : entityRendererRegistration.instructions) {
            if (!(insn instanceof InvokeDynamicInsnNode dynamic) ||
                !dynamic.desc.equals("()Lnet/minecraft/client/renderer/entity/EntityRendererProvider;")) continue;
            providerLambdas++;
            if (!dynamic.name.equals("m_174009_")) {
                throw new IllegalStateException("EntityRendererProvider lambda has wrong SAM name: " + dynamic.name);
            }
        }
        if (providerLambdas != 1) {
            throw new IllegalStateException("Expected exactly one NoopRenderer provider lambda, found " + providerLambdas);
        }

        int calls = 0;
        for (MethodNode method : commonClient.methods) {
            if (!method.name.equals("registerBlockEntityRenderers") || !method.desc.equals("()V")) continue;
            for (AbstractInsnNode insn : method.instructions) {
                if (!(insn instanceof MethodInsnNode call) ||
                    call.getOpcode() != Opcodes.INVOKESTATIC ||
                    !call.owner.equals(commonClientName) ||
                    !call.name.equals("registerBlockEntityRenderer")) continue;
                calls++;
                if (!call.desc.equals(helperDescriptor)) {
                    throw new IllegalStateException("CommonClient.registerBlockEntityRenderers calls " +
                        "registerBlockEntityRenderer with the wrong descriptor: " + call.desc);
                }
            }
        }
        if (calls != 2) {
            throw new IllegalStateException("Expected exactly 2 CommonClient block-entity renderer helper calls, found " + calls);
        }
    }

    private static void addServiceRoots(Map<String, byte[]> entries, Set<String> roots) {
        String prefix = "META-INF/services/";
        for (Map.Entry<String, byte[]> entry : entries.entrySet()) {
            if (!entry.getKey().startsWith(prefix)) continue;
            String body = new String(entry.getValue(), java.nio.charset.StandardCharsets.UTF_8);
            for (String line : body.split("\\R")) {
                String value = line.split("#", 2)[0].trim();
                if (value.isEmpty()) continue;
                if (value.startsWith("com.eternal_creations.archies_dino_mod.")) {
                    roots.add(value.replace('.', '/'));
                }
            }
        }
    }

    private static void addConstant(Set<String> refs, Object value) {
        if (value instanceof Type type) {
            if (type.getSort() == Type.OBJECT) addType(refs, type.getInternalName());
            else if (type.getSort() == Type.ARRAY) addDescriptor(refs, type.getDescriptor());
        } else if (value instanceof Handle handle) {
            addType(refs, handle.getOwner());
            addDescriptor(refs, handle.getDesc());
        } else if (value instanceof ConstantDynamic dynamic) {
            addDescriptor(refs, dynamic.getDescriptor());
            addConstant(refs, dynamic.getBootstrapMethod());
            for (int i = 0; i < dynamic.getBootstrapMethodArgumentCount(); i++) {
                addConstant(refs, dynamic.getBootstrapMethodArgument(i));
            }
        }
    }

    private static void addDescriptor(Set<String> refs, String descriptor) {
        if (descriptor == null) return;
        try {
            Type type = Type.getType(descriptor);
            addTypeFromType(refs, type);
        } catch (IllegalArgumentException ignored) { }
    }

    private static void addTypeFromType(Set<String> refs, Type type) {
        if (type.getSort() == Type.OBJECT) addType(refs, type.getInternalName());
        else if (type.getSort() == Type.ARRAY) addTypeFromType(refs, type.getElementType());
        else if (type.getSort() == Type.METHOD) {
            addTypeFromType(refs, type.getReturnType());
            for (Type arg : type.getArgumentTypes()) addTypeFromType(refs, arg);
        }
    }

    private static void addType(Set<String> refs, String internalName) {
        if (internalName != null) refs.add(internalName);
    }
}