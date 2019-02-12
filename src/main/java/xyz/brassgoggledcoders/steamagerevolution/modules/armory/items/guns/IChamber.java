package xyz.brassgoggledcoders.steamagerevolution.modules.armory.items.guns;

import xyz.brassgoggledcoders.steamagerevolution.modules.armory.items.guns.IAmmo.AmmoType;

public interface IChamber extends IGunPart {
	public AmmoType getAcceptedType();

	default GunPartType getPartType() {
		return GunPartType.CHAMBER;
	}

}
