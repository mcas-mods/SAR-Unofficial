package xyz.brassgoggledcoders.steamagerevolution.api;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.util.INBTSerializable;

public interface ISpinHandler extends INBTSerializable<NBTTagCompound> {

	int fill(EnumFacing from, int amount, boolean doFill);

	int drain(EnumFacing from, int amount, boolean doDrain);

	boolean canFill(EnumFacing from, int amount);

	boolean canDrain(EnumFacing from, int amount);

	int getStoredSpin();

	int getMaxSpin();

	void setStoredSpin(int toSet);
}
