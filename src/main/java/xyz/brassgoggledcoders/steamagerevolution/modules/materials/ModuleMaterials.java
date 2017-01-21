package xyz.brassgoggledcoders.steamagerevolution.modules.materials;

import java.awt.Color;

import com.teamacronymcoders.base.api.materials.MaterialRegistry;
import com.teamacronymcoders.base.api.materials.MaterialType;
import com.teamacronymcoders.base.api.materials.MaterialType.EnumPartType;
import com.teamacronymcoders.base.modulesystem.Module;
import com.teamacronymcoders.base.modulesystem.ModuleBase;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;

@Module(value = SteamAgeRevolution.MODID)
public class ModuleMaterials extends ModuleBase {

	EnumPartType[] alloy = new EnumPartType[] {EnumPartType.BLOCK, EnumPartType.DUST, EnumPartType.GEAR,
			EnumPartType.INGOT, EnumPartType.NUGGET, EnumPartType.PLATE};

	@Override
	public void preInit(FMLPreInitializationEvent fmlPreInitializationEvent) {
		MaterialType steel = new MaterialType("Steel", new Color(12876343), false, alloy);
		MaterialRegistry.registerMaterial(666, steel);
		MaterialType brass = new MaterialType("Brass", new Color(12876343), false, alloy);
		MaterialRegistry.registerMaterial(667, brass);
	}

	@Override
	public String getName() {
		return "Materials";
	}
}
