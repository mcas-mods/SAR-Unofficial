package xyz.brassgoggledcoders.steamagerevolution.modules.tea;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import xyz.brassgoggledcoders.boilerplate.module.Module;
import xyz.brassgoggledcoders.boilerplate.module.ModuleBase;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.modules.tea.items.ItemTeacup;

@Module(mod = SteamAgeRevolution.MODID)
public class TeaModule extends ModuleBase
{

	public static ItemTeacup teacup;

	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		this.registerItems();
	}

	@Override
	public String getName()
	{
		return "Tea";
	}

	public void registerItems()
	{
		teacup = new ItemTeacup();
		this.getItemRegistry().registerItem(teacup);
	}

}
