#!/usr/bin/env python3
import json
import re
import struct
import sys
import zipfile
from pathlib import Path

if len(sys.argv) != 3:
    raise SystemExit("Usage: prune_binary_jar.py <classes-pruned.jar> <legacy.jar>")

source = Path(sys.argv[1])
target = Path(sys.argv[2])
if not source.is_file():
    raise SystemExit(f"Input JAR not found: {source}")

OLD_NAMESPACE = "ec_ad"
NEW_NAMESPACE = "archie_dinosaurs"
SOURCE_MOD = f"assets/{OLD_NAMESPACE}/"
SOURCE_DATA = f"data/{OLD_NAMESPACE}/"
MOD = f"assets/{NEW_NAMESPACE}/"
DATA = f"data/{NEW_NAMESPACE}/"
ITEMS = {
    "lush_core",
    "chorus_fossil",
    "sculk_toxin",
    "lushsaur_egg",
    "chorusoatl_egg",
    "lushsaur_spawn_egg",
    "chorusoatl_spawn_egg",
    "sculksaur_spawn_egg",
    "lush_mace",
    "ooze_tipped_arrow",
}
MOBS = {"lushsaur", "chorusoatl", "sculksaur"}
EGGS = {"lushsaur_egg", "chorusoatl_egg"}
SOUND_PREFIXES = (
    "mob.dino.",
    "mob.lushsaur.",
    "mob.chorusoatl.",
    "mob.sculksaur.",
    "item.lushsaur_mace.",
)
KEEP_MIXINS = {
    "mixins": [
        "AbstractArrowMixin",
        "BreedGoalAccessor",
        "CriteriaTriggersAccessor",
        "EntityAccessor",
        "DamageSourcesMixin",
        "PlayerInterfaceMixin",
        "ServerPlayerInterfaceMixin",
        "PlayerListMixin",
        "sculksaur.PrimaryLevelDataMixin",
        "sculksaur.SculkShriekerBlockEntityMixin",
        "sculksaur.WalkNodeEvaluatorAccessor",
        "sculksaur.WardenMixin",
    ],
    "client": [
        "client.CameraMixin",
        "client.KeyMappingMixin",
        "client.LevelRendererMixin",
        "client.LivingEntityRendererMixin",
        "client.LocalPlayerMixin",
    ],
}
KEEP_FORGE_MIXINS = {
    "mixins": [
        "PlayerAttackDamageBonusMixin",
        "forge.ADWeaponItemMixin",
        "forge.ForgeEntityMultipartMixin",
    ],
    "client": [],
}

def read_json(data, name):
    try:
        return json.loads(data.decode("utf-8"))
    except Exception as exc:
        raise RuntimeError(f"Invalid JSON in {name}: {exc}") from exc

def dump_json(value):
    return (json.dumps(value, ensure_ascii=False, indent=2) + "\n").encode("utf-8")

def filter_mixin_config(data, template, name):
    value = read_json(data, name)
    for section in ("mixins", "client", "server"):
        if section in value:
            allowed = set(template.get(section, []))
            value[section] = [item for item in value[section] if item in allowed]
    if name == "archie_dinosaurs.forge.mixins.json":
        mixins = value.setdefault("mixins", [])
        multipart_mixin = "forge.ForgeEntityMultipartMixin"
        if multipart_mixin not in mixins:
            mixins.append(multipart_mixin)
    return dump_json(value)


def rewrite_manifest(data):
    text = data.decode("utf-8")
    physical_lines = text.replace("\r\n", "\n").replace("\r", "\n").split("\n")
    logical_lines = []
    for line in physical_lines:
        if line.startswith(" ") and logical_lines:
            logical_lines[-1] += line[1:]
        else:
            logical_lines.append(line)

    mixin_configs_index = next(
        (index for index, line in enumerate(logical_lines) if line.startswith("MixinConfigs:")),
        None,
    )
    if mixin_configs_index is None:
        main_section_end = next(
            (index for index, line in enumerate(logical_lines) if not line),
            len(logical_lines),
        )
        logical_lines.insert(main_section_end, "MixinConfigs: end_peaks.mixins.json")

    output_lines = []
    for line in logical_lines:
        if not line:
            output_lines.append("")
            continue
        if line.startswith("MixinConfigs:"):
            configs = [item.strip() for item in line.split(":", 1)[1].split(",") if item.strip()]
            if "end_peaks.mixins.json" not in configs:
                configs.append("end_peaks.mixins.json")
            line = "MixinConfigs: " + ",".join(configs)
        remaining = line.replace(OLD_NAMESPACE, NEW_NAMESPACE).encode("utf-8")
        first_line = True
        while remaining:
            capacity = 72 if first_line else 71
            end = min(capacity, len(remaining))
            while end > 0 and end < len(remaining) and (remaining[end] & 0xC0) == 0x80:
                end -= 1
            if end == 0:
                raise RuntimeError("Unable to wrap UTF-8 manifest attribute")
            piece = remaining[:end].decode("utf-8")
            output_lines.append(piece if first_line else " " + piece)
            remaining = remaining[end:]
            first_line = False

    return ("\r\n".join(output_lines).rstrip("\r\n") + "\r\n").encode("utf-8")


