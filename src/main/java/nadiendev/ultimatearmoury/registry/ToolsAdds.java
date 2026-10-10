package nadiendev.ultimatearmoury.registry;

import nadiendev.ultimatearmoury.items.tools.ChanclaItem;
import nadiendev.ultimatearmoury.items.tools.TieredAxeItem;
import nadiendev.ultimatearmoury.items.tools.TieredHoeItem;
import nadiendev.ultimatearmoury.items.tools.TieredPickaxeItem;
import nadiendev.ultimatearmoury.items.tools.TieredShovelItem;
import nadiendev.ultimatearmoury.items.tools.TieredSwordItem;

import nadiendev.ultimatearmoury.UltimateArmouryCore;
import nadiendev.ultimatearmoury.items.ModTier;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.component.Unbreakable;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.function.Function;

public class ToolsAdds {

    public static final DeferredRegister<Item> TOOL_ITEMS =
            DeferredRegister.create(BuiltInRegistries.ITEM, UltimateArmouryCore.MOD_ID);

    public static final DeferredHolder<Item, Item> MUSHASHITE_SWORD = sword(ModTier.MUSHASHITE);
    public static final DeferredHolder<Item, Item> MUSHASHITE_PICKAXE = pickaxe(ModTier.MUSHASHITE);
    public static final DeferredHolder<Item, Item> MUSHASHITE_AXE = axe(ModTier.MUSHASHITE);
    public static final DeferredHolder<Item, Item> MUSHASHITE_SHOVEL = shovel(ModTier.MUSHASHITE);
    public static final DeferredHolder<Item, Item> MUSHASHITE_HOE = hoe(ModTier.MUSHASHITE);

    public static final DeferredHolder<Item, Item> JOANFOITE_SWORD = sword(ModTier.JOANFOITE);
    public static final DeferredHolder<Item, Item> JOANFOITE_PICKAXE = pickaxe(ModTier.JOANFOITE);
    public static final DeferredHolder<Item, Item> JOANFOITE_AXE = axe(ModTier.JOANFOITE);
    public static final DeferredHolder<Item, Item> JOANFOITE_SHOVEL = shovel(ModTier.JOANFOITE);
    public static final DeferredHolder<Item, Item> JOANFOITE_HOE = hoe(ModTier.JOANFOITE);

    public static final DeferredHolder<Item, Item> NADIENITE_SWORD = sword(ModTier.NADIENITE);
    public static final DeferredHolder<Item, Item> NADIENITE_PICKAXE = pickaxe(ModTier.NADIENITE);
    public static final DeferredHolder<Item, Item> NADIENITE_AXE = axe(ModTier.NADIENITE);
    public static final DeferredHolder<Item, Item> NADIENITE_SHOVEL = shovel(ModTier.NADIENITE);
    public static final DeferredHolder<Item, Item> NADIENITE_HOE = hoe(ModTier.NADIENITE);

    public static final DeferredHolder<Item, Item> CHANCLA = TOOL_ITEMS.register(
            "chancla",
            () -> new ChanclaItem(
                    ModToolTiers.NADIENITE,
                    new Item.Properties()
                            .stacksTo(1)
                            .fireResistant()
                            .rarity(Rarity.EPIC)
                            .component(DataComponents.UNBREAKABLE, new Unbreakable(true))
            )
    );

    private static DeferredHolder<Item, Item> sword(ModTier tier) {
        return tool(tier, "sword", props -> new TieredSwordItem(tier, props));
    }

    private static DeferredHolder<Item, Item> pickaxe(ModTier tier) {
        return tool(tier, "pickaxe", props -> new TieredPickaxeItem(tier, props));
    }

    private static DeferredHolder<Item, Item> axe(ModTier tier) {
        return tool(tier, "axe", props -> new TieredAxeItem(tier, props));
    }

    private static DeferredHolder<Item, Item> shovel(ModTier tier) {
        return tool(tier, "shovel", props -> new TieredShovelItem(tier, props));
    }

    private static DeferredHolder<Item, Item> hoe(ModTier tier) {
        return tool(tier, "hoe", props -> new TieredHoeItem(tier, props));
    }

    private static DeferredHolder<Item, Item> tool(ModTier tier, String suffix, Function<Item.Properties, Item> factory) {
        return TOOL_ITEMS.register(
                tier.id() + "_" + suffix,
                () -> factory.apply(new Item.Properties()
                        .stacksTo(1)
                        .fireResistant()
                        .rarity(tier.rarity())
                        .component(DataComponents.UNBREAKABLE, new Unbreakable(true)))
        );
    }

    public static List<DeferredHolder<Item, Item>> toolsOf(ModTier tier) {
        return switch (tier) {
            case MUSHASHITE -> List.of(MUSHASHITE_SWORD, MUSHASHITE_PICKAXE, MUSHASHITE_AXE, MUSHASHITE_SHOVEL, MUSHASHITE_HOE);
            case JOANFOITE -> List.of(JOANFOITE_SWORD, JOANFOITE_PICKAXE, JOANFOITE_AXE, JOANFOITE_SHOVEL, JOANFOITE_HOE);
            case NADIENITE -> List.of(NADIENITE_SWORD, NADIENITE_PICKAXE, NADIENITE_AXE, NADIENITE_SHOVEL, NADIENITE_HOE);
        };
    }

    public static void register(IEventBus eventBus) {
        TOOL_ITEMS.register(eventBus);
    }
}
