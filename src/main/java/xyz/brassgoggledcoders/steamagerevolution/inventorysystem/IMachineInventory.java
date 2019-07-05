package xyz.brassgoggledcoders.steamagerevolution.inventorysystem;

import javax.annotation.Nullable;

import net.minecraftforge.items.ItemStackHandler;
import xyz.brassgoggledcoders.steamagerevolution.utils.fluids.FluidHandlerMulti;
import xyz.brassgoggledcoders.steamagerevolution.utils.fluids.FluidTankSingleSmart;
import xyz.brassgoggledcoders.steamagerevolution.utils.items.ItemStackHandlerExtractSpecific;

public interface IMachineInventory {
	@Nullable
	public ItemStackHandlerExtractSpecific getInputHandler();

	@Nullable
	public FluidHandlerMulti getInputTank();

	@Nullable
	public ItemStackHandler getOutputHandler();

	@Nullable
	public FluidHandlerMulti getOutputTank();

	@Nullable
	public FluidTankSingleSmart getSteamTank();
}
