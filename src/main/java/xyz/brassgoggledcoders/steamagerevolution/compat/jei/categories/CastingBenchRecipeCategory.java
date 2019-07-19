package xyz.brassgoggledcoders.steamagerevolution.compat.jei.categories;

import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import xyz.brassgoggledcoders.steamagerevolution.compat.jei.SARJEIPlugin;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.MachineRecipe;
import xyz.brassgoggledcoders.steamagerevolution.tileentities.TileEntityCastingBench;

public class CastingBenchRecipeCategory extends SARRecipeCategory<MachineRecipe> {

	public CastingBenchRecipeCategory() {
		super(TileEntityCastingBench.class);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, MachineRecipe recipeWrapper, IIngredients ingredients) {
		recipeLayout.getFluidStacks().init(0, true, 1, 1, 20, 60, TileEntityCastingBench.inputCapacity, true,
				SARJEIPlugin.tankBackground);
		recipeLayout.getItemStacks().init(1, false, 58, 23);

		recipeLayout.getFluidStacks().set(0, ingredients.getInputs(VanillaTypes.FLUID).get(0));
		recipeLayout.getItemStacks().set(1, ingredients.getOutputs(VanillaTypes.ITEM).get(0));
	}
}
