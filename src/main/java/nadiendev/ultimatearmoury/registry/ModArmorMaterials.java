package nadiendev.ultimatearmoury.registry;

import nadiendev.ultimatearmoury.UltimateArmouryCore;
import nadiendev.ultimatearmoury.datagen.providers.ModItemTags;
import nadiendev.ultimatearmoury.items.ModTier;
import net.minecraft.util.Util;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;

import java.util.EnumMap;
import java.util.Map;

public class ModArmorMaterials {

    private static final Map<ModTier, ArmorMaterial> MATERIALS = new EnumMap<>(ModTier.class);

    static {
        for (ModTier tier : ModTier.values()) {
            MATERIALS.put(tier, new ArmorMaterial(
                    tier.durability(),
                    Util.make(new EnumMap<>(ArmorType.class), map -> {
                        map.put(ArmorType.HELMET, tier.defenseHelmet());
                        map.put(ArmorType.CHESTPLATE, tier.defenseChestplate());
                        map.put(ArmorType.LEGGINGS, tier.defenseLeggings());
                        map.put(ArmorType.BOOTS, tier.defenseBoots());
                    }),
                    tier.enchantmentValue(),
                    SoundEvents.ARMOR_EQUIP_NETHERITE,
                    tier.toughness(),
                    tier.knockbackResistance(),
                    ModItemTags.Items.INGOTS_NADIENITE,
                    assetOf(tier)
            ));
        }
    }

    public static ResourceKey<EquipmentAsset> assetOf(ModTier tier) {
        return ResourceKey.create(EquipmentAssets.ROOT_ID,
                Identifier.fromNamespaceAndPath(UltimateArmouryCore.MOD_ID, tier.id()));
    }

    public static ArmorMaterial of(ModTier tier) {
        return MATERIALS.get(tier);
    }

    private ModArmorMaterials() {
    }
}
