#!/usr/bin/env python3
from __future__ import annotations

import argparse
from pathlib import Path


def replace_method(source: str, signature: str, replacement: str) -> str:
    position = source.find(signature)
    if position < 0:
        raise RuntimeError(f"method not found: {signature}")
    start = source.rfind("\n", 0, position) + 1
    opening = source.find("{", position)
    if opening < 0:
        raise RuntimeError(f"method body not found: {signature}")

    depth = 0
    for index in range(opening, len(source)):
        if source[index] == "{":
            depth += 1
        elif source[index] == "}":
            depth -= 1
            if depth == 0:
                end = index + 1
                if end < len(source) and source[end] == "\r":
                    end += 1
                if end < len(source) and source[end] == "\n":
                    end += 1
                return source[:start] + replacement + source[end:]
    raise RuntimeError(f"unbalanced method: {signature}")


def patch_source(source: str) -> str:
    old_y_check = "if (py <= 67 || py >= 250)"
    if source.count(old_y_check) != 1:
        raise RuntimeError("expected exactly one legacy upper-Y landing check")
    source = source.replace(old_y_check, "if (py <= 67)", 1)

    old_call = "validPlatform(level, posClass, heightType, px, py, pz)"
    if source.count(old_call) != 2:
        raise RuntimeError("expected two landing validation call sites")
    source = source.replace(old_call, "validPlatform(mob, level, posClass, heightType, px, py, pz)")

    valid_platform = """    private static boolean validPlatform(
        Object mob, Object level, Class<?> posClass, Object heightType, int x, int y, int z
    ) throws Exception {
        Constructor<?> constructor = posClass.getConstructor(int.class, int.class, int.class);
        Class<?> directionClass = Class.forName("net.minecraft.core.Direction");
        Object upDirection = enumValue(directionClass, "UP");
        if (upDirection == null || heightType == null) {
            return false;
        }
        Object mobBox = call(mob, new String[]{"m_20191_"});
        if (mobBox == null) {
            return false;
        }
        double mobHeight = boxValue(mobBox, "f_82292_") - boxValue(mobBox, "f_82289_");
        if (!(mobHeight > 0.0) || !Double.isFinite(mobHeight)) {
            return false;
        }
        int clearHeight = (int)Math.ceil(mobHeight);
        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                Object heightValue = call(level, new String[]{"m_6924_"}, heightType, x + dx, z + dz);
                if (!(heightValue instanceof Number)) {
                    return false;
                }
                int surfaceY = ((Number)heightValue).intValue();
                if (surfaceY > y) {
                    return false;
                }
                Object floorPos = constructor.newInstance(x + dx, surfaceY - 1, z + dz);
                Object support = call(level, new String[]{"m_8055_"}, floorPos);
                if (support == null || bool(call(support, new String[]{"m_60795_"})) ||
                    !bool(call(support, new String[]{"m_60783_"}, level, floorPos, upDirection))) {
                    return false;
                }
                for (int up = 0; up < clearHeight; up++) {
                    Object airPos = constructor.newInstance(x + dx, y + up, z + dz);
                    Object state = call(level, new String[]{"m_8055_"}, airPos);
                    if (state == null || !bool(call(state, new String[]{"m_60795_"}))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
"""
    source = replace_method(source, "private static boolean validPlatform(", valid_platform)

    overlap = """    private static boolean overlapsOtherChorusoatl(Object mob, double[] point) {
        try {
            Class<?> chorusClass = mob.getClass();
            Object currentBox = call(mob, new String[]{"m_20191_"});
            Object currentX = call(mob, new String[]{"m_20185_"});
            Object currentY = call(mob, new String[]{"m_20186_"});
            Object currentZ = call(mob, new String[]{"m_20189_"});
            if (currentBox == null || !(currentX instanceof Number) ||
                !(currentY instanceof Number) || !(currentZ instanceof Number)) {
                return true;
            }
            Object box = call(
                currentBox,
                new String[]{"m_82386_"},
                point[0] - ((Number)currentX).doubleValue(),
                point[1] - ((Number)currentY).doubleValue(),
                point[2] - ((Number)currentZ).doubleValue()
            );
            if (box == null) {
                return true;
            }
            Object level = call(mob, new String[]{"m_9236_"});
            Object result = call(level, new String[]{"m_45976_"}, chorusClass, box);
            if (!(result instanceof Collection<?>)) {
                return true;
            }
            for (Object other : (Collection<?>)result) {
                if (other == mob) {
                    continue;
                }
                Object otherBox = call(other, new String[]{"m_20191_"});
                if (otherBox == null) {
                    return true;
                }
                Object intersects = call(otherBox, new String[]{"m_82381_", "intersects"}, box);
                if (!(intersects instanceof Boolean)) {
                    return true;
                }
                if (((Boolean)intersects).booleanValue()) {
                    return true;
                }
            }
        } catch (Throwable ignored) {
            return true;
        }
        return false;
    }
"""
    source = replace_method(source, "private static boolean overlapsOtherChorusoatl(", overlap)

    forbidden = (
        "py >= 250", "point[0] - 1.8", "point[1] + 5.2", "up < 6",
        "surfaceY < y - 1", "validPlatform(level, posClass, heightType, px, py, pz)",
    )
    for token in forbidden:
        if token in source:
            raise RuntimeError(f"legacy landing constraint remains: {token}")
    required = (
        "validPlatform(mob, level, posClass, heightType, px, py, pz)",
        'boxValue(mobBox, "f_82292_") - boxValue(mobBox, "f_82289_")',
        'new String[]{"m_82386_"}',
        'new String[]{"m_82381_", "intersects"}',
        "if (!(result instanceof Collection<?>))",
        "if (!(intersects instanceof Boolean))",
    )
    for token in required:
        if token not in source:
            raise RuntimeError(f"landing geometry patch missing: {token}")
    return source


def main() -> None:
    parser = argparse.ArgumentParser()
    parser.add_argument("source", type=Path)
    args = parser.parse_args()
    original = args.source.read_text(encoding="utf-8")
    patched = patch_source(original)
    args.source.write_text(patched, encoding="utf-8")
    print("Applied and verified live-hitbox Chorusoatl landing geometry polish.")


if __name__ == "__main__":
    main()
