package xyz.brassgoggledcoders.steamagerevolution.utils;

public interface IHasInventory {
	// TODO Duplicated in multiblock interface
	public String getName();

	public void setInventory(InventoryMachine inventory);

	public InventoryMachine getInventory();
}
