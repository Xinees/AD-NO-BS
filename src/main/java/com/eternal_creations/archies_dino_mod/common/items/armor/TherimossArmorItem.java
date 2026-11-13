/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.ChatFormatting
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.client.model.geom.ModelLayerLocation
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.item.ArmorItem$Type
 *  net.minecraft.world.item.ArmorMaterials
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
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
import com.eternal_creations.archies_dino_mod.common.effect.ADMobEffects;
import com.eternal_creations.archies_dino_mod.common.items.util.ADArmorItem;
import com.eternal_creations.archies_dino_mod.common.sound.ADSoundEvents;
import com.eternal_creations.archies_dino_mod.server.ServerConfig;
import com.eternal_creations.archies_dino_mod.util.ClientUtil;
import java.util.List;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.ChatFormatting;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class TherimossArmorItem
extends ADArmorItem {
    private static final ResourceLocation TEXTURE = Constants.prefix("textures/armor/therimoss_armor.png");
    private static final int COOLDOWN = 200;
    public static final ADArmorItem.ArmorStats DEFAULT_STATS = new ADArmorItem.ArmorStats(new ADArmorItem.SlotStats(2, 350), new ADArmorItem.SlotStats(6, 530), new ADArmorItem.SlotStats(2, 470), new ADArmorItem.SlotStats(1, 400), ArmorMaterials.LEATHER.m_6646_(), 0.0f, 0.1f);
    public static final ADArmorItem.ADArmorMaterial THERIMOSS = new ADArmorItem.ADArmorMaterial(ServerConfig.getArmorSettings()::therimoss, ADSoundEvents.ARMOR_EQUIP_THERIMOSS.get(), "therimoss", Ingredient.m_43929_((ItemLike[])new ItemLike[]{Items.f_276698_}));

    public TherimossArmorItem(ArmorItem.Type type, Item.Properties properties) {
        super(THERIMOSS, type, properties);
    }

    @Override
    public boolean hasAbility() {
        return true;
    }

    @Override
    public boolean activateServerSetAbility(ServerPlayer player, Vec3 pos, Vec3 viewVector) {
        player.m_7292_(new MobEffectInstance(MobEffects.f_19596_, 300, 0, true, true, true));
        return true;
    }

    @Override
    public void tickWholeSet(Level level, LivingEntity living) {
        living.m_7292_(new MobEffectInstance((MobEffect)ADMobEffects.HUNGER_REDUCTION.get(), 20, 0, true, false, true));
    }

    public void m_7373_(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        tooltipComponents.add((Component)Component.m_237115_((String)ADStrings.Description.SET_BONUS).m_130940_(ChatFormatting.GOLD));
        ClientUtil.addMultilineTooltip(ADStrings.Description.THERIMOSS_ARMOR_SET_BONUS, tooltipComponents, 10);
    }

    @Override
    public int getSetAbilityCooldownDuration(ServerPlayer player) {
        return 200;
    }

    @Override
    public ResourceLocation getArmorTexture() {
        return TEXTURE;
    }

    @Override
    public ModelLayerLocation getLayerLocation() {
        return this.f_265916_ == ArmorItem.Type.LEGGINGS ? ADModelLayers.THERIMOSS_ARMOR_INNER : ADModelLayers.THERIMOSS_ARMOR_OUTER;
    }
}

