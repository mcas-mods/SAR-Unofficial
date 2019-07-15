package xyz.brassgoggledcoders.steamagerevolution.inventorysystem;

import xyz.brassgoggledcoders.steamagerevolution.utils.items.ItemStackHandlerExtractSpecific;

//TODO Bring back custom stack sync packets because AFAIK vanilla only syncs when GUI is opened (?) which doesn't work for steam hammer etc.
public class ItemStackHandlerSync extends ItemStackHandlerExtractSpecific {

	final IHasInventory<?> container;
	String name;

	public ItemStackHandlerSync(String name, int size, IHasInventory<?> container) {
		super(size);
		this.name = name;
		this.container = container;
	}

	@Override
	protected void onContentsChanged(int slot) {
		// TODO
	}
}
