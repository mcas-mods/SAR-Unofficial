package xyz.brassgoggledcoders.steamagerevolution.modules.processing.multiblock.furnace;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.oredict.OreDictionary;

public class SteamFurnaceRecipe {

	public final ItemStack input;
	public final ItemStack output;

	private SteamFurnaceRecipe(ItemStack input, ItemStack output) {
		this.input = input;
		this.output = output;
	}

	private static ArrayList<SteamFurnaceRecipe> recipeList = new ArrayList<SteamFurnaceRecipe>();

	public static void addSteamFurnaceRecipe(ItemStack input, ItemStack output) {
		if(!input.isEmpty())
			recipeList.add(new SteamFurnaceRecipe(input, output));
	}

	public static ItemStack getResult(ItemStack input) {
		for(SteamFurnaceRecipe r : recipeList) {
			// Check own recipes first
			if(OreDictionary.itemMatches(r.input, input, false)) {
				return r.output;
			}
			// Then furnace ones
			else {
				return FurnaceRecipes.instance().getSmeltingResult(input);
			}
		}
		return ItemStack.EMPTY;
	}
}
