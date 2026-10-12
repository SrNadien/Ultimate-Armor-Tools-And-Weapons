package nadiendev.ultimatearmoury.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import nadiendev.ultimatearmoury.UltimateArmouryCore;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public record ConfigCondition(String option) implements ICondition {

    public static final MapCodec<ConfigCondition> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    Codec.STRING.fieldOf("option").forGetter(ConfigCondition::option)
            ).apply(instance, ConfigCondition::new));

    public static final DeferredRegister<MapCodec<? extends ICondition>> CONDITION_CODECS =
            DeferredRegister.create(NeoForgeRegistries.Keys.CONDITION_CODECS, UltimateArmouryCore.MOD_ID);

    static {
        CONDITION_CODECS.register("config", () -> CODEC);
    }

    public static final String EX_DEORUM = "exDeorum";
    public static final String ALLTHEMODIUM = "allTheModium";
    public static final String ALLTHECOMPRESSED = "allTheCompressed";
    public static final String THAUMATURGE = "thaumaturge";

    @Override
    public boolean test(IContext context) {
        return switch (this.option) {
            case EX_DEORUM -> ModConfigs.exDeorumEnabled();
            case ALLTHEMODIUM -> ModConfigs.allTheModiumEnabled();
            case ALLTHECOMPRESSED -> ModConfigs.allTheCompressedEnabled();
            case THAUMATURGE -> ModConfigs.thaumaturgeEnabled();
            default -> true;
        };
    }

    @Override
    public MapCodec<? extends ICondition> codec() {
        return CODEC;
    }

    public static void register(IEventBus eventBus) {
        CONDITION_CODECS.register(eventBus);
    }
}
