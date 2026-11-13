/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 */
package com.eternal_creations.archies_dino_mod.common.entity.component;

import com.eternal_creations.archies_dino_mod.common.entity.component.EntityComponent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class EntityComponentHolder
implements EntityComponent {
    private final List<EntityComponent> components = new ArrayList<EntityComponent>();
    private final Map<Class<? extends EntityComponent>, EntityComponent> componentMap = new HashMap<Class<? extends EntityComponent>, EntityComponent>();

    public <Component extends EntityComponent> Component addComponent(Component component) {
        this.components.add(component);
        this.componentMap.put(component.getClass(), component);
        return component;
    }

    public <Component extends EntityComponent> Component getComponent(Class<Component> clazz) {
        return (Component)this.componentMap.get(clazz);
    }

    public boolean hasComponent(Class<? extends EntityComponent> clazz) {
        return this.componentMap.containsKey(clazz);
    }

    public List<EntityComponent> getComponents() {
        return this.components;
    }

    public Map<Class<? extends EntityComponent>, EntityComponent> getComponentMap() {
        return this.componentMap;
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compound) {
        for (EntityComponent component : this.components) {
            component.addAdditionalSaveData(compound);
        }
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compound) {
        for (EntityComponent component : this.components) {
            component.readAdditionalSaveData(compound);
        }
    }

    @Override
    public void tick() {
        for (EntityComponent component : this.components) {
            component.tick();
        }
    }

    @Override
    public void tickRidden(@NotNull Player player, @NotNull Vec3 motion) {
        for (EntityComponent component : this.components) {
            component.tickRidden(player, motion);
        }
    }

    @Override
    public void aiStep() {
        for (EntityComponent component : this.components) {
            component.aiStep();
        }
    }

    @Override
    public void customServerAiStep() {
        for (EntityComponent component : this.components) {
            component.customServerAiStep();
        }
    }

    @Override
    public Vec3 travel(Vec3 motion) {
        for (EntityComponent component : this.components) {
            motion = component.travel(motion);
        }
        return motion;
    }

    @Override
    public void handleEntityEvent(byte id) {
        for (EntityComponent component : this.components) {
            component.handleEntityEvent(id);
        }
    }

    @Override
    public void positionRider(@NotNull Entity passenger, // Could not load outer class - annotation placement on inner may be incorrect
    @NotNull Entity.MoveFunction callback) {
        for (EntityComponent component : this.components) {
            component.positionRider(passenger, callback);
        }
    }

    @Override
    @NotNull
    public InteractionResult mobInteract(@NotNull Player player, @NotNull InteractionHand hand) {
        InteractionResult result = InteractionResult.PASS;
        for (EntityComponent component : this.components) {
            InteractionResult interact = component.mobInteract(player, hand);
            if (interact == InteractionResult.PASS) continue;
            result = interact;
        }
        return result;
    }

    @Override
    public void addPassenger(@NotNull Entity passenger) {
        for (EntityComponent component : this.components) {
            component.addPassenger(passenger);
        }
    }

    @Override
    public void dropEquipment() {
        for (EntityComponent component : this.components) {
            component.dropEquipment();
        }
    }
}

