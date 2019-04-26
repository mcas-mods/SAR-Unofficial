package xyz.brassgoggledcoders.steamagerevolution.modules.materials;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

import com.google.common.collect.Lists;
import com.teamacronymcoders.base.Base;
import com.teamacronymcoders.base.items.ItemBase;
import com.teamacronymcoders.base.materialsystem.*;
import com.teamacronymcoders.base.materialsystem.materials.Material;
import com.teamacronymcoders.base.materialsystem.materials.MaterialBuilder;
import com.teamacronymcoders.base.materialsystem.parts.PartBuilder;
import com.teamacronymcoders.base.materialsystem.parttype.ItemPartType;
import com.teamacronymcoders.base.materialsystem.parttype.PartType;
import com.teamacronymcoders.base.modulesystem.Module;
import com.teamacronymcoders.base.modulesystem.ModuleBase;
import com.teamacronymcoders.base.registrysystem.BlockRegistry;
import com.teamacronymcoders.base.registrysystem.ItemRegistry;
import com.teamacronymcoders.base.registrysystem.config.ConfigRegistry;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.oredict.OreDictionary;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.HeavyOrePartType;

@ObjectHolder(SteamAgeRevolution.MODID)
@Module(value = SteamAgeRevolution.MODID)
@EventBusSubscriber(modid = SteamAgeRevolution.MODID)
public class ModuleMaterials extends ModuleBase {

	public static final Block charcoal_block = null;
	public static final Item charcoal_powder = null;
	public static Color brassColor = new Color(251, 194, 99);
	String[] vanillaParts = new String[] { "ore", "heavy_ore", "plate", "dust", "molten", "crushed_ore", "solution",
			"crystal", "lattice" };
	String[] metalParts = new String[] { "ore", "heavy_ore", "crushed_ore", "solution", "crystal", "ingot", "nugget",
			"plate", "dust", "block", "molten", "lattice" };
	String[] alloyParts = Arrays.copyOfRange(metalParts, 6, metalParts.length);
	String[] compatParts = new String[] { "heavy_ore", "crushed_ore", "solution", "crystal", "lattice" };

