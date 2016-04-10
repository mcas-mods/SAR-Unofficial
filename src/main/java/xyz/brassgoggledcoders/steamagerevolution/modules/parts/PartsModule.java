package xyz.brassgoggledcoders.steamagerevolution.modules.parts;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import xyz.brassgoggledcoders.boilerplate.lib.common.modules.Module;
import xyz.brassgoggledcoders.boilerplate.lib.common.registries.ItemRegistry;
import xyz.brassgoggledcoders.steamagerevolution.items.ItemSubBaseTest;
import xyz.brassgoggledcoders.steamagerevolution.lib.ItemTypes;

public class PartsModule extends Module
{
	public static ItemSubBaseTest bearing, bolt, gear, nail, nut, screw, spring, sprocket, thread, washer;

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
		bearing = new ItemSubBaseTest("parts", "bearing", ItemTypes.metalsParts);
		ItemRegistry.registerItem(bearing);
		bolt = new ItemSubBaseTest("parts", "bolt", ItemTypes.metalsParts);
		ItemRegistry.registerItem(bolt);
		gear = new ItemSubBaseTest("parts", "gear", ItemTypes.metalsParts);
		ItemRegistry.registerItem(gear);
		nail = new ItemSubBaseTest("parts", "nail", ItemTypes.metalsParts);
		ItemRegistry.registerItem(nail);
		nut = new ItemSubBaseTest("parts", "nut", ItemTypes.metalsParts);
		ItemRegistry.registerItem(nut);
		screw = new ItemSubBaseTest("parts", "screw", ItemTypes.metalsParts);
		ItemRegistry.registerItem(screw);
		spring = new ItemSubBaseTest("parts", "spring", ItemTypes.metalsParts);
		ItemRegistry.registerItem(spring);
		sprocket = new ItemSubBaseTest("parts", "sprocket", ItemTypes.metalsParts);
		ItemRegistry.registerItem(sprocket);
		thread = new ItemSubBaseTest("parts", "thread", ItemTypes.metalsParts);
		ItemRegistry.registerItem(thread);
		washer = new ItemSubBaseTest("parts",  "washer", ItemTypes.metalsParts);
		ItemRegistry.registerItem(washer);
	}
}
