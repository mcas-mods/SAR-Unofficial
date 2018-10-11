package xyz.brassgoggledcoders.steamagerevolution.modules.armory.items.guns;

import com.teamacronymcoders.base.items.ItemBase;

public class ItemChamber extends ItemBase implements IChamber {

	String name;
	AmmoType type;

	public ItemChamber(String name, AmmoType type) {
		super(name);
		this.name = name;
		this.type = type;
		GunPartRegistry.registerPart(this);
	}

	@Override
	public String getPartName() {
		return name;
	}

	@Override
	public AmmoType getAcceptedType() {
		return type;
	}

}
