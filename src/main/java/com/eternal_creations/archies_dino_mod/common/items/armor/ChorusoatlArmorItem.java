/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.ChatFormatting
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.client.model.geom.ModelLayerLocation
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.item.ArmorItem$Type
 *  net.minecraft.world.item.ArmorMaterials
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.item.crafting.Ingredient
 *  net.minecraft.world.level.ClipContext
 *  net.minecraft.world.level.ClipContext$Block
 *  net.minecraft.world.level.ClipContext$Fluid
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.gameevent.GameEvent
 *  net.minecraft.world.level.gameevent.GameEvent$Context
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.Nullable
 */
package com.eternal_creations.archies_dino_mod.common.items.armor;

import com.eternal_creations.archies_dino_mod.Constants;
import com.eternal_creations.archies_dino_mod.client.model.registry.ADModelLayers;
import com.eternal_creations.archies_dino_mod.common.ADStrings;
import com.eternal_creations.archies_dino_mod.common.items.ADItems;
import com.eternal_creations.archies_dino_mod.common.items.util.ADArmorItem;
import com.eternal_creations.archies_dino_mod.common.sound.ADSoundEvents;
import com.eternal_creations.archies_dino_mod.server.ServerConfig;
import com.eternal_creations.archies_dino_mod.util.ClientUtil;
import java.util.List;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.ChatFormatting;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ChorusoatlArmorItem
extends ADArmorItem {
    private static final ResourceLocation TEXTURE = Constants.prefix("textures/armor/chorusoatl_armor.png");
    private static final double TELEPORT_RANGE = 45.0;
    private static final int COOLDOWN = 300;
    public static final ADArmorItem.ArmorStats DEFAULT_STATS = new ADArmorItem.ArmorStats(new ADArmorItem.SlotStats(2, 350), new ADArmorItem.SlotStats(6, 530), new ADArmorItem.SlotStats(3, 470), new ADArmorItem.SlotStats(2, 400), ArmorMaterials.CHAIN.m_6646_(), 0.0f, 0.0f);

    public ChorusoatlArmorItem(ArmorItem.Type type, Item.Properties properties) {
        super(new ADArmorItem.ADArmorMaterial(ServerConfig.getArmorSettings()::chorusoatl, ADSoundEvents.ARMOR_EQUIP_CHORUSOATL.get(), "chorusoatl", Ingredient.m_43929_((ItemLike[])new ItemLike[]{(ItemLike)ADItems.CHORUS_FOSSIL.get()})), type, properties);
    }

    @Override
    public void tickWholeSet(Level level, LivingEntity living) {
        living.m_7292_(new MobEffectInstance(MobEffects.f_19591_, 20, 0, true, false, true));
    }

    @Override
    public boolean hasAbility() {
        return true;
    }

    @Override
    public boolean activateServerSetAbility(ServerPlayer player, Vec3 pos, Vec3 viewVector) {
        ServerLevel level = player.m_284548_();
        Vec3 eyePos = pos.m_82520_(0.0, (double)player.m_20192_(), 0.0);
        level.m_8767_((ParticleOptions)ParticleTypes.f_123760_, eyePos.m_7096_(), pos.m_7098_() + (double)player.m_20206_() * 0.5, eyePos.m_7094_(), 128, (double)player.m_20205_(), (double)player.m_20206_() * 0.5, (double)player.m_20205_(), (double)-0.2f);
        BlockHitResult result = level.m_45547_(new ClipContext(eyePos, eyePos.m_82549_(viewVector.m_82490_(45.0)), ClipContext.Block.COLLIDER, ClipContext.Fluid.WATER, (Entity)player));
        Vec3 hit = result.m_82450_();
        Vec3 backTrack = hit.m_82546_(eyePos).m_82541_().m_82490_(0.5);
        hit = hit.m_82546_(backTrack);
        BlockHitResult check = level.m_45547_(new ClipContext(hit, hit.m_82492_(0.0, (double)player.m_20192_(), 0.0), ClipContext.Block.COLLIDER, ClipContext.Fluid.WATER, (Entity)player));
        hit = check.m_82450_();
        level.m_214171_(GameEvent.f_238175_, hit, GameEvent.Context.m_223717_((Entity)player));
        player.m_20324_(hit.f_82479_, hit.f_82480_, hit.f_82481_);
        level.m_6263_(null, player.f_19854_, player.f_19855_, player.f_19856_, ADSoundEvents.CHORUSOATL_ARMOR_ABILITY.get(), player.m_5720_(), 1.0f, 1.0f);
        player.m_5496_(ADSoundEvents.CHORUSOATL_ARMOR_ABILITY.get(), 1.0f, 1.0f);
        level.m_8767_((ParticleOptions)ParticleTypes.f_123760_, player.m_20185_(), player.m_20186_() + (double)player.m_20206_() * 0.5, player.m_20189_(), 128, (double)player.m_20205_(), (double)player.m_20206_() * 0.5, (double)player.m_20205_(), (double)0.2f);
        return true;
    }

    public void m_7373_(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        tooltipComponents.add((Component)Component.m_237115_((String)ADStrings.Description.SET_BONUS).m_130940_(ChatFormatting.GOLD));
        ClientUtil.addMultilineTooltip(ADStrings.Description.CHORUSOATL_ARMOR_SET_BONUS, tooltipComponents, 15);
    }

    @Override
    public int getSetAbilityCooldownDuration(ServerPlayer player) {
        return 300;
    }

    @Override
    public ResourceLocation getArmorTexture() {
        return TEXTURE;
    }

    @Override
    public ModelLayerLocation getLayerLocation() {
        return this.f_265916_ == ArmorItem.Type.LEGGINGS ? ADModelLayers.CHORUSOATL_ARMOR_INNER : ADModelLayers.CHORUSOATL_ARMOR_OUTER;
    }
}

