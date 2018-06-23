package xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.alloyfurnace;

import java.util.ArrayList;

import javax.annotation.Nullable;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Optional;

@Optional.Interface(iface = "mezz.jei.api.recipe.IRecipeWrapper", modid = "jei", striprefs = true)
public class AlloyFurnaceRecipe implements IRecipeWrapper {

	public final FluidStack primaryInput;
	public final ItemStack secondaryInputItem;
	public final FluidStack secondaryInputFluid;
	public final FluidStack output;

	public AlloyFurnaceRecipe(FluidStack primaryInput, FluidStack secondaryInput, FluidStack output) {
		this.primaryInput = primaryInput;
		secondaryInputItem = null;
		secondaryInputFluid = secondaryInput;
		this.output = output;
	}

	private static ArrayList<AlloyFurnaceRecipe> recipeList = new ArrayList<AlloyFurnaceRecipe>();

	public static void addAlloyFurnaceRecipe(FluidStack primaryInput, FluidStack secondaryInput, FluidStack output) {
		recipeList.add(new AlloyFurnaceRecipe(primaryInput, secondaryInput, output));
	}

	@Nullable
	public static AlloyFurnaceRecipe getRecipe(FluidStack primaryInput, ItemStack secondaryInput) {
		for(AlloyFurnaceRecipe r : recipeList) {
			if(r.primaryInput.isFluidEqual(primaryInput)) {
				if(r.secondaryInputItem != null && ItemStack.areItemsEqual(r.secondaryInputItem, secondaryInput)) {
					return r;
				}
			}

		}
		return null;
	}

	@Nullable
	public static AlloyFurnaceRecipe getRecipe(FluidStack primaryInput, FluidStack secondaryInput) {
		for(AlloyFurnaceRecipe r : recipeList) {
			if(r.primaryInput.isFluidEqual(primaryInput)) {
				if(r.secondaryInputFluid != null && r.secondaryInputFluid.isFluidEqual(secondaryInput)) {
					return r;
				}
			}

		}
		return null;
	}

	public static ArrayList<AlloyFurnaceRecipe> getRecipeList() {
		return recipeList;
	}

	@Optional.Method(modid = "jei")
	@Override
	public void getIngredients(IIngredients ingredients) {
		ArrayList<FluidStack> inputs = new ArrayList<FluidStack>();
		inputs.add(primaryInput);
		inputs.add(secondaryInputFluid);
		ingredients.setInputs(FluidStack.class, inputs);
		ingredients.setOutput(FluidStack.class, output);
	}
}
