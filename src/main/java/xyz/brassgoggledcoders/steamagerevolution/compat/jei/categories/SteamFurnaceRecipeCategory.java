package xyz.brassgoggledcoders.steamagerevolution.compat.jei.categories;

import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.item.ItemStack;
import xyz.brassgoggledcoders.steamagerevolution.utils.recipe.SARMachineRecipe;

public class SteamFurnaceRecipeCategory extends SARRecipeCategory<SARMachineRecipe> {

    public static final String uid = "steamfurnace";

    public SteamFurnaceRecipeCategory() {
        super(uid, "Steam Furnace");
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, SARMachineRecipe recipeWrapper, IIngredients ingredients) {
        recipeLayout.getItemStacks().init(0, true, 0, 0);
        recipeLayout.getItemStacks().init(1, false, 60, 18);

        recipeLayout.getItemStacks().set(0, ingredients.getInputs(ItemStack.class).get(0));
        recipeLayout.getItemStacks().set(1, ingredients.getOutputs(ItemStack.class).get(0));
    }

}
