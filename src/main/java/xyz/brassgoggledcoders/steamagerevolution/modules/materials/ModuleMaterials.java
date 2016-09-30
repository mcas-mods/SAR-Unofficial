package xyz.brassgoggledcoders.steamagerevolution.modules.materials;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import xyz.brassgoggledcoders.boilerplate.Boilerplate;
import xyz.brassgoggledcoders.boilerplate.blocks.material.BlockMetal;
import xyz.brassgoggledcoders.boilerplate.blocks.material.BlockMetalOre;
import xyz.brassgoggledcoders.boilerplate.items.ItemSubBase;
import xyz.brassgoggledcoders.boilerplate.module.Module;
import xyz.brassgoggledcoders.boilerplate.module.ModuleBase;
import xyz.brassgoggledcoders.boilerplate.recipes.RecipeUtils;
import xyz.brassgoggledcoders.boilerplate.registries.BlockRegistry;
import xyz.brassgoggledcoders.boilerplate.registries.ConfigRegistry;
import xyz.brassgoggledcoders.boilerplate.registries.ItemRegistry;
import xyz.brassgoggledcoders.boilerplate.worldgen.OreGenerationHandler;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;

@Module(mod = SteamAgeRevolution.MODID)
public class ModuleMaterials extends ModuleBase {

	public static List<String> oreMetals = Arrays.asList(BlockMetalOre.EnumBlockType.names());
	public static List<String> ourMetals = Arrays.asList(BlockMetal.EnumBlockType.names());
	public static List<String> almostAllMetals =
			Arrays.asList(ArrayUtils.addAll(BlockMetal.EnumBlockType.names(), new String[] {"iron"}));
	public static List<String> allMetals =
			Arrays.asList(ArrayUtils.addAll(BlockMetal.EnumBlockType.names(), new String[] {"iron", "gold"}));

	public static Block metalOre, metalBlock;
	public static Item ingot, nugget, dust, plate;

	// TODO Oredict registration

	@Override
	public void registerItems(ConfigRegistry configRegistry, ItemRegistry itemRegistry) {
		dust = new ItemSubBase("metals/", "dust", allMetals);
		itemRegistry.registerItem(dust);
		plate = new ItemSubBase("metals/", "plate", allMetals);
		itemRegistry.registerItem(plate);
		ingot = new ItemSubBase("metals/", "ingot", ourMetals);
		itemRegistry.registerItem(ingot);
		nugget = new ItemSubBase("metals/", "nugget", almostAllMetals);
		itemRegistry.registerItem(nugget);
	}

	@Override
	public void registerBlocks(ConfigRegistry configRegistry, BlockRegistry blockRegistry) {
		metalOre = new BlockMetalOre();
		blockRegistry.registerBlock(metalOre, "metal_ore");
		metalBlock = new BlockMetal();
		blockRegistry.registerBlock(metalBlock, "metal_block");
	}

	@SuppressWarnings("deprecation")
	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
		// TODO This needs to go someplace else.
		Boilerplate.instance.getRegistryHolder().getConfigRegistry().addNewConfigFile("oregeneration");
		OreGenerationHandler.requestOreGeneration("Copper", metalOre.getStateFromMeta(0), 5, 70, 2);
		OreGenerationHandler.requestOreGeneration("Zinc", metalOre.getStateFromMeta(1), 3, 50, 1);
	}

	@Override
	public void init(FMLInitializationEvent event) {

		for(int i = 0; i < allMetals.size(); i++) {
			OreDictionary.registerOre("dust" + StringUtils.capitalize(allMetals.get(i)), new ItemStack(dust, 1, i));
			OreDictionary.registerOre("plate" + StringUtils.capitalize(allMetals.get(i)), new ItemStack(plate, 1, i));
		}

		for(int i = 0; i < ourMetals.size(); i++) {
			OreDictionary.registerOre("ore" + StringUtils.capitalize(ourMetals.get(i)), new ItemStack(metalOre, 1, i));
			OreDictionary.registerOre("block" + StringUtils.capitalize(ourMetals.get(i)),
					new ItemStack(metalBlock, 1, i));
			OreDictionary.registerOre("ingot" + StringUtils.capitalize(ourMetals.get(i)), new ItemStack(ingot, 1, i));
		}

		for(int i = 0; i < almostAllMetals.size(); i++) {
			OreDictionary.registerOre("nugget" + StringUtils.capitalize(almostAllMetals.get(i)),
					new ItemStack(nugget, 1, i));
		}

		GameRegistry.addSmelting(new ItemStack(metalOre, 1, 0), new ItemStack(ingot, 1, 0), 0.5F);
		GameRegistry.addSmelting(new ItemStack(metalOre, 1, 1), new ItemStack(ingot, 1, 1), 0.5F);

		for(int i = 0; i < BlockMetal.EnumBlockType.values().length; i++)
			RecipeUtils.addMetalRecipes(metalBlock, ingot, nugget, i);

		for(int i = 0; i < BlockMetal.EnumBlockType.values().length; i++)
			// TODO Plate/Dust creation recipes
			GameRegistry.addSmelting(new ItemStack(ingot, 1, i), new ItemStack(dust, 1, i), 0.5F);
	}

	@Override
	public String getName() {
		return "Materials";
	}
}
