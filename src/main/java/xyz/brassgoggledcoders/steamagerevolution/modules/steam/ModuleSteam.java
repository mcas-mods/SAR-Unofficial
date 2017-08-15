package xyz.brassgoggledcoders.steamagerevolution.modules.steam;

import java.util.ArrayList;
import java.util.List;

import com.teamacronymcoders.base.blocks.BlockFluidBase;
import com.teamacronymcoders.base.items.ItemBase;
import com.teamacronymcoders.base.modulesystem.Module;
import com.teamacronymcoders.base.modulesystem.ModuleBase;
import com.teamacronymcoders.base.registrysystem.BlockRegistry;
import com.teamacronymcoders.base.registrysystem.ItemRegistry;
import com.teamacronymcoders.base.registrysystem.config.ConfigEntry;
import com.teamacronymcoders.base.registrysystem.config.ConfigRegistry;
import com.teamacronymcoders.base.util.OreDictUtils;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Property.Type;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.blocks.BlockCastingBench;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.blocks.BlockSteamVent;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.items.ItemDie;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.alloyfurnace.AlloyFurnaceRecipe;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.alloyfurnace.blocks.BlockAlloyFurnaceController;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.alloyfurnace.blocks.BlockAlloyFurnaceFluidOutput;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.alloyfurnace.blocks.BlockAlloyFurnaceFrame;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.alloyfurnace.blocks.BlockAlloyFurnacePrimaryFluidInput;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.alloyfurnace.blocks.BlockAlloyFurnaceSecondaryFluidInput;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.boiler.blocks.BlockBoilerCasing;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.boiler.blocks.BlockBoilerLiquidFirebox;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.boiler.blocks.BlockBoilerPressureMonitor;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.boiler.blocks.BlockBoilerPressureValve;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.boiler.blocks.BlockBoilerSolidFirebox;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.boiler.blocks.BlockBoilerSteamGauge;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.boiler.blocks.BlockBoilerSteamOutput;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.boiler.blocks.BlockBoilerWaterGauge;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.boiler.blocks.BlockBoilerWaterInput;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.crucible.MoltenMetalRecipe;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.crucible.blocks.BlockCrucibleCasing;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.crucible.blocks.BlockCrucibleFluidOutput;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.crucible.blocks.BlockCrucibleItemInput;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.crucible.blocks.BlockCrucibleSteamInput;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.furnace.SteamFurnaceRecipe;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.furnace.blocks.BlockFurnaceCasing;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.furnace.blocks.BlockFurnaceItemInput;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.furnace.blocks.BlockFurnaceItemOutput;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.furnace.blocks.BlockFurnaceModeToggle;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.furnace.blocks.BlockFurnaceSteamInput;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.hammer.SteamHammerRecipe;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.hammer.blocks.BlockSteamHammerAnvil;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.hammer.blocks.BlockSteamHammerFrame;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.hammer.blocks.BlockSteamHammerHammer;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.hammer.blocks.BlockSteamHammerShielding;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.kiln.BlockKilnDoor;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.kiln.BlockKilnFrame;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.kiln.BlockKilnSteamInput;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.kiln.BlockKilnWindow;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.steelworks.BlockSteelworksCarbonInput;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.steelworks.BlockSteelworksFrame;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.steelworks.BlockSteelworksIronInput;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.steelworks.BlockSteelworksSteamInput;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.steelworks.BlockSteelworksSteelOutput;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.tileentities.TileEntityCastingBench;
import xyz.brassgoggledcoders.steamagerevolution.utils.RecipesIngotToPlate;
import xyz.brassgoggledcoders.steamagerevolution.utils.RecipesOreToDust;

@Module(value = SteamAgeRevolution.MODID)
public class ModuleSteam extends ModuleBase {

	public static Fluid steam;
	public static BlockFluidBase steamBlock;

