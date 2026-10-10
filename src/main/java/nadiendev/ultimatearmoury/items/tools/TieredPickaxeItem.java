package nadiendev.ultimatearmoury.items.tools;

import nadiendev.ultimatearmoury.items.ModTier;
import nadiendev.ultimatearmoury.registry.ModToolTiers;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class TieredPickaxeItem extends Item {

    private final ModTier tier;

    public TieredPickaxeItem(ModTier tier, Properties properties) {
        super(properties.pickaxe(ModToolTiers.of(tier), 1.0F, -2.8F).enchantable(tier.enchantmentValue()));
        this.tier = tier;
    }

    public ModTier modTier() {
        return tier;
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, @NotNull ServerLevel level, @NotNull Entity entity,
                              EquipmentSlot slot) {
        TieredToolBehaviour.keepEnchanted(stack, level, tier, true);
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
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context,
                                @NotNull TooltipDisplay display, @NotNull Consumer<Component> tooltip,
                                @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, context, display, tooltip, flag);
        TieredToolBehaviour.appendUnbreakableTooltip(tooltip);
    }

    @Override
    public boolean isFoil(@NotNull ItemStack stack) {
        return false;
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
}
