/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.platform.InputConstants$Key
 *  com.mojang.blaze3d.platform.InputConstants$Type
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.client.KeyMapping
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 */
package com.eternal_creations.archies_dino_mod.client;

import com.eternal_creations.archies_dino_mod.common.entity.base.Mount;
import com.eternal_creations.archies_dino_mod.common.items.util.ADArmorItem;
import com.eternal_creations.archies_dino_mod.networking.serverbound.MountControlMessage;
import com.eternal_creations.archies_dino_mod.platform.Services;
import com.mojang.blaze3d.platform.InputConstants;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ADKeyMappings {
    public static final String DEFAULT_MOD_CATEGORY = "category.ec_ad.default";
    public static final Map<KeyMapping, BiConsumer<LocalPlayer, ClientLevel>> KEY_MAPPINGS = new HashMap<KeyMapping, BiConsumer<LocalPlayer, ClientLevel>>();
    public static final KeyMapping ARMOR_ABILITY = ADKeyMappings.create("armor", InputConstants.Type.KEYSYM, 73, "category.ec_ad.default", (localPlayer, level) -> {
        Item patt1611$temp;
        Map<EquipmentSlot, ItemStack> armorSet = ADArmorItem.getArmorSet((LivingEntity)localPlayer);
        if (armorSet.size() == 4 && (patt1611$temp = armorSet.get(EquipmentSlot.HEAD).m_41720_()) instanceof ADArmorItem) {
            ADArmorItem armorItem = (ADArmorItem)patt1611$temp;
            armorItem.executeInputPress((Player)localPlayer);
        }
    });
    public static final KeyMapping MOUNT_ATTACK_PRIMARY = ADKeyMappings.createConditional("mount_primary_attack", InputConstants.Type.MOUSE, 0, "category.ec_ad.default", (localPlayer, level) -> {
        Mount mount;
        Entity vehicle = localPlayer.m_20202_();
        if (vehicle instanceof Mount && (mount = (Mount)vehicle).mount().isNoPassengerCooldown()) {
            Services.NETWORK.sendToServer(new MountControlMessage(vehicle.m_19879_(), Mount.AttackType.PRIMARY));
        }
    }, ADKeyMappings::isOnMount);
    public static final KeyMapping MOUNT_ATTACK_SPECIAL = ADKeyMappings.createConditional("mount_special_attack", InputConstants.Type.MOUSE, 1, "category.ec_ad.default", (localPlayer, level) -> {
        Mount mount;
        Entity vehicle = localPlayer.m_20202_();
        if (vehicle instanceof Mount && (mount = (Mount)vehicle).mount().isNoPassengerCooldown()) {
            Services.NETWORK.sendToServer(new MountControlMessage(vehicle.m_19879_(), Mount.AttackType.SPECIAL));
        }
    }, ADKeyMappings::isOnMount);

    private static boolean isOnMount(LocalPlayer player) {
        return player.m_20202_() instanceof Mount;
    }

    private static KeyMapping create(String name, InputConstants.Type type, int keyCode, String category, BiConsumer<LocalPlayer, ClientLevel> consumer) {
        KeyMapping mapping = new KeyMapping("ec_ad." + name, type, keyCode, category);
        KEY_MAPPINGS.put(mapping, consumer);
        return mapping;
    }

    private static KeyMapping createConditional(String name, InputConstants.Type type, int keyCode, String category, BiConsumer<LocalPlayer, ClientLevel> consumer, Predicate<LocalPlayer> predicate) {
        KeyMappingConditional mapping = new KeyMappingConditional("ec_ad." + name, type, keyCode, category, predicate);
        KEY_MAPPINGS.put(mapping, consumer);
        return mapping;
    }

    public static class KeyMappingConditional
    extends KeyMapping {
        private final Predicate<LocalPlayer> predicate;

        public KeyMappingConditional(String name, int keyCode, String category, Predicate<LocalPlayer> predicate) {
            super(name, keyCode, category);
            this.predicate = predicate;
        }

        public KeyMappingConditional(String name, InputConstants.Type type, int keyCode, String category, Predicate<LocalPlayer> predicate) {
            super(name, type, keyCode, category);
            this.predicate = predicate;
        }

        public boolean isActive(LocalPlayer player) {
            return this.predicate.test(player);
        }

        public Predicate<LocalPlayer> getPredicate() {
            return this.predicate;
        }
    }

    public static interface KeyMappingExtension {
        public InputConstants.Key getKey();

        public int getClickCount();

        public void setClickCount(int var1);

        public void setDown(boolean var1);
    }
}

