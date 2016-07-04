package xyz.brassgoggledcoders.steamagerevolution.modules.mechanical;

import net.minecraft.nbt.NBTTagCompound;

public class TileOneWayPair extends TilePairedBlock {
	private boolean master = true;

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		this.master = compound.getBoolean("master");
		super.readFromNBT(compound);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setBoolean("master", master);
		return super.writeToNBT(compound);
	}

	public void setMaster() {
		this.master = true;
	}

	public void setSlave() {
		this.master = false;
	}

	public boolean isMaster() {
		return master;
	}
}
