package xyz.brassgoggledcoders.steamagerevolution.modules.armory.items;

import com.teamacronymcoders.base.items.ItemBase;

public class BoltActionMechanism extends ItemBase implements IFiringMechanism {
	public BoltActionMechanism() {
		super("bolt_action_mechanism");
		ModuleRegistry.registerModule(this);
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
