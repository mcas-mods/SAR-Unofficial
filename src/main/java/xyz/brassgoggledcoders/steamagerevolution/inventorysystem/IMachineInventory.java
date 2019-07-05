package xyz.brassgoggledcoders.steamagerevolution.inventorysystem;

import java.util.List;

import javax.annotation.Nullable;

import xyz.brassgoggledcoders.steamagerevolution.utils.fluids.FluidTankSmart;
import xyz.brassgoggledcoders.steamagerevolution.utils.items.ItemStackHandlerExtractSpecific;

public interface IMachineInventory {
	@Nullable
	List<ItemStackHandlerExtractSpecific> getItemHandlersOfType(IOType type);

	@Nullable
	List<FluidTankSmart> getFluidHandlersOfType(IOType type);
}
