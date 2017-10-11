package xyz.brassgoggledcoders.steamagerevolution.compat.jei.categories;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fluids.FluidStack;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.modules.alchemical.multiblocks.vat.VatRecipe;

public class VatRecipeCategory implements IRecipeCategory<VatRecipe> {

	private final IGuiHelper helper;

	public VatRecipeCategory(IGuiHelper helper) {
		this.helper = helper;
	}

	@Override
	public void drawExtras(Minecraft minecraft) {
		helper.getSlotDrawable().draw(minecraft, 80, 80);
	}

	@Override
	public String getUid() {
		return SteamAgeRevolution.MODID + ":vat";
	}

	@Override
	public String getTitle() {
		return "Vat";
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
	public void setRecipe(IRecipeLayout recipeLayout, VatRecipe recipeWrapper, IIngredients ingredients) {
		recipeLayout.getFluidStacks().init(0, true, 80, 80);
		recipeLayout.getFluidStacks().init(1, true, 120, 80);
		recipeLayout.getFluidStacks().init(2, true, 140, 80);
		recipeLayout.getItemStacks().init(3, true, 80, 100);
		recipeLayout.getItemStacks().init(4, true, 120, 100);
		recipeLayout.getItemStacks().init(5, true, 140, 100);
		recipeLayout.getFluidStacks().init(6, false, 150, 95);

		for(int i = 0; i < recipeWrapper.fluidInputs.length; i++) {
			recipeLayout.getFluidStacks().set(i, recipeWrapper.fluidInputs[i]);
		}
		for(int i2 = 0; i2 < recipeWrapper.itemInputs.length; i2++) {
			recipeLayout.getItemStacks().set(i2 + 3, recipeWrapper.itemInputs[i2]);
		}
		recipeLayout.getFluidStacks().set(6, ingredients.getOutputs(FluidStack.class).get(0));
	}

}
