package xyz.brassgoggledcoders.steamagerevolution.modules.steam;

import com.teamacronymcoders.base.Base;
import com.teamacronymcoders.base.modulesystem.proxies.IModuleProxy;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.boiler.renderers.TileEntityBoilerFluidMonitorRenderer;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.boiler.tileentities.TileEntityBoilerFluidMonitor;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.tileentities.TileEntityCastingBench;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.tileentities.TileEntityCastingBenchRenderer;

public class ClientProxy implements IModuleProxy {

	@Override
	public void preInit(FMLPreInitializationEvent event) {}

	@Override
	public void init(FMLInitializationEvent event) {
		Base.instance.getLibProxy().registerFluidModel(ModuleSteam.steamBlock,
				new ResourceLocation(SteamAgeRevolution.MODID, "steam"));
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCastingBench.class,
				new TileEntityCastingBenchRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBoilerFluidMonitor.class,
				new TileEntityBoilerFluidMonitorRenderer());
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {}
}
