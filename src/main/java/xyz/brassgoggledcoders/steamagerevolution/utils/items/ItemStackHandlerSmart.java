package xyz.brassgoggledcoders.steamagerevolution.utils.items;

import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.IMachineHasInventory;

public class ItemStackHandlerSmart extends ItemStackHandlerExtractSpecific {
	private IMachineHasInventory controller;

	public ItemStackHandlerSmart(int size, IMachineHasInventory controller) {
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