def rewrite_astemir_model(data, name):
    magic = b"ASTEMIR-MODEL-1"
    if not data.startswith(magic):
        raise RuntimeError(f"Unrecognized Astemir model header in {name}")

    offset = len(magic)
    if offset + 4 > len(data):
        raise RuntimeError(f"Truncated Astemir model asset count in {name}")
    asset_count = struct.unpack_from("<I", data, offset)[0]
    offset += 4
    if asset_count == 0 or asset_count > 256:
        raise RuntimeError(f"Invalid Astemir model asset count {asset_count} in {name}")

    for asset_index in range(asset_count):
        if offset + 4 > len(data):
            raise RuntimeError(f"Truncated embedded asset-name length in {name}")
        filename_length = struct.unpack_from("<I", data, offset)[0]
        offset += 4
        if filename_length == 0 or filename_length > 1024:
            raise RuntimeError(f"Invalid embedded asset-name length in {name}: {filename_length}")
        filename_end = offset + filename_length
        if filename_end + 4 > len(data):
            raise RuntimeError(f"Truncated embedded asset name in {name}")
        try:
            filename = data[offset:filename_end].decode("utf-8")
        except UnicodeDecodeError as exc:
            raise RuntimeError(f"Invalid embedded asset filename in {name}") from exc
        payload_length = struct.unpack_from("<I", data, filename_end)[0]
        payload_start = filename_end + 4
        payload_end = payload_start + payload_length
        if payload_end > len(data):
            raise RuntimeError(f"Truncated embedded asset {filename} in {name}")
        offset = payload_end

    if offset + 4 > len(data):
        raise RuntimeError(f"Embedded model JSON length is missing from {name}")
    json_length = struct.unpack_from("<I", data, offset)[0]
    json_offset = offset + 4
    json_end = json_offset + json_length
    if json_end != len(data):
        raise RuntimeError(
            f"Astemir model JSON length mismatch in {name}: "
            f"declared={json_length}, remaining={len(data) - json_offset}"
        )

    model_json = data[json_offset:json_end]
    try:
        json.loads(model_json.decode("utf-8"))
    except Exception as exc:
        raise RuntimeError(f"Invalid embedded Astemir model JSON in {name}: {exc}") from exc

    rewritten_json = model_json.replace(
        OLD_NAMESPACE.encode("ascii"),
        NEW_NAMESPACE.encode("ascii"),
    )
    try:
        json.loads(rewritten_json.decode("utf-8"))
    except Exception as exc:
        raise RuntimeError(f"Namespace rewrite damaged embedded model JSON in {name}: {exc}") from exc

    return data[:offset] + struct.pack("<I", len(rewritten_json)) + rewritten_json


ENTITY_REGISTRY_NAMESPACE = "archie_dinosaurs"
ENTITY_REGISTRY_NAMES = MOBS | {"chorusoatl_spit", "pus_tipped_arrow"}
OLD_ENTITY_IDS = {f"{OLD_NAMESPACE}:{name}" for name in ENTITY_REGISTRY_NAMES}
NEW_ENTITY_IDS = {f"{ENTITY_REGISTRY_NAMESPACE}:{name}" for name in ENTITY_REGISTRY_NAMES}


def rewrite_entity_namespace(value):
    if isinstance(value, dict):
        rewritten = {}
        for key, child in value.items():
            new_key = key.replace(OLD_NAMESPACE, NEW_NAMESPACE) if isinstance(key, str) else key
            rewritten[new_key] = rewrite_entity_namespace(child)
        return rewritten
    if isinstance(value, list):
        return [rewrite_entity_namespace(child) for child in value]
    if isinstance(value, str):
        return value.replace(OLD_NAMESPACE, NEW_NAMESPACE)
    return value


def key_is_allowed(key):
    if key in {
        NEW_NAMESPACE,
        f"category.{NEW_NAMESPACE}.default",
        f"biome.{NEW_NAMESPACE}.end_peaks",
        f"effect.{NEW_NAMESPACE}.dazed",
        f"item_group.{NEW_NAMESPACE}_items",
        f"item_group.{NEW_NAMESPACE}.items",
        f"message.{NEW_NAMESPACE}.pet_not_found",
        f"message.{NEW_NAMESPACE}.pet_teleport_success",
        f"message.{NEW_NAMESPACE}.pet_world_unavailable",
        f"message.{NEW_NAMESPACE}.status.buried",
        f"{NEW_NAMESPACE}.mount_primary_attack",
        f"{NEW_NAMESPACE}.mount_special_attack",
    }:
        return True

    if key.startswith("advancement.root."):
        return True

    for mob in MOBS:
        if any(key.startswith(f"advancement.{kind}_{mob}.") for kind in ("kill", "spot", "tame")):
            return True
        if key == f"entity.{NEW_NAMESPACE}.{mob}":
            return True
        if key.startswith(f"subtitles.{NEW_NAMESPACE}.mob.{mob}."):
            return True

    if key == f"entity.{NEW_NAMESPACE}.chorusoatl_spit":
        return True
    if key.startswith(f"subtitles.{NEW_NAMESPACE}.mob.dino."):
        return True
    if key in {
        f"block.{NEW_NAMESPACE}.lushsaur_egg",
        f"block.{NEW_NAMESPACE}.chorusoatl_egg",
    }:
        return True
    if key in {
        f"item.{NEW_NAMESPACE}.lush_core",
        f"item.{NEW_NAMESPACE}.chorus_fossil",
        f"item.{NEW_NAMESPACE}.sculk_toxin",
        f"item.{NEW_NAMESPACE}.lush_mace",
        f"item.{NEW_NAMESPACE}.ooze_tipped_arrow",
        f"entity.{NEW_NAMESPACE}.pus_tipped_arrow",
        f"subtitles.{NEW_NAMESPACE}.item.lushsaur_mace.attack",
        f"subtitles.{NEW_NAMESPACE}.item.lushsaur_mace.spin",
        f"item.{NEW_NAMESPACE}.lushsaur_spawn_egg",
        f"item.{NEW_NAMESPACE}.chorusoatl_spawn_egg",
        f"item.{NEW_NAMESPACE}.sculksaur_spawn_egg",
    }:
        return True
    return False

def keep_asset(path):
    rel = path[len(MOD):]
    low = rel.lower()

    if low.startswith("lang/"):
        return low.endswith(".json")

    if low == "sounds.json":
        return True

    if low.startswith("sounds/mob/"):
        return any(f"/{mob}/" in f"/{low}" for mob in MOBS) or low.startswith("sounds/mob/dino/")
    if low.startswith("sounds/item/lushsaur_mace/"):
        return low.endswith(".ogg")
    if low.startswith("sounds/"):
        return False

    if low.startswith("animations/player/riding/"):
        return low.endswith("/lushsaur.astemiranim") or low.endswith("/chorusoatl.astemiranim")
    if low.startswith("animations/"):
        return False

    if low.startswith("blockstates/"):
        return any(low.endswith(f"/{egg}.json") for egg in EGGS)

    if low.startswith("models/entity/"):
        return low.rsplit("/", 1)[-1] in {
            "lushsaur.astemirmodel",
            "lushsaur_baby.astemirmodel",
            "chorusoatl.astemirmodel",
            "chorusoatl_baby.astemirmodel",
            "sculksaur.astemirmodel",
        }

    if low == "models/attachable/lushsaur_mace.astemirmodel":
        return True

    if low.startswith("models/block/"):
        return any(egg in low for egg in EGGS)

    if low.startswith("models/item/"):
        return low.rsplit("/", 1)[-1].removesuffix(".json") in ITEMS

    if low.startswith("models/"):
        return False

    if low.startswith("particles/"):
        return low.endswith("dust_pillar.json") or any(f"/{mob}/" in f"/{low}" for mob in MOBS)

    if low.startswith("textures/block/"):
        return low.rsplit("/", 1)[-1] in {f"{egg}.png" for egg in EGGS}

    if low == "textures/item/model/lushsaur_mace.png":
        return True

    if low == "textures/entity/projectiles/pus_tipped_arrow.png":
        return True

    if low.startswith("textures/item/"):
        name = low.rsplit("/", 1)[-1]
        return name.endswith(".png") and name[:-4] in ITEMS

    if low in {"textures/gui/dino.png", "textures/gui/back.png"}:
        return True

    if low.startswith("textures/mob_effect/"):
        return low == "textures/mob_effect/dazed.png"

    if low.startswith("textures/emitter/"):
        allowed_emitter_textures = {
            "end_dust.png",
            "glow_pixel.png",
            "glow_pixel_rotated.png",
            "leaves.png",
            "pixel.png",
            "ring.png",
            "ring_4.png",
            "sculk_charge.png",
            "sculk_charge_pop.png",
            "sculk_raptor.png",
            "sculk_tentacles_1.png",
            "signal.png",
            "smoke.png",
            "sonic_explosion.png",
            "tail_slash.png",
            "wind.png",
            "z.png",
        }
        return low.rsplit("/", 1)[-1] in allowed_emitter_textures

    return False

