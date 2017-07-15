package xyz.brassgoggledcoders.steamagerevolution.modules.steam;

import com.teamacronymcoders.base.blocks.BlockFluidBase;
import com.teamacronymcoders.base.items.ItemBase;
import com.teamacronymcoders.base.modulesystem.Module;
import com.teamacronymcoders.base.modulesystem.ModuleBase;
import com.teamacronymcoders.base.registrysystem.BlockRegistry;
import com.teamacronymcoders.base.registrysystem.ItemRegistry;
import com.teamacronymcoders.base.registrysystem.config.ConfigRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.oredict.OreDictionary;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.blocks.BlockCastingBench;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.blocks.BlockSteamVent;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.alloyfurnace.AlloyFurnaceRecipe;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.alloyfurnace.blocks.BlockAlloyFurnaceController;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.alloyfurnace.blocks.BlockAlloyFurnaceFluidInput;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.alloyfurnace.blocks.BlockAlloyFurnaceFluidOutput;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.alloyfurnace.blocks.BlockAlloyFurnaceFrame;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.alloyfurnace.blocks.BlockAlloyFurnaceHardFrame;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.alloyfurnace.blocks.BlockAlloyFurnaceItemInput;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.boiler.blocks.BlockBoilerCasing;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.boiler.blocks.BlockBoilerController;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.boiler.blocks.BlockBoilerLiquidFirebox;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.boiler.blocks.BlockBoilerPressureMonitor;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.boiler.blocks.BlockBoilerPressureValve;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.boiler.blocks.BlockBoilerSolidFirebox;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.boiler.blocks.BlockBoilerSteamOutput;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.boiler.blocks.BlockBoilerWaterInput;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.furnace.BlockFurnaceCasing;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.furnace.BlockFurnaceItemInput;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.furnace.BlockFurnaceItemOutput;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.furnace.BlockFurnaceMonitor;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.furnace.BlockFurnaceSteamInput;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.furnace.SteamFurnaceRecipe;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.hammer.BlockSteamHammerAnvil;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.hammer.BlockSteamHammerFrame;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.hammer.BlockSteamHammerHammer;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.hammer.BlockSteamHammerShielding;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.hammer.SteamHammerRecipe;

@Module(value = SteamAgeRevolution.MODID)
public class ModuleSteam extends ModuleBase {

	public static Fluid steam;
	public static BlockFluidClassic steamBlock;

	public static Block boilerCasing, boilerWaterInput, boilerSolidFirebox, boilerLiquidFirebox, boilerSteamOutput,
			boilerMonitor, boilerPressureMonitor, boilerPressureValve;
	public static Block steamTurbine, mechanicalOutput, steamInput, turbineFrame;
	public static Block furnaceCasing, furnaceItemInput, furnaceItemOutput, furnaceSteamInput, furnaceMonitor;
	public static Block alloyFurnaceController, itemInput, alloyFurnaceFrame, alloyFurnaceHardFrame,
			alloyFurnaceCoalInput, alloyFurnaceIronInput, alloyFurnaceSteelOutput;
	public static Block castingBench;
	public static Block steamhammerHammer, steamhammerAnvil, steamhammerFrame, steamhammerShielding;
	public static Block steamVent;

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
		SteamFurnaceRecipe.addSteamFurnaceRecipe(new ItemStack(Items.COOKED_CHICKEN),
				new ItemStack(ModuleSteam.charcoalPowder));
		SteamFurnaceRecipe.addSteamFurnaceRecipe(new ItemStack(Items.COOKED_BEEF),
				new ItemStack(ModuleSteam.charcoalPowder));
		SteamFurnaceRecipe.addSteamFurnaceRecipe(new ItemStack(Items.COOKED_FISH),
				new ItemStack(ModuleSteam.charcoalPowder));
		SteamFurnaceRecipe.addSteamFurnaceRecipe(new ItemStack(Items.COOKED_MUTTON),
				new ItemStack(ModuleSteam.charcoalPowder));
		SteamFurnaceRecipe.addSteamFurnaceRecipe(new ItemStack(Items.COOKED_RABBIT),
				new ItemStack(ModuleSteam.charcoalPowder));

		SteamHammerRecipe.addSteamHammerRecipe(new ItemStack(Blocks.COBBLESTONE), new ItemStack(Blocks.GRAVEL));
		SteamHammerRecipe.addSteamHammerRecipe(new ItemStack(Blocks.DIRT), new ItemStack(Items.DIAMOND), "test");

		AlloyFurnaceRecipe.addUpgradedAlloyFurnaceRecipe(new FluidStack(FluidRegistry.WATER, Fluid.BUCKET_VOLUME),
				new FluidStack(FluidRegistry.LAVA, Fluid.BUCKET_VOLUME / 2));

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

		boilerCasing = new BlockBoilerCasing(Material.IRON, "brass_frame");
		blockRegistry.register(boilerCasing);
		FMLInterModComms.sendMessage("chisel", "variation:add", "brass_frame|SteamAgeRevolution:brass_frame|0");
		boilerWaterInput = new BlockBoilerWaterInput(Material.IRON, "water_input");
		blockRegistry.register(boilerWaterInput);
		boilerSteamOutput = new BlockBoilerSteamOutput(Material.IRON, "steam_output");
		blockRegistry.register(boilerSteamOutput);
		boilerSolidFirebox = new BlockBoilerSolidFirebox(Material.IRON, "solid_firebox");
		blockRegistry.register(boilerSolidFirebox);
		boilerLiquidFirebox = new BlockBoilerLiquidFirebox(Material.IRON, "boiler_liquid_firebox");
		blockRegistry.register(boilerLiquidFirebox);
		boilerMonitor = new BlockBoilerController(Material.IRON, "boiler_controller");
		blockRegistry.register(boilerMonitor);
		boilerPressureMonitor = new BlockBoilerPressureMonitor(Material.IRON, "boiler_pressuremonitor");
		blockRegistry.register(boilerPressureMonitor);
		boilerPressureValve = new BlockBoilerPressureValve(Material.IRON, "boiler_pressurevalve");
		blockRegistry.register(boilerPressureValve);

		alloyFurnaceController = new BlockAlloyFurnaceController(Material.ANVIL, "alloy_furnace_controller");
		// blockRegistry.register(alloyFurnaceController);
		alloyFurnaceFrame = new BlockAlloyFurnaceFrame(Material.ROCK, "alloy_furnace_frame");
		blockRegistry.register(alloyFurnaceFrame);
		alloyFurnaceHardFrame = new BlockAlloyFurnaceHardFrame(Material.ROCK, "alloy_furnace_hard_frame");
		blockRegistry.register(alloyFurnaceHardFrame);
		alloyFurnaceCoalInput = new BlockAlloyFurnaceItemInput(Material.ROCK, "alloy_furnace_item_input");
		blockRegistry.register(alloyFurnaceCoalInput);
		alloyFurnaceIronInput = new BlockAlloyFurnaceFluidInput(Material.ROCK, "alloy_furnace_fluid_input");
		blockRegistry.register(alloyFurnaceIronInput);
		alloyFurnaceSteelOutput = new BlockAlloyFurnaceFluidOutput(Material.ROCK, "alloy_furnace_fluid_output");
		blockRegistry.register(alloyFurnaceSteelOutput);

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
	}
}