	public static List<String> knownMetalTypes = Lists.newArrayList();

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		try {
			MaterialUser SAR = SteamAgeRevolution.instance.getMaterialUser();

			PartType heavyOre = new HeavyOrePartType();
			MaterialSystem.registerPartType(heavyOre);
			registerPart("Heavy Ore", heavyOre);

			registerPart("Lattice", MaterialSystem.getPartType("block"));

			new PartBuilder().setOwnerId(SAR.getId()).setName("Solution").setOreDictName("solution")
					.setPartType(MaterialSystem.getPartType("fluid")).build();

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
			// TODO Can I make this a default property of the Part?
			MaterialSystem.getMaterialPart("iron_ore_rock").getData().addDataValue(ItemPartType.STACKSIZE_DATA_NAME,
					"1");
			MaterialSystem.getMaterialPart("iron_lattice").getData().addDataValue("transparent", "true");

			SAR.registerPartsForMaterial(gold, vanillaParts);
			MaterialSystem.getMaterialPart("gold_ore").getData().addDataValue("variants", "gravel,sand");
			MaterialSystem.getMaterialPart("gold_ore_rock").getData().addDataValue(ItemPartType.STACKSIZE_DATA_NAME,
					"1");
			MaterialSystem.getMaterialPart("gold_lattice").getData().addDataValue("transparent", "true");

			SAR.registerPartsForMaterial(copper, metalParts);
			MaterialSystem.getMaterialPart("copper_ore").getData().addDataValue("variants", "stone,gravel,sand");
			MaterialSystem.getMaterialPart("copper_ore_rock").getData().addDataValue(ItemPartType.STACKSIZE_DATA_NAME,
					"1");
			MaterialSystem.getMaterialPart("copper_lattice").getData().addDataValue("transparent", "true");

			SAR.registerPartsForMaterial(zinc, metalParts);
			MaterialSystem.getMaterialPart("zinc_ore").getData().addDataValue("variants", "stone,gravel,sand");
			MaterialSystem.getMaterialPart("zinc_ore_rock").getData().addDataValue(ItemPartType.STACKSIZE_DATA_NAME,
					"1");
			MaterialSystem.getMaterialPart("zinc_lattice").getData().addDataValue("transparent", "true");

			SAR.registerPartsForMaterial(steel, alloyParts);
			MaterialSystem.getMaterialPart("steel_ore_rock").getData().addDataValue(ItemPartType.STACKSIZE_DATA_NAME,
					"1");
			MaterialSystem.getMaterialPart("steel_lattice").getData().addDataValue("transparent", "true");

			SAR.registerPartsForMaterial(brass, alloyParts);
			MaterialSystem.getMaterialPart("brass_ore_rock").getData().addDataValue(ItemPartType.STACKSIZE_DATA_NAME,
					"1");
			MaterialSystem.getMaterialPart("brass_lattice").getData().addDataValue("transparent", "true");

			// TODO Do this for all 'known metals'
			// Only register when present in oredict
			SAR.registerPartsForMaterial(tin, compatParts);
			MaterialSystem.getMaterialPart("tin_ore_rock").getData().addDataValue(ItemPartType.STACKSIZE_DATA_NAME,
					"1");
			MaterialSystem.getMaterialPart("tin_lattice").getData().addDataValue("transparent", "true");
			SAR.registerPartsForMaterial(aluminum, compatParts);
			MaterialSystem.getMaterialPart("aluminum_ore_rock").getData().addDataValue(ItemPartType.STACKSIZE_DATA_NAME,
					"1");
			MaterialSystem.getMaterialPart("aluminum_lattice").getData().addDataValue("transparent", "true");
			SAR.registerPartsForMaterial(lead, compatParts);
			MaterialSystem.getMaterialPart("lead_ore_rock").getData().addDataValue(ItemPartType.STACKSIZE_DATA_NAME,
					"1");
			MaterialSystem.getMaterialPart("lead_lattice").getData().addDataValue("transparent", "true");
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
	public void postInit(FMLPostInitializationEvent event) {
		super.postInit(event);
		// SteamAgeRevolution.instance.getLogger().devInfo(knownMetalTypes.toString());
		// SteamAgeRevolution.instance.getRegistry(ConfigRegistry.class,
		// "CONFIG").updateEntry("knownMetals", String.join(",", knownMetalTypes));
	}

	@SubscribeEvent
	public static void onOreRegistered(OreDictionary.OreRegisterEvent event) {
		String name = event.getName();
		String[] splitName = name.split("(?=[A-Z])");
		if(splitName.length == 2) {
			if(splitName[0].equals("ingot")) {
				String metalType = splitName[1];
				if(!ModuleMaterials.knownMetalTypes.contains(metalType)) {
					ModuleMaterials.knownMetalTypes.add(metalType);
					SteamAgeRevolution.instance.getLogger().devInfo("Metal type detected: " + metalType);
				}
			}
		}
	}

	@Override
	public void configure(ConfigRegistry configRegistry) {
		// configRegistry.addEntry(new ConfigEntry("General", "autodetectMetals",
		// Type.BOOLEAN, "true", "Should SAR query the oredictionary for new metals and
		// setup recipes for them? "));
		// configRegistry.addEntry(new ConfigEntry("General", "knownMetals",
		// Type.STRING, "Iron,Gold",
		// "Ore Dictionary metals that SAR will attempt setup recipes and associated
		// items for.", false, true));
		// ModuleMaterials.knownMetalTypes =
		// Lists.newArrayList(SteamAgeRevolution.instance.getRegistry(ConfigRegistry.class,
		// "CONFIG").getEntry("knownMetals").getStringArray());
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

	private void registerPart(String name, PartType partType) {
		registerPart(new PartBuilder().setOwnerId(this.getMod().getID()).setName(name).setPartType(partType));
	}

	private void registerPart(PartBuilder partBuilder) {
		try {
			partBuilder.build();
		}
		catch(MaterialException e) {
			Base.instance.getLogger().getLogger().error(e);
		}
	}
}
