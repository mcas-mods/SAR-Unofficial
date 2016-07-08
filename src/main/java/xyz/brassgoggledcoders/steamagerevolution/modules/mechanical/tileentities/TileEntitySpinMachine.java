package xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.tileentities;

import net.minecraft.nbt.NBTTagCompound;
import xyz.brassgoggledcoders.boilerplate.tileentities.TileEntitySlowTick;

public abstract class TileEntitySpinMachine extends TileEntitySlowTick {

	@Override
	public void readFromNBTCustom(NBTTagCompound compound) {

	}

	@Override
	public NBTTagCompound writeToNBTCustom(NBTTagCompound compound) {
		return compound;
	}
}
