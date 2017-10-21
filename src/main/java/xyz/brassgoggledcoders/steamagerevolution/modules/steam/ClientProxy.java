package xyz.brassgoggledcoders.steamagerevolution.modules.steam;

import com.teamacronymcoders.base.Base;
import com.teamacronymcoders.base.modulesystem.proxies.IModuleProxy;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.event.*;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;

public class ClientProxy implements IModuleProxy {

	@Override
	public void preInit(FMLPreInitializationEvent event) {}

	@Override
	public void init(FMLInitializationEvent event) {
		Base.instance.getLibProxy().registerFluidModel(FluidRegistry.getFluid("steam").getBlock(),
				new ResourceLocation(SteamAgeRevolution.MODID, "steam"));
		// ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBoilerWaterGauge.class,
		// new TileEntityBoilerWaterGaugeRenderer());
		// ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBoilerSteamGauge.class,
		// new TileEntityBoilerSteamGaugeRenderer());
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {}
}
