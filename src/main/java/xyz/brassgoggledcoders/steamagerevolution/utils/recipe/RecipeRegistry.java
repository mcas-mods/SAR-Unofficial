package xyz.brassgoggledcoders.steamagerevolution.utils.recipe;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;

import java.util.ArrayList;
import java.util.HashMap;

public class RecipeRegistry {
    private static HashMap<String, ArrayList<SARMachineRecipe>> recipeMasterlist = Maps.newHashMap();

    public static void addRecipe(String crafter, SARMachineRecipe recipe) {
        if (!recipeMasterlist.containsKey(crafter)) {
            SteamAgeRevolution.instance.getLogger().devInfo("Recipe machine " + crafter + " did not exist, creating");
            recipeMasterlist.put(crafter, Lists.newArrayList());
        }
        ArrayList<SARMachineRecipe> recipeList = recipeMasterlist.get(crafter);
        // if(recipeList.containsValue(recipe)) {
        // SteamAgeRevolution.instance.getLogger().devError("Attempted to add duplicate
        // recipe");
        // }
        // else {
        recipeList.add(recipe);
        // }
    }

    public static HashMap<String, ArrayList<SARMachineRecipe>> getRecipeMasterlist() {
        return recipeMasterlist;
    }

    public static ArrayList<SARMachineRecipe> getRecipesForMachine(String machineType) {
        if (!recipeMasterlist.containsKey(machineType)) {
            recipeMasterlist.put(machineType, Lists.newArrayList());
        }
        return recipeMasterlist.get(machineType);
    }

}
