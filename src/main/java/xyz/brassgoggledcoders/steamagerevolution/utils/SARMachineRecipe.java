package xyz.brassgoggledcoders.steamagerevolution.utils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class SARMachineRecipe {
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
}
