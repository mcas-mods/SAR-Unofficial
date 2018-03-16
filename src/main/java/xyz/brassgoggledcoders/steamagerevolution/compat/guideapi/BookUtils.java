package xyz.brassgoggledcoders.steamagerevolution.compat.guideapi;

import amerifrance.guideapi.api.IRecipeRenderer;
import amerifrance.guideapi.page.reciperenderer.*;
import net.minecraft.item.crafting.*;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import xyz.brassgoggledcoders.steamagerevolution.utils.ShapelessOreResultRecipe;

public class BookUtils {

	private static IRecipeRenderer getRenderer(IRecipe recipe) {
		if(recipe == null) {
			return null;
		}
		else if(recipe.getClass() == ShapedRecipes.class) {
			return new ShapedRecipesRenderer((ShapedRecipes) recipe);
		}
		else if(recipe.getClass() == ShapelessRecipes.class) {
			return new ShapelessRecipesRenderer((ShapelessRecipes) recipe);
		}
		else if(recipe.getClass() == ShapedOreRecipe.class) {
			return new ShapedOreRecipeRenderer((ShapedOreRecipe) recipe);
		}
		else if(recipe.getClass() == ShapelessOreRecipe.class || recipe.getClass() == ShapelessOreResultRecipe.class) {
			return new ShapelessOreRecipeRenderer((ShapelessOreRecipe) recipe);
		}
		else {
			return null;
		}
	}
}
