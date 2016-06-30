package xyz.brassgoggledcoders.steamagerevolution.modules.materials;

import java.util.Arrays;
import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import xyz.brassgoggledcoders.boilerplate.blocks.material.BlockMetal;
import xyz.brassgoggledcoders.boilerplate.items.ItemSubBase;
import xyz.brassgoggledcoders.boilerplate.module.Module;
import xyz.brassgoggledcoders.boilerplate.module.ModuleBase;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;

@Module(mod = SteamAgeRevolution.MODID)
public class ModuleMaterials extends ModuleBase {
	public static Item dust, plate;
	public static ItemSubBase bearing, bolt, gear, nail, nut, screw, spring, sprocket, thread, washer;
	public static List<String> metalsParts = Arrays.asList("iron", "brass");

	@Override
	public void preInit(FMLPreInitializationEvent fmlPreInitializationEvent) {
		// TODO Move to materials tab
		dust = new ItemSubBase("metals/", "dust",
				xyz.brassgoggledcoders.boilerplate.modules.materials.MaterialsModule.allMetals);
		getItemRegistry().registerItem(dust);
		plate = new ItemSubBase("metals/", "plate",
				xyz.brassgoggledcoders.boilerplate.modules.materials.MaterialsModule.allMetals);
		getItemRegistry().registerItem(plate);

		bearing = new ItemSubBase("parts", "bearing", metalsParts);
		this.getItemRegistry().registerItem(bearing);
		bolt = new ItemSubBase("parts", "bolt", metalsParts);
		this.getItemRegistry().registerItem(bolt);
		gear = new ItemSubBase("parts", "gear", metalsParts);
		this.getItemRegistry().registerItem(gear);
		nail = new ItemSubBase("parts", "nail", metalsParts);
		this.getItemRegistry().registerItem(nail);
		nut = new ItemSubBase("parts", "nut", metalsParts);
		this.getItemRegistry().registerItem(nut);
		screw = new ItemSubBase("parts", "screw", metalsParts);
		this.getItemRegistry().registerItem(screw);
		spring = new ItemSubBase("parts", "spring", metalsParts);
		this.getItemRegistry().registerItem(spring);
	}

	@Override
	public void init(FMLInitializationEvent event) {
		for(int i = 0; i < BlockMetal.EnumBlockType.values().length; i++)
			// TODO Plate/Dust creation recipes
			GameRegistry.addSmelting(
					new ItemStack(xyz.brassgoggledcoders.boilerplate.modules.materials.MaterialsModule.ingot, 1, i),
					new ItemStack(dust, 1, i), 0.5F);
	}

	@Override
	public String getName() {
		return "Materials";
	}
}
