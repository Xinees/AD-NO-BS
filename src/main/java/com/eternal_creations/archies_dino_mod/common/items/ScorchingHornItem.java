/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.network.chat.Component
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.stats.Stats
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.OwnableEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Rarity
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.level.Level
 */
package com.eternal_creations.archies_dino_mod.common.items;

import com.eternal_creations.archies_dino_mod.common.ADStrings;
import com.eternal_creations.archies_dino_mod.common.entity.ADPetData;
import com.eternal_creations.archies_dino_mod.common.sound.ADSoundEvents;
import com.eternal_creations.archies_dino_mod.networking.clientbound.ScorchingHornScreenMessage;
import com.eternal_creations.archies_dino_mod.platform.Services;
import com.eternal_creations.archies_dino_mod.util.ClientUtil;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ScorchingHornItem
extends Item {
    private static final int COOLDOWN_TICKS = 100;
    private static final int FAIL_COOLDOWN_TICKS = 40;

    public ScorchingHornItem() {
        super(new Item.Properties().m_41497_(Rarity.COMMON).m_41503_(500));
    }

    public void m_5929_(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
        super.m_5929_(level, livingEntity, stack, remainingUseDuration);
        if (remainingUseDuration == 10 && !level.f_46443_ && livingEntity instanceof ServerPlayer) {
            ServerPlayer player = (ServerPlayer)livingEntity;
            ArrayList<ADPetData> pets = new ArrayList<ADPetData>();
            for (ServerLevel serverLevel : player.f_8924_.m_129785_()) {
                for (Entity entity : serverLevel.m_8583_()) {
                    if (!(entity instanceof OwnableEntity)) continue;
                    OwnableEntity pet = (OwnableEntity)entity;
                    if (!player.m_20148_().equals(pet.m_21805_())) continue;
                    pets.add(new ADPetData(entity.m_20148_(), entity.m_7755_(), entity.m_20183_(), serverLevel.m_46472_().m_135782_()));
                }
            }
            Services.NETWORK.sendToClient(player, new ScorchingHornScreenMessage(pets));
            player.m_36335_().m_41524_((Item)this, 100);
        }
    }

    public InteractionResultHolder<ItemStack> m_7203_(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.m_21120_(hand);
        player.m_6672_(hand);
        level.m_5594_(null, player.m_20183_(), ADSoundEvents.SCORCHING_HORN.get(), SoundSource.PLAYERS, 1.0f, 1.0f);
        player.m_36246_(Stats.f_12982_.m_12902_((Object)this));
        player.m_36335_().m_41524_((Item)this, 40);
        return InteractionResultHolder.m_19096_((Object)itemstack);
    }

    public int m_8105_(ItemStack stack) {
        return 70;
    }

    public void m_7373_(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        super.m_7373_(stack, level, tooltipComponents, isAdvanced);
        ClientUtil.addMultilineTooltip(ADStrings.Description.SCORCHING_HORN, tooltipComponents, new Object[0]);
    }
}

