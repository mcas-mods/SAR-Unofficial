package xyz.brassgoggledcoders.steamagerevolution.compat.jei;

import java.util.Collection;

import mezz.jei.api.*;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.ITooltipCallback;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import xyz.brassgoggledcoders.steamagerevolution.SARObjectHolder;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.compat.jei.categories.*;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.RecipeRegistry;
import xyz.brassgoggledcoders.steamagerevolution.recipes.FumeCollectorRecipe;

@JEIPlugin
public class SARJEIPlugin implements IModPlugin {

	// TODO JEI Should display amounts automatically?
	public static ITooltipCallback<FluidStack> fluidTooltipCallback = new SARFluidTooltipCallback();

	public static IDrawable tankBackground;

	@Override
	public void registerCategories(IRecipeCategoryRegistration registry) {
		SARRecipeCategory.setGuiHelper(registry.getJeiHelpers().getGuiHelper());
		tankBackground = registry.getJeiHelpers().getGuiHelper()
				.createDrawable(new ResourceLocation(SteamAgeRevolution.MODID, "textures/gui/vat.png"), 176, 8, 20, 60);
		registry.addRecipeCategories(new SteamHammerRecipeCategory(), new AlloyFurnaceRecipeCategory(),
				new CrucibleRecipeCategory(), new SteamFurnaceRecipeCategory(), new CastingBlockRecipeCategory(),
				new VatRecipeCategory(), new FumeCollectorRecipeCategory(), new DistillerRecipeCategory(),
				new GrinderRecipeCategory(), new SteelworksRecipeCategory());
	}

	// TODO Steam consumption support
	// TODO GUI Widget for processing time
	@Override
	public void register(IModRegistry registry) {
		add(registry, RecipeRegistry.getRecipesForMachine("steam hammer"), SteamHammerRecipeCategory.uid,
				new ItemStack(SARObjectHolder.steamhammer_anvil));
		add(registry, RecipeRegistry.getRecipesForMachine("alloy forge"), AlloyFurnaceRecipeCategory.uid,
				new ItemStack(SARObjectHolder.alloy_furnace_frame));
		add(registry, RecipeRegistry.getRecipesForMachine("crucible"), CrucibleRecipeCategory.uid,
				new ItemStack(SARObjectHolder.crucible_casing));
		add(registry, RecipeRegistry.getRecipesForMachine("steam furnace"), SteamFurnaceRecipeCategory.uid,
				new ItemStack(SARObjectHolder.furnace_casing));
		add(registry, RecipeRegistry.getRecipesForMachine("casting block"), CastingBlockRecipeCategory.uid,
				new ItemStack(SARObjectHolder.casting_bench));
		add(registry, RecipeRegistry.getRecipesForMachine("vat"), VatRecipeCategory.uid,
				new ItemStack(SARObjectHolder.vat_output));
		add(registry, FumeCollectorRecipe.getRecipeList(), FumeCollectorRecipeCategory.uid,
				new ItemStack(SARObjectHolder.fume_collector));
		add(registry, RecipeRegistry.getRecipesForMachine("distiller"), DistillerRecipeCategory.uid,
				new ItemStack(SARObjectHolder.distiller_frame));
		add(registry, RecipeRegistry.getRecipesForMachine("grinder"), GrinderRecipeCategory.uid,
				new ItemStack(SARObjectHolder.grinder_frame));
		add(registry, RecipeRegistry.getRecipesForMachine("steelworks"), SteelworksRecipeCategory.uid,
				new ItemStack(SARObjectHolder.steelworks_frame));
	}

	private void add(IModRegistry registry, Collection<?> recipeList, String id, ItemStack catalyst) {
		registry.addRecipes(recipeList, SteamAgeRevolution.MODID + ":" + id);
		registry.addRecipeCatalyst(catalyst, SteamAgeRevolution.MODID + ":" + id);
	}

}