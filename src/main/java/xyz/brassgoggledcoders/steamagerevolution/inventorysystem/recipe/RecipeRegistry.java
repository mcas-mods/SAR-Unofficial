package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;

public class RecipeRegistry {
	private static HashMap<String, ArrayList<MachineRecipe>> recipeMasterlist = Maps.newHashMap();

	public static void addRecipe(String crafter, MachineRecipe recipe) {
		if (!recipeMasterlist.containsKey(crafter)) {
			SteamAgeRevolution.instance.getLogger().devInfo("Recipe machine " + crafter + " did not exist, creating");
			recipeMasterlist.put(crafter, Lists.newArrayList());
		}
		ArrayList<MachineRecipe> recipeList = recipeMasterlist.get(crafter);
		//FIXME Check for duplicate inputs here
		if (recipeList.contains(recipe)) {
			SteamAgeRevolution.instance.getLogger().devError("Attempted to add duplicate recipe");
		} else {
			recipeList.add(recipe);
		}
	}

	public static HashMap<String, ArrayList<MachineRecipe>> getRecipeMasterlist() {
		return recipeMasterlist;
	}

	public static ArrayList<MachineRecipe> getRecipesForMachine(String machineType) {
		if (!recipeMasterlist.containsKey(machineType)) {
			recipeMasterlist.put(machineType, Lists.newArrayList());
		}
		return recipeMasterlist.get(machineType);
	}
}
