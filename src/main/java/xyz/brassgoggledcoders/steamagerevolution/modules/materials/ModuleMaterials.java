package xyz.brassgoggledcoders.steamagerevolution.modules.materials;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import xyz.brassgoggledcoders.boilerplate.MaterialsModule;
import xyz.brassgoggledcoders.boilerplate.blocks.material.BlockMetal;
import xyz.brassgoggledcoders.boilerplate.items.ItemSubBase;
import xyz.brassgoggledcoders.boilerplate.module.Module;
import xyz.brassgoggledcoders.boilerplate.module.ModuleBase;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;

@Module(mod = SteamAgeRevolution.MODID)
public class ModuleMaterials extends ModuleBase
{
	public static Item dust, plate;

	@Override
	public void preInit(FMLPreInitializationEvent fmlPreInitializationEvent)
	{
		// TODO Move to materials tab
		dust = new ItemSubBase("metals/", "dust", MaterialsModule.allMetals);
		getItemRegistry().registerItem(dust);
		plate = new ItemSubBase("metals/", "plate", MaterialsModule.allMetals);
		getItemRegistry().registerItem(plate);
	}

	@Override
	public void init(FMLInitializationEvent event)
	{
		for(int i = 0; i < BlockMetal.EnumBlockType.values().length; i++)
		{
			// TODO Plate/Dust creation recipes
			GameRegistry.addSmelting(new ItemStack(MaterialsModule.ingot, 1, i), new ItemStack(dust, 1, i), 0.5F);
		}
	}

	@Override
	public String getName()
	{
		return "Materials";
	}
}
