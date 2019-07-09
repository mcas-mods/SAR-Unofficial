package xyz.brassgoggledcoders.steamagerevolution.inventorysystem;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraftforge.items.ItemStackHandler;

public interface IMachineInventory {
	@Nullable
	List<ItemStackHandler> getItemHandlers();

	@Nullable
	List<FluidTankSmart> getFluidHandlers();
}
