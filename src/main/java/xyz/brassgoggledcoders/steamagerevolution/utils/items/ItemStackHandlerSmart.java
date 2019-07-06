package xyz.brassgoggledcoders.steamagerevolution.utils.items;

import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.IHasInventory;

public class ItemStackHandlerSmart extends ItemStackHandlerExtractSpecific {

	final IHasInventory controller;

	public ItemStackHandlerSmart(int size, IHasInventory controller) {
		super(size);
		this.controller = controller;
	}

	@Override
	protected void onContentsChanged(int slot) {
		if(controller != null) {
			controller.getInventory().onContentsChanged(type, slot, controller);
		}
	}
}
