package nadiendev.ultimatearmoury.datagen.providers;

import nadiendev.ultimatearmoury.items.ModTier;
import nadiendev.ultimatearmoury.registry.BlocksAdds;
import nadiendev.ultimatearmoury.registry.CompressedBlocks;
import nadiendev.ultimatearmoury.registry.ItemsAdds;
import nadiendev.ultimatearmoury.registry.ModOreBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class UltimateArmouryBlockLootTables extends BlockLootSubProvider {

    public UltimateArmouryBlockLootTables(HolderLookup.Provider provider) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), provider);
    }

    @Override
    protected void generate() {
        dropSelf(BlocksAdds.STEEL_BLOCK.get());
        dropSelf(BlocksAdds.SEXY_XD.get());
        dropSelf(BlocksAdds.MUSHASHITE_BLOCK.get());
        dropSelf(BlocksAdds.JOANFOITE_BLOCK.get());
        dropSelf(BlocksAdds.NADIENITE_BLOCK.get());
        dropSelf(BlocksAdds.ENDER_PEARL_BLOCK.get());
        dropSelf(BlocksAdds.NETHER_STAR_BLOCK.get());

        for (var compressed : CompressedBlocks.all()) {
            dropSelf(compressed.get());
        }

        for (ModTier tier : ModTier.values()) {
            add(ModOreBlocks.stoneOreOf(tier).get(),
                    block -> createOreDrop(block, ItemsAdds.rawOreOf(tier).get()));
            add(ModOreBlocks.deepslateOreOf(tier).get(),
                    block -> createOreDrop(block, ItemsAdds.rawOreOf(tier).get()));
        }
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        List<Block> blocks = new ArrayList<>(List.of(
                BlocksAdds.STEEL_BLOCK.get(),
                BlocksAdds.SEXY_XD.get(),
                BlocksAdds.MUSHASHITE_BLOCK.get(),
                BlocksAdds.JOANFOITE_BLOCK.get(),
                BlocksAdds.NADIENITE_BLOCK.get(),
                BlocksAdds.ENDER_PEARL_BLOCK.get(),
                BlocksAdds.NETHER_STAR_BLOCK.get()
        ));
        CompressedBlocks.all().forEach(holder -> blocks.add(holder.get()));
        blocks.addAll(ModOreBlocks.allOres());
        return blocks;
    }
}
