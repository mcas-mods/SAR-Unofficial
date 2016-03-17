package xyz.brassgoggledcoders.steamagerevolution.init;

import net.minecraft.item.Item;
import xyz.brassgoggledcoders.boilerplate.lib.common.registries.ItemRegistry;
import xyz.brassgoggledcoders.steamagerevolution.item.ItemMaterial;
import xyz.brassgoggledcoders.steamagerevolution.item.ItemWatch;
import xyz.brassgoggledcoders.steamagerevolution.lib.MetalTypes;

public class InitItems
{
	public static Item ITEM_INGOTS;
	public static Item ITEM_NUGGETS;
	public static Item ITEM_PLATES;

	public static Item ITEM_WATCH;

	public static void init()
	{
		ITEM_INGOTS = new ItemMaterial(MetalTypes.metalsIngot, "ingot");
		ITEM_NUGGETS = new ItemMaterial(MetalTypes.metalsNugget, "nugget");
		ITEM_PLATES = new ItemMaterial(MetalTypes.metalsAll, "plate");

		ITEM_WATCH = new ItemWatch();

		ItemRegistry.registerItem(ITEM_INGOTS);
		ItemRegistry.registerItem(ITEM_NUGGETS);
		ItemRegistry.registerItem(ITEM_PLATES);

		ItemRegistry.registerItem(ITEM_WATCH);
	}
}
