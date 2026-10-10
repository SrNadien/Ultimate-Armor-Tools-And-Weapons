package nadiendev.ultimatearmoury.client;

import nadiendev.ultimatearmoury.UltimateArmouryCore;
import nadiendev.ultimatearmoury.items.armor.HatItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorItem;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

/**
 * Renderiza el gorro como geometria 3D propia. Es la unica pieza que lo
 * necesita: una corona, una fedora y un sombrero de mago son volumen, y un
 * layer plano solo pinta sobre la cabeza sin poder agregar forma.
 *
 * Peto, grebas y botas van por el sistema de capas de Minecraft.
 */
public class HatRenderer extends GeoArmorRenderer<HatItem> {

    public HatRenderer() {
        super(new ArmorGeoModel());
    }

    /** Un gorro por material. Las otras tres piezas no pasan por aca. */
    private static String modelOf(HatItem item) {
        return switch (item.modTier()) {
            case NADIENITE -> "crown";
            case MUSHASHITE -> "fedora";
            case JOANFOITE -> "wizard_hat";
        };
    }

    private static class ArmorGeoModel extends GeoModel<HatItem> {

        @Override
        public ResourceLocation getModelResource(HatItem item) {
            return path("geo/" + modelOf(item) + ".geo.json");
        }

        @Override
        public ResourceLocation getTextureResource(HatItem item) {
            // un solo atlas por material: mismos huecos de color para todas las piezas
            return path("textures/armor/" + item.modTier().id() + "_set.png");
        }

        @Override
        public ResourceLocation getAnimationResource(HatItem item) {
            return path("animations/hat.animation.json");
        }

        private static ResourceLocation path(String path) {
            return ResourceLocation.fromNamespaceAndPath(UltimateArmouryCore.MOD_ID, path);
        }
    }
}
