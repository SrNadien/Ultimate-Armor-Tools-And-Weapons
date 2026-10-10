package nadiendev.ultimatearmoury.enchant;

import nadiendev.ultimatearmoury.registry.ModEnchantments;

import nadiendev.ultimatearmoury.UltimateArmouryCore;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockDropsEvent;

import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber(modid = UltimateArmouryCore.MOD_ID)
public class AutoSmeltBlockHandler {

    @SubscribeEvent
    public static void onBlockDrops(BlockDropsEvent event) {
        if (!(event.getLevel() instanceof ServerLevel serverLevel)) return;

        ItemStack tool = event.getTool();
        if (tool.isEmpty()) return;

        var enchantmentRegistry = serverLevel.registryAccess().registryOrThrow(Registries.ENCHANTMENT);
        var autoSmeltHolder = enchantmentRegistry.getHolder(ModEnchantments.AUTOSMELT).orElse(null);
        if (autoSmeltHolder == null) return;

        int enchLevel = EnchantmentHelper.getItemEnchantmentLevel(autoSmeltHolder, tool);
        if (enchLevel <= 0) return;

        List<ItemEntity> toAdd = new ArrayList<>();
        List<ItemEntity> toRemove = new ArrayList<>();

        for (ItemEntity itemEntity : event.getDrops()) {
            ItemStack stack = itemEntity.getItem().copy();
            var recipeInput = new SingleRecipeInput(stack);

            serverLevel.getRecipeManager()
                    .getRecipeFor(RecipeType.SMELTING, recipeInput, serverLevel)
                    .ifPresent(recipe -> {
                        ItemStack result = recipe.value().assemble(recipeInput, serverLevel.registryAccess());
                        if (!result.isEmpty()) {
                            result.setCount(stack.getCount());
                            toAdd.add(new ItemEntity(serverLevel,
                                    itemEntity.getX(), itemEntity.getY(), itemEntity.getZ(),
                                    result));
                            toRemove.add(itemEntity);
                        }
                    });
        }

        event.getDrops().removeAll(toRemove);
        event.getDrops().addAll(toAdd);
    }
}
