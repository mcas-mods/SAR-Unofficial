package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.io;

import net.minecraftforge.items.IItemHandler;

public class ItemIO extends IO {
	IItemHandler[] handlers;

	public ItemIO(IOType type) {
		super(type);
	}

	public ItemIO setFluidHandlers(IItemHandler... handlers) {
		this.handlers = handlers;
		return this;
	}

	public IItemHandler[] getFluidHandlers() {
		return handlers;
	}
}
