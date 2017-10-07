package xyz.brassgoggledcoders.steamagerevolution.compat.jei.categories;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.hammer.SteamHammerRecipe;

public class SteamHammerRecipeCategory implements IRecipeCategory<SteamHammerRecipe> {

	protected final IDrawableStatic hammer;
	private final IGuiHelper helper;

	public SteamHammerRecipeCategory(IGuiHelper helper) {
		this.helper = helper;
		hammer = helper.createDrawable(new ResourceLocation(SteamAgeRevolution.MODID, "textures/gui/spritesheet.png"),
				0, 0, 32, 32);
	}

	@Override
	public void drawExtras(Minecraft minecraft) {
		hammer.draw(minecraft, 120, 100);
		helper.getSlotDrawable().draw(minecraft, 100, 100);
		helper.getSlotDrawable().draw(minecraft, 160, 100);
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
	public void setRecipe(IRecipeLayout recipeLayout, SteamHammerRecipe recipeWrapper, IIngredients ingredients) {
		recipeLayout.getItemStacks().init(0, true, 100, 100);
		recipeLayout.getItemStacks().init(1, false, 160, 100);

		recipeLayout.getItemStacks().set(0, ingredients.getInputs(ItemStack.class).get(0));
		recipeLayout.getItemStacks().set(1, ingredients.getOutputs(ItemStack.class).get(0));
	}

}
