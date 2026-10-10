package nadiendev.ultimatearmoury.datagen.providers;

import nadiendev.ultimatearmoury.UltimateArmouryCore;
import nadiendev.ultimatearmoury.registry.BlocksAdds;
import nadiendev.ultimatearmoury.registry.CompressedBlocks;
import nadiendev.ultimatearmoury.registry.ModOreBlocks;
import nadiendev.ultimatearmoury.registry.FluidsRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;

public class BlockModelProvider extends BlockStateProvider {

    public BlockModelProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, UltimateArmouryCore.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

        blockWithItem(BlocksAdds.MUSHASHITE_BLOCK);
        blockWithItem(BlocksAdds.JOANFOITE_BLOCK);
        blockWithItem(BlocksAdds.NADIENITE_BLOCK);
        blockWithItem(BlocksAdds.STEEL_BLOCK);
        blockWithItem(BlocksAdds.SEXY_XD);
        blockWithItem(BlocksAdds.NETHER_STAR_BLOCK);
        blockWithItem(BlocksAdds.ENDER_PEARL_BLOCK);

        for (var compressed : CompressedBlocks.all()) {
            blockWithItem(compressed);
        }

        blockWithItem(ModOreBlocks.MUSHASHITE_ORE);
        blockWithItem(ModOreBlocks.DEEPSLATE_MUSHASHITE_ORE);
        blockWithItem(ModOreBlocks.JOANFOITE_ORE);
        blockWithItem(ModOreBlocks.DEEPSLATE_JOANFOITE_ORE);
        blockWithItem(ModOreBlocks.NADIENITE_ORE);
        blockWithItem(ModOreBlocks.DEEPSLATE_NADIENITE_ORE);

        fluidBlock(FluidsRegistry.NADIENITE_FLUID_BLOCK,
                ResourceLocation.fromNamespaceAndPath(UltimateArmouryCore.MOD_ID, "block/nadienite_fluid"),
                ResourceLocation.fromNamespaceAndPath(UltimateArmouryCore.MOD_ID, "block/nadienite_flow"));
    }

    private void blockWithItem(DeferredHolder<Block, Block> deferredBlock) {
        simpleBlockWithItem(deferredBlock.get(), cubeAll(deferredBlock.get()));
    }

    private void fluidBlock(DeferredHolder<Block, LiquidBlock> deferredBlock,
                            ResourceLocation stillTexture,
                            ResourceLocation flowTexture) {
        ModelFile model = models().getBuilder(deferredBlock.getId().getPath())
                .texture("particle", stillTexture);
        simpleBlock(deferredBlock.get(), model);
    }

    private void customBlockWithItem(DeferredHolder<Block, Block> deferredBlock) {
        Block block = deferredBlock.get();
        String blockName = deferredBlock.getId().getPath();

        ModelFile blockModel = models().getExistingFile(
            ResourceLocation.fromNamespaceAndPath(UltimateArmouryCore.MOD_ID, "block/" + blockName)
        );

        simpleBlock(block, blockModel);

        simpleBlockItem(block, blockModel);
    }
}
