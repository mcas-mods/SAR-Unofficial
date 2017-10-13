package xyz.brassgoggledcoders.steamagerevolution.compat.jei;

import mezz.jei.api.*;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.ItemStack;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.compat.jei.categories.*;
import xyz.brassgoggledcoders.steamagerevolution.modules.alchemical.multiblocks.vat.VatRecipe;
import xyz.brassgoggledcoders.steamagerevolution.modules.alchemical.tileentities.EvaporatorRecipe;
import xyz.brassgoggledcoders.steamagerevolution.modules.alchemical.tileentities.FumeCollectorRecipe;
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
				new CastingBlockRecipeCategory(jeiHelper.getGuiHelper()),
				new VatRecipeCategory(jeiHelper.getGuiHelper()),
				new FumeCollectorRecipeCategory(jeiHelper.getGuiHelper()),
				new EvaporatorRecipeCategory(jeiHelper.getGuiHelper()));
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

		registry.addRecipes(VatRecipe.getRecipeList(), SteamAgeRevolution.MODID + ":vat");

		registry.addRecipes(FumeCollectorRecipe.getRecipeList(), SteamAgeRevolution.MODID + ":fume_collector");

		registry.addRecipes(EvaporatorRecipe.getRecipeList(), SteamAgeRevolution.MODID + ":evaporator");
	}

}