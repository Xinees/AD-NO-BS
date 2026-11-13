/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Ascii
 *  javax.annotation.Nullable
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.advancements.Advancement
 *  net.minecraft.advancements.Advancement$Builder
 *  net.minecraft.advancements.AdvancementRewards$Builder
 *  net.minecraft.advancements.CriterionTriggerInstance
 *  net.minecraft.advancements.DisplayInfo
 *  net.minecraft.advancements.FrameType
 *  net.minecraft.advancements.RequirementsStrategy
 *  net.minecraft.advancements.critereon.EntityPredicate
 *  net.minecraft.advancements.critereon.EntityPredicate$Builder
 *  net.minecraft.advancements.critereon.KilledTrigger$TriggerInstance
 *  net.minecraft.advancements.critereon.RecipeCraftedTrigger$TriggerInstance
 *  net.minecraft.advancements.critereon.TameAnimalTrigger$TriggerInstance
 *  net.minecraft.core.HolderLookup$Provider
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.level.ItemLike
 *  net.minecraftforge.common.data.ExistingFileHelper
 *  net.minecraftforge.common.data.ForgeAdvancementProvider$AdvancementGenerator
 */
package com.eternal_creations.archies_dino_mod.data.gen;

import com.eternal_creations.archies_dino_mod.Constants;
import com.eternal_creations.archies_dino_mod.common.entity.ADEntityTypes;
import com.eternal_creations.archies_dino_mod.common.items.ADItems;
import com.eternal_creations.archies_dino_mod.common.trigger.SpottedEntityTrigger;
import com.eternal_creations.archies_dino_mod.data.util.ArchiesLangProvider;
import com.eternal_creations.archies_dino_mod.util.ResourceSupplier;
import com.google.common.base.Ascii;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.KilledTrigger;
import net.minecraft.advancements.critereon.RecipeCraftedTrigger;
import net.minecraft.advancements.critereon.TameAnimalTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ArchiesAdvancementGen
implements ForgeAdvancementProvider.AdvancementGenerator {
    public void generate(HolderLookup.Provider registries, Consumer<Advancement> consumer, ExistingFileHelper existingFileHelper) {
        Advancement root = this.create("Archie\u2019s Dinosaurs", "Meet Archie", builder -> builder.m_138371_((ItemLike)ADItems.GUIDEBOOK.get(), (Component)this.makeComponent("root", "title"), (Component)this.makeComponent("root", "desc"), Constants.prefix("textures/gui/back.png"), FrameType.TASK, true, false, false).m_138386_("root", (CriterionTriggerInstance)SpottedEntityTrigger.TriggerInstance.spottedEntity(EntityPredicate.Builder.m_36633_().m_36636_(ADEntityTypes.ARCHIE.get()))).m_138389_(consumer, "ec_ad:root"));
        this.tree(consumer, root, ADEntityTypes.LUSHSAUR, "Locate a Lushsaur in the Lush Caves biome.", (ItemLike)ADItems.LUSHSAUR_SPAWN_EGG.get(), (ItemLike)ADItems.LUSH_CORE.get(), (ItemLike)ADItems.LUSHSAUR_EGG.get(), (ItemLike)ADItems.LUSHSAUR_HELMET.get(), ArchiesAdvancementGen.craftArmorCriteria("lushsaur"), ADItems.LUSH_TOTEM.id());
        this.tree(consumer, root, ADEntityTypes.CHORUSOATL, "Locate a Chorusoatl in The End.", (ItemLike)ADItems.CHORUSOATL_SPAWN_EGG.get(), (ItemLike)ADItems.CHORUS_FOSSIL.get(), (ItemLike)ADItems.CHORUSOATL_EGG.get(), (ItemLike)ADItems.CHORUSOATL_HELMET.get(), ArchiesAdvancementGen.craftArmorCriteria("chorusoatl"), ADItems.SOARING_TOTEM.id());
        this.tree(consumer, root, ADEntityTypes.OBSIDICERATOPS, "Locate an Obsidiceratops in a Crimson Forest biome.", (ItemLike)ADItems.OBSIDICERATOPS_SPAWN_EGG.get(), (ItemLike)ADItems.OBSIDIAN_HORN.get(), (ItemLike)ADItems.OBSIDICERATOPS_EGG.get(), (ItemLike)ADItems.OBSIDICERATOPS_HELMET.get(), ArchiesAdvancementGen.craftArmorCriteria("obsidiceratops"), ADItems.BURNING_TOTEM.id());
        this.tree(consumer, root, ADEntityTypes.SCULKRAPTOR, "Locate a Sculkraptor in the Deep Dark biome.", (ItemLike)ADItems.SCULKRAPTOR_SPAWN_EGG.get(), (ItemLike)ADItems.RAPTOR_TALON.get(), (ItemLike)ADItems.SCULKRAPTOR_EGG.get(), (ItemLike)ADItems.SCULK_BOW.get(), builder -> builder.m_138386_("craft_bow", (CriterionTriggerInstance)RecipeCraftedTrigger.TriggerInstance.m_280097_((ResourceLocation)Constants.prefix("sculk_bow"))), ADItems.ECHO_TOTEM.id());
        this.tree(consumer, root, ADEntityTypes.SCULKSAUR, "Locate a Sculksaur in an Ancient City.", (ItemLike)ADItems.SCULKSAUR_SPAWN_EGG.get(), (ItemLike)ADItems.SCULK_TOXIN.get(), null, (ItemLike)ADItems.SCULKSAUR_HELMET.get(), ArchiesAdvancementGen.craftArmorCriteria("sculksaur"), ADItems.FEARFUL_TOTEM.id());
        this.tree(consumer, root, ADEntityTypes.CORALOSAUR, "Locate a Coralosaur in the Warm Ocean biome.", (ItemLike)ADItems.CORALOSAUR_SPAWN_EGG.get(), (ItemLike)ADItems.CORAL_RIB.get(), (ItemLike)ADItems.CORAL_STEW.get(), (ItemLike)ADItems.CORALOSAUR_HELMET.get(), ArchiesAdvancementGen.craftArmorCriteria("coralosaur"), ADItems.TIDAL_TOTEM.id());
        this.tree(consumer, root, ADEntityTypes.THERIMOSS, "Locate a Therimoss in a Jungle biome.", (ItemLike)ADItems.THERIMOSS_SPAWN_EGG.get(), (ItemLike)ADItems.MOSSY_CLAW.get(), (ItemLike)ADItems.THERIMOSS_EGG.get(), (ItemLike)ADItems.THERIMOSS_HELMET.get(), ArchiesAdvancementGen.craftArmorCriteria("therimoss"), ADItems.TROPIC_TOTEM.id());
    }

    private <T extends LivingEntity> void tree(Consumer<Advancement> consumer, Advancement root, ResourceSupplier<EntityType<T>> dino, String foundIn, ItemLike spotItem, ItemLike killItem, @Nullable ItemLike tameItem, ItemLike masterItem, Consumer<Advancement.Builder> modifier, ResourceLocation totem) {
        Advancement kill;
        String lowerCase = dino.id().m_135815_();
        String titleCase = ArchiesAdvancementGen.toTitleCase(lowerCase);
        String article = ArchiesAdvancementGen.articleFor(titleCase);
        Advancement spot = this.create("Spot " + article + " " + titleCase, foundIn, builder -> builder.m_138398_(root).m_138371_(spotItem, (Component)this.makeComponent("spot_" + lowerCase, "title"), (Component)this.makeComponent("spot_" + lowerCase, "desc"), null, FrameType.TASK, true, true, false).m_138386_("spot", (CriterionTriggerInstance)SpottedEntityTrigger.TriggerInstance.spottedEntity(EntityPredicate.Builder.m_36633_().m_36636_((EntityType)dino.get()))).m_138354_(AdvancementRewards.Builder.m_10005_((int)100)).m_138389_(consumer, "ec_ad:spot_" + lowerCase));
        Advancement preMaster = kill = this.create("Defeat " + article + " " + titleCase, "Best " + article + " " + titleCase + " in combat!", builder -> builder.m_138398_(spot).m_138371_(killItem, (Component)this.makeComponent("kill_" + lowerCase, "title"), (Component)this.makeComponent("kill_" + lowerCase, "desc"), null, FrameType.TASK, true, true, false).m_138386_("kill", (CriterionTriggerInstance)KilledTrigger.TriggerInstance.m_48134_((EntityPredicate.Builder)EntityPredicate.Builder.m_36633_().m_36636_((EntityType)dino.get()))).m_138354_(AdvancementRewards.Builder.m_10005_((int)100)).m_138389_(consumer, "ec_ad:kill_" + lowerCase));
        if (tameItem != null) {
            preMaster = this.create("Tame " + article + " " + titleCase, "Make " + article + " " + titleCase + " trust you enough to be tamed.", builder -> builder.m_138398_(kill).m_138371_(tameItem, (Component)this.makeComponent("tame_" + lowerCase, "title"), (Component)this.makeComponent("tame_" + lowerCase, "desc"), null, FrameType.TASK, true, true, false).m_138386_("tame", (CriterionTriggerInstance)TameAnimalTrigger.TriggerInstance.m_68848_((EntityPredicate)EntityPredicate.Builder.m_36633_().m_36636_((EntityType)dino.get()).m_36662_())).m_138354_(AdvancementRewards.Builder.m_10005_((int)100)).m_138389_(consumer, "ec_ad:tame_" + lowerCase));
        }
        Advancement finalPreMaster = preMaster;
        this.create(titleCase + " Master", tameItem != null ? "Discover, defeat, tame, and craft gear to master the " + titleCase + "!" : "Discover, defeat, and craft gear to master the " + titleCase + "!", builder -> {
            Advancement.Builder masterBuilder = builder.m_138398_(finalPreMaster).m_138371_(masterItem, (Component)this.makeComponent("master_" + lowerCase, "title"), (Component)this.makeComponent("master_" + lowerCase, "desc"), null, FrameType.CHALLENGE, true, true, false).m_138360_(RequirementsStrategy.f_15978_).m_138386_("spot", (CriterionTriggerInstance)SpottedEntityTrigger.TriggerInstance.spottedEntity(EntityPredicate.Builder.m_36633_().m_36636_((EntityType)dino.get()))).m_138386_("kill", (CriterionTriggerInstance)KilledTrigger.TriggerInstance.m_48134_((EntityPredicate.Builder)EntityPredicate.Builder.m_36633_().m_36636_((EntityType)dino.get()))).m_138354_(AdvancementRewards.Builder.m_10009_((ResourceLocation)totem));
            modifier.accept((Advancement.Builder)builder);
            if (tameItem != null) {
                masterBuilder = masterBuilder.m_138386_("tame", (CriterionTriggerInstance)TameAnimalTrigger.TriggerInstance.m_68848_((EntityPredicate)EntityPredicate.Builder.m_36633_().m_36636_((EntityType)dino.get()).m_36662_()));
            }
            return masterBuilder.m_138389_(consumer, "ec_ad:master_" + lowerCase);
        });
    }

    private static Consumer<Advancement.Builder> craftArmorCriteria(String dinoPrefix) {
        return builder -> {
            for (String piece : List.of("boots", "chestplate", "helmet", "leggings")) {
                builder.m_138386_("craft_" + piece, (CriterionTriggerInstance)RecipeCraftedTrigger.TriggerInstance.m_280097_((ResourceLocation)Constants.prefix(dinoPrefix + "_" + piece)));
            }
        };
    }

    private static String articleFor(String name) {
        char first = Character.toLowerCase(name.charAt(0));
        return first == 'a' || first == 'e' || first == 'i' || first == 'o' || first == 'u' ? "an" : "a";
    }

    private static String toTitleCase(String s) {
        return Arrays.stream(s.split("_")).map(word -> word.isEmpty() ? word : Ascii.toUpperCase((char)word.charAt(0)) + word.substring(1).toLowerCase()).collect(Collectors.joining(" "));
    }

    private Advancement create(String name, String description, Function<Advancement.Builder, Advancement> builder) {
        Advancement advancement = builder.apply(Advancement.Builder.m_138353_());
        DisplayInfo display = advancement.m_138320_();
        if (display != null) {
            ArchiesLangProvider.AUTO_GENERATOR.put(display.m_14977_().getString(), name);
            ArchiesLangProvider.AUTO_GENERATOR.put(display.m_14985_().getString(), description);
        }
        return advancement;
    }

    private MutableComponent makeComponent(String name, String path) {
        return Component.m_237115_((String)("advancement." + name + "." + path));
    }
}

