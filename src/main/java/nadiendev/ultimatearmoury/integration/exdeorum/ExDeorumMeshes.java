package nadiendev.ultimatearmoury.integration.exdeorum;

import net.minecraft.world.item.Item;
import thedarkcolour.exdeorum.item.MeshItem;

public class ExDeorumMeshes {

    private ExDeorumMeshes() {
    }

    public static Item create(Item.Properties properties) {
        return new MeshItem(properties);
    }
}
