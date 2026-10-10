package nadiendev.ultimatearmoury.avaritia;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.SimpleTier;

public class InfinityTier {

    public static final Tier INFINITY = new SimpleTier(
        BlockTags.INCORRECT_FOR_NETHERITE_TOOL,
        10000,
        100.0F,
        60.0F,
        30,
        () -> Ingredient.EMPTY
    );

    public static final Tier INFINITY_BALANCED = new SimpleTier(
        BlockTags.INCORRECT_FOR_NETHERITE_TOOL,
        10000,
        100.0F,
        400.0F,
        30,
        () -> Ingredient.EMPTY
    );
}
