package nadiendev.ultimatearmoury.items.tools;

import nadiendev.ultimatearmoury.items.ModTier;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

import java.util.function.Consumer;

final class TieredToolBehaviour {

    private TieredToolBehaviour() {
    }

    static void keepEnchanted(ItemStack stack, Level level, ModTier tier, boolean withFortune) {
        if (level.isClientSide()) {
            return;
        }

        var registry = level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
        int levelWanted = tier.toolEnchantLevel();

        ensure(stack, registry.getOrThrow(Enchantments.EFFICIENCY), levelWanted);
        if (withFortune) {
            ensure(stack, registry.getOrThrow(Enchantments.FORTUNE), levelWanted);
        }

        if (stack.getDamageValue() > 0) {
            stack.setDamageValue(0);
        }
    }

    private static void ensure(ItemStack stack, Holder<Enchantment> enchantment, int levelWanted) {
        if (stack.getEnchantmentLevel(enchantment) < levelWanted) {
            stack.enchant(enchantment, levelWanted);
        }
    }

    static float bonusDestroySpeed(ModTier tier) {
        return tier.miningSpeed() * 5.0F;
    }

    static void appendUnbreakableTooltip(Consumer<Component> tooltip) {
        tooltip.accept(Component.translatable("tooltip.ultimatearmoury.unbreakable")
                .withStyle(style -> style.withColor(0xFFAA00)));
    }
}