def keep_data_path(path):
    rel = path[len(DATA):]
    low = rel.lower()
    base = low.rsplit("/", 1)[-1]

    if low.startswith("advancements/recipes/"):
        return base in {"lush_mace.json", "ooze_tipped_arrow.json"}

    if low.startswith("advancements/"):
        if base == "root.json":
            return True
        return any(base == f"{kind}_{mob}.json"
                   for mob in MOBS for kind in ("kill", "spot", "tame"))

    if low.startswith("loot_tables/entities/"):
        return any(base == f"{mob}.json" for mob in MOBS)

    if low.startswith("loot_tables/blocks/"):
        return any(base == f"{egg}.json" for egg in EGGS)

    if low.startswith("forge/biome_modifier/"):
        return base in {"chorusoatl_spawns.json", "lushsaur_spawns.json"}

    if low.startswith("worldgen/biome/"):
        return base == "end_peaks.json"

    if low.startswith("worldgen/configured_feature/"):
        return base in {"end_peaks_mountains.json", "end_peaks_chorus.json"}

    if low.startswith("worldgen/placed_feature/"):
        return base in {"end_peaks_mountains.json", "end_peaks_chorus.json"}

    if low.startswith("recipes/archie_trade/"):
        return False
    if low.startswith("recipes/"):
        return any(base == f"{item}.json" for item in ITEMS)
    if low.startswith("advancements/recipes/"):
        return False

    if low.startswith("tags/blocks/"):
        return base.startswith(("lushsaur_", "chorusoatl_"))

    if low.startswith("tags/items/"):
        return base in {"lushsaur_edible.json", "chorusoatl_edible.json"}

    if low.startswith("tags/entity_types/"):
        return base in {"dinosaur.json", "sculk.json"}

    if low.startswith("tags/game_events/"):
        return base == "sculksaur_can_listen.json"

    if low.startswith("tags/damage_type/"):
        return base == "hurts_combat_sculksaur_extra.json"

    if low.startswith("tags/dimension_type/"):
        return base == "chorusoatl_egg_preferred.json"

    if low.startswith("tags/worldgen/biome/"):
        return base == "spawns_chorusoatls.json"

    if low.startswith(("worldgen/structure/", "worldgen/structure_set/", "structures/")):
        return False

    if low.startswith("damage_type/"):
        return base == "pus_arrow.json"

    return False

def filter_loot_entry(entry):
    if isinstance(entry, dict):
        if entry.get("type") == "minecraft:item":
            item = entry.get("name")
            if (
                isinstance(item, str)
                and item.startswith((f"{OLD_NAMESPACE}:", f"{NEW_NAMESPACE}:"))
                and item.split(":", 1)[1] not in ITEMS
            ):
                return {"type": "minecraft:empty", "weight": entry.get("weight", 1)}
        return {key: filter_loot_entry(value) for key, value in entry.items()}
    if isinstance(entry, list):
        return [filter_loot_entry(value) for value in entry]
    return entry


def filter_loot_table(data, name):
    value = read_json(data, name)
    pools = value.get("pools")
    if not isinstance(pools, list):
        raise RuntimeError(f"Loot table {name} has no pools array")
    strip_lush_core = name == "data/archie_dinosaurs/loot_tables/entities/lushsaur.json"

    def filter_entry(entry):
        if isinstance(entry, dict):
            if strip_lush_core and entry.get("type") == "minecraft:item" and entry.get("name") in {
                "ec_ad:lush_core", "archie_dinosaurs:lush_core"
            }:
                return {"type": "minecraft:empty", "weight": entry.get("weight", 1)}
            return {key: filter_entry(child) for key, child in entry.items()}
        if isinstance(entry, list):
            return [filter_entry(child) for child in entry]
        return entry

    for pool in pools:
        if not isinstance(pool, dict) or not isinstance(pool.get("entries"), list):
            raise RuntimeError(f"Loot table {name} has malformed pool entries")
        filtered = [filter_loot_entry(entry) for entry in pool["entries"]]
        pool["entries"] = [filter_entry(entry) for entry in filtered]
    return dump_json(value)


def validate_entity_namespace(entries):
    found_new_ids = set()

    def visit(value, name):
        if isinstance(value, dict):
            for key, child in value.items():
                if isinstance(key, str) and key.startswith("entity.ec_ad."):
                    raise RuntimeError(f"Old entity translation key remains in {name}: {key}")
                if isinstance(key, str) and key.startswith(f"entity.{ENTITY_REGISTRY_NAMESPACE}."):
                    entity_name = key.split(".")[-1]
                    if entity_name in ENTITY_REGISTRY_NAMES:
                        found_new_ids.add(f"{ENTITY_REGISTRY_NAMESPACE}:{entity_name}")
                visit(child, name)
        elif isinstance(value, list):
            for child in value:
                visit(child, name)
        elif isinstance(value, str):
            if value in OLD_ENTITY_IDS:
                raise RuntimeError(f"Old entity registry ID remains in {name}: {value}")
            if value in NEW_ENTITY_IDS:
                found_new_ids.add(value)

    for name, data in entries.items():
        if not name.endswith(".json"):
            continue
        if name.startswith("assets/archie_dinosaurs/lang/") or name.startswith("data/archie_dinosaurs/"):
            visit(read_json(data, name), name)

    missing = sorted(NEW_ENTITY_IDS - found_new_ids)
    if missing:
        raise RuntimeError("New entity registry IDs are missing from retained JSON resources: " + ", ".join(missing))


