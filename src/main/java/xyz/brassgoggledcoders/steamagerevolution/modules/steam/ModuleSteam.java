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
	public static BlockFluidClassic steam_block;

	public static Block brass_casing, water_input, solid_firebox, friction_firebox, water_tank, steam_output,
			steam_tank, boiler_controller, steam_turbine, mechanical_output, steam_input, turbine_frame;

	@Override
	public String getName() {
		return "Steam";
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
			steam_block = new BlockModFluid(Material.LAVA, "steam", steam);
			blockRegistry.registerBlock(steam_block);
		}

		brass_casing = new BlockBrassFrame(Material.IRON, "brass_frame");
		blockRegistry.registerBlock(brass_casing);
		water_input = new BlockWaterInput(Material.IRON, "water_input");
		blockRegistry.registerBlock(water_input);
		water_tank = new BlockWaterTank(Material.IRON, "water_tank");
		blockRegistry.registerBlock(water_tank);
		steam_output = new BlockSteamOutput(Material.IRON, "steam_output");
		blockRegistry.registerBlock(steam_output);
		steam_tank = new BlockSteamTank(Material.IRON, "steam_tank");
		blockRegistry.registerBlock(steam_tank);
		solid_firebox = new BlockSolidFirebox(Material.IRON, "solid_firebox");
		blockRegistry.registerBlock(solid_firebox);
		friction_firebox = new BlockFrictionFirebox(Material.IRON, "friction_firebox");
		blockRegistry.registerBlock(friction_firebox);
		boiler_controller = new BlockBoilerController(Material.IRON, "boiler_controller");
		blockRegistry.registerBlock(boiler_controller);

		steam_turbine = new BlockTurbine(Material.IRON, "steam_turbine");
		blockRegistry.registerBlock(steam_turbine);
		mechanical_output = new BlockMechanicalOutput(Material.IRON, "mechanical_output");
		blockRegistry.registerBlock(mechanical_output);
		steam_input = new BlockSteamInput(Material.IRON, "steam_input");
		blockRegistry.registerBlock(steam_input);
		turbine_frame = new BlockTurbineFrame(Material.IRON, "turbine_frame");
		blockRegistry.registerBlock(turbine_frame);

	}
}