package xyz.brassgoggledcoders.steamagerevolution.compat.jei.steamhammer;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.item.ItemStack;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;

public class SteamHammerRecipeCategory implements IRecipeCategory<SteamHammerRecipeWrapper> {

	private final IGuiHelper helper;

	public SteamHammerRecipeCategory(IGuiHelper helper) {
		this.helper = helper;
	}

	@Override
	public String getUid() {
		return SteamAgeRevolution.MODID + ":steamhammer";
	}

	@Override
	public String getTitle() {
		return "Steam Hammer";
	}

	@Override
	public String getModName() {
		return SteamAgeRevolution.MODNAME;
	}

	@Override
	public IDrawable getBackground() {
		return helper.createBlankDrawable(256, 256);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, SteamHammerRecipeWrapper recipeWrapper,
			IIngredients ingredients) {
		recipeLayout.getItemStacks().init(0, true, 100, 60);
		recipeLayout.getItemStacks().init(1, false, 125, 60);

		recipeLayout.getItemStacks().set(0, ingredients.getInputs(ItemStack.class).get(0));
		recipeLayout.getItemStacks().set(1, ingredients.getOutputs(ItemStack.class).get(0));
	}

}
