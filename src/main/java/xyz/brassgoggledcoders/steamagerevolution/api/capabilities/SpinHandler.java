package xyz.brassgoggledcoders.steamagerevolution.api.capabilities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

public class SpinHandler implements ISpinHandler {

	int storedSpin = 0;
	int maxSpin;

	public SpinHandler() {
		maxSpin = 100;
	}

	public SpinHandler(int maxSpin) {
		this.maxSpin = maxSpin;
	}

	@Override
	public int fill(EnumFacing from, int amount, boolean doFill) {
		return storedSpin += amount;
	}

	@Override
	public int drain(EnumFacing from, int amount, boolean doDrain) {
		return storedSpin -= amount;
	}

	@Override
	public boolean canFill(EnumFacing from, int amount) {
		return true;
	}

	@Override
	public boolean canDrain(EnumFacing from, int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getStoredSpin() {
		return this.storedSpin;
	}

	@Override
	public int getMaxSpin() {
		return maxSpin;
	}

	@Override
	public void setStoredSpin(int toSet) {
		this.storedSpin = toSet;
	}

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound compound = new NBTTagCompound();
		compound.setInteger("spin", storedSpin);
		return compound;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		this.storedSpin = nbt.getInteger("spin");
	}
}
