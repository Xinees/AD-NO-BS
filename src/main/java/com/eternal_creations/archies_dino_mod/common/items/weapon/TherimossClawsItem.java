/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.astemir.astemirlib.re.common.action.ActionState
 *  com.astemir.astemirlib.re.common.action.ActionType
 *  com.astemir.astemirlib.re.common.action.PlayerActions
 *  com.astemir.astemirlib.re.common.world.item.ItemCombatAction
 *  com.astemir.astemirlib.re.common.world.item.ItemCombatAction$Handler
 *  javax.annotation.Nullable
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.level.Level
 */
package com.eternal_creations.archies_dino_mod.common.items.weapon;

import com.astemir.astemirlib.re.common.action.ActionState;
import com.astemir.astemirlib.re.common.action.ActionType;
import com.astemir.astemirlib.re.common.action.PlayerActions;
import com.astemir.astemirlib.re.common.world.item.ItemCombatAction;
import com.eternal_creations.archies_dino_mod.Constants;
import com.eternal_creations.archies_dino_mod.common.ADStrings;
import com.eternal_creations.archies_dino_mod.common.ADTags;
import com.eternal_creations.archies_dino_mod.common.items.util.ADWeaponItem;
import com.eternal_creations.archies_dino_mod.common.sound.ADSoundEvents;
import com.eternal_creations.archies_dino_mod.util.ClientUtil;
import java.util.List;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class TherimossClawsItem
extends ADWeaponItem
implements ItemCombatAction.Handler {
    public static final ActionType ATTACK = ActionType.register((ResourceLocation)Constants.prefix("therimoss_claws.attack"));
    public static final ADWeaponItem.WeaponStats GOLD_STATS = new ADWeaponItem.WeaponStats(3, -2.4f, 156, false);
    public static final ADWeaponItem.WeaponStats COPPER_STATS = new ADWeaponItem.WeaponStats(4, -2.4f, 247, false);
    public static final ADWeaponItem.WeaponStats IRON_STATS = new ADWeaponItem.WeaponStats(5, -2.4f, 325, false);
    public static final ADWeaponItem.WeaponStats DIAMOND_STATS = new ADWeaponItem.WeaponStats(6, -2.4f, 2029, false);
    public static final ADWeaponItem.WeaponStats NETHERITE_STATS = new ADWeaponItem.WeaponStats(7, -2.4f, 2640, false);
    private final ItemCombatAction combatAction = ItemCombatAction.of().simpleAttackAction(ATTACK).canAttackCheckActions(new ActionType[]{ATTACK}).disableBlockDigging().shouldContinueAttack().build();

    public TherimossClawsItem(Item.Properties properties, Supplier<ADWeaponItem.WeaponStats> weaponStatsSupplier) {
        super(properties, weaponStatsSupplier);
    }

    @Nullable
    public ItemCombatAction getCombatAction(ItemStack itemStack) {
        return !itemStack.m_204117_(ADTags.Items.TWO_HANDED) || this.getWeaponStats().disableFancyAttacks() ? null : this.combatAction;
    }

    public void m_7373_(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        super.m_7373_(stack, level, tooltipComponents, isAdvanced);
        ClientUtil.addMultilineTooltip(ADStrings.Description.THERI_CLAWS, tooltipComponents, new Object[0]);
    }

    static {
        PlayerActions.INSTANCE.registerActionState(player -> ActionState.of((ActionType)ATTACK, (float)1.25f, (float)0.2f, (float)0.2f).exitProgress(0.95f).onTick(tick -> {
            if (tick == 8 || tick == 15) {
                player.m_9236_().m_214150_(player, player.m_20185_(), player.m_20186_(), player.m_20189_(), ADSoundEvents.THERIMOSS_CLAWS_ATTACK.get(), SoundSource.PLAYERS, 1.0f, 1.0f, player.m_9236_().f_46441_.m_188505_());
                ItemCombatAction.Handler.simulateAttack((LivingEntity)player, (double)2.5, (double)0.5);
            }
        }).requireHolding((LivingEntity)player, itemStack -> itemStack.m_41720_() instanceof TherimossClawsItem));
    }
}

