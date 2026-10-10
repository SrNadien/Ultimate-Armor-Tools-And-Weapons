package nadiendev.ultimatearmoury.integration.jei;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.ITooltipBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import nadiendev.ultimatearmoury.UltimateArmouryCore;
import nadiendev.ultimatearmoury.registry.ItemsAdds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

public class NetheriteDaggerCategory implements IRecipeCategory<NetheriteDaggerRecipe> {

    public static final RecipeType<NetheriteDaggerRecipe> RECIPE_TYPE = RecipeType.create(
            UltimateArmouryCore.MOD_ID, "netherite_dagger_ritual", NetheriteDaggerRecipe.class);

    private static final int SLOT_SWORD_X  = 10;
    private static final int SLOT_SWORD_Y  = 15;
    private static final int SLOT_BLOCK_X  = 50;
    private static final int SLOT_BLOCK_Y  = 15;
    private static final int SLOT_OUTPUT_X = 110;
    private static final int SLOT_OUTPUT_Y = 15;

    private final IDrawable background;
    private final IDrawable icon;

    public NetheriteDaggerCategory(IGuiHelper guiHelper) {

        this.background = guiHelper.createBlankDrawable(160, 75);
        this.icon = guiHelper.createDrawableItemStack(new ItemStack(ItemsAdds.NETHERITE_DAGGER.get()));
    }

    @Override
    public RecipeType<NetheriteDaggerRecipe> getRecipeType() {
        return RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("jei.ultimatearmoury.netherite_dagger.category");
    }

    @Override
    @SuppressWarnings("removal")
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, NetheriteDaggerRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, SLOT_SWORD_X, SLOT_SWORD_Y)
                .addIngredient(VanillaTypes.ITEM_STACK, recipe.getSword());

        builder.addSlot(RecipeIngredientRole.INPUT, SLOT_BLOCK_X, SLOT_BLOCK_Y)
                .addIngredient(VanillaTypes.ITEM_STACK, recipe.getBlock());

        builder.addSlot(RecipeIngredientRole.OUTPUT, SLOT_OUTPUT_X, SLOT_OUTPUT_Y)
                .addIngredient(VanillaTypes.ITEM_STACK, recipe.getOutput());
    }

    @Override
    public void draw(NetheriteDaggerRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        Font font = Minecraft.getInstance().font;

        guiGraphics.drawString(font, "+", SLOT_SWORD_X + 20, SLOT_SWORD_Y + 5, 0x404040, false);

        guiGraphics.drawString(font, "\u2192", SLOT_BLOCK_X + 20, SLOT_BLOCK_Y + 5, 0x404040, false);

        guiGraphics.drawString(font,
                Component.translatable("jei.ultimatearmoury.netherite_dagger.hint1").getVisualOrderText(),
                5, 42, 0x666666, false);

        guiGraphics.drawString(font,
                Component.translatable("jei.ultimatearmoury.netherite_dagger.hint2").getVisualOrderText(),
                5, 54, 0x666666, false);
    }
}
