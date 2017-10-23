package xyz.brassgoggledcoders.steamagerevolution.compat.crafttweaker;

import java.util.ArrayList;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.CastingBlockRecipe;
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.crucible.CrucibleRecipe;

@ZenClass("mods.steamagerevolution.CastingBlock")
public class CastingBlockTweaker {

	@ZenMethod
	public static void addRecipe(ILiquidStack input, IItemStack output) {
		CastingBlockRecipe r = new CastingBlockRecipe(CTHelper.toFluidStack(input), CTHelper.toItemStack(output));
		CraftTweakerAPI.apply(new Add(r));
	}

	private static class Add implements IAction {
		private final CastingBlockRecipe recipe;

		public Add(CastingBlockRecipe recipe) {
			this.recipe = recipe;
		}

		@Override
		public void apply() {
			CastingBlockRecipe.getRecipeList().add(recipe);
		}

		@Override
		public String describe() {
			return "Adding Casting Block Recipe for " + recipe.output.getDisplayName();
		}
	}

	@ZenMethod
	public static void removeRecipe(IItemStack output) {
		CraftTweakerAPI.apply(new Remove(CTHelper.toItemStack(output)));
	}

	private static class Remove implements IAction {
		private final ItemStack output;

		public Remove(ItemStack output) {
			this.output = output;
		}

		@Override
		public void apply() {
			// TODO
			ArrayList<CastingBlockRecipe> toRemove = new ArrayList<CastingBlockRecipe>();
			for(CastingBlockRecipe r : CastingBlockRecipe.getRecipeList()) {
				if(output.isItemEqual(r.output)) {
					toRemove.add(r);
				}
			}
			CrucibleRecipe.getRecipeList().removeAll(toRemove);
		}

		@Override
		public String describe() {
			return "Removing Casting Block Recipe for " + output.getDisplayName();
		}
	}
}
