package xyz.brassgoggledcoders.steamagerevolution.modules.armory.items.guns;

import net.minecraft.nbt.NBTTagCompound;

public interface IGunPart {
	public String getModuleID();

	public NBTTagCompound getTagFromPart();
}
