package com.eternal_creations.archies_dino_mod.worldgen;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.entity.living.MobSpawnEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = "archie_dinosaurs", bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class EndPeaksChorusoatlSpawnLimiter {
    private static final ResourceLocation CHORUSOATL_ID =
        new ResourceLocation("archie_dinosaurs", "chorusoatl");
    private static final String GROUP_ID_KEY = "EndPeaksChorusoatlSpawnGroup";
    private static final int MAX_ACTIVE_GROUPS = 3;
    private static final double GROUP_REGION_RADIUS = 512.0D;
    private static final double GROUP_REGION_VERTICAL = 64.0D;
    private static final double GROUP_MEMBER_RADIUS = 16.0D;
    private static final float BABY_GROUP_CHANCE = 0.92F;
    private static final double HORIZONTAL_CLEARANCE = 0.05D;
    private static final double HEAD_CLEARANCE = 0.08D;

    @SubscribeEvent
    public static void checkNaturalSpawnSpace(MobSpawnEvent.PositionCheck event) {
        Mob entity = event.getEntity();
        if (event.getSpawnType() != MobSpawnType.NATURAL || !isChorusoatl(entity)) {
            return;
        }

        BlockPos position = entity.blockPosition();
        if (!entity.level().getBiome(position).is(EndPeaksWorldgen.BIOME)) {
            return;
        }

        AABB box = entity.getBoundingBox();
        AABB adultClearance = new AABB(
            box.minX - HORIZONTAL_CLEARANCE,
            box.minY + 0.01D,
            box.minZ - HORIZONTAL_CLEARANCE,
            box.maxX + HORIZONTAL_CLEARANCE,
            box.maxY + HEAD_CLEARANCE,
            box.maxZ + HORIZONTAL_CLEARANCE
        );

        if (adultClearance.minY < entity.level().getMinBuildHeight()
            || adultClearance.maxY > entity.level().getMaxBuildHeight()
            || !entity.level().noCollision(entity, adultClearance)) {
            event.setResult(Event.Result.DENY);
        }
    }

    @SubscribeEvent
    public static void limitNaturalGroups(MobSpawnEvent.FinalizeSpawn event) {
        Mob entity = event.getEntity();
        if (event.getSpawnType() != MobSpawnType.NATURAL
            || !isChorusoatl(entity)
            || !(entity.level() instanceof ServerLevel level)) {
            return;
        }

        BlockPos position = entity.blockPosition();
        if (!level.getBiome(position).is(EndPeaksWorldgen.BIOME)) {
            return;
        }

        UUID groupId = findNearbyGroup(level, entity);

        if (groupId == null) {
            Set<UUID> activeGroups = findActiveGroups(level, entity);
            if (activeGroups.size() >= MAX_ACTIVE_GROUPS) {
                event.setSpawnCancelled(true);
                return;
            }
            groupId = UUID.randomUUID();
        }

        CompoundTag persistentData = entity.getPersistentData();
        persistentData.putUUID(GROUP_ID_KEY, groupId);

        if (entity instanceof AgeableMob && event.getSpawnData() == null) {
            event.setSpawnData(new AgeableMob.AgeableMobGroupData(BABY_GROUP_CHANCE));
        }
    }

    private static UUID findNearbyGroup(ServerLevel level, Entity entity) {
        double x = entity.getX();
        double y = entity.getY();
        double z = entity.getZ();
        AABB area = new AABB(
            x - GROUP_MEMBER_RADIUS,
            y - GROUP_MEMBER_RADIUS,
            z - GROUP_MEMBER_RADIUS,
            x + GROUP_MEMBER_RADIUS,
            y + GROUP_MEMBER_RADIUS,
            z + GROUP_MEMBER_RADIUS
        );

        for (Entity candidate : level.getEntitiesOfClass(Entity.class, area,
            other -> other != entity
                && isChorusoatl(other)
                && other.getPersistentData().hasUUID(GROUP_ID_KEY))) {
            return candidate.getPersistentData().getUUID(GROUP_ID_KEY);
        }
        return null;
    }

    private static Set<UUID> findActiveGroups(ServerLevel level, Entity entity) {
        double x = entity.getX();
        double y = entity.getY();
        double z = entity.getZ();
        AABB area = new AABB(
            x - GROUP_REGION_RADIUS,
            y - GROUP_REGION_VERTICAL,
            z - GROUP_REGION_RADIUS,
            x + GROUP_REGION_RADIUS,
            y + GROUP_REGION_VERTICAL,
            z + GROUP_REGION_RADIUS
        );

        Set<UUID> groups = new HashSet<>();
        for (Entity candidate : level.getEntitiesOfClass(Entity.class, area,
            other -> other != entity
                && isChorusoatl(other)
                && other.getPersistentData().hasUUID(GROUP_ID_KEY))) {
            groups.add(candidate.getPersistentData().getUUID(GROUP_ID_KEY));
        }
        return groups;
    }

    private static boolean isChorusoatl(Entity entity) {
        return entity.getType().builtInRegistryHolder().key().location().equals(CHORUSOATL_ID);
    }

    private EndPeaksChorusoatlSpawnLimiter() {
    }
}
