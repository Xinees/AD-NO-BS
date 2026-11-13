/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.data.PackOutput
 *  net.minecraft.world.item.Item
 */
package com.eternal_creations.archies_dino_mod.data.gen;

import com.eternal_creations.archies_dino_mod.client.ADKeyMappings;
import com.eternal_creations.archies_dino_mod.client.screen.ArchieDialogScreen;
import com.eternal_creations.archies_dino_mod.common.ADDamageTypes;
import com.eternal_creations.archies_dino_mod.common.ADStrings;
import com.eternal_creations.archies_dino_mod.common.block.ADBlocks;
import com.eternal_creations.archies_dino_mod.common.effect.ADMobEffects;
import com.eternal_creations.archies_dino_mod.common.entity.ADEntityTypes;
import com.eternal_creations.archies_dino_mod.common.entity.mob.archie.ArchieEntity;
import com.eternal_creations.archies_dino_mod.common.items.ADItems;
import com.eternal_creations.archies_dino_mod.common.tab.ADCreativeTabs;
import com.eternal_creations.archies_dino_mod.data.util.ArchiesLangProvider;
import java.util.function.Supplier;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;

public class ArchiesLangGen
extends ArchiesLangProvider {
    public ArchiesLangGen(PackOutput output) {
        super(output);
    }

    @Override
    protected void addCustomTranslations() {
        this.addItems();
        this.addBlocks();
        this.addEntities();
        this.addMisc();
        this.addArchieDialogues();
    }

    public void addItems() {
        this.addItem(ADItems.LUSH_CORE, "Lush Core");
        this.addItem(ADItems.CHORUS_FOSSIL, "Chorus Fossil");
        this.addItem(ADItems.RAPTOR_TALON, "Raptor Talon");
        this.addItem(ADItems.SCULK_TOXIN, "Sculk Toxin");
        this.addItem(ADItems.MOSSY_CLAW, "Mossy Claw");
        this.addItem(ADItems.OBSIDIAN_HORN, "Obsidian Horn");
        this.addItem(ADItems.CORAL_RIB, "Coral Rib");
        this.addItem(ADItems.CORAL_STEW, "Coral Stew");
        this.addItem(ADItems.GUIDEBOOK, "Archie's Guidebook");
        this.addItem(ADItems.SCORCHING_HORN, "Scorching Horn");
        this.addItem(ADItems.LUSHSAUR_MACE, "Lush Mace");
        this.addItem(ADItems.CHORUS_SCYTHE, "Chorus Scythe");
        this.addItem(ADItems.RIB_BOW, "Rib Bow");
        this.addItem(ADItems.SCULK_BOW, "Sculk Bow");
        this.addItem(ADItems.OOZE_TIPPED_ARROW, "Ooze Tipped Arrow");
        this.addArmorSet("Lushsaur", ADItems.LUSHSAUR_HELMET, ADItems.LUSHSAUR_CHESTPLATE, ADItems.LUSHSAUR_LEGGINGS, ADItems.LUSHSAUR_BOOTS);
        this.addArmorSet("Obsidiceratops", ADItems.OBSIDICERATOPS_HELMET, ADItems.OBSIDICERATOPS_CHESTPLATE, ADItems.OBSIDICERATOPS_LEGGINGS, ADItems.OBSIDICERATOPS_BOOTS);
        this.addArmorSet("Chorusoatl", ADItems.CHORUSOATL_HELMET, ADItems.CHORUSOATL_CHESTPLATE, ADItems.CHORUSOATL_LEGGINGS, ADItems.CHORUSOATL_BOOTS);
        this.addArmorSet("Sculksaur", ADItems.SCULKSAUR_HELMET, ADItems.SCULKSAUR_CHESTPLATE, ADItems.SCULKSAUR_LEGGINGS, ADItems.SCULKSAUR_BOOTS);
        this.addArmorSet("Coralosaur", ADItems.CORALOSAUR_HELMET, ADItems.CORALOSAUR_CHESTPLATE, ADItems.CORALOSAUR_LEGGINGS, ADItems.CORALOSAUR_BOOTS);
        this.addArmorSet("Therimoss", ADItems.THERIMOSS_HELMET, ADItems.THERIMOSS_CHESTPLATE, ADItems.THERIMOSS_LEGGINGS, ADItems.THERIMOSS_BOOTS);
        this.addItem(ADItems.THERIMOSS_CLAWS_COPPER, "Copper Theri Claws");
        this.addItem(ADItems.THERIMOSS_CLAWS_IRON, "Iron Theri Claws");
        this.addItem(ADItems.THERIMOSS_CLAWS_GOLD, "Golden Theri Claws");
        this.addItem(ADItems.THERIMOSS_CLAWS_DIAMOND, "Diamond Theri Claws");
        this.addItem(ADItems.THERIMOSS_CLAWS_NETHERITE, "Netherite Theri Claws");
        this.addItem(ADItems.AMBER, "Amber");
    }

    private void addBlocks() {
        this.addBlock(ADBlocks.LUSHSAUR_EGG, "Lushsaur Egg");
        this.addBlock(ADBlocks.CHORUSOATL_EGG, "Chorusoatl Egg");
        this.addBlock(ADBlocks.OBSIDICERATOPS_EGG, "Obsidiceratops Egg");
        this.addBlock(ADBlocks.THERIMOSS_EGG, "Therimoss Egg");
        this.addBlock(ADBlocks.SCULKRAPTOR_EGG, "Sculkraptor Egg");
        this.addBlock(ADBlocks.LUSH_TOTEM, "Lush Totem");
        this.addBlock(ADBlocks.BURNING_TOTEM, "Burning Totem");
        this.addBlock(ADBlocks.TROPIC_TOTEM, "Tropic Totem");
        this.addBlock(ADBlocks.SOARING_TOTEM, "Soaring Totem");
        this.addBlock(ADBlocks.TIDAL_TOTEM, "Tidal Totem");
        this.addBlock(ADBlocks.ECHO_TOTEM, "Echo Totem");
        this.addBlock(ADBlocks.FEARFUL_TOTEM, "Fearful Totem");
        this.addBlock(ADBlocks.AMBER_ORE, "Amber Ore");
        this.addBlock(ADBlocks.RAW_AMBER_BLOCK, "Raw Amber");
        this.addBlock(ADBlocks.POLISHED_AMBER, "Polished Amber");
        this.addBlock(ADBlocks.POLISHED_AMBER_SLAB, "Polished Amber Slab");
        this.addBlock(ADBlocks.POLISHED_AMBER_STAIRS, "Polished Amber Stairs");
        this.addBlock(ADBlocks.POLISHED_AMBER_WALL, "Polished Amber Wall");
        this.addBlock(ADBlocks.AMBER_BRICKS, "Amber Bricks");
        this.addBlock(ADBlocks.AMBER_BRICK_SLAB, "Amber Brick Slab");
        this.addBlock(ADBlocks.AMBER_BRICK_STAIRS, "Amber Brick Stairs");
        this.addBlock(ADBlocks.AMBER_BRICK_WALL, "Amber Brick Wall");
        this.addBlock(ADBlocks.CRACKED_AMBER_BRICKS, "Cracked Amber Bricks");
        this.addBlock(ADBlocks.CARVED_AMBER, "Carved Amber");
        this.addBlock(ADBlocks.AMBER_CHAIN, "Amber Chain");
        this.addBlock(ADBlocks.AMBER_LAMP, "Amber Lamp");
    }

    private void addEntities() {
        this.addEntityType(ADEntityTypes.ARCHIE, "Archie");
        this.addEntityType(ADEntityTypes.CHORUSOATL_SPIT, "Chorusoatl Spit");
        this.addEntityType(ADEntityTypes.THERIMOSS_PROJECTILE, "Therimoss Projectile");
        this.addEntityType(ADEntityTypes.PUS_TIPPED_ARROW, "Ooze Tipped Arrow");
        this.addEntityType(ADEntityTypes.SCULKRAPTOR_SPIT, "Sculkraptor Projectile");
        this.addEntityAndEgg(ADEntityTypes.LUSHSAUR, "Lushsaur");
        this.addEntityAndEgg(ADEntityTypes.CHORUSOATL, "Chorusoatl");
        this.addEntityAndEgg(ADEntityTypes.OBSIDICERATOPS, "Obsidiceratops");
        this.addEntityAndEgg(ADEntityTypes.THERIMOSS, "Therimoss");
        this.addEntityAndEgg(ADEntityTypes.SCULKRAPTOR, "Sculkraptor");
        this.addEntityAndEgg(ADEntityTypes.SCULKSAUR, "Sculksaur");
        this.addEntityAndEgg(ADEntityTypes.CORALOSAUR, "Coralosaur");
    }

    private void addMisc() {
        this.addEffect(ADMobEffects.DAZED, "Dazed");
        this.addEffect(ADMobEffects.TRACKED, "Tracked");
        this.addEffect(ADMobEffects.HUNGER_REDUCTION, "Hunger Reduction");
        this.addEffect(ADMobEffects.FALL_NEGATION, "Fall Negation");
        this.add("ec_ad", "Archie\u2019s Dinosaurs");
        this.addCreativeTab(ADCreativeTabs.MOD_TAB, "Archie\u2019s Dinosaurs");
        this.addDamageType(ADDamageTypes.PUS_ARROW, "%1$s was obliterated by %2$s", "%1$s was obliterated by %2$s using %3$s", null);
        this.add(ADStrings.Description.TWO_HANDED, "Two-Handed");
        this.add(ADStrings.Description.SET_BONUS, "Set bonus:");
        this.add(ADStrings.Description.CHORUS_SCYTHE, "\u00a77Spin attack in a \u00a796-block\u00a77 radius\n\u00a77Deals \u00a79150%%\u00a77 damage and launches enemies\n\u00a77Cooldown: \u00a79%ss");
        this.add(ADStrings.Description.LUSH_MACE, "\u00a77Spin attack in a \u00a793-block\u00a77 radius\n\u00a77Deals \u00a79150%%\u00a77 damage and knocks back enemies\n\u00a77Cooldown: \u00a79%ss");
        this.add(ADStrings.Description.SCULK_BOW, "\u00a77Empowers \u00a79Ooze Tipped Arrows\n\u00a77and marks targets, drawing\n\u00a77extreme aggro from sculk mobs");
        this.add(ADStrings.Description.RIB_BOW, "\u00a77Arrows travel freely underwater");
        this.add(ADStrings.Description.THERI_CLAWS, "\u00a77Allows you to slide down walls");
        this.add(ADStrings.Description.SCORCHING_HORN, "\u00a77Can summon your tamed creatures to you");
        this.add(ADStrings.Description.LUSHSAUR_ARMOR_SET_BONUS, "  \u00a77Camouflages you while sneaking:\n   \u00a78\u2022 \u00a7950%% \u00a77when moving\n   \u00a78\u2022 \u00a79100%% \u00a77when standing still\n  \u00a79Resistance");
        this.add(ADStrings.Description.CORALOSAUR_ARMOR_SET_BONUS, "  \u00a77Press \u00a7e[{key:ec_ad.armor}]\u00a77 to summon allied Guardians\n    \u00a78\u2022 \u00a77Cooldown: \u00a79%ss\n  \u00a79Full Oceanic Adaptation");
        this.add(ADStrings.Description.CHORUSOATL_ARMOR_SET_BONUS, "  \u00a77Sneak + Press \u00a7e[{key:ec_ad.armor}]\u00a77 to teleport:\n   \u00a78\u2022 \u00a77Range up to \u00a7945 blocks\n   \u00a78\u2022 \u00a77Cooldown: \u00a79%ss\n  \u00a79Slow Falling");
        this.add(ADStrings.Description.OBSIDICERATOPS_ARMOR_SET_BONUS, "  \u00a77Allows walking on lava surfaces\n  \u00a77Immunity to all Fire & Lava damage");
        this.add(ADStrings.Description.SCULKSAUR_ARMOR_SET_BONUS, "  \u00a77Cancels all movement & vibration noise\n  \u00a77Hold \u00a7e[{key:ec_ad.armor}]\u00a77 to emit a Sculksaur explosion:\n   \u00a78\u2022 \u00a77Blinds, confuses and knocks foes back.\n   \u00a78\u2022 \u00a77Cooldown: \u00a79%ss");
        this.add(ADStrings.Description.THERIMOSS_ARMOR_SET_BONUS, "  \u00a77Significantly reduces food consumption\n  \u00a77Press \u00a7e[{key:ec_ad.armor}]\u00a77 for a burst of speed:\n   \u00a78\u2022 \u00a77Cooldown: \u00a79%ss");
        this.add(ADStrings.Gui.CORALOSAUR_OPEN_BUTTON, "Open");
        this.add(ADStrings.Messages.ARMOR_NO_ABILITY, "\u00a74This Set Has No Active Ability!");
        this.add(ADStrings.Messages.ARMOR_ON_COOLDOWN, "\u00a7bCooldown is at \u00a74%s%%\u00a7b!");
        this.add(ADStrings.Messages.ARMOR_NO_SET, "\u00a74You Aren't Wearing A Matching Archie\u2019s Dinosaurs Armor Set!");
        this.add(ADStrings.Messages.CORALOSAUR_ARMOR_NOT_IN_WATER, "\u00a74You Aren't In Water!");
        this.add(ADStrings.Messages.CORALOSAUR_BURIED, "\u00a7lYou are Being Buried!");
        this.add(ADStrings.Gui.SCORCHING_HORN_TITLE, "Pet List");
        this.add(ADStrings.Gui.SCORCHING_HORN_SEARCH_HINT, "Search pets...");
        this.add(ADStrings.Gui.SCORCHING_HORN_TELEPORT_BUTTON, "Teleport");
        this.add(ADStrings.Messages.SCORCHING_HORN_TELEPORT_SUCCESS, "%s has been teleported to you!");
        this.add(ADStrings.Messages.SCORCHING_HORN_TELEPORT_WORLD_UNAVAILABLE, "%s dimension is currently unavailable!");
        this.add(ADStrings.Messages.SCORCHING_HORN_TELEPORT_PET_NOT_FOUND, "%s not found or dead.");
        this.add("category.ec_ad.default", "Archie\u2019s Dinosaurs Key Bindings");
        this.addKeyMapping(ADKeyMappings.ARMOR_ABILITY, "Use Armor Ability");
        this.addKeyMapping(ADKeyMappings.MOUNT_ATTACK_PRIMARY, "Primary Mount Attack");
        this.addKeyMapping(ADKeyMappings.MOUNT_ATTACK_SPECIAL, "Special Mount Attack");
    }

    private void addArchieDialogues() {
        this.add(ADStrings.Gui.ARCHIE_TITLE, "\u00a7lArchie");
        this.add(ADStrings.Messages.ARCHIE_SPAWN, "\u00a7eArchie was spotted near you!");
        this.add(ADStrings.Messages.ARCHIE_LOW_HEALTH, "Sorry, I'm getting hit a lot! I need a break, cover up for me!");
        this.addArchieDialogueTree();
        this.addArchieVoicedLogs();
    }

    private void addArchieDialogueTree() {
        String ARCHIE_DENY_TEXT = "Oh, sorry! I didn't mean to get in your way.";
        String ARCHIE_FOLLOW_TEXT = "Alright, let's go!";
        this.add(ArchieDialogScreen.DialogTree.INTRO.speech(), "Shhh, don't move!\n\nWe're in dino territory.\nI've been tracking this specimen for weeks.\n\nTake me with you.\nI'll fight at your side, and I've got dino knowledge and gear to trade.");
        this.add(ArchieDialogScreen.DialogOption.FOLLOW_ME.text(), "Follow me!");
        this.add(ArchieDialogScreen.ARCHIE_ON_FOLLOW, "Alright, let's go!");
        this.add(ArchieDialogScreen.DialogOption.DO_NOT_FOLLOW_ME.text(), "No, thanks.");
        this.add(ArchieDialogScreen.ARCHIE_DENY, "Oh, sorry! I didn't mean to get in your way.");
        this.add(ArchieDialogScreen.DialogTree.FRIEND.speech(), "Right behind you. Eyes on the treeline.\n\nWant to talk dinos, or see what I've got for trade?");
        this.add(ArchieDialogScreen.DialogOption.DINO_QUESTION.text(), "I need information about a dino.");
        this.add(ArchieDialogScreen.DialogTree.DINO_INFO.speech(), "Ah, a fellow enthusiast!\n\nI've tracked every dino in these lands, so ask away.\nWhich one do you want to know about?");
        this.add(ArchieDialogScreen.DialogOption.LUSHSAUR.text(), "Lushsaur");
        this.add(ArchieDialogScreen.ARCHIE_ON_LUSHSAUR, "We should probably head underground.\nFind a Lush Cave and keep your eyes peeled. They are very good at blending in.");
        this.add(ArchieDialogScreen.DialogOption.THERIMOSS.text(), "Therimoss");
        this.add(ArchieDialogScreen.ARCHIE_ON_THERIMOSS, "Look for a Jungle!\nThey are quite big so you should be able to see them from afar.");
        this.add(ArchieDialogScreen.DialogOption.CORALOSAUR.text(), "Coralosaur");
        this.add(ArchieDialogScreen.ARCHIE_ON_CORALOSAUR, "This one lives under water.\nFind a Warm Ocean and look for many bubbles rising straight out of sand on the ocean floor.");
        this.add(ArchieDialogScreen.DialogOption.SCULKRAPTOR.text(), "Sculkraptor");
        this.add(ArchieDialogScreen.ARCHIE_ON_SCULKRAPTOR, "They lurk in the Deep Dark.\nBe careful when approaching, because if you encounter a pack they will be aggressive.");
        this.add(ArchieDialogScreen.DialogOption.SCULKSAUR.text(), "Sculksaur");
        this.add(ArchieDialogScreen.ARCHIE_ON_SCULKSAUR, "If you are sure you want to find it there is only one way.\nGo to an Ancient City and bait the Sculksaur by summoning its favorite snack. The Warden.");
        this.add(ArchieDialogScreen.DialogOption.OBSIDICERATOPS.text(), "Obsidiceratops");
        this.add(ArchieDialogScreen.ARCHIE_ON_OBSIDICERATOPS, "The only environment this one would be comfortable in is very hot.\nSo we should probably head to the Nether.");
        this.add(ArchieDialogScreen.DialogOption.CHORUSOATL.text(), "Chorusoatl");
        this.add(ArchieDialogScreen.ARCHIE_ON_CHORUSOATL, "This is a more challenging one to find.\nWe will have to find a Stronghold and go to The End.\nThen, after defeating the Ender Dragon, search for the Chorusoatl in the outer End Islands.");
        this.add(ArchieDialogScreen.DialogOption.TRADE.text(), "Let's trade.");
        this.add(ArchieDialogScreen.ARCHIE_ON_TRADE, "Got something for me?\nLet's make a deal!");
        this.add(ArchieDialogScreen.DialogOption.STOP.text(), "Stop following.");
        this.add(ArchieDialogScreen.ARCHIE_ON_STOP, "Oh, sorry! I didn't mean to get in your way.");
        this.add(ADStrings.Messages.TOTEM_CANT_PLACE, "You must collect all %s stamps before placing this Totem!");
        this.add("ec_ad.config.book", "You can always configure everything you need in mod configuration files.");
    }

    private void addArchieVoicedLogs() {
        this.add(ArchieEntity.VoicedChatLog.ARCHIE_ABOVEGROUND_1.text(), "Feels strange being out in the open again... stay alert anyway.");
        this.add(ArchieEntity.VoicedChatLog.ARCHIE_ABOVEGROUND_2.text(), "Tracks don't last long up here. We'll have to rely on instinct.");
        this.add(ArchieEntity.VoicedChatLog.ARCHIE_ACCEPT.text(), "Alright, let's go!");
        this.add(ArchieEntity.VoicedChatLog.ARCHIE_CHORUSOATL_1.text(), "Look up. See them? They always move in a flock.");
        this.add(ArchieEntity.VoicedChatLog.ARCHIE_CHORUSOATL_2.text(), "If one screams, the whole flock will know where we are.");
        this.add(ArchieEntity.VoicedChatLog.ARCHIE_CORALOSAUR_1.text(), "See those bubbles? Something massive is sleeping down there...");
        this.add(ArchieEntity.VoicedChatLog.ARCHIE_CORALOSAUR_2.text(), "Careful where you dig. If you wake it, there's no putting it back to sleep.");
        this.add(ArchieEntity.VoicedChatLog.ARCHIE_DENY.text(), "Oh, sorry! I didn't mean to get in your way.");
        this.add(ArchieEntity.VoicedChatLog.ARCHIE_DIMENSIONS_1.text(), "This place... it doesn't follow the same rules. Stay focused.");
        this.add(ArchieEntity.VoicedChatLog.ARCHIE_DIMENSIONS_2.text(), "Whatever lives here has adapted. We're the outsiders now.");
        this.add(ArchieEntity.VoicedChatLog.ARCHIE_FRIEND.text(), "What do you need? I'm right here.");
        this.add(ArchieEntity.VoicedChatLog.ARCHIE_INTRO.text(), "Shhh, don't move! We're in dino territory. I've been tracking this specimen for weeks. You can join me, if you want.");
        this.add(ArchieEntity.VoicedChatLog.ARCHIE_LUSHSAUR_1.text(), "Wait don't move. It's right there... or at least, it was.");
        this.add(ArchieEntity.VoicedChatLog.ARCHIE_LUSHSAUR_2.text(), "They blend in when they feel safe. You won't see it unless it wants you to.");
        this.add(ArchieEntity.VoicedChatLog.ARCHIE_OBSIDICERATOPS_1.text(), "That one feeds on lava. See the glow? That tells you everything.");
        this.add(ArchieEntity.VoicedChatLog.ARCHIE_OBSIDICERATOPS_2.text(), "If it runs dry, it won't hesitate. Keep your distance.");
        this.add(ArchieEntity.VoicedChatLog.ARCHIE_SCULK_RAPTOR_1.text(), "Stay sharp. Sculkraptors don't hunt alone.");
        this.add(ArchieEntity.VoicedChatLog.ARCHIE_SCULK_RAPTOR_2.text(), "If my research is right, the Warden isn't the only thing we should fear...");
        this.add(ArchieEntity.VoicedChatLog.ARCHIE_SCULKSAUR_1.text(), "Be careful... down here, even the walls are listening.");
        this.add(ArchieEntity.VoicedChatLog.ARCHIE_SCULKSAUR_2.text(), "Don't touch that slime! You'll light yourself up like a beacon to them.");
        this.add(ArchieEntity.VoicedChatLog.ARCHIE_THERIMOSS_1.text(), "There... see it? That's a Therimoss. Don't rush it, it barely notices us.");
        this.add(ArchieEntity.VoicedChatLog.ARCHIE_THERIMOSS_2.text(), "If you want its attention, you'll need something big. Really big.");
        this.add(ArchieEntity.VoicedChatLog.ARCHIE_UNDERGROUND_1.text(), "Can you see anything? We're looking for a Deep Dark... or maybe Lush Caves.");
        this.add(ArchieEntity.VoicedChatLog.ARCHIE_UNDERGROUND_2.text(), "Listen closely. Caves like these always hide more than they show.");
    }

    private void addArmorSet(String name, Supplier<? extends Item> helmet, Supplier<? extends Item> chestplate, Supplier<? extends Item> leggings, Supplier<? extends Item> boots) {
        this.addItem(helmet, name + " Helmet");
        this.addItem(chestplate, name + " Chestplate");
        this.addItem(leggings, name + " Leggings");
        this.addItem(boots, name + " Boots");
    }
}

