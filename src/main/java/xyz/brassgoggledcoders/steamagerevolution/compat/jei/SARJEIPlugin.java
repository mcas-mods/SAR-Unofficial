package xyz.brassgoggledcoders.steamagerevolution.compat.jei;

import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.ItemStack;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.compat.jei.steamhammer.SteamHammerRecipeCategory;
import xyz.brassgoggledcoders.steamagerevolution.compat.jei.steamhammer.SteamHammerRecipeWrapperFactory;
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.hammer.SteamHammerRecipe;

@JEIPlugin
public class SARJEIPlugin implements IModPlugin {

	@Override
	public void registerCategories(IRecipeCategoryRegistration registry) {
		IJeiHelpers jeiHelper = registry.getJeiHelpers();
		registry.addRecipeCategories(new SteamHammerRecipeCategory(jeiHelper.getGuiHelper()));
	}

	@Override
	public void register(IModRegistry registry) {
		registry.handleRecipes(SteamHammerRecipe.class, new SteamHammerRecipeWrapperFactory(),
				SteamAgeRevolution.MODID + ":steamhammer");
		registry.addRecipes(SteamHammerRecipe.getRecipeList(), SteamAgeRevolution.MODID + ":steamhammer");
		registry.addRecipeCatalyst(new ItemStack(JEIObjectHolder.steamhammer_anvil),
				SteamAgeRevolution.MODID + ":steamhammer");
	}

}