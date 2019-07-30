package xyz.brassgoggledcoders.steamagerevolution.api;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

public interface IHeatable extends INBTSerializable<NBTTagCompound> {

    public static double ZERO_CELCIUS = 273.15;

    public double getCurrentTemperature();

    public double getMaximumTemperature();

    public double getMinimumTemperature();
}
