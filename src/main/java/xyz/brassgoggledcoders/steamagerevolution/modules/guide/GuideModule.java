package xyz.brassgoggledcoders.steamagerevolution.modules.guide;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import xyz.brassgoggledcoders.boilerplate.module.Module;
import xyz.brassgoggledcoders.boilerplate.module.ModuleBase;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;

@Module
public class GuideModule extends ModuleBase
{
	public static ItemManual manual; 
	
	@Override
	public String getName() {
		return "Guide";
	}

	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		manual = new ItemManual();
		this.getItemRegistry().registerItem(manual);
	}
	
}
