import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.jar.*;
import java.util.zip.*;

public final class JarPruner {
    private static final String ROOT = "com/eternal_creations/archies_dino_mod/";
    private static final String LEGACY_HOOKS = "com/eternal_creations/archies_dino_mod/legacy/LegacyBehaviorHooks";
    private static final Map<String, Set<String>> PROCESSED_METHODS = new HashMap<>();

    private static final Map<String, Set<String>> REGISTRY_FIELDS = Map.ofEntries(
        Map.entry(ROOT + "common/items/ADItems", Set.of(
            "LUSH_CORE", "CHORUS_FOSSIL", "SCULK_TOXIN", "LUSHSAUR_MACE", "OOZE_TIPPED_ARROW",
            "LUSHSAUR_EGG", "CHORUSOATL_EGG",
            "LUSHSAUR_SPAWN_EGG", "CHORUSOATL_SPAWN_EGG", "SCULKSAUR_SPAWN_EGG"
        )),
        Map.entry(ROOT + "common/block/ADBlocks", Set.of("LUSHSAUR_EGG", "CHORUSOATL_EGG")),
        Map.entry(ROOT + "common/block/entity/ADBlockEntityTypes", Set.of("LUSHSAUR_EGG", "CHORUSOATL_EGG")),
        Map.entry(ROOT + "common/menu/ADMenus", Set.of("DINO")),
        Map.entry(ROOT + "common/entity/ADEntityTypes", Set.of(
            "SPAWN_INFO", "LUSHSAUR", "CHORUSOATL", "SCULKSAUR", "CHORUSOATL_SPIT", "PUS_TIPPED_ARROW"
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
        Map.entry(ROOT + "common/ADGameEvents", Set.of("PUS_ARROW_AGGRO")),
        Map.entry(ROOT + "common/particle/ADParticles", Set.of(
            "DUST_PILLAR",
            "CHORUSOATL_SPIT_0", "CHORUSOATL_SPIT_1", "CHORUSOATL_SPIT_AOE", "CHORUSOATL_SPIT_PROJECTILE",
            "SCULKSAUR_EXPLOSION_2", "SCULKSAUR_EXPLOSION_3", "SCULKSAUR_EXPLOSION_4",
            "SCULKSAUR_EXPLOSION_5", "SCULKSAUR_EXPLOSION_6"
        )),
        Map.entry(ROOT + "common/sound/ADSoundEvents", Set.of(
            "MACE_ATTACK", "MACE_SPIN",
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
        Map.entry(ROOT + "client/model/registry/ADAttachableModels", Set.of("LUSHSAUR_MACE"))
    );

    private static final List<String> CREATIVE_ITEMS = List.of(
        "LUSHSAUR_SPAWN_EGG", "CHORUSOATL_SPAWN_EGG", "SCULKSAUR_SPAWN_EGG",
        "LUSHSAUR_EGG", "CHORUSOATL_EGG",
        "LUSH_CORE", "SCULK_TOXIN", "CHORUS_FOSSIL", "LUSHSAUR_MACE", "OOZE_TIPPED_ARROW"
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
        ),
        ROOT + "client/particle/effect/SculksaurEffects", Set.of(
            "lambda$explosionFinalEvent$12(Lcom/astemir/astemirlib/re/client/model/core/animation/event/AnimationEvent$Context;)V",
            "lambda$explosionGroundEvent$11(Lcom/astemir/astemirlib/re/client/model/core/animation/event/AnimationEvent$Context;)V"
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
        ROOT + "client/model/system/ADModifiers$1",
        ROOT + "platform/Services",
        ROOT + "platform/ForgeCommonHelper",
        ROOT + "platform/ForgeClientHelper",
        ROOT + "platform/ForgeNetworkHelper",
        ROOT + "platform/ForgePlatformHelper"
    );

    private static final Set<String> MIXIN_ROOTS = Set.of(
        ROOT + "mixin/AbstractArrowMixin",
        ROOT + "mixin/DamageSourcesMixin",
        ROOT + "mixin/PlayerInterfaceMixin",
        ROOT + "mixin/ServerPlayerInterfaceMixin",
        ROOT + "mixin/PlayerAttackDamageBonusMixin",
        ROOT + "mixin/forge/ADWeaponItemMixin",
        ROOT + "mixin/forge/ForgeEntityMultipartMixin",
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

        importBundledForgeMultipartMixin(classBytes, nodes);

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
        patchLushsaurPlayerAvoidance(nodes.get(ROOT + "common/entity/mob/lushsaur/ai/LushsaurAI"));
        patchLargeMobTrackingRanges(nodes.get(ROOT + "common/entity/ADEntityTypes"));
        patchEntitySettings(nodes.get(ROOT + "server/ServerConfig$EntitySettings"));
        patchServerConfig(nodes.get(ROOT + "server/ServerConfig"));
        patchLushMaceItemFactory(nodes.get(ROOT + "common/items/ADItems"));
        patchLushMaceTooltip(nodes.get(ROOT + "common/items/weapon/LushsaurMaceItem"));
        patchLushMaceAttachable(nodes.get(ROOT + "client/model/attachable/LushsaurMaceAttachable$1"));
        patchLushMaceSwings(nodes.get(ROOT + "common/items/weapon/LushsaurMaceItem"));
        patchPusTippedArrowEntity(nodes.get(ROOT + "common/entity/projectile/PusTippedArrowEntity"));
        patchPopZitMessage(nodes.get(ROOT + "networking/serverbound/PopZitMessage"));
        patchNetworkHelper(nodes.get(ROOT + "platform/ForgeNetworkHelper"));
        patchKeyMappings(nodes.get(ROOT + "client/ADKeyMappings"));
        patchAbstractArrowMixin(nodes.get(ROOT + "mixin/AbstractArrowMixin"));
        ClassNode lushsaurEntity = nodes.get(ROOT + "common/entity/mob/lushsaur/LushsaurEntity");
        ClassNode chorusoatlEntity = nodes.get(ROOT + "common/entity/mob/chorusoatl/ChorusoatlEntity");
        ClassNode lushsaurClient = nodes.get(ROOT + "client/model/render/entity/mob/LushsaurClient");
        ClassNode chorusoatlClient = nodes.get(ROOT + "client/model/render/entity/mob/ChorusoatlClient");
        ClassNode sculksaurClient = nodes.get(ROOT + "client/model/render/entity/mob/SculksaurClient");
        ClassNode sculksaurEntity = nodes.get(ROOT + "common/entity/mob/sculksaur/SculksaurEntity");
        patchLushsaurRuntimeHooks(lushsaurEntity);
        patchLushsaurStrictWarningActions(
            lushsaurEntity,
            nodes.get(ROOT + "common/entity/mob/lushsaur/ai/LushsaurAI"),
            nodes.get(ROOT + "common/entity/ai/goal/action/GoalChargeMeleeAttack"));
        patchChorusoatlRuntimeHooks(chorusoatlEntity);
        verifyChorusoatlAnimationHooks(nodes.get(ROOT + "client/model/render/entity/mob/ChorusoatlClient"));
        patchChorusoatlGoalSuppression(nodes.get(ROOT + "common/entity/ai/goal/GoalStrollLandAndAir"), "shouldSuppressChorusStroll");
        patchChorusoatlGoalSuppression(nodes.get(ROOT + "common/entity/mob/chorusoatl/ai/ChorusoatlGoalFollowPack"), "shouldSuppressChorusPackFollow");
        patchLushsaurControllingPassenger(lushsaurEntity);
        patchLushsaurNoInventory(lushsaurEntity);
        patchLushsaurCanSprint(lushsaurEntity);
        patchLushsaurMountSpeed(lushsaurEntity);
        patchLushsaurRunAnimation(lushsaurClient);
        patchMountComponentSaddleGate(nodes.get(ROOT + "common/entity/component/MountComponent"));
        patchLushsaurPassengerInputCooldown(nodes.get(ROOT + "common/entity/component/MountComponent"));
        patchLushsaurNoEquipment(lushsaurEntity);
        patchEntityRegistryNamespace(nodes.get(ROOT + "platform/ForgeCommonHelper"));
        patchSculksaurMultipart(sculksaurEntity, nodes);
        patchSculksaurExplosionEffects(
            nodes.get(ROOT + "client/particle/effect/SculksaurEffects"));
        patchLushsaurRestParticles(
            nodes.get(ROOT + "client/particle/effect/LushsaurEffects"));
        patchLushsaurRunParticles(
            nodes.get(ROOT + "client/particle/effect/LushsaurEffects"));
        patchChorusoatlFootstepParticles(
            nodes.get(ROOT + "client/particle/effect/ChorusoatlEffects"));
        patchSculksaurFootstepParticles(
            nodes.get(ROOT + "client/particle/effect/SculksaurEffects"));
        patchNoRollRootOperation(lushsaurClient);
        patchNoRollRootOperation(sculksaurClient);
        patchRiderLookSteering(nodes.get(ROOT + "common/entity/component/MountComponent"));
        patchTailPhysics(
            lushsaurClient,
            Set.of("tail1", "tail2", "mase", "tail"), 5);
        patchTailPhysics(
            sculksaurClient,
            Set.of("tail1", "tail2"), 2);
        patchLushsaurLocalPlayerSprintMixin(nodes.get(ROOT + "mixin/client/LocalPlayerMixin"));
        patchLushsaurOpenPlayerInventory(nodes.get(ROOT + "mixin/client/LocalPlayerMixin"));
        patchPlayerMixin(nodes.get(ROOT + "mixin/PlayerMixin"));
        removeMethods(nodes.get(ROOT + "mixin/client/LevelRendererMixin"),
            Set.of("archiesDinoMod$onEntityRender"));

        Set<String> roots = new LinkedHashSet<>(ROOT_CLASSES);
        roots.addAll(MIXIN_ROOTS);
        addServiceRoots(allEntries, roots);

        Set<String> retained = dependencyClosure(roots, nodes);
        stripUnreferencedMethods(retained, nodes);
        verifyNoRemovedRegistryReferences(retained, nodes);
        verifyCommonClientRendererCalls(retained, nodes);
        printPruneDiagnostics(retained, nodes);

        Map<String, byte[]> transformedClasses = new HashMap<>();
        for (String internalName : retained) {
            ClassNode node = nodes.get(internalName);
            if (node == null) continue;
            ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
            node.accept(writer);
            byte[] rewrittenClass = rewriteClassUtf8Namespace(writer.toByteArray());
            if (containsAscii(rewrittenClass, "ec_ad")) {
                throw new IllegalStateException("Old mod namespace remains in class " + internalName);
            }
            transformedClasses.put(internalName + ".class", rewrittenClass);
        }
        for (Map.Entry<String, byte[]> transformed : transformedClasses.entrySet()) {
            allEntries.putIfAbsent(transformed.getKey(), transformed.getValue());
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


    private static void importBundledForgeMultipartMixin(
        Map<String, byte[]> classBytes, Map<String, ClassNode> nodes
    ) throws Exception {
        String internalName = ROOT + "mixin/forge/ForgeEntityMultipartMixin";
        Path bundle = Paths.get("tools/ForgeEntityMultipartMixin.class.b64");
        if (!Files.isRegularFile(bundle)) {
            throw new FileNotFoundException("Bundled official Forge multipart mixin is missing: " + bundle);
        }
        String encoded = Files.readString(bundle, java.nio.charset.StandardCharsets.UTF_8)
            .replaceAll("\\s+", "");
        byte[] bytecode = Base64.getDecoder().decode(encoded);
        String sha256 = HexFormat.of().formatHex(
            java.security.MessageDigest.getInstance("SHA-256").digest(bytecode));
        if (!sha256.equals("161ab5bd12cd151daae2a01229b428b72979ccc26019e3d908d9c600f3a207f3")) {
            throw new IllegalStateException("Bundled Forge multipart mixin hash does not match official 1.0.5 reference");
        }

        ClassNode node = new ClassNode(Opcodes.ASM9);
        new ClassReader(bytecode).accept(node, 0);
        if (!internalName.equals(node.name)) {
            throw new IllegalStateException("Bundled Forge multipart mixin has unexpected class name: " + node.name);
        }
        if (nodes.putIfAbsent(internalName, node) != null ||
            classBytes.putIfAbsent(internalName, bytecode) != null) {
            throw new IllegalStateException("Forge multipart mixin unexpectedly already exists in the pinned source JAR");
        }
        System.out.println("IMPORT official 1.0.5 ForgeEntityMultipartMixin into immutable 1.0.4 Legacy input");
    }

    private static byte[] rewriteClassUtf8Namespace(byte[] classBytes) {
        if (classBytes.length < 10 ||
            classBytes[0] != (byte) 0xCA ||
            classBytes[1] != (byte) 0xFE ||
            classBytes[2] != (byte) 0xBA ||
            classBytes[3] != (byte) 0xBE) {
            throw new IllegalArgumentException("Invalid class-file header");
        }

        byte[] oldToken = "ec_ad".getBytes(java.nio.charset.StandardCharsets.US_ASCII);
        byte[] newToken = "archie_dinosaurs".getBytes(java.nio.charset.StandardCharsets.US_ASCII);
        int constantPoolCount = readUnsignedShort(classBytes, 8);
        int offset = 10;
        java.io.ByteArrayOutputStream rewritten = new java.io.ByteArrayOutputStream(classBytes.length + 256);
        rewritten.write(classBytes, 0, 8);
        writeUnsignedShort(rewritten, constantPoolCount);

        for (int index = 1; index < constantPoolCount; index++) {
            if (offset >= classBytes.length) {
                throw new IllegalArgumentException("Truncated class-file constant pool");
            }

            int tag = classBytes[offset++] & 0xFF;
            rewritten.write(tag);

            switch (tag) {
                case 1 -> {
                    int length = readUnsignedShort(classBytes, offset);
                    offset += 2;
                    if (offset + length > classBytes.length) {
                        throw new IllegalArgumentException("Truncated CONSTANT_Utf8 entry");
                    }
                    byte[] original = java.util.Arrays.copyOfRange(classBytes, offset, offset + length);
                    byte[] replaced = replaceBytes(original, oldToken, newToken);
                    if (replaced.length > 0xFFFF) {
                        throw new IllegalArgumentException("Rewritten CONSTANT_Utf8 entry is too long");
                    }
                    writeUnsignedShort(rewritten, replaced.length);
                    rewritten.write(replaced, 0, replaced.length);
                    offset += length;
                }
                case 3, 4 -> {
                    copyClassBytes(rewritten, classBytes, offset, 4);
                    offset += 4;
                }
                case 5, 6 -> {
                    copyClassBytes(rewritten, classBytes, offset, 8);
                    offset += 8;
                    index++;
                }
                case 7, 8, 16, 19, 20 -> {
                    copyClassBytes(rewritten, classBytes, offset, 2);
                    offset += 2;
                }
                case 9, 10, 11, 12, 17, 18 -> {
                    copyClassBytes(rewritten, classBytes, offset, 4);
                    offset += 4;
                }
                case 15 -> {
                    copyClassBytes(rewritten, classBytes, offset, 3);
                    offset += 3;
                }
                default -> throw new IllegalArgumentException("Unknown class-file constant tag: " + tag);
            }
        }

        if (offset > classBytes.length) {
            throw new IllegalArgumentException("Truncated class-file constant pool payload");
        }
        rewritten.write(classBytes, offset, classBytes.length - offset);
        return rewritten.toByteArray();
    }

    private static int readUnsignedShort(byte[] bytes, int offset) {
        if (offset < 0 || offset + 2 > bytes.length) {
            throw new IllegalArgumentException("Truncated class-file u2 value");
        }
        return ((bytes[offset] & 0xFF) << 8) | (bytes[offset + 1] & 0xFF);
    }

    private static void writeUnsignedShort(java.io.ByteArrayOutputStream out, int value) {
        out.write((value >>> 8) & 0xFF);
        out.write(value & 0xFF);
    }

    private static void copyClassBytes(
        java.io.ByteArrayOutputStream out,
        byte[] source,
        int offset,
        int length
    ) {
        if (offset < 0 || length < 0 || offset + length > source.length) {
            throw new IllegalArgumentException("Truncated class-file constant");
        }
        out.write(source, offset, length);
    }

    private static byte[] replaceBytes(byte[] source, byte[] oldToken, byte[] newToken) {
        java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream(source.length);
        int offset = 0;
        while (offset < source.length) {
            boolean match = offset + oldToken.length <= source.length;
            if (match) {
                for (int i = 0; i < oldToken.length; i++) {
                    if (source[offset + i] != oldToken[i]) {
                        match = false;
                        break;
                    }
                }
            }
            if (match) {
                out.write(newToken, 0, newToken.length);
                offset += oldToken.length;
            } else {
                out.write(source[offset] & 0xFF);
                offset++;
            }
        }
        return out.toByteArray();
    }

    private static boolean containsAscii(byte[] source, String value) {
        byte[] target = value.getBytes(java.nio.charset.StandardCharsets.US_ASCII);
        outer:
        for (int offset = 0; offset + target.length <= source.length; offset++) {
            for (int i = 0; i < target.length; i++) {
                if (source[offset + i] != target[i]) continue outer;
            }
            return true;
        }
        return false;
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
                code.add(new MethodInsnNode(Opcodes.INVOKESTATIC, ROOT + "client/model/registry/ADAttachableModels", "init", "()V", false));
                code.add(new MethodInsnNode(Opcodes.INVOKESTATIC, ROOT + "client/animation/player/ADPlayerAnimations", "init", "()V", false));
                code.add(new MethodInsnNode(Opcodes.INVOKESTATIC, ROOT + "client/particle/ADParticleEmitters", "init", "()V", false));
                code.add(new MethodInsnNode(Opcodes.INVOKESTATIC, owner.name, "registerEntityRenderers", "()V", false));
                code.add(new MethodInsnNode(Opcodes.INVOKESTATIC, owner.name, "registerBlockEntityRenderers", "()V", false));
                code.add(new MethodInsnNode(Opcodes.INVOKESTATIC, owner.name, "registerLegacyAttachables", "()V", false));
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

                addPusArrowEntityRenderer(method, owner.name);
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

        owner.methods.removeIf(method ->
            method.name.equals("registerLegacyAttachables") && method.desc.equals("()V"));
        MethodNode attachables = new MethodNode(
            Opcodes.ACC_PRIVATE | Opcodes.ACC_STATIC,
            "registerLegacyAttachables", "()V", null, null);
        InsnList code = attachables.instructions;
        code.add(new InsnNode(Opcodes.ICONST_1));
        code.add(new TypeInsnNode(Opcodes.ANEWARRAY,
            "com/astemir/astemirlib/re/client/model/core/attachable/Attachable$Builder"));
        code.add(new InsnNode(Opcodes.DUP));
        code.add(new InsnNode(Opcodes.ICONST_0));
        code.add(new MethodInsnNode(Opcodes.INVOKESTATIC,
            ROOT + "client/model/attachable/LushsaurMaceAttachable", "create",
            "()Lcom/astemir/astemirlib/re/client/model/core/attachable/Attachable$Builder;", false));
        code.add(new InsnNode(Opcodes.AASTORE));
        code.add(new MethodInsnNode(Opcodes.INVOKESTATIC,
            "com/astemir/astemirlib/re/client/model/core/attachable/Attachable", "register",
            "([Lcom/astemir/astemirlib/re/client/model/core/attachable/Attachable$Builder;)V", false));
        code.add(new InsnNode(Opcodes.RETURN));
        attachables.maxStack = 4;
        attachables.maxLocals = 0;
        owner.methods.add(attachables);
    }

    private static void addPusArrowEntityRenderer(MethodNode method, String commonClientOwner) {
        InsnList code = method.instructions;
        code.add(new FieldInsnNode(Opcodes.GETSTATIC, ROOT + "common/entity/ADEntityTypes",
            "PUS_TIPPED_ARROW", "L" + ROOT + "util/ResourceSupplier;"));
        code.add(new InvokeDynamicInsnNode("m_174009_",
            "()Lnet/minecraft/client/renderer/entity/EntityRendererProvider;",
            new Handle(Opcodes.H_INVOKESTATIC, "java/lang/invoke/LambdaMetafactory", "metafactory",
                "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodHandle;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;", false),
            Type.getMethodType("(Lnet/minecraft/client/renderer/entity/EntityRendererProvider$Context;)Lnet/minecraft/client/renderer/entity/EntityRenderer;"),
            new Handle(Opcodes.H_INVOKESTATIC, commonClientOwner, "lambda$registerEntityRenderers$0",
                "(Lnet/minecraft/client/renderer/entity/EntityRendererProvider$Context;)Lnet/minecraft/client/renderer/entity/EntityRenderer;", false),
            Type.getMethodType("(Lnet/minecraft/client/renderer/entity/EntityRendererProvider$Context;)Lnet/minecraft/client/renderer/entity/EntityRenderer;")));
        code.add(new MethodInsnNode(Opcodes.INVOKESTATIC, commonClientOwner, "registerEntityRenderer",
            "(Ljava/util/function/Supplier;Lnet/minecraft/client/renderer/entity/EntityRendererProvider;)V", false));
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

    private static void patchLushsaurPlayerAvoidance(ClassNode owner) {
        if (owner == null) throw new IllegalStateException("LushsaurAI class is missing");

        String goalAvoidEntity = ROOT + "common/entity/ai/goal/GoalAvoidEntity";
        String factoryDescriptor = "(Ljava/lang/Class;)L" + goalAvoidEntity + "$Builder;";
        String goalSelector = "net/minecraft/world/entity/ai/goal/GoalSelector";
        String addGoalDescriptor = "(ILnet/minecraft/world/entity/ai/goal/Goal;)V";
        MethodNode setup = owner.methods.stream()
            .filter(method -> method.name.equals("setupAI") &&
                method.desc.startsWith("(L" + ROOT + "common/entity/mob/lushsaur/LushsaurEntity;"))
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("LushsaurAI.setupAI is missing"));

        List<MethodInsnNode> factories = new ArrayList<>();
        for (AbstractInsnNode instruction : setup.instructions.toArray()) {
            if (instruction instanceof MethodInsnNode call &&
                call.getOpcode() == Opcodes.INVOKESTATIC &&
                call.owner.equals(goalAvoidEntity) &&
                call.name.equals("of") &&
                call.desc.equals(factoryDescriptor)) {
                factories.add(call);
            }
        }
        if (factories.size() != 1) {
            throw new IllegalStateException("Expected one Lushsaur player-avoidance factory, found " + factories.size());
        }

        MethodInsnNode factory = factories.get(0);
        AbstractInsnNode playerClass = previousCodeInstruction(factory);
        AbstractInsnNode priority = previousCodeInstruction(playerClass);
        AbstractInsnNode selectorLoad = previousCodeInstruction(priority);
        if (!(playerClass instanceof LdcInsnNode classLiteral) ||
            !(classLiteral.cst instanceof Type playerType) ||
            !playerType.getInternalName().equals("net/minecraft/world/entity/player/Player") ||
            priority == null || priority.getOpcode() != Opcodes.ICONST_2 ||
            !(selectorLoad instanceof VarInsnNode load) ||
            load.getOpcode() != Opcodes.ALOAD || load.var != 1) {
            throw new IllegalStateException("Lushsaur player-avoidance goal registration shape changed");
        }

        MethodInsnNode registration = null;
        for (AbstractInsnNode instruction = factory.getNext(); instruction != null; instruction = instruction.getNext()) {
            if (instruction instanceof MethodInsnNode call &&
                call.getOpcode() == Opcodes.INVOKEVIRTUAL &&
                call.owner.equals(goalSelector) &&
                call.name.equals("m_25352_") &&
                call.desc.equals(addGoalDescriptor)) {
                registration = call;
                break;
            }
        }
        if (registration == null) {
            throw new IllegalStateException("Lushsaur player-avoidance goal registration is missing");
        }

        AbstractInsnNode buildCall = previousCodeInstruction(registration);
        if (!(buildCall instanceof MethodInsnNode build) || !build.name.equals("build")) {
            throw new IllegalStateException("Lushsaur player-avoidance builder call is unexpected");
        }

        removeInclusive(setup.instructions, selectorLoad, registration);
        for (AbstractInsnNode instruction : setup.instructions.toArray()) {
            if (instruction instanceof MethodInsnNode call &&
                call.owner.equals(goalAvoidEntity) && call.name.equals("of")) {
                throw new IllegalStateException("LushsaurAI still constructs a player-avoidance goal");
            }
        }
        System.out.println("PATCH LUSHS AVOIDANCE removed player flee goal; Raider hide goal retained");
    }

    private static void patchLargeMobTrackingRanges(ClassNode owner) {
        if (owner == null) throw new IllegalStateException("ADEntityTypes class is missing");

        String builder = "net/minecraft/world/entity/EntityType$Builder";
        String sizeDescriptor = "(FF)L" + builder + ";";
        String trackingName = "m_20702_";
        String trackingDescriptor = "(I)L" + builder + ";";
        int lushPatched = 0;
        int chorusPatched = 0;

        for (MethodNode method : owner.methods) {
            if (method.name.equals("<clinit>")) continue;

            for (AbstractInsnNode instruction : method.instructions.toArray()) {
                if (!(instruction instanceof MethodInsnNode sized) ||
                    sized.getOpcode() != Opcodes.INVOKEVIRTUAL ||
                    !sized.owner.equals(builder) ||
                    !sized.name.equals("m_20699_") ||
                    !sized.desc.equals(sizeDescriptor)) {
                    continue;
                }

                AbstractInsnNode heightNode = previousCodeInstruction(sized);
                AbstractInsnNode widthNode = previousCodeInstruction(heightNode);
                Float height = heightNode instanceof LdcInsnNode ldc && ldc.cst instanceof Float value
                    ? value : null;
                Float width = widthNode instanceof LdcInsnNode ldc && ldc.cst instanceof Float value
                    ? value : null;
                if (width == null || height == null) continue;

                boolean lush = Math.abs(width - 3.2f) < 0.0001f &&
                    Math.abs(height - 3.0f) < 0.0001f &&
                    !hasBuilderCall(method, builder, "m_20719_");
                boolean chorus = Math.abs(width - 3.6f) < 0.0001f &&
                    Math.abs(height - 5.2f) < 0.0001f;
                if (!lush && !chorus) continue;

                List<MethodInsnNode> trackingCalls = new ArrayList<>();
                MethodInsnNode lastBuilderCall = sized;
                for (AbstractInsnNode cursor = sized.getNext(); cursor != null; cursor = cursor.getNext()) {
                    if (cursor instanceof MethodInsnNode call && call.owner.equals(builder) &&
                        (call.name.equals("m_20717_") || call.name.equals("m_20719_") ||
                         call.name.equals(trackingName))) {
                        lastBuilderCall = call;
                        if (call.name.equals(trackingName)) trackingCalls.add(call);
                    }
                    if (cursor.getOpcode() == Opcodes.ARETURN) break;
                }

                if (trackingCalls.size() > 1) {
                    throw new IllegalStateException("Multiple tracking range calls in " + method.name);
                }
                if (!trackingCalls.isEmpty()) {
                    AbstractInsnNode valueNode = previousCodeInstruction(trackingCalls.get(0));
                    if (!(valueNode instanceof IntInsnNode value) ||
                        value.getOpcode() != Opcodes.BIPUSH || value.operand != 16 ||
                        !trackingCalls.get(0).desc.equals(trackingDescriptor)) {
                        throw new IllegalStateException("Unexpected existing tracking range in " + method.name);
                    }
                } else {
                    if (!trackingDescriptor.equals("(I)L" + builder + ";")) {
                        throw new IllegalStateException("Unexpected EntityType.Builder tracking range descriptor");
                    }
                    InsnList tracking = new InsnList();
                    tracking.add(new IntInsnNode(Opcodes.BIPUSH, 16));
                    tracking.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL,
                        builder, trackingName, trackingDescriptor, false));
                    method.instructions.insert(lastBuilderCall, tracking);
                    method.maxStack = Math.max(method.maxStack, 2);
                }

                if (lush) lushPatched++;
                if (chorus) chorusPatched++;
            }
        }

        if (lushPatched != 1 || chorusPatched != 1) {
            throw new IllegalStateException("Expected one Lushsaur and one Chorusoatl tracking range patch; found " +
                lushPatched + " and " + chorusPatched);
        }
        System.out.println("PATCH TRACKING Lushsaur and Chorusoatl client tracking ranges set to 16 chunks (matching Sculksaur)");
    }

    private static boolean hasBuilderCall(MethodNode method, String owner, String name) {
        for (AbstractInsnNode instruction : method.instructions.toArray()) {
            if (instruction instanceof MethodInsnNode call &&
                call.owner.equals(owner) && call.name.equals(name)) {
                return true;
            }
        }
        return false;
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
        if (owner == null) throw new IllegalStateException("ADPlayerAnimations class is missing");
        MethodNode init = owner.methods.stream()
            .filter(method -> method.name.equals("init") && method.desc.equals("()V"))
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("ADPlayerAnimations.init() is missing"));
        clearMethod(init);
        init.instructions.add(new InsnNode(Opcodes.RETURN));
        init.maxStack = 0;
        init.maxLocals = 0;
    }

    private static AbstractInsnNode previousCodeInstruction(AbstractInsnNode instruction) {
        AbstractInsnNode previous = instruction == null ? null : instruction.getPrevious();
        while (previous != null && previous.getOpcode() < 0) previous = previous.getPrevious();
        return previous;
    }

    private static AbstractInsnNode nextCodeInstruction(AbstractInsnNode instruction) {
        AbstractInsnNode next = instruction == null ? null : instruction.getNext();
        while (next != null && next.getOpcode() < 0) next = next.getNext();
        return next;
    }

    private static void patchLushsaurNoInventory(ClassNode owner) {
        if (owner == null) throw new IllegalStateException("LushsaurEntity class is missing");
        String descriptor = "(Lnet/minecraft/world/entity/player/Player;)V";
        owner.methods.removeIf(method -> method.name.equals("m_213583_") && method.desc.equals(descriptor));
        MethodNode method = new MethodNode(Opcodes.ACC_PUBLIC, "m_213583_", descriptor, null, null);
        method.instructions.add(new InsnNode(Opcodes.RETURN));
        method.maxStack = 0;
        method.maxLocals = 2;
        owner.methods.add(method);
    }

    private static void patchRiderLookSteering(ClassNode owner) {
        if (owner == null) throw new IllegalStateException("MountComponent class is missing");
        MethodNode tickRidden = owner.methods.stream()
            .filter(method -> method.name.equals("tickRidden") &&
                method.desc.equals("(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/phys/Vec3;)V"))
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("MountComponent.tickRidden is missing"));

        String playerInternal = "net/minecraft/world/entity/player/Player";
        String lushsaurInternal = ROOT + "common/entity/mob/lushsaur/LushsaurEntity";
        String chorusoatlInternal = ROOT + "common/entity/mob/chorusoatl/ChorusoatlEntity";
        String mobDescriptor = "Lnet/minecraft/world/entity/PathfinderMob;";
        String mathInternal = "java/lang/Math";

        int originalFrameCount = 0;
        int originalBranchCount = 0;
        for (AbstractInsnNode instruction : tickRidden.instructions.toArray()) {
            if (instruction instanceof FrameNode) originalFrameCount++;
            if (instruction instanceof JumpInsnNode ||
                instruction instanceof TableSwitchInsnNode ||
                instruction instanceof LookupSwitchInsnNode) originalBranchCount++;
        }

        MethodInsnNode playerPitch = null;
        for (AbstractInsnNode instruction : tickRidden.instructions.toArray()) {
            if (instruction instanceof MethodInsnNode call &&
                call.getOpcode() == Opcodes.INVOKEVIRTUAL &&
                call.owner.equals(playerInternal) &&
                call.name.equals("m_146909_") &&
                call.desc.equals("()F")) {
                playerPitch = call;
                break;
            }
        }
        if (playerPitch == null) throw new IllegalStateException("Rider pitch read is missing");

        AbstractInsnNode pitchScaleAnchor = nextCodeInstruction(
            nextCodeInstruction(nextCodeInstruction(playerPitch)));
        if (!(pitchScaleAnchor instanceof VarInsnNode pitchStore) ||
            pitchStore.getOpcode() != Opcodes.FSTORE || pitchStore.var != 3) {
            throw new IllegalStateException("Unexpected MountComponent.tickRidden rider-pitch sequence");
        }
        AbstractInsnNode pitchRemainder = previousCodeInstruction(pitchStore);
        if (pitchRemainder == null || pitchRemainder.getOpcode() != Opcodes.FREM) {
            throw new IllegalStateException("Expected the original 360-degree rider-pitch normalization");
        }

        InsnList neutralizeTargetPitch = new InsnList();
        neutralizeTargetPitch.add(new VarInsnNode(Opcodes.ALOAD, 0));
        neutralizeTargetPitch.add(new FieldInsnNode(Opcodes.GETFIELD, owner.name, "mob", mobDescriptor));
        neutralizeTargetPitch.add(new TypeInsnNode(Opcodes.INSTANCEOF, lushsaurInternal));
        neutralizeTargetPitch.add(new VarInsnNode(Opcodes.ALOAD, 0));
        neutralizeTargetPitch.add(new FieldInsnNode(Opcodes.GETFIELD, owner.name, "mob", mobDescriptor));
        neutralizeTargetPitch.add(new TypeInsnNode(Opcodes.INSTANCEOF, chorusoatlInternal));
        neutralizeTargetPitch.add(new InsnNode(Opcodes.IOR));
        neutralizeTargetPitch.add(new InsnNode(Opcodes.ICONST_1));
        neutralizeTargetPitch.add(new InsnNode(Opcodes.IXOR));
        neutralizeTargetPitch.add(new InsnNode(Opcodes.I2F));
        neutralizeTargetPitch.add(new InsnNode(Opcodes.FMUL));
        tickRidden.instructions.insert(pitchRemainder, neutralizeTargetPitch);

        int headCalls = 0;
        int bodyCalls = 0;
        for (AbstractInsnNode instruction : tickRidden.instructions.toArray()) {
            if (!(instruction instanceof MethodInsnNode call) ||
                call.getOpcode() != Opcodes.INVOKEINTERFACE ||
                !call.owner.equals(ROOT + "common/entity/base/Mount") ||
                !call.desc.equals("()Z")) continue;

            boolean head = call.name.equals("canMountRotateHead");
            boolean body = call.name.equals("canMountRotateBody");
            if (!head && !body) continue;

            AbstractInsnNode following = nextCodeInstruction(call);
            if (!(following instanceof JumpInsnNode branch) ||
                branch.getOpcode() != Opcodes.IFEQ) {
                throw new IllegalStateException("Unexpected control-flow after " + call.name);
            }

            InsnList requireMovementForTargetMounts = new InsnList();
            requireMovementForTargetMounts.add(new VarInsnNode(Opcodes.ALOAD, 0));
            requireMovementForTargetMounts.add(new FieldInsnNode(Opcodes.GETFIELD, owner.name, "mob", mobDescriptor));
            requireMovementForTargetMounts.add(new TypeInsnNode(Opcodes.INSTANCEOF, lushsaurInternal));
            requireMovementForTargetMounts.add(new VarInsnNode(Opcodes.ALOAD, 0));
            requireMovementForTargetMounts.add(new FieldInsnNode(Opcodes.GETFIELD, owner.name, "mob", mobDescriptor));
            requireMovementForTargetMounts.add(new TypeInsnNode(Opcodes.INSTANCEOF, chorusoatlInternal));
            requireMovementForTargetMounts.add(new InsnNode(Opcodes.IOR));
            requireMovementForTargetMounts.add(new InsnNode(Opcodes.ICONST_1));
            requireMovementForTargetMounts.add(new InsnNode(Opcodes.IXOR));

            requireMovementForTargetMounts.add(new VarInsnNode(Opcodes.ALOAD, 1));
            requireMovementForTargetMounts.add(new FieldInsnNode(Opcodes.GETFIELD, playerInternal, "f_20900_", "F"));
            requireMovementForTargetMounts.add(new MethodInsnNode(
                Opcodes.INVOKESTATIC, mathInternal, "abs", "(F)F", false));
            requireMovementForTargetMounts.add(new VarInsnNode(Opcodes.ALOAD, 1));
            requireMovementForTargetMounts.add(new FieldInsnNode(Opcodes.GETFIELD, playerInternal, "f_20902_", "F"));
            requireMovementForTargetMounts.add(new MethodInsnNode(
                Opcodes.INVOKESTATIC, mathInternal, "abs", "(F)F", false));
            requireMovementForTargetMounts.add(new InsnNode(Opcodes.FADD));
            requireMovementForTargetMounts.add(new MethodInsnNode(
                Opcodes.INVOKESTATIC, mathInternal, "signum", "(F)F", false));
            requireMovementForTargetMounts.add(new InsnNode(Opcodes.F2I));
            requireMovementForTargetMounts.add(new InsnNode(Opcodes.IOR));
            requireMovementForTargetMounts.add(new InsnNode(Opcodes.IAND));
            tickRidden.instructions.insert(call, requireMovementForTargetMounts);

            if (head) headCalls++;
            if (body) bodyCalls++;
        }
        if (headCalls != 1 || bodyCalls != 1) {
            throw new IllegalStateException(
                "Expected one head and one body rider-look gate, found head=" + headCalls + ", body=" + bodyCalls);
        }

        int finalFrameCount = 0;
        int finalBranchCount = 0;
        for (AbstractInsnNode instruction : tickRidden.instructions.toArray()) {
            if (instruction instanceof FrameNode) finalFrameCount++;
            if (instruction instanceof JumpInsnNode ||
                instruction instanceof TableSwitchInsnNode ||
                instruction instanceof LookupSwitchInsnNode) finalBranchCount++;
        }
        if (finalFrameCount != originalFrameCount || finalBranchCount != originalBranchCount) {
            throw new IllegalStateException(
                "Rider-look patch must not add stack-map frames or control-flow branches; " +
                "frames " + originalFrameCount + " -> " + finalFrameCount +
                ", branches " + originalBranchCount + " -> " + finalBranchCount);
        }
    }



    private static void patchEntityRegistryNamespace(ClassNode owner) {
        if (owner == null) throw new IllegalStateException("ForgeCommonHelper class is missing");

        MethodNode initializer = owner.methods.stream()
            .filter(method -> method.name.equals("<clinit>") && method.desc.equals("()V"))
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("ForgeCommonHelper static initializer is missing"));

        int deferredRegisterChanges = 0;
        for (AbstractInsnNode instruction : initializer.instructions.toArray()) {
            if (!(instruction instanceof FieldInsnNode field) ||
                field.getOpcode() != Opcodes.PUTSTATIC ||
                !field.owner.equals(owner.name) ||
                !field.name.equals("ENTITY_TYPES") ||
                !field.desc.equals("Lnet/minecraftforge/registries/DeferredRegister;")) {
                continue;
            }

            AbstractInsnNode createInstruction = previousCodeInstruction(field);
            AbstractInsnNode namespaceInstruction = previousCodeInstruction(createInstruction);
            AbstractInsnNode registryInstruction = previousCodeInstruction(namespaceInstruction);
            if (!(createInstruction instanceof MethodInsnNode create) ||
                create.getOpcode() != Opcodes.INVOKESTATIC ||
                !create.owner.equals("net/minecraftforge/registries/DeferredRegister") ||
                !create.name.equals("create") ||
                !create.desc.equals("(Lnet/minecraftforge/registries/IForgeRegistry;Ljava/lang/String;)Lnet/minecraftforge/registries/DeferredRegister;") ||
                !(namespaceInstruction instanceof LdcInsnNode namespace) ||
                !"ec_ad".equals(namespace.cst) ||
                !(registryInstruction instanceof FieldInsnNode registry) ||
                registry.getOpcode() != Opcodes.GETSTATIC ||
                !registry.owner.equals("net/minecraftforge/registries/ForgeRegistries") ||
                !registry.name.equals("ENTITY_TYPES")) {
                throw new IllegalStateException("Unexpected Forge entity DeferredRegister initialization");
            }

            initializer.instructions.set(namespaceInstruction, new LdcInsnNode("archie_dinosaurs"));
            deferredRegisterChanges++;
        }
        if (deferredRegisterChanges != 1) {
            throw new IllegalStateException("Expected exactly one Forge entity DeferredRegister namespace change, found " + deferredRegisterChanges);
        }

        String registerDescriptor =
            "(Ljava/lang/String;Lnet/minecraft/world/entity/EntityType$Builder;)L" +
            ROOT + "util/ResourceSupplier;";
        MethodNode registerEntityType = owner.methods.stream()
            .filter(method -> method.name.equals("registerEntityType") && method.desc.equals(registerDescriptor))
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("ForgeCommonHelper.registerEntityType is missing"));

        List<MethodInsnNode> oldPrefixes = new ArrayList<>();
        for (AbstractInsnNode instruction : registerEntityType.instructions.toArray()) {
            if (instruction instanceof MethodInsnNode call &&
                call.getOpcode() == Opcodes.INVOKESTATIC &&
                call.owner.equals(ROOT + "Constants") &&
                call.name.equals("prefix") &&
                call.desc.equals("(Ljava/lang/String;)Lnet/minecraft/resources/ResourceLocation;")) {
                oldPrefixes.add(call);
            }
        }
        if (oldPrefixes.size() != 1) {
            throw new IllegalStateException("Expected exactly one Constants.prefix call in registerEntityType, found " + oldPrefixes.size());
        }

        MethodInsnNode oldPrefix = oldPrefixes.get(0);
        AbstractInsnNode oldNameLoad = previousCodeInstruction(oldPrefix);
        if (!(oldNameLoad instanceof VarInsnNode nameLoad) ||
            nameLoad.getOpcode() != Opcodes.ALOAD ||
            nameLoad.var != 1) {
            throw new IllegalStateException("Unexpected entity ResourceLocation name argument");
        }

        InsnList replacement = new InsnList();
        replacement.add(new TypeInsnNode(Opcodes.NEW, "net/minecraft/resources/ResourceLocation"));
        replacement.add(new InsnNode(Opcodes.DUP));
        replacement.add(new LdcInsnNode("archie_dinosaurs"));
        replacement.add(new VarInsnNode(Opcodes.ALOAD, 1));
        replacement.add(new MethodInsnNode(
            Opcodes.INVOKESPECIAL,
            "net/minecraft/resources/ResourceLocation",
            "<init>",
            "(Ljava/lang/String;Ljava/lang/String;)V",
            false
        ));
        registerEntityType.instructions.insertBefore(oldNameLoad, replacement);
        registerEntityType.instructions.remove(oldNameLoad);
        registerEntityType.instructions.remove(oldPrefix);
        registerEntityType.maxStack = Math.max(registerEntityType.maxStack, 4);
    }

