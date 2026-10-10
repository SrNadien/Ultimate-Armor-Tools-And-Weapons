package nadiendev.ultimatearmoury.events;

import nadiendev.ultimatearmoury.UltimateArmouryCore;
import nadiendev.ultimatearmoury.registry.FluidsRegistry;
import net.minecraft.client.renderer.block.FluidModel;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.resources.Identifier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterFluidModelsEvent;
import net.neoforged.neoforge.client.fluid.FluidTintSources;

@EventBusSubscriber(modid = UltimateArmouryCore.MOD_ID, value = Dist.CLIENT)
public class ClientSetup {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
        });
    }

    @SubscribeEvent
    public static void onRegisterFluidModels(RegisterFluidModelsEvent event) {
        Material still = material("block/nadienite_fluid");
        Material flowing = material("block/nadienite_flow");

        event.register(
                new FluidModel.Unbaked(still, flowing, still, FluidTintSources.constant(0xFFFFFFFF)),
                FluidsRegistry.NADIENITE_FLUID_SOURCE::get,
                FluidsRegistry.NADIENITE_FLUID_FLOWING::get
        );
    }

    private static Material material(String path) {
        return new Material(Identifier.fromNamespaceAndPath(UltimateArmouryCore.MOD_ID, path));
    }
}
