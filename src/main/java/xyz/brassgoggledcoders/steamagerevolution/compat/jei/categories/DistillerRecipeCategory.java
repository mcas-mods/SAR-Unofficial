package xyz.brassgoggledcoders.steamagerevolution.compat.jei.categories;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import xyz.brassgoggledcoders.steamagerevolution.compat.jei.SARJEIPlugin;
import xyz.brassgoggledcoders.steamagerevolution.modules.alchemical.multiblocks.distiller.DistillerRecipe;

public class DistillerRecipeCategory extends SARRecipeCategory<DistillerRecipe> {

	public static final String uid = "distiller";

	public DistillerRecipeCategory(IGuiHelper helper) {
		super(helper, uid, "Distiller");
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, DistillerRecipe recipeWrapper, IIngredients ingredients) {
		recipeLayout.getFluidStacks().init(0, true, 80, 80);
		recipeLayout.getFluidStacks().init(1, false, 170, 80);
		recipeLayout.getItemStacks().init(2, false, 3, 30);

		recipeLayout.getFluidStacks().set(0, ingredients.getInputs(FluidStack.class).get(0));
		recipeLayout.getFluidStacks().set(1, ingredients.getOutputs(FluidStack.class).get(0));
		recipeLayout.getItemStacks().set(2, ingredients.getOutputs(ItemStack.class).get(0));

		recipeLayout.getFluidStacks().addTooltipCallback(SARJEIPlugin.fluidTooltipCallback);
	}

}
