package nadiendev.ultimatearmoury.datagen.providers;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

public class NadieniteChestLootModifier extends LootModifier {

    public static final MapCodec<NadieniteChestLootModifier> CODEC = RecordCodecBuilder.mapCodec(inst ->
            codecStart(inst).apply(inst, NadieniteChestLootModifier::new));

    private static final ThreadLocal<Boolean> IS_APPLYING = ThreadLocal.withInitial(() -> false);

    private static final ResourceLocation[] DUNGEON_TABLES = {
        ResourceLocation.parse("minecraft:chests/simple_dungeon"),
        ResourceLocation.parse("minecraft:chests/stronghold_corridor"),
        ResourceLocation.parse("minecraft:chests/stronghold_crossing"),
        ResourceLocation.parse("minecraft:chests/stronghold_library"),
        ResourceLocation.parse("minecraft:chests/abandoned_mineshaft"),
        ResourceLocation.parse("minecraft:chests/jungle_temple"),
        ResourceLocation.parse("minecraft:chests/desert_pyramid"),
        ResourceLocation.parse("minecraft:chests/pillager_outpost"),
        ResourceLocation.parse("minecraft:chests/ancient_city"),
    };

    private static final ResourceLocation[] NETHER_TABLES = {
        ResourceLocation.parse("minecraft:chests/nether_bridge"),
        ResourceLocation.parse("minecraft:chests/bastion_treasure"),
        ResourceLocation.parse("minecraft:chests/bastion_other"),
        ResourceLocation.parse("minecraft:chests/bastion_hoglin_stable"),
    };

    private static final ResourceLocation BONUS_DUNGEON =
            ResourceLocation.fromNamespaceAndPath("ultimatearmoury", "chests/nadienite_dungeon_bonus");
    private static final ResourceLocation BONUS_NETHER =
            ResourceLocation.fromNamespaceAndPath("ultimatearmoury", "chests/nadienite_nether_bonus");

    public NadieniteChestLootModifier(LootItemCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        if (IS_APPLYING.get()) return generatedLoot;

        ResourceLocation tableId = context.getQueriedLootTableId();

        ResourceLocation bonusTable = null;

        for (ResourceLocation rl : DUNGEON_TABLES) {
            if (rl.equals(tableId)) { bonusTable = BONUS_DUNGEON; break; }
        }
        if (bonusTable == null) {
            for (ResourceLocation rl : NETHER_TABLES) {
                if (rl.equals(tableId)) { bonusTable = BONUS_NETHER; break; }
            }
        }

        if (bonusTable == null) return generatedLoot;

        var bonusKey = net.minecraft.resources.ResourceKey.create(
                net.minecraft.core.registries.Registries.LOOT_TABLE, bonusTable);

        var lootTable = context.getLevel().getServer()
                .reloadableRegistries()
                .getLootTable(bonusKey);

        IS_APPLYING.set(true);
        try {
            lootTable.getRandomItems(context, generatedLoot::add);
        } finally {
            IS_APPLYING.set(false);
        }

        return generatedLoot;
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}
