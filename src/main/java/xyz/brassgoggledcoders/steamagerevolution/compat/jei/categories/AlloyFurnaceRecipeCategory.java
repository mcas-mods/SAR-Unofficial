package xyz.brassgoggledcoders.steamagerevolution.compat.jei.categories;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraftforge.fluids.FluidStack;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.alloyfurnace.AlloyFurnaceRecipe;

public class AlloyFurnaceRecipeCategory implements IRecipeCategory<AlloyFurnaceRecipe> {

	private final IGuiHelper helper;

	public AlloyFurnaceRecipeCategory(IGuiHelper helper) {
		this.helper = helper;
	}

	@Override
	public String getUid() {
		return SteamAgeRevolution.MODID + ":alloyfurnace";
	}

	@Override
	public String getTitle() {
		return "Alloy Forge";
	}

	@Override
	public String getModName() {
		return SteamAgeRevolution.MODNAME;
	}

	@Override
	public IDrawable getBackground() {
		return helper.createBlankDrawable(256, 256);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, AlloyFurnaceRecipe recipeWrapper, IIngredients ingredients) {
		recipeLayout.getFluidStacks().init(0, true, 100, 100);
		recipeLayout.getFluidStacks().init(1, true, 160, 100);
		recipeLayout.getFluidStacks().init(2, false, 200, 100);

		recipeLayout.getFluidStacks().set(0, ingredients.getInputs(FluidStack.class).get(0));
		recipeLayout.getFluidStacks().set(1, ingredients.getInputs(FluidStack.class).get(1));
		recipeLayout.getFluidStacks().set(2, ingredients.getOutputs(FluidStack.class).get(0));
	}

}
