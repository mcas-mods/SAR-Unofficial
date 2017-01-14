package xyz.brassgoggledcoders.steamagerevolution.modules.armory;

import com.teamacronymcoders.base.modulesystem.ModuleBase;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

// @Module(mod = SteamAgeRevolution.MODID)
public class ModuleArmory extends ModuleBase {

	public static ItemSlabShield slabShield;

	@Override
	public void preInit(FMLPreInitializationEvent fmlPreInitializationEvent) {
		slabShield = new ItemSlabShield();
		getItemRegistry().register(slabShield);
	}

	@Override
	public String getName() {
		return "Armoury";
	}
}
