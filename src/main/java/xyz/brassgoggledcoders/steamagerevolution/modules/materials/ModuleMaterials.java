package xyz.brassgoggledcoders.steamagerevolution.modules.materials;

import java.awt.Color;
import java.util.Arrays;

import com.teamacronymcoders.base.items.ItemBase;
import com.teamacronymcoders.base.materialsystem.MaterialException;
import com.teamacronymcoders.base.materialsystem.MaterialSystem;
import com.teamacronymcoders.base.materialsystem.MaterialUser;
import com.teamacronymcoders.base.materialsystem.materials.Material;
import com.teamacronymcoders.base.materialsystem.materials.MaterialBuilder;
import com.teamacronymcoders.base.materialsystem.parts.PartBuilder;
import com.teamacronymcoders.base.materialsystem.parttype.ItemPartType;
import com.teamacronymcoders.base.modulesystem.Module;
import com.teamacronymcoders.base.modulesystem.ModuleBase;
import com.teamacronymcoders.base.registrysystem.BlockRegistry;
import com.teamacronymcoders.base.registrysystem.ItemRegistry;
import com.teamacronymcoders.base.registrysystem.config.ConfigRegistry;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.oredict.OreDictionary;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;

@ObjectHolder(SteamAgeRevolution.MODID)
@Module(value = SteamAgeRevolution.MODID)
@EventBusSubscriber(modid = SteamAgeRevolution.MODID)
public class ModuleMaterials extends ModuleBase {

	public static final Block charcoal_block = null;
	public static final Item charcoal_powder = null;
	public static Color brassColor = new Color(251, 194, 99);
	String[] vanillaParts = new String[] { "ore", "plate", "dust", "molten", "crushed_ore", "solution", "crystal", "ore_rock" };
	String[] metalParts = new String[] { "ore", "crushed_ore", "ore_rock", "solution", "crystal", "ingot", "nugget", "plate",
			"dust", "block", "molten" };
	String[] alloyParts = Arrays.copyOfRange(metalParts, 5, metalParts.length);
	String[] compatParts = new String[] { "crushed_ore", "solution", "crystal", "ore_rock" };

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		try {
			MaterialUser SAR = SteamAgeRevolution.instance.getMaterialUser();

			new PartBuilder().setOwnerId(SAR.getId()).setName("Solution").setOreDictName("solution")
					.setPartType(MaterialSystem.getPartType("fluid")).build();

			new PartBuilder().setOwnerId(SAR.getId()).setName("Ore Rock").setOreDictName("rock").setPartType(MaterialSystem.getPartType("item")).setOverlay(true).build();

			
			Material iron = new MaterialBuilder().setName("Iron").setColor(Color.GRAY).setHasEffect(false).build();
			Material gold = new MaterialBuilder().setName("Gold").setColor(Color.YELLOW).setHasEffect(false).build();

			Material copper = new MaterialBuilder().setName("Copper").setColor(new Color(159, 61, 19))
					.setHasEffect(false).build();
			Material zinc = new MaterialBuilder().setName("Zinc").setColor(new Color(133, 153, 154)).setHasEffect(false)
					.build();

			Material steel = new MaterialBuilder().setName("Steel").setColor(Color.DARK_GRAY).setHasEffect(false)
					.build();
			Material brass = new MaterialBuilder().setName("Brass").setColor(brassColor).setHasEffect(false).build();

			Material sulphur = new MaterialBuilder().setName("Sulphur").setColor(new Color(200, 200, 60))
					.setHasEffect(false).build();

			Material tin = new MaterialBuilder().setName("Tin").setColor(Color.LIGHT_GRAY).setHasEffect(false).build();
			Material aluminum = new MaterialBuilder().setName("Aluminum").setColor(Color.WHITE).setHasEffect(false)
					.build();
			Material lead = new MaterialBuilder().setName("Lead").setColor(Color.BLACK).setHasEffect(false).build();

			SAR.registerPartsForMaterial(sulphur, "ore", "dust", "crystal");
			MaterialSystem.getMaterialPart("sulphur_crystal").getData().addDataValue("burn", "1800");
			MaterialSystem.getMaterialPart("sulphur_ore").getData().addDataValue("drops", "oredict:crystalSulphur");

			SAR.registerPartsForMaterial(iron, vanillaParts);
			MaterialSystem.getMaterialPart("iron_ore").getData().addDataValue("variants", "gravel,sand");
			//TODO Can I make this a default property of the Part?
			MaterialSystem.getMaterialPart("iron_ore_rock").getData().addDataValue(ItemPartType.STACKSIZE_DATA_NAME, "1");

			SAR.registerPartsForMaterial(gold, vanillaParts);
			MaterialSystem.getMaterialPart("gold_ore").getData().addDataValue("variants", "gravel,sand");
			MaterialSystem.getMaterialPart("gold_ore_rock").getData().addDataValue(ItemPartType.STACKSIZE_DATA_NAME, "1");

			SAR.registerPartsForMaterial(copper, metalParts);
			MaterialSystem.getMaterialPart("copper_ore").getData().addDataValue("variants", "stone,gravel,sand");
			MaterialSystem.getMaterialPart("copper_ore_rock").getData().addDataValue(ItemPartType.STACKSIZE_DATA_NAME, "1");

			SAR.registerPartsForMaterial(zinc, metalParts);
			MaterialSystem.getMaterialPart("zinc_ore").getData().addDataValue("variants", "stone,gravel,sand");
			MaterialSystem.getMaterialPart("zinc_ore_rock").getData().addDataValue(ItemPartType.STACKSIZE_DATA_NAME, "1");

			SAR.registerPartsForMaterial(steel, alloyParts);
			MaterialSystem.getMaterialPart("steel_ore_rock").getData().addDataValue(ItemPartType.STACKSIZE_DATA_NAME, "1");

			SAR.registerPartsForMaterial(brass, alloyParts);
			MaterialSystem.getMaterialPart("brass_ore_rock").getData().addDataValue(ItemPartType.STACKSIZE_DATA_NAME, "1");

			//TODO Do this for all 'known metals'
			SAR.registerPartsForMaterial(tin, compatParts);
			MaterialSystem.getMaterialPart("tin_ore_rock").getData().addDataValue(ItemPartType.STACKSIZE_DATA_NAME, "1");
			SAR.registerPartsForMaterial(aluminum, compatParts);
			MaterialSystem.getMaterialPart("aluminum_ore_rock").getData().addDataValue(ItemPartType.STACKSIZE_DATA_NAME, "1");
			SAR.registerPartsForMaterial(lead, compatParts);
			MaterialSystem.getMaterialPart("lead_ore_rock").getData().addDataValue(ItemPartType.STACKSIZE_DATA_NAME, "1");
		}
		catch(MaterialException e) {
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
		blockRegistry.register(new BlockCharcoal());
	}

	@Override
	public void registerItems(ConfigRegistry configRegistry, ItemRegistry itemRegistry) {
		itemRegistry.register(new ItemBase("charcoal_powder"));
	}

	@Override
	public String getClientProxyPath() {
		return "xyz.brassgoggledcoders.steamagerevolution.modules.materials.ClientProxy";
	}

	@Override
	public String getName() {
		return "Materials";
	}
}
