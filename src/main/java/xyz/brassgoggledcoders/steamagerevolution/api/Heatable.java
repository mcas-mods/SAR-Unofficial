package xyz.brassgoggledcoders.steamagerevolution.api;

import java.util.concurrent.Callable;

import net.minecraft.nbt.NBTTagCompound;

public class Heatable implements IHeatable {

    double maxTemp;
    double temperature;

    public Heatable() {
        this(100);
    }

    public Heatable(int maxTemp) {
        this.maxTemp = maxTemp;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setDouble("temp", temperature);
        return tag;
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

    @Override
    public void setCurrentTemperature(int currentTemperature) {
        this.temperature = currentTemperature;
        if(currentTemperature < 0) {
            this.temperature = 0;
        }
    }

    @Override
    public boolean heat(int by) {
        if(temperature >= maxTemp) {
            temperature = maxTemp;
            return true;
        }
        else {
            this.temperature += by;
        }
        return false;
    }

    public static class Factory implements Callable<IHeatable> {
        @Override
        public IHeatable call() throws Exception {
            return new Heatable();
        }
    }

}
