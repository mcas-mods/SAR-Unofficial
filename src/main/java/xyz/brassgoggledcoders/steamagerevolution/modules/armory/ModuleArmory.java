package xyz.brassgoggledcoders.steamagerevolution.modules.armory;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import xyz.brassgoggledcoders.boilerplate.module.Module;
import xyz.brassgoggledcoders.boilerplate.module.ModuleBase;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;

@Module(mod = SteamAgeRevolution.MODID)
public class ModuleArmory extends ModuleBase {

	public static Item slab_shield;

	@Override
	public void preInit(FMLPreInitializationEvent fmlPreInitializationEvent) {
		slab_shield = new ItemSlabShield();
		getItemRegistry().registerItem(slab_shield);
	}

	@Override
	public String getName() {
		return "Armoury";
	}
}
