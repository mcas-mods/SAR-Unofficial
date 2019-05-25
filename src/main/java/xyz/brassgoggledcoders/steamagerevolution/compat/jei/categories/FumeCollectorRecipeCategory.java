package xyz.brassgoggledcoders.steamagerevolution.compat.jei.categories;

import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import net.minecraft.util.ResourceLocation;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.recipes.FumeCollectorRecipe;
import xyz.brassgoggledcoders.steamagerevolution.tileentities.TileEntityFumeCollector;

public class FumeCollectorRecipeCategory extends SARRecipeCategory<FumeCollectorRecipe> {

	public static final String uid = "fumecollector";

	public FumeCollectorRecipeCategory() {
		super(uid, "Fume Collector");
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, FumeCollectorRecipe recipeWrapper, IIngredients ingredients) {
		recipeLayout.getItemStacks().init(0, true, 0, 23);
		recipeLayout.getFluidStacks().init(1, false, 53, 1, 20, 60, TileEntityFumeCollector.outputCapacity, true, null);

		recipeLayout.getItemStacks().set(0, ingredients.getInputs(VanillaTypes.ITEM).get(0));
		recipeLayout.getFluidStacks().set(1, ingredients.getOutputs(VanillaTypes.FLUID).get(0));
	}
	
	@Override
	public IDrawable getBackground() {
		return helper.createDrawable(new ResourceLocation(SteamAgeRevolution.MODID, "textures/gui/fume_collector.png"), 52, 10, 74, 62);
	}

}
