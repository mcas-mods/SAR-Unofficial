package xyz.brassgoggledcoders.steamagerevolution.api.capabilities;

import net.minecraft.nbt.NBTTagCompound;

public class SpinHandler implements ISpinHandler {

	int storedSpin = 0;
	int maxSpin;
	int spinPer;

	public SpinHandler() {
		maxSpin = 100;
		spinPer = 10;
	}

	public SpinHandler(int maxSpin) {
		this.maxSpin = maxSpin;
	}

	public SpinHandler(int maxSpin, int transferRate) {
		this.maxSpin = maxSpin;
		this.spinPer = transferRate;
	}

	@Override
	public void fill(int amount) {
		storedSpin += amount;
	}

	@Override
	public void drain(int amount) {
		storedSpin -= amount;
	}

	@Override
	public boolean canFill(int amount) {
		if((this.getMaxSpin() - this.getStoredSpin()) >= amount)
			return true;

		return false;
	}

	@Override
	public boolean canDrain(int amount) {
		if(this.getStoredSpin() >= amount)
			return true;

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

	@Override
	public void fill(int amount, float slipPercent) {
		if(slipPercent == 0.0F)
			this.fill(amount);
		int adjustedAmount = Math.round(amount * slipPercent);
		this.fill(adjustedAmount);
	}

	@Override
	public void drain(int amount, float slipPercent) {
		if(slipPercent == 0.0F)
			this.drain(amount);
		int adjustedAmount = Math.round(amount * slipPercent);
		this.drain(adjustedAmount);
	}

	@Override
	public void transferSpin(ISpinHandler from, ISpinHandler to, float slipPercent) {
		if(from.canDrain(spinPer) && to.canFill(spinPer)) {
			from.drain(spinPer);
			to.fill(spinPer, slipPercent);
		}
	}

	@Override
	public void fill() {
		this.fill(spinPer);
	}

	@Override
	public void fill(float slipPercent) {
		this.fill(spinPer, slipPercent);
	}

	@Override
	public void drain() {
		this.drain(spinPer);
	}

	@Override
	public int getSpinPer() {
		return spinPer;
	}
}
