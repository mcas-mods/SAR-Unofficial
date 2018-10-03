package xyz.brassgoggledcoders.steamagerevolution.modules.armory.items;

public interface IFiringMechanism extends IGunPart {
	public ActionType getActionType();

	// public float getReloadTime();

	public static enum ActionType {
		BOLT, SEMI, AUTO
	}
}
