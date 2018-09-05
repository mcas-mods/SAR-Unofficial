package xyz.brassgoggledcoders.steamagerevolution.utils.inventory;

import xyz.brassgoggledcoders.steamagerevolution.utils.IMachine;
import xyz.brassgoggledcoders.steamagerevolution.utils.recipe.SARMachineRecipe;

public interface IHasInventory extends IMachine {
    // TODO Duplicated in multiblock interface
    public String getName();

    public InventoryMachine getInventory();

    public void setInventory(InventoryMachine inventory);

    @Deprecated
    public SARMachineRecipe getCurrentRecipe();

    // TODO These should probably be a seperate interface
    public void setCurrentRecipe(SARMachineRecipe recipe);

    int getCurrentProgress();

    int getCurrentMaxTicks();

    void setCurrentTicks(int ticks);
}
