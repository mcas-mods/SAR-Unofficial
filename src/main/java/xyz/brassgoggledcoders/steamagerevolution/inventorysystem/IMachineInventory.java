package xyz.brassgoggledcoders.steamagerevolution.inventorysystem;

import javax.annotation.Nullable;

import net.minecraftforge.items.ItemStackHandler;
import xyz.brassgoggledcoders.steamagerevolution.utils.fluids.FluidTankSingleSmart;
import xyz.brassgoggledcoders.steamagerevolution.utils.fluids.FluidTankSmart;
import xyz.brassgoggledcoders.steamagerevolution.utils.items.ItemStackHandlerExtractSpecific;

public interface IMachineInventory {
	@Nullable
	public ItemStackHandlerExtractSpecific getInputHandler();

	@Nullable
	public FluidTankSmart getInputTank();

	@Nullable
	public ItemStackHandler getOutputHandler();

	@Nullable
	public FluidTankSmart getOutputTank();

	@Nullable
	public FluidTankSingleSmart getSteamTank();
}