def validate_loot_tables(entries):
    for name, data in entries.items():
        if not (name.startswith("data/archie_dinosaurs/loot_tables/") and name.endswith(".json")):
            continue
        value = read_json(data, name)
        def visit(node):
            if isinstance(node, dict):
                if node.get("type") == "minecraft:item":
                    item = node.get("name")
                    if isinstance(item, str) and item.startswith("archie_dinosaurs:") and item.split(":", 1)[1] not in ITEMS:
                        raise RuntimeError(f"Loot table {name} still references removed item {item}")
                for child in node.values():
                    visit(child)
            elif isinstance(node, list):
                for child in node:
                    visit(child)
        visit(value)
        if name == "data/archie_dinosaurs/loot_tables/entities/lushsaur.json":
            def has_core(node):
                if isinstance(node, dict):
                    if node.get("type") == "minecraft:item" and node.get("name") == "archie_dinosaurs:lush_core":
                        return True
                    return any(has_core(child) for child in node.values())
                if isinstance(node, list):
                    return any(has_core(child) for child in node)
                return False
            if has_core(value):
                raise RuntimeError("Lushsaur loot table duplicates the single Lush Core drop handled by LushsaurEntity.m_30232_()")


def extend_chorusoatl_hatchable(data):
    value = read_json(data, "data/archie_dinosaurs/tags/blocks/chorusoatl_egg_hatchable.json")
    values = value.get("values")
    if not isinstance(values, list):
        raise RuntimeError("Chorusoatl hatchable block tag must contain a values array")

    required = (
        "minecraft:end_stone",
        "minecraft:end_stone_bricks",
        "minecraft:purpur_block",
        "minecraft:purpur_pillar",
        "minecraft:obsidian",
        "minecraft:bedrock",
    )
    for block_id in required:
        if block_id not in values:
            values.append(block_id)
    return dump_json(value)


def filter_tag_json(data, name):
    value = rewrite_entity_namespace(read_json(data, name))
    allowed = ITEMS | EGGS | MOBS | {"chorusoatl_spit", "pus_tipped_arrow", "pus_arrow", "pus_arrow_aggro"}
    if isinstance(value, dict) and isinstance(value.get("values"), list):
        value["values"] = [
            item for item in value["values"]
            if not (
                isinstance(item, str)
                and item.startswith(f"{NEW_NAMESPACE}:")
                and item.split(":", 1)[1] not in allowed
            )
        ]
    return dump_json(value)

def filter_root_advancement(data):
    value = read_json(data, "data/archie_dinosaurs/advancements/root.json")
    criteria = {}
    for mob in sorted(MOBS):
        criteria[f"spot_{mob}"] = {
            "conditions": {
                "entity": [
                    {
                        "condition": "minecraft:entity_properties",
                        "entity": "this",
                        "predicate": {"type": f"archie_dinosaurs:{mob}"},
                    }
                ]
            },
            "trigger": "archie_dinosaurs:spotted_entity",
        }
    value["criteria"] = criteria
    value["requirements"] = [list(criteria)]
    display = value.setdefault("display", {})
    display["icon"] = {"item": "archie_dinosaurs:lush_core"}
    display["background"] = "archie_dinosaurs:textures/gui/back.png"
    return dump_json(value)


def validate_advancement_graph(entries):
    docs = {}
    for name, data in entries.items():
        if name.startswith("data/archie_dinosaurs/advancements/") and name.endswith(".json"):
            docs[name[len("data/archie_dinosaurs/advancements/"):-5]] = read_json(data, name)
    valid_icons = {
        "archie_dinosaurs:lush_core", "archie_dinosaurs:chorus_fossil", "archie_dinosaurs:sculk_toxin",
        "archie_dinosaurs:lushsaur_egg", "archie_dinosaurs:chorusoatl_egg",
        "archie_dinosaurs:lushsaur_spawn_egg", "archie_dinosaurs:chorusoatl_spawn_egg",
        "archie_dinosaurs:sculksaur_spawn_egg",
        "archie_dinosaurs:lush_mace", "archie_dinosaurs:ooze_tipped_arrow",
    }
    for name, doc in docs.items():
        parent = doc.get("parent")
        if parent and parent.startswith("archie_dinosaurs:") and parent.split(":", 1)[1] not in docs:
            raise RuntimeError(f"Advancement {name} references missing parent {parent}")
        icon = doc.get("display", {}).get("icon", {}).get("item")
        if icon and icon.startswith("archie_dinosaurs:") and icon not in valid_icons:
            raise RuntimeError(f"Advancement {name} references removed item {icon}")
    for name in entries:
        if name.startswith("data/archie_dinosaurs/recipes/archie_trade/"):
            raise RuntimeError(f"Removed Archie trade recipe leaked into output: {name}")


