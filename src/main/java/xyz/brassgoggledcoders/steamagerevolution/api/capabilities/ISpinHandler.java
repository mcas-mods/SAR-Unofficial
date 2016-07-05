package xyz.brassgoggledcoders.steamagerevolution.api.capabilities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

public interface ISpinHandler extends INBTSerializable<NBTTagCompound> {

	void fill(int amount);

	void drain(int amount);

	void fill(int amount, float slipPercent);

	void drain(int amount, float slipPercent);

	boolean canFill(int amount);

	boolean canDrain(int amount);

	int getStoredSpin();

	int getMaxSpin();

	void setStoredSpin(int toSet);

	void transferSpin(ISpinHandler from, ISpinHandler to, int spinPer, float slipPercent);
}
