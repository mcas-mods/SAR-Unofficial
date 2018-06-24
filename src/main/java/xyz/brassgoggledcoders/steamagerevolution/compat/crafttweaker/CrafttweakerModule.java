package xyz.brassgoggledcoders.steamagerevolution.compat.crafttweaker;

import java.util.List;

import com.teamacronymcoders.base.modulesystem.Module;
import com.teamacronymcoders.base.modulesystem.ModuleBase;
import com.teamacronymcoders.base.modulesystem.dependencies.IDependency;
import com.teamacronymcoders.base.modulesystem.dependencies.ModDependency;

import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;

@Module(value = SteamAgeRevolution.MODID)
public class CrafttweakerModule extends ModuleBase {

	@Override
	public List<IDependency> getDependencies(List<IDependency> dependencies) {
		dependencies.add(new ModDependency("crafttweaker"));
		return dependencies;
	}

	@Override
	public String getName() {
		return "Crafttweaker Support";
	}
}
