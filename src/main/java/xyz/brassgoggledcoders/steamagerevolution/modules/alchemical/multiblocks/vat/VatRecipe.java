package xyz.brassgoggledcoders.steamagerevolution.modules.alchemical.multiblocks.vat;

import java.util.ArrayList;
import java.util.Arrays;

import javax.annotation.Nonnull;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Optional;

@Optional.Interface(iface = "mezz.jei.api.IRecipeWrapper", modid = "jei", striprefs = true)
public class VatRecipe implements IRecipeWrapper {
	public final FluidStack[] fluidInputs;
	public final ItemStack[] itemInputs;
	public final FluidStack output;

	private VatRecipe(FluidStack[] fluids, ItemStack[] items, FluidStack output) {
		this.fluidInputs = fluids;
		this.itemInputs = items;
		this.output = output;
	}

	public static class VatRecipeBuilder {
		public FluidStack[] fluidInputs;
		public ItemStack[] itemInputs;
		public FluidStack output;

		public VatRecipeBuilder setFluids(FluidStack... fluids) {
			if(fluids.length < 3) {
				this.fluidInputs = fluids;
			}
			return this;
		}

		public VatRecipeBuilder setItems(ItemStack... items) {
			if(items.length < 3) {
				this.itemInputs = items;
			}
			return this;
		}

		public VatRecipeBuilder setOutput(FluidStack fluid) {
			this.output = fluid;
			return this;
		}

		public VatRecipe build() {
			VatRecipe r = new VatRecipe(fluidInputs, itemInputs, output);
			recipeList.add(r);
			return r;
		}
	}

	private static ArrayList<VatRecipe> recipeList = new ArrayList<VatRecipe>();

	public static ArrayList<VatRecipe> getRecipeList() {
		return recipeList;
	}

	@Optional.Method(modid = "jei")
	@Override
	public void getIngredients(IIngredients ingredients) {
		// ingredients.setInputs(FluidStack.class, Arrays.asList(fluidInputs));
		ingredients.setInputs(ItemStack.class, Arrays.asList(itemInputs));
		ingredients.setOutput(FluidStack.class, output);
	}

	@Override
	@Optional.Method(modid = "jei")
	public void drawInfo(@Nonnull Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {

	}
}
