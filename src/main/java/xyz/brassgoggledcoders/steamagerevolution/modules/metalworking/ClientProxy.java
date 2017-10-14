package xyz.brassgoggledcoders.steamagerevolution.modules.metalworking;

import com.teamacronymcoders.base.modulesystem.proxies.IModuleProxy;

import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.*;
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.hammer.tileentities.TileEntitySteamHammerAnvil;
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.hammer.tileentities.TileEntitySteamHammerAnvilRenderer;

public class ClientProxy implements IModuleProxy {

	@Override
	public void preInit(FMLPreInitializationEvent event) {}

	@Override
	public void init(FMLInitializationEvent event) {
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCastingBench.class,
				new TileEntityCastingBenchRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySteamHammerAnvil.class,
				new TileEntitySteamHammerAnvilRenderer());
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {}
}
