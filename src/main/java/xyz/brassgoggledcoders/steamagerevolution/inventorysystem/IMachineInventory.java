package xyz.brassgoggledcoders.steamagerevolution.inventorysystem;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraftforge.items.ItemStackHandler;
import xyz.brassgoggledcoders.steamagerevolution.utils.fluids.FluidTankSmart;

public interface IMachineInventory {
	@Nullable
	List<ItemStackHandler> getItemHandlers();

	@Nullable
	List<FluidTankSmart> getFluidHandlers();
}
