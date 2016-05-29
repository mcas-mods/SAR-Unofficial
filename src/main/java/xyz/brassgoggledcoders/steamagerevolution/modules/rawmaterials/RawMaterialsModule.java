package xyz.brassgoggledcoders.steamagerevolution.modules.rawmaterials;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import xyz.brassgoggledcoders.boilerplate.items.ItemSubBase;
import xyz.brassgoggledcoders.boilerplate.modules.Module;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
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
		SteamAgeRevolution.instance.getRegistryHolder().getItemRegistry().registerItem(ingots);
		nuggets = new ItemSubBase("metals", "nugget", ItemTypes.metalsNugget);
		SteamAgeRevolution.instance.getRegistryHolder().getItemRegistry().registerItem(nuggets);
		dusts = new ItemSubBase("metals", "dust", ItemTypes.metalsAll);
		SteamAgeRevolution.instance.getRegistryHolder().getItemRegistry().registerItem(dusts);
		plates = new ItemSubBase("metals", "plate", ItemTypes.metalsAll);
		SteamAgeRevolution.instance.getRegistryHolder().getItemRegistry().registerItem(plates);
	}

}
