package xyz.brassgoggledcoders.steamagerevolution.utils.items;

import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.IMachineHasInventory;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.IOType;

public class ItemStackHandlerSmart extends ItemStackHandlerExtractSpecific {

	final IMachineHasInventory controller;
	final IOType type;

	public ItemStackHandlerSmart(int size, IMachineHasInventory controller, IOType type) {
		super(size);
		this.controller = controller;
		this.type = type;
	}

	@Override
	protected void onContentsChanged(int slot) {
		if(controller != null) {
			controller.getInventory().onContentsChanged(type, slot, controller);
		}
	}
}
