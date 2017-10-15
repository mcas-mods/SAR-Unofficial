package xyz.brassgoggledcoders.steamagerevolution.compat.jei.categories;

import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;

public abstract class SARRecipeCategory<T extends IRecipeWrapper> implements IRecipeCategory<T> {

	String uid, name;

	public SARRecipeCategory(String uid, String name) {
		this.uid = uid;
		this.name = name;
	}

	@Override
	public String getUid() {
		return SteamAgeRevolution.MODID + ":" + uid;
	}

	@Override
	public String getTitle() {
		return name;
	}

	@Override
	public String getModName() {
		return SteamAgeRevolution.MODNAME;
	}

}
