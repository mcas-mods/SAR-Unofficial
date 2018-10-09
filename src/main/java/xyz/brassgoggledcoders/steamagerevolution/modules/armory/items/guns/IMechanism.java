package xyz.brassgoggledcoders.steamagerevolution.modules.armory.items.guns;

import net.minecraft.nbt.NBTTagCompound;

public interface IMechanism extends IGunPart {
	public int getReloadTime();

	default NBTTagCompound getTagFromPart() {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setInteger("reloadTime", this.getReloadTime());
		return tag;
	}
}
