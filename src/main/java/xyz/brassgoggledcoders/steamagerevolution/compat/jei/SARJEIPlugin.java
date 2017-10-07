package xyz.brassgoggledcoders.steamagerevolution.compat.jei;

import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.ItemStack;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.compat.jei.categories.AlloyFurnaceRecipeCategory;
import xyz.brassgoggledcoders.steamagerevolution.compat.jei.categories.CrucibleRecipeCategory;
import xyz.brassgoggledcoders.steamagerevolution.compat.jei.categories.SteamHammerRecipeCategory;
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.alloyfurnace.AlloyFurnaceRecipe;
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.hammer.SteamHammerRecipe;
import xyz.brassgoggledcoders.steamagerevolution.modules.smelting.multiblock.crucible.CrucibleRecipe;

@JEIPlugin
public class SARJEIPlugin implements IModPlugin {

	@Override
	public void registerCategories(IRecipeCategoryRegistration registry) {
		IJeiHelpers jeiHelper = registry.getJeiHelpers();
		registry.addRecipeCategories(new SteamHammerRecipeCategory(jeiHelper.getGuiHelper()),
				new AlloyFurnaceRecipeCategory(jeiHelper.getGuiHelper()),
				new CrucibleRecipeCategory(jeiHelper.getGuiHelper()));
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
	}

}