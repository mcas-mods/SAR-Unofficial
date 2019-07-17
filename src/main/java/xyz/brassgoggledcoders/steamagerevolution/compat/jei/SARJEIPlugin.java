package xyz.brassgoggledcoders.steamagerevolution.compat.jei;

import mezz.jei.api.*;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.ITooltipCallback;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.compat.jei.categories.*;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.RecipeRegistry;
import xyz.brassgoggledcoders.steamagerevolution.machines.IMachine;

@JEIPlugin
public class SARJEIPlugin implements IModPlugin {

	// TODO JEI Should display amounts automatically?
	public static ITooltipCallback<FluidStack> fluidTooltipCallback = new SARFluidTooltipCallback();

	public static IDrawable tankBackground;

	public static IRecipesGui recipesGui;

	@Override
	public void registerCategories(IRecipeCategoryRegistration registry) {
		SARRecipeCategory.setGuiHelper(registry.getJeiHelpers().getGuiHelper());
		tankBackground = registry.getJeiHelpers().getGuiHelper().createDrawable(
				new ResourceLocation(SteamAgeRevolution.MODID, "textures/gui/inventory.png"), 176, 8, 20, 60);
		registry.addRecipeCategories(new SteamHammerRecipeCategory(), new AlloyFurnaceRecipeCategory(),
				new CrucibleRecipeCategory(), new SteamFurnaceRecipeCategory(), new CastingBenchRecipeCategory(),
				new VatRecipeCategory(), new FumeCollectorRecipeCategory(), new DistillerRecipeCategory(),
				new GrinderRecipeCategory(), new SteelworksRecipeCategory());
	}

	// TODO Steam consumption support
	// TODO GUI Widget for processing time
	@Override
	public void register(IModRegistry registry) {
		IMachine.referenceMachinesList.forEach((uid, machine) -> add(registry, uid, machine.getCatalyst()));
	}

	@Override
	public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
		SARJEIPlugin.recipesGui = jeiRuntime.getRecipesGui();
	}

	private void add(IModRegistry registry, String id, ItemStack catalyst) {
		if(RecipeRegistry.getRecipeMasterlist().containsKey(id)) {
			registry.addRecipes(RecipeRegistry.getRecipesForMachine(id), SteamAgeRevolution.MODID + ":" + id);
			registry.addRecipeCatalyst(catalyst, SteamAgeRevolution.MODID + ":" + id);
		}
	}
}