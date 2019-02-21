package xyz.brassgoggledcoders.steamagerevolution.modules.steam;

import com.teamacronymcoders.base.blocks.BlockFluidBase;
import com.teamacronymcoders.base.modulesystem.Module;
import com.teamacronymcoders.base.modulesystem.ModuleBase;
import com.teamacronymcoders.base.registrysystem.BlockRegistry;
import com.teamacronymcoders.base.registrysystem.config.ConfigEntry;
import com.teamacronymcoders.base.registrysystem.config.ConfigRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Property.Type;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.blocks.BlockSteamElevator;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.blocks.BlockSteamVent;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.boiler.blocks.*;

@ObjectHolder(value = SteamAgeRevolution.MODID)
@Module(value = SteamAgeRevolution.MODID)
public class ModuleSteam extends ModuleBase {

	public static final Block portable_boiler = null;
	public static Fluid steam;
	public static Block boilerCasing, boilerWaterInput, boilerSolidFirebox, boilerLiquidFirebox, boilerSteamOutput,
			boilerWaterGauge, boilerSteamGauge, boilerPressureMonitor, boilerPressureValve;
	
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
		getConfigRegistry().addEntry("enableDestruction",
				new ConfigEntry("general", "enableDestruction", Type.BOOLEAN, "false"));
		enableDestruction = getConfigRegistry().getBoolean("enableDestruction", false);
		super.preInit(event);
	}

	@Override
	public void registerBlocks(ConfigRegistry configRegistry, BlockRegistry blockRegistry) {

		// TODO Adjust properties
		steam = new Fluid("steam", new ResourceLocation(SteamAgeRevolution.MODID, "fluids/steam"),
				new ResourceLocation(SteamAgeRevolution.MODID, "fluids/steam_flow")).setGaseous(true)
						.setTemperature(1000).setViscosity(200);

		if(!(FluidRegistry.isFluidRegistered(steam))) { // Soft registration
			FluidRegistry.registerFluid(steam);
			FluidRegistry.addBucketForFluid(steam);
		}
		blockRegistry.register(new BlockFluidBase("steam", FluidRegistry.getFluid("steam"), Material.LAVA) {
			@Override
			public ResourceLocation getResourceLocation(IBlockState blockState) {
				return new ResourceLocation(SteamAgeRevolution.MODID, "steam");
			}
		});

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
		boilerWaterGauge = new BlockBoilerGauge(Material.IRON, "boiler_gauge");
		blockRegistry.register(boilerWaterGauge);
		boilerPressureMonitor = new BlockBoilerPressureMonitor(Material.IRON, "boiler_pressuremonitor");
		blockRegistry.register(boilerPressureMonitor);
		boilerPressureValve = new BlockBoilerPressureValve(Material.IRON, "boiler_pressurevalve");
		blockRegistry.register(boilerPressureValve);

		blockRegistry.register(new BlockSteamVent(Material.IRON, "steam_vent"));

		//blockRegistry.register(new BlockSteamElevator(Material.IRON, "steam_elevator"));

		// blockRegistry.register(new BlockPortableBoiler());
	}
}