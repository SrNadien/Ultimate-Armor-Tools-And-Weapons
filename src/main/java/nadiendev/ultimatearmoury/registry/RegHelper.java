package nadiendev.ultimatearmoury.registry;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public final class RegHelper {

    private static final ThreadLocal<BlockBehaviour.Properties> BLOCK_PROPERTIES = new ThreadLocal<>();
    private static final ThreadLocal<Item.Properties> ITEM_PROPERTIES = new ThreadLocal<>();

    private RegHelper() {
    }

    public static BlockBehaviour.Properties blockProps() {
        BlockBehaviour.Properties properties = BLOCK_PROPERTIES.get();
        return properties != null ? properties : BlockBehaviour.Properties.of();
    }

    public static Item.Properties itemProps() {
        Item.Properties properties = ITEM_PROPERTIES.get();
        return properties != null ? properties : new Item.Properties();
    }

    public static <T extends Block> DeferredBlock<T> block(DeferredRegister.Blocks registry,
                                                           String name, Supplier<T> supplier) {
        return registry.registerBlock(name, properties -> {
            BLOCK_PROPERTIES.set(properties);
            try {
                return supplier.get();
            } finally {
                BLOCK_PROPERTIES.remove();
            }
        });
    }

    public static <T extends Item> DeferredItem<T> item(DeferredRegister.Items registry,
                                                        String name, Supplier<T> supplier) {
        return registry.registerItem(name, properties -> {
            ITEM_PROPERTIES.set(properties);
            try {
                return supplier.get();
            } finally {
                ITEM_PROPERTIES.remove();
            }
        });
    }
}
