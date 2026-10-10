package nadiendev.ultimatearmoury.enchant;

import nadiendev.ultimatearmoury.UltimateArmouryCore;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class EnchantAutoSmelt {

    public static final DeferredRegister<Enchantment> ENCHANTMENTS =
            DeferredRegister.create(Registries.ENCHANTMENT, UltimateArmouryCore.MOD_ID);

    public static final ResourceKey<Enchantment> AUTOSMELT =
            ResourceKey.create(Registries.ENCHANTMENT,
                    Identifier.fromNamespaceAndPath(UltimateArmouryCore.MOD_ID, "autosmelt"));

    public static void register(IEventBus modEventBus) {
        ENCHANTMENTS.register(modEventBus);
    }
}
