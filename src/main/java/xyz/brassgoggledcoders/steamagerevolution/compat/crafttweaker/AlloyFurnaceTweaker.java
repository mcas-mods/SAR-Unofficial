package xyz.brassgoggledcoders.steamagerevolution.compat.crafttweaker;

import java.util.ArrayList;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.api.liquid.ILiquidStack;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.alloyfurnace.AlloyFurnaceRecipe;

@ZenClass("mods.steamagerevolution.AlloyForge")
public class AlloyFurnaceTweaker {
	@ZenMethod
	public static void addRecipe(ILiquidStack first, ILiquidStack second, ILiquidStack output) {
		AlloyFurnaceRecipe r = new AlloyFurnaceRecipe(CTHelper.toFluidStack(first), CTHelper.toFluidStack(second),
				CTHelper.toFluidStack(output));
		CraftTweakerAPI.apply(new Add(r));
	}

	private static class Add implements IAction {
		private final AlloyFurnaceRecipe recipe;

		public Add(AlloyFurnaceRecipe recipe) {
			this.recipe = recipe;
		}

		@Override
		public void apply() {
			AlloyFurnaceRecipe.getRecipeList().add(recipe);
		}

		@Override
		public String describe() {
			return "Adding Alloy Forge Recipe for " + recipe.output.getLocalizedName();
		}
	}

	@ZenMethod
	public static void removeRecipe(ILiquidStack output) {
		CraftTweakerAPI.apply(new Remove(CTHelper.toFluidStack(output)));
	}

	private static class Remove implements IAction {
		private final FluidStack output;

		public Remove(FluidStack output) {
			this.output = output;
		}

		@Override
		public void apply() {
			// TODO
			ArrayList<AlloyFurnaceRecipe> toRemove = new ArrayList<AlloyFurnaceRecipe>();
			for(AlloyFurnaceRecipe r : AlloyFurnaceRecipe.getRecipeList()) {
				if(r.output == output) {
					toRemove.add(r);
				}
			}
			AlloyFurnaceRecipe.getRecipeList().removeAll(toRemove);
		}

		@Override
		public String describe() {
			return "Removing Alloy Forge Recipe for " + output.getLocalizedName();
		}
	}
}
