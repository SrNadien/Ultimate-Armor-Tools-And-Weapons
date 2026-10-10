package nadiendev.ultimatearmoury.registry;

import nadiendev.ultimatearmoury.UltimateArmouryCore;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.enchantment.Enchantment;

public class ModEnchantments {

    public static final ResourceKey<Enchantment> AUTOSMELT = key("autosmelt");
    public static final ResourceKey<Enchantment> XPBOOST   = key("xpboost");

    private static ResourceKey<Enchantment> key(String name) {
        return ResourceKey.create(Registries.ENCHANTMENT,
                Identifier.fromNamespaceAndPath(UltimateArmouryCore.MOD_ID, name));
    }
}
