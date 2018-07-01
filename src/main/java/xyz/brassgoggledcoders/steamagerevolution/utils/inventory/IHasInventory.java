package xyz.brassgoggledcoders.steamagerevolution.utils.inventory;

import xyz.brassgoggledcoders.steamagerevolution.utils.recipe.SARMachineRecipe;

public interface IHasInventory {
	// TODO Duplicated in multiblock interface
	public String getName();

	public void setInventory(InventoryMachine inventory);

	public InventoryMachine getInventory();

	// TODO This should probably be a seperate interface
	public void setCurrentRecipe(SARMachineRecipe recipe);

	public SARMachineRecipe getCurrentRecipe();

	int getCurrentProgress();
}
