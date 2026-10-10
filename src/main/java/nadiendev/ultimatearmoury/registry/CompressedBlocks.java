package nadiendev.ultimatearmoury.registry;

import nadiendev.ultimatearmoury.UltimateArmouryCore;
import net.minecraft.core.registries.BuiltInRegistries;
import nadiendev.ultimatearmoury.items.CompressedBlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CompressedBlocks {

    public static final int MAX_LEVEL = 9;

    public static final List<String> MATERIALS = List.of(
            "steel", "mushashite", "joanfoite", "nadienite", "nether_star", "ender_pearl");

    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(UltimateArmouryCore.MOD_ID);

    private static final Map<String, List<DeferredHolder<Block, Block>>> BY_MATERIAL = new LinkedHashMap<>();

    static {
        for (String material : MATERIALS) {
            List<DeferredHolder<Block, Block>> levels = new ArrayList<>();
            for (int level = 1; level <= MAX_LEVEL; level++) {
                levels.add(register(material, level));
            }
            BY_MATERIAL.put(material, List.copyOf(levels));
        }
    }

    private static DeferredHolder<Block, Block> register(String material, int level) {
        String name = name(material, level);
        DeferredHolder<Block, Block> block = RegHelper.block(BLOCKS, name, () -> new Block(
                RegHelper.blockProps()
                        .strength(5f + level, 10f + level * 4f)
                        .sound(SoundType.ANCIENT_DEBRIS)
                        .instrument(NoteBlockInstrument.BASEDRUM)
                        .requiresCorrectToolForDrops()
        ));
        RegHelper.item(ItemsAdds.ITEMS, name,
                () -> new CompressedBlockItem(block.get(), RegHelper.itemProps(), level));
        return block;
    }

    public static String name(String material, int level) {
        return material + "_block_" + level + "x";
    }

    public static DeferredHolder<Block, Block> get(String material, int level) {
        return BY_MATERIAL.get(material).get(level - 1);
    }

    public static DeferredHolder<Block, Block> baseBlock(String material) {
        return switch (material) {
            case "steel" -> BlocksAdds.STEEL_BLOCK;
            case "mushashite" -> BlocksAdds.MUSHASHITE_BLOCK;
            case "joanfoite" -> BlocksAdds.JOANFOITE_BLOCK;
            case "nadienite" -> BlocksAdds.NADIENITE_BLOCK;
            case "nether_star" -> BlocksAdds.NETHER_STAR_BLOCK;
            case "ender_pearl" -> BlocksAdds.ENDER_PEARL_BLOCK;
            default -> throw new IllegalArgumentException("material desconocido: " + material);
        };
    }

    public static List<DeferredHolder<Block, Block>> all() {
        List<DeferredHolder<Block, Block>> all = new ArrayList<>();
        BY_MATERIAL.values().forEach(all::addAll);
        return all;
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

    private CompressedBlocks() {
    }
}
