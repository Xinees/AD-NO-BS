# Legacy recovery status

Last updated: 2026-11-13
Working branch: `binary-prune/legacy`
Latest successful structural-prune commit: `dd30b20744c4561884bff5d339a8e0adffd09840`
Successful CI run: `43438805978`
Artifact ID: `14045021165`

## Target

Produce a Forge 1.20.1 legacy rebuild from the untouched original Archie's Dinosaurs JAR, retaining only:

- Mobs: Lushsaur, Chorusoatl, Sculksaur
- Items: Lush Core, Chorus Fossil, Sculk Toxin
- Their required eggs/spawn eggs, models, animations, sounds, AI, particles, client rendering, and mount inventory support

Do not modify either input JAR. Work only on this branch and generated build outputs.

## Immutable build inputs

- Original mod: `ec_ad-forge-1.20.1-1.0.4.jar`
  - SHA-256: `8df20e08d46f778a6e975b60b0cc7e19ff674264834812015537d7ef0fb121b1`
- Astemir RE: `astemirlib_re-1.20.1-forge-1.2.98-SNAPSHOT.jar`
  - SHA-256: `906198b220958f352c308ec9a2178342b7af90198ade206d1596eb336a89a0a3`

## Verified so far

- CI verifies both input JARs exist and checksums them before pruning.
- The direct ASM bytecode-pruning task compiles and runs.
- The resource-pruning script completes.
- The generated JAR passes ZIP integrity and structural content checks.
- Latest result: 237 class files, 486 JAR entries, 2,502,526 bytes.
- 338 classes and 838 resource entries were removed.
- Excluded entity packages for Archie, Coralosaur, Obsidiceratops, Sculkraptor, and Therimoss are absent from the filtered result.
- The three retained entity packages and their principal AI classes are present.
- Latest structural artifact: workflow run `43438805978`, artifact `14045021165`.

## Important remaining work

1. Inspect the Astemir RE JAR's own `mods.toml`, manifest, module identity, and any nested dependencies.
2. Embed the exact Astemir RE input into the output with Forge Jar-in-Jar metadata, without modifying its bytes.
3. Update the parent mod metadata so the final dependency behavior is correct for an embedded Astemir RE copy. Do not remove the dependency declaration until Forge's nested-mod loading behavior has been verified.
4. Re-run structural checks, verify the embedded library checksum, and inspect the final mod's mixin/resource configuration.
5. Run a real Forge 1.20.1 client/server smoke test. A structurally valid ZIP is not proof the mod launches or that entity behavior is correct.

## Current technical caveat

The successful 2.50 MB JAR is a structural-pruning result, **not a finished standalone release**. Its current `mods.toml` still declares Astemir RE as a mandatory external dependency. The successful artifact also contains MixinExtras in Jar-in-Jar metadata, but does not yet embed the supplied Astemir RE JAR.

## Continuation instructions

Continue from the latest commit on `binary-prune/legacy`. Do not return to the stale `main` branch, do not overwrite the original input JARs, and do not describe the current JAR as runtime-tested.
