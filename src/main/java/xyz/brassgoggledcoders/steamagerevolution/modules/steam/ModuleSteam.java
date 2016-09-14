package xyz.brassgoggledcoders.steamagerevolution.modules.steam;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import xyz.brassgoggledcoders.boilerplate.blocks.BlockModFluid;
import xyz.brassgoggledcoders.boilerplate.module.Module;
import xyz.brassgoggledcoders.boilerplate.module.ModuleBase;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.blocks.multiblock.boiler.BlockBoilerController;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.blocks.multiblock.boiler.BlockBrassFrame;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.blocks.multiblock.boiler.BlockFrictionFirebox;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.blocks.multiblock.boiler.BlockSolidFirebox;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.blocks.multiblock.boiler.BlockSteamOutput;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.blocks.multiblock.boiler.BlockSteamTank;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.blocks.multiblock.boiler.BlockWaterInput;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.blocks.multiblock.boiler.BlockWaterTank;

@Module(mod = SteamAgeRevolution.MODID)
public class ModuleSteam extends ModuleBase {

	public static Fluid steam;
	public static BlockFluidClassic steam_block;

	public static Block brass_casing, water_input, solid_firebox, friction_firebox, water_tank, steam_output,
			steam_tank, boiler_controller;

	@Override
	public String getName() {
		return "Steam";
	}

	@Override
	public void preInit(FMLPreInitializationEvent event) {

		// TODO Adjust properties
		steam = new Fluid("steam", new ResourceLocation(SteamAgeRevolution.MODID, "blocks/steam"),
				new ResourceLocation(SteamAgeRevolution.MODID, "blocks/steam_flow")).setGaseous(true)
						.setTemperature(1000).setViscosity(200);

		if(!(FluidRegistry.isFluidRegistered(steam))) { // Soft registration
			FluidRegistry.registerFluid(steam);
			FluidRegistry.addBucketForFluid(steam);
			steam_block = new BlockModFluid(Material.LAVA, "steam", steam);
			getBlockRegistry().registerBlock(steam_block);
		}

		brass_casing = new BlockBrassFrame(Material.IRON, "brass_frame");
		getBlockRegistry().registerBlock(brass_casing);
		water_input = new BlockWaterInput(Material.IRON, "water_input");
		getBlockRegistry().registerBlock(water_input);
		water_tank = new BlockWaterTank(Material.IRON, "water_tank");
		getBlockRegistry().registerBlock(water_tank);
		steam_output = new BlockSteamOutput(Material.IRON, "steam_output");
		getBlockRegistry().registerBlock(steam_output);
		steam_tank = new BlockSteamTank(Material.IRON, "steam_tank");
		getBlockRegistry().registerBlock(steam_tank);
		solid_firebox = new BlockSolidFirebox(Material.IRON, "solid_firebox");
		getBlockRegistry().registerBlock(solid_firebox);
		friction_firebox = new BlockFrictionFirebox(Material.IRON, "friction_firebox");
		getBlockRegistry().registerBlock(friction_firebox);
		boiler_controller = new BlockBoilerController(Material.IRON, "boiler_controller");
		getBlockRegistry().registerBlock(boiler_controller);
	}
}