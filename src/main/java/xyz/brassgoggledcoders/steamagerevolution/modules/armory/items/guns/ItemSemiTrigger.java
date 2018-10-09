package xyz.brassgoggledcoders.steamagerevolution.modules.armory.items.guns;

import com.teamacronymcoders.base.items.ItemBase;

public class ItemSemiTrigger extends ItemBase implements ITrigger {

	public ItemSemiTrigger(String name) {
		super("semi_mechanism");
		GunPartRegistry.registerModule(this);
	}

	@Override
	public ActionType getActionType() {
		return ActionType.SEMI;
	}

	@Override
	public String getModuleID() {
		return "semi_mechanism";
	}
}
