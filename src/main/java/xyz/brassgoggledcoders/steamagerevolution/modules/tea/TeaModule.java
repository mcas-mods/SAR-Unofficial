package xyz.brassgoggledcoders.steamagerevolution.modules.tea;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import xyz.brassgoggledcoders.boilerplate.lib.common.modules.Module;
import xyz.brassgoggledcoders.boilerplate.lib.common.registries.ItemRegistry;
import xyz.brassgoggledcoders.steamagerevolution.modules.tea.items.ItemTeacup;

public class TeaModule extends Module
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
		ItemRegistry.registerItem(teacup);
	}

}
