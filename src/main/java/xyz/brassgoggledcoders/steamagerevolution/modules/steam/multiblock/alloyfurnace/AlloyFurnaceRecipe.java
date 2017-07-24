package xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.alloyfurnace;

import java.util.ArrayList;

import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class AlloyFurnaceRecipe {

	public final FluidStack primaryInput;
	public final ItemStack secondaryInput;
	public final FluidStack output;
	public final boolean requiresHardCase;

	private AlloyFurnaceRecipe(FluidStack primaryInput, ItemStack secondaryInput, FluidStack output,
			boolean requiresHardCase) {
		this.primaryInput = primaryInput;
		this.secondaryInput = secondaryInput;
		this.output = output;
		this.requiresHardCase = requiresHardCase;
	}

	private static ArrayList<AlloyFurnaceRecipe> recipeList = new ArrayList<AlloyFurnaceRecipe>();

	// public static void addAlloyFurnaceRecipe(FluidStack input1, FluidStack input2, FluidStack output) {
	// recipeList.add(new AlloyFurnaceRecipe(Pair.of(input1, input2), output));
	// }

	// public static void addUpgradedAlloyFurnaceRecipe(FluidStack input1, FluidStack input2, FluidStack output) {
	// recipeList.add(new AlloyFurnaceRecipe(Pair.of(input1, input2), output, true));
	// }

	// public static void addAlloyFurnaceRecipe(FluidStack input1, ItemStack input2, FluidStack output) {
	// recipeList.add(new AlloyFurnaceRecipe(Pair.of(input1, input2), output));
	// }

	public static void addUpgradedAlloyFurnaceRecipe(FluidStack input1, ItemStack input2, FluidStack output) {
		recipeList.add(new AlloyFurnaceRecipe(input1, input2, output, true));
	}

	@Nullable
	// TODO
	public static AlloyFurnaceRecipe getRecipe(FluidStack primaryInput, ItemStack secondaryInput) {
		for(AlloyFurnaceRecipe r : recipeList) {
			if(r.primaryInput.isFluidEqual(primaryInput) && ItemStack.areItemsEqual(r.secondaryInput, secondaryInput))
				return r;
		}
		return null;
	}
}
