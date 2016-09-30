package xyz.brassgoggledcoders.steamagerevolution.modules.parts;

import java.util.Arrays;
import java.util.List;

import xyz.brassgoggledcoders.boilerplate.items.ItemSubBase;
import xyz.brassgoggledcoders.boilerplate.module.Module;
import xyz.brassgoggledcoders.boilerplate.module.ModuleBase;
import xyz.brassgoggledcoders.boilerplate.registries.ConfigRegistry;
import xyz.brassgoggledcoders.boilerplate.registries.ItemRegistry;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;

@Module(mod = SteamAgeRevolution.MODID)
public class ModuleParts extends ModuleBase {

	public static List<String> partTypes = Arrays.asList("gear");

	public static ItemSubBase ironPart, brassPart;

	@Override
	public void registerItems(ConfigRegistry configRegistry, ItemRegistry itemRegistry) {
		ironPart = new ItemSubBase("parts", "iron_part", partTypes);
		itemRegistry.registerItem(ironPart);
		brassPart = new ItemSubBase("parts", "brass_part", partTypes);
		itemRegistry.registerItem(brassPart);
	}

	@Override
	public String getName() {
		return "Parts";
	}

}
