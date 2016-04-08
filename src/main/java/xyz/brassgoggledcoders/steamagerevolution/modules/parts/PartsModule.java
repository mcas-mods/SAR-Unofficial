package xyz.brassgoggledcoders.steamagerevolution.modules.parts;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import xyz.brassgoggledcoders.boilerplate.lib.common.items.ItemSubBase;
import xyz.brassgoggledcoders.boilerplate.lib.common.modules.Module;
import xyz.brassgoggledcoders.boilerplate.lib.common.registries.ItemRegistry;
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
		bearing = new ItemSubBase("bearing", "parts", ItemTypes.metalsParts);
		ItemRegistry.registerItem(bearing);
		bolt = new ItemSubBase("bolt", "parts", ItemTypes.metalsParts);
		ItemRegistry.registerItem(bolt);
		gear = new ItemSubBase("gear", "parts", ItemTypes.metalsParts);
		ItemRegistry.registerItem(gear);
		nail = new ItemSubBase("nail", "parts", ItemTypes.metalsParts);
		ItemRegistry.registerItem(nail);
		nut = new ItemSubBase("nut", "parts", ItemTypes.metalsParts);
		ItemRegistry.registerItem(nut);
		screw = new ItemSubBase("screw", "parts", ItemTypes.metalsParts);
		ItemRegistry.registerItem(screw);
		spring = new ItemSubBase("spring", "parts", ItemTypes.metalsParts);
		ItemRegistry.registerItem(spring);
		sprocket = new ItemSubBase("sprocket", "parts", ItemTypes.metalsParts);
		ItemRegistry.registerItem(sprocket);
		thread = new ItemSubBase("thread", "parts", ItemTypes.metalsParts);
		ItemRegistry.registerItem(thread);
		washer = new ItemSubBase("washer", "parts", ItemTypes.metalsParts);
		ItemRegistry.registerItem(washer);
	}
}
