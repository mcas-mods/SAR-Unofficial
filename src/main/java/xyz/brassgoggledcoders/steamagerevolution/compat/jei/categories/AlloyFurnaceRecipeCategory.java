package xyz.brassgoggledcoders.steamagerevolution.compat.jei.categories;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.alloyfurnace.AlloyFurnaceRecipe;

public class AlloyFurnaceRecipeCategory implements IRecipeCategory<AlloyFurnaceRecipe> {

	private final IGuiHelper helper;
	private final IDrawableStatic plus, arrow;

	public AlloyFurnaceRecipeCategory(IGuiHelper helper) {
		this.helper = helper;
		plus = helper.createDrawable(new ResourceLocation(SteamAgeRevolution.MODID, "textures/gui/spritesheet.png"), 64,
				0, 32, 32);
		arrow = helper.createDrawable(new ResourceLocation(SteamAgeRevolution.MODID, "textures/gui/spritesheet.png"),
				96, 0, 32, 32);
	}

	@Override
	public String getUid() {
		return SteamAgeRevolution.MODID + ":alloyfurnace";
	}

	@Override
	public String getTitle() {
		return "Alloy Forge";
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
	public void setRecipe(IRecipeLayout recipeLayout, AlloyFurnaceRecipe recipeWrapper, IIngredients ingredients) {
		recipeLayout.getFluidStacks().init(0, true, 56, 110);
		recipeLayout.getFluidStacks().init(1, true, 110, 110);
		recipeLayout.getFluidStacks().init(2, false, 170, 110);

		recipeLayout.getFluidStacks().set(0, ingredients.getInputs(FluidStack.class).get(0));
		recipeLayout.getFluidStacks().set(1, ingredients.getInputs(FluidStack.class).get(1));
		recipeLayout.getFluidStacks().set(2, ingredients.getOutputs(FluidStack.class).get(0));
	}

	@Override
	public void drawExtras(Minecraft minecraft) {
		plus.draw(minecraft, 75, 100);
		arrow.draw(minecraft, 135, 100);
	}

}
