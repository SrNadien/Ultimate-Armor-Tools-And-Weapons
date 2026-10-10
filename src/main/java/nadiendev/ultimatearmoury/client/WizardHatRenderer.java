package nadiendev.ultimatearmoury.client;

import com.geckolib.model.GeoModel;
import com.geckolib.renderer.GeoArmorRenderer;
import com.geckolib.renderer.base.GeoRenderState;
import nadiendev.ultimatearmoury.UltimateArmouryCore;
import nadiendev.ultimatearmoury.items.ModTier;
import nadiendev.ultimatearmoury.items.armor.WizardHatItem;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.resources.Identifier;

public class WizardHatRenderer extends GeoArmorRenderer<WizardHatItem, HumanoidRenderState> {

    public WizardHatRenderer(ModTier tier) {
        super(new WizardHatGeoModel(tier));
    }

    private static class WizardHatGeoModel extends GeoModel<WizardHatItem> {

        private static final Identifier MODEL = path("wizard_hat");
        private static final Identifier ANIMATION = path("geckolib/animations/wizard_hat.animation.json");

        private final Identifier texture;

        WizardHatGeoModel(ModTier tier) {
            this.texture = path("textures/armor/" + tier.id() + "_wizard_hat.png");
        }

        @Override
        public Identifier getModelResource(GeoRenderState state) {
            return MODEL;
        }

        @Override
        public Identifier getTextureResource(GeoRenderState state) {
            return this.texture;
        }

        @Override
        public Identifier getAnimationResource(WizardHatItem item) {
            return ANIMATION;
        }

        private static Identifier path(String path) {
            return Identifier.fromNamespaceAndPath(UltimateArmouryCore.MOD_ID, path);
        }
    }
}
