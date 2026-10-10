package nadiendev.ultimatearmoury.items;

import net.minecraft.world.item.Rarity;

public enum ModTier {

    MUSHASHITE(
            "mushashite", Rarity.RARE,
            3, 8, 6, 3, 3.0F, 0.05F, 15,
            4000, 12.0F, 5.0F, 24, 4,
            0, 0, 1
    ),

    JOANFOITE(
            "joanfoite", Rarity.EPIC,
            4, 9, 7, 4, 4.5F, 0.10F, 20,
            7000, 16.0F, 7.5F, 64, 7,
            1, 1, 3
    ),

    NADIENITE(
            "nadienite", Rarity.EPIC,
            5, 10, 8, 5, 6.0F, 0.15F, 25,
            10000, 20.0F, 10.0F, 104, 10,
            2, 2, 5
    );

    private final String id;
    private final Rarity rarity;

    private final int defenseHelmet;
    private final int defenseChestplate;
    private final int defenseLeggings;
    private final int defenseBoots;
    private final float toughness;
    private final float knockbackResistance;
    private final int enchantmentValue;

    private final int durability;
    private final float miningSpeed;
    private final float attackDamageBonus;
    private final int swordDamage;
    private final int toolEnchantLevel;

    private final int pieceEffectAmplifier;
    private final int setEffectAmplifier;
    private final int setHealthBoostAmplifier;

    ModTier(String id, Rarity rarity,
            int defenseHelmet, int defenseChestplate, int defenseLeggings, int defenseBoots,
            float toughness, float knockbackResistance, int enchantmentValue,
            int durability, float miningSpeed, float attackDamageBonus, int swordDamage, int toolEnchantLevel,
            int pieceEffectAmplifier, int setEffectAmplifier, int setHealthBoostAmplifier) {
        this.id = id;
        this.rarity = rarity;
        this.defenseHelmet = defenseHelmet;
        this.defenseChestplate = defenseChestplate;
        this.defenseLeggings = defenseLeggings;
        this.defenseBoots = defenseBoots;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.enchantmentValue = enchantmentValue;
        this.durability = durability;
        this.miningSpeed = miningSpeed;
        this.attackDamageBonus = attackDamageBonus;
        this.swordDamage = swordDamage;
        this.toolEnchantLevel = toolEnchantLevel;
        this.pieceEffectAmplifier = pieceEffectAmplifier;
        this.setEffectAmplifier = setEffectAmplifier;
        this.setHealthBoostAmplifier = setHealthBoostAmplifier;
    }

    public String id() {
        return id;
    }

    public Rarity rarity() {
        return rarity;
    }

    public int defenseHelmet() {
        return defenseHelmet;
    }

    public int defenseChestplate() {
        return defenseChestplate;
    }

    public int defenseLeggings() {
        return defenseLeggings;
    }

    public int defenseBoots() {
        return defenseBoots;
    }

    public float toughness() {
        return toughness;
    }

    public float knockbackResistance() {
        return knockbackResistance;
    }

    public int enchantmentValue() {
        return enchantmentValue;
    }

    public int durability() {
        return durability;
    }

    public float miningSpeed() {
        return miningSpeed;
    }

    public float attackDamageBonus() {
        return attackDamageBonus;
    }

    public int swordDamage() {
        return swordDamage;
    }

    public int toolEnchantLevel() {
        return toolEnchantLevel;
    }

    public int pieceEffectAmplifier() {
        return pieceEffectAmplifier;
    }

    public int setEffectAmplifier() {
        return setEffectAmplifier;
    }

    public int setHealthBoostAmplifier() {
        return setHealthBoostAmplifier;
    }

    public boolean hasElitePerks() {
        return this == NADIENITE;
    }
}