kept = {}
removed = []
with zipfile.ZipFile(source, "r") as zin:
    for info in zin.infolist():
        source_name = info.filename
        if info.is_dir():
            continue

        name = source_name
        if source_name.startswith(SOURCE_MOD):
            name = MOD + source_name[len(SOURCE_MOD):]
        elif source_name.startswith(SOURCE_DATA):
            name = DATA + source_name[len(SOURCE_DATA):]
        elif source_name == f"{OLD_NAMESPACE}.mixins.json":
            name = f"{NEW_NAMESPACE}.mixins.json"
        elif source_name == f"{OLD_NAMESPACE}.forge.mixins.json":
            name = f"{NEW_NAMESPACE}.forge.mixins.json"
        elif source_name == f"{OLD_NAMESPACE}.accesswidener":
            name = f"{NEW_NAMESPACE}.accesswidener"
        elif source_name == f"{OLD_NAMESPACE}.refmap.json":
            name = f"{NEW_NAMESPACE}.refmap.json"

        if name.upper().endswith((".SF", ".RSA", ".DSA")) or name.upper() == "META-INF/INDEX.LIST":
            removed.append(name)
            continue

        data = zin.read(source_name)

        if name == "META-INF/MANIFEST.MF":
            data = rewrite_manifest(data)

        elif name == "META-INF/mods.toml":
            metadata = data.decode("utf-8")
            metadata, mod_id_count = re.subn(
                r'(?m)^([ \t]*modId[ \t]*=[ \t]*)"ec_ad"',
                r'\1"archie_dinosaurs"',
                metadata,
            )
            if mod_id_count != 1:
                raise RuntimeError(f"Expected exactly one parent modId in mods.toml, found {mod_id_count}")
            data = metadata.replace(OLD_NAMESPACE, NEW_NAMESPACE).encode("utf-8")

        elif name.startswith(MOD):
            if not keep_asset(name):
                removed.append(name)
                continue

            if name.startswith(MOD + "lang/"):
                lang = rewrite_entity_namespace(read_json(data, name))
                lang = {k: v for k, v in lang.items() if key_is_allowed(k)}

                locale = name.rsplit("/", 1)[-1].removesuffix(".json").lower()
                translations = {
                    "en_us": {
                        "item_group.archie_dinosaurs_items": "Archies Dinosaurs",
                        "item.archie_dinosaurs.lushsaur_spawn_egg": "Lushsaur Spawn Egg",
                        "item.archie_dinosaurs.chorusoatl_spawn_egg": "Chorusoatl Spawn Egg",
                        "item.archie_dinosaurs.sculksaur_spawn_egg": "Sculksaur Spawn Egg",
                        "item.archie_dinosaurs.lush_core": "Lush Core",
                        "item.archie_dinosaurs.chorus_fossil": "Chorus Fossil",
                        "item.archie_dinosaurs.sculk_toxin": "Sculk Toxin",
                        "block.archie_dinosaurs.lushsaur_egg": "Lushsaur Egg",
                        "block.archie_dinosaurs.chorusoatl_egg": "Chorusoatl Egg",
                        "biome.archie_dinosaurs.end_peaks": "End Peaks",
                    },
                    "ru_ru": {
                        "item_group.archie_dinosaurs_items": "Динозавры Арчи",
                        "item.archie_dinosaurs.lushsaur_spawn_egg": "Яйцо призыва Пышнозавра",
                        "item.archie_dinosaurs.chorusoatl_spawn_egg": "Яйцо призыва Хоросоатля",
                        "item.archie_dinosaurs.sculksaur_spawn_egg": "Яйцо призыва Скалкозавра",
                        "item.archie_dinosaurs.lush_core": "Ядро Пышнозавра",
                        "item.archie_dinosaurs.chorus_fossil": "Окаменелость Хоросоатля",
                        "item.archie_dinosaurs.sculk_toxin": "Токсин Скалкозавра",
                        "block.archie_dinosaurs.lushsaur_egg": "Яйцо Пышнозавра",
                        "block.archie_dinosaurs.chorusoatl_egg": "Яйцо Хоросоатля",
                        "biome.archie_dinosaurs.end_peaks": "Пики Энда",
                    },
                }
                for key, value in translations.get(locale, {}).items():
                    lang.setdefault(key, value)

                if locale == "en_us":
                    lang["advancement.root.desc"] = "Meet the dinosaurs"
                elif locale == "ru_ru":
                    lang["advancement.root.desc"] = "Познакомьтесь с динозаврами"
                data = dump_json(lang)
            elif name == MOD + "sounds.json":
                sounds = read_json(data, name)
                sounds = {k: v for k, v in sounds.items() if k.startswith(SOUND_PREFIXES)}
                data = dump_json(sounds)
            elif name.endswith(".astemirmodel"):
                data = rewrite_astemir_model(data, name)

        elif name.startswith("data/archie_dinosaurs/"):
            if not keep_data_path(name):
                removed.append(name)
                continue
            if name == "data/archie_dinosaurs/advancements/root.json":
                data = filter_root_advancement(data)
            elif name.startswith("data/archie_dinosaurs/loot_tables/") and name.endswith(".json"):
                data = filter_loot_table(data, name)
            elif name == "data/archie_dinosaurs/tags/blocks/chorusoatl_egg_hatchable.json":
                data = extend_chorusoatl_hatchable(data)
            elif "/tags/" in name and name.endswith(".json"):
                data = filter_tag_json(data, name)

        elif name in {
            "data/minecraft/tags/game_events/vibrations.json",
            "data/minecraft/tags/game_events/warden_can_listen.json",
            "data/minecraft/tags/entity_types/arrows.json",
            "data/minecraft/tags/damage_type/is_projectile.json",
            "data/minecraft/tags/worldgen/biome/is_end.json",
            "data/minecraft/tags/worldgen/biome/has_structure/end_city.json",
        }:
            data = filter_tag_json(data, name)

        elif name.startswith("data/") and not name.startswith("data/archie_dinosaurs/"):
            removed.append(name)
            continue

        elif name == "archie_dinosaurs.mixins.json":
            data = filter_mixin_config(data, KEEP_MIXINS, name)

        elif name == "archie_dinosaurs.forge.mixins.json":
            data = filter_mixin_config(data, KEEP_FORGE_MIXINS, name)

        elif name.startswith("assets/") and not name.startswith(MOD):
            removed.append(name)
            continue

        elif name.startswith("data/"):
            removed.append(name)
            continue

        if name.endswith(".json") and (
            name.startswith("assets/archie_dinosaurs/") or name.startswith("data/archie_dinosaurs/")
        ):
            data = dump_json(rewrite_entity_namespace(read_json(data, name)))

        if name.endswith((".json", ".toml", ".accesswidener", ".mcmeta", ".cfg", ".properties", ".txt")):
            data = data.replace(OLD_NAMESPACE.encode("ascii"), NEW_NAMESPACE.encode("ascii"))

        kept[name] = data

kept["data/minecraft/tags/items/arrows.json"] = dump_json({
    "replace": False,
    "values": ["archie_dinosaurs:ooze_tipped_arrow"],
})

kept["data/archie_dinosaurs/forge/biome_modifier/lushsaur_spawns.json"] = dump_json({
    "type": "forge:add_spawns",
    "biomes": "minecraft:lush_caves",
    "spawners": [{
        "type": "archie_dinosaurs:lushsaur",
        "weight": 1,
        "minCount": 1,
        "maxCount": 1,
    }],
})

chorusoatl_spawn_path = "data/archie_dinosaurs/forge/biome_modifier/chorusoatl_spawns.json"
if chorusoatl_spawn_path not in kept:
    raise SystemExit("Original Chorusoatl biome modifier is missing.")
