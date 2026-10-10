package nadiendev.ultimatearmoury.registry;

import nadiendev.ultimatearmoury.items.ModTier;
import nadiendev.ultimatearmoury.datagen.providers.ModBlockTags;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.SimpleTier;

import java.util.EnumMap;
import java.util.Map;

public class ModToolTiers {

    private static final Map<ModTier, Tier> TIERS = new EnumMap<>(ModTier.class);

    static {
        for (ModTier tier : ModTier.values()) {
            TIERS.put(tier, new SimpleTier(
                    ModBlockTags.Blocks.INCORRECT_FOR_NADIENITE_TOOL,
                    tier.durability(),
                    tier.miningSpeed(),
                    tier.attackDamageBonus(),
                    tier.enchantmentValue(),
                    () -> Ingredient.of(ItemsAdds.NADIENITE_INGOT.get())
            ));
        }
    }

    public static final Tier MUSHASHITE = TIERS.get(ModTier.MUSHASHITE);
    public static final Tier JOANFOITE = TIERS.get(ModTier.JOANFOITE);
    public static final Tier NADIENITE = TIERS.get(ModTier.NADIENITE);

    public static Tier of(ModTier tier) {
        return TIERS.get(tier);
    }

    private ModToolTiers() {
    }
}
