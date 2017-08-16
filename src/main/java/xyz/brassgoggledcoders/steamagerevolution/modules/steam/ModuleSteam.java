package xyz.brassgoggledcoders.steamagerevolution.modules.steam;

import com.teamacronymcoders.base.blocks.BlockFluidBase;
import com.teamacronymcoders.base.modulesystem.Module;
import com.teamacronymcoders.base.modulesystem.ModuleBase;
import com.teamacronymcoders.base.registrysystem.BlockRegistry;
import com.teamacronymcoders.base.registrysystem.config.ConfigEntry;
import com.teamacronymcoders.base.registrysystem.config.ConfigRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Property.Type;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.blocks.BlockSteamVent;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.boiler.blocks.BlockBoilerCasing;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.boiler.blocks.BlockBoilerLiquidFirebox;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.boiler.blocks.BlockBoilerPressureMonitor;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.boiler.blocks.BlockBoilerPressureValve;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.boiler.blocks.BlockBoilerSolidFirebox;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.boiler.blocks.BlockBoilerSteamGauge;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.boiler.blocks.BlockBoilerSteamOutput;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.boiler.blocks.BlockBoilerWaterGauge;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.boiler.blocks.BlockBoilerWaterInput;

@Module(value = SteamAgeRevolution.MODID)
public class ModuleSteam extends ModuleBase {

	public static Fluid steam;
	public static BlockFluidBase steamBlock;

	public static Block boilerCasing, boilerWaterInput, boilerSolidFirebox, boilerLiquidFirebox, boilerSteamOutput,
			boilerWaterGauge, boilerSteamGauge, boilerPressureMonitor, boilerPressureValve;
	public static Block steamVent;

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
		super.preInit(event);
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

		steamVent = new BlockSteamVent(Material.IRON, "steam_vent");
		blockRegistry.register(steamVent);
	}
}