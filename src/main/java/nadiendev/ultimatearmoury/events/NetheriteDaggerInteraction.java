package nadiendev.ultimatearmoury.events;

import nadiendev.ultimatearmoury.registry.ItemsAdds;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@EventBusSubscriber(modid = nadiendev.ultimatearmoury.UltimateArmouryCore.MOD_ID)
public class NetheriteDaggerInteraction {

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {

        if (event.getHand() != InteractionHand.MAIN_HAND) return;

        Player player = event.getEntity();
        Level level = event.getLevel();
        ItemStack held = player.getItemInHand(InteractionHand.MAIN_HAND);

        if (!held.is(Items.NETHERITE_SWORD)) return;

        if (!level.getBlockState(event.getPos()).is(Blocks.NETHERITE_BLOCK)) return;

        if (level.isClientSide()) return;

        held.shrink(1);

        ItemStack dagger = new ItemStack(ItemsAdds.NETHERITE_DAGGER.get());
        if (!player.getInventory().add(dagger)) {
            player.drop(dagger, false);
        }

        event.setCanceled(true);
    }
}
