package xyz.brassgoggledcoders.steamagerevolution.utils;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;

public class RecipeRegistry {
	private static HashMap<String, ArrayList<SARMachineRecipe>> recipeMasterlist = Maps.newHashMap();

	public static void addRecipe(String crafter, ItemStack[] itemInputs, FluidStack[] fluidInputs, int ticksToProcess,
			int steamUsePerCraft, ItemStack[] itemOutputs, FluidStack[] fluidOutputs) {
		SARMachineRecipe recipe = new SARMachineRecipe(crafter, itemInputs, fluidInputs, ticksToProcess,
				steamUsePerCraft, itemOutputs, fluidOutputs);
		if(!recipeMasterlist.containsKey(crafter)) {
			SteamAgeRevolution.instance.getLogger().devInfo("Recipe machine " + crafter + " did not exist, creating");
			recipeMasterlist.put(crafter, Lists.newArrayList());
		}
		ArrayList<SARMachineRecipe> recipeList = recipeMasterlist.get(crafter);
		if(recipeList.contains(recipe)) {
			SteamAgeRevolution.instance.getLogger().devError("Attempted to add duplicate recipe");
		}
		else {
			recipeList.add(recipe);
		}
	}

	public static HashMap<String, ArrayList<SARMachineRecipe>> getRecipeMasterlist() {
		return recipeMasterlist;
	}

	public static ArrayList<SARMachineRecipe> getRecipesForMachine(String machineType) {
		return recipeMasterlist.get(machineType);
	}

}
