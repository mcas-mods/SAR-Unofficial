package xyz.brassgoggledcoders.steamagerevolution.modules.vanity;

import com.teamacronymcoders.base.modulesystem.Module;
import com.teamacronymcoders.base.modulesystem.ModuleBase;

import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;

@Module(value = SteamAgeRevolution.MODID)
public class ModuleVanity extends ModuleBase {
	public static ItemWatch watch;
	public static ItemTeacup teacup;

	@Override
	public String getName() {
		return "Vanity";
	}
}
