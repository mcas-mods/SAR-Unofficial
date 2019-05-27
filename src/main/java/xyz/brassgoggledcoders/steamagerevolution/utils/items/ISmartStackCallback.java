package xyz.brassgoggledcoders.steamagerevolution.utils.items;

import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.IMachineHasInventory;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.IOTYPE;

public interface ISmartStackCallback {
	void onContentsChanged(IOTYPE type, int slot, IMachineHasInventory parent);
}
