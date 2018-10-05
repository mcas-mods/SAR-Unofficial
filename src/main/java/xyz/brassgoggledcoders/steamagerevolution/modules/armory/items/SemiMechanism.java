package xyz.brassgoggledcoders.steamagerevolution.modules.armory.items;

import com.teamacronymcoders.base.items.ItemBase;

public class SemiMechanism extends ItemBase implements IFiringMechanism {

	public SemiMechanism(String name) {
		super("semi_mechanism");
		ModuleRegistry.registerModule(this);
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
