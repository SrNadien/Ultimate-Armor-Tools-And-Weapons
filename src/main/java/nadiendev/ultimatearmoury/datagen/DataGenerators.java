package nadiendev.ultimatearmoury.datagen;

import nadiendev.ultimatearmoury.UltimateArmouryCore;
import nadiendev.ultimatearmoury.datagen.providers.BlockModelProvider;
import nadiendev.ultimatearmoury.datagen.providers.ExDeorumCompatProvider;
import nadiendev.ultimatearmoury.datagen.providers.ItemsModelProvider;
import nadiendev.ultimatearmoury.datagen.providers.ModAdvancementProvider;
import nadiendev.ultimatearmoury.datagen.providers.ModLootModifierProvider;
import nadiendev.ultimatearmoury.datagen.providers.ModLootTableProvider;
import nadiendev.ultimatearmoury.datagen.providers.ModRecipeProvider;
import nadiendev.ultimatearmoury.datagen.providers.ModWorldGenProvider;
import nadiendev.ultimatearmoury.datagen.providers.ModBlockTags;
import nadiendev.ultimatearmoury.datagen.providers.ModItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class DataGenerators {

    private DataGenerators() {
    }

    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        existingFileHelper.trackGenerated(
                ResourceLocation.fromNamespaceAndPath(UltimateArmouryCore.MOD_ID, "block/nadienite_fluid"),
                PackType.CLIENT_RESOURCES, ".png", "textures"
        );
        existingFileHelper.trackGenerated(
                ResourceLocation.fromNamespaceAndPath(UltimateArmouryCore.MOD_ID, "block/nadienite_flow"),
                PackType.CLIENT_RESOURCES, ".png", "textures"
        );

        UltimateArmouryCore.LOGGER.info("=== INICIANDO GENERACION DE DATOS ===");

        generator.addProvider(event.includeServer(), new ModRecipeProvider(output, lookupProvider));
        generator.addProvider(event.includeServer(), new ModWorldGenProvider(output, lookupProvider));
        generator.addProvider(event.includeServer(), new ModLootTableProvider(output, lookupProvider));
        generator.addProvider(event.includeServer(),
                new AdvancementProvider(output, lookupProvider, existingFileHelper,
                        List.of(new ModAdvancementProvider())));
        generator.addProvider(event.includeServer(), new ModLootModifierProvider(output, lookupProvider));
        generator.addProvider(event.includeServer(), new ExDeorumCompatProvider(output));

        ModBlockTags.Provider blockTagsProvider = generator.addProvider(
                event.includeServer(),
                new ModBlockTags.Provider(output, lookupProvider, existingFileHelper)
        );
        generator.addProvider(
                event.includeServer(),
                new ModItemTags.Provider(output, lookupProvider, blockTagsProvider.contentsGetter(), existingFileHelper)
        );

        generator.addProvider(event.includeClient(), new BlockModelProvider(output, existingFileHelper));
        generator.addProvider(event.includeClient(), new ItemsModelProvider(output, existingFileHelper));

        UltimateArmouryCore.LOGGER.info("=== TODOS LOS PROVIDERS REGISTRADOS CORRECTAMENTE ===");
    }
}
