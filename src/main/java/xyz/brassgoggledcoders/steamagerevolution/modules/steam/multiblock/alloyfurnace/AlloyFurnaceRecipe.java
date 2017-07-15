package xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.alloyfurnace;

import java.util.ArrayList;

import javax.annotation.Nullable;

import net.minecraftforge.fluids.FluidStack;

public class AlloyFurnaceRecipe {

	public final FluidStack input;
	public final FluidStack output;
	public final boolean requiresHardCase;

	private AlloyFurnaceRecipe(FluidStack input, FluidStack output, boolean requiresHardCase) {
		this.input = input;
		this.output = output;
		this.requiresHardCase = requiresHardCase;
	}

	private AlloyFurnaceRecipe(FluidStack input, FluidStack output) {
		this(input, output, false);
	}

	private static ArrayList<AlloyFurnaceRecipe> recipeList = new ArrayList<AlloyFurnaceRecipe>();

	public static void addAlloyFurnaceRecipe(FluidStack input, FluidStack output) {
		recipeList.add(new AlloyFurnaceRecipe(input, output));
	}

	public static void addUpgradedAlloyFurnaceRecipe(FluidStack input, FluidStack output) {
		recipeList.add(new AlloyFurnaceRecipe(input, output, true));
	}

	@Nullable
	public static AlloyFurnaceRecipe getRecipe(FluidStack input) {
		for(AlloyFurnaceRecipe r : recipeList) {
			if(input.isFluidEqual(r.input)) {
				return r;
			}
		}
		return null;
	}
}
