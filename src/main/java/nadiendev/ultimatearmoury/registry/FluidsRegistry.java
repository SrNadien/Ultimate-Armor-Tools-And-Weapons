package nadiendev.ultimatearmoury.registry;

import nadiendev.ultimatearmoury.UltimateArmouryCore;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class FluidsRegistry {

    public static final DeferredRegister<FluidType> FLUID_TYPES =
            DeferredRegister.create(NeoForgeRegistries.FLUID_TYPES, UltimateArmouryCore.MOD_ID);

    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(BuiltInRegistries.FLUID, UltimateArmouryCore.MOD_ID);

    public static final DeferredRegister.Blocks FLUID_BLOCKS =
            DeferredRegister.createBlocks(UltimateArmouryCore.MOD_ID);

    @SuppressWarnings({"deprecation", "removal"})
    public static final Supplier<FluidType> NADIENITE_FLUID_TYPE = FLUID_TYPES.register("nadienite_fluid",
            () -> new FluidType(FluidType.Properties.create()
                    .density(1000)
                    .viscosity(1000)
            ));

    public static final Supplier<BaseFlowingFluid.Source> NADIENITE_FLUID_SOURCE = FLUIDS.register("nadienite_fluid",
            () -> new BaseFlowingFluid.Source(nadieniteProperties()));

    public static final Supplier<BaseFlowingFluid.Flowing> NADIENITE_FLUID_FLOWING = FLUIDS.register("nadienite_fluid_flowing",
            () -> new BaseFlowingFluid.Flowing(nadieniteProperties()));

    public static final DeferredHolder<net.minecraft.world.level.block.Block, LiquidBlock> NADIENITE_FLUID_BLOCK =
            RegHelper.block(FLUID_BLOCKS, "nadienite_fluid", () -> new LiquidBlock(
                    NADIENITE_FLUID_SOURCE.get(),
                    RegHelper.blockProps()
                            .mapColor(MapColor.COLOR_LIGHT_GREEN)
                            .replaceable()
                            .noCollision()
                            .strength(100f)
                            .pushReaction(PushReaction.DESTROY)
                            .noLootTable()
                            .liquid()
            ));

    private static BaseFlowingFluid.Properties nadieniteProperties() {
        return new BaseFlowingFluid.Properties(
                () -> NADIENITE_FLUID_TYPE.get(),
                () -> NADIENITE_FLUID_SOURCE.get(),
                () -> NADIENITE_FLUID_FLOWING.get()
        )
        .slopeFindDistance(2)
        .levelDecreasePerBlock(2)
        .bucket(() -> nadiendev.ultimatearmoury.registry.ItemsAdds.NADIENITE_FLUID_BUCKET.get())
        .block(() -> NADIENITE_FLUID_BLOCK.get());
    }
}
