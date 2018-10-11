package xyz.brassgoggledcoders.steamagerevolution.modules.armory.items.guns;

public interface IChamber extends IGunPart {
	public AmmoType getAcceptedType();

	default GunPartType getPartType() {
		return GunPartType.CHAMBER;
	}

}
