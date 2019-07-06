package xyz.brassgoggledcoders.steamagerevolution.inventorysystem;

import xyz.brassgoggledcoders.steamagerevolution.recipes.SARMachineRecipe;

//Interface implemented by machines that hold an inventory, an instance of IMachineInventory
public interface IRecipeMachine<I extends InventoryRecipe> extends IHasInventory<I> {
	public SARMachineRecipe getCurrentRecipe();

	public void setCurrentRecipe(SARMachineRecipe recipe);

	int getCurrentProgress();

	int getCurrentMaxTicks();

	void setCurrentTicks(int ticks);
}
