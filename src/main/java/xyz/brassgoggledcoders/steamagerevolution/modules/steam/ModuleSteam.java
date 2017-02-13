package xyz.brassgoggledcoders.steamagerevolution.modules.steam;

import com.teamacronymcoders.base.blocks.BlockFluidBase;
import com.teamacronymcoders.base.items.ItemBase;
import com.teamacronymcoders.base.modulesystem.Module;
import com.teamacronymcoders.base.modulesystem.ModuleBase;
import com.teamacronymcoders.base.registry.BlockRegistry;
import com.teamacronymcoders.base.registry.ItemRegistry;
import com.teamacronymcoders.base.registry.config.ConfigRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.blocks.BlockCastingBench;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.blocks.BlockDropHammer;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.blocks.BlockDropHammerAnvil;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.boiler.BlockBoilerController;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.boiler.BlockBrassFrame;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.boiler.BlockSolidFirebox;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.boiler.BlockSteamOutput;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.boiler.BlockWaterInput;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.furnace.BlockFurnaceCasing;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.furnace.BlockFurnaceItemInput;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.furnace.BlockFurnaceItemOutput;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.furnace.BlockFurnaceMonitor;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.furnace.BlockFurnaceSteamInput;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.furnace.SteamFurnaceRecipes;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.smeltery.BlockSmelteryCoalInput;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.smeltery.BlockSmelteryController;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.smeltery.BlockSmelteryFrame;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.smeltery.BlockSmelteryIronInput;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.smeltery.BlockSmelterySteelOutput;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.tileentities.DropHammerRecipes;

@Module(value = SteamAgeRevolution.MODID)
public class ModuleSteam extends ModuleBase {

	public static Fluid steam;
	public static BlockFluidClassic steamBlock;

	public static Block brassCasing, waterInput, solidFirebox, frictionFirebox, steamOutput, boilerController;
	public static Block steamTurbine, mechanicalOutput, steamInput, turbineFrame;
	public static Block furnaceCasing, furnaceItemInput, furnaceItemOutput, furnaceSteamInput, furnaceMonitor;
	public static Block smelteryController, itemInput, smelteryFrame, smelteryCoalInput, smelteryIronInput,
			smelterySteelOutput;
	public static Block castingBench;
	public static Block dropHammer, dropHammerAnvil;

	public static Item charcoalPowder;

	@Override
	public String getName() {
		return "Steam";
	}

	@Override
	public String getClientProxyPath() {
		return "xyz.brassgoggledcoders.steamagerevolution.modules.steam.ClientProxy";
	}

	@Override
	public void init(FMLInitializationEvent event) {
		SteamFurnaceRecipes.registerRecipes();
		DropHammerRecipes.registerRecipes();
		GameRegistry.addRecipe(
				new ShapedOreRecipe(new ItemStack(Items.COAL, 1, 1), "PPP", "PPP", "PPP", 'P', "dustCharcoal"));
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
			steamBlock = new BlockFluidBase("steam", steam, Material.LAVA);
			blockRegistry.register(steamBlock);
		}

		brassCasing = new BlockBrassFrame(Material.IRON, "brass_frame");
		blockRegistry.register(brassCasing);
		FMLInterModComms.sendMessage("chisel", "variation:add", "brass_frame|SteamAgeRevolution:brass_frame|0");
		waterInput = new BlockWaterInput(Material.IRON, "water_input");
		blockRegistry.register(waterInput);
		steamOutput = new BlockSteamOutput(Material.IRON, "steam_output");
		blockRegistry.register(steamOutput);
		solidFirebox = new BlockSolidFirebox(Material.IRON, "solid_firebox");
		blockRegistry.register(solidFirebox);
		// frictionFirebox = new BlockFrictionFirebox(Material.IRON, "friction_firebox");
		// blockRegistry.register(frictionFirebox);
		boilerController = new BlockBoilerController(Material.IRON, "boiler_controller");
		blockRegistry.register(boilerController);

		smelteryController = new BlockSmelteryController(Material.ANVIL, "smeltery_controller");
		// blockRegistry.register(smelteryController);
		smelteryFrame = new BlockSmelteryFrame(Material.ROCK, "smeltery_frame");
		blockRegistry.register(smelteryFrame);
		smelteryCoalInput = new BlockSmelteryCoalInput(Material.ROCK, "smeltery_coalinput");
		blockRegistry.register(smelteryCoalInput);
		smelteryIronInput = new BlockSmelteryIronInput(Material.ROCK, "smeltery_ironinput");
		blockRegistry.register(smelteryIronInput);
		smelterySteelOutput = new BlockSmelterySteelOutput(Material.ROCK, "smeltery_steeloutput");
		blockRegistry.register(smelterySteelOutput);

		furnaceCasing = new BlockFurnaceCasing(Material.IRON, "furnace_casing");
		blockRegistry.register(furnaceCasing);
		furnaceItemInput = new BlockFurnaceItemInput(Material.IRON, "furnace_iteminput");
		blockRegistry.register(furnaceItemInput);
		furnaceItemOutput = new BlockFurnaceItemOutput(Material.IRON, "furnace_itemoutput");
		blockRegistry.register(furnaceItemOutput);
		furnaceSteamInput = new BlockFurnaceSteamInput(Material.IRON, "furnace_steaminput");
		blockRegistry.register(furnaceSteamInput);
		furnaceMonitor = new BlockFurnaceMonitor(Material.IRON, "furnace_monitor");
		blockRegistry.register(furnaceMonitor);

		// steamTurbine = new BlockTurbine(Material.IRON, "steam_turbine");
		// blockRegistry.register(steamTurbine);
		// mechanicalOutput = new BlockMechanicalOutput(Material.IRON, "mechanical_output");
		// blockRegistry.register(mechanicalOutput);
		// steamInput = new BlockSteamInput(Material.IRON, "steam_input");
		// blockRegistry.register(steamInput);
		// turbineFrame = new BlockTurbineFrame(Material.IRON, "turbine_frame");
		// blockRegistry.register(turbineFrame);

		castingBench = new BlockCastingBench(Material.ANVIL, "casting_bench");
		blockRegistry.register(castingBench);

		dropHammer = new BlockDropHammer(Material.IRON, "drop_hammer");
		blockRegistry.register(dropHammer);
		dropHammerAnvil = new BlockDropHammerAnvil(Material.IRON, "drop_hammer_anvil");
		blockRegistry.register(dropHammerAnvil);
	}

	@Override
	public void registerItems(ConfigRegistry configRegistry, ItemRegistry itemRegistry) {
		charcoalPowder = new ItemBase("charcoal_powder");
		itemRegistry.register(charcoalPowder);
	}
}