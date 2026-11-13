/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 */
package com.eternal_creations.archies_dino_mod.common;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ADStrings {

    public static class Gui {
        public static final String SCORCHING_HORN_TITLE = Gui.gui("scorching_horn.title");
        public static final String SCORCHING_HORN_SEARCH_HINT = Gui.gui("scorching_horn.search_hint");
        public static final String SCORCHING_HORN_TELEPORT_BUTTON = Gui.gui("scorching_horn.teleport");
        public static final String CORALOSAUR_OPEN_BUTTON = Gui.gui("coralosaur.open_button");
        public static final String ARCHIE_TITLE = Gui.gui("archie.title");

        private static String gui(String id) {
            return "gui.ec_ad." + id;
        }
    }

    public static class Messages {
        public static final String SCORCHING_HORN_TELEPORT_SUCCESS = Messages.message("pet_teleport_success");
        public static final String SCORCHING_HORN_TELEPORT_WORLD_UNAVAILABLE = Messages.message("pet_world_unavailable");
        public static final String SCORCHING_HORN_TELEPORT_PET_NOT_FOUND = Messages.message("pet_not_found");
        public static final String ARCHIE_SPAWN = Messages.message("archie.spawn");
        public static final String ARCHIE_LOW_HEALTH = Messages.message("archie.low");
        public static final String TOTEM_CANT_PLACE = Messages.message("totem.cant_place");
        public static final String ARMOR_ON_COOLDOWN = Messages.message("armor.on_cooldown");
        public static final String ARMOR_NO_ABILITY = Messages.message("armor.no_ability");
        public static final String ARMOR_NO_SET = Messages.message("armor.no_set");
        public static final String CORALOSAUR_BURIED = Messages.message("status.buried");
        public static final String CORALOSAUR_ARMOR_NOT_IN_WATER = Messages.message("armor.coralosaur.not_in_water");

        private static String message(String id) {
            return "message.ec_ad." + id;
        }
    }

    public static class Description {
        public static final String SET_BONUS = Description.item("set_bonus");
        public static final String LUSHSAUR_ARMOR_SET_BONUS = Description.setBonus("lushsaur");
        public static final String CORALOSAUR_ARMOR_SET_BONUS = Description.setBonus("coralosaur");
        public static final String THERIMOSS_ARMOR_SET_BONUS = Description.setBonus("therimoss");
        public static final String SCULKSAUR_ARMOR_SET_BONUS = Description.setBonus("sculksaur");
        public static final String CHORUSOATL_ARMOR_SET_BONUS = Description.setBonus("chorusoatl");
        public static final String OBSIDICERATOPS_ARMOR_SET_BONUS = Description.setBonus("obsidiceratops");
        public static final String LUSH_MACE = Description.item("lush_mace");
        public static final String THERI_CLAWS = Description.item("theri_claws");
        public static final String RIB_BOW = Description.item("rib_bow");
        public static final String SCULK_BOW = Description.item("sculk_bow");
        public static final String SCORCHING_HORN = Description.item("scorching_horn");
        public static final String CHORUS_SCYTHE = Description.item("chorus_scythe");
        public static final String TWO_HANDED = Description.item("two_handed");

        private static String setBonus(String id) {
            return Description.item("armor." + id, "set_bonus");
        }

        private static String item(String itemId, String id) {
            return "item.ec_ad." + itemId + ".desc." + id;
        }

        private static String item(String id) {
            return "item.ec_ad.desc." + id;
        }
    }
}

