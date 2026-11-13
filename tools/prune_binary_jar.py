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
        "BlockMixin",
        "BreedGoalAccessor",
        "CriteriaTriggersAccessor",
        "DamageEnchantmentMixin",
        "DamageSourcesMixin",
        "EnchantmentCategoryMixin",
        "EnchantmentHelperMixin",
        "EntityAccessor",
        "EntityMixin",
        "LivingEntityMixin",
        "PlayerInterfaceMixin",
        "PlayerListMixin",
        "PlayerMixin",
        "ServerPlayerInterfaceMixin",
        "VibrationSystemListenerMixin",
        "sculksaur.PrimaryLevelDataMixin",
        "sculksaur.SculkShriekerBlockEntityMixin",
        "sculksaur.WalkNodeEvaluatorAccessor",
        "sculksaur.WardenMixin",
    ],
    "client": [
        "MultiplayerGameModeMixin",
        "client.CameraMixin",
        "client.ClientAdvancementsAccessor",
        "client.KeyMappingMixin",
        "client.LevelRendererMixin",
        "client.LivingEntityRendererMixin",
        "client.LocalPlayerMixin",
        "client.MinecraftMixin",
    ],
}
KEEP_FORGE_MIXINS = {
    "mixins": ["forge.CustomStructurePieceMixin"],
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
    k = key.lower()
    if any(mob in k for mob in MOBS):
        return True
    if any(item in k for item in ITEMS):
        return True
    if "egg" in k and ("lushsaur" in k or "chorusoatl" in k):
        return True
    if k.startswith("item_group.ec_ad.items"):
        return True
    if k.startswith("subtitle.ec_ad.mob.dino."):
        return True
    if k.startswith("subtitle.ec_ad.mob.lushsaur."):
        return True
    if k.startswith("subtitle.ec_ad.mob.chorusoatl."):
        return True
    if k.startswith("subtitle.ec_ad.mob.sculksaur."):
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
        return any(low.rsplit("/", 1)[-1] == f"{egg}.png" for egg in EGGS)

    if low.startswith("textures/item/"):
        name = low.rsplit("/", 1)[-1]
        return name.endswith(".png") and name[:-4] in ITEMS

    if low.startswith("textures/mob_effect/"):
        return low.endswith("/dazed.png")

    if low.startswith("textures/emitter/"):
        return low.endswith(".png") or low.endswith(".json")

    if low.startswith("textures/"):
        return any(mob in low for mob in MOBS)

    return False

def keep_data_path(path):
    rel = path[len("data/ec_ad/"):]
    low = rel.lower()
    base = low.rsplit("/", 1)[-1]

    if low.startswith("advancements/"):
        if base == "root.json":
            return True
        return any(mob in base for mob in MOBS) and (
            base.startswith(("kill_", "master_", "spot_", "tame_"))
        )

    if low.startswith("loot_tables/entities/"):
        return any(base == f"{mob}.json" for mob in MOBS)

    if low.startswith("loot_tables/blocks/"):
        return any(base == f"{egg}.json" for egg in EGGS)

    if low.startswith("forge/biome_modifier/"):
        return base == "chorusoatl_spawns.json"

    if low.startswith("recipes/") or low.startswith("advancements/recipes/"):
        return any(item in base for item in ITEMS)

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
        return base in {"lushsaur.nbt", "lushsaur_kid.nbt"} or low.startswith("structures/fossil/")

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
                data = dump_json(lang)
            elif name == MOD + "sounds.json":
                sounds = read_json(data, name)
                sounds = {k: v for k, v in sounds.items() if k.startswith(SOUND_PREFIXES)}
                data = dump_json(sounds)

        elif name.startswith("data/ec_ad/"):
            if not keep_data_path(name):
                removed.append(name)
                continue
            if "/tags/" in name and name.endswith(".json"):
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

print(f"Output JAR: {target}")
print(f"Resource entries kept: {len(kept)}")
print(f"Resource entries removed: {len(removed)}")
print(f"Output size: {target.stat().st_size} bytes")
