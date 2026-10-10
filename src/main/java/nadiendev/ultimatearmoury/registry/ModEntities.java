package nadiendev.ultimatearmoury.registry;

import nadiendev.ultimatearmoury.entities.ChanclaEntity;

import nadiendev.ultimatearmoury.UltimateArmouryCore;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(Registries.ENTITY_TYPE, UltimateArmouryCore.MOD_ID);

    public static final DeferredHolder<EntityType<?>, EntityType<ChanclaEntity>> CHANCLA = ENTITY_TYPES.register(
            "chancla",
            () -> EntityType.Builder.<ChanclaEntity>of(ChanclaEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(4)
                    .updateInterval(20)
                    .build("chancla")
    );
}
