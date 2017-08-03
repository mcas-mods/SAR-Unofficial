package xyz.brassgoggledcoders.steamagerevolution.compat.guideapi;

import javax.annotation.Nullable;

import amerifrance.guideapi.api.IRecipeRenderer;
import amerifrance.guideapi.page.PageIRecipe;
import amerifrance.guideapi.page.reciperenderer.ShapedOreRecipeRenderer;
import amerifrance.guideapi.page.reciperenderer.ShapedRecipesRenderer;
import amerifrance.guideapi.page.reciperenderer.ShapelessOreRecipeRenderer;
import amerifrance.guideapi.page.reciperenderer.ShapelessRecipesRenderer;
import amerifrance.guideapi.util.LogHelper;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import xyz.brassgoggledcoders.steamagerevolution.utils.ShapelessOreResultRecipe;

public class BookUtils {
	@Nullable
	public static PageIRecipe fromJson(ResourceLocation recipeResourceLocation) {
		IRecipe recipe = CraftingManager.getRecipe(recipeResourceLocation);
		if(recipe == null) {
			LogHelper.error("Recipe for ResourceLocation " + recipeResourceLocation.toString() + " was null!");
			return null;
		}
		return new PageIRecipe(recipe, getRenderer(recipe));
	}

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
