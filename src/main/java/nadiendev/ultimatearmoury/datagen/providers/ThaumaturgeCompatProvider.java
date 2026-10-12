package nadiendev.ultimatearmoury.datagen.providers;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import nadiendev.ultimatearmoury.UltimateArmouryCore;
import nadiendev.ultimatearmoury.config.ConfigCondition;
import nadiendev.ultimatearmoury.registry.HammerAdds;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class ThaumaturgeCompatProvider implements DataProvider {

    private static final String MOD = UltimateArmouryCore.MOD_ID;
    private static final String THAUMATURGE = "thaumaturge";
    private static final String EXDEORUM = "exdeorum";
    private static final String ALLTHEMODIUM = "allthemodium";

    private static final String CATEGORY = MOD + ":armoury";
    private static final String ENTRY_MESHES = MOD + ":thaumic_meshes";
    private static final String ENTRY_HAMMERS = MOD + ":thaumic_hammers";

    private static final int ARCANE_VIS = 100;
    private static final int ARCANE_CRYSTALS = 64;
    private static final List<String> PRIMALS = List.of("aer", "aqua", "ignis", "ordo", "perditio", "terra");

    private static final double MIN_CHANCE = 0.30;
    private static final double MAX_CHANCE = 1.00;

    private static final int ARCANE_TIERS = 1;

    private static final Map<String, String> BLOCKS = Map.of(
            "brass", "thaumaturge:metal_brass",
            "thaumium", "thaumaturge:metal_thaumium",
            "void_metal", "thaumaturge:metal_void");

    private static final Map<String, Integer> HEAT = Map.of(
            "brass", 5,
            "thaumium", 6,
            "void_metal", 7);

    private record Drop(String item, List<String> inputs) {
    }

    private static final List<Drop> DROPS = List.of(
            new Drop("thaumaturge:amber", List.of("minecraft:gravel", "minecraft:sand")),
            new Drop("thaumaturge:raw_cinnabar", List.of("exdeorum:crushed_deepslate", "exdeorum:crushed_netherrack"))
    );

    private final PackOutput output;

    public ThaumaturgeCompatProvider(PackOutput output) {
        this.output = output;
    }

    @Override
    public String getName() {
        return "Thaumaturge compat: " + MOD;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        Path root = this.output.getOutputFolder(PackOutput.Target.DATA_PACK);
        List<CompletableFuture<?>> tasks = new ArrayList<>();
        List<HammerAdds.Hammer> tiers = HammerAdds.THAUMATURGE_HAMMERS;

        List<String> meshRecipes = new ArrayList<>();
        List<String> hammerRecipes = new ArrayList<>();

        for (int i = 0; i < tiers.size(); i++) {
            HammerAdds.Hammer tier = tiers.get(i);

            for (Drop drop : DROPS) {
                for (String input : drop.inputs()) {
                    String suffix = shortName(input) + "/" + meshName(tier.material()) + "/" + shortName(drop.item());
                    double chance = chance(i, tiers.size());

                    tasks.add(DataProvider.saveStable(cache, sieveRecipe(tier, input, drop, chance, false),
                            recipePath(root, "thaumaturge/sieve/" + suffix)));
                    tasks.add(DataProvider.saveStable(cache, sieveRecipe(tier, input, drop, chance, true),
                            recipePath(root, "thaumaturge/compressed_sieve/" + suffix)));
                }
            }

            String where = i < ARCANE_TIERS ? "arcane" : "infusion";

            String meshId = "thaumaturge/" + where + "/mesh/" + tier.material();
            JsonObject mesh = i < ARCANE_TIERS ? arcaneMesh(tiers, i) : infusedMesh(tiers, i);
            tasks.add(DataProvider.saveStable(cache, mesh, recipePath(root, meshId)));
            meshRecipes.add(MOD + ":" + meshId);

            String hammerId = "thaumaturge/" + where + "/hammer/" + tier.material();
            JsonObject hammer = i < ARCANE_TIERS ? arcaneHammer(tier) : infusedHammer(tier, i);
            tasks.add(DataProvider.saveStable(cache, hammer, recipePath(root, hammerId)));
            hammerRecipes.add(MOD + ":" + hammerId);

            String compressedId = "thaumaturge/arcane/compressed_hammer/" + tier.material();
            tasks.add(DataProvider.saveStable(cache, arcaneCompressedHammer(tier), recipePath(root, compressedId)));
            hammerRecipes.add(MOD + ":" + compressedId);

            tasks.add(DataProvider.saveStable(cache, heatSource(tier.material()),
                    recipePath(root, "thaumaturge/crucible_heat_source/" + tier.material())));
        }

        tasks.add(DataProvider.saveStable(cache, category(),
                root.resolve(MOD).resolve(THAUMATURGE).resolve("research_category").resolve("armoury.json")));

        tasks.add(DataProvider.saveStable(cache,
                entry("thaumic_meshes", List.of("thaumaturge:metallurgy@3", "thaumaturge:infusion"),
                        0, 0, MOD + ":void_metal_mesh", meshRecipes,
                        new String[][]{{"metallum", "4"}, {"instrumentum", "3"}, {"terra", "2"}}, 2),
                entryPath(root, "thaumic_meshes")));

        tasks.add(DataProvider.saveStable(cache,
                entry("thaumic_hammers", List.of(ENTRY_MESHES),
                        3, 0, MOD + ":void_metal_hammer", hammerRecipes,
                        new String[][]{{"metallum", "4"}, {"instrumentum", "3"}, {"perditio", "2"}}, 2),
                entryPath(root, "thaumic_hammers")));

        tasks.add(DataProvider.saveStable(cache,
                entry("thaumic_heaters", List.of(ENTRY_MESHES),
                        -3, 0, "thaumaturge:metal_void", List.of(),
                        new String[][]{{"ignis", "4"}, {"metallum", "3"}, {"alkimia", "2"}}, 2),
                entryPath(root, "thaumic_heaters")));

        return CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new));
    }

    private static double chance(int tierIndex, int tierCount) {
        double raw = MIN_CHANCE + (MAX_CHANCE - MIN_CHANCE) * tierIndex / (tierCount - 1);
        return Math.round(raw * 1000.0) / 1000.0;
    }

    private Path recipePath(Path root, String path) {
        return root.resolve(MOD).resolve("recipe").resolve(path + ".json");
    }

    private Path entryPath(Path root, String name) {
        return root.resolve(MOD).resolve(THAUMATURGE).resolve("research_entry").resolve(name + ".json");
    }

    private static String shortName(String id) {
        return id.substring(id.indexOf(':') + 1);
    }

    private static JsonElement conditions(boolean needsAllTheModium) {
        List<ICondition> list = new ArrayList<>();
        list.add(new ModLoadedCondition(THAUMATURGE));
        list.add(new ModLoadedCondition(EXDEORUM));
        list.add(new ConfigCondition(ConfigCondition.THAUMATURGE));
        if (needsAllTheModium) {
            list.add(new ModLoadedCondition(ALLTHEMODIUM));
            list.add(new ConfigCondition(ConfigCondition.ALLTHEMODIUM));
        }

        return ICondition.LIST_CODEC.encodeStart(JsonOps.INSTANCE, list)
                .getOrThrow(message -> new IllegalStateException("No se pudieron serializar las condiciones: " + message));
    }

    private static JsonObject itemIngredient(String id) {
        JsonObject json = new JsonObject();
        json.addProperty("item", id);
        return json;
    }

    private static JsonObject tagIngredient(String id) {
        JsonObject json = new JsonObject();
        json.addProperty("tag", id);
        return json;
    }

    private static JsonObject result(String id) {
        JsonObject json = new JsonObject();
        json.addProperty("count", 1);
        json.addProperty("id", id);
        return json;
    }

    private static JsonObject gate(String entry) {
        JsonObject json = new JsonObject();
        json.addProperty("entry", entry);
        return json;
    }

    private static String meshName(String material) {
        return material + "_mesh";
    }

    private static String meshId(String material) {
        return MOD + ":" + meshName(material);
    }

    private static String hammerId(String material, boolean compressed) {
        return MOD + ":" + HammerAdds.name(material, compressed);
    }

    private static String previousMeshId(List<HammerAdds.Hammer> tiers, int index) {
        return meshId(tiers.get(index).previousMaterial());
    }

    private static JsonObject sieveRecipe(HammerAdds.Hammer tier, String input, Drop drop, double chance,
                                          boolean compressed) {
        JsonObject amount = new JsonObject();
        amount.addProperty("type", "minecraft:binomial");
        amount.addProperty("n", compressed ? 7.0 : 1.0);
        amount.addProperty("p", chance);

        JsonObject json = new JsonObject();
        json.add("neoforge:conditions", conditions(true));
        json.addProperty("type", compressed ? "exdeorum:compressed_sieve" : "exdeorum:sieve");
        json.add("ingredient", compressed
                ? tagIngredient(EXDEORUM + ":compressed/" + shortName(input))
                : itemIngredient(input));
        json.add("mesh", itemIngredient(meshId(tier.material())));
        json.add("result", result(drop.item()));
        json.add("result_amount", amount);
        return json;
    }

    private static JsonObject heatSource(String material) {
        JsonObject predicate = new JsonObject();
        predicate.addProperty("block", BLOCKS.get(material));

        JsonObject json = new JsonObject();
        json.add("neoforge:conditions", conditions(false));
        json.addProperty("type", "exdeorum:crucible_heat_source");
        json.add("block_predicate", predicate);
        json.addProperty("heat_value", HEAT.get(material));
        return json;
    }

    private static JsonArray crystals() {
        JsonArray array = new JsonArray();
        for (String primal : PRIMALS) {
            JsonObject crystal = new JsonObject();
            crystal.addProperty("amount", ARCANE_CRYSTALS);
            crystal.addProperty("aspect", THAUMATURGE + ":" + primal);
            array.add(crystal);
        }
        return array;
    }

    private static JsonObject arcaneShaped(String entry, String resultId, List<String> pattern, JsonObject key) {
        JsonArray rows = new JsonArray();
        pattern.forEach(rows::add);

        JsonObject json = new JsonObject();
        json.add("neoforge:conditions", conditions(true));
        json.addProperty("type", "thaumaturge:arcane_workbench_shaped");
        json.add("crystals", crystals());
        json.add("key", key);
        json.add("pattern", rows);
        json.add("research", gate(entry));
        json.add("result", result(resultId));
        json.addProperty("vis", ARCANE_VIS);
        return json;
    }

    private static JsonObject arcaneMesh(List<HammerAdds.Hammer> tiers, int index) {
        HammerAdds.Hammer tier = tiers.get(index);

        JsonObject key = new JsonObject();
        key.add("#", tagIngredient(tier.ingotTag()));
        key.add("S", tagIngredient("c:strings"));
        key.add("P", itemIngredient(previousMeshId(tiers, index)));

        return arcaneShaped(ENTRY_MESHES, meshId(tier.material()), List.of("S#S", "#P#", "S#S"), key);
    }

    private static JsonObject arcaneHammer(HammerAdds.Hammer tier) {
        JsonObject key = new JsonObject();
        key.add("s", tagIngredient("c:rods/wooden"));
        key.add("m", tagIngredient(tier.ingotTag()));
        key.add("p", itemIngredient(hammerId(tier.previousMaterial(), false)));

        return arcaneShaped(ENTRY_HAMMERS, hammerId(tier.material(), false), List.of(" m ", " pm", "s  "), key);
    }

    private static JsonObject arcaneCompressedHammer(HammerAdds.Hammer tier) {
        JsonObject key = new JsonObject();
        key.add("#", itemIngredient(hammerId(tier.material(), false)));

        return arcaneShaped(ENTRY_HAMMERS, hammerId(tier.material(), true), List.of("###", "###", "###"), key);
    }

    private static JsonArray infusionAspects(int index) {
        JsonArray array = new JsonArray();
        String[] aspects = {"metallum", "praecantatio", "potentia"};
        int[] base = {20, 10, 10};
        for (int i = 0; i < aspects.length; i++) {
            JsonObject aspect = new JsonObject();
            aspect.addProperty("amount", base[i] * index);
            aspect.addProperty("aspect", THAUMATURGE + ":" + aspects[i]);
            array.add(aspect);
        }
        return array;
    }

    private static JsonObject infusion(String entry, JsonObject catalyst, List<JsonObject> components,
                                       String resultId, int index) {
        JsonArray list = new JsonArray();
        components.forEach(list::add);

        JsonObject json = new JsonObject();
        json.add("neoforge:conditions", conditions(true));
        json.addProperty("type", "thaumaturge:infusion");
        json.add("aspects", infusionAspects(index));
        json.add("catalyst", catalyst);
        json.add("components", list);
        json.addProperty("instability", 3 + index);
        json.add("research", gate(entry));
        json.add("result", result(resultId));
        return json;
    }

    private static JsonObject infusedMesh(List<HammerAdds.Hammer> tiers, int index) {
        HammerAdds.Hammer tier = tiers.get(index);
        List<JsonObject> components = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            components.add(tagIngredient(tier.ingotTag()));
            components.add(tagIngredient("c:strings"));
        }

        return infusion(ENTRY_MESHES, itemIngredient(previousMeshId(tiers, index)), components,
                meshId(tier.material()), index);
    }

    private static JsonObject infusedHammer(HammerAdds.Hammer tier, int index) {
        List<JsonObject> components = new ArrayList<>();
        components.add(tagIngredient(tier.ingotTag()));
        components.add(tagIngredient(tier.ingotTag()));
        components.add(tagIngredient("c:rods/wooden"));

        return infusion(ENTRY_HAMMERS, itemIngredient(hammerId(tier.previousMaterial(), false)), components,
                hammerId(tier.material(), false), index);
    }

    private static JsonObject category() {
        String[][] formula = {{"metallum", "10"}, {"instrumentum", "10"}, {"potentia", "5"},
                {"praecantatio", "5"}, {"terra", "5"}};
        JsonArray aspects = new JsonArray();
        for (String[] item : formula) {
            JsonObject aspect = new JsonObject();
            aspect.addProperty("amount", Integer.parseInt(item[1]));
            aspect.addProperty("aspect", THAUMATURGE + ":" + item[0]);
            aspects.add(aspect);
        }

        JsonObject json = new JsonObject();
        json.add("neoforge:conditions", conditions(true));
        json.addProperty("background", "thaumaturge:textures/gui/gui_research_back_4.png");
        json.add("formula", aspects);
        json.addProperty("icon", MOD + ":textures/item/void_metal_mesh.png");
        json.addProperty("index", 7);
        json.addProperty("overlay_background", "thaumaturge:textures/gui/gui_research_back_over.png");
        json.addProperty("required_research", "thaumaturge:unlock_infusion");
        return json;
    }

    private static JsonObject entry(String name, List<String> parents, int column, int row, String icon,
                                    List<String> recipes, String[][] noteAspects, int complexity) {
        JsonArray parentArray = new JsonArray();
        parents.forEach(parentArray::add);

        JsonObject theory = new JsonObject();
        theory.addProperty("type", "theory");
        theory.addProperty("category", "thaumaturge:infusion");
        theory.addProperty("amount", 1);
        JsonArray knowledge = new JsonArray();
        knowledge.add(theory);

        JsonObject stage0 = new JsonObject();
        stage0.addProperty("text", "research." + MOD + "." + name + ".stage_0");
        stage0.add("required_knowledge", knowledge);

        JsonObject stage1 = new JsonObject();
        stage1.addProperty("text", "research." + MOD + "." + name + ".stage_1");
        if (!recipes.isEmpty()) {
            JsonArray recipeArray = new JsonArray();
            recipes.forEach(recipeArray::add);
            stage1.add("recipes", recipeArray);
        }

        JsonArray stages = new JsonArray();
        stages.add(stage0);
        stages.add(stage1);

        JsonArray icons = new JsonArray();
        icons.add(icon);

        JsonArray notes = new JsonArray();
        for (String[] note : noteAspects) {
            JsonObject aspect = new JsonObject();
            aspect.addProperty("aspect", THAUMATURGE + ":" + note[0]);
            aspect.addProperty("amount", Integer.parseInt(note[1]));
            notes.add(aspect);
        }

        JsonObject json = new JsonObject();
        json.add("neoforge:conditions", conditions(true));
        json.addProperty("category", CATEGORY);
        json.addProperty("name", "research." + MOD + "." + name + ".title");
        json.add("parents", parentArray);
        json.addProperty("column", column);
        json.addProperty("row", row);
        json.add("icons", icons);
        json.add("stages", stages);
        json.add("note_aspects", notes);
        json.addProperty("complexity", complexity);
        return json;
    }
}
