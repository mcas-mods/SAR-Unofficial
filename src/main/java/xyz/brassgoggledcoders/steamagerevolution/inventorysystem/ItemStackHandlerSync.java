package xyz.brassgoggledcoders.steamagerevolution.inventorysystem;

import xyz.brassgoggledcoders.steamagerevolution.utils.items.ItemStackHandlerExtractSpecific;

//TODO Bring back custom stack sync packets because AFAIK vanilla only syncs when GUI is opened (?) which doesn't work for steam hammer etc.
public class ItemStackHandlerSync extends ItemStackHandlerExtractSpecific {

	final InventoryBasic containingInventory;
	String name;

	public ItemStackHandlerSync(int size, InventoryBasic parent) {
		super(size);
		this.containingInventory = parent;
	}

	// Better to find a way to parent the handler with the inventory piece
	@Deprecated
	public ItemStackHandlerSync setName(String name) {
		this.name = name;
		return this;
	}

	@Override
	protected void onContentsChanged(int slot) {
		if(containingInventory != null) {
			containingInventory.onContentsChanged(name, this);
		}
	}
}
