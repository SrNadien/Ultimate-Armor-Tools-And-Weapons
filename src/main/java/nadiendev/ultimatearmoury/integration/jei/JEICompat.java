package nadiendev.ultimatearmoury.integration.jei;

import nadiendev.ultimatearmoury.UltimateArmouryCore;
import nadiendev.ultimatearmoury.items.ModTier;
import nadiendev.ultimatearmoury.config.ModConfigs;
import nadiendev.ultimatearmoury.registry.ArmorAdds;
import nadiendev.ultimatearmoury.registry.HammerAdds;
import nadiendev.ultimatearmoury.registry.MeshAdds;
import nadiendev.ultimatearmoury.registry.BlocksAdds;
import nadiendev.ultimatearmoury.registry.ItemsAdds;
import nadiendev.ultimatearmoury.registry.ModOreBlocks;
import nadiendev.ultimatearmoury.registry.ToolsAdds;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import java.util.List;

@JeiPlugin
public class JEICompat implements IModPlugin {

    @Override
    public Identifier getPluginUid() {
        return Identifier.fromNamespaceAndPath(UltimateArmouryCore.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();
        registration.addRecipeCategories(new NetheriteDaggerCategory(guiHelper));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {

        registration.addRecipes(
                NetheriteDaggerCategory.RECIPE_TYPE,
                List.of(NetheriteDaggerRecipe.INSTANCE)
        );

        registration.addIngredientInfo(
                new ItemStack(ItemsAdds.NETHERITE_DAGGER.get()),
                VanillaTypes.ITEM_STACK,
                Component.translatable("jei.ultimatearmoury.netherite_dagger.info")
        );

        for (ModTier tier : ModTier.values()) {
            String id = tier.id();

            info(registration, ArmorAdds.piecesOf(tier), "jei.ultimatearmoury.armor." + id + ".info");
            info(registration, ToolsAdds.toolsOf(tier), "jei.ultimatearmoury.tools." + id + ".info");

            info(registration, List.of(
                    ItemsAdds.ingotOf(tier),
                    ItemsAdds.nuggetOf(tier),
                    ItemsAdds.rawOreOf(tier)
            ), "jei.ultimatearmoury.material." + id + ".info");

            registration.addIngredientInfo(
                    List.of(
                            new ItemStack(ModOreBlocks.stoneOreOf(tier).get()),
                            new ItemStack(ModOreBlocks.deepslateOreOf(tier).get()),
                            new ItemStack(BlocksAdds.storageBlockOf(tier).get())
                    ),
                    VanillaTypes.ITEM_STACK,
                    Component.translatable("jei.ultimatearmoury.material." + id + ".info")
            );

            info(registration, List.of(ItemsAdds.heartOf(tier)), "jei.ultimatearmoury.heart." + id + ".info");
        }

        info(registration, List.of(ItemsAdds.STEEL_NUGGET), "jei.ultimatearmoury.steel_nugget.info");
        info(registration, List.of(ItemsAdds.NETHERITE_NUGGET), "jei.ultimatearmoury.netherite_nugget.info");
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        if (!MeshAdds.sieveModPresent() || !ModConfigs.exDeorumEnabled()) {
            return;
        }

        IJeiHelpers helpers = registration.getJeiHelpers();

        ItemLike[] meshes = MeshAdds.all().stream().map(DeferredHolder::get).toArray(ItemLike[]::new);
        catalysts(registration, helpers, "sieve", meshes);
        catalysts(registration, helpers, "compressed_sieve", meshes);

        catalysts(registration, helpers, "hammer", hammers(false));
        catalysts(registration, helpers, "compressed_hammer", hammers(true));
    }

    private static ItemLike[] hammers(boolean compressed) {
        return HammerAdds.all().stream()
                .filter(holder -> holder.getId().getPath().startsWith("compressed_") == compressed)
                .map(DeferredHolder::get)
                .toArray(ItemLike[]::new);
    }

    private static void catalysts(IRecipeCatalystRegistration registration, IJeiHelpers helpers,
                                  String type, ItemLike... items) {
        if (items.length == 0) {
            return;
        }
        helpers.getRecipeType(Identifier.fromNamespaceAndPath("exdeorum", type))
                .ifPresent(recipeType -> registration.addRecipeCatalysts(recipeType, items));
    }

    private static void info(IRecipeRegistration registration,
                             List<DeferredHolder<Item, Item>> items, String translationKey) {
        registration.addIngredientInfo(
                items.stream().map(holder -> new ItemStack(holder.get())).toList(),
                VanillaTypes.ITEM_STACK,
                Component.translatable(translationKey)
        );
    }
}
