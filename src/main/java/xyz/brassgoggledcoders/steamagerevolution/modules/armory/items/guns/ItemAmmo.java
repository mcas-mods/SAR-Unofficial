package xyz.brassgoggledcoders.steamagerevolution.modules.armory.items.guns;

import com.teamacronymcoders.base.items.ItemBase;

public class ItemAmmo extends ItemBase implements IAmmo {

	AmmoType type;
	int dmg;

	public ItemAmmo(String name, AmmoType type, int dmg) {
		super(name);
		this.type = type;
		this.dmg = dmg;
	}

	public AmmoType getAmmoType() {
		return type;
	}

	@Override
	public int getBaseDamage() {
		return dmg;
	}

}
