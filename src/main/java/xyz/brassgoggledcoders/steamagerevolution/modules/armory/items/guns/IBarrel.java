package xyz.brassgoggledcoders.steamagerevolution.modules.armory.items.guns;

import net.minecraft.nbt.NBTTagCompound;

public interface IBarrel extends IGunPart {
	public float getVelocityModifier();

	public float getAccuracyModifier();

	default NBTTagCompound getTagFromPart() {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setFloat("velocityMod", this.getVelocityModifier());
		tag.setFloat("accMod", this.getAccuracyModifier());
		return tag;
	}
}
