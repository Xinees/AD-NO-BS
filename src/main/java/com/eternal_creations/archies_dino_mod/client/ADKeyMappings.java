package com.eternal_creations.archies_dino_mod.client;

import com.eternal_creations.archies_dino_mod.common.entity.base.Mount;
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

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ADKeyMappings {
    public static final String DEFAULT_MOD_CATEGORY = "category.ec_ad.default";
    public static final Map<KeyMapping, BiConsumer<LocalPlayer, ClientLevel>> KEY_MAPPINGS = new HashMap<>();
    public static final KeyMapping MOUNT_ATTACK_PRIMARY = createConditional(
        "mount_primary_attack", InputConstants.Type.MOUSE, 0, DEFAULT_MOD_CATEGORY,
        (player, level) -> sendMountControl(player, Mount.AttackType.PRIMARY), ADKeyMappings::isOnMount);
    public static final KeyMapping MOUNT_ATTACK_SPECIAL = createConditional(
        "mount_special_attack", InputConstants.Type.MOUSE, 1, DEFAULT_MOD_CATEGORY,
        (player, level) -> sendMountControl(player, Mount.AttackType.SPECIAL), ADKeyMappings::isOnMount);

    private static void sendMountControl(LocalPlayer player, Mount.AttackType type) {
        if (player.m_20202_() instanceof Mount mount && mount.mount().isNoPassengerCooldown()) {
            Services.NETWORK.sendToServer(new MountControlMessage(player.m_20202_().m_19879_(), type));
        }
    }

    private static boolean isOnMount(LocalPlayer player) {
        return player.m_20202_() instanceof Mount;
    }

    private static KeyMapping createConditional(String name, InputConstants.Type type, int keyCode,
            String category, BiConsumer<LocalPlayer, ClientLevel> consumer, Predicate<LocalPlayer> predicate) {
        KeyMappingConditional mapping = new KeyMappingConditional("ec_ad." + name, type, keyCode, category, predicate);
        KEY_MAPPINGS.put(mapping, consumer);
        return mapping;
    }

    public static class KeyMappingConditional extends KeyMapping {
        private final Predicate<LocalPlayer> predicate;

        public KeyMappingConditional(String name, InputConstants.Type type, int keyCode, String category,
                Predicate<LocalPlayer> predicate) {
            super(name, type, keyCode, category);
            this.predicate = predicate;
        }

        public boolean isActive(LocalPlayer player) { return predicate.test(player); }
        public Predicate<LocalPlayer> getPredicate() { return predicate; }
    }

    public interface KeyMappingExtension {
        InputConstants.Key getKey();
        int getClickCount();
        void setClickCount(int count);
        void setDown(boolean down);
    }
}
