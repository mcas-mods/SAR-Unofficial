package xyz.brassgoggledcoders.steamagerevolution.modules.materials;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import xyz.brassgoggledcoders.boilerplate.Boilerplate;
import xyz.brassgoggledcoders.boilerplate.blocks.material.BlockMetal;
import xyz.brassgoggledcoders.boilerplate.blocks.material.BlockMetalOre;
import xyz.brassgoggledcoders.boilerplate.items.ItemSubBase;
import xyz.brassgoggledcoders.boilerplate.module.Module;
import xyz.brassgoggledcoders.boilerplate.module.ModuleBase;
import xyz.brassgoggledcoders.boilerplate.recipes.RecipeUtils;
import xyz.brassgoggledcoders.boilerplate.worldgen.OreGenerationHandler;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;

@Module(mod = SteamAgeRevolution.MODID)
public class ModuleMaterials extends ModuleBase {
	public static Item dust, plate;
	public static ItemSubBase bearing, bolt, gear, nail, nut, screw, spring, sprocket, thread, washer;

	public static List<String> oreMetals = Arrays.asList(BlockMetalOre.EnumBlockType.names());
	public static List<String> ourMetals = Arrays.asList(BlockMetal.EnumBlockType.names());
	public static List<String> almostAllMetals =
			Arrays.asList(ArrayUtils.addAll(BlockMetalOre.EnumBlockType.names(), new String[] {"iron"}));
	public static List<String> allMetals =
			Arrays.asList(ArrayUtils.addAll(BlockMetalOre.EnumBlockType.names(), new String[] {"iron", "gold"}));
	public static List<String> partsMetals = Arrays.asList("iron", "brass");

	public static Block metal_ore, metal_block;
	public static Item ingot, nugget;

	// TODO oredict registration

	@Override
	public void preInit(FMLPreInitializationEvent fmlPreInitializationEvent) {

		dust = new ItemSubBase("metals/", "dust", allMetals);
		getItemRegistry().registerItem(dust);
		plate = new ItemSubBase("metals/", "plate", allMetals);
		getItemRegistry().registerItem(plate);

		// TODO Old method.
		bearing = new ItemSubBase("parts", "bearing", partsMetals);
		this.getItemRegistry().registerItem(bearing);
		bolt = new ItemSubBase("parts", "bolt", partsMetals);
		this.getItemRegistry().registerItem(bolt);
		gear = new ItemSubBase("parts", "gear", partsMetals);
		this.getItemRegistry().registerItem(gear);
		nail = new ItemSubBase("parts", "nail", partsMetals);
		this.getItemRegistry().registerItem(nail);
		nut = new ItemSubBase("parts", "nut", partsMetals);
		this.getItemRegistry().registerItem(nut);
		screw = new ItemSubBase("parts", "screw", partsMetals);
		this.getItemRegistry().registerItem(screw);
		spring = new ItemSubBase("parts", "spring", partsMetals);
		this.getItemRegistry().registerItem(spring);

		metal_ore = new BlockMetalOre();
		this.getBlockRegistry().registerBlock(metal_ore, "metal_ore");
		metal_block = new BlockMetal();
		this.getBlockRegistry().registerBlock(metal_block, "metal_block");

		ingot = new ItemSubBase("metals/", "ingot", ourMetals);
		getItemRegistry().registerItem(ingot);
		nugget = new ItemSubBase("metals/", "nugget", almostAllMetals);
		getItemRegistry().registerItem(nugget);

		// TODO This needs to go someplace else.
		Boilerplate.instance.getRegistryHolder().getConfigRegistry().addNewConfigFile("oregeneration");
		OreGenerationHandler.requestOreGeneration("Copper", metal_ore.getStateFromMeta(0), 5, 70, 2);
		OreGenerationHandler.requestOreGeneration("Zinc", metal_ore.getStateFromMeta(1), 3, 50, 1);
	}

	@Override
	public void init(FMLInitializationEvent event) {
		GameRegistry.addSmelting(new ItemStack(metal_ore, 1, 0), new ItemStack(ingot, 1, 0), 0.5F);
		GameRegistry.addSmelting(new ItemStack(metal_ore, 1, 1), new ItemStack(ingot, 1, 1), 0.5F);

		for(int i = 0; i < BlockMetal.EnumBlockType.values().length; i++)
			RecipeUtils.addMetalRecipes(metal_block, ingot, nugget, i);

		for(int i = 0; i < BlockMetal.EnumBlockType.values().length; i++)
			// TODO Plate/Dust creation recipes
			GameRegistry.addSmelting(new ItemStack(ingot, 1, i), new ItemStack(dust, 1, i), 0.5F);
	}

	@Override
	public String getName() {
		return "Materials";
	}
}
