package xyz.brassgoggledcoders.steamagerevolution.compat.jei.categories;

import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import net.minecraftforge.fluids.Fluid;
import xyz.brassgoggledcoders.steamagerevolution.utils.recipe.SARMachineRecipe;

public class CrucibleRecipeCategory extends SARRecipeCategory<SARMachineRecipe> {

	public static final String uid = "crucible";

	public CrucibleRecipeCategory() {
		super(uid, "Crucible");
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, SARMachineRecipe recipeWrapper, IIngredients ingredients) {
		recipeLayout.getItemStacks().init(0, true, 2, 23);
		recipeLayout.getFluidStacks().init(1, false, 55, 1, 20, 60, Fluid.BUCKET_VOLUME * 8, true, null);

		recipeLayout.getItemStacks().set(0, ingredients.getInputs(VanillaTypes.ITEM).get(0));
		recipeLayout.getFluidStacks().set(1, ingredients.getOutputs(VanillaTypes.FLUID).get(0));
	}

}
