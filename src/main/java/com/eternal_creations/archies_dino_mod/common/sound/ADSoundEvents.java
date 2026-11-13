/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.sounds.SoundEvent
 */
package com.eternal_creations.archies_dino_mod.common.sound;

import com.eternal_creations.archies_dino_mod.platform.Services;
import com.eternal_creations.archies_dino_mod.util.ResourceSupplier;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.sounds.SoundEvent;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ADSoundEvents {
    public static ResourceSupplier<SoundEvent> DINO_EQUIP = ADSoundEvents.registerMobSound("dino.equip");
    public static ResourceSupplier<SoundEvent> DINO_EGG_PICKUP = ADSoundEvents.registerMobSound("dino.egg.pickup");
    public static ResourceSupplier<SoundEvent> DINO_EGG_BREAK = ADSoundEvents.registerMobSound("dino.egg.breaks");
    public static ResourceSupplier<SoundEvent> DINO_EGG_CRACK = ADSoundEvents.registerMobSound("dino.egg.cracks");
    public static ResourceSupplier<SoundEvent> DINO_EGG_HATCH = ADSoundEvents.registerMobSound("dino.egg.hatches");
    public static ResourceSupplier<SoundEvent> DINO_EGG_LAY = ADSoundEvents.registerMobSound("dino.egg.lay");
    public static ResourceSupplier<SoundEvent> THERIMOSS_IDLE = ADSoundEvents.registerMobSound("therimoss.idle");
    public static ResourceSupplier<SoundEvent> THERIMOSS_HURT = ADSoundEvents.registerMobSound("therimoss.hurt");
    public static ResourceSupplier<SoundEvent> THERIMOSS_DEATH = ADSoundEvents.registerMobSound("therimoss.death");
    public static ResourceSupplier<SoundEvent> THERIMOSS_ATTACK_BEAK = ADSoundEvents.registerMobSound("therimoss.beak");
    public static ResourceSupplier<SoundEvent> THERIMOSS_ATTACK_MELEE = ADSoundEvents.registerMobSound("therimoss.melee");
    public static ResourceSupplier<SoundEvent> THERIMOSS_ATTACK_THROW = ADSoundEvents.registerMobSound("therimoss.throw");
    public static ResourceSupplier<SoundEvent> THERIMOSS_FALL = ADSoundEvents.registerMobSound("therimoss.fall");
    public static ResourceSupplier<SoundEvent> THERIMOSS_LAND = ADSoundEvents.registerMobSound("therimoss.land");
    public static ResourceSupplier<SoundEvent> THERIMOSS_LAND_BIGGER = ADSoundEvents.registerMobSound("therimoss.land_bigger");
    public static ResourceSupplier<SoundEvent> THERIMOSS_CLIMB_UP = ADSoundEvents.registerMobSound("therimoss.climb_up");
    public static ResourceSupplier<SoundEvent> THERIMOSS_CLIMB_DOWN = ADSoundEvents.registerMobSound("therimoss.climb_down");
    public static ResourceSupplier<SoundEvent> THERIMOSS_EAT = ADSoundEvents.registerMobSound("therimoss.eat");
    public static ResourceSupplier<SoundEvent> THERIMOSS_STEP = ADSoundEvents.registerMobSound("therimoss.step");
    public static ResourceSupplier<SoundEvent> THERIMOSS_BLOCK_THROW = ADSoundEvents.registerMobSound("therimoss.block.throw");
    public static ResourceSupplier<SoundEvent> THERIMOSS_BLOCK_LAND = ADSoundEvents.registerMobSound("therimoss.block.land");
    public static ResourceSupplier<SoundEvent> LUSHSAUR_IDLE = ADSoundEvents.registerMobSound("lushsaur.idle");
    public static ResourceSupplier<SoundEvent> LUSHSAUR_ATTACK_HEADBUTT = ADSoundEvents.registerMobSound("lushsaur.attack1");
    public static ResourceSupplier<SoundEvent> LUSHSAUR_ATTACK_BITE = ADSoundEvents.registerMobSound("lushsaur.attack2");
    public static ResourceSupplier<SoundEvent> LUSHSAUR_ATTACK_TAIL_STOMP = ADSoundEvents.registerMobSound("lushsaur.attack3");
    public static ResourceSupplier<SoundEvent> LUSHSAUR_ATTACK_TAIL_SWING = ADSoundEvents.registerMobSound("lushsaur.attack4");
    public static ResourceSupplier<SoundEvent> LUSHSAUR_EMERGE = ADSoundEvents.registerMobSound("lushsaur.up");
    public static ResourceSupplier<SoundEvent> LUSHSAUR_BURY = ADSoundEvents.registerMobSound("lushsaur.down");
    public static ResourceSupplier<SoundEvent> LUSHSAUR_EAR_RINGING = ADSoundEvents.registerMobSound("lushsaur.ear_ringing");
    public static ResourceSupplier<SoundEvent> LUSHSAUR_EAT = ADSoundEvents.registerMobSound("lushsaur.eat");
    public static ResourceSupplier<SoundEvent> LUSHSAUR_REST = ADSoundEvents.registerMobSound("lushsaur.rest");
    public static ResourceSupplier<SoundEvent> LUSHSAUR_ROAR = ADSoundEvents.registerMobSound("lushsaur.roar");
    public static ResourceSupplier<SoundEvent> LUSHSAUR_RUN_ATTACK = ADSoundEvents.registerMobSound("lushsaur.run_attack");
    public static ResourceSupplier<SoundEvent> LUSHSAUR_STEP = ADSoundEvents.registerMobSound("lushsaur.step");
    public static ResourceSupplier<SoundEvent> LUSHSAUR_HURT = ADSoundEvents.registerMobSound("lushsaur.hurt");
    public static ResourceSupplier<SoundEvent> LUSHSAUR_DEATH = ADSoundEvents.registerMobSound("lushsaur.death");
    public static ResourceSupplier<SoundEvent> LUSHSAUR_SHEAR = ADSoundEvents.registerMobSound("lushsaur.shear");
    public static ResourceSupplier<SoundEvent> LUSHSAUR_STOMP_EXPLOSION = ADSoundEvents.registerMobSound("lushsaur.stomp_explosion");
    public static ResourceSupplier<SoundEvent> CHORUSOATL_BITE = ADSoundEvents.registerMobSound("chorusoatl.bite");
    public static ResourceSupplier<SoundEvent> CHORUSOATL_HURT = ADSoundEvents.registerMobSound("chorusoatl.hurt");
    public static ResourceSupplier<SoundEvent> CHORUSOATL_DEATH_AIR = ADSoundEvents.registerMobSound("chorusoatl.death_air");
    public static ResourceSupplier<SoundEvent> CHORUSOATL_DEATH_AIR_LAND = ADSoundEvents.registerMobSound("chorusoatl.death_air_ground");
    public static ResourceSupplier<SoundEvent> CHORUSOATL_DEATH_FALLING = ADSoundEvents.registerMobSound("chorusoatl.death_falling");
    public static ResourceSupplier<SoundEvent> CHORUSOATL_DEATH_GROUND = ADSoundEvents.registerMobSound("chorusoatl.death_ground");
    public static ResourceSupplier<SoundEvent> CHORUSOATL_FLAP_DOWN = ADSoundEvents.registerMobSound("chorusoatl.flap_down");
    public static ResourceSupplier<SoundEvent> CHORUSOATL_FLAP_UP = ADSoundEvents.registerMobSound("chorusoatl.flap_up");
    public static ResourceSupplier<SoundEvent> CHORUSOATL_GLIDE = ADSoundEvents.registerMobSound("chorusoatl.glide");
    public static ResourceSupplier<SoundEvent> CHORUSOATL_IDLE = ADSoundEvents.registerMobSound("chorusoatl.idle");
    public static ResourceSupplier<SoundEvent> CHORUSOATL_LAND = ADSoundEvents.registerMobSound("chorusoatl.land");
    public static ResourceSupplier<SoundEvent> CHORUSOATL_SCREAM = ADSoundEvents.registerMobSound("chorusoatl.scream");
    public static ResourceSupplier<SoundEvent> CHORUSOATL_SPIT = ADSoundEvents.registerMobSound("chorusoatl.spit");
    public static ResourceSupplier<SoundEvent> CHORUSOATL_STEP_HEAVY = ADSoundEvents.registerMobSound("chorusoatl.step_heavy");
    public static ResourceSupplier<SoundEvent> CHORUSOATL_STEP_LIGHT = ADSoundEvents.registerMobSound("chorusoatl.step_light");
    public static ResourceSupplier<SoundEvent> CHORUSOATL_STOMP = ADSoundEvents.registerMobSound("chorusoatl.stomp");
    public static ResourceSupplier<SoundEvent> CHORUSOATL_TAME = ADSoundEvents.registerMobSound("chorusoatl.tame");
    public static ResourceSupplier<SoundEvent> CHORUSOATL_UP = ADSoundEvents.registerMobSound("chorusoatl.up");
    public static ResourceSupplier<SoundEvent> OBSIDICERATOPS_AGRO = ADSoundEvents.registerMobSound("obsidiceratops.agro");
    public static ResourceSupplier<SoundEvent> OBSIDICERATOPS_ATTACK = ADSoundEvents.registerMobSound("obsidiceratops.attack");
    public static ResourceSupplier<SoundEvent> OBSIDICERATOPS_CHARGE_AGRO = ADSoundEvents.registerMobSound("obsidiceratops.charge_agro");
    public static ResourceSupplier<SoundEvent> OBSIDICERATOPS_CHARGE_ATTACK = ADSoundEvents.registerMobSound("obsidiceratops.charge_attack");
    public static ResourceSupplier<SoundEvent> OBSIDICERATOPS_DEATH = ADSoundEvents.registerMobSound("obsidiceratops.death");
    public static ResourceSupplier<SoundEvent> OBSIDICERATOPS_DRINK = ADSoundEvents.registerMobSound("obsidiceratops.drink");
    public static ResourceSupplier<SoundEvent> OBSIDICERATOPS_EAT = ADSoundEvents.registerMobSound("obsidiceratops.eat");
    public static ResourceSupplier<SoundEvent> OBSIDICERATOPS_HURT = ADSoundEvents.registerMobSound("obsidiceratops.hurt");
    public static ResourceSupplier<SoundEvent> OBSIDICERATOPS_IDLE = ADSoundEvents.registerMobSound("obsidiceratops.idle");
    public static ResourceSupplier<SoundEvent> OBSIDICERATOPS_RUN = ADSoundEvents.registerMobSound("obsidiceratops.run");
    public static ResourceSupplier<SoundEvent> OBSIDICERATOPS_STEP = ADSoundEvents.registerMobSound("obsidiceratops.step");
    public static ResourceSupplier<SoundEvent> OBSIDICERATOPS_WALK = ADSoundEvents.registerMobSound("obsidiceratops.walk");
    public static ResourceSupplier<SoundEvent> OBSIDICERATOPS_FEED_LAVA = ADSoundEvents.registerMobSound("obsidiceratops.feed_lava");
    public static ResourceSupplier<SoundEvent> OBSIDICERATOPS_HARVEST_LAVA = ADSoundEvents.registerMobSound("obsidiceratops.harvest_lava");
    public static ResourceSupplier<SoundEvent> OBSIDICERATOPS_EGG_SCORCH = ADSoundEvents.registerMobSound("obsidiceratops.egg.scorch");
    public static ResourceSupplier<SoundEvent> MACE_ATTACK = ADSoundEvents.registerItemSound("lushsaur_mace.attack");
    public static ResourceSupplier<SoundEvent> MACE_SPIN = ADSoundEvents.registerItemSound("lushsaur_mace.spin");
    public static ResourceSupplier<SoundEvent> SCYTHE_ATTACK = ADSoundEvents.registerItemSound("chorus_scythe.attack");
    public static ResourceSupplier<SoundEvent> SCYTHE_SPIN = ADSoundEvents.registerItemSound("chorus_scythe.spin");
    public static ResourceSupplier<SoundEvent> SCORCHING_HORN = ADSoundEvents.registerItemSound("scorching_horn");
    public static ResourceSupplier<SoundEvent> THERIMOSS_CLAWS_ATTACK = ADSoundEvents.registerItemSound("therimoss_claws.attack");
    public static ResourceSupplier<SoundEvent> ARMOR_EQUIP_LUSHSAUR = ADSoundEvents.registerItemSound("armor.equip_lush");
    public static ResourceSupplier<SoundEvent> ARMOR_EQUIP_THERIMOSS = ADSoundEvents.registerItemSound("armor.equip_therimoss");
    public static ResourceSupplier<SoundEvent> ARMOR_EQUIP_CORALOSAUR = ADSoundEvents.registerItemSound("armor.equip_coralosaur");
    public static ResourceSupplier<SoundEvent> ARMOR_EQUIP_OBSIDICERATOPS = ADSoundEvents.registerItemSound("armor.equip_obsidiceratops");
    public static ResourceSupplier<SoundEvent> ARMOR_EQUIP_CHORUSOATL = ADSoundEvents.registerItemSound("armor.equip_chorusoatl");
    public static ResourceSupplier<SoundEvent> CHORUSOATL_ARMOR_ABILITY = ADSoundEvents.registerItemSound("armor.teleport");
    public static ResourceSupplier<SoundEvent> ARMOR_EQUIP_SCULKSAUR = ADSoundEvents.registerItemSound("armor.equip_sculksaur");
    public static ResourceSupplier<SoundEvent> SCULKSAUR_ARMOR_ABILITY = ADSoundEvents.registerItemSound("armor.explosion");
    public static ResourceSupplier<SoundEvent> SCULKRAPTOR_IDLE = ADSoundEvents.registerMobSound("sculkraptor.idle");
    public static ResourceSupplier<SoundEvent> SCULKRAPTOR_ATTACK = ADSoundEvents.registerMobSound("sculkraptor.bite_attack");
    public static ResourceSupplier<SoundEvent> SCULKRAPTOR_RANGED = ADSoundEvents.registerMobSound("sculkraptor.ranged_attack");
    public static ResourceSupplier<SoundEvent> SCULKRAPTOR_HURT = ADSoundEvents.registerMobSound("sculkraptor.hurt");
    public static ResourceSupplier<SoundEvent> SCULKRAPTOR_DEATH = ADSoundEvents.registerMobSound("sculkraptor.death");
    public static ResourceSupplier<SoundEvent> SCULKRAPTOR_EXPLOSION = ADSoundEvents.registerMobSound("sculkraptor.scream");
    public static ResourceSupplier<SoundEvent> SCULKRAPTOR_SNIFF = ADSoundEvents.registerMobSound("sculkraptor.sniff");
    public static ResourceSupplier<SoundEvent> SCULKRAPTOR_STEP = ADSoundEvents.registerMobSound("sculkraptor.step");
    public static ResourceSupplier<SoundEvent> SCULKRAPTOR_TENDRIL_CLICKS = ADSoundEvents.registerMobSound("sculkraptor.tendril_clicks");
    public static ResourceSupplier<SoundEvent> SCULKRAPTOR_NEST_EGG = ADSoundEvents.registerMobSound("sculkraptor.nest.egg");
    public static ResourceSupplier<SoundEvent> SCULKRAPTOR_SCULK_PROJECTILE = ADSoundEvents.registerMobSound("sculkraptor.sculk_projectile");
    public static ResourceSupplier<SoundEvent> SCULKRAPTOR_SCULK_TRACKER = ADSoundEvents.registerMobSound("sculkraptor.sculk_tracker");
    public static ResourceSupplier<SoundEvent> SCULKSAUR_IDLE = ADSoundEvents.registerMobSound("sculksaur.idle");
    public static ResourceSupplier<SoundEvent> SCULKSAUR_ATTACK = ADSoundEvents.registerMobSound("sculksaur.attack");
    public static ResourceSupplier<SoundEvent> SCULKSAUR_ATTACK_TAIL = ADSoundEvents.registerMobSound("sculksaur.tail_attack");
    public static ResourceSupplier<SoundEvent> SCULKSAUR_BITE = ADSoundEvents.registerMobSound("sculksaur.charge_bite");
    public static ResourceSupplier<SoundEvent> SCULKSAUR_EXPLOSION = ADSoundEvents.registerMobSound("sculksaur.explosion");
    public static ResourceSupplier<SoundEvent> SCULKSAUR_ROAR = ADSoundEvents.registerMobSound("sculksaur.roar");
    public static ResourceSupplier<SoundEvent> SCULKSAUR_SNIFF = ADSoundEvents.registerMobSound("sculksaur.sniff");
    public static ResourceSupplier<SoundEvent> SCULKSAUR_SPAWN = ADSoundEvents.registerMobSound("sculksaur.spawn");
    public static ResourceSupplier<SoundEvent> SCULKSAUR_STEP = ADSoundEvents.registerMobSound("sculksaur.step");
    public static ResourceSupplier<SoundEvent> SCULKSAUR_HURT = ADSoundEvents.registerMobSound("sculksaur.hurt");
    public static ResourceSupplier<SoundEvent> SCULKSAUR_DEATH = ADSoundEvents.registerMobSound("sculksaur.death");
    public static ResourceSupplier<SoundEvent> SCULKSAUR_WART_POP = ADSoundEvents.registerMobSound("sculksaur.wart_pop");
    public static ResourceSupplier<SoundEvent> SCULKSAUR_TENDRIL_CLICKS = ADSoundEvents.registerMobSound("sculksaur.tendril_clicks");
    public static ResourceSupplier<SoundEvent> CORALOSAUR_IDLE = ADSoundEvents.registerMobSound("coralosaur.idle");
    public static ResourceSupplier<SoundEvent> CORALOSAUR_SLEEP = ADSoundEvents.registerMobSound("coralosaur.sleep");
    public static ResourceSupplier<SoundEvent> CORALOSAUR_BITE = ADSoundEvents.registerMobSound("coralosaur.bite");
    public static ResourceSupplier<SoundEvent> CORALOSAUR_DEATH = ADSoundEvents.registerMobSound("coralosaur.death");
    public static ResourceSupplier<SoundEvent> CORALOSAUR_HURT = ADSoundEvents.registerMobSound("coralosaur.hurt");
    public static ResourceSupplier<SoundEvent> CORALOSAUR_BURY = ADSoundEvents.registerMobSound("coralosaur.bury");
    public static ResourceSupplier<SoundEvent> CORALOSAUR_WAKE = ADSoundEvents.registerMobSound("coralosaur.wake");
    public static ResourceSupplier<SoundEvent> ARCHIE_ABOVEGROUND_1 = ADSoundEvents.registerMobSound("archie.aboveground_1");
    public static ResourceSupplier<SoundEvent> ARCHIE_ABOVEGROUND_2 = ADSoundEvents.registerMobSound("archie.aboveground_2");
    public static ResourceSupplier<SoundEvent> ARCHIE_ACCEPT = ADSoundEvents.registerMobSound("archie.accept");
    public static ResourceSupplier<SoundEvent> ARCHIE_CHORUSOATL_1 = ADSoundEvents.registerMobSound("archie.chorusoatl_1");
    public static ResourceSupplier<SoundEvent> ARCHIE_CHORUSOATL_2 = ADSoundEvents.registerMobSound("archie.chorusoatl_2");
    public static ResourceSupplier<SoundEvent> ARCHIE_CORALOSAUR_1 = ADSoundEvents.registerMobSound("archie.coralosaur_1");
    public static ResourceSupplier<SoundEvent> ARCHIE_CORALOSAUR_2 = ADSoundEvents.registerMobSound("archie.coralosaur_2");
    public static ResourceSupplier<SoundEvent> ARCHIE_DENY = ADSoundEvents.registerMobSound("archie.deny");
    public static ResourceSupplier<SoundEvent> ARCHIE_DIMENSIONS_1 = ADSoundEvents.registerMobSound("archie.dimensions_1");
    public static ResourceSupplier<SoundEvent> ARCHIE_DIMENSIONS_2 = ADSoundEvents.registerMobSound("archie.dimensions_2");
    public static ResourceSupplier<SoundEvent> ARCHIE_FRIEND = ADSoundEvents.registerMobSound("archie.friend");
    public static ResourceSupplier<SoundEvent> ARCHIE_INTRO = ADSoundEvents.registerMobSound("archie.intro");
    public static ResourceSupplier<SoundEvent> ARCHIE_LUSHSAUR_1 = ADSoundEvents.registerMobSound("archie.lushsaur_1");
    public static ResourceSupplier<SoundEvent> ARCHIE_LUSHSAUR_2 = ADSoundEvents.registerMobSound("archie.lushsaur_2");
    public static ResourceSupplier<SoundEvent> ARCHIE_OBSIDICERATOPS_1 = ADSoundEvents.registerMobSound("archie.obsidiceratops_1");
    public static ResourceSupplier<SoundEvent> ARCHIE_OBSIDICERATOPS_2 = ADSoundEvents.registerMobSound("archie.obsidiceratops_2");
    public static ResourceSupplier<SoundEvent> ARCHIE_SCULK_RAPTOR_1 = ADSoundEvents.registerMobSound("archie.sculk_raptor_1");
    public static ResourceSupplier<SoundEvent> ARCHIE_SCULK_RAPTOR_2 = ADSoundEvents.registerMobSound("archie.sculk_raptor_2");
    public static ResourceSupplier<SoundEvent> ARCHIE_SCULKSAUR_1 = ADSoundEvents.registerMobSound("archie.sculksaur_1");
    public static ResourceSupplier<SoundEvent> ARCHIE_SCULKSAUR_2 = ADSoundEvents.registerMobSound("archie.sculksaur_2");
    public static ResourceSupplier<SoundEvent> ARCHIE_THERIMOSS_1 = ADSoundEvents.registerMobSound("archie.therimoss_1");
    public static ResourceSupplier<SoundEvent> ARCHIE_THERIMOSS_2 = ADSoundEvents.registerMobSound("archie.therimoss_2");
    public static ResourceSupplier<SoundEvent> ARCHIE_UNDERGRONUD_1 = ADSoundEvents.registerMobSound("archie.underground_1");
    public static ResourceSupplier<SoundEvent> ARCHIE_UNDERGRONUD_2 = ADSoundEvents.registerMobSound("archie.underground_2");

    public static void init() {
    }

    private static ResourceSupplier<SoundEvent> registerItemSound(String id) {
        return Services.COMMON.registerSoundEvent(ADSoundEvents.prefix("item", id), SoundEvent::m_262824_);
    }

    private static ResourceSupplier<SoundEvent> registerMobSound(String id) {
        return Services.COMMON.registerSoundEvent(ADSoundEvents.prefix("mob", id), SoundEvent::m_262824_);
    }

    private static String prefix(String soundType, String soundName) {
        return soundType + "." + soundName;
    }
}

