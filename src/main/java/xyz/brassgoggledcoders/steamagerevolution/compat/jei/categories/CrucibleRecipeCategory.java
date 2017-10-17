package xyz.brassgoggledcoders.steamagerevolution.compat.jei.categories;

import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.crucible.CrucibleRecipe;

public class CrucibleRecipeCategory extends SARRecipeCategory<CrucibleRecipe> {

	public static final String uid = "crucible";

	public CrucibleRecipeCategory() {
		super(uid, "Crucible");
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, CrucibleRecipe recipeWrapper, IIngredients ingredients) {
		recipeLayout.getItemStacks().init(0, true, 2, 23);
		recipeLayout.getFluidStacks().init(1, false, 55, 1, 20, 60, Fluid.BUCKET_VOLUME * 8, true, null);

		recipeLayout.getItemStacks().set(0, ingredients.getInputs(ItemStack.class).get(0));
		recipeLayout.getFluidStacks().set(1, ingredients.getOutputs(FluidStack.class).get(0));
	}

}
