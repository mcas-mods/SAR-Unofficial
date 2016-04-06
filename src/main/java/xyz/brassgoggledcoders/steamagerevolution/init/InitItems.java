package xyz.brassgoggledcoders.steamagerevolution.init;

import net.minecraft.item.Item;
import xyz.brassgoggledcoders.boilerplate.lib.common.registries.ItemRegistry;
import xyz.brassgoggledcoders.steamagerevolution.item.ItemMetaFromList;
import xyz.brassgoggledcoders.steamagerevolution.item.ItemTeacup;
import xyz.brassgoggledcoders.steamagerevolution.item.ItemWatch;
import xyz.brassgoggledcoders.steamagerevolution.lib.ItemTypes;

public class InitItems
{
	public static ItemMetaFromList item_ingots, item_nuggets, item_dusts, item_plates;

	public static ItemTeacup item_teacup;
	public static Item item_watch;

	public static void init()
	{
		item_ingots = new ItemMetaFromList(ItemTypes.metalsIngot, "ingot", "metals");
		item_nuggets = new ItemMetaFromList(ItemTypes.metalsNugget, "nugget", "metals");
		item_dusts = new ItemMetaFromList(ItemTypes.metalsAll, "dust", "metals");
		item_plates = new ItemMetaFromList(ItemTypes.metalsAll, "plate", "metals");

		item_teacup = new ItemTeacup();
		item_watch = new ItemWatch();

		ItemRegistry.registerItem(item_ingots);
		ItemRegistry.registerItem(item_nuggets);
		ItemRegistry.registerItem(item_plates);
		ItemRegistry.registerItem(item_dusts);

		ItemRegistry.registerItem(item_teacup);
		ItemRegistry.registerItem(item_watch);
	}
}
