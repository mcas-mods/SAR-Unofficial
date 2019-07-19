package xyz.brassgoggledcoders.steamagerevolution.compat.jei.categories;

import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import net.minecraftforge.fluids.Fluid;
import xyz.brassgoggledcoders.steamagerevolution.compat.jei.SARJEIPlugin;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.MachineRecipe;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.steelworks.ControllerSteelworks;

public class SteelworksRecipeCategory extends SARRecipeCategory<MachineRecipe> {

	public SteelworksRecipeCategory() {
		super(ControllerSteelworks.class);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, MachineRecipe recipeWrapper, IIngredients ingredients) {
		IGuiFluidStackGroup guiFluidStacks = recipeLayout.getFluidStacks();
		guiFluidStacks.init(0, true, 35, 4, 20, 60, Fluid.BUCKET_VOLUME, false, SARJEIPlugin.tankBackground);
		recipeLayout.getItemStacks().init(1, true, 76, 25);
		guiFluidStacks.init(2, false, 135, 4, 20, 60, Fluid.BUCKET_VOLUME, true, SARJEIPlugin.tankBackground);

		guiFluidStacks.set(0, ingredients.getInputs(VanillaTypes.FLUID).get(0));
		recipeLayout.getItemStacks().set(1, ingredients.getInputs(VanillaTypes.ITEM).get(0));
		guiFluidStacks.set(2, ingredients.getOutputs(VanillaTypes.FLUID).get(0));
	}

}
