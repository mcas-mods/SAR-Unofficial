package xyz.brassgoggledcoders.steamagerevolution.compat.jei.categories;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.util.ResourceLocation;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;

public abstract class SARRecipeCategory<T extends IRecipeWrapper> implements IRecipeCategory<T> {

	String uid, name;
	IGuiHelper helper;

	public SARRecipeCategory(IGuiHelper helper, String uid, String name) {
		this.helper = helper;
		this.uid = uid;
		this.name = name;
	}

	@Override
	public IDrawable getBackground() {
		return helper.createDrawable(new ResourceLocation(SteamAgeRevolution.MODID, "textures/gui/jei/" + uid + ".png"),
				0, 0, 133, 65);
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
