package nadiendev.ultimatearmoury.items.tools;

import nadiendev.ultimatearmoury.registry.ModToolTiers;

import nadiendev.ultimatearmoury.items.ModTier;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TieredPickaxeItem extends PickaxeItem {

    private final ModTier tier;

    public TieredPickaxeItem(ModTier tier, Properties properties) {
        super(ModToolTiers.of(tier), properties);
        this.tier = tier;
    }

    public ModTier modTier() {
        return tier;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        TieredToolBehaviour.keepEnchanted(stack, level, tier, true);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context,
                                @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag isAdvanced) {
        super.appendHoverText(stack, context, tooltipComponents, isAdvanced);
        TieredToolBehaviour.appendUnbreakableTooltip(tooltipComponents);
    }

    @Override
    public boolean isFoil(@NotNull ItemStack stack) {
        return false;
    }

    @Override
    public int getEnchantmentValue(@NotNull ItemStack stack) {
        return tier.enchantmentValue();
    }

    @Override
    public float getDestroySpeed(@NotNull ItemStack stack, @NotNull BlockState state) {
        float baseSpeed = super.getDestroySpeed(stack, state);
        if (baseSpeed > 1.0F) {
            return baseSpeed + TieredToolBehaviour.bonusDestroySpeed(tier);
        }
        return baseSpeed;
    }

    @Override
    public boolean mineBlock(@NotNull ItemStack stack, @NotNull Level level, @NotNull BlockState state,
                             @NotNull BlockPos pos, @NotNull LivingEntity entity) {
        return true;
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
