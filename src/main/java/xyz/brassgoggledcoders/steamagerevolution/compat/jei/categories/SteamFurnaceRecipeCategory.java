package xyz.brassgoggledcoders.steamagerevolution.compat.jei.categories;

import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.MachineRecipe;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.furnace.ControllerSteamFurnace;

public class SteamFurnaceRecipeCategory extends SARRecipeCategory<MachineRecipe> {

	public SteamFurnaceRecipeCategory() {
		super(ControllerSteamFurnace.class);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, MachineRecipe recipeWrapper, IIngredients ingredients) {
		recipeLayout.getItemStacks().init(0, true, 38, 27);
		recipeLayout.getItemStacks().init(1, false, 98, 27);

		recipeLayout.getItemStacks().set(0, ingredients.getInputs(VanillaTypes.ITEM).get(0));
		recipeLayout.getItemStacks().set(1, ingredients.getOutputs(VanillaTypes.ITEM).get(0));
	}

}
