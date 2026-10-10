// package nadiendev.ultimatearmoury.registry;

// import nadiendev.ultimatearmoury.avaritia.Infinity1HoeItem;
// import nadiendev.ultimatearmoury.avaritia.Infinity1PickaxeItem;
// import nadiendev.ultimatearmoury.avaritia.Infinity1SwordItem;
// import nadiendev.ultimatearmoury.avaritia.InfinitySwordBalancedItem;

// import nadiendev.ultimatearmoury.UltimateArmouryCore;
// import nadiendev.ultimatearmoury.avaritia.InfinityTier;
// import net.minecraft.core.registries.BuiltInRegistries;
// import net.minecraft.world.item.Item;
// import net.minecraft.world.item.Rarity;
// import net.minecraft.core.component.DataComponents;
// import net.minecraft.world.item.component.Unbreakable;
// import net.neoforged.bus.api.IEventBus;
// import net.neoforged.neoforge.registries.DeferredHolder;
// import net.neoforged.neoforge.registries.DeferredRegister;

// public class AvaritiaToolsAdds {
//     public static final DeferredRegister<Item> AVARITIA_ITEMS =
//             DeferredRegister.create(BuiltInRegistries.ITEM, UltimateArmouryCore.MOD_ID);

//     public static final DeferredHolder<Item, Item> INFINITY_1_SWORD = AVARITIA_ITEMS.register(
//             "infinity_1_sword",
//             () -> new Infinity1SwordItem(
//                     InfinityTier.INFINITY,
//                     new Item.Properties()
//                             .stacksTo(1)
//                             .fireResistant()
//                             .rarity(Rarity.EPIC)
//                             .component(DataComponents.UNBREAKABLE, new Unbreakable(true))
//             )
//     );

//     public static final DeferredHolder<Item, Item> INFINITY_1_PICKAXE = AVARITIA_ITEMS.register(
//             "infinity_1_pickaxe",
//             () -> new Infinity1PickaxeItem(
//                     InfinityTier.INFINITY,
//                     new Item.Properties()
//                             .stacksTo(1)
//                             .fireResistant()
//                             .rarity(Rarity.EPIC)
//                             .component(DataComponents.UNBREAKABLE, new Unbreakable(true))
//             )
//     );

//     public static final DeferredHolder<Item, Item> INFINITY_1_HOE = AVARITIA_ITEMS.register(
//             "infinity_1_hoe",
//             () -> new Infinity1HoeItem(
//                     InfinityTier.INFINITY,
//                     new Item.Properties()
//                             .stacksTo(1)
//                             .fireResistant()
//                             .rarity(Rarity.EPIC)
//                             .component(DataComponents.UNBREAKABLE, new Unbreakable(true))
//             )
//     );

//     public static final DeferredHolder<Item, Item> INFINITY_SWORD_BALANCED = AVARITIA_ITEMS.register(
//             "infinity_sword_balanced",
//             () -> new InfinitySwordBalancedItem(
//                     InfinityTier.INFINITY_BALANCED,
//                     new Item.Properties()
//                             .stacksTo(1)
//                             .fireResistant()
//                             .rarity(Rarity.EPIC)
//                             .component(DataComponents.UNBREAKABLE, new Unbreakable(true))
//             )
//     );

//     public static void register(IEventBus eventBus) {
//         AVARITIA_ITEMS.register(eventBus);
//     }
// }
