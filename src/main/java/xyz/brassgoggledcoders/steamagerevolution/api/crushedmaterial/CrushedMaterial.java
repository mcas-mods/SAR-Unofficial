package xyz.brassgoggledcoders.steamagerevolution.api.crushedmaterial;

import net.minecraft.util.ResourceLocation;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;

public class CrushedMaterial implements ICrushedMaterial {

	String name;
	int color;
	
	public CrushedMaterial(String name, int color) {
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
