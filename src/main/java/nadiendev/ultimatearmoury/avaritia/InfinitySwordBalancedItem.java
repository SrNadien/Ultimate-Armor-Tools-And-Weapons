// package nadiendev.ultimatearmoury.avaritia;
// 
// import net.minecraft.ChatFormatting;
// import net.minecraft.network.chat.Component;
// import net.minecraft.world.item.ItemStack;
// import net.minecraft.world.item.SwordItem;
// import net.minecraft.world.item.Tier;
// import net.minecraft.world.item.TooltipFlag;
// 
// import java.util.List;
// 
// public class InfinitySwordBalancedItem extends SwordItem {
// 
//     public InfinitySwordBalancedItem(Tier tier, Properties properties) {
// 
//         super(tier, properties.attributes(SwordItem.createAttributes(tier, 399, -2.4F)));
//     }
// 
//     @Override
//     public boolean isDamageable(ItemStack stack) {
//         return false;
//     }
// 
//     @Override
//     public int getMaxDamage(ItemStack stack) {
//         return 0;
//     }
// 
//     @Override
//     public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
//         tooltip.add(Component.translatable("tooltip.ultimatearmoury.infinity_sword_balanced.title").withStyle(ChatFormatting.LIGHT_PURPLE));
//         tooltip.add(Component.translatable("tooltip.ultimatearmoury.infinity_sword_balanced.damage").withStyle(ChatFormatting.DARK_RED));
//         tooltip.add(Component.translatable("tooltip.ultimatearmoury.infinity_sword_balanced.durability").withStyle(ChatFormatting.GREEN));
// 
//         super.appendHoverText(stack, context, tooltip, flag);
//     }
// }
