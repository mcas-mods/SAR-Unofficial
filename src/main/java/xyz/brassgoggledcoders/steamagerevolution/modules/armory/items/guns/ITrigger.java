package xyz.brassgoggledcoders.steamagerevolution.modules.armory.items.guns;

public interface ITrigger extends IGunPart {
	public ActionType getActionType();

	public static enum ActionType {
		BOLT, SEMI, AUTO
	}
}
