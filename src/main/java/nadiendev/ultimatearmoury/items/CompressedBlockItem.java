package nadiendev.ultimatearmoury.items;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.text.NumberFormat;
import net.minecraft.world.item.component.TooltipDisplay;

import java.util.function.Consumer;

public class CompressedBlockItem extends BlockItem {

    private final String quantity;

    public CompressedBlockItem(Block block, Properties properties, int level) {
        super(block, properties);
        this.quantity = NumberFormat.getInstance().format(Math.pow(9, level));
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context,
                                @NotNull TooltipDisplay display, @NotNull Consumer<Component> tooltip, @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, context, display, tooltip, flag);
        tooltip.accept(Component.translatable("tooltip.ultimatearmoury.quantity", this.quantity));
    }
}
