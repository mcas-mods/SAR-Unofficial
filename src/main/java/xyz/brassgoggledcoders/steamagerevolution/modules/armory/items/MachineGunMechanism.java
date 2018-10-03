package xyz.brassgoggledcoders.steamagerevolution.modules.armory.items;

import com.teamacronymcoders.base.items.ItemBase;

public class MachineGunMechanism extends ItemBase implements IFiringMechanism {

	public MachineGunMechanism() {
		super("machine_gun_mechanism");
		ModuleRegistry.registerModule(this);
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
