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
		bearing = new ItemSubBase("parts", "bearing", ItemTypes.metalsParts);
		ItemRegistry.registerItem(bearing);
		bolt = new ItemSubBase("parts", "bolt", ItemTypes.metalsParts);
		ItemRegistry.registerItem(bolt);
		gear = new ItemSubBase("parts", "gear", ItemTypes.metalsParts);
		ItemRegistry.registerItem(gear);
		nail = new ItemSubBase("parts", "nail", ItemTypes.metalsParts);
		ItemRegistry.registerItem(nail);
		nut = new ItemSubBase("parts", "nut", ItemTypes.metalsParts);
		ItemRegistry.registerItem(nut);
		screw = new ItemSubBase("parts", "screw", ItemTypes.metalsParts);
		ItemRegistry.registerItem(screw);
		spring = new ItemSubBase("parts", "spring", ItemTypes.metalsParts);
		ItemRegistry.registerItem(spring);
		sprocket = new ItemSubBase("parts", "sprocket", ItemTypes.metalsParts);
		ItemRegistry.registerItem(sprocket);
		thread = new ItemSubBase("parts", "thread", ItemTypes.metalsParts);
		ItemRegistry.registerItem(thread);
		washer = new ItemSubBase("parts",  "washer", ItemTypes.metalsParts);
		ItemRegistry.registerItem(washer);
	}
}
