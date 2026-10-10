package nadiendev.ultimatearmoury.datagen.providers;

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

public class ExDeorumCompatProvider implements DataProvider {

    private static final String EXDEORUM = "exdeorum";
    private static final String ALLTHEMODIUM = "allthemodium";

    private record Mesh(String id, String ingotTag, String previousMesh, String requiredMod, int tier) {
    }

    private record Drop(String item, double chance, String requiredMod) {

        Drop(String item, double chance) {
            this(item, chance, EXDEORUM);
        }
    }

    private record SieveSource(String meshItem, String requiredMod, List<Drop> drops) {
    }

    private static final List<Mesh> MESHES = List.of(
            new Mesh("steel_mesh", "c:ingots/steel", null, EXDEORUM, 1),
            new Mesh("mushashite_mesh", "c:ingots/mushashite", "steel_mesh", EXDEORUM, 2),
            new Mesh("joanfoite_mesh", "c:ingots/joanfoite", "mushashite_mesh", EXDEORUM, 3),
            new Mesh("nadienite_mesh", "c:ingots/nadienite", "joanfoite_mesh", EXDEORUM, 4),
            new Mesh("allthemodium_mesh", "c:ingots/allthemodium", "nadienite_mesh", ALLTHEMODIUM, 5),
            new Mesh("vibranium_mesh", "c:ingots/vibranium", "allthemodium_mesh", ALLTHEMODIUM, 6),
            new Mesh("unobtainium_mesh", "c:ingots/unobtainium", "vibranium_mesh", ALLTHEMODIUM, 7)
    );

    private static final List<String> SIEVE_INPUTS = List.of(
            "minecraft:gravel",
            "minecraft:sand",
            "exdeorum:crushed_deepslate",
            "exdeorum:crushed_netherrack"
    );

    private static final List<String[]> HEAT_SOURCES = List.of(
            new String[]{"mushashite_block", "2"},
            new String[]{"joanfoite_block", "3"},
            new String[]{"nadienite_block", "4"}
    );

    private final PackOutput output;

    public ExDeorumCompatProvider(PackOutput output) {
        this.output = output;
    }

