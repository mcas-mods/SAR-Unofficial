package xyz.brassgoggledcoders.steamagerevolution.modules.alchemical.multiblocks.vat;

import java.util.ArrayList;
import java.util.Arrays;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Optional;

@Optional.Interface(iface = "mezz.jei.api.recipe.IRecipeWrapper", modid = "jei", striprefs = true)
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
			this.fluidInputs = fluids;
			return this;
		}

		public VatRecipeBuilder setItems(ItemStack... items) {
			this.itemInputs = items;
			return this;
		}

		public VatRecipeBuilder setOutput(FluidStack fluid) {
			this.output = fluid;
			return this;
		}

		public VatRecipe build() {
			this.validate();
			VatRecipe r = new VatRecipe(fluidInputs, itemInputs, output);
			recipeList.add(r);
			return r;
		}

		private void validate() {
			if(output == null) {
				throw new NullPointerException("Recipe output cannot be null");
			}
			if(this.fluidInputs == null) {
				throw new NullPointerException("Recipe must have at least one fluid input");
			}
			else if(this.fluidInputs.length > 3) {
				throw new IllegalArgumentException("Recipe cannot have more than three fluid inputs");
			}
			if(this.itemInputs != null && this.itemInputs.length > 3) {
				throw new IllegalArgumentException("Recipe cannot have more than three item inputs");
			}
		}
	}

	private static ArrayList<VatRecipe> recipeList = new ArrayList<VatRecipe>();

	public static ArrayList<VatRecipe> getRecipeList() {
		return recipeList;
	}

	@Optional.Method(modid = "jei")
	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputs(FluidStack.class, Arrays.asList(fluidInputs));
		// ingredients.setInputs(ItemStack.class, Arrays.asList(itemInputs));
		ingredients.setOutput(FluidStack.class, output);
	}
}
