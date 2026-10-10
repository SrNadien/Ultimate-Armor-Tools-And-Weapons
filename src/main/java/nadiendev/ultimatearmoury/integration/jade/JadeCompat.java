package nadiendev.ultimatearmoury.integration.jade;

import nadiendev.ultimatearmoury.UltimateArmouryCore;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class JadeCompat implements IWailaPlugin {

    @Override
    public void register(IWailaCommonRegistration registration) {

    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {

    }

    public ResourceLocation getUid() {
        return ResourceLocation.fromNamespaceAndPath(UltimateArmouryCore.MOD_ID, "jade_plugin");
    }
}
