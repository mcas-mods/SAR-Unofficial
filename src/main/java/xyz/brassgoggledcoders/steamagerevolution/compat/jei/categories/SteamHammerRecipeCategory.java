package xyz.brassgoggledcoders.steamagerevolution.compat.jei.categories;

import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import net.minecraft.util.ResourceLocation;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.utils.recipe.SARMachineRecipe;

public class SteamHammerRecipeCategory extends SARRecipeCategory<SARMachineRecipe> {

	public static final String uid = "steamhammer";

	public SteamHammerRecipeCategory() {
		super(uid, "Steam Hammer");
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, SARMachineRecipe recipeWrapper, IIngredients ingredients) {
		recipeLayout.getItemStacks().init(0, true, 29, 24);
		recipeLayout.getItemStacks().init(1, false, 132, 24);

		recipeLayout.getItemStacks().set(0, ingredients.getInputs(VanillaTypes.ITEM).get(0));
		recipeLayout.getItemStacks().set(1, ingredients.getOutputs(VanillaTypes.ITEM).get(0));
	}

	@Override
	public IDrawable getBackground() {
		return helper.createDrawable(new ResourceLocation(SteamAgeRevolution.MODID, "textures/gui/steam_hammer.png"), 5, 7, 150, 68);
	}
}
