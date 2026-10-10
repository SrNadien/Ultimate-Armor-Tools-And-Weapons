package nadiendev.ultimatearmoury.registry;

import nadiendev.ultimatearmoury.items.armor.TieredArmorItem;
import nadiendev.ultimatearmoury.items.armor.WizardHatItem;

import nadiendev.ultimatearmoury.UltimateArmouryCore;
import nadiendev.ultimatearmoury.items.ModTier;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.Item;
import net.minecraft.util.Unit;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

public class ArmorAdds {

    public static final DeferredRegister.Items ARMOR_ITEMS =
            DeferredRegister.createItems(UltimateArmouryCore.MOD_ID);

    public static final DeferredHolder<Item, Item> MUSHASHITE_GORRO =
            piece(ModTier.MUSHASHITE, ArmorType.HELMET, "gorro");
    public static final DeferredHolder<Item, Item> MUSHASHITE_REMERA =
            piece(ModTier.MUSHASHITE, ArmorType.CHESTPLATE, "remera");
    public static final DeferredHolder<Item, Item> MUSHASHITE_GAYUMBOS =
            piece(ModTier.MUSHASHITE, ArmorType.LEGGINGS, "gayumbos");
    public static final DeferredHolder<Item, Item> MUSHASHITE_MEDIAS =
            piece(ModTier.MUSHASHITE, ArmorType.BOOTS, "medias");

    public static final DeferredHolder<Item, Item> JOANFOITE_GORRO =
            piece(ModTier.JOANFOITE, ArmorType.HELMET, "gorro");
    public static final DeferredHolder<Item, Item> JOANFOITE_REMERA =
            piece(ModTier.JOANFOITE, ArmorType.CHESTPLATE, "remera");
    public static final DeferredHolder<Item, Item> JOANFOITE_GAYUMBOS =
            piece(ModTier.JOANFOITE, ArmorType.LEGGINGS, "gayumbos");
    public static final DeferredHolder<Item, Item> JOANFOITE_MEDIAS =
            piece(ModTier.JOANFOITE, ArmorType.BOOTS, "medias");

    public static final DeferredHolder<Item, Item> NADIENITE_GORRO =
            piece(ModTier.NADIENITE, ArmorType.HELMET, "gorro");
    public static final DeferredHolder<Item, Item> NADIENITE_REMERA =
            piece(ModTier.NADIENITE, ArmorType.CHESTPLATE, "remera");
    public static final DeferredHolder<Item, Item> NADIENITE_GAYUMBOS =
            piece(ModTier.NADIENITE, ArmorType.LEGGINGS, "gayumbos");
    public static final DeferredHolder<Item, Item> NADIENITE_MEDIAS =
            piece(ModTier.NADIENITE, ArmorType.BOOTS, "medias");

    private static DeferredHolder<Item, Item> piece(ModTier tier, ArmorType type, String suffix) {
        return ARMOR_ITEMS.registerItem(
                tier.id() + "_" + suffix,
                props -> type == ArmorType.HELMET
                        ? new WizardHatItem(tier, props)
                        : new TieredArmorItem(tier, type, props),
                () -> properties(tier)
        );
    }

    private static Item.Properties properties(ModTier tier) {
        return new Item.Properties()
                .stacksTo(1)
                .fireResistant()
                .rarity(tier.rarity())
                .component(DataComponents.UNBREAKABLE, Unit.INSTANCE);
    }

    public static List<DeferredHolder<Item, Item>> piecesOf(ModTier tier) {
        return switch (tier) {
            case MUSHASHITE -> List.of(MUSHASHITE_GORRO, MUSHASHITE_REMERA, MUSHASHITE_GAYUMBOS, MUSHASHITE_MEDIAS);
            case JOANFOITE -> List.of(JOANFOITE_GORRO, JOANFOITE_REMERA, JOANFOITE_GAYUMBOS, JOANFOITE_MEDIAS);
            case NADIENITE -> List.of(NADIENITE_GORRO, NADIENITE_REMERA, NADIENITE_GAYUMBOS, NADIENITE_MEDIAS);
        };
    }

    public static void register(IEventBus eventBus) {
        ARMOR_ITEMS.register(eventBus);
    }
}
