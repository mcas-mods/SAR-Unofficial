package xyz.brassgoggledcoders.steamagerevolution.utils.multiblock;

import javax.annotation.Nullable;

import net.minecraftforge.items.ItemStackHandler;
import xyz.brassgoggledcoders.steamagerevolution.utils.fluids.FluidTankSingleSmart;
import xyz.brassgoggledcoders.steamagerevolution.utils.fluids.MultiFluidTank;
import xyz.brassgoggledcoders.steamagerevolution.utils.items.ItemStackHandlerExtractSpecific;

public interface ISARMachineInventory {
	@Nullable
	public ItemStackHandlerExtractSpecific getItemInput();

	@Nullable
	public MultiFluidTank getFluidInputs();

	@Nullable
	public ItemStackHandler getItemOutput();

	@Nullable
	public MultiFluidTank getFluidOutputs();

	@Nullable
	public FluidTankSingleSmart getSteamTank();
}
