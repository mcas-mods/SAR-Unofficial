package xyz.brassgoggledcoders.steamagerevolution.compat.jei.categories;

import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import net.minecraft.util.ResourceLocation;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.compat.jei.SARJEIPlugin;
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.alloyfurnace.ControllerAlloyFurnace;
import xyz.brassgoggledcoders.steamagerevolution.utils.recipe.SARMachineRecipe;

public class AlloyFurnaceRecipeCategory extends SARRecipeCategory<SARMachineRecipe> {

	public static final String uid = "alloyfurnace";
	// private final IDrawable tankOverlay;

	public AlloyFurnaceRecipeCategory() {
		super(uid, "Alloy Furnace");
		// TODO
		// tankOverlay = helper.createDrawable(background, 134, 55, 20, 49, 7, 5, 0, 0);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, SARMachineRecipe recipeWrapper, IIngredients ingredients) {
		IGuiFluidStackGroup guiFluidStacks = recipeLayout.getFluidStacks();
		guiFluidStacks.init(0, true, 1, 1, 20, 60, ControllerAlloyFurnace.inputCapacity / 2, true, SARJEIPlugin.tankBackground);
		guiFluidStacks.init(1, true, 57, 1, 20, 60, ControllerAlloyFurnace.inputCapacity / 2, true, SARJEIPlugin.tankBackground);
		guiFluidStacks.init(2, false, 113, 1, 20, 60, ControllerAlloyFurnace.outputCapacity, true, SARJEIPlugin.tankBackground);

		guiFluidStacks.set(0, ingredients.getInputs(VanillaTypes.FLUID).get(0));
		guiFluidStacks.set(1, ingredients.getInputs(VanillaTypes.FLUID).get(1));
		guiFluidStacks.set(2, ingredients.getOutputs(VanillaTypes.FLUID).get(0));
	}
	
	@Override
	public IDrawable getBackground() {
		return helper.createDrawable(new ResourceLocation(SteamAgeRevolution.MODID, "textures/gui/alloy_furnace.png"), 21, 10, 134, 62);
	}

}
