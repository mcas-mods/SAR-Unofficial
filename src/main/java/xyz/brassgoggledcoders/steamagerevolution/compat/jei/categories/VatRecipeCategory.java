package xyz.brassgoggledcoders.steamagerevolution.compat.jei.categories;

import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.compat.jei.SARJEIPlugin;
import xyz.brassgoggledcoders.steamagerevolution.modules.alchemical.multiblocks.vat.VatRecipe;

public class VatRecipeCategory extends SARRecipeCategory<VatRecipe> {

	public static final String uid = "vat";

	public VatRecipeCategory() {
		super(uid, "Vat");
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
		// 144, 122
		return helper.createDrawable(new ResourceLocation(SteamAgeRevolution.MODID, "textures/gui/jei/vat.png"), 0, 0,
				144, 125);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, VatRecipe recipeWrapper, IIngredients ingredients) {
		for(int i = 0; i < recipeWrapper.fluidInputs.length; i++) {
			FluidStack stack = recipeWrapper.fluidInputs[i];
			if(stack != null) {
				recipeLayout.getFluidStacks().init(i, true, 1 + (i * 39), 45);
				recipeLayout.getFluidStacks().set(i, recipeWrapper.fluidInputs[i]);
			}
		}
		if(recipeWrapper.itemInputs != null) {
			for(int i2 = 0; i2 < recipeWrapper.itemInputs.length; i2++) {
				ItemStack stack = recipeWrapper.itemInputs[i2];
				if(stack != null && !stack.isEmpty()) {
					recipeLayout.getItemStacks().init(i2 + 3, true, 2 + (i2 * 21), 105);
					recipeLayout.getItemStacks().set(i2 + 3, recipeWrapper.itemInputs[i2]);
				}
			}
		}
		recipeLayout.getFluidStacks().init(6, false, 124, 94);
		recipeLayout.getFluidStacks().set(6, ingredients.getOutputs(FluidStack.class).get(0));

		recipeLayout.getFluidStacks().addTooltipCallback(SARJEIPlugin.fluidTooltipCallback);
	}

}
