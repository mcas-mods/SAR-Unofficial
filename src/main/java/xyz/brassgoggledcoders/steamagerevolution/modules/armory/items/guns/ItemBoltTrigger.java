package xyz.brassgoggledcoders.steamagerevolution.modules.armory.items.guns;

import com.teamacronymcoders.base.items.ItemBase;

public class ItemBoltTrigger extends ItemBase implements ITrigger {
	public ItemBoltTrigger() {
		super("bolt_action_mechanism");
		GunPartRegistry.registerModule(this);
	}

	@Override
	public ActionType getActionType() {
		return ActionType.BOLT;
	}

	@Override
	public String getModuleID() {
		return "bolt_action_mechanism";
	}

}
