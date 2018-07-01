package xyz.brassgoggledcoders.steamagerevolution.utils.recipe;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.common.collect.Maps;

import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;

public class RecipeRegistry {
	private static HashMap<String, HashMap<Integer, SARMachineRecipe>> recipeMasterlist = Maps.newHashMap();
	private static int ids = 0;

	public static void addRecipe(String crafter, SARMachineRecipe recipe) {
		if(!recipeMasterlist.containsKey(crafter)) {
			SteamAgeRevolution.instance.getLogger().devInfo("Recipe machine " + crafter + " did not exist, creating");
			recipeMasterlist.put(crafter, Maps.newHashMap());
		}
		HashMap<Integer, SARMachineRecipe> recipeList = recipeMasterlist.get(crafter);
		// if(recipeList.containsValue(recipe)) {
		// SteamAgeRevolution.instance.getLogger().devError("Attempted to add duplicate
		// recipe");
		// }
		// else {
		recipe.networkID = ids++;
		recipeList.put(recipe.networkID, recipe);
		// }
	}

	public static HashMap<String, HashMap<Integer, SARMachineRecipe>> getRecipeMasterlist() {
		return recipeMasterlist;
	}

	public static ArrayList<SARMachineRecipe> getRecipesForMachine(String machineType) {
		if(!recipeMasterlist.containsKey(machineType)) {
			recipeMasterlist.put(machineType, Maps.newHashMap());
		}
		return new ArrayList<SARMachineRecipe>(recipeMasterlist.get(machineType).values());
	}

	public static SARMachineRecipe getRecipeByID(String name, int id) {
		return recipeMasterlist.get(name).get(id);
	}

}
