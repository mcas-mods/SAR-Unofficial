package xyz.brassgoggledcoders.steamagerevolution.modules.steam;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import xyz.brassgoggledcoders.boilerplate.blocks.BlockModFluid;
import xyz.brassgoggledcoders.boilerplate.module.Module;
import xyz.brassgoggledcoders.boilerplate.module.ModuleBase;
import xyz.brassgoggledcoders.boilerplate.registries.BlockRegistry;
import xyz.brassgoggledcoders.boilerplate.registries.ConfigRegistry;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.blocks.multiblock.boiler.BlockBoilerController;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.blocks.multiblock.boiler.BlockBrassFrame;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.blocks.multiblock.boiler.BlockFrictionFirebox;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.blocks.multiblock.boiler.BlockSolidFirebox;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.blocks.multiblock.boiler.BlockSteamOutput;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.blocks.multiblock.boiler.BlockSteamTank;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.blocks.multiblock.boiler.BlockWaterInput;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.blocks.multiblock.boiler.BlockWaterTank;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.blocks.multiblock.turbine.BlockMechanicalOutput;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.blocks.multiblock.turbine.BlockSteamInput;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.blocks.multiblock.turbine.BlockTurbine;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.blocks.multiblock.turbine.BlockTurbineFrame;

@Module(mod = SteamAgeRevolution.MODID)
public class ModuleSteam extends ModuleBase {

	public static Fluid steam;
	public static BlockFluidClassic steamBlock;

	public static Block brassCasing, waterInput, solidFirebox, frictionFirebox, waterTank, steamOutput, steamTank,
			boilerController;
	public static Block steamTurbine, mechanicalOutput, steamInput, turbineFrame;

	@Override
	public String getName() {
		return "Steam";
	}

	@Override
	public String getClientProxyPath() {
		return "xyz.brassgoggledcoders.steamagerevolution.modules.steam.ClientProxy";
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
			steamBlock = new BlockModFluid(Material.LAVA, "steam", steam);
			blockRegistry.registerBlock(steamBlock);
		}

		brassCasing = new BlockBrassFrame(Material.IRON, "brass_frame");
		blockRegistry.registerBlock(brassCasing);
		waterInput = new BlockWaterInput(Material.IRON, "water_input");
		blockRegistry.registerBlock(waterInput);
		waterTank = new BlockWaterTank(Material.IRON, "water_tank");
		blockRegistry.registerBlock(waterTank);
		steamOutput = new BlockSteamOutput(Material.IRON, "steam_output");
		blockRegistry.registerBlock(steamOutput);
		steamTank = new BlockSteamTank(Material.IRON, "steam_tank");
		blockRegistry.registerBlock(steamTank);
		solidFirebox = new BlockSolidFirebox(Material.IRON, "solid_firebox");
		blockRegistry.registerBlock(solidFirebox);
		frictionFirebox = new BlockFrictionFirebox(Material.IRON, "friction_firebox");
		blockRegistry.registerBlock(frictionFirebox);
		boilerController = new BlockBoilerController(Material.IRON, "boiler_controller");
		blockRegistry.registerBlock(boilerController);

		steamTurbine = new BlockTurbine(Material.IRON, "steam_turbine");
		blockRegistry.registerBlock(steamTurbine);
		mechanicalOutput = new BlockMechanicalOutput(Material.IRON, "mechanical_output");
		blockRegistry.registerBlock(mechanicalOutput);
		steamInput = new BlockSteamInput(Material.IRON, "steam_input");
		blockRegistry.registerBlock(steamInput);
		turbineFrame = new BlockTurbineFrame(Material.IRON, "turbine_frame");
		blockRegistry.registerBlock(turbineFrame);

	}
}