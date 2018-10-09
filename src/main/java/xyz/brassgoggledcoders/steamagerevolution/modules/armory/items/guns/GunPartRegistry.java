package xyz.brassgoggledcoders.steamagerevolution.modules.armory.items.guns;

import java.util.HashMap;

public class GunPartRegistry {
	private static HashMap<String, IGunPart> modules = new HashMap<String, IGunPart>();

	public static void registerModule(IGunPart module) {
		modules.put(module.getModuleID(), module);
	}

	public static IGunPart getModule(String id) {
		return modules.get(id);
	}
}
