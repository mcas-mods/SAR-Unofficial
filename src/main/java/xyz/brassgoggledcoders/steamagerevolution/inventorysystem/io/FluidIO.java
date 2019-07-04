package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.io;

import xyz.brassgoggledcoders.steamagerevolution.utils.fluids.FluidTankSmart;

public class FluidIO extends IO {
	FluidTankSmart[] fluidHandlers;

	public FluidIO(IOType type) {
		super(type);
	}

	public FluidIO setFluidHandlers(FluidTankSmart... handlers) {
		fluidHandlers = handlers;
		return this;
	}

	public FluidTankSmart[] getFluidHandlers() {
		return fluidHandlers;
	}
}
