package xyz.brassgoggledcoders.steamagerevolution.utils.items;

import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.IHasInventory;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.IOType;

public interface ISmartStackCallback {
	void onContentsChanged(IOType type, int slot, IHasInventory parent);
}
