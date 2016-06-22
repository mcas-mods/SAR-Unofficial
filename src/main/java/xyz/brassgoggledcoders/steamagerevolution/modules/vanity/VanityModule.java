package xyz.brassgoggledcoders.steamagerevolution.modules.vanity;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import xyz.brassgoggledcoders.boilerplate.module.Module;
import xyz.brassgoggledcoders.boilerplate.module.ModuleBase;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.modules.vanity.items.ItemWatch;

@Module(mod = SteamAgeRevolution.MODID)
public class VanityModule extends ModuleBase {
	public static ItemWatch watch;

	@Override
	public String getName() {
		return "Vanity";
	}

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		this.registerItems();
	}

	public void registerItems() {
		watch = new ItemWatch();
		this.getItemRegistry().registerItem(watch);
	}

}
