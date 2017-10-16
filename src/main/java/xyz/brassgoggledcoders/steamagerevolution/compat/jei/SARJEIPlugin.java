package xyz.brassgoggledcoders.steamagerevolution.compat.jei;

import java.util.Collection;

import mezz.jei.api.*;
import mezz.jei.api.gui.ITooltipCallback;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.compat.jei.categories.*;
import xyz.brassgoggledcoders.steamagerevolution.modules.alchemical.multiblocks.distiller.DistillerRecipe;
import xyz.brassgoggledcoders.steamagerevolution.modules.alchemical.multiblocks.vat.VatRecipe;
import xyz.brassgoggledcoders.steamagerevolution.modules.alchemical.tileentities.FumeCollectorRecipe;
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.CastingBlockRecipe;
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.alloyfurnace.AlloyFurnaceRecipe;
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.crucible.CrucibleRecipe;
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.hammer.SteamHammerRecipe;
import xyz.brassgoggledcoders.steamagerevolution.modules.processing.multiblock.furnace.SteamFurnaceRecipe;

@JEIPlugin
public class SARJEIPlugin implements IModPlugin {

	// TODO JEI Should display amounts automatically?
	public static ITooltipCallback<FluidStack> fluidTooltipCallback = new SARFluidTooltipCallback();

	@Override
	public void registerCategories(IRecipeCategoryRegistration registry) {
		IJeiHelpers jeiHelper = registry.getJeiHelpers();
		SARRecipeCategory.setGuiHelper(jeiHelper.getGuiHelper());
		registry.addRecipeCategories(new SteamHammerRecipeCategory(jeiHelper.getGuiHelper()),
				new AlloyForgeRecipeCategory(), new CrucibleRecipeCategory(jeiHelper.getGuiHelper()),
				new SteamFurnaceRecipeCategory(jeiHelper.getGuiHelper()),
				new CastingBlockRecipeCategory(jeiHelper.getGuiHelper()), new VatRecipeCategory(),
				new FumeCollectorRecipeCategory(jeiHelper.getGuiHelper()), new DistillerRecipeCategory());
	}

	@Override
	public void register(IModRegistry registry) {
		registry.addRecipes(SteamHammerRecipe.getRecipeList(), SteamAgeRevolution.MODID + ":steamhammer");
		registry.addRecipeCatalyst(new ItemStack(JEIObjectHolder.steamhammer_anvil),
				SteamAgeRevolution.MODID + ":steamhammer");

		add(registry, AlloyFurnaceRecipe.getRecipeList(), AlloyForgeRecipeCategory.uid,
				new ItemStack(JEIObjectHolder.alloy_furnace_frame));

		registry.addRecipes(CrucibleRecipe.getRecipeList(), SteamAgeRevolution.MODID + ":crucible");
		registry.addRecipeCatalyst(new ItemStack(JEIObjectHolder.crucible_casing),
				SteamAgeRevolution.MODID + ":crucible");

		registry.addRecipes(SteamFurnaceRecipe.getRecipeList(), SteamAgeRevolution.MODID + ":steamfurnace");
		registry.addRecipeCatalyst(new ItemStack(JEIObjectHolder.furnace_casing),
				SteamAgeRevolution.MODID + ":steamfurnace");

		registry.addRecipes(CastingBlockRecipe.getRecipeList(), SteamAgeRevolution.MODID + ":casting_block");
		registry.addRecipeCatalyst(new ItemStack(JEIObjectHolder.casting_bench),
				SteamAgeRevolution.MODID + ":casting_block");

		add(registry, VatRecipe.getRecipeList(), VatRecipeCategory.uid,
				new ItemStack(JEIObjectHolder.vat_output));

		registry.addRecipes(FumeCollectorRecipe.getRecipeList(), SteamAgeRevolution.MODID + ":fume_collector");

		add(registry, DistillerRecipe.getRecipeList(), DistillerRecipeCategory.uid,
				new ItemStack(JEIObjectHolder.distiller_frame));
	}

	private void add(IModRegistry registry, Collection<?> recipeList, String id, ItemStack catalyst) {
		registry.addRecipes(recipeList, SteamAgeRevolution.MODID + ":" + id);
		registry.addRecipeCatalyst(catalyst, SteamAgeRevolution.MODID + ":" + id);
	}

}