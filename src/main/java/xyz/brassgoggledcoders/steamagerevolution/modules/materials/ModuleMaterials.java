package xyz.brassgoggledcoders.steamagerevolution.modules.materials;

import java.awt.Color;

import com.teamacronymcoders.base.blocks.BlockBase;
import com.teamacronymcoders.base.items.ItemBase;
import com.teamacronymcoders.base.materialsystem.MaterialException;
import com.teamacronymcoders.base.materialsystem.MaterialUser;
import com.teamacronymcoders.base.materialsystem.materialparts.MaterialPart;
import com.teamacronymcoders.base.materialsystem.materials.Material;
import com.teamacronymcoders.base.materialsystem.materials.MaterialBuilder;
import com.teamacronymcoders.base.materialsystem.partdata.MaterialPartData;
import com.teamacronymcoders.base.materialsystem.parttype.OrePartType;
import com.teamacronymcoders.base.modulesystem.Module;
import com.teamacronymcoders.base.modulesystem.ModuleBase;
import com.teamacronymcoders.base.registrysystem.BlockRegistry;
import com.teamacronymcoders.base.registrysystem.ItemRegistry;
import com.teamacronymcoders.base.registrysystem.config.ConfigRegistry;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.oredict.OreDictionary;
import scala.actors.threadpool.Arrays;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;

@ObjectHolder(SteamAgeRevolution.MODID)
@Module(value = SteamAgeRevolution.MODID)
public class ModuleMaterials extends ModuleBase {

	public static final Block charcoal_block = null;
	public static final Item charcoal_powder = null;

	String[] vanillaParts = new String[] {"ore", "plate", "dust", "molten"};
	String[] metalParts = new String[] {"ore", "ingot", "nugget", "plate", "dust", "block", "molten"};
	String[] alloyParts = (String[]) Arrays.copyOfRange(metalParts, 1, metalParts.length);
	public static Color brassColor = new Color(251, 194, 99);

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
			Material brass = new MaterialBuilder().setName("Brass").setColor(brassColor).setHasEffect(false).build();

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
			for(MaterialPart part : SAR.registerPartsForMaterial(steel, alloyParts)) {
				if(part.getPartType() instanceof OrePartType) {
					MaterialPartData data = part.getData();
					data.addDataValue("variants", "sand");
				}
			}
			for(MaterialPart part : SAR.registerPartsForMaterial(brass, alloyParts)) {
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
		super.preInit(event);
	}

	@Override
	public void init(FMLInitializationEvent event) {
		// TODO
		// FurnaceRecipes.instance().addSmeltingRecipe(OreDictUtils.getPreferredItemStack("dustBrass"),
		// OreDictUtils.getPreferredItemStack("ingotBrass"), 0.7f);
		OreDictionary.registerOre("blockCharcoal", charcoal_block);
		OreDictionary.registerOre("dustCharcoal", charcoal_powder);
		super.init(event);
	}

	@Override
	public void registerBlocks(ConfigRegistry configRegistry, BlockRegistry blockRegistry) {
		blockRegistry.register(new BlockBase(net.minecraft.block.material.Material.ROCK, "charcoal_block"));
	}

	@Override
	public void registerItems(ConfigRegistry configRegistry, ItemRegistry itemRegistry) {
		itemRegistry.register(new ItemBase("charcoal_powder"));
	}

	@Override
	public String getName() {
		return "Materials";
	}
}
