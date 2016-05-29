package xyz.brassgoggledcoders.steamagerevolution.modules.vanity;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import xyz.brassgoggledcoders.boilerplate.modules.Module;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.modules.vanity.items.ItemWatch;

public class VanityModule extends Module
{
	public static ItemWatch watch;

	@Override
	public String getName()
	{
		return "Vanity";
	}

	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		this.registerItems();
	}

	public void registerItems()
	{
		watch = new ItemWatch();
		SteamAgeRevolution.instance.getRegistryHolder().getItemRegistry().registerItem(watch);
	}

}
