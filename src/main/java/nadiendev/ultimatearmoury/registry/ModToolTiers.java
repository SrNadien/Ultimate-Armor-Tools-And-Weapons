package nadiendev.ultimatearmoury.registry;

import nadiendev.ultimatearmoury.datagen.providers.ModBlockTags;
import nadiendev.ultimatearmoury.datagen.providers.ModItemTags;
import nadiendev.ultimatearmoury.items.ModTier;
import net.minecraft.world.item.ToolMaterial;

import java.util.EnumMap;
import java.util.Map;

public class ModToolTiers {

    private static final Map<ModTier, ToolMaterial> TIERS = new EnumMap<>(ModTier.class);

    static {
        for (ModTier tier : ModTier.values()) {
            TIERS.put(tier, new ToolMaterial(
                    ModBlockTags.Blocks.INCORRECT_FOR_NADIENITE_TOOL,
                    tier.durability(),
                    tier.miningSpeed(),
                    tier.attackDamageBonus(),
                    tier.enchantmentValue(),
                    ModItemTags.Items.INGOTS_NADIENITE
            ));
        }
    }

    public static final ToolMaterial MUSHASHITE = TIERS.get(ModTier.MUSHASHITE);
    public static final ToolMaterial JOANFOITE = TIERS.get(ModTier.JOANFOITE);
    public static final ToolMaterial NADIENITE = TIERS.get(ModTier.NADIENITE);

    public static ToolMaterial of(ModTier tier) {
        return TIERS.get(tier);
    }

    private ModToolTiers() {
    }
}
