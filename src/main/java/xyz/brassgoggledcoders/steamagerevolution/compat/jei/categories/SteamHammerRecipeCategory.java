package xyz.brassgoggledcoders.steamagerevolution.compat.jei.categories;

import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.item.ItemStack;
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.hammer.SteamHammerRecipe;

public class SteamHammerRecipeCategory extends SARRecipeCategory<SteamHammerRecipe> {

	public static final String uid = "steamhammer";

	public SteamHammerRecipeCategory() {
		super(uid, "Steam Hammer");
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, SteamHammerRecipe recipeWrapper, IIngredients ingredients) {
		recipeLayout.getItemStacks().init(0, true, 7, 23);
		recipeLayout.getItemStacks().init(1, false, 116, 23);

		recipeLayout.getItemStacks().set(0, ingredients.getInputs(ItemStack.class).get(0));
		recipeLayout.getItemStacks().set(1, ingredients.getOutputs(ItemStack.class).get(0));
	}

}
