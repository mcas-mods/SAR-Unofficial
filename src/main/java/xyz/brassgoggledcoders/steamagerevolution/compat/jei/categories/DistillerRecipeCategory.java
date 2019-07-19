package xyz.brassgoggledcoders.steamagerevolution.compat.jei.categories;

import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import xyz.brassgoggledcoders.steamagerevolution.compat.jei.SARJEIPlugin;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.MachineRecipe;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.distiller.ControllerDistiller;

public class DistillerRecipeCategory extends SARRecipeCategory<MachineRecipe> {

	public DistillerRecipeCategory() {
		super(ControllerDistiller.class);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, MachineRecipe recipeWrapper, IIngredients ingredients) {
		recipeLayout.getFluidStacks().init(0, true, 35, 4, 20, 60, ControllerDistiller.tankCapacity, true,
				SARJEIPlugin.tankBackground);
		recipeLayout.getFluidStacks().init(1, false, 91, 4, 20, 60, ControllerDistiller.tankCapacity, true,
				SARJEIPlugin.tankBackground);
		recipeLayout.getItemStacks().init(2, false, 141, 26);

		recipeLayout.getFluidStacks().set(0, ingredients.getInputs(VanillaTypes.FLUID).get(0));
		recipeLayout.getFluidStacks().set(1, ingredients.getOutputs(VanillaTypes.FLUID).get(0));
		recipeLayout.getItemStacks().set(2, ingredients.getOutputs(VanillaTypes.ITEM).get(0));
	}

}
