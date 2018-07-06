package xyz.brassgoggledcoders.steamagerevolution.compat.crafttweaker;

import java.util.ArrayList;

import javax.annotation.Nullable;

import org.apache.commons.lang3.ArrayUtils;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import xyz.brassgoggledcoders.steamagerevolution.utils.recipe.RecipeRegistry;
import xyz.brassgoggledcoders.steamagerevolution.utils.recipe.SARMachineRecipe;
import xyz.brassgoggledcoders.steamagerevolution.utils.recipe.SARMachineRecipe.MachineRecipeBuilder;

public class MachineTweaker {

	public static void addRecipe(String machine, @Nullable IIngredient[] itemInputs,
			@Nullable ILiquidStack[] fluidInputs, @Nullable IItemStack[] itemOutputs,
			@Nullable ILiquidStack[] fluidOutputs, int timeToCraft, int steamCost) {
		Object[] itemInputsObj = null;
		FluidStack[] fluidInputsStack = null;
		ItemStack[] itemOutputsStack = null;
		FluidStack[] fluidOutputsStack = null;
		if(itemInputs != null) {
			itemInputsObj = new Object[itemInputs.length];
			for(int i = 0; i < itemInputs.length; i++) {
				itemInputsObj[i] = CTHelper.toObject(itemInputs[i]);
			}
		}
		if(fluidInputs != null) {
			fluidInputsStack = new FluidStack[fluidInputs.length];
			for(int i = 0; i < fluidInputs.length; i++) {
				fluidInputsStack[i] = CTHelper.toFluidStack(fluidInputs[i]);
			}
		}
		if(itemOutputs != null) {
			itemOutputsStack = new ItemStack[itemOutputs.length];
			for(int i = 0; i < itemOutputs.length; i++) {
				itemOutputsStack[i] = CTHelper.toItemStack(itemOutputs[i]);
			}
		}
		if(fluidOutputs != null) {
			fluidOutputsStack = new FluidStack[fluidOutputs.length];
			for(int i = 0; i < fluidOutputsStack.length; i++) {
				fluidOutputsStack[i] = CTHelper.toFluidStack(fluidOutputs[i]);
			}
		}
		SARMachineRecipe r = new MachineRecipeBuilder(machine).setItemInputs(itemInputsObj)
				.setFluidInputs(fluidInputsStack).setItemOutputs(itemOutputsStack).setFluidOutputs(fluidOutputsStack)
				.setCraftTime(timeToCraft).setSteamCost(steamCost).build();
		CraftTweakerAPI.apply(new Add(machine, r));
	}

	protected static class Add implements IAction {
		private final String crafter;
		private final SARMachineRecipe recipe;

		public Add(String crafter, SARMachineRecipe recipe) {
			this.crafter = crafter;
			this.recipe = recipe;
		}

		@Override
		public void apply() {
			RecipeRegistry.addRecipe(crafter, recipe);
		}

		@Override
		public String describe() {
			return "Adding " + crafter + " Recipe"; // TODO Recipe toString()
		}
	}

	public static void removeRecipe(String crafter, ItemStack[] itemOutputs, FluidStack[] fluidOutputs) {
		CraftTweakerAPI.apply(new Remove(crafter, itemOutputs, fluidOutputs));
	}

	protected static class Remove implements IAction {

		private final String crafter;
		private final ItemStack[] itemOutputs;
		private final FluidStack[] fluidOutputs;

		public Remove(String machine, @Nullable ItemStack[] itemOutputs, @Nullable FluidStack[] fluidOutputs) {
			this.crafter = machine;
			this.itemOutputs = itemOutputs;
			this.fluidOutputs = fluidOutputs;
		}

		@Override
		public void apply() {
			ArrayList<SARMachineRecipe> recipeList = RecipeRegistry.getRecipesForMachine(crafter);
			for(SARMachineRecipe r : recipeList) {
				boolean itemOutputMatches = false;
				boolean fluidOutputMatches = false;
				// TODO Make this match more flexibly
				if(ArrayUtils.isNotEmpty(itemOutputs)) {
					itemOutputMatches = r.getItemOutputs().equals(itemOutputs);
				}
				else {
					itemOutputMatches = true;
				}
				if(ArrayUtils.isNotEmpty(fluidOutputs)) {
					fluidOutputMatches = r.getFluidOutputs().equals(fluidOutputs);
				}
				else {
					fluidOutputMatches = true;
				}
				if(itemOutputMatches && fluidOutputMatches) {
					recipeList.remove(r);
				}
			}
		}

		@Override
		public String describe() {
			return "Removing " + crafter + " recipe";
		}

	}
}
