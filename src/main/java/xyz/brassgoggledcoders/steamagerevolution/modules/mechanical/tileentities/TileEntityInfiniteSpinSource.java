package xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.tileentities;

import net.minecraft.nbt.NBTTagCompound;
import xyz.brassgoggledcoders.steamagerevolution.api.capabilities.ISpinHandler;
import xyz.brassgoggledcoders.steamagerevolution.api.capabilities.SpinHandler;

public class TileEntityInfiniteSpinSource extends TileEntitySpinMachine {

	private ISpinHandler handler = new SpinHandler();

	@Override
	public void updateTile() {
		if(getWorld().isRemote)
			return;

		this.handler.setSpeed(Integer.MAX_VALUE);
	}

	@Override
	public void readFromNBTCustom(NBTTagCompound nbtTagCompound) {}

	@Override
	public NBTTagCompound writeToNBTCustom(NBTTagCompound compound) {
		return compound;
	}
}
