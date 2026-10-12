package nadiendev.ultimatearmoury.datagen;

import nadiendev.ultimatearmoury.UltimateArmouryCore;
import nadiendev.ultimatearmoury.datagen.providers.ExDeorumCompatProvider;
import nadiendev.ultimatearmoury.datagen.providers.ModAdvancementProvider;
import nadiendev.ultimatearmoury.datagen.providers.ModBlockTags;
import nadiendev.ultimatearmoury.datagen.providers.ModItemTags;
import nadiendev.ultimatearmoury.datagen.providers.ModLootModifierProvider;
import nadiendev.ultimatearmoury.datagen.providers.ModLootTableProvider;
import nadiendev.ultimatearmoury.datagen.providers.ModModelProvider;
import nadiendev.ultimatearmoury.datagen.providers.ModRecipeProvider;
import nadiendev.ultimatearmoury.datagen.providers.ModWorldGenProvider;
import nadiendev.ultimatearmoury.datagen.providers.ThaumaturgeCompatProvider;
import net.minecraft.data.advancements.AdvancementProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.List;

public class DataGenerators {

    private DataGenerators() {
    }

    public static void gatherClientData(GatherDataEvent.Client event) {
        event.createProvider(ModModelProvider::new);
    }

    public static void gatherServerData(GatherDataEvent.Server event) {
        UltimateArmouryCore.LOGGER.info("=== INICIANDO GENERACION DE DATOS ===");

        event.createProvider(ModRecipeProvider.Runner::new);
        event.createProvider(ModWorldGenProvider::new);
        event.createProvider(ModLootTableProvider::new);
        event.createProvider((output, lookupProvider) ->
                new AdvancementProvider(output, lookupProvider, List.of(new ModAdvancementProvider())));
        event.createProvider(ModLootModifierProvider::new);
        event.createProvider(ExDeorumCompatProvider::new);
        event.createProvider(ThaumaturgeCompatProvider::new);
        event.createProvider(ModBlockTags.Provider::new);
        event.createProvider(ModItemTags.Provider::new);

        UltimateArmouryCore.LOGGER.info("=== TODOS LOS PROVIDERS REGISTRADOS CORRECTAMENTE ===");
    }
}
