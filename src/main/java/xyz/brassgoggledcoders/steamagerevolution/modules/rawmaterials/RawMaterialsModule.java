package xyz.brassgoggledcoders.steamagerevolution.modules.rawmaterials;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import xyz.brassgoggledcoders.boilerplate.items.ItemSubBase;
import xyz.brassgoggledcoders.boilerplate.module.Module;
import xyz.brassgoggledcoders.boilerplate.module.ModuleBase;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.lib.ItemTypes;

@Module(mod = SteamAgeRevolution.MODID)
public class RawMaterialsModule extends ModuleBase
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
		this.getItemRegistry().registerItem(ingots);
		nuggets = new ItemSubBase("metals", "nugget", ItemTypes.metalsNugget);
		this.getItemRegistry().registerItem(nuggets);
		dusts = new ItemSubBase("metals", "dust", ItemTypes.metalsAll);
		this.getItemRegistry().registerItem(dusts);
		plates = new ItemSubBase("metals", "plate", ItemTypes.metalsAll);
		this.getItemRegistry().registerItem(plates);
	}

}
