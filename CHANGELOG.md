# Changelog

## [2.0.12] — 2026-10-25 — Minecraft 26.1.2

### ✨ Added
- All UltimateArmoury hammers and compressed hammers are now in Ex Deorum's `hammer_fortune_blacklist` and `compressed_hammer_fortune_blacklist` item tags.

## [2.0.11] — 2026-10-12 — Minecraft 26.1.2

### ✨ Added
- Thaumaturge integration, optional. Three new tiers of Ex Deorum meshes, hammers and compressed hammers made from Thaumaturge's metals, one step above the unobtainium ones: alchemical brass, thaumium and void metal.
- The new meshes sift Thaumaturge's amber (from gravel and sand) and raw cinnabar (from crushed deepslate and crushed netherrack) in the sieve and the compressed sieve. The chance is 30% with the brass mesh, 65% with the thaumium mesh and 100% with the void metal mesh.
- The brass mesh and hammer and the three compressed hammers are made at the Thaumaturge arcane workbench for 100 vis and 64 crystals of each primal. The thaumium and void metal meshes and hammers are made in the infusion altar.
- Blocks of alchemical brass, thaumium and void metal heat an Ex Deorum crucible, with heat 5, 6 and 7.
- An UltimateArmoury category in the Thaumonomicon, with a page for the meshes, one for the hammers and one for the heaters.
- A `thaumaturge` option in the config file, under `integraciones`, to turn all of this off.

## [2.0.10] — 2026-10-10 — Minecraft 26.1.2

First release as a standalone mod. UltimateArmoury was split out of Ultimate Foods: the foods stayed in Ultimate Foods and the utility items and music discs moved to Ultimate Tweaks.

Requires **Java 25** and NeoForge `26.1.2.100`.

### ⚠️ Breaking
- The mod id is now `ultimatearmoury`; it was `ultimatefoods`. Items and blocks from worlds made with Ultimate Foods 2.0.9 or earlier do not carry over.
- The config file is now `ultimatearmoury-common.toml`.

### ✨ Added
- A distinct 3D helmet per tier, rendered through GeckoLib: a crown for Nadienite, a detective fedora for Mushashite and a pointed wizard hat for JoanFoite.
- Inventory icons for the twelve armor pieces.
- A mod logo.

### ⚔️ Changed
- Only the equipment and the materials remain here. Foods, utility items, music discs and sounds are gone from this mod.
- New armor layer textures, one theme per tier: Roman king, spy and mage.
- New item textures for the swords, axes, pickaxes, shovels, hoes, hammers and ingots, and deepslate ores redrawn to match the vanilla silhouette.
- The Heart of the Elite drop from the Warden is now a global loot modifier, so it no longer replaces the vanilla Warden loot table.
- AppleSkin is no longer required.

### 🐛 Fixes
- The Nadienite ingot model pointed at `green_ingot`, a texture that no longer exists, so the item rendered as a missing model.
