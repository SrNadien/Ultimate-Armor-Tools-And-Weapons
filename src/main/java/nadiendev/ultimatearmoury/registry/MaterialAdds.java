package nadiendev.ultimatearmoury.registry;

import nadiendev.ultimatearmoury.UltimateArmouryCore;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MaterialAdds {

    public record Metal(String name, String ingot, String nugget, String block) {
    }

    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(UltimateArmouryCore.MOD_ID);

    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(UltimateArmouryCore.MOD_ID);

    public static final List<Metal> METALS = List.of(
            new Metal("amberite", "amberite_ingot",
                    "amberite_nugget", "amberite_block"),
            new Metal("infernium", "infernium_ingot", "infernium_nugget", "infernium_block"),
            new Metal("tenebrite", "tenebrite_ingot", "tenebrite_nugget", "tenebrite_block"),
            new Metal("umbrite", "umbrite_ingot", "umbrite_nugget", "umbrite_block"),
            new Metal("ionite", "ionite_ingot", "ionite_nugget",
                    "ionite_block"),
            new Metal("ascendium", "ascendium_ingot", "ascendium_nugget", "ascendium_block"),
            new Metal("gorium", "gorium_ingot", "gorium_nugget", "gorium_block"),
            new Metal("luminite", "luminite_ingot", "luminite_nugget", "luminite_block"),
            new Metal("osmirite", "osmirite_ingot", "osmirite_nugget", "osmirite_block")
    );

    public static final List<String> DUSTS = List.of("umbrite_dust", "lustrous_dust");

    public static final String CRYSTAL = "pyrestone_crystal";

    public static final String GEM = "viridite_gem";

    public static final String AURELITE = "aurelite_crystal";

    public static final String LAPIS_BLOCK = "viridite_block";

    private static final Map<String, DeferredHolder<Item, Item>> ITEM_HOLDERS = new LinkedHashMap<>();

    private static final Map<String, DeferredHolder<Block, Block>> BLOCK_HOLDERS = new LinkedHashMap<>();

    static {
        for (Metal metal : METALS) {
            registerItem(metal.ingot(), Rarity.UNCOMMON);
            registerItem(metal.nugget(), Rarity.UNCOMMON);
            registerBlock(metal.block());
        }
        DUSTS.forEach(dust -> registerItem(dust, Rarity.UNCOMMON));
        registerItem(CRYSTAL, Rarity.RARE);
        registerItem(GEM, Rarity.UNCOMMON);
        registerItem(AURELITE, Rarity.UNCOMMON);
        registerBlock(LAPIS_BLOCK);
    }

    private static void registerItem(String name, Rarity rarity) {
        ITEM_HOLDERS.put(name, RegHelper.item(ITEMS, name, () -> new Item(RegHelper.itemProps()
                .stacksTo(64)
                .rarity(rarity))));
    }

    private static void registerBlock(String name) {
        DeferredHolder<Block, Block> block = RegHelper.block(BLOCKS, name, () -> new Block(RegHelper.blockProps()
                .strength(5f, 10f)
                .sound(SoundType.METAL)
                .requiresCorrectToolForDrops()));
        BLOCK_HOLDERS.put(name, block);
        RegHelper.item(ITEMS, name, () -> new BlockItem(block.get(), RegHelper.itemProps()));
    }

    public static DeferredHolder<Item, Item> item(String name) {
        return ITEM_HOLDERS.get(name);
    }

    public static DeferredHolder<Block, Block> block(String name) {
        return BLOCK_HOLDERS.get(name);
    }

    public static List<DeferredHolder<Item, Item>> items() {
        return new ArrayList<>(ITEM_HOLDERS.values());
    }

    public static List<DeferredHolder<Block, Block>> blocks() {
        return new ArrayList<>(BLOCK_HOLDERS.values());
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
        ITEMS.register(eventBus);
    }

    private MaterialAdds() {
    }
}
