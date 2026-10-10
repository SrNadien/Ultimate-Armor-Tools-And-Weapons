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
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;

import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber(modid = UltimateArmouryCore.MOD_ID)
public class AutoSmeltEventHandler {

    @SubscribeEvent
    public static void onLivingDrops(LivingDropsEvent event) {
        if (!(event.getEntity().level() instanceof ServerLevel level)) return;

        ItemStack tool = event.getSource().getWeaponItem();
        if (tool == null || tool.isEmpty()) return;

        var enchantmentRegistry = level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
        var autoSmeltHolder = enchantmentRegistry.get(ModEnchantments.AUTOSMELT).orElse(null);
        if (autoSmeltHolder == null) return;

        int enchLevel = EnchantmentHelper.getItemEnchantmentLevel(autoSmeltHolder, tool);
        if (enchLevel <= 0) return;

        List<ItemEntity> toAdd = new ArrayList<>();
        List<ItemEntity> toRemove = new ArrayList<>();

        for (ItemEntity itemEntity : event.getDrops()) {
            ItemStack stack = itemEntity.getItem().copy();
            var recipeInput = new SingleRecipeInput(stack);

            level.recipeAccess()
                    .getRecipeFor(RecipeType.SMELTING, recipeInput, level)
                    .ifPresent(recipe -> {
                        ItemStack result = recipe.value().assemble(recipeInput);
                        if (!result.isEmpty()) {
                            result.setCount(stack.getCount());
                            toAdd.add(new ItemEntity(level,
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
