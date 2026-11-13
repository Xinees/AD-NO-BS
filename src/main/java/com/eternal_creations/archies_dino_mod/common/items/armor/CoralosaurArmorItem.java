/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.ChatFormatting
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.client.model.geom.ModelLayerLocation
 *  net.minecraft.core.BlockPos
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.monster.Guardian
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ArmorItem$Type
 *  net.minecraft.world.item.ArmorMaterials
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemCooldowns
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.item.crafting.Ingredient
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.Nullable
 */
package com.eternal_creations.archies_dino_mod.common.items.armor;

import com.eternal_creations.archies_dino_mod.Constants;
import com.eternal_creations.archies_dino_mod.client.model.registry.ADModelLayers;
import com.eternal_creations.archies_dino_mod.common.ADStrings;
import com.eternal_creations.archies_dino_mod.common.entity.mob.coralosaur.CoralosaurEntity;
import com.eternal_creations.archies_dino_mod.common.items.ADItems;
import com.eternal_creations.archies_dino_mod.common.items.util.ADArmorItem;
import com.eternal_creations.archies_dino_mod.common.sound.ADSoundEvents;
import com.eternal_creations.archies_dino_mod.server.ServerConfig;
import com.eternal_creations.archies_dino_mod.util.ClientUtil;
import com.eternal_creations.archies_dino_mod.util.iGuardianSummoned;
import java.util.List;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.ChatFormatting;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Guardian;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CoralosaurArmorItem
extends ADArmorItem {
    private static final ResourceLocation TEXTURE = Constants.prefix("textures/armor/coralosaur_armor.png");
    private static final int COOLDOWN = 300;
    public static final ADArmorItem.ArmorStats DEFAULT_STATS = new ADArmorItem.ArmorStats(new ADArmorItem.SlotStats(2, 350), new ADArmorItem.SlotStats(5, 530), new ADArmorItem.SlotStats(2, 470), new ADArmorItem.SlotStats(1, 400), ArmorMaterials.LEATHER.m_6646_(), 0.0f, 0.1f);

    public CoralosaurArmorItem(ArmorItem.Type type, Item.Properties properties) {
        super(new ADArmorItem.ADArmorMaterial(ServerConfig.getArmorSettings()::coralosaur, ADSoundEvents.ARMOR_EQUIP_CORALOSAUR.get(), "coralosaur", Ingredient.m_43929_((ItemLike[])new ItemLike[]{(ItemLike)ADItems.CORAL_RIB.get()})), type, properties);
    }

    @Override
    public boolean hasAbility() {
        return true;
    }

    @Override
    public void activateClientSetAbility(Player player) {
        if (!player.m_20070_()) {
            player.m_5661_((Component)Component.m_237115_((String)ADStrings.Messages.CORALOSAUR_ARMOR_NOT_IN_WATER), true);
            return;
        }
        ItemCooldowns playerCooldowns = player.m_36335_();
        float biggestCooldown = 0.0f;
        for (ItemStack stack : ADArmorItem.getArmorSet((LivingEntity)player).values()) {
            if (!playerCooldowns.m_41519_(stack.m_41720_())) continue;
            float per = playerCooldowns.m_41521_(stack.m_41720_(), 1.0f);
            if (!(per > biggestCooldown)) break;
            biggestCooldown = per;
            break;
        }
        if (biggestCooldown > 0.0f) {
            int percent = Math.round(biggestCooldown * 100.0f);
            player.m_5661_((Component)Component.m_237110_((String)ADStrings.Messages.ARMOR_ON_COOLDOWN, (Object[])new Object[]{percent}), true);
            return;
        }
        super.activateClientSetAbility(player);
    }

    @Override
    public boolean activateServerSetAbility(ServerPlayer player, Vec3 pos, Vec3 viewVector) {
        ServerLevel level = player.m_284548_();
        for (int i = 0; i < 3 + player.m_217043_().m_188503_(3); ++i) {
            Guardian guardian;
            BlockPos spawnPos = CoralosaurEntity.findValidSpawnPosition((Level)level, player.m_20183_(), 8);
            if (spawnPos == null || !((guardian = (Guardian)EntityType.f_20455_.m_20615_((Level)level)) instanceof iGuardianSummoned)) continue;
            iGuardianSummoned summoned = (iGuardianSummoned)guardian;
            summoned.archiesDinoMod$setOwner(player.m_20148_());
            guardian.m_7678_((double)spawnPos.m_123341_() + 0.5, (double)spawnPos.m_123342_(), (double)spawnPos.m_123343_() + 0.5, 0.0f, 0.0f);
            level.m_7967_((Entity)guardian);
        }
        return true;
    }

    @Override
    public int getSetAbilityCooldownDuration(ServerPlayer player) {
        return 300;
    }

    @Override
    public void tickWholeSet(Level level, LivingEntity living) {
        living.m_7292_(new MobEffectInstance(MobEffects.f_19608_, 20, 0, true, false, true));
        living.m_7292_(new MobEffectInstance(MobEffects.f_19592_, 20, 0, true, false, true));
    }

    public void m_7373_(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        tooltipComponents.add((Component)Component.m_237115_((String)ADStrings.Description.SET_BONUS).m_130940_(ChatFormatting.GOLD));
        ClientUtil.addMultilineTooltip(ADStrings.Description.CORALOSAUR_ARMOR_SET_BONUS, tooltipComponents, 15);
    }

    @Override
    public ResourceLocation getArmorTexture() {
        return TEXTURE;
    }

    @Override
    public ModelLayerLocation getLayerLocation() {
        return this.f_265916_ == ArmorItem.Type.LEGGINGS ? ADModelLayers.CORALOSAUR_ARMOR_INNER : ADModelLayers.CORALOSAUR_ARMOR_OUTER;
    }
}

