package xyz.brassgoggledcoders.steamagerevolution.modules.mining;

import javax.annotation.Nullable;

import com.teamacronymcoders.base.util.inventory.IngredientFluidStack;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Optional;
import xyz.brassgoggledcoders.steamagerevolution.api.semisolid.SemisolidStack;
import xyz.brassgoggledcoders.steamagerevolution.utils.recipe.RecipeRegistry;
import xyz.brassgoggledcoders.steamagerevolution.utils.recipe.SARMachineRecipe;

@Optional.Interface(iface = "mezz.jei.api.recipe.IRecipeWrapper", modid = "jei", striprefs = true)
public class SemisolidRecipe extends SARMachineRecipe {
	@Nullable
	protected final SemisolidStack[] semisolidIngredients;
	@Nullable
	protected final SemisolidStack[] semisolidOutputs;
	
	public SemisolidRecipe(String crafter, Ingredient[] itemInputs, IngredientFluidStack[] fluidInputs, SemisolidStack[] semisolidInputs,
			int ticksToProcess, int steamUsePerCraft, ItemStack[] itemOutputs, FluidStack[] fluidOutputs, SemisolidStack[] semisolidOutputs) {
		super(crafter, itemInputs, fluidInputs, ticksToProcess, steamUsePerCraft, itemOutputs, fluidOutputs);
		this.semisolidIngredients = semisolidInputs;
		this.semisolidOutputs = semisolidOutputs;
	}
	
	public SemisolidStack[] getSemisolidInputs() {
		return semisolidIngredients;
	}
	
	public SemisolidStack[] getSemisolidOutputs() {
		return semisolidOutputs;
	}
	
	public static class Builder extends MachineRecipeBuilder {
		public SemisolidStack[] semisolidInputs;
		public SemisolidStack[] semisolidOutputs;
		
		public Builder(String crafter) {
			super(crafter);
		}
		
		public Builder setSemisolidInputs(SemisolidStack... s) {
			this.semisolidInputs = s;
			return this;
		}
		
		public Builder setSemisolidOUtputs(SemisolidStack... s) {
			this.semisolidOutputs = s;
			return this;
		}

		@Override
		public SemisolidRecipe build() {
			//validate(); TODO
			SemisolidRecipe recipe = new SemisolidRecipe(crafter, itemInputs, fluidInputs, semisolidInputs, ticksToProcess,
					steamUsePerCraft, itemOutputs, fluidOutputs, semisolidOutputs);
			RecipeRegistry.addRecipe(crafter, recipe);
			return recipe;
		}
	}
}
