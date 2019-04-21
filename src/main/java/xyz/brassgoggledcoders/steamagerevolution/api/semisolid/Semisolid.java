package xyz.brassgoggledcoders.steamagerevolution.api.semisolid;

import net.minecraft.util.ResourceLocation;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;

public class Semisolid implements ISemisolid {

	String name;
	int color;
	
	public Semisolid(String name, int color) {
		this.name = name;
		this.color = color;
	}
	
	@Override
	public String getTranslationKey() {
		return "crushedmaterial." + name;
	}

	@Override
	public int getColor() {
		return color;
	}

	@Override
	public ResourceLocation getRegistryName() {
		return new ResourceLocation(SteamAgeRevolution.MODID, name);
	}

}
