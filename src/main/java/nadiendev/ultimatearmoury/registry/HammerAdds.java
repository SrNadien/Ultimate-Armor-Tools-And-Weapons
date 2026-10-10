package nadiendev.ultimatearmoury.registry;

import nadiendev.ultimatearmoury.UltimateArmouryCore;
import nadiendev.ultimatearmoury.items.ModTier;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.component.Unbreakable;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class HammerAdds {

    public static final TagKey<Block> MINEABLE_WITH_HAMMER = TagKey.create(
            Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("exdeorum", "mineable/hammer"));

    public record Hammer(String material, String ingotTag, boolean needsAllTheModium,
                         Supplier<Tier> tier, String previousMaterial, ModTier heart,
                         boolean unbreakable) {
    }

    public static final List<Hammer> HAMMERS = List.of(
            new Hammer("steel", "c:ingots/steel", false, () -> Tiers.DIAMOND, "exdeorum:iron_hammer", null, false),
            new Hammer("mushashite", "c:ingots/mushashite", false, () -> ModToolTiers.of(ModTier.MUSHASHITE), "exdeorum:netherite_hammer", ModTier.MUSHASHITE, false),
            new Hammer("joanfoite", "c:ingots/joanfoite", false, () -> ModToolTiers.of(ModTier.JOANFOITE), "mushashite", ModTier.JOANFOITE, false),
            new Hammer("nadienite", "c:ingots/nadienite", false, () -> ModToolTiers.of(ModTier.NADIENITE), "joanfoite", ModTier.NADIENITE, false),
            new Hammer("allthemodium", "c:ingots/allthemodium", true, () -> ModToolTiers.of(ModTier.NADIENITE), "nadienite", null, false),
            new Hammer("vibranium", "c:ingots/vibranium", true, () -> ModToolTiers.of(ModTier.NADIENITE), "allthemodium", null, false),
            new Hammer("unobtainium", "c:ingots/unobtainium", true, () -> ModToolTiers.of(ModTier.NADIENITE), "vibranium", null, true)
    );

    public static boolean isExternal(String previousMaterial) {
        return previousMaterial != null && previousMaterial.indexOf(':') >= 0;
    }

    public static final DeferredRegister<Item> HAMMER_ITEMS =
            DeferredRegister.create(BuiltInRegistries.ITEM, UltimateArmouryCore.MOD_ID);

    private static final List<DeferredHolder<Item, Item>> ALL = new ArrayList<>();

    static {
        for (Hammer hammer : HAMMERS) {
            register(name(hammer.material(), false), hammer.tier(), hammer.unbreakable());
            register(name(hammer.material(), true), hammer.tier(), hammer.unbreakable());
        }
    }

    private static void register(String name, Supplier<Tier> tier, boolean unbreakable) {
        ALL.add(HAMMER_ITEMS.register(name, () -> new DiggerItem(
                tier.get(),
                MINEABLE_WITH_HAMMER,
                properties(unbreakable)
        )));
    }

    private static Item.Properties properties(boolean unbreakable) {
        Item.Properties properties = new Item.Properties().rarity(Rarity.RARE);
        return unbreakable
                ? properties.component(DataComponents.UNBREAKABLE, new Unbreakable(true))
                : properties;
    }

    public static String name(String material, boolean compressed) {
        return compressed ? "compressed_" + material + "_hammer" : material + "_hammer";
    }

    public static List<DeferredHolder<Item, Item>> all() {
        return ALL;
    }

    public static void register(IEventBus eventBus) {
        HAMMER_ITEMS.register(eventBus);
    }

    private HammerAdds() {
    }
}
