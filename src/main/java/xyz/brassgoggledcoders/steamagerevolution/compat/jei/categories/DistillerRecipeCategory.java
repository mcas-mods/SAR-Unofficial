package xyz.brassgoggledcoders.steamagerevolution.compat.jei.categories;

import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import net.minecraft.util.ResourceLocation;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.modules.alchemical.multiblocks.distiller.ControllerDistiller;
import xyz.brassgoggledcoders.steamagerevolution.utils.recipe.SARMachineRecipe;

public class DistillerRecipeCategory extends SARRecipeCategory<SARMachineRecipe> {

	public static final String uid = "distiller";

	public DistillerRecipeCategory() {
		super(uid, "Distiller");
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, SARMachineRecipe recipeWrapper, IIngredients ingredients) {
		recipeLayout.getFluidStacks().init(0, true, 35, 4, 20, 60, ControllerDistiller.tankCapacity, true, null);
		recipeLayout.getFluidStacks().init(1, false, 91, 4, 20, 60, ControllerDistiller.tankCapacity, true, null);
		recipeLayout.getItemStacks().init(2, false, 141, 26);

		recipeLayout.getFluidStacks().set(0, ingredients.getInputs(VanillaTypes.FLUID).get(0));
		recipeLayout.getFluidStacks().set(1, ingredients.getOutputs(VanillaTypes.FLUID).get(0));
		recipeLayout.getItemStacks().set(2, ingredients.getOutputs(VanillaTypes.ITEM).get(0));
	}
	
	@Override
	public IDrawable getBackground() {
		return helper.createDrawable(new ResourceLocation(SteamAgeRevolution.MODID, "textures/gui/distiller.png"), 6, 5, 164, 68);
	}

}
