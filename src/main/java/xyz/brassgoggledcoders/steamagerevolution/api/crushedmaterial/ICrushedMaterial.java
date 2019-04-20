package xyz.brassgoggledcoders.steamagerevolution.api.crushedmaterial;

import net.minecraft.util.ResourceLocation;

public interface ICrushedMaterial {
	public ResourceLocation getRegistryName();
	public String getTranslationKey();
	public int getColor();
}
