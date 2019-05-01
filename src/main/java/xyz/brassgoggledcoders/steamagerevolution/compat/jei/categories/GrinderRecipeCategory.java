package xyz.brassgoggledcoders.steamagerevolution.compat.jei.categories;

import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import xyz.brassgoggledcoders.steamagerevolution.compat.jei.SARJEIPlugin;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.SemisolidRecipe;

public class GrinderRecipeCategory extends SARRecipeCategory<SemisolidRecipe> {

	public static final String uid = "grinder";

	public GrinderRecipeCategory() {
		super(uid, "Grinder");
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, SemisolidRecipe recipeWrapper, IIngredients ingredients) {
		recipeLayout.getIngredientsGroup(SARJEIPlugin.SEMISOLID).init(0, true, 0, 0);
		recipeLayout.getIngredientsGroup(SARJEIPlugin.SEMISOLID).set(0,
				ingredients.getInputs(SARJEIPlugin.SEMISOLID).get(0));
		recipeLayout.getItemStacks().init(0, false, 116, 23);
		recipeLayout.getItemStacks().set(0, ingredients.getOutputs(VanillaTypes.ITEM).get(0));
	}

}
