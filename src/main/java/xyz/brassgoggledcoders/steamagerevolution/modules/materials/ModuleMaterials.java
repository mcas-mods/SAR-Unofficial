package xyz.brassgoggledcoders.steamagerevolution.modules.materials;

import java.awt.Color;

import com.teamacronymcoders.base.materialsystem.MaterialException;
import com.teamacronymcoders.base.materialsystem.MaterialUser;
import com.teamacronymcoders.base.materialsystem.materialparts.MaterialPart;
import com.teamacronymcoders.base.materialsystem.materials.Material;
import com.teamacronymcoders.base.materialsystem.materials.MaterialBuilder;
import com.teamacronymcoders.base.materialsystem.parts.Part;
import com.teamacronymcoders.base.materialsystem.parts.PartBuilder;
import com.teamacronymcoders.base.materialsystem.parttype.BlockPartType;
import com.teamacronymcoders.base.materialsystem.parttype.FluidPartType;
import com.teamacronymcoders.base.modulesystem.Module;
import com.teamacronymcoders.base.modulesystem.ModuleBase;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import scala.actors.threadpool.Arrays;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.utils.MaterialPartMetalBlock;

@Module(value = SteamAgeRevolution.MODID)
public class ModuleMaterials extends ModuleBase {

	String[] vanillaParts = new String[] {"nugget", "plate", "dust"};
	String[] alloyParts = new String[] {"ingot", "nugget", "plate", "dust"};

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		try {
			MaterialUser SAR = SteamAgeRevolution.instance.getMaterialUser();
			Material iron = new MaterialBuilder().setName("Iron").setColor(Color.GRAY).setHasEffect(false).build();
			Material gold = new MaterialBuilder().setName("Gold").setColor(Color.YELLOW).setHasEffect(false).build();
			Material steel =
					new MaterialBuilder().setName("Steel").setColor(Color.DARK_GRAY).setHasEffect(false).build();
			Material brass = new MaterialBuilder().setName("Brass").setColor(Color.ORANGE).setHasEffect(false).build();

			Part moltenMetal = new PartBuilder().setName("metal_molten").setPartType(new FluidPartType()).build();
			Part storageMetal = new PartBuilder().setName("metal_storage").setPartType(new BlockPartType()).build();

			MaterialPart moltenIron = new MaterialPart(SAR, iron, moltenMetal);
			MaterialPart moltenGold = new MaterialPart(SAR, gold, moltenMetal);
			MaterialPart moltenBrass = new MaterialPart(SAR, brass, moltenMetal);
			MaterialPart moltenSteel = new MaterialPart(SAR, steel, moltenMetal);

			SAR.registerPartsForMaterial(iron, vanillaParts);
			SAR.registerMaterialPart(moltenIron);
			// Gold already has a nugget
			SAR.registerPartsForMaterial(gold, (String[]) Arrays.copyOfRange(vanillaParts, 1, vanillaParts.length));
			SAR.registerMaterialPart(moltenGold);
			SAR.registerPartsForMaterial(steel, alloyParts);
			SAR.registerMaterialPart(moltenSteel);
			SAR.registerMaterialPart(new MaterialPartMetalBlock(SAR, brass, storageMetal));
			SAR.registerPartsForMaterial(brass, alloyParts);
			SAR.registerMaterialPart(moltenBrass);
			SAR.registerMaterialPart(new MaterialPartMetalBlock(SAR, steel, storageMetal));
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
