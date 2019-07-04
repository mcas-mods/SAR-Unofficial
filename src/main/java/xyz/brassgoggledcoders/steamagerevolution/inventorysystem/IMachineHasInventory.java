package xyz.brassgoggledcoders.steamagerevolution.inventorysystem;

import xyz.brassgoggledcoders.steamagerevolution.recipes.SARMachineRecipe;
import xyz.brassgoggledcoders.steamagerevolution.utils.IMachine;

//Interface implemented by machines that hold an inventory, an instance of IMachineInventory
public interface IMachineHasInventory<I extends InventoryRecipeMachine> extends IMachine {
	// TODO Duplicated in multiblock interface
	public String getName();

	public I getInventory();

	public void setInventory(I inventory);

	public SARMachineRecipe getCurrentRecipe();

	// TODO These should probably be a seperate interface
	public void setCurrentRecipe(SARMachineRecipe recipe);

	int getCurrentProgress();

	int getCurrentMaxTicks();

	void setCurrentTicks(int ticks);
}
