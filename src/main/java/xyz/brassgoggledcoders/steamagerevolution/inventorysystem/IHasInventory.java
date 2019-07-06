package xyz.brassgoggledcoders.steamagerevolution.inventorysystem;

import xyz.brassgoggledcoders.steamagerevolution.recipes.SARMachineRecipe;
import xyz.brassgoggledcoders.steamagerevolution.utils.IMachine;

//Interface implemented by machines that hold an inventory, an instance of IMachineInventory
public interface IHasInventory<I extends InventoryBasic> extends IMachine {
	// TODO Duplicated in multiblock interface
	public String getName();

	public I getInventory();

	public void setInventory(I inventory);

	// TODO These should probably be a seperate interface
	public SARMachineRecipe getCurrentRecipe();

	public void setCurrentRecipe(SARMachineRecipe recipe);

	int getCurrentProgress();

	int getCurrentMaxTicks();

	void setCurrentTicks(int ticks);
}
