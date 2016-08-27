package xyz.brassgoggledcoders.steamagerevolution.modules.steam;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import xyz.brassgoggledcoders.boilerplate.module.Module;
import xyz.brassgoggledcoders.boilerplate.module.ModuleBase;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.blocks.BlockBrassFrame;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.blocks.BlockFrictionFirebox;

@Module(mod = SteamAgeRevolution.MODID)
public class ModuleSteam extends ModuleBase {

	public static Fluid steam;
	public static BlockFluidClassic steam_block;

	public static Block brass_casing, fluid_port, solid_firebox, friction_firebox, tank;

	@Override
	public String getName() {
		return "Steam";
	}

	@Override
	public void preInit(FMLPreInitializationEvent event) {

		// TODO Adjust properties
		// steam = new Fluid("steam", new ResourceLocation(SteamAgeRevolution.MODID, "blocks/steam"),
		// new ResourceLocation(SteamAgeRevolution.MODID, "blocks/steam_flow")).setGaseous(true)
		// .setTemperature(1000).setViscosity(200);
		//
		// if(!(FluidRegistry.isFluidRegistered(steam))) { // Soft registration
		// FluidRegistry.registerFluid(steam);
		// FluidRegistry.addBucketForFluid(steam);
		// steam_block = new BlockFluidClassic(steam, Material.LAVA);
		// getBlockRegistry().registerBlock(steam_block);
		// }

		brass_casing = new BlockBrassFrame(Material.IRON, "brass_frame");
		getBlockRegistry().registerBlock(brass_casing);
		fluid_port = new BlockBrassFrame(Material.IRON, "fluid_port");
		getBlockRegistry().registerBlock(fluid_port);
		solid_firebox = new BlockBrassFrame(Material.IRON, "solid_firebox");
		getBlockRegistry().registerBlock(solid_firebox);
		friction_firebox = new BlockFrictionFirebox(Material.IRON, "friction_firebox");
		getBlockRegistry().registerBlock(friction_firebox);
		tank = new BlockBrassFrame(Material.IRON, "fluid_tank");
		getBlockRegistry().registerBlock(tank);

	}
}