package nadiendev.ultimatearmoury.registry;

import nadiendev.ultimatearmoury.UltimateArmouryCore;
import nadiendev.ultimatearmoury.items.ModTier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class ModOreBlocks {

    public static final DeferredRegister.Blocks ORE_BLOCKS =
            DeferredRegister.createBlocks(UltimateArmouryCore.MOD_ID);

    public static final DeferredRegister.Items ORE_BLOCK_ITEMS =
            DeferredRegister.createItems(UltimateArmouryCore.MOD_ID);

    private static final Map<ModTier, DeferredHolder<Block, Block>> STONE_ORES = new EnumMap<>(ModTier.class);
    private static final Map<ModTier, DeferredHolder<Block, Block>> DEEPSLATE_ORES = new EnumMap<>(ModTier.class);

    public static final DeferredHolder<Block, Block> MUSHASHITE_ORE = stoneOre(ModTier.MUSHASHITE);
    public static final DeferredHolder<Block, Block> DEEPSLATE_MUSHASHITE_ORE = deepslateOre(ModTier.MUSHASHITE);

    public static final DeferredHolder<Block, Block> JOANFOITE_ORE = stoneOre(ModTier.JOANFOITE);
    public static final DeferredHolder<Block, Block> DEEPSLATE_JOANFOITE_ORE = deepslateOre(ModTier.JOANFOITE);

    public static final DeferredHolder<Block, Block> NADIENITE_ORE = stoneOre(ModTier.NADIENITE);
    public static final DeferredHolder<Block, Block> DEEPSLATE_NADIENITE_ORE = deepslateOre(ModTier.NADIENITE);

    private static DeferredHolder<Block, Block> stoneOre(ModTier tier) {
        DeferredHolder<Block, Block> ore = register(tier.id() + "_ore", 3.0F, SoundType.STONE);
        STONE_ORES.put(tier, ore);
        return ore;
    }

    private static DeferredHolder<Block, Block> deepslateOre(ModTier tier) {
        DeferredHolder<Block, Block> ore = register("deepslate_" + tier.id() + "_ore", 4.5F, SoundType.DEEPSLATE);
        DEEPSLATE_ORES.put(tier, ore);
        return ore;
    }

    private static DeferredHolder<Block, Block> register(String name, float hardness, SoundType sound) {
        DeferredHolder<Block, Block> block = RegHelper.block(ORE_BLOCKS, name, () -> new Block(
                RegHelper.blockProps()
                        .strength(hardness, 3.0F)
                        .requiresCorrectToolForDrops()
                        .sound(sound)
        ));
        RegHelper.item(ORE_BLOCK_ITEMS, name, () -> new BlockItem(block.get(), RegHelper.itemProps().rarity(Rarity.RARE)));
        return block;
    }

    public static DeferredHolder<Block, Block> stoneOreOf(ModTier tier) {
        return STONE_ORES.get(tier);
    }

    public static DeferredHolder<Block, Block> deepslateOreOf(ModTier tier) {
        return DEEPSLATE_ORES.get(tier);
    }

    public static List<Block> allOres() {
        List<Block> ores = new ArrayList<>();
        for (ModTier tier : ModTier.values()) {
            ores.add(STONE_ORES.get(tier).get());
            ores.add(DEEPSLATE_ORES.get(tier).get());
        }
        return ores;
    }

    public static void register(IEventBus eventBus) {
        ORE_BLOCKS.register(eventBus);
        ORE_BLOCK_ITEMS.register(eventBus);
    }

    private ModOreBlocks() {
    }
}
