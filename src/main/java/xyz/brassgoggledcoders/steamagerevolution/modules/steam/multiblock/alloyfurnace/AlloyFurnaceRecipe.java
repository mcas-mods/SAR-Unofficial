package xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.alloyfurnace;

import java.util.ArrayList;

import javax.annotation.Nullable;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class AlloyFurnaceRecipe {

	public final Pair<FluidStack, Object> input;
	public final FluidStack output;
	public final boolean requiresHardCase;

	private AlloyFurnaceRecipe(Pair<FluidStack, Object> input, FluidStack output, boolean requiresHardCase) {
		this.input = input;
		this.output = output;
		this.requiresHardCase = requiresHardCase;
	}

	private AlloyFurnaceRecipe(Pair<FluidStack, Object> input, FluidStack output) {
		this(input, output, false);
	}

	private static ArrayList<AlloyFurnaceRecipe> recipeList = new ArrayList<AlloyFurnaceRecipe>();

	public static void addAlloyFurnaceRecipe(FluidStack input1, FluidStack input2, FluidStack output) {
		recipeList.add(new AlloyFurnaceRecipe(Pair.of(input1, input2), output));
	}

	public static void addUpgradedAlloyFurnaceRecipe(FluidStack input1, FluidStack input2, FluidStack output) {
		recipeList.add(new AlloyFurnaceRecipe(Pair.of(input1, input2), output, true));
	}

	public static void addAlloyFurnaceRecipe(FluidStack input1, ItemStack input2, FluidStack output) {
		recipeList.add(new AlloyFurnaceRecipe(Pair.of(input1, input2), output));
	}

	public static void addUpgradedAlloyFurnaceRecipe(FluidStack input1, ItemStack input2, FluidStack output) {
		recipeList.add(new AlloyFurnaceRecipe(Pair.of(input1, input2), output, true));
	}

	@Nullable
	// TODO
	public static AlloyFurnaceRecipe getRecipe(Pair<FluidStack, Object> input) {
		for(AlloyFurnaceRecipe r : recipeList) {
			FluidStack fluid = null;
			ItemStack item = null;
			if(r.input.getRight() instanceof FluidStack) {
				fluid = (FluidStack) r.input.getRight();
			}
			else if(r.input.getRight() instanceof ItemStack) {
				item = (ItemStack) r.input.getRight();
			}
			// else { who's been throwing rubbish in my recipe registry?! }
			if(r.input.getLeft().isFluidStackIdentical(input.getLeft())
					&& ((item != null && ItemStack.areItemStacksEqual(item, (ItemStack) r.input.getRight())))
					|| (fluid != null && fluid.isFluidStackIdentical((FluidStack) r.input.getRight()))) {
				return r;
			}
		}
		return null;
	}
}
