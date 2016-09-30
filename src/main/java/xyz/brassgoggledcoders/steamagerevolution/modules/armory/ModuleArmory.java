package xyz.brassgoggledcoders.steamagerevolution.modules.armory;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import xyz.brassgoggledcoders.boilerplate.module.ModuleBase;

// @Module(mod = SteamAgeRevolution.MODID)
public class ModuleArmory extends ModuleBase {

	public static Item slabShield;

	@Override
	public void preInit(FMLPreInitializationEvent fmlPreInitializationEvent) {
		slabShield = new ItemSlabShield();
		getItemRegistry().registerItem(slabShield);
	}

	@Override
	public String getName() {
		return "Armoury";
	}
}
