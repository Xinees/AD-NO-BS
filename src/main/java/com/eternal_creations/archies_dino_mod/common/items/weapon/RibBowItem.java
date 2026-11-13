/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.astemir.astemirlib.re.common.action.ActionState
 *  com.astemir.astemirlib.re.common.action.ActionType
 *  com.astemir.astemirlib.re.common.action.PlayerActions
 *  com.astemir.astemirlib.re.common.action.SyncedActionHolder
 *  javax.annotation.Nullable
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.entity.projectile.AbstractArrow
 *  net.minecraft.world.item.ArrowItem
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.item.UseAnim
 *  net.minecraft.world.level.Level
 */
package com.eternal_creations.archies_dino_mod.common.items.weapon;

import com.astemir.astemirlib.re.common.action.ActionState;
import com.astemir.astemirlib.re.common.action.ActionType;
import com.astemir.astemirlib.re.common.action.PlayerActions;
import com.astemir.astemirlib.re.common.action.SyncedActionHolder;
import com.eternal_creations.archies_dino_mod.Constants;
import com.eternal_creations.archies_dino_mod.common.ADStrings;
import com.eternal_creations.archies_dino_mod.common.items.util.ADBowItem;
import com.eternal_creations.archies_dino_mod.util.ClientUtil;
import com.eternal_creations.archies_dino_mod.util.IModifiedArrow;
import java.util.List;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class RibBowItem
extends ADBowItem {
    public static final ActionType RELEASE = ActionType.register((ResourceLocation)Constants.prefix("rib_bow.release"));
    public static final ADBowItem.BowStats DEFAULT_STATS = new ADBowItem.BowStats(1, 2048);

    public RibBowItem(Item.Properties properties, Supplier<ADBowItem.BowStats> bowStats) {
        super(properties, bowStats);
        PlayerActions.INSTANCE.registerActionState(player -> ActionState.of((ActionType)RELEASE, (float)0.28f, (float)0.1f, (float)0.2f).exitProgress(0.9f).condition(state -> {
            ItemStack held = player.m_21120_(state.getHand());
            return held.m_150930_((Item)this);
        }));
    }

    @Override
    public void m_5551_(ItemStack stack, Level level, LivingEntity entityLiving, int timeLeft) {
        InteractionHand hand = entityLiving.m_7655_();
        super.m_5551_(stack, level, entityLiving, timeLeft);
        if (entityLiving instanceof SyncedActionHolder) {
            SyncedActionHolder holder = (SyncedActionHolder)entityLiving;
            holder.playActionToServer(RELEASE, hand);
        }
    }

    @Override
    protected AbstractArrow createArrowFromItem(ArrowItem item, Player player, ItemStack stack) {
        AbstractArrow arrow = super.createArrowFromItem(item, player, stack);
        if (arrow instanceof IModifiedArrow) {
            IModifiedArrow m = (IModifiedArrow)arrow;
            m.archiesDinoMod$setShotFromRibBow(true);
        }
        return arrow;
    }

    public void m_7373_(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        super.m_7373_(stack, level, tooltipComponents, isAdvanced);
        ClientUtil.addMultilineTooltip(ADStrings.Description.RIB_BOW, tooltipComponents, new Object[0]);
    }

    public UseAnim m_6164_(ItemStack stack) {
        return UseAnim.NONE;
    }
}

