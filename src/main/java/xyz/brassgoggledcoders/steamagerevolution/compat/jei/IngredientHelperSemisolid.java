package xyz.brassgoggledcoders.steamagerevolution.compat.jei;

import mezz.jei.api.ingredients.IIngredientHelper;
import mezz.jei.gui.overlay.IngredientGrid;
import xyz.brassgoggledcoders.steamagerevolution.api.semisolid.SemisolidStack;

public class IngredientHelperSemisolid implements IIngredientHelper<SemisolidStack> {

	@Override
	public SemisolidStack getMatch(Iterable<SemisolidStack> ingredients, SemisolidStack ingredientToMatch) {
		for(SemisolidStack ingredient : ingredients) {
			if(ingredientToMatch.equals(ingredient)) {
				return ingredient;
			}
		}
		return null;
	}

	@Override
	public String getDisplayName(SemisolidStack ingredient) {
		return ingredient.getMaterial().getTranslationKey();
	}

	@Override
	public String getUniqueId(SemisolidStack ingredient) {
		return ingredient.getMaterial().getRegistryName().toString() + ingredient.amount;
	}

	@Override
	public String getWildcardId(SemisolidStack ingredient) {
		return this.getUniqueId(ingredient);
	}

	@Override
	public String getModId(SemisolidStack ingredient) {
		return ingredient.getMaterial().getRegistryName().getNamespace();
	}

	@Override
	public String getResourceId(SemisolidStack ingredient) {
		return ingredient.getMaterial().getRegistryName().getPath();
	}

	@Override
	public SemisolidStack copyIngredient(SemisolidStack ingredient) {
		return new SemisolidStack(ingredient.getMaterial(), ingredient.amount);
	}

	@Override
	public String getErrorInfo(SemisolidStack ingredient) {
		return ingredient.getMaterial().getTranslationKey();//TODO
	}


}
