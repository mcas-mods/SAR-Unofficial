package xyz.brassgoggledcoders.steamagerevolution.modules.processing;

import com.teamacronymcoders.base.modulesystem.proxies.IModuleProxy;

import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.*;
import xyz.brassgoggledcoders.steamagerevolution.modules.processing.multiblock.sawmill.TileEntitySawmillAxle;
import xyz.brassgoggledcoders.steamagerevolution.modules.processing.multiblock.sawmill.TileEntitySawmillAxleRenderer;

public class ClientProxy implements IModuleProxy {

	@Override
	public void preInit(FMLPreInitializationEvent event) {}

	@Override
	public void init(FMLInitializationEvent event) {
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySawmillAxle.class, new TileEntitySawmillAxleRenderer());
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {}
}
