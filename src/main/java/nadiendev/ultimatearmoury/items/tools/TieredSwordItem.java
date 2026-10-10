package nadiendev.ultimatearmoury.items.tools;

import nadiendev.ultimatearmoury.items.ModTier;
import nadiendev.ultimatearmoury.registry.ModToolTiers;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class TieredSwordItem extends Item {

    private final ModTier tier;

    public TieredSwordItem(ModTier tier, Properties properties) {
        super(properties.sword(ModToolTiers.of(tier), tier.swordDamage(), 100.0F)
                .enchantable(tier.enchantmentValue()));
        this.tier = tier;
    }

    public ModTier modTier() {
        return tier;
    }

    @Override
    public boolean isDamageable(ItemStack stack) {
        return false;
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return 0;
    }
}
