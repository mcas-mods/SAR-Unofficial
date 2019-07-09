package xyz.brassgoggledcoders.steamagerevolution.inventorysystem;

import xyz.brassgoggledcoders.steamagerevolution.utils.IMachine;

//Interface implemented by machines that hold an inventory, an instance of IMachineInventory
public interface IHasInventory<I extends InventoryBasic> extends IMachine {
	// TODO Duplicated in multiblock interface
	public String getName();

	public I getInventory();

	public void setInventory(I inventory);

	public void markDirty();
}
