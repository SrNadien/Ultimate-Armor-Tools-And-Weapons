package nadiendev.ultimatearmoury.datagen.providers;

import nadiendev.ultimatearmoury.UltimateArmouryCore;
import nadiendev.ultimatearmoury.registry.BlocksAdds;
import nadiendev.ultimatearmoury.registry.ModOreBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModBlockTags {

    private ModBlockTags() {
    }

    public static class Blocks {

        private Blocks() {
        }

        public static final TagKey<Block> NEEDS_NADIENITE_TOOL = tag("needs_nadienite_tool");
        public static final TagKey<Block> INCORRECT_FOR_NADIENITE_TOOL = tag("incorrect_for_nadienite_tool");

        public static final TagKey<Block> STORAGE_BLOCKS_MUSHASHITE = commonTag("storage_blocks/mushashite");
        public static final TagKey<Block> STORAGE_BLOCKS_JOANFOITE = commonTag("storage_blocks/joanfoite");
        public static final TagKey<Block> STORAGE_BLOCKS_NADIENITE = commonTag("storage_blocks/nadienite");
        public static final TagKey<Block> STORAGE_BLOCKS_STEEL = commonTag("storage_blocks/steel");

        private static TagKey<Block> commonTag(String name) {
            return BlockTags.create(Identifier.fromNamespaceAndPath("c", name));
        }

        private static TagKey<Block> tag(String name) {
            return BlockTags.create(Identifier.fromNamespaceAndPath(UltimateArmouryCore.MOD_ID, name));
        }
    }

    public static class Provider extends BlockTagsProvider {

        public Provider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
            super(output, lookupProvider, UltimateArmouryCore.MOD_ID);
        }

        @Override
        protected void addTags(HolderLookup.Provider provider) {

            List<Block> tierBlocks = new ArrayList<>(ModOreBlocks.allOres());
            tierBlocks.add(BlocksAdds.MUSHASHITE_BLOCK.get());
            tierBlocks.add(BlocksAdds.JOANFOITE_BLOCK.get());
            tierBlocks.add(BlocksAdds.NADIENITE_BLOCK.get());
            Block[] tierBlockArray = tierBlocks.toArray(new Block[0]);

            this.tag(Blocks.NEEDS_NADIENITE_TOOL)
                .addTag(Tags.Blocks.NEEDS_NETHERITE_TOOL)
                .add(tierBlockArray);

            this.tag(Tags.Blocks.NEEDS_NETHERITE_TOOL)
                .add(tierBlockArray);

            this.tag(Blocks.INCORRECT_FOR_NADIENITE_TOOL);

            this.tag(BlockTags.INCORRECT_FOR_WOODEN_TOOL).add(tierBlockArray);
            this.tag(BlockTags.INCORRECT_FOR_STONE_TOOL).add(tierBlockArray);
            this.tag(BlockTags.INCORRECT_FOR_IRON_TOOL).add(tierBlockArray);
            this.tag(BlockTags.INCORRECT_FOR_GOLD_TOOL).add(tierBlockArray);
            this.tag(BlockTags.INCORRECT_FOR_DIAMOND_TOOL).add(tierBlockArray);

            this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(tierBlockArray)
                .add(BlocksAdds.STEEL_BLOCK.get())
                .add(BlocksAdds.SEXY_XD.get());

            this.tag(Blocks.STORAGE_BLOCKS_MUSHASHITE)
                .add(BlocksAdds.MUSHASHITE_BLOCK.get());

            this.tag(Blocks.STORAGE_BLOCKS_JOANFOITE)
                .add(BlocksAdds.JOANFOITE_BLOCK.get());

            this.tag(Blocks.STORAGE_BLOCKS_NADIENITE)
                .add(BlocksAdds.NADIENITE_BLOCK.get());

            this.tag(Blocks.STORAGE_BLOCKS_STEEL)
                .add(BlocksAdds.STEEL_BLOCK.get());
        }
    }
}
