import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.jar.*;
import java.util.zip.*;

public final class JarPruner {
    private static final String ROOT = "com/eternal_creations/archies_dino_mod/";

    private static final Map<String, Set<String>> REGISTRY_FIELDS = Map.ofEntries(
        Map.entry(ROOT + "common/items/ADItems", Set.of(
            "LUSH_CORE", "CHORUS_FOSSIL", "SCULK_TOXIN",
            "LUSHSAUR_EGG", "CHORUSOATL_EGG",
            "LUSHSAUR_SPAWN_EGG", "CHORUSOATL_SPAWN_EGG", "SCULKSAUR_SPAWN_EGG"
        )),
        Map.entry(ROOT + "common/block/ADBlocks", Set.of("LUSHSAUR_EGG", "CHORUSOATL_EGG")),
        Map.entry(ROOT + "common/block/entity/ADBlockEntityTypes", Set.of("LUSHSAUR_EGG", "CHORUSOATL_EGG")),
        Map.entry(ROOT + "common/entity/ADEntityTypes", Set.of(
            "SPAWN_INFO", "LUSHSAUR", "CHORUSOATL", "SCULKSAUR", "CHORUSOATL_SPIT"
        )),
        Map.entry(ROOT + "common/entity/ADSensorTypes", Set.of("SCULKSAUR_SENSOR")),
        Map.entry(ROOT + "common/entity/ADActivities", Set.of("CHASE", "SCREAM", "FLEE")),
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
            "LUSHSAUR_BURY", "LUSHSAUR_EAT", "LUSHSAUR_REST", "LUSHSAUR_ROAR",
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
        Map.entry(ROOT + "client/model/registry/ADBlockEntityModels", Set.of("LUSHSAUR_EGG", "CHORUSOATL_EGG"))
    );

    private static final Set<String> CREATIVE_ITEMS = Set.of(
        "LUSH_CORE", "CHORUS_FOSSIL", "SCULK_TOXIN",
        "LUSHSAUR_EGG", "CHORUSOATL_EGG",
        "LUSHSAUR_SPAWN_EGG", "CHORUSOATL_SPAWN_EGG", "SCULKSAUR_SPAWN_EGG"
    );

    private static final Set<String> NETWORK_MESSAGES = Set.of(
        ROOT + "networking/serverbound/MountControlMessage",
        ROOT + "networking/clientbound/SyncSettingsMessage"
    );

