package nadiendev.ultimatearmoury.datagen.providers;

import nadiendev.ultimatearmoury.UltimateArmouryCore;
import nadiendev.ultimatearmoury.registry.BlocksAdds;
import nadiendev.ultimatearmoury.registry.CompressedBlocks;
import nadiendev.ultimatearmoury.registry.ItemsAdds;
import nadiendev.ultimatearmoury.registry.ModOreBlocks;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.core.Holder;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ModModelProvider extends ModelProvider {

    public ModModelProvider(PackOutput output) {
        super(output, UltimateArmouryCore.MOD_ID);
    }

    private static List<Block> generatedBlocks() {
        List<Block> blocks = new ArrayList<>(List.of(
                BlocksAdds.MUSHASHITE_BLOCK.get(),
                BlocksAdds.JOANFOITE_BLOCK.get(),
                BlocksAdds.NADIENITE_BLOCK.get(),
                BlocksAdds.STEEL_BLOCK.get(),
                BlocksAdds.SEXY_XD.get(),
                BlocksAdds.NETHER_STAR_BLOCK.get(),
                BlocksAdds.ENDER_PEARL_BLOCK.get(),
                ModOreBlocks.MUSHASHITE_ORE.get(),
                ModOreBlocks.DEEPSLATE_MUSHASHITE_ORE.get(),
                ModOreBlocks.JOANFOITE_ORE.get(),
                ModOreBlocks.DEEPSLATE_JOANFOITE_ORE.get(),
                ModOreBlocks.NADIENITE_ORE.get(),
                ModOreBlocks.DEEPSLATE_NADIENITE_ORE.get()
        ));
        for (DeferredHolder<Block, Block> compressed : CompressedBlocks.all()) {
            blocks.add(compressed.get());
        }
        return blocks;
    }

    private static List<Item> generatedItems() {
        return List.of(
                ItemsAdds.NADIENITE_NUGGET.get(),
                ItemsAdds.NETHERITE_DAGGER.get()
        );
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        for (Block block : generatedBlocks()) {
            blockModels.createTrivialCube(block);
        }

        itemModels.generateFlatItem(ItemsAdds.NADIENITE_NUGGET.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemsAdds.NETHERITE_DAGGER.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
    }

    @Override
    protected Stream<? extends Holder<Block>> getKnownBlocks() {
        return generatedBlocks().stream().map(Block::builtInRegistryHolder);
    }

    @Override
    protected Stream<? extends Holder<Item>> getKnownItems() {
        return generatedItems().stream().map(Item::builtInRegistryHolder);
    }
}
