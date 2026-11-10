package nadiendev.ultimatearmoury.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ModConfigs {

    public static final ModConfigSpec SPEC;

    public static final ModConfigSpec.BooleanValue EX_DEORUM;
    public static final ModConfigSpec.BooleanValue ALLTHEMODIUM;
    public static final ModConfigSpec.BooleanValue ALLTHECOMPRESSED;
    public static final ModConfigSpec.BooleanValue THAUMATURGE;
    public static final ModConfigSpec.BooleanValue FTB_UNEARTHED;
    public static final ModConfigSpec.BooleanValue FTB_UNEARTHED_ALLTHEMODIUM;
    public static final ModConfigSpec.BooleanValue FTB_UNEARTHED_THAUMATURGE;

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

        THAUMATURGE = builder
                .comment("Mallas y martillos de laton, thaumium y metal del vacio de Thaumaturge,",
                         "con su propia categoria en el Thaumonomicon y fuentes de calor para el crisol de Ex Deorum.",
                         "Necesita que la integracion de Ex Deorum tambien este activa.")
                .define("thaumaturge", true);

        FTB_UNEARTHED = builder
                .comment("Pinceles de acero, mushashite, joanfoite y nadienite para FTB Unearthed,",
                         "con sus recetas de crafteo y las del Unearther.")
                .define("ftbUnearthed", true);

        FTB_UNEARTHED_ALLTHEMODIUM = builder
                .comment("Pinceles de Allthemodium, Vibranium y Unobtainium.",
                         "Necesita que la integracion de FTB Unearthed tambien este activa.")
                .define("ftbUnearthedAllTheModium", true);

        FTB_UNEARTHED_THAUMATURGE = builder
                .comment("Pinceles de laton, thaumium y metal del vacio de Thaumaturge,",
                         "con su propia entrada en el Thaumonomicon.",
                         "Necesita que las integraciones de FTB Unearthed y Allthemodium tambien esten activas.")
                .define("ftbUnearthedThaumaturge", true);

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

    public static boolean thaumaturgeEnabled() {
        return exDeorumEnabled() && SPEC.isLoaded() && THAUMATURGE.get();
    }

    public static boolean ftbUnearthedEnabled() {
        return SPEC.isLoaded() && FTB_UNEARTHED.get();
    }

    public static boolean ftbUnearthedAllTheModiumEnabled() {
        return ftbUnearthedEnabled() && SPEC.isLoaded() && FTB_UNEARTHED_ALLTHEMODIUM.get();
    }

    public static boolean ftbUnearthedThaumaturgeEnabled() {
        return ftbUnearthedAllTheModiumEnabled() && SPEC.isLoaded() && FTB_UNEARTHED_THAUMATURGE.get();
    }
}
