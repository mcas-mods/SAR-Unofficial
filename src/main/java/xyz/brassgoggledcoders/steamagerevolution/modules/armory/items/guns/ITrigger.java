package xyz.brassgoggledcoders.steamagerevolution.modules.armory.items.guns;

import net.minecraft.nbt.NBTTagCompound;

public interface ITrigger extends IGunPart {
	public ActionType getActionType();

	public static enum ActionType {
		BOLT, SEMI, AUTO
	}

	default NBTTagCompound getTagFromPart() {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setString("actionType", this.getActionType().name().toLowerCase());
		return tag;
	}

}
