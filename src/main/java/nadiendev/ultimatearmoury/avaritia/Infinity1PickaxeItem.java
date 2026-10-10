package nadiendev.ultimatearmoury.avaritia;

import nadiendev.ultimatearmoury.registry.ModDataComponents;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class Infinity1PickaxeItem extends PickaxeItem {

    public Infinity1PickaxeItem(Tier tier, Properties properties) {
        super(tier, properties.attributes(PickaxeItem.createAttributes(tier, 8.0F, -2.8F)));
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
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (player.isShiftKeyDown()) {
            if (!level.isClientSide) {
                boolean currentlySilkTouch = stack.getOrDefault(ModDataComponents.SILK_TOUCH_MODE, false);
                stack.set(ModDataComponents.SILK_TOUCH_MODE, !currentlySilkTouch);

                Component modeText = !currentlySilkTouch
                        ? Component.translatable("tooltip.ultimatearmoury.infinity_1_pickaxe.mode_silk")
                        : Component.translatable("tooltip.ultimatearmoury.infinity_1_pickaxe.mode_fortune");

                player.displayClientMessage(modeText.copy().withStyle(ChatFormatting.GOLD), true);

                level.playSound(null, player.getX(), player.getY(), player.getZ(),
                        SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 1.0F, 1.0F);
            }
            return InteractionResultHolder.success(stack);
        }

        return InteractionResultHolder.pass(stack);
    }

    @Override
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state) {
        return state.is(BlockTags.MINEABLE_WITH_PICKAXE) ||
               state.is(BlockTags.MINEABLE_WITH_AXE) ||
               state.is(BlockTags.MINEABLE_WITH_SHOVEL);
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        if (this.isCorrectToolForDrops(stack, state)) {
            return 100.0F;
        }
        return super.getDestroySpeed(stack, state);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        boolean silkTouch = stack.getOrDefault(ModDataComponents.SILK_TOUCH_MODE, false);

        tooltip.add(Component.translatable("tooltip.ultimatearmoury.infinity_1_pickaxe.multitool")
                .withStyle(ChatFormatting.AQUA));

        Component currentMode = silkTouch
                ? Component.translatable("tooltip.ultimatearmoury.infinity_1_pickaxe.mode_silk")
                : Component.translatable("tooltip.ultimatearmoury.infinity_1_pickaxe.mode_fortune");
        tooltip.add(Component.translatable("tooltip.ultimatearmoury.infinity_1_pickaxe.current_mode", currentMode)
                .withStyle(ChatFormatting.GOLD));

        tooltip.add(Component.translatable("tooltip.ultimatearmoury.infinity_1_pickaxe.change_mode")
                .withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("tooltip.ultimatearmoury.infinity_1_pickaxe.durability")
                .withStyle(ChatFormatting.GREEN));

        super.appendHoverText(stack, context, tooltip, flag);
    }
}
