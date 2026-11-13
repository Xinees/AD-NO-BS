/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.Entity$MoveFunction
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 */
package com.eternal_creations.archies_dino_mod.common.entity.component;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public interface EntityComponent {
    default public void addAdditionalSaveData(@NotNull CompoundTag compound) {
    }

    default public void readAdditionalSaveData(@NotNull CompoundTag compound) {
    }

    default public void tick() {
    }

    default public void tickRidden(@NotNull Player player, @NotNull Vec3 motion) {
    }

    default public void aiStep() {
    }

    default public void customServerAiStep() {
    }

    default public Vec3 travel(Vec3 motion) {
        return motion;
    }

    default public void handleEntityEvent(byte id) {
    }

    default public void positionRider(@NotNull Entity passenger, @NotNull Entity.MoveFunction callback) {
    }

    @NotNull
    default public InteractionResult mobInteract(@NotNull Player player, @NotNull InteractionHand hand) {
        return InteractionResult.PASS;
    }

    default public void addPassenger(@NotNull Entity passenger) {
    }

    default public void dropEquipment() {
    }

    public static interface Definition<T> {
        public EntityComponent build(T var1);
    }
}

