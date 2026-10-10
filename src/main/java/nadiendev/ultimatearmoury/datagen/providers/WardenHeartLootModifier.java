package nadiendev.ultimatearmoury.datagen.providers;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import nadiendev.ultimatearmoury.registry.ItemsAdds;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

/**
 * El Warden suelta el Corazon de la Elite. Se agrega con un modificador y no
 * reemplazando la tabla de vanilla, porque el mod de comida tambien le agrega
 * drops al Warden y dos mods que pisan el mismo archivo se anulan entre si.
 */
public class WardenHeartLootModifier extends LootModifier {

    public static final MapCodec<WardenHeartLootModifier> CODEC = RecordCodecBuilder.mapCodec(inst ->
            codecStart(inst).apply(inst, WardenHeartLootModifier::new));

    private static final Identifier WARDEN = Identifier.parse("minecraft:entities/warden");

    public WardenHeartLootModifier(LootItemCondition[] conditionsIn, int priority) {
        super(conditionsIn, priority);
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        if (WARDEN.equals(context.getQueriedLootTableId())) {
            generatedLoot.add(new ItemStack(ItemsAdds.CORAZON_DE_LA_ELITE.get()));
        }
        return generatedLoot;
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}
