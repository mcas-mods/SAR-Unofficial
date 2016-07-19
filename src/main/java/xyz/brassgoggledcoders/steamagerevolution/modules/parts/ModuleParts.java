package xyz.brassgoggledcoders.steamagerevolution.modules.parts;

import java.util.Arrays;
import java.util.List;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import xyz.brassgoggledcoders.boilerplate.items.ItemSubBase;
import xyz.brassgoggledcoders.boilerplate.module.Module;
import xyz.brassgoggledcoders.boilerplate.module.ModuleBase;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;

@Module(mod = SteamAgeRevolution.MODID)
public class ModuleParts extends ModuleBase {

	public static List<String> partTypes = Arrays.asList("gear");

	public static ItemSubBase iron_part, brass_part;

	@SuppressWarnings("deprecation")
	@Override
	public void preInit(FMLPreInitializationEvent fmlPreInitializationEvent) {

		iron_part = new ItemSubBase("parts", "iron_part", partTypes);
		this.getItemRegistry().registerItem(iron_part);
		brass_part = new ItemSubBase("parts", "brass_part", partTypes);
		this.getItemRegistry().registerItem(brass_part);
	}

	@Override
	public String getName() {
		return "Parts";
	}

}
