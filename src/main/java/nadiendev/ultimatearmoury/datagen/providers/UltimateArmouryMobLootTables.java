package nadiendev.ultimatearmoury.datagen.providers;

import nadiendev.ultimatearmoury.registry.ItemsAdds;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.EnchantedCountIncreaseFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithEnchantedBonusCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.stream.Stream;

public class UltimateArmouryMobLootTables extends EntityLootSubProvider {

    public UltimateArmouryMobLootTables(HolderLookup.Provider registries) {
        super(FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    public void generate() {
        add(EntityType.ZOMBIE, heartTable(ItemsAdds.CORAZON_DE_LOS_CAIDOS.get(), 0.03f));
        add(EntityType.SKELETON, heartTable(ItemsAdds.CORAZON_DE_LOS_CAIDOS.get(), 0.03f));
        add(EntityType.WITHER_SKELETON, heartTable(ItemsAdds.CORAZON_DE_LOS_CAIDOS.get(), 0.25f));

        add(EntityType.PHANTOM, heartTable(ItemsAdds.CORAZON_DE_LOS_FANTASMAS.get(), 0.15f));
        add(EntityType.GHAST, heartTable(ItemsAdds.CORAZON_DE_LOS_FANTASMAS.get(), 0.25f));
        add(EntityType.VEX, heartTable(ItemsAdds.CORAZON_DE_LOS_FANTASMAS.get(), 0.20f));

        add(EntityType.ELDER_GUARDIAN, heartTable(ItemsAdds.CORAZON_DE_LA_ELITE.get(), 0.50f));
        add(EntityType.WITHER, guaranteedTable(ItemsAdds.CORAZON_DE_LA_ELITE.get(), 1));
        add(EntityType.ENDER_DRAGON, guaranteedTable(ItemsAdds.CORAZON_DE_LA_ELITE.get(), 2));
    }

    private LootTable.Builder heartTable(ItemLike heart, float chance) {
        return LootTable.lootTable().withPool(chancePool(heart, 1, chance));
    }

    private LootTable.Builder guaranteedTable(ItemLike item, int count) {
        return LootTable.lootTable().withPool(guaranteedPool(item, count));
    }

    private LootPool.Builder chancePool(ItemLike item, int count, float chance) {
        return LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(item)
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(count)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(
                                registries, UniformGenerator.between(0, 1))))
                .when(LootItemRandomChanceWithEnchantedBonusCondition.randomChanceAndLootingBoost(
                        registries, chance, 0.01f));
    }

    private LootPool.Builder guaranteedPool(ItemLike item, int count) {
        return LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(item)
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(count)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(
                                registries, UniformGenerator.between(0, 1))));
    }

    @Override
    protected Stream<EntityType<?>> getKnownEntityTypes() {
        return Stream.of(
                EntityType.ZOMBIE,
                EntityType.SKELETON,
                EntityType.WITHER_SKELETON,
                EntityType.PHANTOM,
                EntityType.GHAST,
                EntityType.VEX,
                EntityType.ELDER_GUARDIAN,
                EntityType.WITHER,
                EntityType.ENDER_DRAGON
        );
    }
}
