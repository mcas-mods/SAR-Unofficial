package xyz.brassgoggledcoders.steamagerevolution.compat.jei.categories;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.util.ResourceLocation;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;

public abstract class SARRecipeCategory<T extends IRecipeWrapper> implements IRecipeCategory<T> {

	protected static IGuiHelper helper;
	String uid, name;
	ResourceLocation background;

	public SARRecipeCategory(String uid, String name) {
		this.uid = uid;
		this.name = name;
		background = new ResourceLocation(SteamAgeRevolution.MODID, "textures/gui/jei/" + uid + ".png");
	}

	public static void setGuiHelper(IGuiHelper helper) {
		SARRecipeCategory.helper = helper;
	}

	@Override
	public IDrawable getBackground() {
		//TODO
		return helper.createDrawable(background, 0, 0, 134, 65);
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
