package xyz.brassgoggledcoders.steamagerevolution.modules.materials;

import java.awt.Color;

import com.teamacronymcoders.base.materialsystem.MaterialException;
import com.teamacronymcoders.base.materialsystem.MaterialUser;
import com.teamacronymcoders.base.materialsystem.materialparts.MaterialPart;
import com.teamacronymcoders.base.materialsystem.materialparts.MaterialPartData;
import com.teamacronymcoders.base.materialsystem.materials.Material;
import com.teamacronymcoders.base.materialsystem.materials.MaterialBuilder;
import com.teamacronymcoders.base.materialsystem.parttype.OrePartType;
import com.teamacronymcoders.base.modulesystem.Module;
import com.teamacronymcoders.base.modulesystem.ModuleBase;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;

@Module(value = SteamAgeRevolution.MODID)
public class ModuleMaterials extends ModuleBase {

	String[] vanillaParts = new String[] {"ore", "plate", "dust", "fluid"};
	String[] metalParts = new String[] {"ore", "ingot", "nugget", "plate", "dust", "block", "fluid"};
	// String[] alloyParts = new String[] {"ingot", "nugget", "plate", "dust", "block", "fluid"};

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		try {
			MaterialUser SAR = SteamAgeRevolution.instance.getMaterialUser();

			Material iron = new MaterialBuilder().setName("Iron").setColor(Color.GRAY).setHasEffect(false).build();
			Material gold = new MaterialBuilder().setName("Gold").setColor(Color.YELLOW).setHasEffect(false).build();

			Material copper = new MaterialBuilder().setName("Copper").setColor(new Color(159, 61, 19))
					.setHasEffect(false).build();
			Material zinc = new MaterialBuilder().setName("Zinc").setColor(new Color(133, 153, 154)).setHasEffect(false)
					.build();

			Material steel =
					new MaterialBuilder().setName("Steel").setColor(Color.DARK_GRAY).setHasEffect(false).build();
			Material brass = new MaterialBuilder().setName("Brass").setColor(Color.ORANGE).setHasEffect(false).build();

			for(MaterialPart part : SAR.registerPartsForMaterial(iron, vanillaParts)) {
				if(part.getPartType() instanceof OrePartType) {
					MaterialPartData data = part.getData();
					data.addDataValue("variants", "gravel,sand");
				}
			}
			for(MaterialPart part : SAR.registerPartsForMaterial(gold, vanillaParts)) {
				if(part.getPartType() instanceof OrePartType) {
					MaterialPartData data = part.getData();
					data.addDataValue("variants", "gravel,sand");
				}
			}
			for(MaterialPart part : SAR.registerPartsForMaterial(copper, metalParts)) {
				if(part.getPartType() instanceof OrePartType) {
					MaterialPartData data = part.getData();
					data.addDataValue("variants", "stone,gravel,sand");
				}
			}
			for(MaterialPart part : SAR.registerPartsForMaterial(zinc, metalParts)) {
				if(part.getPartType() instanceof OrePartType) {
					MaterialPartData data = part.getData();
					data.addDataValue("variants", "stone,gravel,sand");
				}
			}
			for(MaterialPart part : SAR.registerPartsForMaterial(steel, metalParts)) {
				if(part.getPartType() instanceof OrePartType) {
					MaterialPartData data = part.getData();
					data.addDataValue("variants", "sand");
				}
			}
			for(MaterialPart part : SAR.registerPartsForMaterial(brass, metalParts)) {
				if(part.getPartType() instanceof OrePartType) {
					MaterialPartData data = part.getData();
					data.addDataValue("variants", "sand");
				}
			}
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
