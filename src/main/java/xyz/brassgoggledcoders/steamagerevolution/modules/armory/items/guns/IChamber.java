package xyz.brassgoggledcoders.steamagerevolution.modules.armory.items.guns;

import net.minecraft.nbt.NBTTagCompound;

public interface IChamber extends IGunPart {
	public AmmoType getAcceptedType();

	default NBTTagCompound getTagFromPart() {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setString("acceptedAmmo", this.getAcceptedType().name().toLowerCase());
		return tag;
	}

}
