# Archie's Dinosaurs — Forge 1.20.1 recovery

This branch is the clean recovery workspace for the original Forge 1.20.1 project.

## Toolchain
- Minecraft 1.20.1
- Forge 47.4.28
- Java 17
- ForgeGradle 6.x
- Astemir Re Lib 1.2.98-SNAPSHOT (local dependency; kept unchanged)

## Recovery source
The initial Java source snapshot is reconstructed from `ec_ad-forge-1.20.1-1.0.4.jar`.
Original resource files are extracted from that same JAR. Decompiled code is a recovery
baseline and will be audited before cleanup or release.

## Current scope
The intended retained creatures are:
- Sculksaur
- Chorusoatl
- Lushsaur

Behavior is not being redesigned in this first pass. The priority is to retain the
original models, textures, animations, sounds, and rendering integration while the
project structure and automated build are restored.

The first content pass is limited to the three retained creatures and non-combat support
needed by them. Unselected creatures and unrelated content are not part of this branch.

## Build
The GitHub Actions workflow restores sources when they are absent and then runs
`gradle clean build` on Java 17.
