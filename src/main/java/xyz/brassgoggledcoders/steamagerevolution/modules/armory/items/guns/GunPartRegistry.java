package xyz.brassgoggledcoders.steamagerevolution.modules.armory.items.guns;

import java.util.HashMap;

public class GunPartRegistry {
	private static HashMap<String, IGunPart> parts = new HashMap<String, IGunPart>();

	public static void registerPart(IGunPart module) {
		parts.put(module.getPartName(), module);
	}

	public static IGunPart getPart(String id) {
		return parts.get(id);
	}
}
