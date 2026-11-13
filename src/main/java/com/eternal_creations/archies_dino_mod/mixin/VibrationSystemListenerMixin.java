/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.gameevent.GameEvent
 *  net.minecraft.world.level.gameevent.GameEvent$Context
 *  net.minecraft.world.level.gameevent.vibrations.VibrationSystem$Listener
 *  net.minecraft.world.phys.Vec3
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package com.eternal_creations.archies_dino_mod.mixin;

import com.eternal_creations.archies_dino_mod.common.items.armor.SculksaurArmorItem;
import com.eternal_creations.archies_dino_mod.common.items.util.ADArmorItem;
import java.util.Map;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.vibrations.VibrationSystem;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@Mixin(value={VibrationSystem.Listener.class})
public class VibrationSystemListenerMixin {
    @Inject(method={"handleGameEvent"}, at={@At(value="HEAD")}, cancellable=true)
    private void onHandleGameEvent(ServerLevel level, GameEvent gameEvent, GameEvent.Context context, Vec3 pos, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity living;
        Map<EquipmentSlot, ItemStack> armorSet;
        Entity entity = context.f_223711_();
        if (entity instanceof LivingEntity && (armorSet = ADArmorItem.getArmorSet(living = (LivingEntity)entity)).size() == 4 && armorSet.get(EquipmentSlot.HEAD).m_41720_() instanceof SculksaurArmorItem) {
            cir.setReturnValue((Object)false);
        }
    }
}

