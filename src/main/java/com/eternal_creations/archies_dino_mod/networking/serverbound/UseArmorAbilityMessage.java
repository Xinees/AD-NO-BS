/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemCooldowns
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.phys.Vec3
 */
package com.eternal_creations.archies_dino_mod.networking.serverbound;

import com.eternal_creations.archies_dino_mod.Constants;
import com.eternal_creations.archies_dino_mod.common.ADStrings;
import com.eternal_creations.archies_dino_mod.common.items.util.ADArmorItem;
import com.eternal_creations.archies_dino_mod.networking.serverbound.AbstractServerboundMessage;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Map;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public final class UseArmorAbilityMessage
extends AbstractServerboundMessage {
    public static final ResourceLocation ID = Constants.prefix("use_armor_ability");
    private static final Codec<UseArmorAbilityMessage> CODEC = RecordCodecBuilder.create(recordCodecBuilder -> recordCodecBuilder.group((App)Vec3.f_231074_.fieldOf("pos").forGetter(UseArmorAbilityMessage::getPos), (App)Vec3.f_231074_.fieldOf("view_vector").forGetter(UseArmorAbilityMessage::getViewVector)).apply((Applicative)recordCodecBuilder, UseArmorAbilityMessage::new));
    private final Vec3 pos;
    private final Vec3 viewVector;

    public UseArmorAbilityMessage(Vec3 pos, Vec3 viewVector) {
        this.pos = pos;
        this.viewVector = viewVector;
    }

    public static UseArmorAbilityMessage decode(FriendlyByteBuf buf) {
        return (UseArmorAbilityMessage)buf.m_271872_(CODEC);
    }

    public Vec3 getPos() {
        return this.pos;
    }

    public Vec3 getViewVector() {
        return this.viewVector;
    }

    @Override
    public ResourceLocation getID() {
        return ID;
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.m_272073_(CODEC, (Object)this);
    }

    @Override
    public void execute(ServerPlayer player) {
        Item item;
        Map<EquipmentSlot, ItemStack> armorSet = ADArmorItem.getArmorSet((LivingEntity)player);
        if (armorSet.size() == 4 && (item = armorSet.get(EquipmentSlot.HEAD).m_41720_()) instanceof ADArmorItem) {
            ADArmorItem item2 = (ADArmorItem)item;
            ItemCooldowns playerCooldowns = player.m_36335_();
            float biggestCooldown = 0.0f;
            for (ItemStack stack2 : armorSet.values()) {
                if (!playerCooldowns.m_41519_(stack2.m_41720_())) continue;
                float per = playerCooldowns.m_41521_(stack2.m_41720_(), 1.0f);
                if (!(per > biggestCooldown)) break;
                biggestCooldown = per;
                break;
            }
            if (biggestCooldown > 0.0f) {
                int percent = Math.round(biggestCooldown * 100.0f);
                player.m_5661_((Component)Component.m_237110_((String)ADStrings.Messages.ARMOR_ON_COOLDOWN, (Object[])new Object[]{percent}), true);
                return;
            }
            if (item2.activateServerSetAbility(player, this.pos, this.viewVector)) {
                int cooldown = item2.getSetAbilityCooldownDuration(player);
                armorSet.forEach((equipmentSlot, stack) -> {
                    if (cooldown > 0) {
                        playerCooldowns.m_41524_(stack.m_41720_(), cooldown);
                    }
                    stack.m_41622_(1, (LivingEntity)player, living -> living.m_21166_(equipmentSlot));
                });
            }
        } else {
            player.m_5661_((Component)Component.m_237115_((String)ADStrings.Messages.ARMOR_NO_SET), true);
        }
    }
}