	public static Block boilerCasing, boilerWaterInput, boilerSolidFirebox, boilerLiquidFirebox, boilerSteamOutput,
			boilerWaterGauge, boilerSteamGauge, boilerPressureMonitor, boilerPressureValve;
	public static Block steamTurbine, mechanicalOutput, steamInput, turbineFrame;
	public static Block furnaceCasing, furnaceItemInput, furnaceItemOutput, furnaceSteamInput, furnaceMonitor;
	public static Block alloyFurnaceController, alloyFurnaceFrame, alloyFurnacePrimaryFluidInput,
			alloyFurnaceSecondaryFluidInput, alloyFurnaceFluidOutput;
	public static Block castingBench;
	public static Block crucibleCasing, crucibleItemInput, crucibleSteamInput, crucibleFluidOutput;
	public static Block steamhammerHammer, steamhammerAnvil, steamhammerFrame, steamhammerShielding;
	public static Block steamVent;

	public static Item charcoalPowder, die;

	public static List<String> knownMetalTypes = new ArrayList<String>();

	public static DamageSource hammer =
			new DamageSource("hammer").setDifficultyScaled().setDamageBypassesArmor().setDamageIsAbsolute();

	public static boolean enableDestruction;

	@Override
	public String getName() {
		return "Steam";
	}

