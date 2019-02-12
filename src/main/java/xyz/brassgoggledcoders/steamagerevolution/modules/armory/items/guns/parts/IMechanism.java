package xyz.brassgoggledcoders.steamagerevolution.modules.armory.items.guns.parts;

public interface IMechanism extends IGunPart {
	public int getReloadTime();

	public ActionType getActionType();

	public static enum ActionType {
		BOLT, SEMI, AUTO
	}

	default GunPartType getPartType() {
		return GunPartType.MECHANISM;
	}
}
