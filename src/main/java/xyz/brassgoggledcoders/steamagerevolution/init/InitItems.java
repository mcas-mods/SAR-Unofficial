package xyz.brassgoggledcoders.steamagerevolution.init;

import net.minecraft.item.Item;
import xyz.brassgoggledcoders.boilerplate.lib.common.registries.ItemRegistry;
import xyz.brassgoggledcoders.steamagerevolution.item.ItemMaterial;
import xyz.brassgoggledcoders.steamagerevolution.item.ItemTeacup;
import xyz.brassgoggledcoders.steamagerevolution.item.ItemWatch;
import xyz.brassgoggledcoders.steamagerevolution.lib.ItemTypes;

public class InitItems
{
	public static ItemMaterial item_ingots, item_nuggets, item_dusts, item_plates;

	public static ItemTeacup item_teacup;
	public static Item item_watch;

	public static void init()
	{
		item_ingots = new ItemMaterial(ItemTypes.metalsIngot, "ingot");
		item_nuggets = new ItemMaterial(ItemTypes.metalsNugget, "nugget");
		item_dusts = new ItemMaterial(ItemTypes.metalsAll, "dust");
		item_plates = new ItemMaterial(ItemTypes.metalsAll, "plate");

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
