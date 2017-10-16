package xyz.brassgoggledcoders.steamagerevolution.compat.jei.categories;

import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import xyz.brassgoggledcoders.steamagerevolution.modules.alchemical.multiblocks.distiller.DistillerRecipe;
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.alloyfurnace.ControllerAlloyFurnace;

public class DistillerRecipeCategory extends SARRecipeCategory<DistillerRecipe> {

	public static final String uid = "distiller";

	public DistillerRecipeCategory() {
		super(uid, "Distiller");
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, DistillerRecipe recipeWrapper, IIngredients ingredients) {
		recipeLayout.getFluidStacks().init(0, true, 1, 1, 20, 60, ControllerAlloyFurnace.inputCapacity / 2, true, null);
		recipeLayout.getFluidStacks().init(1, false, 57, 1, 20, 60, ControllerAlloyFurnace.inputCapacity / 2, true,
				null);
		recipeLayout.getItemStacks().init(2, false, 111, 23);

		recipeLayout.getFluidStacks().set(0, ingredients.getInputs(FluidStack.class).get(0));
		recipeLayout.getFluidStacks().set(1, ingredients.getOutputs(FluidStack.class).get(0));
		recipeLayout.getItemStacks().set(2, ingredients.getOutputs(ItemStack.class).get(0));
	}

}
