package nadiendev.ultimatearmoury.registry;

import nadiendev.ultimatearmoury.items.armor.HatItem;
import nadiendev.ultimatearmoury.items.armor.TieredArmorItem;

import nadiendev.ultimatearmoury.UltimateArmouryCore;
import nadiendev.ultimatearmoury.items.ModTier;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.Unbreakable;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

public class ArmorAdds {

    public static final DeferredRegister<Item> ARMOR_ITEMS =
            DeferredRegister.create(BuiltInRegistries.ITEM, UltimateArmouryCore.MOD_ID);

    public static final DeferredHolder<Item, Item> MUSHASHITE_GORRO =
            piece(ModTier.MUSHASHITE, ArmorItem.Type.HELMET, "gorro");
    public static final DeferredHolder<Item, Item> MUSHASHITE_REMERA =
            piece(ModTier.MUSHASHITE, ArmorItem.Type.CHESTPLATE, "remera");
    public static final DeferredHolder<Item, Item> MUSHASHITE_GAYUMBOS =
            piece(ModTier.MUSHASHITE, ArmorItem.Type.LEGGINGS, "gayumbos");
    public static final DeferredHolder<Item, Item> MUSHASHITE_MEDIAS =
            piece(ModTier.MUSHASHITE, ArmorItem.Type.BOOTS, "medias");

    public static final DeferredHolder<Item, Item> JOANFOITE_GORRO =
            piece(ModTier.JOANFOITE, ArmorItem.Type.HELMET, "gorro");
    public static final DeferredHolder<Item, Item> JOANFOITE_REMERA =
            piece(ModTier.JOANFOITE, ArmorItem.Type.CHESTPLATE, "remera");
    public static final DeferredHolder<Item, Item> JOANFOITE_GAYUMBOS =
            piece(ModTier.JOANFOITE, ArmorItem.Type.LEGGINGS, "gayumbos");
    public static final DeferredHolder<Item, Item> JOANFOITE_MEDIAS =
            piece(ModTier.JOANFOITE, ArmorItem.Type.BOOTS, "medias");

    public static final DeferredHolder<Item, Item> NADIENITE_GORRO =
            piece(ModTier.NADIENITE, ArmorItem.Type.HELMET, "gorro");
    public static final DeferredHolder<Item, Item> NADIENITE_REMERA =
            piece(ModTier.NADIENITE, ArmorItem.Type.CHESTPLATE, "remera");
    public static final DeferredHolder<Item, Item> NADIENITE_GAYUMBOS =
            piece(ModTier.NADIENITE, ArmorItem.Type.LEGGINGS, "gayumbos");
    public static final DeferredHolder<Item, Item> NADIENITE_MEDIAS =
            piece(ModTier.NADIENITE, ArmorItem.Type.BOOTS, "medias");

    private static DeferredHolder<Item, Item> piece(ModTier tier, ArmorItem.Type type, String suffix) {
        return ARMOR_ITEMS.register(
                tier.id() + "_" + suffix,
                // Solo el gorro es 3D. El resto usa el sistema de capas planas de
                // Minecraft, que se pinta sobre el cuerpo y por eso no puede
                // despegarse al caminar.
                () -> type == ArmorItem.Type.HELMET
                        ? new HatItem(tier, type, properties(tier))
                        : new TieredArmorItem(tier, type, properties(tier))
        );
    }

    private static Item.Properties properties(ModTier tier) {
        return new Item.Properties()
                .stacksTo(1)
                .fireResistant()
                .rarity(tier.rarity())
                .component(DataComponents.UNBREAKABLE, new Unbreakable(true));
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
