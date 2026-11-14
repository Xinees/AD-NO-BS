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
}
MOBS = {"lushsaur", "chorusoatl", "sculksaur"}
EGGS = {"lushsaur_egg", "chorusoatl_egg"}
SOUND_PREFIXES = (
    "mob.dino.",
    "mob.lushsaur.",
    "mob.chorusoatl.",
    "mob.sculksaur.",
)
KEEP_MIXINS = {
    "mixins": [
        "AbstractArrowMixin",
        "BreedGoalAccessor",
        "CriteriaTriggersAccessor",
        "EntityAccessor",
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
    "mixins": [],
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

    output_lines = []
    for line in logical_lines:
        if not line:
            output_lines.append("")
            continue
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
ENTITY_REGISTRY_NAMES = MOBS | {"chorusoatl_spit"}
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
        "ec_ad",
        "category.ec_ad.default",
        "effect.ec_ad.dazed",
        "item_group.ec_ad.items",
        "message.ec_ad.pet_not_found",
        "message.ec_ad.pet_teleport_success",
        "message.ec_ad.pet_world_unavailable",
        "message.ec_ad.status.buried",
        "archie_dinosaurs.mount_primary_attack",
        "archie_dinosaurs.mount_special_attack",
    }:
        return True

    if key.startswith("advancement.root."):
        return True

    for mob in MOBS:
        if any(key.startswith(f"advancement.{kind}_{mob}.") for kind in ("kill", "spot", "tame")):
            return True
        if key == f"entity.archie_dinosaurs.{mob}":
            return True
        if key.startswith(f"subtitles.ec_ad.mob.{mob}."):
            return True

    if key == "entity.archie_dinosaurs.chorusoatl_spit":
        return True
    if key.startswith("subtitles.ec_ad.mob.dino."):
        return True
    if key in {"block.ec_ad.lushsaur_egg", "block.ec_ad.chorusoatl_egg"}:
        return True
    if key in {
        "item.ec_ad.lush_core",
        "item.ec_ad.chorus_fossil",
        "item.ec_ad.sculk_toxin",
        "item.ec_ad.lushsaur_spawn_egg",
        "item.ec_ad.chorusoatl_spawn_egg",
        "item.ec_ad.sculksaur_spawn_egg",
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
        return base == "chorusoatl_spawns.json"

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
        return base in {"spawns_lushsaurs.json", "spawns_chorusoatls.json"}

    if low.startswith("worldgen/structure/"):
        return base == "lushsaur.json"

    if low.startswith("worldgen/structure_set/"):
        return base == "lushsaur.json"

    if low.startswith("structures/"):
        return base in {"lushsaur.nbt", "lushsaur_kid.nbt"}

    if low.startswith("damage_type/"):
        return False

    return False

def filter_loot_entry(entry):
    if isinstance(entry, dict):
        if entry.get("type") == "minecraft:item":
            item = entry.get("name")
            if isinstance(item, str) and item.startswith("archie_dinosaurs:") and item.split(":", 1)[1] not in ITEMS:
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
    for pool in pools:
        if not isinstance(pool, dict) or not isinstance(pool.get("entries"), list):
            raise RuntimeError(f"Loot table {name} has malformed pool entries")
        pool["entries"] = [filter_loot_entry(entry) for entry in pool["entries"]]
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
    allowed = ITEMS | EGGS | MOBS | {"chorusoatl_spit"}
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
                if name.endswith("/en_us.json"):
                    lang["advancement.root.desc"] = "Meet the dinosaurs"
                elif name.endswith("/ru_ru.json"):
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
    }
    missing = sorted(required - names)
    if missing:
        raise SystemExit(f"Required resources missing from output: {missing}")

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
