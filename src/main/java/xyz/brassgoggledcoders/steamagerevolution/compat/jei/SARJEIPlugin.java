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
import xyz.brassgoggledcoders.steamagerevolution.modules.processing.multiblock.sawmill.SawmillRecipe;

@JEIPlugin
public class SARJEIPlugin implements IModPlugin {

	// TODO JEI Should display amounts automatically?
	public static ITooltipCallback<FluidStack> fluidTooltipCallback = new SARFluidTooltipCallback();

	@Override
	public void registerCategories(IRecipeCategoryRegistration registry) {
		IJeiHelpers jeiHelper = registry.getJeiHelpers();
		SARRecipeCategory.setGuiHelper(jeiHelper.getGuiHelper());
		registry.addRecipeCategories(new SteamHammerRecipeCategory(), new AlloyForgeRecipeCategory(),
				new CrucibleRecipeCategory(), new SteamFurnaceRecipeCategory(), new CastingBlockRecipeCategory(),
				new VatRecipeCategory(), new FumeCollectorRecipeCategory(), new DistillerRecipeCategory(),
				new SawmillRecipeCategory());
	}

	@Override
	public void register(IModRegistry registry) {
		add(registry, SteamHammerRecipe.getRecipeList(), SteamHammerRecipeCategory.uid,
				new ItemStack(JEIObjectHolder.steamhammer_anvil));

		add(registry, AlloyFurnaceRecipe.getRecipeList(), AlloyForgeRecipeCategory.uid,
				new ItemStack(JEIObjectHolder.alloy_furnace_frame));

		add(registry, CrucibleRecipe.getRecipeList(), CrucibleRecipeCategory.uid,
				new ItemStack(JEIObjectHolder.crucible_casing));

		add(registry, SteamFurnaceRecipe.getRecipeList(), SteamFurnaceRecipeCategory.uid, new ItemStack(JEIObjectHolder.furnace_casing));

		add(registry, CastingBlockRecipe.getRecipeList(), CastingBlockRecipeCategory.uid,
				new ItemStack(JEIObjectHolder.casting_bench));

		add(registry, VatRecipe.getRecipeList(), VatRecipeCategory.uid, new ItemStack(JEIObjectHolder.vat_output));

		add(registry, FumeCollectorRecipe.getRecipeList(), FumeCollectorRecipeCategory.uid,
				new ItemStack(JEIObjectHolder.fume_collector));

		add(registry, DistillerRecipe.getRecipeList(), DistillerRecipeCategory.uid,
				new ItemStack(JEIObjectHolder.distiller_frame));

		add(registry, SawmillRecipe.getRecipeList(), SawmillRecipeCategory.uid,
				new ItemStack(JEIObjectHolder.sawmill_casing));
	}

	private void add(IModRegistry registry, Collection<?> recipeList, String id, ItemStack catalyst) {
		registry.addRecipes(recipeList, SteamAgeRevolution.MODID + ":" + id);
		registry.addRecipeCatalyst(catalyst, SteamAgeRevolution.MODID + ":" + id);
	}

}