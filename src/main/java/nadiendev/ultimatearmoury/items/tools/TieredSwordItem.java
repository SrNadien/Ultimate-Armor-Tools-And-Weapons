package nadiendev.ultimatearmoury.items.tools;

import nadiendev.ultimatearmoury.registry.ModToolTiers;

import nadiendev.ultimatearmoury.items.ModTier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;

public class TieredSwordItem extends SwordItem {

    private final ModTier tier;

    public TieredSwordItem(ModTier tier, Properties properties) {

        super(ModToolTiers.of(tier),
                properties.attributes(SwordItem.createAttributes(ModToolTiers.of(tier), tier.swordDamage(), 100.0F)));
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
