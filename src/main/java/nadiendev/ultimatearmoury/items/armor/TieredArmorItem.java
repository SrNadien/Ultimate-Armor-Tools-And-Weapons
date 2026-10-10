package nadiendev.ultimatearmoury.items.armor;

import nadiendev.ultimatearmoury.items.ModTier;
import nadiendev.ultimatearmoury.registry.ModArmorMaterials;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.equipment.ArmorType;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class TieredArmorItem extends Item {

    public static final int BOOTS = 0;
    public static final int LEGGINGS = 1;
    public static final int CHESTPLATE = 2;
    public static final int HELMET = 3;

    private final ModTier tier;
    private final ArmorType armorType;

    public TieredArmorItem(ModTier tier, ArmorType armorType, Properties properties) {
        super(properties.humanoidArmor(ModArmorMaterials.of(tier), armorType));
        this.tier = tier;
        this.armorType = armorType;
    }

    public ModTier modTier() {
        return tier;
    }

    public ArmorType armorType() {
        return armorType;
    }

    private static EquipmentSlot slotOf(int armorSlot) {
        return switch (armorSlot) {
            case BOOTS -> EquipmentSlot.FEET;
            case LEGGINGS -> EquipmentSlot.LEGS;
            case CHESTPLATE -> EquipmentSlot.CHEST;
            default -> EquipmentSlot.HEAD;
        };
    }

    public static boolean wears(LivingEntity entity, ModTier tier, int armorSlot) {
        return entity.getItemBySlot(slotOf(armorSlot)).getItem() instanceof TieredArmorItem piece
                && piece.tier == tier;
    }

    public static boolean wearsPair(LivingEntity entity, ModTier tier, int slotA, int slotB) {
        return wears(entity, tier, slotA) && wears(entity, tier, slotB);
    }

    public static boolean wearsFullSet(LivingEntity entity, ModTier tier) {
        return wears(entity, tier, HELMET)
                && wears(entity, tier, CHESTPLATE)
                && wears(entity, tier, LEGGINGS)
                && wears(entity, tier, BOOTS);
    }

    @Override
    public boolean makesPiglinsNeutral(@NotNull ItemStack stack, @NotNull LivingEntity wearer) {
        return tier != ModTier.MUSHASHITE && wearsFullSet(wearer, tier);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context,
                                @NotNull TooltipDisplay display, @NotNull Consumer<Component> tooltip,
                                @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, context, display, tooltip, flag);

        tooltip.accept(Component.translatable("tooltip.ultimatearmoury." + tier.id() + "_" + pieceKey())
                .withStyle(ChatFormatting.AQUA));

        String[] setKeys = setBonusKeys();
        if (setKeys.length > 0) {
            tooltip.accept(Component.translatable("tooltip.ultimatearmoury.set_bonus")
                    .withStyle(ChatFormatting.GOLD));
            for (String key : setKeys) {
                tooltip.accept(Component.literal(" ")
                        .append(Component.translatable(key))
                        .withStyle(ChatFormatting.LIGHT_PURPLE));
            }
        }
    }

    private String pieceKey() {
        return switch (armorType) {
            case HELMET -> "gorro";
            case CHESTPLATE -> "remera";
            case LEGGINGS -> "gayumbos";
            case BOOTS -> "medias";
            default -> "gorro";
        };
    }

    private String[] setBonusKeys() {
        return switch (tier) {
            case MUSHASHITE -> new String[0];
            case JOANFOITE -> new String[]{
                    "tooltip.ultimatearmoury.set.piglins",
                    "tooltip.ultimatearmoury.set.creepers"
            };
            case NADIENITE -> new String[]{
                    "tooltip.ultimatearmoury.set.piglins",
                    "tooltip.ultimatearmoury.set.creepers",
                    "tooltip.ultimatearmoury.set.endermen",
                    "tooltip.ultimatearmoury.set.phantoms"
            };
        };
    }
}
