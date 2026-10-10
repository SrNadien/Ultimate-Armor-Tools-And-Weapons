package nadiendev.ultimatearmoury.items.armor;

import com.geckolib.animatable.GeoItem;
import com.geckolib.animatable.client.GeoRenderProvider;
import com.geckolib.animatable.instance.AnimatableInstanceCache;
import com.geckolib.animatable.manager.AnimatableManager;
import com.geckolib.renderer.GeoArmorRenderer;
import com.geckolib.util.GeckoLibUtil;
import nadiendev.ultimatearmoury.client.WizardHatRenderer;
import nadiendev.ultimatearmoury.items.ModTier;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.ArmorType;

import java.util.function.Consumer;

public class WizardHatItem extends TieredArmorItem implements GeoItem {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public WizardHatItem(ModTier tier, Properties properties) {
        super(tier, ArmorType.HELMET, properties);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
        consumer.accept(new GeoRenderProvider() {

            private GeoArmorRenderer<?, ?> renderer;

            @Override
            public GeoArmorRenderer<?, ?> getGeoArmorRenderer(ItemStack stack, EquipmentSlot slot) {
                if (this.renderer == null) {
                    this.renderer = new WizardHatRenderer(modTier());
                }
                return this.renderer;
            }
        });
    }
}
