package nadiendev.ultimatearmoury.registry;

import nadiendev.ultimatearmoury.UltimateArmouryCore;
import nadiendev.ultimatearmoury.items.ModTier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class BlocksAdds {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(UltimateArmouryCore.MOD_ID);

    public static final DeferredHolder<Block, Block> STEEL_BLOCK = registerBlock(
            "steel_block",
            () -> new Block(RegHelper.blockProps()
                    .strength(5f, 10f)
                    .sound(SoundType.NETHERITE_BLOCK)
            )
    );

     public static final DeferredHolder<Block, Block> SEXY_XD = registerBlock(
            "sexy_xd",
            () -> new Block(RegHelper.blockProps()
                    .strength(5f, 10f)
                    .sound(SoundType.NETHERITE_BLOCK)
            )
    );

    public static final DeferredHolder<Block, Block> MUSHASHITE_BLOCK = registerStorageBlock("mushashite_block");

    public static final DeferredHolder<Block, Block> JOANFOITE_BLOCK = registerStorageBlock("joanfoite_block");

    public static final DeferredHolder<Block, Block> NADIENITE_BLOCK = registerStorageBlock("nadienite_block");

    public static final DeferredHolder<Block, Block> ENDER_PEARL_BLOCK = registerStorageBlock("ender_pearl_block");

    public static final DeferredHolder<Block, Block> NETHER_STAR_BLOCK = registerBlock(
            "nether_star_block",
            () -> new Block(RegHelper.blockProps()
                    .strength(5f, 10f)
                    .sound(SoundType.ANCIENT_DEBRIS)
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .requiresCorrectToolForDrops()
            )
    );

    private static <T extends Block> DeferredHolder<Block, T> registerBlock(String name, Supplier<T> block) {
        DeferredHolder<Block, T> toReturn = RegHelper.block(BLOCKS, name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredHolder<Block, T> block) {
        RegHelper.item(ItemsAdds.ITEMS, name, () -> new BlockItem(block.get(), RegHelper.itemProps()));
    }

    public static DeferredHolder<Block, Block> storageBlockOf(ModTier tier) {
        return switch (tier) {
            case MUSHASHITE -> MUSHASHITE_BLOCK;
            case JOANFOITE -> JOANFOITE_BLOCK;
            case NADIENITE -> NADIENITE_BLOCK;
        };
    }

    private static DeferredHolder<Block, Block> registerStorageBlock(String name) {
        return registerBlock(name, () -> new Block(RegHelper.blockProps()
                .strength(5f, 10f)
                .sound(SoundType.ANCIENT_DEBRIS)
                .instrument(NoteBlockInstrument.BASEDRUM)
                .requiresCorrectToolForDrops()
        ));
    }

    public static DeferredHolder<Block, Block> registerSimpleBlock(String name, float hardness, float resistance) {
        return registerBlock(name, () -> new Block(RegHelper.blockProps()
                .strength(hardness, resistance)
                .sound(SoundType.STONE)));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
