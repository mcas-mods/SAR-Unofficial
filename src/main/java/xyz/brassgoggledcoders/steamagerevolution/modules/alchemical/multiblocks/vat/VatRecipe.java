package xyz.brassgoggledcoders.steamagerevolution.modules.alchemical.multiblocks.vat;

import java.util.ArrayList;

import javax.annotation.Nonnull;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Optional;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;

@Optional.Interface(iface = "mezz.jei.api.IRecipeWrapper", modid = "jei", striprefs = true)
public class VatRecipe implements IRecipeWrapper {
	public final FluidStack[] fluidInputs;
	public final ItemStack[] itemInputs;
	public final FluidStack output;

	public VatRecipe(FluidStack[] fluids, ItemStack[] items, FluidStack output) {
		this.fluidInputs = fluids;
		this.itemInputs = items;
		this.output = output;
	}

	private static ArrayList<VatRecipe> recipeList = new ArrayList<VatRecipe>();

	public static void addRecipe(FluidStack[] fluids, ItemStack[] items, FluidStack output) {
		if(fluids.length > 3 || items.length > 3) {
			SteamAgeRevolution.instance.getLogger().warning("Ignored invalid Vat recipe");
		}
		recipeList.add(new VatRecipe(fluids, items, output));
	}

	public static VatRecipe getRecipe(FluidStack[] fluids, ItemStack[] solids) {
		return null;
	}

	public static ArrayList<VatRecipe> getRecipeList() {
		return recipeList;
	}

	@Optional.Method(modid = "jei")
	@Override
	public void getIngredients(IIngredients ingredients) {

	}

	@Override
	@Optional.Method(modid = "jei")
	public void drawInfo(@Nonnull Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {

	}
}
