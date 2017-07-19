package xyz.brassgoggledcoders.steamagerevolution.modules.materials;

import java.awt.Color;

import com.teamacronymcoders.base.materialsystem.MaterialException;
import com.teamacronymcoders.base.materialsystem.materials.Material;
import com.teamacronymcoders.base.materialsystem.materials.MaterialBuilder;
import com.teamacronymcoders.base.modulesystem.Module;
import com.teamacronymcoders.base.modulesystem.ModuleBase;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;

@Module(value = SteamAgeRevolution.MODID)
public class ModuleMaterials extends ModuleBase {

	String[] alloyParts = new String[] {"gear", "ingot", "nugget", "plate"};

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		try {
			Material steel =
					new MaterialBuilder().setName("Steel").setColor(Color.DARK_GRAY).setHasEffect(false).build();
			SteamAgeRevolution.instance.getMaterialUser().registerPartsForMaterial(steel, alloyParts);
			Material brass = new MaterialBuilder().setName("Brass").setColor(Color.ORANGE).setHasEffect(false).build();
			SteamAgeRevolution.instance.getMaterialUser().registerPartsForMaterial(brass, alloyParts);
		}
		catch(MaterialException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public String getName() {
		return "Materials";
	}
}
