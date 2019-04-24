package xyz.brassgoggledcoders.steamagerevolution.compat.jei.categories;

import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.item.ItemStack;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.SemisolidRecipe;

public class GrinderRecipeCategory extends SARRecipeCategory<SemisolidRecipe> {

	public static final String uid = "grinder";

	public GrinderRecipeCategory() {
		super(uid, "Grinder");
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, SemisolidRecipe recipeWrapper, IIngredients ingredients) {
		recipeLayout.getItemStacks().init(0, false, 116, 23);
		recipeLayout.getItemStacks().set(0, ingredients.getOutputs(ItemStack.class).get(0));
	}

}
