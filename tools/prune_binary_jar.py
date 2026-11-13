#!/usr/bin/env python3
import json
import re
import sys
import zipfile
from pathlib import Path

if len(sys.argv) != 3:
    raise SystemExit("Usage: prune_binary_jar.py <classes-pruned.jar> <legacy.jar>")

source = Path(sys.argv[1])
target = Path(sys.argv[2])
if not source.is_file():
    raise SystemExit(f"Input JAR not found: {source}")

MOD = "assets/ec_ad/"
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
        "ec_ad.mount_primary_attack",
        "ec_ad.mount_special_attack",
    }:
        return True

    if key.startswith("advancement.root."):
        return True

    for mob in MOBS:
        if any(key.startswith(f"advancement.{kind}_{mob}.") for kind in ("kill", "spot", "tame")):
            return True
        if key == f"entity.ec_ad.{mob}":
            return True
        if key.startswith(f"subtitles.ec_ad.mob.{mob}."):
            return True

    if key == "entity.ec_ad.chorusoatl_spit":
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
    rel = path[len("data/ec_ad/"):]
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

def filter_tag_json(data, name):
    value = read_json(data, name)
    if isinstance(value, dict) and isinstance(value.get("values"), list):
        allowed_ids = {
            "ec_ad:lushsaur", "ec_ad:chorusoatl", "ec_ad:sculksaur",
            "ec_ad:chorusoatl_spit",
        }
        value["values"] = [
            item for item in value["values"]
            if not (isinstance(item, str) and item.startswith("ec_ad:"))
            or item in allowed_ids
        ]
    return dump_json(value)

def filter_root_advancement(data):
    value = read_json(data, "data/ec_ad/advancements/root.json")
    criteria = {}
    for mob in sorted(MOBS):
        criteria[f"spot_{mob}"] = {
            "conditions": {
                "entity": [
                    {
                        "condition": "minecraft:entity_properties",
                        "entity": "this",
                        "predicate": {"type": f"ec_ad:{mob}"},
                    }
                ]
            },
            "trigger": "ec_ad:spotted_entity",
        }
    value["criteria"] = criteria
    value["requirements"] = [list(criteria)]
    display = value.setdefault("display", {})
    display["icon"] = {"item": "ec_ad:lush_core"}
    display["background"] = "ec_ad:textures/gui/back.png"
    return dump_json(value)


def validate_advancement_graph(entries):
    docs = {}
    for name, data in entries.items():
        if name.startswith("data/ec_ad/advancements/") and name.endswith(".json"):
            docs[name[len("data/ec_ad/advancements/"):-5]] = read_json(data, name)
    valid_icons = {
        "ec_ad:lush_core", "ec_ad:chorus_fossil", "ec_ad:sculk_toxin",
        "ec_ad:lushsaur_egg", "ec_ad:chorusoatl_egg",
        "ec_ad:lushsaur_spawn_egg", "ec_ad:chorusoatl_spawn_egg",
        "ec_ad:sculksaur_spawn_egg",
    }
    for name, doc in docs.items():
        parent = doc.get("parent")
        if parent and parent.startswith("ec_ad:") and parent.split(":", 1)[1] not in docs:
            raise RuntimeError(f"Advancement {name} references missing parent {parent}")
        icon = doc.get("display", {}).get("icon", {}).get("item")
        if icon and icon.startswith("ec_ad:") and icon not in valid_icons:
            raise RuntimeError(f"Advancement {name} references removed item {icon}")
    for name in entries:
        if name.startswith("data/ec_ad/recipes/archie_trade/"):
            raise RuntimeError(f"Removed Archie trade recipe leaked into output: {name}")


kept = {}
removed = []
with zipfile.ZipFile(source, "r") as zin:
    for info in zin.infolist():
        name = info.filename
        if info.is_dir():
            continue

        if name.upper().endswith((".SF", ".RSA", ".DSA")) or name.upper() == "META-INF/INDEX.LIST":
            removed.append(name)
            continue

        data = zin.read(name)

        if name.startswith(MOD):
            if not keep_asset(name):
                removed.append(name)
                continue

            if name.startswith(MOD + "lang/"):
                lang = read_json(data, name)
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

        elif name.startswith("data/ec_ad/"):
            if not keep_data_path(name):
                removed.append(name)
                continue
            if name == "data/ec_ad/advancements/root.json":
                data = filter_root_advancement(data)
            elif "/tags/" in name and name.endswith(".json"):
                data = filter_tag_json(data, name)

        elif name.startswith("data/") and not name.startswith("data/ec_ad/"):
            removed.append(name)
            continue

        elif name == "ec_ad.mixins.json":
            data = filter_mixin_config(data, KEEP_MIXINS, name)

        elif name == "ec_ad.forge.mixins.json":
            data = filter_mixin_config(data, KEEP_FORGE_MIXINS, name)

        elif name.startswith("assets/") and not name.startswith(MOD):
            removed.append(name)
            continue

        elif name.startswith("data/"):
            removed.append(name)
            continue

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
        "ec_ad.mixins.json",
        "ec_ad.forge.mixins.json",
        "pack.mcmeta",
    }
    missing = sorted(required - names)
    if missing:
        raise SystemExit(f"Required resources missing from output: {missing}")

    allowed_item_models = {
        f"assets/ec_ad/models/item/{item}.json" for item in ITEMS
    }
    missing_items = sorted(allowed_item_models - names)
    if missing_items:
        raise SystemExit(f"Required item models missing: {missing_items}")
    if "assets/ec_ad/textures/gui/back.png" not in names:
        raise SystemExit("Retained advancement background texture is missing")
    if "data/ec_ad/advancements/root.json" not in names:
        raise SystemExit("Retained root advancement is missing")

validate_advancement_graph(kept)

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
