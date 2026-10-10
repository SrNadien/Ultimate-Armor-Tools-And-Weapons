package nadiendev.ultimatearmoury.datagen.providers;

import nadiendev.ultimatearmoury.UltimateArmouryCore;
import nadiendev.ultimatearmoury.registry.ModOreBlocks;
import nadiendev.ultimatearmoury.items.ModTier;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.HolderSet;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

public class ModOreGeneration {

    private record OreSettings(int veinSize, int countPerChunk, int minY, int maxY) {
    }

    private static OreSettings settingsFor(ModTier tier) {
        return switch (tier) {
            case MUSHASHITE -> new OreSettings(9, 8, -48, 48);
            case JOANFOITE -> new OreSettings(6, 5, -64, 16);
            case NADIENITE -> new OreSettings(4, 3, -64, -8);
        };
    }

    public static void bootstrapConfigured(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        RuleTest stoneReplaceables = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceables = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);

        for (ModTier tier : ModTier.values()) {
            List<OreConfiguration.TargetBlockState> targets = List.of(
                    OreConfiguration.target(stoneReplaceables,
                            ModOreBlocks.stoneOreOf(tier).get().defaultBlockState()),
                    OreConfiguration.target(deepslateReplaceables,
                            ModOreBlocks.deepslateOreOf(tier).get().defaultBlockState())
            );

            context.register(
                    configuredKey(tier),
                    new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(targets, settingsFor(tier).veinSize()))
            );
        }
    }

    public static void bootstrapBiomeModifiers(BootstrapContext<BiomeModifier> context) {
        HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
        HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);

        for (ModTier tier : ModTier.values()) {
            context.register(
                    ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS,
                            ResourceLocation.fromNamespaceAndPath(UltimateArmouryCore.MOD_ID, "add_" + tier.id() + "_ore")),
                    new BiomeModifiers.AddFeaturesBiomeModifier(
                            biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                            HolderSet.direct(placedFeatures.getOrThrow(placedKey(tier))),
                            GenerationStep.Decoration.UNDERGROUND_ORES));
        }
    }

    public static void bootstrapPlaced(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        for (ModTier tier : ModTier.values()) {
            OreSettings settings = settingsFor(tier);
            context.register(
                    placedKey(tier),
                    new PlacedFeature(
                            configuredFeatures.getOrThrow(configuredKey(tier)),
                            List.of(
                                    HeightRangePlacement.triangle(
                                            VerticalAnchor.absolute(settings.minY()),
                                            VerticalAnchor.absolute(settings.maxY())
                                    ),
                                    CountPlacement.of(settings.countPerChunk()),
                                    InSquarePlacement.spread(),
                                    BiomeFilter.biome()
                            )
                    )
            );
        }
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> configuredKey(ModTier tier) {
        return ResourceKey.create(
                Registries.CONFIGURED_FEATURE,
                ResourceLocation.fromNamespaceAndPath(UltimateArmouryCore.MOD_ID, tier.id() + "_ore")
        );
    }

    public static ResourceKey<PlacedFeature> placedKey(ModTier tier) {
        return ResourceKey.create(
                Registries.PLACED_FEATURE,
                ResourceLocation.fromNamespaceAndPath(UltimateArmouryCore.MOD_ID, tier.id() + "_ore")
        );
    }

    private ModOreGeneration() {
    }
}
