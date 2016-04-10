package xyz.brassgoggledcoders.steamagerevolution.modules.guide;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import xyz.brassgoggledcoders.boilerplate.lib.common.modules.Module;
import xyz.brassgoggledcoders.boilerplate.lib.common.registries.ItemRegistry;

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
		ItemRegistry.registerItem(manual);
	}
	
}
