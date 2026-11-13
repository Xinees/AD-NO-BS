/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  javax.annotation.Nullable
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.entity.PathfinderMob
 *  net.minecraft.world.entity.ai.attributes.AttributeSupplier
 *  net.minecraft.world.entity.ai.attributes.AttributeSupplier$Builder
 *  net.minecraft.world.entity.ai.attributes.Attributes
 */
package com.eternal_creations.archies_dino_mod.server;

import com.eternal_creations.archies_dino_mod.common.entity.base.AbstractComplexMob;
import com.eternal_creations.archies_dino_mod.common.entity.mob.archie.ArchieEntity;
import com.eternal_creations.archies_dino_mod.common.entity.mob.chorusoatl.ChorusoatlEntity;
import com.eternal_creations.archies_dino_mod.common.entity.mob.coralosaur.CoralosaurEntity;
import com.eternal_creations.archies_dino_mod.common.entity.mob.lushsaur.LushsaurEntity;
import com.eternal_creations.archies_dino_mod.common.entity.mob.obsidiceratops.ObsidiceratopsEntity;
import com.eternal_creations.archies_dino_mod.common.entity.mob.sculkraptor.SculkraptorEntity;
import com.eternal_creations.archies_dino_mod.common.entity.mob.sculksaur.SculksaurEntity;
import com.eternal_creations.archies_dino_mod.common.entity.mob.therimoss.TherimossEntity;
import com.eternal_creations.archies_dino_mod.common.items.armor.ChorusoatlArmorItem;
import com.eternal_creations.archies_dino_mod.common.items.armor.CoralosaurArmorItem;
import com.eternal_creations.archies_dino_mod.common.items.armor.LushsaurArmorItem;
import com.eternal_creations.archies_dino_mod.common.items.armor.ObsidiceratopsArmorItem;
import com.eternal_creations.archies_dino_mod.common.items.armor.SculksaurArmorItem;
import com.eternal_creations.archies_dino_mod.common.items.armor.TherimossArmorItem;
import com.eternal_creations.archies_dino_mod.common.items.util.ADArmorItem;
import com.eternal_creations.archies_dino_mod.common.items.util.ADBowItem;
import com.eternal_creations.archies_dino_mod.common.items.util.ADWeaponItem;
import com.eternal_creations.archies_dino_mod.common.items.weapon.ChorusScytheItem;
import com.eternal_creations.archies_dino_mod.common.items.weapon.LushsaurMaceItem;
import com.eternal_creations.archies_dino_mod.common.items.weapon.RibBowItem;
import com.eternal_creations.archies_dino_mod.common.items.weapon.SculkBowItem;
import com.eternal_creations.archies_dino_mod.common.items.weapon.TherimossClawsItem;
import com.eternal_creations.archies_dino_mod.util.ADConfig;
import com.eternal_creations.archies_dino_mod.util.handlers.SculksaurHandler;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ServerConfig
extends ADConfig {
    @Nullable
    private static ServerConfig INSTANCE = null;
    private final ADConfig.Setting<EntitySettings> entitySettings = new ADConfig.Setting<EntitySettings>("entity_settings", new EntitySettings(ArchieEntity.DEFAULT_STATS, LushsaurEntity.DEFAULT_STATS, ObsidiceratopsEntity.DEFAULT_STATS, TherimossEntity.DEFAULT_STATS, ChorusoatlEntity.DEFAULT_STATS, SculkraptorEntity.DEFAULT_STATS, SculksaurEntity.DEFAULT_STATS, CoralosaurEntity.DEFAULT_STATS), EntitySettings::toTag, EntitySettings::fromTag);
    private final ADConfig.Setting<WeaponSettings> weaponSettings = new ADConfig.Setting<WeaponSettings>("weapon_settings", new WeaponSettings(ChorusScytheItem.DEFAULT_STATS, LushsaurMaceItem.DEFAULT_STATS, TherimossClawsItem.COPPER_STATS, TherimossClawsItem.IRON_STATS, TherimossClawsItem.GOLD_STATS, TherimossClawsItem.DIAMOND_STATS, TherimossClawsItem.NETHERITE_STATS, RibBowItem.DEFAULT_STATS, SculkBowItem.DEFAULT_STATS), WeaponSettings::toTag, WeaponSettings::fromTag);
    private final ADConfig.Setting<ArmorSettings> armorSettings = new ADConfig.Setting<ArmorSettings>("armor_settings", new ArmorSettings(LushsaurArmorItem.DEFAULT_STATS, ObsidiceratopsArmorItem.DEFAULT_STATS, TherimossArmorItem.DEFAULT_STATS, ChorusoatlArmorItem.DEFAULT_STATS, SculksaurArmorItem.DEFAULT_STATS, CoralosaurArmorItem.DEFAULT_STATS), ArmorSettings::toTag, ArmorSettings::fromTag);

    public ServerConfig() {
        super("server_config");
    }

    public static ServerConfig getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ServerConfig();
        }
        if (!ServerConfig.INSTANCE.loaded) {
            INSTANCE.loadOrCreate();
        }
        return INSTANCE;
    }

    public static void setInstance(ServerConfig config) {
        INSTANCE = config;
    }

    public static ServerConfig createFrom(CompoundTag tag) {
        ServerConfig config = new ServerConfig();
        config.fromTag(tag);
        config.loaded = true;
        return config;
    }

    @Override
    public CompoundTag toTag() {
        CompoundTag tag = new CompoundTag();
        this.entitySettings.write(tag);
        this.weaponSettings.write(tag);
        this.armorSettings.write(tag);
        return tag;
    }

    @Override
    public void fromTag(CompoundTag tag) {
        this.entitySettings.read(tag);
        this.weaponSettings.read(tag);
        this.armorSettings.read(tag);
    }

    public static EntitySettings getEntitySettings() {
        return ServerConfig.getInstance().entitySettings.getValue();
    }

    public static WeaponSettings getWeaponSettings() {
        return ServerConfig.getInstance().weaponSettings.getValue();
    }

    public static ArmorSettings getArmorSettings() {
        return ServerConfig.getInstance().armorSettings.getValue();
    }

    public record EntitySettings(ArchieEntity.ArchieSettings archie, AbstractComplexMob.TameableDinoSettings lushsaur, AbstractComplexMob.TameableDinoSettings obsidiceratops, AbstractComplexMob.TameableDinoSettings therimoss, AbstractComplexMob.TameableDinoSettings chorusoatl, AbstractComplexMob.TameableDinoSettings sculkraptor, SculksaurHandler.SculksaurSettings sculksaur, AbstractComplexMob.TameableDinoSettings coralosaur) {
        public CompoundTag toTag() {
            CompoundTag tag = new CompoundTag();
            tag.m_128365_("archie", ADConfig.encode(ArchieEntity.ArchieSettings.CODEC, this.archie));
            tag.m_128365_("lushsaur", ADConfig.encode(AbstractComplexMob.TameableDinoSettings.CODEC, this.lushsaur));
            tag.m_128365_("obsidiceratops", ADConfig.encode(AbstractComplexMob.TameableDinoSettings.CODEC, this.obsidiceratops));
            tag.m_128365_("therimoss", ADConfig.encode(AbstractComplexMob.TameableDinoSettings.CODEC, this.therimoss));
            tag.m_128365_("chorusoatl", ADConfig.encode(AbstractComplexMob.TameableDinoSettings.CODEC, this.chorusoatl));
            tag.m_128365_("sculkraptor", ADConfig.encode(AbstractComplexMob.TameableDinoSettings.CODEC, this.sculkraptor));
            tag.m_128365_("sculksaur", ADConfig.encode(SculksaurHandler.SculksaurSettings.CODEC, this.sculksaur));
            tag.m_128365_("coralosaur", ADConfig.encode(AbstractComplexMob.TameableDinoSettings.CODEC, this.coralosaur));
            return tag;
        }

        public static EntitySettings fromTag(CompoundTag tag, EntitySettings defaults) {
            return new EntitySettings(ADConfig.decode(ArchieEntity.ArchieSettings.CODEC, tag.m_128423_("archie"), defaults.archie()), ADConfig.decode(AbstractComplexMob.TameableDinoSettings.CODEC, tag.m_128423_("lushsaur"), defaults.lushsaur()), ADConfig.decode(AbstractComplexMob.TameableDinoSettings.CODEC, tag.m_128423_("obsidiceratops"), defaults.obsidiceratops()), ADConfig.decode(AbstractComplexMob.TameableDinoSettings.CODEC, tag.m_128423_("therimoss"), defaults.therimoss()), ADConfig.decode(AbstractComplexMob.TameableDinoSettings.CODEC, tag.m_128423_("chorusoatl"), defaults.chorusoatl()), ADConfig.decode(AbstractComplexMob.TameableDinoSettings.CODEC, tag.m_128423_("sculkraptor"), defaults.sculkraptor()), ADConfig.decode(SculksaurHandler.SculksaurSettings.CODEC, tag.m_128423_("sculksaur"), defaults.sculksaur()), ADConfig.decode(AbstractComplexMob.TameableDinoSettings.CODEC, tag.m_128423_("coralosaur"), defaults.coralosaur()));
        }
    }

    public record WeaponSettings(ADWeaponItem.WeaponStats chorusScythe, ADWeaponItem.WeaponStats lushsaurMace, ADWeaponItem.WeaponStats copperClaws, ADWeaponItem.WeaponStats ironClaws, ADWeaponItem.WeaponStats goldClaws, ADWeaponItem.WeaponStats diamondClaws, ADWeaponItem.WeaponStats netheriteClaws, ADBowItem.BowStats ribBow, ADBowItem.BowStats sculkBow) {
        public CompoundTag toTag() {
            CompoundTag tag = new CompoundTag();
            tag.m_128365_("chorus_scythe", ADConfig.encode(ADWeaponItem.WeaponStats.CODEC, this.chorusScythe));
            tag.m_128365_("lushsaur_mace", ADConfig.encode(ADWeaponItem.WeaponStats.CODEC, this.lushsaurMace));
            tag.m_128365_("copper_claws", ADConfig.encode(ADWeaponItem.WeaponStats.CODEC, this.copperClaws));
            tag.m_128365_("iron_claws", ADConfig.encode(ADWeaponItem.WeaponStats.CODEC, this.ironClaws));
            tag.m_128365_("gold_claws", ADConfig.encode(ADWeaponItem.WeaponStats.CODEC, this.goldClaws));
            tag.m_128365_("diamond_claws", ADConfig.encode(ADWeaponItem.WeaponStats.CODEC, this.diamondClaws));
            tag.m_128365_("netherite_claws", ADConfig.encode(ADWeaponItem.WeaponStats.CODEC, this.netheriteClaws));
            tag.m_128365_("rib_bow", ADConfig.encode(ADBowItem.BowStats.CODEC, this.ribBow));
            tag.m_128365_("sculk_bow", ADConfig.encode(ADBowItem.BowStats.CODEC, this.sculkBow));
            return tag;
        }

        public static WeaponSettings fromTag(CompoundTag tag, WeaponSettings defaults) {
            return new WeaponSettings(ADConfig.decode(ADWeaponItem.WeaponStats.CODEC, tag.m_128423_("chorus_scythe"), defaults.chorusScythe()), ADConfig.decode(ADWeaponItem.WeaponStats.CODEC, tag.m_128423_("lushsaur_mace"), defaults.lushsaurMace()), ADConfig.decode(ADWeaponItem.WeaponStats.CODEC, tag.m_128423_("copper_claws"), defaults.copperClaws()), ADConfig.decode(ADWeaponItem.WeaponStats.CODEC, tag.m_128423_("iron_claws"), defaults.ironClaws()), ADConfig.decode(ADWeaponItem.WeaponStats.CODEC, tag.m_128423_("gold_claws"), defaults.goldClaws()), ADConfig.decode(ADWeaponItem.WeaponStats.CODEC, tag.m_128423_("diamond_claws"), defaults.diamondClaws()), ADConfig.decode(ADWeaponItem.WeaponStats.CODEC, tag.m_128423_("netherite_claws"), defaults.netheriteClaws()), ADConfig.decode(ADBowItem.BowStats.CODEC, tag.m_128423_("rib_bow"), defaults.ribBow()), ADConfig.decode(ADBowItem.BowStats.CODEC, tag.m_128423_("sculk_bow"), defaults.sculkBow()));
        }
    }

    public record ArmorSettings(ADArmorItem.ArmorStats lushsaur, ADArmorItem.ArmorStats obsidiceratops, ADArmorItem.ArmorStats therimoss, ADArmorItem.ArmorStats chorusoatl, ADArmorItem.ArmorStats sculksaur, ADArmorItem.ArmorStats coralosaur) {
        public CompoundTag toTag() {
            CompoundTag tag = new CompoundTag();
            tag.m_128365_("lushsaur_armor", ADConfig.encode(ADArmorItem.ArmorStats.CODEC, this.lushsaur));
            tag.m_128365_("obsidiceratops_armor", ADConfig.encode(ADArmorItem.ArmorStats.CODEC, this.obsidiceratops));
            tag.m_128365_("therimoss_armor", ADConfig.encode(ADArmorItem.ArmorStats.CODEC, this.therimoss));
            tag.m_128365_("chorusoatl_armor", ADConfig.encode(ADArmorItem.ArmorStats.CODEC, this.chorusoatl));
            tag.m_128365_("sculksaur_armor", ADConfig.encode(ADArmorItem.ArmorStats.CODEC, this.sculksaur));
            tag.m_128365_("coralosaur_armor", ADConfig.encode(ADArmorItem.ArmorStats.CODEC, this.coralosaur));
            return tag;
        }

        public static ArmorSettings fromTag(CompoundTag tag, ArmorSettings defaults) {
            return new ArmorSettings(ADConfig.decode(ADArmorItem.ArmorStats.CODEC, tag.m_128423_("lushsaur_armor"), defaults.lushsaur()), ADConfig.decode(ADArmorItem.ArmorStats.CODEC, tag.m_128423_("obsidiceratops_armor"), defaults.obsidiceratops()), ADConfig.decode(ADArmorItem.ArmorStats.CODEC, tag.m_128423_("therimoss_armor"), defaults.therimoss()), ADConfig.decode(ADArmorItem.ArmorStats.CODEC, tag.m_128423_("chorusoatl_armor"), defaults.chorusoatl()), ADConfig.decode(ADArmorItem.ArmorStats.CODEC, tag.m_128423_("sculksaur_armor"), defaults.sculksaur()), ADConfig.decode(ADArmorItem.ArmorStats.CODEC, tag.m_128423_("coralosaur_armor"), defaults.coralosaur()));
        }
    }

    public record EntityStats(double movementSpeed, Optional<Double> flyingSpeed, double maxHealth, double knockbackResistance, double armor, double followRange, double attackDamage) {
        public static final Codec<EntityStats> CODEC = RecordCodecBuilder.create(instance -> instance.group((App)Codec.DOUBLE.fieldOf("movement_speed").forGetter(EntityStats::movementSpeed), (App)Codec.DOUBLE.optionalFieldOf("flying_speed").forGetter(EntityStats::flyingSpeed), (App)Codec.DOUBLE.fieldOf("max_health").forGetter(EntityStats::maxHealth), (App)Codec.DOUBLE.fieldOf("knockback_resistance").forGetter(EntityStats::knockbackResistance), (App)Codec.DOUBLE.fieldOf("armor").forGetter(EntityStats::armor), (App)Codec.DOUBLE.fieldOf("follow_range").forGetter(EntityStats::followRange), (App)Codec.DOUBLE.fieldOf("attack_damage").forGetter(EntityStats::attackDamage)).apply((Applicative)instance, EntityStats::new));

        public EntityStats(double movementSpeed, double maxHealth, double knockbackResistance, double armor, double followRange, double attackDamage) {
            this(movementSpeed, Optional.empty(), maxHealth, knockbackResistance, armor, followRange, attackDamage);
        }

        public EntityStats(double movementSpeed, double flyingSpeed, double maxHealth, double knockbackResistance, double armor, double followRange, double attackDamage) {
            this(movementSpeed, Optional.of(flyingSpeed), maxHealth, knockbackResistance, armor, followRange, attackDamage);
        }

        public AttributeSupplier build() {
            AttributeSupplier.Builder builder = PathfinderMob.m_21552_().m_22268_(Attributes.f_22279_, this.movementSpeed()).m_22268_(Attributes.f_22276_, this.maxHealth()).m_22268_(Attributes.f_22278_, this.knockbackResistance()).m_22268_(Attributes.f_22277_, this.followRange()).m_22268_(Attributes.f_22281_, this.attackDamage());
            this.flyingSpeed().ifPresent(flySpeed -> builder.m_22268_(Attributes.f_22280_, flySpeed.doubleValue()));
            return builder.m_22265_();
        }
    }
}