chorusoatl_modifier = read_json(kept[chorusoatl_spawn_path], chorusoatl_spawn_path)
if chorusoatl_modifier.get("type") != "forge:add_spawns":
    raise SystemExit("Unexpected Chorusoatl biome modifier type.")
chorusoatl_spawner = chorusoatl_modifier.get("spawners")
if not isinstance(chorusoatl_spawner, dict):
    raise SystemExit("Original Chorusoatl spawner entry must remain a single group definition.")
if chorusoatl_spawner.get("type") != "archie_dinosaurs:chorusoatl":
    raise SystemExit("Unexpected entity in the Chorusoatl biome modifier.")
chorusoatl_spawner["weight"] = 1
chorusoatl_spawner["minCount"] = 3
chorusoatl_spawner["maxCount"] = 5
chorusoatl_modifier["biomes"] = "archie_dinosaurs:end_peaks"
kept[chorusoatl_spawn_path] = dump_json(chorusoatl_modifier)

kept["data/archie_dinosaurs/tags/worldgen/biome/spawns_chorusoatls.json"] = dump_json({
    "replace": True,
    "values": ["archie_dinosaurs:end_peaks"],
})

kept["data/minecraft/tags/worldgen/biome/is_end.json"] = dump_json({
    "replace": False,
    "values": ["archie_dinosaurs:end_peaks"],
})

kept["data/minecraft/tags/worldgen/biome/has_structure/end_city.json"] = dump_json({
    "replace": False,
    "values": ["archie_dinosaurs:end_peaks"],
})

kept["data/archie_dinosaurs/worldgen/biome/end_peaks.json"] = dump_json({
    "carvers": {},
    "downfall": 0.5,
    "effects": {
        "fog_color": 10518688,
        "sky_color": 0,
        "water_color": 4159204,
        "water_fog_color": 329011,
    },
    "features": [
        [],
        [],
        [],
        [],
        [
            "minecraft:end_gateway_return",
            "archie_dinosaurs:end_peaks_mountains",
        ],
        [],
        [],
        [],
        [],
        [
            "archie_dinosaurs:end_peaks_chorus",
        ],
    ],
    "has_precipitation": False,
    "spawners": {
        "ambient": [],
        "axolotls": [],
        "creature": [],
        "misc": [],
        "monster": [
            {
                "type": "minecraft:enderman",
                "weight": 10,
                "minCount": 4,
                "maxCount": 4,
            }
        ],
        "underground_water_creature": [],
        "water_ambient": [],
        "water_creature": [],
    },
    "spawn_costs": {},
    "temperature": 0.5,
})

for feature_name in ("end_peaks_mountains", "end_peaks_chorus"):
    kept[f"data/archie_dinosaurs/worldgen/configured_feature/{feature_name}.json"] = dump_json({
        "type": f"archie_dinosaurs:{feature_name}",
        "config": {},
    })
    kept[f"data/archie_dinosaurs/worldgen/placed_feature/{feature_name}.json"] = dump_json({
        "feature": f"archie_dinosaurs:{feature_name}",
        "placement": [
            {"type": "minecraft:in_square"},
        ],
    })

with zipfile.ZipFile(target, "w", compression=zipfile.ZIP_DEFLATED, compresslevel=9) as zout:
    for name, data in sorted(kept.items()):
        info = zipfile.ZipInfo(name)
        info.compress_type = zipfile.ZIP_DEFLATED
        info.external_attr = 0o100644 << 16
        zout.writestr(info, data)

