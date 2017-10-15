package xyz.brassgoggledcoders.steamagerevolution.compat.jei.categories;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.compat.jei.SARJEIPlugin;
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.alloyfurnace.AlloyFurnaceRecipe;

public class AlloyForgeRecipeCategory extends SARRecipeCategory<AlloyFurnaceRecipe> {

	public static final String uid = "alloyforge";
	private final IGuiHelper helper;

	public AlloyForgeRecipeCategory(IGuiHelper helper) {
		super(uid, "Alloy Forge");
		this.helper = helper;
	}

	@Override
	public IDrawable getBackground() {
		return helper.createDrawable(new ResourceLocation(SteamAgeRevolution.MODID, "textures/gui/jei/alloy_forge.png"),
				0, 0, 133, 61);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, AlloyFurnaceRecipe recipeWrapper, IIngredients ingredients) {
		recipeLayout.getFluidStacks().init(0, true, 0, 45);
		recipeLayout.getFluidStacks().init(1, true, 57, 45);
		recipeLayout.getFluidStacks().init(2, false, 113, 45);

		recipeLayout.getFluidStacks().set(0, ingredients.getInputs(FluidStack.class).get(0));
		recipeLayout.getFluidStacks().set(1, ingredients.getInputs(FluidStack.class).get(1));
		recipeLayout.getFluidStacks().set(2, ingredients.getOutputs(FluidStack.class).get(0));

		recipeLayout.getFluidStacks().addTooltipCallback(SARJEIPlugin.fluidTooltipCallback);
	}

}
