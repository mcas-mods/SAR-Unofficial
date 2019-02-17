package xyz.brassgoggledcoders.steamagerevolution.modules.armory.items.guns.parts;

public interface IBarrel extends IGunPart {

	public float getVelocityModifier();

	public float getAccuracyModifier();

	@Override
	default GunPartType getPartType() {
		return GunPartType.BARREL;
	}

}
