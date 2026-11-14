package nadiendev.ultimatearmoury.datagen.providers;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import nadiendev.ultimatearmoury.UltimateArmouryCore;
import nadiendev.ultimatearmoury.config.ConfigCondition;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class MaterialRecipeProvider implements DataProvider {

    private static final String ENDERIO = "enderio";
    private static final String ORITECH = "oritech";
    private static final String MEKANISM = "mekanism";
    private static final String ALLTHEORES = "alltheores";
    private static final String EXCESSIVE_UTILITIES = "excessive_utilities";
    private static final String KUBEJS = "kubejs";
    private static final String ACTUALLY_ADDITIONS = "actuallyadditions";
    private static final String AE2 = "ae2";

    private record Input(String id, boolean tag, int count) {

        static Input item(String id) {
            return new Input(id, false, 1);
        }

        static Input item(String id, int count) {
            return new Input(id, false, count);
        }
    }

    private record Alloy(String name, String output, int count, List<Input> inputs, int energy, double experience,
                         int time, String... mods) {
    }

    private static final String OSMIUM = "mekanism:ingot_osmium";
    private static final String DIESEL = "oritech:still_diesel_bucket";
    private static final String OSGLOGLAS = UltimateArmouryCore.MOD_ID + ":luminite_ingot";

    private static final List<Alloy> ALLOYS = List.of(
            new Alloy("osmirite_ingot", id("osmirite_ingot"), 1,
                    List.of(Input.item(OSMIUM), Input.item("alltheores:iridium_ingot")),
                    5000, 5.5, 80, MEKANISM, ALLTHEORES),
            new Alloy("luminite_ingot", id("luminite_ingot"), 1,
                    List.of(Input.item(OSMIUM), Input.item("mekanism:ingot_refined_glowstone"),
                            Input.item("mekanism:ingot_refined_obsidian")),
                    4800, 0.3, 0, MEKANISM),
            new Alloy("viridite_gem", id("viridite_gem"), 1,
                    List.of(Input.item("minecraft:lapis_lazuli"), Input.item(OSGLOGLAS)),
                    4800, 0.3, 80),
            new Alloy("infernium_ingot", id("infernium_ingot"), 1,
                    List.of(Input.item("excessive_utilities:lunar_reactive_dust"),
                            Input.item("excessive_utilities:demon_ingot")),
                    4800, 0.3, 80, EXCESSIVE_UTILITIES),
            new Alloy("ionite_ingot", id("ionite_ingot"), 8,
                    List.of(Input.item(OSMIUM, 8), Input.item(DIESEL)),
                    4800, 0.3, 0, MEKANISM, ORITECH),
            new Alloy("umbrite_ingot", id("umbrite_ingot"), 8,
                    List.of(Input.item(id("tenebrite_ingot"), 8), Input.item(DIESEL)),
                    4800, 0.3, 0, ORITECH),
            new Alloy("tenebrite_ingot", id("tenebrite_ingot"), 1,
                    List.of(Input.item("kubejs:bitumen"), Input.item("kubejs:tar")),
                    4800, 0.3, 80, KUBEJS),
            new Alloy("aurelite_crystal", id("aurelite_crystal"), 8,
                    List.of(Input.item("ae2:certus_quartz_crystal", 8), Input.item(DIESEL)),
                    4800, 0.3, 0, AE2, ORITECH),
            new Alloy("amberite_ingot", id("amberite_ingot"), 1,
                    List.of(Input.item("minecraft:slime_block", 4), Input.item("minecraft:glowstone_dust", 4),
                            Input.item("minecraft:brown_dye")),
                    4800, 0.3, 0)
    );

    private final PackOutput output;

    public MaterialRecipeProvider(PackOutput output) {
        this.output = output;
    }

    @Override
    public String getName() {
        return "Material recipes: " + UltimateArmouryCore.MOD_ID;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        Path root = this.output.getOutputFolder(PackOutput.Target.DATA_PACK);
        List<CompletableFuture<?>> tasks = new ArrayList<>();

        for (Alloy alloy : ALLOYS) {
            tasks.add(DataProvider.saveStable(cache, enderIoAlloy(alloy),
                    recipePath(root, "alloy_smelting/" + alloy.name())));
            if (alloy.time() > 0) {
                tasks.add(DataProvider.saveStable(cache, oritechFoundry(alloy),
                        recipePath(root, "foundry/" + alloy.name())));
            }
        }

        tasks.add(DataProvider.saveStable(cache, restoniaReaction(), recipePath(root, "reaction/pyrestone_crystal")));
        tasks.add(DataProvider.saveStable(cache,
                singleItemCraft(id("lustrous_dust"), "mekanism:dust_osmium", MEKANISM),
                recipePath(root, "crafting/lustrous_dust_to_mekanism")));
        tasks.add(DataProvider.saveStable(cache,
                singleItemCraft("mekanism:dust_osmium", id("lustrous_dust"), MEKANISM),
                recipePath(root, "crafting/lustrous_dust_from_mekanism")));

        return CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new));
    }

    private Path recipePath(Path root, String path) {
        return root.resolve(UltimateArmouryCore.MOD_ID).resolve("recipe").resolve(path + ".json");
    }

    private static String id(String name) {
        return UltimateArmouryCore.MOD_ID + ":" + name;
    }

    private static JsonElement conditions(String... requiredMods) {
        List<ICondition> conditions = new ArrayList<>();
        conditions.add(new ConfigCondition(ConfigCondition.MATERIALS));

        Set<String> seen = new LinkedHashSet<>();
        for (String mod : requiredMods) {
            if (seen.add(mod)) {
                conditions.add(new ModLoadedCondition(mod));
            }
        }

        return ICondition.LIST_CODEC.encodeStart(JsonOps.INSTANCE, conditions)
                .getOrThrow(message -> new IllegalStateException("No se pudieron serializar las condiciones: " + message));
    }

    private static String[] mods(String first, String... rest) {
        String[] all = new String[rest.length + 1];
        all[0] = first;
        System.arraycopy(rest, 0, all, 1, rest.length);
        return all;
    }

    private static JsonObject ingredient(Input input, boolean sized) {
        JsonObject json = new JsonObject();
        if (sized) {
            json.addProperty("count", input.count());
        }
        json.addProperty(input.tag() ? "tag" : "item", input.id());
        return json;
    }

    private static JsonObject result(String id, int count) {
        JsonObject json = new JsonObject();
        json.addProperty("count", count);
        json.addProperty("id", id);
        return json;
    }

    private static JsonObject enderIoAlloy(Alloy alloy) {
        JsonArray inputs = new JsonArray();
        alloy.inputs().forEach(input -> inputs.add(ingredient(input, true)));

        JsonObject json = new JsonObject();
        json.add("neoforge:conditions", conditions(mods(ENDERIO, alloy.mods())));
        json.addProperty("type", "enderio:alloy_smelting");
        json.addProperty("energy", alloy.energy());
        json.addProperty("experience", alloy.experience());
        json.add("inputs", inputs);
        json.add("output", result(alloy.output(), alloy.count()));
        return json;
    }

    private static JsonObject oritechFoundry(Alloy alloy) {
        JsonArray ingredients = new JsonArray();
        alloy.inputs().forEach(input -> ingredients.add(ingredient(input, false)));

        JsonArray results = new JsonArray();
        results.add(result(alloy.output(), alloy.count()));

        JsonObject json = new JsonObject();
        json.add("neoforge:conditions", conditions(mods(ORITECH, alloy.mods())));
        json.addProperty("type", "oritech:foundry");
        json.add("ingredients", ingredients);
        json.add("results", results);
        json.addProperty("time", alloy.time());
        return json;
    }

    private static JsonObject restoniaReaction() {
        JsonObject chemicalInput = new JsonObject();
        chemicalInput.addProperty("amount", 100);
        chemicalInput.addProperty("chemical", "mekanism:fissile_fuel");

        JsonObject chemicalOutput = new JsonObject();
        chemicalOutput.addProperty("amount", 100);
        chemicalOutput.addProperty("id", "mekanism:nuclear_waste");

        JsonObject fluidInput = new JsonObject();
        fluidInput.addProperty("amount", 10);
        fluidInput.addProperty("fluid", "mekanism:steam");

        JsonObject itemInput = new JsonObject();
        itemInput.addProperty("count", 1);
        itemInput.addProperty("item", "actuallyadditions:empowered_restonia_crystal");

        JsonObject json = new JsonObject();
        json.add("neoforge:conditions", conditions(MEKANISM, ACTUALLY_ADDITIONS));
        json.addProperty("type", "mekanism:reaction");
        json.add("chemical_input", chemicalInput);
        json.add("chemical_output", chemicalOutput);
        json.addProperty("duration", 100);
        json.add("fluid_input", fluidInput);
        json.add("item_input", itemInput);
        json.add("item_output", result(id("pyrestone_crystal"), 1));
        return json;
    }

    private static JsonObject singleItemCraft(String output, String input, String requiredMod) {
        JsonObject key = new JsonObject();
        key.add("A", ingredient(Input.item(input), false));

        JsonArray pattern = new JsonArray();
        pattern.add("A");

        JsonObject json = new JsonObject();
        json.add("neoforge:conditions", conditions(requiredMod));
        json.addProperty("type", "minecraft:crafting_shaped");
        json.addProperty("category", "misc");
        json.add("key", key);
        json.add("pattern", pattern);
        json.add("result", result(output, 1));
        return json;
    }
}
