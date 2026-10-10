package nadiendev.ultimatearmoury.registry;

import nadiendev.ultimatearmoury.UltimateArmouryCore;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;

public class MeshAdds {

    public static final DeferredRegister<Item> MESH_ITEMS =
            DeferredRegister.create(BuiltInRegistries.ITEM, UltimateArmouryCore.MOD_ID);

    private static final List<DeferredHolder<Item, Item>> ALL = new ArrayList<>();

    public static final List<String> MESH_NAMES = List.of(
            "steel_mesh", "mushashite_mesh", "joanfoite_mesh", "nadienite_mesh",
            "allthemodium_mesh", "vibranium_mesh", "unobtainium_mesh");

    public static final DeferredHolder<Item, Item> STEEL_MESH = mesh("steel_mesh", Rarity.COMMON);
    public static final DeferredHolder<Item, Item> MUSHASHITE_MESH = mesh("mushashite_mesh", Rarity.RARE);
    public static final DeferredHolder<Item, Item> JOANFOITE_MESH = mesh("joanfoite_mesh", Rarity.RARE);
    public static final DeferredHolder<Item, Item> NADIENITE_MESH = mesh("nadienite_mesh", Rarity.EPIC);
    public static final DeferredHolder<Item, Item> ALLTHEMODIUM_MESH = mesh("allthemodium_mesh", Rarity.EPIC);
    public static final DeferredHolder<Item, Item> VIBRANIUM_MESH = mesh("vibranium_mesh", Rarity.EPIC);
    public static final DeferredHolder<Item, Item> UNOBTAINIUM_MESH = mesh("unobtainium_mesh", Rarity.EPIC);

    private static DeferredHolder<Item, Item> mesh(String name, Rarity rarity) {
        DeferredHolder<Item, Item> holder = MESH_ITEMS.register(name,
                () -> new Item(new Item.Properties().stacksTo(1).rarity(rarity)));
        ALL.add(holder);
        return holder;
    }

    public static List<DeferredHolder<Item, Item>> all() {
        return ALL;
    }

    public static boolean sieveModPresent() {
        return ModList.get().isLoaded("exdeorum");
    }

    public static void register(IEventBus eventBus) {
        MESH_ITEMS.register(eventBus);
    }
}
