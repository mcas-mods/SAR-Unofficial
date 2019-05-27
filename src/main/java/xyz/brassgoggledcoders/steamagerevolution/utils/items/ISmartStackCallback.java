package xyz.brassgoggledcoders.steamagerevolution.utils.items;

import xyz.brassgoggledcoders.steamagerevolution.network.PacketItemUpdate;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.IMachineHasInventory;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.IOTYPE;

public interface ISmartStackCallback {
	void updateStack(PacketItemUpdate message);

	void onContentsChanged(IOTYPE type, int slot, IMachineHasInventory parent);
}
