package xyz.brassgoggledcoders.steamagerevolution.compat.jei;

import java.util.Collection;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.ITooltipCallback;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.compat.jei.categories.AlloyFurnaceRecipeCategory;
import xyz.brassgoggledcoders.steamagerevolution.compat.jei.categories.CastingBlockRecipeCategory;
import xyz.brassgoggledcoders.steamagerevolution.compat.jei.categories.CrucibleRecipeCategory;
import xyz.brassgoggledcoders.steamagerevolution.compat.jei.categories.DistillerRecipeCategory;
import xyz.brassgoggledcoders.steamagerevolution.compat.jei.categories.FumeCollectorRecipeCategory;
import xyz.brassgoggledcoders.steamagerevolution.compat.jei.categories.GrinderRecipeCategory;
import xyz.brassgoggledcoders.steamagerevolution.compat.jei.categories.SARRecipeCategory;
import xyz.brassgoggledcoders.steamagerevolution.compat.jei.categories.SteamFurnaceRecipeCategory;
import xyz.brassgoggledcoders.steamagerevolution.compat.jei.categories.SteamHammerRecipeCategory;
import xyz.brassgoggledcoders.steamagerevolution.compat.jei.categories.SteelworksRecipeCategory;
import xyz.brassgoggledcoders.steamagerevolution.compat.jei.categories.VatRecipeCategory;
import xyz.brassgoggledcoders.steamagerevolution.modules.alchemical.tileentities.FumeCollectorRecipe;
import xyz.brassgoggledcoders.steamagerevolution.utils.recipe.RecipeRegistry;

@JEIPlugin
public class SARJEIPlugin implements IModPlugin {

	// TODO JEI Should display amounts automatically?
	public static ITooltipCallback<FluidStack> fluidTooltipCallback = new SARFluidTooltipCallback();

	public static IDrawable tankBackground;

	@Override
	public void registerCategories(IRecipeCategoryRegistration registry) {
		SARRecipeCategory.setGuiHelper(registry.getJeiHelpers().getGuiHelper());
		tankBackground = registry.getJeiHelpers().getGuiHelper().createDrawable(new ResourceLocation(SteamAgeRevolution.MODID, "textures/gui/vat.png"), 176, 8, 20, 60);
		registry.addRecipeCategories(new SteamHammerRecipeCategory(), new AlloyFurnaceRecipeCategory(),
				new CrucibleRecipeCategory(), new SteamFurnaceRecipeCategory(), new CastingBlockRecipeCategory(), new VatRecipeCategory(),
				new FumeCollectorRecipeCategory(), new DistillerRecipeCategory(), new GrinderRecipeCategory(), new SteelworksRecipeCategory());
	}

	//TODO Steam consumption support
	//TODO GUI Widget for processing time
	@Override
	public void register(IModRegistry registry) {
		add(registry, RecipeRegistry.getRecipesForMachine("steam hammer"), SteamHammerRecipeCategory.uid,
				new ItemStack(JEIObjectHolder.steamhammer_anvil));
		add(registry, RecipeRegistry.getRecipesForMachine("alloy forge"), AlloyFurnaceRecipeCategory.uid,
				new ItemStack(JEIObjectHolder.alloy_furnace_frame));
		add(registry, RecipeRegistry.getRecipesForMachine("crucible"), CrucibleRecipeCategory.uid,
				new ItemStack(JEIObjectHolder.crucible_casing));
		add(registry, RecipeRegistry.getRecipesForMachine("steam furnace"), SteamFurnaceRecipeCategory.uid,
				new ItemStack(JEIObjectHolder.furnace_casing));
		add(registry, RecipeRegistry.getRecipesForMachine("casting block"), CastingBlockRecipeCategory.uid,
				new ItemStack(JEIObjectHolder.casting_bench));
		add(registry, RecipeRegistry.getRecipesForMachine("vat"), VatRecipeCategory.uid,
				new ItemStack(JEIObjectHolder.vat_output));
		add(registry, FumeCollectorRecipe.getRecipeList(), FumeCollectorRecipeCategory.uid,
				new ItemStack(JEIObjectHolder.fume_collector));
		add(registry, RecipeRegistry.getRecipesForMachine("distiller"), DistillerRecipeCategory.uid,
				new ItemStack(JEIObjectHolder.distiller_frame));
		add(registry, RecipeRegistry.getRecipesForMachine("grinder"), GrinderRecipeCategory.uid,
				new ItemStack(JEIObjectHolder.grinder_frame));
		add(registry, RecipeRegistry.getRecipesForMachine("steelworks"), SteelworksRecipeCategory.uid, new ItemStack(JEIObjectHolder.steelworks_frame));
	}

	private void add(IModRegistry registry, Collection<?> recipeList, String id, ItemStack catalyst) {
		registry.addRecipes(recipeList, SteamAgeRevolution.MODID + ":" + id);
		registry.addRecipeCatalyst(catalyst, SteamAgeRevolution.MODID + ":" + id);
	}

}