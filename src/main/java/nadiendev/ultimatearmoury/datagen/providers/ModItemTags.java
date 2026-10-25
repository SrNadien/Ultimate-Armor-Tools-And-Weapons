package nadiendev.ultimatearmoury.datagen.providers;

import nadiendev.ultimatearmoury.UltimateArmouryCore;
import nadiendev.ultimatearmoury.registry.BlocksAdds;

import nadiendev.ultimatearmoury.registry.ModOreBlocks;
import nadiendev.ultimatearmoury.registry.ItemsAdds;
import nadiendev.ultimatearmoury.registry.HammerAdds;
import nadiendev.ultimatearmoury.registry.MeshAdds;
import nadiendev.ultimatearmoury.registry.ArmorAdds;
import nadiendev.ultimatearmoury.registry.ToolsAdds;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ItemTagsProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagEntry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public class ModItemTags {

    public static final List<String> COMPAT_ITEMS = List.of(
            "exdeorum:iron_hammer",
            "exdeorum:netherite_hammer",
            "exdeorum:iron_mesh",
            "exdeorum:netherite_mesh");

    private ModItemTags() {
    }

    public static class Items {

        private Items() {
        }

        public static final TagKey<Item> INGOTS_MUSHASHITE = commonTag("ingots/mushashite");
        public static final TagKey<Item> INGOTS_JOANFOITE = commonTag("ingots/joanfoite");
        public static final TagKey<Item> INGOTS_NADIENITE = commonTag("ingots/nadienite");
        public static final TagKey<Item> INGOTS_STEEL = commonTag("ingots/steel");

        public static final TagKey<Item> NUGGETS_MUSHASHITE = commonTag("nuggets/mushashite");
        public static final TagKey<Item> NUGGETS_JOANFOITE = commonTag("nuggets/joanfoite");
        public static final TagKey<Item> NUGGETS_NADIENITE = commonTag("nuggets/nadienite");

        public static final TagKey<Item> STORAGE_BLOCKS_MUSHASHITE = commonTag("storage_blocks/mushashite");
        public static final TagKey<Item> STORAGE_BLOCKS_JOANFOITE = commonTag("storage_blocks/joanfoite");
        public static final TagKey<Item> STORAGE_BLOCKS_NADIENITE = commonTag("storage_blocks/nadienite");
        public static final TagKey<Item> STORAGE_BLOCKS_STEEL = commonTag("storage_blocks/steel");

        public static final TagKey<Item> HAMMERS =
                ItemTags.create(Identifier.fromNamespaceAndPath("exdeorum", "hammers"));

        public static final TagKey<Item> COMPRESSED_HAMMERS =
                ItemTags.create(Identifier.fromNamespaceAndPath("exdeorum", "compressed_hammers"));

        public static final TagKey<Item> HAMMER_FORTUNE_BLACKLIST =
                ItemTags.create(Identifier.fromNamespaceAndPath("exdeorum", "hammer_fortune_blacklist"));

        public static final TagKey<Item> COMPRESSED_HAMMER_FORTUNE_BLACKLIST =
                ItemTags.create(Identifier.fromNamespaceAndPath("exdeorum", "compressed_hammer_fortune_blacklist"));

        public static final TagKey<Item> SIEVE_MESHES =
                ItemTags.create(Identifier.fromNamespaceAndPath("exdeorum", "sieve_meshes"));

        public static final TagKey<Item> ORES_MUSHASHITE = commonTag("ores/mushashite");
        public static final TagKey<Item> ORES_JOANFOITE = commonTag("ores/joanfoite");
        public static final TagKey<Item> ORES_NADIENITE = commonTag("ores/nadienite");

        public static final TagKey<Item> AUTOSMELT_ENCHANTABLE_MINING   = tag("enchantable/autosmelt_mining");
        public static final TagKey<Item> AUTOSMELT_ENCHANTABLE_AXE      = tag("enchantable/autosmelt_axe");
        public static final TagKey<Item> AUTOSMELT_ENCHANTABLE_ALL      = tag("enchantable/autosmelt_all");
        public static final TagKey<Item> AUTOSMELT_ENCHANTABLE_AXES     = tag("enchantable/autosmelt_axes");
        public static final TagKey<Item> AUTOSMELT_ENCHANTABLE_SHOVELS  = tag("enchantable/autosmelt_shovels");
        public static final TagKey<Item> AUTOSMELT_ENCHANTABLE_PICKAXES = tag("enchantable/autosmelt_pickaxes");
        public static final TagKey<Item> AUTOSMELT_ENCHANTABLE          = tag("enchantable/autosmelt");

        public static final TagKey<Item> XPBOOST_ENCHANTABLE_SWORD      = tag("enchantable/xpboost_sword");
        public static final TagKey<Item> XPBOOST_ENCHANTABLE_SWORDS     = tag("enchantable/xpboost_swords");
        public static final TagKey<Item> XPBOOST_ENCHANTABLE_AXES       = tag("enchantable/xpboost_axes");
        public static final TagKey<Item> XPBOOST_ENCHANTABLE            = tag("enchantable/xpboost");

        private static TagKey<Item> commonTag(String name) {
            return ItemTags.create(Identifier.fromNamespaceAndPath("c", name));
        }

        public static TagKey<Item> compat(String id) {
            return tag("compat/" + id.replace(':', '_'));
        }

        private static TagKey<Item> tag(String name) {
            return ItemTags.create(Identifier.fromNamespaceAndPath(UltimateArmouryCore.MOD_ID, name));
        }
    }

    public static class Provider extends ItemTagsProvider {

        public Provider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
            super(output, lookupProvider, UltimateArmouryCore.MOD_ID);
        }

        @Override
        protected void addTags(HolderLookup.Provider provider) {
            this.tag(Items.INGOTS_MUSHASHITE).add(ItemsAdds.MUSHASHITE_INGOT.get());
            this.tag(Items.INGOTS_JOANFOITE).add(ItemsAdds.JOANFOITE_INGOT.get());
            this.tag(Items.INGOTS_NADIENITE).add(ItemsAdds.NADIENITE_INGOT.get());
            this.tag(Items.INGOTS_STEEL).add(ItemsAdds.STEEL_INGOT.get());

            for (String mesh : MeshAdds.MESH_NAMES) {
                this.tag(Items.SIEVE_MESHES)
                    .add(TagEntry.optionalElement(Identifier.fromNamespaceAndPath(UltimateArmouryCore.MOD_ID, mesh)));
            }

            for (String compat : COMPAT_ITEMS) {
                this.tag(Items.compat(compat)).add(TagEntry.optionalElement(Identifier.parse(compat)));
            }

            for (HammerAdds.Hammer hammer : Stream.concat(
                    HammerAdds.HAMMERS.stream(), HammerAdds.THAUMATURGE_HAMMERS.stream()).toList()) {
                this.tag(Items.HAMMERS).add(TagEntry.optionalElement(
                        Identifier.fromNamespaceAndPath(UltimateArmouryCore.MOD_ID,
                                HammerAdds.name(hammer.material(), false))));
                this.tag(Items.COMPRESSED_HAMMERS).add(TagEntry.optionalElement(
                        Identifier.fromNamespaceAndPath(UltimateArmouryCore.MOD_ID,
                                HammerAdds.name(hammer.material(), true))));
                this.tag(Items.HAMMER_FORTUNE_BLACKLIST).add(TagEntry.optionalElement(
                        Identifier.fromNamespaceAndPath(UltimateArmouryCore.MOD_ID,
                                HammerAdds.name(hammer.material(), false))));
                this.tag(Items.COMPRESSED_HAMMER_FORTUNE_BLACKLIST).add(TagEntry.optionalElement(
                        Identifier.fromNamespaceAndPath(UltimateArmouryCore.MOD_ID,
                                HammerAdds.name(hammer.material(), true))));
            }

            this.tag(Items.NUGGETS_MUSHASHITE).add(ItemsAdds.MUSHASHITE_NUGGET.get());
            this.tag(Items.NUGGETS_JOANFOITE).add(ItemsAdds.JOANFOITE_NUGGET.get());
            this.tag(Items.NUGGETS_NADIENITE).add(ItemsAdds.NADIENITE_NUGGET.get());

            this.tag(Items.STORAGE_BLOCKS_MUSHASHITE).add(BlocksAdds.MUSHASHITE_BLOCK.get().asItem());
            this.tag(Items.STORAGE_BLOCKS_JOANFOITE).add(BlocksAdds.JOANFOITE_BLOCK.get().asItem());
            this.tag(Items.STORAGE_BLOCKS_NADIENITE).add(BlocksAdds.NADIENITE_BLOCK.get().asItem());
            this.tag(Items.STORAGE_BLOCKS_STEEL).add(BlocksAdds.STEEL_BLOCK.get().asItem());

            this.tag(Items.ORES_MUSHASHITE)
                .add(ModOreBlocks.MUSHASHITE_ORE.get().asItem())
                .add(ModOreBlocks.DEEPSLATE_MUSHASHITE_ORE.get().asItem());

            this.tag(Items.ORES_JOANFOITE)
                .add(ModOreBlocks.JOANFOITE_ORE.get().asItem())
                .add(ModOreBlocks.DEEPSLATE_JOANFOITE_ORE.get().asItem());

            this.tag(Items.ORES_NADIENITE)
                .add(ModOreBlocks.NADIENITE_ORE.get().asItem())
                .add(ModOreBlocks.DEEPSLATE_NADIENITE_ORE.get().asItem());

            this.tag(ItemTags.SWORDS)
                .add(ToolsAdds.MUSHASHITE_SWORD.get())
                .add(ToolsAdds.JOANFOITE_SWORD.get())
                .add(ToolsAdds.NADIENITE_SWORD.get());

            this.tag(Items.AUTOSMELT_ENCHANTABLE_MINING)   .addTag(ItemTags.MINING_ENCHANTABLE);
            this.tag(Items.AUTOSMELT_ENCHANTABLE_AXE)      .addTag(ItemTags.AXES);

            this.tag(Items.AUTOSMELT_ENCHANTABLE_AXES)     .addTag(ItemTags.AXES);
            this.tag(Items.AUTOSMELT_ENCHANTABLE_SHOVELS)  .addTag(ItemTags.SHOVELS);
            this.tag(Items.AUTOSMELT_ENCHANTABLE_PICKAXES) .addTag(ItemTags.PICKAXES);

            this.tag(Items.AUTOSMELT_ENCHANTABLE)
                .addTag(Items.AUTOSMELT_ENCHANTABLE_MINING)
                .addTag(Items.AUTOSMELT_ENCHANTABLE_AXE)
                .addTag(Items.AUTOSMELT_ENCHANTABLE_AXES)
                .addTag(Items.AUTOSMELT_ENCHANTABLE_SHOVELS)
                .addTag(Items.AUTOSMELT_ENCHANTABLE_PICKAXES);

            this.tag(Items.XPBOOST_ENCHANTABLE_SWORD)
                .addTag(ItemTags.SHARP_WEAPON_ENCHANTABLE);
            this.tag(Items.XPBOOST_ENCHANTABLE_SWORDS)
                .addTag(ItemTags.SWORDS);
            this.tag(Items.XPBOOST_ENCHANTABLE_AXES)
                .addTag(ItemTags.AXES)
                .add(ToolsAdds.MUSHASHITE_SWORD.get())
                .add(ToolsAdds.JOANFOITE_SWORD.get())
                .add(ToolsAdds.NADIENITE_SWORD.get());

            this.tag(Items.XPBOOST_ENCHANTABLE)
                .addTag(Items.XPBOOST_ENCHANTABLE_SWORD)
                .addTag(Items.XPBOOST_ENCHANTABLE_SWORDS)
                .addTag(Items.XPBOOST_ENCHANTABLE_AXES);

            this.tag(ItemTags.PICKAXES)
                .add(ToolsAdds.MUSHASHITE_PICKAXE.get())
                .add(ToolsAdds.JOANFOITE_PICKAXE.get())
                .add(ToolsAdds.NADIENITE_PICKAXE.get());

            this.tag(ItemTags.AXES)
                .add(ToolsAdds.MUSHASHITE_AXE.get())
                .add(ToolsAdds.JOANFOITE_AXE.get())
                .add(ToolsAdds.NADIENITE_AXE.get());

            this.tag(ItemTags.SHOVELS)
                .add(ToolsAdds.MUSHASHITE_SHOVEL.get())
                .add(ToolsAdds.JOANFOITE_SHOVEL.get())
                .add(ToolsAdds.NADIENITE_SHOVEL.get());

            this.tag(ItemTags.HOES)
                .add(ToolsAdds.MUSHASHITE_HOE.get())
                .add(ToolsAdds.JOANFOITE_HOE.get())
                .add(ToolsAdds.NADIENITE_HOE.get());

            this.tag(ItemTags.HEAD_ARMOR)
                .add(ArmorAdds.MUSHASHITE_GORRO.get())
                .add(ArmorAdds.JOANFOITE_GORRO.get())
                .add(ArmorAdds.NADIENITE_GORRO.get());

            this.tag(ItemTags.CHEST_ARMOR)
                .add(ArmorAdds.MUSHASHITE_REMERA.get())
                .add(ArmorAdds.JOANFOITE_REMERA.get())
                .add(ArmorAdds.NADIENITE_REMERA.get());

            this.tag(ItemTags.LEG_ARMOR)
                .add(ArmorAdds.MUSHASHITE_GAYUMBOS.get())
                .add(ArmorAdds.JOANFOITE_GAYUMBOS.get())
                .add(ArmorAdds.NADIENITE_GAYUMBOS.get());

            this.tag(ItemTags.FOOT_ARMOR)
                .add(ArmorAdds.MUSHASHITE_MEDIAS.get())
                .add(ArmorAdds.JOANFOITE_MEDIAS.get())
                .add(ArmorAdds.NADIENITE_MEDIAS.get());

            this.tag(ItemTags.TRIMMABLE_ARMOR)
                .add(ArmorAdds.MUSHASHITE_GORRO.get())
                .add(ArmorAdds.MUSHASHITE_REMERA.get())
                .add(ArmorAdds.MUSHASHITE_GAYUMBOS.get())
                .add(ArmorAdds.MUSHASHITE_MEDIAS.get())
                .add(ArmorAdds.JOANFOITE_GORRO.get())
                .add(ArmorAdds.JOANFOITE_REMERA.get())
                .add(ArmorAdds.JOANFOITE_GAYUMBOS.get())
                .add(ArmorAdds.JOANFOITE_MEDIAS.get())
                .add(ArmorAdds.NADIENITE_GORRO.get())
                .add(ArmorAdds.NADIENITE_REMERA.get())
                .add(ArmorAdds.NADIENITE_GAYUMBOS.get())
                .add(ArmorAdds.NADIENITE_MEDIAS.get());
        }
    }
}