    private static void patchLushsaurPassengerInputCooldown(ClassNode owner) {
        if (owner == null) throw new IllegalStateException("MountComponent class is missing");

        String descriptor = "(Lnet/minecraft/world/entity/Entity;)V";
        MethodNode addPassenger = owner.methods.stream()
            .filter(method -> method.name.equals("addPassenger") && method.desc.equals(descriptor))
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("MountComponent.addPassenger is missing"));

        FieldInsnNode cooldownWrite = null;
        for (AbstractInsnNode instruction : addPassenger.instructions.toArray()) {
            if (instruction instanceof FieldInsnNode field &&
                field.getOpcode() == Opcodes.PUTFIELD &&
                field.owner.equals(owner.name) &&
                field.name.equals("passengerRideCooldown") &&
                field.desc.equals("I")) {
                if (cooldownWrite != null) {
                    throw new IllegalStateException("Unexpected multiple passenger cooldown writes in addPassenger");
                }
                cooldownWrite = field;
            }
        }
        if (cooldownWrite == null) {
            throw new IllegalStateException("MountComponent.addPassenger passenger cooldown assignment is missing");
        }

        AbstractInsnNode originalValue = previousCodeInstruction(cooldownWrite);
        if (!(originalValue instanceof IntInsnNode value) ||
            value.getOpcode() != Opcodes.BIPUSH || value.operand != 20) {
            throw new IllegalStateException("Unexpected original passenger cooldown value");
        }

