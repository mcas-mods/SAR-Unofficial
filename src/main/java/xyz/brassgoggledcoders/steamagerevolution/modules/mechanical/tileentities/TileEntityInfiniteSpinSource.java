package xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.tileentities;

import net.minecraft.nbt.NBTTagCompound;

public class TileEntityInfiniteSpinSource extends TileEntitySpinGenerator {

	@Override
	public void updateTile() {
		if(getWorld().isRemote)
			return;

		this.handler.setSpeed(Integer.MAX_VALUE);

		super.updateTile();
	}

	@Override
	public void readFromNBTCustom(NBTTagCompound nbtTagCompound) {}

	@Override
	public NBTTagCompound writeToNBTCustom(NBTTagCompound compound) {
		return compound;
	}
}
