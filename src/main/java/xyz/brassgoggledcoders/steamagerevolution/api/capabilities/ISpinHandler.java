package xyz.brassgoggledcoders.steamagerevolution.api.capabilities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

public interface ISpinHandler extends INBTSerializable<NBTTagCompound> {
	int getSpeed();

	void setSpeed(int speed);

	void incrSpeed(int toIncr);

	void decrSpeed(int toDecr);

	void incrSpeed();

	void decrSpeed();
}
