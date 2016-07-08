package xyz.brassgoggledcoders.steamagerevolution.api.capabilities;

import net.minecraft.nbt.NBTTagCompound;

public class SpinHandler implements ISpinHandler {

	private int speed = 0;

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger("speed", speed);
		return nbt;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		this.speed = nbt.getInteger("speed");
	}

	@Override
	public int getSpeed() {
		return speed;
	}

	@Override
	public void setSpeed(int speed) {
		this.speed = speed;
	}

	@Override
	public void incrSpeed(int toIncr) {
		this.speed += toIncr;
	}

	@Override
	public void decrSpeed(int toDecr) {
		this.speed -= toDecr;
	}

	@Override
	public void incrSpeed() {
		speed++;
	}

	@Override
	public void decrSpeed() {
		speed--;
	}

}
