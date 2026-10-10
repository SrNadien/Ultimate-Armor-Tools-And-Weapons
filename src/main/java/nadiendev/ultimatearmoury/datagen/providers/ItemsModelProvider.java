package nadiendev.ultimatearmoury.datagen.providers;

import nadiendev.ultimatearmoury.UltimateArmouryCore;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.ModList;
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
    }
}
