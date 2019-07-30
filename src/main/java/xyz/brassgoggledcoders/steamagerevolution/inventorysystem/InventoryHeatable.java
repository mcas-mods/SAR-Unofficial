package xyz.brassgoggledcoders.steamagerevolution.inventorysystem;

import net.minecraft.nbt.NBTTagCompound;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.InventoryCraftingMachine;

public class InventoryHeatable extends InventoryCraftingMachine {
    public int maxTemperature;
    protected int currentTemperature;
    protected boolean belowZeroAllowed;

    public InventoryHeatable(IHasInventory<? extends InventoryHeatable> parent, int maxTemperature,
            boolean belowZeroAllowed) {
        super(parent);
        this.maxTemperature = maxTemperature;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound tag = super.serializeNBT();
        tag.setInteger("temp", currentTemperature);
        return tag;
    }

    @Override
    public void deserializeNBT(NBTTagCompound tag) {
        this.currentTemperature = tag.getInteger("temp");
        super.deserializeNBT(tag);
    }

    public int getCurrentTemperature() {
        return currentTemperature;
    }

    public void setCurrentTemperature(int currentTemperature) {
        this.currentTemperature = currentTemperature;
        if(currentTemperature < 0 && !belowZeroAllowed) {
            this.currentTemperature = 0;
        }
    }

    public void modifyTemperature(int i) {
        this.currentTemperature += i;
        if(currentTemperature < 0 && !belowZeroAllowed) {
            this.currentTemperature = 0;
        }
    }

    public boolean heat(int by) {
        if(currentTemperature >= maxTemperature) {
            currentTemperature = maxTemperature;
            return true;
        }
        else {
            this.currentTemperature += by;
        }
        return false;
    }

}
