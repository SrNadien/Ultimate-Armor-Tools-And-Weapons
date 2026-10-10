package nadiendev.ultimatearmoury.datagen.providers;

import nadiendev.ultimatearmoury.UltimateArmouryCore;
import nadiendev.ultimatearmoury.registry.BlocksAdds;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.advancements.criterion.PlayerTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;

import net.minecraft.data.advancements.AdvancementSubProvider;

import java.util.function.Consumer;

public class ModAdvancementProvider implements AdvancementSubProvider {

    private static final Identifier RL_INFINITY_1_SWORD  = Identifier.fromNamespaceAndPath("ultimatearmoury", "infinity_1_sword");
    private static final Identifier RL_INFINITY_BALANCED = Identifier.fromNamespaceAndPath("ultimatearmoury", "infinity_sword_balanced");

    @Override
    public void generate(HolderLookup.Provider registries, Consumer<AdvancementHolder> saver) {

        AdvancementHolder installMod = Advancement.Builder.advancement()
                .display(
                        BlocksAdds.NADIENITE_BLOCK.get(),
                        Component.translatable("advancements.ultimatearmoury.install_mod.title"),
                        Component.translatable("advancements.ultimatearmoury.install_mod.description"),
                        Identifier.parse("minecraft:textures/gui/advancements/backgrounds/adventure.png"),
                        AdvancementType.TASK,
                        true, true, false
                )
                .addCriterion("tick", PlayerTrigger.TriggerInstance.tick())
                .save(saver, Identifier.fromNamespaceAndPath(UltimateArmouryCore.MOD_ID, "install_mod").toString());

        if (BuiltInRegistries.ITEM.containsKey(RL_INFINITY_1_SWORD)
                && BuiltInRegistries.ITEM.containsKey(RL_INFINITY_BALANCED)) {

            Item infinity1Sword   = BuiltInRegistries.ITEM.getValue(RL_INFINITY_1_SWORD);
            Item infinityBalanced = BuiltInRegistries.ITEM.getValue(RL_INFINITY_BALANCED);

            Advancement.Builder.advancement()
                    .parent(installMod)
                    .display(
                            infinity1Sword,
                            Component.translatable("advancements.ultimatearmoury.casy_try_hard.title"),
                            Component.translatable("advancements.ultimatearmoury.casy_try_hard.description"),
                            null, AdvancementType.CHALLENGE, true, true, false
                    )
                    .addCriterion("has_infinity_1_sword",
                            InventoryChangeTrigger.TriggerInstance.hasItems(infinity1Sword))
                    .save(saver, Identifier.fromNamespaceAndPath(UltimateArmouryCore.MOD_ID, "casy_try_hard").toString());

            Advancement.Builder.advancement()
                    .parent(installMod)
                    .display(
                            infinityBalanced,
                            Component.translatable("advancements.ultimatearmoury.tryhard_balanced.title"),
                            Component.translatable("advancements.ultimatearmoury.tryhard_balanced.description"),
                            null, AdvancementType.CHALLENGE, true, true, false
                    )
                    .addCriterion("has_infinity_sword_balanced",
                            InventoryChangeTrigger.TriggerInstance.hasItems(infinityBalanced))
                    .save(saver, Identifier.fromNamespaceAndPath(UltimateArmouryCore.MOD_ID, "tryhard_balanced").toString());
        }

    }
}
