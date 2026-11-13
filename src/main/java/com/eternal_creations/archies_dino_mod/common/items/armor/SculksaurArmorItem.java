/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.astemir.astemirlib.re.common.function.PositionSupplier
 *  com.astemir.astemirlib.re.common.world.particle.ParticleEmitter
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.ChatFormatting
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.model.geom.ModelLayerLocation
 *  net.minecraft.core.Position
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.entity.monster.Enemy
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

import com.astemir.astemirlib.re.common.function.PositionSupplier;
import com.astemir.astemirlib.re.common.world.particle.ParticleEmitter;
import com.eternal_creations.archies_dino_mod.Constants;
import com.eternal_creations.archies_dino_mod.client.model.registry.ADModelLayers;
import com.eternal_creations.archies_dino_mod.common.ADStrings;
import com.eternal_creations.archies_dino_mod.common.ADTags;
import com.eternal_creations.archies_dino_mod.common.items.ADItems;
import com.eternal_creations.archies_dino_mod.common.items.util.ADArmorItem;
import com.eternal_creations.archies_dino_mod.common.particle.ADParticles;
import com.eternal_creations.archies_dino_mod.common.sound.ADSoundEvents;
import com.eternal_creations.archies_dino_mod.networking.serverbound.UseArmorAbilityMessage;
import com.eternal_creations.archies_dino_mod.platform.Services;
import com.eternal_creations.archies_dino_mod.server.ServerConfig;
import com.eternal_creations.archies_dino_mod.util.ClientUtil;
import java.util.List;
import java.util.Map;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.ChatFormatting;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.core.Position;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Enemy;
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
public class SculksaurArmorItem
extends ADArmorItem {
    private static final ResourceLocation TEXTURE = Constants.prefix("textures/armor/sculksaur_armor.png");
    private static final double NOISE_RANGE = 32.0;
    private static final int COOLDOWN = 600;
    private static final int ABILITY_USE_THRESHOLD = 60;
    private float clientDuration = 0.0f;
    private int warmup = 0;
    private int attemptCooldown = 0;
    public static final ADArmorItem.ArmorStats DEFAULT_STATS = new ADArmorItem.ArmorStats(new ADArmorItem.SlotStats(2, 350), new ADArmorItem.SlotStats(6, 530), new ADArmorItem.SlotStats(5, 470), new ADArmorItem.SlotStats(2, 400), ArmorMaterials.LEATHER.m_6646_(), 0.0f, 0.0f);

    public SculksaurArmorItem(ArmorItem.Type type, Item.Properties properties) {
        super(new ADArmorItem.ADArmorMaterial(ServerConfig.getArmorSettings()::sculksaur, ADSoundEvents.ARMOR_EQUIP_SCULKSAUR.get(), "sculksaur", Ingredient.m_43929_((ItemLike[])new ItemLike[]{(ItemLike)ADItems.SCULK_TOXIN.get()})), type, properties);
    }

    @Override
    public boolean hasAbility() {
        return true;
    }

    @Override
    public void tickWholeSet(Level level, LivingEntity living) {
        if (this.attemptCooldown > 0) {
            --this.attemptCooldown;
        }
        if (this.warmup > 0) {
            --this.warmup;
        }
        if (this.warmup <= 0 && this.clientDuration > 0.0f) {
            this.clientDuration = Math.max(0.0f, this.clientDuration - 1.5f);
            if (living instanceof Player) {
                Player player = (Player)living;
                SculksaurArmorItem.send(player, (this.clientDuration + Minecraft.m_91087_().m_91297_()) / 60.0f);
            }
        }
    }

    @Override
    public void activateClientSetAbility(Player player) {
        if (this.attemptCooldown > 0) {
            return;
        }
        Map<EquipmentSlot, ItemStack> armorSet = ADArmorItem.getArmorSet((LivingEntity)player);
        ItemCooldowns playerCooldowns = player.m_36335_();
        float partialTick = Minecraft.m_91087_().m_91297_();
        float biggestCooldown = 0.0f;
        for (ItemStack stack : armorSet.values()) {
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
        SculksaurArmorItem.send(player, (this.clientDuration + partialTick) / 60.0f);
        float f = this.clientDuration;
        this.clientDuration = f + 1.0f;
        if (f + partialTick >= 60.0f) {
            this.clientDuration = 0.0f;
            this.attemptCooldown = 20;
            Services.NETWORK.sendToServer(new UseArmorAbilityMessage(player.m_20318_(partialTick), player.m_20252_(partialTick)));
            Level level = player.m_9236_();
            Vec3 head = player.m_20299_(partialTick);
            Vec3 feet = player.m_20318_(partialTick);
            ADParticles.SCULKSAUR_EXPLOSION_2.get().spawnClient(level, PositionSupplier.of((Position)head), ParticleEmitter.DEFAULT_ROTATION, 1.0f);
            ADParticles.SCULKSAUR_EXPLOSION_3.get().spawnClient(level, PositionSupplier.of((Position)feet), ParticleEmitter.DEFAULT_ROTATION, 1.0f);
            ADParticles.SCULKSAUR_EXPLOSION_4.get().spawnClient(level, PositionSupplier.of((Position)feet), ParticleEmitter.DEFAULT_ROTATION, 1.0f);
            ADParticles.SCULKSAUR_EXPLOSION_5.get().spawnClient(level, PositionSupplier.of((Position)feet), ParticleEmitter.DEFAULT_ROTATION, 1.0f);
        } else {
            this.warmup = 10;
        }
    }

    @Override
    public boolean activateServerSetAbility(ServerPlayer player, Vec3 pos, Vec3 viewVector) {
        ServerLevel level = player.m_284548_();
        level.m_6263_(null, player.m_20185_(), player.m_20186_(), player.m_20189_(), ADSoundEvents.SCULKSAUR_ARMOR_ABILITY.get(), SoundSource.PLAYERS, 3.0f, 1.0f);
        Vec3 position = player.m_20318_(1.0f);
        level.m_45933_((Entity)player, player.m_20191_().m_82400_(32.0)).forEach(entity -> {
            Mob mob;
            if (entity instanceof Mob && ((mob = (Mob)entity) instanceof Enemy || mob.m_5448_() == player || mob.m_6095_().m_204039_(ADTags.EntityTypes.SCULK)) && player.m_6779_((LivingEntity)mob)) {
                Vec3 vec31 = mob.m_146892_().m_82546_(position);
                Vec3 vec32 = vec31.m_82541_();
                if (mob.m_6469_(level.m_269111_().m_269285_((Entity)player), 2.0f)) {
                    double d1 = 0.5 * (1.0 - mob.m_21133_(Attributes.f_22278_));
                    double d0 = 2.5 * (1.0 - mob.m_21133_(Attributes.f_22278_));
                    mob.m_5997_(vec32.m_7096_() * d0, vec32.m_7098_() * d1, vec32.m_7094_() * d0);
                    mob.m_7292_(new MobEffectInstance(MobEffects.f_19610_, 60, 0));
                    mob.m_7292_(new MobEffectInstance(MobEffects.f_19604_, 60, 0));
                    if (mob.m_6095_().m_204039_(ADTags.EntityTypes.SCULK)) {
                        mob.m_7292_(new MobEffectInstance(MobEffects.f_19597_, 60, 200));
                    }
                }
            }
        });
        return true;
    }

    public static void send(Player player, float progress) {
        player.m_5661_(SculksaurArmorItem.build(progress, 25), true);
    }

    public static Component build(float progress, int length) {
        float p = Math.max(0.0f, Math.min(1.0f, progress));
        int filled = Math.round(p * (float)length);
        MutableComponent bar = Component.m_237113_((String)"[").m_130940_(ChatFormatting.GRAY);
        bar.m_7220_((Component)Component.m_237113_((String)"|".repeat(filled)).m_130940_(ChatFormatting.AQUA));
        bar.m_7220_((Component)Component.m_237113_((String)"|".repeat(length - filled)).m_130940_(ChatFormatting.DARK_GRAY));
        bar.m_7220_((Component)Component.m_237113_((String)"] ").m_130940_(ChatFormatting.GRAY));
        bar.m_7220_((Component)Component.m_237113_((String)(Math.round(p * 100.0f) + "%")).m_130940_(ChatFormatting.WHITE));
        return bar;
    }

    public void m_7373_(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        tooltipComponents.add((Component)Component.m_237115_((String)ADStrings.Description.SET_BONUS).m_130940_(ChatFormatting.GOLD));
        ClientUtil.addMultilineTooltip(ADStrings.Description.SCULKSAUR_ARMOR_SET_BONUS, tooltipComponents, 30);
    }

    @Override
    public int getSetAbilityCooldownDuration(ServerPlayer player) {
        return 600;
    }

    @Override
    public ResourceLocation getArmorTexture() {
        return TEXTURE;
    }

    @Override
    public ModelLayerLocation getLayerLocation() {
        return this.f_265916_ == ArmorItem.Type.LEGGINGS ? ADModelLayers.SCULKSAUR_ARMOR_INNER : ADModelLayers.SCULKSAUR_ARMOR_OUTER;
    }
}

