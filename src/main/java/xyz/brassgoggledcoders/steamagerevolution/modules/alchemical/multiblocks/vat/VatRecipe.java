package xyz.brassgoggledcoders.steamagerevolution.modules.alchemical.multiblocks.vat;

import java.util.ArrayList;
import java.util.Arrays;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Optional;

@Optional.Interface(iface = "mezz.jei.api.recipe.IRecipeWrapper", modid = "jei", striprefs = true)
public class VatRecipe implements IRecipeWrapper {
	public final FluidStack[] fluidInputs;
	public final ItemStack[] itemInputs;
	public final FluidStack output;

	private VatRecipe(FluidStack[] fluids, ItemStack[] items, FluidStack output) {
		this.fluidInputs = fluids;
		this.itemInputs = items;
		this.output = output;
	}

	private static ArrayList<VatRecipe> recipeList = new ArrayList<VatRecipe>();

	public static ArrayList<VatRecipe> getRecipeList() {
		return recipeList;
	}

	@Optional.Method(modid = "jei")
	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputs(FluidStack.class, Arrays.asList(fluidInputs));
		if(itemInputs != null) {
			ingredients.setInputs(ItemStack.class, Arrays.asList(itemInputs));
		}
		ingredients.setOutput(FluidStack.class, output);
	}
}
