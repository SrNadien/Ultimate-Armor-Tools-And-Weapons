package nadiendev.ultimatearmoury.avaritia;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class Infinity1HoeItem extends HoeItem {

    public Infinity1HoeItem(Tier tier, Properties properties) {
        super(tier, properties.attributes(HoeItem.createAttributes(tier, 0.0F, -3.0F)));
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
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("tooltip.ultimatearmoury.infinity_1_hoe.durability").withStyle(ChatFormatting.GREEN));
        tooltip.add(Component.translatable("tooltip.ultimatearmoury.infinity_1_hoe.farming").withStyle(ChatFormatting.AQUA));

        super.appendHoverText(stack, context, tooltip, flag);
    }
}
