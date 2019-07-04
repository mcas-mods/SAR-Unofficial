package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.io;

import xyz.brassgoggledcoders.steamagerevolution.utils.fluids.MultiFluidHandler;

public class MultiFluidIO extends IO {
	MultiFluidHandler fluidHandler;

	public MultiFluidIO(IOType type) {
		super(type);
	}

	public MultiFluidIO setFluidHandler(MultiFluidHandler handler) {
		fluidHandler = handler;
		return this;
	}

	public MultiFluidHandler getFluidHandlers() {
		return fluidHandler;
	}
}
