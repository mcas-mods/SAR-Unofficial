package xyz.brassgoggledcoders.steamagerevolution.modules.materials;

import java.awt.Color;

import com.teamacronymcoders.base.materialsystem.materials.Material;
import com.teamacronymcoders.base.modulesystem.Module;
import com.teamacronymcoders.base.modulesystem.ModuleBase;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;

@Module(value = SteamAgeRevolution.MODID)
public class ModuleMaterials extends ModuleBase {

	String[] alloy = new String[] {"gear", "ingot", "nugget", "plate"};

	@Override
	public void preInit(FMLPreInitializationEvent fmlPreInitializationEvent) {
		Material steel = new Material("Steel", Color.DARK_GRAY, false);
		steel.registerPartsFor(alloy);
		Material brass = new Material("Brass", Color.MAGENTA, false);
		brass.registerPartsFor(alloy);

	}

	@Override
	public String getName() {
		return "Materials";
	}
}
