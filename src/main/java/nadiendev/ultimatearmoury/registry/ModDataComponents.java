package nadiendev.ultimatearmoury.registry;

import com.mojang.serialization.Codec;
import nadiendev.ultimatearmoury.UltimateArmouryCore;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModDataComponents {
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENTS =
            DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, UltimateArmouryCore.MOD_ID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> SILK_TOUCH_MODE =
            DATA_COMPONENTS.register("silk_touch_mode",
                    () -> DataComponentType.<Boolean>builder()
                            .persistent(Codec.BOOL)
                            .networkSynchronized(ByteBufCodecs.BOOL)
                            .build()
            );

    public static void register(IEventBus eventBus) {
        DATA_COMPONENTS.register(eventBus);
    }
}
