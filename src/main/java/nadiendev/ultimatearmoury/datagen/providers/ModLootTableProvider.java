package nadiendev.ultimatearmoury.datagen.providers;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ModLootTableProvider extends LootTableProvider {

    public ModLootTableProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, Set.of(), List.of(
                new SubProviderEntry(UltimateArmouryBlockLootTables::new, LootContextParamSets.BLOCK),
                new SubProviderEntry(UltimateArmouryMobLootTables::new,  LootContextParamSets.ENTITY),
                new SubProviderEntry(UltimateArmouryChestLootTables::new, LootContextParamSets.CHEST)
        ), registries);
    }
}
