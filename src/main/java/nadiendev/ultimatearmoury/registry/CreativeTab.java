package nadiendev.ultimatearmoury.registry;

import nadiendev.ultimatearmoury.UltimateArmouryCore;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CreativeTab {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, UltimateArmouryCore.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> ULTIMATE_ARMOURY_TAB =
            CREATIVE_MODE_TABS.register("ultimate_armoury_tab", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.ultimatearmoury"))
                    .icon(() -> new ItemStack(ToolsAdds.CHANCLA.get()))
                    .displayItems((parameters, output) -> {
                         output.accept(ItemsAdds.RAW_MUSHASHITE.get());
                        output.accept(ItemsAdds.MUSHASHITE_INGOT.get());
                        output.accept(ItemsAdds.MUSHASHITE_NUGGET.get());
                        output.accept(ItemsAdds.RAW_JOANFOITE.get());
                        output.accept(ItemsAdds.JOANFOITE_INGOT.get());
                        output.accept(ItemsAdds.JOANFOITE_NUGGET.get());
                        output.accept(ItemsAdds.RAW_NADIENITE.get());
                        output.accept(ItemsAdds.NADIENITE_INGOT.get());
                        output.accept(ItemsAdds.NADIENITE_NUGGET.get());
                        output.accept(ItemsAdds.STEEL_INGOT.get());
                        output.accept(ItemsAdds.STEEL_NUGGET.get());
                        output.accept(ItemsAdds.NETHERITE_NUGGET.get());

                        output.accept(ItemsAdds.NETHERITE_DAGGER.get());

                        output.accept(BlocksAdds.STEEL_BLOCK.get());
                        output.accept(BlocksAdds.SEXY_XD.get());
                        output.accept(BlocksAdds.MUSHASHITE_BLOCK.get());
                        output.accept(BlocksAdds.JOANFOITE_BLOCK.get());
                        output.accept(BlocksAdds.NADIENITE_BLOCK.get());
                        output.accept(BlocksAdds.NETHER_STAR_BLOCK.get());
                        output.accept(BlocksAdds.ENDER_PEARL_BLOCK.get());

                        output.accept(ModOreBlocks.MUSHASHITE_ORE.get());
                        output.accept(ModOreBlocks.DEEPSLATE_MUSHASHITE_ORE.get());
                        output.accept(ModOreBlocks.JOANFOITE_ORE.get());
                        output.accept(ModOreBlocks.DEEPSLATE_JOANFOITE_ORE.get());
                        output.accept(ModOreBlocks.NADIENITE_ORE.get());
                        output.accept(ModOreBlocks.DEEPSLATE_NADIENITE_ORE.get());

                        output.accept(ItemsAdds.CORAZON_DE_LOS_CAIDOS.get());
                        output.accept(ItemsAdds.CORAZON_DE_LOS_FANTASMAS.get());
                        output.accept(ItemsAdds.CORAZON_DE_LA_ELITE.get());

                        output.accept(ArmorAdds.MUSHASHITE_GORRO.get());
                        output.accept(ArmorAdds.MUSHASHITE_REMERA.get());
                        output.accept(ArmorAdds.MUSHASHITE_GAYUMBOS.get());
                        output.accept(ArmorAdds.MUSHASHITE_MEDIAS.get());
                        output.accept(ToolsAdds.MUSHASHITE_SWORD.get());
                        output.accept(ToolsAdds.MUSHASHITE_AXE.get());
                        output.accept(ToolsAdds.MUSHASHITE_PICKAXE.get());
                        output.accept(ToolsAdds.MUSHASHITE_HOE.get());
                        output.accept(ToolsAdds.MUSHASHITE_SHOVEL.get());

                        output.accept(ArmorAdds.JOANFOITE_GORRO.get());
                        output.accept(ArmorAdds.JOANFOITE_REMERA.get());
                        output.accept(ArmorAdds.JOANFOITE_GAYUMBOS.get());
                        output.accept(ArmorAdds.JOANFOITE_MEDIAS.get());
                        output.accept(ToolsAdds.JOANFOITE_SWORD.get());
                        output.accept(ToolsAdds.JOANFOITE_AXE.get());
                        output.accept(ToolsAdds.JOANFOITE_PICKAXE.get());
                        output.accept(ToolsAdds.JOANFOITE_HOE.get());
                        output.accept(ToolsAdds.JOANFOITE_SHOVEL.get());

                        output.accept(ArmorAdds.NADIENITE_GORRO.get());
                        output.accept(ArmorAdds.NADIENITE_REMERA.get());
                        output.accept(ArmorAdds.NADIENITE_GAYUMBOS.get());
                        output.accept(ArmorAdds.NADIENITE_MEDIAS.get());
                        output.accept(ToolsAdds.NADIENITE_SWORD.get());
                        output.accept(ToolsAdds.NADIENITE_AXE.get());
                        output.accept(ToolsAdds.NADIENITE_PICKAXE.get());
                        output.accept(ToolsAdds.NADIENITE_HOE.get());
                        output.accept(ToolsAdds.NADIENITE_SHOVEL.get());
                        output.accept(ToolsAdds.CHANCLA.get());

                        if (MeshAdds.sieveModPresent()) {
                            MeshAdds.all().forEach(mesh -> output.accept(mesh.get()));
                            HammerAdds.all().forEach(hammer -> output.accept(hammer.get()));
                        }

                        CompressedBlocks.all().forEach(block -> output.accept(block.get()));

                        output.accept(ItemsAdds.NADIENITE_FLUID_BUCKET.get());

                    })
                    .build()
            );

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
