package nadiendev.ultimatearmoury.registry;

import nadiendev.ultimatearmoury.items.ModTier;

import nadiendev.ultimatearmoury.UltimateArmouryCore;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ItemsAdds {
    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(UltimateArmouryCore.MOD_ID);

    public static final DeferredHolder<Item, Item> MUSHASHITE_INGOT = registerMaterial("mushashite_ingot");
    public static final DeferredHolder<Item, Item> MUSHASHITE_NUGGET = registerMaterial("mushashite_nugget");
    public static final DeferredHolder<Item, Item> RAW_MUSHASHITE = registerMaterial("raw_mushashite");

    public static final DeferredHolder<Item, Item> JOANFOITE_INGOT = registerMaterial("joanfoite_ingot");
    public static final DeferredHolder<Item, Item> JOANFOITE_NUGGET = registerMaterial("joanfoite_nugget");
    public static final DeferredHolder<Item, Item> RAW_JOANFOITE = registerMaterial("raw_joanfoite");

    public static final DeferredHolder<Item, Item> NADIENITE_INGOT = registerMaterial("nadienite_ingot");
    public static final DeferredHolder<Item, Item> RAW_NADIENITE = registerMaterial("raw_nadienite");

    public static final DeferredHolder<Item, Item> STEEL_NUGGET = registerMaterial("steel_nugget");
    public static final DeferredHolder<Item, Item> NETHERITE_NUGGET = registerMaterial("netherite_nugget");

    public static final DeferredHolder<Item, Item> STEEL_INGOT = reg(
        "steel_ingot",
        () -> new Item(props()
                .stacksTo(64)
                .fireResistant()
                .rarity(Rarity.COMMON)
        )
    );

    public static final DeferredHolder<Item, Item> NADIENITE_NUGGET = registerMaterial("nadienite_nugget");

    public static final DeferredHolder<Item, Item> CORAZON_DE_LOS_CAIDOS = registerCorazon("corazon_de_los_caidos");

    public static final DeferredHolder<Item, Item> CORAZON_DE_LOS_FANTASMAS = registerCorazon("corazon_de_los_fantasmas");

    public static final DeferredHolder<Item, Item> CORAZON_DE_LA_ELITE = registerCorazon("corazon_de_la_elite");

    public static final DeferredHolder<Item, Item> NETHERITE_DAGGER = reg(
        "netherite_dagger",
        () -> new Item(props()
                .stacksTo(1)
                .fireResistant()
                .rarity(Rarity.EPIC)
        )
    );

    public static final DeferredHolder<Item, BucketItem> NADIENITE_FLUID_BUCKET = reg(
        "nadienite_fluid_bucket",
        () -> new BucketItem(
                FluidsRegistry.NADIENITE_FLUID_SOURCE.get(),
                props()
                        .craftRemainder(Items.BUCKET)
                        .stacksTo(1)
        )
    );

    private static final ThreadLocal<Item.Properties> CURRENT_PROPERTIES = new ThreadLocal<>();

    private static Item.Properties props() {
        Item.Properties properties = CURRENT_PROPERTIES.get();
        return properties != null ? properties : new Item.Properties();
    }

    private static <T extends Item> DeferredHolder<Item, T> reg(String name, Supplier<T> supplier) {
        return ITEMS.registerItem(name, properties -> {
            CURRENT_PROPERTIES.set(properties);
            try {
                return supplier.get();
            } finally {
                CURRENT_PROPERTIES.remove();
            }
        });
    }

    public static DeferredHolder<Item, Item> ingotOf(ModTier tier) {
        return switch (tier) {
            case MUSHASHITE -> MUSHASHITE_INGOT;
            case JOANFOITE -> JOANFOITE_INGOT;
            case NADIENITE -> NADIENITE_INGOT;
        };
    }

    public static DeferredHolder<Item, Item> nuggetOf(ModTier tier) {
        return switch (tier) {
            case MUSHASHITE -> MUSHASHITE_NUGGET;
            case JOANFOITE -> JOANFOITE_NUGGET;
            case NADIENITE -> NADIENITE_NUGGET;
        };
    }

    public static DeferredHolder<Item, Item> rawOreOf(ModTier tier) {
        return switch (tier) {
            case MUSHASHITE -> RAW_MUSHASHITE;
            case JOANFOITE -> RAW_JOANFOITE;
            case NADIENITE -> RAW_NADIENITE;
        };
    }

    public static DeferredHolder<Item, Item> heartOf(ModTier tier) {
        return switch (tier) {
            case MUSHASHITE -> CORAZON_DE_LOS_CAIDOS;
            case JOANFOITE -> CORAZON_DE_LOS_FANTASMAS;
            case NADIENITE -> CORAZON_DE_LA_ELITE;
        };
    }

    private static DeferredHolder<Item, Item> registerMaterial(String name) {
        return reg(name, () -> new Item(props()
                .stacksTo(64)
                .fireResistant()
                .rarity(Rarity.COMMON)
        ));
    }

    private static DeferredHolder<Item, Item> registerCorazon(String name) {
        return reg(name, () -> new Item(props()
                .stacksTo(16)
                .fireResistant()
                .rarity(Rarity.EPIC)
        ));
    }

    public static DeferredHolder<Item, Item> registerItem(String name) {
        return reg(name, () -> new Item(props()));
    }

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
