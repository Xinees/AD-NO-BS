/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  javax.annotation.Nullable
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.core.UUIDUtil
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.phys.Vec3
 */
package com.eternal_creations.archies_dino_mod.networking.clientbound;

import com.eternal_creations.archies_dino_mod.Constants;
import com.eternal_creations.archies_dino_mod.networking.clientbound.AbstractClientboundMessage;
import com.eternal_creations.archies_dino_mod.util.ClientUtil;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public final class AskShooterIfHitBoneMessage
extends AbstractClientboundMessage {
    public static final ResourceLocation ID = Constants.prefix("check_bone_collision");
    private static final Codec<AskShooterIfHitBoneMessage> CODEC = RecordCodecBuilder.create(recordCodecBuilder -> recordCodecBuilder.group((App)Codec.INT.fieldOf("victim").forGetter(message -> message.victim), (App)UUIDUtil.f_235867_.fieldOf("owner").forGetter(message -> message.owner), (App)Vec3.f_231074_.fieldOf("arrow_pos").forGetter(message -> message.arrowPos), (App)Vec3.f_231074_.fieldOf("arrow_delta").forGetter(message -> message.arrowDelta), (App)Codec.STRING.optionalFieldOf("bone_name").forGetter(message -> Optional.ofNullable(message.boneName))).apply((Applicative)recordCodecBuilder, (victimID, owner, pos, delta, optionalBone) -> new AskShooterIfHitBoneMessage((int)victimID, (UUID)owner, (Vec3)pos, (Vec3)delta, optionalBone.orElse(null))));
    private final int victim;
    private final UUID owner;
    private final Vec3 arrowPos;
    private final Vec3 arrowDelta;
    @Nullable
    private final String boneName;

    public AskShooterIfHitBoneMessage(int victim, UUID owner, Vec3 arrowPos, Vec3 arrowDelta, @Nullable String boneName) {
        this.victim = victim;
        this.owner = owner;
        this.arrowPos = arrowPos;
        this.arrowDelta = arrowDelta;
        this.boneName = boneName;
    }

    public int getVictim() {
        return this.victim;
    }

    public UUID getOwner() {
        return this.owner;
    }

    @Nullable
    public String getBoneName() {
        return this.boneName;
    }

    public Vec3 getArrowDelta() {
        return this.arrowDelta;
    }

    public Vec3 getArrowPos() {
        return this.arrowPos;
    }

    public static AskShooterIfHitBoneMessage decode(FriendlyByteBuf buf) {
        return (AskShooterIfHitBoneMessage)buf.m_271872_(CODEC);
    }

    @Override
    public ResourceLocation getID() {
        return ID;
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.m_272073_(CODEC, (Object)this);
    }

    @Override
    public void execute() {
        ClientUtil.handleArrowHit(this);
    }
}

