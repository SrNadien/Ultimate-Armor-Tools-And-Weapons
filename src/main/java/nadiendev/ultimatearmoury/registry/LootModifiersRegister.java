package nadiendev.ultimatearmoury.registry;

import nadiendev.ultimatearmoury.datagen.providers.NadieniteChestLootModifier;
import nadiendev.ultimatearmoury.datagen.providers.WardenHeartLootModifier;

import nadiendev.ultimatearmoury.UltimateArmouryCore;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.DeferredHolder;
import com.mojang.serialization.MapCodec;

public class LootModifiersRegister {

    public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> LOOT_MODIFIER_SERIALIZERS =
            DeferredRegister.create(NeoForgeRegistries.GLOBAL_LOOT_MODIFIER_SERIALIZERS, UltimateArmouryCore.MOD_ID);

    public static final DeferredHolder<MapCodec<? extends IGlobalLootModifier>, MapCodec<NadieniteChestLootModifier>> NADIENITE_CHEST =
            LOOT_MODIFIER_SERIALIZERS.register("nadienite_chest", () -> NadieniteChestLootModifier.CODEC);

    public static final DeferredHolder<MapCodec<? extends IGlobalLootModifier>, MapCodec<WardenHeartLootModifier>> WARDEN_HEART =
            LOOT_MODIFIER_SERIALIZERS.register("warden_heart", () -> WardenHeartLootModifier.CODEC);

    public static void register(IEventBus eventBus) {
        LOOT_MODIFIER_SERIALIZERS.register(eventBus);
    }
}
