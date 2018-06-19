package xyz.brassgoggledcoders.steamagerevolution.utils;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;

public class RecipeRegistry {
	private static HashMap<SARMachineRecipe, String> recipeMasterlist = Maps.newHashMap();

	public static void addRecipe(String crafter, ItemStack[] itemInputs, FluidStack[] fluidInputs, int ticksToProcess,
			int steamUsePerCraft, ItemStack[] itemOutputs, FluidStack[] fluidOutputs) {
		SARMachineRecipe recipe = new SARMachineRecipe(crafter, itemInputs, fluidInputs, ticksToProcess,
				steamUsePerCraft, itemOutputs, fluidOutputs);
		if(recipeMasterlist.putIfAbsent(recipe, crafter) != null) {
			SteamAgeRevolution.instance.getLogger().devError("Attempted to add duplicate recipe");
		}
	}

	public static HashMap<SARMachineRecipe, String> getRecipeMasterlist() {
		return recipeMasterlist;
	}

	public static ArrayList<SARMachineRecipe> getRecipesForMachine(String machineType) {
		ArrayList<SARMachineRecipe> recipeList = Lists.newArrayList();
		// Goodness gracious a wild stream appeared TODO Do this at runtime
		recipeMasterlist.entrySet().parallelStream().filter(entry -> entry.getValue().equals(machineType))
				.forEach(entry -> recipeList.add(entry.getKey()));
		return recipeList;
	}

}
