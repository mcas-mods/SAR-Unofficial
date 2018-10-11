package xyz.brassgoggledcoders.steamagerevolution.modules.armory.items.guns;

public interface IGunPart {

	public GunPartType getPartType();

	public String getPartName();

	public static enum GunPartType {
		BARREL, CHAMBER, MECHANISM, STOCK
	}
}
