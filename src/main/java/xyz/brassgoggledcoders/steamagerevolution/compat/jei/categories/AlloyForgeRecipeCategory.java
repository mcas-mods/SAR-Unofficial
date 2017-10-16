package xyz.brassgoggledcoders.steamagerevolution.compat.jei.categories;

import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraftforge.fluids.FluidStack;
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.alloyfurnace.AlloyFurnaceRecipe;
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.alloyfurnace.ControllerAlloyFurnace;

public class AlloyForgeRecipeCategory extends SARRecipeCategory<AlloyFurnaceRecipe> {

	public static final String uid = "alloyforge";
	// private final IDrawable tankOverlay;

	public AlloyForgeRecipeCategory() {
		super(uid, "Alloy Forge");
		// TODO
		// tankOverlay = helper.createDrawable(background, 134, 55, 20, 49, 7, 5, 0, 0);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, AlloyFurnaceRecipe recipeWrapper, IIngredients ingredients) {
		IGuiFluidStackGroup guiFluidStacks = recipeLayout.getFluidStacks();
		guiFluidStacks.init(0, true, 1, 1, 20, 60, ControllerAlloyFurnace.inputCapacity / 2, true, null);
		guiFluidStacks.init(1, true, 57, 1, 20, 60, ControllerAlloyFurnace.inputCapacity / 2, true, null);
		guiFluidStacks.init(2, false, 113, 1, 20, 60, ControllerAlloyFurnace.outputCapacity, true, null);

		guiFluidStacks.set(0, ingredients.getInputs(FluidStack.class).get(0));
		guiFluidStacks.set(1, ingredients.getInputs(FluidStack.class).get(1));
		guiFluidStacks.set(2, ingredients.getOutputs(FluidStack.class).get(0));
	}

}
