package xyz.brassgoggledcoders.steamagerevolution.modules.vanity;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
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

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		this.registerItems();
	}

	public void registerItems() {
		watch = new ItemWatch();
		this.getItemRegistry().registerItem(watch);
		teacup = new ItemTeacup();
		this.getItemRegistry().registerItem(teacup);
	}

}
