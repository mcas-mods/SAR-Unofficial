package xyz.brassgoggledcoders.steamagerevolution.init;

import net.minecraft.item.Item;
import xyz.brassgoggledcoders.boilerplate.lib.common.registries.ItemRegistry;
import xyz.brassgoggledcoders.steamagerevolution.item.ItemIngots;
import xyz.brassgoggledcoders.steamagerevolution.item.ItemNuggets;
import xyz.brassgoggledcoders.steamagerevolution.item.ItemPlates;

public class InitItems {
	public static Item ingots;
	public static Item nuggets;
	public static Item plates;
	
	public static void init(){
		ingots=new ItemIngots();
		nuggets=new ItemNuggets();
		plates=new ItemPlates();
		
		ItemRegistry.registerItem(ingots);
		ItemRegistry.registerItem(nuggets);
		ItemRegistry.registerItem(plates);
	}
}
