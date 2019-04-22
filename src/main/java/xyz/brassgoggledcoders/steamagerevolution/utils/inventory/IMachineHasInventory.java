package xyz.brassgoggledcoders.steamagerevolution.utils.inventory;

import xyz.brassgoggledcoders.steamagerevolution.utils.IMachine;
import xyz.brassgoggledcoders.steamagerevolution.utils.recipe.SARMachineRecipe;

public interface IMachineHasInventory<I extends InventoryMachine> extends IMachine {
	// TODO Duplicated in multiblock interface
	public String getName();

	public I getInventory();

	public void setInventory(I inventory);
	
	@Deprecated
	public SARMachineRecipe getCurrentRecipe();

	// TODO These should probably be a seperate interface
	public void setCurrentRecipe(SARMachineRecipe recipe);

	int getCurrentProgress();

	int getCurrentMaxTicks();

	void setCurrentTicks(int ticks);
}
