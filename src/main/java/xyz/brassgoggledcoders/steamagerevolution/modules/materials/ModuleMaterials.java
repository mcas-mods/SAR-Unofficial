package xyz.brassgoggledcoders.steamagerevolution.modules.materials;

import java.awt.Color;

import com.teamacronymcoders.base.materialsystem.MaterialException;
import com.teamacronymcoders.base.materialsystem.MaterialSystem;
import com.teamacronymcoders.base.materialsystem.materials.Material;
import com.teamacronymcoders.base.materialsystem.materials.MaterialBuilder;
import com.teamacronymcoders.base.materialsystem.parts.Part;
import com.teamacronymcoders.base.materialsystem.parts.PartBuilder;
import com.teamacronymcoders.base.modulesystem.Module;
import com.teamacronymcoders.base.modulesystem.ModuleBase;

import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;

@Module(value = SteamAgeRevolution.MODID)
public class ModuleMaterials extends ModuleBase {

	String[] alloy = new String[] {"gear", "ingot", "nugget", "plate"};

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		try {
			Material steel =
					new MaterialBuilder().setName("steel").setColor(Color.DARK_GRAY).setHasEffect(false).build();
			FMLLog.warning(MaterialSystem.getPartType("storage").getName());
			Part test = new PartBuilder().setName("test").setPartType(MaterialSystem.getPartType("storage")).build();
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
