package xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.alloyfurnace;

import java.util.ArrayList;

import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class AlloyFurnaceRecipe {

	public final FluidStack primaryInput;
	public final ItemStack secondaryInputItem;
	public final FluidStack secondaryInputFluid;
	public final FluidStack output;

	private AlloyFurnaceRecipe(FluidStack primaryInput, FluidStack secondaryInput, FluidStack output) {
		this.primaryInput = primaryInput;
		this.secondaryInputItem = null;
		this.secondaryInputFluid = secondaryInput;
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
}
