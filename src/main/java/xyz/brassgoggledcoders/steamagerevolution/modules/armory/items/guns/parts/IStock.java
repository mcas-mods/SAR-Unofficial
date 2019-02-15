package xyz.brassgoggledcoders.steamagerevolution.modules.armory.items.guns.parts;

public interface IStock extends IGunPart {
	default GunPartType getPartType() {
		return GunPartType.STOCK;
	}

	public float getKnockbackModifier();
}
