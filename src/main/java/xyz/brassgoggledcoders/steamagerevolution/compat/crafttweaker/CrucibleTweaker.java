package xyz.brassgoggledcoders.steamagerevolution.compat.crafttweaker;

import java.util.ArrayList;

import blusunrize.immersiveengineering.common.util.compat.crafttweaker.CraftTweakerHelper;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.crucible.CrucibleRecipe;

@ZenClass("mods.steamagerevolution.Crucible")
public class CrucibleTweaker {

	@ZenMethod
	public static void addRecipe(IItemStack input, ILiquidStack output) {
		CrucibleRecipe r =
				new CrucibleRecipe(CraftTweakerHelper.toStack(input), CraftTweakerHelper.toFluidStack(output));
		CraftTweakerAPI.apply(new Add(r));
	}

	private static class Add implements IAction {
		private final CrucibleRecipe recipe;

		public Add(CrucibleRecipe recipe) {
			this.recipe = recipe;
		}

		@Override
		public void apply() {
			CrucibleRecipe.getRecipeList().add(recipe);
		}

		@Override
		public String describe() {
			return "Adding Crucible Recipe for " + recipe.output.getLocalizedName();
		}
	}

	@ZenMethod
	public static void removeRecipe(ILiquidStack output) {
		CraftTweakerAPI.apply(new Remove(CraftTweakerHelper.toFluidStack(output)));
	}

	private static class Remove implements IAction {
		private final FluidStack output;

		public Remove(FluidStack output) {
			this.output = output;
		}

		@Override
		public void apply() {
			// TODO
			ArrayList<CrucibleRecipe> toRemove = new ArrayList<CrucibleRecipe>();
			for(CrucibleRecipe r : CrucibleRecipe.getRecipeList()) {
				if(output.isFluidEqual(new FluidStack(r.output, 1))) {
					toRemove.add(r);
				}
			}
			CrucibleRecipe.getRecipeList().removeAll(toRemove);
		}

		@Override
		public String describe() {
			return "Removing Crucible Recipe for " + output.getLocalizedName();
		}
	}
}
