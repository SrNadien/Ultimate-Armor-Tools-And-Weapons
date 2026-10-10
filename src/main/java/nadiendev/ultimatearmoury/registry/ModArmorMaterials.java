package nadiendev.ultimatearmoury.registry;

import nadiendev.ultimatearmoury.UltimateArmouryCore;
import nadiendev.ultimatearmoury.items.ModTier;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.List;

public class ModArmorMaterials {

    public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS =
            DeferredRegister.create(Registries.ARMOR_MATERIAL, UltimateArmouryCore.MOD_ID);

    public static final Holder<ArmorMaterial> MUSHASHITE = register(ModTier.MUSHASHITE);
    public static final Holder<ArmorMaterial> JOANFOITE = register(ModTier.JOANFOITE);
    public static final Holder<ArmorMaterial> NADIENITE = register(ModTier.NADIENITE);

    private static Holder<ArmorMaterial> register(ModTier tier) {
        return ARMOR_MATERIALS.register(tier.id(), () -> new ArmorMaterial(
                Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                    map.put(ArmorItem.Type.HELMET, tier.defenseHelmet());
                    map.put(ArmorItem.Type.CHESTPLATE, tier.defenseChestplate());
                    map.put(ArmorItem.Type.LEGGINGS, tier.defenseLeggings());
                    map.put(ArmorItem.Type.BOOTS, tier.defenseBoots());
                }),
                tier.enchantmentValue(),
                SoundEvents.ARMOR_EQUIP_NETHERITE,

                () -> Ingredient.of(ItemsAdds.NADIENITE_INGOT.get()),
                List.of(new ArmorMaterial.Layer(
                        ResourceLocation.fromNamespaceAndPath(UltimateArmouryCore.MOD_ID, tier.id())
                )),
                tier.toughness(),
                tier.knockbackResistance()
        ));
    }

    public static Holder<ArmorMaterial> of(ModTier tier) {
        return switch (tier) {
            case MUSHASHITE -> MUSHASHITE;
            case JOANFOITE -> JOANFOITE;
            case NADIENITE -> NADIENITE;
        };
    }
}
