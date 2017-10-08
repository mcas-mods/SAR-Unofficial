package xyz.brassgoggledcoders.steamagerevolution.compat.minetweaker;

import crafttweaker.CraftTweakerAPI;

public class SARMinetweaker {
	public static void preInit() {
		CraftTweakerAPI.registerClass(AlloyFurnaceTweaker.class);
		CraftTweakerAPI.registerClass(SteamHammerTweaker.class);
		CraftTweakerAPI.registerClass(SteamFurnaceTweaker.class);

	}
}
