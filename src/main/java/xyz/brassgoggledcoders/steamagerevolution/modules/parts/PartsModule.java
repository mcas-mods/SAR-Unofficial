package xyz.brassgoggledcoders.steamagerevolution.modules.parts;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import xyz.brassgoggledcoders.boilerplate.items.ItemSubBase;
import xyz.brassgoggledcoders.boilerplate.modules.Module;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.lib.ItemTypes;

public class PartsModule extends Module
{
	public static ItemSubBase bearing, bolt, gear, nail, nut, screw, spring, sprocket, thread, washer;

	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		this.registerItems();
	}

	@Override
	public String getName()
	{
		return "Parts";
	}

	public void registerItems()
	{
		bearing = new ItemSubBase("parts", "bearing", ItemTypes.metalsParts);
		SteamAgeRevolution.instance.getRegistryHolder().getItemRegistry().registerItem(bearing);
		bolt = new ItemSubBase("parts", "bolt", ItemTypes.metalsParts);
		SteamAgeRevolution.instance.getRegistryHolder().getItemRegistry().registerItem(bolt);
		gear = new ItemSubBase("parts", "gear", ItemTypes.metalsParts);
		SteamAgeRevolution.instance.getRegistryHolder().getItemRegistry().registerItem(gear);
		nail = new ItemSubBase("parts", "nail", ItemTypes.metalsParts);
		SteamAgeRevolution.instance.getRegistryHolder().getItemRegistry().registerItem(nail);
		nut = new ItemSubBase("parts", "nut", ItemTypes.metalsParts);
		SteamAgeRevolution.instance.getRegistryHolder().getItemRegistry().registerItem(nut);
		screw = new ItemSubBase("parts", "screw", ItemTypes.metalsParts);
		SteamAgeRevolution.instance.getRegistryHolder().getItemRegistry().registerItem(screw);
		spring = new ItemSubBase("parts", "spring", ItemTypes.metalsParts);
		SteamAgeRevolution.instance.getRegistryHolder().getItemRegistry().registerItem(spring);
		sprocket = new ItemSubBase("parts", "sprocket", ItemTypes.metalsParts);
		SteamAgeRevolution.instance.getRegistryHolder().getItemRegistry().registerItem(sprocket);
		thread = new ItemSubBase("parts", "thread", ItemTypes.metalsParts);
		SteamAgeRevolution.instance.getRegistryHolder().getItemRegistry().registerItem(thread);
		washer = new ItemSubBase("parts",  "washer", ItemTypes.metalsParts);
		SteamAgeRevolution.instance.getRegistryHolder().getItemRegistry().registerItem(washer);
	}
}
