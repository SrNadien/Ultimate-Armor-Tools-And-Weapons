package nadiendev.ultimatearmoury.registry;

import nadiendev.ultimatearmoury.UltimateArmouryCore;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BrushItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.component.Unbreakable;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.data.loading.DatagenModLoader;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BrushAdds {

    public enum Source {
        VANILLA,
        BASE,
        ALLTHEMODIUM,
        THAUMATURGE
    }

    public record Brush(String material, String ingotTag, String previous, Source source, Rarity rarity,
                        int durability) {

        public String itemName() {
            return material + "_brush";
        }

        public boolean unbreakable() {
            return durability <= 0;
        }

        public boolean hasPrevious() {
            return previous != null;
        }
    }

    public static final String VANILLA_BRUSH = "minecraft:brush";

    public static final List<Brush> BRUSHES = List.of(
            new Brush("wooden", "minecraft:planks", null, Source.VANILLA, Rarity.COMMON, 32),
            new Brush("iron", "c:ingots/iron", VANILLA_BRUSH, Source.VANILLA, Rarity.COMMON, 128),
            new Brush("golden", "c:ingots/gold", "iron", Source.VANILLA, Rarity.COMMON, 192),
            new Brush("diamond", "c:gems/diamond", "golden", Source.VANILLA, Rarity.UNCOMMON, 320),
            new Brush("netherite", "c:ingots/netherite", "diamond", Source.VANILLA, Rarity.RARE, 512),
            new Brush("steel", "c:ingots/steel", "iron", Source.BASE, Rarity.COMMON, 256),
            new Brush("mushashite", "c:ingots/mushashite", "netherite", Source.BASE, Rarity.RARE, 768),
            new Brush("joanfoite", "c:ingots/joanfoite", "mushashite", Source.BASE, Rarity.RARE, 1024),
            new Brush("nadienite", "c:ingots/nadienite", "joanfoite", Source.BASE, Rarity.EPIC, 1536),
            new Brush("allthemodium", "c:ingots/allthemodium", "nadienite", Source.ALLTHEMODIUM, Rarity.EPIC, 0),
            new Brush("vibranium", "c:ingots/vibranium", "allthemodium", Source.ALLTHEMODIUM, Rarity.EPIC, 0),
            new Brush("unobtainium", "c:ingots/unobtainium", "vibranium", Source.ALLTHEMODIUM, Rarity.EPIC, 0),
            new Brush("brass", "c:ingots/brass", "unobtainium", Source.THAUMATURGE, Rarity.EPIC, 3072),
            new Brush("thaumium", "c:ingots/thaumium", "brass", Source.THAUMATURGE, Rarity.EPIC, 4096),
            new Brush("void_metal", "c:ingots/void_metal", "thaumium", Source.THAUMATURGE, Rarity.EPIC, 0)
    );

    public static final DeferredRegister<Item> BRUSH_ITEMS =
            DeferredRegister.create(BuiltInRegistries.ITEM, UltimateArmouryCore.MOD_ID);

    private static final List<DeferredHolder<Item, Item>> ALL = new ArrayList<>();
    private static final Map<String, DeferredHolder<Item, Item>> BY_MATERIAL = new LinkedHashMap<>();

    static {
        for (Brush brush : BRUSHES) {
            DeferredHolder<Item, Item> holder = BRUSH_ITEMS.register(brush.itemName(),
                    () -> new BrushItem(properties(brush)));
            ALL.add(holder);
            BY_MATERIAL.put(brush.material(), holder);
        }
    }

    private static Item.Properties properties(Brush brush) {
        Item.Properties properties = new Item.Properties().stacksTo(1).rarity(brush.rarity());
        return brush.unbreakable()
                ? properties.component(DataComponents.UNBREAKABLE, new Unbreakable(true))
                : properties.durability(brush.durability());
    }

    public static List<DeferredHolder<Item, Item>> all() {
        return ALL;
    }

    public static DeferredHolder<Item, Item> of(String material) {
        return BY_MATERIAL.get(material);
    }

    public static List<Brush> bySource(Source source) {
        return BRUSHES.stream().filter(brush -> brush.source() == source).toList();
    }

    public static boolean unearthedPresent() {
        return ModList.get().isLoaded("ftbunearthed");
    }

    public static boolean enabled() {
        return unearthedPresent() || DatagenModLoader.isRunningDataGen();
    }

    public static void register(IEventBus eventBus) {
        if (enabled()) {
            BRUSH_ITEMS.register(eventBus);
        }
    }

    private BrushAdds() {
    }
}
