package xyz.brassgoggledcoders.steamagerevolution.compat.jei.categories;

import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.compat.jei.SARJEIPlugin;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.MachineRecipe;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.vat.ControllerVat;

public class VatRecipeCategory extends SARRecipeCategory<MachineRecipe> {

	public VatRecipeCategory() {
		super("vat");
	}

	@Override
	public String getModName() {
		return SteamAgeRevolution.MODNAME;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, MachineRecipe recipeWrapper, IIngredients ingredients) {
		for(int i = 0; i < recipeWrapper.getFluidInputs().length; i++) {
			FluidStack stack = recipeWrapper.getFluidInputs()[i].getFluid();
			if(stack != null) {
				recipeLayout.getFluidStacks().init(i, true, 1 + (i * 25), 1, 20, 60,
						ControllerVat.inputCapacity / recipeWrapper.getFluidInputs().length, true,
						SARJEIPlugin.tankBackground);
				recipeLayout.getFluidStacks().set(i, recipeWrapper.getFluidInputs()[i].getFluid());
			}
		}
		if(recipeWrapper.getItemInputs() != null) {
			for(int i2 = 0; i2 < recipeWrapper.getItemInputs().length; i2++) {
				ItemStack stack = recipeWrapper.getItemInputs()[i2].getMatchingStacks()[0];
				if(!stack.isEmpty()) {
					recipeLayout.getItemStacks().init(i2 + 3, true, 76, 2 + (i2 * 18));
					recipeLayout.getItemStacks().set(i2 + 3, recipeWrapper.getItemInputs()[i2].getMatchingStacks()[0]);// TODO
				}
			}
		}
		recipeLayout.getFluidStacks().init(6, false, 132, 1, 20, 60, ControllerVat.outputCapacity, true,
				SARJEIPlugin.tankBackground);
		recipeLayout.getFluidStacks().set(6, ingredients.getOutputs(VanillaTypes.FLUID).get(0));

		recipeLayout.getFluidStacks().addTooltipCallback(SARJEIPlugin.fluidTooltipCallback);
	}

}
