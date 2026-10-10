package nadiendev.ultimatearmoury.items.tools;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.level.Level;
import nadiendev.ultimatearmoury.entities.ChanclaEntity;

public class ChanclaItem extends Item {

    public ChanclaItem(ToolMaterial tier, Properties properties) {
        super(properties.sword(tier, 200.0F, 100.0F));
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
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.TRIDENT_THROW, SoundSource.PLAYERS, 1.0F, 1.0F);

        if (!level.isClientSide()) {

            ChanclaEntity chancla = new ChanclaEntity(level, player);
            chancla.setItem(itemstack.copy());
            chancla.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 2.5F, 1.0F);
            level.addFreshEntity(chancla);

            if (!player.getAbilities().instabuild) {
                itemstack.shrink(1);
            }

            player.awardStat(Stats.ITEM_USED.get(this));
        }

        return InteractionResult.SUCCESS;
    }
}