with zipfile.ZipFile(target, "r") as check:
    names = set(check.namelist())
    required = {
        "META-INF/mods.toml",
        "archie_dinosaurs.mixins.json",
        "archie_dinosaurs.forge.mixins.json",
        "pack.mcmeta",
        "data/archie_dinosaurs/forge/biome_modifier/lushsaur_spawns.json",
        "data/archie_dinosaurs/forge/biome_modifier/chorusoatl_spawns.json",
        "data/archie_dinosaurs/tags/worldgen/biome/spawns_chorusoatls.json",
        "data/archie_dinosaurs/worldgen/biome/end_peaks.json",
        "data/archie_dinosaurs/worldgen/configured_feature/end_peaks_mountains.json",
        "data/archie_dinosaurs/worldgen/configured_feature/end_peaks_chorus.json",
        "data/archie_dinosaurs/worldgen/placed_feature/end_peaks_mountains.json",
        "data/archie_dinosaurs/worldgen/placed_feature/end_peaks_chorus.json",
        "data/minecraft/tags/worldgen/biome/is_end.json",
        "data/minecraft/tags/worldgen/biome/has_structure/end_city.json",
    }
    missing = sorted(required - names)
    if missing:
        raise SystemExit(f"Required resources missing from output: {missing}")

    spawn_path = "data/archie_dinosaurs/forge/biome_modifier/lushsaur_spawns.json"
    spawn_modifier = read_json(check.read(spawn_path), spawn_path)
    expected_spawn_modifier = {
        "type": "forge:add_spawns",
        "biomes": "minecraft:lush_caves",
        "spawners": [{
            "type": "archie_dinosaurs:lushsaur",
            "weight": 1,
            "minCount": 1,
            "maxCount": 1,
        }],
    }
    if spawn_modifier != expected_spawn_modifier:
        raise SystemExit("Lushsaur natural spawn must remain weight 1, group size 1, in minecraft:lush_caves")

    chorus_modifier_path = "data/archie_dinosaurs/forge/biome_modifier/chorusoatl_spawns.json"
    chorus_modifier = read_json(check.read(chorus_modifier_path), chorus_modifier_path)
    chorus_spawner = chorus_modifier.get("spawners")
    if chorus_modifier.get("biomes") != "archie_dinosaurs:end_peaks":
        raise SystemExit("Chorusoatl natural spawning must be restricted to End Peaks.")
    if chorus_modifier.get("type") != "forge:add_spawns" or not isinstance(chorus_spawner, dict):
        raise SystemExit("Chorusoatl biome modifier must retain its single Forge spawner entry.")
    if chorus_spawner != {
        "type": "archie_dinosaurs:chorusoatl",
        "weight": 1,
        "minCount": 3,
        "maxCount": 5,
    }:
        raise SystemExit("Chorusoatl spawn settings must be weight 1 with the original 3-5 group size.")

    chorus_tag_path = "data/archie_dinosaurs/tags/worldgen/biome/spawns_chorusoatls.json"
    chorus_tag = read_json(check.read(chorus_tag_path), chorus_tag_path)
    if chorus_tag != {"replace": True, "values": ["archie_dinosaurs:end_peaks"]}:
        raise SystemExit("The Chorusoatl spawn biome tag must contain only End Peaks.")

    end_biome_path = "data/archie_dinosaurs/worldgen/biome/end_peaks.json"
    end_biome = read_json(check.read(end_biome_path), end_biome_path)
    if end_biome.get("has_precipitation") is not False or end_biome.get("temperature") != 0.5:
        raise SystemExit("End Peaks climate must match the End.")
    if end_biome.get("carvers") != {}:
        raise SystemExit("End Peaks carvers must use the vanilla biome object format.")
    effects = end_biome.get("effects")
    if not isinstance(effects, dict) or any(effects.get(key) != value for key, value in {
        "fog_color": 10518688,
        "sky_color": 0,
        "water_color": 4159204,
        "water_fog_color": 329011,
    }.items()):
        raise SystemExit("End Peaks effects must contain all vanilla End biome color fields.")
    if "minecraft:end_gateway_return" not in end_biome["features"][4]:
        raise SystemExit("End Peaks must retain the vanilla End gateway feature.")
    if "archie_dinosaurs:end_peaks_mountains" not in end_biome["features"][4]:
        raise SystemExit("End Peaks is missing the mountain terrain feature.")
    if "archie_dinosaurs:end_peaks_chorus" not in end_biome["features"][9]:
        raise SystemExit("End Peaks is missing the foot-of-mountain chorus feature.")

    for feature_name in ("end_peaks_mountains", "end_peaks_chorus"):
        configured = f"data/archie_dinosaurs/worldgen/configured_feature/{feature_name}.json"
        placed = f"data/archie_dinosaurs/worldgen/placed_feature/{feature_name}.json"
        read_json(check.read(configured), configured)
        read_json(check.read(placed), placed)

    for tag_path in (
        "data/minecraft/tags/worldgen/biome/is_end.json",
        "data/minecraft/tags/worldgen/biome/has_structure/end_city.json",
    ):
        tag = read_json(check.read(tag_path), tag_path)
        if tag.get("replace") is not False or "archie_dinosaurs:end_peaks" not in tag.get("values", []):
            raise SystemExit("End Peaks is missing a required vanilla End biome tag: " + tag_path)

    stale_structures = sorted(
        name for name in names
        if "lushsaur" in name.lower() and (
            "/worldgen/structure/" in name.lower()
            or "/worldgen/structure_set/" in name.lower()
            or "/structures/" in name.lower()
            or name.lower().endswith("/spawns_lushsaurs.json")
        )
    )
    if stale_structures:
        raise SystemExit("Lushsaur structure-based spawning resources remain: " + repr(stale_structures))

    manifest = check.read("META-INF/MANIFEST.MF").decode("utf-8")
    logical_manifest = re.sub(r"\r?\n ", "", manifest).replace("\r\n", "\n")
    main_manifest_section = logical_manifest.split("\n\n", 1)[0]
    mixin_configs_line = next(
        (line for line in main_manifest_section.splitlines() if line.startswith("MixinConfigs:")),
        None,
    )
    if mixin_configs_line is None:
        raise SystemExit("The mod manifest has no main-section MixinConfigs attribute.")
    mixin_configs = [
        item.strip()
        for item in mixin_configs_line.split(":", 1)[1].split(",")
        if item.strip()
    ]
    if "end_peaks.mixins.json" not in mixin_configs:
        raise SystemExit("End Peaks biome source mixin is not registered in the main manifest section.")
    mixin_config = read_json(check.read("end_peaks.mixins.json"), "end_peaks.mixins.json")
    if "EndPeaksBiomeSourceMixin" not in mixin_config.get("mixins", []):
        raise SystemExit("End Peaks biome source mixin is missing from its config.")
    read_json(check.read("end_peaks.refmap.json"), "end_peaks.refmap.json")

    stale_names = sorted(name for name in names if OLD_NAMESPACE in name)
    if stale_names:
        raise SystemExit("Old mod namespace remains in JAR entry names: " + repr(stale_names))

    for name in sorted(names):
        if name.endswith(".jar"):
            continue
        payload = check.read(name)
        if OLD_NAMESPACE.encode("ascii") in payload:
            raise SystemExit(f"Old mod namespace remains in JAR entry content: {name}")

    metadata = check.read("META-INF/mods.toml").decode("utf-8")
    if 'modId = "archie_dinosaurs"' not in metadata or 'modId = "ec_ad"' in metadata:
        raise SystemExit("Forge mods.toml does not declare only the archie_dinosaurs mod ID")

    required_translations = {
        "item_group.archie_dinosaurs_items",
        "item.archie_dinosaurs.lushsaur_spawn_egg",
        "item.archie_dinosaurs.chorusoatl_spawn_egg",
        "item.archie_dinosaurs.sculksaur_spawn_egg",
        "item.archie_dinosaurs.lush_core",
        "item.archie_dinosaurs.chorus_fossil",
        "item.archie_dinosaurs.sculk_toxin",
        "item.archie_dinosaurs.lush_mace",
        "item.archie_dinosaurs.ooze_tipped_arrow",
        "entity.archie_dinosaurs.pus_tipped_arrow",
        "subtitles.archie_dinosaurs.item.lushsaur_mace.attack",
        "subtitles.archie_dinosaurs.item.lushsaur_mace.spin",
        "block.archie_dinosaurs.lushsaur_egg",
        "block.archie_dinosaurs.chorusoatl_egg",
    }
    for locale in ("en_us", "ru_ru"):
        language_path = f"assets/archie_dinosaurs/lang/{locale}.json"
        language = read_json(check.read(language_path), language_path)
        if "item.archie_dinosaurs.desc.lush_mace" in language:
            raise SystemExit(f"Mace description translation should be absent: {language_path}")
        missing_translations = sorted(
            key for key in required_translations
            if not isinstance(language.get(key), str)
            or not language[key].strip()
            or language[key] == key
        )
        if missing_translations:
            raise SystemExit(
                f"Missing real {locale} translations for retained entries: "
                + repr(missing_translations)
            )

    allowed_item_models = {
        f"assets/archie_dinosaurs/models/item/{item}.json" for item in ITEMS
    }
    missing_items = sorted(allowed_item_models - names)
    if missing_items:
        raise SystemExit(f"Required item models missing: {missing_items}")
    hatchable_tag_path = "data/archie_dinosaurs/tags/blocks/chorusoatl_egg_hatchable.json"
    with check.open(hatchable_tag_path) as file:
        hatchable_tag = json.load(file)
    expected_hatchable_blocks = {
        "minecraft:end_stone",
        "minecraft:end_stone_bricks",
        "minecraft:purpur_block",
        "minecraft:purpur_pillar",
        "minecraft:obsidian",
        "minecraft:bedrock",
    }
    actual_hatchable_blocks = set(hatchable_tag.get("values", []))
    missing_hatchable_blocks = sorted(expected_hatchable_blocks - actual_hatchable_blocks)
    if missing_hatchable_blocks:
        raise SystemExit(f"Chorusoatl egg hatchable tag is missing blocks: {missing_hatchable_blocks}")

    required_item_resources = {
        "data/archie_dinosaurs/recipes/lush_mace.json",
        "data/archie_dinosaurs/recipes/ooze_tipped_arrow.json",
        "data/archie_dinosaurs/advancements/recipes/combat/lush_mace.json",
        "data/archie_dinosaurs/advancements/recipes/combat/ooze_tipped_arrow.json",
        "data/archie_dinosaurs/damage_type/pus_arrow.json",
        "data/minecraft/tags/game_events/vibrations.json",
        "data/minecraft/tags/game_events/warden_can_listen.json",
        "data/minecraft/tags/items/arrows.json",
        "data/minecraft/tags/entity_types/arrows.json",
        "data/minecraft/tags/damage_type/is_projectile.json",
        "assets/archie_dinosaurs/textures/entity/projectiles/pus_tipped_arrow.png",
        "assets/archie_dinosaurs/models/attachable/lushsaur_mace.astemirmodel",
        "assets/archie_dinosaurs/textures/item/model/lushsaur_mace.png",
        "assets/archie_dinosaurs/sounds/item/lushsaur_mace/spin.ogg",
    }
    mace_animation = "assets/archie_dinosaurs/animations/player/attachable/lushsaur_mace.astemiranim"
    if mace_animation in names:
        raise SystemExit("Lush Mace player attack animation should be removed from Legacy")
    missing_item_resources = sorted(required_item_resources - names)
    if missing_item_resources:
        raise SystemExit(f"Missing retained Lush Mace/Ooze Arrow resources: {missing_item_resources}")

    for tag_path in (
        "data/minecraft/tags/game_events/vibrations.json",
        "data/minecraft/tags/game_events/warden_can_listen.json",
    ):
        tag = read_json(check.read(tag_path), tag_path)
        values = set(tag.get("values", []))
        if "archie_dinosaurs:pus_arrow_aggro" not in values:
            raise SystemExit(f"Vanilla sculk event tag lost Pus Arrow aggro: {tag_path}")
        stale = sorted(value for value in values if isinstance(value, str) and value.startswith("archie_dinosaurs:") and value.split(":", 1)[1] not in {"pus_arrow_aggro"})
        if stale:
            raise SystemExit(f"Removed mod game events remain in {tag_path}: {stale}")

    expected_mc_tags = {
        "data/minecraft/tags/items/arrows.json": "archie_dinosaurs:ooze_tipped_arrow",
        "data/minecraft/tags/entity_types/arrows.json": "archie_dinosaurs:pus_tipped_arrow",
        "data/minecraft/tags/damage_type/is_projectile.json": "archie_dinosaurs:pus_arrow",
    }
    for tag_path, expected_entry in expected_mc_tags.items():
        tag = read_json(check.read(tag_path), tag_path)
        if expected_entry not in set(tag.get("values", [])):
            raise SystemExit(f"Required entry {expected_entry} missing from {tag_path}")

    sound_definitions = read_json(check.read("assets/archie_dinosaurs/sounds.json"), "assets/archie_dinosaurs/sounds.json")
    for sound_id in ("item.lushsaur_mace.attack", "item.lushsaur_mace.spin"):
        if sound_id not in sound_definitions:
            raise SystemExit(f"Missing retained Lush Mace sound definition: {sound_id}")

    with check.open("data/archie_dinosaurs/recipes/lush_mace.json") as file:
        mace_recipe = json.load(file)
    with check.open("data/archie_dinosaurs/recipes/ooze_tipped_arrow.json") as file:
        arrow_recipe = json.load(file)
    if mace_recipe.get("result", {}).get("item") != "archie_dinosaurs:lush_mace":
        raise SystemExit("Lush Mace recipe result is incorrect")
    if mace_recipe.get("pattern") != ["W", "S", "S"] or mace_recipe.get("key", {}).get("W", {}).get("item") != "archie_dinosaurs:lush_core":
        raise SystemExit("Lush Mace recipe no longer matches the original Lush Core + 2 Dripstone pattern")
    if arrow_recipe.get("result", {}).get("item") != "archie_dinosaurs:ooze_tipped_arrow" or arrow_recipe.get("result", {}).get("count") != 4:
        raise SystemExit("Ooze Tipped Arrow recipe result is incorrect")
    if arrow_recipe.get("pattern") != ["W", "I", "S"] or arrow_recipe.get("key", {}).get("W", {}).get("item") != "archie_dinosaurs:sculk_toxin":
        raise SystemExit("Ooze Tipped Arrow recipe no longer matches the original Toxin + Rod + Feather pattern")

    if "assets/archie_dinosaurs/textures/gui/back.png" not in names:
        raise SystemExit("Retained advancement background texture is missing")
    if "data/archie_dinosaurs/advancements/root.json" not in names:
        raise SystemExit("Retained root advancement is missing")

validate_advancement_graph(kept)
validate_entity_namespace(kept)
validate_loot_tables(kept)

report = Path("build/resource-prune-report.txt")
report.parent.mkdir(parents=True, exist_ok=True)
report.write_text(
    "\n".join([
        f"Input JAR: {source}",
        f"Output JAR: {target}",
        f"Resource entries kept: {len(kept)}",
        f"Resource entries removed: {len(removed)}",
        f"Output size: {target.stat().st_size} bytes",
        "",
        "Removed resource entries:",
        *sorted(removed),
    ]) + "\n",
    encoding="utf-8",
)

print(f"Output JAR: {target}")
print(f"Resource entries kept: {len(kept)}")
print(f"Resource entries removed: {len(removed)}")
print(f"Output size: {target.stat().st_size} bytes")
print(f"Report: {report}")
