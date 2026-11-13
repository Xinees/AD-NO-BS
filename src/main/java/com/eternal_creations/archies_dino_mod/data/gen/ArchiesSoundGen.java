/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.data.PackOutput
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraftforge.common.data.ExistingFileHelper
 */
package com.eternal_creations.archies_dino_mod.data.gen;

import com.eternal_creations.archies_dino_mod.common.sound.ADSoundEvents;
import com.eternal_creations.archies_dino_mod.data.util.ArchiesSoundProvider;
import java.util.function.Supplier;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.data.PackOutput;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.common.data.ExistingFileHelper;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ArchiesSoundGen
extends ArchiesSoundProvider {
    public ArchiesSoundGen(PackOutput output, ExistingFileHelper helper) {
        super(output, helper);
    }

    public void registerSounds() {
        this.generateWithExistingSound(ADSoundEvents.DINO_EQUIP, SoundEvents.f_11973_, "Dino equips");
        this.generateWithExistingSound(ADSoundEvents.DINO_EGG_PICKUP, SoundEvents.f_12019_, "Dino Egg plops");
        this.generateWithExistingSound(ADSoundEvents.DINO_EGG_BREAK, SoundEvents.f_12533_, "Dino Egg breaks");
        this.generateWithExistingSound(ADSoundEvents.DINO_EGG_CRACK, SoundEvents.f_12534_, "Dino Egg cracks");
        this.generateWithExistingSound(ADSoundEvents.DINO_EGG_HATCH, SoundEvents.f_12535_, "Dino Egg hatches");
        this.generateWithExistingSound(ADSoundEvents.DINO_EGG_LAY, SoundEvents.f_12486_, "Dino lays Egg");
        this.registerLushsaur();
        this.registerChorusoatl();
        this.registerObsidiceratops();
        this.registerSculkraptor();
        this.registerSculksaur();
        this.registerTherimoss();
        this.registerCoralosaur();
        this.registerItems();
        this.registerArchie();
    }

    private void registerLushsaur() {
        this.generateNewSoundWithSubtitle(ADSoundEvents.LUSHSAUR_IDLE, 5, "Lushsaur Grumbles");
        this.generateNewSoundWithSubtitle(ADSoundEvents.LUSHSAUR_ATTACK_HEADBUTT, 1, "Lushsaur Attacks");
        this.generateNewSoundWithSubtitle(ADSoundEvents.LUSHSAUR_ATTACK_BITE, 1, "Lushsaur Attacks");
        this.generateNewSoundWithSubtitle(ADSoundEvents.LUSHSAUR_ATTACK_TAIL_STOMP, 1, "Lushsaur Attacks");
        this.generateNewSoundWithSubtitle(ADSoundEvents.LUSHSAUR_ATTACK_TAIL_SWING, 1, "Lushsaur Attacks");
        this.generateNewSoundWithSubtitle(ADSoundEvents.LUSHSAUR_RUN_ATTACK, 1, "Lushsaur Charges");
        this.generateNewSoundWithSubtitle(ADSoundEvents.LUSHSAUR_EMERGE, 1, "Lushsaur Emerges");
        this.generateNewSoundWithSubtitle(ADSoundEvents.LUSHSAUR_BURY, 1, "Lushsaur Hides");
        this.generateNewSoundWithSubtitle(ADSoundEvents.LUSHSAUR_REST, 1, "Lushsaur Sleeps");
        this.generateNewSoundWithSubtitle(ADSoundEvents.LUSHSAUR_ROAR, 1, "Lushsaur Roars");
        this.generateNewSoundWithSubtitle(ADSoundEvents.LUSHSAUR_EAR_RINGING, 1, "Your Ears Ring");
        this.generateNewSoundWithSubtitle(ADSoundEvents.LUSHSAUR_EAT, 1, "Lushsaur Eats");
        this.generateNewSoundWithSubtitle(ADSoundEvents.LUSHSAUR_HURT, 3, "Lushsaur Hurts");
        this.generateNewSoundWithSubtitle(ADSoundEvents.LUSHSAUR_DEATH, 1, "Lushsaur Dies");
        this.makeNewStepSound(ADSoundEvents.LUSHSAUR_STEP, "mob/lushsaur/step", 4);
        this.generateWithExistingSound(ADSoundEvents.LUSHSAUR_SHEAR, SoundEvents.f_12344_, "Lushsaur is sheared");
        this.generateWithExistingSound(ADSoundEvents.LUSHSAUR_STOMP_EXPLOSION, SoundEvents.f_11913_, "Lushsaur Stomps");
    }

    private void registerChorusoatl() {
        this.generateNewSoundWithSubtitle(ADSoundEvents.CHORUSOATL_IDLE, 3, "Chorusoatl Grumbles");
        this.generateNewSoundWithSubtitle(ADSoundEvents.CHORUSOATL_BITE, 1, "Chorusoatl Attacks");
        this.generateNewSoundWithSubtitle(ADSoundEvents.CHORUSOATL_HURT, 3, "Chorusoatl Hurts");
        this.generateNewSoundWithSubtitle(ADSoundEvents.CHORUSOATL_SPIT, 1, "Chorusoatl Spits");
        this.generateNewSoundWithSubtitle(ADSoundEvents.CHORUSOATL_SCREAM, 1, "Chorusoatl Screams");
        this.generateNewSoundWithSubtitle(ADSoundEvents.CHORUSOATL_UP, 1, "Chorusoatl Emerges");
        this.generateNewSoundWithSubtitle(ADSoundEvents.CHORUSOATL_GLIDE, 2, "Chorusoatl Glides");
        this.generateNewSoundWithSubtitle(ADSoundEvents.CHORUSOATL_FLAP_UP, 2, "Chorusoatl Flaps Wings");
        this.generateNewSoundWithSubtitle(ADSoundEvents.CHORUSOATL_FLAP_DOWN, 2, "Chorusoatl Flaps Wings");
        this.generateNewSoundWithSubtitle(ADSoundEvents.CHORUSOATL_LAND, 1, "Chorusoatl Lands");
        this.generateNewSoundWithSubtitle(ADSoundEvents.CHORUSOATL_STOMP, 1, "Chorusoatl Stomps");
        this.generateNewSoundWithSubtitle(ADSoundEvents.CHORUSOATL_TAME, 2, "Chorusoatl Purrs");
        this.generateNewSoundWithSubtitle(ADSoundEvents.CHORUSOATL_DEATH_AIR, 1, "Chorusoatl Dies");
        this.generateNewSoundWithSubtitle(ADSoundEvents.CHORUSOATL_DEATH_GROUND, 1, "Chorusoatl Dies");
        this.generateNewSoundWithSubtitle(ADSoundEvents.CHORUSOATL_DEATH_AIR_LAND, 1, "Chorusoatl Lands");
        this.generateNewSoundWithSubtitle(ADSoundEvents.CHORUSOATL_DEATH_FALLING, 1, "Chorusoatl Falls");
        this.makeNewStepSound(ADSoundEvents.CHORUSOATL_STEP_HEAVY, "mob/chorusoatl/step_heavy", 2);
        this.makeNewStepSound(ADSoundEvents.CHORUSOATL_STEP_LIGHT, "mob/chorusoatl/step_light", 2);
    }

    private void registerObsidiceratops() {
        this.generateNewSoundWithSubtitle(ADSoundEvents.OBSIDICERATOPS_AGRO, 1, "Obsidiceratops Roars");
        this.generateNewSoundWithSubtitle(ADSoundEvents.OBSIDICERATOPS_ATTACK, 2, "Obsidiceratops Attacks");
        this.generateNewSoundWithSubtitle(ADSoundEvents.OBSIDICERATOPS_CHARGE_AGRO, 1, "Obsidiceratops Charges");
        this.generateNewSoundWithSubtitle(ADSoundEvents.OBSIDICERATOPS_CHARGE_ATTACK, 1, "Obsidiceratops Rams");
        this.generateNewSoundWithSubtitle(ADSoundEvents.OBSIDICERATOPS_DEATH, 1, "Obsidiceratops Dies");
        this.generateNewSoundWithSubtitle(ADSoundEvents.OBSIDICERATOPS_DRINK, 2, "Obsidiceratops Drinks");
        this.generateNewSoundWithSubtitle(ADSoundEvents.OBSIDICERATOPS_EAT, 1, "Obsidiceratops Eats");
        this.generateNewSoundWithSubtitle(ADSoundEvents.OBSIDICERATOPS_HURT, 3, "Obsidiceratops Hurts");
        this.generateNewSoundWithSubtitle(ADSoundEvents.OBSIDICERATOPS_IDLE, 4, "Obsidiceratops Grunts");
        this.generateNewSoundWithSubtitle(ADSoundEvents.OBSIDICERATOPS_RUN, 1, "Obsidiceratops Runs");
        this.generateNewSoundWithSubtitle(ADSoundEvents.OBSIDICERATOPS_WALK, 1, "Obsidiceratops Walks");
        this.makeNewStepSound(ADSoundEvents.OBSIDICERATOPS_STEP, "mob/obsidiceratops/step", 4);
        this.generateWithExistingSound(ADSoundEvents.OBSIDICERATOPS_FEED_LAVA, SoundEvents.f_11833_, "Obsidiceratops gets fed lava");
        this.generateWithExistingSound(ADSoundEvents.OBSIDICERATOPS_HARVEST_LAVA, SoundEvents.f_11833_, "Obsidiceratops's lava gets harvested");
        this.generateWithExistingSound(ADSoundEvents.OBSIDICERATOPS_EGG_SCORCH, SoundEvents.f_11874_, "Obsidiceratops egg scorches");
    }

    private void registerSculkraptor() {
        this.generateNewSoundWithSubtitle(ADSoundEvents.SCULKRAPTOR_IDLE, 3, "Sculkraptor Snarls");
        this.generateNewSoundWithSubtitle(ADSoundEvents.SCULKRAPTOR_ATTACK, 1, "Sculkraptor Attacks");
        this.generateNewSoundWithSubtitle(ADSoundEvents.SCULKRAPTOR_RANGED, 2, "Sculkraptor Shoots");
        this.generateNewSoundWithSubtitle(ADSoundEvents.SCULKRAPTOR_EXPLOSION, 1, "Sculkraptor Explodes");
        this.generateNewSoundWithSubtitle(ADSoundEvents.SCULKRAPTOR_SNIFF, 1, "Sculkraptor Sniffs");
        this.generateNewSoundWithSubtitle(ADSoundEvents.SCULKRAPTOR_HURT, 3, "Sculkraptor Hurts");
        this.generateNewSoundWithSubtitle(ADSoundEvents.SCULKRAPTOR_DEATH, 1, "Sculkraptor Dies");
        this.generateNewSoundWithSubtitle(ADSoundEvents.SCULKRAPTOR_SCULK_PROJECTILE, 1, "Sculkraptor Spits");
        this.generateNewSoundWithSubtitle(ADSoundEvents.SCULKRAPTOR_SCULK_TRACKER, 2, "Sculk Tracker Resonates");
        this.generateWithExistingSound(ADSoundEvents.SCULKRAPTOR_TENDRIL_CLICKS, SoundEvents.f_215774_, "Sculkraptor's tendrils click");
        this.generateWithExistingSound(ADSoundEvents.SCULKRAPTOR_NEST_EGG, SoundEvents.f_11752_, "Sculkraptor Nest gets harvested");
        this.makeNewStepSound(ADSoundEvents.SCULKRAPTOR_STEP, "mob/sculkraptor/step", 3);
    }

    private void registerSculksaur() {
        this.generateNewSoundWithSubtitle(ADSoundEvents.SCULKSAUR_IDLE, 3, "Sculksaur Snarls");
        this.generateNewSoundWithSubtitle(ADSoundEvents.SCULKSAUR_ATTACK, 1, "Sculksaur Attacks");
        this.generateNewSoundWithSubtitle(ADSoundEvents.SCULKSAUR_ATTACK_TAIL, 1, "Sculksaur Swings Its Tail");
        this.generateNewSoundWithSubtitle(ADSoundEvents.SCULKSAUR_BITE, 1, "Sculksaur Bites");
        this.generateNewSoundWithSubtitle(ADSoundEvents.SCULKSAUR_EXPLOSION, 1, "Sculksaur Explodes");
        this.generateNewSoundWithSubtitle(ADSoundEvents.SCULKSAUR_ROAR, 1, "Sculksaur Roars");
        this.generateNewSoundWithSubtitle(ADSoundEvents.SCULKSAUR_SNIFF, 1, "Sculksaur Sniffs");
        this.generateNewSoundWithSubtitle(ADSoundEvents.SCULKSAUR_SPAWN, 1, "Sculksaur Spawns");
        this.generateNewSoundWithSubtitle(ADSoundEvents.SCULKSAUR_HURT, 2, "Sculksaur Hurts");
        this.generateNewSoundWithSubtitle(ADSoundEvents.SCULKSAUR_DEATH, 1, "Sculksaur Dies", 4.0f, 1.0f);
        this.generateWithExistingSound(ADSoundEvents.SCULKSAUR_WART_POP, SoundEvents.f_12389_, "Sculksaur wart pops");
        this.generateWithExistingSound(ADSoundEvents.SCULKSAUR_TENDRIL_CLICKS, SoundEvents.f_215774_, "Sculksaur's tendrils click");
        this.makeNewStepSound(ADSoundEvents.SCULKSAUR_STEP, "mob/sculksaur/step", 4);
    }

    private void registerTherimoss() {
        this.generateNewSoundWithSubtitle(ADSoundEvents.THERIMOSS_IDLE, 4, "Therimoss Grumbles");
        this.generateNewSoundWithSubtitle(ADSoundEvents.THERIMOSS_HURT, 2, "Therimoss Hurts");
        this.generateNewSoundWithSubtitle(ADSoundEvents.THERIMOSS_DEATH, 1, "Therimoss Dies");
        this.generateNewSoundWithSubtitle(ADSoundEvents.THERIMOSS_ATTACK_BEAK, 1, "Therimoss Stomps");
        this.generateNewSoundWithSubtitle(ADSoundEvents.THERIMOSS_ATTACK_MELEE, 1, "Therimoss Attacks");
        this.generateNewSoundWithSubtitle(ADSoundEvents.THERIMOSS_ATTACK_THROW, 1, "Therimoss Throws Dirt");
        this.generateNewSoundWithSubtitle(ADSoundEvents.THERIMOSS_FALL, 1, "Therimoss Falls");
        this.generateNewSoundWithSubtitle(ADSoundEvents.THERIMOSS_LAND, 1, "Therimoss Lands");
        this.generateNewSoundWithSubtitle(ADSoundEvents.THERIMOSS_LAND_BIGGER, 1, "Therimoss Lands");
        this.generateNewSoundWithSubtitle(ADSoundEvents.THERIMOSS_CLIMB_UP, 1, "Therimoss Climbs Up");
        this.generateNewSoundWithSubtitle(ADSoundEvents.THERIMOSS_CLIMB_DOWN, 1, "Therimoss Climbs Down");
        this.generateNewSoundWithSubtitle(ADSoundEvents.THERIMOSS_EAT, 1, "Therimoss Eats");
        this.makeNewStepSound(ADSoundEvents.THERIMOSS_STEP, "mob/therimoss/step", 4);
        this.generateWithExistingSound(ADSoundEvents.THERIMOSS_BLOCK_THROW, SoundEvents.f_11993_, "Block gets thrown");
        this.generateWithExistingSound(ADSoundEvents.THERIMOSS_BLOCK_LAND, SoundEvents.f_11993_, "Block collides");
    }

    private void registerCoralosaur() {
        this.generateNewSoundWithSubtitle(ADSoundEvents.CORALOSAUR_IDLE, 3, "Coralosaur Snarls");
        this.generateNewSoundWithSubtitle(ADSoundEvents.CORALOSAUR_SLEEP, 1, "Coralosaur Sleeps");
        this.generateNewSoundWithSubtitle(ADSoundEvents.CORALOSAUR_BITE, 1, "Coralosaur Bites");
        this.generateNewSoundWithSubtitle(ADSoundEvents.CORALOSAUR_DEATH, 1, "Coralosaur Dies");
        this.generateNewSoundWithSubtitle(ADSoundEvents.CORALOSAUR_HURT, 3, "Coralosaur Hurts");
        this.generateNewSoundWithSubtitle(ADSoundEvents.CORALOSAUR_BURY, 1, "Coralosaur Buries");
        this.generateNewSoundWithSubtitle(ADSoundEvents.CORALOSAUR_WAKE, 1, "Coralosaur Awakens");
    }

    private void registerItems() {
        this.generateWithExistingSound(ADSoundEvents.MACE_ATTACK, SoundEvents.f_12317_, "Mace Attacks");
        this.generateNewSoundWithSubtitle(ADSoundEvents.MACE_SPIN, 1, "Mace Spins");
        this.generateNewSoundWithSubtitle(ADSoundEvents.SCYTHE_ATTACK, 2, "Scythe Attacks");
        this.generateNewSoundWithSubtitle(ADSoundEvents.SCYTHE_SPIN, 1, "Scythe Spins");
        this.generateNewSoundWithSubtitle(ADSoundEvents.SCORCHING_HORN, 1, "Scorching Horn Blows");
        this.generateNewSoundWithSubtitle(ADSoundEvents.THERIMOSS_CLAWS_ATTACK, 1, "Claws Attack");
        this.generateWithExistingSound(ADSoundEvents.ARMOR_EQUIP_LUSHSAUR, SoundEvents.f_11672_, "Lushsaur armor jingles");
        this.generateWithExistingSound(ADSoundEvents.ARMOR_EQUIP_THERIMOSS, SoundEvents.f_11678_, "Therimoss armor rustles");
        this.generateWithExistingSound(ADSoundEvents.ARMOR_EQUIP_CORALOSAUR, SoundEvents.f_11678_, "Coralosaur armor rustles");
        this.generateWithExistingSound(ADSoundEvents.ARMOR_EQUIP_OBSIDICERATOPS, SoundEvents.f_11672_, "Obsidiceratops armor jingles");
        this.generateWithExistingSound(ADSoundEvents.ARMOR_EQUIP_CHORUSOATL, SoundEvents.f_11672_, "Chorusoatl armor jingles");
        this.generateWithExistingSound(ADSoundEvents.CHORUSOATL_ARMOR_ABILITY, SoundEvents.f_11852_, "Chorusoatl armor teleports");
        this.generateWithExistingSound(ADSoundEvents.ARMOR_EQUIP_SCULKSAUR, SoundEvents.f_11678_, "Sculksaur armor rustles");
        this.generateNewSoundWithSubtitle(ADSoundEvents.SCULKSAUR_ARMOR_ABILITY, 1, "Sculksaur armor explodes");
    }

    private void registerArchie() {
        this.generateSimpleSounds(1, ADSoundEvents.ARCHIE_ABOVEGROUND_1, ADSoundEvents.ARCHIE_ABOVEGROUND_2, ADSoundEvents.ARCHIE_ACCEPT, ADSoundEvents.ARCHIE_CHORUSOATL_1, ADSoundEvents.ARCHIE_CHORUSOATL_2, ADSoundEvents.ARCHIE_CORALOSAUR_1, ADSoundEvents.ARCHIE_CORALOSAUR_2, ADSoundEvents.ARCHIE_DENY, ADSoundEvents.ARCHIE_DIMENSIONS_1, ADSoundEvents.ARCHIE_DIMENSIONS_2, ADSoundEvents.ARCHIE_FRIEND, ADSoundEvents.ARCHIE_INTRO, ADSoundEvents.ARCHIE_LUSHSAUR_1, ADSoundEvents.ARCHIE_LUSHSAUR_2, ADSoundEvents.ARCHIE_OBSIDICERATOPS_1, ADSoundEvents.ARCHIE_OBSIDICERATOPS_2, ADSoundEvents.ARCHIE_SCULK_RAPTOR_1, ADSoundEvents.ARCHIE_SCULK_RAPTOR_2, ADSoundEvents.ARCHIE_SCULKSAUR_1, ADSoundEvents.ARCHIE_SCULKSAUR_2, ADSoundEvents.ARCHIE_THERIMOSS_1, ADSoundEvents.ARCHIE_THERIMOSS_2, ADSoundEvents.ARCHIE_UNDERGRONUD_1, ADSoundEvents.ARCHIE_UNDERGRONUD_2);
    }

    @SafeVarargs
    private void generateSimpleSounds(int variations, Supplier<SoundEvent> ... sounds) {
        for (Supplier<SoundEvent> sound : sounds) {
            this.generateNewSound(sound, variations);
        }
    }
}

