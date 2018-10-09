package xyz.brassgoggledcoders.steamagerevolution.modules.armory.items.guns;

import com.teamacronymcoders.base.items.ItemBase;

public class ItemBasicTrigger extends ItemBase implements ITrigger {

	String name;
	ActionType type;

	public ItemBasicTrigger(String name, ActionType type) {
		super(name);
		this.name = name;
		this.type = type;
		GunPartRegistry.registerModule(this);
	}

	@Override
	public ActionType getActionType() {
		return type;
	}

	@Override
	public String getModuleID() {
		return name;
	}

}
