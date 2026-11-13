/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType
 */
package com.eternal_creations.archies_dino_mod.common.structure.piece;

import com.eternal_creations.archies_dino_mod.common.structure.piece.CustomStructurePiece;
import com.eternal_creations.archies_dino_mod.platform.Services;
import com.eternal_creations.archies_dino_mod.util.ResourceSupplier;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;

public class ADStructurePieces {
    public static final ResourceSupplier<StructurePieceType> CUSTOM_STRUCTURE_PIECE = Services.COMMON.registerStructurePieceType("custom_structure_piece", CustomStructurePiece::new);

    public static void init() {
    }
}