    @Override
    public String getName() {
        return "Ex Deorum compat: " + UltimateArmouryCore.MOD_ID;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        Path root = this.output.getOutputFolder(PackOutput.Target.DATA_PACK);
        List<CompletableFuture<?>> tasks = new ArrayList<>();

        for (SieveSource source : sieveSources()) {
            for (String input : SIEVE_INPUTS) {
                for (Drop drop : source.drops()) {
                    String suffix = shortName(input) + "/"
                            + shortName(source.meshItem()) + "/" + shortName(drop.item());

                    tasks.add(DataProvider.saveStable(cache, sieveRecipe(source, input, drop, false),
                            recipePath(root, "sieve/" + suffix)));

                    tasks.add(DataProvider.saveStable(cache, sieveRecipe(source, input, drop, true),
                            recipePath(root, "compressed_sieve/" + suffix)));
                }
            }
        }

        for (String[] source : HEAT_SOURCES) {
            tasks.add(DataProvider.saveStable(cache, heatSource(source[0], Integer.parseInt(source[1])),
                    recipePath(root, "crucible_heat_source/" + source[0])));
        }

        return CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new));
    }

    private Path recipePath(Path root, String path) {
        return root.resolve(UltimateArmouryCore.MOD_ID).resolve("recipe").resolve(path + ".json");
    }

    private static String shortName(String id) {
        return id.substring(id.indexOf(':') + 1);
    }

    private static List<SieveSource> sieveSources() {
        List<SieveSource> sources = new ArrayList<>();

        sources.add(new SieveSource("exdeorum:netherite_mesh", EXDEORUM, List.of(
                new Drop("ultimatearmoury:raw_mushashite", 0.05))));

        for (Mesh mesh : MESHES) {
            sources.add(new SieveSource(UltimateArmouryCore.MOD_ID + ":" + mesh.id(),
                    mesh.requiredMod(), dropsFor(mesh.tier())));
        }
        return sources;
    }

    private static List<Drop> dropsFor(int tier) {
        return switch (tier) {
            case 1 -> List.of(
                    new Drop("minecraft:raw_iron", 0.20),
                    new Drop("minecraft:raw_gold", 0.12),
                    new Drop("minecraft:diamond", 0.03));
            case 2 -> List.of(
                    new Drop("ultimatearmoury:raw_mushashite", 0.12),
                    new Drop("ultimatearmoury:raw_joanfoite", 0.04),
                    new Drop("minecraft:diamond", 0.05));
            case 3 -> List.of(
                    new Drop("ultimatearmoury:raw_mushashite", 0.16),
                    new Drop("ultimatearmoury:raw_joanfoite", 0.10),
                    new Drop("ultimatearmoury:raw_nadienite", 0.03),
                    new Drop("minecraft:ancient_debris", 0.02));
            case 4 -> List.of(
                    new Drop("ultimatearmoury:raw_mushashite", 0.20),
                    new Drop("ultimatearmoury:raw_joanfoite", 0.14),
                    new Drop("ultimatearmoury:raw_nadienite", 0.08),
                    new Drop("minecraft:ancient_debris", 0.04),
                    new Drop("allthemodium:raw_allthemodium", 0.03, ALLTHEMODIUM));
            case 5 -> List.of(
                    new Drop("ultimatearmoury:raw_nadienite", 0.12),
                    new Drop("allthemodium:raw_allthemodium", 0.08, ALLTHEMODIUM),
                    new Drop("allthemodium:raw_vibranium", 0.03, ALLTHEMODIUM));
            case 6 -> List.of(
                    new Drop("ultimatearmoury:raw_nadienite", 0.14),
                    new Drop("allthemodium:raw_allthemodium", 0.12, ALLTHEMODIUM),
                    new Drop("allthemodium:raw_vibranium", 0.08, ALLTHEMODIUM),
                    new Drop("allthemodium:raw_unobtainium", 0.03, ALLTHEMODIUM));
            default -> List.of(
                    new Drop("ultimatearmoury:raw_nadienite", 0.16),
                    new Drop("allthemodium:raw_allthemodium", 0.14, ALLTHEMODIUM),
                    new Drop("allthemodium:raw_vibranium", 0.10, ALLTHEMODIUM),
                    new Drop("allthemodium:raw_unobtainium", 0.06, ALLTHEMODIUM));
        };
    }

    private static JsonElement conditions(String... requiredMods) {
        List<ICondition> conditions = new ArrayList<>();
        conditions.add(new ModLoadedCondition(EXDEORUM));
        conditions.add(new ConfigCondition(ConfigCondition.EX_DEORUM));

        Set<String> seen = new LinkedHashSet<>();
        for (String mod : requiredMods) {
            if (mod == null || EXDEORUM.equals(mod) || !seen.add(mod)) {
                continue;
            }
            conditions.add(new ModLoadedCondition(mod));
            if (ALLTHEMODIUM.equals(mod)) {
                conditions.add(new ConfigCondition(ConfigCondition.ALLTHEMODIUM));
            }
        }

        return ICondition.LIST_CODEC.encodeStart(JsonOps.INSTANCE, conditions)
                .getOrThrow(message -> new IllegalStateException("No se pudieron serializar las condiciones: " + message));
    }

    private static JsonObject sieveRecipe(SieveSource source, String input, Drop drop, boolean compressed) {
        JsonObject ingredient = new JsonObject();
        if (compressed) {
            ingredient.addProperty("tag", EXDEORUM + ":compressed/" + shortName(input));
        } else {
            ingredient.addProperty("item", input);
        }

        JsonObject meshItem = new JsonObject();
        meshItem.addProperty("item", source.meshItem());

        JsonObject result = new JsonObject();
        result.addProperty("count", 1);
        result.addProperty("id", drop.item());

        JsonObject amount = new JsonObject();
        amount.addProperty("type", "minecraft:binomial");
        amount.addProperty("n", compressed ? 7.0 : 1.0);
        amount.addProperty("p", drop.chance());

        JsonObject json = new JsonObject();
        json.add("neoforge:conditions", conditions(source.requiredMod(), drop.requiredMod()));
        json.addProperty("type", compressed ? "exdeorum:compressed_sieve" : "exdeorum:sieve");
        json.add("ingredient", ingredient);
        json.add("mesh", meshItem);
        json.add("result", result);
        json.add("result_amount", amount);
        return json;
    }

    private static JsonObject heatSource(String block, int heat) {
        JsonObject predicate = new JsonObject();
        predicate.addProperty("block", UltimateArmouryCore.MOD_ID + ":" + block);

        JsonObject json = new JsonObject();
        json.add("neoforge:conditions", conditions(EXDEORUM));
        json.addProperty("type", "exdeorum:crucible_heat_source");
        json.add("block_predicate", predicate);
        json.addProperty("heat_value", heat);
        return json;
    }
}
