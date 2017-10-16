package xyz.brassgoggledcoders.steamagerevolution.compat.jei.categories;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraftforge.fluids.FluidStack;
import xyz.brassgoggledcoders.steamagerevolution.compat.jei.SARJEIPlugin;
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.alloyfurnace.AlloyFurnaceRecipe;

public class AlloyForgeRecipeCategory extends SARRecipeCategory<AlloyFurnaceRecipe> {

	public static final String uid = "alloyforge";

	public AlloyForgeRecipeCategory(IGuiHelper helper) {
		super(helper, uid, "Alloy Forge");
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, AlloyFurnaceRecipe recipeWrapper, IIngredients ingredients) {
		recipeLayout.getFluidStacks().init(0, true, 1, 45);
		recipeLayout.getFluidStacks().init(1, true, 57, 45);
		recipeLayout.getFluidStacks().init(2, false, 113, 45);

		recipeLayout.getFluidStacks().set(0, ingredients.getInputs(FluidStack.class).get(0));
		recipeLayout.getFluidStacks().set(1, ingredients.getInputs(FluidStack.class).get(1));
		recipeLayout.getFluidStacks().set(2, ingredients.getOutputs(FluidStack.class).get(0));

		recipeLayout.getFluidStacks().addTooltipCallback(SARJEIPlugin.fluidTooltipCallback);
	}

}
