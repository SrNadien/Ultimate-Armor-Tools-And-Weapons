package nadiendev.ultimatearmoury.items.tools;

import nadiendev.ultimatearmoury.registry.ModToolTiers;

import nadiendev.ultimatearmoury.items.ModTier;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TieredAxeItem extends AxeItem {

    private final ModTier tier;

    public TieredAxeItem(ModTier tier, Properties properties) {
        super(ModToolTiers.of(tier),
                properties.attributes(AxeItem.createAttributes(ModToolTiers.of(tier), tier.swordDamage(), -3.0F))
                        .durability(0));
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
    public boolean hurtEnemy(@NotNull ItemStack stack, @NotNull LivingEntity target, @NotNull LivingEntity attacker) {

        if (target.isBlocking() && attacker.getRandom().nextFloat() < 0.25F) {
            target.stopUsingItem();
            target.level().playSound(null, target.getX(), target.getY(), target.getZ(),
                    SoundEvents.SHIELD_BREAK, target.getSoundSource(),
                    1.0F, 0.8F + target.level().random.nextFloat() * 0.4F);
        }
        return true;
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
    public boolean isDamageable(ItemStack stack) {
        return false;
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return 0;
    }

    @Override
    public boolean mineBlock(@NotNull ItemStack stack, @NotNull Level level, @NotNull BlockState state,
                             @NotNull BlockPos pos, @NotNull LivingEntity entity) {
        return true;
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack stack) {
        return stack.copy();
    }
}
