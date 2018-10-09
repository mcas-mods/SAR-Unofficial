package xyz.brassgoggledcoders.steamagerevolution.modules.armory.items.guns;

import com.teamacronymcoders.base.items.ItemBase;

public class ItemAmmo extends ItemBase implements IAmmo {

	AmmoType type;

	public ItemAmmo(String name, AmmoType type) {
		super(name);
		this.type = type;
	}

	public AmmoType getAmmoType() {
		return type;
	}

}
