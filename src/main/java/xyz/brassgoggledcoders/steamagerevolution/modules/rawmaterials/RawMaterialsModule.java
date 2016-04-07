package xyz.brassgoggledcoders.steamagerevolution.modules.rawmaterials;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import xyz.brassgoggledcoders.boilerplate.lib.common.modules.Module;
import xyz.brassgoggledcoders.boilerplate.lib.common.registries.ItemRegistry;
import xyz.brassgoggledcoders.steamagerevolution.item.ItemSubBase;
import xyz.brassgoggledcoders.steamagerevolution.lib.ItemTypes;

public class RawMaterialsModule extends Module
{

	public static ItemSubBase ingots, nuggets, dusts, plates;

	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		this.registerItems();
	}

	@Override
	public String getName()
	{
		return "Raw Materials";
	}

	public void registerItems()
	{
		ingots = new ItemSubBase(ItemTypes.metalsIngot, "ingot", "metals");
		ItemRegistry.registerItem(ingots);
		nuggets = new ItemSubBase(ItemTypes.metalsNugget, "nugget", "metals");
		ItemRegistry.registerItem(nuggets);
		dusts = new ItemSubBase(ItemTypes.metalsAll, "dust", "metals");
		ItemRegistry.registerItem(dusts);
		plates = new ItemSubBase(ItemTypes.metalsAll, "plate", "metals");
		ItemRegistry.registerItem(plates);
	}

}
