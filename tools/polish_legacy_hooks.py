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

    if "WARNING_REPEAT_INTERVAL" in source:
        raise RuntimeError("warning repeat interval is already patched")
    tick_marker = "    public static void lushsaurTick(Object mob) {"
    if source.count(tick_marker) != 1:
        raise RuntimeError("expected exactly one Lushsaur tick hook")
    source = source.replace(
        tick_marker,
        "    private static final long WARNING_REPEAT_INTERVAL = 80L;\n\n" + tick_marker,
        1,
    )

    lush_tick = """    public static void lushsaurTick(Object mob) {
        if (!isServer(mob) || isRemoved(mob) || isTamed(mob)) {
            return;
        }
        try {
            LushState state = LUSH.computeIfAbsent(mob, ignored -> new LushState());
            long now = entityTick(mob);
            Object level = call(mob, new String[]{"m_9236_"});
            Object mobBox = call(mob, new String[]{"m_20191_"});
            if (level == null || mobBox == null) {
                return;
            }
            Object noticeBox = call(mobBox, new String[]{"m_82377_"}, 15.0, 3.0, 15.0);
            Class<?> playerClass = Class.forName("net.minecraft.world.entity.player.Player");
            Collection<?> nearby = collection(call(level, new String[]{"m_45976_"}, playerClass, noticeBox));
            Set<String> observed = new HashSet<>();
            for (Object player : nearby) {
                String id = uuid(player);
                if (id == null) {
                    continue;
                }
                observed.add(id);
                WarningData data = state.warnings.computeIfAbsent(id, ignored -> new WarningData());
                if (data.expiresAt > 0 && now >= data.expiresAt) {
                    data.count = 0;
                    data.expiresAt = 0;
                    data.nextWarningAt = 0;
                }
                if (state.loyal.contains(id)) {
                    data.inside = false;
                    data.berriesSeen = false;
                    data.nextWarningAt = 0;
                    continue;
                }
                Object playerBox = call(player, new String[]{"m_20191_"});
                double distance = horizontalBoxDistance(mobBox, playerBox);
                boolean crouching = bool(call(player, new String[]{"m_6047_"}));
                double limit = crouching ? 5.0 : 9.0;
                boolean inside = boxesOverlapVertically(mobBox, playerBox) && distance < limit;

                data.berriesSeen = holdsGlowBerries(player);
                if (inside && data.count < 3 && (!data.inside || now >= data.nextWarningAt)) {
                    data.count++;
                    data.expiresAt = now + WARNING_WINDOW;
                    data.nextWarningAt = now + WARNING_REPEAT_INTERVAL;
                    lookAt(mob, player);
                    if (data.count < 3) {
                        queueActions(mob, "actionRoar");
                    } else {
                        call(mob, new String[]{"m_6710_"}, player);
                        queueActions(mob, "actionRoar", "actionAttackTailStomp");
                    }
                }
                data.inside = inside;
            }
            for (Map.Entry<String, WarningData> entry : state.warnings.entrySet()) {
                WarningData data = entry.getValue();
                if (data.expiresAt > 0 && now >= data.expiresAt) {
                    data.count = 0;
                    data.expiresAt = 0;
                    data.nextWarningAt = 0;
                }
                if (!observed.contains(entry.getKey())) {
                    data.inside = false;
                    data.berriesSeen = false;
                }
            }
            state.warnings.entrySet().removeIf(entry ->
                entry.getValue().count == 0 &&
                !entry.getValue().inside &&
                !entry.getValue().berriesSeen &&
                !state.loyal.contains(entry.getKey()));
        } catch (Throwable ignored) {
        }
    }
"""
    source = replace_method(source, "public static void lushsaurTick(", lush_tick)

    after_feed = """    public static Object afterLushFeed(Object mob, Object player, Object stack, Object result) {
        try {
            if (isServer(mob) && !isTamed(mob) && isGlowBerries(stack) && successfulResult(result)) {
                String id = uuid(player);
                if (id != null) {
                    LushState state = LUSH.computeIfAbsent(mob, ignored -> new LushState());
                    WarningData warning = state.warnings.get(id);
                    if (warning == null || !warning.berriesSeen) {
                        return result;
                    }
                    state.loyal.add(id);
                    warning.count = 0;
                    warning.expiresAt = 0;
                    warning.inside = false;
                    warning.berriesSeen = false;
                    warning.nextWarningAt = 0;
                    Object currentTarget = call(mob, new String[]{"m_5448_"});
                    if (currentTarget != null && id.equals(uuid(currentTarget))) {
                        call(mob, new String[]{"m_6710_"}, (Object)null);
                    }
                }
            }
        } catch (Throwable ignored) {
        }
        return result;
    }
"""
    source = replace_method(source, "public static Object afterLushFeed(", after_feed)

    save_lush = """    public static void saveLush(Object mob, Object tag) {
        try {
            LushState state = LUSH.get(mob);
            if (state == null) {
                return;
            }
            long now = entityTick(mob);
            StringBuilder output = new StringBuilder();
            for (String id : state.loyal) {
                if (output.length() > 0) output.append(';');
                output.append('L').append(',').append(id);
            }
            for (Map.Entry<String, WarningData> entry : state.warnings.entrySet()) {
                WarningData w = entry.getValue();
                if (output.length() > 0) output.append(';');
                output.append('W').append(',').append(entry.getKey()).append(',')
                    .append(w.count).append(',').append(Math.max(0, w.expiresAt - now)).append(',')
                    .append(w.inside ? 1 : 0).append(',').append(w.berriesSeen ? 1 : 0).append(',')
                    .append(Math.max(0, w.nextWarningAt - now));
            }
            putString(tag, LUSH_SAVE_KEY, output.toString());
        } catch (Throwable ignored) {
        }
    }
"""
    source = replace_method(source, "public static void saveLush(", save_lush)

    load_lush = """    public static void loadLush(Object mob, Object tag) {
        try {
            LushState state = new LushState();
            long now = entityTick(mob);
            String encoded = getString(tag, LUSH_SAVE_KEY);
            if (encoded != null && !encoded.isEmpty()) {
                for (String row : encoded.split(";")) {
                    String[] part = row.split(",");
                    if (part.length >= 2 && "L".equals(part[0])) {
                        state.loyal.add(part[1]);
                    } else if (part.length >= 6 && "W".equals(part[0])) {
                        WarningData w = new WarningData();
                        w.count = Integer.parseInt(part[2]);
                        w.expiresAt = now + Long.parseLong(part[3]);
                        w.inside = "1".equals(part[4]);
                        w.berriesSeen = "1".equals(part[5]);
                        if (part.length >= 7) {
                            w.nextWarningAt = now + Long.parseLong(part[6]);
                        } else if (w.count > 0 && w.inside) {
                            w.nextWarningAt = now + WARNING_REPEAT_INTERVAL;
                        }
                        state.warnings.put(part[1], w);
                    }
                }
            }
            LUSH.put(mob, state);
        } catch (Throwable ignored) {
        }
    }
"""
    source = replace_method(source, "public static void loadLush(", load_lush)

    warning_data = """    private static final class WarningData {
        int count;
        long expiresAt;
        long nextWarningAt;
        boolean inside;
        boolean berriesSeen;
    }"""
    old_warning_data = """    private static final class WarningData {
        int count;
        long expiresAt;
        boolean inside;
        boolean berriesSeen;
    }"""
    if source.count(old_warning_data) != 1:
        raise RuntimeError("expected exactly one WarningData declaration")
    source = source.replace(old_warning_data, warning_data, 1)

    chorus_approach = """    private static void tickChorusApproach(Object mob, ChorusState state, long now) {
        boolean flying = bool(call(mob, new String[]{"isFlying"}));
        boolean actionDown = actionActive(mob, "actionDown");
        double dx = x(mob) - state.targetX;
        double dz = z(mob) - state.targetZ;
        double dy = y(mob) - state.targetY;
        double horizontal = Math.sqrt(dx * dx + dz * dz);

        if (hurtTime(mob) > 0) {
            state.mode = 0;
            state.cooldownUntil = now + CHORUS_REST_COOLDOWN;
            stopNavigation(mob);
            return;
        }

        if (!flying && !actionDown && horizontal <= 6.0 && Math.abs(dy) <= 3.0) {
            state.mode = 2;
            state.perchedUntil = now + CHORUS_PERCH_MIN + randomInt(mob, CHORUS_PERCH_MAX - CHORUS_PERCH_MIN + 1);
            state.cooldownUntil = 0;
            stopNavigation(mob);
            return;
        }

        if (!flying) {
            if (!actionDown) {
                setFlightPhase(mob, "PREPARING_TO_FLY");
            }
            state.nextPathUpdate = now + 1;
            return;
        }

        if (now >= state.nextPathUpdate) {
            Object nav = call(mob, new String[]{"m_21573_"});
            double[] selected = null;
            double[] current = new double[]{state.targetX, state.targetY, state.targetZ};

            if (validLandingPoint(mob, current[0], current[1], current[2]) &&
                startChorusLandingRoute(nav, current)) {
                selected = current;
            }

            if (selected == null) {
                stopNavigation(mob);
                Set<String> attempted = new HashSet<>();
                attempted.add(current[0] + "," + current[1] + "," + current[2]);
                for (int attempt = 0; attempt < 8; attempt++) {
                    double[] candidate = findLandingPoint(mob);
                    if (candidate == null) {
                        break;
                    }
                    String key = candidate[0] + "," + candidate[1] + "," + candidate[2];
                    if (!attempted.add(key)) {
                        continue;
                    }
                    if (!validLandingPoint(mob, candidate[0], candidate[1], candidate[2])) {
                        continue;
                    }
                    if (startChorusLandingRoute(nav, candidate)) {
                        selected = candidate;
                        break;
                    }
                }
            }

            if (selected == null) {
                state.mode = 0;
                state.cooldownUntil = now + CHORUS_REST_COOLDOWN;
                stopNavigation(mob);
                return;
            }

            state.targetX = selected[0];
            state.targetY = selected[1];
            state.targetZ = selected[2];
            state.nextPathUpdate = now + 20;
        }

        dx = x(mob) - state.targetX;
        dz = z(mob) - state.targetZ;
        dy = y(mob) - state.targetY;
        horizontal = Math.sqrt(dx * dx + dz * dz);
        if (flying && horizontal <= 6.0 && Math.abs(dy) <= 4.0) {
            setFlightPhase(mob, "PREPARING_TO_LAND");
        }
    }

    private static boolean startChorusLandingRoute(Object navigation, double[] point) {
        if (navigation == null || point == null) {
            return false;
        }
        try {
            Object result = call(navigation, new String[]{"m_26519_"},
                point[0], point[1], point[2], 2.0);
            return result instanceof Boolean && ((Boolean)result).booleanValue();
        } catch (Throwable ignored) {
            return false;
        }
    }
"""
    source = replace_method(source, "private static void tickChorusApproach(", chorus_approach)


    forbidden = (
        "py >= 250", "point[0] - 1.8", "point[1] + 5.2", "up < 6",
        "surfaceY < y - 1", "validPlatform(level, posClass, heightType, px, py, pz)",
    )
    for token in forbidden:
        if token in source:
            raise RuntimeError(f"legacy landing constraint remains: {token}")
    for signature in (
        "public static boolean chorusoatlActionStateAnimationActive(",
        "public static boolean chorusoatlAirState(",
    ):
        if source.count(signature) != 1:
            raise RuntimeError(f"expected exactly one obsolete vertical-animation helper: {signature}")
        source = replace_method(source, signature, "")

    if "chorusoatlActionStateAnimationActive" in source or "chorusoatlAirState" in source:
        raise RuntimeError("obsolete vertical-only Chorusoatl animation helpers remain")

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
