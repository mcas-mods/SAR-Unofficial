package xyz.brassgoggledcoders.steamagerevolution.recipes;

import java.util.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.commons.lang3.ArrayUtils;

import com.google.common.collect.Lists;
import com.teamacronymcoders.base.util.inventory.IngredientFluidStack;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.oredict.OreIngredient;

@Optional.Interface(iface = "mezz.jei.api.recipe.IRecipeWrapper", modid = "jei", striprefs = true)
public class SARMachineRecipe implements IRecipeWrapper {
	@Nullable
	protected final Ingredient[] itemIngredients;
	@Nullable
	protected final IngredientFluidStack[] fluidIngredients;
	protected final int ticksToProcess;
	protected final int steamUsePerCraft;
	@Nullable
	protected final ItemStack[] itemOutputs;
	@Nullable
	protected final FluidStack[] fluidOutputs;
	@Nonnull
	private final String crafter;
	@Nonnull
	public int networkID;

	public SARMachineRecipe(String crafter, Ingredient[] itemInputs, IngredientFluidStack[] fluidInputs,
			int ticksToProcess, int steamUsePerCraft, ItemStack[] itemOutputs, FluidStack[] fluidOutputs) {
		this.crafter = crafter;
		itemIngredients = itemInputs;
		fluidIngredients = fluidInputs;
		this.ticksToProcess = ticksToProcess;
		this.steamUsePerCraft = steamUsePerCraft;
		this.itemOutputs = itemOutputs;
		this.fluidOutputs = fluidOutputs;
	}

	public Ingredient[] getItemInputs() {
		return itemIngredients;
	}

	public IngredientFluidStack[] getFluidInputs() {
		return fluidIngredients;
	}

	public ItemStack[] getItemOutputs() {
		return itemOutputs;
	}

	public FluidStack[] getFluidOutputs() {
		return fluidOutputs;
	}

	public String getCrafter() {
		return crafter;
	}

	public int getTicksPerOperation() {
		return ticksToProcess;
	}

	public int getSteamUsePerCraft() {
		return steamUsePerCraft;
	}

	@Optional.Method(modid = "jei")
	@Override
	public void getIngredients(IIngredients ingredients) {
		if (ArrayUtils.isNotEmpty(fluidIngredients)) {
			ArrayList<FluidStack> fluids = Lists.newArrayList();
			for (IngredientFluidStack fs : fluidIngredients) {
				fluids.add(fs.getFluid());
			}
			ingredients.setInputs(VanillaTypes.FLUID, fluids);
		}
		if (ArrayUtils.isNotEmpty(itemIngredients)) {
			ArrayList<List<ItemStack>> items = Lists.newArrayList();
			for (Ingredient ing : itemIngredients) {
				items.add(Arrays.asList(ing.getMatchingStacks())); // TODO
			}
			ingredients.setInputLists(VanillaTypes.ITEM, items);
		}
		if (ArrayUtils.isNotEmpty(fluidOutputs)) {
			ingredients.setOutputs(VanillaTypes.FLUID, Arrays.asList(fluidOutputs));
		}
		if (ArrayUtils.isNotEmpty(itemOutputs)) {
			ingredients.setOutputs(VanillaTypes.ITEM, Arrays.asList(itemOutputs));
		}
	}
	
	//TODO
//	@Optional.Method(modid = "jei")
//	@Override
//	public List<String> getTooltipStrings(int mouseX, int mouseY) {
//		if((mouseX > 100 && mouseX < 130) && (mouseY > 10 && mouseY < 50)) {
//			ArrayList<String> tooltip = new ArrayList<>();
//			tooltip.add("Recipe time: " + this.getTicksPerOperation() + " ticks");
//			return tooltip;
//		}
//		return Collections.emptyList();
//	}

	public static class MachineRecipeBuilder {
		public String crafter;
		public Ingredient[] itemInputs;
		public IngredientFluidStack[] fluidInputs;
		public ItemStack[] itemOutputs;
		public FluidStack[] fluidOutputs;
		public int ticksToProcess, steamUsePerCraft = 0;

		public MachineRecipeBuilder(String crafter) {
			this.crafter = crafter;
		}

		public MachineRecipeBuilder setFluidInputs(FluidStack... fluids) {
			if (fluids == null) {
				return this;
			}
			ArrayList<IngredientFluidStack> ingredients = Lists.newArrayList();
			for (FluidStack fs : fluids) {
				ingredients.add(new IngredientFluidStack(fs));
			}
			fluidInputs = ingredients.toArray(new IngredientFluidStack[ingredients.size()]);
			return this;
		}

		public MachineRecipeBuilder setItemInputs(Object... items) {
			if (items == null) {
				return this;
			}
			ArrayList<Ingredient> ingredients = Lists.newArrayList();
			for (Object input : items) {
				if (input instanceof String) {
					ingredients.add(new OreIngredient((String) input));
				} else {
					ingredients.add(Ingredient.fromStacks((ItemStack) input));
				}
			}
			itemInputs = ingredients.toArray(new Ingredient[ingredients.size()]);
			return this;
		}

		public MachineRecipeBuilder setFluidOutputs(FluidStack... fluid) {
			if (fluid != null) {
				fluidOutputs = fluid;
			}
			return this;
		}

		public MachineRecipeBuilder setItemOutputs(ItemStack... items) {
			if (items != null) {
				itemOutputs = items;
			}
			return this;
		}

		public MachineRecipeBuilder setCraftTime(int time) {
			ticksToProcess = time;
			return this;
		}

		public MachineRecipeBuilder setSteamCost(int use) {
			steamUsePerCraft = use;
			return this;
		}

		public SARMachineRecipe build() {
			validate();
			SARMachineRecipe recipe = new SARMachineRecipe(crafter, itemInputs, fluidInputs, ticksToProcess,
					steamUsePerCraft, itemOutputs, fluidOutputs);
			RecipeRegistry.addRecipe(crafter, recipe);
			return recipe;
		}

		private void validate() {
			if (ArrayUtils.isEmpty(itemInputs) && ArrayUtils.isEmpty(fluidInputs)) {
				throw new IllegalArgumentException("Recipe must have at least one input");
			}
			if (ArrayUtils.isEmpty(itemInputs) && ArrayUtils.isEmpty(fluidInputs)) {
				throw new IllegalArgumentException("Recipe must have at least one output");
			}
		}
	}
}
