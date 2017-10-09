package xyz.brassgoggledcoders.steamagerevolution.compat.jei;

import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.ItemStack;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.compat.jei.categories.AlloyFurnaceRecipeCategory;
import xyz.brassgoggledcoders.steamagerevolution.compat.jei.categories.CastingBlockRecipeCategory;
import xyz.brassgoggledcoders.steamagerevolution.compat.jei.categories.CrucibleRecipeCategory;
import xyz.brassgoggledcoders.steamagerevolution.compat.jei.categories.SteamFurnaceRecipeCategory;
import xyz.brassgoggledcoders.steamagerevolution.compat.jei.categories.SteamHammerRecipeCategory;
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.alloyfurnace.AlloyFurnaceRecipe;
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.crucible.CrucibleRecipe;
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.hammer.SteamHammerRecipe;
import xyz.brassgoggledcoders.steamagerevolution.modules.processing.CastingBlockRecipe;
import xyz.brassgoggledcoders.steamagerevolution.modules.processing.multiblock.furnace.SteamFurnaceRecipe;

@JEIPlugin
public class SARJEIPlugin implements IModPlugin {

	@Override
	public void registerCategories(IRecipeCategoryRegistration registry) {
		IJeiHelpers jeiHelper = registry.getJeiHelpers();
		registry.addRecipeCategories(new SteamHammerRecipeCategory(jeiHelper.getGuiHelper()),
				new AlloyFurnaceRecipeCategory(jeiHelper.getGuiHelper()),
				new CrucibleRecipeCategory(jeiHelper.getGuiHelper()),
				new SteamFurnaceRecipeCategory(jeiHelper.getGuiHelper()),
				new CastingBlockRecipeCategory(jeiHelper.getGuiHelper()));
	}

	@Override
	public void register(IModRegistry registry) {
		registry.addRecipes(SteamHammerRecipe.getRecipeList(), SteamAgeRevolution.MODID + ":steamhammer");
		registry.addRecipeCatalyst(new ItemStack(JEIObjectHolder.steamhammer_anvil),
				SteamAgeRevolution.MODID + ":steamhammer");

		registry.addRecipes(AlloyFurnaceRecipe.getRecipeList(), SteamAgeRevolution.MODID + ":alloyfurnace");
		registry.addRecipeCatalyst(new ItemStack(JEIObjectHolder.alloy_furnace_frame),
				SteamAgeRevolution.MODID + ":alloyfurnace");

		registry.addRecipes(CrucibleRecipe.getRecipeList(), SteamAgeRevolution.MODID + ":crucible");
		registry.addRecipeCatalyst(new ItemStack(JEIObjectHolder.crucible_casing),
				SteamAgeRevolution.MODID + ":crucible");

		registry.addRecipes(SteamFurnaceRecipe.getRecipeList(), SteamAgeRevolution.MODID + ":steamfurnace");
		registry.addRecipeCatalyst(new ItemStack(JEIObjectHolder.furnace_casing),
				SteamAgeRevolution.MODID + ":steamfurnace");

		registry.addRecipes(CastingBlockRecipe.getRecipeList(), SteamAgeRevolution.MODID + ":casting_block");
		registry.addRecipeCatalyst(new ItemStack(JEIObjectHolder.casting_bench),
				SteamAgeRevolution.MODID + ":casting_block");
	}

}