package xyz.brassgoggledcoders.steamagerevolution.modules.steam;

import com.teamacronymcoders.base.blocks.BlockFluidBase;
import com.teamacronymcoders.base.modulesystem.Module;
import com.teamacronymcoders.base.modulesystem.ModuleBase;
import com.teamacronymcoders.base.registry.BlockRegistry;
import com.teamacronymcoders.base.registry.config.ConfigRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
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

@Module(value = SteamAgeRevolution.MODID)
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
			steamBlock = new BlockFluidBase("steam", steam, Material.LAVA);
			blockRegistry.register(steamBlock);
		}

		brassCasing = new BlockBrassFrame(Material.IRON, "brass_frame");
		blockRegistry.register(brassCasing);
		waterInput = new BlockWaterInput(Material.IRON, "water_input");
		blockRegistry.register(waterInput);
		waterTank = new BlockWaterTank(Material.IRON, "water_tank");
		blockRegistry.register(waterTank);
		steamOutput = new BlockSteamOutput(Material.IRON, "steam_output");
		blockRegistry.register(steamOutput);
		steamTank = new BlockSteamTank(Material.IRON, "steam_tank");
		blockRegistry.register(steamTank);
		solidFirebox = new BlockSolidFirebox(Material.IRON, "solid_firebox");
		blockRegistry.register(solidFirebox);
		frictionFirebox = new BlockFrictionFirebox(Material.IRON, "friction_firebox");
		blockRegistry.register(frictionFirebox);
		boilerController = new BlockBoilerController(Material.IRON, "boiler_controller");
		blockRegistry.register(boilerController);

		steamTurbine = new BlockTurbine(Material.IRON, "steam_turbine");
		blockRegistry.register(steamTurbine);
		mechanicalOutput = new BlockMechanicalOutput(Material.IRON, "mechanical_output");
		blockRegistry.register(mechanicalOutput);
		steamInput = new BlockSteamInput(Material.IRON, "steam_input");
		blockRegistry.register(steamInput);
		turbineFrame = new BlockTurbineFrame(Material.IRON, "turbine_frame");
		blockRegistry.register(turbineFrame);

	}
}