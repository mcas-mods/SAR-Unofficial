package xyz.brassgoggledcoders.steamagerevolution.modules.rawmaterials;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import xyz.brassgoggledcoders.boilerplate.lib.common.modules.Module;
import xyz.brassgoggledcoders.boilerplate.lib.common.registries.ItemRegistry;
import xyz.brassgoggledcoders.steamagerevolution.items.ItemSubBaseTest;
import xyz.brassgoggledcoders.steamagerevolution.lib.ItemTypes;

public class RawMaterialsModule extends Module
{
	public static ItemSubBaseTest ingots, nuggets, dusts, plates;

	@Override
	public String getName()
	{
		return "Raw Materials";
	}

	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		this.registerItems();
	}

	public void registerItems()
	{
		ingots = new ItemSubBaseTest("metals", "ingot", ItemTypes.metalsIngot);
		ItemRegistry.registerItem(ingots);
		nuggets = new ItemSubBaseTest("metals", "nugget", ItemTypes.metalsNugget);
		ItemRegistry.registerItem(nuggets);
		dusts = new ItemSubBaseTest("metals", "dust", ItemTypes.metalsAll);
		ItemRegistry.registerItem(dusts);
		plates = new ItemSubBaseTest("metals", "plate", ItemTypes.metalsAll);
		ItemRegistry.registerItem(plates);
	}

}
