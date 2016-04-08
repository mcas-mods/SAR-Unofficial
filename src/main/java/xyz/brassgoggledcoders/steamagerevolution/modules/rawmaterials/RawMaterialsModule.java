package xyz.brassgoggledcoders.steamagerevolution.modules.rawmaterials;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import xyz.brassgoggledcoders.boilerplate.lib.common.items.ItemSubBase;
import xyz.brassgoggledcoders.boilerplate.lib.common.modules.Module;
import xyz.brassgoggledcoders.boilerplate.lib.common.registries.ItemRegistry;
import xyz.brassgoggledcoders.steamagerevolution.lib.ItemTypes;

import java.util.Arrays;

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
		ingots = new ItemSubBase("ingot", "metals", Arrays.asList(ItemTypes.metalsIngot));
		ItemRegistry.registerItem(ingots);
		nuggets = new ItemSubBase("nugget", "metals", Arrays.asList(ItemTypes.metalsNugget));
		ItemRegistry.registerItem(nuggets);
		dusts = new ItemSubBase("dust", "metals", Arrays.asList(ItemTypes.metalsAll));
		ItemRegistry.registerItem(dusts);
		plates = new ItemSubBase("plate", "metals", Arrays.asList(ItemTypes.metalsAll));
		ItemRegistry.registerItem(plates);
	}

}
