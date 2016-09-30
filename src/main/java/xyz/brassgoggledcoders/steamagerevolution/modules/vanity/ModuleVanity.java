package xyz.brassgoggledcoders.steamagerevolution.modules.vanity;

import xyz.brassgoggledcoders.boilerplate.module.Module;
import xyz.brassgoggledcoders.boilerplate.module.ModuleBase;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;

@Module(mod = SteamAgeRevolution.MODID)
public class ModuleVanity extends ModuleBase {
	public static ItemWatch watch;
	public static ItemTeacup teacup;

	@Override
	public String getName() {
		return "Vanity";
	}
}
