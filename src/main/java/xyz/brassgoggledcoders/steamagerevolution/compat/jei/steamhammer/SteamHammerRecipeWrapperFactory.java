package xyz.brassgoggledcoders.steamagerevolution.compat.jei.steamhammer;

import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IRecipeWrapperFactory;
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.hammer.SteamHammerRecipe;

public class SteamHammerRecipeWrapperFactory implements IRecipeWrapperFactory<SteamHammerRecipe> {

	@Override
	public IRecipeWrapper getRecipeWrapper(SteamHammerRecipe recipe) {
		return new SteamHammerRecipeWrapper(recipe.input, recipe.output, recipe.dieType);
	}
}