	@Override
	public String getClientProxyPath() {
		return "xyz.brassgoggledcoders.steamagerevolution.modules.steam.ClientProxy";
	}

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		this.getConfigRegistry().addEntry("enableDestruction",
				new ConfigEntry("general", "enableDestruction", Type.BOOLEAN, "false"));
		enableDestruction = this.getConfigRegistry().getBoolean("enableDestruction", false);
		MinecraftForge.EVENT_BUS.register(this);
		super.preInit(event);
	}

	@Override
	public void init(FMLInitializationEvent event) {
		for(ItemStack cooked : OreDictionary.getOres("listAllmeatcooked")) {
			SteamFurnaceRecipe.addSteamFurnaceRecipe(cooked, new ItemStack(ModuleSteam.charcoalPowder));
		}

		SteamHammerRecipe.addSteamHammerRecipe(new ItemStack(Blocks.STONE), new ItemStack(Blocks.COBBLESTONE));
		SteamHammerRecipe.addSteamHammerRecipe(new ItemStack(Blocks.COBBLESTONE), new ItemStack(Blocks.GRAVEL));
		SteamHammerRecipe.addSteamHammerRecipe(new ItemStack(Blocks.GRAVEL), new ItemStack(Blocks.SAND));

		SteamHammerRecipe.addSteamHammerRecipe(new ItemStack(Blocks.DIRT), new ItemStack(Items.DIAMOND), "test");

		AlloyFurnaceRecipe.addAlloyFurnaceRecipe(
				FluidRegistry.getFluidStack("copper", TileEntityCastingBench.VALUE_INGOT),
				FluidRegistry.getFluidStack("zinc", TileEntityCastingBench.VALUE_INGOT),
				FluidRegistry.getFluidStack("brass", TileEntityCastingBench.VALUE_INGOT));

		OreDictionary.registerOre("dustCharcoal", charcoalPowder);
		super.init(event);
	}

	@Override
	public void registerBlocks(ConfigRegistry configRegistry, BlockRegistry blockRegistry) {

		// TODO Adjust properties
		steam = new Fluid("steam", new ResourceLocation(SteamAgeRevolution.MODID, "blocks/steam"),
				new ResourceLocation(SteamAgeRevolution.MODID, "blocks/steam_flow")).setGaseous(true)
						.setTemperature(1000).setViscosity(200);

		if(!(FluidRegistry.isFluidRegistered(steam))) { // Soft registration
			FluidRegistry.registerFluid(steam);
			FluidRegistry.addBucketForFluid(steam);
		}
		steamBlock = new BlockFluidBase("steam", steam, Material.LAVA);
		blockRegistry.register(steamBlock);

		boilerCasing = new BlockBoilerCasing(Material.IRON, "boiler_casing");
		blockRegistry.register(boilerCasing);
		boilerWaterInput = new BlockBoilerWaterInput(Material.IRON, "boiler_water_input");
		blockRegistry.register(boilerWaterInput);
		boilerSteamOutput = new BlockBoilerSteamOutput(Material.IRON, "boiler_steam_output");
		blockRegistry.register(boilerSteamOutput);
		boilerSolidFirebox = new BlockBoilerSolidFirebox(Material.IRON, "boiler_solid_firebox");
		blockRegistry.register(boilerSolidFirebox);
		boilerLiquidFirebox = new BlockBoilerLiquidFirebox(Material.IRON, "boiler_liquid_firebox");
		blockRegistry.register(boilerLiquidFirebox);
		boilerWaterGauge = new BlockBoilerWaterGauge(Material.IRON, "boiler_water_gauge");
		blockRegistry.register(boilerWaterGauge);
		boilerSteamGauge = new BlockBoilerSteamGauge(Material.IRON, "boiler_steam_gauge");
		blockRegistry.register(boilerSteamGauge);
		boilerPressureMonitor = new BlockBoilerPressureMonitor(Material.IRON, "boiler_pressuremonitor");
		blockRegistry.register(boilerPressureMonitor);
		boilerPressureValve = new BlockBoilerPressureValve(Material.IRON, "boiler_pressurevalve");
		blockRegistry.register(boilerPressureValve);

		alloyFurnaceController = new BlockAlloyFurnaceController(Material.ANVIL, "alloy_furnace_controller");
		// blockRegistry.register(alloyFurnaceController);
		alloyFurnaceFrame = new BlockAlloyFurnaceFrame(Material.ROCK, "alloy_furnace_frame");
		blockRegistry.register(alloyFurnaceFrame);
		alloyFurnacePrimaryFluidInput =
				new BlockAlloyFurnacePrimaryFluidInput(Material.ROCK, "alloy_furnace_primary_fluid_input");
		blockRegistry.register(alloyFurnacePrimaryFluidInput);
		alloyFurnaceSecondaryFluidInput =
				new BlockAlloyFurnaceSecondaryFluidInput(Material.ROCK, "alloy_furnace_secondary_fluid_input");
		blockRegistry.register(alloyFurnaceSecondaryFluidInput);
		alloyFurnaceFluidOutput = new BlockAlloyFurnaceFluidOutput(Material.ROCK, "alloy_furnace_fluid_output");
		blockRegistry.register(alloyFurnaceFluidOutput);

		blockRegistry.register(new BlockSteelworksFrame(Material.ROCK, "steelworks_frame"));
		blockRegistry.register(new BlockSteelworksIronInput(Material.ROCK, "steelworks_iron_input"));
		blockRegistry.register(new BlockSteelworksCarbonInput(Material.ROCK, "steelworks_carbon_input"));
		blockRegistry.register(new BlockSteelworksSteamInput(Material.ROCK, "steelworks_steam_input"));
		blockRegistry.register(new BlockSteelworksSteelOutput(Material.ROCK, "steelworks_steel_output"));

		blockRegistry.register(new BlockKilnFrame(Material.ROCK, "kiln_frame"));
		blockRegistry.register(new BlockKilnSteamInput(Material.ROCK, "kiln_steam_input"));
		blockRegistry.register(new BlockKilnWindow(Material.GLASS, "kiln_window"));
		blockRegistry.register(new BlockKilnDoor(Material.IRON, "kiln_door"));

		furnaceCasing = new BlockFurnaceCasing(Material.IRON, "furnace_casing");
		blockRegistry.register(furnaceCasing);
		furnaceItemInput = new BlockFurnaceItemInput(Material.IRON, "furnace_iteminput");
		blockRegistry.register(furnaceItemInput);
		furnaceItemOutput = new BlockFurnaceItemOutput(Material.IRON, "furnace_itemoutput");
		blockRegistry.register(furnaceItemOutput);
		furnaceSteamInput = new BlockFurnaceSteamInput(Material.IRON, "furnace_steaminput");
		blockRegistry.register(furnaceSteamInput);
		furnaceMonitor = new BlockFurnaceModeToggle(Material.IRON, "furnace_monitor");
		blockRegistry.register(furnaceMonitor);

		castingBench = new BlockCastingBench(Material.ANVIL, "casting_bench");
		blockRegistry.register(castingBench);

		crucibleCasing = new BlockCrucibleCasing(Material.IRON, "crucible_casing");
		blockRegistry.register(crucibleCasing);
		crucibleItemInput = new BlockCrucibleItemInput(Material.IRON, "crucible_item_input");
		blockRegistry.register(crucibleItemInput);
		crucibleSteamInput = new BlockCrucibleSteamInput(Material.IRON, "crucible_steam_input");
		blockRegistry.register(crucibleSteamInput);
		crucibleFluidOutput = new BlockCrucibleFluidOutput(Material.IRON, "crucible_fluid_output");
		blockRegistry.register(crucibleFluidOutput);

		steamhammerFrame = new BlockSteamHammerFrame(Material.IRON, "steamhammer_frame");
		blockRegistry.register(steamhammerFrame);
		steamhammerShielding = new BlockSteamHammerShielding(Material.GLASS, "steamhammer_shielding");
		blockRegistry.register(steamhammerShielding);
		steamhammerAnvil = new BlockSteamHammerAnvil(Material.ANVIL, "steamhammer_anvil");
		blockRegistry.register(steamhammerAnvil);
		steamhammerHammer = new BlockSteamHammerHammer(Material.IRON, "steamhammer_hammer");
		blockRegistry.register(steamhammerHammer);

		steamVent = new BlockSteamVent(Material.IRON, "steam_vent");
		blockRegistry.register(steamVent);
	}

	@Override
	public void registerItems(ConfigRegistry configRegistry, ItemRegistry itemRegistry) {
		charcoalPowder = new ItemBase("charcoal_powder");
		itemRegistry.register(charcoalPowder);
		die = new ItemDie();
		itemRegistry.register(die);
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		knownMetalTypes.add("Iron");
		knownMetalTypes.add("Gold");
		for(String metal : knownMetalTypes) {
			if(FluidRegistry.isFluidRegistered(metal)) {
				for(ItemStack metalBlock : OreDictionary.getOres("block" + metal, false)) {
					MoltenMetalRecipe.addMelting(metalBlock, FluidRegistry.getFluid(metal.toLowerCase()));
				}
			}
			for(ItemStack ingot : OreDictionary.getOres("ingot" + metal, false)) {
				if(OreDictionary.doesOreNameExist("plate" + metal)) {
					SteamHammerRecipe.addSteamHammerRecipe(ingot, OreDictUtils.getPreferredItemStack("plate" + metal));
				}
				if(OreDictionary.doesOreNameExist("gear" + metal)) {
					SteamHammerRecipe.addSteamHammerRecipe(ingot, OreDictUtils.getPreferredItemStack("gear" + metal),
							"gear");
				}
			}
			for(ItemStack ore : OreDictionary.getOres("ore" + metal, false)) {
				if(OreDictionary.doesOreNameExist("dust" + metal)) {
					SteamHammerRecipe.addSteamHammerRecipe(ore, OreDictUtils.getPreferredItemStack("dust" + metal));
				}
			}
		}
		super.postInit(event);
	}

	@SubscribeEvent
	public void registerRecipes(RegistryEvent.Register<IRecipe> event) {
		event.getRegistry().register(
				new RecipesOreToDust().setRegistryName(new ResourceLocation(SteamAgeRevolution.MODID, "ore_to_dust")));
		event.getRegistry().register(new RecipesIngotToPlate()
				.setRegistryName(new ResourceLocation(SteamAgeRevolution.MODID, "ingot_to_plate")));
	}

	@SubscribeEvent
	public void onOreRegistered(OreDictionary.OreRegisterEvent event) {
		String name = event.getName();
		String[] splitName = name.split("(?=[A-Z])");
		if(splitName.length == 2) {
			if(splitName[0].equals("ingot")) {
				String metalType = splitName[1];
				if(!knownMetalTypes.contains(metalType)) {
					knownMetalTypes.add(metalType);
					SteamAgeRevolution.instance.getLogger().devInfo("Metal type detected: " + metalType);
				}
			}
		}
	}
}