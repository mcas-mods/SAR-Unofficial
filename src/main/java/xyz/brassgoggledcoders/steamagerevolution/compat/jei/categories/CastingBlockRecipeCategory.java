package xyz.brassgoggledcoders.steamagerevolution.compat.jei.categories;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.modules.processing.CastingBlockRecipe;

public class CastingBlockRecipeCategory implements IRecipeCategory<CastingBlockRecipe> {

	private final IGuiHelper helper;

	public CastingBlockRecipeCategory(IGuiHelper helper) {
		this.helper = helper;
	}

	@Override
	public void drawExtras(Minecraft minecraft) {
		helper.getSlotDrawable().draw(minecraft, 170, 80);
	}

	@Override
	public String getUid() {
		return SteamAgeRevolution.MODID + ":casting_block";
	}

	@Override
	public String getTitle() {
		return "Casting Block";
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
	public void setRecipe(IRecipeLayout recipeLayout, CastingBlockRecipe recipeWrapper, IIngredients ingredients) {
		recipeLayout.getFluidStacks().init(0, true, 80, 80);
		recipeLayout.getItemStacks().init(1, false, 170, 80);

		recipeLayout.getFluidStacks().set(0, ingredients.getInputs(FluidStack.class).get(0));
		recipeLayout.getItemStacks().set(1, ingredients.getOutputs(ItemStack.class).get(0));
	}

}
