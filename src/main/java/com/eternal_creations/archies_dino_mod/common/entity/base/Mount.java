/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.Container
 *  net.minecraft.world.ContainerListener
 *  net.minecraft.world.MenuProvider
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.HasCustomInventoryScreen
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Saddleable
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.MenuConstructor
 *  net.minecraft.world.inventory.MenuType
 *  net.minecraft.world.level.gameevent.GameEvent
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.Nullable
 *  org.joml.Vector3d
 */
package com.eternal_creations.archies_dino_mod.common.entity.base;

import com.eternal_creations.archies_dino_mod.common.entity.component.MountComponent;
import com.eternal_creations.archies_dino_mod.common.menu.ADMenus;
import com.eternal_creations.archies_dino_mod.common.menu.DinoMenu;
import java.util.function.BiPredicate;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerListener;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HasCustomInventoryScreen;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Saddleable;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuConstructor;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3d;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public interface Mount
extends HasCustomInventoryScreen,
MenuConstructor,
MenuProvider,
ContainerListener,
Saddleable {
    default public float getCameraDistance(float partialTick) {
        return 2.0f;
    }

    default public void onRiderAttack(LivingEntity rider, AttackType type) {
    }

    public Vector3d getCameraPosition();

    public void setCameraPosition(float var1, float var2, float var3);

    public void setSaddled(boolean var1);

    default public void m_5853_(@Nullable SoundSource soundSource) {
        this.setSaddled(true);
    }

    public MountComponent<?> mount();

    public boolean canMountRotateBody();

    public boolean canMountRotateHead();

    default public void m_213583_(Player player) {
        player.m_5893_((MenuProvider)this);
        if (!player.m_9236_().f_46443_) {
            player.m_146852_(GameEvent.f_157803_, (Entity)player);
        }
    }

    default public DinoMenu createMenu(int containerID, Inventory inventory, Player player) {
        return (DinoMenu)this.getMenuType().m_39985_(containerID, inventory);
    }

    default public MenuType<? extends DinoMenu> getMenuType() {
        return ADMenus.DINO.get();
    }

    default public void m_5757_(Container container) {
    }

    public static enum PointType {
        BONE,
        LOCATOR;

    }

    public record MountPoint<T extends LivingEntity>(BiPredicate<T, Integer> seat, PointType type, String name, Vec3 offset) {
        public MountPoint(boolean saddled, PointType type, String name, Vec3 offset) {
            this((t, integer) -> ((Saddleable)t).m_6254_() == saddled, type, name, offset);
        }
    }

    public static enum AttackType {
        PRIMARY,
        SPECIAL;

    }
}

