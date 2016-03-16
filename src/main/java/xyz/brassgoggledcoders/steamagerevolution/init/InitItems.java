package xyz.brassgoggledcoders.steamagerevolution.init;

import net.minecraft.item.Item;
import xyz.brassgoggledcoders.boilerplate.lib.common.registries.ItemRegistry;
import xyz.brassgoggledcoders.steamagerevolution.item.ItemMaterial;
import xyz.brassgoggledcoders.steamagerevolution.lib.MetalTypes;

public class InitItems {
	public static Item ingots;
	public static Item nuggets;
	public static Item plates;
	
	public static void init(){
		ingots=new ItemMaterial(MetalTypes.metalsIngot, "ingot");
		nuggets=new ItemMaterial(MetalTypes.metalsNugget, "nugget");
		plates=new ItemMaterial(MetalTypes.metalsAll, "plate");
		
		ItemRegistry.registerItem(ingots);
		ItemRegistry.registerItem(nuggets);
		ItemRegistry.registerItem(plates);
	}
}
