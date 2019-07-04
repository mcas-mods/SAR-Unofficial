package xyz.brassgoggledcoders.steamagerevolution.inventorysystem;

import javax.annotation.Nullable;

import net.minecraftforge.items.ItemStackHandler;
import xyz.brassgoggledcoders.steamagerevolution.utils.fluids.MultiFluidHandler;
import xyz.brassgoggledcoders.steamagerevolution.utils.items.ItemStackHandlerExtractSpecific;

public interface IMachineInventory {
	@Nullable
	public ItemStackHandlerExtractSpecific getInputHandler();

	@Nullable
	public MultiFluidHandler getInputTank();

	@Nullable
	public ItemStackHandler getOutputHandler();

	@Nullable
	public MultiFluidHandler getOutputTank();

	@Nullable
	public MultiFluidHandler getSteamTank();
}