        InsnList replacement = new InsnList();
        replacement.add(new VarInsnNode(Opcodes.ALOAD, 0));
        replacement.add(new FieldInsnNode(Opcodes.GETFIELD, owner.name,
            "mob", "Lnet/minecraft/world/entity/PathfinderMob;"));
        replacement.add(new TypeInsnNode(Opcodes.INSTANCEOF,
            ROOT + "common/entity/mob/lushsaur/LushsaurEntity"));
        replacement.add(new InsnNode(Opcodes.ICONST_1));
        replacement.add(new InsnNode(Opcodes.IXOR));
        replacement.add(new IntInsnNode(Opcodes.BIPUSH, 20));
        replacement.add(new InsnNode(Opcodes.IMUL));
        addPassenger.instructions.insertBefore(value, replacement);
        addPassenger.instructions.remove(value);
        addPassenger.maxStack = Math.max(addPassenger.maxStack, 3);

        System.out.println("PATCH LUSHS MOUNT passenger input cooldown set to zero; other mounts retain 20 ticks");
    }

    private static void patchMountComponentSaddleGate(ClassNode owner) {
        if (owner == null) throw new IllegalStateException("MountComponent class is missing");

        String descriptor =
            "(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/InteractionResult;";
        MethodNode interact = owner.methods.stream()
            .filter(method -> method.name.equals("mobInteract") && method.desc.equals(descriptor))
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("MountComponent.mobInteract is missing"));

        int patched = 0;
        for (AbstractInsnNode instruction : interact.instructions.toArray()) {
            if (!(instruction instanceof MethodInsnNode itemCheck) ||
                itemCheck.getOpcode() != Opcodes.INVOKEVIRTUAL ||
                !itemCheck.owner.equals("net/minecraft/world/item/ItemStack") ||
                !itemCheck.name.equals("m_150930_") ||
                !itemCheck.desc.equals("(Lnet/minecraft/world/item/Item;)Z")) {
                continue;
            }

            AbstractInsnNode itemField = previousCodeInstruction(itemCheck);
            AbstractInsnNode branchInstruction = nextCodeInstruction(itemCheck);
            if (!(itemField instanceof FieldInsnNode saddleItem) ||
                saddleItem.getOpcode() != Opcodes.GETSTATIC ||
                !saddleItem.owner.equals("net/minecraft/world/item/Items") ||
                !saddleItem.name.equals("f_42450_") ||
                !(branchInstruction instanceof JumpInsnNode itemNotSaddle) ||
                itemNotSaddle.getOpcode() != Opcodes.IFEQ) {
                continue;
            }

            InsnList capabilityGate = new InsnList();
            capabilityGate.add(new VarInsnNode(Opcodes.ALOAD, 0));
            capabilityGate.add(new FieldInsnNode(
                Opcodes.GETFIELD,
                owner.name,
                "mob",
                "Lnet/minecraft/world/entity/PathfinderMob;"
            ));
            capabilityGate.add(new TypeInsnNode(Opcodes.CHECKCAST, "net/minecraft/world/entity/Saddleable"));
            capabilityGate.add(new MethodInsnNode(
                Opcodes.INVOKEINTERFACE,
                "net/minecraft/world/entity/Saddleable",
                "m_6741_",
                "()Z",
                true
            ));
            capabilityGate.add(new JumpInsnNode(Opcodes.IFEQ, itemNotSaddle.label));
            interact.instructions.insert(itemNotSaddle, capabilityGate);
            patched++;
        }

        if (patched != 1) {
            throw new IllegalStateException(
                "Expected one MountComponent saddle item branch, patched " + patched
            );
        }
        interact.maxStack = Math.max(interact.maxStack, 1);
    }

    private static void patchLushsaurNoEquipment(ClassNode owner) {
        if (owner == null) throw new IllegalStateException("Lushsaur entity class is missing");

        owner.methods.removeIf(method ->
            method.name.equals("m_6741_") && method.desc.equals("()Z"));
        MethodNode saddleable = new MethodNode(Opcodes.ACC_PUBLIC, "m_6741_", "()Z", null, null);
        saddleable.instructions.add(new InsnNode(Opcodes.ICONST_0));
        saddleable.instructions.add(new InsnNode(Opcodes.IRETURN));
        owner.methods.add(saddleable);

        owner.methods.removeIf(method ->
            method.name.equals("m_6254_") && method.desc.equals("()Z"));
        MethodNode saddled = new MethodNode(Opcodes.ACC_PUBLIC, "m_6254_", "()Z", null, null);
        saddled.instructions.add(new InsnNode(Opcodes.ICONST_0));
        saddled.instructions.add(new InsnNode(Opcodes.IRETURN));
        owner.methods.add(saddled);

        owner.methods.removeIf(method ->
            method.name.equals("setSaddled") && method.desc.equals("(Z)V"));
        MethodNode setSaddled = new MethodNode(Opcodes.ACC_PUBLIC, "setSaddled", "(Z)V", null, null);
        setSaddled.instructions.add(new InsnNode(Opcodes.RETURN));
        owner.methods.add(setSaddled);

        owner.methods.removeIf(method ->
            method.name.equals("getInventorySize") && method.desc.equals("()I"));
        MethodNode inventorySize = new MethodNode(Opcodes.ACC_PROTECTED, "getInventorySize", "()I", null, null);
        inventorySize.instructions.add(new InsnNode(Opcodes.ICONST_0));
        inventorySize.instructions.add(new InsnNode(Opcodes.IRETURN));
        owner.methods.add(inventorySize);

        owner.methods.removeIf(method ->
            method.name.equals("updateContainerEquipment") && method.desc.equals("()V"));
        MethodNode updateEquipment = new MethodNode(
            Opcodes.ACC_PROTECTED, "updateContainerEquipment", "()V", null, null);
        updateEquipment.instructions.add(new InsnNode(Opcodes.RETURN));
        owner.methods.add(updateEquipment);

        owner.methods.removeIf(method ->
            method.name.equals("m_141942_") &&
            method.desc.equals("(I)Lnet/minecraft/world/entity/SlotAccess;"));
        MethodNode slotAccess = new MethodNode(
            Opcodes.ACC_PUBLIC,
            "m_141942_",
            "(I)Lnet/minecraft/world/entity/SlotAccess;",
            null,
            null
        );
        slotAccess.instructions.add(new FieldInsnNode(
            Opcodes.GETSTATIC,
            "net/minecraft/world/entity/SlotAccess",
            "f_147290_",
            "Lnet/minecraft/world/entity/SlotAccess;"
        ));
        slotAccess.instructions.add(new InsnNode(Opcodes.ARETURN));
        owner.methods.add(slotAccess);

        owner.methods.removeIf(method ->
            method.name.equals("m_6844_") &&
            method.desc.equals("(Lnet/minecraft/world/entity/EquipmentSlot;)Lnet/minecraft/world/item/ItemStack;"));
        MethodNode getItemBySlot = new MethodNode(
            Opcodes.ACC_PUBLIC,
            "m_6844_",
            "(Lnet/minecraft/world/entity/EquipmentSlot;)Lnet/minecraft/world/item/ItemStack;",
            null,
            null
        );
        getItemBySlot.instructions.add(new FieldInsnNode(
            Opcodes.GETSTATIC,
            "net/minecraft/world/item/ItemStack",
            "f_41583_",
            "Lnet/minecraft/world/item/ItemStack;"
        ));
        getItemBySlot.instructions.add(new InsnNode(Opcodes.ARETURN));
        owner.methods.add(getItemBySlot);

        owner.methods.removeIf(method ->
            method.name.equals("m_8061_") &&
            method.desc.equals("(Lnet/minecraft/world/entity/EquipmentSlot;Lnet/minecraft/world/item/ItemStack;)V"));
        MethodNode setItemSlot = new MethodNode(
            Opcodes.ACC_PUBLIC,
            "m_8061_",
            "(Lnet/minecraft/world/entity/EquipmentSlot;Lnet/minecraft/world/item/ItemStack;)V",
            null,
            null
        );
        setItemSlot.instructions.add(new InsnNode(Opcodes.RETURN));
        owner.methods.add(setItemSlot);

        owner.methods.removeIf(method ->
            method.name.equals("m_255207_") &&
            method.desc.equals("(Lnet/minecraft/world/item/ItemStack;)Lnet/minecraft/world/item/ItemStack;"));
        MethodNode equipItemIfPossible = new MethodNode(
            Opcodes.ACC_PUBLIC,
            "m_255207_",
            "(Lnet/minecraft/world/item/ItemStack;)Lnet/minecraft/world/item/ItemStack;",
            null,
            null
        );
        equipItemIfPossible.instructions.add(new VarInsnNode(Opcodes.ALOAD, 1));
        equipItemIfPossible.instructions.add(new InsnNode(Opcodes.ARETURN));
        owner.methods.add(equipItemIfPossible);

        for (String methodName : Set.of("m_7252_", "m_7243_")) {
            owner.methods.removeIf(method ->
                method.name.equals(methodName) &&
                method.desc.equals("(Lnet/minecraft/world/item/ItemStack;)Z"));
            MethodNode rejectPickup = new MethodNode(
                Opcodes.ACC_PUBLIC,
                methodName,
                "(Lnet/minecraft/world/item/ItemStack;)Z",
                null,
                null
            );
            rejectPickup.instructions.add(new InsnNode(Opcodes.ICONST_0));
            rejectPickup.instructions.add(new InsnNode(Opcodes.IRETURN));
            owner.methods.add(rejectPickup);
        }

        owner.methods.removeIf(method ->
            method.name.equals("m_7581_") &&
            method.desc.equals("(Lnet/minecraft/world/entity/item/ItemEntity;)V"));
        MethodNode rejectItemPickup = new MethodNode(
            Opcodes.ACC_PROTECTED,
            "m_7581_",
            "(Lnet/minecraft/world/entity/item/ItemEntity;)V",
            null,
            null
        );
        rejectItemPickup.instructions.add(new InsnNode(Opcodes.RETURN));
        owner.methods.add(rejectItemPickup);
    }

    private static void patchNoRollRootOperation(ClassNode owner) {
        if (owner == null) throw new IllegalStateException("Mob renderer is missing");
        MethodNode initialize = owner.methods.stream()
            .filter(method -> method.name.equals("onInitialize") &&
                method.desc.startsWith("(Lcom/astemir/astemirlib/re/client/model/core/system/ModelSystem;"))
            .findFirst()
            .orElseThrow(() -> new IllegalStateException(owner.name + ".onInitialize is missing"));
        String commonRoot = "com/astemir/astemirlib/re/client/model/CommonRootOperations";
        String operationOwner = commonRoot + "$RollOnRotation";
        MethodInsnNode rollFactory = null;
        int rollCalls = 0;
        for (AbstractInsnNode instruction : initialize.instructions.toArray()) {
            if (instruction instanceof MethodInsnNode call &&
                call.getOpcode() == Opcodes.INVOKESTATIC &&
                call.owner.equals(operationOwner) && call.name.equals("of") &&
                call.desc.equals("(FFF)L" + operationOwner + ";")) {
                rollFactory = call;
                rollCalls++;
            }
        }
        if (rollCalls != 1 || rollFactory == null) {
            throw new IllegalStateException(owner.name + " must contain exactly one RollOnRotation root operation");
        }
        AbstractInsnNode arrayStore = nextCodeInstruction(rollFactory);
        if (arrayStore == null || arrayStore.getOpcode() != Opcodes.AASTORE) {
            throw new IllegalStateException(owner.name + " RollOnRotation array store is unexpected");
        }
        AbstractInsnNode arrayDuplicate = previousCodeInstruction(rollFactory);
        while (arrayDuplicate != null && arrayDuplicate.getOpcode() != Opcodes.DUP) {
            arrayDuplicate = previousCodeInstruction(arrayDuplicate);
        }
        if (arrayDuplicate == null) throw new IllegalStateException("RollOnRotation array DUP is missing");

        TypeInsnNode rootArray = null;
        int rootArrays = 0;
        for (AbstractInsnNode instruction : initialize.instructions.toArray()) {
            if (instruction instanceof TypeInsnNode type &&
                type.getOpcode() == Opcodes.ANEWARRAY &&
                type.desc.equals("com/astemir/astemirlib/re/client/model/core/RootOperation")) {
                rootArray = type;
                rootArrays++;
            }
        }
        if (rootArrays != 1 || rootArray == null) {
            throw new IllegalStateException(owner.name + " root operation array is unexpected");
        }
        AbstractInsnNode arraySize = previousCodeInstruction(rootArray);
        if (arraySize == null || arraySize.getOpcode() != Opcodes.ICONST_4) {
            throw new IllegalStateException(owner.name + " expected four original root operations");
        }
        initialize.instructions.set(arraySize, new InsnNode(Opcodes.ICONST_3));
        removeInclusive(initialize.instructions, arrayDuplicate, arrayStore);

        FieldInsnNode grumm = null;
        for (AbstractInsnNode instruction : initialize.instructions.toArray()) {
            if (instruction instanceof FieldInsnNode field &&
                field.getOpcode() == Opcodes.GETSTATIC &&
                field.owner.equals(commonRoot) && field.name.equals("GRUMM_ROTATION")) {
                grumm = field;
                break;
            }
        }
        if (grumm == null) throw new IllegalStateException(owner.name + " GRUMM root operation was lost");
        AbstractInsnNode grummIndex = previousCodeInstruction(grumm);
        if (grummIndex == null || grummIndex.getOpcode() != Opcodes.ICONST_3) {
            throw new IllegalStateException(owner.name + " GRUMM root operation index is unexpected");
        }
        initialize.instructions.set(grummIndex, new InsnNode(Opcodes.ICONST_2));
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

    private static void patchLushMaceItemFactory(ClassNode owner) {
        if (owner == null) throw new IllegalStateException("ADItems class is missing");

        String maceOwner = ROOT + "common/items/weapon/LushsaurMaceItem";
        String statsOwner = ROOT + "common/items/util/ADWeaponItem$WeaponStats";
        String factoryDescriptor =
            "(Lnet/minecraft/world/item/Item$Properties;)L" + maceOwner + ";";
        MethodNode factory = owner.methods.stream()
            .filter(method -> method.name.equals("lambda$static$0") &&
                method.desc.equals(factoryDescriptor))
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("Lushsaur mace item factory is missing"));

        owner.methods.removeIf(method ->
            method.name.equals("legacyLushMaceStats") &&
            method.desc.equals("()L" + statsOwner + ";"));

        MethodNode statsSupplier = new MethodNode(
            Opcodes.ACC_PRIVATE | Opcodes.ACC_STATIC,
            "legacyLushMaceStats", "()L" + statsOwner + ";", null, null);
        statsSupplier.instructions.add(new FieldInsnNode(Opcodes.GETSTATIC,
            maceOwner, "DEFAULT_STATS", "L" + statsOwner + ";"));
        statsSupplier.instructions.add(new InsnNode(Opcodes.ARETURN));
        statsSupplier.maxStack = 1;
        statsSupplier.maxLocals = 0;
        owner.methods.add(statsSupplier);

        clearMethod(factory);
        InsnList code = factory.instructions;
        code.add(new TypeInsnNode(Opcodes.NEW, maceOwner));
        code.add(new InsnNode(Opcodes.DUP));
        code.add(new VarInsnNode(Opcodes.ALOAD, 0));
        code.add(new InvokeDynamicInsnNode("get", "()Ljava/util/function/Supplier;",
            new Handle(Opcodes.H_INVOKESTATIC, "java/lang/invoke/LambdaMetafactory", "metafactory",
                "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodHandle;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;", false),
            Type.getMethodType("()Ljava/lang/Object;"),
            new Handle(Opcodes.H_INVOKESTATIC, owner.name, "legacyLushMaceStats",
                "()L" + statsOwner + ";", false),
            Type.getMethodType("()L" + statsOwner + ";")));
        code.add(new MethodInsnNode(Opcodes.INVOKESPECIAL, maceOwner, "<init>",
            "(Lnet/minecraft/world/item/Item$Properties;Ljava/util/function/Supplier;)V", false));
        code.add(new InsnNode(Opcodes.ARETURN));
        factory.maxStack = 4;
        factory.maxLocals = 1;
    }

    private static void patchLushMaceTooltip(ClassNode owner) {
        if (owner == null) throw new IllegalStateException("LushsaurMaceItem class is missing");

        String descriptor =
            "(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/level/Level;Ljava/util/List;Lnet/minecraft/world/item/TooltipFlag;)V";
        owner.methods.removeIf(method ->
            method.name.equals("m_7373_") && method.desc.equals(descriptor));

        MethodNode tooltip = new MethodNode(
            Opcodes.ACC_PUBLIC, "m_7373_", descriptor, null, null);
        tooltip.instructions.add(new InsnNode(Opcodes.RETURN));
        tooltip.maxStack = 0;
        tooltip.maxLocals = 5;
        owner.methods.add(tooltip);
    }

    private static void patchLushMaceAttachable(ClassNode owner) {
        if (owner == null) throw new IllegalStateException("LushsaurMaceAttachable$1 class is missing");

        boolean replaced = false;
        for (MethodNode method : owner.methods) {
            if (!method.name.equals("onInitialize")) continue;
            for (AbstractInsnNode insn : method.instructions.toArray()) {
                if (insn instanceof LdcInsnNode ldc && "spin_first_person".equals(ldc.cst)) {
                    ldc.cst = "attack_first_person";
                    replaced = true;
                }
            }
        }
        if (!replaced) {
            throw new IllegalStateException("Expected spin_first_person clip in LushsaurMaceAttachable$1.onInitialize");
        }
    }

    private static void patchLushMaceSwings(ClassNode owner) {
        if (owner == null) throw new IllegalStateException("LushsaurMaceItem class is missing");

        String playerInternal = "net/minecraft/world/entity/player/Player";
        String handInternal = "net/minecraft/world/InteractionHand";
        String handDesc = "L" + handInternal + ";";
        String itemInternal = "net/minecraft/world/item/Item";
        String cooldownsInternal = "net/minecraft/world/item/ItemCooldowns";
        String syncHolderInternal = "com/astemir/astemirlib/re/common/action/SyncedActionHolder";
        String actionTypeInternal = "com/astemir/astemirlib/re/common/action/ActionType";
        String actionTypeDesc = "L" + actionTypeInternal + ";";
        String actionStateInternal = "com/astemir/astemirlib/re/common/action/ActionState";
        String levelInternal = "net/minecraft/world/level/Level";
        String itemStackInternal = "net/minecraft/world/item/ItemStack";
        String resultHolderInternal = "net/minecraft/world/InteractionResultHolder";

        // 1) LMB attack action ticker: trigger vanilla 3rd-person arm swing on tick == 10 (exact delay of simulateAttack).
        MethodNode attackTick = owner.methods.stream()
            .filter(m -> m.name.equals("lambda$static$0") && m.desc.equals("(L" + playerInternal + ";I)V"))
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("LushsaurMaceItem.lambda$static$0 not found"));

        MethodInsnNode simulateAttackCall = null;
        for (AbstractInsnNode insn : attackTick.instructions.toArray()) {
            if (insn instanceof MethodInsnNode call &&
                call.owner.equals("com/astemir/astemirlib/re/common/world/item/ItemCombatAction$Handler") &&
                call.name.equals("simulateAttack")) {
                simulateAttackCall = call;
                break;
            }
        }
        if (simulateAttackCall == null) {
            throw new IllegalStateException("Expected ItemCombatAction$Handler.simulateAttack call in LushsaurMaceItem.lambda$static$0");
        }
        InsnList lmbSwing = new InsnList();
        lmbSwing.add(new VarInsnNode(Opcodes.ALOAD, 0));
        lmbSwing.add(new FieldInsnNode(Opcodes.GETSTATIC, handInternal, "MAIN_HAND", handDesc));
        lmbSwing.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, playerInternal, "m_6674_", "(" + handDesc + ")V", false));
        attackTick.instructions.insert(simulateAttackCall, lmbSwing);

        // 2) RMB spin attack action ticker: trigger vanilla 3rd-person arm swing on tick == 12 (exact delay of executeSpinAttack).
        MethodNode spinTick = owner.methods.stream()
            .filter(m -> m.name.equals("lambda$static$3") && m.desc.equals("(L" + playerInternal + ";I)V"))
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("LushsaurMaceItem.lambda$static$3 not found"));

        MethodInsnNode executeSpinCall = null;
        for (AbstractInsnNode insn : spinTick.instructions.toArray()) {
            if (insn instanceof MethodInsnNode call &&
                call.owner.equals(owner.name) &&
                call.name.equals("executeSpinAttack")) {
                executeSpinCall = call;
                break;
            }
        }
        if (executeSpinCall == null) {
            throw new IllegalStateException("Expected executeSpinAttack call in LushsaurMaceItem.lambda$static$3");
        }
        InsnList rmbSwing = new InsnList();
        rmbSwing.add(new VarInsnNode(Opcodes.ALOAD, 0));
        rmbSwing.add(new FieldInsnNode(Opcodes.GETSTATIC, handInternal, "MAIN_HAND", handDesc));
        rmbSwing.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, playerInternal, "m_6674_", "(" + handDesc + ")V", false));
        spinTick.instructions.insert(executeSpinCall, rmbSwing);

        // 3) RMB use(): single-click activation (remove Player.m_6672_ startUsingItem, guard active ATTACK,
        //    and apply 20-tick anti-spam cooldown so server still plays MACE_SPIN sound on first click).
        MethodNode useMethod = owner.methods.stream()
            .filter(m -> m.name.equals("m_7203_"))
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("LushsaurMaceItem.m_7203_ not found"));

        MethodInsnNode startUsingCall = null;
        for (AbstractInsnNode insn : useMethod.instructions.toArray()) {
            if (insn instanceof MethodInsnNode call &&
                call.owner.equals(playerInternal) &&
                call.name.equals("m_6672_")) {
                startUsingCall = call;
                break;
            }
        }
        if (startUsingCall == null) {
            throw new IllegalStateException("Expected Player.m_6672_ call in LushsaurMaceItem.m_7203_");
        }
        AbstractInsnNode loadHand = previousCodeInstruction(startUsingCall);
        AbstractInsnNode loadPlayer = previousCodeInstruction(loadHand);
        if (!(loadHand instanceof VarInsnNode vHand && vHand.getOpcode() == Opcodes.ALOAD && vHand.var == 3) ||
            !(loadPlayer instanceof VarInsnNode vPlayer && vPlayer.getOpcode() == Opcodes.ALOAD && vPlayer.var == 2)) {
            throw new IllegalStateException("Unexpected instructions before Player.m_6672_ in LushsaurMaceItem.m_7203_");
        }
        InsnList activeGuard = new InsnList();
        LabelNode notActive = new LabelNode();
        activeGuard.add(new VarInsnNode(Opcodes.ALOAD, 2));
        activeGuard.add(new TypeInsnNode(Opcodes.INSTANCEOF, syncHolderInternal));
        activeGuard.add(new JumpInsnNode(Opcodes.IFEQ, notActive));
        activeGuard.add(new VarInsnNode(Opcodes.ALOAD, 2));
        activeGuard.add(new TypeInsnNode(Opcodes.CHECKCAST, syncHolderInternal));
        activeGuard.add(new FieldInsnNode(Opcodes.GETSTATIC, owner.name, "ATTACK", actionTypeDesc));
        activeGuard.add(new MethodInsnNode(Opcodes.INVOKEINTERFACE, syncHolderInternal, "getActionByType",
            "(" + actionTypeDesc + ")L" + actionStateInternal + ";", true));
        activeGuard.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, actionStateInternal, "isActive", "()Z", false));
        activeGuard.add(new JumpInsnNode(Opcodes.IFEQ, notActive));
        activeGuard.add(new VarInsnNode(Opcodes.ALOAD, 4));
        activeGuard.add(new MethodInsnNode(Opcodes.INVOKESTATIC, resultHolderInternal, "m_19100_",
            "(Ljava/lang/Object;)L" + resultHolderInternal + ";", false));
        activeGuard.add(new InsnNode(Opcodes.ARETURN));
        activeGuard.add(notActive);
        activeGuard.add(new FrameNode(Opcodes.F_FULL, 5,
            new Object[] { owner.name, levelInternal, playerInternal, handInternal, itemStackInternal },
            0, new Object[0]));
        activeGuard.add(new VarInsnNode(Opcodes.ALOAD, 2));
        activeGuard.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, playerInternal, "m_36335_",
            "()L" + cooldownsInternal + ";", false));
        activeGuard.add(new VarInsnNode(Opcodes.ALOAD, 0));
        activeGuard.add(new IntInsnNode(Opcodes.BIPUSH, 20));
        activeGuard.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, cooldownsInternal, "m_41524_",
            "(L" + itemInternal + ";I)V", false));
        useMethod.instructions.insertBefore(loadPlayer, activeGuard);
        useMethod.instructions.remove(loadPlayer);
        useMethod.instructions.remove(loadHand);
        useMethod.instructions.remove(startUsingCall);

        // 4) Match ATTACK_SPIN ActionState duration (1.0f) with "attack_first_person" clip length,
        //    and switch requireUsing -> requireHolding so a single RMB click completes the full action.
        MethodNode spinFactory = owner.methods.stream()
            .filter(m -> m.name.equals("lambda$static$5"))
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("LushsaurMaceItem.lambda$static$5 not found"));
        boolean matchedDuration = false;
        boolean switchedToHolding = false;
        for (AbstractInsnNode insn : spinFactory.instructions.toArray()) {
            if (insn instanceof LdcInsnNode ldc && ldc.cst instanceof Float f && Math.abs(f - 2.2f) < 0.01f) {
                ldc.cst = Float.valueOf(1.0f);
                matchedDuration = true;
            } else if (insn instanceof MethodInsnNode call &&
                call.owner.equals(actionStateInternal) &&
                call.name.equals("requireUsing")) {
                call.name = "requireHolding";
                switchedToHolding = true;
            }
        }
        if (!matchedDuration) {
            throw new IllegalStateException("Expected 2.2f duration in LushsaurMaceItem.lambda$static$5");
        }
        if (!switchedToHolding) {
            throw new IllegalStateException("Expected ActionState.requireUsing call in LushsaurMaceItem.lambda$static$5");
        }
    }

    private static void patchPusTippedArrowEntity(ClassNode owner) {
        if (owner == null) throw new IllegalStateException("PusTippedArrowEntity class is missing");

        String arrowSuper = "net/minecraft/world/entity/projectile/AbstractArrow";
        String entityInternal = "net/minecraft/world/entity/Entity";
        String mobInternal = "net/minecraft/world/entity/Mob";
        String levelInternal = "net/minecraft/world/level/Level";
        String vec3Internal = "net/minecraft/world/phys/Vec3";
        String aabbInternal = "net/minecraft/world/phys/AABB";
        String randomInternal = "net/minecraft/util/RandomSource";
        String selectorInternal = "net/minecraft/world/entity/EntitySelector";
        String sculkChargeOpts = "net/minecraft/core/particles/SculkChargeParticleOptions";
        String particleOpts = "net/minecraft/core/particles/ParticleOptions";
        String mthInternal = "net/minecraft/util/Mth";
        String listInternal = "java/util/List";

        owner.methods.removeIf(m ->
            (m.name.equals("m_8119_") && m.desc.equals("()V")) ||
            m.name.equals("spawnSculkChargeParticles") ||
            m.name.equals("findNearestHomingMob") ||
            m.name.equals("steerTowardNearestMob")
        );

        // 1) public void m_8119_() { super.m_8119_(); if (inGround) return; if (level.isClientSide) spawnSculkChargeParticles(); steerTowardNearestMob(); }
        MethodNode tick = new MethodNode(Opcodes.ACC_PUBLIC, "m_8119_", "()V", null, null);
        InsnList tc = tick.instructions;
        LabelNode notInGround = new LabelNode();
        LabelNode afterParticles = new LabelNode();
        tc.add(new VarInsnNode(Opcodes.ALOAD, 0));
        tc.add(new MethodInsnNode(Opcodes.INVOKESPECIAL, arrowSuper, "m_8119_", "()V", false));
        tc.add(new VarInsnNode(Opcodes.ALOAD, 0));
        tc.add(new FieldInsnNode(Opcodes.GETFIELD, arrowSuper, "f_36703_", "Z"));
        tc.add(new JumpInsnNode(Opcodes.IFEQ, notInGround));
        tc.add(new InsnNode(Opcodes.RETURN));
        tc.add(notInGround);
        tc.add(new FrameNode(Opcodes.F_FULL, 1, new Object[] { owner.name }, 0, new Object[0]));
        tc.add(new VarInsnNode(Opcodes.ALOAD, 0));
        tc.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, owner.name, "m_9236_", "()L" + levelInternal + ";", false));
        tc.add(new VarInsnNode(Opcodes.ASTORE, 1));
        tc.add(new VarInsnNode(Opcodes.ALOAD, 1));
        tc.add(new FieldInsnNode(Opcodes.GETFIELD, levelInternal, "f_46443_", "Z"));
        tc.add(new JumpInsnNode(Opcodes.IFEQ, afterParticles));
        tc.add(new VarInsnNode(Opcodes.ALOAD, 0));
        tc.add(new MethodInsnNode(Opcodes.INVOKESPECIAL, owner.name, "spawnSculkChargeParticles", "()V", false));
        tc.add(afterParticles);
        tc.add(new FrameNode(Opcodes.F_FULL, 2, new Object[] { owner.name, levelInternal }, 0, new Object[0]));
        tc.add(new VarInsnNode(Opcodes.ALOAD, 0));
        tc.add(new MethodInsnNode(Opcodes.INVOKESPECIAL, owner.name, "steerTowardNearestMob", "()V", false));
        tc.add(new InsnNode(Opcodes.RETURN));
        tick.maxStack = 2;
        tick.maxLocals = 2;
        owner.methods.add(tick);

        // 2) private void spawnSculkChargeParticles()
        MethodNode particles = new MethodNode(Opcodes.ACC_PRIVATE, "spawnSculkChargeParticles", "()V", null, null);
        InsnList pc = particles.instructions;
        LabelNode startLoop = new LabelNode();
        LabelNode loopCond = new LabelNode();
        LabelNode loopDone = new LabelNode();
        pc.add(new VarInsnNode(Opcodes.ALOAD, 0));
        pc.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, owner.name, "m_9236_", "()L" + levelInternal + ";", false));
        pc.add(new VarInsnNode(Opcodes.ASTORE, 1));
        pc.add(new VarInsnNode(Opcodes.ALOAD, 0));
        pc.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, owner.name, "m_20184_", "()L" + vec3Internal + ";", false));
        pc.add(new VarInsnNode(Opcodes.ASTORE, 2));
        // speedSqr >= 0.0025
        pc.add(new VarInsnNode(Opcodes.ALOAD, 2));
        pc.add(new FieldInsnNode(Opcodes.GETFIELD, vec3Internal, "f_82479_", "D"));
        pc.add(new InsnNode(Opcodes.DUP2));
        pc.add(new InsnNode(Opcodes.DMUL));
        pc.add(new VarInsnNode(Opcodes.ALOAD, 2));
        pc.add(new FieldInsnNode(Opcodes.GETFIELD, vec3Internal, "f_82480_", "D"));
        pc.add(new InsnNode(Opcodes.DUP2));
        pc.add(new InsnNode(Opcodes.DMUL));
        pc.add(new InsnNode(Opcodes.DADD));
        pc.add(new VarInsnNode(Opcodes.ALOAD, 2));
        pc.add(new FieldInsnNode(Opcodes.GETFIELD, vec3Internal, "f_82481_", "D"));
        pc.add(new InsnNode(Opcodes.DUP2));
        pc.add(new InsnNode(Opcodes.DMUL));
        pc.add(new InsnNode(Opcodes.DADD));
        pc.add(new LdcInsnNode(Double.valueOf(0.0025d)));
        pc.add(new InsnNode(Opcodes.DCMPL));
        pc.add(new JumpInsnNode(Opcodes.IFGE, startLoop));
        pc.add(new InsnNode(Opcodes.RETURN));
        pc.add(startLoop);
        pc.add(new FrameNode(Opcodes.F_FULL, 3,
            new Object[] { owner.name, levelInternal, vec3Internal }, 0, new Object[0]));
        pc.add(new InsnNode(Opcodes.ICONST_0));
        pc.add(new VarInsnNode(Opcodes.ISTORE, 3));
        pc.add(loopCond);
        pc.add(new FrameNode(Opcodes.F_FULL, 4,
            new Object[] { owner.name, levelInternal, vec3Internal, Opcodes.INTEGER }, 0, new Object[0]));
        pc.add(new VarInsnNode(Opcodes.ILOAD, 3));
        pc.add(new IntInsnNode(Opcodes.BIPUSH, 28));
        pc.add(new JumpInsnNode(Opcodes.IF_ICMPGE, loopDone));
        // double t = i / 27.0;
        pc.add(new VarInsnNode(Opcodes.ILOAD, 3));
        pc.add(new InsnNode(Opcodes.I2D));
        pc.add(new LdcInsnNode(Double.valueOf(0.037037037037037035d)));
        pc.add(new InsnNode(Opcodes.DMUL));
        pc.add(new VarInsnNode(Opcodes.DSTORE, 4));
        // level.m_7106_(new SculkChargeParticleOptions(roll), px, py, pz, vx, vy, vz)
        pc.add(new VarInsnNode(Opcodes.ALOAD, 1));
        pc.add(new TypeInsnNode(Opcodes.NEW, sculkChargeOpts));
        pc.add(new InsnNode(Opcodes.DUP));
        pc.add(new VarInsnNode(Opcodes.ALOAD, 0));
        pc.add(new FieldInsnNode(Opcodes.GETFIELD, entityInternal, "f_19796_", "L" + randomInternal + ";"));
        pc.add(new MethodInsnNode(Opcodes.INVOKEINTERFACE, randomInternal, "m_188501_", "()F", true));
        pc.add(new LdcInsnNode(Float.valueOf(0.5f)));
        pc.add(new InsnNode(Opcodes.FSUB));
        pc.add(new LdcInsnNode(Float.valueOf(0.4f)));
        pc.add(new InsnNode(Opcodes.FMUL));
        pc.add(new MethodInsnNode(Opcodes.INVOKESPECIAL, sculkChargeOpts, "<init>", "(F)V", false));
        // px
        pc.add(new VarInsnNode(Opcodes.ALOAD, 0));
        pc.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, owner.name, "m_20185_", "()D", false));
        pc.add(new VarInsnNode(Opcodes.ALOAD, 2));
        pc.add(new FieldInsnNode(Opcodes.GETFIELD, vec3Internal, "f_82479_", "D"));
        pc.add(new VarInsnNode(Opcodes.DLOAD, 4));
        pc.add(new InsnNode(Opcodes.DMUL));
        pc.add(new InsnNode(Opcodes.DSUB));
        // py
        pc.add(new VarInsnNode(Opcodes.ALOAD, 0));
        pc.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, owner.name, "m_20186_", "()D", false));
        pc.add(new VarInsnNode(Opcodes.ALOAD, 2));
        pc.add(new FieldInsnNode(Opcodes.GETFIELD, vec3Internal, "f_82480_", "D"));
        pc.add(new VarInsnNode(Opcodes.DLOAD, 4));
        pc.add(new InsnNode(Opcodes.DMUL));
        pc.add(new InsnNode(Opcodes.DSUB));
        // pz
        pc.add(new VarInsnNode(Opcodes.ALOAD, 0));
        pc.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, owner.name, "m_20189_", "()D", false));
        pc.add(new VarInsnNode(Opcodes.ALOAD, 2));
        pc.add(new FieldInsnNode(Opcodes.GETFIELD, vec3Internal, "f_82481_", "D"));
        pc.add(new VarInsnNode(Opcodes.DLOAD, 4));
        pc.add(new InsnNode(Opcodes.DMUL));
        pc.add(new InsnNode(Opcodes.DSUB));
        // vx, vy, vz
        pc.add(new VarInsnNode(Opcodes.ALOAD, 2));
        pc.add(new FieldInsnNode(Opcodes.GETFIELD, vec3Internal, "f_82479_", "D"));
        pc.add(new InsnNode(Opcodes.DNEG));
        pc.add(new LdcInsnNode(Double.valueOf(0.08d)));
        pc.add(new InsnNode(Opcodes.DMUL));
        pc.add(new VarInsnNode(Opcodes.ALOAD, 2));
        pc.add(new FieldInsnNode(Opcodes.GETFIELD, vec3Internal, "f_82480_", "D"));
        pc.add(new InsnNode(Opcodes.DNEG));
        pc.add(new LdcInsnNode(Double.valueOf(0.08d)));
        pc.add(new InsnNode(Opcodes.DMUL));
        pc.add(new VarInsnNode(Opcodes.ALOAD, 2));
        pc.add(new FieldInsnNode(Opcodes.GETFIELD, vec3Internal, "f_82481_", "D"));
        pc.add(new InsnNode(Opcodes.DNEG));
        pc.add(new LdcInsnNode(Double.valueOf(0.08d)));
        pc.add(new InsnNode(Opcodes.DMUL));
        pc.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, levelInternal, "m_7106_",
            "(L" + particleOpts + ";DDDDDD)V", false));
        pc.add(new IincInsnNode(3, 1));
        pc.add(new JumpInsnNode(Opcodes.GOTO, loopCond));
        pc.add(loopDone);
        pc.add(new FrameNode(Opcodes.F_FULL, 3,
            new Object[] { owner.name, levelInternal, vec3Internal }, 0, new Object[0]));
        pc.add(new InsnNode(Opcodes.RETURN));
        particles.maxStack = 20;
        particles.maxLocals = 6;
        owner.methods.add(particles);

        // 3) private Mob findNearestHomingMob(double dirX, double dirY, double dirZ)
        MethodNode findMob = new MethodNode(Opcodes.ACC_PRIVATE, "findNearestHomingMob",
            "(DDD)L" + mobInternal + ";", null, null);
        InsnList fc = findMob.instructions;
        LabelNode noVehicle = new LabelNode();
        LabelNode gotVehicle = new LabelNode();
        LabelNode mobLoop = new LabelNode();
        LabelNode mobNext = new LabelNode();
        LabelNode mobDone = new LabelNode();

        fc.add(new VarInsnNode(Opcodes.ALOAD, 0));
        fc.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, owner.name, "m_19749_", "()L" + entityInternal + ";", false));
        fc.add(new VarInsnNode(Opcodes.ASTORE, 7)); // owner
        fc.add(new VarInsnNode(Opcodes.ALOAD, 7));
        fc.add(new JumpInsnNode(Opcodes.IFNULL, noVehicle));
        fc.add(new VarInsnNode(Opcodes.ALOAD, 7));
        fc.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, entityInternal, "m_20202_", "()L" + entityInternal + ";", false));
        fc.add(new JumpInsnNode(Opcodes.GOTO, gotVehicle));
        fc.add(noVehicle);
        fc.add(new FrameNode(Opcodes.F_FULL, 5,
            new Object[] { owner.name, Opcodes.DOUBLE, Opcodes.DOUBLE, Opcodes.DOUBLE, entityInternal },
            0, new Object[0]));
        fc.add(new InsnNode(Opcodes.ACONST_NULL));
        fc.add(gotVehicle);
        fc.add(new FrameNode(Opcodes.F_FULL, 5,
            new Object[] { owner.name, Opcodes.DOUBLE, Opcodes.DOUBLE, Opcodes.DOUBLE, entityInternal },
            1, new Object[] { entityInternal }));
        fc.add(new VarInsnNode(Opcodes.ASTORE, 8)); // ownerVehicle

        // candidates = level.getEntitiesOfClass(Mob.class, getBoundingBox().inflate(18.0), EntitySelector.LIVING_ENTITY_STILL_ALIVE)
        fc.add(new VarInsnNode(Opcodes.ALOAD, 0));
        fc.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, owner.name, "m_9236_", "()L" + levelInternal + ";", false));
        fc.add(new LdcInsnNode(Type.getObjectType(mobInternal)));
        fc.add(new VarInsnNode(Opcodes.ALOAD, 0));
        fc.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, owner.name, "m_20191_", "()L" + aabbInternal + ";", false));
        fc.add(new LdcInsnNode(Double.valueOf(18.0d)));
        fc.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, aabbInternal, "m_82400_", "(D)L" + aabbInternal + ";", false));
        fc.add(new FieldInsnNode(Opcodes.GETSTATIC, selectorInternal, "f_20403_", "Ljava/util/function/Predicate;"));
        fc.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, levelInternal, "m_6443_",
            "(Ljava/lang/Class;L" + aabbInternal + ";Ljava/util/function/Predicate;)L" + listInternal + ";", false));
        fc.add(new VarInsnNode(Opcodes.ASTORE, 9)); // candidates
        fc.add(new InsnNode(Opcodes.ACONST_NULL));
        fc.add(new VarInsnNode(Opcodes.ASTORE, 10)); // bestTarget
        fc.add(new LdcInsnNode(Double.valueOf(Double.MAX_VALUE)));
        fc.add(new VarInsnNode(Opcodes.DSTORE, 11)); // bestDistSqr
        fc.add(new InsnNode(Opcodes.ICONST_0));
        fc.add(new VarInsnNode(Opcodes.ISTORE, 13)); // i

        Object[] loopFrame = new Object[] {
            owner.name, Opcodes.DOUBLE, Opcodes.DOUBLE, Opcodes.DOUBLE,
            entityInternal, entityInternal, listInternal, mobInternal, Opcodes.DOUBLE, Opcodes.INTEGER
        };
        fc.add(mobLoop);
        fc.add(new FrameNode(Opcodes.F_FULL, loopFrame.length, loopFrame, 0, new Object[0]));
        fc.add(new VarInsnNode(Opcodes.ILOAD, 13));
        fc.add(new VarInsnNode(Opcodes.ALOAD, 9));
        fc.add(new MethodInsnNode(Opcodes.INVOKEINTERFACE, listInternal, "size", "()I", true));
        fc.add(new JumpInsnNode(Opcodes.IF_ICMPGE, mobDone));

        // mob = (Mob) candidates.get(i)
        fc.add(new VarInsnNode(Opcodes.ALOAD, 9));
        fc.add(new VarInsnNode(Opcodes.ILOAD, 13));
        fc.add(new MethodInsnNode(Opcodes.INVOKEINTERFACE, listInternal, "get", "(I)Ljava/lang/Object;", true));
        fc.add(new TypeInsnNode(Opcodes.CHECKCAST, mobInternal));
        fc.add(new VarInsnNode(Opcodes.ASTORE, 14)); // mob

        // skip if mob == owner || mob == ownerVehicle
        fc.add(new VarInsnNode(Opcodes.ALOAD, 14));
        fc.add(new VarInsnNode(Opcodes.ALOAD, 7));
        fc.add(new JumpInsnNode(Opcodes.IF_ACMPEQ, mobNext));
        fc.add(new VarInsnNode(Opcodes.ALOAD, 14));
        fc.add(new VarInsnNode(Opcodes.ALOAD, 8));
        fc.add(new JumpInsnNode(Opcodes.IF_ACMPEQ, mobNext));
        // skip if owner != null && mob.isAlliedTo(owner)
        LabelNode notAllied = new LabelNode();
        fc.add(new VarInsnNode(Opcodes.ALOAD, 7));
        fc.add(new JumpInsnNode(Opcodes.IFNULL, notAllied));
        fc.add(new VarInsnNode(Opcodes.ALOAD, 14));
        fc.add(new VarInsnNode(Opcodes.ALOAD, 7));
        fc.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, mobInternal, "m_7307_", "(L" + entityInternal + ";)Z", false));
        fc.add(new JumpInsnNode(Opcodes.IFNE, mobNext));
        fc.add(notAllied);
        fc.add(new FrameNode(Opcodes.F_FULL, 11, new Object[] {
            owner.name, Opcodes.DOUBLE, Opcodes.DOUBLE, Opcodes.DOUBLE,
            entityInternal, entityInternal, listInternal, mobInternal, Opcodes.DOUBLE, Opcodes.INTEGER, mobInternal
        }, 0, new Object[0]));
        // skip if !mob.isAlive() || mob.isSpectator()
        fc.add(new VarInsnNode(Opcodes.ALOAD, 14));
        fc.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, mobInternal, "m_6084_", "()Z", false));
        fc.add(new JumpInsnNode(Opcodes.IFEQ, mobNext));
        fc.add(new VarInsnNode(Opcodes.ALOAD, 14));
        fc.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, mobInternal, "m_5833_", "()Z", false));
        fc.add(new JumpInsnNode(Opcodes.IFNE, mobNext));

        // tx, ty, tz
        fc.add(new VarInsnNode(Opcodes.ALOAD, 14));
        fc.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, mobInternal, "m_20185_", "()D", false));
        fc.add(new VarInsnNode(Opcodes.ALOAD, 0));
        fc.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, owner.name, "m_20185_", "()D", false));
        fc.add(new InsnNode(Opcodes.DSUB));
        fc.add(new VarInsnNode(Opcodes.DSTORE, 15));

        fc.add(new VarInsnNode(Opcodes.ALOAD, 14));
        fc.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, mobInternal, "m_20186_", "()D", false));
        fc.add(new VarInsnNode(Opcodes.ALOAD, 14));
        fc.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, mobInternal, "m_20206_", "()F", false));
        fc.add(new InsnNode(Opcodes.F2D));
        fc.add(new LdcInsnNode(Double.valueOf(0.5d)));
        fc.add(new InsnNode(Opcodes.DMUL));
        fc.add(new InsnNode(Opcodes.DADD));
        fc.add(new VarInsnNode(Opcodes.ALOAD, 0));
        fc.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, owner.name, "m_20186_", "()D", false));
        fc.add(new InsnNode(Opcodes.DSUB));
        fc.add(new VarInsnNode(Opcodes.DSTORE, 17));

        fc.add(new VarInsnNode(Opcodes.ALOAD, 14));
        fc.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, mobInternal, "m_20189_", "()D", false));
        fc.add(new VarInsnNode(Opcodes.ALOAD, 0));
        fc.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, owner.name, "m_20189_", "()D", false));
        fc.add(new InsnNode(Opcodes.DSUB));
        fc.add(new VarInsnNode(Opcodes.DSTORE, 19));

        // distSqr = tx*tx + ty*ty + tz*tz
        fc.add(new VarInsnNode(Opcodes.DLOAD, 15));
        fc.add(new VarInsnNode(Opcodes.DLOAD, 15));
        fc.add(new InsnNode(Opcodes.DMUL));
        fc.add(new VarInsnNode(Opcodes.DLOAD, 17));
        fc.add(new VarInsnNode(Opcodes.DLOAD, 17));
        fc.add(new InsnNode(Opcodes.DMUL));
        fc.add(new InsnNode(Opcodes.DADD));
        fc.add(new VarInsnNode(Opcodes.DLOAD, 19));
        fc.add(new VarInsnNode(Opcodes.DLOAD, 19));
        fc.add(new InsnNode(Opcodes.DMUL));
        fc.add(new InsnNode(Opcodes.DADD));
        fc.add(new VarInsnNode(Opcodes.DSTORE, 21));

        // if (distSqr < 0.0001 || distSqr >= bestDistSqr) continue;
        fc.add(new VarInsnNode(Opcodes.DLOAD, 21));
        fc.add(new LdcInsnNode(Double.valueOf(0.0001d)));
        fc.add(new InsnNode(Opcodes.DCMPG));
        fc.add(new JumpInsnNode(Opcodes.IFLT, mobNext));
        fc.add(new VarInsnNode(Opcodes.DLOAD, 21));
        fc.add(new VarInsnNode(Opcodes.DLOAD, 11));
        fc.add(new InsnNode(Opcodes.DCMPL));
        fc.add(new JumpInsnNode(Opcodes.IFGE, mobNext));

        // dot = (tx*dirX + ty*dirY + tz*dirZ) / Math.sqrt(distSqr); if (dot <= -0.25) continue;
        fc.add(new VarInsnNode(Opcodes.DLOAD, 15));
        fc.add(new VarInsnNode(Opcodes.DLOAD, 1));
        fc.add(new InsnNode(Opcodes.DMUL));
        fc.add(new VarInsnNode(Opcodes.DLOAD, 17));
        fc.add(new VarInsnNode(Opcodes.DLOAD, 3));
        fc.add(new InsnNode(Opcodes.DMUL));
        fc.add(new InsnNode(Opcodes.DADD));
        fc.add(new VarInsnNode(Opcodes.DLOAD, 19));
        fc.add(new VarInsnNode(Opcodes.DLOAD, 5));
        fc.add(new InsnNode(Opcodes.DMUL));
        fc.add(new InsnNode(Opcodes.DADD));
        fc.add(new VarInsnNode(Opcodes.DLOAD, 21));
        fc.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "java/lang/Math", "sqrt", "(D)D", false));
        fc.add(new InsnNode(Opcodes.DDIV));
        fc.add(new LdcInsnNode(Double.valueOf(-0.25d)));
        fc.add(new InsnNode(Opcodes.DCMPL));
        fc.add(new JumpInsnNode(Opcodes.IFLE, mobNext));

        fc.add(new VarInsnNode(Opcodes.DLOAD, 21));
        fc.add(new VarInsnNode(Opcodes.DSTORE, 11));
        fc.add(new VarInsnNode(Opcodes.ALOAD, 14));
        fc.add(new VarInsnNode(Opcodes.ASTORE, 10));

        fc.add(mobNext);
        fc.add(new FrameNode(Opcodes.F_FULL, loopFrame.length, loopFrame, 0, new Object[0]));
        fc.add(new IincInsnNode(13, 1));
        fc.add(new JumpInsnNode(Opcodes.GOTO, mobLoop));

        fc.add(mobDone);
        fc.add(new FrameNode(Opcodes.F_FULL, 9, new Object[] {
            owner.name, Opcodes.DOUBLE, Opcodes.DOUBLE, Opcodes.DOUBLE,
            entityInternal, entityInternal, listInternal, mobInternal, Opcodes.DOUBLE
        }, 0, new Object[0]));
        fc.add(new VarInsnNode(Opcodes.ALOAD, 10));
        fc.add(new InsnNode(Opcodes.ARETURN));
        findMob.maxStack = 10;
        findMob.maxLocals = 23;
        owner.methods.add(findMob);

        // 4) private void steerTowardNearestMob()
        MethodNode steer = new MethodNode(Opcodes.ACC_PRIVATE, "steerTowardNearestMob", "()V", null, null);
        InsnList sc = steer.instructions;
        LabelNode tickOk = new LabelNode();
        LabelNode speedOk = new LabelNode();
        LabelNode targetOk = new LabelNode();
        LabelNode distOk = new LabelNode();
        LabelNode normOk = new LabelNode();

        // if (this.tickCount < 2) return;
        sc.add(new VarInsnNode(Opcodes.ALOAD, 0));
        sc.add(new FieldInsnNode(Opcodes.GETFIELD, entityInternal, "f_19797_", "I"));
        sc.add(new InsnNode(Opcodes.ICONST_2));
        sc.add(new JumpInsnNode(Opcodes.IF_ICMPGE, tickOk));
        sc.add(new InsnNode(Opcodes.RETURN));
        sc.add(tickOk);
        sc.add(new FrameNode(Opcodes.F_FULL, 1, new Object[] { owner.name }, 0, new Object[0]));

        // motion = getDeltaMovement(); speedSqr >= 0.01
        sc.add(new VarInsnNode(Opcodes.ALOAD, 0));
        sc.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, owner.name, "m_20184_", "()L" + vec3Internal + ";", false));
        sc.add(new VarInsnNode(Opcodes.ASTORE, 1));
        sc.add(new VarInsnNode(Opcodes.ALOAD, 1));
        sc.add(new FieldInsnNode(Opcodes.GETFIELD, vec3Internal, "f_82479_", "D"));
        sc.add(new InsnNode(Opcodes.DUP2));
        sc.add(new InsnNode(Opcodes.DMUL));
        sc.add(new VarInsnNode(Opcodes.ALOAD, 1));
        sc.add(new FieldInsnNode(Opcodes.GETFIELD, vec3Internal, "f_82480_", "D"));
        sc.add(new InsnNode(Opcodes.DUP2));
        sc.add(new InsnNode(Opcodes.DMUL));
        sc.add(new InsnNode(Opcodes.DADD));
        sc.add(new VarInsnNode(Opcodes.ALOAD, 1));
        sc.add(new FieldInsnNode(Opcodes.GETFIELD, vec3Internal, "f_82481_", "D"));
        sc.add(new InsnNode(Opcodes.DUP2));
        sc.add(new InsnNode(Opcodes.DMUL));
        sc.add(new InsnNode(Opcodes.DADD));
        sc.add(new VarInsnNode(Opcodes.DSTORE, 2)); // speedSqr
        sc.add(new VarInsnNode(Opcodes.DLOAD, 2));
        sc.add(new LdcInsnNode(Double.valueOf(0.01d)));
        sc.add(new InsnNode(Opcodes.DCMPL));
        sc.add(new JumpInsnNode(Opcodes.IFGE, speedOk));
        sc.add(new InsnNode(Opcodes.RETURN));
        sc.add(speedOk);
        sc.add(new FrameNode(Opcodes.F_FULL, 3,
            new Object[] { owner.name, vec3Internal, Opcodes.DOUBLE }, 0, new Object[0]));

        // speed = Math.sqrt(speedSqr) -> local 4..5
        sc.add(new VarInsnNode(Opcodes.DLOAD, 2));
        sc.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "java/lang/Math", "sqrt", "(D)D", false));
        sc.add(new VarInsnNode(Opcodes.DSTORE, 4));
        // dirX, dirY, dirZ -> locals 6, 8, 10
        sc.add(new VarInsnNode(Opcodes.ALOAD, 1));
        sc.add(new FieldInsnNode(Opcodes.GETFIELD, vec3Internal, "f_82479_", "D"));
        sc.add(new VarInsnNode(Opcodes.DLOAD, 4));
        sc.add(new InsnNode(Opcodes.DDIV));
        sc.add(new VarInsnNode(Opcodes.DSTORE, 6));
        sc.add(new VarInsnNode(Opcodes.ALOAD, 1));
        sc.add(new FieldInsnNode(Opcodes.GETFIELD, vec3Internal, "f_82480_", "D"));
        sc.add(new VarInsnNode(Opcodes.DLOAD, 4));
        sc.add(new InsnNode(Opcodes.DDIV));
        sc.add(new VarInsnNode(Opcodes.DSTORE, 8));
        sc.add(new VarInsnNode(Opcodes.ALOAD, 1));
        sc.add(new FieldInsnNode(Opcodes.GETFIELD, vec3Internal, "f_82481_", "D"));
        sc.add(new VarInsnNode(Opcodes.DLOAD, 4));
        sc.add(new InsnNode(Opcodes.DDIV));
        sc.add(new VarInsnNode(Opcodes.DSTORE, 10));

        // target = findNearestHomingMob(dirX, dirY, dirZ) -> local 12
        sc.add(new VarInsnNode(Opcodes.ALOAD, 0));
        sc.add(new VarInsnNode(Opcodes.DLOAD, 6));
        sc.add(new VarInsnNode(Opcodes.DLOAD, 8));
        sc.add(new VarInsnNode(Opcodes.DLOAD, 10));
        sc.add(new MethodInsnNode(Opcodes.INVOKESPECIAL, owner.name, "findNearestHomingMob", "(DDD)L" + mobInternal + ";", false));
        sc.add(new VarInsnNode(Opcodes.ASTORE, 12));
        sc.add(new VarInsnNode(Opcodes.ALOAD, 12));
        sc.add(new JumpInsnNode(Opcodes.IFNONNULL, targetOk));
        sc.add(new InsnNode(Opcodes.RETURN));
        sc.add(targetOk);
        sc.add(new FrameNode(Opcodes.F_FULL, 8, new Object[] {
            owner.name, vec3Internal, Opcodes.DOUBLE, Opcodes.DOUBLE,
            Opcodes.DOUBLE, Opcodes.DOUBLE, Opcodes.DOUBLE, mobInternal
        }, 0, new Object[0]));

        // tx -> 13, ty -> 15, tz -> 17
        sc.add(new VarInsnNode(Opcodes.ALOAD, 12));
        sc.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, mobInternal, "m_20185_", "()D", false));
        sc.add(new VarInsnNode(Opcodes.ALOAD, 0));
        sc.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, owner.name, "m_20185_", "()D", false));
        sc.add(new InsnNode(Opcodes.DSUB));
        sc.add(new VarInsnNode(Opcodes.DSTORE, 13));

        sc.add(new VarInsnNode(Opcodes.ALOAD, 12));
        sc.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, mobInternal, "m_20186_", "()D", false));
        sc.add(new VarInsnNode(Opcodes.ALOAD, 12));
        sc.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, mobInternal, "m_20206_", "()F", false));
        sc.add(new InsnNode(Opcodes.F2D));
        sc.add(new LdcInsnNode(Double.valueOf(0.5d)));
        sc.add(new InsnNode(Opcodes.DMUL));
        sc.add(new InsnNode(Opcodes.DADD));
        sc.add(new VarInsnNode(Opcodes.ALOAD, 0));
        sc.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, owner.name, "m_20186_", "()D", false));
        sc.add(new InsnNode(Opcodes.DSUB));
        sc.add(new VarInsnNode(Opcodes.DSTORE, 15));

        sc.add(new VarInsnNode(Opcodes.ALOAD, 12));
        sc.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, mobInternal, "m_20189_", "()D", false));
        sc.add(new VarInsnNode(Opcodes.ALOAD, 0));
        sc.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, owner.name, "m_20189_", "()D", false));
        sc.add(new InsnNode(Opcodes.DSUB));
        sc.add(new VarInsnNode(Opcodes.DSTORE, 17));

        // dist = Math.sqrt(tx*tx + ty*ty + tz*tz) -> 19
        sc.add(new VarInsnNode(Opcodes.DLOAD, 13));
        sc.add(new VarInsnNode(Opcodes.DLOAD, 13));
        sc.add(new InsnNode(Opcodes.DMUL));
        sc.add(new VarInsnNode(Opcodes.DLOAD, 15));
        sc.add(new VarInsnNode(Opcodes.DLOAD, 15));
        sc.add(new InsnNode(Opcodes.DMUL));
        sc.add(new InsnNode(Opcodes.DADD));
        sc.add(new VarInsnNode(Opcodes.DLOAD, 17));
        sc.add(new VarInsnNode(Opcodes.DLOAD, 17));
        sc.add(new InsnNode(Opcodes.DMUL));
        sc.add(new InsnNode(Opcodes.DADD));
        sc.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "java/lang/Math", "sqrt", "(D)D", false));
        sc.add(new VarInsnNode(Opcodes.DSTORE, 19));
        sc.add(new VarInsnNode(Opcodes.DLOAD, 19));
        sc.add(new LdcInsnNode(Double.valueOf(0.0001d)));
        sc.add(new InsnNode(Opcodes.DCMPG));
        sc.add(new JumpInsnNode(Opcodes.IFGE, distOk));
        sc.add(new InsnNode(Opcodes.RETURN));
        sc.add(distOk);
        sc.add(new FrameNode(Opcodes.F_FULL, 12, new Object[] {
            owner.name, vec3Internal, Opcodes.DOUBLE, Opcodes.DOUBLE,
            Opcodes.DOUBLE, Opcodes.DOUBLE, Opcodes.DOUBLE, mobInternal,
            Opcodes.DOUBLE, Opcodes.DOUBLE, Opcodes.DOUBLE, Opcodes.DOUBLE
        }, 0, new Object[0]));

        // nx = dirX*0.68 + (tx/dist)*0.32 -> 21, ny -> 23, nz -> 25
        sc.add(new VarInsnNode(Opcodes.DLOAD, 6));
        sc.add(new LdcInsnNode(Double.valueOf(0.68d)));
        sc.add(new InsnNode(Opcodes.DMUL));
        sc.add(new VarInsnNode(Opcodes.DLOAD, 13));
        sc.add(new VarInsnNode(Opcodes.DLOAD, 19));
        sc.add(new InsnNode(Opcodes.DDIV));
        sc.add(new LdcInsnNode(Double.valueOf(0.32d)));
        sc.add(new InsnNode(Opcodes.DMUL));
        sc.add(new InsnNode(Opcodes.DADD));
        sc.add(new VarInsnNode(Opcodes.DSTORE, 21));

        sc.add(new VarInsnNode(Opcodes.DLOAD, 8));
        sc.add(new LdcInsnNode(Double.valueOf(0.68d)));
        sc.add(new InsnNode(Opcodes.DMUL));
        sc.add(new VarInsnNode(Opcodes.DLOAD, 15));
        sc.add(new VarInsnNode(Opcodes.DLOAD, 19));
        sc.add(new InsnNode(Opcodes.DDIV));
        sc.add(new LdcInsnNode(Double.valueOf(0.32d)));
        sc.add(new InsnNode(Opcodes.DMUL));
        sc.add(new InsnNode(Opcodes.DADD));
        sc.add(new VarInsnNode(Opcodes.DSTORE, 23));

        sc.add(new VarInsnNode(Opcodes.DLOAD, 10));
        sc.add(new LdcInsnNode(Double.valueOf(0.68d)));
        sc.add(new InsnNode(Opcodes.DMUL));
        sc.add(new VarInsnNode(Opcodes.DLOAD, 17));
        sc.add(new VarInsnNode(Opcodes.DLOAD, 19));
        sc.add(new InsnNode(Opcodes.DDIV));
        sc.add(new LdcInsnNode(Double.valueOf(0.32d)));
        sc.add(new InsnNode(Opcodes.DMUL));
        sc.add(new InsnNode(Opcodes.DADD));
        sc.add(new VarInsnNode(Opcodes.DSTORE, 25));

        // norm = Math.sqrt(nx*nx + ny*ny + nz*nz) -> 27
        sc.add(new VarInsnNode(Opcodes.DLOAD, 21));
        sc.add(new VarInsnNode(Opcodes.DLOAD, 21));
        sc.add(new InsnNode(Opcodes.DMUL));
        sc.add(new VarInsnNode(Opcodes.DLOAD, 23));
        sc.add(new VarInsnNode(Opcodes.DLOAD, 23));
        sc.add(new InsnNode(Opcodes.DMUL));
        sc.add(new InsnNode(Opcodes.DADD));
        sc.add(new VarInsnNode(Opcodes.DLOAD, 25));
        sc.add(new VarInsnNode(Opcodes.DLOAD, 25));
        sc.add(new InsnNode(Opcodes.DMUL));
        sc.add(new InsnNode(Opcodes.DADD));
        sc.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "java/lang/Math", "sqrt", "(D)D", false));
        sc.add(new VarInsnNode(Opcodes.DSTORE, 27));
        sc.add(new VarInsnNode(Opcodes.DLOAD, 27));
        sc.add(new LdcInsnNode(Double.valueOf(0.0001d)));
        sc.add(new InsnNode(Opcodes.DCMPG));
        sc.add(new JumpInsnNode(Opcodes.IFGE, normOk));
        sc.add(new InsnNode(Opcodes.RETURN));
        sc.add(normOk);
        sc.add(new FrameNode(Opcodes.F_FULL, 16, new Object[] {
            owner.name, vec3Internal, Opcodes.DOUBLE, Opcodes.DOUBLE,
            Opcodes.DOUBLE, Opcodes.DOUBLE, Opcodes.DOUBLE, mobInternal,
            Opcodes.DOUBLE, Opcodes.DOUBLE, Opcodes.DOUBLE, Opcodes.DOUBLE,
            Opcodes.DOUBLE, Opcodes.DOUBLE, Opcodes.DOUBLE, Opcodes.DOUBLE
        }, 0, new Object[0]));

        // vx -> 29, vy -> 31, vz -> 33
        sc.add(new VarInsnNode(Opcodes.DLOAD, 21));
        sc.add(new VarInsnNode(Opcodes.DLOAD, 27));
        sc.add(new InsnNode(Opcodes.DDIV));
        sc.add(new VarInsnNode(Opcodes.DLOAD, 4));
        sc.add(new InsnNode(Opcodes.DMUL));
        sc.add(new VarInsnNode(Opcodes.DSTORE, 29));

        sc.add(new VarInsnNode(Opcodes.DLOAD, 23));
        sc.add(new VarInsnNode(Opcodes.DLOAD, 27));
        sc.add(new InsnNode(Opcodes.DDIV));
        sc.add(new VarInsnNode(Opcodes.DLOAD, 4));
        sc.add(new InsnNode(Opcodes.DMUL));
        sc.add(new VarInsnNode(Opcodes.DSTORE, 31));

        sc.add(new VarInsnNode(Opcodes.DLOAD, 25));
        sc.add(new VarInsnNode(Opcodes.DLOAD, 27));
        sc.add(new InsnNode(Opcodes.DDIV));
        sc.add(new VarInsnNode(Opcodes.DLOAD, 4));
        sc.add(new InsnNode(Opcodes.DMUL));
        sc.add(new VarInsnNode(Opcodes.DSTORE, 33));

        // setDeltaMovement(new Vec3(vx, vy, vz))
        sc.add(new VarInsnNode(Opcodes.ALOAD, 0));
        sc.add(new TypeInsnNode(Opcodes.NEW, vec3Internal));
        sc.add(new InsnNode(Opcodes.DUP));
        sc.add(new VarInsnNode(Opcodes.DLOAD, 29));
        sc.add(new VarInsnNode(Opcodes.DLOAD, 31));
        sc.add(new VarInsnNode(Opcodes.DLOAD, 33));
        sc.add(new MethodInsnNode(Opcodes.INVOKESPECIAL, vec3Internal, "<init>", "(DDD)V", false));
        sc.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, owner.name, "m_20256_", "(L" + vec3Internal + ";)V", false));

        // yaw = (float)(Mth.atan2(vx, vz) * (180.0 / Math.PI)) -> 35
        sc.add(new VarInsnNode(Opcodes.DLOAD, 29));
        sc.add(new VarInsnNode(Opcodes.DLOAD, 33));
        sc.add(new MethodInsnNode(Opcodes.INVOKESTATIC, mthInternal, "m_14136_", "(DD)D", false));
        sc.add(new LdcInsnNode(Double.valueOf(180.0d / Math.PI)));
        sc.add(new InsnNode(Opcodes.DMUL));
        sc.add(new InsnNode(Opcodes.D2F));
        sc.add(new VarInsnNode(Opcodes.FSTORE, 35));

        // pitch = (float)(Mth.atan2(vy, Math.sqrt(vx*vx + vz*vz)) * (180.0 / Math.PI)) -> 36
        sc.add(new VarInsnNode(Opcodes.DLOAD, 31));
        sc.add(new VarInsnNode(Opcodes.DLOAD, 29));
        sc.add(new VarInsnNode(Opcodes.DLOAD, 29));
        sc.add(new InsnNode(Opcodes.DMUL));
        sc.add(new VarInsnNode(Opcodes.DLOAD, 33));
        sc.add(new VarInsnNode(Opcodes.DLOAD, 33));
        sc.add(new InsnNode(Opcodes.DMUL));
        sc.add(new InsnNode(Opcodes.DADD));
        sc.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "java/lang/Math", "sqrt", "(D)D", false));
        sc.add(new MethodInsnNode(Opcodes.INVOKESTATIC, mthInternal, "m_14136_", "(DD)D", false));
        sc.add(new LdcInsnNode(Double.valueOf(180.0d / Math.PI)));
        sc.add(new InsnNode(Opcodes.DMUL));
        sc.add(new InsnNode(Opcodes.D2F));
        sc.add(new VarInsnNode(Opcodes.FSTORE, 36));

        sc.add(new VarInsnNode(Opcodes.ALOAD, 0));
        sc.add(new VarInsnNode(Opcodes.FLOAD, 35));
        sc.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, owner.name, "m_146922_", "(F)V", false));
        sc.add(new VarInsnNode(Opcodes.ALOAD, 0));
        sc.add(new VarInsnNode(Opcodes.FLOAD, 36));
        sc.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, owner.name, "m_146926_", "(F)V", false));
        sc.add(new VarInsnNode(Opcodes.ALOAD, 0));
        sc.add(new VarInsnNode(Opcodes.FLOAD, 35));
        sc.add(new FieldInsnNode(Opcodes.PUTFIELD, entityInternal, "f_19859_", "F"));
        sc.add(new VarInsnNode(Opcodes.ALOAD, 0));
        sc.add(new VarInsnNode(Opcodes.FLOAD, 36));
        sc.add(new FieldInsnNode(Opcodes.PUTFIELD, entityInternal, "f_19860_", "F"));
        sc.add(new InsnNode(Opcodes.RETURN));
        steer.maxStack = 10;
        steer.maxLocals = 37;
        owner.methods.add(steer);
    }

    private static void patchPopZitMessage(ClassNode owner) {
        if (owner == null) throw new IllegalStateException("PopZitMessage class is missing");

        String sculksaurInternal = ROOT + "common/entity/mob/sculksaur/SculksaurEntity";
        String playerInternal = "net/minecraft/world/entity/player/Player";
        String serverPlayerInternal = "net/minecraft/server/level/ServerPlayer";
        String entityInternal = "net/minecraft/world/entity/Entity";
        String damageSourcesInternal = "net/minecraft/world/damagesource/DamageSources";
        String damageSourceInternal = "net/minecraft/world/damagesource/DamageSource";
        String helperDesc = "(L" + sculksaurInternal + ";L" + serverPlayerInternal + ";I)V";

        owner.methods.removeIf(m ->
            m.name.equals("damageSculksaurOnWartPop") && m.desc.equals(helperDesc));

        // private static void damageSculksaurOnWartPop(SculksaurEntity sculksaur, ServerPlayer player, int remainingWarts)
        MethodNode helper = new MethodNode(
            Opcodes.ACC_PRIVATE | Opcodes.ACC_STATIC,
            "damageSculksaurOnWartPop", helperDesc, null, null);
        InsnList hc = helper.instructions;
        hc.add(new VarInsnNode(Opcodes.ALOAD, 0));
        hc.add(new InsnNode(Opcodes.ICONST_0));
        hc.add(new FieldInsnNode(Opcodes.PUTFIELD, entityInternal, "f_19802_", "I"));
        hc.add(new VarInsnNode(Opcodes.ALOAD, 0));
        hc.add(new VarInsnNode(Opcodes.ALOAD, 0));
        hc.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, sculksaurInternal, "m_269291_",
            "()L" + damageSourcesInternal + ";", false));
        hc.add(new VarInsnNode(Opcodes.ALOAD, 1));
        hc.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, damageSourcesInternal, "m_269075_",
            "(L" + playerInternal + ";)L" + damageSourceInternal + ";", false));
        hc.add(new LdcInsnNode(Float.valueOf(1000.0f)));
        hc.add(new InsnNode(Opcodes.ICONST_1));
        hc.add(new IntInsnNode(Opcodes.BIPUSH, 20));
        hc.add(new InsnNode(Opcodes.ICONST_3));
        hc.add(new VarInsnNode(Opcodes.ILOAD, 2));
        hc.add(new InsnNode(Opcodes.IMUL));
        hc.add(new InsnNode(Opcodes.ISUB));
        hc.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "java/lang/Math", "max", "(II)I", false));
        hc.add(new InsnNode(Opcodes.I2F));
        hc.add(new InsnNode(Opcodes.FDIV));
        hc.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, sculksaurInternal, "m_6469_",
            "(L" + damageSourceInternal + ";F)Z", false));
        hc.add(new InsnNode(Opcodes.POP));
        hc.add(new InsnNode(Opcodes.RETURN));
        helper.maxStack = 6;
        helper.maxLocals = 3;
        owner.methods.add(helper);

        MethodNode execute = owner.methods.stream()
            .filter(m -> m.name.equals("execute") && m.desc.equals("(L" + serverPlayerInternal + ";)V"))
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("PopZitMessage.execute not found"));

        VarInsnNode remainingWartsLoad = null;
        for (AbstractInsnNode insn : execute.instructions.toArray()) {
            if (insn instanceof VarInsnNode var && var.getOpcode() == Opcodes.ILOAD && var.var == 7) {
                if (remainingWartsLoad != null) {
                    throw new IllegalStateException("Expected unique ILOAD 7 in PopZitMessage.execute");
                }
                remainingWartsLoad = var;
            }
        }
        if (remainingWartsLoad == null) {
            throw new IllegalStateException("Remaining wart count load (ILOAD 7) not found in PopZitMessage.execute");
        }

        InsnList callDamage = new InsnList();
        callDamage.add(new VarInsnNode(Opcodes.ALOAD, 3));
        callDamage.add(new VarInsnNode(Opcodes.ALOAD, 1));
        callDamage.add(new VarInsnNode(Opcodes.ILOAD, 7));
        callDamage.add(new MethodInsnNode(Opcodes.INVOKESTATIC, owner.name, "damageSculksaurOnWartPop", helperDesc, false));
        execute.instructions.insertBefore(remainingWartsLoad, callDamage);
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
            code.add(new FrameNode(Opcodes.F_FULL, 2,
                new Object[] { owner.name, "org/spongepowered/asm/mixin/injection/callback/CallbackInfo" },
                0, new Object[0]));
            code.add(new InsnNode(Opcodes.RETURN));
            method.maxStack = 2;
            method.maxLocals = 2;
        }
    }


    private static MethodNode requireMethod(ClassNode owner, String name, String descriptor) {
        if (owner == null) throw new IllegalStateException("Missing class for method " + name + descriptor);
        return owner.methods.stream()
            .filter(method -> method.name.equals(name) && method.desc.equals(descriptor))
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("Missing method " + owner.name + "." + name + descriptor));
    }

    private static void patchVoidMethodReturns(MethodNode method, String hookName, String hookDescriptor, boolean saveTag) {
        int patched = 0;
        for (AbstractInsnNode instruction : method.instructions.toArray()) {
            if (instruction.getOpcode() != Opcodes.RETURN) continue;
            InsnList hook = new InsnList();
            hook.add(new VarInsnNode(Opcodes.ALOAD, 0));
            if (saveTag) hook.add(new VarInsnNode(Opcodes.ALOAD, 1));
            hook.add(new MethodInsnNode(Opcodes.INVOKESTATIC, LEGACY_HOOKS, hookName, hookDescriptor, false));
            method.instructions.insertBefore(instruction, hook);
            patched++;
        }
        if (patched == 0) throw new IllegalStateException("No RETURN found in " + method.name + method.desc);
        method.maxStack = Math.max(method.maxStack, saveTag ? 2 : 1);
    }

    private static void patchLushsaurRuntimeHooks(ClassNode owner) {
        if (owner == null) throw new IllegalStateException("LushsaurEntity is missing");
        MethodNode tick = requireMethod(owner, "m_8119_", "()V");
        MethodNode save = requireMethod(owner, "m_7380_", "(Lnet/minecraft/nbt/CompoundTag;)V");
        MethodNode load = requireMethod(owner, "m_7378_", "(Lnet/minecraft/nbt/CompoundTag;)V");
        patchVoidMethodReturns(tick, "lushsaurTick", "(Ljava/lang/Object;)V", false);
        patchVoidMethodReturns(save, "saveLush", "(Ljava/lang/Object;Ljava/lang/Object;)V", true);
        patchVoidMethodReturns(load, "loadLush", "(Ljava/lang/Object;Ljava/lang/Object;)V", true);

        String feedDescriptor =
            "(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/ItemStack;)Lnet/minecraft/world/InteractionResult;";
        MethodNode feed = requireMethod(owner, "eatFromHand", feedDescriptor);
        MethodInsnNode superFeed = null;
        for (AbstractInsnNode instruction : feed.instructions.toArray()) {
            if (instruction instanceof MethodInsnNode call &&
                call.getOpcode() == Opcodes.INVOKESPECIAL &&
                call.name.equals("eatFromHand") && call.desc.equals(feedDescriptor)) {
                if (superFeed != null) throw new IllegalStateException("Multiple superclass eatFromHand calls");
                superFeed = call;
            }
        }
        if (superFeed == null) throw new IllegalStateException("Lushsaur eatFromHand superclass call is missing");
        AbstractInsnNode store = nextCodeInstruction(superFeed);
        if (!(store instanceof VarInsnNode resultStore) || resultStore.getOpcode() != Opcodes.ASTORE) {
            throw new IllegalStateException("Unexpected Lushsaur eatFromHand result storage");
        }
        InsnList hook = new InsnList();
        hook.add(new VarInsnNode(Opcodes.ALOAD, 0));
        hook.add(new VarInsnNode(Opcodes.ALOAD, 1));
        hook.add(new VarInsnNode(Opcodes.ALOAD, 2));
        hook.add(new VarInsnNode(Opcodes.ALOAD, resultStore.var));
        hook.add(new MethodInsnNode(Opcodes.INVOKESTATIC, LEGACY_HOOKS, "afterLushFeed",
            "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", false));
        hook.add(new TypeInsnNode(Opcodes.CHECKCAST, "net/minecraft/world/InteractionResult"));
        hook.add(new VarInsnNode(Opcodes.ASTORE, resultStore.var));
        feed.instructions.insert(store, hook);
        feed.maxStack = Math.max(feed.maxStack, 4);
        System.out.println("PATCH LUSHS warning, loyalty, feed, and persistence hooks installed");
    }


    private static void patchLushsaurStrictWarningActions(
        ClassNode lushsaur, ClassNode lushsaurAI, ClassNode chargeGoal
    ) {
        if (lushsaur == null || lushsaurAI == null || chargeGoal == null) {
            throw new IllegalStateException("Lushsaur strict warning action dependency is missing");
        }

        String damageDescriptor =
            "(Lnet/minecraft/world/damagesource/DamageSource;F)Z";
        MethodNode hurt = requireMethod(lushsaur, "m_6469_", damageDescriptor);
        String actionSequence = "com/astemir/astemirlib/re/common/action/ActionSequence";
        String actionState = "com/astemir/astemirlib/re/common/action/ActionState";
        int removedBuriedStomps = 0;
        boolean emergeRetained = false;
        for (AbstractInsnNode instruction : hurt.instructions.toArray()) {
            if (!(instruction instanceof MethodInsnNode call) ||
                call.getOpcode() != Opcodes.INVOKEVIRTUAL ||
                !call.owner.equals(actionSequence) ||
                !call.name.equals("addAction") ||
                !call.desc.equals("(L" + actionState + ";)L" + actionSequence + ";")) {
                continue;
            }
            AbstractInsnNode fieldInstruction = previousCodeInstruction(instruction);
            AbstractInsnNode receiverLoad = previousCodeInstruction(fieldInstruction);
            if (fieldInstruction instanceof FieldInsnNode field &&
                field.getOpcode() == Opcodes.GETFIELD &&
                field.owner.equals(lushsaur.name) &&
                field.name.equals("actionAttackTailStomp") &&
                receiverLoad instanceof VarInsnNode load &&
                load.getOpcode() == Opcodes.ALOAD && load.var == 0) {
                hurt.instructions.remove(receiverLoad);
                hurt.instructions.remove(fieldInstruction);
                hurt.instructions.remove(instruction);
                removedBuriedStomps++;
            }
        }
        for (AbstractInsnNode instruction : hurt.instructions.toArray()) {
            if (instruction instanceof FieldInsnNode field &&
                field.getOpcode() == Opcodes.GETFIELD &&
                field.owner.equals(lushsaur.name) &&
                field.name.equals("actionEmerge")) {
                emergeRetained = true;
            }
        }
        if (removedBuriedStomps != 1 || !emergeRetained) {
            throw new IllegalStateException("Failed to remove only the buried-hit tail stomp while preserving emergence");
        }

        String wrappedAction = ROOT + "common/entity/ai/goal/action/WrappedAction";
        String setupDescriptor =
            "(L" + lushsaur.name + ";Lnet/minecraft/world/entity/ai/goal/GoalSelector;Lnet/minecraft/world/entity/ai/goal/GoalSelector;)V";
        MethodNode setup = requireMethod(lushsaurAI, "setupAI", setupDescriptor);
        TypeInsnNode meleeArray = null;
        AbstractInsnNode arraySize = null;
        for (AbstractInsnNode instruction : setup.instructions.toArray()) {
            if (!(instruction instanceof TypeInsnNode type) ||
                type.getOpcode() != Opcodes.ANEWARRAY || !type.desc.equals(wrappedAction)) {
                continue;
            }
            AbstractInsnNode size = previousCodeInstruction(instruction);
            if (size == null || size.getOpcode() != Opcodes.ICONST_4) {
                continue;
            }
            if (meleeArray != null) {
                throw new IllegalStateException("Multiple four-entry Lushsaur melee action arrays found");
            }
            meleeArray = type;
            arraySize = size;
        }
        if (meleeArray == null || arraySize == null) {
            throw new IllegalStateException("Lushsaur four-entry melee action array is missing");
        }

        List<AbstractInsnNode> stompStores = new ArrayList<>();
        for (AbstractInsnNode instruction : setup.instructions.toArray()) {
            if (instruction.getOpcode() != Opcodes.AASTORE) continue;
            AbstractInsnNode value = previousCodeInstruction(instruction);
            AbstractInsnNode index = previousCodeInstruction(value);
            AbstractInsnNode duplicate = previousCodeInstruction(index);
            if (value instanceof VarInsnNode load &&
                load.getOpcode() == Opcodes.ALOAD && load.var == 6 &&
                index != null && index.getOpcode() == Opcodes.ICONST_3 &&
                duplicate != null && duplicate.getOpcode() == Opcodes.DUP) {
                stompStores.add(duplicate);
                stompStores.add(index);
                stompStores.add(value);
                stompStores.add(instruction);
            }
        }
        if (stompStores.size() != 4) {
            throw new IllegalStateException("Expected the fourth Lushsaur melee action (tail stomp) exactly once");
        }
        setup.instructions.set(arraySize, new InsnNode(Opcodes.ICONST_3));
        for (AbstractInsnNode instruction : stompStores) {
            setup.instructions.remove(instruction);
        }

        FieldNode mobField = chargeGoal.fields.stream()
            .filter(field -> field.name.equals("mob") &&
                field.desc.equals("Lnet/minecraft/world/entity/PathfinderMob;"))
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("GoalChargeMeleeAttack.mob field is missing"));
        MethodNode warning = requireMethod(chargeGoal, "transitionToWarning", "()V");
        List<FieldInsnNode> warnActionReads = new ArrayList<>();
        for (AbstractInsnNode instruction : warning.instructions.toArray()) {
            if (instruction instanceof FieldInsnNode field &&
                field.getOpcode() == Opcodes.GETFIELD &&
                field.owner.equals(chargeGoal.name) &&
                field.name.equals("warnAction")) {
                warnActionReads.add(field);
            }
        }
        if (warnActionReads.size() != 2) {
            throw new IllegalStateException(
                "Expected null-check and invocation warnAction reads, found " + warnActionReads.size());
        }
        FieldInsnNode warnActionField = warnActionReads.get(0);
        AbstractInsnNode loadThis = previousCodeInstruction(warnActionField);
        AbstractInsnNode nullCheck = nextCodeInstruction(warnActionField);
        if (!(loadThis instanceof VarInsnNode load) ||
            load.getOpcode() != Opcodes.ALOAD || load.var != 0 ||
            !(nullCheck instanceof JumpInsnNode skipNull) ||
            skipNull.getOpcode() != Opcodes.IFNULL) {
            throw new IllegalStateException("Unexpected GoalChargeMeleeAttack warning action branch");
        }

        InsnList gate = new InsnList();
        gate.add(new VarInsnNode(Opcodes.ALOAD, 0));
        gate.add(new FieldInsnNode(Opcodes.GETFIELD, chargeGoal.name, mobField.name, mobField.desc));
        gate.add(new TypeInsnNode(Opcodes.INSTANCEOF, lushsaur.name));
        gate.add(new JumpInsnNode(Opcodes.IFNE, skipNull.label));
        warning.instructions.insertBefore(loadThis, gate);
        warning.maxStack = Math.max(warning.maxStack, 1);

        MethodNode speedGetter = requireMethod(lushsaur, "getSpeedBoostTicks", "()I");
        clearMethod(speedGetter);
        speedGetter.instructions.add(new InsnNode(Opcodes.ICONST_0));
        speedGetter.instructions.add(new InsnNode(Opcodes.IRETURN));
        speedGetter.maxStack = 1;
        speedGetter.maxLocals = 1;

        MethodNode speedSetter = requireMethod(lushsaur, "setSpeedBoostTicks", "(I)V");
        clearMethod(speedSetter);
        speedSetter.instructions.add(new InsnNode(Opcodes.RETURN));
        speedSetter.maxStack = 0;
        speedSetter.maxLocals = 2;

        String feedDescriptor =
            "(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/ItemStack;)Lnet/minecraft/world/InteractionResult;";
        MethodNode feed = requireMethod(lushsaur, "eatFromHand", feedDescriptor);
        int removedFeedBoost = 0;
        for (AbstractInsnNode instruction : feed.instructions.toArray()) {
            if (!(instruction instanceof MethodInsnNode call) ||
                call.getOpcode() != Opcodes.INVOKEVIRTUAL ||
                !call.owner.equals(lushsaur.name) ||
                !call.name.equals("setSpeedBoostTicks") ||
                !call.desc.equals("(I)V")) {
                continue;
            }
            AbstractInsnNode ticks = previousCodeInstruction(instruction);
            AbstractInsnNode receiver = previousCodeInstruction(ticks);
            if (!(ticks instanceof IntInsnNode amount) || amount.getOpcode() != Opcodes.SIPUSH ||
                amount.operand != 1200 ||
                !(receiver instanceof VarInsnNode feedReceiver) ||
                feedReceiver.getOpcode() != Opcodes.ALOAD || feedReceiver.var != 0) {
                throw new IllegalStateException("Unexpected Lushsaur feed speed-boost setter shape");
            }
            feed.instructions.set(instruction, new InsnNode(Opcodes.POP2));
            removedFeedBoost++;
        }
        if (removedFeedBoost != 1) {
            throw new IllegalStateException("Expected the single 1200-tick feed speed boost to be removed");
        }

        MethodNode shearable = requireMethod(lushsaur, "m_6220_", "()Z");
        clearMethod(shearable);
        shearable.instructions.add(new InsnNode(Opcodes.ICONST_0));
        shearable.instructions.add(new InsnNode(Opcodes.IRETURN));
        shearable.maxStack = 1;
        shearable.maxLocals = 1;

        MethodNode shear = requireMethod(
            lushsaur, "m_5851_", "(Lnet/minecraft/sounds/SoundSource;)V");
        clearMethod(shear);
        shear.instructions.add(new InsnNode(Opcodes.RETURN));
        shear.maxStack = 0;
        shear.maxLocals = 2;

        MethodNode setSheared = requireMethod(lushsaur, "setSheared", "(Z)V");
        clearMethod(setSheared);
        setSheared.instructions.add(new InsnNode(Opcodes.RETURN));
        setSheared.maxStack = 0;
        setSheared.maxLocals = 2;

        MethodNode isSheared = requireMethod(lushsaur, "isSheared", "()Z");
        clearMethod(isSheared);
        isSheared.instructions.add(new InsnNode(Opcodes.ICONST_0));
        isSheared.instructions.add(new InsnNode(Opcodes.IRETURN));
        isSheared.maxStack = 1;
        isSheared.maxLocals = 1;

        System.out.println("PATCH LUSHS warning-only roar/tail stomp; removed feed speed boost and shearing");
    }

    private static void patchChorusoatlRuntimeHooks(ClassNode owner) {
        if (owner == null) throw new IllegalStateException("ChorusoatlEntity is missing");
        MethodNode tick = requireMethod(owner, "m_8119_", "()V");
        MethodNode save = requireMethod(owner, "m_7380_", "(Lnet/minecraft/nbt/CompoundTag;)V");
        MethodNode load = requireMethod(owner, "m_7378_", "(Lnet/minecraft/nbt/CompoundTag;)V");
        patchVoidMethodReturns(tick, "chorusoatlTick", "(Ljava/lang/Object;)V", false);
        patchVoidMethodReturns(save, "saveChorusoatl", "(Ljava/lang/Object;Ljava/lang/Object;)V", true);
        patchVoidMethodReturns(load, "loadChorusoatl", "(Ljava/lang/Object;Ljava/lang/Object;)V", true);

        String phase = ROOT + "common/entity/base/Flyer$FlightPhase";
        MethodNode setPhase = requireMethod(owner, "setFlightPhase", "(L" + phase + ";)V");
        LabelNode proceed = new LabelNode();
        InsnList guard = new InsnList();
        guard.add(new VarInsnNode(Opcodes.ALOAD, 0));
        guard.add(new VarInsnNode(Opcodes.ALOAD, 1));
        guard.add(new MethodInsnNode(Opcodes.INVOKESTATIC, LEGACY_HOOKS,
            "shouldSuppressChorusoatlFlightPhase", "(Ljava/lang/Object;Ljava/lang/Object;)Z", false));
        guard.add(new JumpInsnNode(Opcodes.IFEQ, proceed));
        guard.add(new InsnNode(Opcodes.RETURN));
        guard.add(proceed);
        guard.add(new FrameNode(Opcodes.F_SAME, 0, null, 0, null));
        setPhase.instructions.insert(guard);
        setPhase.maxStack = Math.max(setPhase.maxStack, 2);
        System.out.println("PATCH CHORUS autonomous landing and perched-state hooks installed");
    }

    private static void verifyChorusoatlAnimationHooks(ClassNode owner) {
        if (owner == null) throw new IllegalStateException("ChorusoatlClient is missing");
        String chorus = ROOT + "common/entity/mob/chorusoatl/ChorusoatlEntity";
        String actionState = "com/astemir/astemirlib/re/common/action/ActionState";
        String condition = "com/astemir/astemirlib/re/core/function/Condition";
        String actionStateCallSite = "(L" + actionState + ";)L" + condition + ";";
        String airStateCallSite = "(L" + chorus + ";)L" + condition + ";";

        MethodNode locomotion = owner.methods.stream()
            .filter(method -> method.name.equals("buildLocomotionState") &&
                method.desc.startsWith("(L" + chorus + ";)"))
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("ChorusoatlClient.buildLocomotionState is missing"));

        int actionStateReferences = 0;
        int airMethodReferences = 0;
        for (AbstractInsnNode instruction : locomotion.instructions) {
            if (!(instruction instanceof InvokeDynamicInsnNode dynamic) ||
                !dynamic.name.equals("check")) continue;

            boolean actionStateSite = dynamic.desc.equals(actionStateCallSite);
            boolean airStateSite = dynamic.desc.equals(airStateCallSite);
            if (!actionStateSite && !airStateSite) continue;

            for (Object argument : dynamic.bsmArgs) {
                if (!(argument instanceof Handle handle) ||
                    handle.getTag() != Opcodes.H_INVOKEVIRTUAL) continue;

                if (actionStateSite &&
                    handle.getOwner().equals(actionState) &&
                    handle.getName().equals("isActiveWithoutFadeOut") &&
                    handle.getDesc().equals("()Z")) {
                    actionStateReferences++;
                }

                if (airStateSite &&
                    handle.getOwner().equals(chorus) &&
                    handle.getName().equals("isFlying") &&
                    handle.getDesc().equals("()Z")) {
                    airMethodReferences++;
                }
            }
        }

        if (actionStateReferences != 2) {
            throw new IllegalStateException("Expected the original two ActionState.isActiveWithoutFadeOut " +
                "references in Chorusoatl locomotion, found " + actionStateReferences);
        }
        if (airMethodReferences != 1) {
            throw new IllegalStateException("Expected the original ChorusoatlEntity.isFlying " +
                "reference in locomotion, found " + airMethodReferences);
        }
        if (owner.methods.stream().anyMatch(method ->
            method.name.equals("legacy$chorusoatlActionStateAnimationActive") ||
                method.name.equals("legacy$chorusoatlAirState"))) {
            throw new IllegalStateException("Obsolete vertical-animation bridges remain in ChorusoatlClient");
        }
        System.out.println("VERIFY CHORUS original UP/DOWN and isFlying animation method references are preserved");
    }

    private static void patchChorusoatlGoalSuppression(ClassNode owner, String hookName) {
        if (owner == null) throw new IllegalStateException("Chorusoatl goal class is missing for " + hookName);
        FieldNode mobField = owner.fields.stream()
            .filter(field -> field.name.equals("mob"))
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("Missing mob field in " + owner.name));
        int patchedMethods = 0;
        for (MethodNode method : owner.methods) {
            if (!(method.name.equals("m_8036_") || method.name.equals("m_8045_")) ||
                !method.desc.equals("()Z")) continue;
            int returns = 0;
            for (AbstractInsnNode instruction : method.instructions.toArray()) {
                if (instruction.getOpcode() != Opcodes.IRETURN) continue;
                InsnList gate = new InsnList();
                gate.add(new VarInsnNode(Opcodes.ALOAD, 0));
                gate.add(new FieldInsnNode(Opcodes.GETFIELD, owner.name, mobField.name, mobField.desc));
                gate.add(new MethodInsnNode(Opcodes.INVOKESTATIC, LEGACY_HOOKS,
                    hookName, "(Ljava/lang/Object;)Z", false));
                gate.add(new InsnNode(Opcodes.ICONST_1));
                gate.add(new InsnNode(Opcodes.IXOR));
                if (hookName.equals("shouldSuppressChorusPackFollow") && method.name.equals("m_8045_")) {
                    gate.add(new VarInsnNode(Opcodes.ALOAD, 0));
                    gate.add(new FieldInsnNode(Opcodes.GETFIELD, owner.name, mobField.name, mobField.desc));
                    gate.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL,
                        "net/minecraft/world/entity/Mob", "m_21573_",
                        "()Lnet/minecraft/world/entity/ai/navigation/PathNavigation;", false));
                    gate.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL,
                        "net/minecraft/world/entity/ai/navigation/PathNavigation", "m_26571_", "()Z", false));
                    gate.add(new InsnNode(Opcodes.ICONST_1));
                    gate.add(new InsnNode(Opcodes.IXOR));
                }
                gate.add(new InsnNode(Opcodes.IAND));
                method.instructions.insertBefore(instruction, gate);
                returns++;
            }
            if (returns == 0) throw new IllegalStateException("No boolean return in " + owner.name + "." + method.name);
            method.maxStack = Math.max(method.maxStack, 3);
            patchedMethods++;
        }
        if (patchedMethods != 2) {
            throw new IllegalStateException("Expected start/continue gates in " + owner.name + ", found " + patchedMethods);
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
        code.add(new InsnNode(Opcodes.FRETURN));

        method.maxStack = 2;
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



    private static void patchSculksaurMultipart(ClassNode owner, Map<String, ClassNode> nodes) {
        if (owner == null) throw new IllegalStateException("SculksaurEntity class is missing");

        String partBase = ROOT + "common/entity/base/";
        String simplePart = partBase + "SimplePart";
        String partInterface = partBase + "IPartEntity";
        String multipartInterface = partBase + "IMultipartEntity";
        String forgePart = ROOT + "entity/ForgeEntityPart";
        String forgeMultipartMixin = ROOT + "mixin/forge/ForgeEntityMultipartMixin";
        String entityAccessor = ROOT + "mixin/EntityAccessor";
        String simplePartDescriptor = "L" + simplePart + ";";
        String partDescriptor = "L" + partInterface + ";";
        String partsDescriptor = "[" + partDescriptor;

        for (String required : List.of(
            simplePart, partInterface, multipartInterface, forgePart,
            forgeMultipartMixin, entityAccessor,
            ROOT + "platform/Services",
            ROOT + "platform/services/ICommonHelper",
            ROOT + "platform/ForgeCommonHelper"
        )) {
            if (!nodes.containsKey(required)) {
                throw new IllegalStateException("Sculksaur multipart dependency is missing: " + required);
            }
        }

        ClassNode simplePartNode = nodes.get(simplePart);
        requireMethod(simplePartNode, "<init>",
            "(Lnet/minecraft/world/entity/LivingEntity;FFDDD)V");
        requireMethod(simplePartNode, "tick", "()V");
        requireMethod(simplePartNode, "collectEntities",
            "(Lnet/minecraft/world/entity/Entity;[L" + simplePart + ";)[" + partDescriptor);

        requireMethod(nodes.get(ROOT + "platform/ForgeCommonHelper"), "makePartEntity",
            "(Lnet/minecraft/world/entity/Entity;FF)" + partDescriptor);

        ClassNode forgePartNode = nodes.get(forgePart);
        if (!"net/minecraftforge/entity/PartEntity".equals(forgePartNode.superName) ||
            forgePartNode.interfaces == null || !forgePartNode.interfaces.contains(partInterface)) {
            throw new IllegalStateException("ForgeEntityPart is not the expected Forge PartEntity adapter");
        }

        ClassNode mixinNode = nodes.get(forgeMultipartMixin);
        requireMethod(mixinNode, "getParts", "()[Lnet/minecraftforge/entity/PartEntity;");
        requireMethod(mixinNode, "isMultipartEntity", "()Z");

        boolean hasMultipartInterface = owner.interfaces != null && owner.interfaces.contains(multipartInterface);
        boolean hasMultipartFields = owner.fields.stream().anyMatch(field ->
            Set.of("neck1", "neck2", "tail1", "tail2", "parts").contains(field.name));
        boolean hasMultipartMethods = owner.methods.stream().anyMatch(method ->
            method.name.equals("getIParts") ||
            method.name.equals("hurtFromPart") ||
            method.name.equals("interactFromPart"));
        if (hasMultipartInterface || hasMultipartFields || hasMultipartMethods) {
            throw new IllegalStateException("Sculksaur multipart implementation is already partly present; refusing to double-patch");
        }

        if (owner.interfaces == null) owner.interfaces = new ArrayList<>();
        owner.interfaces.add(multipartInterface);

        for (String fieldName : List.of("neck1", "neck2", "tail1", "tail2")) {
            owner.fields.add(new FieldNode(
                Opcodes.ACC_PRIVATE | Opcodes.ACC_FINAL,
                fieldName, simplePartDescriptor, null, null));
        }
        owner.fields.add(new FieldNode(
            Opcodes.ACC_PRIVATE | Opcodes.ACC_FINAL,
            "parts", partsDescriptor, null, null));

        String constructorDescriptor =
            "(Lnet/minecraft/world/entity/EntityType;Lnet/minecraft/world/level/Level;)V";
        MethodNode constructor = findUniqueMethod(owner, "<init>", constructorDescriptor);
        AbstractInsnNode constructorReturn = null;
        int returnCount = 0;
        for (AbstractInsnNode instruction : constructor.instructions.toArray()) {
            if (instruction.getOpcode() == Opcodes.RETURN) {
                constructorReturn = instruction;
                returnCount++;
            }
        }
        if (returnCount != 1 || constructorReturn == null) {
            throw new IllegalStateException("Sculksaur constructor does not have exactly one RETURN");
        }

        InsnList initializeParts = new InsnList();
        addSculksaurSimplePart(initializeParts, owner.name, simplePart,
            "neck1", 2.0f, 2.5f, 0.0d, 2.0999999046325684d, 2.200000047683716d);
        addSculksaurSimplePart(initializeParts, owner.name, simplePart,
            "neck2", 2.0f, 2.0f, 0.0d, 3.5d, 4.099999904632568d);
        addSculksaurSimplePart(initializeParts, owner.name, simplePart,
            "tail1", 1.5f, 2.0f, 0.0d, 2.0d, -2.200000047683716d);
        addSculksaurSimplePart(initializeParts, owner.name, simplePart,
            "tail2", 1.0f, 1.0f, 0.0d, 2.5d, -3.9000000953674316d);

        initializeParts.add(new VarInsnNode(Opcodes.ALOAD, 0));
        initializeParts.add(new VarInsnNode(Opcodes.ALOAD, 0));
        initializeParts.add(new InsnNode(Opcodes.ICONST_4));
        initializeParts.add(new TypeInsnNode(Opcodes.ANEWARRAY, simplePart));
        String[] partFields = {"neck1", "neck2", "tail1", "tail2"};
        for (int index = 0; index < partFields.length; index++) {
            initializeParts.add(new InsnNode(Opcodes.DUP));
            initializeParts.add(new InsnNode(Opcodes.ICONST_0 + index));
            initializeParts.add(new VarInsnNode(Opcodes.ALOAD, 0));
            initializeParts.add(new FieldInsnNode(Opcodes.GETFIELD,
                owner.name, partFields[index], simplePartDescriptor));
            initializeParts.add(new InsnNode(Opcodes.AASTORE));
        }
        initializeParts.add(new MethodInsnNode(Opcodes.INVOKESTATIC,
            simplePart, "collectEntities",
            "(Lnet/minecraft/world/entity/Entity;[L" + simplePart + ";)[" + partDescriptor,
            false));
        initializeParts.add(new FieldInsnNode(Opcodes.PUTFIELD,
            owner.name, "parts", partsDescriptor));
        constructor.instructions.insertBefore(constructorReturn, initializeParts);

        MethodNode tick = findUniqueMethod(owner, "m_8119_", "()V");
        MethodInsnNode superTick = null;
        int superTickCount = 0;
        String parentClass = ROOT + "common/entity/base/AbstractComplexMonster";
        for (AbstractInsnNode instruction : tick.instructions.toArray()) {
            if (instruction instanceof MethodInsnNode call &&
                call.getOpcode() == Opcodes.INVOKESPECIAL &&
                call.owner.equals(parentClass) &&
                call.name.equals("m_8119_") &&
                call.desc.equals("()V")) {
                superTick = call;
                superTickCount++;
            }
        }
        if (superTickCount != 1 || superTick == null) {
            throw new IllegalStateException("Sculksaur tick does not contain exactly one AbstractComplexMonster tick call");
        }

        InsnList tickParts = new InsnList();
        for (String fieldName : partFields) {
            tickParts.add(new VarInsnNode(Opcodes.ALOAD, 0));
            tickParts.add(new FieldInsnNode(Opcodes.GETFIELD,
                owner.name, fieldName, simplePartDescriptor));
            tickParts.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL,
                simplePart, "tick", "()V", false));
        }
        tick.instructions.insert(superTick, tickParts);

        MethodNode getParts = new MethodNode(
            Opcodes.ACC_PUBLIC, "getIParts", "()" + partsDescriptor, null, null);
        getParts.instructions.add(new VarInsnNode(Opcodes.ALOAD, 0));
        getParts.instructions.add(new FieldInsnNode(Opcodes.GETFIELD,
            owner.name, "parts", partsDescriptor));
        getParts.instructions.add(new InsnNode(Opcodes.ARETURN));
        getParts.maxStack = 1;
        getParts.maxLocals = 1;
        owner.methods.add(getParts);

        String damageSource = "net/minecraft/world/damagesource/DamageSource";
        String hurtDescriptor =
            "(" + partDescriptor + "L" + damageSource + ";F)Z";
        MethodNode hurt = new MethodNode(
            Opcodes.ACC_PUBLIC, "hurtFromPart", hurtDescriptor, null, null);
        hurt.instructions.add(new VarInsnNode(Opcodes.ALOAD, 0));
        hurt.instructions.add(new VarInsnNode(Opcodes.ALOAD, 2));
        hurt.instructions.add(new VarInsnNode(Opcodes.FLOAD, 3));
        hurt.instructions.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL,
            owner.name, "m_6469_", "(L" + damageSource + ";F)Z", false));
        hurt.instructions.add(new InsnNode(Opcodes.IRETURN));
        hurt.maxStack = 3;
        hurt.maxLocals = 4;
        owner.methods.add(hurt);

        String player = "net/minecraft/world/entity/player/Player";
        String interactionHand = "net/minecraft/world/InteractionHand";
        String interactionResult = "net/minecraft/world/InteractionResult";
        String interactDescriptor =
            "(" + partDescriptor + "L" + player + ";L" + interactionHand + ";)L" + interactionResult + ";";
        MethodNode interact = new MethodNode(
            Opcodes.ACC_PUBLIC, "interactFromPart", interactDescriptor, null, null);
        interact.instructions.add(new VarInsnNode(Opcodes.ALOAD, 0));
        interact.instructions.add(new VarInsnNode(Opcodes.ALOAD, 2));
        interact.instructions.add(new VarInsnNode(Opcodes.ALOAD, 3));
        interact.instructions.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL,
            owner.name, "m_6071_",
            "(L" + player + ";L" + interactionHand + ";)L" + interactionResult + ";", false));
        interact.instructions.add(new InsnNode(Opcodes.ARETURN));
        interact.maxStack = 3;
        interact.maxLocals = 4;
        owner.methods.add(interact);

        System.out.println("PATCH SCULKSAUR multipart: two neck parts, two tail parts, Forge hitbox delegation");
    }

    private static void addSculksaurSimplePart(
        InsnList code, String owner, String simplePart, String fieldName,
        float width, float height, double offsetX, double offsetY, double offsetZ
    ) {
        code.add(new VarInsnNode(Opcodes.ALOAD, 0));
        code.add(new TypeInsnNode(Opcodes.NEW, simplePart));
        code.add(new InsnNode(Opcodes.DUP));
        code.add(new VarInsnNode(Opcodes.ALOAD, 0));
        code.add(new LdcInsnNode(Float.valueOf(width)));
        code.add(new LdcInsnNode(Float.valueOf(height)));
        code.add(new LdcInsnNode(Double.valueOf(offsetX)));
        code.add(new LdcInsnNode(Double.valueOf(offsetY)));
        code.add(new LdcInsnNode(Double.valueOf(offsetZ)));
        code.add(new MethodInsnNode(Opcodes.INVOKESPECIAL,
            simplePart, "<init>",
            "(Lnet/minecraft/world/entity/LivingEntity;FFDDD)V", false));
        code.add(new FieldInsnNode(Opcodes.PUTFIELD,
            owner, fieldName, "L" + simplePart + ";"));
    }

    private static MethodNode findUniqueMethod(ClassNode owner, String name, String descriptor) {
        List<MethodNode> matches = owner.methods.stream()
            .filter(method -> method.name.equals(name) && method.desc.equals(descriptor))
            .toList();
        if (matches.size() != 1) {
            throw new IllegalStateException("Expected exactly one " + owner.name + "." +
                name + descriptor + ", found " + matches.size());
        }
        return matches.get(0);
    }

    private static void patchSculksaurExplosionEffects(ClassNode owner) {
        if (owner == null) throw new IllegalStateException("SculksaurEffects class is missing");

        String contextInternal =
            "com/astemir/astemirlib/re/client/model/core/animation/event/AnimationEvent$Context";
        String entityInternal = "net/minecraft/world/entity/Entity";
        String finalDescriptor = "(L" + contextInternal + ";)V";
        MethodNode finalEvent = owner.methods.stream()
            .filter(method -> method.name.equals("lambda$explosionFinalEvent$12") &&
                method.desc.equals(finalDescriptor))
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("Sculksaur final Sonic Boom event is missing"));

        clearMethod(finalEvent);
        InsnList finalCode = finalEvent.instructions;
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
    }

    private static void patchLushsaurRestParticles(ClassNode owner) {
        if (owner == null) throw new IllegalStateException("LushsaurEffects class is missing");
        MethodNode clinit = owner.methods.stream()
            .filter(method -> method.name.equals("<clinit>") && method.desc.equals("()V"))
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("LushsaurEffects static initializer is missing"));
        FieldInsnNode restEmitter = null;
        int references = 0;
        for (AbstractInsnNode instruction : clinit.instructions.toArray()) {
            if (instruction instanceof FieldInsnNode field &&
                field.getOpcode() == Opcodes.GETSTATIC &&
                field.owner.equals(owner.name) && field.name.equals("EMITTER_REST")) {
                restEmitter = field;
                references++;
            }
        }
        if (references != 1 || restEmitter == null) {
            throw new IllegalStateException("Expected one Lushsaur sleep particle emitter");
        }
        AbstractInsnNode start = previousCodeInstruction(restEmitter);
        AbstractInsnNode locator = nextCodeInstruction(restEmitter);
        AbstractInsnNode spawn = nextCodeInstruction(locator);
        AbstractInsnNode addEvent = nextCodeInstruction(spawn);
        if (start == null || start.getOpcode() != Opcodes.FCONST_0 ||
            !(locator instanceof LdcInsnNode key) || !"head".equals(key.cst) ||
            !(spawn instanceof MethodInsnNode spawnCall) ||
            !spawnCall.owner.equals("com/astemir/astemirlib/re/client/model/core/animation/event/AnimationEvent") ||
            !spawnCall.name.equals("spawnParticle") ||
            !(addEvent instanceof MethodInsnNode add) ||
            !add.name.equals("addEvent")) {
            throw new IllegalStateException("Unexpected Lushsaur REST_EFFECTS particle event");
        }
        removeInclusive(clinit.instructions, start, addEvent);
    }

    private static void patchLushsaurRunParticles(ClassNode owner) {
        if (owner == null) throw new IllegalStateException("LushsaurEffects class is missing");
        String contextDescriptor =
            "(Lcom/astemir/astemirlib/re/client/model/core/animation/event/AnimationEvent$Context;)V";
        MethodNode runTrail = owner.methods.stream()
            .filter(method -> method.name.equals("lambda$static$3") && method.desc.equals(contextDescriptor))
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("Lushsaur run particle callback is missing"));
        clearMethod(runTrail);
        runTrail.instructions.add(new InsnNode(Opcodes.RETURN));
        runTrail.maxStack = 0;
        runTrail.maxLocals = 1;
    }

    private static void patchChorusoatlFootstepParticles(ClassNode owner) {
        if (owner == null) throw new IllegalStateException("ChorusoatlEffects class is missing");
        MethodNode clinit = owner.methods.stream()
            .filter(method -> method.name.equals("<clinit>") && method.desc.equals("()V"))
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("ChorusoatlEffects static initializer is missing"));
        FieldInsnNode walkTrack = null;
        for (AbstractInsnNode instruction : clinit.instructions.toArray()) {
            if (instruction instanceof FieldInsnNode field &&
                field.getOpcode() == Opcodes.PUTSTATIC &&
                field.owner.equals(owner.name) && field.name.equals("WALK_EFFECTS")) {
                walkTrack = field;
                break;
            }
        }
        if (walkTrack == null) throw new IllegalStateException("Chorusoatl WALK_EFFECTS track is missing");
        AbstractInsnNode walkStart = null;
        for (AbstractInsnNode cursor = previousCodeInstruction(walkTrack);
             cursor != null; cursor = previousCodeInstruction(cursor)) {
            if (cursor instanceof MethodInsnNode call &&
                call.getOpcode() == Opcodes.INVOKESTATIC &&
                call.owner.equals("com/astemir/astemirlib/re/client/model/core/animation/event/AnimationEventTrack") &&
                call.name.equals("of")) {
                walkStart = cursor;
                break;
            }
        }
        if (walkStart == null) throw new IllegalStateException("Chorusoatl WALK_EFFECTS start is missing");

        List<FieldInsnNode> stompEmitters = new ArrayList<>();
        for (AbstractInsnNode cursor = walkStart; cursor != walkTrack; cursor = cursor.getNext()) {
            if (cursor instanceof FieldInsnNode field &&
                field.getOpcode() == Opcodes.GETSTATIC &&
                field.owner.equals(owner.name) && field.name.equals("EMITTER_STOMP")) {
                stompEmitters.add(field);
            }
        }
        if (stompEmitters.size() != 2) {
            throw new IllegalStateException("Expected two Chorusoatl walking stomp particle events");
        }

        FieldInsnNode firstEmitter = stompEmitters.get(0);
        AbstractInsnNode firstLocator = nextCodeInstruction(firstEmitter);
        AbstractInsnNode firstSpawn = nextCodeInstruction(firstLocator);
        if (!(firstLocator instanceof LdcInsnNode firstName) || !"claw2".equals(firstName.cst) ||
            !(firstSpawn instanceof MethodInsnNode firstSpawnCall) ||
            !firstSpawnCall.name.equals("spawnParticle")) {
            throw new IllegalStateException("Unexpected first Chorusoatl footstep particle event");
        }
        clinit.instructions.remove(firstEmitter);
        clinit.instructions.remove(firstLocator);
        clinit.instructions.remove(firstSpawn);

        MethodInsnNode combine = null;
        for (AbstractInsnNode cursor = walkStart; cursor != walkTrack; cursor = cursor.getNext()) {
            if (cursor instanceof MethodInsnNode call &&
                call.getOpcode() == Opcodes.INVOKEINTERFACE &&
                call.owner.equals("com/astemir/astemirlib/re/client/model/core/animation/event/AnimationEvent") &&
                call.name.equals("and")) {
                combine = call;
                break;
            }
        }
        if (combine == null) throw new IllegalStateException("Chorusoatl footstep particle/sound combiner is missing");
        clinit.instructions.remove(combine);

        FieldInsnNode secondEmitter = stompEmitters.get(1);
        AbstractInsnNode secondLocator = nextCodeInstruction(secondEmitter);
        AbstractInsnNode secondSpawn = nextCodeInstruction(secondLocator);
        AbstractInsnNode secondTime = previousCodeInstruction(secondEmitter);
        AbstractInsnNode secondAdd = nextCodeInstruction(secondSpawn);
        if (!(secondTime instanceof LdcInsnNode time) ||
            !(time.cst instanceof Float value) || Math.abs(value - 1.04f) > 0.00001f ||
            !(secondLocator instanceof LdcInsnNode secondName) || !"claw1".equals(secondName.cst) ||
            !(secondSpawn instanceof MethodInsnNode secondSpawnCall) ||
            !secondSpawnCall.name.equals("spawnParticle") ||
            !(secondAdd instanceof MethodInsnNode add) || !add.name.equals("addEvent")) {
            throw new IllegalStateException("Unexpected second Chorusoatl footstep particle event");
        }
        removeInclusive(clinit.instructions, secondTime, secondAdd);
    }

    private static void patchSculksaurFootstepParticles(ClassNode owner) {
        if (owner == null) throw new IllegalStateException("SculksaurEffects class is missing");
        String contextInternal =
            "com/astemir/astemirlib/re/client/model/core/animation/event/AnimationEvent$Context";
        String descriptor =
            "(Lcom/astemir/astemirlib/re/client/particle/ParticleEmitterConfig;Ljava/lang/String;" +
            "Lcom/astemir/astemirlib/re/client/particle/ParticleEmitterConfig;Ljava/lang/String;" +
            "L" + contextInternal + ";)V";
        MethodNode step = owner.methods.stream()
            .filter(method -> method.name.equals("lambda$stepEvent$10") && method.desc.equals(descriptor))
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("Sculksaur footstep callback is missing"));
        clearMethod(step);
        InsnList code = step.instructions;
        String entityInternal = ROOT + "common/entity/mob/sculksaur/SculksaurEntity";
        code.add(new VarInsnNode(Opcodes.ALOAD, 4));
        code.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, contextInternal,
            "getInstance", "()Ljava/lang/Object;", false));
        code.add(new TypeInsnNode(Opcodes.CHECKCAST, entityInternal));
        code.add(new VarInsnNode(Opcodes.ASTORE, 5));
        code.add(new VarInsnNode(Opcodes.ALOAD, 5));
        code.add(new LdcInsnNode(20.0f));
        code.add(new MethodInsnNode(Opcodes.INVOKESTATIC,
            ROOT + "client/util/ShakeUtil", "shakeStep", "(Lnet/minecraft/world/entity/Entity;F)V", false));
        code.add(new VarInsnNode(Opcodes.ALOAD, 4));
        code.add(new FieldInsnNode(Opcodes.GETSTATIC,
            ROOT + "common/sound/ADSoundEvents", "SCULKSAUR_STEP",
            "L" + ROOT + "util/ResourceSupplier;"));
        code.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL,
            ROOT + "util/ResourceSupplier", "get", "()Ljava/lang/Object;", false));
        code.add(new TypeInsnNode(Opcodes.CHECKCAST, "net/minecraft/sounds/SoundEvent"));
        code.add(new FieldInsnNode(Opcodes.GETSTATIC,
            "net/minecraft/sounds/SoundSource", "NEUTRAL", "Lnet/minecraft/sounds/SoundSource;"));
        code.add(new InsnNode(Opcodes.FCONST_1));
        code.add(new LdcInsnNode(0.8f));
        code.add(new VarInsnNode(Opcodes.ALOAD, 5));
        code.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL,
            entityInternal, "m_217043_", "()Lnet/minecraft/util/RandomSource;", false));
        code.add(new MethodInsnNode(Opcodes.INVOKEINTERFACE,
            "net/minecraft/util/RandomSource", "m_188501_", "()F", true));
        code.add(new LdcInsnNode(0.4f));
        code.add(new InsnNode(Opcodes.FMUL));
        code.add(new InsnNode(Opcodes.FADD));
        code.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, contextInternal,
            "playSound", "(Lnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FF)V", false));
        code.add(new InsnNode(Opcodes.RETURN));
        step.maxStack = 5;
        step.maxLocals = 6;
    }

    private static void patchTailPhysics(ClassNode owner, Set<String> tailBones, int expectedRemoved) {
        if (owner == null) throw new IllegalStateException("Tail physics renderer is missing");

        List<MethodNode> setupMethods = owner.methods.stream()
            .filter(method -> method.name.equals("setupPhysics"))
            .toList();
        if (setupMethods.size() != 1) {
            throw new IllegalStateException(owner.name + " must have exactly one setupPhysics method");
        }

        MethodNode setup = setupMethods.get(0);
        String chainOwner =
            "com/astemir/astemirlib/re/client/model/core/system/module/PhysicsModule$PhysicsChain";
        String addBoneDescriptor =
            "(Ljava/lang/String;Lcom/astemir/astemirlib/re/client/model/core/system/module/PhysicsModule$PhysicsProperties;)" +
            "Lcom/astemir/astemirlib/re/client/model/core/system/module/PhysicsModule$PhysicsChain;";
        int removed = 0;

        for (AbstractInsnNode instruction : setup.instructions.toArray()) {
            if (!(instruction instanceof MethodInsnNode call) ||
                call.getOpcode() != Opcodes.INVOKEVIRTUAL ||
                !call.owner.equals(chainOwner) ||
                !call.name.equals("addBone") ||
                !call.desc.equals(addBoneDescriptor)) continue;

            AbstractInsnNode candidate = call.getPrevious();
            LdcInsnNode boneNameLoad = null;
            for (int searched = 0; candidate != null && searched < 4; searched++) {
                while (candidate != null && candidate.getOpcode() < 0) {
                    candidate = candidate.getPrevious();
                }
                if (candidate == null) break;
                if (candidate instanceof LdcInsnNode constant &&
                    constant.cst instanceof String name && tailBones.contains(name)) {
                    boneNameLoad = constant;
                    break;
                }
                candidate = candidate.getPrevious();
            }

            if (boneNameLoad == null) continue;

            List<AbstractInsnNode> operandSetup = new ArrayList<>();
            for (AbstractInsnNode cursor = boneNameLoad.getNext(); cursor != null && cursor != call;
                 cursor = cursor.getNext()) {
                if (cursor.getOpcode() >= 0) operandSetup.add(cursor);
            }

            boolean localProperties = operandSetup.size() == 1 &&
                operandSetup.get(0) instanceof VarInsnNode load &&
                load.getOpcode() == Opcodes.ALOAD;
            boolean createdProperties = operandSetup.size() == 2 &&
                operandSetup.get(0) instanceof VarInsnNode moduleLoad &&
                moduleLoad.getOpcode() == Opcodes.ALOAD &&
                operandSetup.get(1) instanceof MethodInsnNode createProperties &&
                createProperties.getOpcode() == Opcodes.INVOKEVIRTUAL &&
                createProperties.name.equals("createProperties") &&
                createProperties.desc.equals("()Lcom/astemir/astemirlib/re/client/model/core/system/module/PhysicsModule$PhysicsProperties;");

            if (!localProperties && !createdProperties) {
                throw new IllegalStateException(owner.name + " has an unexpected addBone operand sequence for " +
                    boneNameLoad.cst);
            }

            for (AbstractInsnNode operand : operandSetup) setup.instructions.remove(operand);
            setup.instructions.remove(boneNameLoad);
            setup.instructions.remove(call);
            removed++;
        }

        if (removed != expectedRemoved) {
            throw new IllegalStateException(owner.name + " expected to disable " + expectedRemoved +
                " tail physics bones, but disabled " + removed);
        }
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

    private static void patchLushsaurOpenPlayerInventory(ClassNode owner) {
        if (owner == null) throw new IllegalStateException("LocalPlayerMixin class is missing");
        String descriptor =
            "(Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V";
        owner.methods.removeIf(method ->
            method.name.equals("archiesDinoMod$openLushsaurPlayerInventory") &&
                method.desc.equals(descriptor));

        MethodNode method = new MethodNode(
            Opcodes.ACC_PUBLIC,
            "archiesDinoMod$openLushsaurPlayerInventory",
            descriptor,
            null,
            null
        );
        InsnList code = method.instructions;
        LabelNode done = new LabelNode();
        String localPlayer = "net/minecraft/client/player/LocalPlayer";
        String lushsaur = ROOT + "common/entity/mob/lushsaur/LushsaurEntity";
        String minecraft = "net/minecraft/client/Minecraft";
        String inventoryScreen = "net/minecraft/client/gui/screens/inventory/InventoryScreen";
        String screen = "net/minecraft/client/gui/screens/Screen";
        String callbackInfo = "org/spongepowered/asm/mixin/injection/callback/CallbackInfo";

        code.add(new VarInsnNode(Opcodes.ALOAD, 0));
        code.add(new TypeInsnNode(Opcodes.CHECKCAST, localPlayer));
        code.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL,
            localPlayer, "m_20202_", "()Lnet/minecraft/world/entity/Entity;", false));
        code.add(new TypeInsnNode(Opcodes.INSTANCEOF, lushsaur));
        code.add(new JumpInsnNode(Opcodes.IFEQ, done));

        code.add(new MethodInsnNode(Opcodes.INVOKESTATIC,
            minecraft, "m_91087_", "()L" + minecraft + ";", false));
        code.add(new TypeInsnNode(Opcodes.NEW, inventoryScreen));
        code.add(new InsnNode(Opcodes.DUP));
        code.add(new VarInsnNode(Opcodes.ALOAD, 0));
        code.add(new TypeInsnNode(Opcodes.CHECKCAST, "net/minecraft/world/entity/player/Player"));
        code.add(new MethodInsnNode(Opcodes.INVOKESPECIAL,
            inventoryScreen, "<init>", "(Lnet/minecraft/world/entity/player/Player;)V", false));
        code.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL,
            minecraft, "m_91152_", "(L" + screen + ";)V", false));
        code.add(new VarInsnNode(Opcodes.ALOAD, 1));
        code.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL,
            callbackInfo, "cancel", "()V", false));

        code.add(done);
        code.add(new FrameNode(Opcodes.F_FULL, 2,
            new Object[] { owner.name, callbackInfo }, 0, new Object[0]));
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
        inject.values.add(List.of("m_108628_"));
        inject.values.add("at");
        inject.values.add(List.of(at));
        inject.values.add("cancellable");
        inject.values.add(Boolean.TRUE);
        inject.values.add("remap");
        inject.values.add(Boolean.FALSE);
        method.visibleAnnotations = new ArrayList<>();
        method.visibleAnnotations.add(inject);

        method.maxStack = 4;
        method.maxLocals = 2;
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
        if (NETWORK_MESSAGES.contains(name)) return true;

        return name.equals(ROOT + "common/items/weapon/LushsaurMaceItem") ||
            name.startsWith(ROOT + "common/items/util/ADWeaponItem") ||
            name.equals(ROOT + "common/items/PusArrowItem") ||
            name.equals(ROOT + "common/entity/projectile/PusTippedArrowEntity") ||
            name.equals(ROOT + "client/CommonClient$1") ||
            name.startsWith(ROOT + "client/model/attachable/LushsaurMaceAttachable") ||
            name.startsWith(ROOT + "common/entity/base/") ||
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
        if (providerLambdas != 2) {
            throw new IllegalStateException("Expected one NoopRenderer and one Pus Arrow renderer provider lambda, found " + providerLambdas);
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