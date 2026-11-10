package nadiendev.ultimatearmoury.datagen.providers;

import nadiendev.ultimatearmoury.UltimateArmouryCore;
import nadiendev.ultimatearmoury.registry.BrushAdds;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ItemsModelProvider extends ItemModelProvider {

    public ItemsModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, UltimateArmouryCore.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

        withExistingParent(
                nadiendev.ultimatearmoury.registry.ItemsAdds.NETHERITE_DAGGER.getId().getPath(),
                ResourceLocation.withDefaultNamespace("item/handheld")
        ).texture("layer0", ResourceLocation.fromNamespaceAndPath(UltimateArmouryCore.MOD_ID, "item/netherite_dagger"));

        basicItem(nadiendev.ultimatearmoury.registry.ItemsAdds.NADIENITE_NUGGET.get());

        ResourceLocation brushing = ResourceLocation.withDefaultNamespace("brushing");
        for (BrushAdds.Brush brush : BrushAdds.BRUSHES) {
            ResourceLocation texture = ResourceLocation.fromNamespaceAndPath(UltimateArmouryCore.MOD_ID,
                    "item/" + brush.itemName());
            ItemModelBuilder model = withExistingParent(brush.itemName(),
                    ResourceLocation.withDefaultNamespace("item/brush")).texture("layer0", texture);
            for (int stage = 0; stage < 3; stage++) {
                ItemModelBuilder animation = withExistingParent(brush.itemName() + "_brushing_" + stage,
                        ResourceLocation.withDefaultNamespace("item/brush_brushing_" + stage))
                        .texture("layer0", texture);
                model.override().predicate(brushing, 0.25F * (stage + 1)).model(animation).end();
            }
        }
    }
}
