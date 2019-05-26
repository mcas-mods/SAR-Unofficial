package xyz.brassgoggledcoders.steamagerevolution.modules.processing.multiblock.sawmill;

import java.util.ArrayList;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.oredict.OreDictionary;

@Optional.Interface(iface = "mezz.jei.api.recipe.IRecipeWrapper", modid = "jei", striprefs = true)
public class SawmillRecipe implements IRecipeWrapper {

	public final ItemStack input;
	public final ItemStack output;

	public SawmillRecipe(ItemStack input, ItemStack output) {
		this.input = input;
		this.output = output;
	}

	private static ArrayList<SawmillRecipe> recipeList = new ArrayList<SawmillRecipe>();

	public static void addSawmillRecipe(ItemStack input, ItemStack output) {
		if(!input.isEmpty()) {
			recipeList.add(new SawmillRecipe(input, output));
		}
	}

	public static ItemStack getResult(ItemStack input) {
		for(SawmillRecipe r : recipeList) {
			// Check own recipes first
			if(OreDictionary.itemMatches(r.input, input, false)) {
				return r.output;
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

	public static ArrayList<SawmillRecipe> getRecipeList() {
		return recipeList;
	}
}
