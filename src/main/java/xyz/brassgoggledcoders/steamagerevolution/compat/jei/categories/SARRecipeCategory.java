package xyz.brassgoggledcoders.steamagerevolution.compat.jei.categories;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.util.ResourceLocation;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;

public abstract class SARRecipeCategory<T extends IRecipeWrapper> implements IRecipeCategory<T> {

	protected static IGuiHelper helper;
	String uid, name;
	
	IDrawableAnimated arrow;
	
	public SARRecipeCategory(String uid, String name) {
		this.uid = uid;
		this.name = name;
		IDrawableStatic arrowDrawable = helper.createDrawable(new ResourceLocation(SteamAgeRevolution.MODID, "textures/gui/steelworks.png"), 176, 83, 24, 17);
		this.arrow = helper.createAnimatedDrawable(arrowDrawable, 20, IDrawableAnimated.StartDirection.LEFT, false);
	}

	public static void setGuiHelper(IGuiHelper helper) {
		SARRecipeCategory.helper = helper;
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
