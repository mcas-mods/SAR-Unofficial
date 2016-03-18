package xyz.brassgoggledcoders.steamagerevolution.init;

import net.minecraft.item.Item;
import xyz.brassgoggledcoders.boilerplate.lib.common.registries.ItemRegistry;
import xyz.brassgoggledcoders.steamagerevolution.item.ItemMaterial;
import xyz.brassgoggledcoders.steamagerevolution.item.ItemTeacup;
import xyz.brassgoggledcoders.steamagerevolution.item.ItemWatch;
import xyz.brassgoggledcoders.steamagerevolution.lib.ItemTypes;

public class InitItems
{
	public static Item ITEM_INGOTS;
	public static Item ITEM_NUGGETS;
	public static Item ITEM_PLATES;

	public static Item ITEM_TEACUP;
	public static Item ITEM_WATCH;

	public static void init()
	{
		ITEM_INGOTS = new ItemMaterial(ItemTypes.metalsIngot, "ingot");
		ITEM_NUGGETS = new ItemMaterial(ItemTypes.metalsNugget, "nugget");
		ITEM_PLATES = new ItemMaterial(ItemTypes.metalsAll, "plate");

		ITEM_TEACUP = new ItemTeacup();
		ITEM_WATCH = new ItemWatch();

		ItemRegistry.registerItem(ITEM_INGOTS);
		ItemRegistry.registerItem(ITEM_NUGGETS);
		ItemRegistry.registerItem(ITEM_PLATES);

		ItemRegistry.registerItem(ITEM_TEACUP);
		ItemRegistry.registerItem(ITEM_WATCH);
	}
}
