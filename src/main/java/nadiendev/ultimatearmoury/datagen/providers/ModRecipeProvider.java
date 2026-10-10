package nadiendev.ultimatearmoury.datagen.providers;

import nadiendev.ultimatearmoury.UltimateArmouryCore;
import nadiendev.ultimatearmoury.config.ConfigCondition;
import nadiendev.ultimatearmoury.registry.ItemsAdds;
import nadiendev.ultimatearmoury.registry.MeshAdds;
import nadiendev.ultimatearmoury.items.ModTier;
import nadiendev.ultimatearmoury.registry.BlocksAdds;
import nadiendev.ultimatearmoury.registry.CompressedBlocks;
import nadiendev.ultimatearmoury.registry.HammerAdds;
import nadiendev.ultimatearmoury.registry.ArmorAdds;
import nadiendev.ultimatearmoury.registry.ToolsAdds;
import nadiendev.ultimatearmoury.registry.ModOreBlocks;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.Identifier;
import net.minecraft.advancements.Criterion;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.common.conditions.ICondition;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {

    protected ModRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    public static class Runner extends RecipeProvider.Runner {

        public Runner(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
            super(output, registries);
        }

        @Override
        public String getName() {
            return "Recipes: " + UltimateArmouryCore.MOD_ID;
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
            return new ModRecipeProvider(registries, output);
        }
    }

    @Override
    protected void buildRecipes() {
        RecipeOutput writer = this.output;
        RecipeOutput recipeOutput = writer;

        ShapedRecipeBuilder.shaped(this.items, RecipeCategory.BUILDING_BLOCKS, BlocksAdds.NETHER_STAR_BLOCK.get(), 1)
                .pattern("aaa").pattern("aaa").pattern("aaa")
                .define('a', Items.NETHER_STAR)
                .unlockedBy("has_nether_star", has(Items.NETHER_STAR))
                .save(recipeOutput, rl("nether_star_block"));

        ShapelessRecipeBuilder.shapeless(this.items, RecipeCategory.MISC, Items.NETHER_STAR, 9)
               .requires(BlocksAdds.NETHER_STAR_BLOCK.get())
               .unlockedBy("has_nether_star_block", has(BlocksAdds.NETHER_STAR_BLOCK.get()))
               .save(recipeOutput, rl("nether_star_from_block"));

        buildTierChain(recipeOutput);
        buildMeshRecipes(recipeOutput);
        buildCompressedRecipes(recipeOutput);
        buildHammerRecipes(recipeOutput);

        ShapedRecipeBuilder.shaped(this.items, RecipeCategory.BUILDING_BLOCKS, BlocksAdds.ENDER_PEARL_BLOCK.get(), 1)
                .pattern("aaa").pattern("aaa").pattern("aaa")
                .define('a', Items.ENDER_PEARL)
                .unlockedBy("has_ender_pearl", has(Items.ENDER_PEARL))
                .save(recipeOutput, rl("ender_pearl_block"));

        ShapelessRecipeBuilder.shapeless(this.items, RecipeCategory.MISC, Items.ENDER_PEARL, 9)
                .requires(BlocksAdds.ENDER_PEARL_BLOCK.get())
                .unlockedBy("has_ender_pearl_block", has(BlocksAdds.ENDER_PEARL_BLOCK.get()))
                .save(recipeOutput, rl("ender_pearl_from_block"));

        ShapedRecipeBuilder.shaped(this.items, RecipeCategory.COMBAT, ToolsAdds.CHANCLA.get(), 1)
                .pattern(" C ").pattern(" N ").pattern(" S ")
                .define('N', ToolsAdds.NADIENITE_SWORD.get())
                .define('S', Items.STICK)
                .define('C', Items.NETHER_STAR)
                .unlockedBy("has_nadienite_ingot", has(ItemsAdds.NADIENITE_INGOT.get()))
                .save(recipeOutput, rl("chancleta"));

        ShapedRecipeBuilder.shaped(this.items, RecipeCategory.FOOD, ItemsAdds.STEEL_INGOT.get(), 1)
                .pattern("aa ").pattern("bb ").pattern("   ")
                .define('a', Items.IRON_INGOT).define('b', Items.COAL)
                .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
                .save(recipeOutput, rl("acero"));

        ShapedRecipeBuilder.shaped(this.items, RecipeCategory.FOOD, ItemsAdds.STEEL_INGOT.get(), 9)
                .pattern("a  ").pattern("   ").pattern("   ")
                .define('a', BlocksAdds.STEEL_BLOCK.get())
                .unlockedBy("has_steel_block", has(BlocksAdds.STEEL_BLOCK.get()))
                .save(recipeOutput, rl("acero_desde_bloque"));

        ShapedRecipeBuilder.shaped(this.items, RecipeCategory.BUILDING_BLOCKS, BlocksAdds.STEEL_BLOCK.get(), 1)
                .pattern("aaa").pattern("aaa").pattern("aaa")
                .define('a', ItemsAdds.STEEL_INGOT.get())
                .unlockedBy("has_steel_ingot", has(ItemsAdds.STEEL_INGOT.get()))
                .save(recipeOutput, rl("bloque_de_acero"));

    }

    private void buildHammerRecipes(RecipeOutput out) {
        for (HammerAdds.Hammer hammer : HammerAdds.HAMMERS) {
            RecipeOutput target = hammer.needsAllTheModium()
                    ? out.withConditions(
                            new ModLoadedCondition("exdeorum"),
                            new ConfigCondition(ConfigCondition.EX_DEORUM),
                            new ModLoadedCondition("allthemodium"),
                            new ConfigCondition(ConfigCondition.ALLTHEMODIUM))
                    : out.withConditions(
                            new ModLoadedCondition("exdeorum"),
                            new ConfigCondition(ConfigCondition.EX_DEORUM));

            TagKey<Item> ingots = ItemTags.create(Identifier.parse(hammer.ingotTag()));

            hammerRecipe(target, HammerAdds.name(hammer.material(), false), ingots,
                    hammer.previousMaterial(), hammer.heart());
            compressedHammerRecipe(target, hammer.material());
        }
    }

    private void hammerRecipe(RecipeOutput out, String name, TagKey<Item> ingots,
                              String previousMaterial, ModTier heart) {
        Item result = item(name);

        ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(this.items, RecipeCategory.TOOLS, result, 1)
                .define('s', Tags.Items.RODS_WOODEN)
                .define('m', ingots);

        if (HammerAdds.isExternal(previousMaterial)) {
            builder.define('p', ModItemTags.Items.compat(previousMaterial));
        } else {
            builder.define('p', item(HammerAdds.name(previousMaterial, false)));
        }

        if (heart == null) {
            builder.pattern(" m ");
        } else {
            builder.define('h', ItemsAdds.heartOf(heart).get());
            builder.pattern(" mh");
        }

        builder.pattern(" pm")
                .pattern("s  ")
                .unlockedBy("has_stick", has(Tags.Items.RODS_WOODEN))
                .save(out, rl(name));
    }

    private void compressedHammerRecipe(RecipeOutput out, String material) {
        Item base = item(HammerAdds.name(material, false));

        ShapedRecipeBuilder.shaped(this.items, RecipeCategory.TOOLS, item(HammerAdds.name(material, true)), 1)
                .define('#', base)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .unlockedBy("has_" + HammerAdds.name(material, false), has(base))
                .save(out, rl(HammerAdds.name(material, true)));
    }

    private static Item item(String path) {
        return BuiltInRegistries.ITEM.getValue(
                Identifier.fromNamespaceAndPath(UltimateArmouryCore.MOD_ID, path));
    }

    private void buildCompressedRecipes(RecipeOutput out) {
        RecipeOutput compressed = out.withConditions(new ConfigCondition(ConfigCondition.ALLTHECOMPRESSED));

        for (String material : CompressedBlocks.MATERIALS) {
            for (int level = 1; level <= CompressedBlocks.MAX_LEVEL; level++) {
                ItemLike result = CompressedBlocks.get(material, level).get();
                ItemLike source = level == 1
                        ? CompressedBlocks.baseBlock(material).get()
                        : CompressedBlocks.get(material, level - 1).get();
                String name = CompressedBlocks.name(material, level);

                ShapedRecipeBuilder.shaped(this.items, RecipeCategory.BUILDING_BLOCKS, result, 1)
                        .pattern("aaa").pattern("aaa").pattern("aaa")
                        .define('a', source)
                        .unlockedBy("has_" + material, has(source))
                        .save(compressed, rl(name));

                ShapelessRecipeBuilder.shapeless(this.items, RecipeCategory.BUILDING_BLOCKS, source, 9)
                        .requires(result)
                        .unlockedBy("has_" + name, has(result))
                        .save(compressed, rl(name + "_uncompress"));
            }
        }
    }

    private void buildMeshRecipes(RecipeOutput out) {
        RecipeOutput exDeorum = out.withConditions(
                new ModLoadedCondition("exdeorum"),
                new ConfigCondition(ConfigCondition.EX_DEORUM));

        RecipeOutput withAtm = out.withConditions(
                new ModLoadedCondition("exdeorum"),
                new ConfigCondition(ConfigCondition.EX_DEORUM),
                new ModLoadedCondition("allthemodium"),
                new ConfigCondition(ConfigCondition.ALLTHEMODIUM));

        mesh(exDeorum, MeshAdds.STEEL_MESH.get(), "c:ingots/steel", Ingredient.of(this.items.getOrThrow(ModItemTags.Items.compat("exdeorum:iron_mesh"))));
        mesh(exDeorum, MeshAdds.MUSHASHITE_MESH.get(), "c:ingots/mushashite", Ingredient.of(this.items.getOrThrow(ModItemTags.Items.compat("exdeorum:netherite_mesh"))));
        mesh(exDeorum, MeshAdds.JOANFOITE_MESH.get(), "c:ingots/joanfoite", Ingredient.of(MeshAdds.MUSHASHITE_MESH.get()));
        mesh(exDeorum, MeshAdds.NADIENITE_MESH.get(), "c:ingots/nadienite", Ingredient.of(MeshAdds.JOANFOITE_MESH.get()));

        mesh(withAtm, MeshAdds.ALLTHEMODIUM_MESH.get(), "c:ingots/allthemodium", Ingredient.of(MeshAdds.NADIENITE_MESH.get()));
        mesh(withAtm, MeshAdds.VIBRANIUM_MESH.get(), "c:ingots/vibranium", Ingredient.of(MeshAdds.ALLTHEMODIUM_MESH.get()));
        mesh(withAtm, MeshAdds.UNOBTAINIUM_MESH.get(), "c:ingots/unobtainium", Ingredient.of(MeshAdds.VIBRANIUM_MESH.get()));
    }

    private void mesh(RecipeOutput out, ItemLike result, String ingotTag, Ingredient previousMesh) {
        TagKey<Item> ingots = ItemTags.create(Identifier.parse(ingotTag));
        ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(this.items, RecipeCategory.MISC, result, 1)
                .define('#', ingots)
                .define('S', Tags.Items.STRINGS)
                .define('P', previousMesh)
                .pattern("S#S")
                .pattern("#P#")
                .pattern("S#S");

        builder.unlockedBy("has_string", has(Tags.Items.STRINGS))
                .save(out, rl(BuiltInRegistries.ITEM.getKey(result.asItem()).getPath()));
    }

    private void buildTierChain(RecipeOutput out) {
        for (ModTier tier : ModTier.values()) {
            buildMaterialRecipes(out, tier);
            buildGearRecipes(out, tier);
        }
        buildNuggetRecipes(out, "steel", ItemsAdds.STEEL_INGOT.get(), ItemsAdds.STEEL_NUGGET.get());
        buildNuggetRecipes(out, "netherite", Items.NETHERITE_INGOT, ItemsAdds.NETHERITE_NUGGET.get());
    }

    private void buildNuggetRecipes(RecipeOutput out, String id, ItemLike ingot, ItemLike nugget) {
        ShapelessRecipeBuilder.shapeless(this.items, RecipeCategory.MISC, nugget, 9)
                .requires(ingot)
                .unlockedBy("has_" + id + "_ingot", has(ingot))
                .save(out, rl(id + "_nugget_from_ingot"));

        ShapedRecipeBuilder.shaped(this.items, RecipeCategory.MISC, ingot, 1)
                .pattern("aaa").pattern("aaa").pattern("aaa")
                .define('a', nugget)
                .unlockedBy("has_" + id + "_nugget", has(nugget))
                .save(out, rl(id + "_ingot_from_nuggets"));
    }

    private void buildMaterialRecipes(RecipeOutput out, ModTier tier) {
        String id = tier.id();
        ItemLike ingot = ingotOf(tier);
        ItemLike nugget = nuggetOf(tier);
        ItemLike storageBlock = storageBlockOf(tier);
        Criterion<?> hasIngot = has(ingot);

        ItemLike rawOre = ItemsAdds.rawOreOf(tier).get();
        Criterion<?> hasRaw = has(rawOre);

        SimpleCookingRecipeBuilder.smelting(
                Ingredient.of(rawOre),
                RecipeCategory.MISC, net.minecraft.world.item.crafting.CookingBookCategory.MISC, ingot, 1.0f, 200)
                .unlockedBy("has_raw_" + id, hasRaw)
                .save(out, rl(id + "_ingot_furnace"));

        SimpleCookingRecipeBuilder.blasting(
                Ingredient.of(rawOre),
                RecipeCategory.MISC, net.minecraft.world.item.crafting.CookingBookCategory.MISC, ingot, 1.0f, 100)
                .unlockedBy("has_raw_" + id, hasRaw)
                .save(out, rl(id + "_ingot_blastfurnace"));

        SimpleCookingRecipeBuilder.smelting(
                Ingredient.of(ModOreBlocks.stoneOreOf(tier).get(), ModOreBlocks.deepslateOreOf(tier).get()),
                RecipeCategory.MISC, net.minecraft.world.item.crafting.CookingBookCategory.MISC, ingot, 1.0f, 200)
                .unlockedBy("has_raw_" + id, hasRaw)
                .save(out, rl(id + "_ingot_from_ore_furnace"));

        SimpleCookingRecipeBuilder.blasting(
                Ingredient.of(ModOreBlocks.stoneOreOf(tier).get(), ModOreBlocks.deepslateOreOf(tier).get()),
                RecipeCategory.MISC, net.minecraft.world.item.crafting.CookingBookCategory.MISC, ingot, 1.0f, 100)
                .unlockedBy("has_raw_" + id, hasRaw)
                .save(out, rl(id + "_ingot_from_ore_blastfurnace"));

        ShapedRecipeBuilder.shaped(this.items, RecipeCategory.BUILDING_BLOCKS, storageBlock, 1)
                .pattern("aaa").pattern("aaa").pattern("aaa")
                .define('a', ingot)
                .unlockedBy("has_" + id + "_ingot", hasIngot)
                .save(out, rl(id + "_block_recipe"));

        ShapelessRecipeBuilder.shapeless(this.items, RecipeCategory.MISC, ingot, 9)
                .requires(storageBlock)
                .unlockedBy("has_" + id + "_block", has(storageBlock))
                .save(out, rl(id + "_ingot_from_block"));

        ShapelessRecipeBuilder.shapeless(this.items, RecipeCategory.MISC, nugget, 9)
                .requires(ingot)
                .unlockedBy("has_" + id + "_ingot", hasIngot)
                .save(out, rl(id + "_nugget_from_ingot"));

        ShapedRecipeBuilder.shaped(this.items, RecipeCategory.MISC, ingot, 1)
                .pattern("aaa").pattern("aaa").pattern("aaa")
                .define('a', nugget)
                .unlockedBy("has_" + id + "_nugget", has(nugget))
                .save(out, rl(id + "_ingot_from_nuggets"));
    }

    private void buildGearRecipes(RecipeOutput out, ModTier tier) {
        ModTier previous = previousOf(tier);
        if (previous == null) {
            buildBaseGearRecipes(out, tier);
        } else {
            buildUpgradeGearRecipes(out, tier, previous);
        }
    }

    private void buildBaseGearRecipes(RecipeOutput out, ModTier tier) {
        ItemLike ingot = ingotOf(tier);
        ItemLike heart = heartOf(tier);
        Criterion<?> unlock = has(heart);

        forged(out, tier, RecipeCategory.COMBAT, ArmorAdds.MUSHASHITE_GORRO.get(), "gorro", unlock, ingot, heart,
                "NCN", "N N");
        forged(out, tier, RecipeCategory.COMBAT, ArmorAdds.MUSHASHITE_REMERA.get(), "remera", unlock, ingot, heart,
                "N N", "NCN", "NNN");
        forged(out, tier, RecipeCategory.COMBAT, ArmorAdds.MUSHASHITE_GAYUMBOS.get(), "gayumbos", unlock, ingot, heart,
                "NCN", "N N", "N N");
        forged(out, tier, RecipeCategory.COMBAT, ArmorAdds.MUSHASHITE_MEDIAS.get(), "medias", unlock, ingot, heart,
                "C N", "N N");

        forgedWithStick(out, tier, RecipeCategory.COMBAT, ToolsAdds.MUSHASHITE_SWORD.get(), "sword", unlock, ingot, heart,
                " C ", " N ", " S ");
        forgedWithStick(out, tier, RecipeCategory.TOOLS, ToolsAdds.MUSHASHITE_PICKAXE.get(), "pickaxe", unlock, ingot, heart,
                "NCN", " S ", " S ");
        forgedWithStick(out, tier, RecipeCategory.TOOLS, ToolsAdds.MUSHASHITE_AXE.get(), "axe", unlock, ingot, heart,
                "CN ", "NS ", " S ");
        forgedWithStick(out, tier, RecipeCategory.TOOLS, ToolsAdds.MUSHASHITE_SHOVEL.get(), "shovel", unlock, ingot, heart,
                " NC", " S ", " S ");
        forgedWithStick(out, tier, RecipeCategory.TOOLS, ToolsAdds.MUSHASHITE_HOE.get(), "hoe", unlock, ingot, heart,
                "CN ", " S ", " S ");
    }

    private void buildUpgradeGearRecipes(RecipeOutput out, ModTier tier, ModTier previous) {
        for (String piece : new String[]{"gorro", "remera", "gayumbos", "medias"}) {
            upgrade(out, tier, previous, RecipeCategory.COMBAT, piece);
        }
        for (String piece : new String[]{"sword", "pickaxe", "axe", "shovel", "hoe"}) {
            upgrade(out, tier, previous, RecipeCategory.TOOLS, piece);
        }
    }

    private void upgrade(RecipeOutput out, ModTier tier, ModTier previous, RecipeCategory category, String piece) {
        ItemLike result = gearOf(tier, piece);
        ItemLike base = gearOf(previous, piece);
        ItemLike heart = heartOf(tier);

        ShapedRecipeBuilder.shaped(this.items, category, result, 1)
                .pattern(" N ")
                .pattern("NPN")
                .pattern(" C ")
                .define('N', ingotOf(tier))
                .define('P', base)
                .define('C', heart)
                .unlockedBy("has_" + previous.id() + "_" + piece, has(base))
                .save(out, rl(tier.id() + "_" + piece));
    }

    private void forged(RecipeOutput out, ModTier tier, RecipeCategory category, ItemLike result, String piece,
                        Criterion<?> unlock, ItemLike ingot, ItemLike heart, String... pattern) {
        ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(this.items, category, result, 1);
        for (String row : pattern) {
            builder.pattern(row);
        }
        builder.define('N', ingot)
                .define('C', heart)
                .unlockedBy("has_" + tier.id() + "_heart", unlock)
                .save(out, rl(tier.id() + "_" + piece));
    }

    private void forgedWithStick(RecipeOutput out, ModTier tier, RecipeCategory category, ItemLike result, String piece,
                                 Criterion<?> unlock, ItemLike ingot, ItemLike heart, String... pattern) {
        ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(this.items, category, result, 1);
        for (String row : pattern) {
            builder.pattern(row);
        }
        builder.define('N', ingot)
                .define('C', heart)
                .define('S', Items.STICK)
                .unlockedBy("has_" + tier.id() + "_heart", unlock)
                .save(out, rl(tier.id() + "_" + piece));
    }

    private static ModTier previousOf(ModTier tier) {
        return switch (tier) {
            case MUSHASHITE -> null;
            case JOANFOITE -> ModTier.MUSHASHITE;
            case NADIENITE -> ModTier.JOANFOITE;
        };
    }

    private static ItemLike ingotOf(ModTier tier) {
        return switch (tier) {
            case MUSHASHITE -> ItemsAdds.MUSHASHITE_INGOT.get();
            case JOANFOITE -> ItemsAdds.JOANFOITE_INGOT.get();
            case NADIENITE -> ItemsAdds.NADIENITE_INGOT.get();
        };
    }

    private static ItemLike nuggetOf(ModTier tier) {
        return switch (tier) {
            case MUSHASHITE -> ItemsAdds.MUSHASHITE_NUGGET.get();
            case JOANFOITE -> ItemsAdds.JOANFOITE_NUGGET.get();
            case NADIENITE -> ItemsAdds.NADIENITE_NUGGET.get();
        };
    }

    private static ItemLike storageBlockOf(ModTier tier) {
        return switch (tier) {
            case MUSHASHITE -> BlocksAdds.MUSHASHITE_BLOCK.get();
            case JOANFOITE -> BlocksAdds.JOANFOITE_BLOCK.get();
            case NADIENITE -> BlocksAdds.NADIENITE_BLOCK.get();
        };
    }

    private static ItemLike heartOf(ModTier tier) {
        return switch (tier) {
            case MUSHASHITE -> ItemsAdds.CORAZON_DE_LOS_CAIDOS.get();
            case JOANFOITE -> ItemsAdds.CORAZON_DE_LOS_FANTASMAS.get();
            case NADIENITE -> ItemsAdds.CORAZON_DE_LA_ELITE.get();
        };
    }

    private static ItemLike gearOf(ModTier tier, String piece) {
        return switch (piece) {
            case "gorro" -> switch (tier) {
                case MUSHASHITE -> ArmorAdds.MUSHASHITE_GORRO.get();
                case JOANFOITE -> ArmorAdds.JOANFOITE_GORRO.get();
                case NADIENITE -> ArmorAdds.NADIENITE_GORRO.get();
            };
            case "remera" -> switch (tier) {
                case MUSHASHITE -> ArmorAdds.MUSHASHITE_REMERA.get();
                case JOANFOITE -> ArmorAdds.JOANFOITE_REMERA.get();
                case NADIENITE -> ArmorAdds.NADIENITE_REMERA.get();
            };
            case "gayumbos" -> switch (tier) {
                case MUSHASHITE -> ArmorAdds.MUSHASHITE_GAYUMBOS.get();
                case JOANFOITE -> ArmorAdds.JOANFOITE_GAYUMBOS.get();
                case NADIENITE -> ArmorAdds.NADIENITE_GAYUMBOS.get();
            };
            case "medias" -> switch (tier) {
                case MUSHASHITE -> ArmorAdds.MUSHASHITE_MEDIAS.get();
                case JOANFOITE -> ArmorAdds.JOANFOITE_MEDIAS.get();
                case NADIENITE -> ArmorAdds.NADIENITE_MEDIAS.get();
            };
            case "sword" -> switch (tier) {
                case MUSHASHITE -> ToolsAdds.MUSHASHITE_SWORD.get();
                case JOANFOITE -> ToolsAdds.JOANFOITE_SWORD.get();
                case NADIENITE -> ToolsAdds.NADIENITE_SWORD.get();
            };
            case "pickaxe" -> switch (tier) {
                case MUSHASHITE -> ToolsAdds.MUSHASHITE_PICKAXE.get();
                case JOANFOITE -> ToolsAdds.JOANFOITE_PICKAXE.get();
                case NADIENITE -> ToolsAdds.NADIENITE_PICKAXE.get();
            };
            case "axe" -> switch (tier) {
                case MUSHASHITE -> ToolsAdds.MUSHASHITE_AXE.get();
                case JOANFOITE -> ToolsAdds.JOANFOITE_AXE.get();
                case NADIENITE -> ToolsAdds.NADIENITE_AXE.get();
            };
            case "shovel" -> switch (tier) {
                case MUSHASHITE -> ToolsAdds.MUSHASHITE_SHOVEL.get();
                case JOANFOITE -> ToolsAdds.JOANFOITE_SHOVEL.get();
                case NADIENITE -> ToolsAdds.NADIENITE_SHOVEL.get();
            };
            case "hoe" -> switch (tier) {
                case MUSHASHITE -> ToolsAdds.MUSHASHITE_HOE.get();
                case JOANFOITE -> ToolsAdds.JOANFOITE_HOE.get();
                case NADIENITE -> ToolsAdds.NADIENITE_HOE.get();
            };
            default -> throw new IllegalArgumentException("pieza desconocida: " + piece);
        };
    }

    private static net.minecraft.resources.ResourceKey<net.minecraft.world.item.crafting.Recipe<?>> rl(String path) {
        return net.minecraft.resources.ResourceKey.create(net.minecraft.core.registries.Registries.RECIPE,
                Identifier.fromNamespaceAndPath(UltimateArmouryCore.MOD_ID, path));
    }
}
