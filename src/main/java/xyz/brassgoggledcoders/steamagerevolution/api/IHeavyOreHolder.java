package xyz.brassgoggledcoders.steamagerevolution.api;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

public interface IHeavyOreHolder extends INBTSerializable<NBTTagCompound> {
	public int getOreAmount(String oreName);
	
	public boolean hasOre(String oreName);
	
	public void setOreAmount(String oreName, Integer oreLevel);
}
