package xyz.brassgoggledcoders.steamagerevolution.modules.rawmaterials;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import xyz.brassgoggledcoders.boilerplate.lib.common.items.ItemSubBase;
import xyz.brassgoggledcoders.boilerplate.lib.common.modules.Module;
import xyz.brassgoggledcoders.boilerplate.lib.common.registries.ItemRegistry;
import xyz.brassgoggledcoders.steamagerevolution.lib.ItemTypes;

public class RawMaterialsModule extends Module
{
	public static ItemSubBase ingots, nuggets, dusts, plates;

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
		ingots = new ItemSubBase("metals", "ingot", ItemTypes.metalsIngot);
		ItemRegistry.registerItem(ingots);
		nuggets = new ItemSubBase("metals", "nugget", ItemTypes.metalsNugget);
		ItemRegistry.registerItem(nuggets);
		dusts = new ItemSubBase("metals", "dust", ItemTypes.metalsAll);
		ItemRegistry.registerItem(dusts);
		plates = new ItemSubBase("metals", "plate", ItemTypes.metalsAll);
		ItemRegistry.registerItem(plates);
	}

}
