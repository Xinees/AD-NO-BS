/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceLocation
 */
package com.eternal_creations.archies_dino_mod.networking.clientbound;

import com.eternal_creations.archies_dino_mod.Constants;
import com.eternal_creations.archies_dino_mod.client.screen.ScorchingHornScreen;
import com.eternal_creations.archies_dino_mod.common.entity.ADPetData;
import com.eternal_creations.archies_dino_mod.networking.clientbound.AbstractClientboundMessage;
import java.util.List;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public final class ScorchingHornScreenMessage
extends AbstractClientboundMessage {
    public static final ResourceLocation ID = Constants.prefix("scorching_horn_screen");
    private final List<ADPetData> petDataList;

    public ScorchingHornScreenMessage(List<ADPetData> petDataList) {
        this.petDataList = petDataList;
    }

    public static ScorchingHornScreenMessage decode(FriendlyByteBuf buf) {
        List pets = buf.m_236845_(ADPetData::read);
        return new ScorchingHornScreenMessage(pets);
    }

    @Override
    public ResourceLocation getID() {
        return ID;
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.m_236828_(this.petDataList, (b, pet) -> pet.write((FriendlyByteBuf)b));
    }

    @Override
    public void execute() {
        Handler.handle(this);
    }

    private static class Handler {
        private Handler() {
        }

        private static void handle(ScorchingHornScreenMessage message) {
            Minecraft mc = Minecraft.m_91087_();
            mc.execute(() -> {
                if (mc.f_91074_ != null) {
                    mc.m_91152_((Screen)new ScorchingHornScreen(message.petDataList));
                }
            });
        }
    }
}

