package xyz.brassgoggledcoders.steamagerevolution.api;

import net.minecraft.nbt.NBTTagCompound;

public class DefaultHeatable implements IHeatable {

    double temperature;

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setDouble("temperature", temperature);
        return null;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        temperature = nbt.getDouble("temperature");
    }

    @Override
    public double getCurrentTemperature() {
        return temperature;
    }

    @Override
    public double getMaximumTemperature() {
        return 100;
    }

    @Override
    public double getMinimumTemperature() {
        return IHeatable.ZERO_CELCIUS;
    }

}
