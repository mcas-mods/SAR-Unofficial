package xyz.brassgoggledcoders.steamagerevolution.modules.guide;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import xyz.brassgoggledcoders.boilerplate.modules.Module;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;

public class GuideModule extends Module 
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
		SteamAgeRevolution.instance.getRegistryHolder().getItemRegistry().registerItem(manual);
	}
	
}
