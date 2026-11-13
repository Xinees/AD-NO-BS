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
    public static ResourceSupplier<SoundEvent> DINO_EGG_LAY = null;
    public static ResourceSupplier<SoundEvent> THERIMOSS_IDLE = null;
    public static ResourceSupplier<SoundEvent> THERIMOSS_HURT = null;
    public static ResourceSupplier<SoundEvent> THERIMOSS_DEATH = null;
    public static ResourceSupplier<SoundEvent> THERIMOSS_ATTACK_BEAK = null;
    public static ResourceSupplier<SoundEvent> THERIMOSS_ATTACK_MELEE = null;
    public static ResourceSupplier<SoundEvent> THERIMOSS_ATTACK_THROW = null;
    public static ResourceSupplier<SoundEvent> THERIMOSS_FALL = null;
    public static ResourceSupplier<SoundEvent> THERIMOSS_LAND = null;
    public static ResourceSupplier<SoundEvent> THERIMOSS_LAND_BIGGER = null;
    public static ResourceSupplier<SoundEvent> THERIMOSS_CLIMB_UP = null;
    public static ResourceSupplier<SoundEvent> THERIMOSS_CLIMB_DOWN = null;
    public static ResourceSupplier<SoundEvent> THERIMOSS_EAT = null;
    public static ResourceSupplier<SoundEvent> THERIMOSS_STEP = null;
    public static ResourceSupplier<SoundEvent> THERIMOSS_BLOCK_THROW = null;
    public static ResourceSupplier<SoundEvent> THERIMOSS_BLOCK_LAND = null;
    public static ResourceSupplier<SoundEvent> LUSHSAUR_IDLE = ADSoundEvents.registerMobSound("lushsaur.idle");
    public static ResourceSupplier<SoundEvent> LUSHSAUR_ATTACK_HEADBUTT = ADSoundEvents.registerMobSound("lushsaur.attack1");
    public static ResourceSupplier<SoundEvent> LUSHSAUR_ATTACK_BITE = ADSoundEvents.registerMobSound("lushsaur.attack2");
    public static ResourceSupplier<SoundEvent> LUSHSAUR_ATTACK_TAIL_STOMP = ADSoundEvents.registerMobSound("lushsaur.attack3");
    public static ResourceSupplier<SoundEvent> LUSHSAUR_ATTACK_TAIL_SWING = ADSoundEvents.registerMobSound("lushsaur.attack4");
    public static ResourceSupplier<SoundEvent> LUSHSAUR_EMERGE = ADSoundEvents.registerMobSound("lushsaur.up");
    public static ResourceSupplier<SoundEvent> LUSHSAUR_BURY = ADSoundEvents.registerMobSound("lushsaur.down");
    public static ResourceSupplier<SoundEvent> LUSHSAUR_EAR_RINGING = null;
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
    public static ResourceSupplier<SoundEvent> CHORUSOATL_DEATH_AIR = null;
    public static ResourceSupplier<SoundEvent> CHORUSOATL_DEATH_AIR_LAND = null;
    public static ResourceSupplier<SoundEvent> CHORUSOATL_DEATH_FALLING = null;
    public static ResourceSupplier<SoundEvent> CHORUSOATL_DEATH_GROUND = ADSoundEvents.registerMobSound("chorusoatl.death_ground");
    public static ResourceSupplier<SoundEvent> CHORUSOATL_FLAP_DOWN = ADSoundEvents.registerMobSound("chorusoatl.flap_down");
    public static ResourceSupplier<SoundEvent> CHORUSOATL_FLAP_UP = ADSoundEvents.registerMobSound("chorusoatl.flap_up");
    public static ResourceSupplier<SoundEvent> CHORUSOATL_GLIDE = ADSoundEvents.registerMobSound("chorusoatl.glide");
    public static ResourceSupplier<SoundEvent> CHORUSOATL_IDLE = ADSoundEvents.registerMobSound("chorusoatl.idle");
    public static ResourceSupplier<SoundEvent> CHORUSOATL_LAND = null;
    public static ResourceSupplier<SoundEvent> CHORUSOATL_SCREAM = ADSoundEvents.registerMobSound("chorusoatl.scream");
    public static ResourceSupplier<SoundEvent> CHORUSOATL_SPIT = ADSoundEvents.registerMobSound("chorusoatl.spit");
    public static ResourceSupplier<SoundEvent> CHORUSOATL_STEP_HEAVY = ADSoundEvents.registerMobSound("chorusoatl.step_heavy");
    public static ResourceSupplier<SoundEvent> CHORUSOATL_STEP_LIGHT = ADSoundEvents.registerMobSound("chorusoatl.step_light");
    public static ResourceSupplier<SoundEvent> CHORUSOATL_STOMP = null;
    public static ResourceSupplier<SoundEvent> CHORUSOATL_TAME = null;
    public static ResourceSupplier<SoundEvent> CHORUSOATL_UP = null;
    public static ResourceSupplier<SoundEvent> OBSIDICERATOPS_AGRO = null;
    public static ResourceSupplier<SoundEvent> OBSIDICERATOPS_ATTACK = null;
    public static ResourceSupplier<SoundEvent> OBSIDICERATOPS_CHARGE_AGRO = null;
    public static ResourceSupplier<SoundEvent> OBSIDICERATOPS_CHARGE_ATTACK = null;
    public static ResourceSupplier<SoundEvent> OBSIDICERATOPS_DEATH = null;
    public static ResourceSupplier<SoundEvent> OBSIDICERATOPS_DRINK = null;
    public static ResourceSupplier<SoundEvent> OBSIDICERATOPS_EAT = null;
    public static ResourceSupplier<SoundEvent> OBSIDICERATOPS_HURT = null;
    public static ResourceSupplier<SoundEvent> OBSIDICERATOPS_IDLE = null;
    public static ResourceSupplier<SoundEvent> OBSIDICERATOPS_RUN = null;
    public static ResourceSupplier<SoundEvent> OBSIDICERATOPS_STEP = null;
    public static ResourceSupplier<SoundEvent> OBSIDICERATOPS_WALK = null;
    public static ResourceSupplier<SoundEvent> OBSIDICERATOPS_FEED_LAVA = null;
    public static ResourceSupplier<SoundEvent> OBSIDICERATOPS_HARVEST_LAVA = null;
    public static ResourceSupplier<SoundEvent> OBSIDICERATOPS_EGG_SCORCH = null;
    public static ResourceSupplier<SoundEvent> MACE_ATTACK = ADSoundEvents.registerItemSound("lushsaur_mace.attack");
    public static ResourceSupplier<SoundEvent> MACE_SPIN = ADSoundEvents.registerItemSound("lushsaur_mace.spin");
    public static ResourceSupplier<SoundEvent> SCYTHE_ATTACK = null;
    public static ResourceSupplier<SoundEvent> SCYTHE_SPIN = null;
    public static ResourceSupplier<SoundEvent> SCORCHING_HORN = null;
    public static ResourceSupplier<SoundEvent> THERIMOSS_CLAWS_ATTACK = null;
    public static ResourceSupplier<SoundEvent> ARMOR_EQUIP_LUSHSAUR = null;
    public static ResourceSupplier<SoundEvent> ARMOR_EQUIP_THERIMOSS = null;
    public static ResourceSupplier<SoundEvent> ARMOR_EQUIP_CORALOSAUR = null;
    public static ResourceSupplier<SoundEvent> ARMOR_EQUIP_OBSIDICERATOPS = null;
    public static ResourceSupplier<SoundEvent> ARMOR_EQUIP_CHORUSOATL = null;
    public static ResourceSupplier<SoundEvent> CHORUSOATL_ARMOR_ABILITY = null;
    public static ResourceSupplier<SoundEvent> ARMOR_EQUIP_SCULKSAUR = null;
    public static ResourceSupplier<SoundEvent> SCULKSAUR_ARMOR_ABILITY = null;
    public static ResourceSupplier<SoundEvent> SCULKRAPTOR_IDLE = null;
    public static ResourceSupplier<SoundEvent> SCULKRAPTOR_ATTACK = null;
    public static ResourceSupplier<SoundEvent> SCULKRAPTOR_RANGED = null;
    public static ResourceSupplier<SoundEvent> SCULKRAPTOR_HURT = null;
    public static ResourceSupplier<SoundEvent> SCULKRAPTOR_DEATH = null;
    public static ResourceSupplier<SoundEvent> SCULKRAPTOR_EXPLOSION = null;
    public static ResourceSupplier<SoundEvent> SCULKRAPTOR_SNIFF = null;
    public static ResourceSupplier<SoundEvent> SCULKRAPTOR_STEP = null;
    public static ResourceSupplier<SoundEvent> SCULKRAPTOR_TENDRIL_CLICKS = null;
    public static ResourceSupplier<SoundEvent> SCULKRAPTOR_NEST_EGG = null;
    public static ResourceSupplier<SoundEvent> SCULKRAPTOR_SCULK_PROJECTILE = null;
    public static ResourceSupplier<SoundEvent> SCULKRAPTOR_SCULK_TRACKER = null;
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
    public static ResourceSupplier<SoundEvent> CORALOSAUR_IDLE = null;
    public static ResourceSupplier<SoundEvent> CORALOSAUR_SLEEP = null;
    public static ResourceSupplier<SoundEvent> CORALOSAUR_BITE = null;
    public static ResourceSupplier<SoundEvent> CORALOSAUR_DEATH = null;
    public static ResourceSupplier<SoundEvent> CORALOSAUR_HURT = null;
    public static ResourceSupplier<SoundEvent> CORALOSAUR_BURY = null;
    public static ResourceSupplier<SoundEvent> CORALOSAUR_WAKE = null;
    public static ResourceSupplier<SoundEvent> ARCHIE_ABOVEGROUND_1 = null;
    public static ResourceSupplier<SoundEvent> ARCHIE_ABOVEGROUND_2 = null;
    public static ResourceSupplier<SoundEvent> ARCHIE_ACCEPT = null;
    public static ResourceSupplier<SoundEvent> ARCHIE_CHORUSOATL_1 = null;
    public static ResourceSupplier<SoundEvent> ARCHIE_CHORUSOATL_2 = null;
    public static ResourceSupplier<SoundEvent> ARCHIE_CORALOSAUR_1 = null;
    public static ResourceSupplier<SoundEvent> ARCHIE_CORALOSAUR_2 = null;
    public static ResourceSupplier<SoundEvent> ARCHIE_DENY = null;
    public static ResourceSupplier<SoundEvent> ARCHIE_DIMENSIONS_1 = null;
    public static ResourceSupplier<SoundEvent> ARCHIE_DIMENSIONS_2 = null;
    public static ResourceSupplier<SoundEvent> ARCHIE_FRIEND = null;
    public static ResourceSupplier<SoundEvent> ARCHIE_INTRO = null;
    public static ResourceSupplier<SoundEvent> ARCHIE_LUSHSAUR_1 = null;
    public static ResourceSupplier<SoundEvent> ARCHIE_LUSHSAUR_2 = null;
    public static ResourceSupplier<SoundEvent> ARCHIE_OBSIDICERATOPS_1 = null;
    public static ResourceSupplier<SoundEvent> ARCHIE_OBSIDICERATOPS_2 = null;
    public static ResourceSupplier<SoundEvent> ARCHIE_SCULK_RAPTOR_1 = null;
    public static ResourceSupplier<SoundEvent> ARCHIE_SCULK_RAPTOR_2 = null;
    public static ResourceSupplier<SoundEvent> ARCHIE_SCULKSAUR_1 = null;
    public static ResourceSupplier<SoundEvent> ARCHIE_SCULKSAUR_2 = null;
    public static ResourceSupplier<SoundEvent> ARCHIE_THERIMOSS_1 = null;
    public static ResourceSupplier<SoundEvent> ARCHIE_THERIMOSS_2 = null;
    public static ResourceSupplier<SoundEvent> ARCHIE_UNDERGRONUD_1 = null;
    public static ResourceSupplier<SoundEvent> ARCHIE_UNDERGRONUD_2 = null;

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

