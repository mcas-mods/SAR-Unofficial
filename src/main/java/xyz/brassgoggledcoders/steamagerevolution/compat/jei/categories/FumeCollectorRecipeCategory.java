package xyz.brassgoggledcoders.steamagerevolution.compat.jei.categories;

import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import xyz.brassgoggledcoders.steamagerevolution.recipes.FumeCollectorRecipe;
import xyz.brassgoggledcoders.steamagerevolution.tileentities.TileEntityFumeCollector;

public class FumeCollectorRecipeCategory extends SARRecipeCategory<FumeCollectorRecipe> {

	public FumeCollectorRecipeCategory() {
		super("fume_collector");
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, FumeCollectorRecipe recipeWrapper, IIngredients ingredients) {
		recipeLayout.getItemStacks().init(0, true, 0, 23);
		recipeLayout.getFluidStacks().init(1, false, 53, 1, 20, 60, TileEntityFumeCollector.outputCapacity, true, null);

		recipeLayout.getItemStacks().set(0, ingredients.getInputs(VanillaTypes.ITEM).get(0));
		recipeLayout.getFluidStacks().set(1, ingredients.getOutputs(VanillaTypes.FLUID).get(0));
	}

}
