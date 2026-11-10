package nadiendev.ultimatearmoury.datagen.providers;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import nadiendev.ultimatearmoury.UltimateArmouryCore;
import nadiendev.ultimatearmoury.config.ConfigCondition;
import nadiendev.ultimatearmoury.registry.BrushAdds;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class FtbUnearthedCompatProvider implements DataProvider {

    private static final String MOD = UltimateArmouryCore.MOD_ID;
    private static final String UNEARTHED = "ftbunearthed";
    private static final String ALLTHEMODIUM = "allthemodium";
    private static final String THAUMATURGE = "thaumaturge";

    private static final String PROFESSION = "minecraft:mason";
    private static final double DAMAGE_CHANCE = 0.25;
    private static final int BASE_TIME = 280;
    private static final int TIME_STEP = 16;
    private static final int MIN_TIME = 40;

    private static final double THAUMATURGE_MIN_CHANCE = 0.30;
    private static final double THAUMATURGE_MAX_CHANCE = 1.00;

    private record Drop(String item, double chance) {
    }

    private record Input(String block, String thaumaturgeDrop) {
    }

    private static final List<Input> INPUTS = List.of(
            new Input("minecraft:gravel", "thaumaturge:amber"),
            new Input("minecraft:sand", "thaumaturge:amber"),
            new Input("minecraft:deepslate", "thaumaturge:raw_cinnabar"),
            new Input("minecraft:netherrack", "thaumaturge:raw_cinnabar")
    );

    private final PackOutput output;

    public FtbUnearthedCompatProvider(PackOutput output) {
        this.output = output;
    }

    @Override
    public String getName() {
        return "FTB Unearthed compat: " + MOD;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        Path root = this.output.getOutputFolder(PackOutput.Target.DATA_PACK);
        List<CompletableFuture<?>> tasks = new ArrayList<>();
        List<BrushAdds.Brush> brushes = BrushAdds.BRUSHES;
        List<BrushAdds.Brush> thaumaturge = BrushAdds.bySource(BrushAdds.Source.THAUMATURGE);
        List<BrushAdds.Brush> own = brushes.stream()
                .filter(brush -> brush.source() == BrushAdds.Source.BASE
                        || brush.source() == BrushAdds.Source.ALLTHEMODIUM)
                .toList();

        for (int index = 0; index < brushes.size(); index++) {
            BrushAdds.Brush brush = brushes.get(index);
            int time = Math.max(MIN_TIME, BASE_TIME - TIME_STEP * (index + 1));
            int thaumaturgeIndex = thaumaturge.indexOf(brush);
            int ownTier = own.indexOf(brush) + 1;

            for (Input input : INPUTS) {
                List<Drop> drops = switch (brush.source()) {
                    case VANILLA -> vanillaDrops(brush.material());
                    case THAUMATURGE -> List.of(new Drop(input.thaumaturgeDrop(),
                            thaumaturgeChance(thaumaturgeIndex, thaumaturge.size())));
                    default -> dropsFor(ownTier);
                };

                tasks.add(DataProvider.saveStable(cache, recipe(brush, input, drops, time),
                        recipePath(root, shortName(input.block()) + "/" + brush.itemName())));
            }
        }

        return CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new));
    }

    private Path recipePath(Path root, String path) {
        return root.resolve(MOD).resolve("recipe").resolve("unearther").resolve(path + ".json");
    }

    private static String shortName(String id) {
        return id.substring(id.indexOf(':') + 1);
    }

    private static double thaumaturgeChance(int tierIndex, int tierCount) {
        double raw = THAUMATURGE_MIN_CHANCE
                + (THAUMATURGE_MAX_CHANCE - THAUMATURGE_MIN_CHANCE) * tierIndex / (tierCount - 1);
        return Math.round(raw * 1000.0) / 1000.0;
    }

    private static List<Drop> vanillaDrops(String material) {
        return switch (material) {
            case "wooden" -> List.of(
                    new Drop("minecraft:raw_copper", 0.10),
                    new Drop("minecraft:raw_iron", 0.05));
            case "iron" -> List.of(
                    new Drop("minecraft:raw_copper", 0.20),
                    new Drop("minecraft:raw_iron", 0.12),
                    new Drop("minecraft:raw_gold", 0.04));
            case "golden" -> List.of(
                    new Drop("minecraft:raw_iron", 0.16),
                    new Drop("minecraft:raw_gold", 0.10),
                    new Drop("minecraft:diamond", 0.02));
            case "diamond" -> List.of(
                    new Drop("minecraft:raw_gold", 0.12),
                    new Drop("minecraft:diamond", 0.03),
                    new Drop("minecraft:emerald", 0.02));
            default -> List.of(
                    new Drop("minecraft:diamond", 0.05),
                    new Drop("minecraft:emerald", 0.03),
                    new Drop("minecraft:ancient_debris", 0.02));
        };
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
                    new Drop("minecraft:ancient_debris", 0.04));
            case 5 -> List.of(
                    new Drop("ultimatearmoury:raw_nadienite", 0.12),
                    new Drop("allthemodium:raw_allthemodium", 0.08),
                    new Drop("allthemodium:raw_vibranium", 0.03));
            case 6 -> List.of(
                    new Drop("ultimatearmoury:raw_nadienite", 0.14),
                    new Drop("allthemodium:raw_allthemodium", 0.12),
                    new Drop("allthemodium:raw_vibranium", 0.08),
                    new Drop("allthemodium:raw_unobtainium", 0.03));
            default -> List.of(
                    new Drop("ultimatearmoury:raw_nadienite", 0.16),
                    new Drop("allthemodium:raw_allthemodium", 0.14),
                    new Drop("allthemodium:raw_vibranium", 0.10),
                    new Drop("allthemodium:raw_unobtainium", 0.06));
        };
    }

    private static JsonElement conditions(BrushAdds.Source source) {
        List<ICondition> conditions = new ArrayList<>();
        conditions.add(new ModLoadedCondition(UNEARTHED));
        conditions.add(new ConfigCondition(ConfigCondition.FTB_UNEARTHED));

        if (source == BrushAdds.Source.ALLTHEMODIUM || source == BrushAdds.Source.THAUMATURGE) {
            conditions.add(new ModLoadedCondition(ALLTHEMODIUM));
            conditions.add(new ConfigCondition(ConfigCondition.FTB_UNEARTHED_ALLTHEMODIUM));
        }
        if (source == BrushAdds.Source.THAUMATURGE) {
            conditions.add(new ModLoadedCondition(THAUMATURGE));
            conditions.add(new ConfigCondition(ConfigCondition.FTB_UNEARTHED_THAUMATURGE));
        }

        return ICondition.LIST_CODEC.encodeStart(JsonOps.INSTANCE, conditions)
                .getOrThrow(message -> new IllegalStateException("No se pudieron serializar las condiciones: " + message));
    }

    private static JsonObject recipe(BrushAdds.Brush brush, Input input, List<Drop> drops, int time) {
        JsonObject worker = new JsonObject();
        worker.addProperty("profession", PROFESSION);

        JsonObject tool = new JsonObject();
        tool.addProperty("item", MOD + ":" + brush.itemName());

        JsonArray outputs = new JsonArray();
        for (Drop drop : drops) {
            JsonObject stack = new JsonObject();
            stack.addProperty("count", 1);
            stack.addProperty("id", drop.item());

            JsonObject entry = new JsonObject();
            entry.addProperty("chance", drop.chance());
            entry.add("item", stack);
            outputs.add(entry);
        }

        JsonObject json = new JsonObject();
        json.add("neoforge:conditions", conditions(brush.source()));
        json.addProperty("type", UNEARTHED + ":unearther");
        json.addProperty("input_block", input.block());
        json.addProperty("processing_time", time);
        json.add("worker", worker);
        json.add("tool_item", tool);
        json.addProperty("damage_chance", DAMAGE_CHANCE);
        json.add("output_items", outputs);
        return json;
    }
}
