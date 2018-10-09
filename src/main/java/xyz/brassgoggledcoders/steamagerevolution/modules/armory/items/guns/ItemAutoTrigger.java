package xyz.brassgoggledcoders.steamagerevolution.modules.armory.items.guns;

import com.teamacronymcoders.base.items.ItemBase;

public class ItemAutoTrigger extends ItemBase implements ITrigger {

	public ItemAutoTrigger() {
		super("machine_gun_mechanism");
		GunPartRegistry.registerModule(this);
	}

	@Override
	public ActionType getActionType() {
		return ActionType.AUTO;
	}

	@Override
	public String getModuleID() {
		return "machine_gun_mechanism";
	}

}
