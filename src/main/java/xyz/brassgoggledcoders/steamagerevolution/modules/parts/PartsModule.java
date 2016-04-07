package xyz.brassgoggledcoders.steamagerevolution.modules.parts;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import xyz.brassgoggledcoders.boilerplate.lib.common.modules.Module;
import xyz.brassgoggledcoders.boilerplate.lib.common.registries.ItemRegistry;
import xyz.brassgoggledcoders.steamagerevolution.item.ItemSubBase;
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
		bearing = new ItemSubBase(ItemTypes.metalsParts, "bearing", "parts");
		ItemRegistry.registerItem(bearing);
		bolt = new ItemSubBase(ItemTypes.metalsParts, "bolt", "parts");
		ItemRegistry.registerItem(bolt);
		gear = new ItemSubBase(ItemTypes.metalsParts, "gear", "parts");
		ItemRegistry.registerItem(gear);
		nail = new ItemSubBase(ItemTypes.metalsParts, "nail", "parts");
		ItemRegistry.registerItem(nail);
		nut = new ItemSubBase(ItemTypes.metalsParts, "nut", "parts");
		ItemRegistry.registerItem(nut);
		screw = new ItemSubBase(ItemTypes.metalsParts, "screw", "parts");
		ItemRegistry.registerItem(screw);
		spring = new ItemSubBase(ItemTypes.metalsParts, "spring", "parts");
		ItemRegistry.registerItem(spring);
		sprocket = new ItemSubBase(ItemTypes.metalsParts, "sprocket", "parts");
		ItemRegistry.registerItem(sprocket);
		thread = new ItemSubBase(ItemTypes.metalsParts, "thread", "parts");
		ItemRegistry.registerItem(thread);
		washer = new ItemSubBase(ItemTypes.metalsParts, "washer", "parts");
		ItemRegistry.registerItem(washer);
	}
}
