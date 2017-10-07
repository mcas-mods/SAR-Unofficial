package xyz.brassgoggledcoders.steamagerevolution.modules.processing.multiblock.furnace;

import java.util.ArrayList;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.oredict.OreDictionary;

@Optional.Interface(iface = "mezz.jei.api.IRecipeWrapper", modid = "jei", striprefs = true)
public class SteamFurnaceRecipe implements IRecipeWrapper {

	public final ItemStack input;
	public final ItemStack output;

	private SteamFurnaceRecipe(ItemStack input, ItemStack output) {
		this.input = input;
		this.output = output;
	}

	private static ArrayList<SteamFurnaceRecipe> recipeList = new ArrayList<SteamFurnaceRecipe>();

	public static void addSteamFurnaceRecipe(ItemStack input, ItemStack output) {
		if(!input.isEmpty()) {
			recipeList.add(new SteamFurnaceRecipe(input, output));
		}
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

	@Optional.Method(modid = "jei")
	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInput(ItemStack.class, input);
		ingredients.setOutput(ItemStack.class, output);
	}

	public static ArrayList<SteamFurnaceRecipe> getRecipeList() {
		return recipeList;
	}
}
