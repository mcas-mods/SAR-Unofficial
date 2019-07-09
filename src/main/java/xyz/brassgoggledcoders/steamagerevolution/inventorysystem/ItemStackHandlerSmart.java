package xyz.brassgoggledcoders.steamagerevolution.inventorysystem;

import xyz.brassgoggledcoders.steamagerevolution.utils.items.ItemStackHandlerExtractSpecific;

//TODO Bring back custom stack sync packets because AFAIK vanilla only syncs when GUI is opened (?) which doesn't work for steam hammer etc.
public class ItemStackHandlerSmart extends ItemStackHandlerExtractSpecific {

	final InventoryBasic containingInventory;

	public ItemStackHandlerSmart(int size, InventoryBasic parent) {
		super(size);
		this.containingInventory = parent;
	}

	@Override
	protected void onContentsChanged(int slot) {
		if(containingInventory != null) {
			containingInventory.onContentsChanged(this);
		}
		// TODO Markdirty
	}
}
