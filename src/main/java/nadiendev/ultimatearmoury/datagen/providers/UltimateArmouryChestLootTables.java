package nadiendev.ultimatearmoury.datagen.providers;

import nadiendev.ultimatearmoury.registry.ItemsAdds;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.function.BiConsumer;

public class UltimateArmouryChestLootTables implements LootTableSubProvider {

    private final HolderLookup.Provider registries;

    public UltimateArmouryChestLootTables(HolderLookup.Provider registries) {
        this.registries = registries;
    }

    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> output) {

        output.accept(
            ResourceKey.create(
                net.minecraft.core.registries.Registries.LOOT_TABLE,
                Identifier.fromNamespaceAndPath("ultimatearmoury", "chests/nadienite_dungeon_bonus")
            ),
            LootTable.lootTable()
                .withPool(LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1))

                    .when(LootItemRandomChanceCondition.randomChance(0.15f))
                    .add(LootItem.lootTableItem(ItemsAdds.NADIENITE_INGOT.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3)))
                    )
                )
        );

        output.accept(
            ResourceKey.create(
                net.minecraft.core.registries.Registries.LOOT_TABLE,
                Identifier.fromNamespaceAndPath("ultimatearmoury", "chests/nadienite_nether_bonus")
            ),
            LootTable.lootTable()
                .withPool(LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1))
                    .when(LootItemRandomChanceCondition.randomChance(0.15f))
                    .add(LootItem.lootTableItem(ItemsAdds.NADIENITE_INGOT.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3)))
                    )
                )
        );
    }
}
