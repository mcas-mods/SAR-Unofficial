package xyz.brassgoggledcoders.steamagerevolution.utils;

import java.awt.Color;
import java.util.Arrays;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.commons.lang3.ArrayUtils;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Optional;

@Optional.Interface(iface = "mezz.jei.api.recipe.IRecipeWrapper", modid = "jei", striprefs = true)
public class SARMachineRecipe implements IRecipeWrapper {
	@Nonnull
	private final String crafter;
	@Nullable
	protected final ItemStack[] itemInputs;
	@Nullable
	protected final FluidStack[] fluidInputs;
	protected final int ticksToProcess;
	protected final int steamUsePerCraft;
	@Nullable
	protected final ItemStack[] itemOutputs;
	@Nullable
	protected final FluidStack[] fluidOutputs;

	public SARMachineRecipe(String crafter, ItemStack[] itemInputs, FluidStack[] fluidInputs, int ticksToProcess,
			int steamUsePerCraft, ItemStack[] itemOutputs, FluidStack[] fluidOutputs) {
		this.crafter = crafter;
		this.itemInputs = itemInputs;
		this.fluidInputs = fluidInputs;
		this.ticksToProcess = ticksToProcess;
		this.steamUsePerCraft = steamUsePerCraft;
		this.itemOutputs = itemOutputs;
		this.fluidOutputs = fluidOutputs;
	}

	public ItemStack[] getItemInputs() {
		return itemInputs;
	}

	public FluidStack[] getFluidInputs() {
		return fluidInputs;
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

	public int getTicks() {
		return ticksToProcess;
	}

	public int getSteamUsePerCraft() {
		return steamUsePerCraft;
	}

	public static class MachineRecipeBuilder {
		public String crafter;
		public ItemStack[] itemInputs;
		public FluidStack[] fluidInputs;
		public ItemStack[] itemOutputs;
		public FluidStack[] fluidOutputs;
		public int ticksToProcess, steamUsePerCraft = 0;

		public MachineRecipeBuilder(String crafter) {
			this.crafter = crafter;
		}

		public MachineRecipeBuilder setFluidInputs(FluidStack... fluids) {
			fluidInputs = fluids;
			return this;
		}

		public MachineRecipeBuilder setItemInputs(ItemStack... items) {
			itemInputs = items;
			return this;
		}

		public MachineRecipeBuilder setFluidOutputs(FluidStack... fluid) {
			fluidOutputs = fluid;
			return this;
		}

		public MachineRecipeBuilder setItemOutputs(ItemStack... items) {
			itemOutputs = items;
			return this;
		}

		public MachineRecipeBuilder setCraftTime(int time) {
			ticksToProcess = time;
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
			if(ArrayUtils.isEmpty(itemInputs) && ArrayUtils.isEmpty(fluidInputs)) {
				throw new IllegalArgumentException("Recipe must have at least one input");
			}
			if(ArrayUtils.isEmpty(itemInputs) && ArrayUtils.isEmpty(fluidInputs)) {
				throw new IllegalArgumentException("Recipe must have at least one output");
			}
		}
	}

	@Optional.Method(modid = "jei")
	@Override
	public void getIngredients(IIngredients ingredients) {
		if(ArrayUtils.isNotEmpty(fluidInputs)) {
			ingredients.setInputs(FluidStack.class, Arrays.asList(fluidInputs));
		}
		if(ArrayUtils.isNotEmpty(itemInputs)) {
			ingredients.setInputs(ItemStack.class, Arrays.asList(itemInputs));
		}
		if(ArrayUtils.isNotEmpty(fluidOutputs)) {
			ingredients.setOutputs(FluidStack.class, Arrays.asList(fluidOutputs));
		}
		if(ArrayUtils.isNotEmpty(itemOutputs)) {
			ingredients.setOutputs(ItemStack.class, Arrays.asList(itemOutputs));
		}
	}

	@Override
	@Optional.Method(modid = "jei")
	public void drawInfo(@Nonnull Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		minecraft.fontRenderer.drawString(ticksToProcess + " ticks to process", recipeWidth - 52, recipeHeight - 5,
				Color.red.getRGB());
	}
}
