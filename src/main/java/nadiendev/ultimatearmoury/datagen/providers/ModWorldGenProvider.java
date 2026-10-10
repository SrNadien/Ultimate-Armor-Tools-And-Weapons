package nadiendev.ultimatearmoury.datagen.providers;

import nadiendev.ultimatearmoury.UltimateArmouryCore;
import nadiendev.ultimatearmoury.registry.ModEnchantments;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ModWorldGenProvider extends DatapackBuiltinEntriesProvider {

    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, ModOreGeneration::bootstrapConfigured)
            .add(Registries.PLACED_FEATURE, ModOreGeneration::bootstrapPlaced)
            .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ModOreGeneration::bootstrapBiomeModifiers)
            .add(Registries.ENCHANTMENT, ModWorldGenProvider::bootstrapEnchantments);

    public ModWorldGenProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(UltimateArmouryCore.MOD_ID));
    }

    private static void bootstrapEnchantments(BootstrapContext<Enchantment> context) {
        HolderGetter<Enchantment> enchantments = context.lookup(Registries.ENCHANTMENT);
        HolderGetter<Item> items = context.lookup(Registries.ITEM);

        context.register(ModEnchantments.AUTOSMELT, new Enchantment(
                Component.translatable("enchantment.ultimatearmoury.autosmelt"),
                new Enchantment.EnchantmentDefinition(
                        items.getOrThrow(ModItemTags.Items.AUTOSMELT_ENCHANTABLE),
                        Optional.empty(),
                        2,
                        1,
                        new Enchantment.Cost(15, 0),
                        new Enchantment.Cost(60, 0),
                        4,
                        List.of(EquipmentSlotGroup.MAINHAND)
                ),
                HolderSet.direct(enchantments.getOrThrow(Enchantments.SILK_TOUCH)),
                DataComponentMap.EMPTY
        ));

        context.register(ModEnchantments.XPBOOST, new Enchantment(
                Component.translatable("enchantment.ultimatearmoury.xpboost"),
                new Enchantment.EnchantmentDefinition(
                        items.getOrThrow(ModItemTags.Items.XPBOOST_ENCHANTABLE),
                        Optional.empty(),
                        5,
                        3,
                        new Enchantment.Cost(10, 10),
                        new Enchantment.Cost(40, 10),
                        2,
                        List.of(EquipmentSlotGroup.MAINHAND, EquipmentSlotGroup.OFFHAND)
                ),
                HolderSet.direct(enchantments.getOrThrow(Enchantments.SILK_TOUCH)),
                DataComponentMap.EMPTY
        ));
    }
}
