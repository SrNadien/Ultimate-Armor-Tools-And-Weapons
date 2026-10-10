package nadiendev.ultimatearmoury.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ModConfigs {

    public static final ModConfigSpec SPEC;

    public static final ModConfigSpec.BooleanValue EX_DEORUM;
    public static final ModConfigSpec.BooleanValue ALLTHEMODIUM;
    public static final ModConfigSpec.BooleanValue ALLTHECOMPRESSED;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        builder.comment("Integraciones con otros mods.",
                        "Cada una solo se aplica si el mod correspondiente esta instalado.",
                        "Poner en false desactiva sus items y recetas por completo.")
               .push("integraciones");

        EX_DEORUM = builder
                .comment("Mallas de sieve, martillos y fuentes de calor para Ex Deorum.")
                .define("exDeorum", true);

        ALLTHEMODIUM = builder
                .comment("Mallas de Allthemodium, Vibranium y Unobtainium.",
                         "Necesita que la integracion de Ex Deorum tambien este activa.")
                .define("allTheModium", true);

        ALLTHECOMPRESSED = builder
                .comment("Versiones comprimidas 1x a 9x de los bloques del mod.")
                .define("allTheCompressed", true);

        builder.pop();

        SPEC = builder.build();
    }

    private ModConfigs() {
    }

    public static boolean exDeorumEnabled() {
        return SPEC.isLoaded() && EX_DEORUM.get();
    }

    public static boolean allTheModiumEnabled() {
        return exDeorumEnabled() && SPEC.isLoaded() && ALLTHEMODIUM.get();
    }

    public static boolean allTheCompressedEnabled() {
        return SPEC.isLoaded() && ALLTHECOMPRESSED.get();
    }
}
