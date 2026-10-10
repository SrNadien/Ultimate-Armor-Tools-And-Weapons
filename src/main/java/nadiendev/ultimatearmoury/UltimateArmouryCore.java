package nadiendev.ultimatearmoury;

import com.mojang.logging.LogUtils;
import nadiendev.ultimatearmoury.config.ConfigCondition;
import nadiendev.ultimatearmoury.config.ModConfigs;
import nadiendev.ultimatearmoury.datagen.DataGenerators;
import nadiendev.ultimatearmoury.registry.ArmorAdds;

import nadiendev.ultimatearmoury.registry.BlocksAdds;
import nadiendev.ultimatearmoury.registry.CompressedBlocks;
import nadiendev.ultimatearmoury.registry.CreativeTab;
import nadiendev.ultimatearmoury.registry.FluidsRegistry;
import nadiendev.ultimatearmoury.registry.HammerAdds;
import nadiendev.ultimatearmoury.registry.ItemsAdds;
import nadiendev.ultimatearmoury.registry.LootModifiersRegister;
import nadiendev.ultimatearmoury.registry.MeshAdds;
import nadiendev.ultimatearmoury.registry.ModArmorMaterials;
import nadiendev.ultimatearmoury.registry.ModDataComponents;
import nadiendev.ultimatearmoury.registry.ModOreBlocks;
import nadiendev.ultimatearmoury.registry.ToolsAdds;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.slf4j.Logger;

@Mod(UltimateArmouryCore.MOD_ID)
public class UltimateArmouryCore {
    public static final String MOD_ID = "ultimatearmoury";
    public static final Logger LOGGER = LogUtils.getLogger();

    public UltimateArmouryCore(IEventBus modEventBus, ModContainer modContainer) {
        LOGGER.info("Inicializando Ultimate Armoury Mod...");

        modContainer.registerConfig(ModConfig.Type.COMMON, ModConfigs.SPEC);
        ConfigCondition.register(modEventBus);

        BlocksAdds.register(modEventBus);
        ModOreBlocks.register(modEventBus);
        ItemsAdds.register(modEventBus);
        ArmorAdds.register(modEventBus);
        ToolsAdds.register(modEventBus);

        FluidsRegistry.FLUID_TYPES.register(modEventBus);
        FluidsRegistry.FLUIDS.register(modEventBus);
        FluidsRegistry.FLUID_BLOCKS.register(modEventBus);

        LootModifiersRegister.register(modEventBus);
        MeshAdds.register(modEventBus);
        HammerAdds.register(modEventBus);
        CompressedBlocks.register(modEventBus);

        CreativeTab.register(modEventBus);

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(DataGenerators::gatherClientData);
        modEventBus.addListener(DataGenerators::gatherServerData);

        LOGGER.info("Ultimate Armoury Mod inicializado correctamente!");
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("Configuracion comun de Ultimate Armoury");
    }
}
