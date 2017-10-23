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
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.blocks.BlockSteamVent;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.boiler.ControllerBoiler;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.boiler.TileEntityBoilerPart;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.MultiblockBuilder;

@Module(value = SteamAgeRevolution.MODID)
public class ModuleSteam extends ModuleBase {

	public static Fluid steam;

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
		blockRegistry.register(new BlockFluidBase("steam", FluidRegistry.getFluid("steam"), Material.LAVA) {
			@Override
			public ResourceLocation getResourceLocation(IBlockState blockState) {
				return new ResourceLocation(SteamAgeRevolution.MODID, "steam");
			}
		});

		new MultiblockBuilder<ControllerBoiler>(blockRegistry, TileEntityBoilerPart.class, Material.IRON)
				.addNewPart("boiler_casing", MultiblockBuilder.allButInterior)
				.addNewFluidWrapperPart("boiler_steam_output", MultiblockBuilder.allFaces, "steam")
				.addNewFluidWrapperPart("boiler_liquid_firebox", MultiblockBuilder.bottomOnly, "firebox")
				.addNewFluidWrapperPart("boiler_water_input", MultiblockBuilder.allFaces, "water")
				.addNewItemWrapperPart("boiler_solid_firebox", MultiblockBuilder.bottomOnly, "firebox")
				.addNewTransparentPart("boiler_steam_gauge", MultiblockBuilder.allFaces)
				.addNewTransparentPart("boiler_water_gauge", MultiblockBuilder.allFaces).build();

		steamVent = new BlockSteamVent(Material.IRON, "steam_vent");
		blockRegistry.register(steamVent);
	}
}