    private static final Set<String> ROOT_CLASSES = Set.of(
        ROOT + "ArchiesDinosaurs",
        ROOT + "ArchiesDinosaurs$ModBusEvents",
        ROOT + "CommonClass",
        ROOT + "Constants",
        ROOT + "client/ADClientModBus",
        ROOT + "client/ADKeyMappings",
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
        ROOT + "mixin/BlockMixin",
        ROOT + "mixin/BreedGoalAccessor",
        ROOT + "mixin/CriteriaTriggersAccessor",
        ROOT + "mixin/DamageEnchantmentMixin",
        ROOT + "mixin/DamageSourcesMixin",
        ROOT + "mixin/EnchantmentCategoryMixin",
        ROOT + "mixin/EnchantmentHelperMixin",
        ROOT + "mixin/EntityAccessor",
        ROOT + "mixin/EntityMixin",
        ROOT + "mixin/LivingEntityMixin",
        ROOT + "mixin/PlayerInterfaceMixin",
        ROOT + "mixin/PlayerListMixin",
        ROOT + "mixin/PlayerMixin",
        ROOT + "mixin/ServerPlayerInterfaceMixin",
        ROOT + "mixin/VibrationSystemListenerMixin",
        ROOT + "mixin/sculksaur/PrimaryLevelDataMixin",
        ROOT + "mixin/sculksaur/SculkShriekerBlockEntityMixin",
        ROOT + "mixin/sculksaur/WalkNodeEvaluatorAccessor",
        ROOT + "mixin/sculksaur/WardenMixin",
        ROOT + "mixin/MultiplayerGameModeMixin",
        ROOT + "mixin/client/CameraMixin",
        ROOT + "mixin/client/ClientAdvancementsAccessor",
        ROOT + "mixin/client/KeyMappingMixin",
        ROOT + "mixin/client/LevelRendererMixin",
        ROOT + "mixin/client/LivingEntityRendererMixin",
        ROOT + "mixin/client/LocalPlayerMixin",
        ROOT + "mixin/client/MinecraftMixin",
        ROOT + "mixin/forge/CustomStructurePieceMixin"
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
        patchCommonClient(nodes.get(ROOT + "client/CommonClient"));
        patchPlayerAnimations(nodes.get(ROOT + "client/animation/player/ADPlayerAnimations"));
        patchCreativeTab(nodes.get(ROOT + "common/tab/ADCreativeTabs"));
        patchNetworkHelper(nodes.get(ROOT + "platform/ForgeNetworkHelper"));

        Map<String, byte[]> transformedClasses = new HashMap<>();
        for (Map.Entry<String, ClassNode> entry : nodes.entrySet()) {
            ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
            entry.getValue().accept(writer);
            transformedClasses.put(entry.getKey() + ".class", writer.toByteArray());
        }

        Set<String> roots = new LinkedHashSet<>(ROOT_CLASSES);
        roots.addAll(MIXIN_ROOTS);
        addServiceRoots(allEntries, roots);

        Set<String> retained = dependencyClosure(roots, nodes);
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
        for (MethodNode method : owner.methods) {
            if (!method.name.equals("<clinit>")) continue;

            List<AbstractInsnNode> segment = new ArrayList<>();
            for (AbstractInsnNode insn : method.instructions.toArray()) {
                if (insn instanceof FieldInsnNode field &&
                    field.getOpcode() == Opcodes.PUTSTATIC &&
                    field.owner.equals(owner.name)) {

                    if (!keepFields.contains(field.name)) {
                        for (AbstractInsnNode remove : segment) {
                            method.instructions.remove(remove);
                        }
                        method.instructions.insertBefore(field, new InsnNode(Opcodes.ACONST_NULL));
                    }
                    segment.clear();
                } else {
                    segment.add(insn);
                }
            }
        }
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
            }
            if (method.name.equals("registerEntityRenderers") && method.desc.equals("()V")) {
                removeFieldRegistrationBlock(method, ROOT + "common/entity/ADEntityTypes", "PUS_TIPPED_ARROW",
                    owner.name, "registerEntityRenderer");
            }
        }
    }

    private static void patchPlayerAnimations(ClassNode owner) {
        if (owner == null) return;
        for (MethodNode method : owner.methods) {
            if (!method.name.equals("init") || !method.desc.equals("()V")) continue;
            for (AbstractInsnNode insn : method.instructions.toArray()) {
                if (insn instanceof MethodInsnNode call &&
                    call.getOpcode() == Opcodes.INVOKESTATIC &&
                    call.owner.equals(ROOT + "client/animation/player/attachable/LushsaurMaceAnimations")) {
                    method.instructions.remove(call);
                }
            }
        }
    }

    private static void patchCreativeTab(ClassNode owner) {
        if (owner == null) return;
        for (MethodNode method : owner.methods) {
            List<MethodInsnNode> addCalls = new ArrayList<>();
            for (AbstractInsnNode insn : method.instructions.toArray()) {
                if (insn instanceof MethodInsnNode call &&
                    call.owner.contains("CreativeModeTab$Output") &&
                    (call.name.equals("m_246326_") || call.name.equals("accept"))) {
                    addCalls.add(call);
                }
            }

            MethodInsnNode previousAdd = null;
            for (MethodInsnNode add : addCalls) {
                String itemField = null;
                for (AbstractInsnNode cursor = add.getPrevious(); cursor != null; cursor = cursor.getPrevious()) {
                    if (cursor instanceof MethodInsnNode prior &&
                        prior.owner.contains("CreativeModeTab$Output") &&
                        (prior.name.equals("m_246326_") || prior.name.equals("accept"))) {
                        break;
                    }
                    if (cursor instanceof FieldInsnNode field &&
                        field.getOpcode() == Opcodes.GETSTATIC &&
                        field.owner.equals(ROOT + "common/items/ADItems")) {
                        itemField = field.name;
                        break;
                    }
                }

                if (itemField != null && !CREATIVE_ITEMS.contains(itemField)) {
                    AbstractInsnNode start = previousAdd == null
                        ? method.instructions.getFirst()
                        : previousAdd.getNext();
                    removeInclusive(method.instructions, start, add);
                } else {
                    previousAdd = add;
                }
            }
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
                    removeInclusive(method.instructions, start, registration);
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

    private static Set<String> dependencyClosure(Set<String> roots, Map<String, ClassNode> nodes) {
        Set<String> retained = new LinkedHashSet<>();
        Deque<String> queue = new ArrayDeque<>(roots);

        while (!queue.isEmpty()) {
            String name = queue.removeFirst();
            if (!retained.add(name)) continue;
            ClassNode node = nodes.get(name);
            if (node == null) continue;

            Set<String> refs = new LinkedHashSet<>();
            addType(refs, node.superName);
            if (node.interfaces != null) refs.addAll(node.interfaces);
            if (node.fields != null) {
                for (FieldNode field : node.fields) {
                    addDescriptor(refs, field.desc);
                }
            }
            for (MethodNode method : node.methods) {
                addDescriptor(refs, method.desc);
                if (method.exceptions != null) refs.addAll(method.exceptions);

                for (AbstractInsnNode insn : method.instructions.toArray()) {
                    if (insn instanceof TypeInsnNode type) addType(refs, type.desc);
                    else if (insn instanceof FieldInsnNode field) {
                        addType(refs, field.owner);
                        addDescriptor(refs, field.desc);
                    } else if (insn instanceof MethodInsnNode call) {
                        addType(refs, call.owner);
                        addDescriptor(refs, call.desc);
                    } else if (insn instanceof MultiANewArrayInsnNode multi) {
                        addDescriptor(refs, multi.desc);
                    } else if (insn instanceof LdcInsnNode ldc) {
                        addConstant(refs, ldc.cst);
                    } else if (insn instanceof InvokeDynamicInsnNode indy) {
                        addDescriptor(refs, indy.desc);
                        addConstant(refs, indy.bsm);
                        for (Object arg : indy.bsmArgs) addConstant(refs, arg);
                    }
                }
            }

            for (String ref : refs) {
                if (ref.startsWith(ROOT) && nodes.containsKey(ref) && !retained.contains(ref)) {
                    queue.addLast(ref);
                }
            }
        }
        return retained;
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